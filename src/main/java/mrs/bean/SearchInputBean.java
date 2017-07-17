/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.bean;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mrs.mapping.Gnd;
import mrs.mapping.LocalAuthority;
import mrs.mapping.Question;
import mrs.mapping.RevenueSource;
import mrs.mapping.User;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author prathibha_s
 */
public class SearchInputBean {

    //------login details--------//
    private String loginUserName;
    private String LoginPassword;
    private String userType;

    //------Change password------//
    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    //------Bean details--------//
    private String user;
    private String question;
    private String revenueSource;
    private String localAuthority;
    private String gnd;
    private String sid;
    private byte[] img;
    private String imgString;
    private List<User> userList = new ArrayList<User>();
    private List<Question> questionList = new ArrayList<Question>();
    private List<RevenueSource> revenueSourceList = new ArrayList<RevenueSource>();
    private List<LocalAuthority> localAuthorityList = new ArrayList<LocalAuthority>();
    private List<LatLngBean> latLngList = new ArrayList<LatLngBean>();
    private List<Gnd> gndList = new ArrayList<Gnd>();

    private ActionBean actionBean;

    private List<QandAbean> questionAnswer = new ArrayList<QandAbean>();
    private Map<String, String> qandaList = new HashMap<String, String>();

    //-------gq grid details--------//
    private List<SearchDataBean> gridModel;
    private List<SummaryBean> gridModel2;

    public List<SummaryBean> getGridModel2() {
        return gridModel2;
    }

    public void setGridModel2(List<SummaryBean> gridModel2) {
        this.gridModel2 = gridModel2;
    }
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;
    private boolean loadonce = false;

    private ByteArrayInputStream csvStream;

    public ByteArrayInputStream getCsvStream() {
        return csvStream;
    }

    public Map<String, String> getQandaList() {
        return qandaList;
    }

    public void setQandaList(Map<String, String> qandaList) {
        this.qandaList = qandaList;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgString() {
        try {
            byte[] blobAsBytes = getImg();
            blobAsBytes = Base64.encodeBase64(blobAsBytes);
            this.imgString = new String(blobAsBytes);
        } catch (Exception e) {
            this.imgString = "";
        }
        return imgString;
    }

    public void setImgString(String imgString) {
        this.imgString = imgString;
    }

    public List<LatLngBean> getLatLngList() {
        return latLngList;
    }

    public List<QandAbean> getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(List<QandAbean> questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setLatLngList(List<LatLngBean> latLngList) {
        this.latLngList = latLngList;
    }

    public void setCsvStream(ByteArrayInputStream csvStream) {
        this.csvStream = csvStream;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public ActionBean getActionBean() {
        return actionBean;
    }

    public void setActionBean(ActionBean actionBean) {
        this.actionBean = actionBean;
    }

    public String getLoginPassword() {
        return LoginPassword;
    }

    public void setLoginPassword(String LoginPassword) {
        this.LoginPassword = LoginPassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRevenueSource() {
        return revenueSource;
    }

    public void setRevenueSource(String revenueSource) {
        this.revenueSource = revenueSource;
    }

    public String getLocalAuthority() {
        return localAuthority;
    }

    public void setLocalAuthority(String localAuthority) {
        this.localAuthority = localAuthority;
    }

    public String getGnd() {
        return gnd;
    }

    public void setGnd(String gnd) {
        this.gnd = gnd;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<RevenueSource> getRevenueSourceList() {
        return revenueSourceList;
    }

    public void setRevenueSourceList(List<RevenueSource> revenueSourceList) {
        this.revenueSourceList = revenueSourceList;
    }

    public List<LocalAuthority> getLocalAuthorityList() {
        return localAuthorityList;
    }

    public void setLocalAuthorityList(List<LocalAuthority> localAuthorityList) {
        this.localAuthorityList = localAuthorityList;
    }

    public List<Gnd> getGndList() {
        return gndList;
    }

    public void setGndList(List<Gnd> gndList) {
        this.gndList = gndList;
    }

    public List<SearchDataBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<SearchDataBean> gridModel) {
        this.gridModel = gridModel;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSearchField() {
        return searchField;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchOper() {
        return searchOper;
    }

    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }

    public boolean isLoadonce() {
        return loadonce;
    }

    public void setLoadonce(boolean loadonce) {
        this.loadonce = loadonce;
    }

}
