package applicationForm_withoutphoto;

import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PrintApplicationAction extends org.apache.struts.action.Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintApplicationForm formvariable = (PrintApplicationForm) form;
        String regid = formvariable.getRegid();
        Connection con = DBC.DBConnection.getConnection(request);
        HttpSession ses = request.getSession();
        ses.setAttribute("report", DBC.generic.printApplication(con, Integer.parseInt(regid), "imageRetrival"));
        System.out.println("hello");
        if (con != null) {
            con.close();
            con = null;
        }
        return mapping.findForward("success");
    }
}
