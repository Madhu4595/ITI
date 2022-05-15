/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBC;

/**
 *
 * @author radha
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CsvUpload extends HttpServlet {
    
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        System.out.println("The UploadDataServlet2 iniated.");
    }



    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        
        
       // String filepathhdr=request.getParameter("filehdr");
               String semester=request.getParameter("semester");

        String filepathdtl=request.getParameter("filedata");
        Connection con = DBC.DBConnection.getConnection(request);
        System.out.println("connection=----------->"+con);
        PreparedStatement pstmthdr=null;
        PreparedStatement pstmtdtl=null;

        int rowshdr=0;
       // BufferedReader brhdr=new BufferedReader(new FileReader(filepathhdr));
        BufferedReader brdtl=new BufferedReader(new FileReader(filepathdtl));
        System.out.println("reading the file");
        String strLineHdr="";
        String strLineDtl="";
        String hdrstr="";
        String dtlstr="";
        StringTokenizer sthdr=null;
        StringTokenizer stdtl=null;
        //        String firstColumnData[]=new String[10];
        int lineNumberHdr=0;
        int lineNumberDtl=0;
    Statement stmt = null;
    String sql=null;
    String table_name="";
        //        int line=1;
        try{
            
            //create table
            if(semester=="1"){
            
           table_name="aitt_2016_sem1";
            }
            
            stmt = con.createStatement();
            sql = "create table " + table_name + " (htno character varying(14),s1a character(3),s1b character(3),s1c character(3),s1d character(3),s1e character(3))";
            stmt.executeUpdate(sql);
            System.out.println("Created table in given database11111..." + sql);

            //insert into database
            pstmtdtl=con.prepareStatement("insert into "+table_name+" values (?,?,?,?,?,?,?)");
            System.out.println("ps executed");

            while((strLineDtl=brdtl.readLine())!=null){
                System.out.println("detailLINE"+strLineDtl);
                int i=1;
                if(!(lineNumberDtl==0)){
                    stdtl=new StringTokenizer(strLineDtl,",");
                    while(stdtl.hasMoreTokens()){
                        dtlstr=stdtl.nextToken();
                        System.out.println("detail: "+dtlstr);
                        pstmtdtl.setString(i++,dtlstr);
                        System.out.println("below insertion");
                    }
                    int rowsdtl=pstmtdtl.executeUpdate();
                    System.out.println(rowsdtl+" rows are updated.");
                }
                lineNumberDtl++;
            }
            //con.commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally
        {
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        response.sendRedirect("succ.jsp");
    }
}

