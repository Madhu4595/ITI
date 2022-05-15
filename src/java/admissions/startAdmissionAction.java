// 
// Decompiled by Procyon v0.5.36
// 

package admissions;

import java.text.DateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.text.SimpleDateFormat;
import DBC.DBConnection;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

public class startAdmissionAction extends Action
{
    private static final String SUCCESS = "success";
    
    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        System.out.println("class starts");
        
        final HttpSession session = request.getSession(true);
        
        final String iti_code = beans.MyUtil.filterBad(""+session.getAttribute("ins_code"));
        final String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));
        
        System.out.println("iti_code"+iti_code);
        System.out.println("role_id"+role_id);
        
        if (!role_id.equals("3")) {
            if (role_id.equals("4")) {}
        }
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("fail"); }
	
        
        final startAdmissionForm fm     = (startAdmissionForm)form;
        final String caste_perform      = beans.MyUtil.filterBad(fm.getCaste_perform());
        final String qual_perform       = beans.MyUtil.filterBad(fm.getQual_perform());
        final String cal_time_perform   = beans.MyUtil.filterBad(fm.getAdm_perform());
        
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnectionGen();
            System.out.println("connection"+connection);
            
            final String subqry = " and caste='" + caste_perform + "' and minqul='" + qual_perform + "' and cal_time='" + cal_time_perform + "'";
            System.out.println("subqry"+subqry);
            
            final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            final Date date = new Date();
            final java.sql.Date date2 = java.sql.Date.valueOf(dateFormat.format(date));
            
            String query = "";
            if (role_id.equals("3")) {
                query = "select merit_from,merit_to from admission_timings where dist_code =? and cal_date=?" + subqry;
            }
            else if (role_id.equals("4")) {
                query = "select merit_from,merit_to from admission_timings where iti_code =? and cal_date=?" + subqry;
            }
            
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, iti_code);
            preparedStatement.setDate(2, date2);
            resultSet = preparedStatement.executeQuery();
            System.out.println("query"+preparedStatement);
            
            if (resultSet.next()) {
                session.setAttribute("merit_from", (Object)resultSet.getInt("merit_from"));
                session.setAttribute("merit_to", (Object)resultSet.getInt("merit_to"));
                System.out.println("from date is " + resultSet.getInt("merit_from"));
            }
             System.out.println("merit_from"+resultSet.getInt("merit_from"));
             System.out.println("merit_to"+resultSet.getInt("merit_to"));
        }
        catch (Exception ex) {}
        finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
            catch (Exception ex2) {
            System.out.println("exception"+ex2);
           ex2.printStackTrace();
            }
        }
        return mapping.findForward("failure");
    }
}
