 
package Reports.Verification;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

 
public class verify_report_interfaceForm extends org.apache.struts.action.ActionForm {

    String from, to;

    public String getFrom() {
        return beans.MyUtil.filterBad(from);
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return beans.MyUtil.filterBad(to);
    }

    public void setTo(String to) {
        this.to = to;
    }

    public verify_report_interfaceForm() {
        super();
        // TODO Auto-generated constructor stub
    }
 
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        
        ActionErrors errors = new ActionErrors();
        
        if (getFrom().length() < 1 || getFrom() == null) {
            errors.add("from", new ActionMessage("error.from.required"));
        } else if (!Validations.Validate(getFrom(), 2)) {
            errors.add("from", new ActionMessage("error.from.notmatch"));
        }
        if (getFrom().length() > 9) {
            errors.add("from", new ActionMessage("error.from.lenerror"));
        }

        if (getTo().length() < 1 || getTo() == null) {
            errors.add("to", new ActionMessage("error.to.required"));
        } else if (!Validations.Validate(getTo(), 2)) {
            errors.add("to", new ActionMessage("error.to.notmatch"));
        }
        if (getTo().length() > 9) {
            errors.add("to", new ActionMessage("error.to.lenerror"));
        }
        return errors;
    }
}
