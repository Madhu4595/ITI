/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.confirmation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
 
public class NewSubAction extends org.apache.struts.action.Action {

     
    private static final String SUCCESS = "success";

     
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("UR in NewSubAction.java");
        
        HttpSession session = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        System.out.println("token----------->"+token);
        System.out.println("id----------->"+id);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        NewSubActionForm nsa = (NewSubActionForm) form;
        String trade_name = beans.MyUtil.filterBad(nsa.getTrade_name() == null ? "" : nsa.getTrade_name());
        
        session.setAttribute("trade_name", trade_name);
        String trade_code = beans.MyUtil.filterBad(nsa.getTrade_code() == null ? "" : nsa.getTrade_code());
        session.setAttribute("trade_code", trade_code);
         
        Connection con = null;
        ResultSet rs = null; 
        PreparedStatement ps = null;
        boolean flag = false;

        try {

            con = DBC.DBConnection.getConnection(request); 
            String sql = " select trade_name from ititrade_master where trade_code=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, trade_code);
            rs = ps.executeQuery();

            ArrayList alist = new ArrayList();

            if (rs.next()) {
                NewSubActionForm na = new NewSubActionForm();
                na.setTrade_name(rs.getString(1));
                alist.add(na);
            }
            System.out.println("the trade_name list size value is:" + alist.size());
            session.setAttribute("alist", alist);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        //Fetching the subjects
        try {
            con = DBC.DBConnection.getConnection(request);
            String sql = "SELECT subject_code,subject_name  FROM iti_subjects_master";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ArrayList aone = new ArrayList();
            while (rs.next()) {
                NewSubActionForm getData = new NewSubActionForm();
                getData.setSubject_code(rs.getString(1));
                getData.setSubject_name(rs.getString(2));
                aone.add(getData);
            }
            session.setAttribute("aa", aone);

        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            con.close();

        }

        /*Fetching the records from iti_trade_subjects*/
        try {

            con = DBC.DBConnection.getConnection(request);
            
            String queryn = "select * from iti_trade_subjects where trade_code=? order by subject_code";
            ps = con.prepareStatement(queryn);
            ps.setString(1, trade_code);
            rs = ps.executeQuery();
            System.out.println("the fetching query in trade_subjects:" + queryn);
            ArrayList listnn = new ArrayList();
            while (rs.next()) {

                NewSubActionForm nn = new NewSubActionForm();
                nn.setTrade_code(rs.getString(1));
                nn.setSubject_code(rs.getString(2));
                nn.setMax_marks(rs.getString(3));
                listnn.add(nn);

            }
            /*if(!listnn.isEmpty()){

             flag=true;
             }*/
            System.out.println("the arraylist size value is:" + listnn.size());
            session.setAttribute("listnn", listnn);
//request.setAttribute("flag", flag);
        } catch (Exception e) {

            e.printStackTrace();;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }
        }

        try {
            con = DBC.DBConnection.getConnection(request);
            String querData = "select * from iti_subjects_master a  where a.subject_code not in (select subject_code from iti_trade_subjects b where trade_code=?)";
            System.out.println(querData);
            ps = con.prepareStatement(querData);
            ps.setString(1, trade_code);
            rs = ps.executeQuery();
            
            ArrayList myList = new ArrayList();
            while (rs.next()) {
                NewSubActionForm nm = new NewSubActionForm();
                nm.setSubject_code(rs.getString(1));
                nm.setSubject_name(rs.getString(2));
                myList.add(nm);

            }
            session.setAttribute("myList", myList);

        } catch (Exception e) {

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
