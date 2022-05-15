/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author SUBBAREDDY
 */
public class ItiAdmissionProcessForm extends org.apache.struts.action.ActionForm {

    private Date currentDate;
    private Integer merit_from;
    private Integer merit_to;
    private String rank, caste_perform, qual_perform, adm_perform;

    public String getAdm_perform() {
        return beans.MyUtil.filterBad(adm_perform);
    }

    public void setAdm_perform(String adm_perform) {
        this.adm_perform = adm_perform;
    }

    public String getCaste_perform() {
        return beans.MyUtil.filterBad(caste_perform);
    }

    public void setCaste_perform(String caste_perform) {
        this.caste_perform = caste_perform;
    }

    public String getQual_perform() {
        return beans.MyUtil.filterBad(qual_perform);
    }

    public void setQual_perform(String qual_perform) {
        this.qual_perform = qual_perform;
    }

    public ItiAdmissionProcessForm() {
        super();
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return beans.MyUtil.filterBad(rank);
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setMerit_from(Integer merit_from) {
        this.merit_from = merit_from;
    }

    public Integer getMerit_from() {
        return merit_from;
    }

    public void setMerit_to(Integer merit_to) {
        this.merit_to = merit_to;
    }

    public Integer getMerit_to() {
        return merit_to;
    }

    /**
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        //System.out.println("---------------------------------------------In Form Bean" + rank);
        //System.out.println("---------------------------------------------In Form Bean" + currentDate);
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
