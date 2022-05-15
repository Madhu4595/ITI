package callleter;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author RAJU
 */
public class call_letterForm extends org.apache.struts.action.ActionForm {

    String regid;

    public String getRegid() {
        return beans.MyUtil.filterBad(regid);
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }
    String name, fname;

    public String getName() {
        return beans.MyUtil.filterBad(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return beans.MyUtil.filterBad(fname);
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
    String dist_code;

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public call_letterForm() {
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
        {
            if (getRegid() == null || getRegid().length() < 1) {
                errors.add("regid", new ActionMessage("error.regid.required"));
            }

            if (!Validations.Validate(getRegid(), 1)) {
                errors.add("regid", new ActionMessage("error.regid.notmatch"));
            }

            if (getRegid().length() > 10) {
                errors.add("regid", new ActionMessage("error.regid.lenerror"));
            }

        }
        return errors;
    }
}
