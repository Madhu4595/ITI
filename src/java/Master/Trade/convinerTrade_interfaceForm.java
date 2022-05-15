/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Master.Trade;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest; 
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Gopi Daggumalle
 */
public class convinerTrade_interfaceForm extends org.apache.struts.action.ActionForm {

    private String iti_code;

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public convinerTrade_interfaceForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (getIti_code() == null || getIti_code().length() < 1) {
            errors.add("iti_code", new ActionMessage("error.iti_code.required"));
        } else if (!Validations.Validate(getIti_code(), 2)) {
            errors.add("iti_code", new ActionMessage("error.iti_code.notmatch"));
        }
        try {
            if (iti_code.length() > 4) {
                errors.add("iti_code", new ActionMessage("error.iti_code.lenerror"));
            }
        } catch (Exception e) {
        }

        return errors;
    }
}
