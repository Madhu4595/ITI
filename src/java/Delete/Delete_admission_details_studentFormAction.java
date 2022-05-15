package Delete;

import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class Delete_admission_details_studentFormAction extends org.apache.struts.action.Action {

     
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }

        Connection con = null;
        Delete_admission_details_studentForm  frm = (Delete_admission_details_studentForm) form;

        String adm_num = frm.getAdm_num();
        String dsc_year = frm.getDsc_year();
        
        System.out.println("adm_num-> "+adm_num+ "dsc_year-> "+dsc_year);

        Statement ps = null;
        String sql1, sql2, sql3;

        try {
            con = DBC.DBConnection.getConnection(request);
            
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "1", con);
            String month = beans.MyUtil.getOptionValue("iti_params", "code", "value", "2", con);
            
            ps = con.createStatement();
            
            sql1 = "delete from admissions.iti_admissions where adm_num='" + adm_num + "'";
            sql2 = "delete from exams.exam_apply_" + year + "_" + month + " where adm_num='" + adm_num + "'";
            sql3 = "delete from exams.img_cand_photos_exams where adm_num='" + adm_num + "'";
            
            
             
        } 
        catch (Exception e) {}
        finally{
            if(con!=null){
                con.close();
            }
        }

        return mapping.findForward(SUCCESS);
    }
}
