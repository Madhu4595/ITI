 
package applicationForm;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

 
public class Api_missingdata_from extends org.apache.struts.action.ActionForm {
    
    String admission_number,ssc_regno,name,f_name,m_name,aadhar_num,dateofbirth,email_id,shift,unit;
    private String ewc;
    private String dual_mode;
    private FormFile photo;

    public FormFile getPhoto() {
        return photo;
    }

    public void setPhoto(FormFile photo) {
        this.photo = photo;
    }
    private String regid;
 
    public String getRegid() {
        return regid;
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }
    
    public String getDual_mode() {
        return dual_mode;
    }

    public void setDual_mode(String dual_mode) {
        this.dual_mode = dual_mode;
    }

    public Long getPhno() {
        return phno;
    }

    public void setPhno(Long phno) {
        this.phno = phno;
    }
    private Long phno;

    

    public String getPwd_category() {
        return pwd_category;
    }

    public void setPwd_category(String pwd_category) {
        this.pwd_category = pwd_category;
    }
    private String pwd_category;

    public String getSsc_regno() {
        return ssc_regno;
    }

    public void setSsc_regno(String ssc_regno) {
        this.ssc_regno = ssc_regno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getM_name() {
        return m_name;
    }

    public String getEwc() {
        return ewc;
    }

    public void setEwc(String ewc) {
        this.ewc = ewc;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getAadhar_num() {
        return aadhar_num;
    }

    public void setAadhar_num(String aadhar_num) {
        this.aadhar_num = aadhar_num;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAdmission_number() {
        return admission_number;
    }

    public void setAdmission_number(String admission_number) {
        this.admission_number = admission_number;
    }

    @Override
     public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
         
        if(getAdmission_number() == null || getAdmission_number().length() < 1){
         errors.add("admission_number", new ActionMessage("error.admission_number.required"));
        }
        if(!Validations.Validate(getAdmission_number(), 26)){
            errors.add("admission_number", new ActionMessage("error.admission_number.notmatch"));
        }
        if(getSsc_regno() == null || getSsc_regno().length() < 1){
            errors.add("ssc_regno", new ActionMessage("error.ssc_regno.required"));
        }
        if(!Validations.Validate(getSsc_regno(), 34)){
            errors.add("ssc_regno", new ActionMessage("error.ssc_regno.notmatch"));
        }
       
        return errors;
    }
    
}
