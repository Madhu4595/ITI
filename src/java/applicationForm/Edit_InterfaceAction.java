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
import java.util.*;

public class Edit_InterfaceAction extends org.apache.struts.action.Action {

    @Override
    @SuppressWarnings("empty-statement")
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("fdsklsdj");
        
        Edit_InterfaceForm formvariable = (Edit_InterfaceForm) form;
        System.out.println("districts selected" + request.getParameter("addrs_state_p"));
        
        String name = beans.MyUtil.filterBad(formvariable.getName());
        String fname = beans.MyUtil.filterBad(formvariable.getFname());
        String addr = beans.MyUtil.filterBad(formvariable.getAddr());
        String mname = beans.MyUtil.filterBad(formvariable.getMname());
        String ssc_type = beans.MyUtil.filterBad(formvariable.getSsc_type());
        
        String pincode = "0", adarno = "";
        
        long phno = 0;
        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "student_application & student_cand_marks", "Insert", request);
        
        String ssc_regno = beans.MyUtil.filterBad(formvariable.getSsc_regno());
        String ssc_board = beans.MyUtil.filterBad(formvariable.getSsc_board());
        String ssc_year = beans.MyUtil.filterBad(formvariable.getSsc_year());
        String ssc_month = beans.MyUtil.filterBad(formvariable.getSsc_month());
        
        System.out.println("fdsklsdj" + ssc_regno);
        try {
            pincode = beans.MyUtil.filterBad(formvariable.getPincode());
        } catch (Exception ex) {
        }
        try {
            phno = Long.parseLong(formvariable.getPhno());
        } catch (Exception ex) {
        }
        try {
            adarno = beans.MyUtil.filterBad(formvariable.getAdarno());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        String dob = beans.MyUtil.filterBad(formvariable.getDob());
        String phc = beans.MyUtil.filterBad(formvariable.getPhc());
        String exs = beans.MyUtil.filterBad(formvariable.getExs());
        String ssc = beans.MyUtil.filterBad(formvariable.ssc);
        boolean phc_bool = phc.equals("yes"), exs_bool = exs.equals("yes"), ssc_passed = ssc.equals("yes");
        FormFile photo = formvariable.getPhoto(), imgqul = formvariable.getImgqul(), imgcaste = formvariable.getImgcaste();

        HttpSession ses = request.getSession();

        String stored_token = beans.MyUtil.filterBad("" + ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));

        String id = stored_token.substring(10, 20) + request.getSession().getId() + stored_token.substring(2, 6);
        if (!token.equals(id)) { return mapping.findForward("fail"); }
        
        String gen = beans.MyUtil.filterBad(formvariable.getGen());
        String gen1 = beans.MyUtil.filterBad(formvariable.getGen1());
        String caste = beans.MyUtil.filterBad(formvariable.getCaste());
        String qual = beans.MyUtil.filterBad(formvariable.getQual());
        String image = beans.MyUtil.filterBad(formvariable.getImage());
        String inter = beans.MyUtil.filterBad(formvariable.getInter());
        
        boolean inter_passed = inter.equals("yes");
        
        String intmarks = (inter_passed) ? beans.MyUtil.filterBad(formvariable.getIntmarks()) + "" : "0", loconoc = beans.MyUtil.filterBad(formvariable.getLoconoc());
        String update = "";
        
        Connection con = DBC.DBConnection.getConnectionGen();
        con.setAutoCommit(false);
        boolean commit = true;
        
        PreparedStatement st = null;
        PreparedStatement pst = null;
        
        String redirect = "success";
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        String phse = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);

        try {
            update = beans.MyUtil.filterBad("" + ses.getAttribute("action"));
            System.out.println("update" + update);
            if (update.equals("update")) {

                st = con.prepareStatement("Update student_application set name=?, fname=?,addr=?,pincode=?,mname=?,phno=?,adarno=?,dob=?,gender=?,caste=?,ssc_passed=?,inter_passed=?,inter_marks=?,local=?,phc=?,exservice=?,trno=?,ssc_regno=?,ssc_board=?,ssc_year=?,ssc_month=? where regid::character varying='" + beans.MyUtil.filterBad(""+ses.getAttribute("regid")) + "'");
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
                ses.removeAttribute("ssc_month");
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
            }
            try {
                st.setString(5, mname);
            } catch (Exception ex) {
                st.setString(5, " ");
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
            st.setString(18, ssc_regno);
            st.setString(19, ssc_board);
            st.setString(20, ssc_year);
            st.setString(21, ssc_month);
           
            st.executeUpdate();
            System.out.println("dfggdg" + st);
        } catch (Exception ex) {
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
        String sql = "Select regid from student_application where name=? and dob=?";
        System.out.println("sql issssssssssssssssss:" + sql);
        ResultSet rs = null;
        try {
            st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setDate(2, temp2);
            rs = st.executeQuery();
            System.out.println("sql isssssssfgfgsssssssssss:" + sql);

        } catch (Exception ex) {
            System.out.println("" + ex);
        }

        String regid = "";
        if (rs.next()) {
            regid = "" + rs.getInt("regid");
            //System.out.println("regd is " + regid);
            if (!"update".equals(update)) {
                ses.setAttribute("regid", regid);
            }
        }
        String first_lang_marks = beans.MyUtil.filterBad(formvariable.first_lang_marks);
        String first_lang_grade = beans.MyUtil.filterBad(formvariable.first_lang_grade);
        String first_lang_gpa = beans.MyUtil.filterBad(formvariable.first_lang_gpa);
        String second_lang_marks = beans.MyUtil.filterBad(formvariable.second_lang_marks);
        String second_lang_grade = beans.MyUtil.filterBad(formvariable.second_lang_grade);
        String second_lang_gpa = beans.MyUtil.filterBad(formvariable.second_lang_gpa);
        String eng_marks = beans.MyUtil.filterBad(formvariable.eng_marks);
        String eng_grade = beans.MyUtil.filterBad(formvariable.eng_grade);
        String eng_gpa = beans.MyUtil.filterBad(formvariable.eng_gpa);
        String maths_marks = beans.MyUtil.filterBad(formvariable.maths_marks);
        String maths_grade = beans.MyUtil.filterBad(formvariable.maths_grade);
        String maths_gpa = beans.MyUtil.filterBad(formvariable.maths_gpa);
        String science_marks = beans.MyUtil.filterBad(formvariable.science_marks);
        String science_grade = beans.MyUtil.filterBad(formvariable.science_grade);
        String science_gpa = beans.MyUtil.filterBad(formvariable.science_gpa);
        String social_marks = beans.MyUtil.filterBad(formvariable.social_marks);
        String social_grade = beans.MyUtil.filterBad(formvariable.social_grade);
        String social_gpa = beans.MyUtil.filterBad(formvariable.social_gpa);
        String tot_marks = beans.MyUtil.filterBad(formvariable.tot_marks);
        String tot_gpa = beans.MyUtil.filterBad(formvariable.tot_gpa);
        //inter
        String subject1_name = "", subject2_name = "", subject3_name = "";
        int subject1_marks = 0, subject2_marks = 0, subject3_marks = 0;
        String group = "";
        if (inter_passed) {
            group = beans.MyUtil.filterBad(formvariable.getGroup());
            subject1_name = beans.MyUtil.filterBad(formvariable.subject1_name);
            subject2_name = beans.MyUtil.filterBad(formvariable.subject2_name);
            subject3_name = beans.MyUtil.filterBad(formvariable.subject3_name);
            subject1_marks = formvariable.subject1_marks;
            subject2_marks = formvariable.subject2_marks;
            subject3_marks = formvariable.subject3_marks;
        }
        int index = 1;
        if (update.equals("update")) {
            ses = request.getSession(true);
            ses.setAttribute("regid", regid);
            // update cand marks
            sql = "Update student_cand_marks set ssc_first_lang_marks=?, ssc_first_lang_gpa=?, ssc_first_lang_grade=?,ssc_second_lang_marks=?, ssc_second_lang_gpa=?, ssc_second_lang_grade=?, ssc_eng_marks=?, ssc_eng_gpa=?, ssc_eng_grade=?, ssc_math_marks=?, ssc_math_gpa=?, ssc_math_grade=?, ssc_sci_marks=?, ssc_sci_gpa=?, ssc_sci_grade=?, ssc_social_marks=?, ssc_social_gpa=?, ssc_social_grade=?, inter_first_sub=?, inter_first_marks=?, inter_second_sub=?, inter_second_marks=?, inter_third_sub=?, inter_third_marks=?,ssc_tot_marks=?,ssc_tot_gpa=?,groupname=?,trno=? where regid=?";
            System.out.println("update student_cand_marks query issssssss:" + sql);
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

                    }
 

                    if (commit) {
                        ps.executeUpdate();
                        System.out.println("dflkgjldsfgdsf" + ps);
                    }
                } catch (Exception ex) {
                    commit = false;
                    ex.printStackTrace();
                } finally {
                    ps.close();
                }
            }
 
        try {
            System.out.println("sjdghkhkghksdgsd" + formvariable.getAddrs_state_p().length);
            String Saloc[] = formvariable.getAddrs_state_p();

             
            if (formvariable.getAddrs_state_p() != null) {
                int locLength = formvariable.getAddrs_state_p().length;
                String insert_table = "student_trade_sel";
                String fieldname = "";
                String type = beans.MyUtil.filterBad(formvariable.getType());
                 
                String phase = "phase";
                 
                fieldname = "dist_code";
                
                System.out.println("locLength is--->" + locLength);
                List<String> list = new ArrayList<String>();
                List<String> list1 = new ArrayList<String>();
                List<String> list2 = new ArrayList<String>();

                for (String s1 : Saloc) {
                    if (s1 != null && s1.length() > 0) {
                        list1.add(s1);
                    }
                }

                Saloc = list1.toArray(new String[list1.size()]);

                int j = 0;
                System.out.println("Saloc" + Saloc);
                for (int i = 0; i < Saloc.length; i++) {

                    if (Saloc[i] != null || !Saloc[i].equalsIgnoreCase("")) {
                        String state_code = Saloc[i];

                        if (state_code.equals("")) {
                        } else {
                              
                            String query = "insert into ? (regid,?,temp_code,trno,?,year) values(?,?,?,?,hstore(?,'true'),?)";
                             
                            pst = con.prepareStatement(query);
                            pst.setString(1, insert_table);
                            pst.setString(2, fieldname);
                            pst.setString(3, phase);
                            pst.setString(4, regid);
                            pst.setString(5, state_code);
                            pst.setString(6, state_code);
                            pst.setInt(7, trno);
                            pst.setString(8, phse);
                            pst.setString(9, year);
 
                            if (commit) {
                                System.out.println("executing");
                                pst.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            commit = false;
            e.printStackTrace();
        }
        if (ssc_type.equals("sscg")) {
            tot_marks = "0";
        }
        System.out.println(first_lang_marks.length() < 1 || first_lang_marks.equalsIgnoreCase("null") || first_lang_marks == null);
        if (first_lang_marks.length() < 1 || first_lang_marks.equalsIgnoreCase("null") || first_lang_marks == null) {
            first_lang_marks = "0";
            second_lang_marks = "0";
            eng_marks = "0";
            maths_marks = "0";
            science_marks = "0";
            social_marks = "0";
        }
        System.out.println("dfihgkhfdkghkdfhgkdfhgkhd");
        System.out.println("firstmarks----" + first_lang_marks);
        st.setInt(index++, Integer.parseInt(first_lang_marks));

        st.setString(index++, first_lang_gpa);
        st.setString(index++, first_lang_grade);
        st.setInt(index++, Integer.parseInt(second_lang_marks));
        st.setString(index++, second_lang_gpa);
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
        st.setInt(index++, trno);
        st.setString(index++, regid);
        try {
            System.out.println("query is " + st.toString());
            System.out.println("commit value is " + commit);
            System.out.println("insert value is " + update);
            if (commit) {
                try {
                    st.executeUpdate();
                    System.out.println("st is--->" + st);
                    System.out.println("executed");
                } catch (Exception ex) {
                    System.out.println("Exception is--->" + ex);
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception is--->" + ex);
            ex.printStackTrace();
            commit = false;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
            if (st != null) {
                st.close();
                st = null;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (con != null) {
                con.close();
                con = null;
            }
        }}

        return mapping.findForward(redirect);
    }
    
}
