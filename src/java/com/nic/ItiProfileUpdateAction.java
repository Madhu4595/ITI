/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import com.nic.RegistrationForm;
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
public class ItiProfileUpdateAction extends Action {

    private DistrictForm districtForm;
    // private ItiDetailsForm detailsForm;
    private static DistrictDao districtDao;
    private static RegistrationDao registrationDao;

    static {
        //districtDao=new DistrictDao();
        registrationDao = new RegistrationDao();
    }

    public ItiProfileUpdateAction() {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //districtForm=districtDao.getDistrictDetails();
        //RegistrationForm registrationForm = (RegistrationForm) form;  
        ItiDetailsForm itiDetailsForm = (ItiDetailsForm) form;
        HttpSession httpSession = request.getSession();

        //Converting district code to district name
        String tab = "conviner_login";
        String code = "dist_code";
        String name = "dist_name";
        String val = itiDetailsForm.getDist_code();

        String distname = beans.MyUtil.getOptionValue(tab, code, name, val,request);
        request.setAttribute("distname", distname);

        //converting Iti Type 
        String ititype;
        if (itiDetailsForm.getGovt().equals("G")) {
            ititype = "Government";
        } else {
            ititype = "Private";
        }
        request.setAttribute("ititype", ititype);

        //Storing update details in iti_arch table
        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "iti_arch", "Insert", request);
        int j = registrationDao.itiArch(itiDetailsForm, trno,request);

        int trno1 = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "iti", "Update", request);
        //iti updation
        int i = registrationDao.update(itiDetailsForm, trno1,request);

        if (i != 0 && j != 0) {
            return mapping.findForward("itiProfileUpdateSuccess");
        } else {
            return mapping.findForward("itiProfileUpdateFailed");
        }
    }
}
