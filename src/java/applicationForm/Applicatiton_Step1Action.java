/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import DBC.generic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Admin
 */
public class Applicatiton_Step1Action extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
   

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
 System.out.println("cursor action");
        String return_to = "exists";
        Application_Step1_form apf = (Application_Step1_form) form;
        String name = apf.getName();
        String dob = apf.getDob();
        //  String phno = apf.getPhno();
        String fname = apf.getFname();
        String type = apf.getSsc_type();
System.out.println("cursor action222"+fname);
        String regid = generic.ifApplicantExists(name, dob, fname);
        System.out.println("cursor action222"+regid);
        boolean exists = false;
        
        //exists = (regid.equals(null))?false:true;
        try {
            if (!regid.isEmpty()) {
                exists = true;
            }
        } catch (NullPointerException e) {
            exists = false;
        }
       
        HttpSession ses = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        if (!exists) {
            return_to = "new_applicant";
            ses.setAttribute("name", name);
            ses.setAttribute("dob", dob);
            ses.setAttribute("fname", fname);
            ses.setAttribute("ssc_type", type);
        } else {
            ses.setAttribute("regid", regid);
        }
        return mapping.findForward(return_to);
    }
}
