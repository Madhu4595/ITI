/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class generic {

    public static String getItiname(String code,HttpServletRequest request) {
        String name = "";
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;
        try {

            con = DBC.DBConnection.getConnectionGen();
            st = con.createStatement();
            String query = "select iti_name from iti where iti_code='" + code + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("iti_name");
            }
        } catch (Exception ex) {
        } finally {
            try {
                rs.close();
                st.close();
                con.close();
            } catch (Exception ex) {
            }

        }
        return name;
    }

    public static String getTradeName(String code,HttpServletRequest request) {
        String name = "";
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {

            con = DBC.DBConnection.getConnectionGen();
            st = con.createStatement();
            String query = "select trade_short from ititrade_master where trade_code='" + code + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("trade_short");
            }
        } catch (Exception ex) {
        } finally {
            try {
                rs.close();
                st.close();
                con.close();
            } catch (Exception ex) {
            }

        }
        return name;
    }

    public static String getCapacity(String code,HttpServletRequest request) {
        String capacity = "";
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;
        try {

            con = DBC.DBConnection.getConnectionGen();
            st = con.createStatement();
            String sql = "select capacity from iti where iti_code=" + code;
            rs = st.executeQuery(sql);
            while (rs.next()) {
                capacity = rs.getString("capacity");
            }
        } catch (Exception ex) {
        } finally {
            try {
                rs.close();
                st.close();
                con.close();
            } catch (Exception ex) {
            }
        }
        return capacity;
    }

    public static String putOptions1(String tab, String code, String name,HttpServletRequest request) throws SQLException {
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {

            if (tab == null || code == null || name == null) {
                return null;
            }

            conn = DBC.DBConnection.getConnectionGen();
            String sql = "select distinct " + code + "," + name + " from " + tab + " where statecode='" + 28 + "' order by " + name + "";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            String val = "<option value='' selected>--select--</option>";
            for (; rs.next(); val = val + "<option value='" + rs.getString(1) + "' >" + rs.getString(2) + "</option>");
            rs.close();
            rs = null;
            psmt.close();
            psmt = null;
            conn.close();
            return val;
        } catch (Exception exception) {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (psmt != null) {
                psmt.close();
                psmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (psmt != null) {
                psmt.close();
                psmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        }
        return null;
    }

    public static String putOptions(String tab, String code, String name) throws SQLException {
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Connection conn;
        try {

            if (tab == null || code == null || name == null) {
                return null;
            }

            conn = DriverManager.getConnection("jdbc:mysql://localhost/fdca", "root", "");

            psmt = conn.prepareStatement("select distinct " + code + "," + name + " from " + tab + " order by " + name);
            rs = psmt.executeQuery();
            String val = "<option value='' selected>--select--</option>";
            for (; rs.next(); val = val + "<option value='" + rs.getString(1) + "' >" + rs.getString(2) + "</option>");
            rs.close();
            rs = null;
            psmt.close();
            psmt = null;
            return val;
        } catch (Exception exception) {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (psmt != null) {
                psmt.close();
                psmt = null;
            }
        }
        return null;
    }

    public static void fillDistances(String code,HttpServletRequest request) {
        PreparedStatement stat1 = null, stat12 = null, statdist = null, stat = null, stat2 = null;
        ResultSet rs1 = null, rs12 = null, rsdist = null, rs = null, rs2 = null;
        Connection con = null;

        try {

            con = DBC.DBConnection.getConnectionGen();
            String dist_code = code;
            String fldname = "";

            String qry1 = "select * from iti_metainfo where dist_code = '" + dist_code + "' and status='P'";
            stat1 = con.prepareStatement(qry1);
            rs1 = stat1.executeQuery();
            int fromfldno = 0;
            int tofldno = 0;
            while (rs1.next()) {
                fromfldno = rs1.getInt("fromfldno");
                tofldno = rs1.getInt("tofldno");
                fldname = rs1.getString("fldname");
            }
            qry1 = "select iti_code from dis_" + dist_code;  // p - pending
            stat1 = con.prepareStatement(qry1);
            rs1 = stat1.executeQuery();
            int i = 0;
            String[] itis = new String[100];
            while (rs1.next()) {
                itis[i] = rs1.getString("iti_code");
                i++;
            }

            for (int l = 0; l < i; l++) {
                qry1 = "select * from dis_" + dist_code + " where iti_code= '" + itis[l] + "'";

                stat2 = con.prepareStatement(qry1);
                rs2 = stat2.executeQuery();

                while (rs2.next()) {
                    for (int j = 0; j < i; j++) {
                        int k = fromfldno + j;
                        int distance = rs2.getInt(fldname + k);
                        qry1 = "insert into iti_distances (dist_code,fromiti_code,toiti_code,distance) values (" + code + ",'" + itis[l] + "','" + itis[j] + "','" + distance + "')";
                        stat2 = con.prepareStatement(qry1);
                        stat2.executeUpdate();
                    }

                }
            }
            if (stat1 != null) {
                stat1.close();
                stat1 = null;
            }
            if (stat2 != null) {
                stat2.close();
                stat2 = null;
            }
            if (rs1 != null) {
                rs1.close();
                rs1 = null;
            }
            if (rs2 != null) {
                rs2.close();
                rs2 = null;
            }
            con.close();
            con = null;
        } catch (Exception e) {
        } finally {
            if (stat1 != null) {
                try {
                    stat1.close();
                } catch (SQLException e) {
                }
                stat1 = null;
            }
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException e) {
                }
                rs1 = null;
            }
            if (stat2 != null) {
                try {
                    stat2.close();
                } catch (SQLException e) {
                }
                stat2 = null;
            }
            if (rs2 != null) {
                try {
                    rs2.close();
                } catch (SQLException e) {
                }
                rs2 = null;
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
                con = null;
            }
        }
    }

    public static void Jumble(HttpServletRequest request) {
        try {
            Connection con;

            con = DBC.DBConnection.getConnectionGen();
            //String sql_query = "select distinct iti_code from iti where dist_code=15 and iti_noniti='iti' order by tot_strength desc";
            String sql_query = "select distinct iti_code from iti where dist_code=15 and iti_noniti='iti'";
            ResultSet rs;
            Statement st = con.createStatement();
            rs = st.executeQuery(sql_query);
            while (rs.next()) {
                beans.test_center tc = new beans.test_center(rs.getString("iti_code"));
                if (tc.isEmpty()) {
                    //Trying to split
                    sql_query = "select count(*) as total from iti where remaining_capacity >0 and iti_code in (select toiti_code from iti_distances where fromiti_code=" + tc.test_center_code + " and toiti_code!=" + tc.test_center_code + " and distance<20)";
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(sql_query);
                    int count = 0;
                    while (rs2.next()) {
                        count = rs2.getInt("total");
                    }
                    sql_query = "select iti_code,remaining_capacity from iti where remaining_capacity >0 and iti_code in (select toiti_code from iti_distances where fromiti_code=" + tc.test_center_code + " and toiti_code!=" + tc.test_center_code + " and distance<20)";
                    String[] codes = new String[count];
                    int[] seats = new int[count];
                    rs2 = st2.executeQuery(sql_query);
                    int i = 0;
                    while (rs2.next()) {
                        codes[i] = rs2.getString("iti_code");
                        seats[i++] = Integer.parseInt(rs2.getString("remaining_capacity"));
                    }
                    try {
                        rs2.close();
                        st2.close();
                    } catch (Exception ex) {
                    }
                    if (sum(seats) < Integer.parseInt(tc.getTotal())) {
                    } else {
                        //Splitting is possible
						/*
                         Get trades
                         */
                        String trade_sql = "Select * from ititrade where iti_code = " + tc.test_center_code;
                        Statement trade_st = con.createStatement();
                        ResultSet trade_r = trade_st.executeQuery(trade_sql);
                        while (trade_r.next()) {
                            // get all trades
							/*
                             get the capacity of trade, if it is more than seats available insert else
                             */
                            String trade_code = trade_r.getString("trade_code");
                            int trade_strength = trade_r.getInt("strength");
                            for (int j = 0; j < count; j++) {
                                String code = codes[j];
                                int updated_seats = seats[j];
                                if (seats[j] > trade_strength) {
                                    //Insert trade in this center
                                    String query = "insert into iti_ttcallocated(fromiti_code,toiti_code,number_cand,tradecode) values('" + tc.test_center_code + "','" + code + "','" + trade_strength + "', '" + trade_code + "')";
                                    //out.println(query);
                                    PreparedStatement stat1 = con.prepareStatement(query);
                                    stat1.executeUpdate();
                                    query = "update iti set remaining_capacity = " + (updated_seats - trade_strength) + " where iti_code = '" + code + "'";
                                    stat1 = con.prepareStatement(query);
                                    stat1.executeUpdate();
                                    seats[j] = seats[j] - trade_strength;
                                    break;
                                }
                            }
                        }
                        try {
                            trade_r.close();
                            trade_st.close();
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    String center = tc.getRandom_center();
                    String query = "insert into iti_ttcallocated(fromiti_code,toiti_code,number_cand) values('" + tc.test_center_code + "','" + center + "','" + tc.getTotal() + "')";
                    PreparedStatement stat1 = con.prepareStatement(query);
                    stat1.executeUpdate();
                    query = "update iti set remaining_capacity = remaining_capacity-" + tc.getTotal() + " where iti_code = '" + center + "'";
                    stat1 = con.prepareStatement(query);
                    stat1.executeUpdate();
                    try {
                        stat1.close();
                    } catch (Exception ex) {
                    }
                }

            }

            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
        } finally {
        }

    }

    public static void refresh(HttpServletRequest request) {
        /*
         * delete from iti_ttcallocated;
         * update iti set remaining_capacity = capacity
         */
        Connection con = null;
        Statement st = null;
        try {

            con = DBC.DBConnection.getConnectionGen();
            st = con.createStatement();
            String query = "delete from iti_ttcallocated";
            st.executeUpdate(query);
            query = "update iti set remaining_capacity = capacity";
            st.executeUpdate(query);
        } catch (Exception ex) {
        } finally {
            try {
                st.close();
                con.close();
            } catch (Exception ex) {
            }
        }

    }

    private static int sum(int a[]) {
        int s = 0;
        for (int i : a) {
            s += i;
        }
        return s;
    }

    public static String getDistance(String code1, String code2,HttpServletRequest request) {
        String distance = "";
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {

            con = DBC.DBConnection.getConnectionGen();
            st = con.createStatement();
            String query = "select distance from iti_distances where fromiti_code='" + code1 + "' and toiti_code=" + code2;
            //////System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                distance = rs.getString("distance");
            }
        } catch (Exception ex) {
        } finally {
            try {
                rs.close();
                st.close();
                con.close();
            } catch (Exception ex) {
            }
        }
        return distance;
    }

    public static String getAdmissionType(HttpServletRequest request) {
        Connection con = DBC.DBConnection.getConnectionGen();
        String return_value = "";
        Statement st = null;
        try {
            st = con.createStatement();
            String sql = "Select * from data_params where name='admission_type'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return_value = rs.getString("code");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                st.close();
                con.close();
            } catch (Exception ex) {
            }
        }
        return return_value;
    }

    public static boolean CandidateExists(String regid,HttpServletRequest request) throws SQLException {
        boolean exists = false;
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DBC.DBConnection.getConnectionGen();
           // String sql = "Select * from print_application where regid='" + regid + "'";
              String sql = "Select * from student_application a inner join student_trade_sel b on a.regid=b.regid where a.regid='" + regid + "'";
            st = con.createStatement();
            System.out.println("bjklfhlfghgfhf"+sql);
            rs = st.executeQuery(sql);
            if (rs.next()) {
                exists = true;
            }

        } catch (Exception ex) {
        } finally {
            if (st != null) {
                st.close();
                st = null;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (con != null) {
                con.close();
                con = null;
            }
        }
        return exists;
    }
}
