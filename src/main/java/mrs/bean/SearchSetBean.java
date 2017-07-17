/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.bean;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author prathibha_s
 */
public class SearchSetBean {

    private String idno;
    private String sid;
    private String user;
    private String timeStamp;
    private String province;
    private String district;
    private String localAuthority;
    private String lat;
    private String lng;
    private String revenueS;
    private String gnds;
    private Map<String, String> qList = new HashMap<String, String>();

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocalAuthority() {
        return localAuthority;
    }

    public void setLocalAuthority(String localAuthority) {
        this.localAuthority = localAuthority;
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

    public String getRevenueS() {
        return revenueS;
    }

    public void setRevenueS(String revenueS) {
        this.revenueS = revenueS;
    }

    public String getGnds() {
        return gnds;
    }

    public void setGnds(String gnds) {
        this.gnds = gnds;
    }

    public Map<String, String> getqList() {
        return qList;
    }

    public void setqList(Map<String, String> qList) {
        this.qList = qList;
    }

}
