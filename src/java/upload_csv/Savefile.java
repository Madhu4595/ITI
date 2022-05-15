/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upload_csv;

/**
 *
 * @author radha
 */


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Savefile extends HttpServlet {
    public void init(ServletConfig config)throws ServletException{
        super.init(config);
        System.out.println("The SaveFile iniated.^^^^^^^^^^^^^^^^^^^################");
    }
    
    public void service(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        try{
            String pathheader=request.getParameter("filehdr");
            System.out.println("The pathheader is : "+pathheader);
            String pathdetail=request.getParameter("filedtl");
            System.out.println("The pathdetail is : "+pathdetail);
            
            String folderpath=request.getParameter("filefolder");    
            
            String filenamehdr=folderpath+pathheader.substring(pathheader.lastIndexOf('\\'));
            System.out.println("The file output path is : "+filenamehdr);            
            String filenamedtl=folderpath+pathdetail.substring(pathdetail.lastIndexOf('\\'));
            System.out.println("The file output path is : "+filenamedtl);
            
            FileInputStream fis=new FileInputStream(pathheader);
            FileOutputStream fos=new FileOutputStream(filenamehdr);
            
            byte buf[]=new byte[11024];
            fis.read(buf);
            fos.write(buf,0,buf.length);
            
            fis=new FileInputStream(pathdetail);
            fos=new FileOutputStream(filenamedtl);
            fis.read(buf);
            fos.write(buf,0,buf.length);
            
            if(fis!=null)
                fis.close();
            if(fos!=null)
                fos.close();
            
            System.out.println("------------------ Files are Saved in Folder-------------------");
            request.getRequestDispatcher("/uploaddata").forward(request, response);

        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }


}
