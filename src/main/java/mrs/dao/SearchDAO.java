/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.dao;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import mrs.bean.ActionBean;
import mrs.bean.CsvBean;
import mrs.bean.LatLngBean;
import mrs.bean.SearchDataBean;
import mrs.bean.SearchInputBean;
import mrs.bean.SummaryBean;
import mrs.common.dao.CommonDAO;
import mrs.listener.HibernateInit;
import mrs.mapping.Gnd;
import mrs.mapping.LocalAuthority;
import mrs.mapping.Question;
import mrs.mapping.RevenueSource;
import mrs.mapping.SurveyData;
import mrs.mapping.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author prathibha_s
 */
public class SearchDAO {

    public String COES = "COES";
    public String LADB = "LADB";
    public String PALO = "PALO";
    public String REDF = "REDF";
    public String REIS = "REIS";
    public String REMC = "REMC";
    public String RETM = "RETM";
    public String SADD = "SADD";

    String COUNTS = "select count(sd.ID) "
            + "from survey_data sd "
            + "left outer join user u on u.KEYID = sd.USER_ID "
            + "left outer join revenue_source rs on rs.CODE = sd.REVENUE_SRC_CODE "
            + "left outer join local_authority la on la.CODE = sd.LA_CODE "
            + "left outer join gnd g on g.CODE = sd.GND_CODE "
            + "left outer join user_answer ua on ua.SURVEY_ID = sd.ID "
            + "left outer join question q on q.Q_CODE = ua.Q_CODE "
            + "left outer join answer a on a.A_CODE = ua.A_CODE "
            + "left outer join district ds on ds.CODE = la.DISTRICT_CODE "
            + "left outer join province pv on pv.CODE = ds.PROVINCE_CODE ";

    String SEARCH_COUNTS = "select count(DISTINCT sd.ID)"
            + "from survey_data sd  "
            + "left outer join user u on u.KEYID = sd.USER_ID "
            + "left outer join revenue_source rs on rs.CODE = sd.REVENUE_SRC_CODE  "
            + "left outer join user_answer ua on ua.SURVEY_ID = sd.ID   "
            + "left outer join question q on q.Q_CODE = ua.Q_CODE  "
            + "left outer join answer a on a.A_CODE = ua.A_CODE  "
            + "left outer join local_authority la on la.CODE = sd.LA_CODE "
            + "left outer join gnd g on g.CODE = sd.GND_CODE  "
            + "left outer join district ds on ds.CODE = la.DISTRICT_CODE "
            + "left outer join province pv on pv.CODE = ds.PROVINCE_CODE  ";

    String SEARCH_SQL = "select "
            + "DISTINCT sd.ID , "//0
            + "u.USERNAME uu , "//1
            + "rs.DESCRIPTION rsd , "//2
            + "la.DESCRIPTION lad , "//3
            + "g.DESCRIPTION gd, "//4
            + "sd.LATITUDE, "//5
            + "sd.LONGITUDE, "//6
            + "ds.DESCRIPTION dd , "//7
            + "pv.DESCRIPTION pd , "//8
            + "sd.CREATED_TIME times "//9
            + "from survey_data sd  "
            + "left outer join user u on u.KEYID = sd.USER_ID "
            + "left outer join revenue_source rs on rs.CODE = sd.REVENUE_SRC_CODE  "
            + "left outer join user_answer ua on ua.SURVEY_ID = sd.ID   "
            + "left outer join question q on q.Q_CODE = ua.Q_CODE  "
            + "left outer join answer a on a.A_CODE = ua.A_CODE  "
            + "left outer join local_authority la on la.CODE = sd.LA_CODE "
            + "left outer join gnd g on g.CODE = sd.GND_CODE  "
            + "left outer join district ds on ds.CODE = la.DISTRICT_CODE "
            + "left outer join province pv on pv.CODE = ds.PROVINCE_CODE  ";

    String MAIN_SQL = "select "
            + "u.USERNAME uu , "//0
            + "rs.DESCRIPTION rsd , " //01
            + "la.DESCRIPTION lad , " //02
            + "g.DESCRIPTION gd, "
            //            + "a.ANSWER ad , "
            + "ua.ANSWER ad , " // 04
            + "q.QUESTION qd , " // 05
            + "sd.LATITUDE, "
            + "sd.LONGITUDE, "
            + "ds.DESCRIPTION dd , "//08
            + "pv.DESCRIPTION pd , "//09
            + "sd.ID , " //10
            + "rs.CODE rsdc , "//11
            + "la.CODE ladc , "//12
            + "ua.Q_CODE qdc , "//13
            + "sd.CREATED_TIME time "//14
            //            + "ua.A_CODE adc , "//15
            + "from survey_data sd "
            + "left outer join user u on u.KEYID = sd.USER_ID "
            + "left outer join revenue_source rs on rs.CODE = sd.REVENUE_SRC_CODE "
            + "left outer join local_authority la on la.CODE = sd.LA_CODE "
            + "left outer join gnd g on g.CODE = sd.GND_CODE "
            + "left outer join user_answer ua on ua.SURVEY_ID = sd.ID "
            + "left outer join question q on q.Q_CODE = ua.Q_CODE "
            + "left outer join answer a on a.A_CODE = ua.A_CODE "
            + "left outer join district ds on ds.CODE = la.DISTRICT_CODE "
            + "left outer join province pv on pv.CODE = ds.PROVINCE_CODE ";

//    String SUMMARY_SQL = "select "
//            + "rs.CODE rsd , "
//            + "la.CODE lad "
//            + "from survey_data sd "
//            + "left outer join user u on u.KEYID = sd.USER_ID "
//            + "left outer join revenue_source rs on rs.CODE = sd.REVENUE_SRC_CODE "
//            + "left outer join local_authority la on la.CODE = sd.LA_CODE "
//            + "left outer join gnd g on g.CODE = sd.GND_CODE "
//            + "left outer join user_answer ua on ua.SURVEY_ID = sd.ID "
//            + "left outer join question q on q.Q_CODE = ua.Q_CODE "
//            + "left outer join answer a on a.A_CODE = ua.A_CODE "
//            + "left outer join district ds on ds.CODE = la.DISTRICT_CODE "
//            + "left outer join province pv on pv.CODE = ds.PROVINCE_CODE ";

    String SUMMARY_SQL2 = "select "
            + "sd.REVENUE_SRC_CODE rsd , "
            + "sd.LA_CODE lad "
            + "from survey_data sd ";

    String SUMMARY_LA = "select "
            //            + "DISTINCT rs.CODE rsd , "
            + "DISTINCT la.CODE lad "
            + "from survey_data sd "
            + "left outer join user u on u.KEYID = sd.USER_ID "
            + "left outer join revenue_source rs on rs.CODE = sd.REVENUE_SRC_CODE "
            + "left outer join local_authority la on la.CODE = sd.LA_CODE "
            + "left outer join gnd g on g.CODE = sd.GND_CODE "
            + "left outer join user_answer ua on ua.SURVEY_ID = sd.ID "
            + "left outer join question q on q.Q_CODE = ua.Q_CODE "
            + "left outer join answer a on a.A_CODE = ua.A_CODE "
            + "left outer join district ds on ds.CODE = la.DISTRICT_CODE "
            + "left outer join province pv on pv.CODE = ds.PROVINCE_CODE ";

    String ACTION_SQL = "select "
            + "sd.ID , "//0
            + "u.USERNAME uu , "//1
            + "ua.ANSWER ad , "//2
            + "q.QUESTION qd , "//3
            + "sd.LATITUDE, "//4
            + "sd.LONGITUDE , "//5
            + "rs.DESCRIPTION rsd , "//6
            + "la.DESCRIPTION lad , "//7
            + "sd.IMAGE  "//8

            + "from survey_data sd  "
            + "left outer join user u on u.KEYID = sd.USER_ID  "
            + "left outer join revenue_source rs on rs.CODE = sd.REVENUE_SRC_CODE  "
            + "left outer join local_authority la on la.CODE = sd.LA_CODE  "
            + "left outer join gnd g on g.CODE = sd.GND_CODE  "
            + "left outer join user_answer ua on ua.SURVEY_ID = sd.ID  "
            + "left outer join question q on q.Q_CODE = ua.Q_CODE  "
            + "left outer join answer a on a.A_CODE = ua.A_CODE  "
            + "left outer join district ds on ds.CODE = la.DISTRICT_CODE "
            + "left outer join province pv on pv.CODE = ds.PROVINCE_CODE  ";

//    String ACTION_SQL2 = "select "
//            + "sd.id , "//0
//            + "sd.user uu , "//1
//            + "ua.ANSWER ad , "//2
//            + "q.QUESTION qd , "//3
//            + "sd.LATITUDE, "//4
//            + "sd.LONGITUDE , "//5
//            + "rs.DESCRIPTION rsd , "//6
//            + "la.DESCRIPTION lad , "//7
//            + "sd.IMAGE  "//8
//
//            + "from SurveyData sd  "
//            + "left outer join user u on u.keyid = sd.USER_ID  "
//            + "left outer join revenue_source rs on rs.CODE = sd.REVENUE_SRC_CODE  "
//            + "left outer join local_authority la on la.CODE = sd.LA_CODE  "
//            + "left outer join gnd g on g.CODE = sd.GND_CODE  "
//            + "left outer join user_answer ua on ua.SURVEY_ID = sd.ID  "
//            + "left outer join question q on q.Q_CODE = ua.Q_CODE  "
//            + "left outer join answer a on a.A_CODE = ua.A_CODE  "
//            + "left outer join district ds on ds.CODE = la.DISTRICT_CODE "
//            + "left outer join province pv on pv.CODE = ds.PROVINCE_CODE  ";

    public SurveyData getImage(String sid) throws Exception {
        SurveyData sdata = new SurveyData();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "select image from SurveyData as u where u.id=:id";

            Query query = session.createQuery(sql).setString("id", sid);
            List<byte[]> rows = query.list();

            for (byte[] row : rows) {
                try {
//                    System.out.println("(byte[]) row[0]" + (byte[]) row.getImage());
//                    System.out.println("row[0]" + row.getImage());
                    sdata.setImage(row);

                } catch (NullPointerException ex) {
                    System.out.println("error");
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }

        return sdata;

    }

    public List<ActionBean> getActionList(String sid) throws Exception {

        List<ActionBean> dataList = new ArrayList<ActionBean>();
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = this.COUNTS + " where sd.ID = " + '"' + sid + '"' + " ";
            Query queryCount = session.createSQLQuery(sqlCount);

            List countList = queryCount.list();

            BigInteger count = (BigInteger) countList.get(0);

            if (count.longValue() > 0) {
                String sqlQ = this.ACTION_SQL + " where sd.ID = " + '"' + sid + '"' + " ";

                List<Object[]> chequeList = (List<Object[]>) session.createSQLQuery(sqlQ).list();

                for (Object[] Bean : chequeList) {

                    ActionBean bean = new ActionBean();

                    try {
                        bean.setSid(String.valueOf(Bean[0]));
                    } catch (Exception e) {
                        bean.setSid("--");
                    }
                    try {
                        bean.setRevenus(String.valueOf(Bean[6]));
                    } catch (Exception e) {
                        bean.setRevenus("--");
                    }
                    try {
                        bean.setLat(String.valueOf(Bean[4]));
                    } catch (Exception e) {
                        bean.setLat("--");
                    }
                    try {
                        bean.setLng(String.valueOf(Bean[5]));
                    } catch (Exception e) {
                        bean.setLng("--");
                    }
                    try {
                        bean.setQuestion(String.valueOf(Bean[3]));
                    } catch (Exception e) {
                        bean.setQuestion("--");
                    }
                    try {
                        if (!String.valueOf(Bean[2]).isEmpty()) {
                            bean.setAnswer(String.valueOf(Bean[2]));
                        } else {
                            bean.setAnswer("-");
                        }
                    } catch (Exception e) {
                        bean.setAnswer("--");
                    }
                    try {

                        InputStream in = new ByteArrayInputStream((byte[]) Bean[8]);
                        BufferedImage bImageFromConvert = ImageIO.read(in);

                        BufferedImage bufferedImage = this.getScaledInstance(bImageFromConvert, bImageFromConvert.getWidth(), bImageFromConvert.getHeight(), RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(bufferedImage, "jpg", baos);
                        baos.flush();
                        byte[] imageInByte = baos.toByteArray();
                        baos.close();

                        byte[] blobAsBytes = imageInByte;
                        blobAsBytes = Base64.encodeBase64(imageInByte);

                        String imgString = new String(blobAsBytes);
                        bean.setImgBase(imgString);
//                        bean.setImg((byte[]) Bean[8]);
                    } catch (Exception e) {
                        System.out.println("ex " + e);
                        bean.setImage(null);
                    }

                    dataList.add(bean);

                }

            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return dataList;
    }

    public List<SearchDataBean> getSearchList(SearchInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<SearchDataBean> dataList = new ArrayList<SearchDataBean>();
        Session session = null;
        try {

            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by sd.CREATED_TIME asc";
            }

            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = this.SEARCH_COUNTS + " where " + where + " ";
            Query queryCount = session.createSQLQuery(sqlCount);

            List countList = queryCount.list();

            BigInteger count = (BigInteger) countList.get(0);

            if (count.longValue() > 0) {
                String sqlQ = this.SEARCH_SQL + " where " + where + " " + orderBy + " " + "LIMIT " + first + "," + max;

                List<Object[]> chequeList = (List<Object[]>) session.createSQLQuery(sqlQ).list();

                for (Object[] Bean : chequeList) {

                    SearchDataBean Page = new SearchDataBean();

                    try {
                        if (Bean[1] != null) {
                            Page.setUser(String.valueOf(Bean[1]));
                        } else {
                            Page.setUser("--");
                        }
                    } catch (Exception e) {
                        Page.setUser("--");
                    }
                    try {
                        Page.setRevenueSource(String.valueOf(Bean[2]));
                    } catch (Exception e) {
                        Page.setRevenueSource("--");

                    }
                    try {
                        Page.setLocalAuthority(String.valueOf(Bean[3]));

                    } catch (Exception e) {
                        Page.setLocalAuthority("--");
                    }
                    try {
                        if (Bean[4] != null && !Bean[3].equals("")) {
                            Page.setGnd(String.valueOf(Bean[4]));
                        } else {
                            Page.setGnd("--");
                        }

                    } catch (Exception e) {
                        Page.setGnd("--");
                    }
//                    try {
//                        if (Bean[4] != null && !Bean[4].equals("")) {
//                            Page.setAnswer(String.valueOf(Bean[4]));
//                        } else {
//                            Page.setAnswer("--");
//                        }
//
//                    } catch (Exception e) {
//                        Page.setAnswer("--");
//                    }
//                    try {
//                        Page.setQuestion(String.valueOf(Bean[5]));
//
//                    } catch (Exception e) {
//                        Page.setQuestion("--");
//                    }
                    try {
                        Page.setLatitude(String.valueOf(Bean[5]));

                    } catch (Exception e) {
                        Page.setLatitude("--");
                    }
                    try {
                        Page.setLongitude(String.valueOf(Bean[6]));

                    } catch (Exception e) {
                        Page.setLongitude("--");
                    }
                    try {
                        Page.setDistrict(String.valueOf(Bean[7]));

                    } catch (Exception e) {
                        Page.setDistrict("--");
                    }
                    try {
                        Page.setProvince(String.valueOf(Bean[8]));

                    } catch (Exception e) {
                        Page.setProvince("--");
                    }

                    try {
                        Page.setSid(String.valueOf(Bean[0]));

                    } catch (Exception e) {
                        Page.setSid("--");
                    }
                    try {
                        Page.setTimeStamp(String.valueOf(Bean[9]));

                    } catch (Exception e) {
                        Page.setTimeStamp("--");
                    }

                    Page.setFullCount(count.longValue());

                    dataList.add(Page);

                }

            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return dataList;
    }

    public List<SearchDataBean> getSearchListForCSV(SearchInputBean inputBean) throws Exception {

        List<SearchDataBean> dataList = new ArrayList<SearchDataBean>();
        Session session = null;
        try {

            HttpSession sessionHTTP = ServletActionContext.getRequest().getSession(false);
            session = HibernateInit.sessionFactory.openSession();

            String where = this.makeWhereClause(inputBean);

            String sqlCount = this.COUNTS + " where " + where + " ";
            Query queryCount = session.createSQLQuery(sqlCount);

            List countList = queryCount.list();
            BigInteger count = (BigInteger) countList.get(0);

            if (count.longValue() > 0) {
                String sqlQ = this.MAIN_SQL + " where " + where + " ";
                List<Object[]> chequeList = (List<Object[]>) session.createSQLQuery(sqlQ).list();
                sessionHTTP.setAttribute("CSV_QUERY_LIST_DATA", chequeList);

                for (Object[] Bean : chequeList) {

                    SearchDataBean Page = new SearchDataBean();

                    if (Bean[0] != null) {
                        Page.setUser(String.valueOf(Bean[0]));
                    } else {
                        Page.setUser("--");
                    }
                    if (Bean[1] != null) {
                        Page.setRevenueSource(String.valueOf(Bean[1]));
                    } else {
                        Page.setRevenueSource("--");

                    }
                    if (Bean[2] != null) {
                        Page.setLocalAuthority(String.valueOf(Bean[2]));

                    } else {
                        Page.setLocalAuthority("--");
                    }
                    if (Bean[3] != null) {
                        Page.setGnd(String.valueOf(Bean[3]));

                    } else {
                        Page.setGnd("--");
                    }
                    if (Bean[4] != null) {
                        Page.setAnswer(String.valueOf(Bean[4]));

                    } else {
                        Page.setAnswer("--");
                    }
                    if (Bean[5] != null) {
                        Page.setQuestion(String.valueOf(Bean[5]));

                    } else {
                        Page.setQuestion("--");
                    }
                    if (Bean[6] != null) {
                        Page.setLatitude(String.valueOf(Bean[6]));

                    } else {
                        Page.setLatitude("--");
                    }
                    if (Bean[7] != null) {
                        Page.setLongitude(String.valueOf(Bean[7]));

                    } else {
                        Page.setLongitude("--");
                    }
                    if (Bean[8] != null) {
                        Page.setDistrict(String.valueOf(Bean[8]));

                    } else {
                        Page.setDistrict("--");
                    }
                    if (Bean[9] != null) {
                        Page.setProvince(String.valueOf(Bean[9]));

                    } else {
                        Page.setProvince("--");
                    }

                    Page.setFullCount(count.longValue());

                    dataList.add(Page);

                }

//                this.getCSVDATAall();
//                this.getDataForSummary();
            }else{
                sessionHTTP.setAttribute("CSV_QUERY_LIST_DATA", null);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return dataList;
    }

    public ArrayList<String> getlaDISTINCT() throws Exception {

        Session session = null;
        ArrayList<String> full = new ArrayList<String>();
        try {

            session = HibernateInit.sessionFactory.openSession();

            String sqlQ = this.SUMMARY_LA + " ";
            List<String> chequeList = (List<String>) session.createSQLQuery(sqlQ).list();

            for (String Bean : chequeList) {
                full.add(Bean);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return full;
    }

    public List<CsvBean> getCSVDATAall() throws Exception {
        List<CsvBean> CsvBeanList = new ArrayList<CsvBean>();

        HttpSession sessionHTTP = ServletActionContext.getRequest().getSession(false);

        List<Object[]> list = (List<Object[]>) sessionHTTP.getAttribute("CSV_QUERY_LIST_DATA");


        if (list != null) {

            ArrayList<ArrayList<String>> csv = new ArrayList<ArrayList<String>>();
            ArrayList<ArrayList<String>> records = new ArrayList<ArrayList<String>>();
            ArrayList<String> sid = new ArrayList<String>();
            ArrayList<String> yesList = new ArrayList<String>();

            for (Object[] Bean : list) {
                ArrayList obj = new ArrayList();
                obj.add(String.valueOf(Bean[10]));//sid -0
                obj.add(String.valueOf(Bean[0]));//user -1
                obj.add(String.valueOf(Bean[14]));//time -2
                obj.add(String.valueOf(Bean[8]));//district -3
                obj.add(String.valueOf(Bean[9]));//province -4
                obj.add(String.valueOf(Bean[11]));//rs code -5
                obj.add(String.valueOf(Bean[12]));//la code -6
                obj.add(String.valueOf(Bean[13]));//question code -7
                obj.add(String.valueOf(Bean[4]));//answer -8
                if (!String.valueOf(Bean[4]).equals("")) {
                    obj.add(String.valueOf(Bean[11]));//09
                } else {
                    obj.add("NO");//09
                }
                obj.add(String.valueOf(Bean[6]));//10 lat
                obj.add(String.valueOf(Bean[7]));//11 lng
                obj.add(String.valueOf(Bean[3]));//12 gnd

                if (!sid.contains(String.valueOf(Bean[10]))) {
                    ArrayList<String> record = new ArrayList<String>();
                    sid.add(String.valueOf(Bean[10]));

                    record.add(String.valueOf(Bean[10]));
                    record.add(String.valueOf(Bean[0]));
                    record.add(String.valueOf(Bean[14]));
                    record.add(String.valueOf(Bean[8]));
                    record.add(String.valueOf(Bean[9]));
                    record.add(String.valueOf(Bean[12]));
                    record.add(String.valueOf(Bean[6]));
                    record.add(String.valueOf(Bean[7]));
                    record.add(String.valueOf(Bean[11]));
                    if(Bean[3]!=null){
                        record.add(String.valueOf(Bean[3]));
                    }else{
                        record.add(String.valueOf("--"));
                    }
                    records.add(record);

                }

                csv.add(obj);
            }

            for (ArrayList<String> arrayList : csv) {

                if (!yesList.contains(arrayList.get(9))) {
                    yesList.add(arrayList.get(9));
                }
            }

//        System.out.println("array " + csv);
//        System.out.println("yes list " + yesList);
            for (int i = 0; i < records.size(); i++) {
                CsvBean csvfile = new CsvBean();

                csvfile.setIdno(Integer.toString(i + 1));
                csvfile.setSid(records.get(i).get(0));
                csvfile.setUser(records.get(i).get(1));
                csvfile.setTimeStamp(records.get(i).get(2));
                csvfile.setProvince(records.get(i).get(4));
                csvfile.setDistrict(records.get(i).get(3));
                csvfile.setLocalAuthority(records.get(i).get(5));
                csvfile.setLat(records.get(i).get(6));
                csvfile.setLng(records.get(i).get(7));
                csvfile.setGnd(records.get(i).get(9));
                csvfile.setqList(this.getQAListForSID(records.get(i).get(0), csv));

                CsvBeanList.add(csvfile);

            }
//        CommonDAO dao = new CommonDAO();
//        System.out.println("qlist " +  dao.getQuestionListMap()) ;
//        for (CsvBean csvBean : CsvBeanList) {
//            System.out.println("map "  +csvBean.getqList());
//        }

        }

        return CsvBeanList;
    }

//    public List<SearchSetBean> makeSearchList(List<Object[]> lists) throws Exception {
//        List<SearchSetBean> SearchBeanSetList = new ArrayList<SearchSetBean>();
//
//        HttpSession sessionHTTP = ServletActionContext.getRequest().getSession(false);
//
//        List<Object[]> list = (List<Object[]>) sessionHTTP.getAttribute("CSV_QUERY_LIST_DATA");
//
//        ArrayList<ArrayList<String>> csv = new ArrayList<ArrayList<String>>();
//        ArrayList<ArrayList<String>> records = new ArrayList<ArrayList<String>>();
//        ArrayList<String> sid = new ArrayList<String>();
//        ArrayList<String> yesList = new ArrayList<String>();
//
//        for (Object[] Bean : lists) {
//            ArrayList obj = new ArrayList();
//            obj.add(String.valueOf(Bean[10]));//sid -0
//            obj.add(String.valueOf(Bean[0]));//user -1
//            obj.add(String.valueOf(Bean[14]));//time -2
//            obj.add(String.valueOf(Bean[8]));//district -3
//            obj.add(String.valueOf(Bean[9]));//province -4
//            obj.add(String.valueOf(Bean[11]));//rs code -5
//            obj.add(String.valueOf(Bean[12]));//la code -6
//            obj.add(String.valueOf(Bean[13]));//question code -7
//            obj.add(String.valueOf(Bean[4]));//answer -8
//            obj.add(String.valueOf(Bean[3]));//09 -gnd
//            obj.add(String.valueOf(Bean[6]));//10 lat
//            obj.add(String.valueOf(Bean[7]));//11 lng
//
//            if (!sid.contains(String.valueOf(Bean[10]))) {
//                ArrayList<String> record = new ArrayList<String>();
//                sid.add(String.valueOf(Bean[10]));
//
//                record.add(String.valueOf(Bean[10]));
//                record.add(String.valueOf(Bean[0]));
//                record.add(String.valueOf(Bean[14]));
//                record.add(String.valueOf(Bean[8]));
//                record.add(String.valueOf(Bean[9]));
//                record.add(String.valueOf(Bean[12]));
//                record.add(String.valueOf(Bean[6]));
//                record.add(String.valueOf(Bean[7]));
//                record.add(String.valueOf(Bean[11]));
//                record.add(String.valueOf(Bean[3]));
//
//                records.add(record);
//
//            }
//
//            csv.add(obj);
//        }
//
//        for (ArrayList<String> arrayList : csv) {
//
//            if (!yesList.contains(arrayList.get(9))) {
//                yesList.add(arrayList.get(9));
//            }
//        }
//
//        for (int i = 0; i < records.size(); i++) {
//            SearchSetBean csvfile = new SearchSetBean();
//
//            csvfile.setIdno(Integer.toString(i + 1));
//            csvfile.setSid(records.get(i).get(0));
//            csvfile.setUser(records.get(i).get(1));
//            csvfile.setTimeStamp(records.get(i).get(2));
//            csvfile.setProvince(records.get(i).get(4));
//            csvfile.setDistrict(records.get(i).get(3));
//            csvfile.setLocalAuthority(records.get(i).get(5));
//            csvfile.setLat(records.get(i).get(6));
//            csvfile.setLng(records.get(i).get(7));
//            csvfile.setRevenueS(records.get(i).get(8));
//            csvfile.setGnds(records.get(i).get(9));
//            csvfile.setqList(this.getQAListForSID2(records.get(i).get(0), csv));
//
//            SearchBeanSetList.add(csvfile);
//
//        }
//
//        return SearchBeanSetList;
//    }

    public Map<String, String> getQAListForSID(String SID, ArrayList<ArrayList<String>> array) throws Exception {

        CommonDAO dao = new CommonDAO();
        Map<String, String> QAlist = dao.getQuestionListMap();

        for (ArrayList<String> arrayList : array) {

            if (arrayList.get(0).equals(SID)) {
                if (QAlist.containsKey(arrayList.get(9))) {
                    QAlist.put(arrayList.get(9), "YES");
                }

                if (arrayList.get(8).split(",").length > 1) {
                    arrayList.set(8, '"' + arrayList.get(8) + "'");
                    System.out.println("answer split " + arrayList.get(8));
                }
                QAlist.put(arrayList.get(7), arrayList.get(8));
            }
        }

        return QAlist;

    }

    public List<SummaryBean> getDataForSummary_edited() throws Exception {

        List<SummaryBean> summaryList = new ArrayList<SummaryBean>();
        Session session = null;

        Map<String, ArrayList<String>> RAarray = new LinkedHashMap<String, ArrayList<String>>();

        CommonDAO cdao = new CommonDAO();

        ArrayList<String> lalist = new ArrayList<String>();
        lalist = this.getlaDISTINCT();

        try {

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = this.COUNTS + " ";
            Query queryCount = session.createSQLQuery(sqlCount);

            List countList = queryCount.list();
            BigInteger count = (BigInteger) countList.get(0);

            if (count.longValue() > 0) {
                String sqlQ = this.SUMMARY_SQL2 + " ";
                List<Object[]> chequeList = (List<Object[]>) session.createSQLQuery(sqlQ).list();

                ArrayList<Map<String, String>> full = new ArrayList<Map<String, String>>();

                for (Object[] Bean : chequeList) {

                    Map<String, String> LaRs = new HashMap<String, String>();

                    LaRs.put(String.valueOf(Bean[1]), String.valueOf(Bean[0]));
                    full.add(LaRs);

                }

                for (String string : lalist) {
                    ArrayList<String> rec = new ArrayList<String>();
                    rec = getrscountforla(string, full);

                    RAarray.put(string, rec);
                }
                for (int i = 0; i < lalist.size(); i++) {
                    SummaryBean bean = new SummaryBean();

                    Map<String, Map<String, String>> list = new LinkedHashMap<String, Map<String, String>>();
                    list = this.getraCOUNT(lalist.get(i), RAarray);

                    Map<String, String> list_RS = new LinkedHashMap<String, String>();
                    list_RS = list.get(lalist.get(i));

                    Object[] values = list_RS.values().toArray();

                    bean.setId(Integer.toString(i + 1));
                    bean.setLacode(cdao.getLAfromCode(lalist.get(i)));

                    bean.setCoes(String.valueOf(values[0]));
                    bean.setLadb(String.valueOf(values[1]));
                    bean.setPalo(String.valueOf(values[2]));
                    bean.setRedf(String.valueOf(values[3]));
                    bean.setReis(String.valueOf(values[4]));
                    bean.setRemc(String.valueOf(values[5]));
                    bean.setRetm(String.valueOf(values[6]));
                    bean.setSadd(String.valueOf(values[7]));

                    int total = Integer.parseInt(String.valueOf(values[0]))
                            + Integer.parseInt(String.valueOf(values[1]))
                            + Integer.parseInt(String.valueOf(values[2]))
                            + Integer.parseInt(String.valueOf(values[3]))
                            + Integer.parseInt(String.valueOf(values[4]))
                            + Integer.parseInt(String.valueOf(values[5]))
                            + Integer.parseInt(String.valueOf(values[6]))
                            + Integer.parseInt(String.valueOf(values[7]));
                    bean.setTotal(Integer.toString(total));
                    bean.setFullCount(count.longValue());
                    summaryList.add(bean);

                }

            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return summaryList;
    }

    public ArrayList<String> getrscountforla(String la, ArrayList<Map<String, String>> array) {

        ArrayList<String> ss = new ArrayList<String>();
        for (Map<String, String> map : array) {
            ss.add(map.get(la));
        }

        return ss;

    }

    public Map<String, Map<String, String>> getraCOUNT(String la, Map<String, ArrayList<String>> array) throws Exception {
        int count = 0;
        CommonDAO cdao = new CommonDAO();
        Map<String, Map<String, String>> rscount = new LinkedHashMap<String, Map<String, String>>();
        List<RevenueSource> full_rs_list = cdao.getRevenueSourceList();

        if (array.keySet().contains(la)) {

            Map<String, String> sss = new LinkedHashMap<String, String>();
            for (RevenueSource revenueSource : full_rs_list) {
                if (array.get(la).contains(revenueSource.getCode())) {
                    for (int i = 0; i < array.get(la).size(); i++) {
                        if (array.get(la).get(i) != null) {
                            if (array.get(la).get(i).equals(revenueSource.getCode())) {
                                count++;
                            }
                        }
                    }
                }
                sss.put(revenueSource.getCode(), Integer.toString(count));
                count = 0;
                rscount.put(la, sss);

            }

        }

        return rscount;

    }

    public List<LatLngBean> getLatLngForMap(SearchInputBean inputBean) throws Exception {

        List<LatLngBean> dataList = new ArrayList<LatLngBean>();
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            String where = this.makeWhereClause(inputBean);

            String sqlCount = this.COUNTS + " where " + where + " ";
            Query queryCount = session.createSQLQuery(sqlCount);

            List countList = queryCount.list();
            BigInteger count = (BigInteger) countList.get(0);

            int incriment = 1;

            if (count.longValue() > 0) {
                String sqlQ = this.MAIN_SQL + " where " + where + " ";
                List<Object[]> chequeList = (List<Object[]>) session.createSQLQuery(sqlQ).list();

                for (Object[] Bean : chequeList) {

                    LatLngBean latlng = new LatLngBean();

                    if (Bean[6] != null) {
                        latlng.setLat(String.valueOf(Bean[6]));

                    } else {
                        latlng.setLat("--");
                    }
                    if (Bean[7] != null) {
                        latlng.setLng(String.valueOf(Bean[7]));

                    } else {
                        latlng.setLng("--");
                    }
                    if (Bean[3] != null) {
                        latlng.setPlacenamegnd(String.valueOf(Bean[3]));

                    } else {
                        latlng.setPlacenamegnd("--");
                    }
                    if (Bean[2] != null) {
                        latlng.setPlacenamela(String.valueOf(Bean[2]));

                    } else {
                        latlng.setPlacenamela("--");
                    }

                    latlng.setZindex(String.valueOf(incriment));

                    incriment++;

                    dataList.add(latlng);

                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return dataList;
    }

    public void getQuestionforRS(SearchInputBean inputBean) throws Exception {

        List<Question> qList = new ArrayList<Question>();
        List<Question> sssList = new ArrayList< Question>();
        inputBean.setQuestionList(sssList);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Question as t where t.revenueSource.code =:rscode  order by Upper(t.question) asc";
            Query query = session.createQuery(hql).setString("rscode", inputBean.getRevenueSource());
            qList = (List<Question>) query.list();

            for (Iterator<Question> it = qList.iterator(); it.hasNext();) {

                Question ques = it.next();
                Question quesObj = new Question();
                quesObj.setQCode(ques.getQCode());
                quesObj.setQuestion(ques.getQuestion());
                inputBean.getQuestionList().add(quesObj);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public void getGNDforLA(SearchInputBean inputBean) throws Exception {

        List<Gnd> qList = new ArrayList<Gnd>();
        List<Gnd> sssList = new ArrayList< Gnd>();
        inputBean.setGndList(sssList);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Gnd as t where t.laCode =:laCode  order by Upper(t.description) asc";
            Query query = session.createQuery(hql).setString("laCode", inputBean.getLocalAuthority());
            qList = (List<Gnd>) query.list();

            for (Iterator<Gnd> it = qList.iterator(); it.hasNext();) {

                Gnd gnds = it.next();
                Gnd gndObj = new Gnd();
                gndObj.setCode(gnds.getCode());
                gndObj.setDescription(gnds.getDescription());
                inputBean.getGndList().add(gndObj);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public void getQuestionList(SearchInputBean inputBean) throws Exception {

        List<Question> gList = new ArrayList<Question>();
        List<Question> allgList = new ArrayList< Question>();
        inputBean.setQuestionList(allgList);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Question as t order by Upper(t.question) asc";
            Query query = session.createQuery(hql);
            gList = (List<Question>) query.list();

            for (Iterator<Question> it = gList.iterator(); it.hasNext();) {

                Question ques = it.next();
                Question quesObj = new Question();
                quesObj.setQCode(ques.getQCode());
                quesObj.setQuestion(ques.getQuestion());
                inputBean.getQuestionList().add(quesObj);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public void getGNDList(SearchInputBean inputBean) throws Exception {

        List<Gnd> gList = new ArrayList<Gnd>();
        List<Gnd> allgList = new ArrayList< Gnd>();
        inputBean.setGndList(allgList);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Gnd as t order by Upper(t.description) asc";
            Query query = session.createQuery(hql);
            gList = (List<Gnd>) query.list();

            for (Iterator<Gnd> it = gList.iterator(); it.hasNext();) {

                Gnd gnds = it.next();
                Gnd gndObj = new Gnd();
                gndObj.setCode(gnds.getCode());
                gndObj.setDescription(gnds.getDescription());
                inputBean.getGndList().add(gndObj);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public void getRevenueSource(SearchInputBean inputBean) throws Exception {

        List<Question> qList = new ArrayList<Question>();
        List<RevenueSource> rcList = new ArrayList< RevenueSource>();
        inputBean.setRevenueSourceList(rcList);
        List<RevenueSource> rvList = new ArrayList<RevenueSource>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Question as t where t.QCode =:qcode  order by Upper(t.question) asc";
            Query query = session.createQuery(hql).setString("qcode", inputBean.getQuestion());
            qList = (List<Question>) query.list();

            String rscode = "code";

            for (Iterator<Question> it = qList.iterator(); it.hasNext();) {

                Question ques = it.next();
                RevenueSource rc = new RevenueSource();
                rscode = ques.getRevenueSource().getCode();
                rc.setCode(rscode);
                rc.setDescription(ques.getRevenueSource().getDescription());
                inputBean.getRevenueSourceList().add(rc);
            }

            String hql2 = "from RevenueSource as t where t.code !=:code order by t.createdTime asc";
            Query query2 = session.createQuery(hql2).setString("code", rscode);
            rvList = (List<RevenueSource>) query2.list();

            for (Iterator<RevenueSource> it = rvList.iterator(); it.hasNext();) {

                RevenueSource rsource = it.next();
                RevenueSource rsObj = new RevenueSource();
                rsObj.setCode(rsource.getCode());
                rsObj.setDescription(rsource.getDescription());
                inputBean.getRevenueSourceList().add(rsObj);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public void getLA(SearchInputBean inputBean) throws Exception {

        List<Gnd> gList = new ArrayList<Gnd>();
        List<LocalAuthority> laList = new ArrayList< LocalAuthority>();
        inputBean.setLocalAuthorityList(laList);
        List<LocalAuthority> loaList = new ArrayList<LocalAuthority>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Gnd as t where t.code =:code  order by Upper(t.description) asc";
            Query query = session.createQuery(hql).setString("code", inputBean.getGnd());
            gList = (List<Gnd>) query.list();

            String lacode = "code";

            for (Iterator<Gnd> it = gList.iterator(); it.hasNext();) {

                Gnd gnds = it.next();
                LocalAuthority la = new LocalAuthority();
                lacode = gnds.getLaCode();
                la.setCode(lacode);
                la.setDescription(this.getDescriptionByLa(lacode));
                inputBean.getLocalAuthorityList().add(la);
            }

            String hql2 = "from LocalAuthority as t where t.code !=:code order by Upper(t.description) asc";
            Query query2 = session.createQuery(hql2).setString("code", lacode);
            loaList = (List<LocalAuthority>) query2.list();

            for (Iterator<LocalAuthority> it = loaList.iterator(); it.hasNext();) {

                LocalAuthority laty = it.next();
                LocalAuthority laObj = new LocalAuthority();
                laObj.setCode(laty.getCode());
                laObj.setDescription(laty.getDescription());
                inputBean.getLocalAuthorityList().add(laObj);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
    }

    private String getDescriptionByLa(String code) throws Exception {
        List<LocalAuthority> laList = new ArrayList< LocalAuthority>();
        Session session = null;
        String des = "code";
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from LocalAuthority as t where t.code =:code order by Upper(t.description) asc";
            Query query = session.createQuery(hql).setString("code", code);
            laList = (List<LocalAuthority>) query.list();

            des = laList.get(0).getDescription();

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }

        return des;
    }

    private String makeWhereClause(SearchInputBean inputBean) {
        String where = " 1=1 ";
        if (inputBean.getRevenueSource() != null && !inputBean.getRevenueSource().isEmpty()) {
            where += " and sd.REVENUE_SRC_CODE = '" + inputBean.getRevenueSource() + "'";
        }
        if (inputBean.getQuestion() != null && !inputBean.getQuestion().isEmpty()) {
            where += " and ua.Q_CODE = '" + inputBean.getQuestion() + "'";
        }
        if (inputBean.getLocalAuthority() != null && !inputBean.getLocalAuthority().isEmpty()) {
            where += " and sd.LA_CODE = '" + inputBean.getLocalAuthority() + "'";
        }
        if (inputBean.getGnd() != null && !inputBean.getGnd().isEmpty()) {
            where += " and sd.GND_CODE = '" + inputBean.getGnd() + "'";
        }
        if (inputBean.getUser() != null && !inputBean.getUser().isEmpty()) {
            where += " and sd.USER_ID = '" + inputBean.getUser() + "'";
        }

        return where;
    }

    public String HashSHA256(String password) throws NoSuchAlgorithmException {
        String hash = "";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public String checkUser(SearchInputBean inputBean) throws Exception {
        List<User> laList = new ArrayList<User>();
        Session session = null;
        String des = "";
        try {
            session = HibernateInit.sessionFactory.openSession();

            String hash = this.HashSHA256(inputBean.getLoginPassword());

            String hql = "from User as t where t.username =:username and LOWER(t.password)=:password and t.userType.id=:userType  order by Upper(t.username) asc";
            Query query = session.createQuery(hql).setString("username", inputBean.getLoginUserName())
                    .setString("password", hash.toLowerCase())
                    .setInteger("userType", 1);

            laList = (List<User>) query.list();

            if (laList.size() > 0) {
                if (laList.get(0).getUserStatus() == 1) {
                    des = "";
                } else if (laList.get(0).getUserStatus() == 0) {
                    des = "new";
                }
            } else {
                des = "error";
            }

        } catch (Exception e) {
            des = "error";
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }

        return des;
    }

    public void getRSList(SearchInputBean inputBean) throws Exception {

        List<RevenueSource> gList = new ArrayList<RevenueSource>();
        List<RevenueSource> allgList = new ArrayList< RevenueSource>();
        inputBean.setRevenueSourceList(allgList);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from RevenueSource as t order by t.createdTime asc";
            Query query = session.createQuery(hql);
            gList = (List<RevenueSource>) query.list();

            for (Iterator<RevenueSource> it = gList.iterator(); it.hasNext();) {

                RevenueSource ques = it.next();
                RevenueSource quesObj = new RevenueSource();
                quesObj.setCode(ques.getCode());
                quesObj.setDescription(ques.getDescription());
                inputBean.getRevenueSourceList().add(quesObj);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public void getLAList(SearchInputBean inputBean) throws Exception {

        List<LocalAuthority> gList = new ArrayList<LocalAuthority>();
        List<LocalAuthority> allgList = new ArrayList< LocalAuthority>();
        inputBean.setLocalAuthorityList(allgList);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from LocalAuthority as t order by Upper(t.description) asc";
            Query query = session.createQuery(hql);
            gList = (List<LocalAuthority>) query.list();

            for (Iterator<LocalAuthority> it = gList.iterator(); it.hasNext();) {

                LocalAuthority ques = it.next();
                LocalAuthority quesObj = new LocalAuthority();
                quesObj.setCode(ques.getCode());
                quesObj.setDescription(ques.getDescription());
                inputBean.getLocalAuthorityList().add(quesObj);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public void getUserList(SearchInputBean inputBean) throws Exception {

        List<User> gList = new ArrayList<User>();
        List<User> allgList = new ArrayList< User>();
        inputBean.setUserList(allgList);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from User as t where t.userType != 1 order by Upper(t.username) asc";
            Query query = session.createQuery(hql);
            gList = (List<User>) query.list();

            for (Iterator<User> it = gList.iterator(); it.hasNext();) {

                User ques = it.next();
                User quesObj = new User();
                quesObj.setKeyid(ques.getKeyid());
                quesObj.setUsername(ques.getUsername());
                inputBean.getUserList().add(quesObj);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public String passwordChange(SearchInputBean inputBean) throws Exception {
        User User = new User();
        Session session = null;
        Transaction txn = null;
        String des = "";
        try {
            session = HibernateInit.sessionFactory.openSession();

            String hash = this.HashSHA256(inputBean.getOldPassword());

            txn = session.beginTransaction();
            String hql = "from User as t where t.username =:username and LOWER(t.password)=:password and t.userType.id=:userType  order by Upper(t.username) asc";
            Query query = session.createQuery(hql).setString("username", inputBean.getUsername())
                    .setString("password", hash.toLowerCase())
                    .setInteger("userType", 1);

            try {
                User = (User) query.list().get(0);
            } catch (IndexOutOfBoundsException e) {

                throw e;
            }

            if (User != null) {

                hash = this.HashSHA256(inputBean.getNewPassword());

                User.setPassword(hash);
                User.setUserStatus(1);

                session.update(User);
                txn.commit();

            } else {
                des = "User not found";
            }

        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.flush();
                    session.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return des;
    }

    public String userStatusChange(SearchInputBean inputBean) throws Exception {
        User User = new User();
        Session session = null;
        Transaction txn = null;
        String des = "";
        try {
            session = HibernateInit.sessionFactory.openSession();

            String hash = this.HashSHA256(inputBean.getOldPassword());

            txn = session.beginTransaction();
            String hql = "from User as t where t.username =:username and LOWER(t.password)=:password and t.userType.id=:userType  order by Upper(t.username) asc";
            Query query = session.createQuery(hql).setString("username", inputBean.getUsername())
                    .setString("password", hash.toLowerCase())
                    .setInteger("userType", 1);

            User = (User) query.list().get(0);

            if (User != null) {

                //hash = this.HashSHA256(inputBean.getNewPassword());
                //User.setPassword(hash);
                User.setUserStatus(1);

                session.update(User);
                txn.commit();

            } else {
                des = "User not found";
            }

        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.flush();
                    session.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return des;
    }

    public String getQlistForCSV_edited(List<Question> qlist) throws Exception {
        String q_list = ",";
        CommonDAO dao = new CommonDAO();

        List<RevenueSource> rsList = dao.getRevenueSourceList();

        String RSname = "";
        String qlists = "";
        for (int i = 0; i < rsList.size(); i++) {
            RSname = rsList.get(i).getDescription();

            for (int j = 0; j < qlist.size(); j++) {
                if (rsList.get(i).getCode().equals(qlist.get(j).getRevenueSource().getCode())) {

                    if (qlist.get(j).getQuestion().split(",").length > 1) {
                        qlist.get(j).setQuestion('"' + qlist.get(j).getQuestion() + '"');
                    };

                    qlists += "," + qlist.get(j).getQuestion();
                }
            }
            RSname = RSname + qlists + ",";
            qlists = "";

            q_list += RSname;

        }
        return q_list;
    }

    public int imageSizeDivideFac(BufferedImage img) {
        int fac = 1;

        if (img.getWidth() <= 1000) {
            fac = 1;
        } else if (img.getWidth() <= 2000) {
            fac = 3;
        } else if (img.getWidth() <= 3000) {
            fac = 5;
        } else if (img.getWidth() <= 4000) {
            fac = 7;
        } else {
            fac = 10;
        }
        return fac;
    }

    public BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean higherQuality) {

        int type = higherQuality ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage) img;

        targetWidth = targetWidth / this.imageSizeDivideFac(img);
        targetHeight = targetHeight / this.imageSizeDivideFac(img);

        int w, h;
        if (higherQuality) {
            w = img.getWidth();
            h = img.getHeight();

        } else {

            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();

            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);
        return ret;

    }
}
