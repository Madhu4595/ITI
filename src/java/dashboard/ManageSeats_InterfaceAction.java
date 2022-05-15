/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Gopi Daggumalle
 */
public class ManageSeats_InterfaceAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ////System.out.println("Entered");
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        String trade_code = (String) session.getAttribute("trade_code");
        //String sidc = (String) session.getAttribute("sidc");
        String role_id = "" + session.getAttribute("role_id");
        String iti_code = "";
        
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
       
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "seatmatrix_phase", year, "current", "true", con);
        
        if (role_id.equals("4")) {
            iti_code = (String) session.getAttribute("ins_code");
        } else if (role_id.equals("3")) {
            iti_code = (String) session.getAttribute("iti_code_ses");
        }

        //String strength_tot = (String) session.getAttribute("strength_tot");//from ititrade table
        String strength_tot = beans.MyUtil.getOptionValue("ititrade", "iti_code", "strength", iti_code,"trade_code",trade_code, con); 
        System.out.println("tot strength issssssssssssssssss:"+strength_tot);
        String[] category_code = request.getParameterValues("cat_code");
        String[] strength = request.getParameterValues("strength");
        String[] strength_vacant = request.getParameterValues("strength_vacant");
        String[] strength_fill = request.getParameterValues("strength_fill");
        int test_strength = 0;
        
        for (int j = 0; j < strength.length; j++) {
            int st_vac = Integer.parseInt(strength[j]) - Integer.parseInt(strength_fill[j]);
            if (st_vac < 0) {
                return mapping.findForward("lengthfail");
            }
            test_strength = test_strength + Integer.parseInt(strength[j]);
        }
        System.out.println("Strength is :: "+strength_tot);
        System.out.println("Strength is test :: "+test_strength);
        if (Integer.parseInt(strength_tot)< test_strength) {
            return mapping.findForward("lengthfail");
        }
        
        String sql = "delete from iti_seatmatrix where iti_code='" + iti_code + "' and trade_code='" + trade_code + "' and year='" + year + "' and phase=" + phase + "";
        PreparedStatement pst = con.prepareStatement(sql);
        System.out.println("delete query isssssssss"+sql);
        int s = pst.executeUpdate();
        ////System.out.println("S Value means delete status is : " + s);
        //hstore(Array['Hindi','Telugu','English','Social'],Array['12','14','20',NULL])
        String category_codes = "Array['";
        String strengths = "Array['";
        String strength_fills = "Array['";
        String strength_vacants = "Array['";
        int ititrade_strength_vacant=0;
        for (int i = 0; i < category_code.length; i++) {


            int st_vac = Integer.parseInt(strength[i]) - Integer.parseInt(strength_fill[i]);
            ititrade_strength_vacant+=st_vac;
            category_codes += category_code[i] + "','";
            strengths += strength[i] + "','";
            strength_fills += strength_fill[i] + "','";
            strength_vacants += st_vac + "','";
            //String query = "insert into iti_seatmatrix (iti_code,category_code,strength,strength_fill,strength_vacant,trade_code) values('" + iti_code + "','" + category_code[i] + "','" + strength[i] + "'," + strength_fill[i] + ",'" + st_vac + "','" + trade_code + "')";
            //String query="insert into iti_seatmatrix(iti_code,trade_code,strength,strength_fill,strength_vacant,year,phase) values('"+iti_code+"','"+trade_code+"',hstore('"+category_code[i]+"','"+strength[i]+"'),hstore('"+category_code[i]+"','"+strength_fill[i]+"'),hstore('"+category_code[i]+"','"+st_vac+"'),'"+year+"',"+phase+")";
            //System.out.println(query);
            //PreparedStatement pstm = con.prepareStatement(query);
            //pstm.executeUpdate();
            //pstm.close();
        }
       int ititrade_strength_fill=Integer.parseInt(strength_tot)-ititrade_strength_vacant;
        category_codes = category_codes.substring(0, category_codes.length() - 2) + "]";
        strengths = "hstore(" + category_codes + "," + strengths.substring(0, strengths.length() - 2) + "])";
        strength_fills = "hstore(" + category_codes + "," + strength_fills.substring(0, strength_fills.length() - 2) + "])";
        strength_vacants = "hstore(" + category_codes + "," + strength_vacants.substring(0, strength_vacants.length() - 2) + "])";

        String query = "insert into iti_seatmatrix(iti_code,trade_code,strength,strength_fill,strength_vacant,year,phase) values('" + iti_code + "','" + trade_code + "'," + strengths + "," + strength_fills + "," + strength_vacants + ",'" + year + "'," + phase + ")";
        System.out.println("insert query areeeeeeeeeeeeeeeeeeeeeeeeeeee:" + query);
        PreparedStatement pstm = con.prepareStatement(query);
        pstm.executeUpdate();
        String ititradeupdatequery="update ititrade set strength_vacant='"+ititrade_strength_vacant+"',strength_fill='"+ititrade_strength_fill+"' where iti_code='"+iti_code+"' and trade_code='"+trade_code+"'";
         System.out.println("ititradeupdatequery issssssssssss:"+ititradeupdatequery);
        pstm = con.prepareStatement(ititradeupdatequery);
        pstm.executeUpdate();
        pstm.close();
        con.close();

        return mapping.findForward(SUCCESS);
    }
}
