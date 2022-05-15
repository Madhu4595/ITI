/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admission.Process;

import Master.Trade.JavaBeanFormSerializable;
import com.nic.CouncilDetailsForm;
import com.nic.ItiAdmissionForm;
import com.nic.ItiDetailsForm;
import com.nic.RegistrationDao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author GOPI
 */
public class DistrictAdmissionProcess_interfaceAction extends org.apache.struts.action.Action {

    private static RegistrationDao registrationDao;
    private com.nic.ItiAdmissionProcessForm itiAdmissionProcessForm;
    private ItiDetailsForm itiDetailsForm;

    static {
        registrationDao = new RegistrationDao();
    }

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //connection details*****************

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connection = DBC.DBConnection.getConnection(request);
        //Connection details End

        //variables global using********************
        System.out.println("vgfthfgfgf");
        int regid = 0;
        int i = 0;
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

        String inter_marks, idmarks1, idmarks2, ssc_regno, ssc_board, ssc_year, ssc_month;
        String fatherName, name;
        String mname, gpt = null;
        String address;
        String phoneNumber;
        String adarNo;
        boolean ssc_passed;
        boolean interPassed;
        String local;
        int pin_code;
        String cast = null;
        String sex = null;
        String meritNumber;
        boolean exservice;
        String res_category, dob;

        //variable declaration End
        //Previous Details********************* (Details from session and also from before form)
        DistrictAdmissionProcess_interfaceForm fm = (DistrictAdmissionProcess_interfaceForm) form;
        HttpSession session = request.getSession();
        String dist_code = (String) session.getAttribute("ins_code");
        String rank = fm.getRank();
        Date currentDate = fm.getCurrentDate();

        String year_of_admission = DBC.RoleBasedOperations.Current_AcademicYear(request);
        //Previous Details End

        //get Registration Number using dist code, rank**************
        String query = "select regid from ranks where  dist_code='" + dist_code + "' and rank='" + rank + "'";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            regid = resultSet.getInt("regid");
        }
        //Fetching Registration Number Completed

        try {
            //validating Registration details**********************
            if (regid == 0) {
                request.setAttribute("processfailed", "No Registration Id  found with You provided Rank " + rank);
                return mapping.findForward("districtAdmissionProcessFailed");
            }
            //Success validating registration number

            //checking already admitted or not.*****************
            preparedStatement = connection.prepareStatement("select c.iti_name,a.trade_code,a.iti_code,a.regid,b.trade_name,a.adm_num from admissions.iti_admissions a,ititrade_master b,iti c  where a.regid=? and a.trade_code=b.trade_code and a.iti_code=c.iti_code ");
            preparedStatement.setInt(1, regid);
            resultSet = preparedStatement.executeQuery();
            ArrayList UserList = new ArrayList();

            if (resultSet.next()) {
                JavaBeanFormSerializable fm1 = new JavaBeanFormSerializable();
               

                fm1.setRegno("" + resultSet.getInt("regid"));
                fm1.setRank(rank);
                fm1.setIti_code(resultSet.getString("iti_code"));
                fm1.setIti_name(resultSet.getString("iti_name"));
                fm1.setAdm_num(resultSet.getString("adm_num"));
                fm1.setTrade_name(resultSet.getString("trade_name"));
                fm1.setTrade_code(resultSet.getString("trade_code"));
                UserList.add(fm1);
                i++;
            }
            request.setAttribute("users", UserList);
            if (i > 0) {
                request.setAttribute("alreadyadmitted", "Admission is already completed for this Rank with the following details");
                return mapping.findForward("alreadyadmitted");
            }
            //end checking already admitted or not END

            //Gather Application Details using regid and district code **************************
            i = 0;
            preparedStatement = connection.prepareStatement("select name,fname,mname,addr,phno,adarno,gender,caste,ssc_passed,inter_passed,inter_marks,local,pincode,dob,regid,phc,exservice from application where regid=" + regid);
            resultSet = preparedStatement.executeQuery();
            ItiAdmissionForm itiAdmissionForm = new ItiAdmissionForm();
            if (resultSet.next()) {
                i++;

                name = resultSet.getString("name");
                fatherName = resultSet.getString("fname");
                mname = resultSet.getString("mname");
                phoneNumber = resultSet.getString("phno");
                address = resultSet.getString("addr");
                adarNo = resultSet.getString("adarno");
                cast = resultSet.getString("caste");
                sex = resultSet.getString("gender");
                ssc_passed = resultSet.getBoolean("ssc_passed");
                interPassed = resultSet.getBoolean("inter_passed");
                inter_marks = resultSet.getString("inter_marks");
                local = resultSet.getString("local");
                pin_code = resultSet.getInt("pincode");
                dob = resultSet.getString("dob");
                itiAdmissionForm.setName(name);
                itiAdmissionForm.setFatherName(fatherName);
                itiAdmissionForm.setMname(mname);
                itiAdmissionForm.setPhoneNumber(phoneNumber);
                itiAdmissionForm.setAddress(address);
                itiAdmissionForm.setAdarNo(adarNo);
                itiAdmissionForm.setCast(cast);
                itiAdmissionForm.setSex(sex);
                itiAdmissionForm.setSsc_passed(ssc_passed);
                itiAdmissionForm.setInterPassed(interPassed);
                itiAdmissionForm.setInter_marks(Integer.parseInt(inter_marks));
                itiAdmissionForm.setLocal(local);
                itiAdmissionForm.setPin_code(pin_code);
                itiAdmissionForm.setDob(dob);
                regid = resultSet.getInt("regid");
                itiAdmissionForm.setRegid(regid);
                boolean phc = resultSet.getBoolean("phc");
                if (phc == true) {
                    itiAdmissionForm.setPhc("Yes");
                } else {
                    itiAdmissionForm.setPhc("No");
                }
                exservice = resultSet.getBoolean("exservice");
                if (exservice == true) {
                    itiAdmissionForm.setExservice("Yes");
                } else {
                    itiAdmissionForm.setExservice("No");
                }

            }

            if (i == 0) {
                request.setAttribute("processfailed", "No application Details found with given Rank");
                return mapping.findForward("itiAdmissionApplicationDetailsNotFound");
            }

            //Gathered Application details END
            //****************gpa getting
            preparedStatement = connection.prepareStatement("select ssc_tot_gpa from cand_marks where regid=?");
            preparedStatement.setInt(1, regid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                gpt = resultSet.getString("ssc_tot_gpa");
            }
            //End gpa gathering

            //******************* Councilling Process Started *******************************
            itiAdmissionForm.setAcad_year(currentDate.toString().substring(0, 4));
            itiAdmissionForm.setCurrentDate(currentDate);
            itiAdmissionForm.setGpt(gpt);

            request.setAttribute("itiAdmissionForm", itiAdmissionForm);

            ocCastCode = "OC";
            caste_code = cast;

            if (sex.equalsIgnoreCase("female")) {
                if (caste_code.contains("-")) {
                    category_code = caste_code + "W";
                } else {
                    category_code = caste_code + "-W";
                }
            } else {
                category_code = caste_code;
            }
            
            query = "select * from admissions.admission_process_matrix where act_cat_code='" + cast + "' and gender='" + sex + "'";
            
            resultSet = null;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            i = 0;
            ArrayList<CouncilDetailsForm> generalCouncilDetailsForms1 = new ArrayList<CouncilDetailsForm>();
            //CouncilDetailsForm form2=new CouncilDetailsForm();
            while (resultSet.next()) {

                preparedStatement = connection.prepareStatement("select d.iti_name,b.iti_code,a.trade_code,a.strength,a.strength_fill,a.strength_vacant,b.trade_short,b.strength,c.trade_name from iti_seatmatrix a,ititrade b,ititrade_master c,iti d where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%" + "' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.category_code='" + cast + "' and b.trade_short=c.trade_short and b.iti_code=d.iti_code order by b.iti_code,b.trade_code");
                // preparedStatement.setString(1, dist_code);
                resultSet = preparedStatement.executeQuery();
                int hopi = 0;
                //JavaBeanFormSerializable form2;
                while (resultSet.next()) {
                    //form2 = new JavaBeanFormSerializable();
                    CouncilDetailsForm form2 = new CouncilDetailsForm();
                    form2.setIti_name(resultSet.getString("iti_name"));
                    form2.setIti_code(resultSet.getString("iti_code"));
                    form2.setTrade_code(String.valueOf(resultSet.getInt("trade_code")));
                    form2.setGeneralStrength(resultSet.getInt(4));
                    form2.setGeneralStrength_fill(resultSet.getInt("strength_fill"));
                    form2.setGeneralStrength_vacant(resultSet.getInt("strength_vacant"));
                    form2.setTrade_short(resultSet.getString("trade_short"));
                    form2.setTotalStrength(resultSet.getInt(8));
                    form2.setTrade_name(resultSet.getString("trade_name"));
                    generalCouncilDetailsForms1.add(form2);
                }

                int size = generalCouncilDetailsForms1.size();
                if (size != 0) {
                    ArrayList<CouncilDetailsForm> generalCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
                    CouncilDetailsForm councilDetailsForm;
                    CouncilDetailsForm councilDetailsFormPre;
                    for (i = 0; i < size; i++) {
                        councilDetailsForm = new CouncilDetailsForm();
                        councilDetailsForm = generalCouncilDetailsForms1.get(i);
                        int checkName = 0;
                        if (i > 0) {
                            councilDetailsFormPre = generalCouncilDetailsForms1.get(i - 1);
                            String iti_code1 = councilDetailsFormPre.getIti_code();
                            String iti_code2 = councilDetailsForm.getIti_code();
                            if (i > 0 && iti_code1.equals(iti_code2)) {
                                checkName = 1;
                            }
                        }
                        int check = 0;
                        if (councilDetailsForm.getGeneralStrength_vacant() > 0) {
                            check = 1;
                        }
                        councilDetailsForm.setCheck(check);
                        councilDetailsForm.setCheckName(checkName);
                        //councilDetailsForm.setTrade_code(councilDetailsForm.getTrade_code()+"$"+councilDetailsForm.getCheck());
                        generalCouncilDetailsForms.add(councilDetailsForm);
                    }
                    request.setAttribute("generalCouncilDetailsForms", generalCouncilDetailsForms);
                    //return mapping.findForward("districtAdmissionFormOptimise");
                }

            }

            //end getting caste order (sub) END
            //Councilling Process END
        } catch (Exception ex) {
        }

        //Testing*****
        //Testing End
        return mapping.findForward("admission_councilling");
    }
}
