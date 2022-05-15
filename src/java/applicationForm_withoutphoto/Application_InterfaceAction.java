/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm_withoutphoto;

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

public class Application_InterfaceAction extends org.apache.struts.action.Action {

    /**
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings("empty-statement")
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Application_InterfaceForm formvariable = (Application_InterfaceForm) form;
        String name = formvariable.getName(), fname = formvariable.getFname(), addr = formvariable.getAddr(), mname = formvariable.getMname();
        String pincode = "0", adarno = "";
        long phno = 0;
        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "application & cand marks", "Insert", request);
        String ssc_regno = formvariable.getSsc_regno(), ssc_board = formvariable.getSsc_board(), ssc_year = formvariable.getSsc_year(), ssc_month = formvariable.getSsc_month();
        try {
            pincode = formvariable.getPincode();
        } catch (Exception ex) {
        }
        try {
            phno = Long.parseLong(formvariable.getPhno());
        } catch (Exception ex) {
        }
        try {
            adarno = formvariable.getAdarno();
        } catch (Exception ex) {
        }
        String dob = formvariable.getDob(), phc = formvariable.getPhc(), exs = formvariable.getExs(), ssc = formvariable.ssc;;
        boolean phc_bool = phc.equals("yes"), exs_bool = exs.equals("yes"), ssc_passed = ssc.equals("yes");
        //FormFile photo = formvariable.getPhoto(), imgqul = formvariable.getImgqul(), imgcaste = formvariable.getImgcaste();
        HttpSession ses = request.getSession();
        String gen = formvariable.getGen(), gen1 = formvariable.getGen1(), caste = formvariable.getCaste();
        String qual = formvariable.getQual(), image = formvariable.getImage(), inter = formvariable.getInter();
        boolean inter_passed = inter.equals("yes");
        String intmarks = (inter_passed) ? formvariable.getIntmarks() + "" : "0", loconoc = formvariable.getLoconoc();
        String update = "";
        Connection con = DBC.DBConnection.getConnection(request);
        con.setAutoCommit(false);
        boolean commit = true;
        PreparedStatement st = null;
        PreparedStatement pst = null;
        String redirect = "success";
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        String phse = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);
        try {
            update = "" + ses.getAttribute("action");
            if (update.equals("update")) {
                st = con.prepareStatement("Update application set name=?, fname=?,addr=?,pincode=?,mname=?,phno=?,adarno=?,dob=?,gender=?,caste=?,ssc_passed=?,inter_passed=?,inter_marks=?,local=?,phc=?,exservice=?,trno=?,ssc_regno=?,ssc_board=?,ssc_year=?,ssc_month=? where regid::character varying='" + ses.getAttribute("regid") + "'");
                redirect = "update";
                ses.removeAttribute("action");
                ses.removeAttribute("regid");
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
            } else {
                st = con.prepareStatement("Insert into application(name,fname,addr,pincode,mname,phno,adarno,dob,gender,caste,ssc_passed,inter_passed,inter_marks,local,phc,exservice,trno,ssc_regno,ssc_board,ssc_year,ssc_month,phase,year) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,hstore('" + phse + "','true'),'" + year + "')");
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
            // st.setString(22, year);
           /* if (!update.equals("update")) {
             st.setBoolean(22, true);
             }*/
            st.executeUpdate();
        } catch (Exception ex) {
            //System.out.println("commit is changing here");
            commit = false;
            redirect = "failure";
        } finally {
            if (st != null) {
                st.close();
            }
        }
        //System.out.println("reached till 2nd query");

        Date temp2 = Date.valueOf(DBC.MyUtil.ChDate1(dob));
        String sql = "Select regid from application where name='" + name + "' and dob='" + temp2 + "'";
        System.out.println("sql issssssssssssssssss:" + sql);
        ResultSet rs = null;
        try {
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
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
        //ssc
        String first_lang_marks = formvariable.first_lang_marks, first_lang_grade = formvariable.first_lang_grade, first_lang_gpa = formvariable.first_lang_gpa, second_lang_marks = formvariable.second_lang_marks, second_lang_grade = formvariable.second_lang_grade, second_lang_gpa = formvariable.second_lang_gpa;
        String eng_marks = formvariable.eng_marks, eng_grade = formvariable.eng_grade, eng_gpa = formvariable.eng_gpa, maths_marks = formvariable.maths_marks, maths_grade = formvariable.maths_grade, maths_gpa = formvariable.maths_gpa;
        String science_marks = formvariable.science_marks, science_grade = formvariable.science_grade, science_gpa = formvariable.science_gpa, social_marks = formvariable.social_marks, social_grade = formvariable.social_grade, social_gpa = formvariable.social_gpa;
        String tot_marks = formvariable.tot_marks, tot_gpa = formvariable.tot_gpa;
        //inter
        String subject1_name = "", subject2_name = "", subject3_name = "";
        int subject1_marks = 0, subject2_marks = 0, subject3_marks = 0;
        String group = "";
        if (inter_passed) {
            group = formvariable.getGroup();
            subject1_name = formvariable.subject1_name;
            subject2_name = formvariable.subject2_name;
            subject3_name = formvariable.subject3_name;
            subject1_marks = formvariable.subject1_marks;
            subject2_marks = formvariable.subject2_marks;
            subject3_marks = formvariable.subject3_marks;
        }
        int index = 1;
        if (update.equals("update")) {
            // update cand marks
            sql = "Update cand_marks set ssc_first_lang_marks=?, ssc_first_lang_gpa=?, ssc_first_lang_grade=?,ssc_second_lang_marks=?, ssc_second_lang_gpa=?, ssc_second_lang_grade=?, ssc_eng_marks=?, ssc_eng_gpa=?, ssc_eng_grade=?, ssc_math_marks=?, ssc_math_gpa=?, ssc_math_grade=?, ssc_sci_marks=?, ssc_sci_gpa=?, ssc_sci_grade=?, ssc_social_marks=?, ssc_social_gpa=?, ssc_social_grade=?, inter_first_sub=?, inter_first_marks=?, inter_second_sub=?, inter_second_marks=?, inter_third_sub=?, inter_third_marks=?,ssc_tot_marks=?,ssc_tot_gpa=?,groupname=?,trno=" + trno + " where regid='" + regid + "'";
            System.out.println("update cand_marks query issssssss:" + sql);
            st = con.prepareStatement(sql);
           // String check_regid = beans.MyUtil.getOptionValue("img_cand_photos", "regid", "regid", regid, con);
           // String img_query = null;
           // String fileName = photo.getFileName();
            PreparedStatement ps = null;
//            if (!("").equals(fileName)) {
//                try {
//                    if (check_regid.equalsIgnoreCase(regid)) {
//                        img_query = "update img_cand_photos set pic=? where regid=?";
//                        ps = con.prepareStatement(img_query);
//
//                        ps.setBinaryStream(1, photo.getInputStream(), (int) photo.getFileSize());
//                        ps.setInt(2, Integer.parseInt(regid));
//                    } else {
//                        img_query = "insert into img_cand_photos values(?,?)";
//                        ps = con.prepareStatement(img_query);
//                        ps.setInt(1, Integer.parseInt(regid));
//                        ps.setBinaryStream(2, photo.getInputStream(), (int) photo.getFileSize());
//                    }
//
//                    if (commit) {
//                        ps.executeUpdate();
//                    }
//                } catch (Exception ex) {
//                    commit = false;
//                    //ex.printStackTrace();
//                } finally {
//                    ps.close();
//                }
//            }
        } else {
            sql = "INSERT INTO cand_marks(regid, ssc_first_lang_marks, ssc_first_lang_gpa, ssc_first_lang_grade,ssc_second_lang_marks, ssc_second_lang_gpa, ssc_second_lang_grade, ssc_eng_marks, ssc_eng_gpa, ssc_eng_grade, ssc_math_marks, ssc_math_gpa, ssc_math_grade, ssc_sci_marks, ssc_sci_gpa, ssc_sci_grade, ssc_social_marks, ssc_social_gpa, ssc_social_grade, inter_first_sub, inter_first_marks, inter_second_sub, inter_second_marks, inter_third_sub, inter_third_marks,ssc_tot_marks,ssc_tot_gpa,groupname,trno) VALUES (?, ?, ?, ?,?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?,?, ?, ?, ?,?,?,?," + trno + ")";
            st = con.prepareStatement(sql);
            String iti_code = formvariable.getIti_code();
            st.setInt(index++, Integer.parseInt(regid));
            // photo upload            
          //  String fileName = photo.getFileName();
//            if (!("").equals(fileName)) {
//                try {
//                    sql = "insert into img_cand_photos values(?,?)";
//                    pst = con.prepareStatement(sql);
//                    pst.setInt(1, Integer.parseInt(regid));
//                    pst.setBinaryStream(2, photo.getInputStream(), (int) photo.getFileSize());
//                    if (commit) {
//                        pst.executeUpdate();
//                    }
//                } catch (Exception ex) {
//                    commit = false;
//                    //ex.printStackTrace();
//                } finally {
//                    rs.close();
//                    pst.close();
//                }
//            }
            try {
                String insert_table = "trade_sel";
                String fieldname = "";
                String type = formvariable.getType();
                System.out.println("type issss====="+type);
                //String phase = "phase2e";
                String phase = "phase";
                //if (type.equals("iti")) {
                if (!type.equals("iti")) {
                    fieldname = "iti_code";
                } else {
                    fieldname = "dist_code";
                }
                String query = "insert into " + insert_table + " (regid," + fieldname + ",temp_code,trno," + phase + ",year) values('" + regid + "','" + iti_code + "','" + iti_code + "'," + trno + ",hstore('" + phse + "','true'),'" + year + "')";
                pst = con.prepareStatement(query);
                if (commit) {
                    //System.out.println("executing");
                    pst.executeUpdate();
                }
            } catch (Exception ex) {
                commit = false;
                pst.close();
            }
        }
        tot_marks = "0";
        if (first_lang_marks.length() < 1) {
            first_lang_marks = "0";
            second_lang_marks = "0";
            eng_marks = "0";
            maths_marks = "0";
            science_marks = "0";
            social_marks = "0";
        }
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
        try {
            //System.out.println("query is " + st.toString());
            //System.out.println("commit value is " + commit);
            //System.out.println("insert value is " + update);
            if (commit) {
                try {
                    st.executeUpdate();
                    //System.out.println("executed");
                } catch (Exception ex) {
                }
            }
        } catch (Exception ex) {
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
        }
        return mapping.findForward(redirect);
    }
}
