package com.nic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class DistrictDao {

    public DistrictDao() {
    }

    //DistrictForm districtForm;
    public ArrayList<DistrictForm> getDistrictDetails(HttpServletRequest request) {
        Connection connection = DBC.DBConnection.getConnection(request);
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<DistrictForm> districtForms = new ArrayList<DistrictForm>();

        try {
            preparedStatement = connection.prepareStatement("select dist_code,dist_name from dist_mst where statecode='28' order by dist_code");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DistrictForm districtForm = new DistrictForm();
                districtForm.setDist_code(resultSet.getString(1));

                //////System.out.println("Dist Code: "+resultSet.getString(1));
                districtForm.setDist_name(resultSet.getString(2));
                districtForms.add(districtForm);
                //return districtForms;
            }
            return districtForms;
        } catch (Exception e) {
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
                ////System.out.println("Errors at finalizations  in DistrictDao " + e);
            }

        }

        return null;
    }

    public String getOldDistrictCoce(String dist_code,HttpServletRequest request) {
        Connection connection = DBC.DBConnection.getConnection(request);
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("select itidist_code from dist_mst where dist_code='" + dist_code + "' and statecode='28'");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("itidist_code");
            }
            return null;
        } catch (Exception e) {
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
                ////System.out.println("Errors at finalizations  in DistrictDao " + e);
            }

        }

        return null;
    }

    public ArrayList<DistrictForm> getRddDistrictDetails(String region_code,HttpServletRequest request) {
        Connection connection = DBC.DBConnection.getConnection(request);
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<DistrictForm> districtForms = new ArrayList<DistrictForm>();

        try {
            String query = "select * from conviner_login where region_code='" + region_code + "' order by dist_code";
            //////System.out.println("query is --------------------------------------------"+query);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DistrictForm districtForm = new DistrictForm();
                districtForm.setDist_code(resultSet.getString(1));

                //////System.out.println("Dist Code: "+resultSet.getString(1));
                districtForm.setDist_name(resultSet.getString(2));
                districtForms.add(districtForm);
                //return districtForms;
            }
            return districtForms;
        } catch (Exception e) {
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
                ////System.out.println("Errors at finalizations  in DistrictDao " + e);
            }

        }

        return null;
    }
}
