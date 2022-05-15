/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Ramesh Naidu
 */
public class Register_Verification_Form extends org.apache.struts.action.ActionForm {

    String username, pwd, cpwd, designation, eid, mbno, ename, officer_iti;

    public String getOfficer_iti() {
        return beans.MyUtil.filterBad(officer_iti);
    }

    public void setOfficer_iti(String officer_iti) {
        this.officer_iti = officer_iti;
    }

    public String getUsername() {
        return beans.MyUtil.filterBad(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return beans.MyUtil.filterBad(pwd);
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCpwd() {
        return beans.MyUtil.filterBad(cpwd);
    }

    public void setCpwd(String cpwd) {
        this.cpwd = cpwd;
    }

    public String getDesignation() {
        return beans.MyUtil.filterBad(designation);
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEid() {
        return beans.MyUtil.filterBad(eid);
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getMbno() {
        return beans.MyUtil.filterBad(mbno);
    }

    public void setMbno(String mbno) {
        this.mbno = mbno;
    }

    public String getEname() {
        return beans.MyUtil.filterBad(ename);
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    /**
     *
     */
    public Register_Verification_Form() {
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
        if (getUsername() == null || getUsername().length() < 1) {
            errors.add("employeecode", new ActionMessage("error.employeecode.required"));
        } else if (!Validations.Validate(getUsername(), 2)) {
            errors.add("employeecode", new ActionMessage("error.employeecode.notmatch"));
        }
        if (getPwd() == null || getPwd().length() < 1) {
            errors.add("password", new ActionMessage("error.password.required"));
        } else if (!Validations.Validate(getPwd(), 1)) {
            errors.add("password", new ActionMessage("error.password.notmatch"));
        }
        if (getCpwd() == null || getCpwd().length() < 1) {
            errors.add("confirmpwd", new ActionMessage("error.confirmpwd.required")); 
        } else if (!Validations.Validate(getCpwd(), 1)) {
            errors.add("confirmpwd", new ActionMessage("error.confirmpwd.notmatch"));
        }
        if (getDesignation() == null || getDesignation().length() < 1) {
            errors.add("designation", new ActionMessage("error.designation.required")); 
        } else if (!Validations.Validate(getDesignation(), 3)) {
            errors.add("designation", new ActionMessage("error.designation.notmatch"));
        }
        if (getEid() == null || getEid().length() < 1) {
            errors.add("eid", new ActionMessage("error.eid.required")); 
        } else if (!Validations.Validate(getEid(), 3)) {
            errors.add("eid", new ActionMessage("error.eid.notmatch"));
        }
        if (getMbno() == null || getMbno().length() < 1) {
            errors.add("mbno", new ActionMessage("error.mbno.required")); 
        } else if (!Validations.Validate(getMbno(), 10)) {
            errors.add("mbno", new ActionMessage("error.mbno.notmatch"));
        }
        if (getEname() == null || getEname().length() < 1) {
            errors.add("ename", new ActionMessage("error.ename.required")); 
        } else if (!Validations.Validate(getEname(), 3)) {
            errors.add("ename", new ActionMessage("error.ename.notmatch"));
        }
        return errors;
    }
}
