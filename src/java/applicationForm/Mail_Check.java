/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author DAD GIFTED
 */
public class Mail_Check extends org.apache.struts.action.Action {

    private String redirect;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.print("flkjgdfkljgkfjgdf");
        
        open_Application_InterfaceForm formvariable = (open_Application_InterfaceForm) form;
        
        HttpSession session = request.getSession(true);

        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        String email_id = "";
        redirect = "success";
        try {
            email_id = beans.MyUtil.filterBad(formvariable.getEmail_id());

            System.out.print("flkjgdfkljgkfjgdf" + email_id);
            if (email_id == null) {

                request.setAttribute("checkservice", "mail_check");
                request.setAttribute("i", "invalid");
                redirect = "success";

            }

            int checkfacilitate = DBC.MyUtil.DuplicateCheck("student_application", "trim(email) like'" + email_id.trim() + "'", "");
            if (checkfacilitate > 0) {

                request.setAttribute("checkservice", "mail_check");
                request.setAttribute("i", "already Exist");
                redirect = "success";

            } else {

                request.setAttribute("checkservice", "mail_check");
                request.setAttribute("i", "available");
                redirect = "success";
            }
        } catch (Exception ex) {
            System.out.println("exception is--->" + ex);
            ex.printStackTrace();

        }
        return mapping.findForward(redirect);
    }

}
