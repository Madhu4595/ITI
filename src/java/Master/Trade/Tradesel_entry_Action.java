/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Master.Trade;

import DBC.generic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Kranthi Bharat
 */
public class Tradesel_entry_Action extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Tradesel_entry_Form tef = (Tradesel_entry_Form) form;
        String regid = beans.MyUtil.filterBad(tef.getRegid());

        String iti_code = beans.MyUtil.filterBad(tef.getIti_code());
        
        HttpSession ses = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        Connection con = null;
        PreparedStatement ps = null;
        String opr = tef.getOpr();
        
        int trno = generic.logTrace("" + ses.getAttribute("u_name"), "trade_sel", opr, request);
        try {
            con = DBC.DBConnection.getConnection(request);
            //System.out.println("conn"+con);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
            String phse = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);

            String qry1 = null, qry2;
            int k = 0;
            //System.out.println(opr.equalsIgnoreCase("insert"));
            if (opr.equalsIgnoreCase("insert")) {
                String val = beans.MyUtil.getOptionValue("trade_sel", "regid", "regid", regid, "temp_code", iti_code, con);

                if (val == null || "".equals(val)) {
                    qry1 = "insert into trade_sel(regid,iti_code,temp_code,trno,phase,year) values(?,?,?,?,hstore(array[?],array['true']),?)";
                    System.out.println(qry1);
                    ps = con.prepareStatement(qry1);
                    ps.setString(1, regid);
                    ps.setString(2, iti_code);
                    ps.setString(3, iti_code);
                    ps.setInt(4, trno);
                    ps.setString(5, phse);
                    ps.setString(6, year);
                    k = ps.executeUpdate();
                    if (k > 0) {
                        request.setAttribute("success", "<font color=green><b>Success Your Operation</b><font>");
                    } else {
                        request.setAttribute("success", "<font color=red><b>Fail Your Operation</b><font>");
                    }
                    ps.close();
                     
                } else {
                    qry1 = "update trade_sel set trno=?,year=?,phase=case when phase is null then '' else phase end || hstore(array[?],array['true']) where regid=? and iti_code=?";
                    System.out.println(qry1);
                    ps = con.prepareStatement(qry1);
                    ps.setInt(1, trno);
                    ps.setString(2, year);
                    ps.setString(3, phse);
                    ps.setString(4, regid);
                    ps.setString(5, iti_code);
                    k = ps.executeUpdate();
                    if (k > 0) {
                        request.setAttribute("success", "<font color=green><b>Success Your Operation</b><font>");
                    } else {
                        request.setAttribute("success", "<font color=red><b>Fail Your Operation</b><font>");
                    }
                    //qry1 = "update trade_sel set phase2e=true where iti_code='" + iti_code + "' and regid='" + regid + "'";
                }

                qry1 = "update application set year=? ,phase=case when phase is null then '' else phase end || hstore(array[?],array['true']) where regid=?";
                System.out.println(qry1);
                ps = con.prepareStatement(qry1);
                ps.setString(1, year);
                ps.setString(2, phse);
                ps.setString(3, regid);
                ps.executeUpdate();
                //qry2 = "update application set phase2e=true,trno=? where regid='" + regid + "';";
                //ps = con.prepareStatement(qry2);
                // ps.setInt(1, trno);
                //k = ps.executeUpdate();
                //ps.close();
            } else {
                qry1 = "UPDATE trade_sel SET phase = delete(phase, array[?]) where  regid =? and iti_code=?";
                //System.out.println(qry1);
                request.setAttribute("success", "<font color=green><b>Success Your Operation</b><font>");
                ps = con.prepareStatement(qry1);
                ps.setString(1, phse);
                ps.setString(2, regid);
                ps.setString(3, iti_code);
                ps.executeUpdate();
                
               /*  qry1 = "UPDATE application SET phase = delete(phase, array['" + phse + "']) where  regid ='" + regid + "' ";
                //System.out.println(qry1);
                request.setAttribute("success", "<font color=green><b>Success Your Operation</b><font>");
                ps = con.prepareStatement(qry1);
                ps.executeUpdate();*/
                ps.close();
                con.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //ps.close();
            if (ps != null) {
                ps.close();
            }
            //con.close();
            if (con != null) {
                con.close();
            }
        }

        return mapping.findForward(SUCCESS);
    }
}
