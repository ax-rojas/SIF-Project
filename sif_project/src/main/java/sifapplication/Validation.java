/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sifapplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Ying.Jin
 */
public class Validation {
    
    public static JSONArray parseArr(JSONParser parser, String tmpS1) throws ParseException {
        Object tmp;
        JSONArray tmpA1 = new JSONArray();
        if (tmpS1 != null) {
            tmp = parser.parse(tmpS1);
            if (tmp != null) {
                tmpA1 = (JSONArray) tmp;
            } else {
                tmpA1 = null;
            }
        }
        return tmpA1;
    }

    public static JSONObject parseObj(JSONParser parser, String tmpS1) throws ParseException {
        Object tmp;
        JSONObject tmp1 = new JSONObject();
        if (tmpS1 != null) {
            tmp = parser.parse(tmpS1);
            if (tmp != null) {
                tmp1 = (JSONObject) tmp;
            } else {
                tmp1 = null;
            }
        }
        return tmp1;
    }

    public static boolean isValidJson(String json) {
        try {
            if (json == null) {
                System.out.println("Invalid JSON: null ");
                return false;
            }
            JSONParser parser = new JSONParser();
            parser.parse(json);
        } catch (org.json.simple.parser.ParseException e) {
            System.out.println("Invalid JSON: " + json);
            return false;
        }
        return true;
    }
}
