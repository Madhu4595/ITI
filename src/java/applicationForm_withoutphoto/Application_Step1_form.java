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
 * @author Admin
 */
public class Application_Step1_form extends org.apache.struts.action.ActionForm {

    private String name, dob, fname, math, ssc_type;

    public String getMath() {
        return beans.MyUtil.filterBad(math);
    }

    public void setMath(String math) {
        this.math = math;
    }

    public String getSsc_type() {
        return beans.MyUtil.filterBad(ssc_type);
    }

    public void setSsc_type(String ssc_type) {
        this.ssc_type = ssc_type;
    }

    public String getName() {
        return name;
    }

    public String getFname() {
        return beans.MyUtil.filterBad(fname);
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return beans.MyUtil.filterBad(dob);
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Application_Step1_form() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if (getName() == null || getName().length() < 1) {
            errors.add("name", new ActionMessage("error.name.required"));
        } else if (!Validations.Validate(getName(), 3)) {
            errors.add("name", new ActionMessage("error.name.notmatch"));
        }

        if (getFname() == null || getFname().length() < 1) {
            errors.add("fname", new ActionMessage("error.fname.required"));
        } else if (!Validations.Validate(getFname(), 3)) {
            errors.add("fname", new ActionMessage("error.fname.notmatch"));
        }

        if (getName().length() > 50) {
            errors.add("name", new ActionMessage("error.name.lenerror"));
        }
        if (getDob() == null || getDob().length() < 1) {
            errors.add("dob", new ActionMessage("error.dob.required"));
        } else if (!Validations.Validate(getDob(), 8)) {
            errors.add("pincode", new ActionMessage("error.dob.notmatch"));
        }

        if (!Validations.Validate(getSsc_type(), 6)) {
            errors.add("ssc_type", new ActionMessage("error.ssc_type.notmatch"));
        }

        if (getSsc_type().equals("open")) {
            try {
                if (!Validations.Validate(getMath(), 6)) {
                    errors.add("math", new ActionMessage("error.math.notmatch"));
                }

                if (getMath().equals("no")) {
                    //Not eligible! 
                    errors.add("math", new ActionMessage("error.math.eligible"));
                }
            } catch (NullPointerException ex) {
                errors.add("math", new ActionMessage("error.math.required"));
            }
        }
        return errors;
    }
}
