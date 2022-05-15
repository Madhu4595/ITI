package Reports.Verification;

import DBC.RoleBasedOperations;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

 
public class verify_report_interfaceAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        verify_report_interfaceForm lf = (verify_report_interfaceForm) form;
        
        String from = beans.MyUtil.filterBad(lf.getFrom());
        String to = beans.MyUtil.filterBad(lf.getTo());
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        String iti_code = beans.MyUtil.filterBad(""+session.getAttribute("ins_code"));
        String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));

        String returnpath = "";

        String sql = "";
        System.out.println("Role Id is "+role_id);
        if (role_id.equals("3")) {
            returnpath = RoleBasedOperations.ShowApplicants_Report(from, request, iti_code, to);
        } else if (role_id.equals("4")) {
            returnpath = RoleBasedOperations.ShowApplicants_Report(from, request, iti_code, to);
        }
        return mapping.findForward(returnpath);

    }
}
