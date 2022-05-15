/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hallticketlist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DistHallTicketAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Connection con = DBC.DBConnection.getConnection(request);
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        Statement stmt = null;
        Statement stmt1 = null;
        Statement stmt3 = null;
        HttpSession session = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        ListHallTicketForm ldata = (ListHallTicketForm) form;
        String iti_code = ldata.getName_list();

        String trade_code = ldata.getTrade_name();
        con = DBC.DBConnection.getConnection(request);
        
        
            
            String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "1", con);
            String month = beans.MyUtil.getOptionValue("iti_params", "code", "value", "2", con);
            String hallticket_allocation = beans.MyUtil.getOptionValue("iticentres", "iti_code", "ttc_code", iti_code ," month='"+month+"' and year", ""+year+"", con);
          if((hallticket_allocation.equalsIgnoreCase("null") || hallticket_allocation==null || hallticket_allocation.length()<=0)){
             request.setAttribute("success", "<b><font color=red><center>Center Not yet Allocated for  ITI "+iti_code+". Contact Department for Center Allocation</form></b>");
             return mapping.findForward("centersnotallocated");
          }
        
        
        
        try {
            //con = DBC.DBConnection.getConnection(request);
            String emptable = DBC.RoleBasedOperations.getTableNamewithmonth("", "exam_apply",request);
            stmt = con.createStatement();
            stmt1 = con.createStatement();
            String board_code=request.getParameter("type_admission");
            String sql = "select board_code from admissions.iti_board where status='y'";
            /*rs = stmt.executeQuery(sql);
            String board_code = "N";
            while (rs.next()) {
                board_code = rs.getString("board_code");
            }
            String board_code = ldata.getType_admission();
            request.setAttribute("board_code", beans.MyUtil.getOptionValue("admissions.iti_board", "board_code", "board_name", board_code, con)); */

            if (board_code.equals("")) { 
                if (rs2 != null) {
                    rs2.close();
                }
                if (rs1 != null) {
                    rs1.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (stmt1 != null) {
                    stmt1.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }

                return null;
            } else {

                //sql = "select a.adm_num,a.name,a.idmarks1,a.idmarks2,(select iti_name from iti where iti_code=a.iti_code),(select trade_name from ititrade_master where trade_code=a.trade_code),(select mode_exam from exams." + emptable + " where adm_num=a.adm_num),(select ttc_code from iticentres where iti_code=a.iti_code and type_admission='" + board_code + "' and year='2014') as ttc_code,(select iti_name from iti where iti_code  = (select ttc_code from iticentres where iti_code=a.iti_code and type_admission='" + board_code + "' and year='2014')) as ttc_name,a.fname from admissions.iti_admissions a where a.regid is null and a.iti_code='" + iti_code + "'  and a.trade_code='" + trade_code + "' and a.type_admission='" + board_code + "' and (select rdd_approv from exams." + emptable + " where adm_num=a.adm_num)='1' ";
                if(board_code.trim().equalsIgnoreCase("A")){
                sql="select *,case when length(t.ttc_code)<4 then (select ttc_name from ttc_mast where ttc_code::character varying=t.ttc_code) else (select iti_name from iti  where iti_code=t.ttc_code) end as ttc_name from (select a.newadmnum ,a.type_admission,a.name,a.fname,a.gender,a.iti_code,iti_name,a.trade_code,trade_name,idmarks1,idmarks2,mode_exam,(select ttc_code from iticentres where iti_code=a.iti_code and type_admission='"+board_code+"' and sem_nonsem='NONSEM' AND YEAR='"+year+"' and month='"+month+"' LIMIT 1) from admissions.iti_admissions a inner join exams.exam_apply_"+year+"_"+month+" e on a.newadmnum=e.adm_num inner join (select iti_code,iti_name from iti)t1 on t1.iti_code=a.iti_code inner join (select trade_code,trade_name from ititrade_master)t2 on t2.trade_code=a.trade_code where a.type_admission='"+board_code+"' and a.sem_nonsem='NONSEM' AND RDD_APPROV='1' and a.iti_code='"+iti_code+"' and a.trade_code='"+trade_code+"' ORDER BY a.newadmnum,ITI_CODE)t ORDER BY newadmnum,ITI_CODE";
                }
                else{
                sql="select *,case when length(t.ttc_code)<4 then (select ttc_name from ttc_mast where ttc_code::character varying=t.ttc_code) else (select iti_name from iti  where iti_code=t.ttc_code) end as ttc_name from (select a.adm_num ,a.name,a.fname,a.gender,a.iti_code,iti_name,a.trade_code,trade_name,idmarks1,idmarks2,mode_exam,(select ttc_code from iticentres where iti_code=a.iti_code and type_admission='"+board_code+"' and sem_nonsem='NONSEM' AND YEAR='"+year+"' and month='"+month+"' LIMIT 1) from admissions.iti_admissions a inner join exams.exam_apply_"+year+"_"+month+" e on a.adm_num=e.adm_num inner join (select iti_code,iti_name from iti)t1 on t1.iti_code=a.iti_code inner join (select trade_code,trade_name from ititrade_master)t2 on t2.trade_code=a.trade_code where a.type_admission='"+board_code+"' and a.sem_nonsem='NONSEM' AND RDD_APPROV='1' and conv_approv='1' and a.iti_code='"+iti_code+"' and a.trade_code='"+trade_code+"' ORDER BY a.ADM_NUM,ITI_CODE)t ORDER BY ADM_NUM,ITI_CODE";
                }
                //System.out.println("Query is :: "+sql);
                rs = stmt.executeQuery(sql);
                ArrayList alist = new ArrayList();
                ArrayList sublist = new ArrayList();
                ListHallTicketForm esub = null;
                while (rs.next()) {
                    ListHallTicketForm linfo = new ListHallTicketForm();
                    String adm = rs.getString(1).trim();
                    if (adm.length() == 13) {
                        linfo.setTrade_name(rs.getString("trade_name") + " ( " + beans.MyUtil.getOptionValue("iti_subjects_master", "subject_code", "subject_name", adm.substring(11, 13), con) + ")");
                    } else {
                        linfo.setTrade_name(rs.getString("trade_name"));
                    }
                     if(board_code.trim().equalsIgnoreCase("A")){
                    linfo.setAdm_num(rs.getString("newadmnum").trim());
                     }
                     else{
                          linfo.setAdm_num(rs.getString("adm_num").trim());
                     }
                    linfo.setName(rs.getString("name"));
                    linfo.setIdmarks1(rs.getString("idmarks1"));
                    linfo.setIdmarks2(rs.getString("idmarks2"));
                    linfo.setIti_name(rs.getString("iti_name"));
                    linfo.setMode_exam(rs.getString("mode_exam"));
                    linfo.setTtc_code(iti_code);
                    
                    linfo.setTtc_name(rs.getString("ttc_name"));
                    String self_center = beans.MyUtil.getOptionValue("admissions.iti_board", "board_code", "self_center", board_code, con);
                String center_name = "";
                     //String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "1", con);
            //String month = beans.MyUtil.getOptionValue("iti_params", "code", "value", "2", con);
                if (self_center.equalsIgnoreCase("n")) {
                   // if (role_id.equalsIgnoreCase("3") || role_id.equalsIgnoreCase("4") || role_id.equalsIgnoreCase("5")) {
                       String exam_center = beans.MyUtil.getOptionValue("iticentres", "iti_code", "ttc_code", iti_code, "sem_nonsem='NONSEM' and year='" + year + "' and month='" + month + "' and type_admission", board_code, con);
                       String iti_noniti = beans.MyUtil.getOptionValue("iticentres", "iti_code", "iti_noniti", iti_code, "sem_nonsem='NONSEM' and year='" + year + "' and month='" + month + "' and type_admission", board_code, con);
                       
                        if (iti_noniti.equalsIgnoreCase("ITI")) {
                            center_name = beans.MyUtil.getOptionValue("iti", "iti_code", "iti_name", exam_center, con);
                        } else {
                            center_name = beans.MyUtil.getOptionValue("ttc_mast", "ttc_code", "ttc_name", exam_center, con);
                        }
                   // }
                } else {
                    center_name = rs.getString("iti_name");
                }
                   linfo.setTtc_name(center_name);
                    linfo.setFname(rs.getString("fname"));
                    
                    if (false) {
                        String subquery = "select a.subject_code,(select subject_name from iti_subjects_master where subject_code=a.subject_code)from iti_trade_subjects a where a.trade_code='" + trade_code + "'";
                        rs1 = stmt1.executeQuery(subquery);
                        while (rs1.next()) {
                           esub = new ListHallTicketForm();
                            esub.setSubject_code(rs1.getString(1));
                            esub.setSubject_name(rs1.getString(2));
                          esub.setAdm_num(rs.getString(1));
                            sublist.add(esub);
                        }

                    } 
//                    else {
//                        String tab = DBC.RoleBasedOperations.getTableNamewithmonth("", "exam_apply",request);
//                        String subfquery ="";// "select skeys(result) from  admissions.iti_admissions where adm_num ='" + rs.getString(1) + "'";
//                        subfquery = "select * from (select skeys(result),svals(result) from admissions.iti_admissions   where adm_num ='"+rs.getString(1)+"')t where (svals<>'true' or svals is null)";
//                        System.out.println(subfquery);
//                        rs2 = stmt1.executeQuery(subfquery);
//                        while (rs2.next()) {
//                            esub = new ListHallTicketForm();
//                            esub.setSubject_name(beans.MyUtil.getOptionValue("iti_subjects_master", "subject_code", "subject_name", rs2.getString(1),con));
//                            esub.setAdm_num(rs.getString(1));
//                            esub.setSubject_code(rs2.getString(1));
//                            sublist.add(esub);
//
//                        }
//                    }
  alist.add(linfo);
                   // sublist.add(esub);
                }

                request.setAttribute("alist", alist);
                    //request.setAttribute("sublist", sublist);
                year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "1", con);
         month = beans.MyUtil.getOptionValue("iti_params", "code", "value", "2", con);
        int trno = DBC.generic.logTrace("" + session.getAttribute("userName"), "exams.ht_downloaded_info", "insert", request);
        sql = "INSERT INTO exams.ht_downloaded_info(year, month, trno, iti_code, trade_code)VALUES ('"+year+"', '"+month+"', '"+trno+"', '"+iti_code+"', '"+trade_code+"')";
        stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs2 != null) {
                rs2.close();
            }
            if (rs1 != null) {
                rs1.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (stmt1 != null) {
                stmt1.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
            
        return mapping.findForward(SUCCESS);
    }

}
