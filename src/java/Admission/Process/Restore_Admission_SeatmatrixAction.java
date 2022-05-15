package Admission.Process;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

 
public class Restore_Admission_SeatmatrixAction extends org.apache.struts.action.Action {

     
    private static final String SUCCESS = "success";

     
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        Restore_Admission_SeatmatrixForm rsf = (Restore_Admission_SeatmatrixForm) form;
        String category_code = beans.MyUtil.filterBad(rsf.getCategory_code());
        
        HttpSession session = request.getSession();
        
        String iti_code = beans.MyUtil.filterBad("" + session.getAttribute("iti_code"));
        String trade_code = beans.MyUtil.filterBad("" + session.getAttribute("trade_code"));
        String adm_num = beans.MyUtil.filterBad("" + session.getAttribute("adm_num"));
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        
        int strength_fill = 0;
        int strength_vacant = 0;
        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "admissions.iti_admissions", "restore", request);
        
        try {
            con = DBC.DBConnection.getConnection(request);
            String query1 = "select * from iti_seatmatrix where iti_code=? and trade_code=? and category_code=?";
            ps = con.prepareStatement(query1);
            ps.setString(1, iti_code);
            ps.setString(2, trade_code);
            ps.setString(3, category_code);
            rs = ps.executeQuery();
            if (rs.next()) {
                strength_fill = rs.getInt("strength_fill");
                strength_vacant = rs.getInt("strength_vacant");
            }
            strength_fill = strength_fill + 1;
            strength_vacant = strength_vacant - 1;
            String query2 = "update iti_seatmatrix set strength_fill=?,strength_vacant=? where iti_code=? and trade_code=?";
            // String query3="insert into admissions.iti_admissions select regid ,name ,fname ,addr ,mname ,phno ,adarno ,gender ,caste ,ssc_passed ,inter_passed ,inter_marks ,local ,phc ,exservice ,iti_code ,adm_num ,res_category ,year_of_admission,current_year ,current_sem ,mode_adm ,fromd_training ,tod_training ,trade_code ,dist_code ,olddist_code ,olditi_code ,attendance ,dob ,pincode ,idmarks1 ,idmarks2 ,"+trno+" ,ssc_regno ,ssc_board,ssc_year ,ssc_month ,date_of_admission ,type_admission ,phase ,regno ,estcode ,center_code,int_marks ,tot_marks ,year_of_attempt ,result ,grace_marks ,final_result ,final_year_of_pass  from admissions.iti_admissions_archive where adm_num='"+adm_num+"' and trno=(select max(trno) from admissions.iti_admissions_archive where adm_num='"+adm_num+"') limit 1";
            String query3 = "insert into admissions.iti_admissions(regid,name,fname,addr,mname,adarno,gender,caste,ssc_passed,inter_passed,inter_marks,local,phc,exservice,iti_code,adm_num,res_category,year_of_admission,current_year,current_sem,mode_adm,fromd_training,tod_training,trade_code,dist_code,olddist_code,olditi_code,attendance,dob,pincode,idmarks1,idmarks2,trno,ssc_regno,ssc_board,ssc_year,ssc_month,date_of_admission,type_admission,phase,regno,estcode,center_code ) select regid,name,fname,addr,mname,adarno,gender,caste,ssc_passed,inter_passed,inter_marks,local,phc,exservice,iti_code,adm_num,?,year_of_admission,current_year,current_sem,mode_adm,fromd_training,tod_training,trade_code,dist_code,olddist_code,olditi_code,attendance,dob,pincode,idmarks1,idmarks2,?,ssc_regno,ssc_board,ssc_year,ssc_month,date_of_admission,type_admission,phase,regno,estcode,center_code from admissions.iti_admissions_archive where adm_num=? and trno=(select max(trno) from admissions.iti_admissions_archive where adm_num=?) limit 1";

            ps = con.prepareStatement(query3);
            ps.setString(1, category_code);
            ps.setInt(2, trno);
            ps.setString(3, adm_num);
            ps.setString(4, adm_num);
            int x = ps.executeUpdate();

            if (x > 0) {
                ps = con.prepareStatement(query2);
                ps.setInt(1, strength_fill);
                ps.setInt(2, strength_vacant);
                ps.setString(3, iti_code);
                ps.setString(4, trade_code);
                ps.executeUpdate();
            }
            request.setAttribute("success", "Inserted successfully");
        } catch (Exception e) {
            request.setAttribute("success", "Inserted Fail");
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
            if (rs != null) {
                rs.close();
            }

        }

        return mapping.findForward(SUCCESS);
    }
}
