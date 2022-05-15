/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import java.util.ArrayList;
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
public class CodingAndDecodingEntryAction extends Action {

    private DistrictForm districtForm;

    public CodingAndDecodingEntryAction() {
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //DistrictForm districtForm=new DistrictForm();
            ArrayList<com.nic.DistrictForm> districtForms;
            DistrictDao districtDao = new DistrictDao();
            districtForms = districtDao.getRddDistrictDetails((String) request.getSession().getAttribute("ins_code"),request);
            request.setAttribute("districtForms", districtForms);
            return mapping.findForward("CodingAndDecodingEntrySuccess");

        } catch (Exception e) {
        }
        return null;
    }
}
