/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.confirmation;

import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author theru hareesh
 */
public class ConfirmationFormBean extends org.apache.struts.action.ActionForm {

    private int ssc_first_lang_marks, ssc_first_lang_gpa, ssc_second_lang_marks,
            ssc_second_lang_gpa, ssc_eng_marks, ssc_eng_gpa, ssc_math_marks, ssc_math_gpa, ssc_sci_marks, ssc_sci_gpa, ssc_social_marks,
            ssc_social_gpa, ssc_tot_marks, ssc_tot_gpa;

    public int getSsc_first_lang_marks() {
        return ssc_first_lang_marks;
    }

    public void setSsc_first_lang_marks(int ssc_first_lang_marks) {
        this.ssc_first_lang_marks = ssc_first_lang_marks;
    }

    public int getSsc_first_lang_gpa() {
        return ssc_first_lang_gpa;
    }

    public void setSsc_first_lang_gpa(int ssc_first_lang_gpa) {
        this.ssc_first_lang_gpa = ssc_first_lang_gpa;
    }

    public int getSsc_second_lang_marks() {
        return ssc_second_lang_marks;
    }

    public void setSsc_second_lang_marks(int ssc_second_lang_marks) {
        this.ssc_second_lang_marks = ssc_second_lang_marks;
    }

    public int getSsc_second_lang_gpa() {
        return ssc_second_lang_gpa;
    }

    public void setSsc_second_lang_gpa(int ssc_second_lang_gpa) {
        this.ssc_second_lang_gpa = ssc_second_lang_gpa;
    }

    public int getSsc_eng_marks() {
        return ssc_eng_marks;
    }

    public void setSsc_eng_marks(int ssc_eng_marks) {
        this.ssc_eng_marks = ssc_eng_marks;
    }

    public int getSsc_eng_gpa() {
        return ssc_eng_gpa;
    }

    public void setSsc_eng_gpa(int ssc_eng_gpa) {
        this.ssc_eng_gpa = ssc_eng_gpa;
    }

    public int getSsc_math_marks() {
        return ssc_math_marks;
    }

    public void setSsc_math_marks(int ssc_math_marks) {
        this.ssc_math_marks = ssc_math_marks;
    }

    public int getSsc_math_gpa() {
        return ssc_math_gpa;
    }

    public void setSsc_math_gpa(int ssc_math_gpa) {
        this.ssc_math_gpa = ssc_math_gpa;
    }

    public int getSsc_sci_marks() {
        return ssc_sci_marks;
    }

    public void setSsc_sci_marks(int ssc_sci_marks) {
        this.ssc_sci_marks = ssc_sci_marks;
    }

    public int getSsc_sci_gpa() {
        return ssc_sci_gpa;
    }

    public void setSsc_sci_gpa(int ssc_sci_gpa) {
        this.ssc_sci_gpa = ssc_sci_gpa;
    }

    public int getSsc_social_marks() {
        return ssc_social_marks;
    }

    public void setSsc_social_marks(int ssc_social_marks) {
        this.ssc_social_marks = ssc_social_marks;
    }

    public int getSsc_social_gpa() {
        return ssc_social_gpa;
    }

    public void setSsc_social_gpa(int ssc_social_gpa) {
        this.ssc_social_gpa = ssc_social_gpa;
    }

    public int getSsc_tot_marks() {
        return ssc_tot_marks;
    }

    public void setSsc_tot_marks(int ssc_tot_marks) {
        this.ssc_tot_marks = ssc_tot_marks;
    }

    public int getSsc_tot_gpa() {
        return ssc_tot_gpa;
    }

    public void setSsc_tot_gpa(int ssc_tot_gpa) {
        this.ssc_tot_gpa = ssc_tot_gpa;
    }

    public String getSsc_first_lang_grade() {
        return ssc_first_lang_grade;
    }

    public void setSsc_first_lang_grade(String ssc_first_lang_grade) {
        this.ssc_first_lang_grade = ssc_first_lang_grade;
    }

    public String getSsc_second_lang_grade() {
        return ssc_second_lang_grade;
    }

    public void setSsc_second_lang_grade(String ssc_second_lang_grade) {
        this.ssc_second_lang_grade = ssc_second_lang_grade;
    }

    public String getSsc_eng_grade() {
        return ssc_eng_grade;
    }

    public void setSsc_eng_grade(String ssc_eng_grade) {
        this.ssc_eng_grade = ssc_eng_grade;
    }

    public String getSsc_math_grade() {
        return ssc_math_grade;
    }

    public void setSsc_math_grade(String ssc_math_grade) {
        this.ssc_math_grade = ssc_math_grade;
    }

    public String getSsc_sci_grade() {
        return ssc_sci_grade;
    }

    public void setSsc_sci_grade(String ssc_sci_grade) {
        this.ssc_sci_grade = ssc_sci_grade;
    }

    public String getSsc_social_grade() {
        return ssc_social_grade;
    }

    public void setSsc_social_grade(String ssc_social_grade) {
        this.ssc_social_grade = ssc_social_grade;
    }
    private String ssc_first_lang_grade, ssc_second_lang_grade, ssc_eng_grade, ssc_math_grade, ssc_sci_grade, ssc_social_grade;
    private int reg_id;
    private int iti_code;

    public int getIti_code() {
        return iti_code;
    }

    public void setIti_code(int iti_code) {
        this.iti_code = iti_code;
    }
    private String verified;

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }
    public String verified_qual;

    public String getVerified_qual() {
        return verified_qual;
    }

    public void setVerified_qual(String verified_qual) {
        this.verified_qual = verified_qual;
    }
    private int int_marks;
    public String verified_ssc;

    public int getInt_marks() {
        return int_marks;
    }

    public String getVerified_ssc() {
        return verified_ssc;
    }

    public void setVerified_ssc(String verified_ssc) {
        this.verified_ssc = verified_ssc;
    }

    public void setInt_marks(int int_marks) {
        this.int_marks = int_marks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    private String name;
    private String father_name;
    private String dob;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
    private String qualification;
    private int marks;
    private String category;

    public int getReg_id() {
        return reg_id;
    }

    public void setReg_id(int reg_id) {
        this.reg_id = reg_id;

    }
 
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        return errors;
    }
}
