package applicationForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class Api_imageinsert extends org.apache.struts.action.Action{
      
    Connection con = DBC.DBConnection.getConnectionGen();        
        boolean commit = true;
        PreparedStatement st = null;
         ResultSet rs = null;
         String redirect="success";
            
        @Override 
        public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
            System.out.println("InMETHOD...........");
            
            HttpSession ses = request.getSession(true);
            
            String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
            String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
            String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
            if(!token.equals(id)){ return mapping.findForward("failure"); }
            
            Api_missingdata_from formvariable = (Api_missingdata_from) form;
            
            String admission_num = beans.MyUtil.filterBad(formvariable.getAdmission_number());
            String ssc_no = beans.MyUtil.filterBad(formvariable.getSsc_regno());
//              String name = formvariable.getName();
//              String fname = formvariable.getF_name();
//              String mname = formvariable.getM_name();
                FormFile photo = formvariable.getPhoto();
             try{
                
             
                int i=0;
//              String aadharno = formvariable.getAadhar_num();
//              boolean aadharcheck= beans.MyUtil.validateVerhoeff(aadharno);
             
//              if(aadharcheck==false){
//                 redirect="no_data";
//                 request.setAttribute("success", "Invalid Aadhar No.");
//                 return mapping.findForward(redirect);
//             }
             
             
//             String dob = formvariable.getDateofbirth();
//             String emailid = formvariable.getEmail_id();
//             String shift = formvariable.getShift();
//             String unit = formvariable.getUnit();
//             String pwd_category=formvariable.getPwd_category();
//             String ewc=formvariable.getEwc();
//             String is_trainee_dual_mode=formvariable.getDual_mode();
//              int duplicatemail=DBC.MyUtil.DuplicateCheck("admissions.iti_admissions",  "email_id='"+emailid+"' and adm_num!='"+admission_num+"'","");
//                 System.err.println("duplicatemail--->"+duplicatemail);
//              if(duplicatemail>0){
//                 redirect="no_data";
//                 request.setAttribute("success", "MailId Already Exists");
//                 return mapping.findForward(redirect);
//             }
             String regid = beans.MyUtil.filterBad(formvariable.getRegid());
//                 System.err.println("regid is--->"+regid);
//              int reg_id=Integer.parseInt(regid);
//             boolean itrainee_dual_mode=is_trainee_dual_mode.equals("yes");
//               long phno=formvariable.getPhno();
//               
//               int duplicatephone=DBC.MyUtil.DuplicateCheck("admissions.iti_admissions",  "phno='"+phno+"' and adm_num!='"+admission_num+"'","");
//                 System.err.println("duplicatephone--->"+duplicatephone);
//              if(duplicatephone>0){
//                 redirect="no_data";
//                 request.setAttribute("success", "Phone Number Already Exists");
//                 return mapping.findForward(redirect);
//             }     
                                       
               
//              boolean economic_weaker_section = ewc.equals("yes");
//             System.out.println("fdlkg;fdkgdfg"+economic_weaker_section);
//             
//             HttpSession ses = request.getSession();
//             String ins_code=""+ses.getAttribute("iti_code");
//               System.err.println("ins_code is--->"+ins_code);
//           // String query="Update admissions.iti_admissions set name='"+name+"', fname='"+fname+"',mname='"+mname+"',adarno='"+aadharno+"',dob='"+java.sql.Date.valueOf(MyUtil.ChDate1(dob))+"',email_id='"+emailid+"',shift='"+shift+"',unit='"+unit+"',pwd_category='"+pwd_category+"',economic_weaker_section='"+economic_weaker_section+"',phno="+phno+",is_trainee_dual_mode='"+itrainee_dual_mode+"',rec_status='E' where adm_num='"+admission_num+"' and ssc_regno='"+ssc_no+"'";
//              String query="Update api_backup_data set name='"+name+"', fname='"+fname+"',mname='"+mname+"',adarno='"+aadharno+"',dob='"+java.sql.Date.valueOf(MyUtil.ChDate1(dob))+"',email_id='"+emailid+"',shift='"+shift+"',unit='"+unit+"',pwd_category='"+pwd_category+"',economic_weaker_section='"+economic_weaker_section+"',phno="+phno+",is_trainee_dual_mode='"+itrainee_dual_mode+"',rec_status='E' where adm_num='"+admission_num+"' and ssc_regno='"+ssc_no+"'";
//             st = con.prepareStatement(query);
               
//              st.setString(1, name);
//               st.setString(2, fname);
//                st.setString(3, mname);
//                 st.setString(4, aadharno);
//                  st.setDate(5, java.sql.Date.valueOf(MyUtil.ChDate1(dob)));
//                  st.setString(6, emailid);
//                  st.setString(7, shift);
//                  st.setString(8, unit);
//                  st.setString(9, pwd_category);
//                  st.setBoolean(10, economic_weaker_section);
//                  st.setLong(11, phno);
//                  st.setBoolean(12, itrainee_dual_mode);
//                  st.setString(13, "E");
//                  st.setString(14, admission_num);
//                  st.setString(15, ssc_no);
//                  System.err.println("st is---->"+query);
//              i= st.executeUpdate();
//              if(i>0){
//              request.setAttribute("success", "Succesfully Updated");
               //String check_regid = beans.MyUtil.getOptionValue("img_cand_photos", "regid", "regid", regid, con);
                String check_regid = beans.MyUtil.getOptionValue("image_upload_phase1", "regid", "regid", regid, con);
            String img_query = null;
            String fileName = photo.getFileName();
//            String ssc_fileName = ssc_certificate.getFileName();
//            String caste_fileName = caste_certificate.getFileName();
//            String exs_fileName = exservice_certificate.getFileName();
//            String phy_fileName = phy_certificate.getFileName();
            
            PreparedStatement ps = null;
            if (!("").equals(fileName)) {
                try {
                    if (check_regid.equalsIgnoreCase(regid)) {
                      //  img_query = "update img_cand_photos set pic=? where regid=?";
                         img_query = "update image_upload_phase1 set pic=? where regid=?";
                        ps = con.prepareStatement(img_query);

                        ps.setBinaryStream(1, photo.getInputStream(), (int) photo.getFileSize());
                        ps.setInt(2, Integer.parseInt(regid));
                        
                    } 
                    else {
                       // img_query = "insert into img_cand_photos values(?,?)";
                         img_query = "insert into image_upload_phase1 values(?,?)";
                        ps = con.prepareStatement(img_query);
                        ps.setInt(1, Integer.parseInt(regid));
                        ps.setBinaryStream(2, photo.getInputStream(), (int) photo.getFileSize());
                    }

                    if (commit) {
                        ps.executeUpdate();
                        System.out.println("dflkgjldsfgdsf"+ps);
                    }
                     request.setAttribute("success", "Succesfully Updated");
              }
            catch(Exception e){
                System.err.println("dsfhdsfsdfsd"+e);
                e.printStackTrace();
            }}
           
              
              
              
              
//              }
//              else{
//                  request.setAttribute("success", "Failed to Update");
//              }
             }catch(Exception e){
             e.printStackTrace();
             }
             return mapping.findForward(redirect);
           }
}
