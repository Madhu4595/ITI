 
package nic.confirmation;

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

 
public class SubjectAction extends org.apache.struts.action.Action {

     
    private static final String SUCCESS = "success";

     
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("UR in SubjectAction.java");
        
        HttpSession session = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query = "";
        
        SubjectForm s = (SubjectForm) form;

        String[] names = s.getNames();
        int[] max_marks = s.getMax_marks();
        String trade_code1 = beans.MyUtil.filterBad(request.getParameter("trade_code"));

        System.out.println("the trade_code1 value are&&&&&&&&&&&&&&&&:" + trade_code1);

        /* String  sub_one= s.getSubject_one()==null?"":s.getSubject_one()+",";
         String sub_two=s.getSubject_two()==null?"":s.getSubject_two()+",";
         String sub_three=s.getSubject_three()==null?"":s.getSubject_three()+",";
         String sub_four=s.getSubject_four()==null?"":s.getSubject_four()+",";
         String  sub_five=s.getSubject_five()==null?"":s.getSubject_five()+",";
       
         System.out.println("the trade code value is:"+trade_code1);
       
         String temp=sub_one+sub_two+sub_three+sub_four+sub_five;
         temp=temp.substring(0, temp.length() - 1);*/
        //System.out.println("sub 3 is :"+sub_three);
        // System.out.println("sub_one valu is:"+sub_one);
        System.out.println("Iam TRUE 4 all");

        //String trade_code=(String)session.getAttribute("trade");
        //System.out.println("the trade_code session value is:"+trade_code);
        try {
            con = DBC.DBConnection.getConnection(request);
            stmt = con.createStatement();
            String query1 = "delete from iti_trade_subjects where trade_code=?";
            PreparedStatement pst = con.prepareStatement(query1);
            pst.setString(1, trade_code1);
            System.out.println("the delete query is:" + query1);
            pst.executeUpdate();
            /* for (int i = 0; i < names.length; i++) {
                 
             String query1 = "select * from iti_trade_subjects where trade_code=" + trade_code1 + " and subject_code='"+names[i]+"'";
             rs = stmt.executeQuery(query1);
             
             if(rs.next()){
       
             String query2 = "update iti_trade_subjects set subject_code='" + names[i] + "', max_marks="+ marks[i]+" where trade_code=" + trade_code1 + "";
             System.out.println(query2);
             int k = stmt.executeUpdate(query2);
             if (k > 0) {
             return mapping.findForward("update");
             }
             }
                

             else{
             query = "INSERT INTO iti_trade_subjects(trade_code, subject_code,max_marks) VALUES (" + trade_code1 + ",'" + names[i] + "'," + marks[i] + ")";
             System.out.println("the insert query is:" + query);

             int k= stmt.executeUpdate(query);
               
             if(k>0){
               
             return mapping.findForward("success");
             }
             System.out.println("temp:" + temp1);

             }
             }*/

            // query = "select * from iti_trade_subjects where ";
            //System.out.println("The number of subjects are " + names.length);
            for (int i = 0, j = 0; i < names.length; i++) {
                int p = 0;
                System.out.println("p value 1:" + p);

                System.out.println("the max_marks length is:@@@@@@@@@@@@@@@:" + max_marks.length);
                System.out.println("the names length is :%%%%%%%%%%%%%%%%%%:" + names.length);

                while (max_marks[j] == 0) {
                    p++;
                    System.out.println("p 2:" + p);
                    j++;
                    System.out.println("j value in while :" + j);
                    if (max_marks[j] != 0) {
                        p = 0;
                    }
                    System.out.println("p3 value:" + p);
                }
                if (p == 0) {
                    query = "INSERT INTO iti_trade_subjects(trade_code, subject_code,max_marks) VALUES (?,'" + names[i] + "'," + max_marks[j] + ")";
                    System.out.println("the insert query is:" + query);
                    pst = con.prepareStatement(query);
                    pst.setString(1, trade_code1);
                    int n = pst.executeUpdate();
                    j++;
                    System.out.println("the j++ values are in if:" + j);

                }

            }
        } catch (Exception e) {
        } finally {

            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return mapping.findForward(SUCCESS);
    }
}
