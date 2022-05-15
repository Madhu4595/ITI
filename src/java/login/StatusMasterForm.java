 
package login;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
 
public class StatusMasterForm extends org.apache.struts.action.ActionForm {

    private String status, loc, operation;
    private String type;

    public String getType() {
        return beans.MyUtil.filterBad(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return beans.MyUtil.filterBad(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoc() {
        return beans.MyUtil.filterBad(loc);
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getOperation() {
        return beans.MyUtil.filterBad(operation);
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public StatusMasterForm() {
        super();
        
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        
        ActionErrors errors = new ActionErrors();
        
        // type
       if(getType()== null || getType().length()<1){
           errors.add("type", new ActionMessage("error.type.required"));
       }else if(!Validations.Validate(getType(), 3)){
           errors.add("type", new ActionMessage("error.type.notmatch"));
       }
        
       // status 
       if(getStatus() == null || getStatus().length()<1){
           errors.add("status", new ActionMessage("error.status.required"));
       } 
       //loc 
       if(getLoc()== null || getLoc().length()<1){
           errors.add("loc", new ActionMessage("error.loc.required"));
       } 
       //operation
       if(getOperation()== null || getOperation().length()<1){
           errors.add("operation", new ActionMessage("error.operation.required"));
       } 
       
        
         
        return errors;
    }
}
