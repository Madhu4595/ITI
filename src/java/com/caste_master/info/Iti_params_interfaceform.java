package com.caste_master.info;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

 
public class Iti_params_interfaceform extends org.apache.struts.action.ActionForm {

    private String code;
    private String description;
    private String value;

    public String getCode() {
        return beans.MyUtil.filterBad(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return beans.MyUtil.filterBad(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return beans.MyUtil.filterBad(value);
    }

    public void setValue(String value) {
        this.value = value;
    }
    

    public Iti_params_interfaceform() {
        super();
         
    }

    
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        
        ActionErrors errors = new ActionErrors();

        //code getCode()
        if(getCode() == null || getCode().length()<1){
            errors.add("code", new ActionMessage("error.code.required"));
        }else if(!Validations.Validate(getCode(), 2)){
            errors.add("code", new ActionMessage("error.code.notmatch"));
        }
        //description getDescription()
        if(getDescription() == null || getDescription().length()<1){
            errors.add("description", new ActionMessage("error.description.required"));
        } 
        //value getValue()
        if(getValue() == null || getValue().length()<1){
            errors.add("value",  new ActionMessage("error.value.required"));
        }
         
        return errors;
    }
}
