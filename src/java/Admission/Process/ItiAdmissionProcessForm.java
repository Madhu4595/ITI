/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admission.Process;

import java.sql.Date;

/**
 *
 * @author GOPI
 */
public class ItiAdmissionProcessForm extends org.apache.struts.action.ActionForm {

    private Date currentDate;
    private Integer merit_from;
    private Integer merit_to;
    private String rank;

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Integer getMerit_from() {
        return merit_from;
    }

    public void setMerit_from(Integer merit_from) {
        this.merit_from = merit_from;
    }

    public Integer getMerit_to() {
        return merit_to;
    }

    public void setMerit_to(Integer merit_to) {
        this.merit_to = merit_to;
    }

    public String getRank() {
        return beans.MyUtil.filterBad(rank);
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public ItiAdmissionProcessForm() {
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
}
