 
package Admission.Process;

import java.sql.Connection;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpSession;

 
public class Iti_Admissions_Report_Action extends org.apache.struts.action.Action {

     
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
         
        Iti_Admissions_Report_Form ar = (Iti_Admissions_Report_Form) form;
        
        String adm_num              =   beans.MyUtil.filterBad(ar.getAdm_num().trim());
        String regid                =   beans.MyUtil.filterBad(ar.getRegid().trim());
        String name                 =   beans.MyUtil.filterBad(ar.getName().toUpperCase().trim());
        String fname                =   beans.MyUtil.filterBad(ar.getFname().toUpperCase().trim());
        String phno                 =   beans.MyUtil.filterBad(ar.getPhno().trim());
        String gender               =   beans.MyUtil.filterBad(ar.getGender().trim());
        String caste                =   beans.MyUtil.filterBad(ar.getCaste().toUpperCase().trim());
        String res_category         =   beans.MyUtil.filterBad(ar.getRes_category().trim());
        String year_of_admission    =   beans.MyUtil.filterBad(ar.getYear_of_admission().trim());
        String table                =   beans.MyUtil.filterBad(ar.getTable().trim());
        String type_admission       =   beans.MyUtil.filterBad(ar.getType_admission().trim());
        String date_of_admission    =   beans.MyUtil.filterBad(ar.getDate_of_admission().trim());
        String trade_code           =   beans.MyUtil.filterBad(ar.getTrade_code().trim());
        String sem_nonsem           =   beans.MyUtil.filterBad(ar.getSem_nonsem().trim());
        String phase                =   beans.MyUtil.filterBad(ar.getPhase().trim());
        String dist_code            =   beans.MyUtil.filterBad(ar.getDist_code().trim());
        String table_name           =   beans.MyUtil.filterBad(ar.getTable1().trim());
        String year                 =   beans.MyUtil.filterBad(ar.getYear().trim());
        String month                =   beans.MyUtil.filterBad(ar.getMonth().trim());
        
        String table1 = table_name + "_" + year + "_" + month;
        
        table1 = table1.trim();
        
        request.setAttribute("month",month);
        request.setAttribute("year",year);
        
        String query = "";
        String query1="";
        String iti_code=ar.getIti_code();
        
        if (month.isEmpty() || year.isEmpty()) {
            query = "select * from ? a where  ";
        } else {
            query1="select a.adm_num,a.name,a.fname,a.gender,a.iti_code,a.olditi_code,b.attempt_no,(select iti_name from iti where iti_code=a.iti_code),(select trade_name from ititrade_master  where trade_code=a.trade_code),rdd_approv,* from ?  b,?  a where  ";
        }
        
        Map m = new HashMap();
        m.put("adm_num", adm_num);
        m.put("regid", regid);
        m.put("name", name);
        m.put("fname", fname);
        m.put("phno", phno);
        m.put("gender", gender);
        m.put("caste", caste);
        m.put("res_category", res_category);
        m.put("year_of_admission", year_of_admission);
        m.put("type_admission", type_admission);
        m.put("date_of_admission", date_of_admission);
        m.put("trade_code", trade_code);
        m.put("sem_nonsem", sem_nonsem);
        m.put("phase", phase);
        m.put("dist_code", dist_code);
        m.put("iti_code", iti_code);
        
        Iterator it = m.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            if (pairs.getValue().toString().isEmpty()) {
            } else if (pairs.getKey().equals("adm_num")||pairs.getKey().equals("iti_code")) {
                query = query + "a." + pairs.getKey() + " = '" + pairs.getValue() + "' and  ";
                query1 = query1 + "a." + pairs.getKey() + " = '"  + pairs.getValue() + "' and ";
            } else if (pairs.getKey().equals("phno") || pairs.getKey().equals("date_of_admission")) {
                query = query + "a." + pairs.getKey() + "::character varying like '" + pairs.getValue() + "%' and  ";
                query1 = query1 + "a." + pairs.getKey() + "::character varying like '" + pairs.getValue() + "%' and  ";
            } else if (pairs.getKey().equals("gender") || pairs.getKey().equals("type_admission") || pairs.getKey().equals("caste") || pairs.getKey().equals("phase") || pairs.getKey().equals("sem_nonsem") || pairs.getKey().equals("trade_code") || pairs.getKey().equals("dist_code")) {
                query = query + "a." + pairs.getKey() + "='" + pairs.getValue() + "' and  ";
                query1 = query1 + "a." + pairs.getKey() + "='" + pairs.getValue() + "' and  ";
            } else if (pairs.getKey().equals("name") || pairs.getKey().equals("fname")) {
                query = query + "a." + pairs.getKey() + " ilike '" + pairs.getValue() + "%' and ";
                query1 = query1 + "a." + pairs.getKey() + " ilike '" + pairs.getValue() + "%' and ";
            } else {
                query = query + "a." + pairs.getKey() + " like '%" + pairs.getValue() + "%' and ";
                query1 = query1 + "a." + pairs.getKey() + " like '%" + pairs.getValue() + "%' and ";
            }
            it.remove();
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
    try {
            con = DBC.DBConnection.getConnection(request);
            if (month.isEmpty() || year.isEmpty()) {
                query = query.trim();
                query = query.substring(0, query.lastIndexOf(" ") + 1);
                ps = con.prepareStatement(query);
                ps.setString(1, table);
                 
            } else {
                query1 = query1 + "a.adm_num=b.adm_num order by a.adm_num";
                ps = con.prepareStatement(query1);
                ps.setString(1, table1);
                ps.setString(2, table);
                 
            }
            
            rs = ps.executeQuery();
            ArrayList list = new ArrayList();
            while (rs.next()) {
                ReportSerializable ar1 = new ReportSerializable();
                ar1.setAdm_num(rs.getString("adm_num"));
                ar1.setRegid(rs.getString("regid"));
                ar1.setName(rs.getString("name"));
                ar1.setFname(rs.getString("fname"));
                ar1.setGender(rs.getString("gender"));
                ar1.setCaste(rs.getString("caste"));
                ar1.setRes_category(rs.getString("res_category"));
                ar1.setYear_of_admission(rs.getString("year_of_admission"));
                ar1.setPhno(rs.getString("phno"));
                ar1.setDate_of_admission(rs.getString("date_of_admission"));
                ar1.setType_admission(rs.getString("type_admission"));
                ar1.setSem_nonsem(rs.getString("sem_nonsem"));
                ar1.setTrade_code(rs.getString("trade_code"));
                ar1.setDist_code(rs.getString("dist_code"));
                ar1.setResult(rs.getString("result"));
                ar1.setFinal_result(rs.getString("final_result"));
                ar1.setIti_code(rs.getString("iti_code"));
                ar1.setOlditi_code(rs.getString("olditi_code"));
                if (!(month.isEmpty()) || !(year.isEmpty())) {
                ar1.setAttempt_no(rs.getString("attempt_no"));
                ar1.setIti_name(rs.getString("iti_name"));
                ar1.setTrade_name(rs.getString("trade_name"));
                ar1.setRdd_approv(rs.getString("rdd_approv"));
                ar1.setConv_approv(rs.getString("conv_approv"));
                String rddts=rs.getString("rddts");
                     
                try{
                if(rddts.equalsIgnoreCase(null)){
                ar1.setRddts(rs.getString("rddts"));
                }else{
                    rddts=rddts.substring(0,10);
                    ar1.setRddts(rs.getString("rddts"));
                }
                }catch(Exception ex){
                    ar1.setRddts(rs.getString("rddts"));
                }
                String convts=rs.getString("convts");
                
                try{
                if(convts.equalsIgnoreCase(null)){
                ar1.setConvts(rs.getString("convts"));
                }else{
                    convts=convts.substring(0,10);
                    ar1.setConvts(rs.getString("convts"));
                }
                }catch(Exception ex){
                    ar1.setConvts(rs.getString("convts"));
                }
                    ar1.setConvts(rs.getString("convts"));
                
                }
                list.add(ar1);
            }
            
            
            request.setAttribute("list", list);
            session.setAttribute("size", list.size());
            
            
            
	
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

