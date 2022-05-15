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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author APDCA
 */
public class OpeneditviewPageAction extends org.apache.struts.action.Action {

    @Override
    @SuppressWarnings("empty-statement")
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Edit_InterfaceForm formvariable = (Edit_InterfaceForm) form;

        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "student_application & student_cand_marks", "Insert", request);
        HttpSession ses = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        Connection con = null;
        boolean commit = true;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        String redirect = "success";

        try {
            String update = "";
            con = DBC.DBConnection.getConnectionGen();
            int regid = Integer.parseInt("" + ses.getAttribute("regid"));
            con.setAutoCommit(false);

            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
            String phse = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);

            long mills = System.currentTimeMillis();
            Timestamp entry_date = new Timestamp(mills);

            update = beans.MyUtil.filterBad("" + ses.getAttribute("action"));

            if (update.equals("update")) {

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

//            else {
//                st = con.prepareStatement("Insert into student_application(name,fname,addr,pincode,mname,phno,adarno,dob,gender,caste,ssc_passed,inter_passed,inter_marks,local,phc,exservice,trno,ssc_regno,ssc_board,ssc_year,ssc_month,phase,year,app_status) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,hstore('" + phse + "','true'),'" + year + "','F')");
//                    System.out.println("sql 1212121212:" + st);
//
//            }
                System.out.println("sjdghkhkghksdgsd" + formvariable.getAddrs_state_p().length);
                String Saloc[] = formvariable.getAddrs_state_p();

                //String dist_name[]=formvariable.getAddrs_district_p();
                //System.out.println("dfkljglfdg--->"+formvariable.getAddrs_district_p());
                //String addrs_mandal_p[]=formvariable.getAddrs_mandal_p();
                if (formvariable.getAddrs_state_p() != null) {
                    int locLength = formvariable.getAddrs_state_p().length;
                    String insert_table = "student_trade_sel";
                    String fieldname = "";
                    String type = formvariable.getType();
                    //String phase = "phase2e";
                    String phase = "phase";
                    //if (type.equals("iti")) {
                    //if (type.equals("iti")) {
                    //  fieldname = "iti_code";
                    //} else {
                    fieldname = "dist_code";
                    //}
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
                        if (Saloc[i].trim().isEmpty()) {
                        } else {
                            System.out.println("fdjkgnkdfngkdfnkgndfkgndf--->" + Saloc[i] + "knjkkjknknkn" + Saloc[i].trim().isEmpty());
                            if (Saloc[i].trim() != null || !Saloc[i].trim().equalsIgnoreCase("") || !Saloc[i].equalsIgnoreCase(" ") || !Saloc[i].trim().isEmpty()) {
                                String state_code = Saloc[i];

                                if (state_code.equals("")) {
                                } else {
                                    //System.out.println("code is->"+state_code+"district is--->"+district+"mandal is--->"+mandal);
                                    String query = "insert into " + insert_table + " (regid," + fieldname + ",temp_code,trno," + phase + ",year) values('" + regid + "',?,?," + trno + ",hstore('" + phse + "','true'),'" + year + "')";
                                    //String query="INSERT INTO public.multiple_values(state_code, dist_code, mandal_code)VALUES (?, ?, ?)";
                                    pst = con.prepareStatement(query);
                                    pst.setString(1, state_code);
                                    pst.setString(2, state_code);

                                    if (commit) {

                                        System.out.println("executing");
                                        pst.executeUpdate();

                                        String updquery = "update student_application set entry_date=? where regid=?";
                                        pst = con.prepareStatement(updquery);
                                        pst.setTimestamp(1, entry_date);
                                        pst.setInt(2, regid);
                                        pst.executeUpdate();
                                    }
                                }
                            }
                        }

                    }
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
        }

        System.out.println("cxvkl;cxvbkc;xlkb;lkf;lhkgfdhdf");
        return mapping.findForward(redirect);
    }

}
