/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;


public class Edit_InterfaceForm extends org.apache.struts.action.ActionForm {
    private FormFile photo, imgqul, imgcaste,memo,ssccertificate,castecertificate,exservicecertificate,physicallycertificate;
String pwd_category,ews,dual_mode;

    public String getPwd_category() {
        return pwd_category;
    }

    public void setPwd_category(String pwd_category) {
        this.pwd_category = pwd_category;
    }

    public String getEws() {
        return ews;
    }

    public void setEws(String ews) {
        this.ews = ews;
    }

    public String getDual_mode() {
        return dual_mode;
    }

    public void setDual_mode(String dual_mode) {
        this.dual_mode = dual_mode;
    }
    public FormFile getSsccertificate() {
        return ssccertificate;
    }

    public void setSsccertificate(FormFile ssccertificate) {
        this.ssccertificate = ssccertificate;
    }

 

    public FormFile getCastecertificate() {
        return castecertificate;
    }

    public void setCastecertificate(FormFile castecertificate) {
        this.castecertificate = castecertificate;
    }

    public FormFile getExservicecertificate() {
        return exservicecertificate;
    }

    public void setExservicecertificate(FormFile exservicecertificate) {
        this.exservicecertificate = exservicecertificate;
    }

    public FormFile getPhysicallycertificate() {
        return physicallycertificate;
    }

    public void setPhysicallycertificate(FormFile physicallycertificate) {
        this.physicallycertificate = physicallycertificate;
    }
    private String iti_code, iti_name, exs, ossc, type,ssc_type,email;
    String[] addrs_state_p,addrs_district_p,addrs_mandal_p;

    public String[] getAddrs_state_p() {
        return addrs_state_p;
    }

    public void setAddrs_state_p(String[] addrs_state_p) {
        this.addrs_state_p = addrs_state_p;
    }

    public String[] getAddrs_district_p() {
        return addrs_district_p;
    }

    public void setAddrs_district_p(String[] addrs_district_p) {
        this.addrs_district_p = addrs_district_p;
    }

    public String[] getAddrs_mandal_p() {
        return addrs_mandal_p;
    }

    public void setAddrs_mandal_p(String[] addrs_mandal_p) {
        this.addrs_mandal_p = addrs_mandal_p;
    }

   
    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public MultipartRequestHandler getMultipartRequestHandler() {
        return multipartRequestHandler;
    }

    public void setMultipartRequestHandler(MultipartRequestHandler multipartRequestHandler) {
        this.multipartRequestHandler = multipartRequestHandler;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSsc_type() {
        return ssc_type;
    }

    public void setSsc_type(String ssc_type) {
        this.ssc_type = ssc_type;
    }
    private String ssc_regno, ssc_board, ssc_year, ssc_month;
    String name, ssc, tot_marks, tot_gpa, tot_grade;
    String first_lang_marks, first_lang_grade, first_lang_gpa, second_lang_marks, second_lang_grade, second_lang_gpa;
    String eng_marks, eng_grade, eng_gpa, maths_marks, maths_grade, maths_gpa;
    String science_marks, science_grade, science_gpa, social_marks, social_grade, social_gpa;
    String subject1_name, subject2_name, subject3_name;
    int subject1_marks, subject2_marks, subject3_marks;
    private String phc;
    private String group;

    public String getIns_code() {
        return ins_code;
    }

    public void setIns_code(String ins_code) {
        this.ins_code = ins_code;
    }
    private String ins_code;

    public String getSsc_regno() {
        return beans.MyUtil.filterBad(ssc_regno);
    }

    public FormFile getMemo() {
        return memo;
    }

    public void setMemo(FormFile memo) {
        this.memo = memo;
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



    public String getOssc() {
        return beans.MyUtil.filterBad(ossc);
    }

    public void setOssc(String ossc) {
        this.ossc = ossc;
    }

    public String getExs() {
        return beans.MyUtil.filterBad(exs);
    }

    public void setExs(String exs) {
        this.exs = exs;
    }

    public String getPhc() {
        return beans.MyUtil.filterBad(phc);
    }

    public void setPhc(String phc) {
        this.phc = phc;
    }

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getIti_name() {
        return beans.MyUtil.filterBad(iti_name);
    }

    public void setIti_name(String iti_name) {
        this.iti_name = iti_name;
    }

    public String getGroup() {
        return beans.MyUtil.filterBad(group);
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTot_marks() {
        return beans.MyUtil.filterBad(tot_marks);
    }

    public FormFile getPhoto() {
        return photo;
    }

    public void setPhoto(FormFile photo) {
        this.photo = photo;
    }

    public FormFile getImgqul() {
        return imgqul;
    }

    public void setImgqul(FormFile imgqul) {
        this.imgqul = imgqul;
    }

    public FormFile getImgcaste() {
        return imgcaste;
    }

    public void setImgcaste(FormFile imgcaste) {
        this.imgcaste = imgcaste;
    }

    public String getSubject1_name() {
        return beans.MyUtil.filterBad(subject1_name);
    }

    public void setSubject1_name(String subject1_name) {
        this.subject1_name = subject1_name;
    }

    public String getSubject2_name() {
        return beans.MyUtil.filterBad(subject2_name);
    }

    public void setSubject2_name(String subject2_name) {
        this.subject2_name = subject2_name;
    }

    public String getSubject3_name() {
        return beans.MyUtil.filterBad(subject3_name);
    }

    public void setSubject3_name(String subject3_name) {
        this.subject3_name = subject3_name;
    }

    public int getSubject1_marks() {
        return subject1_marks;
    }

    public void setSubject1_marks(int subject1_marks) {
        this.subject1_marks = subject1_marks;
    }

    public int getSubject2_marks() {
        return subject2_marks;
    }

    public void setSubject2_marks(int subject2_marks) {
        this.subject2_marks = subject2_marks;
    }

    public int getSubject3_marks() {
        return subject3_marks;
    }

    public void setSubject3_marks(int subject3_marks) {
        this.subject3_marks = subject3_marks;
    }

    public void setTot_marks(String tot_marks) {
        this.tot_marks = tot_marks;
    }

    public String getTot_gpa() {
        return beans.MyUtil.filterBad(tot_gpa);
    }

    public void setTot_gpa(String tot_gpa) {
        this.tot_gpa = tot_gpa;
    }

    public String getTot_grade() {
        return beans.MyUtil.filterBad(tot_grade);
    }

    public void setTot_grade(String tot_grade) {
        this.tot_grade = tot_grade;
    }

    public String getFirst_lang_marks() {
        return beans.MyUtil.filterBad(first_lang_marks);
    }

    public void setFirst_lang_marks(String first_lang_marks) {
        this.first_lang_marks = first_lang_marks;
    }

    public String getFirst_lang_grade() {
        return beans.MyUtil.filterBad(first_lang_grade);
    }

    public void setFirst_lang_grade(String first_lang_grade) {
        this.first_lang_grade = first_lang_grade;
    }

    public String getFirst_lang_gpa() {
        return beans.MyUtil.filterBad(first_lang_gpa);
    }

    public void setFirst_lang_gpa(String first_lang_gpa) {
        this.first_lang_gpa = first_lang_gpa;
    }

    public String getSecond_lang_marks() {
        return beans.MyUtil.filterBad(second_lang_marks);
    }

    public void setSecond_lang_marks(String second_lang_marks) {
        this.second_lang_marks = second_lang_marks;
    }

    public String getSecond_lang_grade() {
        return beans.MyUtil.filterBad(second_lang_grade);
    }

    public void setSecond_lang_grade(String second_lang_grade) {
        this.second_lang_grade = second_lang_grade;
    }

    public String getSecond_lang_gpa() {
        return beans.MyUtil.filterBad(second_lang_gpa);
    }

    public void setSecond_lang_gpa(String second_lang_gpa) {
        this.second_lang_gpa = second_lang_gpa;
    }

    public String getEng_marks() {
        return beans.MyUtil.filterBad(eng_marks);
    }

    public void setEng_marks(String eng_marks) {
        this.eng_marks = eng_marks;
    }

    public String getEng_grade() {
        return beans.MyUtil.filterBad(eng_grade);
    }

    public void setEng_grade(String eng_grade) {
        this.eng_grade = eng_grade;
    }

    public String getEng_gpa() {
        return beans.MyUtil.filterBad(eng_gpa);
    }

    public void setEng_gpa(String eng_gpa) {
        this.eng_gpa = eng_gpa;
    }

    public String getMaths_marks() {
        return beans.MyUtil.filterBad(maths_marks);
    }

    public void setMaths_marks(String maths_marks) {
        this.maths_marks = maths_marks;
    }

    public String getMaths_grade() {
        return beans.MyUtil.filterBad(maths_grade);
    }

    public void setMaths_grade(String maths_grade) {
        this.maths_grade = maths_grade;
    }

    public String getMaths_gpa() {
        return beans.MyUtil.filterBad(maths_gpa);
    }

    public void setMaths_gpa(String maths_gpa) {
        this.maths_gpa = maths_gpa;
    }

    public String getScience_marks() {
        return beans.MyUtil.filterBad(science_marks);
    }

    public void setScience_marks(String science_marks) {
        this.science_marks = science_marks;
    }

    public String getScience_grade() {
        return beans.MyUtil.filterBad(science_grade);
    }

    public void setScience_grade(String science_grade) {
        this.science_grade = science_grade;
    }

    public String getScience_gpa() {
        return beans.MyUtil.filterBad(science_gpa);
    }

    public void setScience_gpa(String science_gpa) {
        this.science_gpa = science_gpa;
    }

    public String getSocial_marks() {
        return beans.MyUtil.filterBad(social_marks);
    }

    public void setSocial_marks(String social_marks) {
        this.social_marks = social_marks;
    }

    public String getSocial_grade() {
        return beans.MyUtil.filterBad(social_grade);
    }

    public void setSocial_grade(String social_grade) {
        this.social_grade = social_grade;
    }

    public String getSocial_gpa() {
        return beans.MyUtil.filterBad(social_gpa);
    }

    public void setSocial_gpa(String social_gpa) {
        this.social_gpa = social_gpa;
    }

    public String getSsc() {
        return beans.MyUtil.filterBad(ssc);
    }

    public void setSsc(String ssc) {
        this.ssc = ssc;
    }

    public String getName() {
        return beans.MyUtil.filterBad(name);
    }

    public void setName(String name) {
        this.name = name;
    }
    String fname;

    public String getFname() {
        return beans.MyUtil.filterBad(fname);
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
    String addr;

    public String getAddr() {
        return beans.MyUtil.filterBad(addr);
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
    String pincode;

    public String getPincode() {
        return beans.MyUtil.filterBad(pincode);
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
    String mname;

    public String getMname() {
        return beans.MyUtil.filterBad(mname);
    }

    public void setMname(String mname) {
        this.mname = mname;
    }
    String phno;

    public String getPhno() {
        return beans.MyUtil.filterBad(phno);
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }
    String adarno;

    public String getAdarno() {
        return beans.MyUtil.filterBad(adarno);
    }

    public void setAdarno(String adarno) {
        this.adarno = adarno;
    }
    String dob;

    public String getDob() {
        return beans.MyUtil.filterBad(dob);
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
    String gen;

    public String getGen() {
        return beans.MyUtil.filterBad(gen);
    }

    public void setGen(String gen) {
        this.gen = gen;
    }
    String gen1;

    public String getGen1() {
        return beans.MyUtil.filterBad(gen1);
    }

    public void setGen1(String gen1) {
        this.gen1 = gen1;
    }
    String caste;

    public String getCaste() {
        return beans.MyUtil.filterBad(caste);
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }
    String qual;

    public String getQual() {
        return beans.MyUtil.filterBad(qual);
    }

    public void setQual(String qual) {
        this.qual = qual;
    }
    String image;

    public String getImage() {
        return beans.MyUtil.filterBad(image);
    }

    public void setImage(String image) {
        this.image = image;
    }
    String inter;

    public String getInter() {
        return beans.MyUtil.filterBad(inter);
    }

    public void setInter(String inter) {
        this.inter = inter;
    }
    String intmarks;

    public String getIntmarks() {
        return beans.MyUtil.filterBad(intmarks);
    }

    public void setIntmarks(String intmarks) {
        this.intmarks = intmarks;
    }
    String loconoc;

    public String getLoconoc() {
        return beans.MyUtil.filterBad(loconoc);
    }

    public void setLoconoc(String loconoc) {
        this.loconoc = loconoc;
    }

    

    
}
