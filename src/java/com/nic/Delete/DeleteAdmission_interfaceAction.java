package com.nic.Delete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeleteAdmission_interfaceAction extends org.apache.struts.action.Action {
   
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession(true);

        String stored_token = beans.MyUtil.filterBad("" + session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));

        String id = stored_token.substring(10, 20) + request.getSession().getId() + stored_token.substring(2, 6);
        if (!token.equals(id)) { return mapping.findForward("failure"); }

        Connection con = DBC.DBConnection.getConnection(request);

        String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));

        DeleteAdmission_interfaceForm fm = (DeleteAdmission_interfaceForm) form;
        String clickk = beans.MyUtil.filterBad(request.getParameter("click"));
        String remarkk = beans.MyUtil.filterBad(request.getParameter("remark"));

        System.out.println("check1" + clickk + "check2" + remarkk);
        String dsc_year = beans.MyUtil.filterBad(fm.getDsc_year());
        System.out.println("Dsc iejke" + dsc_year);
        fm.setDsc_year(dsc_year);
        String admnum = beans.MyUtil.filterBad(fm.getAdm_num());
        fm.setAdm_num(admnum);

        String adm_num_temp = beans.MyUtil.getOptionValue("admissions.iti_admissions", "adm_num", "adm_num", admnum, con);

        if (!admnum.equalsIgnoreCase(adm_num_temp)) {
            request.setAttribute("adm_num_wrong", "<font color=red><b><h2>Admission numberis already deleted or not yet admitted");
            if (con != null) {
                con.close();
            }
            return mapping.findForward("adm_num_wrong");
        }

        String ins_code = (String) beans.MyUtil.filterBad("" + session.getAttribute("ins_code"));
        // String role_id = (String) session.getAttribute("role_id");
        String temp_dist_code = admnum.substring(0, 2);
        if (role_id.equals("3")) {
            if (!temp_dist_code.equals(ins_code)) {
                request.setAttribute("adm_num_wrong", "<font color=red><b><h2>You are trying to access another District Data... Admission Number is Invalid..");
                if (con != null) {
                    con.close();
                }
                return mapping.findForward("adm_num_wrong");
            } else {

                ins_code = DBC.RoleBasedOperations.getDistName(admnum, request);//"";//(String) session.getAttribute("ins_code");
                //String temp_dist_code = admnum.substring(0, 2);
                String y = beans.MyUtil.getOptionValue("iti", "iti_code", "iti_code", admnum.substring(0, 4), con);
                if (!y.equalsIgnoreCase(admnum.substring(0, 4))) {
                    request.setAttribute("adm_num_wrong", "<font color=red><b><h2>Admission number not allowed., Because This ITI Code is not Entered in our Database");
                    if (con != null) {
                        con.close();
                    }
                    return mapping.findForward("adm_num_wrong");
                }
                if (ins_code.equalsIgnoreCase("")) {
                    request.setAttribute("adm_num_wrong", "<font color=red><b><h2>You are trying to access another District Data... Admission Number is Invalid..");
                    if (con != null) {
                        con.close();
                    }
                    return mapping.findForward("adm_num_wrong");
                }
                String trade_name = DBC.RoleBasedOperations.getTradeName(admnum, request);
                if (trade_name.equals("")) {
                    request.setAttribute("adm_num_wrong", "<font color=red><b><h2>Admission Number is Invalid..Not a valid trade Entered in Admission Number");
                    if (con != null) {
                        con.close();
                    }
                    return mapping.findForward("adm_num_wrong");
                }
                //JavaBeanFormSerializable fm1 =new JavaBeanFormSerializable();
                // String dsc_year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7",con);
                // String dsc_year = beans.MyUtil.getOptionValue("admissions.iti_admissions", "adm_num", "year_of_admission", admnum,con);
                System.out.println("dsc_year is---->" + dsc_year);
                fm.setDsc_year(dsc_year);

                String sql = "select * from admissions.iti_admissions where adm_num=?";
                System.out.println("sql" + sql);
                //Statement st = con.createStatement();
                ResultSet rs = null;
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, admnum);
                rs = ps.executeQuery(sql);
                String res_category = null;

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
                    // fm.setDsc_year(rs.getString("year_of_admission"));

                    //dsc_year=rs.getString("year_of_admission");
                    String dist_code = DBC.RoleBasedOperations.getDistCode(admnum);
                    String rank = beans.MyUtil.getOptionValue("ranks", "dist_code", "rank", dist_code, "regid", rs.getString("regid"), con);
                    fm.setRank(rank);
                }
                if (rs != null) {
                    rs.close();
                }

                String trade_code = DBC.RoleBasedOperations.getTradeCode(admnum, request);
                fm.setTrade_code(trade_code);
                String iti_code = DBC.RoleBasedOperations.getITICode(admnum);
                fm.setIti_code(iti_code);
                String iti_name = DBC.RoleBasedOperations.getITIName(admnum, request);
                fm.setIti_name(iti_name);
                String trade_shot = DBC.RoleBasedOperations.getTradeShort(admnum);
                //String trade_name = DBC.RoleBasedOperations.getTradeName(admnum);
                fm.setTrade_shot(trade_shot);
                fm.setTrade_name(trade_name);
                //String year=beans.MyUtil.getOptionValue("iti_params", "code", "value", "7",con);
                String phase = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "seatmatrix_phase", dsc_year, "current", "true", con);
                // String qry = "select * from iti_seatmatrix where iti_code='" + iti_code + "' and trade_code='" + trade_code + "' and category_code='" + res_category + "'";
                String qry = "select a.iti_code,a.trade_code,a.category_code,a.strength,a.strength_fill,a.strength_vacant from (select iti_code,trade_code,skeys(strength) as category_code,svals(strength) as strength,svals(strength_vacant) as strength_vacant,svals(strength_fill) as strength_fill from iti_seatmatrix i where iti_code='" + iti_code + "' and trade_code='" + trade_code + "'  and year='" + dsc_year + "')a where a.category_code='" + res_category + "'";
                System.out.println("qry" + qry);
                 
                ps.setString(1, iti_code);
                ps.setString(2, trade_code);
                ps.setString(3, dsc_year);
                ps.setString(4, res_category);
                rs = ps.executeQuery(qry);
                while (rs.next()) {

                    fm.setStrength(rs.getString("strength"));
                    fm.setStrength_fill(rs.getString("strength_fill"));
                    fm.setStrength_vacant(rs.getString("strength_vacant"));
                    fm.setStrength_del(rs.getInt("strength"));
                    fm.setStrength_fill_del(rs.getInt("strength_fill") - 1);
                    fm.setStrength_vacant_del(rs.getInt("strength_vacant") + 1);
                    fm.setIti_code(rs.getString("iti_code"));
                    fm.setRes_category(res_category);
                    fm.setTrade_code(rs.getString("trade_code"));
                }
                String val = null;
                String click = null;
                try {

                    val = fm.getDelete();

                    //val = "Click here";
                } catch (Exception ec) {
                    System.out.println("exxxxxxxxxx" + ec);
                    val = "";
                }

                //val = fm.getDelete();
                System.out.println("Butttonnnnnnnnnnn" + val + "######" + click);
                String remarks = beans.MyUtil.filterBad(fm.getRemarks());
                String remark = beans.MyUtil.filterBad(fm.getRemark());
                System.out.println("remarksssssssss" + remarks + "@@@@@@@@@" + remark + "@@@########@@" + admnum);
                String sql1 = "update admissions.iti_admissions set remarks=? where adm_num=? and year_of_admission=?";
                ps.setString(1, remarks);
                ps.setString(2, admnum);
                ps.setString(3, dsc_year);
                ps.executeUpdate(sql1);

                if (clickk != null) {
                    //if (val.equalsIgnoreCase("Click here")) {
                    if (clickk.equalsIgnoreCase("button")) {

                        System.out.println("Val is coreeeeeee" + val);
                        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "admissions.iti_admissions", "delete", request);
                        int k1 = beans.MyUtil.updateField("admissions.iti_admissions", "adm_num", admnum, trno, request, "trno");
                        qry = "delete from admissions.iti_admissions where adm_num=? and year_of_admission=?";
                        ps.setString(1, admnum);
                        ps.setString(2, dsc_year);
                        System.out.println("Admission Deletetet issssssssss:");
                        int k = ps.executeUpdate(qry);
                        System.out.println("issssssssss:" + k);
                        // qry = "update iti_seatmatrix set strength_fill=strength_fill-1,strength_vacant=strength_vacant+1 where iti_code='" + iti_code + "' and trade_code='" + trade_code + "' and category_code='" + res_category + "'";
                        qry = "update  iti_seatmatrix set strength_fill=strength_fill || hstore(Array['" + res_category + "'],Array[((strength_fill->'" + res_category + "')::integer-1)::text]),strength_vacant=strength_vacant || hstore(Array['" + res_category + "'],Array[((strength_vacant->'" + res_category + "')::integer+1)::text]) where iti_code='" + iti_code + "' and trade_code='" + trade_code + "' and year='" + dsc_year + "'";
                        System.out.println("seatmatrix-----" + qry);
                        ps.setString(1, res_category);
                        ps.setString(2, res_category);
                        ps.setString(3, res_category);
                        ps.setString(4, res_category);
                        ps.setString(5, iti_code);
                        ps.setString(6, trade_code);
                        ps.setString(7, dsc_year);
                        if (k > 0) {
                            ps.executeUpdate(qry);
                        }
                        if (rs != null) {
                            rs.close();
                        }
                        if (ps != null) {
                            ps.close();
                        }
                        if (con != null) {
                            con.close();
                        }
                        request.setAttribute("adm_num", admnum);
                        return mapping.findForward("deletion_success");
                    }
                }
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        }
        return mapping.findForward(SUCCESS);

    }

}
