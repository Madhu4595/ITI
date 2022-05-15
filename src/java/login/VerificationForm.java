/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Ramesh Naidu
 */
public class VerificationForm extends org.apache.struts.action.ActionForm {

    String itiCodeEdit;
    String itiNameEdit;
    String officerCodeEdit;
    String verifyOfficerName;
    String v_iti_name;
    String itiCode;
    String itiName;
    String v_iti_code;
    String v_name;
    String v_designation;

    public String getV_iti_name() {
        return beans.MyUtil.filterBad(v_iti_name);
    }

    public void setV_iti_name(String v_iti_name) {
        this.v_iti_name = v_iti_name;
    }

    public String getV_iti_code() {
        return beans.MyUtil.filterBad(v_iti_code);
    }

    public void setV_iti_code(String v_iti_code) {
        this.v_iti_code = v_iti_code;
    }

    public String getV_name() {
        return beans.MyUtil.filterBad(v_name);
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public String getV_designation() {
        return beans.MyUtil.filterBad(v_designation);
    }

    public void setV_designation(String v_designation) {
        this.v_designation = v_designation;
    }

    public String getItiCodeEdit() {
        return beans.MyUtil.filterBad(itiCodeEdit);
    }

    public void setItiCodeEdit(String itiCodeEdit) {
        this.itiCodeEdit = itiCodeEdit;
    }

    public String getItiNameEdit() {
        return beans.MyUtil.filterBad(itiNameEdit);
    }

    public void setItiNameEdit(String itiNameEdit) {
        this.itiNameEdit = itiNameEdit;
    }

    public String getOfficerCodeEdit() {
        return beans.MyUtil.filterBad(officerCodeEdit);
    }

    public void setOfficerCodeEdit(String officerCodeEdit) {
        this.officerCodeEdit = officerCodeEdit;
    }

    public String getVerifyOfficerName() {
        return beans.MyUtil.filterBad(verifyOfficerName);
    }

    public void setVerifyOfficerName(String verifyOfficerName) {
        this.verifyOfficerName = verifyOfficerName;
    }

    public String getItiCode() {
        return beans.MyUtil.filterBad(itiCode);
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public String getItiName() {
        return beans.MyUtil.filterBad(itiName);
    }

    public void setItiName(String itiName) {
        this.itiName = itiName;
    }

    public VerificationForm() {
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
        /*  if (getName() == null || getName().length() < 1) {
         errors.add("name", new ActionMessage("error.name.required"));
         // TODO: add 'error.name.required' key to your resources
         }*/
        return errors;
    }
}
