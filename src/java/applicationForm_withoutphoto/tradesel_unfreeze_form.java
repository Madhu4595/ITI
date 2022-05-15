/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm_withoutphoto;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Admin
 */
public class tradesel_unfreeze_form extends org.apache.struts.action.ActionForm {

    private String dist_code, iti_code, type_freeze;

    public String getType_freeze() {
        return beans.MyUtil.filterBad(type_freeze);
    }

    public void setType_freeze(String type_freeze) {
        this.type_freeze = type_freeze;
    }

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        return errors;
    }
}
