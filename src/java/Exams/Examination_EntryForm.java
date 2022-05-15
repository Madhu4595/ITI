/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exams;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Gopi Daggumalle
 */
public class Examination_EntryForm extends org.apache.struts.action.ActionForm {

    private String dist_code, iti_code, trade_code, subject_code, type;
    private String[] hallticket_no;
    private String[] int_marks, ext_marks;
    private String[] paper_code;
    private int[] theory_marks;
    private int[] practical_marks;
    private String type_admission;
    private String sem_nonsem;
    private String regorprivate, year, month,sem_no;

    public String getSem_no() {
        return sem_no;
    }

    public void setSem_no(String sem_no) {
        this.sem_no = sem_no;
    }

    
    
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getRegorprivate() {
        return beans.MyUtil.filterBad(regorprivate);
    }

    public void setRegorprivate(String regorprivate) {
        this.regorprivate = regorprivate;
    }

    public String[] getExt_marks() {
        return ext_marks;
    }

    public String getType_admission() {
        return beans.MyUtil.filterBad(type_admission);
    }

    public void setType_admission(String type_admission) {
        this.type_admission = type_admission;
    }

    public String getSem_nonsem() {
        return beans.MyUtil.filterBad(sem_nonsem);
    }

    public void setSem_nonsem(String sem_nonsem) {
        this.sem_nonsem = sem_nonsem;
    }

    public void setExt_marks(String[] ext_marks) {
        this.ext_marks = ext_marks;
    }

    public String[] getInt_marks() {
        return int_marks;
    }

    public void setInt_marks(String[] int_marks) {
        this.int_marks = int_marks;
    }

    public String[] getHallticket_no() {
        return hallticket_no;
    }

    public void setHallticket_no(String[] hallticket_no) {
        this.hallticket_no = hallticket_no;
    }

    public String[] getPaper_code() {
        return paper_code;
    }

    public void setPaper_code(String[] paper_code) {
        this.paper_code = paper_code;
    }

    public int[] getTheory_marks() {
        return theory_marks;
    }

    public void setTheory_marks(int[] theory_marks) {
        this.theory_marks = theory_marks;
    }

    public int[] getPractical_marks() {
        return practical_marks;
    }

    public void setPractical_marks(int[] practical_marks) {
        this.practical_marks = practical_marks;
    }

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
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

    public String getSubject_code() {
        return beans.MyUtil.filterBad(subject_code);
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getType() {
        return beans.MyUtil.filterBad(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public Examination_EntryForm() {
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
        if (DBC.DBConnection.RoleAuthentication(getDist_code(), request) && DBC.DBConnection.RoleAuthentication(getIti_code(), request)) {
            if (getDist_code() == null || getDist_code().length() < 1) {
                errors.add("dist_code", new ActionMessage("RegistrationForm.dist_code.required"));
            } else if (!Validations.Validate(getDist_code(), 2)) {
                errors.add("dist_code", new ActionMessage("error.dist_code.notmatch"));
            }

            if (getIti_code() == null || getIti_code().length() < 1) {
                errors.add("iti_code", new ActionMessage("error.iti_code.required"));
            } else if (!Validations.Validate(getIti_code(), 2)) {
                errors.add("iti_code", new ActionMessage("error.iti_code.notmatch"));
            }

            if (getTrade_code() == null || getTrade_code().length() < 1) {
                errors.add("trade_code", new ActionMessage("error.trade_code.required"));
            } else if (!Validations.Validate(getTrade_code(), 2)) {
                errors.add("trade_code", new ActionMessage("error.trade_code.notmatch"));
            }

            if (getSubject_code() == null || getSubject_code().length() < 1) {
                errors.add("subject_code", new ActionMessage("error.subject_code.required"));
            } else if (!Validations.Validate(getSubject_code(), 6)) {
                //errors.add("subject_code", new ActionMessage("error.subject_code.notmatch"));
            }

            if (getType() == null || getType().length() < 1) {
                errors.add("type", new ActionMessage("error.type.required"));
            } else if (!Validations.Validate(getType(), 6)) {
                errors.add("type", new ActionMessage("error.type.notmatch"));
            }
        } else {
            //errors.add(dist_code, new ActionMessage("error.regid.otherdist"));
        }
        return errors;
    }
}
