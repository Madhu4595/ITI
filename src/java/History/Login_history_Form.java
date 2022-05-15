/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package History;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Kranthi Bharat
 */
public class Login_history_Form extends org.apache.struts.action.ActionForm {

    private String slno;
    private String uname;
    private String logindatetime;
    private String logoutdatetime;
    private String ipaddress;
    private String generate_reports;
    private String link;
    private String sessionid;

    public String getGenerate_reports() {
        return beans.MyUtil.filterBad(generate_reports);
    }

    public void setGenerate_reports(String generate_reports) {
        this.generate_reports = generate_reports;
    }

    public String getLink() {
        return beans.MyUtil.filterBad(link);
    }

    public void setLink(String link) {
        this.link = link;
    }
    

    public String getSlno() {
        return beans.MyUtil.filterBad(slno);
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }

    public String getUname() {
        return beans.MyUtil.filterBad(uname);
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getIpaddress() {
        return beans.MyUtil.filterBad(ipaddress);
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getSessionid() {
        return beans.MyUtil.filterBad(sessionid);
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getLogindatetime() {
        return beans.MyUtil.filterBad(logindatetime);
    }

    public void setLogindatetime(String logindatetime) {
        this.logindatetime = logindatetime;
    }

    public String getLogoutdatetime() {
        return beans.MyUtil.filterBad(logoutdatetime);
    }

    public void setLogoutdatetime(String logoutdatetime) {
        this.logoutdatetime = logoutdatetime;
    }

    public Login_history_Form() {
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

        if (getLogindatetime() == null || getLogindatetime().length() < 1) {
            errors.add("logindatetime", new ActionMessage("error.logindatetime.required"));
        } else {

         if (!Validations.Validate(getLogindatetime(),8)) {
         errors.add("logindatetime", new ActionMessage("error.logindatetime.notmatch"));
         }

         if (getLogindatetime().length() > 10) {
         errors.add("logindatetime", new ActionMessage("error.logindatetime.maxlen"));
         }
         }
            
          if (getLogoutdatetime() == null || getLogoutdatetime().length() < 1) {
            errors.add("logoutdatetime", new ActionMessage("error.logoutdatetime.required"));
        } else {

         if (!Validations.Validate(getLogoutdatetime(),18)) {
         errors.add("logoutdatetime", new ActionMessage("error.logoutdatetime.notmatch"));
         }

         if (getLogoutdatetime().length() > 10) {
         errors.add("logoutdatetime", new ActionMessage("error.logoutdatetime.maxlen"));
         }
         }

        return errors;
    }
}
