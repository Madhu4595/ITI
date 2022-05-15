/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm_withoutphoto;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Ramesh Naidu
 */
public class Application_Open_Iti_Form extends org.apache.struts.action.ActionForm {

    String dist_code, iti_code, operation;

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getOperation() {
        return beans.MyUtil.filterBad(operation);
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     *
     */
    public Application_Open_Iti_Form() {
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
        if (getDist_code() == null || getDist_code().length() < 1) {
            errors.add("dist", new ActionMessage("error.dist.required"));
        } else if (!Validations.Validate(getDist_code(), 2)) {
            errors.add("name", new ActionMessage("error.name.notmatch"));
        }

        if (getIti_code() == null || getIti_code().length() < 1) {
            errors.add("iti", new ActionMessage("error.iti.required"));
        } else if (!Validations.Validate(getIti_code(), 2)) {
            errors.add("name", new ActionMessage("error.name.notmatch"));
        }

        if (getOperation() == null || getOperation().length() < 1) {
            errors.add("operation", new ActionMessage("error.operation.required"));
        } else if (!Validations.Validate(getOperation(), 6)) {
            errors.add("name", new ActionMessage("error.name.notmatch"));
        }
        // TODO: add 'error.name.required' key to your resources

        return errors;
    }
}
