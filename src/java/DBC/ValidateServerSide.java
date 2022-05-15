/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBC;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author HOME
 */
public class ValidateServerSide {
    public static String CheckPhotoExists(String admnum, HttpServletRequest request) throws SQLException {
        //admnum = admnum.substring(4, 6);
        Connection con = DBC.DBConnection.getConnection(request);
        try {
            admnum = beans.MyUtil.getOptionValue("exams.img_cand_photos_exams", "adm_num", "adm_num", admnum, con);
        } catch (Exception ex) {
            con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return admnum;
    }
}
