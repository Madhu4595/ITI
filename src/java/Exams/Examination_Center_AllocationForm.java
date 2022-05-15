/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exams;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Ramesh Naidu
 */
public class Examination_Center_AllocationForm extends org.apache.struts.action.ActionForm {

    private String center_iti_code[], iti_code[], type_admission, sem_nonsem, type_iti;

    public String getType_iti() {
        return beans.MyUtil.filterBad(type_iti);
    }

    public void setType_iti(String type_iti) {
        this.type_iti = type_iti;
    }

    public String getSem_nonsem() {
        return beans.MyUtil.filterBad(sem_nonsem);
    }

    public void setSem_nonsem(String sem_nonsem) {
        this.sem_nonsem = sem_nonsem;
    }

    public String getType_admission() {
        return beans.MyUtil.filterBad(type_admission);
    }

    public void setType_admission(String type_admission) {
        this.type_admission = type_admission;
    }

    public String[] getCenter_iti_code() {
        return center_iti_code;
    }

    public void setCenter_iti_code(String[] center_iti_code) {
        this.center_iti_code = center_iti_code;
    }

    public String[] getIti_code() {
        return iti_code;
    }

    public void setIti_code(String[] iti_code) {
        this.iti_code = iti_code;
    }

    public Examination_Center_AllocationForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        /*if (getName() == null || getName().length() < 1) {
         errors.add("name", new ActionMessage("error.name.required"));
         // TODO: add 'error.name.required' key to your resources
         }*/
        return errors;
    }
}
