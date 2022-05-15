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
public class EditTrade_InterfaceForm extends org.apache.struts.action.ActionForm {

    private String trade_short, available;
    String strength, trade_code1,strength_fill,strength_vacant;

    public String getStrength_fill() {
        return strength_fill;
    }

    public void setStrength_fill(String strength_fill) {
        this.strength_fill = strength_fill;
    }

    public String getStrength_vacant() {
        return strength_vacant;
    }

    public void setStrength_vacant(String strength_vacant) {
        this.strength_vacant = strength_vacant;
    }

    public String getTrade_short() {
        return beans.MyUtil.filterBad(trade_short);
    }

    public void setTrade_short(String trade_short) {
        this.trade_short = trade_short;
    }

    public String getAvailable() {
        return beans.MyUtil.filterBad(available);
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getStrength() {
        return beans.MyUtil.filterBad(strength);
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getTrade_code1() {
        return beans.MyUtil.filterBad(trade_code1);
    }

    public void setTrade_code1(String trade_code1) {
        this.trade_code1 = trade_code1;
    }

    public EditTrade_InterfaceForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if (getStrength().length() < 1 || getStrength() == null) {
            errors.add("strength", new ActionMessage("error.strength.required"));
        } else if (!Validations.Validate(getStrength(), 2)) {
            errors.add("strength", new ActionMessage("error.strength.notmatch"));
        }
        if (getStrength().length() > 4) {
            errors.add("strength", new ActionMessage("error.strength.lenerror"));
        }
        /*if (getStrength().length() > 4) {
         errors.add("strength", new ActionMessage("error.strength.lenerror"));
         }*/
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
