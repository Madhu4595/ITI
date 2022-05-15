/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import java.sql.PreparedStatement;
import java.sql.Connection; 
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 *
 * @author APDCA
 */
public class Admissions_Process_Initialization_Action extends Action{

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
  
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
 
        Admissions_Process_Initialization_Form apiform = (Admissions_Process_Initialization_Form)form;
        
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String currentYear = String.valueOf(year);
        
        
        String phase = apiform.getPhase();   
       
        String applstartdate = apiform.getApplstartdate(); 
        Date smfdate = new SimpleDateFormat("yyyy-MM-dd").parse(applstartdate);
        smfdate.setHours(10); smfdate.setMinutes(00); smfdate.setSeconds(00);
        String applStartDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(smfdate.getTime());
                
        String applenddate = apiform.getApplenddate();
        Date smfdate2 = new SimpleDateFormat("yyyy-MM-dd").parse(applenddate);
        smfdate2.setHours(17); smfdate2.setMinutes(00); smfdate2.setSeconds(10);
        String applEndDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(smfdate2.getTime());
                
        String verstartdate = apiform.getVerstartdate();
         
        String verenddate = apiform.getVerenddate();
        Date smfdate3 = new SimpleDateFormat("yyyy-MM-dd").parse(verenddate);
        smfdate3.setHours(17); smfdate3.setMinutes(00); smfdate3.setSeconds(01);
        String verEndDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(smfdate3.getTime());
                
        Connection connection = null;
        PreparedStatement pstm = null;
        Statement statement = null;
        
        try{
           connection = DBC.DBConnection.getConnectionGen();
           String sql1 = "INSERT INTO admissions.admission_phase(year, phase, application_from_date, application_to_date, verification_start_date, verification_end_date, iti_type)VALUES(?,?,?,?,?,?,?);";
           pstm = connection.prepareStatement(sql1);
           pstm.setString(1, currentYear);
           pstm.setInt(2, Integer.parseInt(phase));
           pstm.setTimestamp(3, java.sql.Timestamp.valueOf(applStartDate));
           pstm.setTimestamp(4, java.sql.Timestamp.valueOf(applEndDate));
           pstm.setDate(5, java.sql.Date.valueOf(verstartdate));
           pstm.setTimestamp(6, java.sql.Timestamp.valueOf(verEndDate));
           pstm.setString(7, "G");
           int n1 = pstm.executeUpdate();
           request.setAttribute("api", n1);
           
           if(n1>0){ System.out.println("SUCCESSFULLY INSERTED RECORDS IN admissions.admission_phase - >"+n1); }
           else{ System.out.println("INSERTION FAILED"); }
        
            
           String sql2 = "update iti_params set value=? where code=?";
           pstm = connection.prepareStatement(sql2);
           pstm.setString(1, currentYear);
           pstm.setInt(2, 7);
           int n2 = pstm.executeUpdate();
           if(n2>0){ System.out.println("SUCCESSFULLY UPDATED IN iti_params VALUE AS-> "+currentYear); }
           else{ System.out.println("UPDATION FOR VALUE IS FAILED"); }
         
            
             
            statement=connection.createStatement();
            
            String sql3 = "create table student_application_backup_"+currentYear+" as select * from student_application";
            String sql4 = "create table student_trade_sel_backup_"+currentYear+"_phase_"+phase+" as select * from student_trade_sel";
            String sql5 = "create table student_cand_marks_backup_"+currentYear+" as select * from student_cand_marks";
            
            String sql6 = "delete from student_application";
            String sql7 = "delete from student_trade_sel";
            String sql8 = "delete from student_cand_marks";
            
            String sql9 = "create table admission_timings_backup_"+currentYear+"_phase_"+phase+" as select * from admission_timings";
            String sql10 = "create table ranks_backup_"+currentYear+"_phase_"+phase+" as select * from ranks";
            String sql11 = "create table checklist_backup_"+currentYear+"_phase_"+phase+"  as select * from checklist";
            
            String sql12 = "delete from admission_timings";
            String sql13 = "delete from ranks";
            String sql14 = "delete from checklist";
            
            String sql15 = "create table application_backup_"+currentYear+" as select * from application";
            String sql16 = "create table trade_sel_backup_"+currentYear+"_phase_"+phase+" as select * from trade_sel";
            String sql17 = "create table cand_marks_backup_"+currentYear+" as select * from cand_marks";
            
            String sql18 = "delete from application";
            String sql19 = "delete from trade_sel";
            String sql20 = "delete from cand_marks";
            
             
            if(phase.equals(String.valueOf(1))){    
                
                //Backup Operations
                statement.executeUpdate(sql3);
                System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM student_application TO student_application_backup_"+currentYear+"");
                 
                statement.executeUpdate(sql4);
                System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM student_trade_sel TO student_trade_sel_backup_"+currentYear+"_phase_"+phase+"");
                 
                statement.executeUpdate(sql5);
                System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM cand_marks TO cand_marks_backup_"+currentYear+"");
                
                statement.executeUpdate(sql9);
                System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM admission_timings TO admission_timings_backup_"+currentYear+"_phase_"+phase+"");
                
                statement.executeUpdate(sql10);
                System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM ranks TO ranks_backup_"+currentYear+"_phase_"+phase+"");
                
                statement.executeUpdate(sql11);
                System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM checklist TO checklist_backup_"+currentYear+"_phase_"+phase+"");
                
                statement.executeUpdate(sql15);
                System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM application TO application_backup_"+currentYear+"");
               
                statement.executeUpdate(sql16);
               System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM trade_sel TO trade_sel_backup_"+currentYear+"_phase_"+phase+"");
                
                statement.executeUpdate(sql17);
                System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM cand_marks TO cand_marks_backup_"+currentYear+"");
                
                //Deleting Data Operations
                statement.execute(sql6);
                System.out.println("ROWS DELETED FROM TABLE student_application");
                
                statement.execute(sql7);
                System.out.println("ROWS DELETED FROM TABLE student_trade_sel");
                
                statement.execute(sql8);
                System.out.println("ROWS DELETED FROM TABLE student_cand_marks");
                
                statement.execute(sql12);
                System.out.println("ROWS DELETED FROM TABLE admission_timings");
                
                statement.execute(sql13);
                System.out.println("ROWS DELETED FROM TABLE ranks");
                
                statement.execute(sql14);
                System.out.println("ROWS DELETED FROM TABLE checklist");
                
               statement.execute(sql18);
                System.out.println("ROWS DELETED FROM TABLE application");
               
               statement.execute(sql19);
               System.out.println("ROWS DELETED FROM TABLE trade_sel");
                
                statement.execute(sql20);
                System.out.println("ROWS DELETED FROM TABLE  cand_marks");
            } 
           else{
                //Backup Operations
                statement.executeUpdate(sql4);
                System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM student_trade_sel TO student_trade_sel_backup_"+currentYear+"_phase_"+phase+"");
                
                statement.executeUpdate(sql9);
                System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM admission_timings TO admission_timings_backup_"+currentYear+"_phase_"+phase+"");
                
                statement.executeUpdate(sql10);
                System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM ranks TO ranks_backup_"+currentYear+"_phase_"+phase+"");
                
                statement.executeUpdate(sql11);
                System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM checklist TO checklist_backup_"+currentYear+"_phase_"+phase+"");
                
               statement.executeUpdate(sql16);
               System.out.println("BACKUP ACTION IS SUCCESS DATA COPIED FROM trade_sel TO trade_sel_backup_"+currentYear+"_phase_"+phase+"");
                
                //Deleting rows from tables
                statement.execute(sql7);
                System.out.println("ROWS DELETED FROM TABLE student_trade_sel");
                
                statement.execute(sql12);
                System.out.println("ROWS DELETED FROM TABLE admission_timings");
                
                statement.execute(sql13);
                System.out.println("ROWS DELETED FROM TABLE ranks");
                
                statement.execute(sql14);
                System.out.println("ROWS DELETED FROM TABLE checklist");
                
                statement.execute(sql19);
                System.out.println("ROWS DELETED FROM TABLE  trade_sel");
                
            }
        }
        catch(Exception e){ System.out.println("EXCEPTION ARISED"); e.printStackTrace(); }
        finally{ pstm.close(); statement.close(); connection.close(); }
              
        return mapping.findForward("success");
         
    }  
}
