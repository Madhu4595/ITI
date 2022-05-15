/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBC;

import Master.Trade.JavaBeanFormSerializable;
import beans.flash_news;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gopi Daggumalle
 */
public class RoleBasedOperations {

    public static String GenerateReport(String name, String where, String table, String orderby, HttpServletRequest request) throws SQLException {
        String qry = "";
        if (name == null || where == null || table == null) {
        }

        if (orderby != null && !orderby.equals("")) {
            if (orderby.indexOf("order by") < 0) {
                orderby = " order by " + orderby;
            }
        }

        String name1 = name.replaceAll("#", ",");
        qry = "select distinct " + name1 + " from " + table + " " + where + "" + orderby;

        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        ResultSet rs = null;
        ArrayList UserList = new ArrayList();
        try {
            rs = st.executeQuery(qry);
            while (rs.next()) {
                ReportSerializable lk = new ReportSerializable();

                StringTokenizer fldnms = null;
                fldnms = new StringTokenizer(name, "#");
                int k = 0;
                while (fldnms.hasMoreTokens()) {
                    String x = fldnms.nextToken();
                    k++;
                    if (x.equalsIgnoreCase("a.statecode") || x.equalsIgnoreCase("b.statecode") || x.equalsIgnoreCase("c.statecode")) {
                        lk.setStatecode(rs.getString(k));
                    } else if (x.equalsIgnoreCase("a.dist_code") || x.equalsIgnoreCase("b.dist_code") || x.equalsIgnoreCase("c.dist_code")) {
                        lk.setDist_code(rs.getString(k));
                    } else if (x.equalsIgnoreCase("a.dist_name") || x.equalsIgnoreCase("b.dist_name") || x.equalsIgnoreCase("c.dist_name")) {
                        lk.setDist_name(rs.getString(k));
                    } else if (x.equalsIgnoreCase("a.region_code") || x.equalsIgnoreCase("b.region_code") || x.equalsIgnoreCase("c.region_code")) {
                        lk.setItidist_code(rs.getString(k));
                    }
                }
                UserList.add(lk);
            }
            request.setAttribute("users", UserList);
            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return "success";
    }

    public static String login_history_insert(String username, HttpServletRequest req) throws UnknownHostException, SQLException {
        HttpSession session = req.getSession(true);
        String ipaddress = req.getRemoteAddr();
        String sessid = req.getSession().getId();
        Connection con = DBC.DBConnection.getConnection(req);
        Statement st = con.createStatement();
        String ampm[] = {"AM", "PM"};
        Calendar calendar = Calendar.getInstance();
        int slno = 0;
        PreparedStatement pst = null;
        ResultSet rs = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
   LocalDateTime now = LocalDateTime.now();  
   System.out.println(dtf.format(now)); 
        String qry = "select max(slno) from login_history where uname='" + username + "'";
        try {
            rs = st.executeQuery(qry);
            if (rs.next()) {
                slno = rs.getInt(1);
            }
            slno++;
            String sql = "insert into login_history(uname,logindatetime,ipaddress,sessionid,slno,login_status)values('" + username + "','"+now+"','" + ipaddress + "','" + sessid + "','" + slno + "','1')";
            System.out.println("sql is--->"+sql);
            try {
                pst = con.prepareStatement(sql);
                int k = pst.executeUpdate();
            } catch (Exception es) {
            }

            session.setAttribute("slno", slno);

            rs.close();
            pst.close();
            st.close();
            con.close();

        } catch (Exception ex) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
            }

        }
        return "";
    }
    public static String insertPDFiles(String message, String status, String fileurl, HttpServletRequest req) throws UnknownHostException, SQLException {
        Connection con = DBC.DBConnection.getConnection(req);
        Statement st = con.createStatement();
        ResultSet rs = null;
        String msg = null;
        int flashnewsid = 0;
       
        String extension = fileurl.substring(fileurl.lastIndexOf("."));
        
        System.out.println("extension--->"+extension);
        if(extension.equals(".pdf")|| extension.equals (".PDF" )){
        String  result = fileurl.substring(0, fileurl.lastIndexOf("."));
        if(result.contains(".")){
        msg="Invalid file name";
        return msg;
        }
        
        
        try {
            String q = "select(nextval('flash"
                    + "_news_flashnewsid_seq'))";
            st=con.createStatement();
            rs=st.executeQuery(q);
            System.out.println("q-->"+q);
            if(rs.next())
            {
                flashnewsid=rs.getInt(1);
            }
            String sql = "insert into flash_news values("+flashnewsid+",'" + message + "','" + status + "','" + fileurl + "')";
            st = con.createStatement();
             System.out.println("query-->"+sql);
            int k = st.executeUpdate(sql);
            if (k > 0) {
                msg = "inserted";
            } else {
                msg = "not";
            }
        } catch (Exception ex) {
            System.out.println("error came-->"+ex.toString());
            return msg = ex.toString();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        }
        else{
            msg="Upload Only Pdf Files";
                    
       }
        return msg;
        
    }
    
    public static String insertMEMOs(String regid, String status, String fileurl, HttpServletRequest req) throws UnknownHostException, SQLException {
        Connection con = DBC.DBConnection.getConnection(req);
        Statement st = con.createStatement();
        ResultSet rs = null;
        String msg = null;
        int memoid = 0;
        System.out.println("In Insert MEMO method");
        try {
            String q = "select(nextval('memoid'))";
            st=con.createStatement();
            rs=st.executeQuery(q);
            System.out.println("q-->"+q);
            if(rs.next())
            {
                memoid=rs.getInt(1);
            }
            String sql = "insert into memos values("+memoid+",'" + regid + "','" + status + "','" + fileurl + "')";
            st = con.createStatement();
             System.out.println("query-->"+sql);
            int k = st.executeUpdate(sql);
            if (k > 0) {
                msg = "inserted";
            } else {
                msg = "not";
            }
            System.out.println("Memo is-----"+msg);
        } catch (Exception ex) {
            System.out.println("error came-->"+ex.toString());
            return msg = ex.toString();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return msg;
    }
public static String insertScroll(String message, String status, HttpServletRequest req) throws UnknownHostException, SQLException {
        Connection con = DBC.DBConnection.getConnection(req);
        Statement st = con.createStatement();
        ResultSet rs = null;
        String msg = null;
        int flashnewsid = 0;
        try {
            String q = "select(nextval('flash_news_flashnewsid_seq'))";
            st=con.createStatement();
            rs=st.executeQuery(q);
            System.out.println("q-->"+q);
            if(rs.next())
            {
                flashnewsid=rs.getInt(1);
            }
            String sql = "insert into flash_news values("+flashnewsid+",'" + message + "','" + status + "')";
            st = con.createStatement();
             System.out.println("query-->"+sql);
            int k = st.executeUpdate(sql);
            if (k > 0) {
                msg = "inserted";
            } else {
                msg = "not";
            }
        } catch (Exception ex) {
            System.out.println("error came-->"+ex.toString());
            return msg = ex.toString();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return msg;
    }
public static String insertSlideshow(String message, String status,byte image,  HttpServletRequest req) throws UnknownHostException, SQLException {
        
    Connection con = DBC.DBConnection.getConnection(req);
        Statement st = con.createStatement();
        ResultSet rs = null;
        String msg = null;
        int flashnewsid = 0;
      
        try {
            String q = "select(nextval('flash_news_flashnewsid_seq'))";
            st=con.createStatement();
            rs=st.executeQuery(q);
            System.out.println("q-->"+q);
            if(rs.next())
            {
                flashnewsid=rs.getInt(1);
            }
            String sql = "insert into flash_news values("+flashnewsid+",'" + message + "','" + status + "','"+image+"')";
            st = con.createStatement();
             System.out.println("query-->"+sql);
            int k = st.executeUpdate(sql);
            if (k > 0) {
                msg = "inserted";
            } else {
                msg = "not";
            }
        } catch (Exception ex) {
            System.out.println("error came-->"+ex.toString());
            return msg = ex.toString();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return msg;
    }


    public static String TradeCodes(String sql, HttpServletRequest request, String iti_code) throws SQLException {
        HttpSession session = request.getSession(true);
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs1 = null;
        ArrayList UserList = new ArrayList();
        try {
            rs = st.executeQuery(sql);
            String trade_name = null;
            while (rs.next()) {
                String trade_code, strength;
                String trade_short, available;
                trade_code = rs.getString("trade_code");
                String sql1 = "select * from ititrade_master WHERE trade_code='" + trade_code + "'";
                rs1 = st1.executeQuery(sql1);
                if (rs1.next()) {
                    trade_name = rs1.getString("trade_name");
                }

                JavaBeanFormSerializable lf = new JavaBeanFormSerializable();

                lf.setTrade_name(trade_name);
                lf.setTrade_code(trade_code);
                strength = rs.getString("strength");
                lf.setStrength(strength);
                trade_short = rs.getString("trade_short");
                lf.setTrade_short(trade_short);
                available = rs.getString("available_for_year");
                String manage_seats = "";
                String p = "1";
                if (available.equals(p)) {
                    available = "Yes";
                } else if (available.equals("0")) {
                    available = "No";
                }
                lf.setAvailable(available);
                String href;
                if ("0".equals(rs.getString("trade_freeze"))) {
                    String editoptions = "editTrade.do?trade_code=" + trade_code + "";
                    lf.setEditoption(editoptions);
                    lf.setLable("Edit");
                    href = "<a href=\"addnew_trade_inter.do\" class=\"style21\">Add Trade</a>";
                    manage_seats = "";
                    lf.setManage_seats(manage_seats);
                    String label2 = "";
                    lf.setLabel2(label2);
                } else {
                    String editoptions = "";
                    lf.setEditoption(editoptions);
                    lf.setLable("");
                    href = "";
                    manage_seats = "manage_seats.do?trade_code=" + trade_code + "&totstrength=" + strength + "";
                    lf.setManage_seats(manage_seats);
                    String label2 = "Reservation Entry";
                    lf.setLabel2(label2);
                }
                session.setAttribute("href", href);
                String sql2 = "select a.iti_code,a.category_code,a.strength,a.strength_vacant,a.trade_code,b.category_name from iti_seatmatrix a,category_mast b WHERE a.trade_code='" + trade_code + "' and a.iti_code='" + iti_code + "' and a.category_code=b.category_code";
                rs2 = st1.executeQuery(sql2);
                String temp = "";
                while (rs2.next()) {
                    temp += "" + rs2.getString("category_name") + "-" + rs2.getInt("strength") + " ";
                }
                lf.setCategory_code(temp);
                UserList.add(lf);
            }
            request.setAttribute("users", UserList);
        } catch (Exception ex) {
        }

        String sql1 = "select * from iti_params WHERE code='" + 1 + "'";
        String value = null;
        rs = st.executeQuery(sql1);
        if (rs.next()) {
            value = rs.getString("value");
        }
        value = MyUtil.year1(value);
        request.setAttribute("year", value);

        rs.close();
        rs2.close();
        st.close();
        st1.close();
        con.close();

        try {
        } catch (Exception ex) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (rs2 != null) {
                    rs2.close();
                }
                if (st != null) {
                    st.close();
                }
                if (st1 != null) {
                    st1.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (Exception ex) {
            }
        }

        return "success";
    }

    public static String selectitiCodes(String sql, HttpServletRequest request, String iti_code) throws SQLException {
        HttpSession session = request.getSession(true);
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        ResultSet rs = null;
        ArrayList UserList = new ArrayList();
        try {
            rs = st.executeQuery(sql);

            while (rs.next()) {
                JavaBeanFormSerializable lf = new JavaBeanFormSerializable();
                lf.setDist_code(rs.getString(1));
                lf.setDist_name(rs.getString(2));

                UserList.add(lf);
            }
            session.setAttribute("users", UserList);
            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return "convinertrade";
    }

    public static String ConvinerTradeCodesRep(String sql, HttpServletRequest request, String iti_code) throws SQLException {
        HttpSession session = request.getSession(true);
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        ResultSet rs = null, rs1 = null;
        ResultSet rs2 = null;
        ArrayList UserList = new ArrayList();
        //tring year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        //int phase = Integer.parseInt(beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, con));

        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "seatmatrix_phase", year, "current", "true", con);

        try {
            rs = st.executeQuery(sql);
            String href = "";
            boolean iti_existornot = false;
            String trade_name = null;
            while (rs.next()) {
                String trade_code, strength, strength_vacants;
                String trade_short, available;
                trade_code = rs.getString("trade_code");
                String sql1 = "select * from ititrade_master WHERE trade_code='" + trade_code + "'";
                rs1 = st1.executeQuery(sql1);
                if (rs1.next()) {
                    trade_name = rs1.getString("trade_name");
                }
                JavaBeanFormSerializable lf = new JavaBeanFormSerializable();
                lf.setTrade_name(trade_name);
                lf.setTrade_code(trade_code);
                strength = rs.getString("strength");//from ititrade
                strength_vacants = rs.getString("strength_vacant");
                lf.setStrength(strength);
                lf.setStrength_vacants(strength_vacants);
                trade_short = rs.getString("trade_short");
                lf.setTrade_short(trade_short);
                available = rs.getString("available_for_year");
                System.out.println("available for year-------->"+available);
                String manage_seats = "";
                String p = "1";
                if (available.equals("1")) {
                    available = "Yes";
                } else {
                    available = "No";
                }
                lf.setAvailable(available);
                String freeze = "hello";
                if ("0".equals(rs.getString("trade_freeze")) || "".equals(rs.getString("trade_freeze"))) {
                    freeze = "<center><font color='red'><b>These Iti Trades are not froozen by ITI level</b></font></center>";

                } else {
                    freeze = "<center><font color='green'><b>These Iti Trades froozen by Convener </b></font></center>";
                }

                if ("0".equals(rs.getString("conv_approval"))) {
                    String editoptions = "editTrade.do?trade_code=" + trade_code + "&iti_code_conv=" + iti_code + "";
                    lf.setEditoption(editoptions);
                    lf.setLable("Edit");
                    href = "<a href=\"addnew_trade_inter.do\" class=\"style21\">Add Trade</a>";
                    manage_seats = "";
                    lf.setManage_seats(manage_seats);
                    String label2 = "";
                    lf.setLabel2(label2);
                } else {

                    String editoptions = "";
                    lf.setEditoption(editoptions);
                    lf.setLable("");
                    href = "";
                    manage_seats = "manage_seats.do?trade_code=" + trade_code + "&totstrength=" + strength + "&iti_code=" + iti_code + "";
                    lf.setManage_seats(manage_seats);
                    String label2 = "Reservation Entry";
                    lf.setLabel2(label2);
                }
                session.setAttribute("href", href);
                session.setAttribute("freeze", freeze);
                //String sql2 = "select a.iti_code,a.category_code,a.strength,a.strength_vacant,a.trade_code,b.category_name from iti_seatmatrix a,category_mast b WHERE a.trade_code='" + trade_code + "' and a.iti_code='" + iti_code + "' and a.category_code=b.category_code";
                String sql2 = "select s.iti_code,s.trade_code ,s.strength_code, s.strengthval,s.strength_vacantval,c.category_name from  (select iti_code,trade_code,skeys(strength) as strength_code,svals(strength) as strengthval,svals(strength_vacant) as strength_vacantval from iti_seatmatrix i where iti_code='" + iti_code + "' and trade_code='" + trade_code + "' and year='" + year + "' and phase=" + phase + ") s inner join category_mast c on s.strength_code =c.category_code";
                rs2 = st1.executeQuery(sql2);
                String temp = "";
                while (rs2.next()) {
                    //temp += "" + rs2.getString("category_name") + "-" + rs2.getInt("strength") + " ";
                    temp += "" + rs2.getString("category_name") + "-" + rs2.getString("strengthval") + " ";
                }
                lf.setCategory_code(temp);
                UserList.add(lf);
            }
            if (!iti_existornot) {
                href = "<a href=\"addnew_trade_inter.do\" class=\"style21\">Add Trade</a>";
                session.setAttribute("href", href);
            }
            session.setAttribute("users", UserList);
            Object l = session.getAttribute("users");

            String sql1 = "select * from iti_params WHERE code='" + 7 + "'";
            String value = null;
            rs = st.executeQuery(sql1);
            if (rs.next()) {
                value = rs.getString("value");
            }
            value = MyUtil.year1(value);
            session.setAttribute("year", value);

            rs.close();
            rs1.close();
            rs2.close();
            st.close();
            st1.close();
            con.close();

        } catch (Exception ex) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (rs1 != null) {
                rs1.close();
            }
            if (rs2 != null) {
                rs2.close();
            }
            if (st != null) {
                st.close();
            }
            if (st1 != null) {
                st1.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return "success";
    }

    public static String EditConvinerTradeCodes(String sql, HttpServletRequest request, String trade_code) throws SQLException {
        HttpSession session = request.getSession(true);
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        ResultSet rs = null, rs1 = null;
        Statement st1 = con.createStatement();
        ArrayList UserList1 = new ArrayList();
        try {
            rs = st.executeQuery(sql);
            String trade_name = null;
            if (rs.next()) {
                JavaBeanFormSerializable lf = new JavaBeanFormSerializable();
                String strength, strength_fill, strength_vacant;
                String trade_short, available;
                strength = rs.getString("strength");
                strength_fill = rs.getString("strength_fill");
                strength_vacant = rs.getString("strength_vacant");
                //strength = rs.getString("strength_vacant");//from ititrade table. rs means from ititrade table
                lf.setStrength(strength);
                lf.setStrength_fill(strength_fill);
                lf.setStrength_vacant(strength_vacant);
                available = rs.getString("available_for_year");
                session.setAttribute("available", available);
                if (available.equals("1")) {
                    available = "Yes";
                } else {
                    available = "No";
                }
                String sql1 = "select * from ititrade_master WHERE trade_code='" + trade_code + "'";
                rs1 = st1.executeQuery(sql1);
                if (rs1.next()) {
                    trade_name = rs1.getString("trade_name");
                }
                lf.setTrade_name(trade_name);
                lf.setAvailable(available);
                trade_short = rs.getString("trade_short");
                lf.setTrade_short(trade_short);
                UserList1.add(lf);
            }
            session.setAttribute("users1", UserList1);
            String sql1 = "select * from iti_params WHERE code='" + 7 + "'";
            String value = null;
            rs = st.executeQuery(sql1);
            if (rs.next()) {
                value = rs.getString("value");
            }
            value = MyUtil.year1(value);
            session.setAttribute("year", value);

            rs.close();
            rs1.close();
            st.close();
            st1.close();
            con.close();

        } catch (Exception ex) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (rs1 != null) {
                rs1.close();
            }
            if (st != null) {
                st.close();
            }
            if (st1 != null) {
                st1.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return "success";
    }

    public static String EditConTradeCodesSuccess(String sql, HttpServletRequest request, String trade_code) throws SQLException {
        HttpSession session = request.getSession(true);
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = DBC.DBConnection.getConnection(request);
            pst = con.prepareStatement(sql);
            int s = pst.executeUpdate();
            con.close();
            pst.close();
            if (s > 0) {
                return "success";
            }
            pst.close();
            con.close();
        } catch (Exception ex) {
            //System.out.println("es" + ex);
            return "derror";
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return "failure";
    }

    public static String AddTradeCodes(String sql, HttpServletRequest request, String trade_code) throws SQLException {
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        ResultSet rs = null;
        ArrayList UserList = new ArrayList();
        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                JavaBeanFormSerializable lk = new JavaBeanFormSerializable();
                lk.setTrade_code(rs.getString(1));
                lk.setTrade_name(rs.getString(2));
                UserList.add(lk);
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("users", UserList);
            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return "success";
    }

    public static String AddnewTrade_success(String sql, HttpServletRequest request) throws SQLException {
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        PreparedStatement pst = con.prepareStatement(sql);
        int s = 0;
        try {
            s = pst.executeUpdate();

            con.close();
            pst.close();
        } catch (Exception ex) {
            //System.out.println(ex);
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }
        if (s > 0) {
            return "success";
        }
        return "failure";
    }

    public static String ChangePassword(String sql, String qry, HttpServletRequest request, String oldpwd) throws SQLException {
        Connection con = null;
        con = DBC.DBConnection.getConnectionGen();
        Statement st = con.createStatement();
        System.out.println("qry---->"+qry);
        ResultSet rs = null;
        int s = 0;
        try {
            rs = st.executeQuery(qry);
            if (rs.next()) {
                String oldpwd_check = rs.getString("hash_password");
                System.out.println("oldpwd_check--->"+oldpwd_check);
                System.out.println("oldpwd_check--->"+oldpwd_check);
                if (oldpwd_check.equals(oldpwd)) {
                    PreparedStatement pst = con.prepareStatement(sql);
                    System.err.println("pst is ---->"+pst);
                    s = pst.executeUpdate();
                    con.close();
                    st.close();
                    rs.close();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        if (s > 0) {
            return "success";
        }
        return "pwderror";
    }

    public static String DashBoard_page(String sql, HttpServletRequest request, String iti_code) throws SQLException {
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        ResultSet rs = null, rs2 = null;
        ArrayList UserList = new ArrayList();
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        String phse = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);
        try {
            int total = 0;
            rs = st.executeQuery(sql);
            while (rs.next()) {
                JavaBeanFormSerializable lk = new JavaBeanFormSerializable();
                lk.setDist_code(rs.getString("dist_code"));
                lk.setDist_name(rs.getString("dist_name"));
                String sql2 = "select count(*) from ( select skeys(phase) as phaskeys ,svals(phase) as phasvals,* from trade_sel ) a where a.phaskeys='" + phse + "' and a.phasvals='true' and temp_code='" + rs.getString("dist_code") + "' and year='" + year + "'";
                rs2 = st1.executeQuery(sql2);
               System.out.println("fdklgdfgdf"+sql2);
                if (rs2.next()) {
                    total += rs2.getInt(1);
                    lk.setStrength(rs2.getString(1));
                }
                UserList.add(lk);
            }
            request.setAttribute("users", UserList);
            request.setAttribute("total", total);

            rs.close();
            rs2.close();
            st.close();
            st1.close();
            con.close();

        } catch (Exception ex) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (rs2 != null) {
                rs2.close();
            }
            if (st != null) {
                st.close();
            }
            if (st1 != null) {
                st1.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return "success";
    }

    public static String DashBoard_Showitis(String sql, HttpServletRequest request, String dist_code) throws SQLException {
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        ResultSet rs = null, rs2 = null;
        ArrayList UserList = new ArrayList();
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        String phse = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);

        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                JavaBeanFormSerializable lk = new JavaBeanFormSerializable();
                lk.setDist_code(rs.getString("iti_code"));

                String sql2 = "select iti_name from iti where iti_code='" + rs.getString("iti_code") + "'";
                rs2 = st1.executeQuery(sql2);
                if (rs2.next()) {
                    lk.setIti_name(rs2.getString("iti_name"));
                }
                sql2 = "select count(iti_code) from ( select skeys(phase) as phaskeys ,svals(phase) as phasvals,* from trade_sel ) a where a.phaskeys='" + phse + "' and a.phasvals='true' and iti_code='" + rs.getString("iti_code") + "' and year='" + year + "'";
                System.out.println("sql2 :: " + sql2);
                rs2 = st1.executeQuery(sql2);
                if (rs2.next()) {
                    lk.setStrength(rs2.getString(1));
                }
                UserList.add(lk);
            }
            request.setAttribute("users", UserList);

            rs.close();
            rs2.close();
            st.close();
            st1.close();
            con.close();

        } catch (Exception ex) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (rs2 != null) {
                rs2.close();
            }
            if (st != null) {
                st.close();
            }
            if (st1 != null) {
                st1.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return "itisuccess";
    }

    public static String DashBoard_Showitisforiti(String sql, HttpServletRequest request, String iti_code) throws SQLException {
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        ResultSet rs = null, rs2 = null;
        ArrayList UserList = new ArrayList();
        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                JavaBeanFormSerializable lk = new JavaBeanFormSerializable();
                lk.setDist_code(rs.getString("iti_code"));

                String sql2 = "select iti_name from iti where iti_code='" + iti_code + "'";
                rs2 = st1.executeQuery(sql2);
                if (rs2.next()) {
                    lk.setIti_name(rs2.getString("iti_name"));
                }

                sql2 = "select count(iti_code) from trade_sel where iti_code='" + rs.getString("iti_code") + "'";
                rs2 = st1.executeQuery(sql2);
                if (rs2.next()) {
                    lk.setStrength(rs2.getString(1));
                }
                UserList.add(lk);
            }
            request.setAttribute("users", UserList);

            rs.close();
            rs2.close();
            st.close();
            st1.close();
            con.close();

        } catch (Exception ex) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (rs2 != null) {
                rs2.close();
            }
            if (st != null) {
                st.close();
            }
            if (st1 != null) {
                st1.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return "itisuccess";
    }

    public static String DashBoard_ShowitisDistwise(String sql, HttpServletRequest request, String dist_code) throws SQLException {
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        ResultSet rs = null, rs2 = null;
        ArrayList UserList = new ArrayList();
        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                JavaBeanFormSerializable lk = new JavaBeanFormSerializable();
                lk.setDist_code(rs.getString("iti_code"));

                String sql2 = "select iti_name from iti where iti_code='" + rs.getString("iti_code") + "'";
                rs2 = st1.executeQuery(sql2);
                if (rs2.next()) {
                    lk.setIti_name(rs2.getString("iti_name"));
                }

                sql2 = "select count(iti_code) from trade_sel where iti_code='" + rs.getString("iti_code") + "'";
                rs2 = st1.executeQuery(sql2);
                if (rs2.next()) {
                    lk.setStrength(rs2.getString(1));
                }
                UserList.add(lk);
            }
            request.setAttribute("users", UserList);

            rs.close();
            rs2.close();
            st.close();
            st1.close();
            con.close();

        } catch (Exception ex) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (rs2 != null) {
                rs2.close();
            }
            if (st != null) {
                st.close();
            }
            if (st1 != null) {
                st1.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return "distsuccess";
    }

    public static String ManageSeats_Interface(String sql, HttpServletRequest request, String iti_code, String trade_code, String sql2) throws SQLException {
        HttpSession session = request.getSession(true);
        session.setAttribute("trade_code", trade_code);
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        ResultSet rs = null, rs1 = null;
        ArrayList UserList = new ArrayList();
        try {
            String qry = "select * from category_mast";
            rs1 = st1.executeQuery(qry);
            while (rs1.next()) {
                String temp = "";
                JavaBeanFormSerializable lf = new JavaBeanFormSerializable();
                temp = sql + " and category_code='" + rs1.getString("category_code") + "'";
                ////System.out.println("temp " + temp);
                rs = st.executeQuery(temp);
                if (rs.next()) {
                    lf.setCategory_code(rs.getString("category_code"));
                    lf.setStrength(rs.getString("strength"));
                    lf.setStrength_fill(rs.getString("strength_fill"));
                    lf.setStrength_vacant(rs.getString("strength_vacant"));
                    lf.setCategory_name(rs.getString("category_name"));
                    lf.setAdmitted(rs.getString("admitted"));
                    UserList.add(lf);
                } else {
                    lf.setCategory_code(rs1.getString("category_code"));
                    lf.setStrength("0");
                    lf.setStrength_fill("0");
                    lf.setStrength_vacant("0");
                    lf.setCategory_name(rs1.getString("category_name"));
                    lf.setAdmitted("0");
                    UserList.add(lf);
                }

            }
            session.setAttribute("users", UserList);
            rs1.close();
            rs.close();
            st.close();
            st1.close();
            con.close();
        } catch (Exception ex) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (rs1 != null) {
                rs1.close();
            }
            if (st != null) {
                st.close();
            }
            if (st1 != null) {
                st1.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return "success";
    }

    /* public static String ManageSeats_Interface_ori(String sql, HttpServletRequest request, String iti_code, String trade_code, String sql2) throws SQLException {
     HttpSession session = request.getSession(true);
     session.setAttribute("trade_code", trade_code);
     Connection con = null;
     con = DBC.DBConnection.getConnection(request);
     Statement st = con.createStatement();
     Statement st1 = con.createStatement();
     ResultSet rs = null, rs1 = null;
     ArrayList UserList = new ArrayList();
     try {
     String qry = "select * from category_mast";
     rs1 = st1.executeQuery(qry);
     while (rs1.next()) {
     String temp = "";
     JavaBeanFormSerializable lf = new JavaBeanFormSerializable();
     temp = sql + " and s.category_code='" + rs1.getString("category_code") + "'";
     //System.out.println("temp "+temp);
     rs = st.executeQuery(temp);
     if (rs.next()) {
     lf.setCategory_code(rs.getString("category_code"));
     lf.setStrength(rs.getString("strength"));
     lf.setStrength_fill(rs.getString("strength_fill"));
     lf.setStrength_vacant(rs.getString("strength_vacant"));
     lf.setCategory_name(rs.getString("category_name"));
     //lf.setAdmitted(rs.getString("admitted"));
     UserList.add(lf);
     } else {
     lf.setCategory_code(rs1.getString("category_code"));
     lf.setStrength("0");
     lf.setStrength_fill("0");
     lf.setStrength_vacant("0");
     lf.setCategory_name(rs1.getString("category_name"));
     //lf.setAdmitted("0");
     UserList.add(lf);
     }

     }
     session.setAttribute("users", UserList);
     rs1.close();
     rs.close();
     st.close();
     st1.close();
     con.close();
     } catch (Exception ex) {
     } finally {
     if (rs != null) {
     rs.close();
     }
     if (rs1 != null) {
     rs1.close();
     }
     if (st != null) {
     st.close();
     }
     if (st1 != null) {
     st1.close();
     }
     if (con != null) {
     con.close();
     }
     }
     return "success";
     }
     */
    public static String ShowApplicants(String sql, HttpServletRequest request, String iti_code) throws SQLException {
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        ResultSet rs = null, rs1 = null;
        ArrayList UserList = new ArrayList();
        HttpSession session = request.getSession(true);
        String role_id = "" + session.getAttribute("role_id");
        String qry = "";

        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        String phse = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);

        if (role_id.equals("4")) {
            qry = "select * from application a,cand_marks b,trade_sel c where  a.regid=c.regid and b.regid::integer=c.regid and a.phase->'" + phse + "'='true' and a.year ='" + year + "' and c.iti_code='" + iti_code + "'";
        } else if (role_id.equals("3")) {
            qry = "select * from application a,cand_marks b,trade_sel c where  a.regid=c.regid and b.regid::integer=c.regid and a.phase->'" + phse + "'='true' and a.year ='" + year + "' and c.dist_code='" + iti_code + "'";
        }
        //System.out.println(qry);
        try {

            rs1 = st1.executeQuery(qry);
            while (rs1.next()) {
                JavaBeanFormSerializable lf = new JavaBeanFormSerializable();
                lf.setRegno(rs1.getString("regid"));
                lf.setName(rs1.getString("name"));
                lf.setFaname(rs1.getString("fname"));
                lf.setTotalgp(rs1.getString("ssc_tot_gpa"));
                lf.setDob(rs1.getString("dob"));
                lf.setCaste(rs1.getString("caste"));

                UserList.add(lf);
            }
            request.setAttribute("users", UserList);

            rs.close();
            rs1.close();
            st.close();
            st1.close();
            con.close();

        } catch (Exception ex) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (rs1 != null) {
                rs1.close();
            }
            if (st != null) {
                st.close();
            }
            if (st1 != null) {
                st1.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return "convinertrade";
    }

    public static String ShowApplicants_Report(String from, HttpServletRequest request, String iti_code, String to) throws SQLException {

        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        Statement st2 = con.createStatement();
        ResultSet rs = null, rs1 = null;
        ArrayList UserList = new ArrayList();
        HttpSession session = request.getSession();
        String role_id = session.getAttribute("role_id").toString();
        String label = null;
        if (role_id.equalsIgnoreCase("3")) {
            label = "dist_code";
        } else if (role_id.equalsIgnoreCase("4")) {
            label = "iti_code";
        }
        int j = 0;
        try {
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
            String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);

            j++;
            String qry = "select  * from application x,cand_marks y,trade_sel z where  x.regid=z.regid and y.regid=z.regid::character varying  and " + label + "='" + iti_code + "' and x.year='" + year + "' and x.phase->'" + phase + "'='true' order by x.regid";
            //System.out.println(qry);
            rs1 = st1.executeQuery(qry);
            while (rs1.next()) {
                JavaBeanFormSerializable lf = new JavaBeanFormSerializable();
                lf.setRegno(rs1.getString("regid"));
                lf.setName(rs1.getString("name"));
                lf.setFaname(rs1.getString("fname"));
                lf.setTotalgp(rs1.getString("ssc_tot_gpa"));
                lf.setDob(rs1.getString("dob"));
                lf.setCaste(rs1.getString("caste"));
                lf.setSsc_first_lang_marks(rs1.getString("ssc_first_lang_marks"));
                lf.setSsc_second_lang_marks(rs1.getString("ssc_second_lang_marks"));
                lf.setSsc_eng_marks(rs1.getString("ssc_eng_marks"));
                lf.setSsc_math_marks(rs1.getString("ssc_math_marks"));
                lf.setSsc_sci_marks(rs1.getString("ssc_sci_marks"));
                lf.setSsc_social_marks(rs1.getString("ssc_social_marks"));
                lf.setSsc_tot_marks(rs1.getString("ssc_tot_marks"));
                lf.setInter_passed(rs1.getString("inter_passed"));
                lf.setInter_first_marks(rs1.getString("inter_first_marks"));
                lf.setInter_second_marks(rs1.getString("inter_second_marks"));
                lf.setInter_third_marks(rs1.getString("inter_third_marks"));
                lf.setInter_marks(rs1.getString("inter_marks"));

                lf.setSsc_first_lang_marks_gpa(rs1.getString("ssc_first_lang_gpa"));
                lf.setSsc_second_lang_marks_gpa(rs1.getString("ssc_second_lang_gpa"));
                lf.setSsc_eng_marks_gpa(rs1.getString("ssc_eng_gpa"));
                lf.setSsc_math_marks_gpa(rs1.getString("ssc_math_gpa"));
                lf.setSsc_sci_marks_gpa(rs1.getString("ssc_sci_gpa"));
                lf.setSsc_social_marks_gpa(rs1.getString("ssc_social_gpa"));

                lf.setPhc(rs1.getString("phc"));
                lf.setExser(rs1.getString("exservice"));
                lf.setGender(rs1.getString("gender"));
                /*
                 ResultSet rs2 = st2.executeQuery(sql1);
                 if (rs2.next()) {
                 if (rs2.getInt("freezee") == 0) {
                 lf.setLabel("Edit");
                 lf.setHref("update.do?regid=" + rs1.getString("regid") + "");
                 } else if (rs2.getInt("freezee") == 1) {
                 lf.setLabel("");
                 lf.setHref("");
                 }
                 }
                 */
                j++;
                UserList.add(lf);
            }

            request.setAttribute("users", UserList);

            st.close();
            st1.close();
            con.close();

        } catch (Exception ex) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (rs1 != null) {
                rs1.close();
            }
            if (st2 != null) {
                st2.close();
            }
            if (st1 != null) {
                st1.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        if (j == 0) {
            return "notfound";
        }
        return "convinertrade";
    }

    public static String setFreeze_itiCode_verification(String sql, HttpServletRequest request, String iti_code) throws SQLException {
        int s = 0;
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = DBC.DBConnection.getConnection(request);
            pst = con.prepareStatement(sql);
            s = pst.executeUpdate();
            con.close();
            pst.close();
        } catch (Exception ex) {
            return "derror";
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }
        if (s > 0) {
            return "success";
        }
        return "failure";
    }

    public static String Loginhistory_rep(String sql, HttpServletRequest request, String uname) throws SQLException {
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        ResultSet rs = null;
        ArrayList UserList = new ArrayList();

        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                JavaBeanFormSerializable lf = new JavaBeanFormSerializable();
                lf.setFromd(rs.getString(2));
                lf.setTod(rs.getString(4));

                UserList.add(lf);
            }
            request.setAttribute("users", UserList);
            rs.close();
            st.close();
            con.close();
        } catch (Exception ex) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
            }
        }

        return "convinertrade";
    }

    public static String getAdmissionYear(String admnum){
        admnum = admnum.substring(6, 8);
        return admnum;
    }

    public static String getTableName(String admnum, String tablename) {
        admnum = admnum.substring(6, 8);
        tablename = tablename + "_20" + admnum;
        return tablename;
    }

    public static String getTableNamewithmonth(String admnum, String tablename, HttpServletRequest request) throws SQLException {

        Connection con = DBC.DBConnection.getConnection(request);
        String p = "";
        try {
            admnum = beans.MyUtil.getOptionValue("iti_params", "code", "value", "1", con);
            p = beans.MyUtil.getOptionValue("iti_params", "code", "value", "2", con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        tablename = tablename + "_" + admnum + "_" + p;

        return tablename;
    }

    public static String getTableNamewithCurrentYear(String admnum, String tablename, HttpServletRequest request) throws SQLException {
        admnum = admnum.substring(6, 8);
        Connection con = DBC.DBConnection.getConnection(request);
        String p = "";
        try {
            p = beans.MyUtil.getOptionValue("iti_params", "code", "value", "1", con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        tablename = tablename + "_" + p;

        return tablename;
    }

    public static String getITICode(String admnum) {
        admnum = admnum.substring(0, 4);
        return admnum;
    }

    public static String getOLDITICode(String admnum, HttpServletRequest request) throws SQLException {
        admnum = admnum.substring(0, 4);
        Connection con = DBC.DBConnection.getConnection(request);
        try {
            admnum = beans.MyUtil.getOptionValue("iti", "olditicode", "iti_code", admnum, con);
            con.close();
        } catch (SQLException es) {
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return admnum;
    }

    public static String getDistCode(String admnum) {
        admnum = admnum.substring(0, 2);
        return admnum;
    }

    public static String getDistName(String admnum, HttpServletRequest request) throws SQLException {
        admnum = admnum.substring(0, 2);
        Connection con = DBC.DBConnection.getConnection(request);
        try {
            admnum = beans.MyUtil.getOptionValue("dist_mst", "statecode", "dist_name", "28", "dist_code", admnum, con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return admnum;
    }

    public static String getITIName(String admnum, HttpServletRequest request) throws SQLException {
        admnum = admnum.substring(0, 4);
        Connection con = DBC.DBConnection.getConnection(request);
        try {
            admnum = beans.MyUtil.getOptionValue("iti", "iti_code", "iti_name", admnum, con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return admnum;
    }

    public static String getAPPITIName(String admnum, HttpServletRequest request) throws SQLException {
        admnum = admnum.substring(3, 5);
        Connection con = DBC.DBConnection.getConnection(request);
        try {
            admnum = beans.MyUtil.getOptionValue("iti", "appcode", "iti_name", admnum, con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return admnum;
    }

    public static String getOLDITIName(String admnum, HttpServletRequest request) throws SQLException {
        admnum = admnum.substring(0, 4);
        Connection con = DBC.DBConnection.getConnection(request);
        try {
            admnum = beans.MyUtil.getOptionValue("iti", "olditicode", "iti_name", admnum, con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        return admnum;
    }

    public static String getDurationYears(String admnum, HttpServletRequest request) throws SQLException {
        String tradeshot = getTradeShort(admnum);
        Connection con = DBC.DBConnection.getConnection(request);
        String durationyrs = "";
        try {
            durationyrs = beans.MyUtil.getOptionValue("ititrade_master", "trade_short", "durationyrs", tradeshot, con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return durationyrs;
    }

    public static String getDurationFromPeriod(String admnum) throws SQLException {
        String date = admnum.substring(6, 8);
        return "01-" + "08-20" + date;
    }

    public static String getDurationToPeriod(String admnum, HttpServletRequest request) throws SQLException {
        Connection con = DBC.DBConnection.getConnection(request);
        String tradeshot = getTradeShort(admnum);
        String durationyrs = "";
        try {
            durationyrs = beans.MyUtil.getOptionValue("ititrade_master", "trade_short", "durationyrs", tradeshot, con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        String date = admnum.substring(6, 8);
        int date1 = Integer.parseInt(date) + Integer.parseInt(durationyrs);
        return "31-07-20" + date1;
    }

    public static String getDurationFromPeriodMonthly(String admnum) throws SQLException {
        String date = admnum.substring(6, 8);
        return "" + "AUGUST-20" + date;
    }

    public static String getDurationToPeriodMonthly(String admnum, HttpServletRequest request) throws SQLException {
        String tradeshot = getTradeShort(admnum);
        Connection con = DBC.DBConnection.getConnection(request);
        String durationyrs = "";
        try {
            durationyrs = beans.MyUtil.getOptionValue("ititrade_master", "trade_short", "durationyrs", tradeshot, con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        String date = admnum.substring(6, 8);
        int date1 = Integer.parseInt(date) + Integer.parseInt(durationyrs);
        return "JULY-20" + date1;
    }

    public static String getTradeShort(String admnum) throws SQLException {
        admnum = admnum.substring(4, 6);
        return admnum;
    }

    public static String getTradeCode(String admnum, HttpServletRequest request) throws SQLException {
        admnum = admnum.substring(4, 6);
        Connection con = DBC.DBConnection.getConnection(request);
        try {
            admnum = beans.MyUtil.getOptionValue("ititrade_master", "trade_short", "trade_code", admnum, con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return admnum;
    }

    public static String getAPPTradeCode(String admnum, HttpServletRequest request) throws SQLException {
        //admnum = admnum.substring(6, 8);
         if(admnum.substring(0, 1).equalsIgnoreCase("1")){
            admnum = admnum.substring(7, 9);
        }else{
            admnum = admnum.substring(6, 8);
        }
        Connection con = DBC.DBConnection.getConnection(request);
        try {
            admnum = beans.MyUtil.getOptionValue("ititrade_master", "trade_short", "trade_code", admnum, con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return admnum;
    }

    public static String getTradeName(String admnum, HttpServletRequest request) throws SQLException {
        admnum = admnum.substring(4, 6);
        Connection con = DBC.DBConnection.getConnection(request);
        try {
            admnum = beans.MyUtil.getOptionValue("ititrade_master", "trade_short", "trade_name", admnum, con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return admnum;
    }

    public static String getAPPTradeName(String admnum, HttpServletRequest request) throws SQLException {
        if(admnum.substring(0, 1).equalsIgnoreCase("1")){
            admnum = admnum.substring(7, 9);
        }else{
            admnum = admnum.substring(6, 8);
        }
        //admnum = admnum.substring(6, 8);
        Connection con = DBC.DBConnection.getConnection(request);
        try {
            admnum = beans.MyUtil.getOptionValue("ititrade_master", "trade_short", "trade_name", admnum, con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return admnum;
    }

    public static String AjaxTest(String admnum) throws SQLException {
        return "hello. how are you man?";
    }

    public static String Current_AcademicYear(HttpServletRequest request) throws SQLException {
        Connection con = null;
        con = DBC.DBConnection.getConnectionGen();
        Statement st = con.createStatement();
        String sql = "select * from iti_params where code='7'";
        ResultSet rs = st.executeQuery(sql);

        String year = "";
        if (rs.next()) {
            year = rs.getString("value");
        }
        System.out.println("academic year is------>"+year);
        rs.close();
        st.close();
        con.close();
        try {
        } catch (Exception ex) {
            con.close();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return year;
    }

    public static String checkDate(String value, String rangefrom, String rangeto) {

        return "";
    }

    public static boolean arche_data_entry(String tablename, String where, HttpServletRequest request) throws SQLException {
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        String sql = "insert into _arche_" + tablename + " select * from " + tablename + " where " + where + "";
        try {
            st.executeUpdate(sql);

            st.close();
            con.close();
        } catch (Exception ex) {
            con.close();
        } finally {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return true;
    }

    public static String Yearend(HttpServletRequest request) throws SQLException {
        Connection con = null;
        con = DBC.DBConnection.getConnectionGen();
        Statement st = con.createStatement();
        String sql = "select * from iti_params where code='3'";
        ResultSet rs = st.executeQuery(sql);

        String year = "";
        if (rs.next()) {
            year = rs.getString("value");
        }
        rs.close();
        st.close();
        con.close();
        try {
        } catch (Exception ex) {
            con.close();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return year;
    }
public static List<flash_news> getRelatedLinks(String status, HttpServletRequest request) throws SQLException {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        List<flash_news> l = new ArrayList<flash_news>();
        flash_news bean = null;
        try {
            con = DBC.DBConnection.getConnection(request);
            String sql = "select message,links from flash_news where status='" + status + "' order by flashnewsid desc ";
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                bean = new flash_news();
                bean.setMessage(rs.getString("message"));
                bean.setLinks(rs.getString("links"));
                l.add(bean);
            }
           
        } catch (Exception ex) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return l;
    }
    public static boolean InsertStatus(int status, String month, String year, String id, HttpServletRequest request) throws SQLException {
        Connection con = DBC.DBConnection.getConnection(request);
        HttpSession session = request.getSession(true);
        int trno = DBC.generic.logTrace("" + session.getAttribute("userName"), "status", "insert", request);
        String rid = "" + session.getAttribute("role_id");
        String query = "insert into status(rid,status,month_year,loc_code,trno)values('" + rid + "','" + status + "','" + year + "_" + month + "','" + id + "','" + trno + "')";
        int x = 0;
        Statement st = con.createStatement();
        try {
            st = con.createStatement();
            x = st.executeUpdate(query);
        } catch (SQLException ex) {
            //System.out.println("ex" + ex);
        } finally {
            try {
                st.close();
                con.close();
            } catch (SQLException ex) {
                //System.out.println("ex" + ex);
            }
        }
        if (x > 0) {
            return true;
        }
        return false;
    }

    public static boolean checkStatus(int status, String month, String year, String loc_code, HttpServletRequest request) throws SQLException {
        Connection con = DBC.DBConnection.getConnection(request);
        HttpSession session = request.getSession(true);
        ResultSet rs = null;
        Statement st = con.createStatement();
        String sql = "select status from status where loc_code='" + loc_code + "' and month_year='" + year + "_" + month + "' and status='" + status + "'";
        try {
            rs = st.executeQuery(sql);
        } catch (Exception ex) {
        }
        int i = 0;
        if (rs.next()) {
            i++;
        }
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }
}
