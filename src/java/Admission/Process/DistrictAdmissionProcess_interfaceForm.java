/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admission.Process;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author GOPI
 */
public class DistrictAdmissionProcess_interfaceForm extends org.apache.struts.action.ActionForm {

    private String acad_year;
    private java.sql.Date currentDate;
    private String adm_num;
    private String trade_code;
    private String trade_name;
    private String gpt;
    private Integer merit_from;
    private Integer merit_to;
    private String rank;
    private String iti_code, idmarks1, idmarks2, ssc_regno, ssc_board, ssc_year, ssc_month;
    private String name;
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
    private FormFile pic;

    public String getAcad_year() {
        return beans.MyUtil.filterBad(acad_year);
    }

    public void setAcad_year(String acad_year) {
        this.acad_year = acad_year;
    }

    public String getAdm_num() {
        return beans.MyUtil.filterBad(adm_num);
    }

    public void setAdm_num(String adm_num) {
        this.adm_num = adm_num;
    }

    public String getTrade_code() {
        return beans.MyUtil.filterBad(trade_code);
    }

    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }

    public String getTrade_name() {
        return beans.MyUtil.filterBad(trade_name);
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public String getGpt() {
        return beans.MyUtil.filterBad(gpt);
    }

    public void setGpt(String gpt) {
        this.gpt = gpt;
    }

    public String getFatherName() {
        return beans.MyUtil.filterBad(fatherName);
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getName() {
        return beans.MyUtil.filterBad(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMname() {
        return beans.MyUtil.filterBad(mname);
    }

    public void setMname(String mname) {
        this.mname = mname;
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

    public boolean isSsc_passed() {
        return ssc_passed;
    }

    public void setSsc_passed(boolean ssc_passed) {
        this.ssc_passed = ssc_passed;
    }

    public boolean isInterPassed() {
        return interPassed;
    }

    public void setInterPassed(boolean interPassed) {
        this.interPassed = interPassed;
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

    public String getCast() {
        return beans.MyUtil.filterBad(cast);
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getSex() {
        return beans.MyUtil.filterBad(sex);
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMeritNumber() {
        return beans.MyUtil.filterBad(meritNumber);
    }

    public void setMeritNumber(String meritNumber) {
        this.meritNumber = meritNumber;
    }

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getIdmarks1() {
        return beans.MyUtil.filterBad(idmarks1);
    }

    public void setIdmarks1(String idmarks1) {
        this.idmarks1 = idmarks1;
    }

    public String getIdmarks2() {
        return beans.MyUtil.filterBad(idmarks2);
    }

    public void setIdmarks2(String idmarks2) {
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

    public int getInter_marks() {
        return inter_marks;
    }

    public void setInter_marks(int inter_marks) {
        this.inter_marks = inter_marks;
    }

    public int getRegid() {
        return regid;
    }

    public void setRegid(int regid) {
        this.regid = regid;
    }

    public String getIti_name() {
        return beans.MyUtil.filterBad(iti_name);
    }

    public void setIti_name(String iti_name) {
        this.iti_name = iti_name;
    }

    public String getExservice() {
        return beans.MyUtil.filterBad(exservice);
    }

    public void setExservice(String exservice) {
        this.exservice = exservice;
    }

    public String getPhc() {
        return beans.MyUtil.filterBad(phc);
    }

    public void setPhc(String phc) {
        this.phc = phc;
    }

    public FormFile getPic() {
        return pic;
    }

    public void setPic(FormFile pic) {
        this.pic = pic;
    }

    public String getRes_category() {
        return beans.MyUtil.filterBad(res_category);
    }

    public void setRes_category(String res_category) {
        this.res_category = res_category;
    }

    public String getDob() {
        return beans.MyUtil.filterBad(dob);
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Integer getMerit_from() {
        return merit_from;
    }

    public void setMerit_from(Integer merit_from) {
        this.merit_from = merit_from;
    }

    public Integer getMerit_to() {
        return merit_to;
    }

    public void setMerit_to(Integer merit_to) {
        this.merit_to = merit_to;
    }

    public String getRank() {
        return beans.MyUtil.filterBad(rank);
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public DistrictAdmissionProcess_interfaceForm() {
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
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        Pattern p = Pattern.compile("^[\\d]*$");
        Matcher m = p.matcher(rank);
        if (rank == null || rank == "") {
            errors.add(rank, new ActionMessage("ItiAdmissionProcessForm.rank.required"));
        } else {
            if (m.matches()) {
                if (Integer.parseInt(rank) < merit_from) {
                    errors.add(rank, new ActionMessage("ItiAdmissionProcessForm.rank.invalid", merit_from, merit_to));
                } else if (Integer.parseInt(rank) > merit_to) {
                    errors.add(rank, new ActionMessage("ItiAdmissionProcessForm.rank.invalid", merit_from, merit_to));
                }
            } else {
                errors.add(rank, new ActionMessage("ItiAdmissionProcessForm.rank.mask"));
            }
        }

        return errors;
    }
}
