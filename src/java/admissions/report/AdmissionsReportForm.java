/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admissions.report;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author theru hareesh
 */
public class AdmissionsReportForm extends org.apache.struts.action.ActionForm {

    private String from_date;
    private String to_date;

    public String getFrom_date() {
        return beans.MyUtil.filterBad(from_date);
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return beans.MyUtil.filterBad(to_date);
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public AdmissionsReportForm() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        
        ActionErrors errors = new ActionErrors();
        
        if (from_date == null || from_date.equalsIgnoreCase("")) {
            errors.add("from_date", new ActionMessage("erro.from_date.required"));
        }
        if (to_date == null || to_date.equalsIgnoreCase("")) {
            errors.add("to_date", new ActionMessage("error.to_date.required"));
        }
        return errors;
    }
}
