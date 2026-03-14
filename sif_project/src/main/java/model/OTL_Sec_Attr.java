/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ying.Jin
 */
public class OTL_Sec_Attr {

    /**
     * @return the UP_AMPLITUDE
     */
    public double getUP_AMPLITUDE() {
        return UP_AMPLITUDE;
    }

    /**
     * @param UP_AMPLITUDE the UP_AMPLITUDE to set
     */
    public void setUP_AMPLITUDE(double UP_AMPLITUDE) {
        this.UP_AMPLITUDE = UP_AMPLITUDE;
    }

    /**
     * @return the EAST_AMPLITUDE
     */
    public double getEAST_AMPLITUDE() {
        return EAST_AMPLITUDE;
    }

    /**
     * @param EAST_AMPLITUDE the EAST_AMPLITUDE to set
     */
    public void setEAST_AMPLITUDE(double EAST_AMPLITUDE) {
        this.EAST_AMPLITUDE = EAST_AMPLITUDE;
    }

    /**
     * @return the NORTH_AMPLITUDE
     */
    public double getNORTH_AMPLITUDE() {
        return NORTH_AMPLITUDE;
    }

    /**
     * @param NORTH_AMPLITUDE the NORTH_AMPLITUDE to set
     */
    public void setNORTH_AMPLITUDE(double NORTH_AMPLITUDE) {
        this.NORTH_AMPLITUDE = NORTH_AMPLITUDE;
    }

    /**
     * @return the UP_PHASE
     */
    public double getUP_PHASE() {
        return UP_PHASE;
    }

    /**
     * @param UP_PHASE the UP_PHASE to set
     */
    public void setUP_PHASE(double UP_PHASE) {
        this.UP_PHASE = UP_PHASE;
    }

    /**
     * @return the EAST_PHASE
     */
    public double getEAST_PHASE() {
        return EAST_PHASE;
    }

    /**
     * @param EAST_PHASE the EAST_PHASE to set
     */
    public void setEAST_PHASE(double EAST_PHASE) {
        this.EAST_PHASE = EAST_PHASE;
    }

    /**
     * @return the NORTH_PHASE
     */
    public double getNORTH_PHASE() {
        return NORTH_PHASE;
    }

    /**
     * @param NORTH_PHASE the NORTH_PHASE to set
     */
    public void setNORTH_PHASE(double NORTH_PHASE) {
        this.NORTH_PHASE = NORTH_PHASE;
    }

    private double UP_AMPLITUDE;
    private double EAST_AMPLITUDE;
    private double NORTH_AMPLITUDE;
    private double UP_PHASE;
    private double EAST_PHASE;
    private double NORTH_PHASE;

    public OTL_Sec_Attr() {
    }

    public OTL_Sec_Attr(
            double UP_AMPLITUDE,
            double EAST_AMPLITUDE,
            double NORTH_AMPLITUDE,
            double UP_PHASE,
            double EAST_PHASE,
            double NORTH_PHASE
    ) {
        this.UP_AMPLITUDE = UP_AMPLITUDE;
        this.EAST_AMPLITUDE = EAST_AMPLITUDE;
        this.NORTH_AMPLITUDE = NORTH_AMPLITUDE;
        this.UP_PHASE = UP_PHASE;
        this.EAST_PHASE = EAST_PHASE;
        this.NORTH_PHASE = NORTH_PHASE;
    }
}
