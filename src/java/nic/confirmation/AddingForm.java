 
package nic.confirmation;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
 
public class AddingForm extends org.apache.struts.action.ActionForm {

    private String trade_short;
    private String trade_name;
    String short_order;

    public String getShort_order() {
        return short_order;
    }

    public void setShort_order(String short_order) {
        this.short_order = short_order;
    }

    public String getTrade_short() {
        return beans.MyUtil.filterBad(trade_short);
    }

    public void setTrade_short(String trade_short) {
        this.trade_short = trade_short;
    }

    public String getTrade_name() {
        return beans.MyUtil.filterBad(trade_name);
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public AddingForm() {
        super();
        // TODO Auto-generated constructor stub
    }

     
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        return errors;
    }
}
