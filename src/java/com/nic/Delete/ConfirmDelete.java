package com.nic.Delete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ConfirmDelete extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("UR IN -> confirmDelete.java <-");

        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        Connection con = DBC.DBConnection.getConnection(request);
        
        DeleteAdmission_interfaceForm fm = (DeleteAdmission_interfaceForm) form;
        
        String clickk = beans.MyUtil.filterBad(request.getParameter("click"));
        String remarkk = beans.MyUtil.filterBad(request.getParameter("remark"));

        String admnum = beans.MyUtil.filterBad(fm.getAdm_num());
        String dsc_year = beans.MyUtil.filterBad(fm.getDsc_year());

        fm.setAdm_num(admnum);
        fm.setDsc_year(dsc_year);
        
        Statement st = con.createStatement();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String qry = null;
        try{
            
        String res_category = beans.MyUtil.filterBad(fm.getRes_category());
        String iti_code = beans.MyUtil.filterBad(fm.getIti_code());
        String trade_code = beans.MyUtil.filterBad(fm.getTrade_code());
        
        String sql = "select * from admissions.iti_admissions where adm_num=? and year_of_admission=?";
        System.out.println("sql"+sql);
        ps = con.prepareStatement(sql);
        ps.setString(1, admnum);
        ps.setString(2, dsc_year);
        rs = null;
        rs = ps.executeQuery();
        res_category = null;

        while (rs.next()) {
            res_category = rs.getString("res_category");
            fm.setRes_category(res_category);
            fm.setName(rs.getString("name"));
            fm.setRegid(rs.getString("regid"));
            fm.setFname(rs.getString("fname"));
            fm.setCaste(rs.getString("caste"));
            fm.setDob(rs.getString("dob"));
            fm.setGender(rs.getString("gender"));
            fm.setIti_code(rs.getString("iti_code"));
            fm.setRes_category(rs.getString("res_category"));
            fm.setTrade_code(rs.getString("trade_code"));
            String dist_code = DBC.RoleBasedOperations.getDistCode(admnum);
            String rank = beans.MyUtil.getOptionValue("ranks", "dist_code", "rank", dist_code, "regid", rs.getString("regid"), con);
            fm.setRank(rank);
        }       
            
            int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "admissions.iti_admissions", "delete", request);
            int k1=beans.MyUtil.updateField("admissions.iti_admissions", "adm_num", admnum, trno, request,"trno");
            String qryI = "delete from admissions.iti_admissions where adm_num=? and year_of_admission=?";
            System.out.println("Admission Deletetet issssssssss:"+qryI);
            ps =  con.prepareStatement(qryI);
            ps.setString(1, admnum);
            ps.setString(2, dsc_year);
            int k = ps.executeUpdate();
            String qryU="update  iti_seatmatrix set strength_fill=strength_fill || hstore(Array[?],Array[((strength_fill->?)::integer-1)::text]),strength_vacant=strength_vacant || hstore(Array[?],Array[((strength_vacant->?)::integer+1)::text]) where iti_code=? and trade_code=? and year=?";
            if (k > 0) {
                ps = con.prepareStatement(qryU);
                ps.setString(1, res_category);
                ps.setString(2, res_category);
                ps.setString(3, res_category);
                ps.setString(4, res_category);
                ps.setString(5, iti_code);
                ps.setString(6, trade_code);
                ps.setString(7, dsc_year);
                ps.executeUpdate();
            }
             
            request.setAttribute("adm_num", admnum);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
             
        }
   
        return mapping.findForward(SUCCESS);
    }
}
