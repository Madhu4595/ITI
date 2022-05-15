/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserRegistration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Kranthi Bharat
 */
public class Register_New_User_UpdateSaveActions extends org.apache.struts.action.Action {

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
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        

        Register_New_User_UpdatedSaveForm update = (Register_New_User_UpdatedSaveForm) form;
        String slno = update.getSno();
        String u_name = update.getUser_name();
        String from_time = update.getFromtime();
        String to_time = update.getTotime();
        String ip_address = update.getIpaddress();
        String desig_code = update.getDesigcode();
        String region_code = update.getRegioncode();
        String dist_code = update.getDistcode();
        String iti_colg = update.getIticolg();
        String mobile_no = update.getMobileno();
        String email_id = update.getEmailid();
        String roleid = update.getRole_id();
        System.out.println("dist_code and iti_college" + dist_code + "'" + iti_colg);
        Connection con = null;
        PreparedStatement ps = null;
        try {
            System.out.println(":the details" + slno + "," + u_name + "," + from_time + "," + to_time + "," + ip_address + "," + desig_code + "," + region_code + "," + mobile_no + "," + email_id + "," + roleid);
            con = DBC.DBConnection.getConnection(request);
            String query = "update login_users set u_name='" + u_name + "',from_time='" + from_time + "',to_time='" + to_time + "',ip_address='" + ip_address + "',desig_code='" + desig_code + "',region_code='" + region_code + "',mobile_no='" + mobile_no + "',email_id='" + email_id + "',roleid='" + roleid + "' where slno='" + slno + "';";
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapping.findForward(SUCCESS);
    }
}
