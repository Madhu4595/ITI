package login;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

 
public class UpdateDataForm extends org.apache.struts.action.ActionForm {

    private String iti_name;
    private String address;
    private String city_town;
    private int pin_code;
    private String app;
    private String app_code,dget_iti_code;
    private String principalname;
    private String mobile;
    private String landlinenumber;
    private String iti_code;
    private String description;
    private String year_est;
    private String govt;
    private String dist_code;

    public String getDget_iti_code() {
        return dget_iti_code;
    }

    public void setDget_iti_code(String dget_iti_code) {
        this.dget_iti_code = dget_iti_code;
    }
    
    

    public String getApp() {
        return beans.MyUtil.filterBad(app);
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getApp_code() {
        return beans.MyUtil.filterBad(app_code);
    }

    public void setApp_code(String app_code) {
        this.app_code = app_code;
    }

    public int getPin_code() {
        return pin_code;
    }

    public void setPin_code(int pin_code) {
        this.pin_code = pin_code;
    }
    private String email;

    public String getIti_name() {
        return beans.MyUtil.filterBad(iti_name);
    }

    public void setIti_name(String iti_name) {
        this.iti_name = iti_name;
    }

    public String getAddress() {
        return beans.MyUtil.filterBad(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity_town() {
        return beans.MyUtil.filterBad(city_town);
    }

    public void setCity_town(String city_town) {
        this.city_town = city_town;
    }

    public String getEmail() {
        return beans.MyUtil.filterBad(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrincipalname() {
        return beans.MyUtil.filterBad(principalname);
    }

    public void setPrincipalname(String principalname) {
        this.principalname = principalname;
    }

    public String getMobile() {
        return beans.MyUtil.filterBad(mobile);
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandlinenumber() {
        return beans.MyUtil.filterBad(landlinenumber);
    }

    public void setLandlinenumber(String landlinenumber) {
        this.landlinenumber = landlinenumber;
    }

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getDescription() {
        return beans.MyUtil.filterBad(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear_est() {
        return beans.MyUtil.filterBad(year_est);
    }

    public void setYear_est(String year_est) {
        this.year_est = year_est;
    }

    public String getGovt() {
        return beans.MyUtil.filterBad(govt);
    }

    public void setGovt(String govt) {
        this.govt = govt;
    }

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }
     
 
    public UpdateDataForm() { super(); }
    
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        System.out.println("UR in UpdateDataForm.java");
        System.out.println("UR in UpdateDataForm.java->iti-code"+getIti_code());
        System.out.println("UR in UpdateDataForm.java->dist-code"+dist_code);
        
        
    ActionErrors errors = new ActionErrors();
     
    // iti_name
    if(getIti_name() == null || getIti_name().length()<1){
        errors.add("iti_name", new ActionMessage("error.iti_name.required"));
    }
     
    // address;
    if(getAddress()== null || getAddress().length()<1){
        errors.add("address", new ActionMessage("error.address.required"));
    }
    /*else if(!Validations.Validate(getAddress(), 3)){
        errors.add("address", new ActionMessage("error.address.notmatch"));
    }*/
    // city_town;
    if(getCity_town()== null || getCity_town().length()<1){
        errors.add("city_town", new ActionMessage("error.city_town.required"));
    }else if(!Validations.Validate(getCity_town(), 3)){
        errors.add("city_town", new ActionMessage("error.city_town.notmatch"));
    } 
    // pin_code;
    System.out.println("pincode is--->"+getPin_code());
    String pinc = String.valueOf(getPin_code());
    if(pinc == null || pinc.length()<6){
        errors.add("pin_code", new ActionMessage("error.pin_code.required"));
    }else if(!Validations.Validate(pinc, 11)){
        errors.add("pin_code", new ActionMessage("error.pin_code.notmatch"));
    }  
    // app;
    if(getApp()== null || getApp().length()<1){
        errors.add("app", new ActionMessage("error.app.required"));
    }else if(!Validations.Validate(getApp(), 29)){
        errors.add("app", new ActionMessage("error.app.notmatch"));
    }  
    // app_code,
    if(getApp_code()== null || getApp_code().length()<1){
        errors.add("app_code", new ActionMessage("error.app_code.required"));
    }else if(!Validations.Validate(getApp_code(), 30)){
        errors.add("app_code", new ActionMessage("error.app_code.notmatch"));
    } 
    // dget_iti_code;
    if(getDget_iti_code()== null || getDget_iti_code().length()<10){
        errors.add("dget_iti_code", new ActionMessage("error.dget_iti_code.required"));
    }else if(!Validations.Validate(getDget_iti_code(), 4)){
        errors.add("dget_iti_code", new ActionMessage("error.dget_iti_code.notmatch"));
    } 
   // principalname;
   if(getPrincipalname()== null || getPrincipalname().length()<1){
        errors.add("principalname", new ActionMessage("error.principalname.required"));
    }
   /*else if(!Validations.Validate(getPrincipalname(), 3)){
        errors.add("principalname", new ActionMessage("error.principalname.notmatch"));
    } */
    // mobile;
    System.out.println("sdffffff"+getMobile());
    if(getMobile()== null || getMobile().length()<10){
        errors.add("mobile", new ActionMessage("error.mobile.required"));
    }else if(!Validations.Validate(getMobile(), 10)){
        errors.add("mobile", new ActionMessage("error.mobile.notmatch"));
    } 
   // landlinenumber;
   if(getLandlinenumber()== null || getLandlinenumber().length()<1){
        errors.add("landlinenumber", new ActionMessage("error.landlinenumber.required"));
    }
    // iti_code;
    if(getIti_code()== null || getIti_code().length()<1){
        errors.add("iti_code", new ActionMessage("error.iti_code.required"));
    }else if(!Validations.Validate(getIti_code(), 7)){
        errors.add("iti_code", new ActionMessage("error.iti_code.notmatch"));
    }
    // description;
    if(getDescription()== null || getDescription().length()<1){
        errors.add("description", new ActionMessage("error.description.required"));
    }
    // year_est;
    if(getYear_est()== null || getYear_est().length()<1){
        errors.add("year_est", new ActionMessage("error.year_est.required"));
    }else if(!Validations.Validate(getYear_est(), 7)){
        errors.add("year_est", new ActionMessage("error.year_est.notmatch"));
    }
    // govt;
    if(getGovt()== null || getGovt().length()<1){
        errors.add("govt", new ActionMessage("error.govt.required"));
    }else if(!Validations.Validate(getGovt(), 29)){
        errors.add("govt", new ActionMessage("error.govt.notmatch"));
    }
    // dist_code;
    if(getDist_code()== null || getDist_code().length()<1){
        errors.add("dist_code", new ActionMessage("error.dist_code.required"));
    }else if(!Validations.Validate(getDist_code(), 31)){
        errors.add("dist_code", new ActionMessage("error.dist_code.notmatch"));
    }

    return errors;
    }
}
