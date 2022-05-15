/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.confirmation;

import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author theru hareesh
 */
public class RejectAction extends org.apache.struts.action.Action {

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
        HttpSession session = request.getSession();
        String reg_id = request.getParameter("reg_id");
        System.out.println("the values are:***********************" + reg_id);
        Connection con = null;
        Statement stmt = null;
        try {
            String code = "" + session.getAttribute("ins_code");
            System.out.println("the registration id:++***++++++++++++++++++++++++++" + code);

            con = DBC.DBConnection.getConnection(request);
            stmt = con.createStatement();
            String query = "update trade_sel set reject=true where regid=" + reg_id + " and dist_code='" + code + "'";
            System.out.println("the query is:" + query);
            stmt.executeUpdate("update trade_sel set reject=true where regid=" + reg_id + " and dist_code='" + code + "'");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
