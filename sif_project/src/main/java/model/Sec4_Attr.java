/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Ying.Jin
 */
public class Sec4_Attr  {

    /**
     * @return the SubSection
     */
    public String getSubSection() {
        return SubSection;
    }

    /**
     * @param SubSection the SubSection to set
     */
    public void setSubSection(String SubSection) {
        this.SubSection = SubSection;
    }

    /**
     * @return the antennaType
     */
    public String getAntennaType() {
        return antennaType;
    }

    /**
     * @param antennaType the antennaType to set
     */
    public void setAntennaType(String antennaType) {
        this.antennaType = antennaType;
    }

    /**
     * @return the serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @return the arp
     */
    public String getArp() {
        return arp;
    }

    /**
     * @param arp the arp to set
     */
    public void setArp(String arp) {
        this.arp = arp;
    }

    /**
     * @return the up
     */
    public String getUp() {
        return up;
    }

    /**
     * @param up the up to set
     */
    public void setUp(String up) {
        this.up = up;
    }

    /**
     * @return the north
     */
    public String getNorth() {
        return north;
    }

    /**
     * @param north the north to set
     */
    public void setNorth(String north) {
        this.north = north;
    }

    /**
     * @return the east
     */
    public String getEast() {
        return east;
    }

    /**
     * @param east the east to set
     */
    public void setEast(String east) {
        this.east = east;
    }

    /**
     * @return the alignment
     */
    public String getAlignment() {
        return alignment;
    }

    /**
     * @param alignment the alignment to set
     */
    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    /**
     * @return the radome
     */
    public String getRadome() {
        return radome;
    }

    /**
     * @param radome the radome to set
     */
    public void setRadome(String radome) {
        this.radome = radome;
    }

    /**
     * @return the radomeSN
     */
    public String getRadomeSN() {
        return radomeSN;
    }

    /**
     * @param radomeSN the radomeSN to set
     */
    public void setRadomeSN(String radomeSN) {
        this.radomeSN = radomeSN;
    }

    /**
     * @return the antennaCableType
     */
    public String getAntennaCableType() {
        return antennaCableType;
    }

    /**
     * @param antennaCableType the antennaCableType to set
     */
    public void setAntennaCableType(String antennaCableType) {
        this.antennaCableType = antennaCableType;
    }

    /**
     * @return the antennaCableLength
     */
    public String getAntennaCableLength() {
        return antennaCableLength;
    }

    /**
     * @param antennaCableLength the antennaCableLength to set
     */
    public void setAntennaCableLength(String antennaCableLength) {
        this.antennaCableLength = antennaCableLength;
    }

    /**
     * @return the dateInstalled
     */
    public String getDateInstalled() {
        return dateInstalled;
    }

    /**
     * @param dateInstalled the dateInstalled to set
     */
    public void setDateInstalled(String dateInstalled) {
        this.dateInstalled = dateInstalled;
    }

    /**
     * @return the dateRemoved
     */
    public String getDateRemoved() {
        return dateRemoved;
    }

    /**
     * @param dateRemoved the dateRemoved to set
     */
    public void setDateRemoved(String dateRemoved) {
        this.dateRemoved = dateRemoved;
    }

    /**
     * @return the addInfo
     */
    public List<String> getAddInfo() {
        return addInfo;
    }

    /**
     * @param addInfo the addInfo to set
     */
    public void setAddInfo(List<String> addInfo) {
        this.addInfo = addInfo;
    }

    private String SubSection;
    private String antennaType;
    private String serialNumber;
    private String arp;
    private String up;
    private String north;
    private String east;
    private String alignment;
    private String radome;
    private String radomeSN;
    private String antennaCableType;
    private String antennaCableLength;
    private String dateInstalled;
    private String dateRemoved;
    private List<String> addInfo;
    public Sec4_Attr(){}
    
    public Sec4_Attr(
    String subsection,        
    String antennaType,
    String serialNumber,
    String arp,
    String up,
    String north,
    String east,
    String alignment,
    String radome,
    String radomeSN,
    String antennaCableType,
    String antennaCableLength,
    String dateInstalled,
    String dateRemoved,
    List<String> addInfo) {
        this.SubSection = subsection;
        this.antennaType = antennaType;
        this.serialNumber= serialNumber;
        this.arp = arp;
        this.up = up;
        this.north = north;
        this.east = east;
        this.alignment = alignment;
        this.radome = radome;
        this.radomeSN = radomeSN;
        this.antennaCableType = antennaCableType;
        this.antennaCableLength = antennaCableLength;
        this.dateInstalled = dateInstalled;
        this.dateRemoved = dateRemoved;
        this.addInfo = addInfo;
    }    
}
