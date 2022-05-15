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

 
public class Restore_Admission_Action extends org.apache.struts.action.Action {

     
    private static final String SUCCESS = "success";

    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Restore_Admission_Form raf = (Restore_Admission_Form) form;
        String adm_no = beans.MyUtil.filterBad(raf.getAdm_no());

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        
        String iti_code = null;
        int trade_code = 0;
        int phase = 0;
        String res_category = null;
        int count = 0;
        int archivecount = 0;
        boolean vacant = false;
        ActionForward af = null;
        
        HttpSession session = request.getSession();
        session.setAttribute("adm_num", adm_no);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }

        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "admissions.iti_admissions", "restore", request);
        
        try {
            String q = "SELECT DISTINCT 1 AS count FROM admissions.iti_admissions WHERE adm_num=?";
            String q1 = "SELECT DISTINCT 1 AS count FROM admissions.iti_admissions_archive WHERE adm_num=?";

            con = DBC.DBConnection.getConnection(request);
            ps = con.prepareStatement(q1);
            ps.setString(1, adm_no);
            rs3 = ps.executeQuery();
            
            if (rs3.next()) {
                archivecount = rs3.getInt("count");
            }
            ps = con.prepareStatement(q);
            ps.setString(1, adm_no);
            rs4 = ps.executeQuery();
            
            if (rs4.next()) {
                count = rs4.getInt("count");
            }

            if (archivecount == 1) {
                if (count != 1) { 
                    //checking for admission number is already in admission table or not(means not in table)
                    String query1 = "select iti_code,trade_code,res_category,phase from admissions.iti_admissions_archive where adm_num=? and trno=(select max(trno) from admissions.iti_admissions_archive where adm_num=?) limit 1";
                    ps = con.prepareStatement(query1);
                    ps.setString(1, adm_no);
                    ps.setString(2, adm_no);
                    rs1 = ps.executeQuery();

                    int strength_fill = 0;
                    int strength_vacant = 0;
                    if (rs1.next()) {
                        iti_code = rs1.getString("iti_code");
                        trade_code = rs1.getInt("trade_code");
                        res_category = rs1.getString("res_category");
                        phase = rs1.getInt("phase");

                    }
                    session.setAttribute("iti_code", iti_code);
                    session.setAttribute("trade_code", trade_code);
                    session.setAttribute("phase", phase);
                    String query2 = "select strength_fill, strength_vacant  from iti_seatmatrix where iti_code=? and trade_code=? ";
                    ps = con.prepareStatement(query2);
                    ps.setString(1, iti_code);
                    ps.setInt(2, trade_code);
                    rs2 = ps.executeQuery();
                    
                    while (rs2.next()) {
                        strength_fill = rs2.getInt("strength_fill");
                        strength_vacant = rs2.getInt("strength_vacant");
                        if (strength_vacant != 0) {
                            strength_fill = strength_fill + 1;
                            strength_vacant = strength_vacant - 1;
                            String query = "insert into admissions.iti_admissions   select regid ,  name ,  fname,  addr ,  mname ,  phno ,  adarno ,\n" +
"  gender ,  caste,  ssc_passed ,  inter_passed ,  inter_marks ,  local ,\n" +
"  phc,  exservice,  iti_code ,  adm_num ,  res_category ,  year_of_admission ,  current_year ,\n" +
"  mode_adm ,  fromd_training ,  tod_training ,  trade_code ,  dist_code ,\n" +
"  olddist_code ,  olditi_code ,  attendance ,  dob ,  pincode,  idmarks1 ,\n" +
"  idmarks2 ,  ? ,  ssc_regno ,  ssc_board ,\n" +
"  ssc_year ,  ssc_month ,  date_of_admission ,  type_admission ,  phase ,  regno ,  estcode ,\n" +
"  center_code ,  newadmnum ,  int_marks ,  tot_marks ,  year_of_attempt ,  result ,\n" +
"  grace_marks ,  final_result ,  final_year_of_pass ,  ext_marks,  attempt_no ,\n" +
"  categories ,  sem_nonsem ,  dgettrade_code ,  noof_sems ,  finaltrade_code ,  engg_nonengg ,\n" +
"  htno ,  paper_result ,  paper_tot_marks ,  sem_paper_result ,  sem_paper_finalresult,\n" +
"  current_sem ,  oldhtno ,  remarks ,  year_of_pass  from admissions.iti_admissions_archive where adm_num=? and trno=(select max(trno) from admissions.iti_admissions_archive where adm_num=?) limit 1";
                            ps = con.prepareStatement(query);
                            ps.setInt(1, trno);
                            ps.setString(2, adm_no);
                            ps.setString(3, adm_no);
                            ps.executeUpdate();
                            
                            String query3 = "update iti_seatmatrix set strength_fill=?,strength_vacant=? where iti_code=? and trade_code=?";
                            ps = con.prepareStatement(query3);
                            ps.setInt(1, strength_fill);
                            ps.setInt(2, strength_vacant);
                            ps.setString(3, iti_code);
                            ps.setInt(4, trade_code);
                            ps.executeUpdate();
                            request.setAttribute("success", "Inserted successfully");

                        } else {
                            // if(phase!=1){
                            if (true) {
                                vacant = true;
                                af = mapping.findForward("seatmatrix");
                            } else {
                                request.setAttribute("success", "no vacancy in seatmatrix for this iti_code:   " + iti_code + "  ,  trade_code:   " + trade_code + " and reservation category:" + res_category + "  in phase" + phase);
                            }
                        }
                    }

                } else {
                    request.setAttribute("success", "admission number is already in admission table");
                }
            } else {
                request.setAttribute("success", "admission number is not in admission_archive table");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs2 != null) {
                rs2.close();
            }
            if (rs1 != null) {
                rs1.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        if (vacant) {
            return af;
        } else {

            return mapping.findForward(SUCCESS);
        }
    }
}
