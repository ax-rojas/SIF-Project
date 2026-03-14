/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author Ying.Jin
 */
public class SIF_Attr_PG {
    public SIF_Attr_PG() {
    }
    public SIF_Attr_PG(Sec1_Attr sec1,Sec2_Attr sec2,List<Sec3_Attr>sec3,List<Sec4_Attr> sec4) {
        this.sec1 = sec1;
        this.sec2 = sec2;
        this.sec3 = sec3;
        this.sec4 = sec4;
    }
    
    public SIF_Attr_PG(Sec1_Attr sec1,Sec2_Attr sec2,List<Sec3_Attr>sec3,List<Sec4_Attr> sec4,OPT_Attr opt,OTL_Attr otl) {
        this.sec1 = sec1;
        this.sec2 = sec2;
        this.sec3 = sec3;
        this.sec4 = sec4;
        this.opt = opt;
        this.otl = otl;
    }

    /**
     * @return the sec1
     */
    public Sec1_Attr getSec1() {
        return sec1;
    }

    /**
     * @param sec1 the sec1 to set
     */
    public void setSec1(Sec1_Attr sec1) {
        this.sec1 = sec1;
    }

    /**
     * @return the sec2
     */
    public Sec2_Attr getSec2() {
        return sec2;
    }

    /**
     * @param sec2 the sec2 to set
     */
    public void setSec2(Sec2_Attr sec2) {
        this.sec2 = sec2;
    }

    /**
     * @return the sec3
     */
    public List<Sec3_Attr> getSec3() {
        return sec3;
    }

    /**
     * @param sec3 the sec3 to set
     */
    public void setSec3(List<Sec3_Attr> sec3) {
        this.sec3 = sec3;
    }

    /**
     * @return the sec4
     */
    public List<Sec4_Attr> getSec4() {
        return sec4;
    }

    /**
     * @param sec4 the sec4 to set
     */
    public void setSec4(List<Sec4_Attr> sec4) {
        this.sec4 = sec4;
    }

    /**
     * @return the opt
     */
    public OPT_Attr getOpt() {
        return opt;
    }

    /**
     * @param opt the opt to set
     */
    public void setOpt(OPT_Attr opt) {
        this.opt = opt;
    }

    /**
     * @return the otl
     */
    public OTL_Attr getOtl() {
        return otl;
    }

    /**
     * @param otl the otl to set
     */
    public void setOtl(OTL_Attr otl) {
        this.otl = otl;
    }

    private Sec1_Attr sec1;
    private Sec2_Attr sec2;
    private List<Sec3_Attr> sec3;
    private List<Sec4_Attr> sec4;
    private OPT_Attr opt;
    private OTL_Attr otl;
}
