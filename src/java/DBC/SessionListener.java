/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBC;

import javax.servlet.ServletContext;  
import javax.servlet.http.HttpSessionEvent;  
import javax.servlet.http.HttpSessionListener;  

public class SessionListener implements HttpSessionListener{  
    ServletContext ctx=null;  
    static int total=0,current=0;  
      
    public void sessionCreated(HttpSessionEvent e) { }  
  
    public void sessionDestroyed(HttpSessionEvent e) {
        
        try{
            MyUtil masterFunc =    new MyUtil();
            masterFunc.resetSessionDetails(e);
                System.out.println("Destroyed ...");
        }
        catch(Exception ex){
            System.out.println("exception "+ex);
            ex.printStackTrace();
        }    
    }  
}
