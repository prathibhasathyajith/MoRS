package mrs.mapping;
// Generated Jun 15, 2017 9:53:30 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Question generated by hbm2java
 */
@Entity
@Table(name="question"
    
)
public class Question  implements java.io.Serializable {


     private String QCode;
     private RevenueSource revenueSource;
     private String question;
     private Date createdTime;
     private Set<UserAnswer> userAnswers = new HashSet(0);

    public Question() {
    }

	
    public Question(String QCode, RevenueSource revenueSource, String question, Date createdTime) {
        this.QCode = QCode;
        this.revenueSource = revenueSource;
        this.question = question;
        this.createdTime = createdTime;
    }
    public Question(String QCode, RevenueSource revenueSource, String question, Date createdTime, Set<UserAnswer> userAnswers) {
       this.QCode = QCode;
       this.revenueSource = revenueSource;
       this.question = question;
       this.createdTime = createdTime;
       this.userAnswers = userAnswers;
    }
   
     @Id 

    
    @Column(name="Q_CODE", unique=true, nullable=false, length=10)
    public String getQCode() {
        return this.QCode;
    }
    
    public void setQCode(String QCode) {
        this.QCode = QCode;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVENUE_SRC_CODE", nullable=false)
    public RevenueSource getRevenueSource() {
        return this.revenueSource;
    }
    
    public void setRevenueSource(RevenueSource revenueSource) {
        this.revenueSource = revenueSource;
    }

    
    @Column(name="QUESTION", nullable=false, length=100)
    public String getQuestion() {
        return this.question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TIME", nullable=false, length=19)
    public Date getCreatedTime() {
        return this.createdTime;
    }
    
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="question")
    public Set<UserAnswer> getUserAnswers() {
        return this.userAnswers;
    }
    
    public void setUserAnswers(Set userAnswers) {
        this.userAnswers = userAnswers;
    }




}


