/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Gopi Daggumalle
 */
public class ManageSeats_InterfaceForm extends org.apache.struts.action.ActionForm {

    private String cat_code, cat_name, strength, strength_fill, strength_vacant;

    public String getStrength_fill() {
        return beans.MyUtil.filterBad(strength_fill);
    }

    public void setStrength_fill(String strength_fill) {
        this.strength_fill = strength_fill;
    }

    public String getStrength_vacant() {
        return beans.MyUtil.filterBad(strength_vacant);
    }

    public void setStrength_vacant(String strength_vacant) {
        this.strength_vacant = strength_vacant;
    }

    public String getCat_code() {
        return beans.MyUtil.filterBad(cat_code);
    }

    public void setCat_code(String cat_code) {
        this.cat_code = cat_code;
    }

    public String getCat_name() {
        return beans.MyUtil.filterBad(cat_name);
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getStrength() {
        return beans.MyUtil.filterBad(strength);
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public ManageSeats_InterfaceForm() {
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
        if ("".equals(getCat_code()) || getCat_code().length() < 1) {
            errors.add("cat_code", new ActionMessage("error.cat_code.required"));
        } else if (!Validations.Validate(getCat_code(), 14)) {
            errors.add("cat_code", new ActionMessage("error.cat_code.notmatch"));
        }

        if (getCat_code().length() > 6) {
            errors.add("cat_code", new ActionMessage("error.cat_code.lenerror"));
        }

        if ("".equals(getStrength()) || getStrength().length() < 1) {
            errors.add("strength", new ActionMessage("error.strength.required"));
        } else if (!Validations.Validate(getStrength(), 2)) {
            errors.add("strength", new ActionMessage("error.strength.notmatch"));
        }

        if (getStrength().length() > 6) {
            errors.add("strength", new ActionMessage("error.strength.lenerror"));
        }

        if ("".equals(getCat_name()) || getCat_name().length() < 1) {
            errors.add("cat_name", new ActionMessage("error.cat_code.required"));
        } else if (!Validations.Validate(getCat_name(), 14)) {
            errors.add("cat_name", new ActionMessage("error.cat_code.notmatch"));
        }

        if (getCat_name().length() > 6) {
            errors.add("cat_name"
                    + "", new ActionMessage("error.cat_code.lenerror"));
        }
        return errors;
    }
}
