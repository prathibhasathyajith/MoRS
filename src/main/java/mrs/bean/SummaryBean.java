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
public class SummaryBean {

    private String id;
    private String lacode;
    private String coes;
    private String ladb;
    private String palo;
    private String redf;
    private String reis;
    private String remc;
    private String retm;
    private String sadd;
    private String total;

    private long fullCount;

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLacode() {
        return lacode;
    }

    public void setLacode(String lacode) {
        this.lacode = lacode;
    }

    public String getCoes() {
        return coes;
    }

    public void setCoes(String coes) {
        this.coes = coes;
    }

    public String getLadb() {
        return ladb;
    }

    public void setLadb(String ladb) {
        this.ladb = ladb;
    }

    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    public String getRedf() {
        return redf;
    }

    public void setRedf(String redf) {
        this.redf = redf;
    }

    public String getReis() {
        return reis;
    }

    public void setReis(String reis) {
        this.reis = reis;
    }

    public String getRemc() {
        return remc;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setRemc(String remc) {
        this.remc = remc;
    }

    public String getRetm() {
        return retm;
    }

    public void setRetm(String retm) {
        this.retm = retm;
    }

    public String getSadd() {
        return sadd;
    }

    public void setSadd(String sadd) {
        this.sadd = sadd;
    }

}
