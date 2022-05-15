/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MeritRankings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Admin
 */
public class Merit_InterfaceAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Merit_Interfaceform f = (Merit_Interfaceform) form;
        String iti_code = f.getIti_code();
        
        HttpSession ses = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        String redirect = "";
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = null;

        String admission_type = "";
        if (request.getSession().getAttribute("role_id").toString().equals("3")) {
            admission_type = "district";
        } else {
            admission_type = "iti";
        }
        st = con.createStatement();
        String caste = f.getType();
        String qual = f.getQual();
        String field_name = "iti_code";
        if (!admission_type.equals("iti")) {
            field_name = "dist_code";
        }
        String temp_sql = "Select * from ranks where " + field_name + "='" + iti_code + "' and qual='" + qual + "' and phase='2e'";
        ResultSet rs = st.executeQuery(temp_sql);
        boolean skip = false;
        if (rs.next()) {
            skip = true;
        }
        try {
            st.close();
            rs.close();
            st = null;
        } catch (Exception ex) {
        }
        if (!skip) {
            String sql = "Select * from trade_sel where " + field_name + "='" + iti_code + "'";
            String where = " where ";
            rs = null;
            if ("gen".equals(caste)) {
                try {
                    st = con.createStatement();
                    if (qual.equals("all") || qual.equals("assc")) {
                        sql = "select * from meritlist('" + iti_code + "','" + qual + "','" + field_name + "','')";
                        st.executeQuery(sql);
                        st.close();
                        con.close();
                    }
                    redirect = "success";
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    if (con != null) {
                        con.close();
                        con = null;
                    }
                }
                ses.setAttribute("iti_code", iti_code);
            } else {
                ses.setAttribute("category", caste);
                redirect = "category";
            }
        } else {
            ses.setAttribute("category", caste);
            if (caste.equals("gen")) {
                ses.setAttribute("iti_code", iti_code);
                redirect = "success";
            } else {
                redirect = "category";
            }
        }

        ses.setAttribute("qual", qual);
        return mapping.findForward(redirect);
    }
}
