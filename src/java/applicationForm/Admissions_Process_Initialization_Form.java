package applicationForm;
 
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
 
 
public class Admissions_Process_Initialization_Form extends org.apache.struts.action.ActionForm{
    private String phase;
    private String applstartdate,applenddate;
    private String verstartdate,verenddate;

   
    public void setPhase(String phase) {
        this.phase = phase;
    }

    public void setApplstartdate(String applstartdate) {
        this.applstartdate = applstartdate;
    }

    public void setApplenddate(String applenddate) {
        this.applenddate = applenddate;
    }

    public void setVerstartdate(String verstartdate) {
        this.verstartdate = verstartdate;
    }

    public void setVerenddate(String verenddate) {
        this.verenddate = verenddate;
    }

    
    public String getPhase() {
        return phase;
    }

    public String getApplstartdate() {
        return applstartdate;
    }

    public String getApplenddate() {
        return applenddate;
    }

    public String getVerstartdate() {
        return verstartdate;
    }

    public String getVerenddate() {
        return verenddate;
    }
    
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
        ActionErrors errors = new ActionErrors();
              
                if(getPhase() == null || getPhase().length()<1){
                    errors.add("phase", new ActionMessage("error.phase.required"));
                }
                if(getApplstartdate() == null || getApplstartdate().length()<1){
                    errors.add("applicationstartdate", new ActionMessage("error.applicationstartdate.required"));
                }
                if(getApplenddate() == null || getApplenddate().length()<1){
                    errors.add("applicationenddate", new ActionMessage("error.applicationenddate.required"));
                }
                if(getVerstartdate() == null || getVerstartdate().length()<1){
                    errors.add("verificationstartdate", new ActionMessage("error.verificationstartdate.required"));
                }
                if(getVerenddate() == null || getVerenddate().length()<1){
                    errors.add("verificationenddate", new ActionMessage("error.verificationenddate.required"));
                }
 
        return errors;
    
    }
    
}
