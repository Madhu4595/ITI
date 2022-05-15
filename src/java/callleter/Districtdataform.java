package callleter;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
 
public class Districtdataform extends org.apache.struts.action.ActionForm {
    
   private String selectdistrict;
   private String statecode;
   private String dist_code;
   private String dist_name;
   private String itidist_code;
   
    public String getStatecode() {
        return beans.MyUtil.filterBad(statecode);
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getDist_name() {
        return beans.MyUtil.filterBad(dist_name);
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }

    public String getItidist_code() {
        return beans.MyUtil.filterBad(itidist_code);
    }

    public void setItidist_code(String itidist_code) {
        this.itidist_code = itidist_code;
    }
   

    public String getSelectdistrict() {
        return beans.MyUtil.filterBad(selectdistrict);
    }

    public void setSelectdistrict(String selectdistrict) {
        this.selectdistrict = selectdistrict;
    }
    
    public Districtdataform() {
        super();
         
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        if(getSelectdistrict() == null || getSelectdistrict().length() <1){
            errors.add("selectdistrict", new ActionMessage("error.selectdistrict.required"));
        }
        if(getStatecode() == null || getStatecode().length() <1){
            errors.add("statecode", new ActionMessage("error.statecode.required"));
        }
        if(getDist_code() == null || getDist_code().length() <1){
            errors.add("dist_code", new ActionMessage("error.dist_code.required"));
        }
        if(getDist_name() == null || getDist_name().length() <1){
            errors.add("dist_name",  new ActionMessage("error.dist_name.required"));
        }
        if(getItidist_code() == null || getItidist_code().length() <1){
            errors.add("itidist_code", new ActionMessage("error.itidist_code.required"));
        }
        
        return errors;
    }
}
