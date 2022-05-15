package callleter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import DBC.Validations;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author RAJU
 */
public class admissionForm extends org.apache.struts.action.ActionForm {

    String iti_code, dist_code, minqul, caste, merit_to, cal_date, cal_time;

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String Dist_code) {
        this.dist_code = dist_code;
    }

    public String getMinqul() {
        return beans.MyUtil.filterBad(minqul);
    }

    public void setMinqul(String minqul) {
        this.minqul = minqul;
    }

    public String getCaste() {
        return beans.MyUtil.filterBad(caste);
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getMerit_to() {
        return beans.MyUtil.filterBad(merit_to);
    }

    public void setMerit_to(String merit_to) {
        this.merit_to = merit_to;
    }

    public String getCal_date() {
        return beans.MyUtil.filterBad(cal_date);
    }

    public void setCal_date(String cal_date) {
        this.cal_date = cal_date;
    }

    public String getCal_time() {
        return beans.MyUtil.filterBad(cal_time);
    }

    public void setCal_time(String cal_time) {
        this.cal_time = cal_time;
    }
    String district_type;

    public String getDistrict_type() {
        return beans.MyUtil.filterBad(district_type);
    }

    public void setDistrict_type(String district_type) {
        this.district_type = district_type;
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

        //if (!Validations.Validate(getDist_code()+"", 2)) {
        //    errors.add("dist_code", new ActionMessage("error.dist_code.notmatch"));
        // }
        //if (getDist_code().length() > 2) {
        //    errors.add("dist_code", new ActionMessage("error.dist_code.lenerror"));
        // }
        String maxrank = "";

        try {
            Connection con = DBC.DBConnection.getConnection(request);
            HttpSession session = request.getSession(true);
            //maxrank = beans.MyUtil.getOptionValue("ranks", "iti_code", "max(iti_code)", "" + session.getAttribute("iti_code"), "phase->'1'", "true", con);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(admissionForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (!Validations.Validate(getMinqul(), 3)) {
                errors.add("minqul", new ActionMessage("error.minqul.notmatch"));
            }
            if (getMinqul().length() > 9) {
                errors.add("minqul", new ActionMessage("error.minqul.lenerror"));
            }

        } catch (NullPointerException ex) {
            errors.add("minqul", new ActionMessage("error.minqul.required"));
        }

        if (!Validations.Validate(getCaste(), 14)) {
            // errors.add("caste", new ActionMessage("error.caste.notmatch"));
        }

        if (getCaste().length() > 5) {
            errors.add("caste", new ActionMessage("error.caste.lenerror"));
        }

        if (!Validations.Validate(getMerit_to(), 2)) {
            errors.add("merit_to", new ActionMessage("error.merit_to.notmatch"));
        }

        if (!Validations.Validate(getCal_date(), 8)) {
            errors.add("cal_date", new ActionMessage("error.cal_date.notmatch"));
        }
        if (getCal_date().length() > 10) {
            errors.add("cal_date", new ActionMessage("error.cal_date.lenerror"));
        }

        if (!Validations.Validate(getCal_time(), 16)) {
            errors.add("cal_time", new ActionMessage("error.cal_time.notmatch"));
        }

        if (getCal_time().length() > 5) {
            errors.add("cal_time", new ActionMessage("error.cal_time.lenerror"));
        }

        return errors;
    }
}
