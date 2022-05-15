/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Delete;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

 
public class Delete_admission_details_studentForm extends org.apache.struts.action.ActionForm {

    private String adm_num,dsc_year;

    public String getAdm_num() {
        return beans.MyUtil.filterBad(adm_num);
    }

    public void setAdm_num(String adm_num) {
        this.adm_num = adm_num;
    }

    public void setDsc_year(String dsc_year) {
        this.dsc_year = dsc_year;
    }

    public String getDsc_year() {
        return dsc_year;
    }
    

    public Delete_admission_details_studentForm() {
        super();
        
    }

     
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        System.out.println("U R IN Delete_admission_details_studentForm");
        
        ActionErrors errors = new ActionErrors();
        
        if (getAdm_num() == null || getAdm_num().length() < 1) {
            errors.add("admnum", new ActionMessage("error.adm_num.required"));
        } 
//        if (!Validations.Validate(getAdm_num(), 15)) {
//         errors.add("admnum", new ActionMessage("error.adm_num.notmatch"));
//         }
        if(getDsc_year() == null || getDsc_year().length()<1){
         errors.add("dsc_year",new ActionMessage("error.dsc_year.required"));
         }
        if(!Validations.Validate(getDsc_year(), 27)){
         errors.add("dsc_year", new ActionMessage("error.dsc_year.notmatch"));
         }
        return errors;
    }
}
