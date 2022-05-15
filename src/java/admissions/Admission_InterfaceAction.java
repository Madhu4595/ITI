package admissions;

import DBC.DBConnection;

import Master.Trade.JavaBeanFormSerializable;
import beans.MyUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class Admission_InterfaceAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        Connection con = null;
        ResultSet rs = null;
        Statement st = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnectionGen();

            final HttpSession session = request.getSession();
            final String ins_code = beans.MyUtil.filterBad("" + session.getAttribute("ins_code"));
            final String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));

            String stored_token = beans.MyUtil.filterBad("" + session.getAttribute("generated_token"));
            String token = beans.MyUtil.filterBad(request.getParameter("token"));

            String id = stored_token.substring(10, 20) + request.getSession().getId() + stored_token.substring(2, 6);
            if (!token.equals(id)) { return mapping.findForward("failure"); }

            String field = "";
            if (role_id.equals("3")) {
                field = "dist_code";
            } else if (role_id.equals("4")) {
                field = "iti_code";
            }
            String year = "";
            String phase = "";
            //phase = "2";
            try {
                year = MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
                phase = MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);

            } catch (Exception ex2) {
                System.out.println("phaseexception" + ex2);
            }
            final Admission_InterfaceForm fm = (Admission_InterfaceForm) form;
            final String rank = beans.MyUtil.filterBad(fm.getRank());

            final int merit_from = Integer.parseInt("" + session.getAttribute("merit_from"));
            final int merit_to = Integer.parseInt("" + session.getAttribute("merit_to"));

            if (Integer.parseInt(rank) >= merit_from && Integer.parseInt(rank) > merit_to) {
                request.setAttribute("detailsnotfound", (Object) ("Give Rank Between " + merit_from + " and " + merit_to + " only"));
                return mapping.findForward("detailsnotfound");
            }

            final ArrayList UserList = new ArrayList();
            final JavaBeanFormSerializable lk = new JavaBeanFormSerializable();
            st = con.createStatement();
            int i = 0;
            String regid = "";

            try {
                regid = MyUtil.getOptionValue("ranks", "rank", "regid", rank, field, ins_code, "phase", phase, con);
                try {
                    if (regid == null) {
                        con.close();
                        return mapping.findForward("fail");
                    }
                } catch (Exception ex3) {
                    System.out.println("exceptionfail" + ex3);
                    con.close();
                    return mapping.findForward("fail");
                }

                final String qry = "select name,fname,caste,gender,phc,dob,exservice from application where regid=?";
                ps = con.prepareStatement(qry);
                ps.setString(1, regid);
                rs = ps.executeQuery();
                request.setAttribute("regid", (Object) regid);

                if (rs.next()) {
                    ++i;
                    lk.setName(rs.getString("name"));
                    lk.setFatherName(rs.getString("fname"));
                    lk.setCaste(rs.getString("caste"));
                    lk.setGender(rs.getString("gender"));
                    lk.setPhc(rs.getString("phc"));
                    lk.setDob(rs.getString("dob"));
                    lk.setRegid(Integer.parseInt(regid));
                    lk.setRank(rank);
                }

                rs.close();
                st.close();
                con.close();
                UserList.add(lk);
                request.setAttribute("users", (Object) UserList);
            } catch (Exception ex3) {
                System.out.println("exception200" + ex3);
                i = -200;
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            if (i > 0) {
                return mapping.findForward("selecttrade");
            }
            if (i == -200) {
                request.setAttribute("detailsnotfound", (Object) "No Details found with Given Rank");
                return mapping.findForward("detailsnotfound");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return mapping.findForward("success");
    }
}
