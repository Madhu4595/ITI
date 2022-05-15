/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Master.Trade;

import DBC.RoleBasedOperations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
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
public class EditTrade extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        HttpSession session = request.getSession(true);
        
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7",request);
       
        int phase = Integer.parseInt(beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase",year,request));

        String iti_code = (String) session.getAttribute("ins_code");
        String trade_code = request.getParameter("trade_code");
        
        Connection con =DBC.DBConnection.getConnection(request);
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String iti_code_gen = request.getParameter("iti_code_conv");
        try{
        String sql1="select conv_approval from ititrade where iti_code = ? and conv_approval='1'";
        ps = con.prepareStatement(sql1);
        ps.setString(1, iti_code_gen);
        rs = ps.executeQuery();
        if(rs.next()){
           return mapping.findForward("derror"); 
        }

        session.setAttribute("trade_code", trade_code);
        String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));
        String returnpath = "";
        
        if (role_id.equals("4")) {
            String sql = "SELECT * FROM ititrade WHERE iti_code='" + iti_code + "' and trade_code='" + trade_code + "'";
            returnpath = RoleBasedOperations.EditConvinerTradeCodes(sql, request, trade_code);
        } else if (role_id.equals("3")) {
            String iti_code_conv = request.getParameter("iti_code_conv");
            //String sql = "SELECT * FROM ititrade WHERE iti_code='" + iti_code_conv + "' and trade_code='" + trade_code + "'";
            String sql = "SELECT * FROM ititrade WHERE iti_code='" + iti_code_conv + "' and trade_code='" + trade_code + "' ";
            session.setAttribute("iti_code_conv", iti_code_conv);
            returnpath = RoleBasedOperations.EditConvinerTradeCodes(sql, request, trade_code);
        
        }return mapping.findForward(returnpath);
        }catch(Exception ex){
            throw ex;
        
                
                }finally{
                        if(con!=null){
                        con.close();
                        }
                        }
            
                      
    
    }}
