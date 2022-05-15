/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.confirmation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

 
public class TradeUpdateAction extends org.apache.struts.action.Action {

    
    private static final String SUCCESS = "success";

     
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
       
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        TradeUpdateForm tdf = (TradeUpdateForm) form;
        
        String trade_name = beans.MyUtil.filterBad(tdf.getTrade_name());
        String short_order = beans.MyUtil.filterBad(tdf.getShort_order());
        
        String trade_code =  beans.MyUtil.filterBad(""+session.getAttribute("trade"));

        System.out.println("the trade_code value is:" + trade_code);
        System.out.println("the trade_name value is:" + trade_name);
        
        try {
            con = DBC.DBConnection.getConnection(request);
            
            String sql = "update ititrade_master set trade_name=?,display_order = ? where trade_code=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, trade_name);
            ps.setInt(2, Integer.parseInt(short_order));
            ps.setInt(3, Integer.parseInt(trade_code));
            System.out.println("update query--------->"+ps);
            ps.executeUpdate();
            
           
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
 return mapping.findForward("update");
    }
}
