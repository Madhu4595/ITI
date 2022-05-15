/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.confirmation;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author theru hareesh
 */
public class SubjectForm extends org.apache.struts.action.ActionForm {

//String max_marks;
    public SubjectForm() {
        super();
         
    }
    private int[] max_marks;
    private String[] names;
    private String subject_name;
    private String subject_code;
    private String trade_code;

    public int[] getMax_marks() {
        return max_marks;
    }

    public void setMax_marks(int[] max_marks) {
        this.max_marks = max_marks;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String getSubject_name() {
        return beans.MyUtil.filterBad(subject_name);
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getSubject_code() {
        return beans.MyUtil.filterBad(subject_code);
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getTrade_code() {
        return beans.MyUtil.filterBad(trade_code);
    }

    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        System.out.println("UR in SubjectForm.java");
        
        ActionErrors errors = new ActionErrors();
         
        return errors;

    }
}
