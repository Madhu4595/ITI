package Master.Trade;

import DBC.RoleBasedOperations;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
public class addnew_tradeAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(true);

        //String sidc = (String) session.getAttribute("sidc");
        String iti_code = (String) session.getAttribute("ins_code");
        String role_id = "" + session.getAttribute("role_id");
        String returnpath = "";
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        String iti_code_gen = (String) session.getAttribute("iti_code_gen");
        String sql1="select conv_approval from ititrade where iti_code = '"+iti_code_gen+"' and conv_approval='1'";
        System.out.println("hi.............."+sql1);
        
        ResultSet rs = st.executeQuery(sql1);
        if(rs.next()){
            System.out.println("Hello"+sql1);
           return mapping.findForward("derror"); 
        }
        
        if (role_id.equals("4")) {
            String sql = "SELECT a.trade_code,a.trade_name FROM ititrade_master a WHERE a.trade_code NOT IN ( SELECT trade_code FROM ititrade WHERE iti_code='" + iti_code + "')";
            returnpath = RoleBasedOperations.AddTradeCodes(sql, request, iti_code);
        } else if (role_id.equals("3")) {
            
            String sql = "SELECT a.trade_code,a.trade_name FROM ititrade_master a WHERE a.trade_code NOT IN ( SELECT trade_code FROM ititrade WHERE iti_code='" + iti_code_gen + "')";
            returnpath = RoleBasedOperations.AddTradeCodes(sql, request, iti_code_gen);
        }
        return mapping.findForward(returnpath);
    }
}
