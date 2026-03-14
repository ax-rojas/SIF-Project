/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import org.json.simple.JSONObject;

/**
 *
 * @author Ying.Jin
 */
public class Occupation_Attr {

    /**
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * @return the cors_type
     */
    public String getCors_type() {
        return cors_type;
    }

    /**
     * @param cors_type the cors_type to set
     */
    public void setCors_type(String cors_type) {
        this.cors_type = cors_type;
    }

    /**
     * @return the designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * @param designation the designation to set
     */
    public void setDesignation(String designation) {
        this.designation = designation;
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
     * @return the COVARIANCE_6by6
     */
    public JSONObject getCOVARIANCE_6by6() {
        return COVARIANCE_6by6;
    }

    /**
     * @param COVARIANCE_6by6 the COVARIANCE_6by6 to set
     */
    public void setCOVARIANCE_6by6(JSONObject COVARIANCE_6by6) {
        this.COVARIANCE_6by6 = COVARIANCE_6by6;
    }

    /**
     * @return the start_date
     */
    public String getStart_date() {
        return start_date;
    }

    /**
     * @param start_date the start_date to set
     */
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    /**
     * @param vel_sigma the vel_sigma to set
     */
    public void setVel_sigma(JSONObject vel_sigma) {
        this.vel_sigma = vel_sigma;
    }

    public Occupation_Attr() {
    }
    private String start_date;
    private String end_date;
    private String x;
    private String y;
    private String z;
    private JSONObject pos_sigma;
    private String vx;
    private String vy;
    private String vz;
    private JSONObject vel_sigma;
    private JSONObject COVARIANCE_6by6;
    private String designation;
    private String longitude;
    private String latitude;
    private String cors_type;
    private String pid;

    public Occupation_Attr(String start, String end, String x, String y, String z,
            JSONObject p_sigma, String vx, String vy, String vz, JSONObject v_sigma) {
        this.start_date = start;
        this.end_date = end;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pos_sigma = p_sigma;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.vel_sigma = v_sigma;
    }

    public Occupation_Attr(String start, String end, String x, String y, String z,
            JSONObject p_sigma, String vx, String vy, String vz, JSONObject v_sigma, JSONObject covariance) {
        this.start_date = start;
        this.end_date = end;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pos_sigma = p_sigma;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.vel_sigma = v_sigma;
        this.COVARIANCE_6by6 = covariance;
    }

    public Occupation_Attr(String start, String end, String x, String y, String z,
            JSONObject p_sigma, String vx, String vy, String vz, JSONObject v_sigma, JSONObject covariance,
            String designation, String longitude, String latitude,String cors_type, String pid) {
        this.start_date = start;
        this.end_date = end;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pos_sigma = p_sigma;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.vel_sigma = v_sigma;
        this.COVARIANCE_6by6 = covariance;
        this.designation = designation;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cors_type = cors_type;
        this.pid = pid;
    }
    
    public Occupation_Attr(String start, String end, String x, String y, String z,
            JSONObject p_sigma, String vx, String vy, String vz, JSONObject v_sigma, JSONObject covariance,
            String longitude, String latitude,String cors_type, String pid) {
        this.start_date = start;
        this.end_date = end;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pos_sigma = p_sigma;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.vel_sigma = v_sigma;
        this.COVARIANCE_6by6 = covariance;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cors_type = cors_type;
        this.pid = pid;
    }

    /**
     * @return the end_date
     */
    public String getEnd_date() {
        return end_date;
    }

    /**
     * @param end_date the end_date to set
     */
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
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
     * @return the pos_sigma
     */
    public JSONObject getPos_sigma() {
        return pos_sigma;
    }

    /**
     * @param pos_sigma the pos_sigma to set
     */
    public void setPos_sigma(JSONObject pos_sigma) {
        this.pos_sigma = pos_sigma;
    }

    /**
     * @return the vx
     */
    public String getVx() {
        return vx;
    }

    /**
     * @param vx the vx to set
     */
    public void setVx(String vx) {
        this.vx = vx;
    }

    /**
     * @return the vy
     */
    public String getVy() {
        return vy;
    }

    /**
     * @param vy the vy to set
     */
    public void setVy(String vy) {
        this.vy = vy;
    }

    /**
     * @return the vz
     */
    public String getVz() {
        return vz;
    }

    /**
     * @param vz the vz to set
     */
    public void setVz(String vz) {
        this.vz = vz;
    }

    /**
     * @return the vel_sigma
     */
    public JSONObject getVel_sigma() {
        return vel_sigma;
    }

}
