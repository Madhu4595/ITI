package login;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import javax.servlet.http.HttpSession;
import DBC.RoleBasedOperations;
import beans.MyUtil;
import org.apache.commons.codec.digest.DigestUtils;
import DBC.DBConnection;
import java.sql.PreparedStatement;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

public class LoginAction extends Action
{
    private static final String SUCCESS = "success";
    
    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        System.out.println("U R IN -> LoginAction.java");
         
        HttpSession session = request.getSession(true);
        System.out.println("SESSION ID from loginaction.java-> "+session.getId());
        
        
        final LoginForm lf = (LoginForm)form;
        final String uname = beans.MyUtil.filterBad(lf.getUname());
       
        session.setAttribute("uname", (Object)uname);
        System.out.println("Sesssion for User_Name"+uname);
        
        String pwd = beans.MyUtil.filterBad(lf.getPwd());
         
        final Connection con = DBConnection.getConnectionGen();
        final Statement st = con.createStatement();
        PreparedStatement ps = null;
        
        String pwd_change = null;
        ResultSet rs1 = null;
       
        try {
    
            final String main = "select * from login_users where userName=?";
            ps = con.prepareStatement(main);
            ps.setString(1, uname);
            rs1 = ps.executeQuery();
             
            if (rs1.next()) {
                
                final String status = rs1.getString("status");
                String password_temp = rs1.getString("hash_password");
                
                String hashedsalt = DigestUtils.md5Hex(session.getAttribute("salt").toString().replaceAll(" ", "").replaceAll("\r", ""));
                pwd = pwd.replaceAll(" ", "").replaceAll("\r", "");
                hashedsalt = hashedsalt.replaceAll(" ", "").replaceAll("\r", "");
                password_temp = password_temp.replaceAll(" ", "").replaceAll("\r", "");
                password_temp = DigestUtils.md5Hex(hashedsalt + password_temp);
                pwd = DigestUtils.md5Hex(pwd);
                
                session.invalidate();
                session=request.getSession();
                
                 
                if (pwd.equals(password_temp)) {
                    System.err.println("in password");
                    session.setAttribute("userName", (Object)rs1.getString("username"));
                    session.setAttribute("u_name", (Object)rs1.getString("u_name"));
                    session.setAttribute("role_id", (Object)rs1.getInt("roleid"));
                    session.setAttribute("roleaudid", (Object)rs1.getInt("roleid"));
                    
                    final String tablename = rs1.getString("tablename");
                    final String ins_code = rs1.getString("ins_code");
                    session.setAttribute("ins_code", (Object)ins_code);
                    
                    final String code_code = MyUtil.getOptionValue("role_mast", "role_id", "code_code", "" + rs1.getString("roleid"), con);
                    final String code_name = MyUtil.getOptionValue("role_mast", "role_id", "code_name", "" + rs1.getString("roleid"), con);
                    final String labelcode = MyUtil.getOptionValue("role_mast", "role_id", "code", rs1.getString("roleid"), con);
                    
                    session.setAttribute("labelcode", (Object)labelcode);
                    session.setAttribute("labelname", (Object)MyUtil.getOptionValue("role_mast", "role_id", "name", rs1.getString("roleid"), con));
                    
                    final String iti_name = MyUtil.getOptionValue(tablename, code_code, code_name, ins_code, con);
                    session.setAttribute("iti_name", (Object)iti_name);
                    pwd_change=rs1.getString("pwd_change");
                   
                    try {
                        RoleBasedOperations.login_history_insert(uname, request);
                        if(pwd_change.equals("N")){ return mapping.findForward("pwd_change"); }
                    }
                    catch (Exception ex2) {ex2.printStackTrace();}
                    
                    try { rs1.close(); st.close(); con.close(); }
                    catch (Exception ex3) { System.out.println("EXCEPTION FROM LoginAction.java for RoleBasedOperations");}
                    
                    finally {
                        try { 
                            if (rs1 != null) { rs1.close(); }
                            if (st != null) { st.close(); }
                            if (con != null) {  con.close(); }
                        }
                        catch (Exception ex4) {System.out.println("Exception for rolebased operations");}
                    }
                    return mapping.findForward("success");
                }
                 
                
            }
        
        
            
        } //try 
        catch (Exception ex) {
            ex.printStackTrace();
            return mapping.findForward("derror");
        }
        finally {
            try {
                if (rs1 != null) { rs1.close(); }
                if (ps != null) {  ps.close(); }
                if (con != null) {  con.close(); }
            }
            catch (Exception ex5) {}
        }
       
        return mapping.findForward("failure");
        
        
    }
}
