/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.confirmation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

 
public class ConfirmationAction extends org.apache.struts.action.Action {

     
    private static final String SUCCESS = "success";

    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        HttpSession session = request.getSession(true);
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
ArrayList list = new ArrayList();
        try {

            String code = "" + session.getAttribute("ins_code");
            System.out.println("the registration id:++++++++++++++++++++++++++++" + code);
            con = DBC.DBConnection.getConnection(request);
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT regid from trade_sel where dist_code='" + code + "' and verified=false and verified_qual=false order by regid asc");
            

            while (rs.next()) {
                
                ConfirmationFormBean confirm = new ConfirmationFormBean();
                confirm.setReg_id(rs.getInt(1));

                list.add(confirm);

            }
            request.setAttribute("list", list);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }

        }

        return mapping.findForward(SUCCESS);
    }
}
