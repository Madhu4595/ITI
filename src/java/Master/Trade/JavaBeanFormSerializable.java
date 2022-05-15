/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Master.Trade;

import java.sql.Date;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Gopi Daggumalle
 */
public class JavaBeanFormSerializable implements java.io.Serializable {

    String trade_code, strength, rank, adm_num,admitted;
    String fromd, tod, ipaddress, iti_code, ssc_tot_marks, strength_fill, strength_vacant;
    int merit_from, merit_to;
    Date currentDate;
    
    private String acad_year;
    //private String trade_name;
    private String gpt;
    String trade_short, available, iti_name, trade_name, editoption, freeze, lable, dist_name, dist_code;
    String category_code, manage_seats, category_name;
    String name, faname, totalgp, caste, dob, gender, regno, label2;
    String ssc_first_lang_marks, ssc_second_lang_marks, ssc_eng_marks, ssc_math_marks, ssc_sci_marks, ssc_social_marks;
    String ssc_first_lang_marks_gpa, ssc_second_lang_marks_gpa, ssc_eng_marks_gpa, ssc_math_marks_gpa, ssc_sci_marks_gpa, ssc_social_marks_gpa;
    String inter_first_marks, inter_second_marks, inter_third_marks, inter_marks, inter_passed;
    String phc, exser, label, href;
    private String idmarks1, idmarks2, ssc_regno, ssc_board, ssc_year, ssc_month;
    private String fatherName;
    private String mname;
    private String address;
    private String phoneNumber;
    private String adarNo;
    private boolean ssc_passed;
    private boolean interPassed;
    private String local;
    private int pin_code;
    private int regid;
    private String cast;
    private String sex;
    private String meritNumber;
    private String exservice;
    private String res_category;
    private FormFile pic;
    private int totalStrength;
    private int generalStrength;
    private int generalStrength_fill;
    private int generalStrength_vacant;
    private int reservationStrength;
    private int reservationStrength_fill;
    private int reservationStrength_vacant;
    private int reservationStrength_w;
    private int reservationStrength_fill_w;
    private int reservationStrength_vacant_w;
    private int generalStrength_w;
    private int generalStrength_fill_w;
    private int generalStrength_vacant_w;
    private int phcStrength;
    private int phcStrength_fill;
    private int phcStrength_vacant;
    private int phcStrength_w;
    private int phcStrength_fill_w;
    private int phcStrength_vacant_w;
    private int exserviceStrength;
    private int exserviceStrength_fill;
    private int exserviceStrength_vacant;
    private int exserviceStrength_w;
    private int exserviceStrength_fill_w;
    private int exserviceStrength_vacant_w;
    private int check;
    private int checkName;
    private String strength_vacants;

    public String getAdmitted() {
        return admitted;
    }

    public void setAdmitted(String admitted) {
        this.admitted = admitted;
    }

    

    public int getTotalStrength() {
        return totalStrength;
    }

    public String getStrength_fill() {
        return strength_fill;
    }

    public void setStrength_fill(String strength_fill) {
        this.strength_fill = strength_fill;
    }

    public String getStrength_vacant() {
        return strength_vacant;
    }

    public void setStrength_vacant(String strength_vacant) {
        this.strength_vacant = strength_vacant;
    }

    public void setTotalStrength(int totalStrength) {
        this.totalStrength = totalStrength;
    }

    public int getGeneralStrength() {
        return generalStrength;
    }

    public void setGeneralStrength(int generalStrength) {
        this.generalStrength = generalStrength;
    }

    public int getGeneralStrength_fill() {
        return generalStrength_fill;
    }

    public void setGeneralStrength_fill(int generalStrength_fill) {
        this.generalStrength_fill = generalStrength_fill;
    }

    public int getGeneralStrength_vacant() {
        return generalStrength_vacant;
    }

    public void setGeneralStrength_vacant(int generalStrength_vacant) {
        this.generalStrength_vacant = generalStrength_vacant;
    }

    public int getReservationStrength() {
        return reservationStrength;
    }

    public void setReservationStrength(int reservationStrength) {
        this.reservationStrength = reservationStrength;
    }

    public int getReservationStrength_fill() {
        return reservationStrength_fill;
    }

    public void setReservationStrength_fill(int reservationStrength_fill) {
        this.reservationStrength_fill = reservationStrength_fill;
    }

    public int getReservationStrength_vacant() {
        return reservationStrength_vacant;
    }

    public void setReservationStrength_vacant(int reservationStrength_vacant) {
        this.reservationStrength_vacant = reservationStrength_vacant;
    }

    public int getReservationStrength_w() {
        return reservationStrength_w;
    }

    public void setReservationStrength_w(int reservationStrength_w) {
        this.reservationStrength_w = reservationStrength_w;
    }

    public int getReservationStrength_fill_w() {
        return reservationStrength_fill_w;
    }

    public void setReservationStrength_fill_w(int reservationStrength_fill_w) {
        this.reservationStrength_fill_w = reservationStrength_fill_w;
    }

    public int getReservationStrength_vacant_w() {
        return reservationStrength_vacant_w;
    }

    public void setReservationStrength_vacant_w(int reservationStrength_vacant_w) {
        this.reservationStrength_vacant_w = reservationStrength_vacant_w;
    }

    public int getGeneralStrength_w() {
        return generalStrength_w;
    }

    public void setGeneralStrength_w(int generalStrength_w) {
        this.generalStrength_w = generalStrength_w;
    }

    public int getGeneralStrength_fill_w() {
        return generalStrength_fill_w;
    }

    public void setGeneralStrength_fill_w(int generalStrength_fill_w) {
        this.generalStrength_fill_w = generalStrength_fill_w;
    }

    public int getGeneralStrength_vacant_w() {
        return generalStrength_vacant_w;
    }

    public void setGeneralStrength_vacant_w(int generalStrength_vacant_w) {
        this.generalStrength_vacant_w = generalStrength_vacant_w;
    }

    public int getPhcStrength() {
        return phcStrength;
    }

    public void setPhcStrength(int phcStrength) {
        this.phcStrength = phcStrength;
    }

    public int getPhcStrength_fill() {
        return phcStrength_fill;
    }

    public void setPhcStrength_fill(int phcStrength_fill) {
        this.phcStrength_fill = phcStrength_fill;
    }

    public int getPhcStrength_vacant() {
        return phcStrength_vacant;
    }

    public void setPhcStrength_vacant(int phcStrength_vacant) {
        this.phcStrength_vacant = phcStrength_vacant;
    }

    public int getPhcStrength_w() {
        return phcStrength_w;
    }

    public void setPhcStrength_w(int phcStrength_w) {
        this.phcStrength_w = phcStrength_w;
    }

    public int getPhcStrength_fill_w() {
        return phcStrength_fill_w;
    }

    public void setPhcStrength_fill_w(int phcStrength_fill_w) {
        this.phcStrength_fill_w = phcStrength_fill_w;
    }

    public int getPhcStrength_vacant_w() {
        return phcStrength_vacant_w;
    }

    public void setPhcStrength_vacant_w(int phcStrength_vacant_w) {
        this.phcStrength_vacant_w = phcStrength_vacant_w;
    }

    public int getExserviceStrength() {
        return exserviceStrength;
    }

    public void setExserviceStrength(int exserviceStrength) {
        this.exserviceStrength = exserviceStrength;
    }

    public int getExserviceStrength_fill() {
        return exserviceStrength_fill;
    }

    public void setExserviceStrength_fill(int exserviceStrength_fill) {
        this.exserviceStrength_fill = exserviceStrength_fill;
    }

    public int getExserviceStrength_vacant() {
        return exserviceStrength_vacant;
    }

    public void setExserviceStrength_vacant(int exserviceStrength_vacant) {
        this.exserviceStrength_vacant = exserviceStrength_vacant;
    }

    public int getExserviceStrength_w() {
        return exserviceStrength_w;
    }

    public void setExserviceStrength_w(int exserviceStrength_w) {
        this.exserviceStrength_w = exserviceStrength_w;
    }

    public int getExserviceStrength_fill_w() {
        return exserviceStrength_fill_w;
    }

    public void setExserviceStrength_fill_w(int exserviceStrength_fill_w) {
        this.exserviceStrength_fill_w = exserviceStrength_fill_w;
    }

    public int getExserviceStrength_vacant_w() {
        return exserviceStrength_vacant_w;
    }

    public void setExserviceStrength_vacant_w(int exserviceStrength_vacant_w) {
        this.exserviceStrength_vacant_w = exserviceStrength_vacant_w;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public int getCheckName() {
        return checkName;
    }

    public void setCheckName(int checkName) {
        this.checkName = checkName;
    }

    public String getAcad_year() {
        return acad_year;
    }

    public void setAcad_year(String acad_year) {
        this.acad_year = acad_year;
    }

    public String getGpt() {
        return gpt;
    }

    public void setGpt(String gpt) {
        this.gpt = gpt;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getAdm_num() {
        return adm_num;
    }

    public void setAdm_num(String adm_num) {
        this.adm_num = adm_num;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getInter_passed() {
        return inter_passed;
    }

    public void setInter_passed(String inter_passed) {
        this.inter_passed = inter_passed;
    }

    public String getSsc_first_lang_marks_gpa() {
        return ssc_first_lang_marks_gpa;
    }

    public int getMerit_from() {
        return merit_from;
    }

    public void setMerit_from(int merit_from) {
        this.merit_from = merit_from;
    }

    public int getMerit_to() {
        return merit_to;
    }

    public void setMerit_to(int merit_to) {
        this.merit_to = merit_to;
    }

    public void setSsc_first_lang_marks_gpa(String ssc_first_lang_marks_gpa) {
        this.ssc_first_lang_marks_gpa = ssc_first_lang_marks_gpa;
    }

    public String getSsc_second_lang_marks_gpa() {
        return ssc_second_lang_marks_gpa;
    }

    public void setSsc_second_lang_marks_gpa(String ssc_second_lang_marks_gpa) {
        this.ssc_second_lang_marks_gpa = ssc_second_lang_marks_gpa;
    }

    public String getSsc_eng_marks_gpa() {
        return ssc_eng_marks_gpa;
    }

    public void setSsc_eng_marks_gpa(String ssc_eng_marks_gpa) {
        this.ssc_eng_marks_gpa = ssc_eng_marks_gpa;
    }

    public String getSsc_math_marks_gpa() {
        return ssc_math_marks_gpa;
    }

    public void setSsc_math_marks_gpa(String ssc_math_marks_gpa) {
        this.ssc_math_marks_gpa = ssc_math_marks_gpa;
    }

    public String getSsc_sci_marks_gpa() {
        return ssc_sci_marks_gpa;
    }

    public void setSsc_sci_marks_gpa(String ssc_sci_marks_gpa) {
        this.ssc_sci_marks_gpa = ssc_sci_marks_gpa;
    }

    public String getSsc_social_marks_gpa() {
        return ssc_social_marks_gpa;
    }

    public void setSsc_social_marks_gpa(String ssc_social_marks_gpa) {
        this.ssc_social_marks_gpa = ssc_social_marks_gpa;
    }

    public String getSsc_tot_marks() {
        return ssc_tot_marks;
    }

    public void setSsc_tot_marks(String ssc_tot_marks) {
        this.ssc_tot_marks = ssc_tot_marks;
    }

    public String getIti_code() {
        return iti_code;
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getFromd() {
        return fromd;
    }

    public void setFromd(String fromd) {
        this.fromd = fromd;
    }

    public String getTod() {
        return tod;
    }

    public void setTod(String tod) {
        this.tod = tod;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getPhc() {
        return phc;
    }

    public void setPhc(String phc) {
        this.phc = phc;
    }

    public String getExser() {
        return exser;
    }

    public void setExser(String exser) {
        this.exser = exser;
    }

    public String getSsc_first_lang_marks() {
        return ssc_first_lang_marks;
    }

    public void setSsc_first_lang_marks(String ssc_first_lang_marks) {
        this.ssc_first_lang_marks = ssc_first_lang_marks;
    }

    public String getSsc_second_lang_marks() {
        return ssc_second_lang_marks;
    }

    public void setSsc_second_lang_marks(String ssc_second_lang_marks) {
        this.ssc_second_lang_marks = ssc_second_lang_marks;
    }

    public String getSsc_eng_marks() {
        return ssc_eng_marks;
    }

    public void setSsc_eng_marks(String ssc_eng_marks) {
        this.ssc_eng_marks = ssc_eng_marks;
    }

    public String getSsc_math_marks() {
        return ssc_math_marks;
    }

    public void setSsc_math_marks(String ssc_math_marks) {
        this.ssc_math_marks = ssc_math_marks;
    }

    public String getSsc_sci_marks() {
        return ssc_sci_marks;
    }

    public void setSsc_sci_marks(String ssc_sci_marks) {
        this.ssc_sci_marks = ssc_sci_marks;
    }

    public String getSsc_social_marks() {
        return ssc_social_marks;
    }

    public void setSsc_social_marks(String ssc_social_marks) {
        this.ssc_social_marks = ssc_social_marks;
    }

    public String getInter_first_marks() {
        return inter_first_marks;
    }

    public void setInter_first_marks(String inter_first_marks) {
        this.inter_first_marks = inter_first_marks;
    }

    public String getInter_second_marks() {
        return inter_second_marks;
    }

    public void setInter_second_marks(String inter_second_marks) {
        this.inter_second_marks = inter_second_marks;
    }

    public String getInter_third_marks() {
        return inter_third_marks;
    }

    public void setInter_third_marks(String inter_third_marks) {
        this.inter_third_marks = inter_third_marks;
    }

    public String getInter_marks() {
        return inter_marks;
    }

    public void setInter_marks(String inter_marks) {
        this.inter_marks = inter_marks;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaname() {
        return faname;
    }

    public void setFaname(String faname) {
        this.faname = faname;
    }

    public String getTotalgp() {
        return totalgp;
    }

    public void setTotalgp(String totalgp) {
        this.totalgp = totalgp;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getManage_seats() {
        return manage_seats;
    }

    public void setManage_seats(String manage_seats) {
        this.manage_seats = manage_seats;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getIti_name() {
        return iti_name;
    }

    public void setIti_name(String iti_name) {
        this.iti_name = iti_name;
    }

    public String getFreeze() {
        return freeze;
    }

    public void setFreeze(String freeze) {
        this.freeze = freeze;
    }

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }

    public String getDist_code() {
        return dist_code;
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getEditoption() {
        return editoption;
    }

    public void setEditoption(String editoption) {
        this.editoption = editoption;
    }

    public String getTrade_name() {
        return trade_name;
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public String getTrade_code() {
        return trade_code;
    }

    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getStrength_vacants() {
        return strength_vacants;
    }

    public void setStrength_vacants(String strength_vacants) {
        this.strength_vacants = strength_vacants;
    }
   

    public String getTrade_short() {
        return trade_short;
    }

    public void setTrade_short(String trade_short) {
        this.trade_short = trade_short;
    }

    public String getAvailable() {
        return available;
    }

    public String getIdmarks1() {
        return idmarks1;
    }

    public void setIdmarks1(String idmarks1) {
        this.idmarks1 = idmarks1;
    }

    public String getIdmarks2() {
        return idmarks2;
    }

    public void setIdmarks2(String idmarks2) {
        this.idmarks2 = idmarks2;
    }

    public String getSsc_regno() {
        return ssc_regno;
    }

    public void setSsc_regno(String ssc_regno) {
        this.ssc_regno = ssc_regno;
    }

    public String getSsc_board() {
        return ssc_board;
    }

    public void setSsc_board(String ssc_board) {
        this.ssc_board = ssc_board;
    }

    public String getSsc_year() {
        return ssc_year;
    }

    public void setSsc_year(String ssc_year) {
        this.ssc_year = ssc_year;
    }

    public String getSsc_month() {
        return ssc_month;
    }

    public void setSsc_month(String ssc_month) {
        this.ssc_month = ssc_month;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAdarNo() {
        return adarNo;
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
        return local;
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

    public int getRegid() {
        return regid;
    }

    public void setRegid(int regid) {
        this.regid = regid;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMeritNumber() {
        return meritNumber;
    }

    public void setMeritNumber(String meritNumber) {
        this.meritNumber = meritNumber;
    }

    public String getExservice() {
        return exservice;
    }

    public void setExservice(String exservice) {
        this.exservice = exservice;
    }

    public String getRes_category() {
        return res_category;
    }

    public void setRes_category(String res_category) {
        this.res_category = res_category;
    }

    public FormFile getPic() {
        return pic;
    }

    public void setPic(FormFile pic) {
        this.pic = pic;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
