/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Ying.Jin
 */

public class Station_Attr implements Serializable {
private static final long serialVersionUID = 3456879732123L;
    /**
     * @return the logKeeper
     */
    public String getLogKeeper() {
        return logKeeper;
    }

    /**
     * @param logKeeper the logKeeper to set
     */
    public void setLogKeeper(String logKeeper) {
        this.logKeeper = logKeeper;
    }

    /**
     * @return the agn_data
     */
    public String getAgn_data() {
        return agn_data;
    }

    /**
     * @param agn_data the agn_data to set
     */
    public void setAgn_data(String agn_data) {
        this.agn_data = agn_data;
    }

    /**
     * @return the antenna
     */
    public String getAntenna() {
        return antenna;
    }

    /**
     * @param antenna the antenna to set
     */
    public void setAntenna(String antenna) {
        this.antenna = antenna;
    }

    /**
     * @return the receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * @return the redomes
     */
    public String getRedomes() {
        return redomes;
    }

    /**
     * @param redomes the redomes to set
     */
    public void setRedomes(String redomes) {
        this.redomes = redomes;
    }

    /**
     * @return the networksS
     */
    public String getNetworksS() {
        return networksS;
    }

    /**
     * @param networksS the networksS to set
     */
    public void setNetworksS(String networksS) {
        this.networksS = networksS;
    }

    /**
     * @return the sampRate
     */
    public String getSampRate() {
        return sampRate;
    }

    /**
     * @param sampRate the sampRate to set
     */
    public void setSampRate(String sampRate) {
        this.sampRate = sampRate;
    }

    /**
     * @return the collRate
     */
    public String getCollRate() {
        return collRate;
    }

    /**
     * @param collRate the collRate to set
     */
    public void setCollRate(String collRate) {
        this.collRate = collRate;
    }

    /**
     * @return the constellationS
     */
    public String getConstellationS() {
        return constellationS;
    }

    /**
     * @param constellationS the constellationS to set
     */
    public void setConstellationS(String constellationS) {
        this.constellationS = constellationS;
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
     * @return the domes
     */
    public String getDomes() {
        return domes;
    }

    /**
     * @param domes the domes to set
     */
    public void setDomes(String domes) {
        this.domes = domes;
    }

    /**
     * @return the rines3
     */
    public String getRines3() {
        return rines3;
    }

    /**
     * @param rines3 the rines3 to set
     */
    public void setRines3(String rines3) {
        this.rines3 = rines3;
    }

    /**
     * @return the networks
     */
    public JSONObject getNetworks() {
        return networks;
    }

    /**
     * @param networks the networks to set
     */
    public void setNetworks(JSONObject networks) {
        this.networks = networks;
    }

    /**
     * @return the online_date
     */
    public String getOnline_date() {
        return online_date;
    }

    /**
     * @param online_date the online_date to set
     */
    public void setOnline_date(String online_date) {
        this.online_date = online_date;
    }

    /**
     * @return the ingest_info
     */
    public JSONObject getIngest_info() {
        return ingest_info;
    }

    /**
     * @param ingest_info the ingest_info to set
     */
    public void setIngest_info(JSONObject ingest_info) {
        this.ingest_info = ingest_info;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the op_history
     */
    public JSONObject getOp_history() {
        return op_history;
    }

    /**
     * @param op_history the op_history to set
     */
    public void setOp_history(JSONObject op_history) {
        this.op_history = op_history;
    }

    /**
     * @return the agn_resp
     */
    public String getAgn_resp() {
        return agn_resp;
    }

    /**
     * @param agn_resp the agn_resp to set
     */
    public void setAgn_resp(String agn_resp) {
        this.agn_resp = agn_resp;
    }

    /**
     * @return the agn_onsite
     */
    public String getAgn_onsite() {
        return agn_onsite;
    }

    /**
     * @param agn_onsite the agn_onsite to set
     */
    public void setAgn_onsite(String agn_onsite) {
        this.agn_onsite = agn_onsite;
    }

    /**
     * @return the agn_owner
     */
    public String getAgn_owner() {
        return agn_owner;
    }

    /**
     * @param agn_owner the agn_owner to set
     */
    public void setAgn_owner(String agn_owner) {
        this.agn_owner = agn_owner;
    }

    /**
     * @return the constellation
     */
    public JSONObject getConstellation() {
        return constellation;
    }

    /**
     * @param constellation the constellation to set
     */
    public void setConstellation(JSONObject constellation) {
        this.constellation = constellation;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    private String id;
    private String name;
    private String pid;
    private String domes;
    private String rines3;
    private JSONObject networks;
    private String online_date;
    private JSONObject ingest_info;

    private String logKeeper;
    private String status;
    private JSONObject op_history;

    private String city;
    private String state;
    private String country;
    private String agn_resp;
    private String agn_onsite;
    private String agn_owner;
    private String agn_data;
    private JSONObject constellation;

    private String networksS;
    private String sampRate;
    private String collRate;
    private String constellationS;
    private String antenna;
    private String receiver;
    private String redomes;

    public Station_Attr() {
    }

    public Station_Attr(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Station_Attr(String id, String name, String pid, String domes, String rines3,
            JSONObject networks,
            String online_date,
            JSONObject ingest_info,
            String status,
            JSONObject op_history,
            String agn_resp,
            String agn_onsite,
            String agn_owner,
            String agn_data,
            JSONObject constellation,String logkeeper) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.domes = domes;
        this.rines3 = rines3;
        this.networks = networks;
        this.online_date = online_date;
        this.ingest_info = ingest_info;
        this.status = status;
        this.op_history = op_history;
        this.agn_resp = agn_resp;
        this.agn_onsite = agn_onsite;
        this.agn_owner = agn_owner;
        this.agn_data = agn_data;
        this.constellation = constellation;
        this.logKeeper = logkeeper;
    }

    public Station_Attr(String id, String name, String pid, String domes, String rines3,
            String networksS,
            String online_date,
            String sampRate,
            String collRate,
            String status,
            String agn_resp,
            String constellationS,String ant, String rec, String dom) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.domes = domes;
        this.rines3 = rines3;
        this.networksS = networksS;
        this.online_date = online_date;
        this.sampRate = sampRate;
        this.collRate = collRate;
        this.status = status;
        this.agn_resp = agn_resp;
        this.constellationS = constellationS;
        this.antenna = ant;
        this.receiver = rec;
        this.redomes = dom;
    }
}
