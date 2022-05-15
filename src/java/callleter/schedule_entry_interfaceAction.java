/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package callleter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Gopi Daggumalle
 */
public class schedule_entry_interfaceAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        schedule_entry_interfaceForm fm = (schedule_entry_interfaceForm) form;
        
        String minqual = beans.MyUtil.filterBad(fm.getMinqual());
        String caste = beans.MyUtil.filterBad(fm.getCaste());
        String phase = beans.MyUtil.filterBad(fm.getPhase());
        
        HttpSession session = request.getSession(true);
        
        session.setAttribute("minqual", minqual);
        session.setAttribute("caste", caste);
        session.setAttribute("phase", phase);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
        
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        System.out.println("stored_token--->"+token);
        System.out.println("token--->"+id);
        
        if(!token.equals(id)){ return mapping.findForward("failure"); }

        return mapping.findForward(SUCCESS);
    }
}
