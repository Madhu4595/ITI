package beans;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.StringTokenizer;

public class test_center {

    public String test_center_code;
    private int length;
    private String possible;
    private boolean empty;
    private String[] possible_array;
    private String total, temp_tot = "";

    public test_center(String test_code) {
        super();
        this.test_center_code = test_code;
        possible = populate();
        PopulateArray();
    }

    public String getTotal() {
        return this.temp_tot;
    }

    public String populate() {
        String sql_temp = "Select tot_strength from iti where iti_code='" + test_center_code + "'";
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/fdca", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql_temp);
            while (rs.next()) {
                temp_tot = "" + rs.getInt("tot_strength");
            }
            try {
                rs.close();
                st.close();
            } catch (Exception ex) {
            }

        } catch (Exception ex) {
        } finally {
        }
        int start_val = 10;
        String temp = "";
        while (start_val <= 50) {
            String sql = "Select toiti_code from iti_distances a, iti b where b.iti_code=a.fromiti_code and fromiti_code='" + test_center_code + "' and toiti_code!='" + test_center_code + "' and govt='G' and distance <= " + start_val;
            temp = "";
            try {
                Class.forName("org.gjt.mm.mysql.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/fdca", "root", "");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                length = 0;
                while (rs.next()) {
                    sql = "select * from iti where iti_code = " + rs.getString("toiti_code") + " and govt='G'  and remaining_capacity >=" + temp_tot;
                    Statement st1 = con.createStatement();
                    ResultSet rs1 = st1.executeQuery(sql);
                    while (rs1.next()) {
                        if (!temp.equals("")) {
                            temp += ",";
                        }
                        temp += rs1.getString("iti_code");
                        length++;
                    }
                    try {
                        rs1.close();
                        st1.close();
                    } catch (Exception ex) {
                    }
                }
                try {
                    rs.close();
                    st.close();
                    con.close();
                } catch (Exception ex) {
                }
                if (temp.equals("")) {
                    start_val += 10;
                } else {
                    break;
                }
            } catch (Exception ex) {
            }
        }
        return temp;
    }

    public void addPossible(String code) {
        if (!possible.equals("")) {
            possible += ",";
        }
        possible = possible + code;
    }

    private void PopulateArray() {
        StringTokenizer str = new StringTokenizer(possible, ",");
        int i = 0;
        possible_array = new String[str.countTokens()];
        while (str.hasMoreTokens()) {
            possible_array[i++] = str.nextToken();
        }
        length = i;
        if (length > 0) {
            empty = false;
        } else {
            empty = true;
        }
    }

    public void removePossible(String code) {
        int index = possible.indexOf(code + ",");
        if (index != -1) {
            possible = possible.replace(code + ",", "");
        } else {
            index = possible.indexOf(code);
            if (index != -1) {
                possible = possible.replace(code, "");
            }
        }
        PopulateArray();
    }

    public String getRandom_center() {
        //PopulateArray();
        int index = 0;
        /* if (length > 1) {
         Random r = new Random();
         index = r.nextInt(length - 1);
         } */
        return possible_array[index];
    }

    public boolean isEmpty() {
        return empty;
    }
}
