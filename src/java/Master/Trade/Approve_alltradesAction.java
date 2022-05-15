/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Master.Trade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Gopi Daggumalle
 */
public class Approve_alltradesAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession(true);

        String iti_code_gen = beans.MyUtil.filterBad(""+session.getAttribute("iti_code_gen"));
        
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7",con);
        String phse = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year,"current","true",con);
        
        try {
            String sql = "update ititrade set conv_approval=?,trade_freeze=? WHERE iti_code=?";
            System.out.println("Sql Query is :: "+sql);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, 1);
            pst.setInt(2, 1);
            pst.setString(3, iti_code_gen);
            int s = pst.executeUpdate();
            con.close();
            pst.close();
            if (s > 0) {
                return mapping.findForward(SUCCESS);
            }
        } catch (Exception ex) {
            return mapping.findForward("derror");
        }finally{
            if(con!=null){
                con.close();
            }
        }

        return mapping.findForward("failure");
    }
}
