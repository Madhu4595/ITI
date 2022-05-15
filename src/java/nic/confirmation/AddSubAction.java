package nic.confirmation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddSubAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        System.out.println("**now i am in report of Addsubaction class**");

        HttpSession session = request.getSession();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {

            con = DBC.DBConnection.getConnection(request);
            String sql = "select trade_code,trade_short,trade_name from ititrade_master order by trade_code asc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ArrayList list = new ArrayList();
            while (rs.next()) {
                AddSubForm as = new AddSubForm();
                as.setTrade_code(rs.getInt(1));
                as.setTrade_short(rs.getString(2));
                as.setTrade_name(rs.getString(3));
                list.add(as);

            }

            session.setAttribute("list", list);

        } 
        catch (Exception e) { e.printStackTrace(); }
        finally {

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
        return mapping.findForward(SUCCESS);
    }
}
