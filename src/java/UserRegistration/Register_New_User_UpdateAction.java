/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserRegistration;

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

/**
 *
 * @author Kranthi Bharat
 */
public class Register_New_User_UpdateAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String u_name = request.getParameter("u_name");
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBC.DBConnection.getConnection(request);
            String query = "select * from login_users where username='" + u_name + "'";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            ArrayList list = new ArrayList();
            while (rs.next()) {

                Register_New_User_UpdateForm fm = new Register_New_User_UpdateForm();
                fm.setSlno(rs.getString(14));
                fm.setU_name(rs.getString(9));
                fm.setFrom_time(rs.getString(4));
                fm.setTo_time(rs.getString(5));
                fm.setIp_address(rs.getString(6));
                fm.setDesig_code(rs.getString(7));
                fm.setRegion_code(rs.getString(8));
                fm.setDist_code(rs.getString(17));
                fm.setIti_colg(rs.getString(15));
                fm.setMobile_no(rs.getString(10));
                fm.setEmail_id(rs.getString(11));
                fm.setRoleid(rs.getString(13));

                list.add(fm);

            }
            HttpSession session = request.getSession();
            session.setAttribute("list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapping.findForward(SUCCESS);

    }
}
