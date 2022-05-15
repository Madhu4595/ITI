/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm_withoutphoto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.w3c.dom.*;

/**
 *
 * @author Narendra
 */
public class sscdsata_application_wsAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        sscdsata_application_wsForm formvariable = (sscdsata_application_wsForm) form;
       String dist_code= formvariable.getDist_code();
              String name= formvariable.getName();
              String fname= formvariable.getFname();
               String mname= formvariable.getMname();
               String dob= formvariable.getDob();
               String sex= formvariable.getSex();
                String l1mrk= formvariable.getL1mrk();
                String l1grade= formvariable.getL1grade();
                String l1points= formvariable.getL1points();
                String l2mrk= formvariable.getL2mrk();
                String l2grade= formvariable.getL2grade();
                String l2points= formvariable.getL2points();
                String l3mrk= formvariable.getL3mrk();
                String l3grade= formvariable.getL3grade();
                String l3points= formvariable.getL3points();
                String matmrk= formvariable.getMatmrk();
                String matgrade= formvariable.getMatgrade();
                String matpoints= formvariable.getMatpoints();
                String scimrk= formvariable.getScimrk();
                String scigrade= formvariable.getScigrade();
                String scipoints= formvariable.getScipoints();
                String socmrk= formvariable.getSocmrk();
                String socgrade= formvariable.getSocgrade();
                String socpoints= formvariable.getSocpoints();
                String gtot= formvariable.getGtot();
                String gpa= formvariable.getGpa();








              String dist_name= formvariable.getDist_name();
try{
       
        HttpSession session = request.getSession(true);
        String role_id = "" + session.getAttribute("role_id");
        String field_name = "dist_code";
        if (role_id.equalsIgnoreCase("4")) {
            field_name = "iti_code";        }
        String ins_code = "" + session.getAttribute("ins_code");
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();

        System.out.println("hello. ");
        String rollno = request.getParameter("rollno");
        String sscyear = request.getParameter("year");
        String Stream = request.getParameter("stream");


        String regid = "";
        String addr = request.getParameter("addr");
        String pincode = request.getParameter("pincode");
        String phno = request.getParameter("phno");
        String adarno = request.getParameter("adarno");
        String caste = request.getParameter("caste");
        String phc = request.getParameter("phc");
        String exser = request.getParameter("exser");
        String local = request.getParameter("local");
        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "application & cand marks", "Insert", request);
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        String phse = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);

        Connection conp = DBC.DBConnection.getConnection(request);


        String query="select name,fname,mname,dob,sex,dist_name,l1mrk,l1grade,l1points,l2mrk,l2grade,l2points,l3mrk,l3grade,l3points,matmrk,matgrade,matpoints,scimrk,scigrade,scipoints,socmrk,socgrade,socpoints,gtot,gpa from ssc.ssc_data_2016_ap where rollno='"+rollno+"' and year='"+year+"' and stream='"+Stream+"'";
       Statement ps = conp.createStatement();
            ResultSet rs;
            rs=ps.executeQuery(query);

        
            
                 while (rs.next()) {
                     name = rs.getString("name");
                    fname = rs.getString("fname");
                    mname = rs.getString("mname");
                    dob = rs.getString("dob");
                    sex = rs.getString("sex");
                    dist_name = rs.getString("dist_name");
                    l1mrk = rs.getString("l1mrk");
                      l1grade = rs.getString("l1grade");
                      l1points = rs.getString("l1points");
                      l2mrk = rs.getString("l2mrk");
                      l2grade = rs.getString("l2grade");
                      l2points = rs.getString("l2points");
                      l3mrk = rs.getString("l3mrk");
                      l3grade = rs.getString("l3grade");
                      l3points = rs.getString("l3points");
                      matmrk = rs.getString("matmrk");
                      matgrade = rs.getString("matgrade");
                      matpoints = rs.getString("matpoints");
                      scimrk = rs.getString("scimrk");
                      scigrade = rs.getString("scigrade");
                      scipoints = rs.getString("scipoints");
                      socmrk = rs.getString("socmrk");
                      socgrade = rs.getString("socgrade");
                      socpoints = rs.getString("socpoints");
                      gtot = rs.getString("gtot");
                      gpa = rs.getString("gpa");
                      
                                                                                                                                  l1mrk = rs.getString("l1mrk");

//dob=beans.MyUtil.ChDate1(dob);

            
              
                System.out.println(name + "  " + fname + "   " + mname + "  " + sex + "  " + rollno);
                System.out.println(l1mrk + "  " + l2mrk + "   " + l3mrk + "  " + l1points + "  " + l2points);

                int k = 0;
                String qry = "select regid from application where ssc_regno='" + rollno + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(qry);
                System.out.println(qry);
                while (rs2.next()) {
                    k++;
                    regid = rs2.getString("regid");
                    // x = false;
                    //out.println("<font color=red>This Candidate already have Registration Number " + rs2.getString("regid") + " Click on Generate button to Apply this candidate into Your District Also<br><br></font>");
                }
                /* st2.close();
                
                 rs2.close();*/
                String sql;
                if (k == 0) {
                    sql = "INSERT INTO application(name, fname, addr, mname, phno, adarno, gender, caste,ssc_passed, inter_passed, inter_marks, local, pincode,phc, exservice, dob, trno, ssc_regno, ssc_year, phase, year) values ('" + name + "','" + fname + "','" + addr + "','" + mname + "','" + phno + "','" + adarno + "','" + sex + "','" + caste + "','y','n'," + trno + ",'" + local + "','" + pincode + "','" + phc + "','" + exser + "','" +dob + "'::date,'" + trno + "','" + rollno + "','" + sscyear + "',hstore(array['" + phse + "'],array['true']),'" + year + "' )";
                    System.out.println(sql);
                    int j = 0;
                    try {
                        j = st.executeUpdate(sql);
                    } catch (Exception ex) {
                        System.out.println("ex" + ex);
                    }
                    if (j > 0) {

                        sql = "select regid from application where name='" + name + "' and fname='" + fname + "' and dob='" +dob + "'::date";
                        ResultSet rs1 = st.executeQuery(sql);
                        if (rs1.next()) {
                            regid = rs1.getString("regid");

                        }
                        System.out.println("Registration Number is :: " + regid);
                    }
                } else {
                    sql = "update application  set name='" + name + "',fname='" + fname + "',addr='" + addr + "',mname='" + mname + "',phno='" + phno + "',adarno='" + adarno + "',gender='" + sex + "',caste='" + caste + "',local='" + local + "',pincode='" + pincode + "',phc='" + phc + "',exservice='" + exser + "',dob='"+dob+"'::date,trno='" + trno + "',ssc_regno='" + rollno + "',phase=(case when phase is null then '' else phase end)||hstore(array['" + phse + "'],array['true']),year='" + year + "' where regid = '" + regid + "'";
                    System.out.println(sql);
                    int j = 0;
                    try {
                        j = st.executeUpdate(sql);
                    } catch (Exception ex) {
                        System.out.println("ex" + ex);
                    }
                }
                request.setAttribute("regid", regid);

                k = 0;
                qry = "select regid from trade_sel where regid='" + regid + "' and " + field_name + "='" + ins_code + "'";
                //st2 = con.createStatement();
                rs2 = st2.executeQuery(qry);
                                    System.out.println(qry);

                while (rs2.next()) {
                    k++;
                    regid = rs2.getString("regid");
                    // x = false;
                    //out.println("<font color=red>This Candidate already have Registration Number " + rs2.getString("regid") + " Click on Generate button to Apply this candidate into Your District Also<br><br></font>");
                }
                if (k == 0) {
                    sql = "INSERT INTO trade_sel(regid, " + field_name + ", temp_code,trno, phase, year)VALUES ('" + regid + "', '" + ins_code + "', '" + ins_code + "', " + trno + ",hstore(array['" + phse + "'],array['true']),'" + year + "')";
                    System.out.println(sql);
                    try {
                        st.executeUpdate(sql);
                    } catch (Exception ex) {
                        System.out.println("ex" + ex);
                    }
                } else {
                    sql = "update trade_sel set  trno= " + trno + ", phase=(case when phase is null then '' else phase end)||hstore(array['" + phse + "'],array['true']),year='" + year + "' where regid='" + regid + "' and " + field_name + "= '" + ins_code + "'";
                    System.out.println(sql);
                    try {
                        st.executeUpdate(sql);
                    } catch (Exception ex) {
                        System.out.println("ex" + ex);
                    }
                }
                
                k = 0;
                qry = "select regid from cand_marks where regid='" + regid + "' ";
                //st2 = con.createStatement();
                rs2 = st2.executeQuery(qry);
                while (rs2.next()) {
                    k++;
                    regid = rs2.getString("regid");
                    // x = false;
                    //out.println("<font color=red>This Candidate already have Registration Number " + rs2.getString("regid") + " Click on Generate button to Apply this candidate into Your District Also<br><br></font>");
                }
                if (k == 0) {
                sql = "INSERT INTO cand_marks(regid, ssc_first_lang_marks, ssc_first_lang_gpa, ssc_first_lang_grade,  ssc_second_lang_marks, ssc_second_lang_gpa, ssc_second_lang_grade,  ssc_eng_marks, ssc_eng_gpa, ssc_eng_grade, ssc_math_marks, ssc_math_gpa,  ssc_math_grade, ssc_sci_marks, ssc_sci_gpa, ssc_sci_grade, ssc_social_marks,  ssc_social_gpa, ssc_social_grade, ssc_tot_gpa,trno, year) values( '" + regid + "', '"+l1mrk+"','" + l1points + "','" + l1grade + "','"+l2mrk+"','" + l2points + "','" + l2grade + "','"+l3mrk+"','" + l3points + "','" + l3grade + "','"+matmrk+"','" + matpoints + "','" + matgrade + "','"+scimrk+"','" + scipoints + "','" + scigrade + "','"+socmrk+"','" + socpoints + "','" + socgrade + "','" + gpa + "'," + trno + ",'" + year + "' )";
                System.out.println(sql);
                try {
                    st.executeUpdate(sql);
                } catch (Exception ex) {
                    System.out.println("ex");
                }
                }else{
                   sql = "update cand_marks set ssc_first_lang_marks='"+l1mrk+"', ssc_first_lang_gpa='"+l1points+"', ssc_first_lang_grade='"+l1grade+"',  ssc_second_lang_marks='"+l2mrk+"', ssc_second_lang_gpa='"+l2points+"', ssc_second_lang_grade='"+l2grade+"',  ssc_eng_marks='"+l3mrk+"', ssc_eng_gpa='"+l3points+"', ssc_eng_grade='"+l3grade+"', ssc_math_marks='"+matmrk+"', ssc_math_gpa='"+matpoints+"',  ssc_math_grade='"+matgrade+"', ssc_sci_marks='"+scimrk+"', ssc_sci_gpa='"+scipoints+"', ssc_sci_grade='"+scigrade+"', ssc_social_marks='"+socmrk+"',  ssc_social_gpa='"+socpoints+"', ssc_social_grade='"+socgrade+"', ssc_tot_gpa='"+gpa+"',trno='"+trno+"', year='"+year+"' where regid ='"+regid+"'";
                System.out.println(sql);
                try {
                    st.executeUpdate(sql);
                } catch (Exception ex) {
                    System.out.println("ex"+ex);
                } 
                }

                FormFile photo = formvariable.getPhoto();
                String fileName = photo.getFileName();
                PreparedStatement ps1 = null;

                try {
                    if (!("").equals(fileName)) {


                        String img_query = "insert into img_cand_photos values(?,?)";
                                        System.out.println(img_query);

                        ps1 = con.prepareStatement(img_query);
                        ps1.setInt(1, Integer.parseInt(regid));
                        ps1.setBinaryStream(2, photo.getInputStream(), (int) photo.getFileSize());



                        ps1.executeUpdate();


                    }
                } catch (Exception ex) {
                    ps1.close();
                    ps.close();
                    con.close();
                    System.out.println(ex);
                }
                try {
                    ps1.close();
                    ps.close();
                    con.close();
                } catch (Exception ex) {
                }

            }//for
        } catch (Exception e) {
            System.out.println(e);
        }
        return mapping.findForward(SUCCESS);
    }
}
