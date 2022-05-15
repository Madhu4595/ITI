/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admissions;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
 
public class startAdmissionForm extends org.apache.struts.action.ActionForm {

    private String caste_perform, qual_perform, adm_perform;

    public String getCaste_perform() {
        return beans.MyUtil.filterBad(caste_perform);
    }

    public void setCaste_perform(String caste_perform) {
        this.caste_perform = caste_perform;
    }

    public String getQual_perform() {
        return beans.MyUtil.filterBad(qual_perform);
    }

    public void setQual_perform(String qual_perform) {
        this.qual_perform = qual_perform;
    }

    public String getAdm_perform() {
        return beans.MyUtil.filterBad(adm_perform);
    }

    public void setAdm_perform(String adm_perform) {
        this.adm_perform = adm_perform;
    }

    public startAdmissionForm() {
        super();
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        System.out.println("UR in startAdmissionForm.java");
        
        ActionErrors errors = new ActionErrors();
        //caste_perform
        if(getCaste_perform() == null || getCaste_perform().length()<1){
            errors.add("caste_perform", new ActionMessage("error.caste_perform.required"));
        }
//        //qual_perform
//         if(getQual_perform()== null || getQual_perform().length()<1){
//            errors.add("qual_perform", new ActionMessage("error.qual_perform.required"));
//        }
        //adm_perform
         if(getAdm_perform()== null || getAdm_perform().length()<1){
            errors.add("adm_perform", new ActionMessage("error.adm_perform.required"));
        }

        
        return errors;
    }
}
