package Password;

import DBC.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChangeUserPwd_Action extends Action
{
  private static final String SUCCESS = "success";
  
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception{
      
        HttpSession session=request.getSession();
         
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id = stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
     
        ChangeUserPwd_Form cpf = (ChangeUserPwd_Form)form;
        String username = beans.MyUtil.filterBad(cpf.getUsername());
        String password = beans.MyUtil.filterBad(cpf.getPassword());
        
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = DBConnection.getConnection(request);
            String query = "update login_users set hash_password=md5(md5(?)) where username=?";
            System.out.println("query is--->"+query);
            ps = con.prepareStatement(query);
            ps.setString(1, password);
            ps.setString(2, username); 
            ps.executeUpdate();
    } catch (Exception e) {e.printStackTrace();}
     
      
    return mapping.findForward("success");
  

  }
}
