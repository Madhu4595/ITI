                                                                                                                                                                     /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard;

import DBC.RoleBasedOperations;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Gopi Daggumalle
 */
public class DashboardAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("im inside the prgrm");
        HttpSession session = request.getSession(true);

        String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));
        String returnpath = "";
        System.out.println("Role Id is : " + role_id);
        if (role_id.equals("2") || role_id.equals("9")) {
            String sql = "select * from dist_mst where statecode='28'";
            returnpath = RoleBasedOperations.DashBoard_page(sql, request, "");
        } /*
         else if (role_id.equals("4")) {
         // String sidc = (String) session.getAttribute("sidc");
         String iti_code = (String) session.getAttribute("ins_code");

         String sql = "select distinct iti_code from trade_sel where iti_code='"+iti_code+"'";
         ////System.out.println(sql);
         returnpath = RoleBasedOperations.DashBoard_Showitisforiti(sql, request, iti_code);
         }*/ else if (role_id.equals("5")) {
            //String sidc = (String) session.getAttribute("sidc");
            String iti_code = (String) session.getAttribute("ins_code");

            String sql = "select * from conviner_login where region_code='" + iti_code + "'";
            System.out.println(sql);
            // returnpath=RoleBasedOperations.DashBoard_ShowitisDistwise(sql, request, iti_code);
            returnpath = RoleBasedOperations.DashBoard_page(sql, request, "");
        } else if (role_id.equals("3")) {
            //String sidc = (String) session.getAttribute("sidc");
            String iti_code = (String) session.getAttribute("ins_code");

            String sql = "select * from dist_mst where dist_code='" + iti_code + "' and statecode='28'";
            System.out.println(sql);
            // returnpath=RoleBasedOperations.DashBoard_ShowitisDistwise(sql, request, iti_code);
            returnpath = RoleBasedOperations.DashBoard_page(sql, request, "");
        } else if (role_id.equals("4")) {
            //String sidc = (String) session.getAttribute("sidc");
            String iti_code = (String) session.getAttribute("ins_code");

            String sql = "select * from iti where iti_code='" + iti_code + "'";
            //System.out.println(sql);
            // returnpath=RoleBasedOperations.DashBoard_ShowitisDistwise(sql, request, iti_code);
            returnpath = RoleBasedOperations.DashBoard_Showitis(sql, request, iti_code);
            //System.out.println("checking is "+returnpath);
            return mapping.findForward("itisuccess");
        }

        return mapping.findForward(returnpath);
    }
}
