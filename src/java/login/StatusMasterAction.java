package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

 
public class StatusMasterAction extends org.apache.struts.action.Action {

   
    private static final String SUCCESS = "success";

   
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        StatusMasterForm smf = (StatusMasterForm) form;
        
        String status       = beans.MyUtil.filterBad(smf.getStatus());
        String loc          = beans.MyUtil.filterBad(smf.getLoc());
        String opertation   = beans.MyUtil.filterBad(smf.getOperation());
        String type         = beans.MyUtil.filterBad(smf.getType());
        
        request.setAttribute("opertation", opertation); 

        Connection con = DBC.DBConnection.getConnection(request);
        PreparedStatement ps = null;
        
        String query = null;
        String q = null;
        
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "1", con);
        String month = beans.MyUtil.getOptionValue("iti_params", "code", "value", "2", con);
        
        String month_year = month + "-" + year;
        
        HttpSession session = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        String roid = beans.MyUtil.filterBad(session.getAttribute("role_id") + "");
        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "status", "insert", request);
        System.out.println("trno iss "+trno);
        int rid = Integer.parseInt(roid);
        
        try {
            if (type.equals("trade")) {
                if (opertation.equals("freeze")) {
                    q = "update ititrade set trade_freeze='1',conv_approval='1' where iti_code=?";
                } else {
                    q = "update ititrade set trade_freeze='0',conv_approval='0' where iti_code=?";
                }
                ps = con.prepareStatement(q);
                ps.setString(1, loc);
                ps.executeUpdate();
            } else {

                if (opertation.equals("freeze")) {

                    query = "insert into status(status,loc_code,month_year,rid,trno) values(?,?,?,?,?)";
                    ps = con.prepareStatement(query);
                    ps.setString(1, status);
                    ps.setString(2, loc);
                    ps.setString(3, month_year);
                    ps.setInt(4, rid);
                    ps.setInt(5, trno);
                } else {
                    query = "delete from status where loc_code=? and status=?";
                    ps = con.prepareStatement(query);
                    ps.setString(1, loc);
                    ps.setString(2, status);
                }
                ps.executeUpdate();

            }
        } catch (Exception e) {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
            request.setAttribute("success", "Fail Operation");
            return mapping.findForward(SUCCESS);

        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        request.setAttribute("success", "Operation Success ");
        return mapping.findForward(SUCCESS);
    }
}
