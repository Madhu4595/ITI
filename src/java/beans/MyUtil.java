 

package beans;

import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import DBC.DBConnection;
import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class MyUtil
{
    static int[][] d;
    static int[][] p;
    static int[] inv;
    static PreparedStatement psmt;
    static ResultSet rs;
    static Connection conn;
    
    public static String filterBadQuot(String str) {
        str = ((str == null) ? "" : str);
        str = str.replaceAll("\"", "");
        return str;
    }
    
     
 public static boolean verificationDate(){
 
    boolean result=false;
   Timestamp verification_start_date=null;
   Timestamp verification_end_date=null;
   String sql = null;
   java.util.Date d = new java.util.Date();
   System.out.println("today date-->"+d);
      
              
             SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
   
   
           String apply_date1=sdf.format(d);
           Connection con1 = DBC.DBConnection.getConnectionGen();
           PreparedStatement ps = null;
           ResultSet rs = null;
           
      try {
                sql = "select verification_start_date,verification_end_date from admissions.admission_phase";
                System.out.println("###"+sql);
               ps = con1.prepareStatement(sql);
              rs = ps.executeQuery();
               if (rs.next()) {
                  
                 verification_start_date=rs.getTimestamp("verification_start_date");
                 verification_end_date=rs.getTimestamp("verification_end_date");
               } 
   
      String verification_s_date = sdf.format(verification_start_date);
      String verification_e_date=sdf.format(verification_end_date);
      System.out.println("verification start Date ---------------------->"+ sdf.parse(verification_s_date));
      System.out.println("Apply date in util---------------------->"+ sdf.parse(apply_date1));
       System.out.println("Verification end date-------->"+sdf.parse(verification_e_date));
        
    if(sdf.parse(apply_date1).after(sdf.parse(verification_s_date)) && sdf.parse(apply_date1).before(sdf.parse(verification_e_date))) 
    {
            System.out.println("verification  started");
            result=true;
              
        }
    else{
        result=false;
    }
}catch(Exception e){
     e.printStackTrace();
  }
      return result;
 }
 
    
    public static String filterBad(String str) {
        str = ((str == null) ? "" : str);
        str = str.replaceAll("delete ", "");
        str = str.replaceAll("select ", "");
        str = str.replaceAll("update ", "");
        str = str.replaceAll("drop ", "");
        str = str.replaceAll("alter ", "");
        str = str.replaceAll("insert ", "");
        str = str.replaceAll("truncate ", "");
        str = str.replaceAll("revoke ", "");
        str = str.replaceAll("union ", "");
        str = str.replaceAll("create ", "");
        str = str.replaceAll("grant ", "");
        str = str.replaceAll("script", "");
        str = str.replaceAll("'", "");
        str = str.replaceAll("\"", "");
        str = str.replaceAll(">", "");
        str = str.replaceAll("<", "");
        str = str.replaceAll("&", "");
        str = str.replaceAll("%", "");
        str = str.replaceAll("limit", "");
        str = str.replaceAll(";", "");
        str = str.replaceAll("\\*", "");
        str = str.replaceAll("=", "");
        str = str.replaceAll("\\(", "");
        str = str.replaceAll("\\)", "");
        return str;
    }
    
    public static boolean containsOnlyNumbers(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); ++i) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static String gettodaysDate() {
        final Date d = new Date();
        final int date = d.getDate();
        String datestr = "";
        String monstr = "";
        if (date < 10) {
            datestr = "0" + date;
        }
        else {
            datestr = "" + date;
        }
        final int mon = d.getMonth() + 1;
        if (mon < 10) {
            monstr = "0" + mon;
        }
        else {
            monstr = "" + mon;
        }
        final int year = d.getYear() + 1900;
        final String date2 = datestr + "-" + monstr + "-" + year;
        return date2;
    }
    
    public static boolean checkSes(final String tab, final String ufldname, final String pfldname, final String sesrole, final String user, final String pwd, final Connection con) throws SQLException {
        boolean perm = false;
        try {
            (MyUtil.psmt = con.prepareStatement("select * from " + tab + " where " + ufldname + "=? and h" + pfldname + "= ?")).setString(1, user);
            MyUtil.psmt.setString(2, pwd);
            MyUtil.rs = MyUtil.psmt.executeQuery();
            perm = MyUtil.rs.next();
        }
        catch (Exception ex) {}
        if (MyUtil.rs != null) {
            MyUtil.rs.close();
            MyUtil.rs = null;
        }
        if (MyUtil.psmt != null) {
            MyUtil.psmt.close();
            MyUtil.psmt = null;
        }
        return perm;
    }
    
    public static boolean checkGenrepSes(final String code, final String tab, final String ufldname, final String pfldname, final String sesrole, final String user, final Connection con) throws SQLException {
        boolean perm = false;
        try {
            (MyUtil.psmt = con.prepareStatement("select * from " + tab + " where " + ufldname + "=? ")).setString(1, user);
            MyUtil.rs = MyUtil.psmt.executeQuery();
            perm = MyUtil.rs.next();
            if (perm) {
                MyUtil.psmt = con.prepareStatement("select role from genrep where code='" + code + "'");
                MyUtil.rs = MyUtil.psmt.executeQuery();
                if (MyUtil.rs.next()) {
                    final String rl = MyUtil.rs.getString("role");
                    if (!rl.equals(sesrole)) {
                        perm = false;
                    }
                }
                else {
                    perm = false;
                }
            }
        }
        catch (Exception ex) {}
        if (MyUtil.rs != null) {
            MyUtil.rs.close();
            MyUtil.rs = null;
        }
        if (MyUtil.psmt != null) {
            MyUtil.psmt.close();
            MyUtil.psmt = null;
        }
        return perm;
    }
    
    public static String getParameter(final String param, final Connection con) throws SQLException {
        try {
            MyUtil.psmt = con.prepareStatement("select value from parameters where name='" + param + "'");
            MyUtil.rs = MyUtil.psmt.executeQuery();
            if (MyUtil.rs.next()) {
                if (MyUtil.rs != null) {
                    MyUtil.rs.close();
                    MyUtil.rs = null;
                }
                if (MyUtil.psmt != null) {
                    MyUtil.psmt.close();
                    MyUtil.psmt = null;
                }
                return MyUtil.rs.getString(1);
            }
        }
        catch (Exception ex) {}
        if (MyUtil.rs != null) {
            MyUtil.rs.close();
            MyUtil.rs = null;
        }
        if (MyUtil.psmt != null) {
            MyUtil.psmt.close();
            MyUtil.psmt = null;
        }
        return null;
    }
    
    public static String isBlank(String str) {
        str = ((str == null) ? "0" : str);
        str = str.replace('<', ' ');
        str = str.replace('>', ' ');
        str = str.replace('\'', ' ');
        str = str.replace('\"', ' ');
        str = str.replace('&', ' ');
        str = str.replace('%', ' ');
        return str;
    }
    
    public static String isBlank2(String str) {
        str = ((str == null) ? " " : str);
        str = str.replace('<', ' ');
        str = str.replace('>', ' ');
        str = str.replace('\'', ' ');
        str = str.replace('\"', ' ');
        str = str.replace('&', ' ');
        str = str.replace('%', ' ');
        return str;
    }
    
    public static String getValuefromXML(final String fxml, final String param_name) {
        String xval = "";
        try {
            final File fXmlFile = new File(fxml);
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            final NodeList nList = doc.getElementsByTagName("context-param");
            for (int temp = 0; temp < nList.getLength(); ++temp) {
                final Node nNode = nList.item(temp);
                if (nNode.getNodeType() == 1) {
                    final Element eElement = (Element)nNode;
                    final String pnm = getTagValue("param-name", eElement);
                    if (pnm.equals(param_name)) {
                        xval = getTagValue("param-value", eElement);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return xval;
    }
    
    private static String getTagValue(final String sTag, final Element eElement) {
        final NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        final Node nValue = nlList.item(0);
        return nValue.getNodeValue();
    }
    
    public static String ChDate(String dt) {
        if (dt != null && !dt.equals("")) {
            StringTokenizer str = null;
            String day = "";
            String mon = "";
            String year = "";
            if (dt.indexOf("/") >= 0) {
                str = new StringTokenizer(dt, "/");
            }
            else if (dt.indexOf("-") >= 0) {
                str = new StringTokenizer(dt, "-");
            }
            if (str != null && !str.equals("")) {
                while (str.hasMoreTokens()) {
                    year = str.nextToken();
                    mon = str.nextToken();
                    day = str.nextToken();
                }
            }
            dt = day + "-" + mon + "-" + year;
        }
        return dt;
    }
    
    public static String ChDate1(final String s1) {
        final String day = s1.substring(0, 2);
        final String month = s1.substring(3, 5);
        final String year = s1.substring(6, 10);
        final String date = year + '-' + month + '-' + day;
        return date;
    }
    
    public static int getMax(final String table, final String fld, final Connection con) throws SQLException {
        int maxnum = 0;
        try {
            MyUtil.psmt = con.prepareStatement("select max(" + fld + ") from " + table);
            MyUtil.rs = MyUtil.psmt.executeQuery();
            if (MyUtil.rs.next()) {
                maxnum = MyUtil.rs.getInt(1);
            }
        }
        catch (Exception ex) {}
        finally {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
        }
        return maxnum;
    }
    
    public static String getMonthname(final int monthno) {
        final String[] monthname = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
        if (monthno >= 0 && monthno < 13) {
            return monthname[monthno - 1];
        }
        return null;
    }
    
    public static String putOptions(final String tab, final String code, final String name, final HttpServletRequest request) throws SQLException {
        try {
            System.out.println("gvghvgvgvg");
            if (tab == null || code == null || name == null) {
                return null;
            }
            if (MyUtil.conn == null) {
                MyUtil.conn = DBConnection.getConnection(request);
            }
            MyUtil.psmt = MyUtil.conn.prepareStatement("select distinct " + code + "," + name + " from " + tab + " order by " + name);
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String val = "";
            while (MyUtil.rs.next()) {
                val = val + "<option value='" + MyUtil.rs.getString(1) + "' >" + MyUtil.rs.getString(2) + "</option>";
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return null;
        }
    }
    
    public static String putOptions(final String tab, final String code, final String name, final Connection con) throws SQLException {
        try {
             
            if (tab == null || code == null || name == null) {
                return null;
            }
            System.out.println("select distinct " + code + "," + name + " from " + tab + " order by " + name);
            MyUtil.psmt = con.prepareStatement("select distinct " + code + "," + name + " from " + tab + " order by " + name);
            MyUtil.rs = MyUtil.psmt.executeQuery();
            System.out.println("queryis----" + MyUtil.psmt);
            String val = "";
            while (MyUtil.rs.next()) {
                val = val + "<option value='" + MyUtil.rs.getString(1) + "' >" + MyUtil.rs.getString(2) + "</option>";
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            System.out.println("ex" + exception);
            exception.printStackTrace();
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return null;
        }
    }
    
    public static String putOptions(final String tab, final String code, final String name, final String whfld, final String whval, final HttpServletRequest request) throws SQLException {
        try {
            if (MyUtil.conn == null) {
                MyUtil.conn = DBConnection.getConnection(request);
            }
            final String q1 = "select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' order by " + name;
            MyUtil.psmt = MyUtil.conn.prepareStatement("select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' order by " + name);
            if (tab == null || code == null || name == null) {
                return null;
            }
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String val = "";
            while (MyUtil.rs.next()) {
                val = val + "<option value='" + MyUtil.rs.getString(1) + "' >" + MyUtil.rs.getString(2) + "</option>";
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return null;
        }
    }
    
    public static String putOptions(final String tab, final String code, final String name, final String whfld, final String whval, final Connection con) throws SQLException {
        try {
            final String q1 = "select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' order by " + name;
            System.out.println("query--ssssss->" + q1);
            MyUtil.psmt = con.prepareStatement("select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' order by " + name);
            if (tab == null || code == null || name == null) {
                return null;
            }
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String val = "<option value='' selected>--select--</option>";
            while (MyUtil.rs.next()) {
                val = val + "<option value='" + MyUtil.rs.getString(1) + "' >" + MyUtil.rs.getString(2) + "</option>";
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return null;
        }
    }
    
    public static String putOptions(final String tab, final String code, final String name, final String whfld, final String whval, final String whfld1, final String whval1, final Connection con) throws SQLException {
        try {
            MyUtil.psmt = con.prepareStatement("select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' and " + whfld1 + "='" + whval1 + "' order by " + name);
            System.out.println("select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' and " + whfld1 + "='" + whval1 + "' order by " + name);
            if (tab == null || code == null || name == null) {
                return null;
            }
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String val = "";
            while (MyUtil.rs.next()) {
                val = val + "<option value='" + MyUtil.rs.getString(1) + "' >" + MyUtil.rs.getString(2) + "</option>";
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return null;
        }
    }
    
    public static String putOptions(final String tab, final String code, final String name, final String whfld, final String whval, final String whfld1, final String whval1, final HttpServletRequest request) throws SQLException {
        try {
            if (MyUtil.conn == null) {
                MyUtil.conn = DBConnection.getConnection(request);
            }
            final String q1 = "select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' order by " + name;
            MyUtil.psmt = MyUtil.conn.prepareStatement("select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' and " + whfld1 + "='" + whval1 + "' order by " + name);
            if (tab == null || code == null || name == null) {
                return null;
            }
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String val = "";
            while (MyUtil.rs.next()) {
                val = val + "<option value='" + MyUtil.rs.getString(1) + "' >" + MyUtil.rs.getString(2) + "</option>";
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return null;
        }
    }
    
    public static String putOptions(final String tab, final String code, final String name, final String whfld, final String whval, final String whfld1, final String whval1, final String whfld2, final String whval2, final HttpServletRequest request) throws SQLException {
        try {
            final String q1 = "select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' order by " + name;
            if (MyUtil.conn == null) {
                MyUtil.conn = DBConnection.getConnection(request);
            }
            MyUtil.psmt = MyUtil.conn.prepareStatement("select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' and " + whfld1 + "='" + whval1 + "' and " + whfld2 + "='" + whval2 + "' order by " + name);
            if (tab == null || code == null || name == null) {
                return null;
            }
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String val = "";
            while (MyUtil.rs.next()) {
                val = val + "<option value='" + MyUtil.rs.getString(1) + "' >" + MyUtil.rs.getString(2) + "</option>";
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return null;
        }
    }
    
    public static String putOptions(final String tab, final String code, final String name, final String whfld, final String whval, final String whfld1, final String whval1, final String whfld2, final String whval2, final Connection con) throws SQLException {
        try {
            final String q1 = "select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' order by " + name;
            MyUtil.psmt = con.prepareStatement("select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' and " + whfld1 + "='" + whval1 + "' and " + whfld2 + "='" + whval2 + "' order by " + name);
            if (tab == null || code == null || name == null) {
                return null;
            }
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String val = "";
            while (MyUtil.rs.next()) {
                val = val + "<option value='" + MyUtil.rs.getString(1) + "' >" + MyUtil.rs.getString(2) + "</option>";
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return null;
        }
    }
    
    public static String showList(final String tab, final String code, final String name, final Connection con, final String target, final String tc, String orderby, final String ttype) throws SQLException {
        String qry = "";
        try {
            if (tab == null || code == null || name == null) {
                return null;
            }
            if (orderby != null && !orderby.equals("") && orderby.indexOf("order by") < 0) {
                orderby = " order by " + orderby;
            }
            final String name2 = name.replaceAll("#", ",");
            qry = "select distinct " + code + "," + name2 + " from " + tab + orderby;
            System.out.println(qry);
            MyUtil.psmt = con.prepareStatement(qry);
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String val = "";
            int slno = 1;
            while (MyUtil.rs.next()) {
                final String rscode = MyUtil.rs.getString(1);
                if (ttype.equals("textbox")) {
                    val = val + "<tr><td>" + slno + "</td><td><input type='text' name='" + tc + "' value='" + MyUtil.rs.getString(target) + "'></td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                else if (ttype.equals("checkbox")) {
                    val = val + "<tr><td>" + slno + "</td><td><input type='checkbox' name='" + tc + "' value='" + MyUtil.rs.getString(target) + "'></td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                else if (target != null && !target.equals("")) {
                    if (ttype.equals("href")) {
                        val = val + "<tr><td>" + slno + "</td><td><a href='" + target + "?" + tc + "=" + rscode + "' >" + MyUtil.rs.getString(2) + "</a></td>";
                    }
                    else if (ttype.equals("img")) {
                        final int ti = target.indexOf("#");
                        if (ti > 0) {
                            final String target_src = target.substring(0, ti);
                            final String imgsrc = target.substring(ti + 1, target.length());
                            val = val + "<tr><td>" + slno + "</td><td><a href='" + target_src + "?" + tc + "=" + rscode + "'><img src='" + imgsrc + "?" + tc + "=" + rscode + "' width=50 height=50></a></td><td>" + MyUtil.rs.getString(2) + "</td>";
                        }
                        else {
                            val = val + "<tr><td>" + slno + "</td><td><img src='" + target + "?" + tc + "=" + rscode + "' width=50 height=50></td><td>" + MyUtil.rs.getString(2) + "</td>";
                        }
                    }
                }
                else {
                    val = val + "<tr><td>" + slno + "</td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                ++slno;
                int ii = 3;
                StringTokenizer fldnms = null;
                fldnms = new StringTokenizer(name, "#");
                for (int ij = fldnms.countTokens(); ii < ij + 2; ++ii) {
                    String vv = MyUtil.rs.getString(ii);
                    if (vv == null) {
                        vv = "-";
                    }
                    final String fldnm = fldnms.nextToken();
                    val = val + "<td>" + vv + "</td>";
                }
                val += "</tr>";
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return "Refresh";
        }
    }
    
    public static String showList(final String tab, final String code, final String name, final String whfld, final String whval, final Connection con, final String target, final String tc, String orderby, final String ttype) throws SQLException {
        String qry = "";
        try {
            if (orderby != null && !orderby.equals("") && orderby.indexOf("order by") < 0) {
                orderby = " order by " + orderby;
            }
            final String name2 = name.replaceAll("#", ",");
            qry = "select distinct " + code + "," + name2 + " from " + tab + " where " + whfld + "='" + whval + "'" + orderby;
            MyUtil.psmt = con.prepareStatement(qry);
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String val = "";
            int slno = 1;
            while (MyUtil.rs.next()) {
                final String rscode = MyUtil.rs.getString(1);
                if (ttype.equals("textbox")) {
                    val = val + "<tr><td>" + slno + "</td><td><input type='text' name='" + tc + "' value='" + MyUtil.rs.getString(target) + "'></td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                else if (ttype.equals("checkbox")) {
                    val = val + "<tr><td>" + slno + "</td><td><input type='checkbox' name='" + tc + "' value='" + MyUtil.rs.getString(target) + "'></td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                else if (target != null && !target.equals("")) {
                    if (ttype.equals("href")) {
                        val = val + "<tr><td>" + slno + "</td><td><a href='" + target + "?" + tc + "=" + rscode + "' >" + MyUtil.rs.getString(2) + "</a></td>";
                    }
                    else if (ttype.equals("img")) {
                        final int ti = target.indexOf("#");
                        if (ti > 0) {
                            final String target_src = target.substring(0, ti);
                            final String imgsrc = target.substring(ti + 1, target.length());
                            val = val + "<tr><td>" + slno + "</td><td><a href='" + target_src + "?" + tc + "=" + rscode + "'><img src='" + imgsrc + "?" + tc + "=" + rscode + "' width=50 height=50></a></td><td>" + MyUtil.rs.getString(2) + "</td>";
                        }
                        else {
                            val = val + "<tr><td>" + slno + "</td><td><img src='" + target + "?" + tc + "=" + rscode + "' width=50 height=50></td><td>" + MyUtil.rs.getString(2) + "</td>";
                        }
                    }
                }
                else {
                    val = val + "<tr><td>" + slno + "</td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                ++slno;
                StringTokenizer fldnms = null;
                fldnms = new StringTokenizer(name, "#");
                for (int ij = fldnms.countTokens(), ii = 3; ii < ij + 2; ++ii) {
                    String vv = MyUtil.rs.getString(ii);
                    if (vv == null || "".equals(vv)) {
                        vv = "-";
                    }
                    final String fldnm = fldnms.nextToken();
                    val = val + "<td>" + vv + "</td>";
                }
                val += "</tr>";
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return "* Refresh Once*";
        }
    }
    
    public static String showList(final String tab, final String code, final String name, final String whfld, final String whval, final String whfld1, final String whval1, final Connection con, final String target, final String tc, String orderby, final String ttype) throws SQLException {
        String qry = "";
        try {
            if (orderby != null && !orderby.equals("") && orderby.indexOf("order by") < 0) {
                orderby = " order by " + orderby;
            }
            final String name2 = name.replaceAll("#", ",");
            qry = "select distinct " + code + "," + name2 + " from " + tab + " where " + whfld + "='" + whval + "' and " + whfld1 + "='" + whval1 + "'" + orderby;
            System.out.println("sdfsdlfjsff"+qry);
            MyUtil.psmt = con.prepareStatement(qry);
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String val = "";
            int slno = 1;
            while (MyUtil.rs.next()) {
                final String rscode = MyUtil.rs.getString(1);
                if (ttype.equals("textbox")) {
                    val = val + "<tr><td>" + slno + "</td><td><input type='text' name='" + tc + "' value='" + MyUtil.rs.getString(target) + "'></td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                else if (ttype.equals("checkbox")) {
                    val = val + "<tr><td>" + slno + "</td><td><input type='checkbox' name='" + tc + "' value='" + MyUtil.rs.getString(target) + "'></td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                else if (target != null && !target.equals("")) {
                    if (ttype.equals("href")) {
                        val = val + "<tr><td>" + slno + "</td><td><a href='" + target + "?" + tc + "=" + rscode + "' >" + MyUtil.rs.getString(2) + "</a></td>";
                    }
                    else if (ttype.equals("img")) {
                        final int ti = target.indexOf("#");
                        if (ti > 0) {
                            final String target_src = target.substring(0, ti);
                            final String imgsrc = target.substring(ti + 1, target.length());
                            val = val + "<tr><td>" + slno + "</td><td><a href='" + target_src + "?" + tc + "=" + rscode + "'><img src='" + imgsrc + "?" + tc + "=" + rscode + "' width=50 height=50></a></td><td>" + MyUtil.rs.getString(2) + "</td>";
                        }
                        else {
                            val = val + "<tr><td>" + slno + "</td><td><img src='" + target + "?" + tc + "=" + rscode + "' width=50 height=50></td><td>" + MyUtil.rs.getString(2) + "</td>";
                        }
                    }
                }
                else {
                    val = val + "<tr><td>" + slno + "</td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                ++slno;
                int ii = 3;
                StringTokenizer fldnms = null;
                fldnms = new StringTokenizer(name, "#");
                for (int ij = fldnms.countTokens(); ii < ij + 2; ++ii) {
                    String vv = MyUtil.rs.getString(ii);
                    if (vv == null) {
                        vv = "-";
                    }
                    final String fldnm = fldnms.nextToken();
                    val = val + "<td>" + vv + "</td>";
                }
                val += "</tr>";
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return "** Refresh **";
        }
    }
    
    public static String showList(final String tab, final String code, final String name, final String whfld, final String whval, final String whfld1, final String whval1, final String whfld2, final String whval2, final Connection con, final String target, final String tc, String orderby, final String ttype) throws SQLException {
        String qry = "";
        try {
            if (orderby != null && !orderby.equals("") && orderby.indexOf("order by") < 0) {
                orderby = " order by " + orderby;
            }
            final String name2 = name.replaceAll("#", ",");
            qry = "select distinct " + code + "," + name2 + " from " + tab + " where " + whfld + "='" + whval + "' and " + whfld1 + "='" + whval1 + "' and " + whfld2 + "='" + whval2 + "'" + orderby;
            MyUtil.psmt = con.prepareStatement(qry);
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String val = "";
            int slno = 1;
            while (MyUtil.rs.next()) {
                final String rscode = MyUtil.rs.getString(1);
                if (ttype.equals("textbox")) {
                    val = val + "<tr><td>" + slno + "</td><td><input type='text' name='" + tc + "' value='" + MyUtil.rs.getString(target) + "'></td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                else if (ttype.equals("checkbox")) {
                    val = val + "<tr><td>" + slno + "</td><td><input type='checkbox' name='" + tc + "' value='" + MyUtil.rs.getString(target) + "'></td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                else if (target != null && !target.equals("")) {
                    if (ttype.equals("href")) {
                        val = val + "<tr><td>" + slno + "</td><td><a href='" + target + "?" + tc + "=" + rscode + "' >" + MyUtil.rs.getString(2) + "</a></td>";
                    }
                    else if (ttype.equals("img")) {
                        final int ti = target.indexOf("#");
                        if (ti > 0) {
                            final String target_src = target.substring(0, ti);
                            final String imgsrc = target.substring(ti + 1, target.length());
                            val = val + "<tr><td>" + slno + "</td><td><a href='" + target_src + "?" + tc + "=" + rscode + "'><img src='" + imgsrc + "?" + tc + "=" + rscode + "' width=50 height=50></a></td><td>" + MyUtil.rs.getString(2) + "</td>";
                        }
                        else {
                            val = val + "<tr><td>" + slno + "</td><td><img src='" + target + "?" + tc + "=" + rscode + "' width=50 height=50></td><td>" + MyUtil.rs.getString(2) + "</td>";
                        }
                    }
                }
                else {
                    val = val + "<tr><td>" + slno + "</td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                ++slno;
                int ii = 3;
                StringTokenizer fldnms = null;
                fldnms = new StringTokenizer(name, "#");
                for (int ij = fldnms.countTokens(); ii < ij + 2; ++ii) {
                    String vv = MyUtil.rs.getString(ii);
                    if (vv == null) {
                        vv = "-";
                    }
                    final String fldnm = fldnms.nextToken();
                    val = val + "<td>" + vv + "</td>";
                }
                val += "</tr>";
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return "*** Refresh Once ***";
        }
    }
    
    public static String putCheckboxes(final String tab, final String code, final String name, final String whfld, final String whval, final String tagname, final Connection con) throws SQLException {
        try {
            final String q1 = "select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' order by " + name;
            MyUtil.psmt = con.prepareStatement("select distinct " + code + "," + name + " from " + tab + " where " + whfld + "='" + whval + "' order by " + name);
            if (tab == null || code == null || name == null) {
                return null;
            }
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String val = "";
            while (MyUtil.rs.next()) {
                val = val + "<input name= " + tagname + " type=checkbox value='" + MyUtil.rs.getString(1) + "' >" + MyUtil.rs.getString(2);
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return null;
        }
    }
    
    public static String getOptionValue(final String tab, final String code, final String name, final String val, final HttpServletRequest request) throws SQLException {
        try {
            MyUtil.conn = DBConnection.getConnection(request);
            MyUtil.psmt = MyUtil.conn.prepareStatement("select " + name + " from " + tab + " where " + code + " ='" + val + "'");
            System.out.println("psmt is ---1121212121---->" + MyUtil.psmt);
            if (tab == null || code == null || name == null || val == null) {
                return null;
            }
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String returnval = "";
            StringTokenizer fldnms = null;
            fldnms = new StringTokenizer(name, ",");
            if (MyUtil.rs.next()) {
                while (fldnms.hasMoreTokens()) {
                    final String fldnm = fldnms.nextToken();
                    returnval += MyUtil.rs.getString(fldnm);
                    returnval += "$";
                }
                returnval = returnval.substring(0, returnval.length() - 1);
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            MyUtil.conn.close();
            return returnval;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            if (MyUtil.conn != null) {
                MyUtil.conn.close();
                MyUtil.conn = null;
            }
            return "";
        }
    }
    
    public static String getOptionValue(final String tab, final String code, final String name, final String val, final Connection con) throws SQLException {
        try {
            System.out.println("select " + name + " from " + tab + " where " + code + "='" + val + "'");
            MyUtil.psmt = con.prepareStatement("select " + name + " from " + tab + " where " + code + "='" + val + "'");
//          MyUtil.psmt = con.prepareStatement("select " + name + " from " + tab + " where " + code + "=?");
//          MyUtil.psmt.setString(1,Integer.parseInt(val));

            
 
            if (tab == null || code == null || name == null || val == null) { return null; }
            System.out.println("sdkfjflsdfs" + MyUtil.psmt);
            rs=psmt.executeQuery();
            
            String returnval = "";
            StringTokenizer fldnms = null;
            fldnms = new StringTokenizer(name, ",");
            
            if (MyUtil.rs.next()) {
                while (fldnms.hasMoreTokens()) {
                    final String fldnm = fldnms.nextToken();
                    returnval += MyUtil.rs.getString(fldnm);
                    returnval += "$";
                }
                returnval = returnval.substring(0, returnval.length() - 1);
            }
            
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return returnval;
        }
        catch (Exception exception) {
            System.out.println("ex" + exception);
            exception.printStackTrace();
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return null;
        }
    }
    
    public static String getOptionValue(final String tab, final String code, final String name, final String val, final String code1, final String val1, final Connection con) throws SQLException {
        try {
            System.out.println("hello");
            System.out.println("select " + name + " from " + tab + " where " + code + " ='" + val + "' and " + code1 + "='" + val1 + "'");
            MyUtil.psmt = con.prepareStatement("select " + name + " from " + tab + " where " + code + " ='" + val + "' and " + code1 + "='" + val1 + "'");
            if (tab == null || code == null || name == null || val == null || code1 == null || val1 == null) {
                return null;
            }
            
            System.out.println("ranks query is--->" + psmt);
            
            MyUtil.rs = MyUtil.psmt.executeQuery();
             
            
            String returnval = "";
            StringTokenizer fldnms = null;
            fldnms = new StringTokenizer(name, ",");
            if (MyUtil.rs.next()) {
                while (fldnms.hasMoreTokens()) {
                    final String fldnm = fldnms.nextToken();
                    returnval += MyUtil.rs.getString(fldnm);
                    returnval += "$";
                }
                returnval = returnval.substring(0, returnval.length() - 1);
                System.out.println("returnval is--->" + returnval);
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return returnval;
        }
        catch (Exception exception) {
             System.out.println("rank error is--->" + exception);
             exception.printStackTrace();
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return null;
        }
    }
    
    public static String getOptionValue(final String tab, final String code, final String name, final String val, final String code1, final String val1, final String code2, final String val2, final Connection con) throws SQLException {
        try {
            if (tab == null || code == null || name == null || val == null || code1 == null || val1 == null || code2 == null || val2 == null) {
                return null;
            }
            MyUtil.psmt = con.prepareStatement("select " + name + " from " + tab + " where " + code + " ='" + val + "' and " + code1 + "='" + val1 + "' and " + code2 + "='" + val2 + "'");
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String returnval = "";
            StringTokenizer fldnms = null;
            fldnms = new StringTokenizer(name, ",");
            if (MyUtil.rs.next()) {
                while (fldnms.hasMoreTokens()) {
                    final String fldnm = fldnms.nextToken();
                    returnval += MyUtil.rs.getString(fldnm);
                    returnval += "$";
                }
                returnval = returnval.substring(0, returnval.length() - 1);
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return returnval;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return null;
        }
    }
    
    public static String getOptionValue(final String tab, final String code, final String name, final String val, final String code1, final String val1, final String code2, final String val2, final String code3, final String val3, final Connection con) throws SQLException {
        try {
            if (tab == null || code == null || name == null || val == null || code1 == null || val1 == null || code2 == null || val2 == null || code3 == null || val3 == null) {
                return null;
            }
            MyUtil.psmt = con.prepareStatement("select " + name + " from " + tab + " where " + code + " ='" + val + "' and " + code1 + "='" + val1 + "' and " + code2 + "='" + val2 + "' and " + code2 + "='" + val2 + "' and " + code3 + "='" + val3 + "'");
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String returnval = "";
            StringTokenizer fldnms = null;
            fldnms = new StringTokenizer(name, ",");
            if (MyUtil.rs.next()) {
                while (fldnms.hasMoreTokens()) {
                    final String fldnm = fldnms.nextToken();
                    returnval += MyUtil.rs.getString(fldnm);
                    returnval += "$";
                }
                returnval = returnval.substring(0, returnval.length() - 1);
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return returnval;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return null;
        }
    }
    
    public static boolean isValidDateTime(final String s) {
        if (s == null) {
            return false;
        }
        if (s.length() != 19) {
            return false;
        }
        if (s.indexOf(45) == 4 || s.indexOf(47) == 4) {
            return (s.lastIndexOf(45) == 7 || s.lastIndexOf(47) == 7) && s.indexOf(58) == 13 && s.lastIndexOf(58) == 16;
        }
        return (s.indexOf(45) == 2 || s.indexOf(47) == 2) && (s.lastIndexOf(45) == 5 || s.lastIndexOf(47) == 5) && s.indexOf(58) == 13 && s.lastIndexOf(58) == 16;
    }
    
    public static String convertDateTime(final String s, final String s1) {
        if (!isValidDateTime(s)) {
            return null;
        }
        String s2 = "";
        if (s.indexOf(45) == 4 || s.indexOf(47) == 4) {
            s2 = "D";
        }
        else {
            s2 = "I";
        }
        if (s2.equalsIgnoreCase(s1)) {
            return s;
        }
        final String s3 = s.substring(0, 10);
        final String s4 = s.substring(11);
        if (s1.equalsIgnoreCase("I")) {
            String s5 = s3.substring(8, 10);
            s5 += s3.charAt(7);
            s5 += s3.substring(5, 7);
            s5 += s3.charAt(4);
            s5 += s3.substring(0, 4);
            final String s6 = s5 + " " + s4;
            return s6;
        }
        if (s1.equalsIgnoreCase("D")) {
            String s7 = s3.substring(6, 10);
            s7 += s3.charAt(5);
            s7 += s3.substring(3, 5);
            s7 += s3.charAt(2);
            s7 += s3.substring(0, 2);
            final String s8 = s7 + " " + s4;
            return s8;
        }
        return null;
    }
    
    public static boolean isValidDate(final String s) {
        if (s == null) {
            return false;
        }
        if (s.length() != 10) {
            return false;
        }
        if (s.indexOf(45) == 4 || s.indexOf(47) == 4) {
            return s.lastIndexOf(45) == 7 || s.lastIndexOf(47) == 7;
        }
        return (s.indexOf(45) == 2 || s.indexOf(47) == 2) && (s.lastIndexOf(45) == 5 || s.lastIndexOf(47) == 5);
    }
    
    public static int updateField(final String tablename, final String field, final String value, final int tr_no, final HttpServletRequest request, final String field_type) throws SQLException {
        final Connection con = DBConnection.getConnection(request);
        PreparedStatement pst = null;
        int result = 0;
        try {
            final String sql = "update " + tablename + " set " + field_type + "= '" + tr_no + "' where " + field + "='" + value + "'";
            pst = con.prepareStatement(sql);
            result = pst.executeUpdate();
            pst.close();
        }
        catch (Exception ex) {}
        finally {
            if (pst == null) {
                pst.close();
            }
            if (con == null) {
                con.close();
            }
        }
        return result;
    }
    
    public static String convertDate(final String s, final String s1) {
        if (!isValidDate(s)) {
            return null;
        }
        String s2 = "";
        if (s.indexOf(45) == 4 || s.indexOf(47) == 4) {
            s2 = "D";
        }
        else {
            s2 = "I";
        }
        if (s2.equalsIgnoreCase(s1)) {
            return s;
        }
        if (s1.equalsIgnoreCase("I")) {
            String s3 = s.substring(8, 10);
            s3 += s.charAt(7);
            s3 += s.substring(5, 7);
            s3 += s.charAt(4);
            s3 += s.substring(0, 4);
            return s3;
        }
        if (s1.equalsIgnoreCase("D")) {
            String s4 = s.substring(6, 10);
            s4 += s.charAt(5);
            s4 += s.substring(3, 5);
            s4 += s.charAt(2);
            s4 += s.substring(0, 2);
            return s4;
        }
        return null;
    }
    
    public static String generateVerhoeff(final String num) {
        int c = 0;
        final int[] myArray = StringToReversedIntArray(num);
        for (int i = 0; i < myArray.length; ++i) {
            c = MyUtil.d[c][MyUtil.p[(i + 1) % 8][myArray[i]]];
        }
        return Integer.toString(MyUtil.inv[c]);
    }
    
    public static boolean validateVerhoeff(final String num) {
        int c = 0;
        final int[] myArray = StringToReversedIntArray(num);
        for (int i = 0; i < myArray.length; ++i) {
            c = MyUtil.d[c][MyUtil.p[i % 8][myArray[i]]];
        }
        return c == 0;
    }
    
    private static int[] StringToReversedIntArray(final String num) {
        int[] myArray = new int[num.length()];
        for (int i = 0; i < num.length(); ++i) {
            myArray[i] = Integer.parseInt(num.substring(i, i + 1));
        }
        myArray = Reverse(myArray);
        return myArray;
    }
    
    public static boolean LastDate() {
        boolean result = false;
        Timestamp to_date1 = null;
        String sql = null;
        final Date d = new Date();
        System.out.println("todayhjhshs date-->" + d);
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        final String apply_date1 = sdf.format(d);
        final Connection con1 = DBConnection.getConnectionGen();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            sql = "select application_to_date from admissions.admission_phase";
            System.out.println("###" + sql);
            ps = con1.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                to_date1 = rs.getTimestamp("application_to_date");
            }
            final Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(to_date1.getTime());
            cal.add(13, 1);
            to_date1 = new Timestamp(cal.getTime().getTime());
            System.out.println("added time----->" + to_date1);
            final String last_date = sdf.format(to_date1);
            System.out.println("Last date in util ---------------------->" + sdf.parse(last_date));
            System.out.println("Apply date in util---------------------->" + sdf.parse(apply_date1));
            if (sdf.parse(apply_date1).after(sdf.parse(last_date))) {
                System.out.println("date expired");
                result = false;
            }
            else {
                result = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    private static int[] Reverse(final int[] myArray) {
        final int[] reversed = new int[myArray.length];
        for (int i = 0; i < myArray.length; ++i) {
            reversed[i] = myArray[myArray.length - (i + 1)];
        }
        return reversed;
    }
    
    public static String summing(final String tablename, final String sumof, final String wherecondition, final HttpServletRequest request) throws SQLException {
        String sumval = "";
        final Connection con = DBConnection.getConnection(request);
        final Statement st = con.createStatement();
        final String qry = "select case when sum(" + sumof + "::integer) is null then '0' else  sum(" + sumof + "::integer) end from " + tablename + wherecondition;
        try {
            final ResultSet rs1 = st.executeQuery(qry);
            while (rs1.next()) {
                sumval = rs1.getString(1);
            }
            rs1.close();
            st.close();
            con.close();
        }
        catch (Exception ex) {
            return "E";
        }
        return sumval;
    }
    
    static {
        MyUtil.d = new int[][] { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 1, 2, 3, 4, 0, 6, 7, 8, 9, 5 }, { 2, 3, 4, 0, 1, 7, 8, 9, 5, 6 }, { 3, 4, 0, 1, 2, 8, 9, 5, 6, 7 }, { 4, 0, 1, 2, 3, 9, 5, 6, 7, 8 }, { 5, 9, 8, 7, 6, 0, 4, 3, 2, 1 }, { 6, 5, 9, 8, 7, 1, 0, 4, 3, 2 }, { 7, 6, 5, 9, 8, 2, 1, 0, 4, 3 }, { 8, 7, 6, 5, 9, 3, 2, 1, 0, 4 }, { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 } };
        MyUtil.p = new int[][] { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 1, 5, 7, 6, 2, 8, 3, 0, 9, 4 }, { 5, 8, 0, 3, 7, 9, 6, 1, 4, 2 }, { 8, 9, 1, 6, 0, 4, 3, 5, 2, 7 }, { 9, 4, 5, 3, 1, 2, 6, 8, 7, 0 }, { 4, 2, 8, 6, 5, 7, 3, 9, 0, 1 }, { 2, 7, 9, 3, 8, 0, 6, 4, 1, 5 }, { 7, 0, 4, 6, 9, 1, 3, 2, 5, 8 } };
        MyUtil.inv = new int[] { 0, 4, 3, 2, 1, 5, 6, 7, 8, 9 };
        MyUtil.psmt = null;
        MyUtil.rs = null;
        MyUtil.conn = null;
    }
    
    public static String showList1(final String tab, final String code, final String name, final String whfld, final String whval, final Connection con, final String target, final String tc, String orderby, final String ttype) throws SQLException {
        String qry = "";
        try {
            if (orderby != null && !orderby.equals("") && orderby.indexOf("order by") < 0) {
                orderby = " order by " + orderby;
            }
            final String name2 = name.replaceAll("#", ",");
            qry = "select distinct " + code + "," + name2 + " from " + tab + " where " + whfld + "='" + whval + "'" + orderby;
            MyUtil.psmt = con.prepareStatement(qry);
            MyUtil.rs = MyUtil.psmt.executeQuery();
            String val = "";
            int slno = 1;
            while (MyUtil.rs.next()) {
                final String rscode = MyUtil.rs.getString(1);
                if (ttype.equals("textbox")) {
                    val = val + "<tr><td>" + slno + "</td><td><input type='text' name='" + tc + "' value='" + MyUtil.rs.getString(target) + "'></td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                else if (ttype.equals("checkbox")) {
                    val = val + "<tr><td>" + slno + "</td><td><input type='checkbox' name='" + tc + "' value='" + MyUtil.rs.getString(target) + "'></td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                else if (target != null && !target.equals("")) {
                    if (ttype.equals("href")) {
                        val = val + "<tr><td>" + slno + "</td><td><a href='" + target + "?" + tc + "=" + rscode + "&"+whfld+"="+whval+"'>" + MyUtil.rs.getString(2) + "</a></td>";
                    }
                    else if (ttype.equals("img")) {
                        final int ti = target.indexOf("#");
                        if (ti > 0) {
                            final String target_src = target.substring(0, ti);
                            final String imgsrc = target.substring(ti + 1, target.length());
                            val = val + "<tr><td>" + slno + "</td><td><a href='" + target_src + "?" + tc + "=" + rscode + "'><img src='" + imgsrc + "?" + tc + "=" + rscode + "' width=50 height=50></a></td><td>" + MyUtil.rs.getString(2) + "</td>";
                        }
                        else {
                            val = val + "<tr><td>" + slno + "</td><td><img src='" + target + "?" + tc + "=" + rscode + "' width=50 height=50></td><td>" + MyUtil.rs.getString(2) + "</td>";
                        }
                    }
                }
                else {
                    val = val + "<tr><td>" + slno + "</td><td>" + MyUtil.rs.getString(2) + "</td>";
                }
                ++slno;
                StringTokenizer fldnms = null;
                fldnms = new StringTokenizer(name, "#");
                for (int ij = fldnms.countTokens(), ii = 3; ii < ij + 2; ++ii) {
                    String vv = MyUtil.rs.getString(ii);
                    if (vv == null || "".equals(vv)) {
                        vv = "-";
                    }
                    final String fldnm = fldnms.nextToken();
                    val = val + "<td>" + vv + "</td>";
                }
                val += "</tr>";
            }
            MyUtil.rs.close();
            MyUtil.rs = null;
            MyUtil.psmt.close();
            MyUtil.psmt = null;
            return val;
        }
        catch (Exception exception) {
            if (MyUtil.rs != null) {
                MyUtil.rs.close();
                MyUtil.rs = null;
            }
            if (MyUtil.psmt != null) {
                MyUtil.psmt.close();
                MyUtil.psmt = null;
            }
            return "* Refresh Once*";
        }
    }
    
}
