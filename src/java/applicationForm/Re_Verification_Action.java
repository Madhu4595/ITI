/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import java.io.*;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author APDCA
 */
public class Re_Verification_Action extends org.apache.struts.action.Action {
     private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
    verification_interfaceForm uf = (verification_interfaceForm) form;
    
    String name = beans.MyUtil.filterBad(uf.getName());
    String fname = beans.MyUtil.filterBad(uf.getFname());
    String addr = beans.MyUtil.filterBad(uf.getAddr());
    String mname = beans.MyUtil.filterBad(uf.getMname());
    String ssc_type = beans.MyUtil.filterBad(uf.getSsc_type());    
    
    String pincode = "0", adarno = "";
   String regid = "";
   String sql="";
   
   String ssc_eq = beans.MyUtil.filterBad(request.getParameter("ssc_eq"));
   String tc = beans.MyUtil.filterBad(request.getParameter("tc"));
   String cc = beans.MyUtil.filterBad(request.getParameter("cc"));
   String pc = beans.MyUtil.filterBad(request.getParameter("pc"));
   String exsv = beans.MyUtil.filterBad(request.getParameter("exsv"));
   String sc = beans.MyUtil.filterBad(request.getParameter("sc"));
   String nc = beans.MyUtil.filterBad(request.getParameter("nc"));
   String uidv = beans.MyUtil.filterBad(request.getParameter("uidv"));
   String ewsv = beans.MyUtil.filterBad(request.getParameter("ewsv"));
   String subjects = beans.MyUtil.filterBad(uf.getSubjects());
        long phno = 0;
        
        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "application & cand marks", "Insert", request);
        String ssc_regno = beans.MyUtil.filterBad(uf.getSsc_regno());
        String ssc_board = beans.MyUtil.filterBad(uf.getSsc_board());
        String ssc_year = beans.MyUtil.filterBad(uf.getSsc_year());
        String ssc_month = beans.MyUtil.filterBad(uf.getSsc_month());
       String  reason = beans.MyUtil.filterBad(uf.getReject_reason());
        try {
            pincode = beans.MyUtil.filterBad(uf.getPincode());
        } catch (Exception ex) {
        }
        try {
            phno = Long.parseLong(uf.getPhno());
        } catch (Exception ex) {
        }
        try {
            adarno = uf.getAdarno();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String dob = beans.MyUtil.filterBad(uf.getDob());
        String phc = beans.MyUtil.filterBad(uf.getPhc());
        String exs = beans.MyUtil.filterBad(uf.getExs());
        String ssc = beans.MyUtil.filterBad(uf.ssc);
        
        boolean phc_bool = phc.equals("yes"), exs_bool = exs.equals("yes"), ssc_passed = ssc.equals("yes");
        FormFile photo = uf.getPhoto(), imgqul = uf.getImgqul(), imgcaste = uf.getImgcaste();
        
        HttpSession ses = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        String gen = beans.MyUtil.filterBad(uf.getGen());
        String gen1 = beans.MyUtil.filterBad(uf.getGen1());
        String caste = beans.MyUtil.filterBad(uf.getCaste());
        String qual = beans.MyUtil.filterBad(uf.getQual());
        String image = beans.MyUtil.filterBad(uf.getImage());
        String inter = beans.MyUtil.filterBad(uf.getInter());
        boolean inter_passed = inter.equals("yes");
        String intmarks =  (inter_passed) ? uf.getIntmarks() + "" : "0", loconoc = uf.getLoconoc();
        String update = "";
        
        Connection con = DBC.DBConnection.getConnectionGen();
        con.setAutoCommit(false);
        boolean commit = true;
        
        PreparedStatement st = null;
        PreparedStatement pst = null;
        PreparedStatement psts = null;
        PreparedStatement pstsmt = null;
        PreparedStatement pststmt = null;
        
        ResultSet rsv=null;
        Statement stv = null;
        
        
        PreparedStatement psv=null;
        String email = beans.MyUtil.filterBad(uf.getEmail());
        String pwd = beans.MyUtil.filterBad(uf.getPwd_category());
        String ews = beans.MyUtil.filterBad(uf.getEws());
        boolean ews_bool = ews.equals("yes");
        
        String redirect = "success";
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        String phse = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);
        String user_name = beans.MyUtil.filterBad(""+ses.getAttribute("userName"));
         String user_id = beans.MyUtil.filterBad(""+ses.getAttribute("u_name"));
         String ins_code = beans.MyUtil.filterBad(""+ses.getAttribute("ins_code"));
         String rej = beans.MyUtil.filterBad(uf.getRej());
         long mills=System.currentTimeMillis();
       Timestamp entry_date=new Timestamp(mills);
       String ip=request.getRemoteAddr();
         //if(rej.equals("1")){
         if(rej.equals("1")){
             Connection con1=null;
             Connection con11=null;
             try {
            update = beans.MyUtil.filterBad("" + ses.getAttribute("action"));
          
           System.out.println("update"+update);
            if (update.equals("update")) {
                
               
               con1=DBC.DBConnection.getConnectionGen();
               String sql1 = "Update student_application set app_status='R',reason=?,user_id=?,ip=?,verified_date=?,ssc_eq_marks=?,tc=?,caste_certificate=?,ph_challenge=?,ex_service=?,study_certificate=?,nativity_certificate=?,uidv=?,ewsv=? where regid::character varying=?";
               System.out.println("tfyf"+st); 
               st = con1.prepareStatement(sql1);
               st.setString(1, reason);
               st.setString(2, ins_code);
               st.setString(3, ip);
               st.setTimestamp(4, entry_date);
               st.setString(5, ssc_eq);
               st.setString(6, tc);
               st.setString(7, cc);
               st.setString(8, pc);
               st.setString(9, exsv);
               st.setString(10, sc);
               st.setString(11, nc);
               st.setString(12, uidv);
               st.setString(13, ewsv);
               st.setString(14, beans.MyUtil.filterBad(""+ses.getAttribute("regid")));
               
                st.executeUpdate();
                redirect = "reject";
             
         }
             } catch (Exception ex) {
               System.out.println("exception is-->"+ex);
                ex.printStackTrace();
            }
         }else{
        try {
            update = beans.MyUtil.filterBad("" + ses.getAttribute("action"));
           String reg_id = beans.MyUtil.filterBad(""+ses.getAttribute("regid"));
            if (update.equals("update")) {
                
                String del_appquery="delete from application  where regid::character varying=?";
                st=con.prepareStatement(del_appquery);
                st.setString(1, beans.MyUtil.filterBad(""+ses.getAttribute("regid")));
                st.executeUpdate();
                
                String del_tradequery="delete from trade_sel  where regid=?";
                st=con.prepareStatement(del_tradequery);
                st.setString(1, beans.MyUtil.filterBad(""+ses.getAttribute("regid")));
                st.executeUpdate();
                 
                String del_candquery="delete from cand_marks where regid=?";
                st=con.prepareStatement(del_candquery);
                st.setString(1, beans.MyUtil.filterBad(""+ses.getAttribute("regid")));
                st.executeUpdate();
                
                st = con.prepareStatement("Update student_application set name=?, fname=?,addr=?,pincode=?,mname=?,phno=?,adarno=?,dob=?,gender=?,caste=?,ssc_passed=?,inter_passed=?,inter_marks=?,local=?,phc=?,exservice=?,trno=?,email=?,pwd_category=?,economic_weaker_section=?,app_status='A',verified_date=?,verified_ip=?,ssc_type='"+ssc_type+"',user_id='"+ins_code+"',ssc_eq_marks='"+ ssc_eq +"',tc='"+tc+"',caste_certificate='"+cc+"',ph_challenge='"+pc+"',ex_service='"+exsv+"',study_certificate='"+sc+"',nativity_certificate='"+nc+"',uidv='"+uidv+"',ewsv='"+ewsv+"' where regid::character varying='" + ses.getAttribute("regid") + "'");
                redirect = "update";
                ses.removeAttribute("action");
               
                ses.removeAttribute("name");
                ses.removeAttribute("fname");
                ses.removeAttribute("mname");
                ses.removeAttribute("addr");
                ses.removeAttribute("phno");
                ses.removeAttribute("gender");
                ses.removeAttribute("caste");
                ses.removeAttribute("adarno");
                ses.removeAttribute("local");
                ses.removeAttribute("pincode");
                ses.removeAttribute("dob");
               // ses.removeAttribute("ssc_month");
                ses.removeAttribute("ssc_regno");
                ses.removeAttribute("ssc_board");
                ses.removeAttribute("ssc_year");
                ses.removeAttribute("intermarks");
                try {
                    ses.removeAttribute("first_lang_marks");
                    ses.removeAttribute("second_lang_marks");
                    ses.removeAttribute("eng_marks");
                    ses.removeAttribute("maths_marks");
                    ses.removeAttribute("science_marks");
                    ses.removeAttribute("social_marks");
                    ses.removeAttribute("group");
                    ses.removeAttribute("inter1s");
                    ses.removeAttribute("inter1m");
                    ses.removeAttribute("inter2s");
                    ses.removeAttribute("inter2m");
                    ses.removeAttribute("inter3s");
                    ses.removeAttribute("inter3m");
                } catch (Exception ex) {
                }
            } 
            st.setString(1, name);
            st.setString(2, fname);
            st.setString(3, addr);
            try {
                st.setInt(4, Integer.parseInt(pincode));
            } catch (Exception ex) {
                st.setInt(4, 0);
                ex.printStackTrace();
            }
            try {
                st.setString(5, mname);
            } catch (Exception ex) {
                st.setString(5, " ");
                ex.printStackTrace();
            }
            st.setLong(6, phno);
            st.setString(7, adarno);
            Date temp = Date.valueOf(DBC.MyUtil.ChDate1(dob));
            st.setDate(8, temp);
            st.setString(9, gen);
            st.setString(10, caste);
            st.setBoolean(11, ssc_passed);
            st.setBoolean(12, inter_passed);
            st.setInt(13, Integer.parseInt(intmarks));
            st.setString(14, loconoc);
            st.setBoolean(15, phc_bool);
            st.setBoolean(16, exs_bool);
            st.setInt(17, trno);
//            st.setString(18, ssc_regno);
//            st.setString(19, ssc_board);
//            st.setString(20, ssc_year);
         //   st.setString(18, ssc_month);
            // st.setString(22, year);
           /* if (!update.equals("update")) {
             st.setBoolean(22, true);
             }*/
              st.setString(18, email);
            st.setString(19, pwd);
            st.setBoolean(20, ews_bool);
            st.setTimestamp(21,entry_date);
            st.setString(22, ip);
      st.executeUpdate();
           
            System.out.println("dfggdg"+st);
        } catch (Exception ex) {
            System.out.println("exception is--->"+ex);
            ex.printStackTrace();
           System.out.println("commit is changing here");
            commit = false;
            redirect = "failure";
        } finally {
            if (st != null) {
                st.close();
            }
        }
         Date temp2 = Date.valueOf(DBC.MyUtil.ChDate1(dob));
//        String sql = "Select regid from student_application where name='" + name + "' and dob='" + temp2 + "'";
//        System.out.println("sql issssssssssssssssss:" + sql);
//        ResultSet rs = null;
//        try {
//            st = con.prepareStatement(sql);
//            rs = st.executeQuery();
//                    System.out.println("sql isssssssfgfgsssssssssss:" + sql);
//
//        } catch (Exception ex) {
//            System.out.println("" + ex);
//        }
//
//        String regid = "";
//        if (rs.next()) {
//            regid =""+ ses.getAttribute("regid") ;
//            //System.out.println("regd is " + regid);
//            if (!"update".equals(update)) {
//                ses.setAttribute("regid", regid);
//            }
//        }
        regid = beans.MyUtil.filterBad(""+ ses.getAttribute("regid"));
        ses.setAttribute("regid", regid);
        
        String first_lang_marks = beans.MyUtil.filterBad(uf.first_lang_marks);
        String first_lang_grade = beans.MyUtil.filterBad(uf.first_lang_grade);
        String first_lang_gpa = beans.MyUtil.filterBad(uf.first_lang_gpa);
        String second_lang_marks = beans.MyUtil.filterBad(uf.second_lang_marks);
        String second_lang_grade = beans.MyUtil.filterBad(uf.second_lang_grade);
        String second_lang_gpa = beans.MyUtil.filterBad(uf.second_lang_gpa);
        String eng_marks = beans.MyUtil.filterBad(uf.eng_marks);
        String eng_grade = beans.MyUtil.filterBad(uf.eng_grade);
        String eng_gpa = beans.MyUtil.filterBad(uf.eng_gpa);
        String maths_marks = beans.MyUtil.filterBad(uf.maths_marks);
        String maths_grade = beans.MyUtil.filterBad(uf.maths_grade);
        String maths_gpa = beans.MyUtil.filterBad(uf.maths_gpa);
        String science_marks = beans.MyUtil.filterBad(uf.science_marks);
        String science_grade = beans.MyUtil.filterBad(uf.science_grade);
        String science_gpa = beans.MyUtil.filterBad(uf.science_gpa);
        String social_marks = beans.MyUtil.filterBad(uf.social_marks);
        String social_grade = beans.MyUtil.filterBad(uf.social_grade);
        String social_gpa = beans.MyUtil.filterBad(uf.social_gpa);
        String tot_marks = beans.MyUtil.filterBad(uf.tot_marks);
        String tot_gpa = beans.MyUtil.filterBad(uf.tot_gpa);
        //inter
          if(ssc_type.equals("sscg"))
        {
            tot_marks="0";
        }
        String subject1_name = "", subject2_name = "", subject3_name = "";
        int subject1_marks = 0, subject2_marks = 0, subject3_marks = 0;
        String group = "";
        if (inter_passed) {
            group = beans.MyUtil.filterBad(uf.getGroup());
            subject1_name = beans.MyUtil.filterBad(uf.subject1_name);
            subject2_name = beans.MyUtil.filterBad(uf.subject2_name);
            subject3_name = beans.MyUtil.filterBad(uf.subject3_name);
            subject1_marks = uf.subject1_marks;
            subject2_marks = uf.subject2_marks;
            subject3_marks = uf.subject3_marks;
        }
        int index = 1;
         if (update.equals("update")) {
            // update cand marks
            sql = "Update student_cand_marks set ssc_first_lang_marks=?, ssc_first_lang_gpa=?, ssc_first_lang_grade=?,ssc_second_lang_marks=?, ssc_second_lang_gpa=?, ssc_second_lang_grade=?, ssc_eng_marks=?, ssc_eng_gpa=?, ssc_eng_grade=?, ssc_math_marks=?, ssc_math_gpa=?, ssc_math_grade=?, ssc_sci_marks=?, ssc_sci_gpa=?, ssc_sci_grade=?, ssc_social_marks=?, ssc_social_gpa=?, ssc_social_grade=?, inter_first_sub=?, inter_first_marks=?, inter_second_sub=?, inter_second_marks=?, inter_third_sub=?, inter_third_marks=?,ssc_tot_marks=?,ssc_tot_gpa=?,groupname=?,trno=" + trno + " where regid='" + regid + "'";
            st = con.prepareStatement(sql);
            String check_regid = beans.MyUtil.getOptionValue("img_cand_photos", "regid", "regid", regid, con);
            String img_query = null;
            String fileName = photo.getFileName();
            PreparedStatement ps = null;
           
            if (!("").equals(fileName)) {
                try {
                    if (check_regid.equalsIgnoreCase(regid)) {
                        img_query = "update img_cand_photos set pic=? where regid=?";
                        ps = con.prepareStatement(img_query);
                        ps.setBinaryStream(1, photo.getInputStream(), (int) photo.getFileSize());
                        ps.setInt(2, Integer.parseInt(regid));
                    } else {
                        img_query = "insert into img_cand_photos values(?,?)";
                        ps = con.prepareStatement(img_query);
                        ps.setInt(1, Integer.parseInt(regid));
                        ps.setBinaryStream(2, photo.getInputStream(), (int) photo.getFileSize());
                    }

                    if (commit) {
                        ps.executeUpdate();
                    }
                } catch (Exception ex) {
                    System.out.println("exception is---->"+ex);
                    commit = false;
                    ex.printStackTrace();
                } finally {
                    ps.close();
                }
            }
        }
         else {
            sql = "INSERT INTO student_cand_marks(regid, ssc_first_lang_marks, ssc_first_lang_gpa, ssc_first_lang_grade,ssc_second_lang_marks, ssc_second_lang_gpa, ssc_second_lang_grade, ssc_eng_marks, ssc_eng_gpa, ssc_eng_grade, ssc_math_marks, ssc_math_gpa, ssc_math_grade, ssc_sci_marks, ssc_sci_gpa, ssc_sci_grade, ssc_social_marks, ssc_social_gpa, ssc_social_grade, inter_first_sub, inter_first_marks, inter_second_sub, inter_second_marks, inter_third_sub, inter_third_marks,ssc_tot_marks,ssc_tot_gpa,groupname,trno) VALUES (?, ?, ?, ?,?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?,?, ?, ?, ?,?,?,?," + trno + ")";
            st = con.prepareStatement(sql);
            System.out.println("sqlfdhfjf ----"+sql);
            //String iti_code = formvariable.getIti_code();
           // String ins_code = formvariable.getIns_code();
            st.setInt(index++, Integer.parseInt(regid));
            // photo upload            
            String fileName = photo.getFileName();
            if (!("").equals(fileName)) {
                try {
                    sql = "insert into img_cand_photos values(?,?)";
                    pst = con.prepareStatement(sql);
                    pst.setInt(1, Integer.parseInt(regid));
                    pst.setBinaryStream(2, photo.getInputStream(), (int) photo.getFileSize());
                    System.out.println("sqlfdhfjf ----"+pst);
                    if (commit) {
                        pst.executeUpdate();
                    }
                } catch (Exception ex) {
                    commit = false;
                    ex.printStackTrace();
                } finally {
//                    rs.close();
                    pst.close();
                }
            }
         }
//          if(uf.getAddrs_mandal_p()!=null)
//          {
//         try {
//                String Saloc[]=uf.getAddrs_state_p();
//		String dist_name[]=uf.getAddrs_district_p();
//	
//		String addrs_mandal_p[]=uf.getAddrs_mandal_p();
//                int locLength=uf.getAddrs_mandal_p().length;
//                String insert_table = "trade_sel";
//                String fieldname = "";
//                String type = uf.getType();
//                //String phase = "phase2e";
//                String phase = "phase";
//                //if (type.equals("iti")) {
//                //if (type.equals("iti")) {
//                  //  fieldname = "iti_code";
//                //} else {
//                    fieldname = "dist_code";
//                //}
//                System.out.println("locLength is--->"+locLength);
//                
//                
//              int j=0;
//		
//		for (int i=0;i<locLength;i++){
////			System.out.println("state_code is--->"+dist_name[i]);
//			if(Saloc[i]!=null||dist_name[i]!=null||addrs_mandal_p[i]!=null||!Saloc[i].equalsIgnoreCase("")||!dist_name[i].equalsIgnoreCase("")){
//			String state_code=Saloc[i];
//			String district=dist_name[i];
//			String mandal=addrs_mandal_p[i];
//			
//			System.out.println("code is->"+state_code+"district is--->"+district+"mandal is--->"+mandal);
//			String query = "insert into " + insert_table + " (regid," + fieldname + ",temp_code,trno," + phase + ",year,iti_code) values('" + regid + "',?,?," + trno + ",hstore('" + phse + "','true'),'" + year + "',?)";
//                        
//                        //String query="INSERT INTO public.multiple_values(state_code, dist_code, mandal_code)VALUES (?, ?, ?)";
//			pst=con.prepareStatement(query);
//			pst.setString(1, state_code);
//                        pst.setString(2, state_code);
//			pst.setString(3, mandal);
//                        
//		
//               
////                
////                String query = "insert into " + insert_table + " (regid," + fieldname + ",temp_code,trno," + phase + ",year,iti_code,rec_status) values('" + regid + "','" + iti_code + "','" + iti_code + "'," + trno + ",hstore('" + phse + "','true'),'" + year + "','"+ins_code+"','F')";
////                pst = con.prepareStatement(query);
//               System.out.println("pst is--->"+pst);
//                if (commit) {
//                    System.out.println("executing");
//                    pst.executeUpdate();
//                }}}
//            } catch(Exception e){
//            
//              commit = false;
//                    e.printStackTrace();
//            }
//          }
         
              if(ssc_type.equals("sscg"))
        {
            tot_marks="0";
        }
        System.out.println(first_lang_marks.length() < 1||first_lang_marks.equalsIgnoreCase("null")||first_lang_marks==null);
        if (first_lang_marks.length() < 1||first_lang_marks.equalsIgnoreCase("null")||first_lang_marks==null) {
            first_lang_marks = "0";
            second_lang_marks = "0";
            eng_marks = "0";
            maths_marks = "0";
            science_marks = "0";
            social_marks = "0";
        }
        System.out.println("dfihgkhfdkghkdfhgkdfhgkhd");
        System.out.println("firstmarks----"+first_lang_marks);
        st.setInt(index++, Integer.parseInt(first_lang_marks));
        
        st.setString(index++, first_lang_gpa);
        st.setString(index++, first_lang_grade);
               if(subjects.equals("sub2"))
{
     st.setInt(index++, Integer.parseInt(second_lang_marks));
}else{
    st.setInt(index++, 0);
}
              if(subjects.equals("sub2"))
{
     st.setString(index++, second_lang_gpa);
}else{
    st.setInt(index++, 0);
}
        //st.setInt(index++, Integer.parseInt(second_lang_marks));
       // st.setString(index++, second_lang_gpa);
        st.setString(index++, second_lang_grade);
        st.setInt(index++, Integer.parseInt(eng_marks));
        st.setString(index++, eng_gpa);
        st.setString(index++, eng_grade);
        st.setInt(index++, Integer.parseInt(maths_marks));
        st.setString(index++, maths_gpa);
        st.setString(index++, maths_grade);
        st.setInt(index++, Integer.parseInt(science_marks));
        st.setString(index++, science_gpa);
        st.setString(index++, science_grade);
        st.setInt(index++, Integer.parseInt(social_marks));
        st.setString(index++, social_gpa);
        st.setString(index++, social_grade);
        st.setString(index++, subject1_name);
        st.setInt(index++, subject1_marks);
        st.setString(index++, subject2_name);
        st.setInt(index++, subject2_marks);
        st.setString(index++, subject3_name);
        st.setInt(index++, subject3_marks);
        st.setInt(index++, Integer.parseInt(tot_marks));
        st.setString(index++, tot_gpa);
        st.setString(index++, group);
        ses.setAttribute("regid", regid);
          try {
            System.out.println("query is " + st.toString());
            System.out.println("commit value is " + commit);
            System.out.println("insert value is " + update);
            if (commit) {
                try {
                    st.executeUpdate();
                    System.out.println("st is--->"+st);
                    System.out.println("executed");
                } catch (Exception ex) {
                    System.out.println("Exception is--->"+ex);
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception is--->"+ex);
                    ex.printStackTrace();
            commit = false;
        } finally {
            if (commit) {
                
                con.commit();
                 Connection connect = DBC.DBConnection.getConnection(request); 
                  Connection connectt = DBC.DBConnection.getConnection(request); 
              ResultSet result = null;
              System.out.println("ssc type verification------->"+ssc_type);
               psts = connect.prepareStatement("INSERT INTO application(regid, name, fname, addr, mname, phno, adarno, gender, caste,ssc_type,ssc_passed, inter_passed, inter_marks, local, pincode, ts, ip,uid, phc, exservice, dob, trno, ssc_regno, ssc_board, ssc_year,ssc_month, phase, year, university_area, data_flag, app_status,user_id,reason,email,pwd_category,economic_weaker_section,is_trainee_dual_mode,verified_date,verified_ip,ssc_eq_marks,tc,caste_certificate,ph_challenge,ex_service,study_certificate,nativity_certificate,uidv,ewsv)(SELECT regid, name, fname, addr, mname, phno, adarno, gender, caste,ssc_type, ssc_passed, inter_passed, inter_marks, local, pincode, ts, ip, uid, phc, exservice, dob, trno, ssc_regno, ssc_board, ssc_year, ssc_month, phase, year, university_area, data_flag, app_status, user_id, reason,email,pwd_category,economic_weaker_section,is_trainee_dual_mode,verified_date,verified_ip,ssc_eq_marks,tc,caste_certificate,ph_challenge,ex_service,study_certificate,nativity_certificate,uidv,ewsv FROM student_application where regid::character varying='" + regid + "')");
               psts.executeUpdate();
              
               connect.close();
//                String sqll= " INSERT INTO application ssc_eq_marks='"+ ssc_eq +"' ";
//  String sqll= " update application set ssc_eq_marks='"+ ssc_eq +"',tc='"+tc+"',caste_certificate='"+cc+"',ph_challenge='"+pc+"',ex_service='"+exsv+"',study_certificate='"+sc+"',nativity_certificate='"+nc+"',uidv='"+uidv+"',ewsv='"+ewsv+"'";
//              
//              psv=connectt.prepareStatement(sqll);
//               psv.executeUpdate();
//         
//               connectt.close();
               Connection connection = DBC.DBConnection.getConnection(request); 
              ResultSet resultst = null;
               pstsmt = connection.prepareStatement("INSERT INTO cand_marks(regid, ssc_first_lang_marks, ssc_first_lang_gpa, ssc_first_lang_grade, ssc_second_lang_marks, ssc_second_lang_gpa, ssc_second_lang_grade, ssc_eng_marks, ssc_eng_gpa, ssc_eng_grade, ssc_math_marks, ssc_math_gpa, ssc_math_grade, ssc_sci_marks, ssc_sci_gpa, ssc_sci_grade, ssc_social_marks, ssc_social_gpa, ssc_social_grade, inter_first_sub, inter_first_marks, inter_second_sub, inter_second_marks, inter_third_sub, inter_third_marks, ssc_tot_marks, ssc_tot_gpa,\"group\", trno, groupname, year)(SELECT regid, ssc_first_lang_marks, ssc_first_lang_gpa, ssc_first_lang_grade, ssc_second_lang_marks, ssc_second_lang_gpa, ssc_second_lang_grade, ssc_eng_marks, ssc_eng_gpa, ssc_eng_grade, ssc_math_marks, ssc_math_gpa, ssc_math_grade, ssc_sci_marks, ssc_sci_gpa, ssc_sci_grade, ssc_social_marks, ssc_social_gpa, ssc_social_grade, inter_first_sub, inter_first_marks, inter_second_sub, inter_second_marks, inter_third_sub, inter_third_marks, ssc_tot_marks, ssc_tot_gpa, \"group\", trno, groupname, year FROM student_cand_marks where regid::character varying='" + regid + "')");
               pstsmt.executeUpdate();
               connection.close();
               Connection connection1 = DBC.DBConnection.getConnection(request); 
              ResultSet resultsts = null;
               pststmt = connection1.prepareStatement("INSERT INTO trade_sel(regid, iti_code, dist_code, temp, freezee, temp_code, trno, phase, year)(SELECT regid, iti_code, dist_code, temp, freezee, temp_code, trno, phase, year FROM student_trade_sel where regid::character varying='" + regid + "')");
               pststmt.executeUpdate();
               connection1.close();
               
               System.out.println("gresgsregesrgsre---"+psts);
            } else {
                con.rollback();
            }
            if (st != null) {
                st.close();
                st = null;
            }
//            if (rs != null) {
//                rs.close();
//                rs = null;
//            }
            if (con != null) {
                con.close();
                con = null;
            }
        }
         }
       System.out.println(SUCCESS);  
return mapping.findForward(redirect);
}
}