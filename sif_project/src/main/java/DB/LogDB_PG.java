/*
 * LogDB_PG.java
 *
 * Database access layer for the NCN/MMT PostgreSQL tables.
 * Updated to source data from:
 *   - mmt.slm_sitealias          (station ID / rinex3 lookup)
 *   - mmt.slm_siteidentification (section 1 – site monument info)
 *   - mmt.slm_sitelocation       (section 2 – XYZ / LLH coordinates)
 *   - mmt.slm_siteantenna        (section 4 – antenna history)
 *   - mmt.slm_antenna            (antenna model lookup)
 *   - mmt.slm_radome             (radome model lookup)
 *   - mmt.slm_corslog            (CORS log metadata, OPT via ocean_tide)
 *   - mmt.slm_sitereceiver       (section 3 – receiver history)
 *   - mmt.slm_receiver           (receiver model lookup)
 *   - nsrsdb.cors_stations       (ocean tide loading – FES2014B)
 *   - nsrsdb.ncn_cf_seg          (occupation segments / coordinates)
 *   - nsrsdb.ncn_seasonal_terms  (nonlinear / seasonal terms)
 *   - nsrsdb.ncn_psd_terms       (post-seismic deformation terms)
 *
 * @author Ying.Jin
 */
package DB;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gov.noaa.ngs.ldap.LdapAuthenticator;
import gov.noaa.ngs.ldap.LdapException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sifapplication.Validation;

public class LogDB_PG {

    private static final Logger LOGGER = Logger.getLogger(LogDB_PG.class.getName());

    public LogDB_PG() {
    }

    // ---------------------------------------------------------------
    //  Utility – JSON helpers
    // ---------------------------------------------------------------
    public JSONArray parseArr(JSONParser parser, String s) throws ParseException {
        if (s == null) return new JSONArray();
        Object tmp = parser.parse(s);
        return tmp != null ? (JSONArray) tmp : null;
    }

    public JSONObject parseObj(JSONParser parser, String s) throws ParseException {
        if (s == null) return new JSONObject();
        Object tmp = parser.parse(s);
        return tmp != null ? (JSONObject) tmp : null;
    }

    public static boolean isValidJson(String json) {
        try {
            if (json == null) return false;
            new JSONParser().parse(json);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private static String safe(String val) {
        if (val == null) return "";
        val = val.trim();
        if (val.startsWith("(") || val.startsWith("DEF_VAL")) return "";
        return val;
    }

    public List<String> getAddInfoLst(JSONParser parser, String add) {
        List<String> addInfo = new ArrayList<>();
        if (Validation.isValidJson(add)) {
            try {
                JSONObject addObj = Validation.parseObj(parser, add);
                if (addObj != null) {
                    JSONArray addJArr = (JSONArray) addObj.get("addInfo");
                    if (addJArr != null && addJArr.size() > 0) {
                        for (int i = 0; i < addJArr.size(); i++) {
                            addInfo.add((String) addJArr.get(i));
                        }
                    }
                }
            } catch (ParseException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return addInfo;
    }

    // ---------------------------------------------------------------
    //  Station existence check (mmt.slm_sitealias)
    // ---------------------------------------------------------------
    public boolean isNewStation(LdapAuthenticator ldap, String stationId) throws SQLException {
        DBconnection dbc = new DBconnection();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT site_name FROM mmt.slm_sitealias WHERE lower(site_name) = ?";
        try {
            conn = dbc.getPGConn(ldap);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stationId.toLowerCase());
            rs = pstmt.executeQuery();
            if (rs.next()) return false;
        } catch (LdapException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (pstmt != null) pstmt.close();
            if (rs != null) rs.close();
            dbc.CloseConnection(conn);
        }
        return true;
    }

    // ---------------------------------------------------------------
    //  Station list
    // ---------------------------------------------------------------
    public List<String> getStationList(LdapAuthenticator ldap, String which) throws SQLException {
        List<String> stationLst = new ArrayList<>();
        DBconnection dbc = new DBconnection();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;
        switch (which) {
            case "1":
                sql = "SELECT sa.site_name FROM mmt.slm_sitealias sa "
                    + " JOIN mmt.slm_corslog cl ON sa.site_id = cl.site_id "
                    + " WHERE cl.log_keeper IS NOT NULL AND cl.log_keeper != 'IGSNET' "
                    + " ORDER BY sa.site_name";
                break;
            case "2":
                sql = "SELECT sa.site_name FROM mmt.slm_sitealias sa "
                    + " JOIN mmt.slm_corslog cl ON sa.site_id = cl.site_id "
                    + " WHERE cl.log_keeper = 'IGSNET' ORDER BY sa.site_name";
                break;
            default:
                sql = "SELECT sa.site_name FROM mmt.slm_sitealias sa "
                    + " JOIN mmt.slm_corslog cl ON sa.site_id = cl.site_id "
                    + " WHERE cl.log_keeper IS NOT NULL ORDER BY sa.site_name";
                break;
        }
        try {
            conn = dbc.getPGConn(ldap);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) stationLst.add(rs.getString(1));
        } catch (LdapException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (pstmt != null) pstmt.close();
            if (rs != null) rs.close();
            dbc.CloseConnection(conn);
        }
        return stationLst;
    }

    // ---------------------------------------------------------------
    //  Rinex3 ID = site_name from slm_sitealias
    // ---------------------------------------------------------------
    public String getRinex3ID(LdapAuthenticator ldap, String stationId) throws SQLException {
        return stationId.toUpperCase();
    }

    // ---------------------------------------------------------------
    //  Log keeper check
    // ---------------------------------------------------------------
    public boolean isNGSkeeper(LdapAuthenticator ldap, String stationId) throws SQLException, Exception {
        DBconnection dbc = new DBconnection();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT cl.log_keeper FROM mmt.slm_corslog cl "
                   + " JOIN mmt.slm_sitealias sa ON cl.site_id = sa.site_id "
                   + " WHERE upper(sa.site_name) = ?";
        try {
            conn = dbc.getPGConn(ldap);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stationId.toUpperCase());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String keeper = rs.getString(1);
                if (keeper != null && keeper.trim().equalsIgnoreCase("NGS")) return true;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (pstmt != null) pstmt.close();
            if (rs != null) rs.close();
            dbc.CloseConnection(conn);
        }
        return false;
    }

    // ---------------------------------------------------------------
    //  Build SIF_Attr_PG: sec1, sec2, sec3, sec4, OTL, OPT
    // ---------------------------------------------------------------
    public SIF_Attr_PG getSIF_PG(LdapAuthenticator ldap, String stationId) throws SQLException {
        SIF_Attr_PG sif = new SIF_Attr_PG();
        DBconnection dbc = new DBconnection();
        Connection conn = null;
        JSONParser parser = new JSONParser();

        String sqlAlias = "SELECT site_id, four_char_id FROM mmt.slm_sitealias WHERE lower(site_name) = ?";

        String sqlSec1 = "SELECT site_name, iers_domes_number, cdp_number, "
                       + " monument_description, monument_height, monument_foundation, "
                       + " foundation_depth, marker_description, date_installed, "
                       + " geologic_characteristic, bedrock_type, bedrock_condition, "
                       + " fracture_spacing, fault_zones, distance, additional_information "
                       + " FROM mmt.slm_siteidentification WHERE site_id = ? "
                       + " AND published = true ORDER BY id DESC LIMIT 1";

        String sqlSec2 = "SELECT city, state, country, tectonic, "
                       + " ST_X(xyz) AS x, ST_Y(xyz) AS y, ST_Z(xyz) AS z, "
                       + " ST_Y(llh) AS latitude, ST_X(llh) AS longitude, ST_Z(llh) AS elevation, "
                       + " additional_information "
                       + " FROM mmt.slm_sitelocation WHERE site_id = ? "
                       + " AND published = true ORDER BY id DESC LIMIT 1";

        String sqlSec4 = "SELECT a.model AS antenna_type, sa.serial_number, "
                       + " sa.reference_point, "
                       + " ST_X(sa.marker_une) AS up_ecc, "
                       + " ST_Y(sa.marker_une) AS north_ecc, "
                       + " ST_Z(sa.marker_une) AS east_ecc, "
                       + " sa.alignment, r.model AS radome, "
                       + " sa.radome_serial_number, sa.cable_type, sa.cable_length, "
                       + " sa.installed, sa.removed, sa.additional_information "
                       + " FROM mmt.slm_siteantenna sa "
                       + " LEFT JOIN mmt.slm_antenna a ON sa.antenna_type_id = a.id "
                       + " LEFT JOIN mmt.slm_radome r ON sa.radome_type_id = r.id "
                       + " WHERE sa.site_id = ? AND sa.is_deleted = false "
                       + " ORDER BY sa.installed ASC";

        // Section 3 – Receiver (from mmt.slm_sitereceiver + mmt.slm_receiver)
        // mmt.slm_sitereceiver columns: serial_number, firmware, elevation_cutoff,
        //   installed, removed, temp_stabilized, additional_info, receiver_type_id
        // mmt.slm_receiver columns: model (receiver type name), satellite_system
        String sqlSec3 = "SELECT r.model AS receiver_type, r.satellite_system, "
                       + " sr.serial_number, sr.firmware, "
                       + " sr.elevation_cutoff, sr.installed, sr.removed, "
                       + " sr.temp_stabilized, sr.additional_info "
                       + " FROM mmt.slm_sitereceiver sr "
                       + " LEFT JOIN mmt.slm_receiver r ON sr.receiver_type_id = r.id "
                       + " WHERE sr.site_id = ? AND sr.is_deleted = false "
                       + " ORDER BY sr.installed ASC";

        String sqlOTL = "SELECT ocean_tide FROM nsrsdb.cors_stations WHERE station_id = ?";

        String sqlOPT = "SELECT cl.ocean_tide FROM mmt.slm_corslog cl "
                      + " JOIN mmt.slm_sitealias sa ON cl.site_id = sa.site_id "
                      + " WHERE lower(sa.site_name) = ?";

        PreparedStatement ps1 = null, ps2 = null, ps3 = null, ps4 = null, ps5 = null, ps6 = null, ps0 = null;
        ResultSet rs0 = null, rs1 = null, rs2 = null, rs3 = null, rs4 = null, rs5 = null, rs6 = null;

        try {
            conn = dbc.getPGConn(ldap);

            // resolve site_id
            int siteId = -1;
            String fourCharId = "";
            ps0 = conn.prepareStatement(sqlAlias);
            ps0.setString(1, stationId.toLowerCase());
            rs0 = ps0.executeQuery();
            if (rs0.next()) {
                siteId = rs0.getInt(1);
                fourCharId = rs0.getString(2);
            } else {
                LOGGER.warning("Station not found in slm_sitealias: " + stationId);
                return sif;
            }
            rs0.close(); ps0.close();

            // Section 1
            Sec1_Attr sec1 = new Sec1_Attr();
            ps1 = conn.prepareStatement(sqlSec1);
            ps1.setInt(1, siteId);
            rs1 = ps1.executeQuery();
            if (rs1.next()) {
                sec1.setSiteName(safe(rs1.getString("site_name")));
                sec1.setFourCharID(fourCharId);
                sec1.setIersDomes(safe(rs1.getString("iers_domes_number")));
                sec1.setCdpNumber(safe(rs1.getString("cdp_number")));
                sec1.setMonDescription(safe(rs1.getString("monument_description")));
                sec1.setMonHeight(safe(rs1.getString("monument_height")));
                sec1.setMonFoundation(safe(rs1.getString("monument_foundation")));
                sec1.setDepthFoundation(safe(rs1.getString("foundation_depth")));
                sec1.setMarkerDescription(safe(rs1.getString("marker_description")));
                sec1.setDateInstalled(safe(rs1.getString("date_installed")));
                sec1.setGeoCharacter(safe(rs1.getString("geologic_characteristic")));
                sec1.setBedrockType(safe(rs1.getString("bedrock_type")));
                sec1.setBedrockCond(safe(rs1.getString("bedrock_condition")));
                sec1.setFractSpacing(safe(rs1.getString("fracture_spacing")));
                sec1.setFaultZoneNearby(safe(rs1.getString("fault_zones")));
                sec1.setDistanceActivity(safe(rs1.getString("distance")));
                String addRaw = rs1.getString("additional_information");
                if (addRaw != null && !addRaw.isEmpty()) {
                    List<String> addLst = new ArrayList<>();
                    addLst.add(addRaw);
                    sec1.setAddInfo(addLst);
                }
            }
            rs1.close(); ps1.close();

            // Section 2
            Sec2_Attr sec2 = new Sec2_Attr();
            ps2 = conn.prepareStatement(sqlSec2);
            ps2.setInt(1, siteId);
            rs2 = ps2.executeQuery();
            if (rs2.next()) {
                sec2.setCity(safe(rs2.getString("city")));
                sec2.setState(safe(rs2.getString("state")));
                sec2.setCountry(safe(rs2.getString("country")));
                sec2.setTectonicPlate(safe(rs2.getString("tectonic")));
                sec2.setX(rs2.getString("x"));
                sec2.setY(rs2.getString("y"));
                sec2.setZ(rs2.getString("z"));
                sec2.setLatitude(rs2.getString("latitude"));
                sec2.setLongitude(rs2.getString("longitude"));
                sec2.setElevation(rs2.getString("elevation"));
                String add2 = rs2.getString("additional_information");
                if (add2 != null && !add2.isEmpty()) {
                    List<String> lst = new ArrayList<>();
                    lst.add(add2);
                    sec2.setAddInfo(lst);
                }
            }
            rs2.close(); ps2.close();

            sif.setSec1(sec1);
            sif.setSec2(sec2);

            // Section 4 – Antenna
            List<Sec4_Attr> sec4List = new ArrayList<>();
            ps3 = conn.prepareStatement(sqlSec4);
            ps3.setInt(1, siteId);
            rs3 = ps3.executeQuery();
            while (rs3.next()) {
                Sec4_Attr s4 = new Sec4_Attr();
                s4.setAntennaType(safe(rs3.getString("antenna_type")));
                s4.setSerialNumber(safe(rs3.getString("serial_number")));
                s4.setArp(safe(rs3.getString("reference_point")));
                s4.setUp(rs3.getString("up_ecc"));
                s4.setNorth(rs3.getString("north_ecc"));
                s4.setEast(rs3.getString("east_ecc"));
                s4.setAlignment(safe(rs3.getString("alignment")));
                s4.setRadome(safe(rs3.getString("radome")));
                s4.setRadomeSN(safe(rs3.getString("radome_serial_number")));
                s4.setAntennaCableType(safe(rs3.getString("cable_type")));
                s4.setAntennaCableLength(safe(rs3.getString("cable_length")));
                s4.setDateInstalled(safe(rs3.getString("installed")));
                s4.setDateRemoved(safe(rs3.getString("removed")));
                String addRaw = rs3.getString("additional_information");
                List<String> addLst4 = new ArrayList<>();
                if (addRaw != null && !addRaw.isEmpty()) addLst4.add(addRaw);
                s4.setAddInfo(addLst4);
                sec4List.add(s4);
            }
            rs3.close(); ps3.close();
            sif.setSec4(sec4List);

            // Section 3 – Receiver (from mmt.slm_sitereceiver + mmt.slm_receiver)
            List<Sec3_Attr> sec3List = new ArrayList<>();
            ps4 = conn.prepareStatement(sqlSec3);
            ps4.setInt(1, siteId);
            rs4 = ps4.executeQuery();
            while (rs4.next()) {
                Sec3_Attr s3 = new Sec3_Attr();
                s3.setReceiverType(safe(rs4.getString("receiver_type")));
                s3.setSatSystem(safe(rs4.getString("satellite_system")));
                s3.setSerialNumber(safe(rs4.getString("serial_number")));
                s3.setFirmwareVer(safe(rs4.getString("firmware")));
                s3.setElevCutoff(safe(rs4.getString("elevation_cutoff")));
                s3.setDateInstalled(safe(rs4.getString("installed")));
                s3.setDateRemoved(safe(rs4.getString("removed")));
                s3.setTempStabil(safe(rs4.getString("temp_stabilized")));
                List<String> addLst3 = new ArrayList<>();
                String addRaw3 = rs4.getString("additional_info");
                if (addRaw3 != null && !addRaw3.trim().isEmpty()) {
                    addLst3.add(addRaw3.trim());
                }
                s3.setAddInfo(addLst3);
                s3.setSubSection("");
                sec3List.add(s3);
            }
            rs4.close(); ps4.close();
            sif.setSec3(sec3List);

            // OTL – FES2014B from nsrsdb.cors_stations
            OTL_Attr otl = null;
            ps5 = conn.prepareStatement(sqlOTL);
            ps5.setString(1, stationId.toUpperCase());
            rs5 = ps5.executeQuery();
            if (rs5.next()) {
                String otlRaw = rs5.getString("ocean_tide");
                if (otlRaw != null && !otlRaw.isEmpty() && isValidJson(otlRaw)) {
                    otl = new Gson().fromJson(otlRaw, OTL_Attr.class);
                }
            }
            rs5.close(); ps5.close();
            sif.setOtl(otl);

            // OPT – from mmt.slm_corslog ocean_tide JSON
            OPT_Attr opt = null;
            ps6 = conn.prepareStatement(sqlOPT);
            ps6.setString(1, stationId.toLowerCase());
            rs6 = ps6.executeQuery();
            if (rs6.next()) {
                String rawJson = rs6.getString("ocean_tide");
                if (rawJson != null && !rawJson.isEmpty() && isValidJson(rawJson)) {
                    JSONObject otObj = parseObj(parser, rawJson);
                    if (otObj != null) {
                        Object optObj = otObj.get("OCEAN_POLE_TIDE");
                        if (optObj != null) {
                            opt = new Gson().fromJson(optObj.toString(), OPT_Attr.class);
                        }
                    }
                }
            }
            rs6.close(); ps6.close();
            sif.setOpt(opt);

        } catch (LdapException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            dbc.CloseConnection(conn);
        }
        return sif;
    }

    // ---------------------------------------------------------------
    //  Station metadata (for DDS/map use)
    // ---------------------------------------------------------------
    public Station_Attr getStationInfo(LdapAuthenticator ldap, String id) throws SQLException {
        Station_Attr data = new Station_Attr();
        DBconnection dbc = new DBconnection();
        Connection conn = null;
        JSONParser parser = new JSONParser();

        String sql = "SELECT sa.site_name, cl.domes_number, cl.cors_network, "
                   + " TO_CHAR(cl.online_date,'YYYYDDD'), cl.ingest_info, "
                   + " cl.operational_status, cl.operational_history, cl.constellation, "
                   + " si.site_name AS description, sl.city, sl.state, sl.country, "
                   + " cl.onsite_agency, cl.responsible_agency, cl.owner_agency, "
                   + " cl.datacenter_agency, cl.log_keeper "
                   + " FROM mmt.slm_sitealias sa "
                   + " JOIN mmt.slm_corslog cl ON sa.site_id = cl.site_id "
                   + " LEFT JOIN mmt.slm_siteidentification si ON sa.site_id = si.site_id AND si.published = true "
                   + " LEFT JOIN mmt.slm_sitelocation sl ON sa.site_id = sl.site_id AND sl.published = true "
                   + " WHERE lower(sa.site_name) = ?";
        try {
            conn = dbc.getPGConn(ldap);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id.toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                data.setId(id.toUpperCase());
                data.setRines3(rs.getString(1));
                data.setDomes(rs.getString(2));
                if (isValidJson(rs.getString(3))) {
                    data.setNetworks(parseObj(parser, rs.getString(3)));
                } else {
                    data.setNetworks(new JSONObject());
                }
                data.setOnline_date(rs.getString(4));
                if (isValidJson(rs.getString(5))) {
                    data.setIngest_info(parseObj(parser, rs.getString(5)));
                } else {
                    data.setIngest_info(new JSONObject());
                }
                data.setStatus(rs.getString(6));
                if (isValidJson(rs.getString(7))) {
                    data.setOp_history(parseObj(parser, rs.getString(7)));
                } else {
                    data.setOp_history(new JSONObject());
                }
                String sat_s = "";
                if (isValidJson(rs.getString(8))) {
                    JSONObject SAT_O = parseObj(parser, rs.getString(8));
                    if (SAT_O != null) {
                        JSONArray SAT_A = (JSONArray) SAT_O.get("constellation");
                        if (SAT_A != null) {
                            for (int i = 0; i < SAT_A.size(); ++i) {
                                JSONObject rec = (JSONObject) SAT_A.get(i);
                                sat_s += rec.get("type") + " ";
                            }
                            data.setConstellationS(sat_s.trim());
                        }
                    }
                }
                data.setName(rs.getString(9));
                data.setCity(rs.getString(10));
                data.setState(rs.getString(11));
                data.setCountry(rs.getString(12));
                data.setAgn_onsite(rs.getString(13));
                data.setAgn_resp(rs.getString(14));
                data.setAgn_owner(rs.getString(15));
                data.setAgn_data(rs.getString(16));
                data.setLogKeeper(rs.getString(17));
            }
            rs.close(); pstmt.close();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            dbc.CloseConnection(conn);
        }
        return data;
    }

    // ---------------------------------------------------------------
    //  Occupation segments from nsrsdb.ncn_cf_seg
    //  Reads the coord JSON directly — values already in target units
    // ---------------------------------------------------------------
    public List<JsonObject> getOccupationSegments(LdapAuthenticator ldap, String stationId) throws SQLException {
        List<JsonObject> occLst = new ArrayList<>();
        DBconnection dbc = new DBconnection();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JsonParser gsonParser = new JsonParser();

        String sql = "SELECT coord FROM nsrsdb.ncn_cf_seg "
                   + " WHERE station_id = ? ORDER BY start_date";
        try {
            conn = dbc.getPGConn(ldap);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stationId.toUpperCase());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String raw = rs.getString("coord");
                if (raw != null && !raw.isEmpty()) {
                    try {
                        JsonObject obj = gsonParser.parse(raw).getAsJsonObject();
                        occLst.add(obj);
                    } catch (Exception e) {
                        LOGGER.warning("Bad coord JSON for " + stationId);
                    }
                }
            }
        } catch (LdapException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (pstmt != null) pstmt.close();
            if (rs != null) rs.close();
            dbc.CloseConnection(conn);
        }
        return occLst;
    }

    // ---------------------------------------------------------------
    //  Approximate coordinates from latest ncn_cf_seg segment
    // ---------------------------------------------------------------
    public double[] getApproximateCoordinates(LdapAuthenticator ldap, String stationId) throws SQLException {
        double[] xyz = {0.0, 0.0, 0.0};
        DBconnection dbc = new DBconnection();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JsonParser gsonParser = new JsonParser();

        String sql = "SELECT coord FROM nsrsdb.ncn_cf_seg "
                   + " WHERE station_id = ? ORDER BY seqnum DESC LIMIT 1";
        try {
            conn = dbc.getPGConn(ldap);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stationId.toUpperCase());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String raw = rs.getString("coord");
                if (raw != null && !raw.isEmpty()) {
                    JsonObject obj = gsonParser.parse(raw).getAsJsonObject();
                    JsonArray coords = obj.getAsJsonArray("COORDINATES");
                    if (coords != null && coords.size() >= 3) {
                        xyz[0] = coords.get(0).getAsDouble();
                        xyz[1] = coords.get(1).getAsDouble();
                        xyz[2] = coords.get(2).getAsDouble();
                    }
                }
            }
        } catch (LdapException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (pstmt != null) pstmt.close();
            if (rs != null) rs.close();
            dbc.CloseConnection(conn);
        }
        return xyz;
    }

    // ---------------------------------------------------------------
    //  Seasonal / Nonlinear Terms from nsrsdb.ncn_seasonal_terms
    // ---------------------------------------------------------------
    public JsonArray getSeasonalTerms(LdapAuthenticator ldap, String stationId) throws SQLException {
        JsonArray termsArr = new JsonArray();
        DBconnection dbc = new DBconnection();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JsonParser gsonParser = new JsonParser();

        String sql = "SELECT seasonal_terms FROM nsrsdb.ncn_seasonal_terms "
                   + " WHERE station_id = ? ORDER BY seqnum";
        try {
            conn = dbc.getPGConn(ldap);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stationId.toUpperCase());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String raw = rs.getString("seasonal_terms");
                if (raw != null && !raw.isEmpty()) {
                    try {
                        termsArr.add(gsonParser.parse(raw));
                    } catch (Exception e) {
                        LOGGER.warning("Bad seasonal_terms JSON for " + stationId);
                    }
                }
            }
        } catch (LdapException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (pstmt != null) pstmt.close();
            if (rs != null) rs.close();
            dbc.CloseConnection(conn);
        }
        return termsArr;
    }

    // ---------------------------------------------------------------
    //  PSD Terms from nsrsdb.ncn_psd_terms
    // ---------------------------------------------------------------
    public JsonArray getPsdTerms(LdapAuthenticator ldap, String stationId) throws SQLException {
        JsonArray psdArr = new JsonArray();
        DBconnection dbc = new DBconnection();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JsonParser gsonParser = new JsonParser();

        String sql = "SELECT psd FROM nsrsdb.ncn_psd_terms "
                   + " WHERE station_id = ? ORDER BY seqnum";
        try {
            conn = dbc.getPGConn(ldap);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stationId.toUpperCase());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String raw = rs.getString("psd");
                if (raw != null && !raw.isEmpty()) {
                    try {
                        psdArr.add(gsonParser.parse(raw));
                    } catch (Exception e) {
                        LOGGER.warning("Bad psd JSON for " + stationId);
                    }
                }
            }
        } catch (LdapException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (pstmt != null) pstmt.close();
            if (rs != null) rs.close();
            dbc.CloseConnection(conn);
        }
        return psdArr;
    }
}
