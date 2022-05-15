/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserRegistration;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Kranthi Bharat
 */
public class Register_New_User_Form extends org.apache.struts.action.ActionForm {

    private String user_name, empid;
    private String choose_pwd;
    private String con_pwd;
    private String addrole_id;
    private String fm_time;
    private String to_time;
    private String ip_add;
    private String desig_code;
    private String dist_code;
    private String u_name;
    private String mob_no;
    private String email;
    private String ins_code;
    private String role_id;
    private String tab_name;
    private String reg_code;
    private String iti_colg;
    
    public String getAddrole_id() {
        return beans.MyUtil.filterBad(addrole_id);
    }

    public void setAddrole_id(String addrole_id) {
        this.addrole_id = addrole_id;
    }

    public String getUser_name() {
        return beans.MyUtil.filterBad(user_name);
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getChoose_pwd() {
        return beans.MyUtil.filterBad(choose_pwd);
    }

    public void setChoose_pwd(String choose_pwd) {
        this.choose_pwd = choose_pwd;
    }

    public String getCon_pwd() {
        return beans.MyUtil.filterBad(con_pwd);
    }

    public void setCon_pwd(String con_pwd) {
        this.con_pwd = con_pwd;
    }

    public String getFm_time() {
        return beans.MyUtil.filterBad(fm_time);
    }

    public void setFm_time(String fm_time) {
        this.fm_time = fm_time;
    }

    public String getTo_time() {
        return beans.MyUtil.filterBad(to_time);
    }

    public String getEmpid() {
        return beans.MyUtil.filterBad(empid);
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public String getIp_add() {
        return beans.MyUtil.filterBad(ip_add);
    }

    public void setIp_add(String ip_add) {
        this.ip_add = ip_add;
    }

    public String getDesig_code() {
        return beans.MyUtil.filterBad(desig_code);
    }

    public void setDesig_code(String desig_code) {
        this.desig_code = desig_code;
    }

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getU_name() {
        return beans.MyUtil.filterBad(u_name);
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getMob_no() {
        return beans.MyUtil.filterBad(mob_no);
    }

    public void setMob_no(String mob_no) {
        this.mob_no = mob_no;
    }

    public String getIns_code() {
        return beans.MyUtil.filterBad(ins_code);
    }

    public void setIns_code(String ins_code) {
        this.ins_code = ins_code;
    }

    public String getRole_id() {
        return beans.MyUtil.filterBad(role_id);
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getTab_name() {
        return beans.MyUtil.filterBad(tab_name);
    }

    public void setTab_name(String tab_name) {
        this.tab_name = tab_name;
    }
    

    public String getEmail() {
        return beans.MyUtil.filterBad(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public String getIti_colg() {
        return beans.MyUtil.filterBad(iti_colg);
    }

    public void setIti_colg(String iti_colg) {
        this.iti_colg = iti_colg;
    }

    public String getReg_code() {
        return beans.MyUtil.filterBad(reg_code);
    }

    public void setReg_code(String reg_code) {
        this.reg_code = reg_code;
    }

    public Register_New_User_Form() {
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

        if (getUser_name() == null || getUser_name().length() < 1) {
            errors.add("user_name", new ActionMessage("error.user_name.required"));
        } else {

            if (getUser_name().length() > 30) {
                errors.add("user_name", new ActionMessage("error.user_name.maxlen"));
            }
        }
        if (getChoose_pwd() == null || getChoose_pwd().length() < 1) {
            errors.add("choose_pwd", new ActionMessage("error.choose_pwd.required"));
        } else {

            if (!Validations.Validate(getChoose_pwd(), 1)) {
                errors.add("choose_pwd", new ActionMessage("error.choose_pwd.notmatch"));
            }

            if (getChoose_pwd().length() > 30) {
                errors.add("choose_pwd", new ActionMessage("error.choose_pwd.maxlen"));
            }
        }
        if (getCon_pwd() == null || getCon_pwd().length() < 1) {
            errors.add("con_pwd", new ActionMessage("error.con_pwd.required"));
        } else {

            if (!Validations.Validate(getCon_pwd(), 1)) {
                errors.add("con_pwd", new ActionMessage("error.con_pwd.notmatch"));
            }

            if (getCon_pwd().length() > 30) {
                errors.add("con_pwd", new ActionMessage("error.con_pwd.maxlen"));
            }
        }

        if (!choose_pwd.equals(con_pwd)) {
            errors.add("check_pwd", new ActionMessage("errors.check_pwd.notmatch"));
        }

        if (getU_name() == null || getU_name().length() < 1) {
            errors.add("u_name", new ActionMessage("error.u_name.required"));
        } else {

            if (!Validations.Validate(getU_name(), 3)) {
                errors.add("u_name", new ActionMessage("error.u_name.notmatch"));
            }

            if (getU_name().length() > 30) {
                errors.add("u_name", new ActionMessage("error.u_name.maxlen"));
            }
        }

         if (getMob_no() == null || getMob_no().length() < 1) {
         errors.add("mob_no", new ActionMessage("error.mob_no.required"));
         } else {

         if (!Validations.Validate(getMob_no(), 2)) {
         errors.add("mob_no", new ActionMessage("error.mob_no.notmatch"));
         }

         if (getMob_no().length() > 10) {
         errors.add("mob_no", new ActionMessage("error.mob_no.maxlen"));
         }
         }



         if (getEmail() == null || getEmail().length() < 1) {
         errors.add("email", new ActionMessage("error.email.required"));
         } else {

         if (!Validations.Validate(getEmail(),3)) {
         errors.add("email", new ActionMessage("error.email.notmatch"));
         }

         if (getEmail().length() > 30) {
         errors.add("email", new ActionMessage("error.email.maxlen"));
         }
         }
        if (getRole_id() == null || getRole_id().length() < 1) {
            errors.add("role_id", new ActionMessage("error.role_id.required"));
        }
        if (getAddrole_id() == null || getAddrole_id().length() < 1) {
            errors.add("addrole_id", new ActionMessage("error.addrole_id.required"));
        }

        return errors;
    }
}
