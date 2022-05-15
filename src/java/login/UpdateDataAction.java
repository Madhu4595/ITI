package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

 
public class UpdateDataAction extends org.apache.struts.action.Action {

     
    private static final String SUCCESS = "success";

    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("UR in UpdateDataAction.java");
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        UpdateDataForm uf = (UpdateDataForm) form;
        
        String iti_code         = beans.MyUtil.filterBad(uf.getIti_code());
        String iti_name         = beans.MyUtil.filterBad(uf.getIti_name());
        String mobile           = beans.MyUtil.filterBad(uf.getMobile());
        int pin_code            =  uf.getPin_code();
        String email            = beans.MyUtil.filterBad(uf.getEmail());
        String dist_code        = beans.MyUtil.filterBad(uf.getDist_code());
        String description      = beans.MyUtil.filterBad(uf.getDescription());
        String govt             = beans.MyUtil.filterBad(uf.getGovt());
        String address          = beans.MyUtil.filterBad(uf.getAddress());
        String city_town        = beans.MyUtil.filterBad(uf.getCity_town());
        String principalname    = beans.MyUtil.filterBad(uf.getPrincipalname());
        String landlinenumber   = beans.MyUtil.filterBad(uf.getLandlinenumber());
        String dget_iti_code    = beans.MyUtil.filterBad(uf.getDget_iti_code());
        String year_est         = beans.MyUtil.filterBad(uf.getYear_est());
        String app              = beans.MyUtil.filterBad(uf.getApp());
        String app_code         = beans.MyUtil.filterBad(uf.getApp_code());
        
        System.out.println("UR in UpdateDataAction.java->iti-code"+iti_code);
        System.out.println("UR in UpdateDataAction.java->dist-code"+dist_code);
        
        
        Connection con = null;
        PreparedStatement ps = null;
        

        try {
            
            con = DBC.DBConnection.getConnection(request);
            String sql = "update iti set mobile=?,pin_code=?,address=?,email=?,landlinenumber=?,year_est=?,principalname=?, appcode=?,city_town=?,govt=?,description=?,dget_iti_code=? where iti_code=?";
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            ps.setString(1, mobile);
            ps.setInt(2, pin_code);
            ps.setString(3, address);
            ps.setString(4, email);
            ps.setString(5, landlinenumber);
            ps.setString(6, year_est);
            ps.setString(7, principalname);
            ps.setString(8, app_code);
            ps.setString(9, city_town);
            ps.setString(10, govt);
            ps.setString(11, description);
            ps.setString(12, dget_iti_code);
            ps.setString(13, iti_code);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }

        return mapping.findForward(SUCCESS);
    }
}
