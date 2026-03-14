/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.google.gson.JsonArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 *
 * @author Ying.Jin
 */
public class OTL_Attr {

    /**
     * @return the COMMENTS
     */
    public JsonArray getCOMMENT() {
        return COMMENT;
    }

    /**
     * @param COMMENT the COMMENTS to set
     */
    public void setCOMMENT(JsonArray COMMENT) {
        this.COMMENT = COMMENT;
    }

    /**
     * @return the M2
     */
    public JSONObject getM2() {
        return M2;
    }

    /**
     * @param M2 the M2 to set
     */
    public void setM2(JSONObject M2) {
        this.M2 = M2;
    }

    /**
     * @return the S2
     */
    public JSONObject getS2() {
        return S2;
    }

    /**
     * @param S2 the S2 to set
     */
    public void setS2(JSONObject S2) {
        this.S2 = S2;
    }

    /**
     * @return the N2
     */
    public JSONObject getN2() {
        return N2;
    }

    /**
     * @param N2 the N2 to set
     */
    public void setN2(JSONObject N2) {
        this.N2 = N2;
    }

    /**
     * @return the K2
     */
    public JSONObject getK2() {
        return K2;
    }

    /**
     * @param K2 the K2 to set
     */
    public void setK2(JSONObject K2) {
        this.K2 = K2;
    }

    /**
     * @return the K1
     */
    public JSONObject getK1() {
        return K1;
    }

    /**
     * @param K1 the K1 to set
     */
    public void setK1(JSONObject K1) {
        this.K1 = K1;
    }

    /**
     * @return the O1
     */
    public JSONObject getO1() {
        return O1;
    }

    /**
     * @param O1 the O1 to set
     */
    public void setO1(JSONObject O1) {
        this.O1 = O1;
    }

    /**
     * @return the P1
     */
    public JSONObject getP1() {
        return P1;
    }

    /**
     * @param P1 the P1 to set
     */
    public void setP1(JSONObject P1) {
        this.P1 = P1;
    }

    /**
     * @return the Q1
     */
    public JSONObject getQ1() {
        return Q1;
    }

    /**
     * @param Q1 the Q1 to set
     */
    public void setQ1(JSONObject Q1) {
        this.Q1 = Q1;
    }

    /**
     * @return the MF
     */
    public JSONObject getMF() {
        return MF;
    }

    /**
     * @param MF the MF to set
     */
    public void setMF(JSONObject MF) {
        this.MF = MF;
    }

    /**
     * @return the MM
     */
    public JSONObject getMM() {
        return MM;
    }

    /**
     * @param MM the MM to set
     */
    public void setMM(JSONObject MM) {
        this.MM = MM;
    }

    /**
     * @return the SSA
     */
    public JSONObject getSSA() {
        return SSA;
    }

    /**
     * @param SSA the SSA to set
     */
    public void setSSA(JSONObject SSA) {
        this.SSA = SSA;
    }
    private JsonArray COMMENT;
    private JSONObject M2;
    private JSONObject S2;
    private JSONObject N2;
    private JSONObject K2;
    private JSONObject K1;
    private JSONObject O1;
    private JSONObject P1;
    private JSONObject Q1;
    private JSONObject MF;
    private JSONObject MM;
    private JSONObject SSA;

    public OTL_Attr() {
    }

    public OTL_Attr(JsonArray COMMENT,
            JSONObject M2,
            JSONObject S2,
            JSONObject N2,
            JSONObject K2,
            JSONObject K1,
            JSONObject O1,
            JSONObject P1,
            JSONObject Q1,
            JSONObject MF,
            JSONObject MM,
            JSONObject SSA) {
        this.COMMENT = COMMENT;
        this.M2 = M2;
        this.S2 = S2;
        this.N2 = N2;
        this.K2 = K2;
        this.K1 = K1;
        this.O1 = O1;
        this.P1 = P1;
        this.Q1 = Q1;
        this.MF = MF;
        this.MM = MM;
        this.SSA = SSA;
    }

}
