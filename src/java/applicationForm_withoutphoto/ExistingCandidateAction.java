/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm_withoutphoto;

import java.sql.Connection;
import java.sql.Statement;
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
public class ExistingCandidateAction extends org.apache.struts.action.Action {

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
        ExistingCandidateForm ex = (ExistingCandidateForm) form;
        String regid = ex.getRegid();
        //System.out.println("regid is "+regid);
        HttpSession ses = request.getSession();
        String roleid = "" + ses.getAttribute("role_id");
        String ins_code = "" + ses.getAttribute("ins_code");
        if (roleid.equals("6")) {
            ins_code = ins_code.substring(0, 2);
        }
        Connection con = DBC.DBConnection.getConnection(request);
        String return_page = "";
        Statement st = null;
        try {
            st = con.createStatement();
            String type = "dist";
            String insert_table = "trade_sel";
            String fieldname = "";
            //System.out.println("type is " + type);
            if (type.equals("iti")) {
                fieldname = "iti_code";
            } else {
                fieldname = "dist_code";
            }
            //String sql2 = "update application set phase2e=true where regid='" + regid + "'";
            //System.out.println(sql2);
            //st.executeUpdate(sql2);
            String val = "";
            val = beans.MyUtil.getOptionValue("trade_sel", "regid", "regid", regid, "temp_code", ins_code, con);
            HttpSession session = request.getSession(true);
            int trno = DBC.generic.logTrace("" + session.getAttribute("userName"), "trade_sel", "update/ins", request);

            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);

            String sql = "insert into trade_sel(regid," + fieldname + ",temp_code,trno,phase,year) values(" + regid + ",'" + ins_code + "','" + ins_code + "','" + trno + "',hstore(array['" + phase + "'],array['true']),'" + year + "')";
            String sql1 = "update trade_sel set trno='" + trno + "' where regid='" + regid + "' and " + fieldname + "='" + ins_code + "' ";

            //System.out.println("valis "+val);
            if (val == null || "".equals(val)) {
                //System.out.println("inserting"+sql);
                st.executeUpdate(sql);
            } else {
                //System.out.println("updating"+sql1);
                st.executeUpdate(sql1);
            }

            request.setAttribute("success", "<font color=green>Registration Success</font>");
        } catch (Exception x) {
            request.setAttribute("success", "<font color=red>Registration Fail</font>");
        } finally {
            st.close();
            con.close();
        }
        return mapping.findForward("success");
    }
}
