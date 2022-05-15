/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package callleter;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Gopi Daggumalle
 */
public class CallOneForm extends org.apache.struts.action.ActionForm {

    private String from, qual;

    public String getQual() {
        return beans.MyUtil.filterBad(qual);
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public String getFrom() {
        return beans.MyUtil.filterBad(from);
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return beans.MyUtil.filterBad(to);
    }

    public void setTo(String to) {
        this.to = to;
    }
    private String to;

    public CallOneForm() {
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
        if (!DBC.Validations.Validate(from, 2)) {
            errors.add("cal_time", new ActionMessage("error.from.notmatch"));
        }
        if (!DBC.Validations.Validate(to, 2)) {
            errors.add("cal_time", new ActionMessage("error.to.notmatch"));
        }
        if (!DBC.Validations.Validate(qual, 6)) {
            errors.add("cal_time", new ActionMessage("error.qual.notmatch"));
        }
        return errors;
    }
}
