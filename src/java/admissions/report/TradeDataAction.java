package admissions.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TradeDataAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        TradeData tq = (TradeData) form;
        String distcode = beans.MyUtil.filterBad(tq.getDist_code());
        String eng = beans.MyUtil.filterBad(tq.getEng());
        String boardcode = beans.MyUtil.filterBad(tq.getBoard_code());
        
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        ResultSet rs1 = null;
        PreparedStatement ps1 = null;

        String sql = null;
        String query = null;

        try {
            con = DBC.DBConnection.getConnection(request);
            sql = "select b.trade_short,a.iti_code,a.trade_code ,count(a.*) as total from  admissions.iti_admissions a join ititrade b on a.trade_code=b.trade_code and a.iti_code=b.iti_code where a.dist_code=? and a.engg_nonengg=? and a.type_admission=? group by a.trade_code,b.trade_short,a.iti_code order by a.iti_code";

            System.out.println("the query ############## is:" + sql);
            ps = con.prepareStatement(sql);
            ps.setString(1, distcode);
            ps.setString(2, eng);
            ps.setString(3, boardcode);
            rs = ps.executeQuery();
            List tradeList = new ArrayList();
            List CompareList = new ArrayList();
            ArrayList shortname = new ArrayList();
            while (rs.next()) {
                TradeData tfa = new TradeData();
                tfa.setShort_name(rs.getString("trade_short"));
                shortname.add(rs.getString("trade_short"));
                tfa.setTrade_code(rs.getString("trade_code"));
                tfa.setIti_code(rs.getString("iti_code"));
                CompareList.add(rs.getString("iti_code"));

                tradeList.add(tfa);
            }
            /* query = "select b.trade_short,a.iti_code,a.trade_code ,count(a.*) as total from  admissions.iti_admissions a join ititrade b on a.trade_code=b.trade_code where a.dist_code='" + distcode + "' group by a.trade_code,b.trade_short,a.iti_code order by a.iti_code";
            System.out.println("the query is:" + query);
            ps1 = con.prepareStatement(query);
            rs1 = ps1.executeQuery();
            List itiData = new ArrayList();
            while (rs1.next()) {
                TradeData tfad = new TradeData();
                tfad.setShort_name(rs1.getString("trade_short"));
                tfad.setTrade_code(rs1.getString("trade_code"));
                tfad.setIti_code(rs1.getString("iti_code"));
                tfad.setTotal(rs1.getString("total"));
                itiData.add(tfad);
            }*/

            System.out.println("the list size value :" + tradeList.size());
            request.setAttribute("tradeList", tradeList);
            //request.setAttribute("itiData", itiData);
            request.setAttribute("CompareList", CompareList);
            request.setAttribute("tradeshort", shortname);
        } catch (Exception e) {
        }

        return mapping.findForward("success");
    }
}
