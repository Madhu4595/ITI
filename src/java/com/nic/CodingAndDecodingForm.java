/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import DBC.Validations;

/**
 *
 * @author SUBBAREDDY
 */
public class CodingAndDecodingForm extends org.apache.struts.action.ActionForm {

    private String[] adm_num;
    private Integer[] paper_code;
    private String dist_code;
    private String iti_code;
    private Integer trade_code;
    private String subject_code;
    private Integer marks;

    public String[] getAdm_num() {
        return adm_num;
    }

    public void setAdm_num(String[] adm_num) {
        this.adm_num = adm_num;
    }

    public Integer[] getPaper_code() {
        return paper_code;
    }

    public void setPaper_code(Integer[] paper_code) {
        this.paper_code = paper_code;
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

    public Integer getTrade_code() {
        return trade_code;
    }

    public void setTrade_code(Integer trade_code) {
        this.trade_code = trade_code;
    }

    public String getSubject_code() {
        return beans.MyUtil.filterBad(subject_code);
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        int paper_code_length = paper_code.length;
        try {
            if (paper_code == null || paper_code_length < 1) {
                errors.add("paper_code", new ActionMessage("CodingAndDecodingForm.dist_code.required"));
            } else {
                if (paper_code_length > 1) {
                    for (int i = 0; i < paper_code_length; i++) {
                        if (paper_code[i] < 1) {
                            errors.add("paper_code", new ActionMessage("CodingAndDecodingForm.dist_code.required"));
                        } else if (!Validations.Validate(paper_code[i].toString(), 2)) {
                            errors.add("paper_code", new ActionMessage("CodingAndDecodingForm.dist_code.invalid"));
                        } else if (paper_code[i] > 99999) {
                            errors.add("paper_code", new ActionMessage("CodingAndDecodingForm.dist_code.length"));
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return errors;
    }
}
