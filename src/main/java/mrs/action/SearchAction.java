/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import mrs.bean.ActionBean;
import mrs.bean.CsvBean;
import mrs.bean.LatLngBean;
import mrs.bean.QandAbean;
import mrs.bean.SearchDataBean;
import mrs.bean.SearchInputBean;
import mrs.bean.SummaryBean;
import mrs.common.dao.CommonDAO;
import mrs.dao.SearchDAO;
import mrs.mapping.Question;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha_s
 */
public class SearchAction extends ActionSupport implements ModelDriven<Object> {

    SearchInputBean inputBean = new SearchInputBean();
    private InputStream fileInputStream;

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called SearchAction : execute");
        return SUCCESS;
    }

    public String Check() throws Exception {
        System.out.println("called SearchAction : Check");

        String msg = "loginsuccess";

        try {

            HttpSession sessionPrevious = ServletActionContext.getRequest().getSession(false);
            if (sessionPrevious != null) {
                sessionPrevious.invalidate();
            }
            //set user to the session
            HttpSession session = ServletActionContext.getRequest().getSession(true);
            session.setAttribute("SYSTEMUSER", inputBean.getLoginUserName());
            session.setAttribute("SYSTEMUSERPASS", inputBean.getLoginPassword());

            SearchDAO daos = new SearchDAO();

            String m = daos.checkUser(inputBean);

            if (m.equals("")) {

                CommonDAO dao = new CommonDAO();

                daos.getRSList(inputBean);
                daos.getLAList(inputBean);
                daos.getGNDList(inputBean);
                daos.getQuestionList(inputBean);
                daos.getUserList(inputBean);

                msg = "loginsuccess";
            } else if (m.equals("new")) {
                inputBean.setUsername(inputBean.getLoginUserName());
                msg = "firstattempt";
            } else {
                addActionError("Username or Password incorrect");
                msg = "loginmessage";
            }

        } catch (Exception e) {
            System.out.println("check " + e);
            Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Error Occured");
            msg = "loginmessage";
        }

        return msg;

    }

    public String ChangePassword() {
        System.out.println("called SearchAction : ChangePassword");
        String msg = "passwordpage";
        String me = "";
        try {
            HttpSession session = ServletActionContext.getRequest().getSession(true);
            String usr = (String) session.getAttribute("SYSTEMUSER");
            inputBean.setUsername(usr);

            String m = this.Validate();

            if (m.isEmpty()) {

                SearchDAO dao = new SearchDAO();

                m = dao.passwordChange(inputBean);

                if (m.isEmpty()) {
                    addActionMessage("Password Change Successful");
                    session.invalidate();
                    msg = "logout";
                } else {
                    addActionError(m);
                }

            } else {
                inputBean.setOldPassword(inputBean.getOldPassword());

                addActionError(m);
            }

        } catch (Exception e) {
            System.out.println("Change Password " + e);
            Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Wrong old password");
            msg = "passwordpage";
        }

        return msg;
    }

    public String ViewCP() {
        System.out.println("called SearchAction : ViewCP");

        HttpSession session = ServletActionContext.getRequest().getSession(true);
        String usr = (String) session.getAttribute("SYSTEMUSER");

        inputBean.setUsername(usr);

        return "passwordpage";

    }

    public String Validate() {
        String msg = "";

        if (inputBean.getOldPassword() == null || inputBean.getOldPassword().trim().isEmpty()) {
            msg = "Old password cannot be empty";
        } else if (inputBean.getNewPassword() == null || inputBean.getNewPassword().trim().isEmpty()) {
            msg = "New password cannot be empty";
        } else if (inputBean.getConfirmPassword() == null || inputBean.getConfirmPassword().trim().isEmpty()) {
            msg = "Confirm password cannot be empty";
        } else if (!inputBean.getNewPassword().equals(inputBean.getConfirmPassword())) {
            msg = "Password mismatched";
        }

        return msg;
    }

    public String Logout() {
        String result = "";
        System.out.println("called SearchAction : logout");

        try {

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            session.invalidate();
            result = "logout";
        } catch (Exception e) {
            System.out.println("Logout " + e);
            Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, e);
            result = "loginsuccess";
        }

        return result;
    }

    public String Logouts() {
        String result = "";
        System.out.println("called SearchAction : logout");

        try {

            result = "logout";
        } catch (Exception e) {
            System.out.println("Logouts " + e);
            Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, e);
            result = "loginsuccess";
        }

        return result;
    }

    public String SendWelcome() {
        String result = "";
        System.out.println("called SearchAction : SendWelcome");

        try {
            SearchDAO daos = new SearchDAO();
            daos.getRSList(inputBean);
            daos.getLAList(inputBean);
            daos.getGNDList(inputBean);
            daos.getQuestionList(inputBean);
            daos.getUserList(inputBean);

            HttpSession session = ServletActionContext.getRequest().getSession(true);
            String pass = (String) session.getAttribute("SYSTEMUSERPASS");
            String username = (String) session.getAttribute("SYSTEMUSER");

            inputBean.setOldPassword(pass);
            inputBean.setUsername(username);

            daos.userStatusChange(inputBean);

            result = "loginsuccess";
        } catch (Exception e) {
            System.out.println("Send Welcome " + e);
            Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, e);
            result = "loginsuccess";
        }

        return result;
    }

    public String ViewPopup() {
        System.out.println("called SearchAction : ViewPopup");
            System.out.println("sid " +inputBean.getSid() );
        try {
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            List<SearchDataBean> datalist = (List<SearchDataBean>) session.getAttribute("SEARCH_BEAN");

            SearchDAO dao = new SearchDAO();
            List<ActionBean> Actdatalist = dao.getActionList(inputBean.getSid());
            
            List<QandAbean> q_and_aList = new ArrayList<QandAbean>();
            
            inputBean.setActionBean(Actdatalist.get(0));
            
            for (ActionBean actionBean : Actdatalist) {
                QandAbean qNa = new QandAbean();
                qNa.setQues(actionBean.getQuestion());
                qNa.setAnsw(actionBean.getAnswer());
                
                q_and_aList.add(qNa);
            }
            
            inputBean.setQuestionAnswer(q_and_aList);

        } catch (Exception e) {
            System.out.println("View popup " + e);
            Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("User session not found");
            return "logout";
        }
        return "viewpopup";
    }

    public String List() {
        System.out.println("called SearchAction : list");

        try {

            HttpSession session = ServletActionContext.getRequest().getSession(false);

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }

            SearchDAO dao = new SearchDAO();
            List<SearchDataBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            List<SearchDataBean> dataListCSV = dao.getSearchListForCSV(inputBean);
            List<LatLngBean> dataListMap = dao.getLatLngForMap(inputBean);
//            List<SummaryBean> summaryList = dao.getDataForSummary();

            session.setAttribute("SEARCH_BEAN", dataList);
            session.setAttribute("SEARCH_BEAN_CSV", dataListCSV);
            session.setAttribute("LAT_LNG_BEAN", dataListMap);

            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                inputBean.setRecords(records);
                inputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);

            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }

        } catch (Exception e) {
            System.out.println("List " + e);
            Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("User session not found");
            return "logout";
        }
        return "list";

    }

    public String ListSummary() {
        System.out.println("called SearchAction : ListSummary");

        try {

            HttpSession session = ServletActionContext.getRequest().getSession(false);

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }

            SearchDAO dao = new SearchDAO();

//            List<SummaryBean> summaryList = dao.getDataForSummary();
            List<SummaryBean> summaryList = dao.getDataForSummary_edited();
            session.setAttribute("SUMMARY_LIST", summaryList);

            if (!summaryList.isEmpty()) {
                records = summaryList.get(0).getFullCount();
                inputBean.setRecords(records);
                inputBean.setGridModel2(summaryList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);

            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }

        } catch (Exception e) {
            System.out.println("List " + e);
            Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("User session not found");
            return "logout";
        }
        return "list";

    }

    public String Summary() {
        return "list";
    }

    public String LoadLatLng() {
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        List<LatLngBean> datalist = (List<LatLngBean>) session.getAttribute("LAT_LNG_BEAN");
        System.out.println("lating  " + datalist.size());
        inputBean.setLatLngList(datalist);
        return "list";
    }

    public String DownloadCsv() {
        String msg = "csv";
        ByteArrayOutputStream outputStream = null;
        String column_names = "";
        String q_list = "";
        try {

//            HttpSession session = ServletActionContext.getRequest().getSession(false);
            outputStream = new ByteArrayOutputStream();

//            List<SearchDataBean> datalist = (List<SearchDataBean>) session.getAttribute("SEARCH_BEAN_CSV");
            SearchDAO dao = new SearchDAO();
            CommonDAO cdao = new CommonDAO();

            List<CsvBean> datalist = dao.getCSVDATAall();

            List<Question> qlist = cdao.getQuestionList();
            Map<String, String> qlistmap = cdao.getQuestionListMap();

//            System.out.println("qlist new " + dao.getQlistForCSV(qlist));
            q_list = dao.getQlistForCSV(qlist);
//            for (Question question : qlist) {
//                String as = "," + question.getRevenueSource().getCode() + "_" + question.getQuestion();
//                q_list += as;
//            }

            column_names = "No,Survey ID,Enumerator,Time Stamp,Province,District,Local Authority,GND,Latitude,Longitude";

            column_names += q_list;

            System.out.println("list " + column_names);

            ArrayList<String> rows = new ArrayList<String>();

            rows.add(column_names);
            rows.add("\n");

            for (int i = 0; i < datalist.size(); i++) {

                String a_list = "";
                String a_list2 = "";

                for (String key : qlistmap.keySet()) {
                    String record = "," + datalist.get(i).getqList().get(key);
                    a_list2 += record;
                }

                for (Question question : qlist) {
                    String as = "," + datalist.get(i).getqList().get(question.getQCode());
                    a_list += as;
                }
//                
//                System.out.println("a_list "+ a_list);
//                System.out.println("a_list2 " + a_list2);

                rows.add(
                        datalist.get(i).getIdno() + ","
                        + datalist.get(i).getSid() + ","
                        + datalist.get(i).getUser() + ","
                        + datalist.get(i).getTimeStamp() + ","
                        + datalist.get(i).getProvince() + ","
                        + datalist.get(i).getDistrict() + ","
                        + cdao.getLAfromCode(datalist.get(i).getLocalAuthority()) + ","
                        + datalist.get(i).getGnd() + ","
                        + datalist.get(i).getLat() + ","
                        + datalist.get(i).getLng()
                        + a_list2
                );
                rows.add("\n");
            }

            Iterator<String> iter = rows.iterator();
            while (iter.hasNext()) {
                String outputString = (String) iter.next();
                byte[] contentInBytes = outputString.getBytes();
                outputStream.write(contentInBytes);

            }

            inputBean.setCsvStream(new ByteArrayInputStream(outputStream.toByteArray()));

        } catch (Exception e) {
            System.out.println("CSV " + e);
            Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, e);
            msg = "view";

        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }

            } catch (IOException ex) {
                //do nothing
            }
        }

        return msg;
    }

    public String FindQuestion() throws Exception {

        System.out.println("called SearchAction: FindQuestion");
        try {

            SearchDAO dao = new SearchDAO();
            dao.getQuestionforRS(inputBean);

        } catch (Exception e) {
            System.out.println("Find Question " + e);
            Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";

    }

    public String FindGND() throws Exception {

        System.out.println("called SearchAction: FindGND");
        try {

            SearchDAO dao = new SearchDAO();
            dao.getGNDforLA(inputBean);

        } catch (Exception e) {
            System.out.println("Find GND " + e);
            Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";

    }

    public String FindRevenueSource() throws Exception {
        System.out.println("called SearchAction: FindRevenueSource");

        try {
            SearchDAO dao = new SearchDAO();

            if (inputBean.getQuestion().equals("empty")) {
                dao.getQuestionList(inputBean);
            } else {
                dao.getRevenueSource(inputBean);
            }
        } catch (Exception e) {
            System.out.println("Find RevenueSource " + e);
            Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return "list";
    }

    public String FindLA() throws Exception {
        System.out.println("called SearchAction: FindLA");

        try {
            SearchDAO dao = new SearchDAO();

            if (inputBean.getGnd().equals("empty")) {
                dao.getGNDList(inputBean);
            } else {
                dao.getLA(inputBean);
            }
        } catch (Exception e) {
            System.out.println("Find LA " + e);
            Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return "list";
    }

    public String Reset() throws Exception {
        System.out.println("called SearchAction: Reset");

        SearchDAO dao = new SearchDAO();

        dao.getRSList(inputBean);
        dao.getLAList(inputBean);
        dao.getGNDList(inputBean);
        dao.getQuestionList(inputBean);
        dao.getUserList(inputBean);

        return "list";
    }

}
