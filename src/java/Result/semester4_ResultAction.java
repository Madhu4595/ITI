
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
/**
 *
 * @author shobha
 */
package Result;

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

public class semester4_ResultAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";
    private Object session;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession ses = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        semester4_ResultForm s4rf = (semester4_ResultForm) form;
        String int_marks = s4rf.getInt_marks();
        String tot_marks = s4rf.getTot_marks();
        String result = s4rf.getResult();
        String final_result = s4rf.getFinal_result();
        String ext_marks = s4rf.getExt_marks();
        String htno = s4rf.getHtno();
        String paper_result = s4rf.getPaper_result();
        String paper_tot_marks = s4rf.getPaper_tot_marks();
        String sem_paper_result = s4rf.getSem_paper_result();
        String sem_paper_finalresult = s4rf.getSem_paper_finalresult();
        String sem_finalresult = s4rf.getSem_finalresult();
        String year = s4rf.getYear();
        String month = s4rf.getMonth();
        String engg = s4rf.getEngg();
        char sem = s4rf.getSem();
        String sem_final = null;
        String S4Aresult = null;
        Integer S4D = null;
        Integer S4B = null;
        Integer S4C = null;
        Integer trade_code = null;
        String S3Aresult = null;
        Integer S3D = null;
        Integer S3B = null;
        Integer S3C = null;
        String S2Aresult = null;
        Integer S2E = null;
        Integer S2B = null;
        Integer S2C = null;
        Integer S2D = null;
        String S1Aresult = null;
        Integer S1E = null;
        Integer S1B = null;
        Integer S1C = null;
        Integer S1D = null;
        Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        PreparedStatement ps = null;
        ResultSet rs = null;
        Statement st = null;

        try {

            String query;
            int tr = 0;

            switch (sem) {
                case '4': {
                    String query2 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S4A'],array[(case when (ext_marks->'S4A')::integer>=162 then 'true' else 'false' end) ]) where exist(result,'S4A') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss222.............." + query2);
                    st = con.createStatement();
                    st.executeUpdate(query2);

                    String query3 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S4paper-4'],array[(case when (result->'S4A')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S4paper-4') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";

                    System.out.println("query iss333.............." + query3);
                    st = con.createStatement();

                    st.executeUpdate(query3);
                    tr = 1;
                    String query4 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S4B'],array[(case when (ext_marks->'S4B')::integer>=60 then 'true' else 'false' end) ]) where exist(result,'S4B') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss222.............." + query4);
                    st = con.createStatement();
                    st.executeUpdate(query4);

                    String query5 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S4paper-1'],array[(case when (result->'S4B')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S4paper-1') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss333.............." + query5);
                    st = con.createStatement();
                    st.executeUpdate(query5);
                    tr = 1;

                    String query6 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S4C'],array[(case when (ext_marks->'S4C')::integer>=30 then 'true' else 'false' end) ]) where exist(result,'S4C') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                                      System.out.println("query iss222.............." + query6);
                    st = con.createStatement();
                    st.executeUpdate(query6);

                    String query7 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S4paper-2'],array[(case when (result->'S4C')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S4paper-2') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                                       System.out.println("query iss333.............." + query7);
                    st = con.createStatement();

                    st.executeUpdate(query7);
                    tr = 1;
                    String query8 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S4D'],array[(case when (ext_marks->'S4D')::integer>=30 then 'true' else 'false' end) ]) where exist(result,'S4D') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                                       System.out.println("query iss222.............." + query8);
                    st = con.createStatement();
                    st.executeUpdate(query8);
                    String query9 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S4paper-3'],array[(case when (result->'S4D')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S4paper-3') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                                        System.out.println("query iss333.............." + query9);
                    st = con.createStatement();
                    st.executeUpdate(query9);
                    tr = 1;
                    //String ss = "select trade_code from admissions.sample where adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                  //                     st = con.createStatement();
                  //  rs = st.executeQuery(ss);
                  //                      while (rs.next()) {
                   //     trade_code = rs.getInt("trade_code");
                   // }
                    if (tr == 1) {
                    //    if (trade_code != 11 || trade_code != 12) {
                            //trade_code not in (11,12) 
                          //  String qry = "select sem_finalresult,case when sem_paper_result->'S4paper-1'='true' and sem_paper_result->'S4paper-2'='true' "
                           //         + "and sem_paper_result->'S4paper-3'='true' and sem_paper_result->'S4paper-4'='true'  then 'true' else 'false' end,sem_paper_result->'S4paper-1',"
                            //        + "sem_paper_result->'S4paper-2',sem_paper_result->'S4paper-3',sem_paper_result->'S4paper-4',sem_paper_result,a.result,a.htno from admissions.sample a  "
                            //        + "where exist(sem_paper_result,'S4paper-1') and engg_nonengg='E' and trade_code not in ('11','12') and sem_nonsem='SEM' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                          
                          //  System.out.println("query is.............." + qry);
                          //  st = con.createStatement();
                           // rs = st.executeQuery(qry);
                            String qry1 = "update admissions.sample a set sem_finalresult =( case when sem_finalresult is null then '' else sem_finalresult end )||hstore(array['S4'],array[case when sem_paper_result->'S4paper-1'='true' and sem_paper_result->'S4paper-2'='true' and sem_paper_result->'S4paper-3'='true' and sem_paper_result->'S4paper-4'='true'  then 'true' else 'false' end]) where exist(sem_paper_result,'S4paper-1') and exist(sem_paper_result,'S4paper-2') and exist(sem_paper_result,'S4paper-3') and exist(sem_paper_result,'S4paper-4') and engg_nonengg='E' and trade_code not in ('11','12') and sem_nonsem='SEM' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                            st = con.createStatement();
                            st.executeUpdate(qry1);
                            System.out.println("dddddddddddddd" + tr);
                       // } else {

                            //trade_code in (11,12)  
                          //  String qry11 = "select sem_finalresult,case when sem_paper_result->'S4paper-1'='true' and sem_paper_result->'S4paper-2'='true' "
                           //         + " and sem_paper_result->'S4paper-4'='true'  then 'true' else 'false' end,sem_paper_result->'S4paper-1',"
                           //         + "sem_paper_result->'S4paper-2',sem_paper_result->'S4paper-4',sem_paper_result,a.result,a.htno from admissions.sample a  "
                           //         + "where exist(sem_paper_result,'S4paper-1') and engg_nonengg='E' and trade_code  in ('11','12') and sem_nonsem='SEM' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        
                         //   System.out.println("query is.............." + qry11);
                         //   st = con.createStatement();
                         //   rs = st.executeQuery(qry11);
                            String qry12 = "update admissions.sample a set sem_finalresult =( case when sem_finalresult is null then '' else sem_finalresult end )||hstore(array['S4'],array[case when sem_paper_result->'S4paper-1'='true' and sem_paper_result->'S4paper-2'='true'  and sem_paper_result->'S4paper-4'='true'  then 'true' else 'false' end]) where exist(sem_paper_result,'S4paper-1') and exist(sem_paper_result,'S4paper-2') and exist(sem_paper_result,'S4paper-4') and engg_nonengg='E' and trade_code in ('11','12') and sem_nonsem='SEM' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                            st = con.createStatement();
                            st.executeUpdate(qry12);
                            System.out.println("dddddddddddddd" + tr);
                            tr = 0;
                        }
                    }

                    System.out.println("sneha.................");

                 //  RequestDispatcher rd = request.getRequestDispatcher("/semester4_Result.jsp");
                   // rd.forward(request, response);
                     request.setAttribute("success", "<h1 align=center><font color='red'>Successfully Updated</font><h1>");
                
                break;
                case '3': {

                    String query2 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S3A'],array[(case when (ext_marks->'S3A')::integer>=162 then 'true' else 'false' end) ]) where exist(result,'S3A') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss222.............." + query2);
                    st = con.createStatement();
                    st.executeUpdate(query2);

                    String query3 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S3paper-4'],array[(case when (result->'S3A')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S3paper-4') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss333.............." + query3);
                    st = con.createStatement();

                    st.executeUpdate(query3);
                    tr = 1;

                    String query4 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S3B'],array[(case when (ext_marks->'S3B')::integer>=60 then 'true' else 'false' end) ]) where exist(result,'S3B') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss222.............." + query4);
                    st = con.createStatement();
                    st.executeUpdate(query4);

                    String query5 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S3paper-1'],array[(case when (result->'S3B')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S3paper-1') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss333.............." + query5);
                    st = con.createStatement();

                    st.executeUpdate(query5);
                    tr = 1;

                    String query6 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S3C'],array[(case when (ext_marks->'S3C')::integer>=30 then 'true' else 'false' end) ]) where exist(result,'S3C') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss222.............." + query6);
                    st = con.createStatement();
                    st.executeUpdate(query6);

                    String query7 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S3paper-2'],array[(case when (result->'S3C')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S3paper-2') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss333.............." + query7);
                    st = con.createStatement();

                    st.executeUpdate(query7);
                    tr = 1;

                    String query8 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S3D'],array[(case when (ext_marks->'S3D')::integer>=30 then 'true' else 'false' end) ]) where exist(result,'S3D') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss222.............." + query8);
                    st = con.createStatement();
                    st.executeUpdate(query8);

                    String query9 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S3paper-3'],array[(case when (result->'S3D')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S3paper-3') and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss333.............." + query9);
                    st = con.createStatement();

                    st.executeUpdate(query9);
                    tr = 1;
                  //  String ss = "select trade_code from admissions.sample where adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                  //  st = con.createStatement();
                   // rs = st.executeQuery(ss);
                  //  while (rs.next()) {
                  //      trade_code = rs.getInt("trade_code");
                   // }
                    if (tr == 1) {
                                              //trade_code not in (11,12) 

                        String qry1 = "update admissions.sample a set sem_finalresult =( case when sem_finalresult is null then '' else sem_finalresult end )||hstore(array['S3'],array[case when sem_paper_result->'S3paper-1'='true' and sem_paper_result->'S3paper-2'='true' and sem_paper_result->'S3paper-3'='true' and sem_paper_result->'S3paper-4'='true'  then 'true' else 'false' end]) where exist(sem_paper_result,'S3paper-1') and exist(sem_paper_result,'S3paper-2') and exist(sem_paper_result,'S3paper-3') and exist(sem_paper_result,'S3paper-4') and engg_nonengg='E' and trade_code not in ('11','12') and sem_nonsem='SEM' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        st = con.createStatement();
                        st.executeUpdate(qry1);
                        System.out.println("dddddddddddddd" + tr);
                      
                        //trade_code in (11,12)  
                        String qry12 = "update admissions.sample a set sem_finalresult =( case when sem_finalresult is null then '' else sem_finalresult end )||hstore(array['S3'],array[case when sem_paper_result->'S3paper-1'='true' and sem_paper_result->'S3paper-2'='true'  and sem_paper_result->'S3paper-4'='true'  then 'true' else 'false' end]) where exist(sem_paper_result,'S3paper-1') and exist(sem_paper_result,'S3paper-2') and exist(sem_paper_result,'S3paper-4') and engg_nonengg='E' and trade_code in ('11','12') and sem_nonsem='SEM' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        st = con.createStatement();
                        st.executeUpdate(qry12);
                        System.out.println("dddddddddddddd" + tr);
                                            }
                }
                break;
//sem2 Non engg 
                case '2': {
                    if (engg.equals("NE")) {
                       // query = " select htno,ext_marks->'S2A' as A1,(case when (ext_marks->'S2A')::integer>=162 then 'true' else 'false' end) as B1 ,hstore(array['S2A'],array[(case when (ext_marks->'S2A')::integer>=162 then 'true' else 'false' end) ]) as C1, ext_marks->'S2B' as A2,(case when (ext_marks->'S2B')::integer>=60 then 'true' else 'false' end) as B2 ,hstore(array['S2B'],array[(case when (ext_marks->'S2B')::integer>=60 then 'true' else 'false' end) ]) as C2, ext_marks->'S2D' as A3,(case when (ext_marks->'S2D')::integer>=30 then 'true' else 'false' end) as B3 ,hstore(array['S2D'],array[(case when (ext_marks->'S2D')::integer>=30 then 'true' else 'false' end) ]) as C3, ext_marks->'S2C' as A4,(case when (ext_marks->'S2C')::integer>=18.75 then 'true' else 'false' end) as B4 ,hstore(array['S2C'],array[(case when (ext_marks->'S2C')::integer>=18.75 then 'true' else 'false' end) ]) as C4, ext_marks->'S2E' as A5,(case when (ext_marks->'S2E')::integer>=12.05 then 'true' else 'false' end) as B5 ,hstore(array['S2E'],array[(case when (ext_marks->'S2E')::integer>=12.05 then 'true' else 'false' end) ]) as C5  from admissions.sample where exist(ext_marks,'S2A') and exist(ext_marks,'S2B') and exist(ext_marks,'S2C') and exist(ext_marks,'S2D') and exist(ext_marks,'S2E') and engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        //System.out.println("query iss.............." + query);
                        //st = con.createStatement();
                        //rs = st.executeQuery(query);
                          //                      while (rs.next()) {
                            //htno = rs.getString("htno");
                            //String A1 = rs.getString("A1");
                            //String B1 = rs.getString("B1");
                            //String A2 = rs.getString("A2");
                            //String B2 = rs.getString("B2");
                            //String A3 = rs.getString("A3");
                            //String B3 = rs.getString("B3");
                            //String A4 = rs.getString("A4");
                            //String B4 = rs.getString("B4");

                                          //        }
                        String query2 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S2A'],array[(case when (ext_marks->'S2A')::integer>=60 then 'true' else 'false' end) ]) where exist(result,'S2A') and engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss222.............." + query2);
                        st = con.createStatement();
                        st.executeUpdate(query2);

                        String query3 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S2paper-3'],array[(case when (result->'S2A')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S2paper-3') and engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss333.............." + query3);
                        st = con.createStatement();
                        st.executeUpdate(query3);
                        tr = 1;
                        String query4 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S2B'],array[(case when (ext_marks->'S2B')::integer>=12 then 'true' else 'false' end) ]) where exist(result,'S2B') and engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss222.............." + query4);
                        st = con.createStatement();
                        st.executeUpdate(query4);

                        String query5 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S2paper-1'],array[(case when (result->'S2B')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S2paper-1') and engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss333.............." + query5);
                        st = con.createStatement();

                        st.executeUpdate(query5);
                        tr = 1;
                        String query8 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S2E'],array[(case when (ext_marks->'S2E')::integer>=20 then 'true' else 'false' end) ]) where exist(result,'S2E') and engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss222.............." + query8);
                        st = con.createStatement();
                        st.executeUpdate(query8);

                        String query10 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S2paper-2'],array[(case when (result->'S2E')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S2paper-2') and engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss333.............." + query10);
                        st = con.createStatement();

                        st.executeUpdate(query10);
                        tr = 1;

                       
                       // String qr3 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S2C'],array[(case when (ext_marks->'S2C')::integer>=18.75 then 'true' else 'false' end) ]) where exist(result,'S2C') and engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                       // System.out.println("query iss333.............." + qr3);
                      //  st = con.createStatement();
                      //  st.executeUpdate(qr3);
                      //  String qr4 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S2E'],array[(case when (ext_marks->'S2E')::integer>=12.05 then 'true' else 'false' end) ]) where exist(result,'S2E') and engg_nonengg='E' and  adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                      //  System.out.println("query iss333.............." + qr4);
                      //  st = con.createStatement();
                     //   st.executeUpdate(qr4);
                        int tt1 = 0;
                     //   String quee = "select ext_marks->'S2C' as a,ext_marks->'S2E' as b from admissions.sample where engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                       // st = con.createStatement();
                       // rs = st.executeQuery(quee);
                      //  while (rs.next()) {

                       //     Integer a = rs.getInt("a");
                      ///      Integer b = rs.getInt("b");
                      //      System.out.println("" + (a + b));

                      //      int c = (a + b);
                        //    System.out.println("the=============" + c);
                        //    if (c >= 50) {
                        //        String query9 = "update admissions.sample set sem_paper_result=sem_paper_result||hstore('S2paper-2','" + true + "') where engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                      //          System.out.println("query iss33*****true*******3.............." + query9);
                       //         st = con.createStatement();
                       //         st.executeUpdate(query9);

                            //    System.out.println("rani..............");
                          //      tt1 = 1;
                          //  } else {
                          //      String query9 = "update admissions.sample set sem_paper_result=sem_paper_result||hstore('S2paper-2','" + false + "') where engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                         //       System.out.println("query iss33****false********3.............." + query9);
                          //      st = con.createStatement();
                          //      st.executeUpdate(query9);

                         //       System.out.println("rani..............");
                         //       tt1 = 1;
                           // }
                       // }
                        tr = 1;

                        if (tr == 1) {
                         //   String qryy = "select sem_finalresult, case when sem_paper_result->'S2paper-1'='true' "
                         //           + "and sem_paper_result->'S2paper-2'='true' and sem_paper_result->'S2paper-3'='true' "
                          //          + "and sem_paper_result->'S2paper-4'='true'  "
                           //         + "then 'true' else 'false' end,sem_paper_result->'S2paper-1',sem_paper_result->'S2paper-2',"
                           //         + "sem_paper_result->'S2paper-3',sem_paper_result->'S2paper-4',sem_paper_result,a.result,a.htno"
                           //         + " from admissions.sample a where exist(sem_paper_result,'S2paper-1') and engg_nonengg='E' and trade_code not in ('11','12') and sem_nonsem='SEM' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";

                         //   System.out.println("qry issssss.............." + qryy);
                        //    st = con.createStatement();
                        //    rs = st.executeQuery(qryy);
                            String qry1 = "update admissions.sample a set sem_finalresult = ( case when sem_finalresult is null then '' else sem_finalresult end )||hstore(array['S2'],"
                                    + "array[case when sem_paper_result->'S2paper-1'='true' and sem_paper_result->'S2paper-2'='true' "
                                    + "and sem_paper_result->'S2paper-3'='true'   then 'true' else 'false' end]) "
                                    + "where exist(sem_paper_result,'S2paper-1') and exist(sem_paper_result,'S2paper-2') and exist(sem_paper_result,'S2paper-3') and engg_nonengg='N' "
                                    + " and  adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                            st = con.createStatement();
                            st.executeUpdate(qry1);
                        }
                    }
                    if (engg.equals("E")) {
                       // query = " select htno,ext_marks->'S2A' as A1,(case when (ext_marks->'S2A')::integer>=162 then 'true' else 'false' end) as B1 ,hstore(array['S2A'],array[(case when (ext_marks->'S2A')::integer>=162 then 'true' else 'false' end) ]) as C1, ext_marks->'S2B' as A2,(case when (ext_marks->'S2B')::integer>=60 then 'true' else 'false' end) as B2 ,hstore(array['S2B'],array[(case when (ext_marks->'S2B')::integer>=60 then 'true' else 'false' end) ]) as C2, ext_marks->'S2D' as A3,(case when (ext_marks->'S2D')::integer>=30 then 'true' else 'false' end) as B3 ,hstore(array['S2D'],array[(case when (ext_marks->'S2D')::integer>=30 then 'true' else 'false' end) ]) as C3, ext_marks->'S2C' as A4,(case when (ext_marks->'S2C')::integer>=18.75 then 'true' else 'false' end) as B4 ,hstore(array['S2C'],array[(case when (ext_marks->'S2C')::integer>=18.75 then 'true' else 'false' end) ]) as C4, ext_marks->'S2E' as A5,(case when (ext_marks->'S2E')::integer>=12.05 then 'true' else 'false' end) as B5 ,hstore(array['S2E'],array[(case when (ext_marks->'S2E')::integer>=12.05 then 'true' else 'false' end) ]) as C5  from admissions.sample where exist(ext_marks,'S2A') and exist(ext_marks,'S2B') and exist(ext_marks,'S2C') and exist(ext_marks,'S2D') and exist(ext_marks,'S2E') and engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                      //  System.out.println("query iss.............." + query);
                       // st = con.createStatement();
                      //  rs = st.executeQuery(query);
                     //                        while (rs.next()) {
                      //      htno = rs.getString("htno");
                      //      String A1 = rs.getString("A1");
                       //     String B1 = rs.getString("B1");
                       //     String A2 = rs.getString("A2");
                       //     String B2 = rs.getString("B2");
                       //     String A3 = rs.getString("A3");
                       //     String B3 = rs.getString("B3");
                        //    String A4 = rs.getString("A4");
                        //    String B4 = rs.getString("B4");
                          //                         }
                        String query2 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S2A'],array[(case when (ext_marks->'S2A')::integer>=162 then 'true' else 'false' end) ]) where exist(result,'S2A') and engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss222.............." + query2);
                        st = con.createStatement();
                        st.executeUpdate(query2);

                        String query3 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S2paper-4'],array[(case when (result->'S2A')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S2paper-4') and engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss333.............." + query3);
                        st = con.createStatement();

                        st.executeUpdate(query3);
                        tr = 1;
                        String query4 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S2B'],array[(case when (ext_marks->'S2B')::integer>=60 then 'true' else 'false' end) ]) where exist(result,'S2B') and engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss222.............." + query4);
                        st = con.createStatement();
                        st.executeUpdate(query4);

                        String query5 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S2paper-1'],array[(case when (result->'S2B')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S2paper-1') and engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss333.............." + query5);
                        st = con.createStatement();

                        st.executeUpdate(query5);
                        tr = 1;
                        String query8 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S2D'],array[(case when (ext_marks->'S2D')::integer>=30 then 'true' else 'false' end) ]) where exist(result,'S2D') and engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss222.............." + query8);
                        st = con.createStatement();
                        st.executeUpdate(query8);

                        String query10 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S2paper-3'],array[(case when (result->'S2D')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S2paper-3') and engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss333.............." + query10);
                        st = con.createStatement();

                        st.executeUpdate(query10);
                        tr = 1;
                        String qr3 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S2C'],array[(case when (ext_marks->'S2C')::integer>=18.75 then 'true' else 'false' end) ]) where exist(result,'S2C') and engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss333.............." + qr3);
                        st = con.createStatement();
                        st.executeUpdate(qr3);
                        String qr4 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S2E'],array[(case when (ext_marks->'S2E')::integer>=12.05 then 'true' else 'false' end) ]) where exist(result,'S2E') and engg_nonengg='E' and  adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        System.out.println("query iss333.............." + qr4);
                        st = con.createStatement();
                        st.executeUpdate(qr4);

                        int tt1 = 0;
                        String quee = "select ext_marks->'S2C' as a,ext_marks->'S2E' as b from admissions.sample where  exist(result,'S2C') and exist(result,'S2E') and engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                        st = con.createStatement();
                        rs = st.executeQuery(quee);
                        while (rs.next()) {

                            Integer a = rs.getInt("a");
                            Integer b = rs.getInt("b");
                            System.out.println("" + (a + b));

                            int c = (a + b);
                            System.out.println("the=============" + c);
                            if (c >= 50) {
                                String query9 = "update admissions.sample set sem_paper_result=sem_paper_result||hstore('S2paper-2','" + true + "') where engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                                System.out.println("query iss33*****true*******3.............." + query9);
                                st = con.createStatement();
                                st.executeUpdate(query9);

                                System.out.println("rani..............");
                                tt1 = 1;
                            } else {
                                String query9 = "update admissions.sample set sem_paper_result=sem_paper_result||hstore('S2paper-2','" + false + "') where engg_nonengg='E' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                                System.out.println("query iss33****false********3.............." + query9);
                                st = con.createStatement();
                                st.executeUpdate(query9);

                                System.out.println("rani..............");
                                tt1 = 1;
                            }
                        }
                        tr = 1;
                        if (tr == 1) {
                            String qryy = "select sem_finalresult, case when sem_paper_result->'S2paper-1'='true' "
                                    + "and sem_paper_result->'S2paper-2'='true' and sem_paper_result->'S2paper-3'='true' "
                                    + "and sem_paper_result->'S2paper-4'='true'  "
                                    + "then 'true' else 'false' end,sem_paper_result->'S2paper-1',sem_paper_result->'S2paper-2',"
                                    + "sem_paper_result->'S2paper-3',sem_paper_result->'S2paper-4',sem_paper_result,a.result,a.htno"
                                    + " from admissions.sample a where exist(sem_paper_result,'S2paper-1') and engg_nonengg='E' and trade_code not in ('11','12') and sem_nonsem='SEM' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";

                            System.out.println("qry issssss.............." + qryy);
                            st = con.createStatement();
                            rs = st.executeQuery(qryy);
                            String qry1 = "update admissions.sample a set sem_finalresult = ( case when sem_finalresult is null then '' else sem_finalresult end )||hstore(array['S2'],"
                                    + "array[case when sem_paper_result->'S2paper-1'='true' and sem_paper_result->'S2paper-2'='true' "
                                    + "and sem_paper_result->'S2paper-3'='true' and sem_paper_result->'S2paper-4'='true'  then 'true' else 'false' end]) "
                                    + "where exist(sem_paper_result,'S2paper-1') and engg_nonengg='E' and trade_code not in ('11','12')"
                                    + " and  adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                            st = con.createStatement();
                            st.executeUpdate(qry1);
                        }
                    }
                }
                break;
//sem 1 Non engg
                case '1': {

                    query = "select htno,ext_marks->'S1A' as A1,(case when (ext_marks->'S1A')::integer>=60 then 'true' else 'false' end) as B1 ,hstore(array['S1A'],array[(case when (ext_marks->'S1A')::integer>=60 then 'true' else 'false' end) ]) as C1,ext_marks->'S1B' as A2,(case when (ext_marks->'S1B')::float>=7.5 then 'true' else 'false' end) as B2 ,hstore(array['S1B'],array[(case when (ext_marks->'S1B')::float>=7.5 then 'true' else 'false' end) ]) as C2,ext_marks->'S1E' as A3,(case when (ext_marks->'S1E')::float>=12.5 then 'true' else 'false' end) as B3 ,hstore(array['S1E'],array[(case when (ext_marks->'S1E')::float>=12.5 then 'true' else 'false' end) ]) as C3 from admissions.sample where exist(ext_marks,'S1A') and exist(ext_marks,'S1B') and exist(ext_marks,'S1E') and engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss.............." + query);
                    st = con.createStatement();
                    rs = st.executeQuery(query);
                                      while (rs.next()) {
                        htno = rs.getString("htno");
                        String A1 = rs.getString("A1");
                        String B1 = rs.getString("B1");
                        String A2 = rs.getString("A2");
                        String B2 = rs.getString("B2");
                        String A3 = rs.getString("A3");
                        String B3 = rs.getString("B3");
                                        }

                    String query2 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S1A'],array[(case when (ext_marks->'S1A')::integer>=60 then 'true' else 'false' end) ]) where exist(result,'S1A') and engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss222.............." + query2);
                    st = con.createStatement();
                    st.executeUpdate(query2);

                    String query3 = "update admissions.sample set sem_paper_result=(case when sem_paper_result is null then '' else sem_paper_result end)||hstore(array['S1paper-3'],array[(case when (result->'S1A')::boolean>='true' then 'true' else 'false' end) ]) where exist(sem_paper_result,'S1paper-3') and engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss333.............." + query3);
                    st = con.createStatement();

                    st.executeUpdate(query3);
                    tr = 1;
                    String qr3 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S1B'],array[(case when (ext_marks->'S1B')::float>=7.5 then 'true' else 'false' end) ]) where exist(result,'S1B') and engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss333.............." + qr3);
                    st = con.createStatement();
                    st.executeUpdate(qr3);
                    //------------------------S1E--------------------------//

                    query = "select htno,ext_marks->'S1E' as A,(case when (ext_marks->'S1E')::float>=12.5 then 'true' else 'false' end) as B ,hstore(array['S1E'],array[(case when (ext_marks->'S1E')::float>=12.5 then 'true' else 'false' end) ]) as C from admissions.sample where exist(ext_marks,'S1E') and engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss.............." + query);
                    st = con.createStatement();
                    rs = st.executeQuery(query);
                    // ps.execute();
                    while (rs.next()) {
                        htno = rs.getString("htno");
                        String ext_marks_temp = rs.getString("A");
                        String ext_marks_temp1 = rs.getString("B");
                        //int ext_marks_temp2=rs.getInt("C");
                    }
                    String qr4 = "update admissions.sample set result = (case when result is null then '' else result end)||hstore(array['S1E'],array[(case when (ext_marks->'S1E')::float>=12.5 then 'true' else 'false' end) ]) where exist(result,'S1E') and engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    System.out.println("query iss333.............." + qr4);
                    st = con.createStatement();
                    st.executeUpdate(qr4);

                    int tt1 = 0;
                    String quee = "select ext_marks->'S1B' as a,ext_marks->'S1E' as b from admissions.sample where  engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                    st = con.createStatement();
                    rs = st.executeQuery(quee);
                    while (rs.next()) {

                        Integer a = rs.getInt("a");
                        Integer b = rs.getInt("b");
                        System.out.println("" + (a + b));

                        int c = (a + b);
                        System.out.println("the=============" + c);
                        if (c >= 32) {
                            String query9 = "update admissions.sample set sem_paper_result=sem_paper_result||hstore('S1paper-1','" + true + "') where  engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                            System.out.println("query iss33*****true*******3.............." + query9);
                            st = con.createStatement();
                            st.executeUpdate(query9);

                            System.out.println("rani..............");
                            tt1 = 1;
                        } else {
                            String query9 = "update admissions.sample set sem_paper_result=sem_paper_result||hstore('S1paper-1','" + false + "') where engg_nonengg='N' and adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";
                            System.out.println("query iss33****false********3.............." + query9);
                            st = con.createStatement();
                            st.executeUpdate(query9);

                            System.out.println("rani..............");
                            tt1 = 1;

                        }
                    }
                    tr = 1;

                    if (tr == 1) {
                        String qry1 = "select sem_finalresult, case when sem_paper_result->'S1paper-1'='true' "
                                + " and sem_paper_result->'S1paper-3'='true' "
                                + "then 'true' else 'false' end,sem_paper_result->'S1paper-1',"
                                + "sem_paper_result->'S1paper-3',sem_paper_result,a.result,a.htno"
                                + " from admissions.sample a where exist(sem_paper_result,'S1paper-1') and engg_nonengg='N'  and sem_nonsem='SEM' and  adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";

                        System.out.println("qry issssss.............." + qry1);
                        st = con.createStatement();
                        rs = st.executeQuery(qry1);
                        String qry11 = "update admissions.sample a set sem_finalresult = ( case when sem_finalresult is null then '' else sem_finalresult end )||hstore(array['S1'],"
                                + "array[case when sem_paper_result->'S1paper-1'='true' "
                                + "and sem_paper_result->'S1paper-3'='true' then 'true' else 'false' end]) "
                                + "where exist(sem_paper_result,'S1paper-1') and engg_nonengg='N' "
                                + " and  adm_num in (select adm_num from exams.exam_apply_" + year + "_" + month + ")";

                        st = con.createStatement();
                        st.executeUpdate(qry11);
                    }

                }
                break;
                default:
                    System.out.println("Please make sure...selection on interface");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("failure");
        } finally {

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

        return mapping.findForward(SUCCESS);

    }
}
