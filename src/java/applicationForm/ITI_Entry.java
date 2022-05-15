package applicationForm;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ITI_Entry extends org.apache.struts.action.Action {

    private String redirect;

    @Override
    @SuppressWarnings("empty-statement")
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Edit_InterfaceForm formvariable = (Edit_InterfaceForm) form;

        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "application & cand_marks", "Insert", request);

        HttpSession ses = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }

        Connection con = DBC.DBConnection.getConnectionGen();
        con.setAutoCommit(false);
        boolean commit = true;
        
        PreparedStatement st = null;
        PreparedStatement pst = null;
        String redirect = "success";

        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        String phse = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);
        System.out.println("phase value" + phse);
        phse = "3";
        year = "2020";

        String dob = beans.MyUtil.filterBad(formvariable.getDob());
        String name = beans.MyUtil.filterBad(formvariable.getName());
        String dist[] =  formvariable.getAddrs_mandal_p();
        System.out.println("dist--->" + dist);

        Date temp2 = Date.valueOf(DBC.MyUtil.ChDate1(dob));
        String sql = "Select regid from student_application where name=? and dob=?";
        System.out.println("sql issssssssssssssssss:" + sql);
        ResultSet rs = null;
        try {
            st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setDate(2, temp2);
            rs = st.executeQuery();
            System.out.println("sql isssssssfgfgsssssssssss:" + sql);

        } catch (Exception ex) {
            System.out.println("" + ex);
        }

        String regid = "";
        if (rs.next()) {
            regid = "" + rs.getInt("regid");
            System.out.println("regd is " + regid);

        }

        try {
            String Saloc[] = formvariable.getAddrs_state_p();
            String dist_name[] = formvariable.getAddrs_district_p();

            String addrs_mandal_p[] = formvariable.getAddrs_mandal_p();
            int locLength = formvariable.getAddrs_mandal_p().length;
            String insert_table = "student_trade_sel";
            String fieldname = "";
            String type = beans.MyUtil.filterBad(formvariable.getType());
            String phase = "phase";
            fieldname = "dist_code";

            int j = 0;

            for (int i = 0; i < locLength; i++) {
                if (Saloc[i] != null || dist_name[i] != null || addrs_mandal_p[i] != null || !Saloc[i].equalsIgnoreCase("") || !dist_name[i].equalsIgnoreCase("")) {
                    String state_code = Saloc[i];
                    String district = dist_name[i];
                    String mandal = addrs_mandal_p[i];

                    String query = "insert into ? (regid,?,temp_code,trno,?,year,iti_code) values(?,?,?,?,hstore(?,'true'),?,?)";
                    pst = con.prepareStatement(query);
                    pst.setString(1, insert_table);
                    pst.setString(2, fieldname);
                    pst.setString(3, phase);
                    pst.setString(4, regid);
                    pst.setString(5, state_code);
                    pst.setString(6, state_code);
                    pst.setInt(7, trno);
                    pst.setString(8, phse);
                    pst.setString(9, year);
                    pst.setString(10, mandal);
               
                    System.out.println("pst is--->" + pst);
                    if (commit) {
                        System.out.println("executing");
                        pst.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {

            commit = false;
            e.printStackTrace();
        }

        try {
            System.out.println("query is " + st.toString());
            System.out.println("commit value is " + commit);

            if (commit) {
                try {
                    st.executeUpdate();
                    System.out.println("st is--->" + st);
                    System.out.println("executed");
                    return mapping.findForward(redirect);

                } catch (Exception ex) {
                    System.out.println("Exception is--->" + ex);
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception is--->" + ex);
            ex.printStackTrace();
            commit = false;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
            if (pst != null) {
               pst.close();
                pst = null;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (con != null) {
                con.close();
                con = null;
            }
        }

        return mapping.findForward(redirect);
    }
}
