package Reports.Verification;

import DBC.RoleBasedOperations;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class freeze_conformAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
       
        HttpSession session = request.getSession(true);
        
        String iti_code = beans.MyUtil.filterBad(""+session.getAttribute("ins_code"));
        String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));
        
        String returnpath = "";
        String sql = "";
        
        if (role_id.equals("3")) {
            sql = "update trade_sel set freezee=1 where dist_code='" + iti_code + "'";
        } else if (role_id.equals("4")) {
            sql = "update trade_sel set freezee=1 where iti_code='" + iti_code + "' and phase->'1'=true";
        }
        
        returnpath = RoleBasedOperations.setFreeze_itiCode_verification(sql, request, iti_code);
        System.out.println(sql+" "+returnpath);
        return mapping.findForward(SUCCESS);
    }
}
