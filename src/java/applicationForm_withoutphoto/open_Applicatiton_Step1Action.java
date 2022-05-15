/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm_withoutphoto;

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
public class open_Applicatiton_Step1Action extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String return_to = "exists";
        open_Application_Step1_form apf = (open_Application_Step1_form) form;
        String name = apf.getName();
        String dob = apf.getDob();
        //  String phno = apf.getPhno();
        String fname = apf.getFname();
        String type = apf.getSsc_type();
        String dist=apf.getDist();

        String regid = generic.ifApplicantExists(name, dob, fname, request);
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
        if (!exists) {
            return_to = "new_applicant";
            ses.setAttribute("name", name);
            ses.setAttribute("dob", dob);
            ses.setAttribute("fname", fname);
            ses.setAttribute("ssc_type", type);
            ses.setAttribute("dist", dist);
        } else {
            ses.setAttribute("regid", regid);
        }
        return mapping.findForward(return_to);
    }
}
