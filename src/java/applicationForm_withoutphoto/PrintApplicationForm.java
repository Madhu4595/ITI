package applicationForm_withoutphoto;

import DBC.Validations;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class PrintApplicationForm extends org.apache.struts.action.ActionForm {

    String regid;

    public String getRegid() {
        return beans.MyUtil.filterBad(regid);
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
//validation part will be here.. These are the serverside validations
        if (getRegid() == null || getRegid().length() < 1) {
            errors.add("regid", new ActionMessage("error.regid.required"));
        }

        if (!Validations.Validate(getRegid(), 2)) {
            errors.add("regid", new ActionMessage("error.regid.notmatch"));
        }

        if (getRegid().length() > 10) {
            errors.add("regid", new ActionMessage("error.regid.lenerror"));
        }
        try {
            if (!beans.generic.CandidateExists(getRegid(), request)) {
                errors.add("regid", new ActionMessage("error.regid.norecordfound"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrintApplicationForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return errors;
    }
}
