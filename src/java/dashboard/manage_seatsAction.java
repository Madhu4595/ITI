 
package dashboard;

import DBC.RoleBasedOperations;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
 
public class manage_seatsAction extends org.apache.struts.action.Action {

   
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
       
        ActionForward af = new ActionForward();
        
        String strength_tot = request.getParameter("totstrength");//from ititrade table strength

        String trade_code = request.getParameter("trade_code");

        
        HttpSession session = request.getSession(true);
        session.setAttribute("strength_tot", strength_tot);
        String iti_code = beans.MyUtil.filterBad(""+session.getAttribute("ins_code"));
        session.setAttribute("trade_code", trade_code);
        String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));
        String returnpath = "";
        
        Connection con = DBC.DBConnection.getConnection(request);
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "seatmatrix_phase", year, "current", "true", con);
        con.close();
        if (role_id.equals("4")) {

            String sql = "select s.category_code, s.strength,s.strength_vacant,s.strength_fill,c.category_name from  (select iti_code,trade_code,skeys(strength) as category_code,svals(strength) as strength,svals(strength_vacant) as strength_vacant,svals(strength_fill) as strength_fill from iti_seatmatrix i where iti_code='" + iti_code + "' and trade_code='" + trade_code + "' and year='" + year + "' and phase='" + phase + "') s inner join category_mast c on s.category_code =c.category_code";
            String sql2 = "SELECT a.category_code FROM category_mast a WHERE a.category_code NOT IN ( SELECT category_code FROM iti_seatmatrix WHERE iti_code='" + iti_code + "' and trade_code='" + trade_code + "')";
            returnpath = RoleBasedOperations.ManageSeats_Interface(sql, request, iti_code, trade_code, sql2);
        } else if (role_id.equals("3")) {
            String iti_code_ses = request.getParameter("iti_code");
            session.setAttribute("iti_code_ses", iti_code_ses);
            String sql2 = "SELECT a.category_code FROM category_mast a WHERE a.category_code NOT IN ( SELECT category_code FROM iti_seatmatrix WHERE iti_code='" + iti_code_ses + "' and trade_code='" + trade_code + "')";
            String sql = "select category_code,strength,strength_vacant,strength_fill,category_code as category_name,admitted from view_seatmatrix_admitted where iti_code='" + iti_code_ses + "' and trade_code='" + trade_code + "' and year='" + year + "' ";
            returnpath = RoleBasedOperations.ManageSeats_Interface(sql, request, iti_code, trade_code, sql2);

        }
        return mapping.findForward(returnpath);
    }
}
