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
public class NewSubActionForm extends org.apache.struts.action.ActionForm {

    private String max_marks;

    public String getMax_marks() {
        return beans.MyUtil.filterBad(max_marks);
    }

    public void setMax_marks(String max_marks) {
        this.max_marks = max_marks;
    }
    private String subject_name;
    private String names;
    private String trade_name;

    public String getTrade_name() {
        return beans.MyUtil.filterBad(trade_name);
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public String getNames() {
        return beans.MyUtil.filterBad(names);
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSubject_name() {
        return beans.MyUtil.filterBad(subject_name);
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }
    private String subject_code;

    public String getSubject_code() {
        return beans.MyUtil.filterBad(subject_code);
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }
    private String subject_one;
    private String subject_two;
    private String subject_three;
    private String subject_four, trade_code;

    public String getTrade_code() {
        return beans.MyUtil.filterBad(trade_code);
    }

    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }

    public String getSubject_one() {
        return beans.MyUtil.filterBad(subject_one);
    }

    public void setSubject_one(String subject_one) {
        this.subject_one = subject_one;
    }

    public String getSubject_two() {
        return beans.MyUtil.filterBad(subject_two);
    }

    public void setSubject_two(String subject_two) {
        this.subject_two = subject_two;
    }

    public String getSubject_three() {
        return beans.MyUtil.filterBad(subject_three);
    }

    public void setSubject_three(String subject_three) {
        this.subject_three = subject_three;
    }

    public String getSubject_four() {
        return beans.MyUtil.filterBad(subject_four);
    }

    public void setSubject_four(String subject_four) {
        this.subject_four = subject_four;
    }

    public String getSubject_five() {
        return beans.MyUtil.filterBad(subject_five);
    }

    public void setSubject_five(String subject_five) {
        this.subject_five = subject_five;
    }
    private String subject_five;

     
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        System.out.println("UR in NewSubActionForm.java");
        ActionErrors errors = new ActionErrors();

    if(getTrade_name() == null || getTrade_name().length() <1){
        errors.add("trade_name", new ActionMessage("error.trade_name.required"));
    }
    
        return errors;
    }
}
