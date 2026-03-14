/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ying.Jin
 */
public class CORS_Attr {

    /**
     * @return the cors
     */
    public String getCors() {
        return cors;
    }

    /**
     * @param cors the cors to set
     */
    public void setCors(String cors) {
        this.cors = cors;
    }

    /**
     * @return the meta_data
     */
    public Station_Attr getMeta_data() {
        return meta_data;
    }

    /**
     * @param meta_data the meta_data to set
     */
    public void setMeta_data(Station_Attr meta_data) {
        this.meta_data = meta_data;
    }

    /**
     * @return the position_date
     */
    public Occupation_Attr getPosition_date() {
        return position_date;
    }

    /**
     * @param position_date the position_date to set
     */
    public void setPosition_date(Occupation_Attr position_date) {
        this.position_date = position_date;
    }
    private String cors;
    private Station_Attr meta_data;
    private Occupation_Attr position_date;
}
