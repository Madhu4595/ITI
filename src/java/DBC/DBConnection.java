
package DBC;

import static DBC.DBConnection.getConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.upload.FormFile;

public class DBConnection extends Action{

    public static boolean insert(String table, String attributes, String[] values,HttpServletRequest request) {
        boolean result = false;
        Connection con = getConnection(request);
        Statement stat = null;
        String sql = "insert into " + table + "(";
        StringTokenizer str = new StringTokenizer(attributes, "$");
        String temp = "";
        while (str.hasMoreTokens()) {
            if (temp.equals("")) {
                temp = str.nextToken();
            } else {
                temp = temp + "," + str.nextToken();
            }
        }
        sql += temp;
        sql += ") values ";
        for (String s : values) {
            temp = "";
            StringTokenizer st = new StringTokenizer(s, "$");
            while (st.hasMoreTokens()) {
                if (temp.equals("")) {
                    temp = "(" + st.nextToken();
                } else {
                    temp = temp + "," + st.nextToken();
                }
            }

            sql = sql + temp + " ),";
        }
        char[] arr = sql.toCharArray();
        sql = "";
        for (int i = 0; i < arr.length - 1; i++) {
            sql += arr[i];
        }
        try {
            stat = con.createStatement();
            stat.executeUpdate(sql);
            result = true;
        } catch (Exception ex) {
        } finally {
            try {
                stat.close();
                con.close();
            } catch (Exception ex) {
            }
        }
        return result;
    }

    public static String getSSCdata(String rollno,String year,String stream) throws IOException{
        URL url;
        String inputLine, inputLine1 = "";
        String a="";
        try {
            a = "http://bseapwebdata.org/SSCDATAWEB.svc/rh/SSCData?RollNo=1328617522&Year=2013&Stream=A";
            url = new URL(a);
            URLConnection conn = url.openConnection();
            
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            while ((inputLine = br.readLine()) != null) {
                inputLine1 = inputLine1 + inputLine+"<br>";
            }
            br.close();

      
        } catch (MalformedURLException e) {
            return "";
        } catch (IOException e) {
            return inputLine1;
        }

        return inputLine1;
    }
    
     public static String sendsms(String message,String mobilenumber) throws IOException{
 URL url;
        String inputLine, inputLine1 = "";
        String a="";
        try {
            a ="http://www.onlinesmslogin.com/quicksms/api.php?username=sunrisegroup&password=hareesh&to="+mobilenumber+"&from=SUNRSE&message="+message+"";
            
            url = new URL(a);
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            while ((inputLine = br.readLine()) != null) {
                inputLine1 = inputLine1 + inputLine;
            }
            br.close();

      
        } catch (MalformedURLException e) {
            return "";
        } catch (IOException e) {
            return "";
        }

        return "true";
    }
    
    
    public static void insertPhoto(String tablename, String action, String fieldname, FormFile photo, String identifier, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        Connection con = DBC.DBConnection.getConnection(request);
        StringTokenizer str = new StringTokenizer(identifier, "$");
        String field = str.nextToken();
        String fileName = photo.getFileName();
        String value = str.nextToken();
        PreparedStatement pst;
        String val = beans.MyUtil.getOptionValue("iti_params", "code", "value", "14", con), x = "", y = "";
        
        
        if (val.equalsIgnoreCase("application")) {
            x = "";
        } else {
            x = " ,year_of_admission ";
            y = ",?";
        }
        int trno = DBC.generic.logTrace("" + session.getAttribute("userName"), tablename, action, request);
        if (!("").equals(fileName)) {
            try {

                String sql = "";
                int index = 1;
                if (action.equalsIgnoreCase("insert")) {
                    sql = "insert into " + tablename + "(" + field + "," + fieldname + x + ",trno) values(?,?" + y + ",?)";
                    pst = con.prepareStatement(sql);
                    pst.setString(index++, value);
                    String acy = "20" + DBC.RoleBasedOperations.getAdmissionYear(value);
                    pst.setBinaryStream(index++, photo.getInputStream(), (int) photo.getFileSize());
                    if (!val.equalsIgnoreCase("application")) {
                        pst.setString(index++, acy);
                    }

                    pst.setInt(index++, trno);
                    pst.executeUpdate();
                    pst.close();
                } else {
                    sql = "update " + tablename + " set " + fieldname + "=?,year_of_admission=?,trno=? where " + field + "='" + value + "'";
                    pst = con.prepareStatement(sql);
                    pst.setBinaryStream(index++, photo.getInputStream(), (int) photo.getFileSize());
                    String acy = "20" + DBC.RoleBasedOperations.getAdmissionYear(value);
                    if (!val.equalsIgnoreCase("application")) {
                        pst.setString(index++, acy);
                    }
                    pst.setInt(index++, trno);
                    pst.executeUpdate();
                    pst.close();
                }

            } catch (Exception ex) {
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    public static void insertPhoto_With_MultipleFields(String tablename, String fieldname, FormFile photo, String identifier,HttpServletRequest request) {
        Connection con = DBC.DBConnection.getConnection(request);
        StringTokenizer str = new StringTokenizer(identifier, "$");
        String field = str.nextToken();
        String fileName = photo.getFileName();
        String value = str.nextToken();
        PreparedStatement pst;
        if (!("").equals(fileName)) {
            try {
                String sql = "";
                int index = 1;
                sql = "insert into " + tablename + "(" + field + "," + fieldname + ") values(" + value + ",?)";
                pst = con.prepareStatement(sql);
                pst.setBinaryStream(index++, photo.getInputStream(), (int) photo.getFileSize());
                pst.executeUpdate();
                pst.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    public static void insertPhoto_2(String tablename, String action, String fieldname, FormFile photo, String identifier,HttpServletRequest request) {
        Connection con = DBC.DBConnection.getConnection(request);
        StringTokenizer str = new StringTokenizer(identifier, "$");
        String field = str.nextToken();
        String fileName = photo.getFileName();
        String value = str.nextToken();
        PreparedStatement pst;
        if (!("").equals(fileName)) {
            try {
                String sql = "";
                int index = 1;
                if (action.equalsIgnoreCase("insert")) {
                    sql = "insert into " + tablename + "(" + field + "," + fieldname + ") values(?,?)";
                    pst = con.prepareStatement(sql);
                    pst.setInt(index++, Integer.parseInt(value));
                } else {
                    sql = "update " + tablename + " set " + fieldname + "=? where " + field + "='" + value + "'";
                    pst = con.prepareStatement(sql);
                }
                pst.setBinaryStream(index++, photo.getInputStream(), (int) photo.getFileSize());
                pst.executeUpdate();
                pst.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /*public static String getReferral(HttpServletRequest request) {
            String referrer = request.getHeader("host"),val = "",bundlename="";
            String path = request.getContextPath();
            String img= "";
        try {
            Connection con = DBC.DBConnection.getConnectionGen();
            val = beans.MyUtil.getOptionValue("global_ap_tg.referral_global", "description='"+path+"' and referral", "statecode", referrer, con);
            //val = beans.MyUtil.getOptionValue("global_ap_tg.referral_global", "referral", "statecode", referrer, con);
            
            bundlename = beans.MyUtil.getOptionValue("global_ap_tg.referral_global", "description='"+path+"' and referral", "bundlename", referrer, con);
            //bundlename = beans.MyUtil.getOptionValue("global_ap_tg.referral_global", "referral", "bundlename", referrer, con);
            
            img = beans.MyUtil.getOptionValue("global_ap_tg.referral_global", "description='"+path+"' and referral", "img", referrer, con);
            //img = beans.MyUtil.getOptionValue("global_ap_tg.referral_global", "referral", "img", referrer, con);
            
            HttpSession session = request.getSession();
            
            session.setAttribute("statecode", val);
            session.setAttribute("bundlename", bundlename);
            session.setAttribute("img", img);
        } catch (Exception e) {
             return val;
        }
        return val;
    }*/

    public static Connection getConnectionGen() {
        Connection con;
        try {
            //String name="beans.MyUtil.putOptions(\"vvv\", \"vvv\", \"vvv\")";
            ResourceBundle bundle = ResourceBundle.getBundle("com.myapp.struts.db");
            String userid, pass, dbname;
            dbname = bundle.getString("dbname");
            pass = bundle.getString("password");
            userid = bundle.getString("userid");
            String url = bundle.getString("url") + ":" + bundle.getString("port");
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url + "/" + dbname, userid, pass);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

     public static Connection getConnection(HttpServletRequest request) {
        Connection con;
        String bundlename="";
        HttpSession session = request.getSession();
        bundlename = ""+session.getAttribute("bundlename"); 
       /* if(statecode.equalsIgnoreCase("28")){
            bundlename="com.myapp.struts.ap"; 
        }else if(statecode.equalsIgnoreCase("29")){
            bundlename="com.myapp.struts.ititg";
        }*/
        try {

            ResourceBundle bundle = ResourceBundle.getBundle("com.myapp.struts.db");
            String userid, pass, dbname;
            dbname = bundle.getString("dbname");
            pass = bundle.getString("password");
            userid = bundle.getString("userid");
            String url = bundle.getString("url") + ":" + bundle.getString("port");
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url + "/" + dbname, userid, pass);
            return con;
        } catch (Exception e) {
            System.out.println("Error"+e);
            e.printStackTrace();
        }
        return null;
    }
    public static Connection getConnectionforImage(HttpServletRequest request) {
        Connection con;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql:localhost:5432/itiap","postgres","postgres");
            return con;
        } catch (ClassNotFoundException e) {
            System.out.println("db exception e--->"+e);
        } catch (SQLException e) {
            System.out.println("db exception e--->"+e);
        }
        return null;
    }  

    public static String selectMenu(String roleid, int mid,HttpServletRequest request) {

        StringTokenizer str = new StringTokenizer(roleid, ",");
        String query = "Select distinct * from role_menus where mid = " + mid + " and ";
        String temp = "", result = "";
        while (str.hasMoreTokens()) {
            temp = temp + " SUBSTRING(role," + str.nextToken() + ",1)='1' ";
            if (str.hasMoreTokens()) {
                temp = temp + " or ";
            }
        }
        query = query + "  " + temp + " order by weight asc";
        Connection con = getConnection(request);
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            String name, link, temp2;
            while (rs.next()) {
                name = rs.getString("name");
                link = rs.getString("link");
                String title = rs.getString("title");
                temp2 = "<li><a href=\"" + link + "\"  title=\"" + title + "\">" + name + " </a></li>";
                result = result + temp2;
            }
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
            } catch (SQLException ex) {
            }
        }
        return result;
    }

    public static boolean PageAuthentication(String roleid, String pagename,HttpServletRequest request) {
        boolean check = false;
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        pagename = pagename.replaceAll(".java", "");
        pagename = pagename.replaceAll("_jsp", ".jsp");
        pagename = pagename.replaceAll("005f", "");
        
        String query = "Select SUBSTRING(role," + roleid + ",1) as check,lid from role_menus where menulink='" + pagename + "'";
       
        try {
             con = DBC.DBConnection.getConnection(request);
            st = con.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                
                String temp = rs.getString("check");
                if (temp.equals("1")) {
                    check = true;
                }
            }else{
                System.out.println("dghfdsgfdsjh44444444444444444444");
            }
//            rs.close();
//            st.close();
//            con.close();
        } catch (Exception e) {
            //e.printStackTrace();
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
            } catch (SQLException ex) {
            }
        }
        return check;
         
    }

    public static boolean RoleAuthentication(String code_to_verify, HttpServletRequest rs) {
        HttpSession ses = rs.getSession();
        int roleid = Integer.parseInt("" + ses.getAttribute("role_id"));
        String code = ses.getAttribute("ins_code") + "";

        /*
         * Roles
         * 
         * 1 : admin
         * 2 : AP
         * 3 : District
         * 4 : ITI
         * 5 : RDD
         * 6 : Data Entry Operator
         * 8 : Public
         */
        switch (roleid) {
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                if (code.length() == code_to_verify.length()) {
                    if (code.equals(code_to_verify)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    String dist_sub = code_to_verify.substring(0, 2);
                    if (code.equals(dist_sub)) {
                        return true;
                    } else {
                        return false;
                    }
                }

            case 4:
                if (code.equals(code_to_verify)) {
                    return true;
                } else {
                    return false;
                }
            case 5:
                boolean return_value = false;
                Connection con = DBC.DBConnection.getConnection(rs);
                Statement st = null;
                ResultSet rst = null;
                try {
                    if (code.equals(code_to_verify)) {
                        return_value = true;
                    } else {
                        st = con.createStatement();
                        String sql = "select dist_code from conviner_login where region_code='" + code + "'";
                        rst = st.executeQuery(sql);
                        while (rst.next()) {
                            String temp_code = rst.getString("dist_code");

                            if (code_to_verify.length() == 2) {
                                if (temp_code.equals(code_to_verify)) {
                                    return_value = true;
                                    break;
                                }
                            } else if (code_to_verify.length() == 4) {

                                String dist_sub = code_to_verify.substring(0, 2);
                                if (temp_code.equals(dist_sub)) {
                                    return_value = true;
                                    break;
                                }
                            }
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (rst != null) {
                            rst.close();
                        }
                        if (st != null) {
                            st.close();
                        }
                        if (con != null) {
                            con.close();
                        }
                    } catch (SQLException ex) {
                    }
                }
                return return_value;
            case 6:
                return false;
            case 8:
                return false;
        }
        return false;
    }
public Connection getconnection_jsp() throws ClassNotFoundException{
Connection con=null;
try{
Class.forName("org.postgresql.Driver");
con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/itiap", "postgres", "postgres");
           
}catch(SQLException e){
          
}
return con;
        }


 public static String getCSRFCode() {
		String csrfCode = "Error";
		      Random rdm = new Random();
		int rl = rdm.nextInt(54545451);
		try {
			String hash1 = Integer.toHexString(rl);
			while (hash1.length() < 6) {
				rl = rdm.nextInt(54545451);
				hash1 = Integer.toHexString(rl);
			}
			csrfCode = hash1.substring(0, 6).toUpperCase();			
		} catch (Exception e) {
			System.out.println("Exception in generating getCSRFCode() "+e);
			csrfCode = "Error";
		}
		return csrfCode;
	}  


}
