package com.nic;

import DBC.DBConnection;
import beans.MyUtil;
import com.nic.CheckedAdmissionDetails;
import com.nic.CouncilDetailsForm;
import com.nic.ItiAdmissionForm;
import com.nic.ItiAdmissionProcessForm;
import com.nic.ItiDetailsForm;
import com.nic.RegistrationDao;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DistrictAdmissionProcessOptimiseAction
        extends Action {

    private static RegistrationDao registrationDao = new RegistrationDao();
    private ItiAdmissionProcessForm itiAdmissionProcessForm;
    private ItiDetailsForm itiDetailsForm;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        block232:
        {
            Statement st = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = null;

            try {
                this.itiAdmissionProcessForm = (ItiAdmissionProcessForm) form;
                HttpSession httpSession = request.getSession();

                String stored_token = beans.MyUtil.filterBad("" + httpSession.getAttribute("generated_token"));
                String token = beans.MyUtil.filterBad(request.getParameter("token"));

                String id = stored_token.substring(10, 20) + request.getSession().getId() + stored_token.substring(2, 6);
                if (!token.equals(id)) { return mapping.findForward("failure"); }

                String dist_code = beans.MyUtil.filterBad(""+httpSession.getAttribute("ins_code"));
                String caste_perform = this.itiAdmissionProcessForm.getCaste_perform();
                String qual_perform = this.itiAdmissionProcessForm.getQual_perform();
               
                con = DBConnection.getConnection((HttpServletRequest) request);
                
                String year = MyUtil.getOptionValue((String) "iti_params", (String) "code", (String) "value", (String) "7", (Connection) con);
                String phase = MyUtil.getOptionValue((String) "admissions.admission_phase", (String) "year", (String) "phase", (String) year, (String) "current", (String) "true", (Connection) con);
                String sub_qry = "  a.phase='" + phase + "' and qual='" + qual_perform + "' and dist_code ";
                
                if (!caste_perform.equalsIgnoreCase("all")) {
                    sub_qry = "  b.caste='" + caste_perform + "' and " + sub_qry + " ";
                }
                int regid = registrationDao.getRegistrationDetails(sub_qry, dist_code, this.itiAdmissionProcessForm.getRank(), request);
                String category_code = null;
                String caste_code = null;
                String ocCastCode = null;
                String ocCode = "OC";
                String oc_wCode = "OC-W";
                String phCode = "PH";
                String ph_wCode = "PH-W";
                String exCode = "EX-S";
                String ex_wCode = "EX-SW";
                String sp_Code = "SP";
                String sp_wCode = "SP_W";
                String imcCode = "IM";

                if (regid != 0) {
                    CheckedAdmissionDetails checkedAdmissionDetails = registrationDao.checkAdmited_or_Not(regid, request);
                    if (checkedAdmissionDetails == null) {
                        ItiAdmissionForm itiAdmissionForm = registrationDao.getApplicationDetails(regid, request);
                        if (itiAdmissionForm != null) {
                            itiAdmissionForm.setMeritNumber(this.itiAdmissionProcessForm.getRank());
                            itiAdmissionForm.setAcad_year(this.itiAdmissionProcessForm.getCurrentDate().toString().substring(0, 4));
                            itiAdmissionForm.setCurrentDate(this.itiAdmissionProcessForm.getCurrentDate());
                            String gpt = registrationDao.getGpt(regid, request);
                            itiAdmissionForm.setGpt(gpt);
                            request.setAttribute("itiAdmissionForm", (Object) itiAdmissionForm);
                            caste_code = itiAdmissionForm.getCast();
                            category_code = itiAdmissionForm.getCast();
                            ocCastCode = "OC";
                            String year_con = null;
                            String permission = null;
                            String cast = null;
                            String query = "select * from generalpool.open_general_pool where dist_code=? and permission='true'";
                            ps = con.prepareStatement(query);
                            ps.setString(1, dist_code);
                            rs = ps.executeQuery();

                            if (rs.next()) { 
                                System.out.println("In result set true....");
                                year_con = rs.getString("year");
                                cast = rs.getString("caste");
                                if (year_con.equals(year)) {
                                    System.out.println("In result set true111" + year);
                                    if (cast.equals(imcCode)) {
                                        caste_code = imcCode;
                                        category_code = caste_code;
                                        System.out.println("caste_code iss--->" + caste_code);

                                    } else {
                                        caste_code = cast;
                                        category_code = caste_code.contains("-") ? caste_code + "W" : caste_code + "-W";
                                        itiAdmissionForm.setSex("female");

                                    }
                                    itiAdmissionForm.setRes_category(category_code);
                                    itiAdmissionForm.setCast(cast.trim());
                                    itiAdmissionForm.setExservice("Yes");
                                    itiAdmissionForm.setPhc("Yes");

                                }

                            } else  
                            if (itiAdmissionForm.getSex().equalsIgnoreCase("female")) {
                                if (caste_code.contains("-")) {
                                    category_code = caste_code + "W";
                                } else {
                                    category_code = caste_code + "-W";
                                }
                                itiAdmissionForm.setRes_category(category_code);

                            }
                           
                            category_code = registrationDao.getCategoryCode(category_code, request);
                            System.out.println("1111sssssssssssss" + category_code);

                            if (itiAdmissionForm.getSex().equalsIgnoreCase("female") && caste_code.equals(ocCastCode)) {
                                category_code = oc_wCode;
                            }
                            System.out.println("sssssssssssss" + category_code);
                            if (caste_code.equals(ocCastCode) && itiAdmissionForm.getSex().equalsIgnoreCase("male") && !itiAdmissionForm.getPhc().equals("Yes") && !itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is---------1111111111----------" + itiAdmissionForm.getPhc());

                                ArrayList generalCouncilDetailsForms1 = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                int size = generalCouncilDetailsForms1.size();
                                if (size != 0) {
                                    ArrayList<CouncilDetailsForm> generalCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
                                    for (int i2 = 0; i2 < size; ++i2) {
                                        CouncilDetailsForm councilDetailsForm = new CouncilDetailsForm();
                                        councilDetailsForm = (CouncilDetailsForm) generalCouncilDetailsForms1.get(i2);
                                        int checkName = 0;
                                        if (i2 > 0) {
                                            CouncilDetailsForm councilDetailsFormPre = (CouncilDetailsForm) generalCouncilDetailsForms1.get(i2 - 1);
                                            String iti_code1 = councilDetailsFormPre.getIti_code();
                                            String iti_code2 = councilDetailsForm.getIti_code();
                                            if (i2 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        int check = 0;
                                        if (councilDetailsForm.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        }
                                        councilDetailsForm.setCheck(check);
                                        councilDetailsForm.setCheckName(checkName);
                                        generalCouncilDetailsForms.add(councilDetailsForm);
                                    }
                                    request.setAttribute("generalCouncilDetailsForms", generalCouncilDetailsForms);
                                    ActionForward i2 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i2;
                                }
                                ActionForward generalCouncilDetailsForms = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return generalCouncilDetailsForms;
                            }
                            if (caste_code.equals(ocCastCode) && itiAdmissionForm.getSex().equalsIgnoreCase("male") && itiAdmissionForm.getPhc().equals("Yes") && !itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is--------22222222222222-----------" + itiAdmissionForm.getPhc());
                                ArrayList generalPhcCouncilDetailsForms1 = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList generalPhcCouncilDetailsForms2 = registrationDao.getPhcCouncilDistrictDetails(dist_code, phCode, request);
                                int size = generalPhcCouncilDetailsForms1.size();
                                int size1 = generalPhcCouncilDetailsForms2.size();
                                if (size != 0 && size == size1) {
                                    ArrayList<CouncilDetailsForm> generalPhcCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
                                    for (int i3 = 0; i3 < size; ++i3) {
                                        CouncilDetailsForm generalPhcCouncilDetailsForm = new CouncilDetailsForm();
                                        CouncilDetailsForm generalPhcCouncilDetailsForm1 = (CouncilDetailsForm) generalPhcCouncilDetailsForms1.get(i3);
                                        CouncilDetailsForm generalPhcCouncilDetailsForm2 = (CouncilDetailsForm) generalPhcCouncilDetailsForms2.get(i3);
                                        int checkName = 0;
                                        if (i3 > 0) {
                                            CouncilDetailsForm generalPhcCouncilDetailsFormprv = (CouncilDetailsForm) generalPhcCouncilDetailsForms1.get(i3 - 1);
                                            String iti_code1 = generalPhcCouncilDetailsFormprv.getIti_code();
                                            String iti_code2 = generalPhcCouncilDetailsForm1.getIti_code();
                                            if (i3 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        generalPhcCouncilDetailsForm.setIti_name(generalPhcCouncilDetailsForm1.getIti_name());
                                        generalPhcCouncilDetailsForm.setIti_code(generalPhcCouncilDetailsForm1.getIti_code());
                                        generalPhcCouncilDetailsForm.setTrade_code(generalPhcCouncilDetailsForm1.getTrade_code());
                                        generalPhcCouncilDetailsForm.setTrade_short(generalPhcCouncilDetailsForm1.getTrade_short());
                                        generalPhcCouncilDetailsForm.setTrade_name(generalPhcCouncilDetailsForm1.getTrade_name());
                                        generalPhcCouncilDetailsForm.setTotalStrength(generalPhcCouncilDetailsForm1.getTotalStrength());
                                        generalPhcCouncilDetailsForm.setGeneralStrength(generalPhcCouncilDetailsForm1.getGeneralStrength());
                                        generalPhcCouncilDetailsForm.setGeneralStrength_fill(generalPhcCouncilDetailsForm1.getGeneralStrength_fill());
                                        generalPhcCouncilDetailsForm.setGeneralStrength_vacant(generalPhcCouncilDetailsForm1.getGeneralStrength_vacant());
                                        generalPhcCouncilDetailsForm.setPhcStrength(generalPhcCouncilDetailsForm2.getPhcStrength());
                                        generalPhcCouncilDetailsForm.setPhcStrength_fill(generalPhcCouncilDetailsForm2.getPhcStrength_fill());
                                        generalPhcCouncilDetailsForm.setPhcStrength_vacant(generalPhcCouncilDetailsForm2.getPhcStrength_vacant());
                                        int check = 0;
                                        if (generalPhcCouncilDetailsForm.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (generalPhcCouncilDetailsForm.getGeneralStrength_vacant() == 0 && generalPhcCouncilDetailsForm.getPhcStrength_vacant() > 0) {
                                            check = 5;
                                        }
                                        generalPhcCouncilDetailsForm.setCheck(check);
                                        generalPhcCouncilDetailsForm.setCheckName(checkName);
                                        generalPhcCouncilDetailsForms.add(generalPhcCouncilDetailsForm);
                                    }
                                    request.setAttribute("generalPhcCouncilDetailsForms", generalPhcCouncilDetailsForms);
                                    ActionForward i3 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i3;
                                }
                                ActionForward generalPhcCouncilDetailsForms = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return generalPhcCouncilDetailsForms;
                            }
                            if (caste_code.equals(ocCastCode) && itiAdmissionForm.getSex().equalsIgnoreCase("male") && !itiAdmissionForm.getPhc().equals("Yes") && itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is--------33333333333-----------" + itiAdmissionForm.getPhc());
                                ArrayList generalExserviceCouncilDetailsForms1 = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList generalExserviceCouncilDetailsForms2 = registrationDao.getExserviceCouncilDistrictDetails(dist_code, exCode, request);
                                int size = generalExserviceCouncilDetailsForms1.size();
                                int size1 = generalExserviceCouncilDetailsForms2.size();
                                if (size != 0 && size == size1) {
                                    ArrayList<CouncilDetailsForm> generalExserviceCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
                                    for (int i4 = 0; i4 < size; ++i4) {
                                        CouncilDetailsForm generalExserviceCouncilDetailsForm = new CouncilDetailsForm();
                                        CouncilDetailsForm generalExserviceCouncilDetailsForm1 = (CouncilDetailsForm) generalExserviceCouncilDetailsForms1.get(i4);
                                        CouncilDetailsForm generalExserviceCouncilDetailsForm2 = (CouncilDetailsForm) generalExserviceCouncilDetailsForms2.get(i4);
                                        int checkName = 0;
                                        if (i4 > 0) {
                                            CouncilDetailsForm generalExserviceCouncilDetailsFormpre = (CouncilDetailsForm) generalExserviceCouncilDetailsForms1.get(i4 - 1);
                                            String iti_code1 = generalExserviceCouncilDetailsFormpre.getIti_code();
                                            String iti_code2 = generalExserviceCouncilDetailsForm1.getIti_code();
                                            if (i4 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        generalExserviceCouncilDetailsForm.setIti_name(generalExserviceCouncilDetailsForm1.getIti_name());
                                        generalExserviceCouncilDetailsForm.setIti_code(generalExserviceCouncilDetailsForm1.getIti_code());
                                        generalExserviceCouncilDetailsForm.setTrade_code(generalExserviceCouncilDetailsForm1.getTrade_code());
                                        generalExserviceCouncilDetailsForm.setTrade_short(generalExserviceCouncilDetailsForm1.getTrade_short());
                                        generalExserviceCouncilDetailsForm.setTrade_name(generalExserviceCouncilDetailsForm1.getTrade_name());
                                        generalExserviceCouncilDetailsForm.setTotalStrength(generalExserviceCouncilDetailsForm1.getTotalStrength());
                                        generalExserviceCouncilDetailsForm.setGeneralStrength(generalExserviceCouncilDetailsForm1.getGeneralStrength());
                                        generalExserviceCouncilDetailsForm.setGeneralStrength_fill(generalExserviceCouncilDetailsForm1.getGeneralStrength_fill());
                                        generalExserviceCouncilDetailsForm.setGeneralStrength_vacant(generalExserviceCouncilDetailsForm1.getGeneralStrength_vacant());
                                        generalExserviceCouncilDetailsForm.setExserviceStrength(generalExserviceCouncilDetailsForm2.getExserviceStrength());
                                        generalExserviceCouncilDetailsForm.setExserviceStrength_fill(generalExserviceCouncilDetailsForm2.getExserviceStrength_fill());
                                        generalExserviceCouncilDetailsForm.setExserviceStrength_vacant(generalExserviceCouncilDetailsForm2.getExserviceStrength_vacant());
                                        int check = 0;
                                        if (generalExserviceCouncilDetailsForm.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (generalExserviceCouncilDetailsForm.getGeneralStrength_vacant() == 0 && generalExserviceCouncilDetailsForm.getExserviceStrength_vacant() > 0) {
                                            check = 7;
                                        }
                                        generalExserviceCouncilDetailsForm.setCheck(check);
                                        generalExserviceCouncilDetailsForm.setCheckName(checkName);
                                        generalExserviceCouncilDetailsForms.add(generalExserviceCouncilDetailsForm);
                                    }
                                    request.setAttribute("generalExserviceCouncilDetailsForms", generalExserviceCouncilDetailsForms);
                                    ActionForward i4 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i4;
                                }
                                ActionForward generalExserviceCouncilDetailsForms = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return generalExserviceCouncilDetailsForms;
                            }
                            if (caste_code.equals(ocCastCode) && itiAdmissionForm.getSex().equalsIgnoreCase("male") && itiAdmissionForm.getPhc().equals("Yes") && itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is------444444444444444-------------" + itiAdmissionForm.getPhc());
                                ArrayList generalPh_Ex_CouncilDetailsForms1 = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList generalPh_Ex_CouncilDetailsForms2 = registrationDao.getPhcCouncilDistrictDetails(dist_code, phCode, request);
                                ArrayList generalPh_Ex_CouncilDetailsForms3 = registrationDao.getExserviceCouncilDistrictDetails(dist_code, exCode, request);
                                int size = generalPh_Ex_CouncilDetailsForms1.size();
                                int size1 = generalPh_Ex_CouncilDetailsForms2.size();
                                int size2 = generalPh_Ex_CouncilDetailsForms3.size();
                                if (size != 0 && size == size1 && size == size2) {
                                    ArrayList<CouncilDetailsForm> generalPh_Ex_CouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
                                    for (int i5 = 0; i5 < size; ++i5) {
                                        CouncilDetailsForm generalPh_Ex_CouncilDetailsForm = new CouncilDetailsForm();
                                        CouncilDetailsForm generalPh_Ex_CouncilDetailsForm1 = (CouncilDetailsForm) generalPh_Ex_CouncilDetailsForms1.get(i5);
                                        CouncilDetailsForm generalPh_Ex_CouncilDetailsForm2 = (CouncilDetailsForm) generalPh_Ex_CouncilDetailsForms2.get(i5);
                                        CouncilDetailsForm generalPh_Ex_CouncilDetailsForm3 = (CouncilDetailsForm) generalPh_Ex_CouncilDetailsForms3.get(i5);
                                        int checkName = 0;
                                        if (i5 > 0) {
                                            CouncilDetailsForm generalPh_Ex_CouncilDetailsFormpre = (CouncilDetailsForm) generalPh_Ex_CouncilDetailsForms1.get(i5 - 1);
                                            String iti_code1 = generalPh_Ex_CouncilDetailsFormpre.getIti_code();
                                            String iti_code2 = generalPh_Ex_CouncilDetailsForm1.getIti_code();
                                            if (i5 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        generalPh_Ex_CouncilDetailsForm.setIti_name(generalPh_Ex_CouncilDetailsForm1.getIti_name());
                                        generalPh_Ex_CouncilDetailsForm.setIti_code(generalPh_Ex_CouncilDetailsForm1.getIti_code());
                                        generalPh_Ex_CouncilDetailsForm.setTrade_code(generalPh_Ex_CouncilDetailsForm1.getTrade_code());
                                        generalPh_Ex_CouncilDetailsForm.setTrade_short(generalPh_Ex_CouncilDetailsForm1.getTrade_short());
                                        generalPh_Ex_CouncilDetailsForm.setTrade_name(generalPh_Ex_CouncilDetailsForm1.getTrade_name());
                                        generalPh_Ex_CouncilDetailsForm.setTotalStrength(generalPh_Ex_CouncilDetailsForm1.getTotalStrength());
                                        generalPh_Ex_CouncilDetailsForm.setGeneralStrength(generalPh_Ex_CouncilDetailsForm1.getGeneralStrength());
                                        generalPh_Ex_CouncilDetailsForm.setGeneralStrength_fill(generalPh_Ex_CouncilDetailsForm1.getGeneralStrength_fill());
                                        generalPh_Ex_CouncilDetailsForm.setGeneralStrength_vacant(generalPh_Ex_CouncilDetailsForm1.getGeneralStrength_vacant());
                                        generalPh_Ex_CouncilDetailsForm.setPhcStrength(generalPh_Ex_CouncilDetailsForm2.getPhcStrength());
                                        generalPh_Ex_CouncilDetailsForm.setPhcStrength_fill(generalPh_Ex_CouncilDetailsForm2.getPhcStrength_fill());
                                        generalPh_Ex_CouncilDetailsForm.setPhcStrength_vacant(generalPh_Ex_CouncilDetailsForm2.getPhcStrength_vacant());
                                        generalPh_Ex_CouncilDetailsForm.setExserviceStrength(generalPh_Ex_CouncilDetailsForm3.getExserviceStrength());
                                        generalPh_Ex_CouncilDetailsForm.setExserviceStrength_fill(generalPh_Ex_CouncilDetailsForm3.getExserviceStrength_fill());
                                        generalPh_Ex_CouncilDetailsForm.setExserviceStrength_vacant(generalPh_Ex_CouncilDetailsForm3.getExserviceStrength_vacant());
                                        int check = 0;
                                        if (generalPh_Ex_CouncilDetailsForm.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (generalPh_Ex_CouncilDetailsForm.getGeneralStrength_vacant() == 0 && generalPh_Ex_CouncilDetailsForm.getPhcStrength_vacant() > 0) {
                                            check = 5;
                                        } else if (generalPh_Ex_CouncilDetailsForm.getGeneralStrength_vacant() == 0 && generalPh_Ex_CouncilDetailsForm.getPhcStrength_vacant() == 0 && generalPh_Ex_CouncilDetailsForm.getExserviceStrength_vacant() > 0) {
                                            check = 7;
                                        }
                                        generalPh_Ex_CouncilDetailsForm.setCheck(check);
                                        generalPh_Ex_CouncilDetailsForm.setCheckName(checkName);
                                        generalPh_Ex_CouncilDetailsForms.add(generalPh_Ex_CouncilDetailsForm);
                                    }
                                    request.setAttribute("generalPh_Ex_CouncilDetailsForms", generalPh_Ex_CouncilDetailsForms);
                                    ActionForward i5 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i5;
                                }
                                ActionForward generalPh_Ex_CouncilDetailsForms = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return generalPh_Ex_CouncilDetailsForms;
                            }
                            if (caste_code.equals(ocCastCode) && !itiAdmissionForm.getSex().equalsIgnoreCase("male") && !itiAdmissionForm.getPhc().equals("Yes") && !itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is-------55555555555------------");
                                ArrayList generalCouncilDetailsForms1 = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList generalCouncilDetailsForms2 = registrationDao.getGeneral_wCouncilDistrictDetails(dist_code, category_code, request);
                                int size = generalCouncilDetailsForms1.size();
                                int size1 = generalCouncilDetailsForms2.size();
                                if (size != 0 && size == size1) {
                                    ArrayList<CouncilDetailsForm> generalCouncilDetailsForms_w = new ArrayList<CouncilDetailsForm>();
                                    for (int i6 = 0; i6 < size; ++i6) {
                                        CouncilDetailsForm generalCouncilDetailsForm_w = new CouncilDetailsForm();
                                        CouncilDetailsForm generalCouncilDetailsForm1_w = (CouncilDetailsForm) generalCouncilDetailsForms1.get(i6);
                                        CouncilDetailsForm generalCouncilDetailsForm2_w = (CouncilDetailsForm) generalCouncilDetailsForms2.get(i6);
                                        int checkName = 0;
                                        if (i6 > 0) {
                                            CouncilDetailsForm generalCouncilDetailsForm_wprv = (CouncilDetailsForm) generalCouncilDetailsForms1.get(i6 - 1);
                                            String iti_code1 = generalCouncilDetailsForm_wprv.getIti_code();
                                            String iti_code2 = generalCouncilDetailsForm1_w.getIti_code();
                                            if (i6 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        generalCouncilDetailsForm_w.setIti_name(generalCouncilDetailsForm1_w.getIti_name());
                                        generalCouncilDetailsForm_w.setIti_code(generalCouncilDetailsForm1_w.getIti_code());
                                        generalCouncilDetailsForm_w.setTrade_code(generalCouncilDetailsForm1_w.getTrade_code());
                                        generalCouncilDetailsForm_w.setTrade_short(generalCouncilDetailsForm1_w.getTrade_short());
                                        generalCouncilDetailsForm_w.setTrade_name(generalCouncilDetailsForm1_w.getTrade_name());
                                        generalCouncilDetailsForm_w.setTotalStrength(generalCouncilDetailsForm1_w.getTotalStrength());
                                        generalCouncilDetailsForm_w.setGeneralStrength(generalCouncilDetailsForm1_w.getGeneralStrength());
                                        generalCouncilDetailsForm_w.setGeneralStrength_fill(generalCouncilDetailsForm1_w.getGeneralStrength_fill());
                                        generalCouncilDetailsForm_w.setGeneralStrength_vacant(generalCouncilDetailsForm1_w.getGeneralStrength_vacant());
                                        generalCouncilDetailsForm_w.setGeneralStrength_w(generalCouncilDetailsForm2_w.getGeneralStrength_w());
                                        generalCouncilDetailsForm_w.setGeneralStrength_fill_w(generalCouncilDetailsForm2_w.getGeneralStrength_fill_w());
                                        generalCouncilDetailsForm_w.setGeneralStrength_vacant_w(generalCouncilDetailsForm2_w.getGeneralStrength_vacant_w());
                                        int check = 0;
                                        if (generalCouncilDetailsForm_w.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (generalCouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && generalCouncilDetailsForm_w.getGeneralStrength_vacant_w() > 0) {
                                            check = 2;
                                        }
                                        generalCouncilDetailsForm_w.setCheck(check);
                                        generalCouncilDetailsForm_w.setCheckName(checkName);
                                        generalCouncilDetailsForms_w.add(generalCouncilDetailsForm_w);
                                    }
                                    request.setAttribute("generalCouncilDetailsForms_w", generalCouncilDetailsForms_w);
                                    ActionForward i6 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i6;
                                }
                                ActionForward generalCouncilDetailsForms_w = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return generalCouncilDetailsForms_w;
                            }
                            if (caste_code.equals(ocCastCode) && !itiAdmissionForm.getSex().equalsIgnoreCase("male") && !itiAdmissionForm.getSex().equalsIgnoreCase("male") && itiAdmissionForm.getPhc().equals("Yes") && !itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is--------66666666666666-----------");
                                ArrayList generalPhcCouncilDetailsForms1 = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList generalPhcCouncilDetailsForms2 = registrationDao.getGeneral_wCouncilDistrictDetails(dist_code, category_code, request);
                                ArrayList generalPhcCouncilDetailsForms3 = registrationDao.getPhcCouncilDistrictDetails(dist_code, phCode, request);
                                ArrayList generalPhcCouncilDetailsForms4 = registrationDao.getPhc_wCouncilDistrictDetails(dist_code, ph_wCode, request);
                                int size = generalPhcCouncilDetailsForms1.size();
                                int size1 = generalPhcCouncilDetailsForms2.size();
                                int size2 = generalPhcCouncilDetailsForms3.size();
                                int size3 = generalPhcCouncilDetailsForms4.size();
                                if (size != 0 && size == size1 && size == size2 && size == size3) {
                                    ArrayList<CouncilDetailsForm> generalPhcCouncilDetailsForms_w = new ArrayList<CouncilDetailsForm>();
                                    for (int i7 = 0; i7 < size; ++i7) {
                                        CouncilDetailsForm generalPhcCouncilDetailsForm_w = new CouncilDetailsForm();
                                        CouncilDetailsForm generalPhcCouncilDetailsForm1_w = (CouncilDetailsForm) generalPhcCouncilDetailsForms1.get(i7);
                                        CouncilDetailsForm generalPhcCouncilDetailsForm2_w = (CouncilDetailsForm) generalPhcCouncilDetailsForms2.get(i7);
                                        CouncilDetailsForm generalPhcCouncilDetailsForm3_w = (CouncilDetailsForm) generalPhcCouncilDetailsForms3.get(i7);
                                        CouncilDetailsForm generalPhcCouncilDetailsForm4_w = (CouncilDetailsForm) generalPhcCouncilDetailsForms4.get(i7);
                                        int checkName = 0;
                                        if (i7 > 0) {
                                            CouncilDetailsForm generalPhcCouncilDetailsForm_wpre = (CouncilDetailsForm) generalPhcCouncilDetailsForms1.get(i7 - 1);
                                            String iti_code1 = generalPhcCouncilDetailsForm_wpre.getIti_code();
                                            String iti_code2 = generalPhcCouncilDetailsForm1_w.getIti_code();
                                            if (i7 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        generalPhcCouncilDetailsForm_w.setIti_name(generalPhcCouncilDetailsForm1_w.getIti_name());
                                        generalPhcCouncilDetailsForm_w.setIti_code(generalPhcCouncilDetailsForm1_w.getIti_code());
                                        generalPhcCouncilDetailsForm_w.setTrade_code(generalPhcCouncilDetailsForm1_w.getTrade_code());
                                        generalPhcCouncilDetailsForm_w.setTrade_short(generalPhcCouncilDetailsForm1_w.getTrade_short());
                                        generalPhcCouncilDetailsForm_w.setTrade_name(generalPhcCouncilDetailsForm1_w.getTrade_name());
                                        generalPhcCouncilDetailsForm_w.setTotalStrength(generalPhcCouncilDetailsForm1_w.getTotalStrength());
                                        generalPhcCouncilDetailsForm_w.setGeneralStrength(generalPhcCouncilDetailsForm1_w.getGeneralStrength());
                                        generalPhcCouncilDetailsForm_w.setGeneralStrength_fill(generalPhcCouncilDetailsForm1_w.getGeneralStrength_fill());
                                        generalPhcCouncilDetailsForm_w.setGeneralStrength_vacant(generalPhcCouncilDetailsForm1_w.getGeneralStrength_vacant());
                                        generalPhcCouncilDetailsForm_w.setGeneralStrength_w(generalPhcCouncilDetailsForm2_w.getGeneralStrength_w());
                                        generalPhcCouncilDetailsForm_w.setGeneralStrength_fill_w(generalPhcCouncilDetailsForm2_w.getGeneralStrength_fill_w());
                                        generalPhcCouncilDetailsForm_w.setGeneralStrength_vacant_w(generalPhcCouncilDetailsForm2_w.getGeneralStrength_vacant_w());
                                        generalPhcCouncilDetailsForm_w.setPhcStrength(generalPhcCouncilDetailsForm3_w.getPhcStrength());
                                        generalPhcCouncilDetailsForm_w.setPhcStrength_fill(generalPhcCouncilDetailsForm3_w.getPhcStrength_fill());
                                        generalPhcCouncilDetailsForm_w.setPhcStrength_vacant(generalPhcCouncilDetailsForm3_w.getPhcStrength_vacant());
                                        generalPhcCouncilDetailsForm_w.setPhcStrength_w(generalPhcCouncilDetailsForm4_w.getPhcStrength_w());
                                        generalPhcCouncilDetailsForm_w.setPhcStrength_fill_w(generalPhcCouncilDetailsForm4_w.getPhcStrength_fill_w());
                                        generalPhcCouncilDetailsForm_w.setPhcStrength_vacant_w(generalPhcCouncilDetailsForm4_w.getPhcStrength_vacant_w());
                                        int check = 0;
                                        if (generalPhcCouncilDetailsForm_w.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (generalPhcCouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && generalPhcCouncilDetailsForm_w.getGeneralStrength_vacant_w() > 0) {
                                            check = 2;
                                        } else if (generalPhcCouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && generalPhcCouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && generalPhcCouncilDetailsForm_w.getPhcStrength_vacant() > 0) {
                                            check = 5;
                                        } else if (generalPhcCouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && generalPhcCouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && generalPhcCouncilDetailsForm_w.getPhcStrength_vacant() == 0 && generalPhcCouncilDetailsForm_w.getPhcStrength_vacant_w() > 0) {
                                            check = 6;
                                        }
                                        generalPhcCouncilDetailsForm_w.setCheck(check);
                                        generalPhcCouncilDetailsForm_w.setCheckName(checkName);
                                        generalPhcCouncilDetailsForms_w.add(generalPhcCouncilDetailsForm_w);
                                    }
                                    request.setAttribute("generalPhcCouncilDetailsForms_w", generalPhcCouncilDetailsForms_w);
                                    ActionForward i7 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i7;
                                }
                                ActionForward generalPhcCouncilDetailsForms_w = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return generalPhcCouncilDetailsForms_w;
                            }
                            if (caste_code.equals(ocCastCode) && !itiAdmissionForm.getSex().equalsIgnoreCase("male") && !itiAdmissionForm.getPhc().equals("Yes") && itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is--------777777777777-----------");
                                ArrayList generalExserviceCouncilDetailsForms1 = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList generalExserviceCouncilDetailsForms2 = registrationDao.getGeneral_wCouncilDistrictDetails(dist_code, category_code, request);
                                ArrayList generalExserviceCouncilDetailsForms3 = registrationDao.getExserviceCouncilDistrictDetails(dist_code, exCode, request);
                                ArrayList generalExserviceCouncilDetailsForms4 = registrationDao.getExservice_wCouncilDistrictDetails(dist_code, ex_wCode, request);
                                int size = generalExserviceCouncilDetailsForms1.size();
                                int size1 = generalExserviceCouncilDetailsForms2.size();
                                int size2 = generalExserviceCouncilDetailsForms3.size();
                                int size3 = generalExserviceCouncilDetailsForms4.size();
                                if (size != 0 && size == size1 && size == size2 && size == size3) {
                                    ArrayList<CouncilDetailsForm> generalExserviceCouncilDetailsForms_w = new ArrayList<CouncilDetailsForm>();
                                    for (int i8 = 0; i8 < size; ++i8) {
                                        CouncilDetailsForm generalExserviceCouncilDetailsForm_w = new CouncilDetailsForm();
                                        CouncilDetailsForm generalExserviceCouncilDetailsForm1_w = (CouncilDetailsForm) generalExserviceCouncilDetailsForms1.get(i8);
                                        CouncilDetailsForm generalExserviceCouncilDetailsForm2_w = (CouncilDetailsForm) generalExserviceCouncilDetailsForms2.get(i8);
                                        CouncilDetailsForm generalExserviceCouncilDetailsForm3_w = (CouncilDetailsForm) generalExserviceCouncilDetailsForms3.get(i8);
                                        CouncilDetailsForm generalExserviceCouncilDetailsForm4_w = (CouncilDetailsForm) generalExserviceCouncilDetailsForms4.get(i8);
                                        int checkName = 0;
                                        if (i8 > 0) {
                                            CouncilDetailsForm generalExserviceCouncilDetailsForm_wpre = (CouncilDetailsForm) generalExserviceCouncilDetailsForms1.get(i8 - 1);
                                            String iti_code1 = generalExserviceCouncilDetailsForm_wpre.getIti_code();
                                            String iti_code2 = generalExserviceCouncilDetailsForm1_w.getIti_code();
                                            if (i8 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        generalExserviceCouncilDetailsForm_w.setIti_name(generalExserviceCouncilDetailsForm1_w.getIti_name());
                                        generalExserviceCouncilDetailsForm_w.setIti_code(generalExserviceCouncilDetailsForm1_w.getIti_code());
                                        generalExserviceCouncilDetailsForm_w.setTrade_code(generalExserviceCouncilDetailsForm1_w.getTrade_code());
                                        generalExserviceCouncilDetailsForm_w.setTrade_short(generalExserviceCouncilDetailsForm1_w.getTrade_short());
                                        generalExserviceCouncilDetailsForm_w.setTrade_name(generalExserviceCouncilDetailsForm1_w.getTrade_name());
                                        generalExserviceCouncilDetailsForm_w.setTotalStrength(generalExserviceCouncilDetailsForm1_w.getTotalStrength());
                                        generalExserviceCouncilDetailsForm_w.setGeneralStrength(generalExserviceCouncilDetailsForm1_w.getGeneralStrength());
                                        generalExserviceCouncilDetailsForm_w.setGeneralStrength_fill(generalExserviceCouncilDetailsForm1_w.getGeneralStrength_fill());
                                        generalExserviceCouncilDetailsForm_w.setGeneralStrength_vacant(generalExserviceCouncilDetailsForm1_w.getGeneralStrength_vacant());
                                        generalExserviceCouncilDetailsForm_w.setGeneralStrength_w(generalExserviceCouncilDetailsForm2_w.getGeneralStrength_w());
                                        generalExserviceCouncilDetailsForm_w.setGeneralStrength_fill_w(generalExserviceCouncilDetailsForm2_w.getGeneralStrength_fill_w());
                                        generalExserviceCouncilDetailsForm_w.setGeneralStrength_vacant_w(generalExserviceCouncilDetailsForm2_w.getGeneralStrength_vacant_w());
                                        generalExserviceCouncilDetailsForm_w.setExserviceStrength(generalExserviceCouncilDetailsForm3_w.getExserviceStrength());
                                        generalExserviceCouncilDetailsForm_w.setExserviceStrength_fill(generalExserviceCouncilDetailsForm3_w.getExserviceStrength_fill());
                                        generalExserviceCouncilDetailsForm_w.setExserviceStrength_vacant(generalExserviceCouncilDetailsForm3_w.getExserviceStrength_vacant());
                                        generalExserviceCouncilDetailsForm_w.setExserviceStrength_w(generalExserviceCouncilDetailsForm4_w.getExserviceStrength_w());
                                        generalExserviceCouncilDetailsForm_w.setExserviceStrength_fill_w(generalExserviceCouncilDetailsForm4_w.getExserviceStrength_fill_w());
                                        generalExserviceCouncilDetailsForm_w.setExserviceStrength_vacant_w(generalExserviceCouncilDetailsForm4_w.getExserviceStrength_vacant_w());
                                        int check = 0;
                                        if (generalExserviceCouncilDetailsForm_w.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (generalExserviceCouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && generalExserviceCouncilDetailsForm_w.getGeneralStrength_vacant_w() > 0) {
                                            check = 2;
                                        } else if (generalExserviceCouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && generalExserviceCouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && generalExserviceCouncilDetailsForm_w.getExserviceStrength_vacant() > 0) {
                                            check = 7;
                                        } else if (generalExserviceCouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && generalExserviceCouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && generalExserviceCouncilDetailsForm_w.getExserviceStrength_vacant() == 0 && generalExserviceCouncilDetailsForm_w.getExserviceStrength_vacant_w() > 0) {
                                            check = 8;
                                        }
                                        generalExserviceCouncilDetailsForm_w.setCheck(check);
                                        generalExserviceCouncilDetailsForm_w.setCheckName(checkName);
                                        generalExserviceCouncilDetailsForms_w.add(generalExserviceCouncilDetailsForm_w);
                                    }
                                    request.setAttribute("generalExserviceCouncilDetailsForms_w", generalExserviceCouncilDetailsForms_w);
                                    ActionForward i8 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i8;
                                }
                                ActionForward generalExserviceCouncilDetailsForms_w = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return generalExserviceCouncilDetailsForms_w;
                            }
                            if (caste_code.equals(ocCastCode) && !itiAdmissionForm.getSex().equalsIgnoreCase("male") && itiAdmissionForm.getPhc().equals("Yes") && itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is--------88888888888-----------");
                                ArrayList general_Phc_Exs_CouncilDetailsForms1 = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList general_Phc_Exs_CouncilDetailsForms2 = registrationDao.getGeneral_wCouncilDistrictDetails(dist_code, category_code, request);
                                ArrayList general_Phc_Exs_CouncilDetailsForms3 = registrationDao.getPhcCouncilDistrictDetails(dist_code, phCode, request);
                                ArrayList general_Phc_Exs_CouncilDetailsForms4 = registrationDao.getPhc_wCouncilDistrictDetails(dist_code, ph_wCode, request);
                                ArrayList general_Phc_Exs_CouncilDetailsForms5 = registrationDao.getExserviceCouncilDistrictDetails(dist_code, exCode, request);
                                ArrayList general_Phc_Exs_CouncilDetailsForms6 = registrationDao.getExservice_wCouncilDistrictDetails(dist_code, ex_wCode, request);
                                int size = general_Phc_Exs_CouncilDetailsForms1.size();
                                int size1 = general_Phc_Exs_CouncilDetailsForms2.size();
                                int size2 = general_Phc_Exs_CouncilDetailsForms3.size();
                                int size3 = general_Phc_Exs_CouncilDetailsForms4.size();
                                int size4 = general_Phc_Exs_CouncilDetailsForms5.size();
                                int size5 = general_Phc_Exs_CouncilDetailsForms6.size();
                                if (size != 0 && size == size1 && size == size2 && size == size3 && size == size4 && size == size5) {
                                    ArrayList<CouncilDetailsForm> general_Phc_Exs_CouncilDetailsForms_w = new ArrayList<CouncilDetailsForm>();
                                    for (int i9 = 0; i9 < size; ++i9) {
                                        CouncilDetailsForm general_Phc_Exs_CouncilDetailsForm_w = new CouncilDetailsForm();
                                        CouncilDetailsForm general_Phc_Exs_CouncilDetailsForm1_w = (CouncilDetailsForm) general_Phc_Exs_CouncilDetailsForms1.get(i9);
                                        CouncilDetailsForm general_Phc_Exs_CouncilDetailsForm2_w = (CouncilDetailsForm) general_Phc_Exs_CouncilDetailsForms2.get(i9);
                                        CouncilDetailsForm general_Phc_Exs_CouncilDetailsForm3_w = (CouncilDetailsForm) general_Phc_Exs_CouncilDetailsForms3.get(i9);
                                        CouncilDetailsForm general_Phc_Exs_CouncilDetailsForm4_w = (CouncilDetailsForm) general_Phc_Exs_CouncilDetailsForms4.get(i9);
                                        CouncilDetailsForm general_Phc_Exs_CouncilDetailsForm5_w = (CouncilDetailsForm) general_Phc_Exs_CouncilDetailsForms5.get(i9);
                                        CouncilDetailsForm general_Phc_Exs_CouncilDetailsForm6_w = (CouncilDetailsForm) general_Phc_Exs_CouncilDetailsForms6.get(i9);
                                        int checkName = 0;
                                        if (i9 > 0) {
                                            CouncilDetailsForm general_Phc_Exs_CouncilDetailsForm_wpre = (CouncilDetailsForm) general_Phc_Exs_CouncilDetailsForms1.get(i9 - 1);
                                            String iti_code1 = general_Phc_Exs_CouncilDetailsForm_wpre.getIti_code();
                                            String iti_code2 = general_Phc_Exs_CouncilDetailsForm1_w.getIti_code();
                                            if (i9 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        general_Phc_Exs_CouncilDetailsForm_w.setIti_name(general_Phc_Exs_CouncilDetailsForm1_w.getIti_name());
                                        general_Phc_Exs_CouncilDetailsForm_w.setIti_code(general_Phc_Exs_CouncilDetailsForm1_w.getIti_code());
                                        general_Phc_Exs_CouncilDetailsForm_w.setTrade_code(general_Phc_Exs_CouncilDetailsForm1_w.getTrade_code());
                                        general_Phc_Exs_CouncilDetailsForm_w.setTrade_short(general_Phc_Exs_CouncilDetailsForm1_w.getTrade_short());
                                        general_Phc_Exs_CouncilDetailsForm_w.setTrade_name(general_Phc_Exs_CouncilDetailsForm1_w.getTrade_name());
                                        general_Phc_Exs_CouncilDetailsForm_w.setTotalStrength(general_Phc_Exs_CouncilDetailsForm1_w.getTotalStrength());
                                        general_Phc_Exs_CouncilDetailsForm_w.setGeneralStrength(general_Phc_Exs_CouncilDetailsForm1_w.getGeneralStrength());
                                        general_Phc_Exs_CouncilDetailsForm_w.setGeneralStrength_fill(general_Phc_Exs_CouncilDetailsForm1_w.getGeneralStrength_fill());
                                        general_Phc_Exs_CouncilDetailsForm_w.setGeneralStrength_vacant(general_Phc_Exs_CouncilDetailsForm1_w.getGeneralStrength_vacant());
                                        general_Phc_Exs_CouncilDetailsForm_w.setGeneralStrength_w(general_Phc_Exs_CouncilDetailsForm2_w.getGeneralStrength_w());
                                        general_Phc_Exs_CouncilDetailsForm_w.setGeneralStrength_fill_w(general_Phc_Exs_CouncilDetailsForm2_w.getGeneralStrength_fill_w());
                                        general_Phc_Exs_CouncilDetailsForm_w.setGeneralStrength_vacant_w(general_Phc_Exs_CouncilDetailsForm2_w.getGeneralStrength_vacant_w());
                                        general_Phc_Exs_CouncilDetailsForm_w.setPhcStrength(general_Phc_Exs_CouncilDetailsForm3_w.getPhcStrength());
                                        general_Phc_Exs_CouncilDetailsForm_w.setPhcStrength_fill(general_Phc_Exs_CouncilDetailsForm3_w.getPhcStrength_fill());
                                        general_Phc_Exs_CouncilDetailsForm_w.setPhcStrength_vacant(general_Phc_Exs_CouncilDetailsForm3_w.getPhcStrength_vacant());
                                        general_Phc_Exs_CouncilDetailsForm_w.setPhcStrength_w(general_Phc_Exs_CouncilDetailsForm4_w.getPhcStrength_w());
                                        general_Phc_Exs_CouncilDetailsForm_w.setPhcStrength_fill_w(general_Phc_Exs_CouncilDetailsForm4_w.getPhcStrength_fill_w());
                                        general_Phc_Exs_CouncilDetailsForm_w.setPhcStrength_vacant_w(general_Phc_Exs_CouncilDetailsForm4_w.getPhcStrength_vacant_w());
                                        general_Phc_Exs_CouncilDetailsForm_w.setExserviceStrength(general_Phc_Exs_CouncilDetailsForm5_w.getExserviceStrength());
                                        general_Phc_Exs_CouncilDetailsForm_w.setExserviceStrength_fill(general_Phc_Exs_CouncilDetailsForm5_w.getExserviceStrength_fill());
                                        general_Phc_Exs_CouncilDetailsForm_w.setExserviceStrength_vacant(general_Phc_Exs_CouncilDetailsForm5_w.getExserviceStrength_vacant());
                                        general_Phc_Exs_CouncilDetailsForm_w.setExserviceStrength_w(general_Phc_Exs_CouncilDetailsForm6_w.getExserviceStrength_w());
                                        general_Phc_Exs_CouncilDetailsForm_w.setExserviceStrength_fill_w(general_Phc_Exs_CouncilDetailsForm6_w.getExserviceStrength_fill_w());
                                        general_Phc_Exs_CouncilDetailsForm_w.setExserviceStrength_vacant_w(general_Phc_Exs_CouncilDetailsForm6_w.getExserviceStrength_vacant_w());
                                        int check = 0;
                                        if (general_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (general_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() > 0) {
                                            check = 2;
                                        } else if (general_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant() > 0) {
                                            check = 5;
                                        } else if (general_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant_w() > 0) {
                                            check = 6;
                                        } else if (general_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant_w() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getExserviceStrength_vacant() > 0) {
                                            check = 7;
                                        } else if (general_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant_w() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getExserviceStrength_vacant() == 0 && general_Phc_Exs_CouncilDetailsForm_w.getExserviceStrength_vacant_w() > 0) {
                                            check = 8;
                                        }
                                        general_Phc_Exs_CouncilDetailsForm_w.setCheck(check);
                                        general_Phc_Exs_CouncilDetailsForm_w.setCheckName(checkName);
                                        general_Phc_Exs_CouncilDetailsForms_w.add(general_Phc_Exs_CouncilDetailsForm_w);
                                    }
                                    request.setAttribute("general_Phc_Exs_CouncilDetailsForms_w", general_Phc_Exs_CouncilDetailsForms_w);
                                    ActionForward i9 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i9;
                                }
                                ActionForward general_Phc_Exs_CouncilDetailsForms_w = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return general_Phc_Exs_CouncilDetailsForms_w;
                            }
                            if (!caste_code.equals(ocCastCode) && itiAdmissionForm.getSex().equalsIgnoreCase("male") && !itiAdmissionForm.getPhc().equals("Yes") && !itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is--------999999999999999999-----------");
                                ArrayList reservationCouncilDetailsForms1 = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList reservationCouncilDetailsForms2 = registrationDao.getReservationCouncilDistrictDetails(dist_code, category_code, request);
                                int size = reservationCouncilDetailsForms1.size();
                                int size1 = reservationCouncilDetailsForms2.size();
                                if (size != 0 && size == size1) {
                                    ArrayList<CouncilDetailsForm> reservationCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
                                    for (int i10 = 0; i10 < size; ++i10) {
                                        CouncilDetailsForm reservationCouncilDetailsForm = new CouncilDetailsForm();
                                        CouncilDetailsForm reservationCouncilDetailsForm1 = (CouncilDetailsForm) reservationCouncilDetailsForms1.get(i10);
                                        CouncilDetailsForm reservationCouncilDetailsForm2 = (CouncilDetailsForm) reservationCouncilDetailsForms2.get(i10);
                                        int checkName = 0;
                                        if (i10 > 0) {
                                            CouncilDetailsForm reservationCouncilDetailsFormpre = (CouncilDetailsForm) reservationCouncilDetailsForms1.get(i10 - 1);
                                            String iti_code1 = reservationCouncilDetailsFormpre.getIti_code();
                                            String iti_code2 = reservationCouncilDetailsForm1.getIti_code();
                                            if (i10 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        reservationCouncilDetailsForm.setIti_name(reservationCouncilDetailsForm1.getIti_name());
                                        reservationCouncilDetailsForm.setIti_code(reservationCouncilDetailsForm1.getIti_code());
                                        reservationCouncilDetailsForm.setTrade_code(reservationCouncilDetailsForm1.getTrade_code());
                                        reservationCouncilDetailsForm.setTrade_short(reservationCouncilDetailsForm1.getTrade_short());
                                        reservationCouncilDetailsForm.setTrade_name(reservationCouncilDetailsForm1.getTrade_name());
                                        reservationCouncilDetailsForm.setTotalStrength(reservationCouncilDetailsForm1.getTotalStrength());
                                        reservationCouncilDetailsForms.add(reservationCouncilDetailsForm);
                                        reservationCouncilDetailsForm.setGeneralStrength(reservationCouncilDetailsForm1.getGeneralStrength());
                                        reservationCouncilDetailsForm.setGeneralStrength_fill(reservationCouncilDetailsForm1.getGeneralStrength_fill());
                                        reservationCouncilDetailsForm.setGeneralStrength_vacant(reservationCouncilDetailsForm1.getGeneralStrength_vacant());
                                        reservationCouncilDetailsForm.setReservationStrength(reservationCouncilDetailsForm2.getReservationStrength());
                                        reservationCouncilDetailsForm.setReservationStrength_fill(reservationCouncilDetailsForm2.getReservationStrength_fill());
                                        reservationCouncilDetailsForm.setReservationStrength_vacant(reservationCouncilDetailsForm2.getReservationStrength_vacant());
                                        int check = 0;
                                        if (reservationCouncilDetailsForm.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (reservationCouncilDetailsForm.getGeneralStrength_vacant() == 0 && reservationCouncilDetailsForm.getReservationStrength_vacant() > 0) {
                                            check = 3;
                                        }
                                        reservationCouncilDetailsForm.setCheck(check);
                                        reservationCouncilDetailsForm.setCheckName(checkName);
                                    }
                                    request.setAttribute("reservationCouncilDetailsForms", reservationCouncilDetailsForms);
                                    ActionForward i10 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i10;
                                }
                                ActionForward reservationCouncilDetailsForms = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return reservationCouncilDetailsForms;
                            }
                            if (!caste_code.equals(ocCastCode) && itiAdmissionForm.getSex().equalsIgnoreCase("male") && itiAdmissionForm.getPhc().equals("Yes") && !itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is--------10000000000-----------");
                                ArrayList reservationPhcCouncilDetailsForms1 = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList reservationPhcCouncilDetailsForms2 = registrationDao.getReservationCouncilDistrictDetails(dist_code, category_code, request);
                                ArrayList reservationPhcCouncilDetailsForms3 = registrationDao.getPhcCouncilDistrictDetails(dist_code, phCode, request);
                                int size = reservationPhcCouncilDetailsForms1.size();
                                int size1 = reservationPhcCouncilDetailsForms2.size();
                                int size2 = reservationPhcCouncilDetailsForms3.size();
                                if (size != 0 && size == size1 && size == size2) {
                                    ArrayList<CouncilDetailsForm> reservationPhcCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
                                    for (int i11 = 0; i11 < size; ++i11) {
                                        CouncilDetailsForm reservationPhcCouncilDetailsForm = new CouncilDetailsForm();
                                        CouncilDetailsForm reservationPhcCouncilDetailsForm1 = (CouncilDetailsForm) reservationPhcCouncilDetailsForms1.get(i11);
                                        CouncilDetailsForm reservationPhcCouncilDetailsForm2 = (CouncilDetailsForm) reservationPhcCouncilDetailsForms2.get(i11);
                                        CouncilDetailsForm reservationPhcCouncilDetailsForm3 = (CouncilDetailsForm) reservationPhcCouncilDetailsForms3.get(i11);
                                        int checkName = 0;
                                        if (i11 > 0) {
                                            CouncilDetailsForm reservationPhcCouncilDetailsFormpre = (CouncilDetailsForm) reservationPhcCouncilDetailsForms1.get(i11 - 1);
                                            String iti_code1 = reservationPhcCouncilDetailsFormpre.getIti_code();
                                            String iti_code2 = reservationPhcCouncilDetailsForm1.getIti_code();
                                            if (i11 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        reservationPhcCouncilDetailsForm.setIti_name(reservationPhcCouncilDetailsForm1.getIti_name());
                                        reservationPhcCouncilDetailsForm.setIti_code(reservationPhcCouncilDetailsForm1.getIti_code());
                                        reservationPhcCouncilDetailsForm.setTrade_code(reservationPhcCouncilDetailsForm1.getTrade_code());
                                        reservationPhcCouncilDetailsForm.setTrade_short(reservationPhcCouncilDetailsForm1.getTrade_short());
                                        reservationPhcCouncilDetailsForm.setTrade_name(reservationPhcCouncilDetailsForm1.getTrade_name());
                                        reservationPhcCouncilDetailsForm.setTotalStrength(reservationPhcCouncilDetailsForm1.getTotalStrength());
                                        reservationPhcCouncilDetailsForm.setGeneralStrength(reservationPhcCouncilDetailsForm1.getGeneralStrength());
                                        reservationPhcCouncilDetailsForm.setGeneralStrength_fill(reservationPhcCouncilDetailsForm1.getGeneralStrength_fill());
                                        reservationPhcCouncilDetailsForm.setGeneralStrength_vacant(reservationPhcCouncilDetailsForm1.getGeneralStrength_vacant());
                                        reservationPhcCouncilDetailsForm.setReservationStrength(reservationPhcCouncilDetailsForm2.getReservationStrength());
                                        reservationPhcCouncilDetailsForm.setReservationStrength_fill(reservationPhcCouncilDetailsForm2.getReservationStrength_fill());
                                        reservationPhcCouncilDetailsForm.setReservationStrength_vacant(reservationPhcCouncilDetailsForm2.getReservationStrength_vacant());
                                        reservationPhcCouncilDetailsForm.setPhcStrength(reservationPhcCouncilDetailsForm3.getPhcStrength());
                                        reservationPhcCouncilDetailsForm.setPhcStrength_fill(reservationPhcCouncilDetailsForm3.getPhcStrength_fill());
                                        reservationPhcCouncilDetailsForm.setPhcStrength_vacant(reservationPhcCouncilDetailsForm3.getPhcStrength_vacant());
                                        int check = 0;
                                        if (reservationPhcCouncilDetailsForm.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (reservationPhcCouncilDetailsForm.getGeneralStrength_vacant() == 0 && reservationPhcCouncilDetailsForm.getReservationStrength_vacant() > 0) {
                                            check = 3;
                                        } else if (reservationPhcCouncilDetailsForm.getGeneralStrength_vacant() == 0 && reservationPhcCouncilDetailsForm.getReservationStrength_vacant() == 0 && reservationPhcCouncilDetailsForm.getPhcStrength_vacant() > 0) {
                                            check = 5;
                                        }
                                        reservationPhcCouncilDetailsForm.setCheck(check);
                                        reservationPhcCouncilDetailsForm.setCheckName(checkName);
                                        reservationPhcCouncilDetailsForms.add(reservationPhcCouncilDetailsForm);
                                    }
                                    request.setAttribute("reservationPhcCouncilDetailsForms", reservationPhcCouncilDetailsForms);
                                    ActionForward i11 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i11;
                                }
                                ActionForward reservationPhcCouncilDetailsForms = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return reservationPhcCouncilDetailsForms;
                            }
                            if (!caste_code.equals(ocCastCode) && itiAdmissionForm.getSex().equalsIgnoreCase("male") && !itiAdmissionForm.getPhc().equals("Yes") && itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is--------123123123123-----------");
                                ArrayList reservationExsCouncilDetailsForms1 = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList reservationExsCouncilDetailsForms2 = registrationDao.getReservationCouncilDistrictDetails(dist_code, category_code, request);
                                ArrayList reservationExsCouncilDetailsForms3 = registrationDao.getExserviceCouncilDistrictDetails(dist_code, exCode, request);
                                int size = reservationExsCouncilDetailsForms1.size();
                                int size1 = reservationExsCouncilDetailsForms2.size();
                                int size2 = reservationExsCouncilDetailsForms3.size();
                                if (size != 0 && size == size1 && size == size2) {
                                    ArrayList<CouncilDetailsForm> reservationExsCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
                                    for (int i12 = 0; i12 < size; ++i12) {
                                        CouncilDetailsForm reservationExsCouncilDetailsForm = new CouncilDetailsForm();
                                        CouncilDetailsForm reservationExsCouncilDetailsForm1 = (CouncilDetailsForm) reservationExsCouncilDetailsForms1.get(i12);
                                        CouncilDetailsForm reservationExsCouncilDetailsForm2 = (CouncilDetailsForm) reservationExsCouncilDetailsForms2.get(i12);
                                        CouncilDetailsForm reservationExsCouncilDetailsForm3 = (CouncilDetailsForm) reservationExsCouncilDetailsForms3.get(i12);
                                        int checkName = 0;
                                        if (i12 > 0) {
                                            CouncilDetailsForm reservationExsCouncilDetailsFormpre = (CouncilDetailsForm) reservationExsCouncilDetailsForms1.get(i12 - 1);
                                            String iti_code1 = reservationExsCouncilDetailsFormpre.getIti_code();
                                            String iti_code2 = reservationExsCouncilDetailsForm1.getIti_code();
                                            if (i12 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        reservationExsCouncilDetailsForm.setIti_name(reservationExsCouncilDetailsForm1.getIti_name());
                                        reservationExsCouncilDetailsForm.setIti_code(reservationExsCouncilDetailsForm1.getIti_code());
                                        reservationExsCouncilDetailsForm.setTrade_code(reservationExsCouncilDetailsForm1.getTrade_code());
                                        reservationExsCouncilDetailsForm.setTrade_short(reservationExsCouncilDetailsForm1.getTrade_short());
                                        reservationExsCouncilDetailsForm.setTrade_name(reservationExsCouncilDetailsForm1.getTrade_name());
                                        reservationExsCouncilDetailsForm.setTotalStrength(reservationExsCouncilDetailsForm1.getTotalStrength());
                                        reservationExsCouncilDetailsForm.setGeneralStrength(reservationExsCouncilDetailsForm1.getGeneralStrength());
                                        reservationExsCouncilDetailsForm.setGeneralStrength_fill(reservationExsCouncilDetailsForm1.getGeneralStrength_fill());
                                        reservationExsCouncilDetailsForm.setGeneralStrength_vacant(reservationExsCouncilDetailsForm1.getGeneralStrength_vacant());
                                        reservationExsCouncilDetailsForm.setReservationStrength(reservationExsCouncilDetailsForm2.getReservationStrength());
                                        reservationExsCouncilDetailsForm.setReservationStrength_fill(reservationExsCouncilDetailsForm2.getReservationStrength_fill());
                                        reservationExsCouncilDetailsForm.setReservationStrength_vacant(reservationExsCouncilDetailsForm2.getReservationStrength_vacant());
                                        reservationExsCouncilDetailsForm.setExserviceStrength(reservationExsCouncilDetailsForm3.getExserviceStrength());
                                        reservationExsCouncilDetailsForm.setExserviceStrength_fill(reservationExsCouncilDetailsForm3.getExserviceStrength_fill());
                                        reservationExsCouncilDetailsForm.setExserviceStrength_vacant(reservationExsCouncilDetailsForm3.getExserviceStrength_vacant());
                                        int check = 0;
                                        if (reservationExsCouncilDetailsForm.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (reservationExsCouncilDetailsForm.getGeneralStrength_vacant() == 0 && reservationExsCouncilDetailsForm.getReservationStrength_vacant() > 0) {
                                            check = 3;
                                        } else if (reservationExsCouncilDetailsForm.getGeneralStrength_vacant() == 0 && reservationExsCouncilDetailsForm.getReservationStrength_vacant() == 0 && reservationExsCouncilDetailsForm.getExserviceStrength_vacant() > 0) {
                                            check = 7;
                                        }
                                        reservationExsCouncilDetailsForm.setCheck(check);
                                        reservationExsCouncilDetailsForm.setCheckName(checkName);
                                        reservationExsCouncilDetailsForms.add(reservationExsCouncilDetailsForm);
                                    }
                                    request.setAttribute("reservationExsCouncilDetailsForms", reservationExsCouncilDetailsForms);
                                    ActionForward i12 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i12;
                                }

                                ActionForward reservationExsCouncilDetailsForms = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return reservationExsCouncilDetailsForms;
                            }
                            if (!caste_code.equals(ocCastCode) && itiAdmissionForm.getSex().equalsIgnoreCase("male") && itiAdmissionForm.getPhc().equals("Yes") && itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is--------1241241246-----------");
                                System.out.println("phc--->" + itiAdmissionForm.getPhc());
                                System.out.println("exservice--->" + itiAdmissionForm.getExservice());
                                ArrayList reservation_Ph_Exs_CouncilDetailsForms1 = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList reservation_Ph_Exs_CouncilDetailsForms2 = registrationDao.getReservationCouncilDistrictDetails(dist_code, category_code, request);
                                ArrayList reservation_Ph_Exs_CouncilDetailsForms3 = registrationDao.getPhcCouncilDistrictDetails(dist_code, phCode, request);
                                ArrayList reservation_Ph_Exs_CouncilDetailsForms4 = registrationDao.getExserviceCouncilDistrictDetails(dist_code, exCode, request);
                                int size = reservation_Ph_Exs_CouncilDetailsForms1.size();
                                int size1 = reservation_Ph_Exs_CouncilDetailsForms2.size();
                                int size2 = reservation_Ph_Exs_CouncilDetailsForms3.size();
                                int size3 = reservation_Ph_Exs_CouncilDetailsForms4.size();
                                System.out.println("size---" + size);
                                System.out.println("array----" + reservation_Ph_Exs_CouncilDetailsForms1);
                                if (size != 0 && size == size1 && size == size2 && size == size3) {

                                    ArrayList<CouncilDetailsForm> reservation_Ph_Exs_CouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
                                    for (int i13 = 0; i13 < size; ++i13) {
                                        CouncilDetailsForm reservation_Ph_Exs_CouncilDetailsForm = new CouncilDetailsForm();
                                        CouncilDetailsForm reservation_Ph_Exs_CouncilDetailsForm1 = (CouncilDetailsForm) reservation_Ph_Exs_CouncilDetailsForms1.get(i13);
                                        CouncilDetailsForm reservation_Ph_Exs_CouncilDetailsForm2 = (CouncilDetailsForm) reservation_Ph_Exs_CouncilDetailsForms2.get(i13);
                                        CouncilDetailsForm reservation_Ph_Exs_CouncilDetailsForm3 = (CouncilDetailsForm) reservation_Ph_Exs_CouncilDetailsForms3.get(i13);
                                        CouncilDetailsForm reservation_Ph_Exs_CouncilDetailsForm4 = (CouncilDetailsForm) reservation_Ph_Exs_CouncilDetailsForms4.get(i13);
                                        int checkName = 0;
                                        if (i13 > 0) {
                                            CouncilDetailsForm reservation_Ph_Exs_CouncilDetailsFormpre = (CouncilDetailsForm) reservation_Ph_Exs_CouncilDetailsForms1.get(i13 - 1);
                                            String iti_code1 = reservation_Ph_Exs_CouncilDetailsFormpre.getIti_code();
                                            String iti_code2 = reservation_Ph_Exs_CouncilDetailsForm1.getIti_code();
                                            if (i13 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        reservation_Ph_Exs_CouncilDetailsForm.setIti_name(reservation_Ph_Exs_CouncilDetailsForm1.getIti_name());
                                        reservation_Ph_Exs_CouncilDetailsForm.setIti_code(reservation_Ph_Exs_CouncilDetailsForm1.getIti_code());
                                        reservation_Ph_Exs_CouncilDetailsForm.setTrade_code(reservation_Ph_Exs_CouncilDetailsForm1.getTrade_code());
                                        reservation_Ph_Exs_CouncilDetailsForm.setTrade_short(reservation_Ph_Exs_CouncilDetailsForm1.getTrade_short());
                                        reservation_Ph_Exs_CouncilDetailsForm.setTrade_name(reservation_Ph_Exs_CouncilDetailsForm1.getTrade_name());
                                        reservation_Ph_Exs_CouncilDetailsForm.setTotalStrength(reservation_Ph_Exs_CouncilDetailsForm1.getTotalStrength());
                                        reservation_Ph_Exs_CouncilDetailsForm.setGeneralStrength(reservation_Ph_Exs_CouncilDetailsForm1.getGeneralStrength());
                                        reservation_Ph_Exs_CouncilDetailsForm.setGeneralStrength_fill(reservation_Ph_Exs_CouncilDetailsForm1.getGeneralStrength_fill());
                                        reservation_Ph_Exs_CouncilDetailsForm.setGeneralStrength_vacant(reservation_Ph_Exs_CouncilDetailsForm1.getGeneralStrength_vacant());
                                        reservation_Ph_Exs_CouncilDetailsForm.setReservationStrength(reservation_Ph_Exs_CouncilDetailsForm2.getReservationStrength());
                                        reservation_Ph_Exs_CouncilDetailsForm.setReservationStrength_fill(reservation_Ph_Exs_CouncilDetailsForm2.getReservationStrength_fill());
                                        reservation_Ph_Exs_CouncilDetailsForm.setReservationStrength_vacant(reservation_Ph_Exs_CouncilDetailsForm2.getReservationStrength_vacant());
                                        reservation_Ph_Exs_CouncilDetailsForm.setPhcStrength(reservation_Ph_Exs_CouncilDetailsForm3.getPhcStrength());
                                        reservation_Ph_Exs_CouncilDetailsForm.setPhcStrength_fill(reservation_Ph_Exs_CouncilDetailsForm3.getPhcStrength_fill());
                                        reservation_Ph_Exs_CouncilDetailsForm.setPhcStrength_vacant(reservation_Ph_Exs_CouncilDetailsForm3.getPhcStrength_vacant());
                                        reservation_Ph_Exs_CouncilDetailsForm.setExserviceStrength(reservation_Ph_Exs_CouncilDetailsForm4.getExserviceStrength());
                                        reservation_Ph_Exs_CouncilDetailsForm.setExserviceStrength_fill(reservation_Ph_Exs_CouncilDetailsForm4.getExserviceStrength_fill());
                                        reservation_Ph_Exs_CouncilDetailsForm.setExserviceStrength_vacant(reservation_Ph_Exs_CouncilDetailsForm4.getExserviceStrength_vacant());
                                        int check = 0;
                                        if (reservation_Ph_Exs_CouncilDetailsForm.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (reservation_Ph_Exs_CouncilDetailsForm.getGeneralStrength_vacant() == 0 && reservation_Ph_Exs_CouncilDetailsForm.getReservationStrength_vacant() > 0) {
                                            check = 3;
                                        } else if (reservation_Ph_Exs_CouncilDetailsForm.getGeneralStrength_vacant() == 0 && reservation_Ph_Exs_CouncilDetailsForm.getReservationStrength_vacant() == 0 && reservation_Ph_Exs_CouncilDetailsForm.getPhcStrength_vacant() > 0) {
                                            check = 5;
                                        } else if (reservation_Ph_Exs_CouncilDetailsForm.getGeneralStrength_vacant() == 0 && reservation_Ph_Exs_CouncilDetailsForm.getReservationStrength_vacant() == 0 && reservation_Ph_Exs_CouncilDetailsForm.getPhcStrength_vacant() == 0 && reservation_Ph_Exs_CouncilDetailsForm.getExserviceStrength_vacant() > 0) {
                                            check = 7;
                                        }
                                        reservation_Ph_Exs_CouncilDetailsForm.setCheck(check);
                                        reservation_Ph_Exs_CouncilDetailsForm.setCheckName(checkName);
                                        reservation_Ph_Exs_CouncilDetailsForms.add(reservation_Ph_Exs_CouncilDetailsForm);
                                    }

                                    request.setAttribute("reservation_Ph_Exs_CouncilDetailsForms", reservation_Ph_Exs_CouncilDetailsForms);
                                    ActionForward i13 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i13;
                                }
                                System.out.println("category check for IM..........");
                                ActionForward reservation_Ph_Exs_CouncilDetailsForms = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return reservation_Ph_Exs_CouncilDetailsForms;
                            }
                            if (!(caste_code.equals(ocCastCode) || itiAdmissionForm.getSex().equalsIgnoreCase("male") || itiAdmissionForm.getPhc().equals("Yes") || itiAdmissionForm.getExservice().equals("Yes"))) {
                                System.out.println("The phc is--------125125125-----------");
                                String category_code1 = null;
                                category_code1 = caste_code.length() == 2 ? registrationDao.getCategoryCode(category_code.substring(0, category_code.length() - 2), request) : registrationDao.getCategoryCode(category_code.substring(0, category_code.length() - 1), request);
                                System.out.println("category_code1 is---->" + category_code1);
                                System.out.println("category_code is---->" + category_code);

                                ArrayList reservationCouncilDetailsForms1_w = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList reservationCouncilDetailsForms2_w = registrationDao.getGeneral_wCouncilDistrictDetails(dist_code, oc_wCode, request);
                                ArrayList reservationCouncilDetailsForms3_w = registrationDao.getReservationCouncilDistrictDetails(dist_code, category_code1, request);
                                ArrayList reservationCouncilDetailsForms4_w = registrationDao.getReservation_wCouncilDistrictDetails(dist_code, category_code, request);
                                int size = reservationCouncilDetailsForms1_w.size();
                                int size1 = reservationCouncilDetailsForms2_w.size();
                                int size2 = reservationCouncilDetailsForms3_w.size();
                                int size3 = reservationCouncilDetailsForms4_w.size();
                                if (size != 0 && size == size1 && size == size2 && size == size3) {
                                    ArrayList<CouncilDetailsForm> reservationCouncilDetailsForms_w = new ArrayList<CouncilDetailsForm>();
                                    for (int i14 = 0; i14 < size; ++i14) {
                                        CouncilDetailsForm reservationCouncilDetailsForm_w = new CouncilDetailsForm();
                                        CouncilDetailsForm reservationCouncilDetailsForm1 = (CouncilDetailsForm) reservationCouncilDetailsForms1_w.get(i14);
                                        CouncilDetailsForm reservationCouncilDetailsForm2 = (CouncilDetailsForm) reservationCouncilDetailsForms2_w.get(i14);
                                        CouncilDetailsForm reservationCouncilDetailsForm3 = (CouncilDetailsForm) reservationCouncilDetailsForms3_w.get(i14);
                                        CouncilDetailsForm reservationCouncilDetailsForm4 = (CouncilDetailsForm) reservationCouncilDetailsForms4_w.get(i14);
                                        int checkName = 0;
                                        if (i14 > 0) {
                                            CouncilDetailsForm reservationCouncilDetailsForm_wpre = (CouncilDetailsForm) reservationCouncilDetailsForms1_w.get(i14 - 1);
                                            String iti_code1 = reservationCouncilDetailsForm_wpre.getIti_code();
                                            String iti_code2 = reservationCouncilDetailsForm1.getIti_code();
                                            if (i14 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        reservationCouncilDetailsForm_w.setIti_name(reservationCouncilDetailsForm1.getIti_name());
                                        reservationCouncilDetailsForm_w.setIti_code(reservationCouncilDetailsForm1.getIti_code());
                                        reservationCouncilDetailsForm_w.setTrade_code(reservationCouncilDetailsForm1.getTrade_code());
                                        reservationCouncilDetailsForm_w.setTrade_short(reservationCouncilDetailsForm1.getTrade_short());
                                        reservationCouncilDetailsForm_w.setTrade_name(reservationCouncilDetailsForm1.getTrade_name());
                                        reservationCouncilDetailsForm_w.setTotalStrength(reservationCouncilDetailsForm1.getTotalStrength());
                                        reservationCouncilDetailsForm_w.setGeneralStrength(reservationCouncilDetailsForm1.getGeneralStrength());
                                        reservationCouncilDetailsForm_w.setGeneralStrength_fill(reservationCouncilDetailsForm1.getGeneralStrength_fill());
                                        reservationCouncilDetailsForm_w.setGeneralStrength_vacant(reservationCouncilDetailsForm1.getGeneralStrength_vacant());
                                        reservationCouncilDetailsForm_w.setGeneralStrength_w(reservationCouncilDetailsForm2.getGeneralStrength_w());
                                        reservationCouncilDetailsForm_w.setGeneralStrength_fill_w(reservationCouncilDetailsForm2.getGeneralStrength_fill_w());
                                        reservationCouncilDetailsForm_w.setGeneralStrength_vacant_w(reservationCouncilDetailsForm2.getGeneralStrength_vacant_w());
                                        reservationCouncilDetailsForm_w.setReservationStrength(reservationCouncilDetailsForm3.getReservationStrength());
                                        reservationCouncilDetailsForm_w.setReservationStrength_fill(reservationCouncilDetailsForm3.getReservationStrength_fill());
                                        reservationCouncilDetailsForm_w.setReservationStrength_vacant(reservationCouncilDetailsForm3.getReservationStrength_vacant());
                                        reservationCouncilDetailsForm_w.setReservationStrength_w(reservationCouncilDetailsForm4.getReservationStrength_w());
                                        reservationCouncilDetailsForm_w.setReservationStrength_fill_w(reservationCouncilDetailsForm4.getReservationStrength_fill_w());
                                        reservationCouncilDetailsForm_w.setReservationStrength_vacant_w(reservationCouncilDetailsForm4.getReservationStrength_vacant_w());
                                        int check = 0;
                                        if (reservationCouncilDetailsForm_w.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (reservationCouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservationCouncilDetailsForm_w.getGeneralStrength_vacant_w() > 0) {
                                            check = 2;
                                        } else if (reservationCouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservationCouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservationCouncilDetailsForm_w.getReservationStrength_vacant() > 0) {
                                            check = 3;
                                        } else if (reservationCouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservationCouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservationCouncilDetailsForm_w.getReservationStrength_vacant() == 0 && reservationCouncilDetailsForm_w.getReservationStrength_vacant_w() > 0) {
                                            check = 4;
                                        }
                                        reservationCouncilDetailsForm_w.setCheck(check);
                                        reservationCouncilDetailsForm_w.setCheckName(checkName);
                                        reservationCouncilDetailsForms_w.add(reservationCouncilDetailsForm_w);
                                    }
                                    request.setAttribute("reservationCouncilDetailsForms_w", reservationCouncilDetailsForms_w);
                                    ActionForward i14 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i14;
                                }
                                ActionForward reservationCouncilDetailsForms_w = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return reservationCouncilDetailsForms_w;
                            }
                            if (!caste_code.equals(ocCastCode) && !itiAdmissionForm.getSex().equalsIgnoreCase("male") && itiAdmissionForm.getPhc().equals("Yes") && !itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is--------126126126-----------");
                                String category_code1 = null;
                                category_code1 = caste_code.length() == 2 ? registrationDao.getCategoryCode(category_code.substring(0, category_code.length() - 2), request) : registrationDao.getCategoryCode(category_code.substring(0, category_code.length() - 1), request);
                                ArrayList reservation_Phc_CouncilDetailsForms1_w = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList reservation_Phc_CouncilDetailsForms2_w = registrationDao.getGeneral_wCouncilDistrictDetails(dist_code, oc_wCode, request);
                                ArrayList reservation_Phc_CouncilDetailsForms3_w = registrationDao.getReservationCouncilDistrictDetails(dist_code, category_code1, request);
                                ArrayList reservation_Phc_CouncilDetailsForms4_w = registrationDao.getReservation_wCouncilDistrictDetails(dist_code, category_code, request);
                                ArrayList reservation_Phc_CouncilDetailsForms5_w = registrationDao.getPhcCouncilDistrictDetails(dist_code, phCode, request);
                                ArrayList reservation_Phc_CouncilDetailsForms6_w = registrationDao.getPhc_wCouncilDistrictDetails(dist_code, ph_wCode, request);
                                int size = reservation_Phc_CouncilDetailsForms1_w.size();
                                int size1 = reservation_Phc_CouncilDetailsForms2_w.size();
                                int size2 = reservation_Phc_CouncilDetailsForms3_w.size();
                                int size3 = reservation_Phc_CouncilDetailsForms4_w.size();
                                int size4 = reservation_Phc_CouncilDetailsForms5_w.size();
                                int size5 = reservation_Phc_CouncilDetailsForms6_w.size();
                                if (size != 0 && size == size1 && size == size2 && size == size3 && size == size4 && size == size5) {
                                    ArrayList<CouncilDetailsForm> reservation_Phc_CouncilDetailsForms_w = new ArrayList<CouncilDetailsForm>();
                                    for (int i15 = 0; i15 < size; ++i15) {
                                        CouncilDetailsForm reservation_Phc_CouncilDetailsForm_w = new CouncilDetailsForm();
                                        CouncilDetailsForm reservation_Phc_CouncilDetailsForm1 = (CouncilDetailsForm) reservation_Phc_CouncilDetailsForms1_w.get(i15);
                                        CouncilDetailsForm reservation_Phc_CouncilDetailsForm2 = (CouncilDetailsForm) reservation_Phc_CouncilDetailsForms2_w.get(i15);
                                        CouncilDetailsForm reservation_Phc_CouncilDetailsForm3 = (CouncilDetailsForm) reservation_Phc_CouncilDetailsForms3_w.get(i15);
                                        CouncilDetailsForm reservation_Phc_CouncilDetailsForm4 = (CouncilDetailsForm) reservation_Phc_CouncilDetailsForms4_w.get(i15);
                                        CouncilDetailsForm reservation_Phc_CouncilDetailsForm5 = (CouncilDetailsForm) reservation_Phc_CouncilDetailsForms5_w.get(i15);
                                        CouncilDetailsForm reservation_Phc_CouncilDetailsForm6 = (CouncilDetailsForm) reservation_Phc_CouncilDetailsForms6_w.get(i15);
                                        int checkName = 0;
                                        if (i15 > 0) {
                                            CouncilDetailsForm reservation_Phc_CouncilDetailsForm_wpre = (CouncilDetailsForm) reservation_Phc_CouncilDetailsForms1_w.get(i15 - 1);
                                            String iti_code1 = reservation_Phc_CouncilDetailsForm_wpre.getIti_code();
                                            String iti_code2 = reservation_Phc_CouncilDetailsForm1.getIti_code();
                                            if (i15 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        reservation_Phc_CouncilDetailsForm_w.setIti_name(reservation_Phc_CouncilDetailsForm1.getIti_name());
                                        reservation_Phc_CouncilDetailsForm_w.setIti_code(reservation_Phc_CouncilDetailsForm1.getIti_code());
                                        reservation_Phc_CouncilDetailsForm_w.setTrade_code(reservation_Phc_CouncilDetailsForm1.getTrade_code());
                                        reservation_Phc_CouncilDetailsForm_w.setTrade_short(reservation_Phc_CouncilDetailsForm1.getTrade_short());
                                        reservation_Phc_CouncilDetailsForm_w.setTrade_name(reservation_Phc_CouncilDetailsForm1.getTrade_name());
                                        reservation_Phc_CouncilDetailsForm_w.setTotalStrength(reservation_Phc_CouncilDetailsForm1.getTotalStrength());
                                        reservation_Phc_CouncilDetailsForm_w.setGeneralStrength(reservation_Phc_CouncilDetailsForm1.getGeneralStrength());
                                        reservation_Phc_CouncilDetailsForm_w.setGeneralStrength_fill(reservation_Phc_CouncilDetailsForm1.getGeneralStrength_fill());
                                        reservation_Phc_CouncilDetailsForm_w.setGeneralStrength_vacant(reservation_Phc_CouncilDetailsForm1.getGeneralStrength_vacant());
                                        reservation_Phc_CouncilDetailsForm_w.setGeneralStrength_w(reservation_Phc_CouncilDetailsForm2.getGeneralStrength_w());
                                        reservation_Phc_CouncilDetailsForm_w.setGeneralStrength_fill_w(reservation_Phc_CouncilDetailsForm2.getGeneralStrength_fill_w());
                                        reservation_Phc_CouncilDetailsForm_w.setGeneralStrength_vacant_w(reservation_Phc_CouncilDetailsForm2.getGeneralStrength_vacant_w());
                                        reservation_Phc_CouncilDetailsForm_w.setReservationStrength(reservation_Phc_CouncilDetailsForm3.getReservationStrength());
                                        reservation_Phc_CouncilDetailsForm_w.setReservationStrength_fill(reservation_Phc_CouncilDetailsForm3.getReservationStrength_fill());
                                        reservation_Phc_CouncilDetailsForm_w.setReservationStrength_vacant(reservation_Phc_CouncilDetailsForm3.getReservationStrength_vacant());
                                        reservation_Phc_CouncilDetailsForm_w.setReservationStrength_w(reservation_Phc_CouncilDetailsForm4.getReservationStrength_w());
                                        reservation_Phc_CouncilDetailsForm_w.setReservationStrength_fill_w(reservation_Phc_CouncilDetailsForm4.getReservationStrength_fill_w());
                                        reservation_Phc_CouncilDetailsForm_w.setReservationStrength_vacant_w(reservation_Phc_CouncilDetailsForm4.getReservationStrength_vacant_w());
                                        reservation_Phc_CouncilDetailsForm_w.setPhcStrength(reservation_Phc_CouncilDetailsForm5.getPhcStrength());
                                        reservation_Phc_CouncilDetailsForm_w.setPhcStrength_fill(reservation_Phc_CouncilDetailsForm5.getPhcStrength_fill());
                                        reservation_Phc_CouncilDetailsForm_w.setPhcStrength_vacant(reservation_Phc_CouncilDetailsForm5.getPhcStrength_vacant());
                                        reservation_Phc_CouncilDetailsForm_w.setPhcStrength_w(reservation_Phc_CouncilDetailsForm6.getPhcStrength_w());
                                        reservation_Phc_CouncilDetailsForm_w.setPhcStrength_fill_w(reservation_Phc_CouncilDetailsForm6.getPhcStrength_fill_w());
                                        reservation_Phc_CouncilDetailsForm_w.setPhcStrength_vacant_w(reservation_Phc_CouncilDetailsForm6.getPhcStrength_vacant_w());
                                        int check = 0;
                                        if (reservation_Phc_CouncilDetailsForm_w.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (reservation_Phc_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Phc_CouncilDetailsForm_w.getGeneralStrength_vacant_w() > 0) {
                                            check = 2;
                                        } else if (reservation_Phc_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Phc_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Phc_CouncilDetailsForm_w.getReservationStrength_vacant() > 0) {
                                            check = 3;
                                        } else if (reservation_Phc_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Phc_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Phc_CouncilDetailsForm_w.getReservationStrength_vacant() == 0 && reservation_Phc_CouncilDetailsForm_w.getReservationStrength_vacant_w() > 0) {
                                            check = 4;
                                        } else if (reservation_Phc_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Phc_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Phc_CouncilDetailsForm_w.getReservationStrength_vacant() == 0 && reservation_Phc_CouncilDetailsForm_w.getReservationStrength_vacant_w() == 0 && reservation_Phc_CouncilDetailsForm_w.getPhcStrength_vacant() > 0) {
                                            check = 5;
                                        } else if (reservation_Phc_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Phc_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Phc_CouncilDetailsForm_w.getReservationStrength_vacant() == 0 && reservation_Phc_CouncilDetailsForm_w.getReservationStrength_vacant_w() == 0 && reservation_Phc_CouncilDetailsForm_w.getPhcStrength_vacant() == 0 && reservation_Phc_CouncilDetailsForm_w.getPhcStrength_vacant_w() > 0) {
                                            check = 6;
                                        }
                                        reservation_Phc_CouncilDetailsForm_w.setCheck(check);
                                        reservation_Phc_CouncilDetailsForm_w.setCheckName(checkName);
                                        reservation_Phc_CouncilDetailsForms_w.add(reservation_Phc_CouncilDetailsForm_w);
                                    }
                                    request.setAttribute("reservation_Phc_CouncilDetailsForms_w", reservation_Phc_CouncilDetailsForms_w);
                                    ActionForward i15 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i15;
                                }
                                ActionForward reservation_Phc_CouncilDetailsForms_w = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return reservation_Phc_CouncilDetailsForms_w;
                            }
                            if (!caste_code.equals(ocCastCode) && !itiAdmissionForm.getSex().equalsIgnoreCase("male") && !itiAdmissionForm.getPhc().equals("Yes") && itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is--------127127127-----------");
                                String category_code1 = null;
                                category_code1 = caste_code.length() == 2 ? registrationDao.getCategoryCode(category_code.substring(0, category_code.length() - 2), request) : registrationDao.getCategoryCode(category_code.substring(0, category_code.length() - 1), request);
                                ArrayList reservation_Exs_CouncilDetailsForms1_w = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList reservation_Exs_CouncilDetailsForms2_w = registrationDao.getGeneral_wCouncilDistrictDetails(dist_code, oc_wCode, request);
                                ArrayList reservation_Exs_CouncilDetailsForms3_w = registrationDao.getReservationCouncilDistrictDetails(dist_code, category_code1, request);
                                ArrayList reservation_Exs_CouncilDetailsForms4_w = registrationDao.getReservation_wCouncilDistrictDetails(dist_code, category_code, request);
                                ArrayList reservation_Exs_CouncilDetailsForms5_w = registrationDao.getExserviceCouncilDistrictDetails(dist_code, exCode, request);
                                ArrayList reservation_Exs_CouncilDetailsForms6_w = registrationDao.getExservice_wCouncilDistrictDetails(dist_code, ex_wCode, request);
                                int size = reservation_Exs_CouncilDetailsForms1_w.size();
                                int size1 = reservation_Exs_CouncilDetailsForms2_w.size();
                                int size2 = reservation_Exs_CouncilDetailsForms3_w.size();
                                int size3 = reservation_Exs_CouncilDetailsForms4_w.size();
                                int size4 = reservation_Exs_CouncilDetailsForms5_w.size();
                                int size5 = reservation_Exs_CouncilDetailsForms6_w.size();
                                if (size != 0 && size == size1 && size == size2 && size == size3 && size == size4 && size == size5) {
                                    ArrayList<CouncilDetailsForm> reservation_Exs_CouncilDetailsForms_w = new ArrayList<CouncilDetailsForm>();
                                    for (int i16 = 0; i16 < size; ++i16) {
                                        CouncilDetailsForm reservation_Exs_CouncilDetailsForm_w = new CouncilDetailsForm();
                                        CouncilDetailsForm reservation_Exs_CouncilDetailsForm1 = (CouncilDetailsForm) reservation_Exs_CouncilDetailsForms1_w.get(i16);
                                        CouncilDetailsForm reservation_Exs_CouncilDetailsForm2 = (CouncilDetailsForm) reservation_Exs_CouncilDetailsForms2_w.get(i16);
                                        CouncilDetailsForm reservation_Exs_CouncilDetailsForm3 = (CouncilDetailsForm) reservation_Exs_CouncilDetailsForms3_w.get(i16);
                                        CouncilDetailsForm reservation_Exs_CouncilDetailsForm4 = (CouncilDetailsForm) reservation_Exs_CouncilDetailsForms4_w.get(i16);
                                        CouncilDetailsForm reservation_Exs_CouncilDetailsForm5 = (CouncilDetailsForm) reservation_Exs_CouncilDetailsForms5_w.get(i16);
                                        CouncilDetailsForm reservation_Exs_CouncilDetailsForm6 = (CouncilDetailsForm) reservation_Exs_CouncilDetailsForms6_w.get(i16);
                                        int checkName = 0;
                                        if (i16 > 0) {
                                            CouncilDetailsForm reservation_Exs_CouncilDetailsForm_wpre = (CouncilDetailsForm) reservation_Exs_CouncilDetailsForms1_w.get(i16 - 1);
                                            String iti_code1 = reservation_Exs_CouncilDetailsForm_wpre.getIti_code();
                                            String iti_code2 = reservation_Exs_CouncilDetailsForm1.getIti_code();
                                            if (i16 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        reservation_Exs_CouncilDetailsForm_w.setIti_name(reservation_Exs_CouncilDetailsForm1.getIti_name());
                                        reservation_Exs_CouncilDetailsForm_w.setIti_code(reservation_Exs_CouncilDetailsForm1.getIti_code());
                                        reservation_Exs_CouncilDetailsForm_w.setTrade_code(reservation_Exs_CouncilDetailsForm1.getTrade_code());
                                        reservation_Exs_CouncilDetailsForm_w.setTrade_short(reservation_Exs_CouncilDetailsForm1.getTrade_short());
                                        reservation_Exs_CouncilDetailsForm_w.setTrade_name(reservation_Exs_CouncilDetailsForm1.getTrade_name());
                                        reservation_Exs_CouncilDetailsForm_w.setTotalStrength(reservation_Exs_CouncilDetailsForm1.getTotalStrength());
                                        reservation_Exs_CouncilDetailsForm_w.setGeneralStrength(reservation_Exs_CouncilDetailsForm1.getGeneralStrength());
                                        reservation_Exs_CouncilDetailsForm_w.setGeneralStrength_fill(reservation_Exs_CouncilDetailsForm1.getGeneralStrength_fill());
                                        reservation_Exs_CouncilDetailsForm_w.setGeneralStrength_vacant(reservation_Exs_CouncilDetailsForm1.getGeneralStrength_vacant());
                                        reservation_Exs_CouncilDetailsForm_w.setGeneralStrength_w(reservation_Exs_CouncilDetailsForm2.getGeneralStrength_w());
                                        reservation_Exs_CouncilDetailsForm_w.setGeneralStrength_fill_w(reservation_Exs_CouncilDetailsForm2.getGeneralStrength_fill_w());
                                        reservation_Exs_CouncilDetailsForm_w.setGeneralStrength_vacant_w(reservation_Exs_CouncilDetailsForm2.getGeneralStrength_vacant_w());
                                        reservation_Exs_CouncilDetailsForm_w.setReservationStrength(reservation_Exs_CouncilDetailsForm3.getReservationStrength());
                                        reservation_Exs_CouncilDetailsForm_w.setReservationStrength_fill(reservation_Exs_CouncilDetailsForm3.getReservationStrength_fill());
                                        reservation_Exs_CouncilDetailsForm_w.setReservationStrength_vacant(reservation_Exs_CouncilDetailsForm3.getReservationStrength_vacant());
                                        reservation_Exs_CouncilDetailsForm_w.setReservationStrength_w(reservation_Exs_CouncilDetailsForm4.getReservationStrength_w());
                                        reservation_Exs_CouncilDetailsForm_w.setReservationStrength_fill_w(reservation_Exs_CouncilDetailsForm4.getReservationStrength_fill_w());
                                        reservation_Exs_CouncilDetailsForm_w.setReservationStrength_vacant_w(reservation_Exs_CouncilDetailsForm4.getReservationStrength_vacant_w());
                                        reservation_Exs_CouncilDetailsForm_w.setExserviceStrength(reservation_Exs_CouncilDetailsForm5.getExserviceStrength());
                                        reservation_Exs_CouncilDetailsForm_w.setExserviceStrength_fill(reservation_Exs_CouncilDetailsForm5.getExserviceStrength_fill());
                                        reservation_Exs_CouncilDetailsForm_w.setExserviceStrength_vacant(reservation_Exs_CouncilDetailsForm5.getExserviceStrength_vacant());
                                        reservation_Exs_CouncilDetailsForm_w.setExserviceStrength_w(reservation_Exs_CouncilDetailsForm6.getExserviceStrength_w());
                                        reservation_Exs_CouncilDetailsForm_w.setExserviceStrength_fill_w(reservation_Exs_CouncilDetailsForm6.getExserviceStrength_fill_w());
                                        reservation_Exs_CouncilDetailsForm_w.setExserviceStrength_vacant_w(reservation_Exs_CouncilDetailsForm6.getExserviceStrength_vacant_w());
                                        int check = 0;
                                        if (reservation_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (reservation_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() > 0) {
                                            check = 2;
                                        } else if (reservation_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Exs_CouncilDetailsForm_w.getReservationStrength_vacant() > 0) {
                                            check = 3;
                                        } else if (reservation_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Exs_CouncilDetailsForm_w.getReservationStrength_vacant() == 0 && reservation_Exs_CouncilDetailsForm_w.getReservationStrength_vacant_w() > 0) {
                                            check = 4;
                                        } else if (reservation_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Exs_CouncilDetailsForm_w.getReservationStrength_vacant() == 0 && reservation_Exs_CouncilDetailsForm_w.getReservationStrength_vacant_w() == 0 && reservation_Exs_CouncilDetailsForm_w.getExserviceStrength_vacant() > 0) {
                                            check = 7;
                                        } else if (reservation_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Exs_CouncilDetailsForm_w.getReservationStrength_vacant() == 0 && reservation_Exs_CouncilDetailsForm_w.getReservationStrength_vacant_w() == 0 && reservation_Exs_CouncilDetailsForm_w.getExserviceStrength_vacant() == 0 && reservation_Exs_CouncilDetailsForm_w.getExserviceStrength_vacant_w() > 0) {
                                            check = 8;
                                        }
                                        reservation_Exs_CouncilDetailsForm_w.setCheck(check);
                                        reservation_Exs_CouncilDetailsForm_w.setCheckName(checkName);
                                        reservation_Exs_CouncilDetailsForms_w.add(reservation_Exs_CouncilDetailsForm_w);
                                    }
                                    request.setAttribute("reservation_Exs_CouncilDetailsForms_w", reservation_Exs_CouncilDetailsForms_w);
                                    ActionForward i16 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i16;
                                }
                                ActionForward reservation_Exs_CouncilDetailsForms_w = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return reservation_Exs_CouncilDetailsForms_w;
                            }
                            if (!caste_code.equals(ocCastCode) && !itiAdmissionForm.getSex().equalsIgnoreCase("male") && itiAdmissionForm.getPhc().equals("Yes") && itiAdmissionForm.getExservice().equals("Yes")) {
                                System.out.println("The phc is--------128128128-----------");
                                String category_code1 = null;
                                category_code1 = caste_code.length() == 2 ? registrationDao.getCategoryCode(category_code.substring(0, category_code.length() - 2), request) : registrationDao.getCategoryCode(category_code.substring(0, category_code.length() - 1), request);
                                ArrayList reservation_Phc_Exs_CouncilDetailsForms1_w = registrationDao.getGeneralCouncilDistrictDetails(dist_code, ocCode, request);
                                ArrayList reservation_Phc_Exs_CouncilDetailsForms2_w = registrationDao.getGeneral_wCouncilDistrictDetails(dist_code, oc_wCode, request);
                                ArrayList reservation_Phc_Exs_CouncilDetailsForms3_w = registrationDao.getReservationCouncilDistrictDetails(dist_code, category_code1, request);
                                ArrayList reservation_Phc_Exs_CouncilDetailsForms4_w = registrationDao.getReservation_wCouncilDistrictDetails(dist_code, category_code, request);
                                ArrayList reservation_Phc_Exs_CouncilDetailsForms5_w = registrationDao.getPhcCouncilDistrictDetails(dist_code, phCode, request);
                                ArrayList reservation_Phc_Exs_CouncilDetailsForms6_w = registrationDao.getPhc_wCouncilDistrictDetails(dist_code, ph_wCode, request);
                                ArrayList reservation_Phc_Exs_CouncilDetailsForms7_w = registrationDao.getExserviceCouncilDistrictDetails(dist_code, exCode, request);
                                ArrayList reservation_Phc_Exs_CouncilDetailsForms8_w = registrationDao.getExservice_wCouncilDistrictDetails(dist_code, ex_wCode, request);
                                int size = reservation_Phc_Exs_CouncilDetailsForms1_w.size();
                                int size1 = reservation_Phc_Exs_CouncilDetailsForms2_w.size();
                                int size2 = reservation_Phc_Exs_CouncilDetailsForms3_w.size();
                                int size3 = reservation_Phc_Exs_CouncilDetailsForms4_w.size();
                                int size4 = reservation_Phc_Exs_CouncilDetailsForms5_w.size();
                                int size5 = reservation_Phc_Exs_CouncilDetailsForms6_w.size();
                                int size6 = reservation_Phc_Exs_CouncilDetailsForms7_w.size();
                                int size7 = reservation_Phc_Exs_CouncilDetailsForms8_w.size();
                                if (size != 0 && size == size1 && size == size2 && size == size3 && size == size4 && size == size5 && size == size6 && size == size7) {
                                    ArrayList<CouncilDetailsForm> reservation_Phc_Exs_CouncilDetailsForms_w = new ArrayList<CouncilDetailsForm>();
                                    for (int i17 = 0; i17 < size; ++i17) {
                                        CouncilDetailsForm reservation_Phc_Exs_CouncilDetailsForm_w = new CouncilDetailsForm();
                                        CouncilDetailsForm reservation_Phc_Exs_CouncilDetailsForm1 = (CouncilDetailsForm) reservation_Phc_Exs_CouncilDetailsForms1_w.get(i17);
                                        CouncilDetailsForm reservation_Phc_Exs_CouncilDetailsForm2 = (CouncilDetailsForm) reservation_Phc_Exs_CouncilDetailsForms2_w.get(i17);
                                        CouncilDetailsForm reservation_Phc_Exs_CouncilDetailsForm3 = (CouncilDetailsForm) reservation_Phc_Exs_CouncilDetailsForms3_w.get(i17);
                                        CouncilDetailsForm reservation_Phc_Exs_CouncilDetailsForm4 = (CouncilDetailsForm) reservation_Phc_Exs_CouncilDetailsForms4_w.get(i17);
                                        CouncilDetailsForm reservation_Phc_Exs_CouncilDetailsForm5 = (CouncilDetailsForm) reservation_Phc_Exs_CouncilDetailsForms5_w.get(i17);
                                        CouncilDetailsForm reservation_Phc_Exs_CouncilDetailsForm6 = (CouncilDetailsForm) reservation_Phc_Exs_CouncilDetailsForms6_w.get(i17);
                                        CouncilDetailsForm reservation_Phc_Exs_CouncilDetailsForm7 = (CouncilDetailsForm) reservation_Phc_Exs_CouncilDetailsForms7_w.get(i17);
                                        CouncilDetailsForm reservation_Phc_Exs_CouncilDetailsForm8 = (CouncilDetailsForm) reservation_Phc_Exs_CouncilDetailsForms8_w.get(i17);
                                        int checkName = 0;
                                        if (i17 > 0) {
                                            CouncilDetailsForm reservation_Phc_Exs_CouncilDetailsForm_wpre = (CouncilDetailsForm) reservation_Phc_Exs_CouncilDetailsForms1_w.get(i17 - 1);
                                            String iti_code1 = reservation_Phc_Exs_CouncilDetailsForm_wpre.getIti_code();
                                            String iti_code2 = reservation_Phc_Exs_CouncilDetailsForm1.getIti_code();
                                            if (i17 > 0 && iti_code1.equals(iti_code2)) {
                                                checkName = 1;
                                            }
                                        }
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setIti_name(reservation_Phc_Exs_CouncilDetailsForm1.getIti_name());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setIti_code(reservation_Phc_Exs_CouncilDetailsForm1.getIti_code());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setTrade_code(reservation_Phc_Exs_CouncilDetailsForm1.getTrade_code());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setTrade_short(reservation_Phc_Exs_CouncilDetailsForm1.getTrade_short());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setTrade_name(reservation_Phc_Exs_CouncilDetailsForm1.getTrade_name());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setTotalStrength(reservation_Phc_Exs_CouncilDetailsForm1.getTotalStrength());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setGeneralStrength(reservation_Phc_Exs_CouncilDetailsForm1.getGeneralStrength());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setGeneralStrength_fill(reservation_Phc_Exs_CouncilDetailsForm1.getGeneralStrength_fill());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setGeneralStrength_vacant(reservation_Phc_Exs_CouncilDetailsForm1.getGeneralStrength_vacant());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setGeneralStrength_w(reservation_Phc_Exs_CouncilDetailsForm2.getGeneralStrength_w());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setGeneralStrength_fill_w(reservation_Phc_Exs_CouncilDetailsForm2.getGeneralStrength_fill_w());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setGeneralStrength_vacant_w(reservation_Phc_Exs_CouncilDetailsForm2.getGeneralStrength_vacant_w());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setReservationStrength(reservation_Phc_Exs_CouncilDetailsForm3.getReservationStrength());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setReservationStrength_fill(reservation_Phc_Exs_CouncilDetailsForm3.getReservationStrength_fill());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setReservationStrength_vacant(reservation_Phc_Exs_CouncilDetailsForm3.getReservationStrength_vacant());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setReservationStrength_w(reservation_Phc_Exs_CouncilDetailsForm4.getReservationStrength_w());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setReservationStrength_fill_w(reservation_Phc_Exs_CouncilDetailsForm4.getReservationStrength_fill_w());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setReservationStrength_vacant_w(reservation_Phc_Exs_CouncilDetailsForm4.getReservationStrength_vacant_w());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setPhcStrength(reservation_Phc_Exs_CouncilDetailsForm5.getPhcStrength());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setPhcStrength_fill(reservation_Phc_Exs_CouncilDetailsForm5.getPhcStrength_fill());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setPhcStrength_vacant(reservation_Phc_Exs_CouncilDetailsForm5.getPhcStrength_vacant());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setPhcStrength_w(reservation_Phc_Exs_CouncilDetailsForm6.getPhcStrength_w());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setPhcStrength_fill_w(reservation_Phc_Exs_CouncilDetailsForm6.getPhcStrength_fill_w());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setPhcStrength_vacant_w(reservation_Phc_Exs_CouncilDetailsForm6.getPhcStrength_vacant_w());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setExserviceStrength(reservation_Phc_Exs_CouncilDetailsForm7.getExserviceStrength());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setExserviceStrength_fill(reservation_Phc_Exs_CouncilDetailsForm7.getExserviceStrength_fill());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setExserviceStrength_vacant(reservation_Phc_Exs_CouncilDetailsForm7.getExserviceStrength_vacant());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setExserviceStrength_w(reservation_Phc_Exs_CouncilDetailsForm8.getExserviceStrength_w());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setExserviceStrength_fill_w(reservation_Phc_Exs_CouncilDetailsForm8.getExserviceStrength_fill_w());
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setExserviceStrength_vacant_w(reservation_Phc_Exs_CouncilDetailsForm8.getExserviceStrength_vacant_w());
                                        int check = 0;
                                        if (reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() > 0) {
                                            check = 1;
                                        } else if (reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() > 0) {
                                            check = 2;
                                        } else if (reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getReservationStrength_vacant() > 0) {
                                            check = 3;
                                        } else if (reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getReservationStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getReservationStrength_vacant_w() > 0) {
                                            check = 4;
                                        } else if (reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getReservationStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getReservationStrength_vacant_w() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant() > 0) {
                                            check = 5;
                                        } else if (reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getReservationStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getReservationStrength_vacant_w() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant_w() > 0) {
                                            check = 6;
                                        } else if (reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getReservationStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getReservationStrength_vacant_w() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant_w() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getExserviceStrength_vacant() > 0) {
                                            check = 7;
                                        } else if (reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getGeneralStrength_vacant_w() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getReservationStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getReservationStrength_vacant_w() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getPhcStrength_vacant_w() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getExserviceStrength_vacant() == 0 && reservation_Phc_Exs_CouncilDetailsForm_w.getExserviceStrength_vacant_w() > 0) {
                                            check = 8;
                                        }
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setCheck(check);
                                        reservation_Phc_Exs_CouncilDetailsForm_w.setCheckName(checkName);
                                        reservation_Phc_Exs_CouncilDetailsForms_w.add(reservation_Phc_Exs_CouncilDetailsForm_w);
                                    }
                                    request.setAttribute("reservation_Phc_Exs_CouncilDetailsForms_w", reservation_Phc_Exs_CouncilDetailsForms_w);
                                    ActionForward i17 = mapping.findForward("districtAdmissionFormOptimise");
                                    return i17;
                                }
                                ActionForward reservation_Phc_Exs_CouncilDetailsForms_w = mapping.findForward("districtAdmissionsInvalidCategoryData");
                                return reservation_Phc_Exs_CouncilDetailsForms_w;
                            }
                            break block232;
                        }
                        ActionForward gpt = mapping.findForward("itiAdmissionApplicationDetailsNotFound");
                        return gpt;
                    }
                    request.setAttribute("checkedAdmissionDetails", (Object) checkedAdmissionDetails);
                    ActionForward itiAdmissionForm = mapping.findForward("districtAdmissionAlreadyFound");
                    return itiAdmissionForm;
                }
                ActionForward checkedAdmissionDetails = mapping.findForward("districtAdmissionProcessFailed");
                return checkedAdmissionDetails;
            } catch (Exception httpSession) {
                System.out.println("Exception isss  " + httpSession);
            } finally {
                if (con != null) {
                    con.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }
        }
        return null;
    }
}
