/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraceMarks;
import GraceMarks.semester3_GraceMarksForm;
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
public class semester3_GraceMarksAction  extends org.apache.struts.action.Action {
     private static final String SUCCESS = "success";
    private Object session;
    
    
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        semester3_GraceMarksForm s3gf= (semester3_GraceMarksForm) form;
        String int_marks=s3gf.getInt_marks();
        String tot_marks=s3gf.getTot_marks();
        String result=s3gf.getResult();
        String final_result=s3gf.getFinal_result();
      String ext_marks=s3gf.getExt_marks();
        String htno=s3gf.getHtno();
        String paper_result=s3gf.getPaper_result();
        String paper_tot_marks=s3gf.getPaper_tot_marks();
        String sem_paper_result=s3gf.getSem_paper_result();
        String sem_paper_finalresult=s3gf.getSem_paper_finalresult();
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
                query="select sem_finalresult->S3 as sfr,sem_paper_result->S3paper-4 as S4P1,sem_paper_result->S3paper-1 as S3P2,sem_paper_result->S3paper-2 as S3P3,sem_paper_result->S3paper-3 as S3P4,ext_marks->S3B as B,ext_marks->S3C as C,ext_marks->S3D as D from admissions.iti_admissions where htno='" + htno + "'";
               
                ps = con.prepareStatement(query);
                ps.executeUpdate();
                while(rs.next())
                {
           
                    String sem_finalresult=rs.getString("sfr");
                    int ext_marks_temp1=rs.getInt("B");
                     int ext_marks_temp2=rs.getInt("C");
                      int ext_marks_temp3=rs.getInt("D");
                       String sem_paper_result1=rs.getString("S3P1");
                       String sem_paper_result2=rs.getString("S3P2");
                       String sem_paper_result3=rs.getString("S3P3");
                       String sem_paper_result4=rs.getString("S3P4");
                       
                     if(sem_finalresult =="false")
                     {
                       int S3Bg= 60-ext_marks_temp1;
                       int S3Cg= 30-ext_marks_temp2;
                       int S3Dg= 30-ext_marks_temp3;
                       
                         int rtgm=S3Bg+S3Cg+S3Dg;
                         
                         if(rtgm<=7)
                         {
                            ext_marks_temp1= ext_marks_temp1+S3Bg;
                             ext_marks_temp2= ext_marks_temp1+S3Cg;
                              ext_marks_temp3= ext_marks_temp1+S3Dg;
                              
                           String query2;
                    query2="update admissions.iti_admissions set ext_marks->S3B='true',ext_marks->S3C='true',ext_marks->S3D='true',sem_paper_result->S3paper-1='true',sem_paper_result->S3paper-2='true',sem_paper_result->S3paper-3='true' where htno='" + htno + "'";
                ps = con.prepareStatement(query2);
                rs = ps.executeQuery();
 
                         }
                    
                    if(sem_paper_result1 =="true" && sem_paper_result2 =="true" && sem_paper_result3 =="true" && sem_paper_result4 =="true" )     
                    {
                       // String query2;
                     String qr="update admissions.iti_admissions set sem_finalresult->S3='true' where htno='" + htno + "'";
                ps = con.prepareStatement(qr);
                rs = ps.executeQuery();
                    }
                    else
                    {
                        String qr="update admissions.iti_admissions set sem_finalresult->S3='false' where htno='" + htno + "'";
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


    

