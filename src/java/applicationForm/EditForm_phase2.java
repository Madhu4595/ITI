/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import DBC.Validations;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
/**
 *
 * @author APDCA
 */
public class EditForm_phase2 extends org.apache.struts.action.ActionForm {
    private String regid, ssc_type,ssc_regno,dob;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSsc_type() {
        return beans.MyUtil.filterBad(ssc_type);
    }

    public String getSsc_regno() {
        return ssc_regno;
    }

    public void setSsc_regno(String ssc_regno) {
        this.ssc_regno = ssc_regno;
    }

    public void setSsc_type(String ssc_type) {
        this.ssc_type = ssc_type;
    }

    public String getRegid() {
        return beans.MyUtil.filterBad(regid);
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        Connection con = DBC.DBConnection.getConnection(request);

        if (getRegid() == null || getRegid().length() < 1) {
            errors.add("regid", new ActionMessage("error.regid.required"));
        }

        if (!Validations.Validate(getRegid(), 2)) {
            errors.add("regid", new ActionMessage("error.regid.notmatch"));
        }

        if (getRegid().length() > 10) {
            errors.add("regid", new ActionMessage("error.regid.lenerror"));
        }
//        try {
//            if (!beans.generic.CandidateExists(getRegid(), request)) {
//                errors.add("regid", new ActionMessage("error.regid.norecordfound"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(UpdateForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
//        if (!Validations.Validate(getSsc_type(), 6)) {
//            errors.add("ssc_type", new ActionMessage("error.ssc_type.notmatch"));
//        }
        return errors;
    }
    
}
