/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserRegistration;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Kranthi Bharat
 */
public class Register_New_User_UpdatedSaveForm extends org.apache.struts.action.ActionForm {

    private String sno;
    private String user_name;
    private String fromtime;
    private String totime;
    private String ipaddress;
    private String desigcode;
    private String regioncode;
    private String distcode;
    private String iticolg;

    public String getDistcode() {
        return beans.MyUtil.filterBad(distcode);
    }

    public void setDistcode(String distcode) {
        this.distcode = distcode;
    }

    public String getIticolg() {
        return beans.MyUtil.filterBad(iticolg);
    }

    public void setIticolg(String iticolg) {
        this.iticolg = iticolg;
    }
    private String mobileno;
    private String emailid;
    private String role_id;

    public String getSno() {
        return beans.MyUtil.filterBad(sno);
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getUser_name() {
        return beans.MyUtil.filterBad(user_name);
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFromtime() {
        return beans.MyUtil.filterBad(fromtime);
    }

    public void setFromtime(String fromtime) {
        this.fromtime = fromtime;
    }

    public String getTotime() {
        return beans.MyUtil.filterBad(totime);
    }

    public void setTotime(String totime) {
        this.totime = totime;
    }

    public String getIpaddress() {
        return beans.MyUtil.filterBad(ipaddress);
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getDesigcode() {
        return beans.MyUtil.filterBad(desigcode);
    }

    public void setDesigcode(String desigcode) {
        this.desigcode = desigcode;
    }

    public String getRegioncode() {
        return beans.MyUtil.filterBad(regioncode);
    }

    public void setRegioncode(String regioncode) {
        this.regioncode = regioncode;
    }

    public String getMobileno() {
        return beans.MyUtil.filterBad(mobileno);
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmailid() {
        return beans.MyUtil.filterBad(emailid);
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getRole_id() {
        return beans.MyUtil.filterBad(role_id);
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public Register_New_User_UpdatedSaveForm() {
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

        if (getSno() == null || getSno().length() < 1) {
            errors.add("sno", new ActionMessage("error.sno.required"));
        } else {

            if (!Validations.Validate(getSno(), 2)) {
                errors.add("sno", new ActionMessage("error.sno.notmatch"));
            }

            if (getSno().length() > 30) {
                errors.add("sno", new ActionMessage("error.sno.maxlen"));
            }
        }

        if (getUser_name() == null || getUser_name().length() < 1) {
            errors.add("user_name", new ActionMessage("error.user_name.required"));
        } else {

            if (!Validations.Validate(getUser_name(), 3)) {
                errors.add("user_name", new ActionMessage("error.user_name.notmatch"));
            }

            if (getUser_name().length() > 30) {
                errors.add("user_name", new ActionMessage("error.user_name.maxlen"));
            }
        }

        if (getFromtime() == null || getFromtime().length() < 1) {
            errors.add("fromtime", new ActionMessage("error.fromtime.required"));
        } else {

            if (!Validations.Validate(getFromtime(), 16)) {
                errors.add("fromtime", new ActionMessage("error.fromtime.notmatch"));
            }

            if (getFromtime().length() > 15) {
                errors.add("fromtime", new ActionMessage("error.fromtime.maxlen"));
            }
        }

        if (getTotime() == null || getTotime().length() < 1) {
            errors.add("totime", new ActionMessage("error.totime.required"));
        } else {

            if (!Validations.Validate(getTotime(), 16)) {
                errors.add("totime", new ActionMessage("error.totime.notmatch"));
            }

        }

        if (getIpaddress() == null || getIpaddress().length() < 1) {
            errors.add("ipaddress", new ActionMessage("error.ipaddress.required"));
        } else if (getIpaddress().length() > 15) {
            errors.add("Ipaddress", new ActionMessage("error.Ipaddress.maxlen"));
        }

        if (getMobileno() == null || getMobileno().length() < 1) {
            errors.add("mobileno", new ActionMessage("error.mobileno.required"));
        } else {

            if (!Validations.Validate(getMobileno(), 2)) {
                errors.add("mobileno", new ActionMessage("error.mobileno.notmatch"));
            }

            if (getMobileno().length() > 10) {
                errors.add("mobileno", new ActionMessage("error.mobileno.maxlen"));
            }
        }

        if (getEmailid() == null || getEmailid().length() < 1) {
            errors.add("email", new ActionMessage("error.email.required"));
        } else {
            /*    if (!Validations.Validate(getEmail(),3)) {
             errors.add("email", new ActionMessage("error.email.notmatch"));
             }*/

            if (getEmailid().length() > 30) {
                errors.add("emailid", new ActionMessage("error.emailid.maxlen"));
            }
        }

        if (getRole_id() == null || getRole_id().length() < 1) {
            errors.add("role_id", new ActionMessage("error.role_id.required"));
        } else {
            if (!Validations.Validate(getRole_id(), 2)) {
                errors.add("role_id", new ActionMessage("error.role_id.notmatch"));
            }

            if (getRole_id().length() > 1) {
                errors.add("role_id", new ActionMessage("error.role_id.maxlen"));
            }
        }

        return errors;
    }
}
