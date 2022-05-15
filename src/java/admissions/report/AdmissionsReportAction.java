/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admissions.report;

import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author theru hareesh
 */
public class AdmissionsReportAction extends org.apache.struts.action.Action {

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
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id = stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        AdmissionsReportForm arf = (AdmissionsReportForm) form;
        String from_date = beans.MyUtil.filterBad(arf.getFrom_date() == null ? "" : (arf.getFrom_date()));
        String to_date = beans.MyUtil.filterBad(arf.getTo_date() == null ? "" : (arf.getTo_date()));
        
        System.out.println("the from date value is:" + from_date);
        System.out.println("the to date value is:" + to_date);
        
        request.setAttribute("to_date", to_date);
        request.setAttribute("from_date", from_date);

        return mapping.findForward(SUCCESS);
    }
}
