
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.ntaa.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author theru hareesh
 */
public class NewTradeDAO {

    public int addNewTrade(NewTradeDTO dto,HttpServletRequest request) throws SQLException {
        String trade_short = dto.getTrade_short().toUpperCase();
        String trade_name = dto.getTrade_name();
        int duration_yrs = dto.getDuration_yrs();
        String eng_noneng = dto.getEng_noneng();
        String min_qual = dto.getMin_qual();
        String type_admission=dto.getType_admission();
        String dr_nondr=dto.getDr_nondr();
        String short_order = dto.getShort_order();
        Statement stmt = null;
        Connection con = null;
        try {
            con = DBC.DBConnection.getConnection(request);
            System.out.println("the connection reference is:" + con);
            String query = "insert into ititrade_master(trade_short,trade_name,durationyrs,eng_nonengg,min_qual,type_admission,dr_nondr,display_order) values('" + trade_short + "','" + trade_name + "'," + duration_yrs + ",'" + eng_noneng + "','" + min_qual + "','"+type_admission+"','"+dr_nondr+"','"+ short_order +"');";
            System.out.println("the oracle query is:" + query);
            stmt = con.createStatement();
            //System.out.println("the statement:"+stmt);
            int n = stmt.executeUpdate(query);
            System.out.println("the insert data is value:" + n);
        } catch (Exception e) {

            return -1;
        } finally {
            if (stmt != null) {
                stmt.close();
            }

            if (con != null) {
                con.close();
            }

        }

        return 0;


    }

    public int genTradeCode(NewTradeDTO dto,HttpServletRequest request) throws SQLException {
        String trade_short = dto.getTrade_short().toUpperCase();
        System.out.println("the trade short value is:" + trade_short);
        System.out.println("Entry to maxLinkID");
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBC.DBConnection.getConnection(request);
            String query = "select trade_code from ititrade_master where trade_short='" + trade_short + "'";
            System.out.println("the select query is:" + query);
            // String test="select currval('ititrade_master_trade_code_seq')";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {

                int n = rs.getInt(1);
                System.out.println("the values of :" + n);
                return n;

            }
            System.out.println("Exit from maxLinkId");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return 0;
    }

    public List getList(NewTradeDTO ntd,HttpServletRequest request) throws SQLException {

        List list = new ArrayList();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBC.DBConnection.getConnection(request);
            String query = "select * from ititrade_master";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                NewTradeDTO dt = new NewTradeDTO();
                System.out.println(rs.getString("trade_short"));
                dt.setTrade_code(rs.getInt("trade_code"));
                dt.setTrade_short(rs.getString("trade_short"));
                dt.setTrade_name(rs.getString("trade_name"));
                dt.setDuration_yrs(rs.getInt("durationyrs"));
                dt.setEng_noneng(rs.getString("eng_nonengg"));
                dt.setMin_qual(rs.getString("min_qual"));
                list.add(dt);
            }



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }

        }

        return list;
    }
    /* public int retrieve(NewTradeDTO dto){
     try{
     String test="select currval('ititrade_master_trade_code_seq')";
     Statement st=con.createStatement();
     int n= st.executeUpdate(test);
     if(n==1){
        
     System.out.println("************************");
     }else{
        
     System.out.println("fail");
     }
     return n;
     }catch(Exception e){
     e.printStackTrace();
     }
         
     return 0;
     
     }*/
}
