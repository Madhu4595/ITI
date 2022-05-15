package com.nic;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class TradeForm extends org.apache.struts.action.ActionForm {

    private String trade_code;
    private String trade_short;
    private String trade_name;

    public TradeForm() {
        super();
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public String getTrade_name() {
        return beans.MyUtil.filterBad(trade_name);
    }

    public void setTrade_short(String trade_short) {
        this.trade_short = trade_short;
    }

    public String getTrade_short() {
        return beans.MyUtil.filterBad(trade_short);
    }

    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }

    public String getTrade_code() {
        return beans.MyUtil.filterBad(trade_code);
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        return null;
    }
}
