/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.bean;

/**
 *
 * @author prathibha_s
 */
public class LatLngBean {

    private String lat;
    private String lng;
    private String zindex;
    private String placenamegnd;
    private String placenamela;

    public String getPlacenamegnd() {
        return placenamegnd;
    }

    public void setPlacenamegnd(String placenamegnd) {
        this.placenamegnd = placenamegnd;
    }

    public String getPlacenamela() {
        return placenamela;
    }

    public void setPlacenamela(String placenamela) {
        this.placenamela = placenamela;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getZindex() {
        return zindex;
    }

    public void setZindex(String zindex) {
        this.zindex = zindex;
    }

    
}
