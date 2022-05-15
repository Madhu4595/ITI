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
public class AddSubForm extends org.apache.struts.action.ActionForm {

    private String trade_short;
    private String trade_name;
    private int trade_code;

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

    public int getTrade_code() {
        return trade_code;
    }

    public void setTrade_code(int trade_code) {
        this.trade_code = trade_code;
    }
    

    public AddSubForm() {
        super();
         
    }
    
    @Override
   public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
    
        ActionErrors errors = new ActionErrors();
    
    
       
   return errors;
    }
}
