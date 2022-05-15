package applicationForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class Adhar_Check extends org.apache.struts.action.Action {

    private String redirect;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("flkjgdfkljgkfjgdf");

        HttpSession session = request.getSession(true);

        String stored_token = beans.MyUtil.filterBad("" + session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));

        String id = stored_token.substring(10, 20) + request.getSession().getId() + stored_token.substring(2, 6);
        if (!token.equals(id)) { return mapping.findForward("failure"); }

        open_Application_InterfaceForm formvariable = (open_Application_InterfaceForm) form;

        String adar = "";
        redirect = "success";

        try {
            adar = beans.MyUtil.filterBad(formvariable.getAdar());

            if (adar == null) {
                request.setAttribute("checkservice", "adar_check");
                request.setAttribute("i", "invalid");
                redirect = "success";
            }

            int checkfacilitate = DBC.MyUtil.DuplicateCheck("student_application", "(adarno) ='" + adar.trim() + "'", "");
            if (checkfacilitate > 0) {
                request.setAttribute("checkservice", "adar_check");
                request.setAttribute("i", "already Exist");
                redirect = "success";
            } else {
                request.setAttribute("checkservice", "adar_check");
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
