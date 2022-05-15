package Admission.Process;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
 
 
public class Iti_Admissions_Report_Form extends org.apache.struts.action.ActionForm {
    private String table;
    private String date_of_admission,type_admission;
    private String sem_nonsem,trade_code,dist_code,phase;
    private String result;
    private String final_result;
    private String rddts,conv_approv,convts;
    private String iti_code;
    
    private String name;
    private String adm_num,regid,fname,phno,gender,caste,res_category,year_of_admission;
    
    private String table1,year,month;

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getRddts() {
        return beans.MyUtil.filterBad(rddts);
    }

    public void setRddts(String rddts) {
        this.rddts = rddts;
    }

    public String getConv_approv() {
        return beans.MyUtil.filterBad(conv_approv);
    }

    public void setConv_approv(String conv_approv) {
        this.conv_approv = conv_approv;
    }

    public String getConvts() {
        return beans.MyUtil.filterBad(convts);
    }

    public void setConvts(String convts) {
        this.convts = convts;
    }


    public String getTable1() {
        return beans.MyUtil.filterBad(table1);
    }

    public void setTable1(String table1) {
        this.table1 = table1;
    }

    public String getYear() {
        return beans.MyUtil.filterBad(year);
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return beans.MyUtil.filterBad(month);
    }

    public void setMonth(String month) {
        this.month = month;
    }
    public String getResult() {
        return beans.MyUtil.filterBad(result);
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFinal_result() {
        return beans.MyUtil.filterBad(final_result);
    }

    public void setFinal_result(String final_result) {
        this.final_result = final_result;
    }
    public String getSem_nonsem() {
        return beans.MyUtil.filterBad(sem_nonsem);
    }

    public void setSem_nonsem(String sem_nonsem) {
        this.sem_nonsem = sem_nonsem;
    }

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getPhase() {
        return beans.MyUtil.filterBad(phase);
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getDate_of_admission() {
        return beans.MyUtil.filterBad(date_of_admission);
    }

    public void setDate_of_admission(String date_of_admission) {
        this.date_of_admission = date_of_admission;
    }

    public String getType_admission() {
        return beans.MyUtil.filterBad(type_admission);
    }

    public void setType_admission(String type_admission) {
        this.type_admission = type_admission;
    }

    public String getTable() {
        return beans.MyUtil.filterBad(table);
    }

    public void setTable(String table) {
        this.table = table;
    }
    

    public String getAdm_num() {
        return beans.MyUtil.filterBad(adm_num);
    }

    public void setAdm_num(String adm_num) {
        this.adm_num = adm_num;
    }

    public String getRegid() {
        return beans.MyUtil.filterBad(regid);
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public String getFname() {
        return beans.MyUtil.filterBad(fname);
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPhno() {
        return beans.MyUtil.filterBad(phno);
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getGender() {
        return beans.MyUtil.filterBad(gender);
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCaste() {
        return beans.MyUtil.filterBad(caste);
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getRes_category() {
        return beans.MyUtil.filterBad(res_category);
    }

    public void setRes_category(String res_category) {
        this.res_category = res_category;
    }

    public String getYear_of_admission() {
        return beans.MyUtil.filterBad(year_of_admission);
    }

    public void setYear_of_admission(String year_of_admission) {
        this.year_of_admission = year_of_admission;
    }

    public String getTrade_code() {
        return beans.MyUtil.filterBad(trade_code);
    }

    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }
    private int number;
 
    public String getName() {
        return beans.MyUtil.filterBad(name);
    }
 
    public void setName(String string) {
        name = string;
    }

   
    public int getNumber() {
        return number;
    }
 
    public void setNumber(int i) {
        number = i;
    }

     
    public Iti_Admissions_Report_Form() {
        super();
        
    }
    
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        //adm_num
        if(getAdm_num() == null || getAdm_num().length()<1){
            errors.add("adm_num", new ActionMessage("error.adm_num.required"));
        }
        if (!Validations.Validate(getAdm_num(), 15)) {
            errors.add("adm_num", new ActionMessage("error.adm_num.notmatch"));
        }
        
        //regid
        if(getRegid() == null || getRegid().length()<1){
            errors.add("regid", new ActionMessage("error.regid.required"));
        }
        if (!Validations.Validate(getRegid(), 25)) {
            errors.add("regid", new ActionMessage("error.regid.notmatch"));
        }
        
        //name
        if(getName() == null || getName().length()<1){
            errors.add("name", new ActionMessage("error.name.required"));
        }
        if (!Validations.Validate(getName(), 3)) {
            errors.add("name", new ActionMessage("error.name.notmatch"));
        }
        
        //fname
        if(getFname() == null || getFname().length()<1){
            errors.add("fname", new ActionMessage("error.fname.required"));
        }
        if (!Validations.Validate(getFname(), 3)) {
            errors.add("fname", new ActionMessage("error.fname.notmatch"));
        }
        
        //phno
        if(getPhno() == null || getPhno().length()<1){
            errors.add("phno", new ActionMessage("error.phno.required"));
        }
        
        //gender
        if(getGender() == null || getGender().length()<1){
            errors.add("gender", new ActionMessage("error.gender.required"));
        }
        
        //caste
        if(getCaste() == null || getCaste().length()<1){
            errors.add("caste", new ActionMessage("error.caste.required"));
        }
        
        //res_category
        if(getRes_category() == null || getRes_category().length()<1){
            errors.add("res_category", new ActionMessage("error.res_category.required"));
        }
        
        //year_of_admission
        if(getYear_of_admission() == null || getYear_of_admission().length()<1){
            errors.add("year_of_admission", new ActionMessage("error.year_of_admission.required"));
        }
        
        //date_of_admission
        if(getDate_of_admission() == null || getDate_of_admission().length()<1){
            errors.add("date_of_admission", new ActionMessage("error.date_of_admission.required"));
        }
        
        //type_admission
        if(getType_admission() == null || getType_admission().length()<1){
            errors.add("type_admission", new ActionMessage("error.type_admission.required"));
        }
        
        //sem_nonsem
        if(getSem_nonsem() == null || getSem_nonsem().length()<1){
            errors.add("sem_nonsem", new ActionMessage("error.sem_nonsem.required"));
        }
        
        //dist_code
        if(getDist_code() == null || getDist_code().length()<1){
            errors.add("dist_code", new ActionMessage("error.dist_code.required"));
        }
        
        //iti_code
        if(getIti_code() == null || getIti_code().length()<1){
            errors.add("iti_code", new ActionMessage("error.iti_code.required"));
        }
        
        //trade_code
        if(getTrade_code() == null || getTrade_code().length()<1){
            errors.add("trade_code", new ActionMessage("error.trade_code.required"));
        }
        
        //phase
        if(getPhase() == null || getPhase().length()<1){
            errors.add("phase", new ActionMessage("error.phase.required"));
        }
        if (!Validations.Validate(getPhase(), 28)) {
            errors.add("phase", new ActionMessage("error.phase.notmatch"));
        }
        //table
        if(getTable() == null || getTable().length()<1){
            errors.add("table", new ActionMessage("error.table.required"));
        }
        
        
        
        return errors;
    }
}
