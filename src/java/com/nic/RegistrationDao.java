package com.nic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegistrationDao {

    public RegistrationDao() {
    }

    public String getItiCode(String dist_code, HttpServletRequest request) {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet = null;
        System.out.println("dist code-------------->"+dist_code);
        try {
            connection = DBC.DBConnection.getConnection(request);
            preparedStatement1 = connection.prepareStatement("select max(iti_code) from iti where dist_code=?");
            preparedStatement1.setString(1, dist_code);
            resultSet = preparedStatement1.executeQuery();

            if (resultSet.next()) {
                String iti_code = (resultSet.getString(1));

                if (iti_code == null || iti_code == "") {
                    String convert = "" + dist_code + "01";
                    iti_code = convert;
                    return iti_code;
                } else {
                    int iti_code1 = Integer.parseInt(iti_code.substring(2));
                    ////System.out.println("the iti code before is ------" + iti_code1);
                    if (iti_code1 <= 9) {
                        iti_code1++;
                        if (iti_code1 <= 9) {
                            iti_code = dist_code + '0' + iti_code1;
                        } else {
                            iti_code = dist_code + iti_code1;
                        }
                    } else {
                        iti_code1++;
                        iti_code = dist_code + iti_code1;
                    }

                    return iti_code;
                }
            }

        } catch (Exception e) {
            ////System.out.println("Error at getting iti_code in RegistrationDao class " + e);
            return null;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement1 != null) {
                    preparedStatement1.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                ////System.out.println("Errors at finalizations of regisration  in RegisrationDao " + e);
            }
        }
        return null;
    }

    public Timestamp getCurrentTimeStamp() {

        java.util.Date date = new Date();
        return new Timestamp(date.getTime());
    }

    public int checkForRankGeneration(HttpServletRequest request) {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet = null;
        try {
            connection = DBC.DBConnection.getConnection(request);
            preparedStatement1 = connection.prepareStatement("select count(regid) from ranks");
            resultSet = preparedStatement1.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            ////System.out.println("Error at getting iti_code in checking for Rank Generation or not class " + e);
            return 0;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement1 != null) {
                    preparedStatement1.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                ////System.out.println("Errors at finalizations of regisration  in RegisrationDao " + e);
            }
        }
        return 0;
    }

    public ArrayList<ItiForm> getItiDetails1(String code, HttpServletRequest request) {
        ArrayList<ItiForm> itiForms = new ArrayList<ItiForm>();
        ItiForm itiForm = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBC.DBConnection.getConnection(request);
            ////System.out.println("select distinct a.iti_code,b.iti_name from ititrade a,iti b where a.iti_code=b.iti_code and trade_freeze=0  and a.iti_code like '" + code + "%' order by iti_code");
            preparedStatement = connection.prepareStatement("select distinct a.iti_code,b.iti_name from ititrade a,iti b where a.iti_code=b.iti_code and trade_freeze=10  and a.iti_code like '" + code + "%' order by iti_code");

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                itiForm = new ItiForm();
                itiForm.setIti_code(resultSet.getString("iti_code"));
                itiForm.setIti_name(resultSet.getString("iti_name"));
                itiForms.add(itiForm);
            }
            return itiForms;

        } catch (Exception e) {
            ////System.out.println("Error at getting iti_code in checking for SeatMatrix Generation " + e);
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
                ////System.out.println("Errors at finalizations of regisration  in RegisrationDao " + e);
            }
        }
    }

    public ArrayList<CodingAndDecodingForm> getCodingAndDecodingDetails(String code, HttpServletRequest request) {
        ArrayList<ItiForm> itiForms = new ArrayList<ItiForm>();
        ItiForm itiForm = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBC.DBConnection.getConnection(request);
            ////System.out.println("select distinct a.iti_code,b.iti_name from ititrade a,iti b where a.iti_code=b.iti_code and trade_freeze=0  and a.iti_code like '" + code + "%' order by iti_code");
            preparedStatement = connection.prepareStatement("select distinct a.iti_code,b.iti_name from ititrade a,iti b where a.iti_code=b.iti_code and trade_freeze=0  and a.iti_code like '" + code + "%' order by iti_code");

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                itiForm = new ItiForm();
                itiForm.setIti_code(resultSet.getString("iti_code"));
                itiForm.setIti_name(resultSet.getString("iti_name"));
                itiForms.add(itiForm);
            }
            return null;

        } catch (Exception e) {
            ////System.out.println("Error at getting iti_code in checking for SeatMatrix Generation " + e);
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
                ////System.out.println("Errors at finalizations of regisration  in RegisrationDao " + e);
            }
        }
    }

    public int register(RegistrationForm registrationForm, int trno, HttpServletRequest request) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBC.DBConnection.getConnection(request);
            String desig_code[] = registrationForm.getDesig_code();
            int strength[] = registrationForm.getStrength();
            int vacant[] = registrationForm.getVacant();
            int fill[] = registrationForm.getFill();
            String keys = "Array['";
            String strength_values = "Array['";
            String vacant_values = "Array['";
            String fill_values = "Array['";
            for (int i = 0; i < desig_code.length; i++) {
                //////System.out.println("values are "+desig_code[i]+strength[i]+vacant[i]+fill[i]);
                keys = keys + desig_code[i] + "','";
                strength_values = strength_values + strength[i] + "','";
                vacant_values = vacant_values + vacant[i] + "','";
                fill_values = fill_values + fill[i] + "','";
            }
            strength_values = strength_values.substring(0, strength_values.length() - 2) + "]";
            vacant_values = vacant_values.substring(0, vacant_values.length() - 2) + "]";
            fill_values = fill_values.substring(0, fill_values.length() - 2) + "]";
            keys = keys.substring(0, keys.length() - 2) + "]";
            preparedStatement = connection.prepareStatement("insert into iti (iti_code,iti_name,userName,password,govt,iti_noniti,address,city_town,dist_code,pin_code,mobile,landlineNumber,email,principalName,tot_strength,capacity,year_est,description,olditicode,trno,land,vtp,vtp_regno,builtUP_area,noof_toilets,available_drinkingwater,noof_labs,noof_classrooms,examconducting_strength,strength,strength_vacant,strength_fill) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,hstore(" + keys + "," + strength_values + "),hstore(" + keys + "," + vacant_values + "),hstore(" + keys + "," + fill_values + "))");
            //String query="insert into iti (iti_code,iti_name,userName,password,govt,iti_noniti,address,city_town,dist_code,pin_code,mobile,landlineNumber,email,principalName,tot_strength,capacity,year_est,description,olditicode,trno,land,vtp,vtp_regno,builtUP_area,noof_toilets,available_drinkingwater,noof_labs,noof_classrooms,examconducting_strength,strength,strength_vacant,strength_fill) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,hstore("+keys+","+strength_values+"),hstore("+keys+","+vacant_values+"),hstore("+keys+","+fill_values+"))";
            //////System.out.println("query isssssssssssssssssssssss:"+query);
            preparedStatement.setString(1, registrationForm.getIti_code());
            preparedStatement.setString(2, registrationForm.getIti_name());
            preparedStatement.setString(3, registrationForm.getUserName());
            preparedStatement.setString(4, registrationForm.getPassword());
            preparedStatement.setString(5, registrationForm.getGovt());
            preparedStatement.setString(6, registrationForm.getIti_noniti());
            preparedStatement.setString(7, registrationForm.getAddress());
            preparedStatement.setString(8, registrationForm.getCity_town());
            preparedStatement.setString(9, registrationForm.getDist_code());
            //preparedStatement.setInt(8,Integer.parseInt(registrationForm.getMand_code()));
            preparedStatement.setInt(10, Integer.parseInt(registrationForm.getPin_code()));

            preparedStatement.setString(11, registrationForm.getMobile());

            preparedStatement.setString(12, registrationForm.getLandlineNumber());

            preparedStatement.setString(13, registrationForm.getEmail());

            preparedStatement.setString(14, registrationForm.getPrincipalName());

            if (registrationForm.getTot_strength() == null || registrationForm.getTot_strength() == "") {
                preparedStatement.setInt(15, 0);
            } else {
                preparedStatement.setInt(15, Integer.parseInt(registrationForm.getTot_strength()));
            }

            if (registrationForm.getCapacity() == null || registrationForm.getCapacity() == "") {
                preparedStatement.setInt(16, 0);
            } else {
                preparedStatement.setInt(16, Integer.parseInt(registrationForm.getCapacity()));
            }

            preparedStatement.setString(17, registrationForm.getYear_est());

            preparedStatement.setString(18, registrationForm.getDescription());
            preparedStatement.setString(19, registrationForm.getOld_iti_code());
            preparedStatement.setInt(20, trno);
//land,vtpregno,builtarea,nooftoilets,dkwateravailable,nooflabs,noofclassrooms,examroomcapacity
            preparedStatement.setString(21, registrationForm.getLand());
            preparedStatement.setBoolean(22, registrationForm.isVtp());
            preparedStatement.setString(23, registrationForm.getVtpregno());
            preparedStatement.setString(24, registrationForm.getBuiltarea());
            preparedStatement.setInt(25, registrationForm.getNooftoilets());
            preparedStatement.setBoolean(26, registrationForm.isDkwateravailable());
            preparedStatement.setInt(27, registrationForm.getNooflabs());
            preparedStatement.setInt(28, registrationForm.getNoofclassrooms());
            preparedStatement.setInt(29, registrationForm.getExamroomcapacity());

            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            ////System.out.println("Error at register" + e);
            return 0;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                ////System.out.println("Errors at finalizations of regisration  in RegisrationDao " + e);
            }
        }
    }

    public int update(ItiDetailsForm itiDetailsForm, int trno, HttpServletRequest request) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
             connection = DBC.DBConnection.getConnection(request);
            preparedStatement = connection.prepareStatement("update  iti set iti_name=?,password=?,govt=?,iti_noniti=?,address=?,city_town=?,dist_code=?,pin_code=?,mobile=?,landlineNumber=?,email=?,principalName=?,tot_strength=?,capacity=?,year_est=?,description=?,trno=? where username=?");
            //preparedStatement.setString(1,itiDetailsForm.getIti_code());
            preparedStatement.setString(1, itiDetailsForm.getIti_name());
            preparedStatement.setString(17, itiDetailsForm.getUserName());
            preparedStatement.setString(2, itiDetailsForm.getPassword());
            preparedStatement.setString(3, itiDetailsForm.getGovt());
            preparedStatement.setString(4, itiDetailsForm.getIti_noniti());
            preparedStatement.setString(5, itiDetailsForm.getAddress());
            preparedStatement.setString(6, itiDetailsForm.getCity_town());
            preparedStatement.setString(7, itiDetailsForm.getDist_code());
            //preparedStatement.setInt(6,Integer.parseInt(itiDetailsForm.getMand_code()));
            preparedStatement.setInt(8, Integer.parseInt(itiDetailsForm.getPin_code()));

            preparedStatement.setString(9, itiDetailsForm.getMobile());

            preparedStatement.setString(10, itiDetailsForm.getLandlineNumber());

            preparedStatement.setString(11, itiDetailsForm.getEmail());

            preparedStatement.setString(12, itiDetailsForm.getPrincipalName());

            if (itiDetailsForm.getTot_strength() == null || itiDetailsForm.getTot_strength() == "") {
                preparedStatement.setInt(13, 0);
            } else {
                preparedStatement.setInt(13, Integer.parseInt(itiDetailsForm.getTot_strength()));
            }

            if (itiDetailsForm.getCapacity() == null || itiDetailsForm.getCapacity() == "") {
                preparedStatement.setInt(14, 0);
            } else {
                preparedStatement.setInt(14, Integer.parseInt(itiDetailsForm.getCapacity()));
            }

            if (itiDetailsForm.getYear_est() == null || itiDetailsForm.getYear_est() == "") {
                preparedStatement.setInt(15, 0);
            } else {
                preparedStatement.setInt(15, Integer.parseInt(itiDetailsForm.getYear_est()));
            }
            preparedStatement.setString(16, itiDetailsForm.getDescription());
            preparedStatement.setInt(17, trno);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            ////System.out.println(e);
            return 0;
        } finally {
            try {
                if(resultSet!=null){
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                ////System.out.println("Errors at finalizations of updation  in RegisrationDao " + e);
            }
        }
    }

    public int updateCodingAndDecodingPaperCode(String iti_code, String trade_code, String admnum, String subcode, int paper_code, int trno, HttpServletRequest request) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBC.DBConnection.getConnection(request);
            preparedStatement = connection.prepareStatement("update exam_marks_2013 set paper_code=?, trno=? where iti_code=? and trade_code=? and subcode=? and adnum=? ");
            preparedStatement.setInt(1, paper_code);
            preparedStatement.setInt(2, trno);
            preparedStatement.setString(3, iti_code.trim());
            preparedStatement.setString(4, trade_code.trim());
            preparedStatement.setString(5, subcode.trim());
            preparedStatement.setString(6, admnum.trim());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            ////System.out.println(e);
            return 0;
        } finally {
            try {
                if(resultSet!=null){
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                ////System.out.println("Errors at finalizations of updation  in RegisrationDao " + e);
            }
        }
    }

    public int insertCodingAndDecodingPaperCodeArch(String iti_code, String trade_code, String admnum, String subcode, int paper_code, int trno, HttpServletRequest request) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBC.DBConnection.getConnection(request);
            preparedStatement = connection.prepareStatement("insert into exam_marks_2013_arch (iti_code,trade_code,subcode,adnum,paper_code,trno) values (?,?,?,?,?,?)");
            preparedStatement.setString(1, iti_code);
            preparedStatement.setString(2, trade_code.trim());
            preparedStatement.setString(3, subcode.trim());
            preparedStatement.setString(4, admnum.trim());
            preparedStatement.setInt(5, paper_code);
            preparedStatement.setInt(6, trno);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            ////System.out.println(e);
            return 0;
        } finally {
            try {
                if(resultSet!=null){
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                ////System.out.println("Errors at finalizations of updation  in RegisrationDao " + e);
            }
        }
    }

    public int itiArch(ItiDetailsForm itiDetailsForm, int trno, HttpServletRequest request) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBC.DBConnection.getConnection(request);
            preparedStatement = connection.prepareStatement("insert into iti_arch (iti_code,iti_name,userName,password,govt,iti_noniti,address,city_town,dist_code,pin_code,mobile,landlineNumber,email,principalName,tot_strength,capacity,year_est,description,trno) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, itiDetailsForm.getIti_code());
            preparedStatement.setString(2, itiDetailsForm.getIti_name());
            preparedStatement.setString(3, itiDetailsForm.getUserName());
            preparedStatement.setString(4, itiDetailsForm.getPassword());
            preparedStatement.setString(5, itiDetailsForm.getGovt());
            preparedStatement.setString(6, itiDetailsForm.getIti_noniti());
            preparedStatement.setString(7, itiDetailsForm.getAddress());
            preparedStatement.setString(8, itiDetailsForm.getCity_town());
            preparedStatement.setString(9, itiDetailsForm.getDist_code());
            //preparedStatement.setInt(8,Integer.parseInt(itiDetailsForm.getMand_code()));
            preparedStatement.setInt(10, Integer.parseInt(itiDetailsForm.getPin_code()));

            preparedStatement.setString(11, itiDetailsForm.getMobile());

            preparedStatement.setString(12, itiDetailsForm.getLandlineNumber());

            preparedStatement.setString(13, itiDetailsForm.getEmail());

            preparedStatement.setString(14, itiDetailsForm.getPrincipalName());

            if (itiDetailsForm.getTot_strength() == null || itiDetailsForm.getTot_strength() == "") {
                preparedStatement.setInt(15, 0);
            } else {
                preparedStatement.setInt(15, Integer.parseInt(itiDetailsForm.getTot_strength()));
            }

            if (itiDetailsForm.getCapacity() == null || itiDetailsForm.getCapacity() == "") {
                preparedStatement.setInt(16, 0);
            } else {
                preparedStatement.setInt(16, Integer.parseInt(itiDetailsForm.getCapacity()));
            }

            preparedStatement.setString(17, itiDetailsForm.getYear_est());

            preparedStatement.setString(18, itiDetailsForm.getDescription());
            preparedStatement.setInt(19, trno);

            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            ////System.out.println(e);
            return 0;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                ////System.out.println("Errors at finalizations of itiArch  in RegisrationDao " + e);
            }
        }
    }

    public int getRegistrationDetails(String code, String value, String rank, HttpServletRequest request) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ItiAdmissionForm itiAdmissionForm = new ItiAdmissionForm();
        try {
            connection = DBC.DBConnection.getConnection(request);

           // String query = "select a.regid,caste from ranks a,application b where a.regid=b.regid   and " + code + "='" + value + "' and rank='" + rank + "'";
            String query = "select a.regid,caste from ranks a,application b where a.regid=b.regid   and " + code + " = ? and rank= ?";
         
 
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.setString(1,value);
         preparedStatement.setString(2,rank);
         System.out.println("query is--------1111111122222223---------" + preparedStatement);
            resultSet = preparedStatement.executeQuery();
               
            if (resultSet.next()) {
                             
                if(String.valueOf(resultSet.getInt("regid"))==null)
                {
                    System.out.println("regid is returning null-->"+resultSet.getInt("regid"));
                    return 0;
                }else{
                  return  resultSet.getInt("regid");
                }
            } else {
                return 0;
            }
        } catch (Exception e) {
            ////System.out.println("Error at geting Registration details from of getregistrationDetails()  in RegistrationDao " + e);
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
                ////System.out.println("Errors at finalizations of geting Registration details from of getregistrationDetails()  in RegisrationDao " + e);
            }
        }

        return 0;
    }

    public ItiAdmissionForm getApplicationDetails(int regid, HttpServletRequest request) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ItiAdmissionForm itiAdmissionForm = new ItiAdmissionForm();
        try {
            ////System.out.println("the regid is---------------------------------" + regid);
            connection = DBC.DBConnection.getConnection(request);
            ////System.out.println("select name,fname,mname,addr,phno,adarno,gender,caste,ssc_passed,inter_passed,inter_marks,local,pincode,dob,regid,phc,exservice from application where regid=" + regid);
//            preparedStatement = connection.prepareStatement("select name,fname,mname,addr,phno,adarno,gender,caste,ssc_passed,inter_passed,inter_marks,local,pincode,dob,regid,phc,exservice from application where regid='" + regid + "'");
            preparedStatement = connection.prepareStatement("select name,fname,mname,addr,phno,adarno,gender,caste,ssc_passed,inter_passed,inter_marks,local,pincode,dob,regid,phc,exservice from application where regid=?");
          
preparedStatement.setInt(1,regid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                itiAdmissionForm.setName(resultSet.getString("name"));
                itiAdmissionForm.setFatherName(resultSet.getString("fname"));
                itiAdmissionForm.setMname(resultSet.getString("mname"));
                itiAdmissionForm.setPhoneNumber(resultSet.getString("phno"));
                itiAdmissionForm.setAddress(resultSet.getString("addr"));
                itiAdmissionForm.setAdarNo(resultSet.getString("adarno"));
                itiAdmissionForm.setCast(resultSet.getString("caste"));
                itiAdmissionForm.setSex(resultSet.getString("gender"));
                itiAdmissionForm.setSsc_passed(resultSet.getBoolean("ssc_passed"));
                itiAdmissionForm.setInterPassed(resultSet.getBoolean("inter_passed"));
                itiAdmissionForm.setInter_marks(resultSet.getInt("inter_marks"));
                itiAdmissionForm.setLocal(resultSet.getString("local"));
                itiAdmissionForm.setPin_code(resultSet.getInt("pincode"));
                itiAdmissionForm.setDob(resultSet.getString("dob"));
                itiAdmissionForm.setRegid(resultSet.getInt("regid"));
                boolean phc = resultSet.getBoolean("phc");
                if (phc == true) {
                    itiAdmissionForm.setPhc("Yes");
                } else {
                    itiAdmissionForm.setPhc("No");
                }

                boolean exservice = resultSet.getBoolean("exservice");
                if (exservice == true) {
                    itiAdmissionForm.setExservice("Yes");
                } else {
                    itiAdmissionForm.setExservice("No");
                }

                
                //connection.close();
                return itiAdmissionForm;
            }

        } catch (Exception e) {
            ////System.out.println("Error at geting appication details from application table in RegistrationDao " + e);
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
                ////System.out.println("Errors at finalizations of getApplicationDetails()  in RegisrationDao " + e);
            }
        }
        return null;
    }

    //Check For Vacencies before taking admisssions
    public int checkForVacencies(String iti_code, int trade_code, String category_code, HttpServletRequest request) {
        Connection connection1 = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        try {
            System.out.println("select strength_vacant from iti_seatmatrix where iti_code='" + iti_code + "' and trade_code='" + trade_code + "' and category_code='" + category_code + "'");
            connection1 = DBC.DBConnection.getConnection(request);

            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection1);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection1);

            preparedStatement1 = connection1.prepareStatement("select strength_vacant->'" + category_code + "' as strength_vacant from iti_seatmatrix where iti_code=? and trade_code=? and phase='" + phase + "' and year='" + year + "' ");
            preparedStatement1.setString(1, iti_code);
            preparedStatement1.setInt(2, trade_code);
            //preparedStatement1.setString(3, category_code);
            System.out.println("select strength_vacant->'" + category_code + "' as strength_vacant from iti_seatmatrix where iti_code='"+iti_code+"' and trade_code='"+trade_code+"' and phase='" + phase + "' and year='" + year + "' ");
            //System.out.println(category_code);
            //System.out.println(trade_code);
            resultSet1 = preparedStatement1.executeQuery();
            
            
            if (resultSet1.next()) {
                System.out.println("strength vacant getting" + resultSet1.getInt("strength_vacant"));
                return resultSet1.getInt("strength_vacant");

            } else {
                //System.out.println("no rows");
            }
        } catch (Exception e) {
            //System.out.println("..Error inn getting Vacencies in seat matrix" + e);
        } finally {
            try {
                if (resultSet1 != null) {
                    resultSet1.close();
                }
                if (preparedStatement1 != null) {
                    preparedStatement1.close();
                }
                if (connection1 != null) {
                    connection1.close();
                }
            } catch (Exception e) {
                ////System.out.println("Errors at finalizations of getApplicationDetails()  in RegisrationDao " + e);
            }
        }
        return 0;
    }

    public ItiDetailsForm getItiDetails(String iti_code, HttpServletRequest request) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {

            connection = DBC.DBConnection.getConnection(request);
            String query = "select iti_code,iti_name,userName,password,govt,iti_noniti,address,city_town,dist_code,mand_code,pin_code,mobile,landlineNumber,email,principalName,tot_strength,capacity,year_est,description from iti where iti_code='" + iti_code + "' ";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            ItiDetailsForm itiDetails = new ItiDetailsForm();
            //RegistrationForm registrationForm=new RegistrationForm();
            if (resultSet.next()) {
                itiDetails.setIti_code(resultSet.getString(1));
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
                itiDetails.setCapacity(Integer.toString(resultSet.getInt(17)));
                itiDetails.setYear_est(resultSet.getString(18));
                return itiDetails;
            }

        } catch (Exception e) {
            ////System.out.println("Error at getting itiDetails from getItiDetails() of RegistrationDao " + e);
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
                ////System.out.println("Errors at finalizations getting itiDetails from getItiDetails() of RegistrationDao" + e);
            }

        }
        return null;
    }

    //getting all trades based on given iti codes
    /*public ArrayList<TradeForm> getTradeDetails(String iti_code){
     ArrayList<TradeForm> tradeForms=new ArrayList<TradeForm>();
     Connection connection=null;
     PreparedStatement preparedStatement=null;
     ResultSet resultSet=null;
     TradeForm tradeForm;
     try {
     connection=DBC.DBConnection.getConnection(request);
     preparedStatement=connection.prepareStatement("select a.trade_code,a.strength,a.strength_fill,a.strength_vacant,b.trade_short,b.trade_name from iti_seatmatrix a,ititrade_master b where a.trade_code=b.trade_code and a.iti_code=? and a.strength>0 and a.strength_vacant>0 and a.category_code='01'");
     preparedStatement.setString(1, iti_code);
     resultSet= preparedStatement.executeQuery();
     while(resultSet.next()){
     tradeForm=new TradeForm();
     tradeForm.setTrade_code(""+resultSet.getInt("trade_code"));
     tradeForm.setTrade_short(resultSet.getString("trade_short"));
     tradeForm.setTrade_name(resultSet.getString("trade_name"));
     tradeForm.setStrength(resultSet.getInt("strength"));
     tradeForm.setStrength_fill(resultSet.getInt("strength_fill"));
     tradeForm.setStrength_vacant(resultSet.getInt("strength_vacant"));
     tradeForms.add(tradeForm);
     }
     return tradeForms;
     } catch (Exception e) {
     ////System.out.println("Error at getting Tradedetails in getTradeDetails() of  RegistationDao"+e);
     }
     finally{
     try{
     if(resultSet!=null)
     resultSet.close();
                            
     if(preparedStatement!=null)
     preparedStatement.close();
                            
     if(connection!=null)
     connection.close();
     }
     catch(Exception e){
     ////System.out.println("Errors at finalizations at getting Tradedetails in getTradeDetails() of  RegistationDao "+e);
     }
                        
     }  
     return null;
     }*/
    public String getCastName(String caste_code, HttpServletRequest request) {
        Connection connection = null;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String caste_name = beans.MyUtil.getOptionValue("caste_master", "caste_code", "caste_name", caste_code, connection);
            return caste_name;
        } catch (Exception e) {
            ////System.out.println("Error at getting getCasteName()  " + e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    public String getCategoryCode(String category_name, HttpServletRequest request) {
        Connection connection = null;
        try {
            System.out.println("category_name------>"+category_name);
            connection = DBC.DBConnection.getConnection(request);
            String category_code = beans.MyUtil.getOptionValue("category_mast", "category_name", "category_code", category_name, connection);
            return category_code;
        } catch (Exception e) {
            ////System.out.println("Error at getting getCategoryCode()" + e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    public String getOcCastName(HttpServletRequest request) {
        Connection connection = null;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String category_code = beans.MyUtil.getOptionValue("caste_master", "caste_code", "caste_name", "OC", connection);
            return category_code;
        } catch (Exception e) {
            ////System.out.println("Error at getting getCategoryCode()" + e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    public CheckedAdmissionDetails checkAdmited_or_Not(int regid, HttpServletRequest request) {

        CheckedAdmissionDetails checkedAdmissionDetails = new CheckedAdmissionDetails();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String year_of_admission = DBC.RoleBasedOperations.Current_AcademicYear(request);
            connection = DBC.DBConnection.getConnection(request);
            ////System.out.println("select c.iti_name,a.iti_code,a.regid,b.trade_name,a.adm_num from admissions.iti_admissions a,ititrade_master b,iti c  where a.regid=? and a.trade_code=b.trade_code and a.iti_code=c.iti_code ");
            preparedStatement = connection.prepareStatement("select c.iti_name,a.iti_code,a.regid,b.trade_name,a.adm_num from admissions.iti_admissions a,ititrade_master b,iti c  where a.regid=? and a.trade_code=b.trade_code and a.iti_code=c.iti_code ");
            preparedStatement.setString(1, "" + regid);
            ////System.out.println(regid);
            System.out.println("admisiion check--------------->"+preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                checkedAdmissionDetails.setRegid(resultSet.getInt("regid"));
                checkedAdmissionDetails.setIti_name(resultSet.getString("iti_name"));
                checkedAdmissionDetails.setIti_code(resultSet.getString("iti_code"));
                checkedAdmissionDetails.setTrade_name(resultSet.getString("trade_name"));
                checkedAdmissionDetails.setAdm_num(resultSet.getString("adm_num"));
            } else {
                return null;
            }
            return checkedAdmissionDetails;
        } catch (Exception e) {
            ////System.out.println("...Error at getting Tradedetails in getTradeDetails(String trade_code) of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting Tradedetails in getTradeDetails(String trade_code) of  RegistationDao " + e);
            }

        }
        return null;
    }

    public int updateSeatmatrixAfterAdmission(String iti_code, String category_code, int trade_code, String tablename, String userName, String ip, Timestamp tp, HttpServletRequest request) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBC.DBConnection.getConnection(request);
            preparedStatement = connection.prepareStatement("update " + tablename + " set strength_fill=strength_fill+1,strength_vacant=strength_vacant-1 where iti_code=? and category_code=? and trade_code=? ");
            ////System.out.println("update " + tablename + "set strength_fill=strength_fill+1,strength_vacant=strength_vacant-1 where iti_code='" + iti_code + "' and category_code='" + category_code + "' and trade_code='" + trade_code + "' ");
            preparedStatement.setString(1, iti_code);
            preparedStatement.setString(2, category_code);
            preparedStatement.setInt(3, trade_code);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.getStackTrace();
            ////System.out.println("..Error at getting Tradedetails in getTradeDetails(String trade_code) of  RegistationDao" + e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                ////System.out.println("Errors at finalizations at getting Tradedetails in getTradeDetails(String trade_code) of  RegistationDao " + e);
            }

        }
        return 0;
    }

    //geting trade name,trade short based on trade code
    public TradeForm getTradeDetails(String trade_code, HttpServletRequest request) {
        TradeForm tradeForm = new TradeForm();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBC.DBConnection.getConnection(request);
            preparedStatement = connection.prepareStatement("select trade_short,trade_name from ititrade_master where trade_code=? ");
            preparedStatement.setInt(1, Integer.parseInt(trade_code));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tradeForm.setTrade_short(resultSet.getString("trade_short"));
                tradeForm.setTrade_name(resultSet.getString("trade_name"));
            }
            return tradeForm;
        } catch (Exception e) {
            ////System.out.println(".Error at getting Tradedetails in getTradeDetails(String trade_code) of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting Tradedetails in getTradeDetails(String trade_code) of  RegistationDao " + e);
            }

        }
        return null;
    }

    //Getting All Iti codes and names in District
    public ArrayList<All_Itis_DistricForm> getAll_Itis_District(String dist_code, HttpServletRequest request) {
        ArrayList<All_Itis_DistricForm> all_Itis_DistricForms = new ArrayList<All_Itis_DistricForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        All_Itis_DistricForm all_Itis_DistricForm;
        try {
            connection = DBC.DBConnection.getConnection(request);
            ////System.out.println("hello 112");
            preparedStatement = connection.prepareStatement("select iti_code,iti_name,dist_code from iti where dist_code=? order by iti_code");
            preparedStatement.setString(1, dist_code);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                all_Itis_DistricForm = new All_Itis_DistricForm();
                all_Itis_DistricForm.setIti_code(resultSet.getString("iti_code"));
                all_Itis_DistricForm.setIti_name(resultSet.getString("iti_name"));
                all_Itis_DistricForm.setDist_code(resultSet.getString("dist_code"));

                all_Itis_DistricForms.add(all_Itis_DistricForm);
            }
            return all_Itis_DistricForms;
        } catch (Exception e) {
            ////System.out.println("hello 113");
            ////System.out.println("Error at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }

    //==================================Getting Iti CouncilDetailsLogic Start===================================   
    //---------------------------------------------------------Logic for General POOL-----------------------------------------------
    public ArrayList<CouncilDetailsForm> getGeneralPoolDetails(String iti_code, String ocCode, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> generalCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm generalCouncilDetailsForm;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            ////System.out.println("oc code is : "+ocCode);
            ////System.out.println("select a.trade_code,a.strength->'" + ocCode + "' as strength,a.strength_fill->'" + ocCode + "' as strength_fill,a.strength_vacant->'" + ocCode + "' as strength_vacant,b.trade_short,b.strength,c.trade_name from iti_seatmatrix a,ititrade b,ititrade_master c where a.trade_code=b.trade_code and b.iti_code=? and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0  and b.trade_short=c.trade_short and a.phase='" + phase + "' and a.year='" + year + "' order by b.trade_code");
            preparedStatement = connection.prepareStatement("select a.trade_code,a.strength->'" + ocCode + "' as strength,a.strength_fill->'" + ocCode + "' as strength_fill,a.strength_vacant->'" + ocCode + "' as strength_vacant,b.trade_short,b.strength,c.trade_name from iti_seatmatrix a,ititrade b,ititrade_master c where a.trade_code=b.trade_code and b.iti_code=? and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0  and b.trade_short=c.trade_short and a.phase='" + phase + "' and a.year='" + year + "' order by b.trade_code");
            preparedStatement.setString(1, iti_code);
            //preparedStatement.setString(2, ocCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                generalCouncilDetailsForm = new CouncilDetailsForm();
                generalCouncilDetailsForm.setTrade_code(String.valueOf(resultSet.getInt("trade_code")));
                generalCouncilDetailsForm.setGeneralStrength(resultSet.getInt(2));
                generalCouncilDetailsForm.setGeneralStrength_fill(resultSet.getInt("strength_fill"));
                generalCouncilDetailsForm.setGeneralStrength_vacant(resultSet.getInt("strength_vacant"));
                generalCouncilDetailsForm.setTrade_short(resultSet.getString("trade_short"));
                generalCouncilDetailsForm.setTotalStrength(resultSet.getInt(6));
                generalCouncilDetailsForm.setTrade_name(resultSet.getString("trade_name"));
                generalCouncilDetailsForms.add(generalCouncilDetailsForm);
            }
            return generalCouncilDetailsForms;
        } catch (Exception e) {
            ////System.out.println("hello 11 is done");
            ////System.out.println("hello... Error at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }
    //==================================Getting Iti CouncilDetailsLogic Start===================================   
    //---------------------------------------------------------Logic for General Category-----------------------------------------------

    public ArrayList<CouncilDetailsForm> getGeneralCouncilDetails(String iti_code, String ocCode, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> generalCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm generalCouncilDetailsForm;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            preparedStatement = connection.prepareStatement("select a.trade_code,a.strength->'" + ocCode + "' as strength,a.strength_fill->'" + ocCode + "' as strength_fill,a.strength_vacant->'" + ocCode + "' as strength_vacant,b.trade_short,b.strength,c.trade_name from iti_seatmatrix a,ititrade b,ititrade_master c where a.trade_code=b.trade_code and b.iti_code=? and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0  and b.trade_short=c.trade_short and a.phase='" + phase + "' and a.year='" + year + "' order by b.trade_code");
            System.out.println("select a.trade_code,a.strength->'" + ocCode + "' as strength,a.strength_fill->'" + ocCode + "' as strength_fill,a.strength_vacant->'" + ocCode + "' as strength_vacant,b.trade_short,b.strength,c.trade_name from iti_seatmatrix a,ititrade b,ititrade_master c where a.trade_code=b.trade_code and b.iti_code=? and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0  and b.trade_short=c.trade_short and a.phase='" + phase + "' and a.year='" + year + "' order by b.trade_code");
            preparedStatement.setString(1, iti_code);
            //preparedStatement.setString(2, ocCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                generalCouncilDetailsForm = new CouncilDetailsForm();
                generalCouncilDetailsForm.setTrade_code(String.valueOf(resultSet.getInt("trade_code")));
                generalCouncilDetailsForm.setGeneralStrength(resultSet.getInt(2));
                generalCouncilDetailsForm.setGeneralStrength_fill(resultSet.getInt("strength_fill"));
                generalCouncilDetailsForm.setGeneralStrength_vacant(resultSet.getInt("strength_vacant"));
                generalCouncilDetailsForm.setTrade_short(resultSet.getString("trade_short"));
                generalCouncilDetailsForm.setTotalStrength(resultSet.getInt(6));
                generalCouncilDetailsForm.setTrade_name(resultSet.getString("trade_name"));
                generalCouncilDetailsForms.add(generalCouncilDetailsForm);
            }
            return generalCouncilDetailsForms;
        } catch (Exception e) {
            ////System.out.println("hello 11 is done");
            ////System.out.println("hello... Error at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }
    //---------------------------------------------Logic Phc Start Category-------------------------------------------------

    public ArrayList<CouncilDetailsForm> getPhcCouncilDetails(String iti_code, String phCode, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> phcCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm phcCouncilDetailsForm;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            preparedStatement = connection.prepareStatement("select a.strength->'" + phCode + "' as strength,a.strength_fill->'" + phCode + "' as strength_fill,a.strength_vacant->'" + phCode + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code=? and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.trade_code");
            preparedStatement.setString(1, iti_code);
            // preparedStatement.setString(2, phCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                phcCouncilDetailsForm = new CouncilDetailsForm();
                phcCouncilDetailsForm.setPhcStrength(resultSet.getInt("strength"));
                phcCouncilDetailsForm.setPhcStrength_fill(resultSet.getInt("strength_fill"));
                phcCouncilDetailsForm.setPhcStrength_vacant(resultSet.getInt("strength_vacant"));
                phcCouncilDetailsForms.add(phcCouncilDetailsForm);
            }
            return phcCouncilDetailsForms;
        } catch (Exception e) {
            ////System.out.println("Error at getting CouncilDetailsForm in getExserviceCouncilDetails() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }
    //---------------------------------------------------------Logic Phc End Category-----------------------------------------------

    //---------------------------------------------Logic  for PHC women  Category Start-------------------------------------------------
    public ArrayList<CouncilDetailsForm> getPhc_wCouncilDetails(String iti_code, String ph_wCode, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> phcCouncilDetailsForm_ws = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm phcCouncilDetailsForm_w;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            preparedStatement = connection.prepareStatement("select a.strength->'" + ph_wCode + "' as strength,a.strength_fill->'" + ph_wCode + "' as strength_fill,a.strength_vacant->'" + ph_wCode + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code=? and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.trade_code");
            preparedStatement.setString(1, iti_code);
            // preparedStatement.setString(2, ph_wCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                phcCouncilDetailsForm_w = new CouncilDetailsForm();
                phcCouncilDetailsForm_w.setPhcStrength_w(resultSet.getInt("strength"));
                phcCouncilDetailsForm_w.setPhcStrength_fill_w(resultSet.getInt("strength_fill"));
                phcCouncilDetailsForm_w.setPhcStrength_vacant_w(resultSet.getInt("strength_vacant"));
                phcCouncilDetailsForm_ws.add(phcCouncilDetailsForm_w);
            }
            return phcCouncilDetailsForm_ws;
        } catch (Exception e) {
            ////System.out.println("Error at getting CouncilDetailsForm in getExserviceCouncilDetails() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }
    //---------------------------------------------------------Logic  Phc for women End Category-----------------------------------------------

//---------------------------------------------Logic for General Exservice Start Category-------------------------------------------------
    public ArrayList<CouncilDetailsForm> getExserviceCouncilDetails(String iti_code, String exCode, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> exserviceCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm exserviceCouncilDetailsForm;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            preparedStatement = connection.prepareStatement("select a.strength->'" + exCode + "' as strength,a.strength_fill->'" + exCode + "' as strength_fill,a.strength_vacant->'" + exCode + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code=? and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.trade_code");
            preparedStatement.setString(1, iti_code);
            //preparedStatement.setString(2, exCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exserviceCouncilDetailsForm = new CouncilDetailsForm();
                exserviceCouncilDetailsForm.setExserviceStrength(resultSet.getInt("strength"));
                exserviceCouncilDetailsForm.setExserviceStrength_fill(resultSet.getInt("strength_fill"));
                exserviceCouncilDetailsForm.setExserviceStrength_vacant(resultSet.getInt("strength_vacant"));
                exserviceCouncilDetailsForms.add(exserviceCouncilDetailsForm);
            }
            return exserviceCouncilDetailsForms;
        } catch (Exception e) {
            ////System.out.println("Error at getting CouncilDetailsForm in getExserviceCouncilDetails() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }
    //---------------------------------------------------------Logic General Ex service End Category-----------------------------------------------

    //---------------------------------------------Logic for General Exservice Start Category-------------------------------------------------
    public ArrayList<CouncilDetailsForm> getExservice_wCouncilDetails(String iti_code, String ph_wCode, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> exserviceCouncilDetailsForm_ws = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm exserviceCouncilDetailsForm_w;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            preparedStatement = connection.prepareStatement("select a.strength->'" + ph_wCode + "' as strength,a.strength_fill->'" + ph_wCode + "' as strength_fill,a.strength_vacant->'" + ph_wCode + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code=? and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.trade_code");
            preparedStatement.setString(1, iti_code);
            // preparedStatement.setString(2, ph_wCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exserviceCouncilDetailsForm_w = new CouncilDetailsForm();
                exserviceCouncilDetailsForm_w.setExserviceStrength_w(resultSet.getInt("strength"));
                exserviceCouncilDetailsForm_w.setExserviceStrength_fill_w(resultSet.getInt("strength_fill"));
                exserviceCouncilDetailsForm_w.setExserviceStrength_vacant_w(resultSet.getInt("strength_vacant"));
                exserviceCouncilDetailsForm_ws.add(exserviceCouncilDetailsForm_w);
            }
            return exserviceCouncilDetailsForm_ws;
        } catch (Exception e) {
            ////System.out.println("Error at getting CouncilDetailsForm in getExserviceCouncilDetails() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }
    //---------------------------------------------------------Logic General Ex service End Category-----------------------------------------------

    //---------------------------------------------------------Logic for General Women Category-----------------------------------------------
    public ArrayList<CouncilDetailsForm> getGeneral_wCouncilDetails(String iti_code, String category_code, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> generalCouncilDetailsForm_ws = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm generalCouncilDetailsForm_w;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            preparedStatement = connection.prepareStatement("select a.strength->'" + category_code + "' as strength,a.strength_fill->'" + category_code + "' as strength_fill,a.strength_vacant->'" + category_code + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code=? and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.trade_code");
            preparedStatement.setString(1, iti_code);
            //preparedStatement.setString(2, category_code);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                generalCouncilDetailsForm_w = new CouncilDetailsForm();
                generalCouncilDetailsForm_w.setGeneralStrength_w(resultSet.getInt("strength"));
                generalCouncilDetailsForm_w.setGeneralStrength_fill_w(resultSet.getInt("strength_fill"));
                generalCouncilDetailsForm_w.setGeneralStrength_vacant_w(resultSet.getInt("strength_vacant"));
                generalCouncilDetailsForm_ws.add(generalCouncilDetailsForm_w);
            }
            return generalCouncilDetailsForm_ws;
        } catch (Exception e) {
            ////System.out.println("....Error at getting CouncilDetailsForm in getGeneralCouncilDetails_w() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }

    //---------------------------------------------------------Logic for Reservation Men Category-----------------------------------------------
    public ArrayList<CouncilDetailsForm> getReservationCouncilDetails(String iti_code, String category_code, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> reservationCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm reservationCouncilDetailsForm;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            preparedStatement = connection.prepareStatement("select a.strength->'" + category_code + "' as strength,a.strength_fill->'" + category_code + "' as strength_fill,a.strength_vacant->'" + category_code + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code=? and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.trade_code");
            preparedStatement.setString(1, iti_code);
            //preparedStatement.setString(2, category_code);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reservationCouncilDetailsForm = new CouncilDetailsForm();
                reservationCouncilDetailsForm.setReservationStrength(resultSet.getInt("strength"));
                reservationCouncilDetailsForm.setReservationStrength_fill(resultSet.getInt("strength_fill"));
                reservationCouncilDetailsForm.setReservationStrength_vacant(resultSet.getInt("strength_vacant"));
                reservationCouncilDetailsForms.add(reservationCouncilDetailsForm);
            }
            return reservationCouncilDetailsForms;
        } catch (Exception e) {
            ////System.out.println(".....Error at getting CouncilDetailsForm in getGeneralCouncilDetails_w() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }

    //---------------------------------------------------------Logic for Reservation Women Category-----------------------------------------------
    public ArrayList<CouncilDetailsForm> getReservation_wCouncilDetails(String iti_code, String category_code, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> reservationCouncilDetailsForm_ws = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm reservationCouncilDetailsForm_w;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            preparedStatement = connection.prepareStatement("select a.strength->'" + category_code + "' as strength,a.strength_fill->'" + category_code + "' as strength_fill,a.strength_vacant->'" + category_code + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code=? and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.trade_code");
            preparedStatement.setString(1, iti_code);
            // preparedStatement.setString(2, category_code);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reservationCouncilDetailsForm_w = new CouncilDetailsForm();
                reservationCouncilDetailsForm_w.setReservationStrength_w(resultSet.getInt("strength"));
                reservationCouncilDetailsForm_w.setReservationStrength_fill_w(resultSet.getInt("strength_fill"));
                reservationCouncilDetailsForm_w.setReservationStrength_vacant_w(resultSet.getInt("strength_vacant"));
                reservationCouncilDetailsForm_ws.add(reservationCouncilDetailsForm_w);
            }
            return reservationCouncilDetailsForm_ws;
        } catch (Exception e) {
            ////System.out.println("......kk Error at getting CouncilDetailsForm in getGeneralCouncilDetails_w() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }

    //==================================Getting Iti CouncilDetails Logic End===================================   
    //==================================Code for District CouncilDetails Start====================================
    //---------------------------------------------------------Logic for General Category-----------------------------------------------
    public ArrayList<CouncilDetailsForm> getGeneralCouncilDistrictDetails(String dist_code, String ocCode, HttpServletRequest request) {//gopi phc
        ArrayList<CouncilDetailsForm> generalCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm generalCouncilDetailsForm;
        System.out.println("dist code"+dist_code+"occode--"+ocCode);
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            //////System.out.println("hello 11");
            //////System.out.println("select d.iti_name,b.iti_code,a.trade_code,a.strength->'"+ocCode+"' as strength,a.strength_fill->'"+ocCode+"' as strength_fill,a.strength_vacant->'"+ocCode+"' as strength_vacant,b.trade_short,b.strength,c.trade_name from iti_seatmatrix a,ititrade b,ititrade_master c,iti d where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%" + "' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and b.trade_short=c.trade_short and b.iti_code=d.iti_code order by b.iti_code,b.trade_code");
            preparedStatement = connection.prepareStatement("select d.iti_name,b.iti_code,a.trade_code,a.strength->'" + ocCode + "' as strength,a.strength_fill->'" + ocCode + "' as strength_fill,a.strength_vacant->'" + ocCode + "' as strength_vacant,b.trade_short,b.strength,c.trade_name from iti_seatmatrix a,ititrade b,ititrade_master c,iti d where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%" + "' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and b.trade_short=c.trade_short and b.iti_code=d.iti_code and a.phase='" + phase + "' and a.year='" + year + "' order by b.iti_code,b.trade_code");
            ////System.out.println(ocCode);
            System.out.println("#######select d.iti_name,b.iti_code,a.trade_code,a.strength->'" + ocCode + "' as strength,a.strength_fill->'" + ocCode + "' as strength_fill,a.strength_vacant->'" + ocCode + "' as strength_vacant,b.trade_short,b.strength,c.trade_name from iti_seatmatrix a,ititrade b,ititrade_master c,iti d where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%" + "' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and b.trade_short=c.trade_short and b.iti_code=d.iti_code and a.phase='" + phase + "' and a.year='" + year + "' order by b.iti_code,b.trade_code");
            // preparedStatement.setString(1, dist_code);
            //preparedStatement.setString(1, ocCode);
            resultSet = preparedStatement.executeQuery();
            System.out.println("resultset------>"+resultSet);
            int hopi = 0;
            while (resultSet.next()) {
                System.out.println("hopi---->"+hopi++);
                generalCouncilDetailsForm = new CouncilDetailsForm();
                generalCouncilDetailsForm.setIti_name(resultSet.getString("iti_name"));
                //////System.out.println(resultSet.getString("iti_name"));
                generalCouncilDetailsForm.setIti_code(resultSet.getString("iti_code"));
                System.out.println("iti  code---->"+resultSet.getString("iti_code"));
                generalCouncilDetailsForm.setTrade_code(String.valueOf(resultSet.getInt("trade_code")));
                generalCouncilDetailsForm.setGeneralStrength(resultSet.getInt(4));
                generalCouncilDetailsForm.setGeneralStrength_fill(resultSet.getInt("strength_fill"));
                generalCouncilDetailsForm.setGeneralStrength_vacant(resultSet.getInt("strength_vacant"));
                generalCouncilDetailsForm.setTrade_short(resultSet.getString("trade_short"));
                generalCouncilDetailsForm.setTotalStrength(resultSet.getInt(8));
                generalCouncilDetailsForm.setTrade_name(resultSet.getString("trade_name"));
                generalCouncilDetailsForms.add(generalCouncilDetailsForm);
            }
            return generalCouncilDetailsForms;
        } catch (Exception e) {
            ////System.out.println("hello 112");
            ////System.out.println("haaa Error at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }

    //---------------------------------------------Logic Phc Start Category-------------------------------------------------
    public ArrayList<CouncilDetailsForm> getPhcCouncilDistrictDetails(String dist_code, String phCode, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> phcCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm phcCouncilDetailsForm;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            //////System.out.println("select b.iti_code,a.strength,a.strength_fill,a.strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.category_code=? order by b.iti_code,b.trade_code");
            preparedStatement = connection.prepareStatement("select b.iti_code,a.strength->'" + phCode + "' as strength,a.strength_fill->'" + phCode + "' as strength_fill,a.strength_vacant->'" + phCode + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.iti_code,b.trade_code");
            //preparedStatement.setString(1, phCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                phcCouncilDetailsForm = new CouncilDetailsForm();
                phcCouncilDetailsForm.setPhcStrength(resultSet.getInt("strength"));
                phcCouncilDetailsForm.setPhcStrength_fill(resultSet.getInt("strength_fill"));
                phcCouncilDetailsForm.setPhcStrength_vacant(resultSet.getInt("strength_vacant"));
                phcCouncilDetailsForms.add(phcCouncilDetailsForm);
            }
            return phcCouncilDetailsForms;
        } catch (Exception e) {
            ////System.out.println("Error at getting CouncilDetailsForm in getExserviceCouncilDetails() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }
    //---------------------------------------------------------Logic Phc End Category-----------------------------------------------

    //---------------------------------------------Logic for General Exservice Start Category-------------------------------------------------
    public ArrayList<CouncilDetailsForm> getExserviceCouncilDistrictDetails(String dist_code, String exCode, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> exserviceCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm exserviceCouncilDetailsForm;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            preparedStatement = connection.prepareStatement("select b.iti_code,a.strength->'" + exCode + "' as strength,a.strength_fill->'" + exCode + "' as strength_fill,a.strength_vacant->'" + exCode + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.iti_code,b.trade_code");
            // preparedStatement.setString(1, exCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exserviceCouncilDetailsForm = new CouncilDetailsForm();
                exserviceCouncilDetailsForm.setExserviceStrength(resultSet.getInt("strength"));
                exserviceCouncilDetailsForm.setExserviceStrength_fill(resultSet.getInt("strength_fill"));
                exserviceCouncilDetailsForm.setExserviceStrength_vacant(resultSet.getInt("strength_vacant"));
                exserviceCouncilDetailsForms.add(exserviceCouncilDetailsForm);
            }
            return exserviceCouncilDetailsForms;
        } catch (Exception e) {
            ////System.out.println("Error at getting CouncilDetailsForm in getExserviceCouncilDetails() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }
    //---------------------------------------------------------Logic General Ex service End Category-----------------------------------------------

    //---------------------------------------------------------Logic for General Women Category-----------------------------------------------
    public ArrayList<CouncilDetailsForm> getGeneral_wCouncilDistrictDetails(String dist_code, String category_code, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> generalCouncilDetailsForm_ws = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm generalCouncilDetailsForm_w;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            preparedStatement = connection.prepareStatement("select b.iti_code,a.strength->'" + category_code + "' as strength,a.strength_fill->'" + category_code + "' as strength_fill,a.strength_vacant->'" + category_code + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.iti_code,b.trade_code");
            // preparedStatement.setString(1, category_code);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                generalCouncilDetailsForm_w = new CouncilDetailsForm();
                generalCouncilDetailsForm_w.setGeneralStrength_w(resultSet.getInt("strength"));
                generalCouncilDetailsForm_w.setGeneralStrength_fill_w(resultSet.getInt("strength_fill"));
                generalCouncilDetailsForm_w.setGeneralStrength_vacant_w(resultSet.getInt("strength_vacant"));
                generalCouncilDetailsForm_ws.add(generalCouncilDetailsForm_w);
            }
            return generalCouncilDetailsForm_ws;
        } catch (Exception e) {
            ////System.out.println(".Error at getting CouncilDetailsForm in getGeneralCouncilDetails_w() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }

    //---------------------------------------------Logic  for PHC women  Category Start-------------------------------------------------
    public ArrayList<CouncilDetailsForm> getPhc_wCouncilDistrictDetails(String dist_code, String ph_wCode, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> phcCouncilDetailsForm_ws = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm phcCouncilDetailsForm_w;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            preparedStatement = connection.prepareStatement("select b.iti_code,a.strength->'" + ph_wCode + "' as strength,a.strength_fill->'" + ph_wCode + "' as strength_fill,a.strength_vacant->'" + ph_wCode + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.iti_code,b.trade_code");
            //preparedStatement.setString(1, ph_wCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                phcCouncilDetailsForm_w = new CouncilDetailsForm();
                phcCouncilDetailsForm_w.setPhcStrength_w(resultSet.getInt("strength"));
                phcCouncilDetailsForm_w.setPhcStrength_fill_w(resultSet.getInt("strength_fill"));
                phcCouncilDetailsForm_w.setPhcStrength_vacant_w(resultSet.getInt("strength_vacant"));
                phcCouncilDetailsForm_ws.add(phcCouncilDetailsForm_w);
            }
            return phcCouncilDetailsForm_ws;
        } catch (Exception e) {
            ////System.out.println("Error at getting CouncilDetailsForm in getExserviceCouncilDetails() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }
    //---------------------------------------------------------Logic  Phc for women End Category-----------------------------------------------

    //---------------------------------------------Logic for General Exservice Start        Women Category-------------------------------------------------
    public ArrayList<CouncilDetailsForm> getExservice_wCouncilDistrictDetails(String dist_code, String ph_wCode, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> exserviceCouncilDetailsForm_ws = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm exserviceCouncilDetailsForm_w;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            preparedStatement = connection.prepareStatement("select b.iti_code,a.strength->'" + ph_wCode + "' as strength,a.strength_fill->'" + ph_wCode + "' as strength_fill,a.strength_vacant->'" + ph_wCode + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.iti_code,b.trade_code");
            //preparedStatement.setString(1, ph_wCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exserviceCouncilDetailsForm_w = new CouncilDetailsForm();
                exserviceCouncilDetailsForm_w.setExserviceStrength_w(resultSet.getInt("strength"));
                exserviceCouncilDetailsForm_w.setExserviceStrength_fill_w(resultSet.getInt("strength_fill"));
                exserviceCouncilDetailsForm_w.setExserviceStrength_vacant_w(resultSet.getInt("strength_vacant"));
                exserviceCouncilDetailsForm_ws.add(exserviceCouncilDetailsForm_w);
            }
            return exserviceCouncilDetailsForm_ws;
        } catch (Exception e) {
            ////System.out.println("Error at getting CouncilDetailsForm in getExserviceCouncilDetails() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }
    //---------------------------------------------------------Logic General Ex service End Category-----------------------------------------------

    //---------------------------------------------------------Logic for Reservation Men Category-----------------------------------------------
    public ArrayList<CouncilDetailsForm> getReservationCouncilDistrictDetails(String dist_code, String category_code, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> reservationCouncilDetailsForms = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //CouncilDetailsForm reservatiocheckForRankGenerationnCouncilDetailsForm;
        CouncilDetailsForm reservationCouncilDetailsForm;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
            //////System.out.println("select b.iti_code,a.strength->'"+category_code+"' as strength,a.strength_fill->'"+category_code+"' as strength_fill,a.strength_vacant->'"+category_code+"' as strength_fill from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0  order by b.iti_code,b.trade_code");
            preparedStatement = connection.prepareStatement("select b.iti_code,a.strength->'" + category_code + "' as strength,a.strength_fill->'" + category_code + "' as strength_fill,a.strength_vacant->'" + category_code + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.iti_code,b.trade_code");
            //System.out.println("$$$$$$select b.iti_code,a.strength->'" + category_code + "' as strength,a.strength_fill->'" + category_code + "' as strength_fill,a.strength_vacant->'" + category_code + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.iti_code,b.trade_code");
            //preparedStatement.setString(1, category_code);
            resultSet = preparedStatement.executeQuery();
            int hopi = 0;
            while (resultSet.next()) {
                ////System.out.println(hopi++);
                reservationCouncilDetailsForm = new CouncilDetailsForm();
                reservationCouncilDetailsForm.setReservationStrength(resultSet.getInt("strength"));
                reservationCouncilDetailsForm.setReservationStrength_fill(resultSet.getInt("strength_fill"));
                reservationCouncilDetailsForm.setReservationStrength_vacant(resultSet.getInt("strength_vacant"));
                reservationCouncilDetailsForms.add(reservationCouncilDetailsForm);
            }
            return reservationCouncilDetailsForms;
        } catch (Exception e) {
            ////System.out.println("..Error at getting CouncilDetailsForm in getGeneralCouncilDetails_w() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }

    //---------------------------------------------------------Logic for Reservation Women Category-----------------------------------------------
    public ArrayList<CouncilDetailsForm> getReservation_wCouncilDistrictDetails(String dist_code, String category_code, HttpServletRequest request) {
        ArrayList<CouncilDetailsForm> reservationCouncilDetailsForm_ws = new ArrayList<CouncilDetailsForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CouncilDetailsForm reservationCouncilDetailsForm_w;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, connection);
System.out.println("phaseeeeeeeeeeeeee"+phase);
System.out.println("yeaaaaaaaaaaaaarrrrrrrrr"+year);
            preparedStatement = connection.prepareStatement("select b.iti_code,a.strength->'" + category_code + "' as strength,a.strength_fill->'" + category_code + "' as strength_fill,a.strength_vacant->'" + category_code + "' as strength_vacant from iti_seatmatrix a,ititrade b where a.trade_code=b.trade_code and b.iti_code like'" + dist_code + "%' and b.iti_code=a.iti_code and b.available_for_year='1' and b.strength>0 and a.phase='" + phase + "' and a.year='" + year + "' order by b.iti_code,b.trade_code");
            //preparedStatement.setString(1, category_code);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reservationCouncilDetailsForm_w = new CouncilDetailsForm();
                reservationCouncilDetailsForm_w.setReservationStrength_w(resultSet.getInt("strength"));
                reservationCouncilDetailsForm_w.setReservationStrength_fill_w(resultSet.getInt("strength_fill"));
                reservationCouncilDetailsForm_w.setReservationStrength_vacant_w(resultSet.getInt("strength_vacant"));
                reservationCouncilDetailsForm_ws.add(reservationCouncilDetailsForm_w);
            }
            return reservationCouncilDetailsForm_ws;
        } catch (Exception e) {
            ////System.out.println("...Error at getting CouncilDetailsForm in getGeneralCouncilDetails_w() of  RegistationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting CouncilDetailsForm in getGeneralCouncilDetails() of  RegistationDao " + e);
            }

        }
        return null;
    }

    //==================================Code for District CouncilDetails End====================================
    public int getAdm_numCount(ItiAdmissionForm itiAdmissionForm, HttpServletRequest request) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBC.DBConnection.getConnection(request);
            String year_of_admission = DBC.RoleBasedOperations.Current_AcademicYear(request);
            //////System.out.println("year of admission is : "+year_of_admission);
            ////System.out.println("select max(substring(adm_num,9,3)) from admissions.iti_admissions where iti_code='"+itiAdmissionForm.getIti_code()+"' and trade_code='"+Integer.parseInt(itiAdmissionForm.getTrade_code())+"' and year_of_admission='" + year_of_admission + "' and length(adm_num)<12");
            preparedStatement = connection.prepareStatement("select max(substring(adm_num,9,3)) from admissions.iti_admissions where iti_code=? and trade_code=? and year_of_admission='" + year_of_admission + "' and length(adm_num)<12");
            preparedStatement.setString(1, itiAdmissionForm.getIti_code());
            preparedStatement.setInt(2, Integer.parseInt(itiAdmissionForm.getTrade_code()));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ////System.out.println("count is ---------------" + resultSet.getInt(1));
            }
            return resultSet.getInt(1);
        } catch (Exception e) {
            ////System.out.println("Error at getting admissions acount in getAdm_numCount() of  RegistationDao " + e);
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
                ////System.out.println("Errors at finalizations at getting adm_count() of  RegistationDao " + e);
            }

        }
        return 0;
    }

    public int insertingAdmissionDetails(ItiAdmissionForm itiAdmissionForm, int trno, String dist_code, String idmarks1, String idmarks2, String ssc_regno, String ssc_board, String ssc_year, String ssc_month, String original_date, HttpServletRequest request) throws SQLException {

        Connection connection = null;
        ////System.out.println("connection is : " + connection);
        PreparedStatement preparedStatement = null;
        //HttpSession session = request.getSession(true);
        String admnum = itiAdmissionForm.getAdm_num();
        ////System.out.println("Admission number is  : " + admnum);
        String fromd_training = DBC.RoleBasedOperations.getDurationFromPeriod(admnum);
        ////System.out.println("from day training is : " + fromd_training);
        String tod_training = DBC.RoleBasedOperations.getDurationToPeriod(admnum, request);
        ////System.out.println("to day training is :" + tod_training);
        try {
            connection = DBC.DBConnection.getConnection(request);
            preparedStatement = connection.prepareStatement("insert into admissions.iti_admissions (regid,name,fname,mname,addr,phno,adarno,gender,caste,ssc_passed,inter_passed,inter_marks,local,pincode,dob,phc,exservice,iti_code,trade_code,res_category,adm_num,year_of_admission,trno,dist_code,mode_adm,fromd_training,tod_training,idmarks1,idmarks2,ssc_regno,ssc_board,ssc_year,ssc_month,date_of_admission) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'" + beans.MyUtil.ChDate(fromd_training) + "','" + beans.MyUtil.ChDate(tod_training) + "','" + idmarks1 + "','" + idmarks2 + "','" + ssc_regno + "','" + ssc_board + "','" + ssc_year + "','" + ssc_month + "','" + original_date + "')");
            String mode_adm = "Convenor of the Sellection comitte";

            ////System.out.println("prepared statement is : " + preparedStatement);
            preparedStatement.setInt(1, itiAdmissionForm.getRegid());
            preparedStatement.setString(2, itiAdmissionForm.getName());
            preparedStatement.setString(3, itiAdmissionForm.getFatherName());
            preparedStatement.setString(4, itiAdmissionForm.getMname());
            preparedStatement.setString(5, itiAdmissionForm.getAddress());
            if (itiAdmissionForm.getPhoneNumber() != null && itiAdmissionForm.getPhoneNumber() != "") {
                preparedStatement.setLong(6, Long.parseLong(itiAdmissionForm.getPhoneNumber()));
            } else {
                preparedStatement.setLong(6, 0);
            }
            preparedStatement.setString(7, itiAdmissionForm.getAdarNo());
            preparedStatement.setString(8, itiAdmissionForm.getSex());
            preparedStatement.setString(9, itiAdmissionForm.getCast());
            preparedStatement.setBoolean(10, itiAdmissionForm.getSsc_passed());
            preparedStatement.setBoolean(11, itiAdmissionForm.getInterPassed());
            preparedStatement.setInt(12, itiAdmissionForm.getInter_marks());
            preparedStatement.setString(13, itiAdmissionForm.getLocal());
            preparedStatement.setInt(14, itiAdmissionForm.getPin_code());
            //Date date = beans.MyUtil.ChDate(itiAdmissionForm.getDob());
            java.sql.Date date = java.sql.Date.valueOf(itiAdmissionForm.getDob());

            preparedStatement.setDate(15, date);

            if (itiAdmissionForm.getPhc().equals("yes")) {
                preparedStatement.setBoolean(16, true);
            } else {
                preparedStatement.setBoolean(16, false);
            }
            if (itiAdmissionForm.getExservice().equals("yes")) {
                preparedStatement.setBoolean(17, true);
            } else {
                preparedStatement.setBoolean(17, false);
            }
            preparedStatement.setString(18, itiAdmissionForm.getIti_code());
            preparedStatement.setInt(19, Integer.parseInt(itiAdmissionForm.getTrade_code()));
            preparedStatement.setString(20, itiAdmissionForm.getRes_category());
            preparedStatement.setString(21, itiAdmissionForm.getAdm_num());
            ////System.out.println(itiAdmissionForm.getCurrentDate());
            preparedStatement.setString(22, "2016");
            preparedStatement.setInt(23, trno);
            preparedStatement.setString(24, dist_code);
            preparedStatement.setString(25, mode_adm);
            /*preparedStatement.setString(26, fromd_training);
             preparedStatement.setString(27, tod_training);*/

            ////System.out.println("Admission Number Generated is : ------------------------" + itiAdmissionForm.getAdm_num());
            System.out.println("preparedStatement---->"+preparedStatement);
            int i = preparedStatement.executeUpdate();
            ////System.out.println("in registrationDAO last part  : " + i);
            return i;
            
        } catch (Exception e) {
            e.printStackTrace();
            ////System.out.println("Error at inserting iti Admission details" + e);
            return 0;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                ////System.out.println("Errors at finalizations of  inserting itiAdmisionDetails RegisrationDao" + e);
            }
        }
    }

    public String getTradeName(String trade_short, HttpServletRequest request) {
        //////System.out.println("Trade short in dao"+trade_short);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBC.DBConnection.getConnection(request);
            preparedStatement = connection.prepareStatement("select trade_name from ititrade_master where trade_short=?");
            preparedStatement.setString(1, trade_short);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("trade_name");

            }
        } catch (Exception e) {
            ////System.out.println("Error at getting trade name in getTradeName in getTradename() of  RegistationDao " + e);
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
                ////System.out.println("Errors at finalizations of trade name in getTradeName in getTradename() RegisrationDao" + e);
            }
        }
        return null;
    }

    public ArrayList<ReservationCategoryForm> getReservationCategoryForm(HttpServletRequest request) {

        ArrayList<ReservationCategoryForm> reservationCategoryForms = new ArrayList<ReservationCategoryForm>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ReservationCategoryForm reservationCategoryForm;
        try {
            connection = DBC.DBConnection.getConnection(request);
            preparedStatement = connection.prepareStatement("select category_code,category_name from category_mast");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reservationCategoryForm = new ReservationCategoryForm();
                reservationCategoryForm.setCategory_code(resultSet.getString("category_code"));
                reservationCategoryForm.setCategory_name(resultSet.getString("category_name"));
                reservationCategoryForms.add(reservationCategoryForm);
            }
            return reservationCategoryForms;
        } catch (Exception e) {
            ////System.out.println("Error at getting RegisrationCategory details in RegistrationDao" + e);
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
                ////System.out.println("Errors at finalizations at getting RegistrationCategory in getReservationForm() of  RegistationDao " + e);
            }

        }
        return null;

    }

    public String getCategoryName(String category_code, HttpServletRequest request) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBC.DBConnection.getConnection(request);
            preparedStatement = connection.prepareStatement("select category_name from category_mast where category_code=?");
            preparedStatement.setString(1, category_code);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("category_name");
            }
        } catch (Exception e) {
            ////System.out.println("Error at getting trade name in getCategoryName in getCategoryName() of  RegistationDao " + e);
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
                ////System.out.println("Errors at finalizations of getting Category name  in getCategory() in RegisrationDao" + e);
            }
        }
        return null;
    }

    public String getGpt(int regid, HttpServletRequest request) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBC.DBConnection.getConnection(request);
            preparedStatement = connection.prepareStatement("select ssc_tot_gpa from cand_marks where regid=?");
            preparedStatement.setString(1, "" + regid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("ssc_tot_gpa");
            }
        } catch (Exception e) {
            ////System.out.println("Error at getting gpt  in getGpt() of  RegistationDao " + e);
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
                ////System.out.println("Errors at finalizations of getting gpa  in getGpa() in RegisrationDao" + e);
            }
        }
        return null;
    }

    public ArrayList<Integer> getCouncilMerits(String code, java.sql.Date date, String value, HttpServletRequest request) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Integer> councilMerits = new ArrayList<Integer>();
        try {
            connection = DBC.DBConnection.getConnection(request);
//            String query = "select merit_from,merit_to from admission_timings where " + code + " ='" + value + "' and cal_date='" + date + "'";
             String query = "select merit_from,merit_to from admission_timings where " + code + " =? and cal_date =?";
            preparedStatement = connection.prepareStatement(query);
             preparedStatement.setString(1,value);
            preparedStatement.setDate(2, date);
            resultSet = preparedStatement.executeQuery();
             
             System.out.println("query is---" + preparedStatement);
           
            
           
            if (resultSet.next()) {
                councilMerits.add(resultSet.getInt("merit_from"));
                councilMerits.add(resultSet.getInt("merit_to"));
                ////System.out.println(resultSet.getInt("merit_from"));
                ////System.out.println(resultSet.getInt("merit_to"));
                return councilMerits;
            } else {
                return null;
            }
        } catch (Exception e) {
            ////System.out.println("Error at getting getCouncilMerits of  RegistationDao " + e);
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
                ////System.out.println("Errors at finalizations of getting getCouncilMerits in RegisrationDao" + e);
            }
        }
        return null;
    }

    CheckedAdmissionDetails checkAdmited_or_Not(int regid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
