/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author SUBBAREDDY
 */
public class DistrictAdmissionProcessAction extends org.apache.struts.action.Action {

    private static RegistrationDao registrationDao;
    private ItiAdmissionProcessForm itiAdmissionProcessForm;
    private ItiDetailsForm itiDetailsForm;

    static {
        registrationDao = new RegistrationDao();
    }

    public DistrictAdmissionProcessAction() {
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            itiAdmissionProcessForm = (ItiAdmissionProcessForm) form;
            //itiAdmissionProcessForm = (ItiAdmissionProcessForm) request.getAttribute("itiAdmissionProcessForm ");
            HttpSession httpSession = request.getSession();
            String dist_code = (String) httpSession.getAttribute("dist_code");
            int regid = registrationDao.getRegistrationDetails("dist_code", dist_code, itiAdmissionProcessForm.getRank(),request);
            if (regid != 0) {
                CheckedAdmissionDetails checkedAdmissionDetails = registrationDao.checkAdmited_or_Not(regid,request);
                if (checkedAdmissionDetails == null) {
                    ItiAdmissionForm itiAdmissionForm = registrationDao.getApplicationDetails(regid,request);
                    itiAdmissionForm.setMeritNumber(itiAdmissionProcessForm.getRank());
                    itiAdmissionForm.setAcad_year(itiAdmissionProcessForm.getCurrentDate().toString().substring(0, 4));
                    itiAdmissionForm.setCurrentDate(itiAdmissionProcessForm.getCurrentDate());
                    String gpt = registrationDao.getGpt(regid,request);
                    itiAdmissionForm.setGpt(gpt);

                    httpSession.setAttribute("itiAdmissionForm", itiAdmissionForm);

                    ArrayList<All_Itis_DistricForm> all_Itis_DistricForms = registrationDao.getAll_Itis_District(dist_code,request);

                    request.setAttribute("all_Itis_DistricForms", all_Itis_DistricForms);
                    return mapping.findForward("districtAdmissionForm");
                } else {
                    request.setAttribute("checkedAdmissionDetails", checkedAdmissionDetails);
                    return mapping.findForward("districtAdmissionAlreadyFound");
                }
            } else {
                return mapping.findForward("districtAdmissionProcessFailed");
            }
        } catch (Exception e) {
        }
        return null;
    }
}
