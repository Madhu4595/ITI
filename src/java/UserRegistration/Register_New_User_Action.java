/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserRegistration;

import java.net.InetAddress;
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
 * @author Kranthi Bharat
 */
public class Register_New_User_Action extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
         
        Register_New_User_Form rnuf = (Register_New_User_Form) form;
 
        String username = beans.MyUtil.filterBad(rnuf.getUser_name());
        String password = beans.MyUtil.filterBad(rnuf.getChoose_pwd());
        String from_time = beans.MyUtil.filterBad(rnuf.getFm_time());
        String to_time = beans.MyUtil.filterBad(rnuf.getTo_time());
        String desig_code = beans.MyUtil.filterBad(rnuf.getDesig_code());
        String region_code = beans.MyUtil.filterBad(rnuf.getReg_code());
        String u_name = beans.MyUtil.filterBad(rnuf.getU_name());
        String mobile_no = beans.MyUtil.filterBad(rnuf.getMob_no());
        String email_id = beans.MyUtil.filterBad(rnuf.getEmail());
        String ins_code = beans.MyUtil.filterBad(rnuf.getIns_code());
        String roleid = beans.MyUtil.filterBad(rnuf.getRole_id());
        String addroleid = beans.MyUtil.filterBad(rnuf.getAddrole_id());
        String iti_colg = beans.MyUtil.filterBad(rnuf.getIti_colg());
        String dist_code = beans.MyUtil.filterBad(rnuf.getDist_code());
        String empid = beans.MyUtil.filterBad(rnuf.getEmpid());
        String tablename = "";
        int k = 0;
        
        Connection con = null;
        PreparedStatement ps = null;
        
        try {

            if (roleid.equals("1")) {
                ins_code = "1";
                tablename = "role_mast";
            } else if (roleid.equals("2")) {
                tablename = "role_mast";
                ins_code = "2";
            } else if (roleid.equals("3")) {
                tablename = "conviner_login";
                ins_code = dist_code;
            } else if (roleid.equals("4")) {
                tablename = "iti";
                ins_code = iti_colg;
            } else if (roleid.equals("5")) {
                tablename = "regional_dd_mast";
                ins_code = region_code;
            } else if (roleid.equals("6")) {
                tablename = "role_mast";
                ins_code = dist_code;
            } else if (roleid.equals("7")) {
                tablename = "iti";
                ins_code = iti_colg;
            }

            request.setAttribute("login_username_registered", username);
            
            con = DBC.DBConnection.getConnection(request);
            String query = "insert into login_users(username,password,pwd_edit,from_time,to_time,desig_code,region_code,u_name,mobile_no,email_id,ins_code,roleid,add_roleid,hash_password,empid,tablename) values(?,'','',?,?,?,?,?,?,?,?,?,?,md5(md5(?)),?,?)";
            System.out.println("Register_New_User_Action : " + query);
            
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, from_time);
            ps.setString(3, to_time);
            ps.setString(4, desig_code);
            ps.setString(5, region_code);
            ps.setString(6, u_name);
            ps.setString(7, mobile_no);
            ps.setString(8, email_id);
            ps.setString(9, ins_code);
            ps.setString(10, roleid);
            ps.setString(11, addroleid);
            ps.setString(12, password);
            ps.setString(13, empid);
            ps.setString(14, tablename);
            k = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Registration fail with" + e);
            request.setAttribute("error", "<font color=red><b>Registration failed " + e + "</b></font>");
            System.out.println(e);
        }
        if (k == 0) {
            request.setAttribute("error", "<font color=red><b>Registration failed</b></font>");
        } else {
            request.setAttribute("error", "<font color=green><b>Registration Success</b></font>");
        }
        return mapping.findForward(SUCCESS);
    }
}
