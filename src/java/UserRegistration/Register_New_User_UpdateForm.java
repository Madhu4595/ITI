/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserRegistration;

/**
 *
 * @author Kranthi Bharat
 */
public class Register_New_User_UpdateForm implements java.io.Serializable {

    String slno;
    String u_name;
    String from_time;
    String to_time;
    String ip_address;

    public String getSlno() {
        return beans.MyUtil.filterBad(slno);
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }

    public String getU_name() {
        return beans.MyUtil.filterBad(u_name);
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getFrom_time() {
        return beans.MyUtil.filterBad(from_time);
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTo_time() {
        return beans.MyUtil.filterBad(to_time);
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public String getIp_address() {
        return beans.MyUtil.filterBad(ip_address);
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getDesig_code() {
        return beans.MyUtil.filterBad(desig_code);
    }

    public void setDesig_code(String desig_code) {
        this.desig_code = desig_code;
    }

    public String getRegion_code() {
        return beans.MyUtil.filterBad(region_code);
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getMobile_no() {
        return beans.MyUtil.filterBad(mobile_no);
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail_id() {
        return beans.MyUtil.filterBad(email_id);
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getRoleid() {
        return beans.MyUtil.filterBad(roleid);
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }
    String desig_code;
    String region_code;
    String dist_code;
    String iti_colg;

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getIti_colg() {
        return beans.MyUtil.filterBad(iti_colg);
    }

    public void setIti_colg(String iti_colg) {
        this.iti_colg = iti_colg;
    }
    String mobile_no;
    String email_id;
    String roleid;
    String designation;

    public String getDesignation() {
        return beans.MyUtil.filterBad(designation);
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
