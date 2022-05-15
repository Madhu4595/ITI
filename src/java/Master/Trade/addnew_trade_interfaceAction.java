/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class addnew_trade_interfaceAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        addnew_trade_interfaceForm lf = (addnew_trade_interfaceForm) form;
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }

        //String sidc = (String) session.getAttribute("sidc");
        
        String iti_code = (String) session.getAttribute("ins_code");
        String trade_code = lf.getTrade_code();
        String strength = lf.getStrength();
        int strength_fill=0;
        int strength_vacant=Integer.parseInt(strength);
        String available = lf.getAvailable(), trade_short = null;
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        String year=beans.MyUtil.getOptionValue("iti_params", "code", "value", "7",con);
        int phase=Integer.parseInt(beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, con));
        String role_id = "" + session.getAttribute("role_id");
        Statement st = con.createStatement();
        ResultSet rs = null;
        String sql1 = "select * from ititrade_master WHERE trade_code='" + trade_code + "'";
        rs = st.executeQuery(sql1);
        while (rs.next()) {
            trade_short = rs.getString("trade_short");
        }

        con.close();
        st.close();
        rs.close();

        String returnpath = "";
        if (role_id.equals("4")) {
            String sql = "insert into ititrade(iti_code,trade_code,trade_short,strength,strength_fill,strength_vacant,available_for_year,trade_freeze,conv_approval) values('" + iti_code + "','" + trade_code + "','" + trade_short + "', '" + strength + "',"+strength_fill+","+strength_vacant+",'" + available + "','" + 0 + "','" + 0 + "')";
            returnpath = RoleBasedOperations.AddnewTrade_success(sql, request);
        } else if (role_id.equals("3")) {
            String iti_code_gen = (String) session.getAttribute("iti_code_gen");
            String sql = "insert into ititrade(iti_code,trade_code,trade_short,strength,strength_fill,strength_vacant,available_for_year,trade_freeze,conv_approval) values('" + iti_code_gen + "','" + trade_code + "','" + trade_short + "', '" + strength + "',"+strength_fill+","+strength_vacant+",'" + available + "','" + 1 + "','" + 0 + "')";
            //System.out.println(sql);
            returnpath = RoleBasedOperations.AddnewTrade_success(sql, request);
        }
        return mapping.findForward(returnpath);
    }
}
