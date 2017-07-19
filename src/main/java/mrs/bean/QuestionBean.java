/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.bean;

import java.util.Date;
import mrs.mapping.RevenueSource;

/**
 *
 * @author prathibha
 */
public class QuestionBean {

    private String QCode;
    private RevenueSource revenueSource;
    private String question;
    private Date createdTime;

    public String getQCode() {
        return QCode;
    }

    public void setQCode(String QCode) {
        this.QCode = QCode;
    }

    public RevenueSource getRevenueSource() {
        return revenueSource;
    }

    public void setRevenueSource(RevenueSource revenueSource) {
        this.revenueSource = revenueSource;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

}
