/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admissions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author GOPI
 */
public class Select_tradeForm extends org.apache.struts.action.ActionForm {

    String select_trade, regid, rank, type_admission;

    public String getType_admission() {
        return beans.MyUtil.filterBad(type_admission);
    }

    public void setType_admission(String type_admission) {
        this.type_admission = type_admission;
    }

    public String getRegid() {
        return beans.MyUtil.filterBad(regid);
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public String getRank() {
        return beans.MyUtil.filterBad(rank);
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSelect_trade() {
        return beans.MyUtil.filterBad(select_trade);
    }

    public void setSelect_trade(String select_trade) {
        this.select_trade = select_trade;
    }

    public Select_tradeForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        System.out.println("UR in Select_tradeForm.java");
        ActionErrors errors = new ActionErrors();
        return errors;
    }
}
