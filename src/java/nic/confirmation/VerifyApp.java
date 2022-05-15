/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.confirmation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Gopi Daggumalle
 */
public class VerifyApp {

    public static String printApplication(Connection con, int registration_id, String jsp_photo_page) {

        String output = "";

        // add more values to this array these are the values that need to be printed
        String values_to_return[] = {"regid", "name", "fname", "dob", "inter_marks", "caste"};

        // These are the labels for the above attributes
        String labels[] = {"Application No", "Full Name", "Fathers Name", "Date of Birth", "Inter Marks", "Caste"};

        //add table name
        String tablename = "application";

        String sql = "select * from " + tablename + " where regid=" + registration_id;
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            System.out.println("THe query is : " + sql);
            rs = st.executeQuery(sql);
            if (rs.next()) {
                int count = 0;
                for (int i = 0; i < values_to_return.length; i++) {
                    if (count == 0) {
                        output += "<tr>";
                    }
                    output += "<td> " + labels[i] + ":</td>";
                    output += "<td>" + rs.getString(values_to_return[i]) + "</td>";
                    if (count == 1) {
                        count = 0;
                        output += "<tr>";
                    } else {
                        count++;
                    }
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
        } finally {
        }
        // for photos        
        // output += "<tr><td>Photo:</td><td><img src=" + jsp_photo_page + ".jsp?id=" + registration_id + " height=100 width=100/></tr>";
        return output;
    }

    public static String getTds(String query,HttpServletRequest request) {
        //label,field,type
        Connection con = DBC.DBConnection.getConnection(request);
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
        } catch (SQLException ex) {
            Logger.getLogger(VerifyApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return output;
    }

    public static String getFormBeanProperties(String query,HttpServletRequest request) throws SQLException {
        String output = "";
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
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
                //System.out.println(property1);
                String concat = "public " + temp + " get".concat(property1) + "()\n{\nreturn " + property + ";\n}\n\n";

                output += concat;

                String concat1 = "public void set".concat(property1) + "(" + temp + " " + property + ")\n{\n this." + property + "=" + property + ";\n}\n\n";
                output += concat1;
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
        return output;
    }

    public static String validations(String query,HttpServletRequest request) throws SQLException {
        String output = "";
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
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
        return output;
    }

    public static String createtable(int slno, String tablename,HttpServletRequest request) throws SQLException {
        String output = "CREATE TABLE " + tablename + "( ";
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        ResultSet rs = null;
        try {
            String main2 = "SELECT * FROM forms_details where formkey=" + slno + "";
            ResultSet rs2 = st.executeQuery(main2);
            while (rs2.next()) {
                output += rs2.getString("fieldname") + " varchar(" + rs2.getString(7) + ") ,";
            }
            output = output.substring(0, output.length() - 1);

            output += ");";

            System.out.println(output);
            PreparedStatement stmt1 = con.prepareStatement(output);
            st1.executeUpdate(output);
        } catch (Exception ex) {
            System.out.println(ex);
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
        return output;
    }

    public static String getActionCode(String query, String formname, String tablename,HttpServletRequest request) throws SQLException {
        String output = "\n";
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = null;
        ResultSet rs = null;
        try {
            output += formname + " formvariable = (" + formname + ") form;\n";
            st = con.createStatement();
            rs = st.executeQuery(query);
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
                //System.out.println(property1);
                //output="\n hello \n";

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
        return output;
    }

    public static String getvalidationxml(String query, String formname, String tablename,HttpServletRequest request) throws SQLException {
        String output = "\n";
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = null;
        ResultSet rs = null;
        try {
            output += "<form name=\"" + formname + "\">\n";
            st = con.createStatement();
            rs = st.executeQuery(query);
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
}
