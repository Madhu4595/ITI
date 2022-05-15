/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admissions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Ramesh Naidu
 */
public class Admission_Insertion_ResultForm extends org.apache.struts.action.ActionForm {

    private String adm_num, name, fname, mname, addr, phno, iti_code, trade_code, type_admission, mode_adm, gender, dist_code, pincode, idmarks1, idmarks2, category_code;
    private String loconoc, group, intmarks, ssc_regno, ssc_board, ssc_year, ssc_month,caste;
//private String first_lang_marks,first_lang_grade,first_lang_gpa,second_lang_marks,second_lang_grade,second_lang_gpa,subject1_name,subject2_name,subject3_name,subject1_marks,subject2_marks,subject3_marks;
    //private String eng_marks,eng_grade,eng_gpa,maths_marks,maths_grade,maths_gpa,science_marks,science_grade,science_gpa;
//private String social_marks,social_grade,social_gpa,tot_marks,tot_grade,tot_gpa,ossc;
//Date fromd_training,tod_training;
    boolean phc, exservice, ssc, inter;
    private FormFile photo;
    String dob, doa, iti_type;

    public String getIti_type() {
        return beans.MyUtil.filterBad(iti_type);
    }

    public String getCaste() {
        return beans.MyUtil.filterBad(caste);
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public void setIti_type(String iti_type) {
        this.iti_type = iti_type;
    }

    String totalgpa, totalmarks;

    public String getTotalgpa() {
        return beans.MyUtil.filterBad(totalgpa);
    }

    public void setTotalgpa(String totalgpa) {
        this.totalgpa = totalgpa;
    }

    public String getTotalmarks() {
        return beans.MyUtil.filterBad(totalmarks);
    }

    public void setTotalmarks(String totalmarks) {
        this.totalmarks = totalmarks;
    }

    public String getDoa() {
        return beans.MyUtil.filterBad(doa);
    }

    public void setDoa(String doa) {
        this.doa = doa;
    }

    public FormFile getPhoto() {
        return photo;
    }

    public void setPhoto(FormFile photo) {
        this.photo = photo;
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

    public String getLoconoc() {
        return beans.MyUtil.filterBad(loconoc);
    }

    public void setLoconoc(String loconoc) {
        this.loconoc = loconoc;
    }

    public String getGroup() {
        return beans.MyUtil.filterBad(group);
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getIntmarks() {
        return beans.MyUtil.filterBad(intmarks);
    }

    public void setIntmarks(String intmarks) {
        this.intmarks = intmarks;
    }

    public boolean isPhc() {
        return phc;
    }

    public void setPhc(boolean phc) {
        this.phc = phc;
    }

    public boolean isExservice() {
        return exservice;
    }

    public void setExservice(boolean exservice) {
        this.exservice = exservice;
    }

    public boolean isSsc() {
        return ssc;
    }

    public void setSsc(boolean ssc) {
        this.ssc = ssc;
    }

    public boolean isInter() {
        return inter;
    }

    public void setInter(boolean inter) {
        this.inter = inter;
    }

    public String getCategory_code() {
        return beans.MyUtil.filterBad(category_code);
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getAdm_num() {
        return beans.MyUtil.filterBad(adm_num);
    }

    public String getDob() {
        return beans.MyUtil.filterBad(dob);
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setAdm_num(String adm_num) {
        this.adm_num = adm_num;
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

    public String getMname() {
        return beans.MyUtil.filterBad(mname);
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getAddr() {
        return beans.MyUtil.filterBad(addr);
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhno() {
        return beans.MyUtil.filterBad(phno);
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getTrade_code() {
        return beans.MyUtil.filterBad(trade_code);
    }

    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }

    public String getType_admission() {
        return beans.MyUtil.filterBad(type_admission);
    }

    public void setType_admission(String type_admission) {
        this.type_admission = type_admission;
    }

    public String getMode_adm() {
        return beans.MyUtil.filterBad(mode_adm);
    }

    public void setMode_adm(String mode_adm) {
        this.mode_adm = mode_adm;
    }

    public String getGender() {
        return beans.MyUtil.filterBad(gender);
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getPincode() {
        return beans.MyUtil.filterBad(pincode);
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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

    public Admission_Insertion_ResultForm() {
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
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        System.out.println("UR in Admission_Insertion_ResultForm.java");
        
        ActionErrors errors = new ActionErrors();
       
        if (getName() == null || getName().length() < 1) {
            errors.add("name", new ActionMessage("error.name.required"));
        }
        if (getFname() == null || getFname().length() < 1) {
            errors.add("fname", new ActionMessage("error.fname.required"));
        }
        if (getMname() == null || getMname().length() < 1) {
            errors.add("mname", new ActionMessage("error.mname.required"));
        }
        if (getAddr() == null || getAddr().length() < 1) {
            errors.add("addr", new ActionMessage("error.addr.required"));
        }
        if (getType_admission() == null || getType_admission().length() < 1) {
            errors.add("typeadmission", new ActionMessage("error.typeadmission.required"));
        }
        if (getMode_adm() == null || getMode_adm().length() < 1) {
            errors.add("modeadmission", new ActionMessage("error.modeadmission.required"));
        }
        if (getIti_code() == null || getIti_code().length() < 1) {
            errors.add("iti_code", new ActionMessage("error.iti_code.required"));
        }
        if (getTrade_code() == null || getTrade_code().length() < 1) {
            errors.add("trade_code", new ActionMessage("error.trade_code.required"));
        }
        if (getGender() == null || getGender().length() < 1) {
            errors.add("gender", new ActionMessage("error.gender.required"));
        }
        if (getDist_code() == null || getDist_code().length() < 1) {
            errors.add("dist_code", new ActionMessage("error.dist_code.required"));
        }
        if (getCategory_code() == null || getCategory_code().length() < 1) {
            errors.add("category_code", new ActionMessage("error.category_code.required"));
        }
        if (getLoconoc() == null || getLoconoc().length() < 1) {
            errors.add("local", new ActionMessage("error.local.required"));
        }
        if (getSsc_regno() == null || getSsc_regno().length() < 1) {
            errors.add("regno", new ActionMessage("error.regno.required"));
        }
        if (getSsc_board() == null || getSsc_board().length() < 1) {
            errors.add("sscboard", new ActionMessage("error.sscboard.required"));
        }
        if (getSsc_year() == null || getSsc_year().length() < 1) {
            errors.add("sscyear", new ActionMessage("error.sscyear.required"));
        }
        if (getSsc_month() == null || getSsc_month().length() < 1) {
            errors.add("sscmonth", new ActionMessage("error.sscmonth.required"));
        }
        if (getPincode() == null || getPincode().length() < 1) {
            errors.add("pincode", new ActionMessage("error.pincode.required"));
        }
        if (getIdmarks1() == null || getIdmarks1().length() < 1) {
            errors.add("idmarks1", new ActionMessage("error.idmarks1.required"));
        }
        if (getTotalmarks() == null || getTotalmarks().length() < 1) {
            errors.add("totalmarks", new ActionMessage("error.totalmarks.required"));
        }
        if (getTotalgpa() == null || getTotalgpa().length() < 1) {
            errors.add("totalgpa", new ActionMessage("error.totalgpa.required"));
        } 
        

        return errors;
    }
}
