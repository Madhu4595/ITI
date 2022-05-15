package callleter;

import DBC.DBConnection;
import beans.MyUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class admissionAction extends org.apache.struts.action.Action
{
    private static final String SUCCESS = "success";
    
    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        
        final admissionForm lf = (admissionForm)form;
        
        final HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id = stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        final String iti_code =  beans.MyUtil.filterBad(""+session.getAttribute("ins_code"));
        final String dist_code = beans.MyUtil.filterBad(lf.getDist_code());
        final String minqul = beans.MyUtil.filterBad(lf.getMinqul());
        final String caste = beans.MyUtil.filterBad(lf.getCaste());
        final String merit_from1 = null;
        String merit_from2 = null;
        final String merit_to = beans.MyUtil.filterBad(lf.getMerit_to());
        final String cal_date = beans.MyUtil.filterBad(lf.getCal_date());
        final String cal_time = beans.MyUtil.filterBad(lf.getCal_time());
         
        System.out.println("cal_time------------->"+cal_time);
        
        String minqual = beans.MyUtil.filterBad(""+session.getAttribute("minqual"));
         
        
        try {
            con = DBConnection.getConnectionGen();
            final String year = MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
            final String phase = MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);
            String fieldname = "temp";
            final String district_type = beans.MyUtil.filterBad(lf.getDistrict_type());
            if (district_type.equals("district")) {
                fieldname = "dist_code";
            }
            else {
                fieldname = "iti_code";
            }
//            final String query1 = "Select max(merit_to)+1 as merit_from from admission_timings where " + fieldname + "='" + iti_code + "' and minqul='" + "" + session.getAttribute("minqual") + "' and  caste='" + "" + session.getAttribute("caste") + "' and phase='" + phase + "' and year='" + year + "'";
//            st1 = con.createStatement();
//            rs = st1.executeQuery(query1);
            final String query1 = "Select max(merit_to)+1 as merit_from from admission_timings where "+fieldname+"=? and minqul=? and  caste=? and phase=? and year=?";
            preparedStatement = con.prepareStatement(query1);
//            preparedStatement.setString(1, fieldname);
            preparedStatement.setString(1, iti_code);
            preparedStatement.setString(2, minqual);
            preparedStatement.setString(3, caste);
            preparedStatement.setString(4, phase);
            preparedStatement.setString(5, year);
            rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                merit_from2 = rs.getString("merit_from");
            }
            if (merit_from2 == null) {
                merit_from2 = "1";
            }
            final int meerit_from_int = Integer.parseInt(merit_from2);
            final int meerit_to_int = Integer.parseInt(merit_to);
            if (meerit_to_int <= meerit_from_int) {
                request.setAttribute("status", (Object)("details entry fail. because, already untel " + meerit_from_int + " Rank have been scheduled... but, you are trying to insert rank ending with " + meerit_to_int + " Rank "));
                return mapping.findForward("failure");
            }
            final int trno = DBC.generic.logTrace("" + session.getAttribute("userName"), "admissions.iti_admissions", "update", request);
            
            String cal_date2 = DBC.MyUtil.ChDate1(cal_date);

            //final String query2 = "insert into admission_timings(trno,minqul,caste,merit_from,merit_to,cal_date,cal_time," + fieldname + ",phase,temp_pk,year) values('" + trno + "','" + minqul + "','" + caste + "','" + merit_from2 + "','" + merit_to + "','" + DBC.MyUtil.ChDate1(cal_date) + "','" + cal_time + "','" + iti_code + "','" + phase + "','" + iti_code + "','" + year + "')";
            //final Statement st2 = con.createStatement();
            
            final String query2 = "insert into admission_timings(trno,minqul,caste,merit_from,merit_to,cal_date,cal_time,"+fieldname+",phase,temp_pk,year) values(?,?,?,?,?,?,'" + cal_time + "',?,?,?,?)";
            final PreparedStatement preparedStatement2 = con.prepareStatement(query2);
//            preparedStatement2.setString(1, fieldname);
            preparedStatement2.setInt(1, trno);
            preparedStatement2.setString(2, minqul);//2
            preparedStatement2.setString(3, caste);
            preparedStatement2.setInt(4, Integer.parseInt(merit_from2));//3
            preparedStatement2.setInt(5, Integer.parseInt(merit_to));//4
            preparedStatement2.setDate(6, java.sql.Date.valueOf(cal_date2));//5
           // preparedStatement2.setTimestamp(6,  java.sql.Timestamp.valueOf(cal_time));//6
            preparedStatement2.setString(7, iti_code);//1
            preparedStatement2.setString(8, phase);
            preparedStatement2.setString(9, iti_code);
            preparedStatement2.setString(10, year);
            int i = preparedStatement2.executeUpdate();
            
            if(i>0){}else{ return mapping.findForward("failure");}
              
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("success", "Schedule Entry Fail");
            return mapping.findForward("success");
        }
        finally {
            try {
                preparedStatement.close();
                con.close();
                rs.close();
            }
            catch (SQLException ex) {
                Logger.getLogger(callleter.generic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.setAttribute("success", "Schedule Entry Successfully Entered");
        return mapping.findForward("success");
    }
}