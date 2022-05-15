package applicationForm;

import DBC.Validations;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
 
public class approve_rejectForm extends org.apache.struts.action.ActionForm {

    private String regid, ssc_type;

    public String getSsc_type() {
        return beans.MyUtil.filterBad(ssc_type);
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

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        System.out.println("UR in approve_rejectForm.java");
        System.out.println("regid------------>"+getRegid());
        
        ActionErrors errors = new ActionErrors();
        System.out.println("sdfsdfsd"+getRegid());
         
        if (getRegid() == null || getRegid().length() < 1) {
            errors.add("regid", new ActionMessage("error.regid.required"));
        }

        if (!Validations.Validate(getRegid(), 2)) {
            errors.add("regid", new ActionMessage("error.regid.notmatch"));
        }

        if (getRegid().length() > 10) {
            errors.add("regid", new ActionMessage("error.regid.lenerror"));
        }
        
//        if (getSsc_type()== null || getSsc_type().length() < 1) {
//            errors.add("regid", new ActionMessage("error.regid.required"));
//        }
         
        try {
            if (!beans.generic.CandidateExists(getRegid(), request)) {
                errors.add("regid", new ActionMessage("error.regid.norecordfound"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(approve_rejectForm.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return errors;
    }
}
