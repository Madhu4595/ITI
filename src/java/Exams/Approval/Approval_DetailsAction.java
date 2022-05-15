/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exams.Approval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Gopi Daggumalle
 */
public class Approval_DetailsAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Approval_DetailsForm fm = (Approval_DetailsForm) form;

        String adm_num = fm.getAdm_num();
        String appornot = fm.getAppornot();
        String remarks = fm.getRemarks();

        Connection con = DBC.DBConnection.getConnection(request);
        PreparedStatement ps = null;
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "1", con);
        String month = beans.MyUtil.getOptionValue("iti_params", "code", "value", "2", con);
        String sql = "update exams.exam_apply_"+year+"_"+month+" set final_approv='" + appornot + "' , remarks='" + remarks + "' where adm_num='" + adm_num + "'";
        ps = con.prepareStatement(sql);
        try {
            int x = ps.executeUpdate();
            ps.close();
            ps.close();
            if (x > 0) {
                return mapping.findForward(SUCCESS);
            }
        } catch (Exception ex) {
        }finally{
            if(con!=null){
                con.close();
            }
        }
        return mapping.findForward("fail");
    }
}
