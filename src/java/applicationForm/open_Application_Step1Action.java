package applicationForm;

import DBC.generic;
import beans.MyUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.util.Date;  

 
public class open_Application_Step1Action extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)throws Exception {
        
         
        HttpSession ses = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        System.out.println("token------->"+token);
        System.out.println("id------->"+id);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        ses.invalidate();
        ses = request.getSession();
        
        System.out.println("in action------>");
        System.out.println("open_Application_Step1Action");
        String return_to = "exists";
        
        open_Application_Step1_form apf = (open_Application_Step1_form) form;
 
        String type = MyUtil.filterBad(apf.getSsc_type());  
        String board = MyUtil.filterBad(apf.getSsc_board());
        String ssc_regno = MyUtil.filterBad(apf.getSsc_regno());    
        String sscyear = MyUtil.filterBad(apf.getPassed_year());
        
        ses.setAttribute("ssc_regno",ssc_regno);
        ses.setAttribute("ssc_board",board); 
        ses.setAttribute("ssc_type",type); 
        ses.setAttribute("ssc_year",sscyear);
        
        String rec = null,       name = null,      sex = null,       fname = null,    mname = null,    dob = null;
        String l1mrk = null,     l1flg = null,     l1grade = null,   l1points = null, l2p1pc = null,    l2mrk = null;
        String l2flg = null,     l2grade = null,   l2points = null,  l3p1pc = null,   l3mrk = null,     l3flg = null; 
        String l3points = null,  matmrk = null,    matflg = null,    matgrade = null, matpoints = null, scimrk = null;
        String sciflg = null,    scigrade = null,  scipoints = null, socmrk = null,   socflg = null,    socgrade = null;
        String socpoints = null, gtot = null,      gpa = null,       result = null,   dist_name = null,  adm_num = null; 
        String regid = null,     university=null,  l1p1pc = null,    l1p2pc = null,   l3grade = null;
       
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
        
        try{ 
            Date d=new Date();  
            int year1=d.getYear(); 
            year1 = Calendar.getInstance().get(Calendar.YEAR);
                System.out.println("year is---->"+year1);
                
            String reg_id = generic.ifApplicantExists(ssc_regno, board, sscyear);
            
            boolean exists = false;
                System.out.println("sjdfksdhfsd "+reg_id);
                
            Connection con1=null;
            PreparedStatement pstm=null;
            ResultSet rst=null;
            
            int count=0;
            int dbregid=0;
            
            try {
                con1=DBC.DBConnection.getConnectionGen();
                String sql="select count(*) as count,regid from student_application where ssc_regno=? group by regid";
             
                pstm = con1.prepareStatement(sql);
                pstm.setString(1, ssc_regno);
                rst=pstm.executeQuery();
                if(rst.next()){
                  count=rst.getInt("count");
                    System.out.println("dfgklfdgdf"+count);
                }
                if (count>0) {
                    System.out.println("df;skl;dssd");
                exists = true;
                return_to = "exists";
                ses.setAttribute("regid", reg_id);
                return mapping.findForward(return_to);
                }
            } 
            catch (NullPointerException e) {
                  System.out.println("testquery"+e);
                  e.printStackTrace();
                  exists = false;
            }
            
            String  year ="2011";
            System.out.println("sdfkljf"+exists);
            if(Integer.parseInt(sscyear)>=2020||Integer.parseInt(sscyear)<=2011){
              return_to = "new_applicant";
            }
            else{
                if (!exists) {
                        System.out.println("not exists");
                    return_to = "new_applicant";
                if(board.equals("28")){    
                return_to = "new_applicant";
                String query = "select name,fname,mname,dob,sex,dist_name,l1mrk,l1grade,l1points,l2mrk,l2grade,l2points,l3mrk,l3grade,l3points,matmrk,matgrade,matpoints,scimrk,scigrade,scipoints,socmrk,socgrade,socpoints,gtot,gpa,university_area from ssc.ssc_data_?_ap where rollno= ? and year=?";
 
            con = DBC.DBConnection.getConnectionGen();
            
            pstm=null;
            pstm=con.prepareStatement(query);
            pstm.setString(1,sscyear);
            pstm.setString(2,ssc_regno);
            pstm.setString(3,sscyear);
            System.out.println("pstm---------->"+pstm);
            rs=pstm.executeQuery();
            

        if (rs.next()) {
            System.out.println("nextrs");
            name = MyUtil.filterBad(rs.getString("name")==null?"":rs.getString("name"));    
            fname = MyUtil.filterBad(rs.getString("fname"));
            mname = MyUtil.filterBad(rs.getString("mname"));              dob = MyUtil.filterBad(beans.MyUtil.ChDate(rs.getString("dob")));
            sex = MyUtil.filterBad(rs.getString("sex"));                  dist_name = MyUtil.filterBad(rs.getString("dist_name"));
            l1mrk = MyUtil.filterBad(rs.getString("l1mrk"));              l1grade = MyUtil.filterBad(rs.getString("l1grade"));
            l1points = MyUtil.filterBad(rs.getString("l1points"));        l2mrk = MyUtil.filterBad(rs.getString("l2mrk"));
            l2grade = MyUtil.filterBad(rs.getString("l2grade"));          l2points = MyUtil.filterBad(rs.getString("l2points"));
            l3grade = MyUtil.filterBad(rs.getString("l3grade"));          l3points = MyUtil.filterBad(rs.getString("l3points"));
            l2points = MyUtil.filterBad(rs.getString("l2points"));        l1points = MyUtil.filterBad(rs.getString("l1points"));
            matmrk = MyUtil.filterBad(rs.getString("matmrk"));            matgrade = MyUtil.filterBad(rs.getString("matgrade"));
            matpoints = MyUtil.filterBad(rs.getString("matpoints"));      scimrk = MyUtil.filterBad(rs.getString("scimrk"));
            scigrade = MyUtil.filterBad(rs.getString("scigrade"));        scipoints = MyUtil.filterBad(rs.getString("scipoints"));
            socmrk = MyUtil.filterBad(rs.getString("socmrk"));            socgrade = MyUtil.filterBad(rs.getString("socgrade"));
            socpoints = MyUtil.filterBad(rs.getString("socpoints"));      gtot = MyUtil.filterBad(rs.getString("gtot"));
            gpa = MyUtil.filterBad(rs.getString("gpa"));                  university = MyUtil.filterBad(rs.getString("university_area"));
            
             
            ses.setAttribute("in_db", 1);                   ses.setAttribute("name",name);
            ses.setAttribute("fname",fname);                ses.setAttribute("mname",mname);
            ses.setAttribute("dob",dob);                    ses.setAttribute("gender",sex);
            ses.setAttribute("dist_name",dist_name);        ses.setAttribute("first_lang_marks",l1mrk);
            ses.setAttribute("l1grade",l1grade);            ses.setAttribute("l1points",l1points);
            ses.setAttribute("second_lang_marks",l2mrk);    ses.setAttribute("l2grade",l2grade);
            ses.setAttribute("l2points",l2points);          ses.setAttribute("l3mrk",l3mrk);
            ses.setAttribute("l3grade",l3grade);            ses.setAttribute("l3points",l3points);                    
            ses.setAttribute("maths_marks",matmrk);         ses.setAttribute("matpoints",matpoints);                    
            ses.setAttribute("science_marks",scimrk);       ses.setAttribute("scipoints",scipoints);
            ses.setAttribute("social_marks",socmrk);        ses.setAttribute("socpoints",socpoints);
            ses.setAttribute("ssc_type", type);             ses.setAttribute("tot_marks_gpa",gtot);
            ses.setAttribute("tot_gpa",gpa);              
                    
                    
        }}else{ ses.setAttribute("regid", reg_id); }
        
        } else {
            ses.setAttribute("regid", reg_id);
        }}
        } catch (NullPointerException e) { System.out.println("testquery"+e); e.printStackTrace(); }
        return mapping.findForward(return_to);
    }

} 