/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 
    DistrictAdmissionAction
 */
package com.nic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Gopi Daggumalle
 */
public class DistrictAdmissionAction extends org.apache.struts.action.Action {

    private static RegistrationDao registrationDao;
    private TradeForm tradeForm;
    private ItiAdmissionForm itiAdmissionForm;
    private ItiAdmissionForm itiAdmissionForm2;
    private static String adm_num;

    static {
        registrationDao = new RegistrationDao();
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        itiAdmissionForm = (ItiAdmissionForm) form;
        String message = "";
        
        HttpSession httpSession = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+httpSession.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        String dist_code = beans.MyUtil.filterBad("" + httpSession.getAttribute("ins_code"));
        int adm_regid = Integer.parseInt( ""+httpSession.getAttribute("adm_regid"));
        request.setAttribute("regid",adm_regid);
        
        String adm_merit_number =  beans.MyUtil.filterBad(""+httpSession.getAttribute("adm_merit_number"));
        String adm_gpa = beans.MyUtil.filterBad(""+httpSession.getAttribute("adm_gpa"));
       
        itiAdmissionForm2 = registrationDao.getApplicationDetails(adm_regid, request);
        itiAdmissionForm2.setMeritNumber(adm_merit_number);

        String tradeCode_check[] = itiAdmissionForm.getTrade_code().split("\\+");

        message = message + "<br><br>" + itiAdmissionForm.getTrade_code() + "<br><br>";

        itiAdmissionForm2.setIti_name(tradeCode_check[0]);
        itiAdmissionForm2.setIti_code(tradeCode_check[1]);
        itiAdmissionForm2.setTrade_code(tradeCode_check[2]);

        message = message + "<br>Iti name " + tradeCode_check[0] + "<br>";
        message = message + "Iti Code " + tradeCode_check[1] + "<br>";
        message = message + "Trade code " + tradeCode_check[2] + "<br>";
        message = message + "Reservation Category " + tradeCode_check[3] + "<br>";

        itiAdmissionForm2.setGpt(adm_gpa);
        String check = tradeCode_check[3];
        itiAdmissionForm = itiAdmissionForm2;
        String gpt = registrationDao.getGpt(adm_regid, request);
        itiAdmissionForm.setGpt(gpt);
        
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        
        try {
            System.out.println("checkcheckcheckcheckcheck" + check + "####" + itiAdmissionForm.getCast());
            String category_code = null;
            String category = null;
            String caste_code = null;

            String year_con = null;
            String permission = null;      
            String cast = null;
            con = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
            
            String query = "select * from generalpool.open_general_pool where dist_code=?";
            System.out.println("query" + query);
            ps = con.prepareStatement(query);
            ps.setString(1, dist_code);
            rs = ps.executeQuery();
            if (rs.next()) {
                year_con = rs.getString("year");
                permission = rs.getString("permission");
                cast = rs.getString("caste");
        System.out.println("11111111"+cast);
            }else{System.out.println("not in general pool");}
            if ((year_con.equalsIgnoreCase(year.trim())) && (permission.equalsIgnoreCase("T"))) {
                System.out.println("in year if");
                caste_code = cast;

                int checkVacencies = registrationDao.checkForVacencies(itiAdmissionForm.getIti_code(), Integer.parseInt(itiAdmissionForm.getTrade_code()), caste_code, request);
                if (checkVacencies > 0) {
                    System.out.println("vacancies are available for particular caste");
                    category_code = caste_code;
                    itiAdmissionForm.setRes_category(category_code);
                } else {
                    System.out.println("else:::--> vacancies are not available for particular caste");
                    if (caste_code.contains("-")) {
                        category_code = caste_code + "W";
                    } else {
                        category_code = caste_code + "-W";
                    }
                    System.out.println("in year if");
                    itiAdmissionForm.setRes_category(category_code);
                }
            } else if (check.equals("1")) {
                System.out.println("11111111111111111111111");
                itiAdmissionForm.setRes_category("OC");
            } else if (check.equals("2")) {
                System.out.println("222222222222222222222222");
                itiAdmissionForm.setRes_category("OC-W");
            } else if (check.equals("3")) {
                System.out.println("33333333333333333333333333");
                itiAdmissionForm.setRes_category(itiAdmissionForm.getCast());
            } else if (check.equals("4")) {
                caste_code=itiAdmissionForm.getCast();
                if (caste_code.contains("-")) {
                        category_code = caste_code + "W";
                    } else {
                        category_code = caste_code + "-W";
                    }
                System.out.println("44444444444444444444");
                    itiAdmissionForm.setRes_category(category_code);


            } else if (check.equals("5")) {
                System.out.println("55555555555555555");
                itiAdmissionForm.setRes_category("PH");
            } else if (check.equals("6")) {
                System.out.println("666666666666666666666666");
                itiAdmissionForm.setRes_category("PH-W");
            } else if (check.equals("7")) {
                System.out.println("67777777777777777777777");
                itiAdmissionForm.setRes_category("EX-S");
            } else if (check.equals("8")) {
                System.out.println("888888888888888888888888");
                itiAdmissionForm.setRes_category("EX-SW");
            }
            con.close();
            tradeForm = registrationDao.getTradeDetails(tradeCode_check[2], request);
            itiAdmissionForm.setTrade_short(tradeForm.getTrade_short());
            itiAdmissionForm.setTrade_name(tradeForm.getTrade_name());

            message = "<br>" + message + " trade short" + itiAdmissionForm.getTrade_short();
            System.out.println("test this once");

            java.util.Date date = new java.util.Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            String display_date = simpleDateFormat.format(date);
            String original_date = simpleDateFormat1.format(date);
            String display_dobD = simpleDateFormat.format(java.sql.Date.valueOf(itiAdmissionForm.getDob()));
            
            itiAdmissionForm.setAcad_year(original_date.substring(0, 4));
            itiAdmissionForm.setCurrentDate(java.sql.Date.valueOf(original_date));
            
            request.setAttribute("display_date", display_date);
            request.setAttribute("display_dobD", display_dobD);

            CheckedAdmissionDetails checkedAdmissionDetails = registrationDao.checkAdmited_or_Not(itiAdmissionForm.getRegid(), request);
            if (checkedAdmissionDetails == null) {
                RegistrationDao registrationDao = new RegistrationDao();
                int count = registrationDao.getAdm_numCount(itiAdmissionForm, request);
                count++;
                String year_of_admission = DBC.RoleBasedOperations.Current_AcademicYear(request);
                if (count == 0) {
                    adm_num = itiAdmissionForm.getIti_code() + itiAdmissionForm.getTrade_short() + year_of_admission.substring(2) + "001";
                    message = message + "<br> hello my number is:: " + adm_num + "<br>";
                    System.out.println("generated adm_num 0 is-->" + adm_num);
                    itiAdmissionForm.setAdm_num(adm_num);
                } else if (count <= 9) {
                    adm_num = itiAdmissionForm.getIti_code() + itiAdmissionForm.getTrade_short() + year_of_admission.substring(2) + "00" + count;
                    message = message + "<br> hello my number is:: " + adm_num + "<br>";
                    System.out.println("generated adm_num 9 is-->" + adm_num);
                    itiAdmissionForm.setAdm_num(adm_num);
                } else if (count <= 99) {
                    adm_num = itiAdmissionForm.getIti_code() + itiAdmissionForm.getTrade_short() + year_of_admission.substring(2) + "0" + count;
                    message = message + "<br> hello my number is:: " + adm_num + "<br>";
                    System.out.println("generated adm_num 99 is-->" + adm_num);
                    itiAdmissionForm.setAdm_num(adm_num);
                } else if (count <= 999) {
                    adm_num = itiAdmissionForm.getIti_code() + itiAdmissionForm.getTrade_short() + year_of_admission.substring(2) + count;
                    message = message + "<br> hello my number 999 is:: " + adm_num + "<br>";
                    System.out.println("generated adm_num is-->" + adm_num);
                    itiAdmissionForm.setAdm_num(adm_num);
                }
                message = message + "<br><br>Generated Admission Number is :: " + itiAdmissionForm.getAdm_num() + " and ITI Code is " + itiAdmissionForm.getIti_code() + " and District Code is " + dist_code;
                httpSession.setAttribute("message", message);

                System.out.println("iti code after substring-->" + itiAdmissionForm.getIti_code().substring(0, 2));
                try {
                    if (!itiAdmissionForm.getIti_code().substring(0, 2).equalsIgnoreCase(dist_code)) {
                        System.out.println("iti substringgg" + itiAdmissionForm.getIti_code().substring(0, 2) + "district codesssssss" + dist_code);
                        return mapping.findForward("itiAdmissionFailed");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    httpSession.setAttribute("message", ex + message);
                    System.out.println("iti substringgg" + itiAdmissionForm.getIti_code().substring(0, 2) + "district codesssssss" + dist_code);
                    return mapping.findForward("itiAdmissionFailed");
                }

                Connection connection = DBC.DBConnection.getConnection(request);
                HttpSession session = request.getSession(true);
                String statecode = "28";
               // String statecode = "" + session.getAttribute("statecode");
                String trade_code = beans.MyUtil.getOptionValue("ititrade_master", "trade_short", "trade_code", itiAdmissionForm.getTrade_short(), connection);
                String dget_tradecode = beans.MyUtil.getOptionValue("dget_trademaster", "trade_code", "dget_tradecode", trade_code, connection);
                String noofsems = beans.MyUtil.getOptionValue("dget_trademaster", "trade_code", "noofsem", trade_code, connection);
                String year_adm = year_of_admission.substring(2, 4);

                String sql111 = "select max(substring(htno,11,4)) from admissions.iti_admissions  where dist_code=? and sem_nonsem='SEM' and  dgettrade_code=? and year_of_admission=?";
                System.out.println(sql111);
                ps = connection.prepareStatement(sql111);
                ps.setString(1, dist_code);
                ps.setString(2, dget_tradecode);
                ps.setString(3, year_of_admission);
                ResultSet rsset = ps.executeQuery();
                String slno = "0";
                int k = 0;
                if (rsset.next()) {
                    slno = rsset.getString(1);
                }
                if (slno != null) {
                    k = Integer.parseInt(slno);
                }
                k = k + 1;
                if (k < 10) {
                    slno = "000" + k;
                } else if (k < 100 && k >= 10) {
                    slno = "00" + k;
                } else if (k < 1000 && k >= 100) {
                    slno = "0" + k;
                } else {
                    slno = "" + k;
                }
                String htno = statecode + dget_tradecode + noofsems + year_adm + dist_code + slno;
System.out.println("htno---->"+htno);
                try {
                    ps.close();
                    rsset.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                } finally {
                    if (connection != null) {
                        connection.close();
                    }

                }

                System.out.println("hellooooooogopi");

                request.setAttribute("adm_num", adm_num);
                request.setAttribute("itiAdmissionForm", itiAdmissionForm);

                System.out.println("####################33" + itiAdmissionForm.getRes_category());
                int checkVacencies = registrationDao.checkForVacencies(itiAdmissionForm.getIti_code(), Integer.parseInt(itiAdmissionForm.getTrade_code()), itiAdmissionForm.getRes_category(), request);
                int xyz = 500;

                System.out.println("caste and other values-->");
                System.out.println("checkVacencies--->" + checkVacencies);
                if (checkVacencies > 0) {
                    int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "iti_admissions", "Insert", request);

                    String idmarks1, idmarks2, ssc_regno, ssc_board, ssc_year, ssc_month;
                    ItiAdmissionForm fm = (ItiAdmissionForm) form;
                    idmarks1 = beans.MyUtil.filterBad(fm.getIdmarks1());
                    idmarks2 =  beans.MyUtil.filterBad(fm.getIdmarks2());
                    ssc_regno =  beans.MyUtil.filterBad(fm.getSsc_regno());
                    ssc_board =  beans.MyUtil.filterBad(fm.getSsc_board());
                    ssc_year =  beans.MyUtil.filterBad(fm.getSsc_year());
                    ssc_month =  beans.MyUtil.filterBad(fm.getSsc_month());
                    String type_admission =  beans.MyUtil.filterBad(fm.getType_admission());
                    
                    String sem_nonsem = "";
                    if (type_admission.trim().equalsIgnoreCase("N")) {
                        sem_nonsem = "SEM";
                    } else {
                        sem_nonsem = "NONSEM";
                    }
                    System.out.println("hi");
                    PreparedStatement preparedStatementInsert = null;
                    PreparedStatement preparedStatementUpdate = null;
                    System.out.println("hi");
                    String admnum = itiAdmissionForm.getAdm_num();
                    String fromd_training = DBC.RoleBasedOperations.getDurationFromPeriod(admnum);
                    String tod_training = DBC.RoleBasedOperations.getDurationToPeriod(admnum, request);
                    connection = DBC.DBConnection.getConnection(request);
                    connection.setAutoCommit(false);
                    System.out.println("year is :: " + year);
                    String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
                    System.out.println("PHASE IS " + phase);
                    String caste = beans.MyUtil.getOptionValue("application", "regid", "caste", "" + itiAdmissionForm.getRegid(), connection);
                    System.out.println("caste is :: " + caste);
                    String exser = beans.MyUtil.getOptionValue("application", "regid", "caste", "" + itiAdmissionForm.getRegid(), connection);
                    String SqlInsert = "insert into admissions.iti_admissions (regid,name,fname,mname,addr,phno,adarno,gender,caste,ssc_passed,inter_passed,inter_marks,local,pincode,dob,phc,exservice,iti_code,trade_code,res_category,adm_num,year_of_admission,trno,dist_code,mode_adm,fromd_training,tod_training,idmarks1,idmarks2,ssc_regno,ssc_board,ssc_year,ssc_month,date_of_admission,type_admission,phase,htno,sem_nonsem,dgettrade_code) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'" + beans.MyUtil.ChDate(fromd_training) + "','" + beans.MyUtil.ChDate(tod_training) + "','" + idmarks1 + "','" + idmarks2 + "','" + ssc_regno + "','" + ssc_board + "','" + ssc_year + "','" + ssc_month + "','" + original_date + "','" + type_admission + "','" + phase + "','" + htno + "','" + sem_nonsem + "','" + dget_tradecode + "')";
                    System.out.println("insert queryyyyyyy is :: " + SqlInsert);
                    String SqlUpdate = "update  iti_seatmatrix set strength_fill=strength_fill || hstore(Array['" + itiAdmissionForm.getRes_category() + "'],Array[((strength_fill->'" + itiAdmissionForm.getRes_category() + "')::integer+1)::text]),strength_vacant=strength_vacant || hstore(Array['" + itiAdmissionForm.getRes_category() + "'],Array[((strength_vacant->'" + itiAdmissionForm.getRes_category() + "')::integer-1)::text]) where iti_code=? and trade_code=? ";
                    System.out.println("seat matrix queryyyyyyy is :: " + SqlUpdate);
                    int updateSeatmatrix = 0;
                    try {

                        preparedStatementInsert = connection.prepareStatement(SqlInsert);
                        String mode_adm = "Convenor of the Sellection comitte";

                        preparedStatementInsert.setInt(1, itiAdmissionForm.getRegid());
                        preparedStatementInsert.setString(2, itiAdmissionForm.getName());
                        preparedStatementInsert.setString(3, itiAdmissionForm.getFatherName());
                        preparedStatementInsert.setString(4, itiAdmissionForm.getMname());
                        preparedStatementInsert.setString(5, itiAdmissionForm.getAddress());
                        if (itiAdmissionForm.getPhoneNumber() != null && itiAdmissionForm.getPhoneNumber() != "") {
                            preparedStatementInsert.setLong(6, Long.parseLong(itiAdmissionForm.getPhoneNumber()));
                        } else {
                            preparedStatementInsert.setLong(6, 0);
                        }
                        preparedStatementInsert.setString(7, itiAdmissionForm.getAdarNo());
                        preparedStatementInsert.setString(8, itiAdmissionForm.getSex());
                        preparedStatementInsert.setString(9, caste);
                        preparedStatementInsert.setBoolean(10, itiAdmissionForm.getSsc_passed());
                        preparedStatementInsert.setBoolean(11, itiAdmissionForm.getInterPassed());
                        preparedStatementInsert.setInt(12, itiAdmissionForm.getInter_marks());
                        preparedStatementInsert.setString(13, itiAdmissionForm.getLocal());
                        preparedStatementInsert.setInt(14, itiAdmissionForm.getPin_code());
                        java.sql.Date date1 = java.sql.Date.valueOf(itiAdmissionForm.getDob());
                        preparedStatementInsert.setDate(15, date1);
                        System.out.println("dddddddddddddaaaaaaaaaaatttttttttt" + date1);
                        if (itiAdmissionForm.getPhc().equals("yes")) {
                            preparedStatementInsert.setBoolean(16, true);
                        } else {
                            preparedStatementInsert.setBoolean(16, false);
                        }
                        if (itiAdmissionForm.getExservice().equals("yes")) {
                            preparedStatementInsert.setBoolean(17, true);
                        } else {
                            preparedStatementInsert.setBoolean(17, false);
                        }
                        preparedStatementInsert.setString(18, itiAdmissionForm.getIti_code());
                        preparedStatementInsert.setInt(19, Integer.parseInt(itiAdmissionForm.getTrade_code()));
                        preparedStatementInsert.setString(20, itiAdmissionForm.getRes_category());
                        preparedStatementInsert.setString(21, itiAdmissionForm.getAdm_num());
                        preparedStatementInsert.setString(22, year_of_admission);
                        preparedStatementInsert.setInt(23, trno);
                        preparedStatementInsert.setString(24, dist_code);
                        preparedStatementInsert.setString(25, mode_adm);
System.out.println("preparedStatementInsert----->"+preparedStatementInsert);
                        int i = preparedStatementInsert.executeUpdate();
                        preparedStatementUpdate = connection.prepareStatement(SqlUpdate);

                        preparedStatementUpdate.setString(1, itiAdmissionForm.getIti_code());
                        preparedStatementUpdate.setInt(2, Integer.parseInt(itiAdmissionForm.getTrade_code()));
                        updateSeatmatrix = preparedStatementUpdate.executeUpdate();
                        connection.commit();

                        preparedStatementInsert.close();
                        preparedStatementUpdate.close();
                        connection.close();
                    } catch (Exception e) {
                        System.out.println("iam in exceptionnnnnnnnnnnn++++++++++" + e);
                        e.printStackTrace();
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

                    } finally {

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

                    String yearofadm = DBC.RoleBasedOperations.Current_AcademicYear(request);
                    try {
                        if (fm.getPic().getFileSize() > 0) {
                            DBC.DBConnection.insertPhoto_With_MultipleFields("exams.img_cand_photos_exams", "image", fm.getPic(), "adm_num,year_of_admission$'" + adm_num + "','" + yearofadm + "'", request);

                        } else {
                            String qry = "insert into exams.img_cand_photos_exams(adm_num,image,year_of_admission) select ?,pic,? from img_cand_photos where regid=?";
                            //st = connection.createStatement();
                            ps = connection.prepareStatement(qry);
                            ps.setString(1, adm_num);
                            ps.setString(2, yearofadm);
                            ps.setInt(3, adm_regid);
                            try {
                                int p = ps.executeUpdate();
                                st.close();
                                connection.close();
                            } catch (Exception ex) {
                                st.close();
                                connection.close();
                            }
                        }
                    } catch (NullPointerException e) {
                    }
                    String tab = "conviner_login";
                    String code = "dist_code";
                    String name = "dist_name";
                    String val = (String) request.getSession().getAttribute("ins_code");
                    System.out.println("val--->" + val);
                    String distname = null;
                    System.out.println("xyz--->" + xyz);
                    if (xyz == 500) {
                        System.out.println("xyz--->" + xyz);
                        distname = beans.MyUtil.getOptionValue(tab, code, name, val, request);
                        request.setAttribute("distname", distname);
                        return mapping.findForward("districtAdmissionSuccess");
                    } else {
                        System.out.println("xyz--->" + xyz);
                        return mapping.findForward("itiAdmissionFailed");
                    }
                } else {
                    System.out.println("no vacancies--->");
                }
            } else {
                ArrayList<Integer> councilMerits = null;
                ItiAdmissionProcessForm itiAdmissionProcessForm = new ItiAdmissionProcessForm();
                councilMerits = registrationDao.getCouncilMerits("dist_code", itiAdmissionForm.getCurrentDate(), (String) httpSession.getAttribute("ins_code"), request);
                if (councilMerits == null) {
                    return mapping.findForward("DistrictAdmissionEntryFailed");

                } else {

                    itiAdmissionProcessForm.setMerit_from(councilMerits.get(0));
                    itiAdmissionProcessForm.setMerit_to(councilMerits.get(1));
                    itiAdmissionProcessForm.setCurrentDate(itiAdmissionForm.getCurrentDate());
                    
                    request.setAttribute("itiAdmissionProcessForm", itiAdmissionProcessForm);
                    request.setAttribute("checkedAdmissionDetails", checkedAdmissionDetails);
                    
                    return mapping.findForward("districtAdmissionAlreadyFound");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception is " + e);
e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return null;

    }
}
