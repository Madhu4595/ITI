package Master.Trade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
 
public class DgetTrade_AllocationAction extends org.apache.struts.action.Action {
    
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        System.out.println("UR IN DgetTrade_AllocationAction.java");
        
        DgetTrade_AllocationForm daf = (DgetTrade_AllocationForm) form;
        
        String trade_code[]         = daf.getTrade_code();
        String dget_tradecode[]     = daf.getDget_tradecode();
        String sem_no[]             = daf.getSem_no();
        String engg_nonengg[]       = daf.getEngg_nonengg();
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
         
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = DBC.DBConnection.getConnection(request);
            for (int i = 0; i < trade_code.length; i++) {
                if (dget_tradecode[i] != null && dget_tradecode[i]!="") {
                    String final_tradecode=dget_tradecode[i]+sem_no[i]+engg_nonengg[i];
                    String query = "insert into dget_trademaster(trade_code,dget_tradecode,noofsem,engg_nonengg,final_tradecode) values('" + trade_code[i] + "','" + dget_tradecode[i] + "','" + sem_no[i] + "','" + engg_nonengg[i] + "','"+final_tradecode+"')";
                    ps = con.prepareStatement(query);
                    ps.executeUpdate();
                }
            }
            request.setAttribute("success", "<h1 align=" + "center" + "><font color=" + "red" + ">Inserted Successfully</font></h1>");
        } 
        catch (Exception e) { e.printStackTrace(); } 
        finally {
            if (ps != null) { ps.close(); }
            if (con != null) {  con.close(); }
        }

        return mapping.findForward(SUCCESS);
    }
}
