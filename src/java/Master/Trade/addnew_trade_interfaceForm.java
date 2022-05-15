/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Master.Trade;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Gopi Daggumalle
 */
public class addnew_trade_interfaceForm extends org.apache.struts.action.ActionForm {

    String trade_code, strength, available;

    public String getTrade_code() {
        return beans.MyUtil.filterBad(trade_code);
    }

    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }

    public String getStrength() {
        return beans.MyUtil.filterBad(strength);
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getAvailable() {
        return beans.MyUtil.filterBad(available);
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public addnew_trade_interfaceForm() {
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
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if ("".equals(getTrade_code()) || getTrade_code().length() < 1) {
            errors.add("trade_code", new ActionMessage("error.trade_code.required"));
        } else if (!Validations.Validate(getTrade_code(), 2)) {
            errors.add("trade_code", new ActionMessage("error.trade_code.notmatch"));
        }

        if (getTrade_code().length() > 3) {
            errors.add("trade_code", new ActionMessage("error.trade_code.lenerror"));
        }

        if ("".equals(getStrength()) || getStrength().length() < 1) {
            errors.add("strength", new ActionMessage("error.strength.required"));
        } else if (!Validations.Validate(getStrength(), 2)) {
            errors.add("strength", new ActionMessage("error.strength.notmatch"));
        }

        if (getStrength().length() > 4) {
            errors.add("strength", new ActionMessage("error.strength.lenerror"));
        }
        try {
            if ("".equals(getAvailable()) || getAvailable().length() < 1) {
                errors.add("available", new ActionMessage("error.available.required"));
            } else if (!Validations.Validate(getAvailable(), 2)) {
                errors.add("available", new ActionMessage("error.available.notmatch"));
            }

            if (getAvailable().length() > 3) {
                errors.add("available", new ActionMessage("error.available.lenerror"));
            }
        } catch (NullPointerException es) {
            errors.add("available", new ActionMessage("error.available.required"));
        }
        return errors;
    }
}
