/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionServletWrapper;

/**
 *
 * @author SUBBAREDDY
 */
public class CouncilDetailsForm extends org.apache.struts.action.ActionForm {

    private String trade_code;
    private String trade_short;
    private String trade_name;
    private int totalStrength;
    private int generalStrength;
    private int generalStrength_fill;
    private int generalStrength_vacant;
    private int reservationStrength;
    private int reservationStrength_fill;
    private int reservationStrength_vacant;
    private int reservationStrength_w;
    private int reservationStrength_fill_w;
    private int reservationStrength_vacant_w;
    private int generalStrength_w;
    private int generalStrength_fill_w;
    private int generalStrength_vacant_w;
    private int phcStrength;
    private int phcStrength_fill;
    private int phcStrength_vacant;
    private int phcStrength_w;
    private int phcStrength_fill_w;
    private int phcStrength_vacant_w;
    private int exserviceStrength;
    private int exserviceStrength_fill;
    private int exserviceStrength_vacant;
    private int exserviceStrength_w;
    private int exserviceStrength_fill_w;
    private int exserviceStrength_vacant_w;
    private int check;
    private int checkName;

    public int getCheckName() {
        return checkName;
    }

    public void setCheckName(int checkName) {
        this.checkName = checkName;
    }

    public String getIti_name() {
        return beans.MyUtil.filterBad(iti_name);
    }

    public void setIti_name(String iti_name) {
        this.iti_name = iti_name;
    }

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }
    private String iti_name;
    private String iti_code;

    public CouncilDetailsForm() {
        super();
    }

    public int getTotalStrength() {
        return totalStrength;
    }

    public void setTotalStrength(int totalStrength) {
        this.totalStrength = totalStrength;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public int getCheck() {
        return check;
    }

    public void setExserviceStrength_vacant_w(int exserviceStrength_vacant_w) {
        this.exserviceStrength_vacant_w = exserviceStrength_vacant_w;
    }

    public int getExserviceStrength_vacant_w() {
        return exserviceStrength_vacant_w;
    }

    public void setExserviceStrength_fill_w(int exserviceStrength_fill_w) {
        this.exserviceStrength_fill_w = exserviceStrength_fill_w;
    }

    public int getExserviceStrength_fill_w() {
        return exserviceStrength_fill_w;
    }

    public void setExserviceStrength_w(int exserviceStrength_w) {
        this.exserviceStrength_w = exserviceStrength_w;
    }

    public int getExserviceStrength_w() {
        return exserviceStrength_w;
    }

    public void setExserviceStrength_vacant(int exserviceStrength_vacant) {
        this.exserviceStrength_vacant = exserviceStrength_vacant;
    }

    public int getExserviceStrength_vacant() {
        return exserviceStrength_vacant;
    }

    public void setExserviceStrength_fill(int exserviceStrength_fill) {
        this.exserviceStrength_fill = exserviceStrength_fill;
    }

    public int getExserviceStrength_fill() {
        return exserviceStrength_fill;
    }

    public void setExserviceStrength(int exserviceStrength) {
        this.exserviceStrength = exserviceStrength;
    }

    public int getExserviceStrength() {
        return exserviceStrength;
    }

    public void setPhcStrength_vacant_w(int phcStrength_vacant_w) {
        this.phcStrength_vacant_w = phcStrength_vacant_w;
    }

    public int getPhcStrength_vacant_w() {
        return phcStrength_vacant_w;
    }

    public void setPhcStrength_fill_w(int phcStrength_fill_w) {
        this.phcStrength_fill_w = phcStrength_fill_w;
    }

    public int getPhcStrength_fill_w() {
        return phcStrength_fill_w;
    }

    public void setPhcStrength_w(int phcStrength_w) {
        this.phcStrength_w = phcStrength_w;
    }

    public int getPhcStrength_w() {
        return phcStrength_w;
    }

    public void setPhcStrength_vacant(int phcStrength_vacant) {
        this.phcStrength_vacant = phcStrength_vacant;
    }

    public int getPhcStrength_vacant() {
        return phcStrength_vacant;
    }

    public void setPhcStrength_fill(int phcStrength_fill) {
        this.phcStrength_fill = phcStrength_fill;
    }

    public int getPhcStrength_fill() {
        return phcStrength_fill;
    }

    public void setPhcStrength(int phcStrength) {
        this.phcStrength = phcStrength;
    }

    public int getPhcStrength() {
        return phcStrength;
    }

    public void setGeneralStrength_vacant_w(int generalStrength_vacant_w) {
        this.generalStrength_vacant_w = generalStrength_vacant_w;
    }

    public int getGeneralStrength_vacant_w() {
        return generalStrength_vacant_w;
    }

    public void setGeneralStrength_fill_w(int generalStrength_fill_w) {
        this.generalStrength_fill_w = generalStrength_fill_w;
    }

    public int getGeneralStrength_fill_w() {
        return generalStrength_fill_w;
    }

    public void setGeneralStrength_w(int generalStrength_w) {
        this.generalStrength_w = generalStrength_w;
    }

    public int getGeneralStrength_w() {
        return generalStrength_w;
    }

    public void setReservationStrength_vacant_w(int reservationStrength_vacant_w) {
        this.reservationStrength_vacant_w = reservationStrength_vacant_w;
    }

    public int getReservationStrength_vacant_w() {
        return reservationStrength_vacant_w;
    }

    public void setReservationStrength_fill_w(int reservationStrength_fill_w) {
        this.reservationStrength_fill_w = reservationStrength_fill_w;
    }

    public int getReservationStrength_fill_w() {
        return reservationStrength_fill_w;
    }

    public void setReservationStrength_w(int reservationStrength_w) {
        this.reservationStrength_w = reservationStrength_w;
    }

    public int getReservationStrength_w() {
        return reservationStrength_w;
    }

    public void setReservationStrength_vacant(int reservationStrength_vacant) {
        this.reservationStrength_vacant = reservationStrength_vacant;
    }

    public int getReservationStrength_vacant() {
        return reservationStrength_vacant;
    }

    public void setReservationStrength_fill(int reservationStrength_fill) {
        this.reservationStrength_fill = reservationStrength_fill;
    }

    public int getReservationStrength_fill() {
        return reservationStrength_fill;
    }

    public void setReservationStrength(int reservationStrength) {
        this.reservationStrength = reservationStrength;
    }

    public int getReservationStrength() {
        return reservationStrength;
    }

    @Override
    public ActionServletWrapper getServletWrapper() {
        return super.getServletWrapper();
    }

    public void setGeneralStrength_vacant(int generalStrength_vacant) {
        this.generalStrength_vacant = generalStrength_vacant;
    }

    public int getGeneralStrength_vacant() {
        return generalStrength_vacant;
    }

    public void setGeneralStrength_fill(int generalStrength_fill) {
        this.generalStrength_fill = generalStrength_fill;
    }

    public int getGeneralStrength_fill() {
        return generalStrength_fill;
    }

    public void setGeneralStrength(int generalStrength) {
        this.generalStrength = generalStrength;
    }

    public int getGeneralStrength() {
        return generalStrength;
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

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        return errors;
    }
}
