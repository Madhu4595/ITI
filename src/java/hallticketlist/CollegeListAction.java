/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hallticketlist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CollegeListAction extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        Connection con = null;
        ResultSet rs = null;
        Statement stmt = null;
        String iti_code=null;
        String iti_name=null;
        String code = "" + session.getAttribute("ins_code");
        try {

            con = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "1", con);
            String month = beans.MyUtil.getOptionValue("iti_params", "code", "value", "2", con);
            stmt = con.createStatement();
            String role_id = "" + session.getAttribute("role_id");
            String status_code = beans.MyUtil.getOptionValue("admissions.iti_board", "status", "status_code_dcp_freeze", "y", con);
            status_code = status_code.trim();

          //String query = "select distinct(a.iti_code),b.iti_name from exams.exam_apply_"+year+"_"+month+" a join iti b on a.iti_code=b.iti_code join admissions.iti_admissions c on a.adm_num=c.newadmnum where c.type_admission='A' and a.rdd_approv='1' and b.dist_code=substr('"+code+"',1,2) and a.conv_approv='1'";
            String query = "select a.iti_code,a.iti_name from iti a , status b where a.dist_code='" + code + "' and substring(b.loc_code,1,2)='" + code + "' and b.loc_code=a.iti_code and b.status='" + status_code + "'";
           System.out.println("halltckt" + query);

            if ("4".equals(role_id)) {
                //query = "select a.iti_code,a.iti_name from iti a , status b where a.iti_code='" + code + "' and b.loc_code='" + code + "' and b.loc_code=a.iti_code and b.status='" + status_code + "'";
                query = "select a.iti_code,a.iti_name from iti a  where a.iti_code='" + code + "'";
            } else if ("5".equals(role_id)) {
                query = "select iti_code,iti_name from iti where iti_type='A' and substring(iti_code,1,2) in (select dist_code from conviner_login where region_code='"+code+"')";
            }
            System.out.println(query);
            session.setAttribute("query", query);
            rs = stmt.executeQuery(query);
            java.util.List hlist = new ArrayList();
            while (rs.next()) {
                //iti_code=rs.getString(iti_code);
               // iti_name=rs.getString(iti_name);
                ListHallTicketForm lft = new ListHallTicketForm();
                lft.setIti_code(rs.getString(1));
                lft.setIti_name(rs.getString(2));
                hlist.add(lft);
            }
           session.setAttribute("hlist", hlist);

        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return mapping.findForward("success");
    }
}
