import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.sql.*;
import java.io.*;
import java.lang.*;
import java.util.*;
import javax.servlet.http.HttpSession;


import common.SendMail;
import javax.servlet.http.HttpServletRequest;

public class adpbackmail
{
    
public static String isBlank(String str)
{
	str=str==null?"":str;
	str=str.replace('<',' ');
        str=str.replace('>',' ');
        str=str.replace('\'',' ');
        str=str.replace('\"',' ');
        str=str.replace('&',' ');
        str=str.replace('%',' ');
	return str;
}


   public static void main(String[] args) throws Exception
   {
   // HttpServletRequest request = null;

String qry="";
PreparedStatement psmt=null;
ResultSet rs=null;
Connection con=null;
Writer output=null;
String startdate="16022017",todate1="",todate="";
try {
startdate=args[0];
try
 {
todate=args[1];
todate1="to_date('"+todate+"','dd-mm-yyyy')";
} catch (Exception e) { todate1="sysdate"; }
if (startdate == null) { System.out.println("Pl mention Start Date and To date (dd-mm-yyyy)");  return; }
}
catch (Exception e) {  System.out.println("Pl mention Start Date");   }
try
{
Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://10.160.4.67" + "/" + "itiap", "iti", "15072016_iti");
    
//con=DriverManager.getConnection("jdbc:oracle:thin:@10.160.0.24:1521:test", "vijn", "vijn");
        java.util.Date d=new java.util.Date();
	int date=d.getDate();
	String datestr="",monstr="";
	if (date < 10)  	{  datestr="0"+date; 	}
        else datestr=""+date;
	int mon=d.getMonth()+1;
	if (mon < 10)  	{  monstr="0"+mon; 	}
        else monstr=""+mon;
	int year=d.getYear()+1900;
        String regid="16099278";
        qry= "select name,fname,dob,regid from application where regid='"+regid +"'";
        System.out.println("qry="+qry);
        psmt = con.prepareStatement(qry);
        rs = psmt.executeQuery();

        String dt=datestr+"/"+monstr+"/"+year;
        dt=startdate+"_"+todate;
   String path="C:/Users/radha/Desktop/"+year+"/";
File pth= new File(path);
boolean folderexists = pth.exists();
if (!folderexists)
 pth.mkdir();
String flnm1="dpi2"+year+monstr+datestr+".txt";
flnm1="dpi2"+dt+".txt";
String flnm=path+flnm1;
System.out.println("flnm="+flnm);
File file = new File(flnm);
output = new BufferedWriter(new FileWriter(file));


String name="",fname="",dob="";
while (rs.next())
{
   //arr_date = isBlank(rs.getString("arr_date"));
   regid = isBlank(rs.getString("regid"));
   name=isBlank(rs.getString("name"));
   fname=isBlank(rs.getString("fname"));
   dob=isBlank(rs.getString("dob"));
   
output.write(regid+"#"+name+"#"+fname+"#"+dob+"\n");
//print "$tamc_code#$dt#$tcomm_code#$tvariety_code#$tgrade_code#\"0\"#0#0#$tmin_price#$tmax_price#$tmodal_price\n";

}     // end of fetch 
output.close();
boolean sm;
sm = SendMail.SendMail1(" dpi.daily agmark data from A.P from New Server","shobha.nuthalapati@gmail.com ","",flnm,"market@ap.nic.in","","");
//sm = SendMail.SendMail1(" dpi.daily agmark data from A.P","agmark@nic.in","",flnm,"market@ap.nic.in","","");
//sm = SendMail.SendMail1(" dpi.daily agmark data from A.P","radha.ap@nic.in","",flnm,"market@ap.nic.in","","");
}
catch (Exception e)
{
  System.out.println("Error..."+e);
}
finally
{
  if (output !=null) { output.close(); output=null; }
  if (rs !=null) { rs.close(); rs=null; }
  if (psmt !=null) { psmt.close(); psmt=null; }
  if (con !=null) { con.close(); con=null; }
}
}
}
