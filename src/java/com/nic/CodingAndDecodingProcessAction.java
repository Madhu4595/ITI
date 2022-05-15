/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author SUBBAREDDY
 */
public class CodingAndDecodingProcessAction extends Action {

    private CodingAndDecodingForm codingAndDecodingForm;
    private static RegistrationDao registrationDao;

    static {
        registrationDao = new RegistrationDao();
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        codingAndDecodingForm = (CodingAndDecodingForm) form;
        String[] adm_num;
        Integer[] paper_code;
        try {
            int updatePaperCode = 0;
            int arch_code = 0;
            adm_num = codingAndDecodingForm.getAdm_num();
            paper_code = codingAndDecodingForm.getPaper_code();
            int trno = 0;
            if (adm_num != null && paper_code != null && adm_num.length == paper_code.length) {
                for (int i = 0; i < adm_num.length; i++) {
                    trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "exam_marks_2013", "Update", request);
                    arch_code = registrationDao.insertCodingAndDecodingPaperCodeArch(codingAndDecodingForm.getIti_code(), "" + codingAndDecodingForm.getTrade_code(), adm_num[i], codingAndDecodingForm.getSubject_code(), paper_code[i], trno,request);
                    updatePaperCode = registrationDao.updateCodingAndDecodingPaperCode(codingAndDecodingForm.getIti_code(), "" + codingAndDecodingForm.getTrade_code(), adm_num[i], codingAndDecodingForm.getSubject_code(), paper_code[i], trno,request);
                }
                if (updatePaperCode > 0 && arch_code > 0) {
                    return mapping.findForward("CodingAndDecodingProcessSuccess");
                } else {
                    return mapping.findForward("CodingAndDecodingProcessFailed");
                }
            } else {
                return mapping.findForward("CodingAndDecodingProcessFailed");
            }

        } catch (Exception e) {
        }
        return null;
    }
}
