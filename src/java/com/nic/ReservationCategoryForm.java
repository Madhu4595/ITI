package com.nic;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author SUBBAREDDY
 */
public class ReservationCategoryForm extends org.apache.struts.action.ActionForm {

    private String category_name;

    public ReservationCategoryForm() {
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_name() {
        return beans.MyUtil.filterBad(category_name);
    }
    private String category_code;

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getCategory_code() {
        return beans.MyUtil.filterBad(category_code);
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        return null;
    }
}
