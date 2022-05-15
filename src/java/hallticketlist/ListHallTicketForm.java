/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hallticketlist;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author theru hareesh
 */
public class ListHallTicketForm extends org.apache.struts.action.ActionForm {

    public ListHallTicketForm() {
        super();
    }
    private String ttc_name, fname;

    public String getFname() {
        return beans.MyUtil.filterBad(fname);
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getTtc_name() {
        return beans.MyUtil.filterBad(ttc_name);
    }

    public void setTtc_name(String ttc_name) {
        this.ttc_name = ttc_name;
    }

    public String getTtc_code() {
        return beans.MyUtil.filterBad(ttc_code);
    }

    public void setTtc_code(String ttc_code) {
        this.ttc_code = ttc_code;
    }
    private String ttc_code;
    private String subject_code;
            public String sub_code[];
            public String sub_name[];

    public String[] getSub_code() {
        return sub_code;
    }

    public void setSub_code(String[] sub_code) {
        this.sub_code = sub_code;
    }

    public String[] getSub_name() {
        return sub_name;
    }

    public void setSub_name(String[] sub_name) {
        this.sub_name = sub_name;
    }
    private String idmarks1;
    private String idmarks2;
    byte[] img;

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getIdmarks1() {
        return beans.MyUtil.filterBad(idmarks1);
    }

    public void setIdmarks1(String idmarks1) {
        this.idmarks1 = idmarks1;
    }

    public String getIdmarks2() {
        return beans.MyUtil.filterBad(idmarks2);
    }

    public void setIdmarks2(String idmarks2) {
        this.idmarks2 = idmarks2;
    }

    public String getSubject_code() {
        return beans.MyUtil.filterBad(subject_code);
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }
    private String iti_name;
    private String iti_code;

    public String getTrade_code() {
        return beans.MyUtil.filterBad(trade_code);
    }

    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }

    public String getTrade_name() {
        return beans.MyUtil.filterBad(trade_name);
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public String getName() {
        return beans.MyUtil.filterBad(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegid() {
        return beans.MyUtil.filterBad(regid);
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }
    private String adm_num1;

    public String getAdm_num1() {
        return beans.MyUtil.filterBad(adm_num1);
    }

    public void setAdm_num1(String adm_num1) {
        this.adm_num1 = adm_num1;
    }
    private String name_list;
    private String adm_num;
    private String trade_code;
    private String trade_name;
    private String name;
    private String regid;
    private String mode_exam,type_admission;

    public String getType_admission() {
        return type_admission;
    }

    public void setType_admission(String type_admission) {
        this.type_admission = type_admission;
    }

    
    
    public String getMode_exam() {
        return beans.MyUtil.filterBad(mode_exam);
    }

    public void setMode_exam(String mode_exam) {
        this.mode_exam = mode_exam;
    }

    public String getAdm_num() {
        return beans.MyUtil.filterBad(adm_num);
    }

    public void setAdm_num(String adm_num) {
        this.adm_num = adm_num;
    }

    public String getName_list() {
        return beans.MyUtil.filterBad(name_list);
    }

    public void setName_list(String name_list) {
        this.name_list = name_list;
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
    private String subject_name;

    public String getSubject_name() {
        return beans.MyUtil.filterBad(subject_name);
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();


        
        return errors;
    }
}
