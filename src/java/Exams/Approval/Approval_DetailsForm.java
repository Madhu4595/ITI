/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exams.Approval;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Gopi Daggumalle
 */
public class Approval_DetailsForm extends org.apache.struts.action.ActionForm {

    private String adm_num, appornot, remarks;

    public String getAdm_num() {
        return beans.MyUtil.filterBad(adm_num);
    }

    public void setAdm_num(String adm_num) {
        this.adm_num = adm_num;
    }

    public String getAppornot() {
        return beans.MyUtil.filterBad(appornot);
    }

    public void setAppornot(String appornot) {
        this.appornot = appornot;
    }

    public String getRemarks() {
        return beans.MyUtil.filterBad(remarks);
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Approval_DetailsForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (getAppornot() == null || getAppornot().length() < 1) {
            errors.add("appornot", new ActionMessage("error.appornot.required"));
        }
        if (getAppornot().equalsIgnoreCase("N")) {
            if (getRemarks() == null || getRemarks().length() < 1) {
                errors.add("remarks", new ActionMessage("error.getRemarks.required"));
            }
        }

        return errors;
    }
}
