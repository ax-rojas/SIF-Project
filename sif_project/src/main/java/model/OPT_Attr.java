/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ying.Jin
 */
public class OPT_Attr {

    /**
     * @return the COMMENT
     */
    public String getCOMMENT() {
        return COMMENT;
    }

    /**
     * @param COMMENT the COMMENT to set
     */
    public void setCOMMENT(String COMMENT) {
        this.COMMENT = COMMENT;
    }

    /**
     * @return the NORTH_REAL
     */
    public double getNORTH_REAL() {
        return NORTH_REAL;
    }

    /**
     * @param NORTH_REAL the NORTH_REAL to set
     */
    public void setNORTH_REAL(double NORTH_REAL) {
        this.NORTH_REAL = NORTH_REAL;
    }

    /**
     * @return the NORTH_IMAGINARY
     */
    public double getNORTH_IMAGINARY() {
        return NORTH_IMAGINARY;
    }

    /**
     * @param NORTH_IMAGINARY the NORTH_IMAGINARY to set
     */
    public void setNORTH_IMAGINARY(double NORTH_IMAGINARY) {
        this.NORTH_IMAGINARY = NORTH_IMAGINARY;
    }

    /**
     * @return the EAST_REAL
     */
    public double getEAST_REAL() {
        return EAST_REAL;
    }

    /**
     * @param EAST_REAL the EAST_REAL to set
     */
    public void setEAST_REAL(double EAST_REAL) {
        this.EAST_REAL = EAST_REAL;
    }

    /**
     * @return the EAST_IMAGINARY
     */
    public double getEAST_IMAGINARY() {
        return EAST_IMAGINARY;
    }

    /**
     * @param EAST_IMAGINARY the EAST_IMAGINARY to set
     */
    public void setEAST_IMAGINARY(double EAST_IMAGINARY) {
        this.EAST_IMAGINARY = EAST_IMAGINARY;
    }

    /**
     * @return the UP_REAL
     */
    public double getUP_REAL() {
        return UP_REAL;
    }

    /**
     * @param UP_REAL the UP_REAL to set
     */
    public void setUP_REAL(double UP_REAL) {
        this.UP_REAL = UP_REAL;
    }

    /**
     * @return the UP_IMAGINARY
     */
    public double getUP_IMAGINARY() {
        return UP_IMAGINARY;
    }

    /**
     * @param UP_IMAGINARY the UP_IMAGINARY to set
     */
    public void setUP_IMAGINARY(double UP_IMAGINARY) {
        this.UP_IMAGINARY = UP_IMAGINARY;
    }
    private String COMMENT;
    private double NORTH_REAL;
    private double NORTH_IMAGINARY;
    private double EAST_REAL;
    private double EAST_IMAGINARY;
    private double UP_REAL;
    private double UP_IMAGINARY;

    public OPT_Attr() {
    }

    public OPT_Attr(String COMMENT,
            double NORTH_REAL,
            double NORTH_IMAGINARY,
            double EAST_REAL,
            double EAST_IMAGINARY,
            double UP_REAL,
            double UP_IMAGINARY) {
        this.COMMENT = COMMENT;
        this.NORTH_REAL = NORTH_REAL;
        this.NORTH_IMAGINARY = NORTH_IMAGINARY;
        this.EAST_REAL = EAST_REAL;
        this.EAST_IMAGINARY = EAST_IMAGINARY;
        this.UP_REAL = UP_REAL;
        this.UP_IMAGINARY = UP_IMAGINARY;
    }
}
