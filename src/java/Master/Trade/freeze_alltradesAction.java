/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Master.Trade;

import DBC.DBConnection;
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
public class freeze_alltradesAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
       
        HttpSession session = request.getSession(true);

        int iti_code = Integer.parseInt(session.getAttribute("ins_code").toString());
        
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);

        try {
            String sql = "update ititrade set trade_freeze=?  WHERE iti_code=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, 1);
            pst.setInt(2, iti_code);
            int s = pst.executeUpdate();

            con.close();
            pst.close();
            if (s > 0) {
                return mapping.findForward(SUCCESS);
            }
        } catch (Exception ex) {
            return mapping.findForward("derror");
        }

        return mapping.findForward("failure");
    }
}
