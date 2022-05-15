/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package callleter;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Gopi Daggumalle
 */
public class schedule_entry_interfaceForm extends org.apache.struts.action.ActionForm {

    private String minqual, caste, phase;

    public String getCaste() {
        return beans.MyUtil.filterBad(caste);
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getMinqual() {
        return beans.MyUtil.filterBad(minqual);
    }

    public void setMinqual(String minqual) {
        this.minqual = minqual;
    }

    public String getPhase() {
        return beans.MyUtil.filterBad(phase);
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public schedule_entry_interfaceForm() {
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
       // minqual 
       if(getMinqual() == null || getMinqual().equals("")){
           errors.add("minqual", new ActionMessage("error.minqual.required"));
       }
       //caste
       if(getCaste()== null || getCaste().equals("")){
           errors.add("caste", new ActionMessage("error.caste.required"));
       }
       //phase
        if(getPhase()== null || getPhase().equals("")){
           errors.add("phase", new ActionMessage("error.phase.required"));
       }
        return errors;
    }
}
