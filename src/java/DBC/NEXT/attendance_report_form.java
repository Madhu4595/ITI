/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBC.NEXT;

/**
 *
 * @author surebel
 */
public class attendance_report_form implements java.io.Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private String month;
    private String working_days;
    private String attended_days;
    private String cumulative_attendance;
    private String trade_code;
    private String adm_num;

    public String getAdm_num() {
        return adm_num;
    }

    public void setAdm_num(String adm_num) {
        this.adm_num = adm_num;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWorking_days() {
        return working_days;
    }

    public void setWorking_days(String working_days) {
        this.working_days = working_days;
    }

    public String getAttended_days() {
        return attended_days;
    }

    public void setAttended_days(String attended_days) {
        this.attended_days = attended_days;
    }

    public String getCumulative_attendance() {
        return cumulative_attendance;
    }

    public void setCumulative_attendance(String cumulative_attendance) {
        this.cumulative_attendance = cumulative_attendance;
    }

    public String getTrade_code() {
        return trade_code;
    }

    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }
}
