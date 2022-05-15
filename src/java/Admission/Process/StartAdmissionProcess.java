/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admission.Process;

import Master.Trade.JavaBeanFormSerializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author GOPI
 */
public class StartAdmissionProcess extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();

        Connection connection = DBC.DBConnection.getConnection(request);
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String code = (String) session.getAttribute("ins_code");
        try {
            preparedStatement = connection.prepareStatement("select distinct a.iti_code,b.iti_name from ititrade a,iti b where a.iti_code=b.iti_code and trade_freeze=0  and a.iti_code like '" + code + "%' order by iti_code");

            ArrayList UserList = new ArrayList();

            resultSet = preparedStatement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                i++;
                JavaBeanFormSerializable fm = new JavaBeanFormSerializable();
                fm.setIti_code(resultSet.getString("iti_code"));
                fm.setIti_name(resultSet.getString("iti_name"));
                UserList.add(fm);
            }
            request.setAttribute("iti_not_freezed", UserList);
            if (i > 0) {
                return mapping.findForward("iti_not_freezed");
            }
            //ResultSet resultSet = null;
            try {
                preparedStatement = connection.prepareStatement("select count(regid) from ranks");
                resultSet = preparedStatement.executeQuery();
                int k = 0;
                if (resultSet.next()) {

                    k = resultSet.getInt(1);
                }
                
                if (k == 0) {
                    return mapping.findForward("rank_not_Generated");
                }
            } catch (Exception e) {
            }

            //ItiAdmissionProcessForm ItiAdmissionProcessForm;
            DistrictAdmissionProcess_interfaceForm ItiAdmissionProcessForm = (DistrictAdmissionProcess_interfaceForm) form;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            java.sql.Date date2 = java.sql.Date.valueOf(dateFormat.format(date));
            ItiAdmissionProcessForm.setCurrentDate(date2);
            String query = "select merit_from,merit_to from admission_timings where dist_code ='" + code + "' and cal_date='" + date + "'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                ItiAdmissionProcessForm.setMerit_from(resultSet.getInt("merit_from"));
                ItiAdmissionProcessForm.setMerit_to(resultSet.getInt("merit_to"));
               
            } else {
                return mapping.findForward("CouncilDates_notFound");
            }

        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
            }
        }

        return mapping.findForward("DistrictAdmissionprocessSuccess");
    }
}
