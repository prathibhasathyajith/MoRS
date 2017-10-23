/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mrs.listener.HibernateInit;
import mrs.mapping.Gnd;
import mrs.mapping.LocalAuthority;
import mrs.mapping.Question;
import mrs.mapping.RevenueSource;
import mrs.mapping.User;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author prathibha_s
 */
public class CommonDAO {

    public List<User> getUserList() throws Exception {
        List<User> userList = new ArrayList<User>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from User as t order by Upper(t.username) asc";
            Query query = session.createQuery(hql);
            userList = (List<User>) query.list();

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
        return userList;
    }

    public List<Question> getQuestionList() throws Exception {
        List<Question> qList = new ArrayList<Question>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
//            String hql = "from Question as t order by Upper(t.revenueSource) asc";
            String hql = "select q.revenueSource,q.QCode,q.question,q.createdTime from Question q JOIN q.revenueSource rs  WHERE q.revenueSource =rs.code ORDER BY q.createdTime asc";

            Query query = session.createQuery(hql);
            List<Object[]> qListObj = (List<Object[]>) query.list();

            for (Object[] objects : qListObj) {
                Question ques = new Question();
                ques.setRevenueSource((RevenueSource) objects[0]);
                ques.setQCode(String.valueOf(objects[1]));
                ques.setQuestion(String.valueOf(objects[2]));
                ques.setCreatedTime((Date) objects[3]);

                qList.add(ques);

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
        return qList;
    }

    public Map<String, String> getQuestionListMap() throws Exception {
        List<Object[]> qListx = new ArrayList<Object[]>();
        List<Question> qList = new ArrayList<Question>();

        Map<String, String> map = new LinkedHashMap<String, String>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
//            String hql = "from Question as t order by t.revenueSource asc";
            String hql = "select q.revenueSource,q.QCode,q.question,q.createdTime from Question q JOIN q.revenueSource rs  WHERE q.revenueSource =rs.code ORDER BY q.createdTime asc";

            Query query = session.createQuery(hql);
            qListx = (List<Object[]>) query.list();

            for (Object[] objects : qListx) {
                Question ques = new Question();
                ques.setRevenueSource((RevenueSource) objects[0]);
                ques.setQCode(String.valueOf(objects[1]));
                ques.setQuestion(String.valueOf(objects[2]));
                ques.setCreatedTime((Date) objects[3]);

                qList.add(ques);

            }

            for (Question Bean : qList) {
                if (!map.containsKey(Bean.getRevenueSource().getCode())) {
                    map.put(Bean.getRevenueSource().getCode(), "NO");
                }
                map.put(Bean.getQCode(), "");

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
        return map;
    }

    public Map<String, String> getQuestionListMap2() throws Exception {
        List<Question> qList = new ArrayList<Question>();
        Map<String, String> map = new LinkedHashMap<String, String>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Question as t order by t.revenueSource asc";
            Query query = session.createQuery(hql);
            qList = (List<Question>) query.list();

            for (Question Bean : qList) {
                map.put(Bean.getQCode(), "");

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
        return map;
    }

    public List<RevenueSource> getRevenueSourceList() throws Exception {
        List<RevenueSource> rvList = new ArrayList<RevenueSource>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from RevenueSource as t order by t.createdTime asc";
            Query query = session.createQuery(hql);
            rvList = (List<RevenueSource>) query.list();

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
        return rvList;
    }

    public List<LocalAuthority> getLocalAuthorityList() throws Exception {
        List<LocalAuthority> laList = new ArrayList<LocalAuthority>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from LocalAuthority as t order by Upper(t.description) asc";
            Query query = session.createQuery(hql);
            laList = (List<LocalAuthority>) query.list();

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
        return laList;
    }

    public List<Gnd> getGndList() throws Exception {
        List<Gnd> gndList = new ArrayList<Gnd>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Gnd as t order by Upper(t.description) asc";
            Query query = session.createQuery(hql);
            gndList = (List<Gnd>) query.list();

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
        return gndList;
    }

    public String getLAfromCode(String code) throws Exception {
        List<LocalAuthority> gndList = new ArrayList<LocalAuthority>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from LocalAuthority as t where t.code =:code order by Upper(t.description) asc";
            Query query = session.createQuery(hql).setString("code", code);
            gndList = (List<LocalAuthority>) query.list();

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
        return gndList.get(0).getDescription();
    }

}
