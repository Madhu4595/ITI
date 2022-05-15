/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.ntaa.info;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author theru hareesh
 */
public class NewTradeAction extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(true);
        NewTradeFormBean ntf = (NewTradeFormBean) form;
        //int trade_code = ntf.getTrade_code();
        String trade_short = ntf.getTrade_short();
        String trade_name = ntf.getTrade_name();
        int duration_yrs = ntf.getDuration_yrs();
        String eng_noneng = ntf.getEng_noneng();
        String min_qual = ntf.getMin_qual();
        String type_admission=ntf.getType_admission();
        String dr_nondr=ntf.getDr_nondr();
        String short_order = ntf.getShort_order();
        NewTradeDTO ntd = new NewTradeDTO();
        ntd.setShort_order(short_order);
        ntd.setTrade_short(trade_short);
        ntd.setTrade_name(trade_name);
        ntd.setDuration_yrs(duration_yrs);
        ntd.setEng_noneng(eng_noneng);
        ntd.setMin_qual(min_qual);
        ntd.setDr_nondr(dr_nondr);        
        ntd.setType_admission(type_admission);
        NewTradeDAO nc = new NewTradeDAO();

        int n = nc.addNewTrade(ntd,request);

        if (n == -1) {

            return mapping.findForward("exists");
        }

        /*NewTradeDAO d=new NewTradeDAO();
         String s=d.genTradeCode(ntd);
         if(s.length()<=0){
         for(int i=0;i<=s.length();i++){
         NewTradeDTO m=new NewTradeDTO();
         System.out.println("the s value is:"+s.length());
    
         }

         }*/



        /*try {

         DriverManager.registerDriver(new com.mysql.jdbc.Driver());
         con = DriverManager.getConnection("jdbc:mysql://localhost/fdca2", "root", "");


         System.out.println("the connection reference is:" + con);
         String query = "insert into ititrade_master(trade_short,trade_name,durationyrs,eng_nonengg,min_qual) values('" + trade_short + "','" + trade_name + "'," + duration_yrs + ",'" + eng_noneng + "','" + min_qual + "');";
         System.out.println("the oracle query is:" + query);
         stmt = con.createStatement();
         //System.out.println("the statement:"+stmt);
         stmt.executeUpdate(query);
            
           
         } catch (Exception e) {
         e.printStackTrace();
         }


         // NewTradeDAO nto=new NewTradeDAO();
         // nto.addNewTrade(ntd);*/

        return mapping.findForward("success");
    }
}
