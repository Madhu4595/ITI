/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  DBC.DBConnection
 *  DBC.RoleBasedOperations
 *  DBC.generic
 *  beans.MyUtil
 *  com.nic.CheckedAdmissionDetails
 *  com.nic.ItiAdmissionForm
 *  com.nic.ItiAdmissionProcessForm
 *  com.nic.RegistrationDao
 *  com.nic.TradeForm
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  org.apache.struts.action.Action
 *  org.apache.struts.action.ActionForm
 *  org.apache.struts.action.ActionForward
 *  org.apache.struts.action.ActionMapping
 *  org.apache.struts.upload.FormFile
 */
package com.nic;

import DBC.DBConnection;
import DBC.RoleBasedOperations;
import DBC.generic;
import beans.MyUtil;
import com.nic.CheckedAdmissionDetails;
import com.nic.ItiAdmissionForm;
import com.nic.ItiAdmissionProcessForm;
import com.nic.RegistrationDao;
import com.nic.TradeForm;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class ItiAdmissionAction
extends Action {
    private static RegistrationDao registrationDao = new RegistrationDao();
    private ItiAdmissionForm itiAdmissionForm2;
    private TradeForm tradeForm;
    private ItiAdmissionForm itiAdmissionForm;
    private static String adm_num;

    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.itiAdmissionForm = (ItiAdmissionForm)form;
        
        HttpSession httpSession = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+httpSession.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        int adm_regid = (Integer)httpSession.getAttribute("adm_regid");
        String adm_merit_number = beans.MyUtil.filterBad(""+httpSession.getAttribute("adm_merit_number"));
        String adm_iti_name =  beans.MyUtil.filterBad(""+httpSession.getAttribute("adm_iti_name"));
        String adm_gpa = (String)httpSession.getAttribute("adm_gpa");
        
        httpSession.removeAttribute("adm_regid");
        httpSession.removeAttribute("adm_merit_number");
        httpSession.removeAttribute("adm_iti_name");
        
        this.itiAdmissionForm2 = registrationDao.getApplicationDetails(adm_regid, request);
        this.itiAdmissionForm2.setIti_code((String)httpSession.getAttribute("ins_code"));
        this.itiAdmissionForm2.setIti_name(adm_iti_name);
        this.itiAdmissionForm2.setMeritNumber(adm_merit_number);
        this.itiAdmissionForm2.setGpt(adm_gpa);
        String[] tradeCode_check = this.itiAdmissionForm.getTrade_code().split("\\+");
        this.itiAdmissionForm2.setTrade_code(tradeCode_check[0]);
        String check = tradeCode_check[1];
        this.itiAdmissionForm = this.itiAdmissionForm2;
        String gpt = registrationDao.getGpt(adm_regid, request);
        this.itiAdmissionForm.setGpt(gpt);
        
        String message = "";
        String iti_code = "" + httpSession.getAttribute("ins_code");
        Object rs = null;
        Object con = null;
       
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs1 = null;
        Connection con1 = null;
        
        String category_code = null;
        String category = null;
        String caste_code = null;

        String year_con = null;
        String permission = null;
        String cast = null;
        con1 = DBC.DBConnection.getConnection(request);
        
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con1);
        String query = "select * from generalpool where iti_code=?";

        ps = con1.prepareStatement(query);
        ps.setString(1, iti_code);
        rs1 = ps.executeQuery(query);
        if (rs1.next()) {
            year_con = rs1.getString("year");
            permission = rs1.getString("genpool");
            cast = rs1.getString("caste");
        }
        if ((year_con.equalsIgnoreCase(year.trim())) && (permission.equalsIgnoreCase("T"))){
            caste_code = cast;


 int checkVacencies = registrationDao.checkForVacencies(itiAdmissionForm.getIti_code(), Integer.parseInt(itiAdmissionForm.getTrade_code()), caste_code, request);
 if (checkVacencies > 0) {
        category_code = caste_code;
         itiAdmissionForm.setRes_category(category_code);
 }else{ 
     if (caste_code.contains("-")) {
                category_code = caste_code + "W";
            } else {
                category_code = caste_code + "-W";
            }
            itiAdmissionForm.setRes_category(category_code);
     }
 
 
 
        } else  if (check.equals("1")) {
            this.itiAdmissionForm.setRes_category("OC");
        } else if (check.equals("2")) {
            this.itiAdmissionForm.setRes_category("OC-W");
        } else if (check.equals("3")) {
            this.itiAdmissionForm.setRes_category(this.itiAdmissionForm.getCast());
        } else if (check.equals("4")) {
            if(this.itiAdmissionForm.getRes_category().contains("-")){
                this.itiAdmissionForm.setRes_category(this.itiAdmissionForm.getCast() + "W");
            }else{
                this.itiAdmissionForm.setRes_category(this.itiAdmissionForm.getCast() + "-W");
           }            
        } else if (check.equals("5")) {
            this.itiAdmissionForm.setRes_category("PH");
        } else if (check.equals("6")) {
            this.itiAdmissionForm.setRes_category("PH-W");
        } else if (check.equals("7")) {
            this.itiAdmissionForm.setRes_category("EX-S");
        } else if (check.equals("8")) {
            this.itiAdmissionForm.setRes_category("EX-SW");
        }
        this.tradeForm = registrationDao.getTradeDetails(this.itiAdmissionForm.getTrade_code(), request);
        this.itiAdmissionForm.setTrade_short(this.tradeForm.getTrade_short());
        this.itiAdmissionForm.setTrade_name(this.tradeForm.getTrade_name());
        
        java.util.Date date = new java.util.Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String display_date = simpleDateFormat.format(date);
        String original_date = simpleDateFormat1.format(date);
        String display_dobD = simpleDateFormat.format(Date.valueOf(this.itiAdmissionForm.getDob()));
        
        this.itiAdmissionForm.setAcad_year(original_date.substring(0, 4));
        this.itiAdmissionForm.setCurrentDate(Date.valueOf(original_date));
        
        request.setAttribute("display_date", (Object)display_date);
        request.setAttribute("display_dobD", (Object)display_dobD);
        
        CheckedAdmissionDetails checkedAdmissionDetails = registrationDao.checkAdmited_or_Not(this.itiAdmissionForm.getRegid(), request);
        if (checkedAdmissionDetails == null) {
            RegistrationDao registrationDao = new RegistrationDao();
            int count = registrationDao.getAdm_numCount(this.itiAdmissionForm, request);
            count++;
            
            String year_of_admission = RoleBasedOperations.Current_AcademicYear((HttpServletRequest)request);
           /* if (count == 0) {
                adm_num = this.itiAdmissionForm.getIti_code() + this.itiAdmissionForm.getTrade_short() + this.itiAdmissionForm.getAcad_year().substring(2) + "001";
                System.out.println("generated adm_num 0 is-->" +adm_num);
                this.itiAdmissionForm.setAdm_num(adm_num);
            } else */if (count <= 9) {
                adm_num = this.itiAdmissionForm.getIti_code() + this.itiAdmissionForm.getTrade_short() + this.itiAdmissionForm.getAcad_year().substring(2) + "00" + count;
                System.out.println("generated adm_num 0 is-->" +adm_num);
                this.itiAdmissionForm.setAdm_num(adm_num);
            } else if (count <= 99) {
                adm_num = this.itiAdmissionForm.getIti_code() + this.itiAdmissionForm.getTrade_short() + this.itiAdmissionForm.getAcad_year().substring(2) + "0" + count;
                System.out.println("generated adm_num 0 is-->" +adm_num);
                this.itiAdmissionForm.setAdm_num(adm_num);
            }
            String dist_code = iti_code.substring(0, 2);
            message = message + "<br><br>Generated Admission Number is :: " + this.itiAdmissionForm.getAdm_num() + " and ITI Code is " + this.itiAdmissionForm.getIti_code() + " and District Code is " + dist_code;
            httpSession.setAttribute("message", (Object)message);
            try {
                if (!this.itiAdmissionForm.getIti_code().substring(0, 2).equalsIgnoreCase(dist_code)) {
                    return mapping.findForward("itiAdmissionFailed");
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                httpSession.setAttribute("message", (Object)(ex + message));
                return mapping.findForward("itiAdmissionFailed");
            }finally{
                if(st!=null)
                {
                    st.close();
                }if(rs1!=null)
                {
                    rs1.close();
                }
                if(con1!=null)
                {
                    con1.close();
                }
            }
            Connection connection = DBConnection.getConnection((HttpServletRequest)request);
            HttpSession session = request.getSession(true);
            String statecode = beans.MyUtil.filterBad("" + session.getAttribute("statecode"));
            
            String trade_code = MyUtil.getOptionValue((String)"ititrade_master", (String)"trade_short", (String)"trade_code", (String)this.itiAdmissionForm.getTrade_short(), (Connection)connection);
            String dget_tradecode = MyUtil.getOptionValue((String)"dget_trademaster", (String)"trade_code", (String)"dget_tradecode", (String)trade_code, (Connection)connection);
            String noofsems = MyUtil.getOptionValue((String)"dget_trademaster", (String)"trade_code", (String)"noofsem", (String)trade_code, (Connection)connection);
            String year_adm = year_of_admission.substring(2, 4);
            
            request.setAttribute("adm_num", (Object)adm_num);
            request.setAttribute("itiAdmissionForm", (Object)this.itiAdmissionForm);
            
            int checkVacencies = registrationDao.checkForVacencies(this.itiAdmissionForm.getIti_code(), Integer.parseInt(this.itiAdmissionForm.getTrade_code()), this.itiAdmissionForm.getRes_category(), request);
            int xyz = 500;
            if (checkVacencies > 0) {
                ItiAdmissionForm fm;
                int trno = generic.logTrace((String)((String)request.getSession().getAttribute("userName")), (String)"iti_admissions", (String)"Insert", (HttpServletRequest)request);
                fm = (ItiAdmissionForm)form;
                String idmarks1 = beans.MyUtil.filterBad(fm.getIdmarks1());
                String idmarks2 = beans.MyUtil.filterBad(fm.getIdmarks2());
                String ssc_regno = beans.MyUtil.filterBad(fm.getSsc_regno());
                String ssc_board = beans.MyUtil.filterBad(fm.getSsc_board());
                String ssc_year = beans.MyUtil.filterBad(fm.getSsc_year());
                String ssc_month = beans.MyUtil.filterBad(fm.getSsc_month());
                String type_admission = beans.MyUtil.filterBad(fm.getType_admission());
                String sem_nonsem = "";
                sem_nonsem = type_admission.trim().equalsIgnoreCase("N") ? "SEM" : "NONSEM";
                
                PreparedStatement preparedStatementInsert = null;
                
                PreparedStatement preparedStatementUpdate = null;
                String admnum = this.itiAdmissionForm.getAdm_num();
                String fromd_training = RoleBasedOperations.getDurationFromPeriod((String)admnum);
                String tod_training = RoleBasedOperations.getDurationToPeriod((String)admnum, (HttpServletRequest)request);
                
                connection = DBConnection.getConnection((HttpServletRequest)request);
                connection.setAutoCommit(false);
                
                year = MyUtil.getOptionValue((String)"iti_params", (String)"code", (String)"value", (String)"7", (Connection)connection);
                String phase = MyUtil.getOptionValue((String)"admissions.admission_phase", (String)"year", (String)"phase", (String)year, (Connection)connection);
                String caste = MyUtil.getOptionValue((String)"application", (String)"regid", (String)"caste", (String)("" + this.itiAdmissionForm.getRegid()), (Connection)connection);
                String exser = MyUtil.getOptionValue((String)"application", (String)"regid", (String)"caste", (String)("" + this.itiAdmissionForm.getRegid()), (Connection)connection);
                String SqlInsert = "insert into admissions.iti_admissions (regid,name,fname,mname,addr,phno,adarno,gender,caste,ssc_passed,inter_passed,inter_marks,local,pincode,dob,phc,exservice,iti_code,trade_code,res_category,adm_num,year_of_admission,trno,dist_code,mode_adm,fromd_training,tod_training,idmarks1,idmarks2,ssc_regno,ssc_board,ssc_year,ssc_month,date_of_admission,type_admission,phase,sem_nonsem,dgettrade_code) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'" + MyUtil.ChDate((String)fromd_training) + "','" + MyUtil.ChDate((String)tod_training) + "','" + idmarks1 + "','" + idmarks2 + "','" + ssc_regno + "','" + ssc_board + "','" + ssc_year + "','" + ssc_month + "','" + original_date + "','" + type_admission + "','" + phase + "','" + sem_nonsem + "','" + dget_tradecode + "')";
                String SqlUpdate = "update  iti_seatmatrix set strength_fill=strength_fill || hstore(Array['" + this.itiAdmissionForm.getRes_category() + "'],Array[((strength_fill->'" + this.itiAdmissionForm.getRes_category() + "')::integer+1)::text]),strength_vacant=strength_vacant || hstore(Array['" + this.itiAdmissionForm.getRes_category() + "'],Array[((strength_vacant->'" + this.itiAdmissionForm.getRes_category() + "')::integer-1)::text]) where iti_code=? and trade_code=? ";
                int updateSeatmatrix = 0;
                try {
                    preparedStatementInsert = connection.prepareStatement(SqlInsert);
                    String mode_adm = "Convenor of the Sellection comitte";
                    preparedStatementInsert.setInt(1, this.itiAdmissionForm.getRegid());
                    preparedStatementInsert.setString(2, this.itiAdmissionForm.getName());
                    preparedStatementInsert.setString(3, this.itiAdmissionForm.getFatherName());
                    preparedStatementInsert.setString(4, this.itiAdmissionForm.getMname());
                    preparedStatementInsert.setString(5, this.itiAdmissionForm.getAddress());
                    if (this.itiAdmissionForm.getPhoneNumber() != null && this.itiAdmissionForm.getPhoneNumber() != "") {
                        preparedStatementInsert.setLong(6, Long.parseLong(this.itiAdmissionForm.getPhoneNumber()));
                    } else {
                        preparedStatementInsert.setLong(6, 0);
                    }
                    preparedStatementInsert.setString(7, this.itiAdmissionForm.getAdarNo());
                    preparedStatementInsert.setString(8, this.itiAdmissionForm.getSex());
                    preparedStatementInsert.setString(9, caste);
                    preparedStatementInsert.setBoolean(10, this.itiAdmissionForm.getSsc_passed());
                    preparedStatementInsert.setBoolean(11, this.itiAdmissionForm.getInterPassed());
                    preparedStatementInsert.setInt(12, this.itiAdmissionForm.getInter_marks());
                    preparedStatementInsert.setString(13, this.itiAdmissionForm.getLocal());
                    preparedStatementInsert.setInt(14, this.itiAdmissionForm.getPin_code());
                    Date date1 = Date.valueOf(this.itiAdmissionForm.getDob());
                    preparedStatementInsert.setDate(15, date1);
                    if (this.itiAdmissionForm.getPhc().equals("yes")) {
                        preparedStatementInsert.setBoolean(16, true);
                    } else {
                        preparedStatementInsert.setBoolean(16, false);
                    }
                    if (this.itiAdmissionForm.getExservice().equals("yes")) {
                        preparedStatementInsert.setBoolean(17, true);
                    } else {
                        preparedStatementInsert.setBoolean(17, false);
                    }
                    preparedStatementInsert.setString(18, this.itiAdmissionForm.getIti_code());
                    preparedStatementInsert.setInt(19, Integer.parseInt(this.itiAdmissionForm.getTrade_code()));
                    preparedStatementInsert.setString(20, this.itiAdmissionForm.getRes_category());
                    preparedStatementInsert.setString(21, this.itiAdmissionForm.getAdm_num());
                    preparedStatementInsert.setString(22, year_of_admission);
                    preparedStatementInsert.setInt(23, trno);
                    preparedStatementInsert.setString(24, dist_code);
                    preparedStatementInsert.setString(25, mode_adm);
                    int i = preparedStatementInsert.executeUpdate();
                    preparedStatementUpdate = connection.prepareStatement(SqlUpdate);
                    preparedStatementUpdate.setString(1, this.itiAdmissionForm.getIti_code());
                    preparedStatementUpdate.setInt(2, Integer.parseInt(this.itiAdmissionForm.getTrade_code()));
                    updateSeatmatrix = preparedStatementUpdate.executeUpdate();
                    connection.commit();
                    preparedStatementInsert.close();
                    preparedStatementUpdate.close();
                    connection.close();
                }
                catch (Exception e) {
                    xyz = 0;
                    connection.rollback();
                    if (preparedStatementInsert != null) {
                        preparedStatementInsert.close();
                    }
                    if (preparedStatementUpdate != null) {
                        preparedStatementUpdate.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                }
                finally {
                    if (preparedStatementInsert != null) {
                        preparedStatementInsert.close();
                    }
                    if (preparedStatementUpdate != null) {
                        preparedStatementUpdate.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                }
                String yearofadm = RoleBasedOperations.Current_AcademicYear((HttpServletRequest)request);
                try {
                    if (fm.getPic().getFileSize() > 0) {
                        DBConnection.insertPhoto_With_MultipleFields((String)"exams.img_cand_photos_exams", (String)"image", (FormFile)fm.getPic(), (String)("adm_num,year_of_admission$'" + adm_num + "','" + yearofadm + "'"), (HttpServletRequest)request);
                    } else {
                        String qry = "insert into exams.img_cand_photos_exams(adm_num,image,year_of_admission) select ?,pic,? from img_cand_photos where regid=?";
                        ps = connection.prepareStatement(qry);
                        ps.setString(1, adm_num);
                        ps.setString(2, yearofadm);
                        ps.setInt(3, adm_regid);
                        try {
                            int p = ps.executeUpdate();
                            ps.close();
                            connection.close();
                        }
                        catch (Exception ex) {
                            ps.close();
                            connection.close();
                        }
                    }
                }
                catch (NullPointerException qry) {
                }
                String tab = "conviner_login";
                String code = "dist_code";
                String name = "dist_name";
                String val = (String)request.getSession().getAttribute("ins_code");
                String distname = null;
                if (xyz == 500) {
                    distname = MyUtil.getOptionValue((String)tab, (String)code, (String)name, (String)val, (HttpServletRequest)request);
                    request.setAttribute("distname", (Object)distname);
                    return mapping.findForward("itiAdmissionSuccess");
                }
                return mapping.findForward("itiAdmissionFailed");
            }
        } else {
            ArrayList councilMerits = null;
            ItiAdmissionProcessForm itiAdmissionProcessForm = new ItiAdmissionProcessForm();
            councilMerits = registrationDao.getCouncilMerits("dist_code", this.itiAdmissionForm.getCurrentDate(), (String)httpSession.getAttribute("ins_code"), request);
            if (councilMerits == null) {
                return mapping.findForward("itiAdmissionEntryFailed");
            }
            itiAdmissionProcessForm.setMerit_from((Integer)councilMerits.get(0));
            itiAdmissionProcessForm.setMerit_to((Integer)councilMerits.get(1));
            itiAdmissionProcessForm.setCurrentDate(this.itiAdmissionForm.getCurrentDate());
            
            request.setAttribute("itiAdmissionProcessForm", (Object)itiAdmissionProcessForm);
            request.setAttribute("checkedAdmissionDetails", (Object)checkedAdmissionDetails);
            
            return mapping.findForward("itiAdmissionAlreadyFound");
        }
        return null;
    }
}
