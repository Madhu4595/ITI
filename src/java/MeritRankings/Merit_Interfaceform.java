/*
 * 1533
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MeritRankings;

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
public class Merit_Interfaceform extends org.apache.struts.action.ActionForm {

    private String iti_code, qual, type;

    public String getQual() {
        return beans.MyUtil.filterBad(qual);
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public String getType() {
        return beans.MyUtil.filterBad(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    /**
     * @param i
     */
    /**
     *
     */
    public Merit_Interfaceform() {
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
        if (!Validations.Validate(getType(), 14)) {
            errors.add("type", new ActionMessage("ItiAdmissionForm.res_category.required"));
        }
        if (!Validations.Validate(getQual(), 6)) {
            errors.add("qual", new ActionMessage("error.min_qual.notmatch"));
        }

        HttpSession ses = request.getSession();
        String role_id = ses.getAttribute("role_id") + "";
        String field_name;
        if (role_id.equals("4")) {
            field_name = "iti_code";
        } else {
            field_name = "dist_code";
        }
        String sql = "Select * from trade_sel where freezee=1 and " + field_name + "='" + iti_code + "' and phase->'1'='true'";
        //System.out.println("in form checking Freezed or not :: "+sql);
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            boolean freeze = false;
            if (rs.next()) {
                freeze = true;
            }
            if (!freeze) {
                errors.add("qual", new ActionMessage("error.freeze.dist"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Merit_Interfaceform.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                st.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Merit_Interfaceform.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return errors;
    }
}
