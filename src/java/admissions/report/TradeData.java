/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package admissions.report;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Siva
 */
public class TradeData extends org.apache.struts.action.ActionForm {
    
       private String name;
       private int number;
    String short_name;
    String trade_code;
    String dist_code;
    String dist_name;
    String iti_code;
    String eng;
    String board_code;

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getBoard_code() {
        return board_code;
    }

    public void setBoard_code(String board_code) {
        this.board_code = board_code;
    }
    private String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getTrade_code() {
        return trade_code;
    }

    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }

    public String getDist_code() {
        return dist_code;
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }

    public String getIti_code() {
        return iti_code;
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }


    /**
     *
     */
    public TradeData() {
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
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
      
        return errors;
    }
}
