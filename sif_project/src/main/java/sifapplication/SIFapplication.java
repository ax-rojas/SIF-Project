/*
 * SIFapplication.java
 *
 * Generates the NCN20 SIF JSON file.
 * Output format matches ncn20_SIF example:
 *   METADATA          – updated for ITRF2020, FES2014B, FORMAT_VERSION 1
 *   Per station:
 *     ID              – from mmt.slm_sitealias.site_name
 *     DOMES           – from mmt.slm_siteidentification
 *     DESCRIPTION     – from mmt.slm_siteidentification.site_name
 *     APPROXIMATE_COORDINATES – from latest nsrsdb.ncn_cf_seg segment (rounded)
 *     OCEAN_TIDE_LOADING      – from nsrsdb.cors_stations (FES2014B)
 *     OCEAN_POLE_TIDE         – from mmt.slm_corslog.ocean_tide
 *     ANTENNA[]       – from mmt.slm_siteantenna (with antenna/radome lookups)
 *     RECEIVER[]      – from mmt.slm_sitereceiver + mmt.slm_receiver
 *     OCCUPATION[]    – from nsrsdb.ncn_cf_seg coord JSON (full precision, direct)
 *     NONLINEAR_TERMS – PERIODIC_TERMS from nsrsdb.ncn_seasonal_terms
 *     PSD_TERMS       – from nsrsdb.ncn_psd_terms (when present)
 *
 * Usage:  java sifapplication.SIFapplication [forWhich] [station1 station2 ...]
 *   forWhich: 0=all, 1=NGS-kept, 2=IGSNET-kept
 *
 * @author Ying.Jin
 */
package sifapplication;

import DB.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gov.noaa.ngs.ldap.LdapAuthenticator;
import gov.noaa.ngs.ldap.LdapException;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.*;
import org.json.simple.JSONObject;

public class SIFapplication {

    private static final Logger LOGGER = Logger.getLogger(SIFapplication.class.getName());

    // -----------------------------------------------------------------
    //  METADATA block – ncn20 format
    // -----------------------------------------------------------------
    private static JsonObject metadataJson() {
        JsonObject m = new JsonObject();
        m.addProperty("FORMAT_VERSION", 1);
        m.addProperty("REVISION", "$Id$");
        m.addProperty("ORGANIZATION", "NGS");

        JsonArray sources = new JsonArray();
        sources.add("/ngslib/data/GPS/cors_20.bin");
        sources.add("/ngs/projects/trf/repro3/proc_fin/mycs3/source_files/MYCS3_final.snx");
        sources.add("/ngs/projects/trf/repro3/proc_fin/mycs3/source_files/MYCS3_final.snx");
        sources.add("/ngs/projects/trf/repro3/proc_fin/mycs3/source_files/seasonal_terms_geocenter_corrected.txt");
        m.add("SOURCE_FILES", sources);

        m.addProperty("REFERENCE_FRAME", "ITRF2020");
        m.addProperty("REFERENCE_EPOCH", 2020);
        m.addProperty("ANTENNA_ORIENTATION_UNITS", "deg");
        m.addProperty("ANTENNA_ARP_MINUS_GRP_ENU_UNITS", "m");
        m.addProperty("COORDINATE_UNITS", "m");
        m.addProperty("VELOCITY_UNITS", "mm/y");
        m.addProperty("AUTO_COVARIANCE_UNITS_COORDINATES", "mm_sq");
        m.addProperty("AUTO_COVARIANCE_UNITS_VELOCITIES ", "mm_sq/yr_sq");
        m.addProperty("CROSS_COVARIANCE_UNITS_COORDINATES_VELOCITIES ", "mm_sq/yr");
        m.addProperty("OPT_UNITS", "m");
        m.addProperty("OTL_MODEL", "FES2014B");
        m.addProperty("OTL_AMPLITUDE_UNITS", "m");
        m.addProperty("OTL_PHASE_UNITS", "deg");
        m.addProperty("PERIOD_UNITS", "y");
        m.addProperty("PERIODIC_COEFF_UNITS", "mm");
        m.addProperty("PSD_COEFF_UNITS", "m");
        m.addProperty("PSD_TIME_UNITS", "y");
        return m;
    }

    // -----------------------------------------------------------------
    //  OPT conversion
    // -----------------------------------------------------------------
    private static JsonObject convertDoubleOPT(OPT_Attr opt) {
        JsonObject o = new JsonObject();
        o.addProperty("COMMENT", opt.getCOMMENT());
        o.addProperty("NORTH_REAL",      BigDecimal.valueOf(opt.getNORTH_REAL()));
        o.addProperty("NORTH_IMAGINARY", BigDecimal.valueOf(opt.getNORTH_IMAGINARY()));
        o.addProperty("EAST_REAL",       BigDecimal.valueOf(opt.getEAST_REAL()));
        o.addProperty("EAST_IMAGINARY",  BigDecimal.valueOf(opt.getEAST_IMAGINARY()));
        o.addProperty("UP_REAL",         BigDecimal.valueOf(opt.getUP_REAL()));
        o.addProperty("UP_IMAGINARY",    BigDecimal.valueOf(opt.getUP_IMAGINARY()));
        return o;
    }

    // -----------------------------------------------------------------
    //  OTL section conversion
    // -----------------------------------------------------------------
    private static JsonObject convertDoubleFormat(JSONObject input) {
        JsonObject o = new JsonObject();
        if (input != null && !input.isEmpty()) {
            OTL_Sec_Attr sec = new Gson().fromJson(input.toString(), OTL_Sec_Attr.class);
            o.addProperty("UP_AMPLITUDE",    BigDecimal.valueOf(sec.getUP_AMPLITUDE()));
            o.addProperty("EAST_AMPLITUDE",  BigDecimal.valueOf(sec.getEAST_AMPLITUDE()));
            o.addProperty("NORTH_AMPLITUDE", BigDecimal.valueOf(sec.getNORTH_AMPLITUDE()));
            o.addProperty("UP_PHASE",        BigDecimal.valueOf(sec.getUP_PHASE()));
            o.addProperty("EAST_PHASE",      BigDecimal.valueOf(sec.getEAST_PHASE()));
            o.addProperty("NORTH_PHASE",     BigDecimal.valueOf(sec.getNORTH_PHASE()));
        }
        return o;
    }

    // -----------------------------------------------------------------
    //  Date format conversion
    // -----------------------------------------------------------------
    public static String convertDateFormat(String installedDate) {
        String sifDate = "";
        SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd'T'HH':'mm'Z'");
        SimpleDateFormat f0 = new SimpleDateFormat("yyyy-MM-dd'T'HH':'mm':'ss");
        try {
            if (installedDate != null) {
                installedDate = installedDate.trim();
                if (installedDate.length() >= 10 && !installedDate.contains("CCYY-MM-DD")) {
                    switch (installedDate.length()) {
                        case 10: sifDate = f0.format(f1.parse(installedDate)); break;
                        case 17: sifDate = f0.format(f2.parse(installedDate)); break;
                        case 19: sifDate = "9999-12-31T00:00:00"; break;
                        default:
                            sifDate = f0.format(f1.parse(installedDate.substring(0, 10)));
                            break;
                    }
                } else {
                    sifDate = "9999-12-31T00:00:00";
                }
            } else {
                sifDate = "9999-12-31T00:00:00";
            }
        } catch (java.text.ParseException ex) {
            LOGGER.log(Level.SEVERE, "Date format error: " + installedDate, ex);
        }
        return sifDate;
    }

    public static boolean isNumeric(String s) {
        if (s == null) return false;
        try { Double.parseDouble(s); } catch (NumberFormatException e) { return false; }
        return true;
    }

    private static double toDouble(String val) {
        if (val == null) return 0.0;
        try { return Double.parseDouble(val); } catch (NumberFormatException e) { return 0.0; }
    }

    // =================================================================
    //  collectDataFromDB_PG  –  produces ncn20_SIF station JSON
    // =================================================================
    private static JsonObject collectDataFromDB_PG(String station) {

        JsonObject stationEl = new JsonObject();
        LogDB_PG db = new LogDB_PG();

        try {
            LdapAuthenticator ldap = new LdapAuthenticator();

            if (!db.isNewStation(ldap, station)) {

                // ---- SIF sections from mmt tables ----
                SIF_Attr_PG sif = db.getSIF_PG(ldap, station);
                Sec1_Attr sec1   = sif.getSec1();
                Sec2_Attr sec2   = sif.getSec2();
                List<Sec3_Attr> sec3 = sif.getSec3();
                List<Sec4_Attr> sec4 = sif.getSec4();
                OPT_Attr opt = sif.getOpt();
                OTL_Attr otl = sif.getOtl();

                // ---- ID / DOMES / DESCRIPTION ----
                String rinex3id = db.getRinex3ID(ldap, station);
                String domex = (sec1.getIersDomes() != null
                        && !sec1.getIersDomes().trim().startsWith("DEF_VAL")
                        && !sec1.getIersDomes().trim().equalsIgnoreCase("(A9)"))
                        ? sec1.getIersDomes() : "";
                String desc = (sec1.getSiteName() != null
                        && !sec1.getSiteName().trim().startsWith("DEF_VAL"))
                        ? sec1.getSiteName().toUpperCase() : "";

                stationEl.addProperty("ID", rinex3id != null ? rinex3id : "");
                stationEl.addProperty("DOMES", domex);
                stationEl.addProperty("DESCRIPTION", desc);

                // ---- APPROXIMATE_COORDINATES (latest ncn_cf_seg, rounded) ----
                double[] approx = db.getApproximateCoordinates(ldap, station);
                DecimalFormat df3 = new DecimalFormat("#.###");
                JsonArray coordArr = new JsonArray();
                coordArr.add(Double.parseDouble(df3.format(approx[0])));
                coordArr.add(Double.parseDouble(df3.format(approx[1])));
                coordArr.add(Double.parseDouble(df3.format(approx[2])));
                stationEl.add("APPROXIMATE_COORDINATES", coordArr);

                // ---- OCEAN_TIDE_LOADING ----
                JsonObject otlJson = new JsonObject();
                if (otl != null) {
                    com.google.gson.JsonArray comment = otl.getCOMMENT();
                    otlJson.add("COMMENT", comment);
                    otlJson.add("M2",  convertDoubleFormat(otl.getM2()));
                    otlJson.add("S2",  convertDoubleFormat(otl.getS2()));
                    otlJson.add("N2",  convertDoubleFormat(otl.getN2()));
                    otlJson.add("K2",  convertDoubleFormat(otl.getK2()));
                    otlJson.add("K1",  convertDoubleFormat(otl.getK1()));
                    otlJson.add("O1",  convertDoubleFormat(otl.getO1()));
                    otlJson.add("P1",  convertDoubleFormat(otl.getP1()));
                    otlJson.add("Q1",  convertDoubleFormat(otl.getQ1()));
                    otlJson.add("MF",  convertDoubleFormat(otl.getMF()));
                    otlJson.add("MM",  convertDoubleFormat(otl.getMM()));
                    otlJson.add("SSA", convertDoubleFormat(otl.getSSA()));
                }
                stationEl.add("OCEAN_TIDE_LOADING", otlJson);

                // ---- OCEAN_POLE_TIDE ----
                JsonObject optJson = new JsonObject();
                if (opt != null) {
                    optJson = convertDoubleOPT(opt);
                }
                stationEl.add("OCEAN_POLE_TIDE", optJson);

                // ---- ANTENNA (from mmt.slm_siteantenna) ----
                JsonArray antennas = new JsonArray();
                if (sec4 != null && !sec4.isEmpty()) {
                    for (Sec4_Attr s4 : sec4) {
                        JsonObject ae = new JsonObject();

                        String startT = convertDateFormat(s4.getDateInstalled()) + " UTC";
                        String endT   = convertDateFormat(s4.getDateRemoved())   + " UTC";

                        // TYPE = antenna (padded to 16) + radome
                        String antType = s4.getAntennaType();
                        if (antType != null && antType.trim().length() < 20) {
                            String radome = s4.getRadome();
                            antType = antType.trim();
                            while (antType.length() < 16) antType += " ";
                            antType = antType + (radome != null ? radome : "");
                        }

                        double orientD = 0.0;
                        String orientS = s4.getAlignment();
                        if (isNumeric(orientS)) {
                            orientD = toDouble(orientS);
                        } else if (orientS != null) {
                            String tmp = orientS.replace("deg.", "").replace("deg", "").trim();
                            if (isNumeric(tmp)) orientD = toDouble(tmp);
                        }

                        ae.addProperty("START", startT);
                        ae.addProperty("END", endT);
                        ae.addProperty("TYPE", antType);
                        ae.addProperty("SERIAL_NUMBER", s4.getSerialNumber());

                        JsonArray enu = new JsonArray();
                        enu.add(toDouble(s4.getEast()));
                        enu.add(toDouble(s4.getNorth()));
                        enu.add(toDouble(s4.getUp()));
                        ae.add("ARP_MINUS_GRP_ENU", enu);

                        ae.addProperty("ORIENTATION", orientD);
                        ae.addProperty("DO_NOT_USE", false);

                        // SOURCE from additional_information
                        List<String> addLst = s4.getAddInfo();
                        String source = "";
                        if (addLst != null && !addLst.isEmpty()) {
                            StringBuilder sb = new StringBuilder();
                            for (String s : addLst) sb.append(s).append(" ");
                            source = sb.toString().trim();
                        }
                        ae.addProperty("SOURCE", source);

                        antennas.add(ae);
                    }
                }
                stationEl.add("ANTENNA", antennas);

                // ---- RECEIVER (from nsrsdb_old.cors_receivers) ----
                JsonArray receivers = new JsonArray();
                if (sec3 != null && !sec3.isEmpty()) {
                    for (Sec3_Attr s3 : sec3) {
                        JsonObject re = new JsonObject();

                        String startT = convertDateFormat(s3.getDateInstalled()) + " UTC";
                        String endT   = convertDateFormat(s3.getDateRemoved())   + " UTC";

                        // Satellite system single-letter codes
                        String oneLetterSats = "";
                        String sats = s3.getSatSystem();
                        if (sats != null && !sats.isEmpty()) {
                            for (String ss : sats.split("\\+")) {
                                switch (ss.trim().toUpperCase()) {
                                    case "GPS":  oneLetterSats += "G"; break;
                                    case "GLO":  oneLetterSats += "R"; break;
                                    case "GAL":  oneLetterSats += "E"; break;
                                    case "BDS":  oneLetterSats += "C"; break;
                                    case "QZSS": oneLetterSats += "J"; break;
                                    default:     oneLetterSats += "G"; break;
                                }
                            }
                        }

                        re.addProperty("START", startT);
                        re.addProperty("END", endT);
                        re.addProperty("TYPE", s3.getReceiverType());
                        re.addProperty("SERIAL_NUMBER", s3.getSerialNumber());
                        re.addProperty("FIRMWARE", s3.getFirmwareVer());
                        re.addProperty("SATELLITE_SYSTEMS", oneLetterSats);
                        re.addProperty("DO_NOT_USE", false);

                        // SOURCE from additional_info (stored in addInfo list)
                        List<String> addLst = s3.getAddInfo();
                        String src = "";
                        if (addLst != null && !addLst.isEmpty()) {
                            StringBuilder sb = new StringBuilder();
                            for (String si : addLst) sb.append(si).append(" ");
                            src = sb.toString().trim();
                        }
                        re.addProperty("SOURCE", src);

                        receivers.add(re);
                    }
                }
                stationEl.add("RECEIVER", receivers);

                // ---- OCCUPATION (directly from nsrsdb.ncn_cf_seg coord JSON) ----
                List<JsonObject> occSegments = db.getOccupationSegments(ldap, station);
                JsonArray occArr = new JsonArray();
                for (JsonObject seg : occSegments) {
                    // The coord JSON already contains all fields in the target format:
                    // START, END, REFERENCE_FRAME, REFERENCE_EPOCH, COORDINATES,
                    // COORDINATE_SIGMAS, VELOCITY, VELOCITY_SIGMAS,
                    // 6X6_COVARIANCE_MATRIX, DO_NOT_USE, SOURCE
                    occArr.add(seg);
                }
                stationEl.add("OCCUPATION", occArr);

                // ---- NONLINEAR_TERMS (seasonal terms) ----
                JsonArray seasonalRaw = db.getSeasonalTerms(ldap, station);
                if (seasonalRaw.size() > 0) {
                    JsonObject nlTerms = new JsonObject();
                    nlTerms.add("PERIODIC_TERMS", seasonalRaw);
                    stationEl.add("NONLINEAR_TERMS", nlTerms);
                }

                // ---- PSD_TERMS (post-seismic deformation, when present) ----
                JsonArray psdRaw = db.getPsdTerms(ldap, station);
                if (psdRaw.size() > 0) {
                    stationEl.add("PSD_TERMS", psdRaw);
                }

            } else {
                System.out.println("     <" + station + " > ID IS NOT FOUND ");
            }
        } catch (LdapException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return stationEl;
    }

    // =================================================================
    //  main
    //
    //  Usage: java sifapplication.SIFapplication [forWhich] [station1 ...]
    //    forWhich: 0=all, 1=NGS, 2=IGSNET
    //    Stations must be 9-char rinex3 IDs (e.g. 1LSU00USA)
    // =================================================================
    public static void main(String[] args) {
        System.out.println("SIF START : " + new Date());

        String forWhich = "0";
        List<String> stationLst = new ArrayList<>();

        if (args.length >= 1) {
            forWhich = args[0];
        }
        for (int i = 1; i < args.length; i++) {
            String s = args[i].trim().toUpperCase();
            if (s.length() == 9) {
                stationLst.add(s);
            }
        }

        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dtStr = sdf.format(new Date());

        try {
            LdapAuthenticator ldap = new LdapAuthenticator();
            LogDB_PG db = new LogDB_PG();

            JsonObject sifObj = new JsonObject();
            JsonArray stations = new JsonArray();
            JsonObject metadata = metadataJson();

            if (stationLst.isEmpty()) {
                stationLst = db.getStationList(ldap, forWhich);
            }

            int total = 0;
            for (String id : stationLst) {
                total++;
                if (id != null && id.trim().length() == 9) {
                    System.out.println("**** INPUT STATION: <" + id + ">");
                    JsonObject stObj = collectDataFromDB_PG(id.trim().toUpperCase());
                    if (!stObj.entrySet().isEmpty()) {
                        stations.add(stObj);
                    }
                } else {
                    System.out.println("**** INPUT STATION: <" + id + "> INVALID (must be 9 chars) ****");
                }
            }

            sifObj.add("METADATA", metadata);
            sifObj.add("STATIONS", stations);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(sifObj);

            String fileName = "SIF_" + forWhich + "_" + dtStr + ".json";
            try (FileWriter file = new FileWriter(fileName)) {
                file.write(json);
                System.out.println("TOTAL STATIONS: " + total);
                System.out.println("OUTPUT FILE: " + fileName);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

        } catch (LdapException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        System.out.println("SIF END : " + new Date());
    }
}
