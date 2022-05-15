/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.ntaa.info;

/**
 *
 * @author theru hareesh
 */
public class NewTradeDTO {

    public int trade_code;
public String type_admission;
public String dr_nondr;

    public String getType_admission() {
        return type_admission;
    }

    public void setType_admission(String type_admission) {
        this.type_admission = type_admission;
    }

    public String getDr_nondr() {
        return dr_nondr;
    }

    public void setDr_nondr(String dr_nondr) {
        this.dr_nondr = dr_nondr;
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
    public String trade_short;
    public String trade_name;
    public int duration_yrs;
    public String eng_noneng;
    public String min_qual;
    String short_order;

    public String getShort_order() {
        return short_order;
    }

    public void setShort_order(String short_order) {
        this.short_order = short_order;
    }
}
