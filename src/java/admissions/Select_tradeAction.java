package admissions;

import DBC.DBConnection;
import DBC.RoleBasedOperations;
import DBC.generic;
import beans.MyUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class Select_tradeAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        System.out.println("Tradeaction");

        final HttpSession session = request.getSession(true);
        final String ins_code = beans.MyUtil.filterBad("" + session.getAttribute("ins_code"));

        String stored_token = beans.MyUtil.filterBad("" + session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));

        String id = stored_token.substring(10, 20) + request.getSession().getId() + stored_token.substring(2, 6);
        if (!token.equals(id)) {
            return mapping.findForward("failure");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnectionGen();

            if (ins_code.equals("") || ins_code.equals("null")) {
                return mapping.findForward("Problem_session");
            }
            final String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));
            String field = "";
            if (role_id.equals("3")) {
                field = "dist_code";
            } else if (role_id.equals("4")) {
                field = "iti_code";
            }
            String year = "";
            String phase = "";
            String seatmatrix_phase = "";
            String academic_year = "";
            try {

                year = MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
                phase = MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", connection);
                //phase="2";
                seatmatrix_phase = MyUtil.getOptionValue("admissions.admission_phase", "year", "seatmatrix_phase", year, "current", "true", connection);
                //seatmatrix_phase="2";
                academic_year = year.substring(2, 4);
//academic_year="20";
            } catch (Exception ex) {
            }

            final Select_tradeForm fm = (Select_tradeForm) form;

            final String select_trade = beans.MyUtil.filterBad(fm.getSelect_trade());
            final String regid = beans.MyUtil.filterBad(fm.getRegid());
            final String rank = beans.MyUtil.filterBad(fm.getRank());

            final StringTokenizer str = new StringTokenizer(select_trade, "$");
            final String trade_code = str.nextToken();
            final String category_code = str.nextToken();
            final String iti_code = str.nextToken();
            System.out.println("hfkdshfsd" + iti_code);
            final String trade_short = MyUtil.getOptionValue("ititrade_master", "trade_code", "trade_short", trade_code, connection);
            year = MyUtil.getOptionValue("iti_params", "code", "value", "7", connection);
            int count = 0;
            Connection iticm = null;
            try {
                iticm = DBConnection.getConnectionGen();
                final String year_of_admission = RoleBasedOperations.Current_AcademicYear(request);
                final String sql = "select max(adm_num) from \n"
                        + "\n"
                        + "((select max(substring(adm_num,9,3)) as adm_num from admissions.iti_admissions where iti_code=? and trade_code=? and year_of_admission=? and length(adm_num)!=9 and regid is not null) UNION ALL (select max(substring(adm_num,9,3)) as adm_num from admarchive_year2020   where iti_code=? and trade_code=? and year_of_admission=? and length(adm_num)!=9 and regid is not null))combined";
                System.out.println(sql);
                preparedStatement = iticm.prepareStatement(sql);
                preparedStatement.setString(1, iti_code);
                preparedStatement.setInt(2, Integer.parseInt(trade_code));
                preparedStatement.setString(3, year_of_admission);
                preparedStatement.setString(4, iti_code);
                preparedStatement.setInt(5, Integer.parseInt(trade_code));
                preparedStatement.setString(6, year_of_admission);

                System.out.println("admissions max query" + preparedStatement);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                    System.out.println("count is ::" + count);

                }
            } catch (Exception e) {
                System.out.println("exception" + e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (iticm != null) {
                    iticm.close();
                }
            }
            String adm_num = "";
            System.out.println("adm_num is--->" + count);
            if (++count == 0) {
                System.out.println("adm_num in 0--->" + count);
                adm_num = iti_code + trade_short + academic_year + "001";
            } else if (count <= 9) {
                System.out.println("adm_num in 9--->" + count);
                adm_num = iti_code + trade_short + academic_year + "00" + count;
            } else if (count <= 99) {
                System.out.println("adm_num in 99--->" + count);
                adm_num = iti_code + trade_short + academic_year + "0" + count;
            } else if (count >= 100) {
                adm_num = iti_code + trade_short + academic_year + "" + count;
            }

            System.out.println("adm_num is--->" + adm_num);
            final int trno = generic.logTrace((String) request.getSession().getAttribute("userName"), "admissions.iti_admissions", "Insert", request);
            final String fromd_training = RoleBasedOperations.getDurationFromPeriod(adm_num);
            final String tod_training = RoleBasedOperations.getDurationToPeriod(adm_num, request);
            java.util.Date date = new java.util.Date();
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            final SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            final String display_date = simpleDateFormat.format(date);
            final String original_date = simpleDateFormat2.format(date);
            final String mode_adm = "Convenor of the Sellection comitte";
            int i = 0;
            String dupadm = "";
            Connection itic = null;
            try {
                itic = DBConnection.getConnectionGen();
                final String iccq = "select regid from admissions.iti_admissions where regid=?";
                preparedStatement = itic.prepareStatement(iccq);
                preparedStatement.setString(1, regid);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    dupadm = resultSet.getString("regid");
                }
            } catch (Exception ex2) {
            } finally {
                if (itic != null) {
                    itic.close();
                }
            }
            System.out.println("dupadm $$$$$$$" + dupadm + "regid=" + regid);
            System.out.println("fgfgfgf----->");
            if (dupadm.equalsIgnoreCase(regid)) {
                request.setAttribute("success", (Object) "<font color=red size=4>Already Admitted This Candidate in your ITI or in Another ITI</font>");
                return mapping.findForward("fail");
            }
            System.out.println("testing----->");
            System.out.println("fgfgfgf----->");
            final String statecode = beans.MyUtil.filterBad("" + session.getAttribute("statecode"));
            System.out.println("dget_tradecode----->" + statecode);
            final String dget_tradecode = MyUtil.getOptionValue("dget_trademaster", "trade_code", "dget_tradecode", trade_code, connection);
            final String noofsems = MyUtil.getOptionValue("dget_trademaster", "trade_code", "noofsem", trade_code, connection);
            final String year_adm = "20" + academic_year;
            System.out.println("dget_tradecode----->" + dget_tradecode);
            System.out.println("noofsems----->" + noofsems);
            try {
                if (ins_code.equals(null) || ins_code.equals("") || ins_code.equals("null")) {
                    return mapping.findForward("Problem_session");
                }
                connection.setAutoCommit(false);
                final String query = "insert into admissions.iti_admissions (regid,name,fname,mname,addr,phno,adarno,gender,caste,ssc_passed,inter_passed,inter_marks,local,pincode,dob,phc,exservice,iti_code ,trade_code,res_category,adm_num,year_of_admission,trno,dist_code,mode_adm,fromd_training,tod_training,idmarks1,idmarks2,ssc_regno,ssc_board,ssc_year,ssc_month,date_of_admission,type_admission,phase,sem_nonsem)  select regid,name,fname,mname,addr,phno,adarno,gender,caste,ssc_passed,inter_passed,inter_marks,local,pincode,dob,phc,exservice,?,?,?,?,?,?,?,?,?,?,'','',ssc_regno,ssc_board,ssc_year,ssc_month,?,?,?,'SEM' from application where regid=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, iti_code);
                preparedStatement.setString(2, trade_code);
                preparedStatement.setString(3, category_code);
                preparedStatement.setString(4, adm_num);
                preparedStatement.setString(5, year_adm);
                preparedStatement.setInt(6, trno);
                preparedStatement.setString(7, ins_code.substring(0, 2));
                preparedStatement.setString(8, mode_adm);
                preparedStatement.setString(9, MyUtil.ChDate(fromd_training));
                preparedStatement.setString(10, MyUtil.ChDate(tod_training));
                preparedStatement.setString(11, MyUtil.ChDate(original_date));
                preparedStatement.setString(12, fm.type_admission);
                preparedStatement.setString(13, phase);
                preparedStatement.setString(14, regid);
                System.out.println("inserteeeeeeeeeeeeeeeeeeeeee" + query);
                i = preparedStatement.executeUpdate();

                String sql = "update iti_seatmatrix set strength_fill=strength_fill || hstore(Array[?],Array[((strength_fill->?)::integer+1)::text]),strength_vacant=strength_vacant || hstore(Array[?],Array[((strength_vacant->?)::integer-1)::text]) where iti_code=? and trade_code::text=? and year=? and phase=?";
                final PreparedStatement preparedStatementUpdate = connection.prepareStatement(sql);
                preparedStatementUpdate.setString(1, category_code);
                preparedStatementUpdate.setString(2, category_code);
                preparedStatementUpdate.setString(3, category_code);
                preparedStatementUpdate.setString(4, category_code);
                preparedStatementUpdate.setString(5, iti_code);
                preparedStatementUpdate.setString(6, trade_code);
                preparedStatementUpdate.setString(7, year);
                preparedStatementUpdate.setString(8, seatmatrix_phase);

                preparedStatementUpdate.executeUpdate();
                connection.commit();
                preparedStatementUpdate.close();
            } catch (Exception e2) {
                connection.rollback();
                System.out.println("ex" + e2);
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
                 
                e2.getStackTrace();
            } finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
            request.setAttribute("adm_num", (Object) adm_num);
        } catch (Exception e3) {
            System.out.println("Exception iss " + e3);
            e3.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return mapping.findForward("success");
    }
}
