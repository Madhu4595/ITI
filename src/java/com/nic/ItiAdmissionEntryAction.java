/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author SUBBAREDDY
 */
public class ItiAdmissionEntryAction extends Action {

    private static RegistrationDao registrationDao;
    private ItiAdmissionProcessForm itiAdmissionProcessForm;

    static {
        registrationDao = new RegistrationDao();
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        
        
        ItiAdmissionProcessForm itiAdmissionProcessForm = (ItiAdmissionProcessForm)form;
         
        HttpSession httpSession = request.getSession();
        
        httpSession.removeAttribute("adm_regid");
        httpSession.removeAttribute("adm_merit_number");
        httpSession.removeAttribute("adm_iti_name");
        httpSession.removeAttribute("adm_gpa");
        
        String stored_token = beans.MyUtil.filterBad(""+httpSession.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }

        String subqry = "";
        String caste_perform = beans.MyUtil.filterBad(itiAdmissionProcessForm.getCaste_perform());
        String qual_perform = beans.MyUtil.filterBad(itiAdmissionProcessForm.getQual_perform());
        String cal_time_perform = beans.MyUtil.filterBad(itiAdmissionProcessForm.getAdm_perform());
        
        itiAdmissionProcessForm.setCaste_perform(caste_perform);
        itiAdmissionProcessForm.setQual_perform(qual_perform);
        itiAdmissionProcessForm.setAdm_perform(cal_time_perform);
        
        System.out.println("form StartAdmission_ConvvinerLevel.jsp--->caste_perform-->" + caste_perform + "--qual_perform-->" + qual_perform + "--cal_time_perform-->" + cal_time_perform);
        subqry = " caste='" + caste_perform + "' and minqul='" + qual_perform + "' and cal_time='" + cal_time_perform + "'";
        subqry = subqry + "and dist_code";
        
        ArrayList<ItiForm> itiForms = null;
        int rank_checkForGeneration = registrationDao.checkForRankGeneration(request);
        System.out.println("rank_checkForGeneration-->" + rank_checkForGeneration);
        if (rank_checkForGeneration != 0) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            java.sql.Date date2 = java.sql.Date.valueOf(dateFormat.format(date));
            itiAdmissionProcessForm.setCurrentDate(date2);
             
            String role=beans.MyUtil.filterBad(""+httpSession.getAttribute("role_id"));
            int role_id=Integer.parseInt(role);
            System.out.println("role_id-->" + role_id);
            String ins_code = (String) beans.MyUtil.filterBad(httpSession.getAttribute("ins_code")+"");
            System.out.println("ins_code-->" + ins_code);
            ArrayList<Integer> councilMerits = null;
            if (role_id == 4) {
                itiForms = registrationDao.getItiDetails1(ins_code, request);
                System.out.println("itiForms.size()-->"+itiForms.size());
                if (itiForms.size() == 0) {
                     
                    councilMerits = registrationDao.getCouncilMerits("iti_code", date2, ins_code, request);
                    System.out.println("councilMerits-->"+councilMerits);
                    if (councilMerits == null) {
                       System.out.println("cursor went to itiAdmissionEntryFailed page");
                        return mapping.findForward("itiAdmissionEntryFailed");
                    } else {
                        itiAdmissionProcessForm.setMerit_from(councilMerits.get(0));
                        itiAdmissionProcessForm.setMerit_to(councilMerits.get(1));
                        System.out.println("cursor went to itiAdmissionEntrySuccess page");
                        return mapping.findForward("itiAdmissionEntrySuccess");
                    }
                } else {
                    request.setAttribute("itiForms", itiForms);
                   System.out.println("cursor went to SeatMatrixNotGenerated  page");
                    return mapping.findForward("SeatMatrixNotGenerated");
                }

            } else if (role_id == 3) {
                itiForms = registrationDao.getItiDetails1(ins_code, request);
                System.out.println("itiForms->" + itiForms.size());
                if (itiForms.size() == 0) {
                     
                    councilMerits = registrationDao.getCouncilMerits(subqry, date2, ins_code, request);
                    System.out.println("councilMerits->" + councilMerits);
                    if (councilMerits == null) {
                         
                        return mapping.findForward("DistrictAdmissionEntryFailed");
                    } else {
                        System.out.println("councilMerits in else->" + councilMerits);
                        itiAdmissionProcessForm.setMerit_from(councilMerits.get(0));
                        itiAdmissionProcessForm.setMerit_to(councilMerits.get(1));
                         
                        return mapping.findForward("DistrictAdmissionEntrySuccess");
                    }

                } else {
                    System.out.println("in else condition->");
                    request.setAttribute("itiForms", itiForms);
                     
                    return mapping.findForward("SeatMatrixNotGenerated");
                }

            }
        } else {
          System.out.println("cursor went to RankGenerationNotCompleated page");
            return mapping.findForward("RankGenerationNotCompleated");
        }
        return null;
    }
    public ItiAdmissionEntryAction() {
    }
}
