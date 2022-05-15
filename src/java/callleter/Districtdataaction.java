 
package callleter;

import DBC.Validations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

 
public class Districtdataaction extends org.apache.struts.action.Action {

     
    private static final String SUCCESS = "success";
    private String userid;

     
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        Districtdataform df = (Districtdataform) form;
        String type = beans.MyUtil.filterBad(request.getParameter("type"));
        
        HttpSession session = request.getSession(true);
        
        String stored_token=""+session.getAttribute("generated_token");
        String token = request.getParameter("token");
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        Connection con = null;
        System.out.println("type " + type); 
        PreparedStatement preparedStatement=null;
        
        if (type.equals("single")) {
            
            String from = beans.MyUtil.filterBad(request.getParameter("from"));
            String to = beans.MyUtil.filterBad(request.getParameter("to"));
            String qual = beans.MyUtil.filterBad(request.getParameter("qual"));
            String caste = beans.MyUtil.filterBad(request.getParameter("caste"));
            String dist_code = beans.MyUtil.filterBad(request.getParameter("dist_code"));
            
            if (Validations.Validate(from, 2) && Validations.Validate(to, 2) && Validations.Validate(qual, 6) && Validations.Validate(caste, 14) && Validations.Validate(dist_code, 2)) {
                try {
                    con = DBC.DBConnection.getConnection(request);
                    String year= beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
                    String phase =beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, con);
                    
                    String sql = "delete from admission_timings where dist_code=? and merit_from=? and merit_to=? and minqul=? and caste=?;";
                     
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, dist_code);
                    preparedStatement.setString(2, from);
                    preparedStatement.setString(3, to);
                    preparedStatement.setString(4, qual);
                    preparedStatement.setString(5, caste);
                    preparedStatement.execute();
                    
                } 
                catch (Exception e) { System.out.println(e); e.printStackTrace(); } 
                finally { preparedStatement.close(); con.close(); }
            }
        } else {
            String dist_code = beans.MyUtil.filterBad(df.getDist_code());
            try {
                con = DBC.DBConnection.getConnection(request);
                String year= beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
                String phase =beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, con);
                    
                String sql = "delete from admission_timings where dist_code='" + dist_code + "' and phase='"+phase+"';";
                 
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();

            } finally {
                preparedStatement.close();
                con.close();
            }
        }
        return mapping.findForward(SUCCESS);

    }
}
