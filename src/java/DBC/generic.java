/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils; 

/**
 *
 * @author Gopi Daggumalle
 */
public class generic {

    public static void Unfreeze(String id, String status, String month_year,HttpServletRequest request) {

        String sql = "delete from status where loc_id=" + id + " and status=" + status;
        Connection con = DBC.DBConnection.getConnectionGen();
        Statement st = null;
        try {
            st = con.createStatement();
            st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(generic.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                if (st != null) {
                    st = null;
                }
                con.close();
                if (con != null) {
                    con = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(generic.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static String Insert_Return(String tablename, String values, String return_value,HttpServletRequest request) throws SQLException {

        String returning = "";
        String attri = "", val = "";
        StringTokenizer st = new StringTokenizer(values, ";");
        ResultSet rs = null;
        while (st.hasMoreTokens()) {
            String temp = st.nextToken();
            StringTokenizer st2 = new StringTokenizer(temp, "=");
            if ((attri.equals(""))) {
                attri = st2.nextToken();
                val = st2.nextToken();
            } else {
                attri = attri + "$" + st2.nextToken();
                val = val + "$" + st2.nextToken();
            }
        }
        Connection con = DBC.DBConnection.getConnectionGen();
        String query = "insert into " + tablename;
        String temp = " values( ";
        if (!(attri.equals("") || attri.length() == 0 || attri.equals("*"))) {
            query += "(";
        }
        StringTokenizer str = new StringTokenizer(attri, "$");
        StringTokenizer str2 = new StringTokenizer(val, "$");
        int len = str.countTokens();
        int len2 = str2.countTokens();
        int i = 1, j = 1;
        while (true) {
            if (str.hasMoreTokens()) {
                if (i == len) {
                    if (!(attri.equals("*"))) {
                        query += str.nextToken() + " )";
                    } else {
                        str.nextToken();
                    }
                } else {
                    query += str.nextToken() + " ,";
                    i++;
                }
            }
            if (str2.hasMoreTokens()) {
                String res = str2.nextToken();
                if (res.indexOf("(int)") != -1) {
                    String sub = res.substring(res.indexOf("(int)") + 5);
                    if (j == len2) {
                        temp += sub + " )";
                    } else {
                        temp += sub + " , ";
                        j++;
                    }
                } else {
                    if (j == len2) {
                        temp += "'" + res + "' )";
                    } else {
                        temp += "'" + res + "' , ";
                        j++;
                    }
                }
            }
            if (!(str.hasMoreTokens() || str2.hasMoreTokens())) {
                break;
            }
        }
        query += temp;
        query += " RETURNING " + tablename + "." + return_value;
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(query);
            rs = pst.executeQuery(); // Remove comments for this method to function as expected
            if (rs.next()) {
                returning = "" + rs.getInt(1);
            }
        } catch (Exception ex) {
        } finally {

            try {
                rs.close();
                pst.close();
                con.close();
            } catch (Exception ex) {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        }
        return returning;
    }

    public static int logTrace(String userid, String tablename, String transaction_type, HttpServletRequest rs) throws SQLException {
        System.out.println("useridis"+userid);
        Date d = new Date();
        Timestamp ts = new Timestamp(d.getTime());
        String stamp = ts.toString();
        String ip = rs.getRemoteAddr();
        String refrer = rs.getHeader("referer");
        System.out.println("referis"+refrer);
        HttpSession ses = rs.getSession();
        String sesid = ses.getId();
        String value_to_send = "ip=" + ip + ";userid=" + userid + ";referal=" + refrer + ";tablename=" + tablename + ";transaction_type=" + transaction_type + ";sessionid=" + sesid + ";ts=" + stamp;
        System.out.println("valuetosendis"+value_to_send);
        return Integer.parseInt(Insert_Return("logtr", value_to_send, "trno",rs));
    }

    public static String getCastes(HttpServletRequest request) throws SQLException {
        String return_val = "";
        String sql = " Select * from caste_master";
        Connection con = DBC.DBConnection.getConnectionGen();
        Statement st = null;
        ResultSet rs = null;
        try {
            return_val = "<option value=''>---Select---</option>";
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                return_val += "<option value='" +rs.getString("caste_name")+"'>" + rs.getString("caste_name") + "</option>";
            }
           rs.close();
           st.close();
           con.close();
        } catch (SQLException ex) {
            Logger.getLogger(generic.class.getName()).log(Level.SEVERE, null, ex);
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
        return return_val;
    }

    public static String getCurrentyear() {
        int current_year = Calendar.getInstance().get(Calendar.YEAR);
        String ret = "" + current_year;
        return ret;
    }

    public static String ifApplicantExists(String name, String dob, String phno) throws SQLException {
        /*
         * phno is currently being used as fathers name
         */
        Connection con = DBC.DBConnection.getConnectionGen();
        boolean ret_value = false;
        Statement st = null;
        ResultSet rs = null;
        String regid = "";
        try {
            st = con.createStatement();
           // String query = "Select * from application where name='" + name + "' and dob='" + beans.MyUtil.ChDate(dob) + "' and fname='" + phno + "'";
             String query = "Select * from student_application where ssc_regno='" + name +"'";
             System.out.println("query-0---"+query);
            rs = st.executeQuery(query);
            if (rs.next()) {
                regid = rs.getString("regid");
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
//            try {
//                rs.close();
//                st.close();
//                con.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(generic.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        return regid;
    }

    public static String printApplication(Connection con, int registration_id, String jsp_photo_page) throws SQLException {

        String output = "<tr><td colspan=4><font size=3>Personal Details</font></td></tr>";
        //String output1 = "<tr><td colspan=4><font size=3>College DEtails Details</font></td></tr>";
        output += "<tr><td colspan='2'>Registration Number:" + registration_id + "</td><td>Photo:</td><td><img src=" + jsp_photo_page + ".jsp?id=" + registration_id + " height=100 width=100/></tr>";
        // add more values to this array these are the values that need to be printed
       //String values_to_return_college_details[] = {"dist_name", "govt", "iti_name"};
        String values_to_return_personal_details[] = {"name", "fname", "addr", "mname", "phno", "adarno", "gender", "caste", "university_area", "pincode", "dob"};
        String temp = "<tr><td colspan=4><font size=3>SSC Marks</font></td></tr>";
        //String temp1 = "<tr><td colspan=4><font size=3>Applied Colleges</font></td></tr>";
        String values_to_return_ssc_marks[] = {"ssc_first_lang_marks", "ssc_first_lang_grade", "ssc_first_lang_gpa", "ssc_second_lang_marks", "ssc_second_lang_grade", "ssc_second_lang_gpa", "ssc_eng_marks", "ssc_eng_grade", "ssc_eng_gpa", "ssc_math_marks", "ssc_math_grade", "ssc_math_gpa", "ssc_sci_marks", "ssc_sci_grade", "ssc_sci_gpa", "ssc_social_marks", "ssc_social_grade", "ssc_social_gpa", "ssc_tot_marks", "ssc_math_grade", "ssc_tot_gpa"};
        // These are the labels for the above attributes        
        String labels_personal_details[] = {"Full Name", "Fathers/Husbands Name", "Address", "Mother's Name", "Phone Number", "Aadhar Card Number", "Gender", "Caste", "University Area", "Pincode", "Date of Birth"};
        //String labels_ssc_marks[] = {"First Language Marks","First Language Grade","First Language GPA","Second Language Marks","Second Language Grade","Second Language GPA", "English Marks", "English Grade", "English GPA","Maths Marks","Maths Grade","Maths GPA","Science Marks","Science Grade","Science GPA","Social Marks","Social Grade","Social GPA"};
        String labels_ssc_marks[] = {"subject", "Marks", "grade", "gpa"};
        //String labels_colleges[] = {"District", "Govt/Private", "College Name"};
        String labels_ssc_subjects[] = {"First Language", "Second Language", "English", "Maths", "Science", "Social", "Total"};
        //add table name

        String inter_temp = "<tr><td colspan=4><h1>Inter Marks</h1></td></tr>";
        boolean add_inter = false;
       
           
 
        String tablename = "print_application";
        String sql = "select * from " + tablename + " where regid=" + registration_id;
       System.out.println("qqqqqqqqqqqq----"+sql);
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            //out.println("The query is : " + sql);
            rs = st.executeQuery(sql);
            if (rs.next()) {
                int count = 0;
                for (int i = 0; i < values_to_return_personal_details.length; i++) {
                    if (count == 0) {
                        output += "<tr>";
                    }
                    output += "<td> " + labels_personal_details[i] + ":</td>";
                    output += "<td>" + rs.getString(values_to_return_personal_details[i]) + "</td>";
                    if (count == 1) {
                        count = 0;
                        output += "</tr>";
                    } else {
                        count++;
                    }
                }
                /*
                 * 
                 * SSC Marks appending
                 */

                temp += "<tr>";
                for (int i = 0; i < labels_ssc_marks.length; i++) {
                    temp += "<td>" + labels_ssc_marks[i] + "</td>";
                }
                temp += "</tr>";
                String temp2 = "";
                for (int i = 0, j = 0; i < values_to_return_ssc_marks.length; i++) {
                    if (i % 3 == 0) {
                        // new subject                        
                        if (i != 0) {
                            j++;
                            temp2 += "</tr>";
                        }
                        temp2 += "<tr><td>" + labels_ssc_subjects[j] + "</td>";
                    }
                    temp2 += "<td>" + rs.getString(values_to_return_ssc_marks[i]) + "</td>";
                }
                temp += temp2;

                add_inter = rs.getBoolean("inter_passed");
                /*
                 * Inter marks appending
                 * 
                 */

                if (add_inter) {
                    // the candidate has passed Inter need to get subjects and display marks
                    try {
                        String[] Fields = {"inter_first_sub", "inter_second_sub", "inter_third_sub"};
                        String[] Fields_marks = {"inter_first_marks", "inter_second_marks", "inter_third_marks"};
                        inter_temp += "<tr>";
                        for (int i = 0; i < 3; i++) {
                            inter_temp += "<td>" + rs.getString(Fields[i]) + "</td>";
                        }
                        inter_temp += "<td>Total</td><tr>";
                        for (int i = 0; i < 3; i++) {
                            inter_temp += "<td>" + rs.getString(Fields_marks[i]) + "</td>";
                        }

                        inter_temp += "<td>" + rs.getString("inter_marks") + "</td></tr>";
                        temp += inter_temp;
                    } catch (Exception ex) {
                    }
                }

            } else {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
                return null;
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

        }
        
        //String tablenames = "trade_sel";
        //String sqls = "select * from " + tablenames + " where regid=" + registration_id;
        
        
        
        output += temp;
       
        
       return output;
    }
    

    public static String printApplication(String regid,HttpServletRequest request) throws SQLException {
        Connection con = DBC.DBConnection.getConnectionGen();
        String photo_page = "imageRetrival";
        String result = "";
        System.out.println("regid-------- "+regid);
        try {
            result = printApplication(con, Integer.parseInt(regid), photo_page);
        } catch (Exception ex) {
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(generic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (con != null) {
            con.close();
        }
        return result;
    }

    public static String getTds(String query,HttpServletRequest request) {
        //label,field,type
        Connection con = DBC.DBConnection.getConnectionGen();
        String output = "";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            int count = 0;
            while (rs.next()) {
                if (count == 0) {
                    output += "<tr>\n";
                }
                output += "<td> " + rs.getString("lablename") + "</td>\n";
                String type = rs.getString("fieldtype");
                String temp = "";
                if (type.equals("select")) {
                    temp = "<html:" + type + " property=\"" + rs.getString("fieldname") + "\">";
                    temp += "\n</html:" + type + ">";
                } else {
                    temp = "<html:" + type + " property=\"" + rs.getString("fieldname") + "\"/>";

                }
                output += temp;
                if (count == 1) {
                    count = -1;
                    output += "</tr>\n";
                }
                count++;
            }
            String temp1 = "<tr><td colspan='2'></td></tr>\n";
            temp1 += "<tr><td colspan='2'><html:submit /></td></tr>";
            output += temp1;
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
            Logger.getLogger(generic.class.getName()).log(Level.SEVERE, null, ex);
        }

        return output;
    }

    public static String getFormBeanProperties(String query,HttpServletRequest request) {
        String output = "";
        Connection con = DBC.DBConnection.getConnectionGen();
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String temp = rs.getString("datatype");
                String property = rs.getString("fieldname");
                String property1 = property;
                output += temp + " " + property + ";\n";

                int l = property1.length();
                char c = Character.toUpperCase(property1.charAt(0));
                property1 = c + property1.substring(1);
                for (int i = 1; i < l; i++) {
                    if (property1.charAt(i) == ' ') {
                        c = Character.toUpperCase(property1.charAt(i + 1));
                        property1 = property1.substring(0, i) + c + property1.substring(i + 2);
                    }
                }
                String concat = "public " + temp + " get".concat(property1) + "()\n{\nreturn " + property + ";\n}\n\n";

                output += concat;

                String concat1 = "public void set".concat(property1) + "(" + temp + " " + property + ")\n{\n this." + property + "=" + property + ";\n}\n\n";
                output += concat1;
            }
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
        return output;
    }

    public static String validations(String query,HttpServletRequest request) {
        String output = "";
        Connection con = DBC.DBConnection.getConnectionGen();
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            int bool = 0;
            while (rs.next()) {
                String temp = rs.getString("datatype");
                String property = rs.getString("fieldname");
                String property1 = property;

                int l = property1.length();
                char c = Character.toUpperCase(property1.charAt(0));
                property1 = c + property1.substring(1);
                for (int i = 1; i < l; i++) {
                    if (property1.charAt(i) == ' ') {
                        c = Character.toUpperCase(property1.charAt(i + 1));
                        property1 = property1.substring(0, i) + c + property1.substring(i + 2);
                    }
                }

                bool = rs.getInt("required");
                if (bool == 1) {
                    output = output + "if (get".concat(property1) + "() == null || get".concat(property1) + "().length()<1){\nerrors.add(\"" + property + "\" , new ActionMessage(\"error." + property + ".required\"));\n}\n\n";
                }

                output = output + "if (!Validations.Validate(get".concat(property1) + "() , " + rs.getString("validation") + "){\n errors.add(\"" + property + "\" , new ActionMessage(\"error." + property + ".notmatch\"));\n}\n\n";
                output = output + "if (get".concat(property1) + "().length()>" + rs.getInt("maxlen") + "){\nerrors.add(\"" + property + "\" , new ActionMessage(\"error." + property + ".lenerror\"));\n}\n\n";
            }
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
        return output;
    }

    public static String createtable(int slno, String tablename,HttpServletRequest request) throws SQLException {
        String output = "CREATE TABLE " + tablename + "( ";
        Connection con = DBC.DBConnection.getConnectionGen();
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        try {
            String main2 = "SELECT * FROM forms_details where formkey=" + slno + "";
            ResultSet rs2 = st.executeQuery(main2);
            while (rs2.next()) {
                output += rs2.getString("fieldname") + " varchar(" + rs2.getString(7) + ") ,";
            }
            output = output.substring(0, output.length() - 1);

            output += ");";

            PreparedStatement stmt1 = con.prepareStatement(output);
            st1.executeUpdate(output);
            if (st1 != null) {
                st1.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception ex) {
        }
        return output;
    }

    public static String getActionCode(String query, String formname, String tablename,HttpServletRequest request) {
        String output = "\n";
        Connection con = DBC.DBConnection.getConnectionGen();
        Statement st;
        try {
            output += formname + " formvariable = (" + formname + ") form;\n";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            String qry = "", qry1 = "", qry2 = "", qry3 = "";
            int i1 = 0;
            while (rs.next()) {
                i1++;
                String temp = rs.getString("datatype");
                String property = rs.getString("fieldname");
                String property1 = property;

                qry += "" + property + ",";
                qry1 += "'\"+" + property + "+\"',";
                qry2 += "?,";
                qry3 += "st.set" + temp + "(" + i1 + "," + property + ");\n";
                int l = property1.length();
                char c = Character.toUpperCase(property1.charAt(0));
                property1 = c + property1.substring(1);
                for (int i = 1; i < l; i++) {
                    if (property1.charAt(i) == ' ') {
                        c = Character.toUpperCase(property1.charAt(i + 1));
                        property1 = property1.substring(0, i) + c + property1.substring(i + 2);
                    }
                }

                output += temp + " " + property + " = formvariable.get" + property1 + "();\n";
            }
            qry = qry.substring(0, qry.length() - 1);
            qry1 = qry1.substring(0, qry1.length() - 1);
            qry2 = qry2.substring(0, qry2.length() - 1);
            output += "\n Connection con = DBConnection.dbConn();";
            output += "\n//make the import for DBConnection, if connection not possible\n";
            output += "String query1 = \"insert into " + tablename + "(" + qry + ") values (" + qry1 + ")\";\n";
            output += "PreparedStatement stmt11 = con.prepareStatement(query1);";
            output += "stmt11.executeUpdate();";

            output += "\n\n//another way of inserting data.... This is recomended\n\n";

            output += "PreparedStatement st;\n";
            output += "st = con.prepareStatement(\"Insert into " + tablename + "(" + qry + ") values (" + qry2 + ")\");\n\n";

            output += qry3 + "\n";
            output += "st.executeUpdate();";

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
        return output;
    }

    public static String getvalidationxml(String query, String formname, String tablename,HttpServletRequest request) {
        String output = "\n";
        Connection con = DBC.DBConnection.getConnectionGen();
        Statement st;
        try {
            output += "<form name=\"" + formname + "\">\n";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String temp = rs.getString("datatype");
                String property = rs.getString("fieldname");
                String property1 = property;

                output += "<field property=\"" + property + "\" depends=\"required,mask\">";
                output += "<arg key=\"" + formname + "." + property + "\"/>\n\t<var>\n\t<var-name>mask</var-name>\n<var-value>^[0-9a-zA-Z]*$</var-value>\n</var>\n</field>\n\n";
                int l = property1.length();
                char c = Character.toUpperCase(property1.charAt(0));
                property1 = c + property1.substring(1);
                for (int i = 1; i < l; i++) {
                    if (property1.charAt(i) == ' ') {
                        c = Character.toUpperCase(property1.charAt(i + 1));
                        property1 = property1.substring(0, i) + c + property1.substring(i + 2);
                    }
                }

            }
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
        return output;
    }
    /* CREATE TABLE activity
     (
     emp_code character varying(4),
     month character varying(2), -- 
     year character varying(4),
     leavefrom date,
     leaveto date,
     activity character varying(200)
     ) */

    public static String ifApplicantExists(String name, String dob, String fname, HttpServletRequest request) {
        System.out.println("dsiuhfuisdhfs");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    


    public boolean checkLogin(String tablename, String name, String password, String code, HttpServletRequest request) {
        boolean test = false;
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        String username = null;
        try {
            String query1 = "select verificationofficer_code from vo_iti_mapping where iti_code='" + code + "' ";

            HttpSession session = request.getSession(true);
            con = DBC.DBConnection.getConnectionGen();
            //String Query = "select * from login_table where userid='" + name + "' and hash_password='" + password + "';";
            ps = con.prepareStatement(query1);
            rs1 = ps.executeQuery();
            if (rs1.next()) {
                username = rs1.getString("verificationofficer_code");
            }
            String Query = "select * from " + tablename + " where username='" + username + "'";
            st = con.createStatement();
            rs = st.executeQuery(Query);
            if (rs.next()) {
                String userid = rs.getString("username");
                String password_temp = rs.getString("hash_password");
                password_temp = password_temp.replaceAll(" ", "").replaceAll("\r", "");
                String hashedsalt = DigestUtils.md5Hex(session.getAttribute("salt").toString().replaceAll(" ", "").replaceAll("\r", ""));
                String temp, temp2;

                hashedsalt = hashedsalt.replaceAll(" ", "").replaceAll("\r", "");//.replaceAll("\n", "");
                password_temp = password_temp.replaceAll(" ", "").replaceAll("\r", "");//.replaceAll("\n", "");
                temp2 = hashedsalt + password_temp;
                temp2 = hashedsalt + password_temp;
                password = password.replaceAll(" ", "").replaceAll("\r", "");//.replaceAll("\n", "");
                temp2 = temp2.replaceAll(" ", "").replaceAll("\r", "");//.replaceAll("\n", "");
                temp2 = DigestUtils.md5Hex(temp2);
                password = DigestUtils.md5Hex(password);
                if (password.equals(temp2) && userid.equals(name)) {

                    return true;
                }
            }
            rs.close();
            rs1.close();
            st.close();
            con.close();
        } catch (Exception ex) {
        }
        return test;
    }
}
