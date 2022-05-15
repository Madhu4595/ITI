/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Admin
 */
public class tradesel_unfreeze_action extends org.apache.struts.action.Action {

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

        tradesel_unfreeze_form f = (tradesel_unfreeze_form) form;
        String type_freeze = f.getType_freeze();
        String dist_code = f.getDist_code();
        String iti_code = f.getIti_code();
        String field_name = null;
        String field_value = null;
        if (type_freeze.equalsIgnoreCase("iti")) {
            field_name = "iti_code";
            field_value = iti_code;
        } else {
            field_name = "dist_code";
            field_value = dist_code;
        }
        Connection con = DBC.DBConnection.getConnection(request);
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, con);
        String sql = "update trade_sel set freezee=0 where " + field_name + "='" + field_value + "' and year='" + year + "' and phase->'" + phase + "'='true'";
        ////System.out.println("query one " + sql);

        Statement ps = null;
        //con.setAutoCommit(false);
        //boolean commit = true;
        try {
            ps = con.createStatement();
            //ps.executeUpdate(sql);
            sql = "delete from ranks where " + field_name + "='" + field_value + "' ";
            //System.out.println("query two " + sql);
            ps.executeUpdate(sql);
            request.getSession().setAttribute("action", "successful");
        } catch (Exception ex) {
            //commit = false;
            request.getSession().setAttribute("action", "failure");
            System.out.println("error in tradesel unfreeze" + ex);
        } finally {
            /*if (commit) {
                con.commit();
            } else {
                con.rollback();
            }*/
            ps.close();
            con.close();
        }
        return mapping.findForward(SUCCESS);
    }
}
