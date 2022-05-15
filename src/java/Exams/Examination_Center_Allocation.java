/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Ramesh Naidu
 */
public class Examination_Center_Allocation extends org.apache.struts.action.Action {

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
        Examination_Center_AllocationForm eca = (Examination_Center_AllocationForm) form;
        String iti_code[] = eca.getIti_code();
        String center_iti_code[] = eca.getCenter_iti_code();
        String type_admission = eca.getType_admission();
        String sem_nonsem = eca.getSem_nonsem();
        String type_iti = eca.getType_iti();
        String del_iti_code = request.getParameter("tc");
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "1", con);
        String month = beans.MyUtil.getOptionValue("iti_params", "code", "value", "2", con);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            int count = 0;
            String query = null;
            String olditicode = null;

            if (del_iti_code != null) {
                query = "delete from iticentres where iti_code='" + del_iti_code + "'";
                ps = con.prepareStatement(query);
                ps.executeUpdate();
                RequestDispatcher rd = request.getRequestDispatcher("/Examination_Center_Allocation.jsp");
                rd.forward(request, response);
    //request.setAttribute("success", "<h1 align=center><font color='red'>Successfully deleted  :"  +del_iti_code+"</font><h1>");
            } else {
                for (int i = 0; i < iti_code.length; i++) {
                    if (!(center_iti_code[i] == null || center_iti_code[i] == "")) {
                        String query2 = "select count(*) from iticentres where iti_code='" + iti_code[i] + "'";
                        ps = con.prepareStatement(query2);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            count = Integer.parseInt(rs.getString(1));
                        }
                        if (count == 0) {
                            olditicode = beans.MyUtil.getOptionValue("iti", "iti_code", "olditicode", iti_code[i], con);
                            query = "insert into iticentres(iti_code,ttc_code,year,month,type_admission,olditi_code,sem_nonsem,iti_noniti) values('" + iti_code[i] + "','" + center_iti_code[i] + "','" + year + "','" + month + "','" + type_admission + "','" + olditicode + "','" + sem_nonsem + "','" + type_iti + "')";
                            ps = con.prepareStatement(query);
                            ps.executeUpdate();
                        }/* else {
                         olditicode=beans.MyUtil.getOptionValue("iti", "iti_code", "olditicode", iti_code[i]);
                         query = "update iticentres set iti_code='" + iti_code[i] + "',ttc_code='" + center_iti_code[i] + "',year='" + year + "',month='" + month + "',type_admission='" + type_admission + "',olditi_code='"+olditicode+"',sem_nonsem='"+sem_nonsem+"',iti_noniti='"+type_iti+"' where iti_code='" + iti_code[i] + "'";
                        

                         }*/


                        request.setAttribute("success", "<h1 align=center><font color='red'>Successfully Inserted All Assigned Centres </font><h1>");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {

                con.close();
            }

        }
        return mapping.findForward(SUCCESS);
    }
}
