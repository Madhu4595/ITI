/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraceMarks;
import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
/**
/**
 *
 * @author shobha
 */
public class semester4_GraceMarksForm  extends org.apache.struts.action.ActionForm {
    String  int_marks, tot_marks, result, final_result,ext_marks,htno,paper_result,paper_tot_marks,sem_paper_result,sem_paper_finalresult,sem_finalresult;

    public String getInt_marks() {
        return int_marks;
    }

    public void setInt_marks(String int_marks) {
        this.int_marks = int_marks;
    }

    public String getTot_marks() {
        return tot_marks;
    }

    public void setTot_marks(String tot_marks) {
        this.tot_marks = tot_marks;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFinal_result() {
        return final_result;
    }

    public void setFinal_result(String final_result) {
        this.final_result = final_result;
    }

    public String getExt_marks() {
        return ext_marks;
    }

    public void setExt_marks(String ext_marks) {
        this.ext_marks = ext_marks;
    }

    public String getHtno() {
        return htno;
    }

    public void setHtno(String htno) {
        this.htno = htno;
    }

    public String getPaper_result() {
        return paper_result;
    }

    public void setPaper_result(String paper_result) {
        this.paper_result = paper_result;
    }

    public String getPaper_tot_marks() {
        return paper_tot_marks;
    }

    public void setPaper_tot_marks(String paper_tot_marks) {
        this.paper_tot_marks = paper_tot_marks;
    }

    public String getSem_paper_result() {
        return sem_paper_result;
    }

    public void setSem_paper_result(String sem_paper_result) {
        this.sem_paper_result = sem_paper_result;
    }

    public String getSem_paper_finalresult() {
        return sem_paper_finalresult;
    }

    public void setSem_paper_finalresult(String sem_paper_finalresult) {
        this.sem_paper_finalresult = sem_paper_finalresult;
    }

    public String getSem_finalresult() {
        return sem_finalresult;
    }

    public void setSem_finalresult(String sem_finalresult) {
        this.sem_finalresult = sem_finalresult;
    }
 public semester4_GraceMarksForm()
{
super();
}
 public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        /*if (getName() == null || getName().length() < 1) {
         errors.add("name", new ActionMessage("error.name.required"));
         // TODO: add 'error.name.required' key to your resources
         }*/
        return errors;
    }
    
}
