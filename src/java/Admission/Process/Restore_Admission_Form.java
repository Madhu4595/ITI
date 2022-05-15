package Admission.Process;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

 
public class Restore_Admission_Form extends org.apache.struts.action.ActionForm {

    private String adm_no;

    public String getAdm_no() {
        return beans.MyUtil.filterBad(adm_no);
    }

    public void setAdm_no(String adm_no) {
        this.adm_no = adm_no;
    }

    public Restore_Admission_Form() {
        super();
         
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        
        ActionErrors errors = new ActionErrors();
        
        if (getAdm_no() == null || getAdm_no().length() < 1) {
            errors.add("adm_num", new ActionMessage("error.adm_num.required"));
        }else if(!Validations.Validate(getAdm_no(), 4)){
            errors.add("adm_num", new ActionMessage("error.adm_num.notmatch"));
        }
        return errors;
    }
}
