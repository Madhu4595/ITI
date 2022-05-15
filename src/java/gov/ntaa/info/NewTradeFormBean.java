/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.ntaa.info;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author theru hareesh
 */
public class NewTradeFormBean extends org.apache.struts.action.ActionForm {

    private int trade_code;
    private String trade_short;
    private String trade_name;
    private int duration_yrs;
    private String dr_nondr;
    private String type_admission;
    private String short_order;

    public String getShort_order() {
        return short_order;
    }

    public void setShort_order(String short_order) {
        this.short_order = short_order;
    }

    
    public String getDr_nondr() {
        return dr_nondr;
    }

    public void setDr_nondr(String dr_nondr) {
        this.dr_nondr = dr_nondr;
    }

    public String getType_admission() {
        return type_admission;
    }

    public void setType_admission(String type_admission) {
        this.type_admission = type_admission;
    }

    public int getTrade_code() {
        return trade_code;
    }

    public void setTrade_code(int trade_code) {
        this.trade_code = trade_code;
    }

    public String getTrade_short() {
        return trade_short;
    }

    public void setTrade_short(String trade_short) {
        this.trade_short = trade_short;
    }

    public String getTrade_name() {
        return trade_name;
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public int getDuration_yrs() {
        return duration_yrs;
    }

    public void setDuration_yrs(int duration_yrs) {
        this.duration_yrs = duration_yrs;
    }

    public String getEng_noneng() {
        return eng_noneng;
    }

    public void setEng_noneng(String eng_noneng) {
        this.eng_noneng = eng_noneng;
    }

    public String getMin_qual() {
        return min_qual;
    }

    public void setMin_qual(String min_qual) {
        this.min_qual = min_qual;
    }
    private String eng_noneng;
    private String min_qual;

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        /*setTrade_code(trade_code);
         setTrade_short(trade_short);
         setTrade_name(trade_name);
         setDuration_yrs(duration_yrs);
         setEng_noneng(eng_noneng);
         setMin_qual(min_qual);*/
        eng_noneng = "";
        duration_yrs = 0;
        trade_short = "";
        trade_name = "";
        min_qual = "";
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors aes = new ActionErrors();

        /*if(trade_code==0){
         aes.add("trade_code",new ActionMessage("trade_code.required"));
              
         }*/
        if (trade_short == null || trade_short.equalsIgnoreCase("")) {

            aes.add("trade_short", new ActionMessage("trade_short.required"));
        } else if (!Validations.Validate(trade_short, 6)) {
            aes.add("trade_short", new ActionMessage("error.trade_short.notmatch"));
        }
        if (trade_short.length() > 2) {
            aes.add("trade_short", new ActionMessage("error.trade_short.lenerror"));
        }

        if (trade_name == null || trade_name.equalsIgnoreCase("")) {
            aes.add("trade_name", new ActionMessage("trade_name.required"));
        } else if (!Validations.Validate(trade_name, 3)) {
            aes.add("trade_name", new ActionMessage("error.trade_name.notmatch"));
        }
        if (trade_short.length() > 50) {
            aes.add("trade_name", new ActionMessage("error.trade_name.lenerror"));
        }
        if (duration_yrs <= 0) {
            aes.add("duration_yrs", new ActionMessage("duration_yrs.required"));
        } else if (!Validations.Validate("" + duration_yrs, 2)) {
            aes.add("duration_yrs", new ActionMessage("error.duration_yrs.notmatch"));
        }

        if (duration_yrs > 100) {
            aes.add("duration_yrs", new ActionMessage("error.duration_yrs.lenerror"));
        }
        if (eng_noneng == null || eng_noneng.equalsIgnoreCase("")) {
            aes.add("eng_noneng", new ActionMessage("eng_noneng.required"));
        } else if (!Validations.Validate(eng_noneng, 6)) {
            aes.add("eng_noneng", new ActionMessage("error.eng_noneng.notmatch"));
        } else if (!eng_noneng.equals("E") && !eng_noneng.equals("N")) {
            aes.add("eng_noneng", new ActionMessage("error.eng_noneng.notmatch"));
        }
        if (eng_noneng.length() > 1) {
            aes.add("eng_noneng", new ActionMessage("error.eng_noneng.lenerror"));
        }
        if (min_qual == null || min_qual.equalsIgnoreCase("")) {
            aes.add("min_qual", new ActionMessage("min_qual.required"));
        } else if (!Validations.Validate(min_qual, 3)) {
            aes.add("min_qual", new ActionMessage("error.min_qual.notmatch"));
        }
        if (min_qual.length() > 10) {
            aes.add("min_qual", new ActionMessage("error.min_qual.lenerror"));
        }
        return aes;
    }
}
