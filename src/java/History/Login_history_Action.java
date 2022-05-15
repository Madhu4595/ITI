/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package History;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Kranthi Bharat
 */
public class Login_history_Action extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Login_history_Form lhf = (Login_history_Form) form;
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        String uname = beans.MyUtil.filterBad("" + session.getAttribute("userName"));
        String logindatetime1 = beans.MyUtil.filterBad(lhf.getLogindatetime());
        String logoutdatetime1 = beans.MyUtil.filterBad(lhf.getLogoutdatetime());
        String logindatetime = beans.MyUtil.filterBad(DBC.MyUtil.ChDate1(logindatetime1));
        String logoutdatetime = beans.MyUtil.filterBad(DBC.MyUtil.ChDate1(logoutdatetime1));
        String generate_reports = beans.MyUtil.filterBad(lhf.getGenerate_reports());
        String link = beans.MyUtil.filterBad(lhf.getLink());

        Connection con = null; 
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            String qry;
            con = DBC.DBConnection.getConnection(request);
            
            if (generate_reports != null) {
                qry = "Select * from login_history where uname=? and logindatetime between ? and ?";
            preparedStatement = con.prepareStatement(qry);
            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, logindatetime);
            preparedStatement.setString(3, logoutdatetime);
            } else {
                qry = "Select * from login_history where logindatetime between ? and ?";
                preparedStatement = con.prepareStatement(qry);
                preparedStatement.setString(1, logindatetime);
                preparedStatement.setString(2, logoutdatetime);
            } 
            rs = preparedStatement.executeQuery();
            
            ArrayList list = new ArrayList();
            while (rs.next()) {
                Login_history_Form l = new Login_history_Form();

                l.setUname(rs.getString(1));
                l.setLogindatetime(rs.getString(2));
                l.setLogoutdatetime(rs.getString(3));
                l.setIpaddress(rs.getString(4));
                l.setSessionid(rs.getString(5));
                l.setSlno(rs.getString(6));
                list.add(l);
            }

            request.setAttribute("list", list);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
        } catch (Exception ex) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception es) {
            }
        }

        return mapping.findForward(SUCCESS);

    }
}
