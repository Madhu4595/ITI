package DBC;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpSessionEvent;

 
public class MyUtil {
   
    public static String ChDate(String s1) {
        String day, month, year, date;
        year = s1.substring(0, 4);
        month = s1.substring(5, 7);
        day = s1.substring(8, 10);
        date = day + '-' + month + '-' + year;
        return date;
    }

    public static String ChDate1(String s1) {
        String day, month, year, date;
        day = s1.substring(0, 2);
        month = s1.substring(3, 5);
        year = s1.substring(6, 10);
        date = year + '-' + month + '-' + day;
        return date;
    }
    public static String day(String s1) {
        String day = s1.substring(0, 2);
        return day;
    }
    public static int DuplicateCheck(String tablename,String wherecondtion,String extra) { 	
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = null;
	int status =0;
        System.out.println("wherecondtion"+wherecondtion);
	try{
	con = DBConnection.getConnectionGen();
	pstmt = con.prepareStatement("SELECT  count(*) as check  FROM "+tablename+" WHERE "+wherecondtion+" ");
	rs = pstmt.executeQuery();
        System.out.println("itistatusis"+pstmt);
   	if (rs.next()) {
   		status=rs.getInt("check");
	
	}
	}catch (Exception e) {
            e.printStackTrace();
		} finally {
		try {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		} 
                catch (Exception e) { }
	}
	return status;
	
}
    public static String month(int s1) {
        String month =s1+"";
        if (month.equalsIgnoreCase("1") || month.equalsIgnoreCase("1")) { return "January";
        } else if (month.equalsIgnoreCase("2")) { return "February";
        } else if (month.equalsIgnoreCase("3")) { return "March";
        } else if (month.equalsIgnoreCase("4")) { return "April";
        } else if (month.equalsIgnoreCase("5")) { return "May";
        } else if (month.equalsIgnoreCase("6")) { return "June";
        } else if (month.equalsIgnoreCase("7")) { return "July";
        } else if (month.equalsIgnoreCase("8")) { return "August";
        } else if (month.equalsIgnoreCase("9")) { return "September";
        } else if (month.equalsIgnoreCase("10")){ return "October";
        } else if (month.equalsIgnoreCase("11")) { return "November";
        } else if (month.equalsIgnoreCase("12")) { return "December";
        } else { return "invalid Month";
        }
    }

    public static String year(String s1) { String year = s1.substring(6, 10); return year; }

    public static String year1(String s1) { String year = s1.substring(0, 4); return year; }

    public static String monthnum(String s1) { String month = s1.substring(3, 5); return month; }

    public static String monthh(int s1) {

        if (s1 == 1) { return "January";
        } else if (s1 == 2) { return "February";
        } else if (s1 == 3) { return "March";
        } else if (s1 == 4) { return "April";
        } else if (s1 == 5) { return "May";
        } else if (s1 == 6) { return "June";
        } else if (s1 == 7) { return "July";
        } else if (s1 == 8) { return "August";
        } else if (s1 == 9) { return "September";
        } else if (s1 == 10){ return "October";
        } else if (s1 == 11){ return "November";
        } else if (s1 == 12){ return "December";
        } else { return "invalid Month";
        }
    }
      public static  BigDecimal decimalElimante(double x,int numberofDecimals){
  	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
  	   
  	} 
      
      
      
       public static boolean checkExistingSession(String user) {
            ResultSet rs1 = null;
            int updateCnt = 0;
            boolean result=false;
            Connection con = null;  
            ResultSet resultSetSes = null;
            PreparedStatement pst = null;
        try{
        con = DBConnection.getConnectionGen();
        String sessionQryStr = "select * from login_history where uname ='"+user+"' and login_status='1' and slno=(select max(slno) from login_history where uname='"+user+"') ";
            pst = con.prepareStatement(sessionQryStr);
            resultSetSes = pst.executeQuery();
            if(resultSetSes.next()){ result = !result; }
        }
        catch(Exception ex){ ex.printStackTrace(); }
        finally {
            try {
                if (resultSetSes != null) { resultSetSes.close(); resultSetSes = null; }
                if (pst != null) { pst.close(); pst = null; }

                if (con != null) { con.close(); con = null; }
            }catch (Exception ex) { }
        }
        return result;
  }
       
       public boolean resetSessionDetails(HttpSessionEvent e) {
            ResultSet rs1 = null;
        
            int updateCnt = 0;
            boolean result=false;
            Connection con = null; 
            PreparedStatement pst = null;
           
            try{
           
                con = DBC.DBConnection.getConnectionGen();
        
                if(e.getSession().getAttribute("userName") != null){
                    String userId = e.getSession().getAttribute("userName").toString();
        
                    String updSesQry = "update login_history set login_status='0' where uname='"+userId+"' and slno=(select max(slno) from login_history where uname='"+userId+"')";
                    pst = con.prepareStatement(updSesQry);
                        System.out.println("PS session update : "+pst);
                    updateCnt = pst.executeUpdate();
                }
                    if(updateCnt > 0){
                        return !result;
                }else{
                    return result;
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
        
            }finally {
            try {
                
                if (pst != null) {  pst.close(); pst = null; }
                if (con != null) { con.close(); con = null; }
            } 
            catch (Exception ex) { ex.printStackTrace();}
        }
        return result;
  }
    
       
   
      
}
