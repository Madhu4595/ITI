/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author APDCA
 */
public class PvtItiAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
       List<ItiGovtBean> dist_names=new ArrayList<ItiGovtBean>();
        
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs=null;

        try {
            
            System.out.println("enter in try block...");
          //  con = DBC.DBConnection.getConnection(request);
            con=DBC.DBConnection.getConnectionGen();
            System.out.println("con---->" +con);
            String sql = "select iti_name,dist_name,address from iti a inner join dist_mst b on a.dist_code=b.dist_code where govt='P'";
            System.out.println(sql);
            st=con.prepareStatement(sql);
            rs=st.executeQuery();
            ItiGovtBean itiGovtBean = null;
            while(rs.next()){
                itiGovtBean = new ItiGovtBean();
//                dist_names.add(rs.getString(1));
//                dist_names.add(rs.getString(2));
//                dist_names.add(rs.getString(3));
                  itiGovtBean.setIti_name(rs.getString("iti_name"));
                  itiGovtBean.setDist_name(rs.getString("dist_name"));
                  itiGovtBean.setAddress(rs.getString("address"));
                  dist_names.add(itiGovtBean);
            }
            
            System.out.println("dist_names size"+dist_names.size());
            request.setAttribute("dist_names", dist_names);
           

        } catch (Exception e) {
            System.out.println("Excep action--->"+e);
            e.printStackTrace();
        }
        finally{
            rs.close();
            st.close();
            con.close();
        }
        System.out.println("end of execute()...");
        return mapping.findForward(SUCCESS);
    }
}

