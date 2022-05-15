package com.nic;
 
import beans.MyUtil; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpSession;
 

public class RegistrationAction extends org.apache.struts.action.Action {

    private DistrictForm districtForm;
    private static DistrictDao districtDao;
    private static RegistrationDao registrationDao;

    static {
        districtDao = new DistrictDao();
        registrationDao = new RegistrationDao();
    }

    public RegistrationAction() {
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
         
        
        RegistrationForm registrationForm = (RegistrationForm) form;
        String dist_code = beans.MyUtil.filterBad(registrationForm.getDist_code());
        
        String iti_code = registrationDao.getItiCode(dist_code,request);
        
        registrationForm.setIti_code(iti_code);
        HttpSession session = request.getSession();
        
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        String tab = "dist_mst";
        String code = "dist_code";
        String name = "dist_name";
        String val = beans.MyUtil.filterBad(registrationForm.getDist_code());

        String distname = MyUtil.getOptionValue(tab, code, name, val,request);
        session.setAttribute("distname", distname);
         

        String old_dist_code = districtDao.getOldDistrictCoce(dist_code,request);
        String old_iti_code = old_dist_code + iti_code.substring(2);
         
        registrationForm.setOld_iti_code(old_iti_code);
         
        String ititype;
        if (registrationForm.getGovt().equals("G")) {
            ititype = "Government";
        } else {
            ititype = "Private";
        }
        session.setAttribute("ititype", ititype);

        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "iti", "Insert", request);
         

        int i = registrationDao.register(registrationForm, trno,request);
        
        
 
               if (i == 1) {
                    request.setAttribute("registrationForm", registrationForm);
                    return mapping.findForward("registrationSuccess");
                } else {
                    request.setAttribute("registrationForm", registrationForm);
                    return mapping.findForward("registrationFailed");
                }
            }
        }
