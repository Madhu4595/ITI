package applicationForm;
import beans.MyUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

 
public class ForgotRegidAction extends org.apache.struts.action.Action {
    
     private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ForgotRegidForm uf = (ForgotRegidForm) form;
        
        String sscregno = MyUtil.filterBad(uf.getSsc_regno());
        String Dob = MyUtil.filterBad(uf.getDob());
        
        Date dateofbirth = Date.valueOf(DBC.MyUtil.ChDate1(Dob));
        
        Connection con=null;
        PreparedStatement ps = null;
        ResultSet rs=null;
        HttpSession ses = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        try {
            con = DBC.DBConnection.getConnection(request);
            String sql = "select regid from student_application where ssc_regno=? and dob=?" ;
            System.out.println("ygyfgftdd"+sql);
            ps = con.prepareStatement(sql);
            ps.setString(1, sscregno);
            ps.setDate(2, dateofbirth);
            rs = ps.executeQuery();
        
            if (rs.next()) {
                ses.removeAttribute("regid");
                request.setAttribute("forgetregid", rs.getString("regid"));
                System.out.println("gffyujgjug"+rs.getString("regid"));
            }
        } 
        catch (Exception ex) { 
            ex.printStackTrace(); if (rs != null) { rs.close(); } 
        }
        if (ps != null) { ps.close(); }
        if (con != null) { con.close(); }
        
        return mapping.findForward("success");
        }
    
}
