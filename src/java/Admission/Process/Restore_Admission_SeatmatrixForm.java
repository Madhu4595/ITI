/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admission.Process;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Ramesh Naidu
 */
public class Restore_Admission_SeatmatrixForm extends org.apache.struts.action.ActionForm {

    private String category_code;

    public String getCategory_code() {
        return beans.MyUtil.filterBad(category_code);
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public Restore_Admission_SeatmatrixForm() {
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
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        if (getCategory_code() == null || getCategory_code().length() < 1) {
            errors.add("cat_code", new ActionMessage("error.cat_code.required"));
        }
        return errors;
    }
}
