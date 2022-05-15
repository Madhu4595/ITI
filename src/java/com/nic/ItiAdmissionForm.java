/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import DBC.Validations;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author SUBBAREDDY
 */
public class ItiAdmissionForm extends org.apache.struts.action.ActionForm {

    private String iti_code, idmarks1, idmarks2, ssc_regno, ssc_board, ssc_year, ssc_month, qual_perform, caste_perform;
    private String name, type_admission;
    private String fatherName;
    private String mname;
    private String address;
    private String phoneNumber;
    private String adarNo;
    private boolean ssc_passed;
    private boolean interPassed;
    private int inter_marks;
    private String local;
    private int pin_code;
    private int regid;
    private String iti_name;
    private String cast;
    private String sex;
    private String meritNumber;
    private String exservice;
    private String dob;
    private String phc;
    private String res_category;
    private String acad_year;
    private java.sql.Date currentDate;
    private String adm_num;
    private String trade_code;
    private String trade_name;
    private String gpt;
    private FormFile pic;
    private String dis_caste;
    
    private String caste_name,ews,pwd_category,email_id;

    public String getCaste_name() {
        return caste_name;
    }

    public void setCaste_name(String caste_name) {
        this.caste_name = caste_name;
    }

    public String getEws() {
        return ews;
    }

    public void setEws(String ews) {
        this.ews = ews;
    }

    public String getPwd_category() {
        return pwd_category;
    }

    public void setPwd_category(String pwd_category) {
        this.pwd_category = pwd_category;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

            
    public String getDis_caste() {
        return dis_caste;
    }

    public void setDis_caste(String dis_caste) {
        this.dis_caste = dis_caste;
    }
    

    public String getType_admission() {
        return beans.MyUtil.filterBad(type_admission);
    }

    public void setType_admission(String type_admission) {
        this.type_admission = type_admission;
    }

    public String getQual_perform() {
        return beans.MyUtil.filterBad(qual_perform);
    }

    public void setQual_perform(String qual_perform) {
        this.qual_perform = qual_perform;
    }

    public String getCaste_perform() {
        return beans.MyUtil.filterBad(caste_perform);
    }

    public void setCaste_perform(String caste_perform) {
        this.caste_perform = caste_perform;
    }

    public FormFile getPic() {
        return pic;
    }

    public void setPic(FormFile pic) {
        this.pic = pic;
    }

    public String getIdmarks2() {
        //System.out.println(idmarks2);
        return beans.MyUtil.filterBad(idmarks2);
    }

    public void setIdmarks2(String idmarks2) {
        //System.out.println(idmarks2);
        this.idmarks2 = idmarks2;
    }

    public String getSsc_regno() {
        return beans.MyUtil.filterBad(ssc_regno);
    }

    public void setSsc_regno(String ssc_regno) {
        this.ssc_regno = ssc_regno;
    }

    public String getSsc_board() {
        return beans.MyUtil.filterBad(ssc_board);
    }

    public void setSsc_board(String ssc_board) {
        this.ssc_board = ssc_board;
    }

    public String getSsc_year() {
        return beans.MyUtil.filterBad(ssc_year);
    }

    public void setSsc_year(String ssc_year) {
        this.ssc_year = ssc_year;
    }

    public String getSsc_month() {
        return beans.MyUtil.filterBad(ssc_month);
    }

    public void setSsc_month(String ssc_month) {
        this.ssc_month = ssc_month;
    }

    public String getIdmarks1() {
        return beans.MyUtil.filterBad(idmarks1);
    }

    public void setIdmarks1(String idmarks1) {
        this.idmarks1 = idmarks1;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getAddress() {
        return beans.MyUtil.filterBad(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return beans.MyUtil.filterBad(phoneNumber);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAdarNo() {
        return beans.MyUtil.filterBad(adarNo);
    }

    public void setAdarNo(String adarNo) {
        this.adarNo = adarNo;
    }

    public boolean getSsc_passed() {
        return ssc_passed;
    }

    public void setSsc_passed(boolean ssc_passed) {
        this.ssc_passed = ssc_passed;
    }

    public boolean getInterPassed() {
        return interPassed;
    }

    public void setInterPassed(boolean interPassed) {
        this.interPassed = interPassed;
    }

    public int getInter_marks() {
        return inter_marks;
    }

    public void setInter_marks(int inter_marks) {
        this.inter_marks = inter_marks;
    }

    public String getLocal() {
        return beans.MyUtil.filterBad(local);
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getPin_code() {
        return pin_code;
    }

    public void setPin_code(int pin_code) {
        this.pin_code = pin_code;
    }
    private String trade_short;

    public String getMname() {
        return beans.MyUtil.filterBad(mname);
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public void setGpt(String gpt) {
        this.gpt = gpt;
    }

    public String getGpt() {
        return beans.MyUtil.filterBad(gpt);
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public String getTrade_name() {
        return beans.MyUtil.filterBad(trade_name);
    }

    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }

    public String getTrade_code() {
        return  trade_code;
    }

    public void setAdm_num(String adm_num) {
        this.adm_num = adm_num;
    }

    public String getAdm_num() {
        return beans.MyUtil.filterBad(adm_num);
    }

    public void setAcad_year(String acad_year) {
        this.acad_year = acad_year;
    }

    public String getAcad_year() {
        return beans.MyUtil.filterBad(acad_year);
    }

    public void setRes_category(String res_category) {
        System.out.println("@@@@@@@@@@@@@@@@@hello. bean. setting hello " + res_category);
        this.res_category = res_category;
    }

    public String getRes_category() {
        return beans.MyUtil.filterBad(res_category);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return beans.MyUtil.filterBad(name);
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherName() {
        return beans.MyUtil.filterBad(fatherName);
    }

    public void setRegid(int regid) {
        this.regid = regid;
    }

    public int getRegid() {
        return regid;
    }

    public void setTrade_short(String trade_short) {
        this.trade_short = trade_short;
    }

    public String getTrade_short() {
        return beans.MyUtil.filterBad(trade_short);
    }

    public void setIti_name(String iti_name) {
        this.iti_name = iti_name;
    }

    public String getIti_name() {
        return beans.MyUtil.filterBad(iti_name);
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getCast() {      
        System.out.println("printing cast in ItiAdmissionForm.java page"+beans.MyUtil.filterBad(cast));
        return beans.MyUtil.filterBad(cast);
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return beans.MyUtil.filterBad(sex);
    }

    public void setMeritNumber(String meritNumber) {
        this.meritNumber = meritNumber;
    }

    public String getMeritNumber() {
        return beans.MyUtil.filterBad(meritNumber);
    }

    public void setExservice(String exservice) {
        this.exservice = exservice;
    }

    public String getExservice() {
        return beans.MyUtil.filterBad(exservice);
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return beans.MyUtil.filterBad(dob);
    }

    public void setPhc(String phc) {
        this.phc = phc;
    }

    public String getPhc() {
        return beans.MyUtil.filterBad(phc);
    }

    public ItiAdmissionForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        
        ActionErrors errors = new ActionErrors();
        
        if (trade_code == null || trade_code.length() == 0) {
            errors.add(trade_code, new ActionMessage("ItiAdmissionForm.trade_code.required"));
        }
        //getIdmarks1()
        if(getIdmarks1() == null || getIdmarks1().equals("")){
        errors.add("idmarks1", new ActionMessage("error.idmarks1.required"));
        } else if (!Validations.Validate(getIdmarks1(), 12)) {
         errors.add("idmarks1", new ActionMessage("error.idmarks1.notmatch"));
         }
        //getIdmarks2()
        if(getIdmarks2() == null || getIdmarks2().equals("")){
        errors.add("idmarks2", new ActionMessage("error.idmarks2.required"));
        } else if (!Validations.Validate(getIdmarks2(), 12)) {
         errors.add("idmarks2", new ActionMessage("error.idmarks2.notmatch"));
         }
        //getSsc_regno()
        if(getSsc_regno() == null || getSsc_regno().equals("")){
        errors.add("ssc_regno", new ActionMessage("error.ssc_regno.required"));
        } else if (!Validations.Validate(getSsc_regno(), 4)) {
         errors.add("ssc_regno", new ActionMessage("error.ssc_regno.notmatch"));
         }
        //ssc_board
        if(getSsc_board()== null || getSsc_board().equals("")){
        errors.add("ssc_board", new ActionMessage("error.ssc_board.required"));
        }
        //ssc_year
        if(getSsc_year()== null || getSsc_year().equals("")){
        errors.add("ssc_year", new ActionMessage("error.ssc_year.required"));
        }
        //ssc_month
        if(getSsc_month()== null || getSsc_month().equals("")){
        errors.add("ssc_month", new ActionMessage("error.ssc_month.required"));
        }
        //type_admission
        if(getType_admission()== null || getType_admission().equals("")){
        errors.add("type_admission", new ActionMessage("error.type_admission.required"));
        }
        
        return errors;
    }
}
