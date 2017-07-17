package mrs.mapping;
// Generated Jun 15, 2017 9:53:30 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * RevenueSource generated by hbm2java
 */
@Entity
@Table(name="revenue_source"
   
)
public class RevenueSource  implements java.io.Serializable {


     private String code;
     private String description;
     private Date createdTime;
     private Set<Question> questions = new HashSet(0);
     private Set<SurveyData> surveyDatas = new HashSet(0);

    public RevenueSource() {
    }

	
    public RevenueSource(String code, String description, Date createdTime) {
        this.code = code;
        this.description = description;
        this.createdTime = createdTime;
    }
    public RevenueSource(String code, String description, Date createdTime, Set<Question> questions, Set<SurveyData> surveyDatas) {
       this.code = code;
       this.description = description;
       this.createdTime = createdTime;
       this.questions = questions;
       this.surveyDatas = surveyDatas;
    }
   
     @Id 

    
    @Column(name="CODE", unique=true, nullable=false, length=10)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    
    @Column(name="DESCRIPTION", nullable=false, length=150)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TIME", nullable=false, length=19)
    public Date getCreatedTime() {
        return this.createdTime;
    }
    
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="revenueSource")
    public Set<Question> getQuestions() {
        return this.questions;
    }
    
    public void setQuestions(Set questions) {
        this.questions = questions;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="revenueSource")
    public Set<SurveyData> getSurveyDatas() {
        return this.surveyDatas;
    }
    
    public void setSurveyDatas(Set surveyDatas) {
        this.surveyDatas = surveyDatas;
    }




}

