/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author SUBBAREDDY
 */
public class ItiProfileUpdateProcessAction extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession httpSession = request.getSession();
        String userName = (String) httpSession.getAttribute("userName");
        //System.out.println("username----------" + userName);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DBC.DBConnection.getConnection(request);
            String query = "select iti_code,iti_name,userName,password,govt,iti_noniti,address,city_town,dist_code,mand_code,pin_code,mobile,landlineNumber,email,principalName,tot_strength,capacity,year_est,description from iti where username='" + userName + "' ";
            preparedStatement = connection.prepareStatement(query);
            //System.out.println("The query is " + query);
            resultSet = preparedStatement.executeQuery();
            ItiDetailsForm itiDetails = new ItiDetailsForm();
            //RegistrationForm registrationForm=new RegistrationForm();
            if (resultSet.next()) {
                //System.out.println("in if ---------------------");

                itiDetails.setIti_code(resultSet.getString(1));
                //System.out.println("The iti code is" + itiDetails.getIti_code());
                itiDetails.setIti_name(resultSet.getString(2));
                itiDetails.setUserName(resultSet.getString(3));
                itiDetails.setPassword(resultSet.getString(4));
                itiDetails.setGovt(resultSet.getString(5));
                itiDetails.setIti_noniti(resultSet.getString(6));
                itiDetails.setAddress(resultSet.getString(7));
                itiDetails.setCity_town(resultSet.getString(8));
                itiDetails.setDist_code(resultSet.getString(9));
                itiDetails.setMand_code(resultSet.getString(10));
                itiDetails.setPin_code(resultSet.getString(11));
                itiDetails.setMobile(resultSet.getString(12));
                itiDetails.setLandlineNumber(resultSet.getString(13));
                itiDetails.setEmail(resultSet.getString(14));
                itiDetails.setPrincipalName(resultSet.getString(15));
                itiDetails.setTot_strength(Integer.toString(resultSet.getInt(16)));
                //System.out.println("test totstrength" + itiDetails.getTot_strength());
                itiDetails.setCapacity(Integer.toString(resultSet.getInt(17)));

                itiDetails.setYear_est(resultSet.getString(18));
                itiDetails.setDescription(resultSet.getString(19));
                request.setAttribute("itiDetails", itiDetails);
            }

        } catch (Exception e) {
            //System.out.println("Error in getting iti details" + e);
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
                //System.out.println("Errors at finalizations  in LoginSuccess.jsp " + e);
            }

        }
        return mapping.findForward("itiProfileUpdateForm1");
    }
}
