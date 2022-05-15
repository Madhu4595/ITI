/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.confirmation;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author theru hareesh
 */
public class TradeUpdateForm extends org.apache.struts.action.ActionForm {

    private String trade_name;
    private String trade_short;
    private String short_order;

    public String getShort_order() {
        return short_order;
    }

    public void setShort_order(String short_order) {
        this.short_order = short_order;
    }

    public String getTrade_name() {
        return beans.MyUtil.filterBad(trade_name);
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public String getTrade_short() {
        return beans.MyUtil.filterBad(trade_short);
    }

    public void setTrade_short(String trade_short) {
        this.trade_short = trade_short;
    }

    public int getTrade_code() {
        return trade_code;
    }

    public void setTrade_code(int trade_code) {
        this.trade_code = trade_code;
    }
    private int trade_code;

    /**
     * @param mapping
     * @param request
     * @return
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        
        ActionErrors errors = new ActionErrors();
        
        //trade_name
        if (getTrade_name() == null || getTrade_name().equalsIgnoreCase("")) {
            errors.add("trade_name", new ActionMessage("trade_name.required"));
        }
//        else if(!Validations.Validate(getTrade_name(),  3)){
//            errors.add("trade_name", new ActionMessage("error.trade_name.notmatch"));
//        
//        }
        //short_order
        if (getShort_order()== null || getShort_order().equalsIgnoreCase("")) {
            errors.add("short_order", new ActionMessage("short_order.required"));
        }else if(!Validations.Validate(getShort_order(),  2)){
            errors.add("short_order", new ActionMessage("short_order.notmatch"));
        
        }
        
        return errors;
    }
}
