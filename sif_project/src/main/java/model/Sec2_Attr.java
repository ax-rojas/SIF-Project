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
public class Sec2_Attr  {

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
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the tectonicPlate
     */
    public String getTectonicPlate() {
        return tectonicPlate;
    }

    /**
     * @param tectonicPlate the tectonicPlate to set
     */
    public void setTectonicPlate(String tectonicPlate) {
        this.tectonicPlate = tectonicPlate;
    }

    /**
     * @return the x
     */
    public String getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(String x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public String getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(String y) {
        this.y = y;
    }

    /**
     * @return the z
     */
    public String getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(String z) {
        this.z = z;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the elevation
     */
    public String getElevation() {
        return elevation;
    }

    /**
     * @param elevation the elevation to set
     */
    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    private String sectionTitle;
    private String city;
    private String state;
    private String country;
    private String tectonicPlate;
    private String x;
    private String y;
    private String z;
    private String latitude;
    private String longitude;
    private String elevation;
   // private String addInfo;
    private List<String> addInfo;
    public Sec2_Attr() {
    }

    public Sec2_Attr(String siteIdentification,
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
            List<String> addInfo
    ) {

        this.sectionTitle = sectionTitle;
        this.city = city;
        this.state = state;
        this.country = country;
        this.tectonicPlate = tectonicPlate;
        this.x = x;
        this.y = y;
        this.z = z;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.addInfo = addInfo;
    }
}
