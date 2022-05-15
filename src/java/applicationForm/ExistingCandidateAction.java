/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExistingCandidateAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

     
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        ExistingCandidateForm ex = (ExistingCandidateForm) form;
        
        String regid = beans.MyUtil.filterBad(ex.getRegid());
        
        HttpSession ses = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id = stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        String roleid = beans.MyUtil.filterBad("" + ses.getAttribute("role_id"));
        String ins_code = beans.MyUtil.filterBad("" + ses.getAttribute("ins_code"));
        if (roleid.equals("6")) {
            ins_code = ins_code.substring(0, 2);
        }
        Connection con = DBC.DBConnection.getConnection(request);
        String return_page = "";
        Statement st = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        String type = null;
                    String fieldname = "";

        try {
            st = con.createStatement();
            
            if (roleid.equals("2")) { type = "dist"; fieldname = "dist_code"; }
            if (roleid.equals("4")) {  type = "iti"; fieldname = "iti_code"; }
            
            String insert_table = "trade_sel";
            String val = "";
            
            val = beans.MyUtil.getOptionValue("trade_sel", "regid", "regid", regid, "temp_code", ins_code, con);
            
            HttpSession session = request.getSession(true);
            
            int trno = DBC.generic.logTrace("" + session.getAttribute("userName"), "trade_sel", "update/ins", request);

            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);

            String sql = "insert into trade_sel(regid," + fieldname + ",temp_code,trno,phase,year) values(?,?,?,?,hstore(array['" + phase + "'],array['true']),?)";
            ps1 = con.prepareStatement(sql);
            ps1.setInt(1, Integer.parseInt(regid));
            ps1.setString(2, ins_code);
            ps1.setString(3, ins_code);
            ps1.setInt(4, trno);
            ps1.setString(5, year);
            String sql1 = "update trade_sel set trno=? where regid=? and " + fieldname + "=?";
            ps2 = con.prepareStatement(sql1);
             ps1.setInt(1, trno);
             ps1.setInt(2, Integer.parseInt(regid));
             ps1.setString(3, ins_code);
            System.out.println("valis " + val);
            if (val == null || val.equals("")) {
                ps1.executeUpdate();
            } else {
                ps2.executeUpdate();
            }

            request.setAttribute("success", "<font color=green>Registration Success</font>");
        } catch (Exception x) {
            request.setAttribute("success", "<font color=red>Registration Fail</font>");
        } finally {
            ps1.close();
            ps2.close();
            con.close();
        }
        return mapping.findForward("success");
    }
}
