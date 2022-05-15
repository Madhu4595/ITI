/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraceMarks;
import GraceMarks.semester4_GraceMarksForm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import static java.util.stream.Collectors.mapping;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 *
 * @author shobha
 */
public class semester4_GraceMarksAction  extends org.apache.struts.action.Action {
     private static final String SUCCESS = "success";
    private Object session;
    
    
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        semester4_GraceMarksForm s4gf= (semester4_GraceMarksForm) form;
        String int_marks=s4gf.getInt_marks();
        String tot_marks=s4gf.getTot_marks();
        String result=s4gf.getResult();
        String final_result=s4gf.getFinal_result();
      String ext_marks=s4gf.getExt_marks();
        String htno=s4gf.getHtno();
        String paper_result=s4gf.getPaper_result();
        String paper_tot_marks=s4gf.getPaper_tot_marks();
        String sem_paper_result=s4gf.getSem_paper_result();
        String sem_paper_finalresult=s4gf.getSem_paper_finalresult();
       // String sem_finalresult=s4gf.getSem_finalresult();
         Connection con = null;
        con = DBC.DBConnection.getConnection(request);
        PreparedStatement ps = null;
        ResultSet rs = null;
       
         
        try{
            
            
            String query;
            int tr=0;
           //SELECT * FROM hstore_test WHERE sale ?| ARRAY['milk','apples'];
        //query= select ext_marks -> 'S4A' from admissions.iti_admissions;
       //query= "SELECT ext_marks->>(S4A) AS s4a,ext_marks(S4B) as s4b,ext_marks(S4C) as s4c,ext_marks(S4D) as s4d FROM admissions.iti_admissions where htno";
               //query = "select ext_marks->'" + S4A + "' as s4a from admissions.iti_admissions"; 
                query="select sem_finalresult->S4 as sfr,sem_paper_result->S4paper-4 as S4P1,sem_paper_result->S4paper-1 as S4P2,sem_paper_result->S4paper-2 as S4P3,sem_paper_result->S4paper-3 as S4P4,ext_marks->S4B as B,ext_marks->S4C as C,ext_marks->S4D as D from admissions.iti_admissions where htno='" + htno + "'";
               
                ps = con.prepareStatement(query);
                ps.executeUpdate();
                while(rs.next())
                {
           
                    String sem_finalresult=rs.getString("sfr");
                    int ext_marks_temp1=rs.getInt("B");
                     int ext_marks_temp2=rs.getInt("C");
                      int ext_marks_temp3=rs.getInt("D");
                       String sem_paper_result1=rs.getString("S4P1");
                       String sem_paper_result2=rs.getString("S4P2");
                       String sem_paper_result3=rs.getString("S4P3");
                       String sem_paper_result4=rs.getString("S4P4");
                       
                     if(sem_finalresult =="false")
                     {
                       int S4Bg= 60-ext_marks_temp1;
                       int S4Cg= 30-ext_marks_temp2;
                       int S4Dg= 30-ext_marks_temp3;
                       
                         int rtgm=S4Bg+S4Cg+S4Dg;
                         
                         if(rtgm<=7)
                         {
                            ext_marks_temp1= ext_marks_temp1+S4Bg;
                            ext_marks_temp2= ext_marks_temp1+S4Cg;
                            ext_marks_temp3= ext_marks_temp1+S4Dg;
                              
                          String query2;
                          
                               query2="update admissions.iti_admissions set ext_marks->S4B='true',ext_marks->S4C='true',ext_marks->S4D='true',sem_paper_result->S4paper-1='true',sem_paper_result->S4paper-2='true',sem_paper_result->S4paper-3='true' where htno='" + htno + "'";
                               ps = con.prepareStatement(query2);
                               rs = ps.executeQuery();
                               
                //paper result set as true
                        String query3;
                        
                              query3="update admissions.iti_admissions set paper_result->S4G='true' where htno='" + htno + "'";
                              ps = con.prepareStatement(query3);
                              rs = ps.executeQuery();
                          }
                         
                    
                    if(sem_paper_result1 =="true" && sem_paper_result2 =="true" && sem_paper_result3 =="true" && sem_paper_result4 =="true" )     
                    {
                       // String query2;
                     String qr="update admissions.iti_admissions set sem_finalresult->S4='true' where htno='" + htno + "'";
                ps = con.prepareStatement(qr);
                rs = ps.executeQuery();
                    }
                    else
                    {
                        String qr="update admissions.iti_admissions set sem_finalresult->S4='false' where htno='" + htno + "'";
                ps = con.prepareStatement(qr);
                rs = ps.executeQuery();
                    }
                    
                    
                     }
                     
                   // int ext_marks_temp=rs.getInt("A");
                    
                    
                   
                }
                
                    
               // RequestDispatcher rd = request.getRequestDispatcher("/semester4_Result.jsp");
               // rd.forward(request, response);
                
            }
        
                
               // request.setAttribute("success", "<h1 align=center><font color='red'>Successfully </font><h1>");
            
        
    

         catch (Exception e) {
            e.printStackTrace();
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


    

