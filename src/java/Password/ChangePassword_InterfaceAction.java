/*
 * To change this template, choose Tools | Templates 
 * and open the template in the editor.
 */
package Password;

import DBC.RoleBasedOperations;
import DBC.generic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Gopi Daggumalle
 */
public class ChangePassword_InterfaceAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        HttpSession session = request.getSession(true);
        System.out.println("SESSION ChangePassword_Interface");
        
        
        String returnpath = "";
        String newpwd1 = beans.MyUtil.filterBad(request.getParameter("newpwd"));
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
        
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        System.out.println("stored_token--->"+token);
        System.out.println("token--->"+id);
        
        if(!token.equals(id)){ return mapping.findForward("authentication.jsp"); }
        
        ChangePassword_InterfaceForm wd = (ChangePassword_InterfaceForm) form;
        
        String oldpwd = beans.MyUtil.filterBad(wd.getOldpwd());
        String newpwd = beans.MyUtil.filterBad(wd.getNewpwd());
        String uname = (String)session.getAttribute("userName");
        
        newpwd = DigestUtils.md5Hex(DigestUtils.md5Hex(newpwd));
        oldpwd = DigestUtils.md5Hex(DigestUtils.md5Hex(oldpwd));
        int trno = generic.logTrace(session.getAttribute("userName") + "", "login_users", "Update", request);
        String sql = "update login_users set pwd_change='Y', hash_password='" + newpwd + "',trno=" + trno + " where userName='" + uname + "'";
        String qry = "select * from login_users where userName='" + uname + "'";
        returnpath = RoleBasedOperations.ChangePassword(sql, qry, request, oldpwd);
        
        
       
        return mapping.findForward(returnpath);
    }
    
}


