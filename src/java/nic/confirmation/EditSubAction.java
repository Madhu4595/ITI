package nic.confirmation;

import DBC.Validations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EditSubAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String trade_code = beans.MyUtil.filterBad(request.getParameter("trade_code"));
        
        if (Validations.Validate(trade_code, 2)) {
            
            session.setAttribute("trade", trade_code);
            System.out.println("the trade_code value is***********************:" + trade_code);
            
            try {
                con = DBC.DBConnection.getConnection(request);
                
                String sql = "select trade_short,trade_name,display_order from ititrade_master where trade_code=?";
                System.out.println("the query is:" + "select trade_code,trade_short,trade_name from ititrade_master where trade_code=" + trade_code + "");
                
                ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(trade_code));
               rs = ps.executeQuery();
                
                ArrayList list = new ArrayList();
                while (rs.next()) {
                    AddingForm as = new AddingForm();
                    as.setTrade_short(rs.getString(1));
                    as.setTrade_name(rs.getString(2));
                    as.setShort_order(rs.getString(3));
                    list.add(as);
                }
                System.out.println("the arraylist size is:" + list.size());
                session.setAttribute("list", list);

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
            return mapping.findForward("edit");
        }
        return mapping.findForward("failure");
    }
}
