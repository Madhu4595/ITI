/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import static java.util.stream.Collectors.mapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.w3c.dom.*;

/**
 *
 * @author Narendra
 */
public class iti_images_upload_action extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        iti_images_upload_form formvariable = (iti_images_upload_form) form;
       
                String iti_code= formvariable.getIti_code();
                
try{
       
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
             ResultSet rs = null,rs1=null,rs2=null;
        Statement st=null,st1=null,st2=null;
		    int count = 0;
            Connection con = DBC.DBConnection.getConnection(request);
PreparedStatement ps1=null;
				    FormFile photo = formvariable.getPhoto();
                String fileName = photo.getFileName();

                try {
                    if (!("").equals(fileName)) {


                        String img_query = "insert into iti_images values(?,?)";
                                        System.out.println(img_query);

                        ps1 = con.prepareStatement(img_query);
                        ps1.setString(1,iti_code);
                        ps1.setBinaryStream(2, photo.getInputStream(), (int) photo.getFileSize());



                        ps1.executeUpdate();


                    }
                } catch (Exception ex) {
                    ps1.close();
                    con.close();
                    System.out.println(ex);
                }
            
        System.out.println("inserted"+count);
		out.println("Photos added successfully");
                                //response.sendRedirect("placementform.jsp");

               // response.sendRedirect("placement_reports.jsp");
            }catch (Exception e) {
            System.out.println(e);
        }
          
return mapping.findForward(SUCCESS);//for
        } 
        
    }

