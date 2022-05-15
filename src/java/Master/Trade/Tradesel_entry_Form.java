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
 * @author Kranthi Bharat
 */
public class Tradesel_entry_Form extends org.apache.struts.action.ActionForm {

    private String regid, opr, iti_code;

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getOpr() {
        return beans.MyUtil.filterBad(opr);
    }

    public void setOpr(String opr) {
        this.opr = opr;
    }

    public String getRegid() {
        return beans.MyUtil.filterBad(regid);
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }
    private String dist_code;

    public Tradesel_entry_Form() {
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

        if (getRegid() == null || getRegid().length() < 1) {
            errors.add("regid", new ActionMessage("error.regid.required"));
        } else {

            if (!Validations.Validate(getRegid(), 4)) {
                errors.add("regid", new ActionMessage("error.regid.notmatch"));
            }

            if (getRegid().length() > 10) {
                errors.add("regid", new ActionMessage("error.regid.maxlen"));
            }
        }

        if (getDist_code() == null || getDist_code().length() < 1) {
             errors.add("dist_code", new ActionMessage("error.dist_code.required"));
        }
        return errors;
    }
}
