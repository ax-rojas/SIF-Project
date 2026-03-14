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
public class Sec3_Attr {

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

    /**
     * @return the receiverType
     */
    public String getReceiverType() {
        return receiverType;
    }

    /**
     * @param receiverType the receiverType to set
     */
    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    /**
     * @return the satSystem
     */
    public String getSatSystem() {
        return satSystem;
    }

    /**
     * @param satSystem the satSystem to set
     */
    public void setSatSystem(String satSystem) {
        this.satSystem = satSystem;
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
     * @return the firmwareVer
     */
    public String getFirmwareVer() {
        return firmwareVer;
    }

    /**
     * @param firmwareVer the firmwareVer to set
     */
    public void setFirmwareVer(String firmwareVer) {
        this.firmwareVer = firmwareVer;
    }

    /**
     * @return the elevCutoff
     */
    public String getElevCutoff() {
        return elevCutoff;
    }

    /**
     * @param elevCutoff the elevCutoff to set
     */
    public void setElevCutoff(String elevCutoff) {
        this.elevCutoff = elevCutoff;
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
     * @return the tempStabil
     */
    public String getTempStabil() {
        return tempStabil;
    }

    /**
     * @param tempStabil the tempStabil to set
     */
    public void setTempStabil(String tempStabil) {
        this.tempStabil = tempStabil;
    }

//    /**
//     * @return the addInfo
//     */
//    public String getAddInfo() {
//        return addInfo;
//    }
//
//    /**
//     * @param addInfo the addInfo to set
//     */
//    public void setAddInfo(String addInfo) {
//        this.addInfo = addInfo;
//    }
//    private String sectionTitle;
//    private String receiver;
    private String SubSection;
    private String receiverType;
    private String satSystem;
    private String serialNumber;
    private String firmwareVer;
    private String elevCutoff;
    private String dateInstalled;
    private String dateRemoved;
    private String tempStabil;
    private List<String> addInfo;

    public Sec3_Attr() {
    }
    public Sec3_Attr(
            String subsection,
            String sectionTitle,
            String receiver,
            String receiverType,
            String satSystem,
            String serialNumber,
            String firmwareVer,
            String elevCutoff,
            String dateInstalled,
            String dateRemoved,
            String tempStabil,
            List<String> addInfo) {
        this.SubSection = subsection;
        this.receiverType = receiverType;
        this.satSystem = satSystem;
        this.serialNumber = serialNumber;
        this.firmwareVer = firmwareVer;
        this.elevCutoff = elevCutoff;
        this.dateInstalled = dateInstalled;
        this.dateRemoved = dateRemoved;
        this.tempStabil = tempStabil;
        this.addInfo = addInfo;
    }
}
