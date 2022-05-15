 
package com.nic.Delete;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
 
public class DeleteAdmission_interfaceForm extends org.apache.struts.action.ActionForm {

    private String adm_num, trade_shot, trade_name, iti_name, trade_code, iti_code, res_category, dsc_year, strength, delete, strength_fill, strength_vacant;
    private String regid, name, fname, caste, dob, rank, gender,remarks;

    
    private Integer strength_del,strength_fill_del,strength_vacant_del,role_id;

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }
    private String remark;

   

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    

    public Integer getStrength_del() {
        return strength_del;
    }

    public void setStrength_del(Integer strength_del) {
        this.strength_del = strength_del;
    }

    public Integer getStrength_fill_del() {
        return strength_fill_del;
    }

    public void setStrength_fill_del(Integer strength_fill_del) {
        this.strength_fill_del = strength_fill_del;
    }

    public Integer getStrength_vacant_del() {
        return strength_vacant_del;
    }

    public void setStrength_vacant_del(Integer strength_vacant_del) {
        this.strength_vacant_del = strength_vacant_del;
    }
    
    
    

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRegid() {
        return beans.MyUtil.filterBad(regid);
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public String getName() {
        return beans.MyUtil.filterBad(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return beans.MyUtil.filterBad(fname);
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getCaste() {
        return beans.MyUtil.filterBad(caste);
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getDob() {
        return beans.MyUtil.filterBad(dob);
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRank() {
        return beans.MyUtil.filterBad(rank);
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getGender() {
        return beans.MyUtil.filterBad(gender);
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getTrade_name() {
        return beans.MyUtil.filterBad(trade_name);
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public String getTrade_shot() {
        return beans.MyUtil.filterBad(trade_shot);
    }

    public void setTrade_shot(String trade_shot) {
        this.trade_shot = trade_shot;
    }

    public String getIti_name() {
        return beans.MyUtil.filterBad(iti_name);
    }

    public void setIti_name(String iti_name) {
        this.iti_name = iti_name;
    }

    public String getStrength() {
        return beans.MyUtil.filterBad(strength);
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getStrength_fill() {
        return beans.MyUtil.filterBad(strength_fill);
    }

    public void setStrength_fill(String strength_fill) {
        this.strength_fill = strength_fill;
    }

    public String getStrength_vacant() {
        return beans.MyUtil.filterBad(strength_vacant);
    }

    public void setStrength_vacant(String strength_vacant) {
        this.strength_vacant = strength_vacant;
    }

    public String getDsc_year() {
        return beans.MyUtil.filterBad(dsc_year);
    }

    public void setDsc_year(String dsc_year) {
        this.dsc_year = dsc_year;
    }

    public String getTrade_code() {
        return beans.MyUtil.filterBad(trade_code);
    }

    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getRes_category() {
        return beans.MyUtil.filterBad(res_category);
    }

    public void setRes_category(String res_category) {
        this.res_category = res_category;
    }

    public String getAdm_num() {
        return beans.MyUtil.filterBad(adm_num);
    }

    public void setAdm_num(String adm_num) {
        this.adm_num = adm_num;
    }

    public DeleteAdmission_interfaceForm() {
        super();
        
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        System.out.println(" UR IN -> DeleteAdmission_interfaceForm.java <-");
        
        ActionErrors errors = new ActionErrors();
        
        if (getAdm_num() == null || getAdm_num().length() < 1) {
            errors.add("adm_num", new ActionMessage("error.adm_num.required"));    
        }else if(!Validations.Validate(getAdm_num(), 4)){
            errors.add("adm_num", new ActionMessage("error.adm_num.notmatched"));
        }
        
        return errors;
    }
}
