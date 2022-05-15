/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm_withoutphoto;

import DBC.Validations;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Admin
 */
public class UpdateForm extends org.apache.struts.action.ActionForm {

    private String regid, ssc_type;

    public String getSsc_type() {
        return beans.MyUtil.filterBad(ssc_type);
    }

    public void setSsc_type(String ssc_type) {
        this.ssc_type = ssc_type;
    }

    public String getRegid() {
        return beans.MyUtil.filterBad(regid);
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        Connection con = DBC.DBConnection.getConnection(request);

        if (getRegid() == null || getRegid().length() < 1) {
            errors.add("regid", new ActionMessage("error.regid.required"));
        }

        if (!Validations.Validate(getRegid(), 2)) {
            errors.add("regid", new ActionMessage("error.regid.notmatch"));
        }

        if (getRegid().length() > 10) {
            errors.add("regid", new ActionMessage("error.regid.lenerror"));
        }
        try {
            if (!beans.generic.CandidateExists(getRegid(), request)) {
                errors.add("regid", new ActionMessage("error.regid.norecordfound"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            HttpSession ses = request.getSession();
            String iti_code = ses.getAttribute("ins_code").toString();
            String nm = "";
            String role_id = ses.getAttribute("role_id").toString();
            if (role_id.equals("3")) {
                nm = beans.MyUtil.getOptionValue("application_check", "regid", "name", regid, "dist_code", iti_code, con);
            } else if (role_id.equals("4")) {
                nm = beans.MyUtil.getOptionValue("application_check", "regid", "name", regid, "iti_code", iti_code, con);
                String sql = "select distinct freezee from trade_sel where iti_code='" + iti_code + "'";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if (rs.next()) {
                    String freezee = rs.getString("freezee");
                    if (freezee.equals("1")) {
                        errors.add(iti_code, new ActionMessage("error.regid.freezed"));
                    }
                }
                try {
                    rs.close();
                    st.close();
                } catch (Exception ex) {
                    //System.out.println("error occured -- IN Update form bean");
                }
            }

            if (!(nm.length() > 1)) {
                errors.add(iti_code, new ActionMessage("error.regid.otherdist"));
            }

            if (con != null) {
                con.close();
                con = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UpdateForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                con = null;
            }
        }
        if (!Validations.Validate(getSsc_type(), 6)) {
            errors.add("ssc_type", new ActionMessage("error.ssc_type.notmatch"));
        }
        return errors;
    }
}
