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
public class Sec1_Attr  {

    
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
        this.setAddInfo(addInfo);
    }

    /**
     * @return the sectionTitle
     */
    public String getSectionTitle() {
        return sectionTitle;
    }

    /**
     * @param sectionTitle the sectionTitle to set
     */
    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    /**
     * @return the siteName
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @param siteName the siteName to set
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * @return the fourCharID
     */
    public String getFourCharID() {
        return fourCharID;
    }

    /**
     * @param fourCharID the fourCharID to set
     */
    public void setFourCharID(String fourCharID) {
        this.fourCharID = fourCharID;
    }

    /**
     * @return the monInscription
     */
    public String getMonInscription() {
        return monInscription;
    }

    /**
     * @param monInscription the monInscription to set
     */
    public void setMonInscription(String monInscription) {
        this.monInscription = monInscription;
    }

    /**
     * @return the iersDomes
     */
    public String getIersDomes() {
        return iersDomes;
    }

    /**
     * @param iersDomes the iersDomes to set
     */
    public void setIersDomes(String iersDomes) {
        this.iersDomes = iersDomes;
    }

    /**
     * @return the cdpNumber
     */
    public String getCdpNumber() {
        return cdpNumber;
    }

    /**
     * @param cdpNumber the cdpNumber to set
     */
    public void setCdpNumber(String cdpNumber) {
        this.cdpNumber = cdpNumber;
    }

    /**
     * @return the monDescription
     */
    public String getMonDescription() {
        return monDescription;
    }

    /**
     * @param monDescription the monDescription to set
     */
    public void setMonDescription(String monDescription) {
        this.monDescription = monDescription;
    }

    /**
     * @return the monHeight
     */
    public String getMonHeight() {
        return monHeight;
    }

    /**
     * @param monHeight the monHeight to set
     */
    public void setMonHeight(String monHeight) {
        this.monHeight = monHeight;
    }

    /**
     * @return the monFoundation
     */
    public String getMonFoundation() {
        return monFoundation;
    }

    /**
     * @param monFoundation the monFoundation to set
     */
    public void setMonFoundation(String monFoundation) {
        this.monFoundation = monFoundation;
    }

    /**
     * @return the depthFoundation
     */
    public String getDepthFoundation() {
        return depthFoundation;
    }

    /**
     * @param depthFoundation the depthFoundation to set
     */
    public void setDepthFoundation(String depthFoundation) {
        this.depthFoundation = depthFoundation;
    }

    /**
     * @return the markerDescription
     */
    public String getMarkerDescription() {
        return markerDescription;
    }

    /**
     * @param markerDescription the markerDescription to set
     */
    public void setMarkerDescription(String markerDescription) {
        this.markerDescription = markerDescription;
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
     * @return the geoCharacter
     */
    public String getGeoCharacter() {
        return geoCharacter;
    }

    /**
     * @param geoCharacter the geoCharacter to set
     */
    public void setGeoCharacter(String geoCharacter) {
        this.geoCharacter = geoCharacter;
    }

    /**
     * @return the bedrockType
     */
    public String getBedrockType() {
        return bedrockType;
    }

    /**
     * @param bedrockType the bedrockType to set
     */
    public void setBedrockType(String bedrockType) {
        this.bedrockType = bedrockType;
    }

    /**
     * @return the bedrockCond
     */
    public String getBedrockCond() {
        return bedrockCond;
    }

    /**
     * @param bedrockCond the bedrockCond to set
     */
    public void setBedrockCond(String bedrockCond) {
        this.bedrockCond = bedrockCond;
    }

    /**
     * @return the fractSpacing
     */
    public String getFractSpacing() {
        return fractSpacing;
    }

    /**
     * @param fractSpacing the fractSpacing to set
     */
    public void setFractSpacing(String fractSpacing) {
        this.fractSpacing = fractSpacing;
    }

    /**
     * @return the faultZoneNearby
     */
    public String getFaultZoneNearby() {
        return faultZoneNearby;
    }

    /**
     * @param faultZoneNearby the faultZoneNearby to set
     */
    public void setFaultZoneNearby(String faultZoneNearby) {
        this.faultZoneNearby = faultZoneNearby;
    }

    /**
     * @return the distanceActivity
     */
    public String getDistanceActivity() {
        return distanceActivity;
    }

    /**
     * @param distanceActivity the distanceActivity to set
     */
    public void setDistanceActivity(String distanceActivity) {
        this.distanceActivity = distanceActivity;
    }

   

    public Sec1_Attr() {
    }

    public Sec1_Attr(String siteIdentification,
            String sectionTitle,
            String siteName,
            String fourCharID,
            String monInscription,
            String iersDomes,
            String cdpNumber,
            String monDescription,
            String monHeight,
            String monFoundation,
            String depthFoundation,
            String markerDescription,
            String dateInstalled,
            String geoCharacter,
            String bedrockType,
            String bedrockCond,
            String fractSpacing,
            String faultZoneNearby,
            String distanceActivity,
            List<String> addInfo) {
        this.sectionTitle = sectionTitle;
        this.siteName = siteName;
        this.fourCharID = fourCharID;
        this.monInscription = monInscription;
        this.iersDomes = iersDomes;
        this.cdpNumber = cdpNumber;
        this.monDescription = monDescription;
        this.monHeight = monHeight;
        this.monFoundation = monFoundation;
        this.depthFoundation = depthFoundation;
        this.markerDescription = markerDescription;
        this.dateInstalled = dateInstalled;
        this.geoCharacter = geoCharacter;
        this.bedrockType = bedrockType;
        this.bedrockCond = bedrockCond;
        this.fractSpacing = fractSpacing;
        this.faultZoneNearby = faultZoneNearby;
        this.distanceActivity = distanceActivity;
        this.addInfo = addInfo;
    }
    private String sectionTitle;
    private String siteName;
    private String fourCharID;
    private String monInscription;
    private String iersDomes;
    private String cdpNumber;
    private String monDescription;
    private String monHeight;
    private String monFoundation;
    private String depthFoundation;
    private String markerDescription;
    private String dateInstalled;
    private String geoCharacter;
    private String bedrockType;
    private String bedrockCond;
    private String fractSpacing;
    private String faultZoneNearby;
    private String distanceActivity;
    private List<String>addInfo;

}
