/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Ramesh Naidu
 */
public class Verify_Vofficer_Action extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        Verify_Vofficer_Form vvf = (Verify_Vofficer_Form) form;
        String vid = beans.MyUtil.filterBad(vvf.getVid());
        String pwd = beans.MyUtil.filterBad(vvf.getPwd());

        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
	
        String iti_code =  beans.MyUtil.filterBad(""+session.getAttribute("ins_code"));
        
        DBC.generic g = new DBC.generic();
        if (g.checkLogin("login_users", vid, pwd, iti_code, request)) {
            session.setAttribute("verificationofficer_id", vid);
            return mapping.findForward("success");
        } else {
            return mapping.findForward("failure");
        }

    }
}
