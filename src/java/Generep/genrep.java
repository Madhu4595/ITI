package Generep;

import java.io.*;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import javax.sql.DataSource;

import beans.MyUtil;

public class genrep extends HttpServlet {

    PrintWriter out;
    BufferedReader br;
    Connection conn = null;
    PreparedStatement ps = null;
    Statement st = null;
    Statement stat1 = null, stat2 = null;
    ResultSet rs = null;
    ResultSet rs1 = null, rs2 = null;
    boolean screen = true;
    StringTokenizer st1, st2, st3, st4, st5;
    String v1[] = new String[300];
    String v2[] = new String[300];
    String f[] = new String[300];
    String t[] = new String[300];
    String datatype[] = new String[300];
    String search_criteria = "", target_frame = "", targetstr = "", bgcolor = "", onchange = "", backfilename = "";
    boolean gfld[] = new boolean[300];
    String fvalue[][] = new String[200][200];
    String avalue[] = new String[300];
    int scount = 0, field_count = 0;
    int rcount = 0, rowcount = 0;
    int i = 0, j = 0;
    int counter = 1;
    String groupbyvalue[] = new String[20];
    String heading = "";
    String code = "", fl = "";
    int inccount = 0, totcount = 0, no_of_pages = 0;
    String no_page_number_links = "t";

//new
    /**
     * *********************************************************************************************
     */
    String replace(String tag, String line, int j) {
        String tempoutput = "";

        if (line.indexOf("<#gslno>") >= 0) {

            int tt = line.indexOf("<#gslno>");
            line = line.substring(0, tt) + counter + line.substring(tt + 8, line.length());
            counter++;
        }
        int fnum = 0;
        while (line.length() > 0) {
            String tag2 = tag.substring(0, 1) + "/" + tag.substring(1, tag.length());
            int in1 = line.indexOf(tag);
            int in2 = line.indexOf(tag2);
            if (in1 < 0) {
                tempoutput += line.substring(0, line.length());
                line = "";
            } else {
                fnum = Integer.parseInt(line.substring(in1 + tag.length(), in2).trim());
                if (tag.equals("<#gstat>")) {
                    line = line.substring(0, in1) + avalue[fnum - 1] + line.substring(in2 + tag2.length(), line.length());
                } else if (gfld[fnum - 1] == true) {
                    if (fvalue[j][fnum - 1].equals(groupbyvalue[fnum - 1])) {
                        line = line.substring(0, in1) + "\"" + line.substring(in2 + tag2.length(), line.length());
                    } else {
                        groupbyvalue[fnum - 1] = fvalue[j][fnum - 1];
                        line = line.substring(0, in1) + fvalue[j][fnum - 1] + line.substring(in2 + tag2.length(), line.length());
                    }

                } else {
                    line = line.substring(0, in1) + fvalue[j][fnum - 1] + line.substring(in2 + tag2.length(), line.length());
                }

            }  // else

        }//while

        return tempoutput;
    }

// end of     String replace(String tag, String line , int j)
    /**
     * *********************************************************************************************
     */
    String replaceblob(String line, String tab, Vector bfld, String wc, int j, String pkf, String pkfnum) throws Exception {
        String tag = "<#gblob>", tempoutput = "";
        String tag2 = "</#gblob>";

        try {

            rs.first();
            while (line.length() > 0) {
                int in1 = line.indexOf(tag);
                int in2 = line.indexOf(tag2);
                if (in1 < 0) {
                    tempoutput += line.substring(0, line.length());
                    line = "";
                } else {
                    int fnum = Integer.parseInt(line.substring(in1 + tag.length(), in2).trim());
                    String pkfval = "";
                    int pp;

                    if (pkfnum.indexOf("!") > 0) {
                        StringTokenizer p1 = new StringTokenizer(pkfnum, "!");
                        while (p1.hasMoreTokens()) {
                            String tk = (String) p1.nextToken();
                            pp = Integer.parseInt(tk);
                            pkfval += fvalue[j][pp - 1] + "!";
                        }   // while

                        pkfval = pkfval.substring(0, pkfval.length() - 1);
                    } // if
                    else {
                        pp = Integer.parseInt(pkfnum);
                        pkfval = fvalue[j][pp - 1];
                    }

// 		     String s="./GImage?tab="+tab+"&fld="+bfld.elementAt(fnum-1)+"&wh="+wc+"&pkf="+pkf+"&pkfnum="+fvalue[j][pkfnum-1];
                    String s = "./GImage?tab=" + tab + "&fld=" + bfld.elementAt(fnum - 1) + "&wh=" + wc + "&pkf=" + pkf + "&pkfnum=" + pkfval;

                    line = line.substring(0, in1) + s + line.substring(in2 + tag2.length(), line.length());
                }//else
            }//while
        } // try
        catch (NumberFormatException ne) {
            ne.printStackTrace();
            out.println("<center><h2>Please check your Html format</h2></center>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("replace blob   " + e);
        }

        return tempoutput;
//http://localhost:8080/efile/servlet/genrep?code=eimg&fl=2&v10=2&scount=1&t1=def&f1=efile.docid
    }  // end of replaceblob

    /**
     * *********************************************************************************************
     */
    /**
     * *********************************************************************************************
     */
    public String Report(String htmlfile, String tab, Vector bfld, String wh, String pkf, String pkfnum, HttpServletRequest req) throws Exception {
        String output = "";
        try {

            String tag = "";
            bgcolor = "white";
            if (rcount == 0) {
                if (bgcolor == null || bgcolor.equals("")) {
                    bgcolor = "white";
                }
                out.println("<body  bgcolor=" + bgcolor + ">");
                out.println("<center>");
                out.println("<b>Information is not available for Chosen Criteria</b>");
                //out.println("<br><br><br><a href='javascript:history.go(-1)'> Click Here</a> to Go Back");
                out.println("<br><br><br><a href='" + backfilename + "'> Click Here</a> to Go Back");

                out.println("</center>");
                return "";
            }

            String line = "";
            FileInputStream file_url = new FileInputStream(htmlfile);
            br = new BufferedReader(new InputStreamReader(file_url));
            int per = totcount % inccount; //modulo value
            no_of_pages = totcount / inccount;
//out.println("perrrrrrrrr = "+totcount+"**"+inccount);
            if (per > 0) {
                no_of_pages++;
            }
            int mm = rowcount % inccount;
            int pageno = 1;
            if (mm > 0) {
                pageno = rowcount / inccount + 1;
            } else {
                pageno = rowcount / inccount;
            }
            //out.println("rowcount "+rowcount+" inccount "+inccount+" page no "+pageno);
            while (true) {
                if ((line = br.readLine()) == null) {
                    break;
                }
                line = line.trim();
                if (line.indexOf("</HTML>") >= 0 || line.indexOf("</html>") >= 0) {
                    break;
                }

                if (line.indexOf("<#gloop>") < 0 && line.indexOf("<#gval>") < 0 && line.indexOf("<#gstat>") < 0 && line.indexOf("<#gdate>") < 0 && line.indexOf("<#gblob>") < 0 && line.indexOf("<#gheading>") < 0 && line.indexOf("<#gpageno>") < 0 && line.indexOf("<#gsearch>") < 0) {
                    output += line;
                }
                if (line.indexOf("<#gdate>") >= 0) {
                    int in1 = line.indexOf("<#gdate>");
                    java.util.Date d = new java.util.Date();
                    int date = d.getDate();
                    int mon = d.getMonth() + 1;
                    int year = d.getYear() + 1900;
                    String date1 = String.valueOf(date + "-" + mon + "-" + year);
                    output += line.substring(0, in1) + date1 + line.substring(in1 + 8, line.length());
                } else if (line.indexOf("<#gheading>") >= 0) {
                    int tt = line.indexOf("<#gheading>");
                    output += line.substring(0, tt) + heading + line.substring(tt + 11, line.length());
                } else if (line.indexOf("<#gpageno>") >= 0) {
                    int tt = line.indexOf("<#gpageno>");
                    output += line.substring(0, tt) + "Page No. " + pageno + line.substring(tt + 10, line.length());
                } else if (line.indexOf("<#gsearch>") >= 0) {
                    int tt = line.indexOf("<#gsearch>");
                    if (!(search_criteria.equals(""))) {
                        output += line.substring(0, tt) + "<table border=1><tr><td>Search Criteria:<br>" + search_criteria + line.substring(tt + 10, line.length()) + "</td></tr></table>";
                    }
                } else if (line.indexOf("<#gstat>") >= 0 && pageno == no_of_pages) {
                    tag = "<#gstat>";
                    output += replace(tag, line, 0);
                    tag = "";
                } else if (line.indexOf("<#gval>") >= 0 && line.indexOf("<#gloop>") < 0) {
                    tag = "<#gval>";
                    output += replace(tag, line, 0);
                    tag = "";
                } else if (line.indexOf("<#gblob>") >= 0 && line.indexOf("<#gloop>") < 0) {
                    output += replaceblob(line, tab, bfld, wh, 0, pkf, pkfnum);

                } else if (line.indexOf("<#gloop>") >= 0) {
                    //counter=1;
                    String temp;
                    line = "";
                    while (true) {
                        temp = br.readLine();
                        if (temp.indexOf("</#gloop>") >= 0) {
                            break;
                        }
                        line += temp.substring(0, temp.length());
                    }
                    String line1 = "", line2 = "";
                    if (line.indexOf("<#gloop ") > 0) //inner gloop
                    {
                        line2 = line.substring(0, line.indexOf("<#gloop ") - 1);
                        line1 = line.substring(line.indexOf("<#gloop ") + 11, line.indexOf("</#gloop "));
                    } else {
                        line2 = line;
                        line1 = "";
                    }
                    for (j = 0; j < rcount; j++) {

                        String temp1 = "";
                        int col_count = i;

//					  if( line2.indexOf("<#gval>") >= 0 && line2.indexOf("<#gloop") < 0 )
                        if (line2.indexOf("<#gval>") >= 0) {

                            tag = "<#gval>";
                            temp1 = replace(tag, line2, j);
                        }

                        //if( temp1.indexOf("<#gblob>") >= 0 && temp1.indexOf("<#gloop") < 0 )
                        if (line2.indexOf("<#gblob>") >= 0) {
                            temp1 = replaceblob(line2, tab, bfld, wh, j, pkf, pkfnum);
                        }

                        output += temp1;

                        // below code added to accomodate an inner gloop
                        if (line.indexOf("<#gloop ") > 0) // inner gloop found
                        {

                            int ik = line.indexOf("<#gloop ");
                            String ss = line.substring(ik + 8, ik + 10).trim();
                            int gloopno = Integer.parseInt(ss);
                            String ftmp = fvalue[j][gloopno];
                            String temp2 = "";

//					  while (j <= rcount)
                            while (j < inccount && j < rowcount) {

                                if (fvalue[j][gloopno].equals(ftmp)) {

                                    //if( line1.indexOf("<#gval>") >= 0 && line1.indexOf("<#gloop") < 0 )
                                    if (line1.indexOf("<#gval>") >= 0) {
                                        tag = "<#gval>";
                                        temp1 = replace(tag, line1, j);
                                    }
                                } else {
                                    j--;
                                    break;
                                }
                                j++;
                                temp2 += temp1;
//   					output+=temp1;

                            } // end of while
                            output += temp2;

                        }   // end of if
                        // above code added to accomodate an inner gloop

                    } // for rcount

                } // end of if

            }//while
            int mycnt, t = 0;

            if (totcount > inccount) {

                String reqstr = req.getQueryString();
                // repreq removes the occurance of second string from the first string(if any)
                reqstr = repreq(reqstr, "inccount");
                reqstr = repreq(reqstr, "rowcount");
                reqstr = repreq(reqstr, "fl");
                reqstr = repreq(reqstr, "sl");

                output += "<center><a href=./genrep?fl=1&code=" + code + "&npl=" + no_page_number_links + "&inccount=" + inccount + ">Search Page</a><br>";

                if (!(no_page_number_links.equals("f"))) {
                    for (mycnt = 1; mycnt <= totcount / inccount; mycnt++) {
                        t = mycnt - 1;
                        counter = (t * inccount) + 1;
                        output += "<a href=./genrep?fl=2&inccount=" + inccount + "&rowcount=" + t * inccount + "&sl=" + counter + "&" + reqstr + ">" + mycnt + "&nbsp;</a>&nbsp;&nbsp;";

                    }

                    if (per > 0) {
                        t = mycnt - 1;
                        counter = (t * inccount) + 1;
                        output += "<a href=./genrep?fl=2&inccount=" + inccount + "&rowcount=" + t * inccount + "&sl=" + counter + "&" + reqstr + ">" + mycnt + "&nbsp;</a>&nbsp;&nbsp;";
                    }
                }

                t = pageno;

                int cntprev = (t - 2) * inccount;
                int cntnext = t * inccount;
                int cntlast = totcount - inccount;

                output += "<br><a href=./genrep?fl=2&inccount=" + inccount + "&rowcount=0" + "&sl=1&" + reqstr + ">First</a>&nbsp;&nbsp;";
                output += "<a href=./genrep?fl=2&inccount=" + inccount + "&rowcount=" + cntprev + "&sl=" + counter + "&" + reqstr + ">Previous &nbsp;</a>&nbsp;&nbsp;";
                output += "<a href=./genrep?fl=2&inccount=" + inccount + "&rowcount=" + cntnext + "&sl=" + counter + "&" + reqstr + ">Next &nbsp;</a>&nbsp;&nbsp;";
                output += "<a href=./genrep?fl=2&inccount=" + inccount + "&rowcount=" + cntlast + "&sl=" + counter + "&" + reqstr + ">Last &nbsp;</a>&nbsp;&nbsp;";

            }
            br.close();
            file_url.close();
            for (int i = 0; i < groupbyvalue.length; i++) {
                groupbyvalue[i] = "";
            }
        } // end of try */
        catch (FileNotFoundException fe) {
            fe.printStackTrace();
            out.println("<center><h2>Check for the file paths</h2></center>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }  // report

    /**
     * *****************************************************************************************
     */
    String repreq(String reqstr, String to_be_removed) {
        int p1 = reqstr.indexOf(to_be_removed);
        int ll = to_be_removed.length() + 1;
        if (p1 >= 0) {
            String ss = reqstr.substring(p1 + ll, reqstr.length());
            int p2 = ss.indexOf("&");
            if (p2 >= 0) {
                reqstr = reqstr.substring(0, p1) + reqstr.substring(p1 + ll + 1 + p2, reqstr.length());
            } else {
                reqstr = reqstr.substring(0, p1);
            }
        }
        return reqstr;
    }    // end of repreq

    /**
     * *****************************************************************************************
     */
    String queryScreen(String textfile, String code, String fl, String tbns) throws Exception {
        String qscr = "";
        try {
            String line = "";
            String form_head = "";
            String datatype[] = new String[200];
            String label[] = new String[200];
            String fld_nam[] = new String[200];
            String master_tab[] = new String[200];
            String disp_nam[] = new String[200];
            String lnk_nam[] = new String[200];
            String ord_nam[] = new String[200];

            FileInputStream file_url = new FileInputStream(textfile);
            BufferedReader br = new BufferedReader(new InputStreamReader(file_url));
            scount = 0;
            while (true) {
                line = br.readLine();
                if (line.equals("*")) {
                    break;
                }
                if (!line.equals("*") && scount == 0) {
                    form_head = line;
                    line = br.readLine();  	
                }

                StringTokenizer st1 = new StringTokenizer(line, ";");
                while (st1.hasMoreTokens()) {
                    datatype[scount] = (String) st1.nextToken();
                    label[scount] = (String) st1.nextToken();
                    fld_nam[scount] = (String) st1.nextToken();
                    if (st1.hasMoreTokens()) {
                        master_tab[scount] = (String) st1.nextToken();
                    }
                    if (st1.hasMoreTokens()) {
                        disp_nam[scount] = (String) st1.nextToken();
                    }
                    if (st1.hasMoreTokens()) {
                        lnk_nam[scount] = (String) st1.nextToken();
                    }
                    if (st1.hasMoreTokens()) {
                        ord_nam[scount] = (String) st1.nextToken();
                    }
                    if (fld_nam[scount].indexOf("*") >= 0 && tbns != null) {
                        int ind = fld_nam[scount].indexOf("*");
                        fld_nam[scount] = fld_nam[scount].substring(0, ind) + tbns + fld_nam[scount].substring(ind + 1, fld_nam[scount].length());
                    }
                    if (master_tab[scount] != null && master_tab[scount].indexOf("*") >= 0 && tbns != null) {
                        int ind = master_tab[scount].indexOf("*");
                        master_tab[scount] = master_tab[scount].substring(0, ind) + tbns + master_tab[scount].substring(ind + 1, master_tab[scount].length());
                    }

                }	// while
                scount++;
            } // d

            br.close();
            file_url.close();

            if (bgcolor == null || bgcolor.equals("")) {
                bgcolor = "white";
            }

            String disp_val = "", lnk_val = "", q = "";
            qscr = "<html><head>"
                    + "<script src=\"../script/caldatetime.js\" language=\"javascript\"></script>"
                    + "<script> window.history.forward(1)</script>"
                    + "</head><body bgcolor=" + bgcolor + "><center>";
            qscr += "<h2>" + form_head + "</h2>";   // heading
            if (target_frame != null && !(target_frame.equals(""))) {
                targetstr = "target=" + target_frame;
            }
            qscr += "\n<form name=\"frm\" action= ./genrep " + targetstr + " >\n";
            qscr += "\n<input type=hidden name=fl value='2'>\n";
            qscr += "\n<input type=hidden name=code value='" + code + "'>\n";
            qscr += "\n<input type=hidden name=scount value='" + scount + "'>\n";
            qscr += "\n<input type=hidden name=inccount value='" + inccount + "'>\n";
            qscr += "\n<input type=hidden name=npl value='" + no_page_number_links + "'>\n";
            if (tbns != null) {
                qscr += "\n<input type=hidden name=tbns value='" + tbns + "'>\n";
            }
            qscr += "\n<table valign=top align=center>";

            for (int i = 0; i < scount; i++) {
                qscr += "\n<input type=hidden name='f" + i + "' value='" + fld_nam[i] + "'>\n";
                qscr += "\n<input type=hidden name='t" + i + "' value='" + datatype[i] + "'>\n";
                qscr += "<tr bgcolor=#99CCFF>";

                qscr += "<td>\n" + label[i] + "</td> ";

                if (datatype[i].equals("d")) {
                    qscr += "<td colspan=2 align=left><input type=text name='v1" + i + "'>";
                    qscr += " <img src=\"../Images/CALENDAR.GIF\" width=\"32\" height=\"19\" alt=\"click to get Calendar\" "
                            + "style=\"cursor:hand;\" onclick=\"popUpCalendar(this, frm.v1" + i + ",'dd-mm-yyyy',-16,-26)\"  > ";
                    qscr += "</td>";
                }
                if (datatype[i].equals("dr")) {
                    qscr += " <td> from  " + "<input type=text name='v1" + i + "'>"
                            + " <img src=\"../Images/CALENDAR.GIF\" width=\"32\" height=\"19\" alt=\"click to get Calendar\" "
                            + "style=\"cursor:hand;\" onclick=\"popUpCalendar(this, frm.v1" + i + ",'dd-mm-yyyy',-16,-26)\"  > "
                            + "  to  " + "<input type=text name='v2" + i + "'>"
                            + " <img src=\"../Images/CALENDAR.GIF\" width=\"32\" height=\"19\" alt=\"click to get Calendar\" "
                            + "style=\"cursor:hand;\" onclick=\"popUpCalendar(this, frm.v2" + i + ",'dd-mm-yyyy',-16,-26)\"  ></td> ";
                }

                if (datatype[i].equals("nr") || datatype[i].equals("dtr")) {
                    qscr += " <td> from  " + "<input type=text name='v1" + i + "'>" + "  to  " + "<input type=text name='v2" + i + "'></td>";
                }
                if (master_tab[i] != null && datatype[i].equals("def")) {
                    String onchangestr = "";
                    if (onchange != null && onchange.equals("y")) {
                        onchangestr = " onchange='javascript:f1.location=\"mail.yahoo.com\";f1.submit()'";
                    }

                    qscr += "<td colspan=2 align=left><select name='v1" + i + "' " + onchangestr + ">";

                    qscr += "<option value=''>    --  select --   </option>";

                    if (disp_nam[i].equals(lnk_nam[i])) {
                        q = "select distinct " + disp_nam[i] + " from " + master_tab[i];
                    } else {
                        q = "select distinct " + disp_nam[i] + "," + lnk_nam[i] + " from " + master_tab[i];
                    }

                    if (!ord_nam[i].equals("")) {
                        q += " order by " + ord_nam[i];
                    }
                    //if(rs!=null) rs.close();
                    rs = st.executeQuery(q);

                    while (rs.next()) {
                        disp_val = rs.getString(1);
                        if (!disp_nam[i].equals(lnk_nam[i])) {
                            lnk_val = rs.getString(2);
                        } else {
                            lnk_val = disp_val;
                        }
                        if (disp_val != null && !disp_val.equals("")) {
                            qscr += "<option value='" + lnk_val + "'>" + disp_val + "</option>\n";
                        }
                    }
                    qscr += "\n</select></td>";
                } // if
                else if (datatype[i].equals("def") || datatype[i].equals("dt") || datatype[i].equals("deflike")) {
                    qscr += "<td colspan=2 align=left><input type=text name='v1" + i + "'></td>";
                }
            }   // end of for all query parameters

// display a submit button
            //  qscr += "\n<tr bgcolor=#9999CC><td colspan=2><center><input type=submit value='Report'> <input type=reset value='Reset'></center></td></table>\n";
//	  	   "<td><a href=\"javascript:history.go(-1)\">BACK</a></td>"+
            qscr += "\n<tr bgcolor=#9999CC><td><center><input type=submit value='Report'> <input type=reset value='Reset'></center></td>"
                    + "<td><a href=\"" + backfilename + "\">BACK</a></td>"
                    + "<td><a href=\"../logout.jsp\">Logout</a></td>"
                    + "</tr></table>\n";
            qscr += "\n</form></body></html>\n";

            screen = false;
            return qscr;

        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
            out.println("<center><h2> check for file paths </h2></center>");
            return qscr;
        } catch (NoSuchElementException ne) {
            ne.printStackTrace();
            out.println("<center><h2> check the interface values in the text file </h2></center>");
            return qscr;
        } catch (Exception e) {
            e.printStackTrace();
            return qscr;
        }
    }     // end of queryScreen

    /**
     * *****************************************************************************************
     */
    String ChangeDateFormat(String date1) {
        String datevalue = "";
        if (date1.length() >= 10) {
            StringTokenizer str1 = new StringTokenizer(date1, "-");
            String day = "", mon = "", year = "";

            while (str1.hasMoreTokens()) {
                day = str1.nextToken();
                mon = str1.nextToken();
                year = str1.nextToken();
            }
            datevalue = year + "-" + mon + "-" + day;
        } else {
            datevalue = date1.substring(6, 10) + "-" + date1.substring(3, 5) + "-" + date1.substring(0, 2) + " " + date1.substring(11, 19);
        }

        return datevalue;
    } //String ChangeDateFormat(String date1)

    /**
     * ************************************************************************************
     */
    String ChangeDateFormattotimestamp(String date1) {
        String datevalue = "";
        if (date1.length() >= 10) {
            StringTokenizer str1 = new StringTokenizer(date1, "-");
            String day = "", mon = "", year = "";

            while (str1.hasMoreTokens()) {
                day = str1.nextToken();
                mon = str1.nextToken();
                year = str1.nextToken();
            }
            datevalue = year + mon + day;
        } else {
            datevalue = date1.substring(6, 10) + date1.substring(3, 5) + date1.substring(0, 2) + date1.substring(11, 19);
        }

        return datevalue;
    } //String ChangeDateFormattotimestamp(String date1)

    /**
     * ************************************************************************************
     */
    public String WhereCondition() {
        String wh = "";
        for (i = 0; i < scount; i++) {
            if (v1[i] != null) {
                if (v1[i].length() > 0) {
                    if (datatype[i].equals("dr") || datatype[i].equals("d")) {
                        v1[i] = ChangeDateFormat(v1[i]);
                        if (datatype[i].equals("dr")) {
                            v2[i] = ChangeDateFormat(v2[i]);
                        }
                    }
                    if (datatype[i].equals("dt")) {
                        v1[i] = ChangeDateFormattotimestamp(v1[i]);
                        wh += f[i] + " like '" + v1[i] + "%' and ";
                    }
                    if (datatype[i].equals("dtr")) {
                        v1[i] = ChangeDateFormattotimestamp(v1[i]);
                        v2[i] = ChangeDateFormattotimestamp(v2[i]);

                        if (v1[i] != null) {
                            wh += f[i] + " >= '" + v1[i] + "'";
                        }

                        if (v2[i] != null) {
                            if (wh.length() > 3) {
                                wh += " and ";
                            }
                            wh += f[i] + " <= '" + v2[i] + "' and ";
                        }
//				wh += f[i] + " >= '" + v1[i] +"' and "+f[i] + " <= '" + v2[i] +"' and " ;
                    }

                    if (datatype[i].equals("nr") || datatype[i].equals("dr"))//nr-- numeric range ; dr-- date range ;
                    {
                        if (v1[i] != null) {
                            wh += f[i] + " >= '" + v1[i] + "'";
                        }

                        if (v2[i] != null && !v2[i].equals("")) {
                            if (wh.length() > 3) {
                                wh += " and ";
                            }
                            wh += f[i] + " <= '" + v2[i] + "' and ";
                        }
                        //				wh += f[i] + " >= '" + v1[i] +"' and "+f[i] + " <= '" + v2[i] +"' and " ;
                    }
                    if (datatype[i].equals("def") || datatype[i].equals("d")) {
                        wh += f[i] + "='" + v1[i] + "' and ";
                    }
                    if (datatype[i].equals("deflike")) {
                        wh += f[i] + " like '%" + v1[i] + "%' and ";
                    }
                }  // if
            }//if
        }//for
        return wh;
    }  // WhereCondition

    /**
     * ***************************************************************************************************
     */
    public String Prepare_search_criteria(String label1[], String v1[]) {
        String s1 = "";   ///= "Search Criteria : ";
        int i = 0;

        while (i < scount) {
            if (v1[i] != null && (!v1[i].equals("null") && !(v1[i].trim().equals("")))) {
                s1 += label1[i] + " : <font color=blue>" + v1[i] + "</font><br>";
                if (v2[i] != null) {
                    s1 += " - <font color=blue>" + v2[i] + "</font>";
                }
            }

            i++;

        }
        return s1;
    }  // end of Prepare_search_criteria

    /**
     * ***************************************************************************************************
     */
    public String xQueryBuilder(String pk, String htmlfile, HttpServletRequest req) throws Exception {

        no_page_number_links = isBlank(req.getParameter("npl"));
        target_frame = isBlank(req.getParameter("tg"));
        bgcolor = isBlank(req.getParameter("bg"));
        onchange = isBlank(req.getParameter("oc"));

        if (no_page_number_links == null || no_page_number_links.equals("")) {
            no_page_number_links = "t";
        }
        code = isBlank(req.getParameter("code"));
        fl = isBlank(req.getParameter("fl"));

        if (fl == null || !MyUtil.containsOnlyNumbers(fl)) {
            fl = "1";
        }
        String slno = isBlank(req.getParameter("sl"));
        String tbns = isBlank(req.getParameter("tbns"));
        try {
            if (slno == null || !MyUtil.containsOnlyNumbers(slno)) {
                counter = 1;
            } // if(req.getParameter("sl")!=null)   // for <#gslno> for next pages
            //	counter=Integer.parseInt(req.getParameter("sl"));
            else {
                counter = Integer.parseInt(slno);
            }
        } catch (Exception e) {
            counter = 1;
        }

        try {
            String sscount = isBlank(req.getParameter("scount"));
//			  if(isBlank(req.getParameter("scount"))!=null || !MyUti.containsOnlyNumbers()
            if (sscount != null && MyUtil.containsOnlyNumbers(sscount)) {
                scount = Integer.parseInt(sscount);
            } else {
                scount = 0;
            }
        } catch (Exception e) {
            scount = 0;
        }
        try {
            String rrcount = isBlank(req.getParameter("rowcount"));
            if (rrcount == null || !MyUtil.containsOnlyNumbers(rrcount)) {
                rowcount = 0;
            } else {
                rowcount = Integer.parseInt(rrcount);
            }
        } catch (Exception e) {
            rowcount = 0;
        }

        try {
            String iicount = req.getParameter("inccount");
            if (iicount == null || !MyUtil.containsOnlyNumbers(iicount)) {
                inccount = 100;
            } else {
                inccount = Integer.parseInt(iicount);
            }
        } catch (Exception e) {
            inccount = 0;
        }

        try {

            if (fl.equals("2")) {
                for (i = 0; i < scount; i++) {
                    datatype[i] = isBlank(req.getParameter("t" + i)).trim();
                    f[i] = isBlank(req.getParameter("f" + i)).trim();
                    v1[i] = isBlank(req.getParameter("v1" + i));
                    v2[i] = isBlank(req.getParameter("v2" + i));
                }//for
                //String s = QueryBuilder(textfile, htmlfile, req);
                //ut.println(s);
            } else if (fl.equals("1")) {
            } else {
                out.println("Invalid Options");
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        String x = "";
        if (v1[0] != null && datatype[0] != null) {
            x += WhereCondition();
        }
        if (true) {
            return x;
        }

        pk = req.getParameter("pk");
        String sval = "";
        String temp = "", temp1 = "";
        int dcount = 0, lab_cou = 0;
        try {
            String line = "";
            String label[] = new String[20];
            String qq1 = "select a.repid,a.line as fline ,b.line as sline,b.heading as heading from report.report_interface a , report.report_report b where a.repid=b.repid and a.repid='" + pk + "'";
            Connection conn = DBC.DBConnection.getConnection(req);
            st = conn.createStatement();
            Statement statement = conn.createStatement();
            ResultSet rslt = statement.executeQuery(qq1);

            if (rslt.next()) {
                line = rslt.getString("sline");
                line = line.replaceAll("[\\t\\n\\r]", "").trim();
                st5 = new StringTokenizer(line, "$~^");
                int mmmm = 0;
                while (st5.hasMoreTokens()) {

                    String checking = st5.nextToken();
                    mmmm++;

                    if (checking.trim().equals("*")) {
                        break;
                    } else {

                        StringTokenizer st1 = new StringTokenizer(checking, ";");
                        if (st1.hasMoreTokens()) {
                            st1.nextToken();
                            label[lab_cou] = (String) st1.nextToken();
                            temp1 = temp1 + label[lab_cou];
                        }
                        lab_cou++;
                    }
                }
                search_criteria = Prepare_search_criteria(label, v1);

                String fieldnames = st5.nextToken();
                String tablenames = "", joinconditions = "", wherecondition = "", orderby = "", groupby = "", aggregate = "";
                String fld[] = new String[200];
                String dmast[] = new String[200];
                String dfname[] = new String[200];
                String dlname[] = new String[200];
                String dsubord[] = new String[200];
                String dsub1[] = new String[200];
                String dsub2[] = new String[200];
                String dsubsub1[] = new String[200];
                String dsubsub2[] = new String[200];
                String dsubsubsub1[] = new String[200];
                String dsubsubsub2[] = new String[200];
                String dmast2[] = new String[200];
                String ddisp2[] = new String[200];
                String dlink2[] = new String[200];
                Vector bfld = new Vector();
                String pkf = "";
                String pkfnum = "";

                st1 = new StringTokenizer(fieldnames, ",");
                while (st1.hasMoreTokens()) {

                    //dcount is the count of display fields
                    fld[dcount] = (String) st1.nextToken();
                    //temp=temp+fld[dcount]+"<br><br>";
                    //changing date_format '!' mark to ',' according to mysql format
                    if (fld[dcount].indexOf("!") >= 0) {
                        fld[dcount] = fld[dcount].replace('!', ',');
                    }
                    //temp=temp+fld[dcount]+"<br><br>";

                    int distinct = 0;   // to remove *  for the first field having distinct before it
                    if (fld[dcount].length() > 8 && fld[dcount].substring(0, 8).equals("distinct")) {
                        fld[dcount] = fld[dcount].substring(8, fld[dcount].length());
                        fld[dcount] = fld[dcount].trim();
                        distinct = 1;
                    }

                    if (fld[dcount].substring(0, 1).equals("*")) {
                        fld[dcount] = fld[dcount].substring(1, fld[dcount].length());
                        gfld[dcount] = true;
                    }
                    if (distinct == 1) {
                        fld[dcount] = "distinct " + fld[dcount];
                    }
                    if (fld[dcount].indexOf("#") > 0) {
                        StringTokenizer st2 = new StringTokenizer(fld[dcount], "#");
                        while (st2.hasMoreTokens()) {
                            fld[dcount] = (String) st2.nextToken();
                            dmast[dcount] = (String) st2.nextToken();
                            dfname[dcount] = (String) st2.nextToken();
                            dlname[dcount] = (String) st2.nextToken();
                            if (st2.hasMoreTokens()) // to show second level of itemname
                            {
                                dsub1[dcount] = (String) st2.nextToken();
                                try {
                                    dsub2[dcount] = (String) st2.nextToken();
                                } catch (Exception e) {
                                    out.println("Please Check in text file # entries to get sub menu values");
                                }
                            }
                            if (st2.hasMoreTokens()) // to show third level of itemname
                            {
                                dsubsub1[dcount] = (String) st2.nextToken();
                                try {
                                    dsubsub2[dcount] = (String) st2.nextToken();
                                } catch (Exception e) {
                                    out.println("Please Check in text file # entries to get sub sub menu values");
                                }
                            }
                            if (st2.hasMoreTokens()) // to show 4th level of itemname
                            {
                                dsubsubsub1[dcount] = (String) st2.nextToken();
                                try {
                                    dsubsubsub2[dcount] = (String) st2.nextToken();
                                } catch (Exception e) {
                                    out.println("Please Check in text file # entries to get sub sub sub menu values");
                                }
                            }
                            if (dlname[dcount].indexOf("@") > 0) {
                                StringTokenizer st3 = new StringTokenizer(dlname[dcount], "@");

                                if (st3.hasMoreTokens()) // to show third level of itemname
                                {
                                    dlname[dcount] = (String) st3.nextToken(); //first token is the dlname
                                    dsubord[dcount] = (String) st3.nextToken(); //order by for previous level
                                    dmast2[dcount] = (String) st3.nextToken();
                                    ddisp2[dcount] = (String) st3.nextToken();
                                    dlink2[dcount] = (String) st3.nextToken();
                                }
                            }
                        }//while loop for # tokenizer
                    }//tokenize with #
                    dcount++;
                }   // while
                //st5.nextToken();
                //st5.nextToken();
                tablenames = st5.nextToken().trim();
                tablenames = tablenames.replaceAll("[*]", "").trim();
                tbns = isBlank(req.getParameter("tbns"));
                if (tbns != null) {

                    if (tablenames.indexOf("*") >= 0) {
                        int ind = tablenames.indexOf("*");
                        //tablenames=tablenames.replaceAll("*",tbns);
                        tablenames = tablenames.substring(0, ind) + tbns + tablenames.substring(ind + 1, tablenames.length());
                    }
                }

                joinconditions = st5.nextToken().trim();
                joinconditions = joinconditions.replaceAll("[*]", "").trim();
                //st5.nextToken();

                wherecondition = st5.nextToken().trim();
                wherecondition = wherecondition.replaceAll("[*]", "").trim();
                //st5.nextToken();

                orderby = st5.nextToken().trim();
                orderby = orderby.replaceAll("[*]", "").trim();
                //st5.nextToken();

                groupby = st5.nextToken().trim();
                groupby = groupby.replaceAll("[*]", "").trim();
                //st5.nextToken();

                aggregate = st5.nextToken().trim();
                aggregate = aggregate.replaceAll("[*]", "").trim();
                //st5.nextToken();
                //temp = aggregate + groupby + orderby + wherecondition + joinconditions;

                temp = st5.nextToken();
                temp = temp.replaceAll("[*]", "").trim();
                if (temp == null) {
                    temp = " ";
                }
                if (temp.indexOf(",") >= 0) {
                    StringTokenizer str = new StringTokenizer(temp, ",");
                    while (str.hasMoreTokens()) {
                        bfld.addElement(str.nextToken());
                    }

                } else {
                    bfld.addElement(temp.trim());
                }

                temp = st5.nextToken();
                temp = temp.replaceAll("[*]", "").trim();
                // next line contains the pk field to retrive the exact record
                // finished reading from  textfile
                if (temp == null) {
                    temp = " ";
                }
                if (temp.indexOf(",") >= 0) {
                    StringTokenizer str = new StringTokenizer(temp, ",");
                    if (str.hasMoreTokens()) {
                        pkf = str.nextToken();
                    }
                    if (str.hasMoreTokens()) {
                        pkfnum = str.nextToken();
                    }
//			{   String pktmp=str.nextToken(); pkfnum=Integer.parseInt(pktmp.trim());  }
                }
                String qstr = "", q1 = "", q = "", qcount = "";

                qstr = " from " + tablenames;
                String wc = "";
                if (!joinconditions.equals(null) && !joinconditions.equals("")) {
                    wc += joinconditions + " and ";
                }
                temp = qstr;

                if (v1[0] != null && datatype[0] != null) {
                    wc += WhereCondition();
                }
                if (!wherecondition.equals(null)) {
                    int ind = wherecondition.indexOf("?");
                    if (ind > 0) {
                        StringTokenizer stk = new StringTokenizer(wherecondition, "?");
                        int cnttok = stk.countTokens();

                        int ii = 0;
                        while (stk.hasMoreTokens()) {
                            String ss = stk.nextToken();
                            // if (!v1[ii].equals(null))
                            if (!(v1[ii] == null) && ii < cnttok - 1) {
                                wc += ss + v1[ii];
                            } else {
                                wc += ss;
                            }
                            ii++;
                        }
                        /*     if(ind>0)
                         {
                         wc+=wherecondition.substring(0,ind)+v1[1]+wherecondition.substring(ind+1,wherecondition.length());
                         }   */
                    }
                }
                if (wc.length() != 0) {
                    if (wc.substring(wc.length() - 4, wc.length()).equals("and ")) {
                        wc = wc.substring(0, wc.length() - 4);//removing  last and
                    }
                }
                if (!wc.trim().equals("")) {
                    qstr += " where " + wc;
                }

                if (!orderby.equals("")) {
                    qstr += " order by " + orderby;
                }

                if (!groupby.equals("")) {
                    qstr += " group by " + groupby;
                }

                //  q="select "+fieldnames + qstr ;
                q = "select ";
                for (int ii = 0; ii < dcount; ii++) {
                    q += fld[ii] + ",";
                }
                q = q.substring(0, q.length() - 1);   // remove last comma
                q += qstr;

                qcount = "select count(*) " + qstr;

                if (!aggregate.equals("")) {
                    q1 = "select " + aggregate + qstr;
                }

                //st.close();
                //st=conn.createStatement();
                try {
                    // ps = conn.prepareStatement(qcount);
                    //ResultSet rscount = ps.executeQuery();

                    ps = conn.prepareStatement(q);
                    rs = ps.executeQuery();

                    ResultSetMetaData rsmd = rs.getMetaData();
                    field_count = rsmd.getColumnCount();

                    /* while (rscount.next()) {
                     totcount = rscount.getInt(1);
                     }*/
                    rcount = 0;

                    int cc = rowcount + inccount;
                    if (rowcount != 0) {
                        rs.absolute(rowcount);
                    }

                    while (rs.next()) {
                        if (rowcount >= cc) {
                            return q;
                        }
                        for (int i = 0; i < field_count; i++) {
                            fvalue[rcount][i] = rs.getString(i + 1);
                            // code added to get name from a master table
                            if (fvalue[rcount][i] == null) {
                                fvalue[rcount][i] = "&nbsp- ";
                            }
                            if (dmast[i] != null) {
                                String s = "select " + dfname[i] + "  from " + dmast[i] + " where " + dlname[i] + " = '" + fvalue[rcount][i] + "'";
                                if (dsub1[i] != null) {
                                    s += " and " + dsub1[i] + " = '" + rs.getString(Integer.parseInt(dsub2[i])) + "'";
                                }
                                if (dsubsub1[i] != null) {
                                    s += " and " + dsubsub1[i] + " = '" + rs.getString(Integer.parseInt(dsubsub2[i])) + "'";
                                } //out.println("sub sub theme "+s+"<br>");  }
                                if (dsubsubsub1[i] != null) {
                                    s += " and " + dsubsubsub1[i] + " = '" + rs.getString(Integer.parseInt(dsubsubsub2[i])) + "'";
                                } //out.println("sub sub theme "+s+"<br>");  }
                                if (dsubord[i] != null) {
                                    s += " order by  " + dsubord[i];
                                }
/////out.println("%%%%%%%%%%get from master table "+s);

                                stat1 = conn.createStatement();
                                rs1 = stat1.executeQuery(s);
                                String ftmp3 = "", ftmp1 = "";
                                while (rs1.next()) {
//			out.println("<br> ******* ");
                                    if (ftmp1.equals("")) {
                                        ftmp1 = rs1.getString(1);
                                    } else {
                                        ftmp1 = ftmp1 + ", " + rs1.getString(1);
                                    }
                                    String ftmp2 = rs1.getString(1);
                                    //	fvalue[rcount][i] = rs1.getString(1);
                                    if (dmast2[i] != null) {

                                        String s1 = "select " + ddisp2[i] + " from " + dmast2[i] + " where " + dlink2[i] + " ='" + ftmp2 + "'";

                                        stat2 = conn.createStatement();
                                        rs2 = stat2.executeQuery(s1);
//			out.println("<br>2222222222 master table "+s1);
                                        while (rs2.next()) {
                                            if (rs2.getString(1) != null) {
                                                if (ftmp3.equals("")) {
                                                    ftmp3 = rs2.getString(1);
                                                } else {
                                                    ftmp3 = ftmp3 + ", " + rs2.getString(1);
                                                }

                                            }
                                        }
                                    }
                                }
                                if (ftmp3.equals("")) {
                                    fvalue[rcount][i] = ftmp1;
                                } else {
                                    fvalue[rcount][i] = ftmp3;
                                }

                            }
                        }
                        rcount++;
                        rowcount++;
                    }//while

                    if (!aggregate.equals("")) {
                        st.close();
                        st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        rs1 = st.executeQuery(q1);
                        rsmd = rs1.getMetaData();
                        int field_count_a = rsmd.getColumnCount();

                        rs1.next();

                        for (int i = 0; i < field_count_a; i++) {
                            avalue[i] = rs1.getString(i + 1);
                        }

                    }//if(aggregate.....

                    //sval = Report(htmlfile, tablenames, bfld, wc, pkf, pkfnum, req);
                } catch (Exception e) {
                    out.println("Invalid Input Data");
                }

                // }//read line
            }

        } catch (Exception ex) {
            sval = sval + ex;
        }
        return sval;
    }

    public String QueryBuilder(String textfile, String htmlfile, HttpServletRequest req) throws Exception {
        String sval = "";
        int dcount = 0, lab_cou = 0;

        try {
            FileInputStream file_url = new FileInputStream(textfile);
            BufferedReader br = new BufferedReader(new InputStreamReader(file_url));
            String line = "";
            String label[] = new String[20];
            int ignore_heading_line = 1;

            while (true) {
                line = br.readLine().trim();//heading
                if (line.equals("*")) {
                    break;
                } else {
                    if (ignore_heading_line == 1) {
                        line = br.readLine().trim();
                        ignore_heading_line = 0;
                    }
                    st1 = new StringTokenizer(line, ";");
                    if (st1.hasMoreTokens()) {
                        st1.nextToken();
                        if (st1.hasMoreTokens()) {
                            label[lab_cou] = (String) st1.nextToken();
                        }
                        lab_cou++;

                    }

                }
            }

//            search_criteria = Prepare_search_criteria(label);
            String fieldnames = br.readLine().trim();
            String tablenames = "", joinconditions = "", wherecondition = "", orderby = "", groupby = "", aggregate = "";
            String fld[] = new String[200];
            String dmast[] = new String[200];
            String dfname[] = new String[200];
            String dlname[] = new String[200];
            String dsubord[] = new String[200];
            String dsub1[] = new String[200];
            String dsub2[] = new String[200];
            String dsubsub1[] = new String[200];
            String dsubsub2[] = new String[200];
            String dsubsubsub1[] = new String[200];
            String dsubsubsub2[] = new String[200];
            String dmast2[] = new String[200];
            String ddisp2[] = new String[200];
            String dlink2[] = new String[200];
            Vector bfld = new Vector();
            String pkf = "";
            String pkfnum = "";

            // code to get the master table values
            // eg: ename,dcode#distnames#distname#distcode,qual,basic etc
            st1 = new StringTokenizer(fieldnames, ",");

            while (st1.hasMoreTokens()) {
                //dcount is the count of display fields

                fld[dcount] = (String) st1.nextToken();

//changing date_format '!' mark to ',' according to mysql format
                if (fld[dcount].indexOf("!") >= 0) {
                    fld[dcount] = fld[dcount].replace('!', ',');
                }

                int distinct = 0;   // to remove *  for the first field having distinct before it
                if (fld[dcount].length() > 8 && fld[dcount].substring(0, 8).equals("distinct")) {
                    fld[dcount] = fld[dcount].substring(8, fld[dcount].length());
                    fld[dcount] = fld[dcount].trim();
                    distinct = 1;
                }

                if (fld[dcount].substring(0, 1).equals("*")) {
                    fld[dcount] = fld[dcount].substring(1, fld[dcount].length());
                    gfld[dcount] = true;
                }
                if (distinct == 1) {
                    fld[dcount] = "distinct " + fld[dcount];
                }

                if (fld[dcount].indexOf("#") > 0) {
                    StringTokenizer st2 = new StringTokenizer(fld[dcount], "#");
                    while (st2.hasMoreTokens()) {
                        fld[dcount] = (String) st2.nextToken();
                        dmast[dcount] = (String) st2.nextToken();
                        dfname[dcount] = (String) st2.nextToken();
                        dlname[dcount] = (String) st2.nextToken();
                        if (st2.hasMoreTokens()) // to show second level of itemname
                        {
                            dsub1[dcount] = (String) st2.nextToken();
                            try {
                                dsub2[dcount] = (String) st2.nextToken();
                            } catch (Exception e) {
                                out.println("Please Check in text file # entries to get sub menu values");
                            }
                        }
                        if (st2.hasMoreTokens()) // to show third level of itemname
                        {
                            dsubsub1[dcount] = (String) st2.nextToken();
                            try {
                                dsubsub2[dcount] = (String) st2.nextToken();
                            } catch (Exception e) {
                                out.println("Please Check in text file # entries to get sub sub menu values");
                            }
                        }
                        if (st2.hasMoreTokens()) // to show 4th level of itemname
                        {
                            dsubsubsub1[dcount] = (String) st2.nextToken();
                            try {
                                dsubsubsub2[dcount] = (String) st2.nextToken();
                            } catch (Exception e) {
                                out.println("Please Check in text file # entries to get sub sub sub menu values");
                            }
                        }
                        if (dlname[dcount].indexOf("@") > 0) {
                            StringTokenizer st3 = new StringTokenizer(dlname[dcount], "@");

                            if (st3.hasMoreTokens()) // to show third level of itemname
                            {
                                dlname[dcount] = (String) st3.nextToken(); //first token is the dlname
                                dsubord[dcount] = (String) st3.nextToken(); //order by for previous level
                                dmast2[dcount] = (String) st3.nextToken();
                                ddisp2[dcount] = (String) st3.nextToken();
                                dlink2[dcount] = (String) st3.nextToken();
                            }
                        }
                    }
                }
                dcount++;
            }   // while

            br.readLine();
            tablenames = br.readLine().trim();
            br.readLine();
            String tbns = isBlank(req.getParameter("tbns"));
            if (tbns != null) {

                if (tablenames.indexOf("*") >= 0) {
                    int ind = tablenames.indexOf("*");
                    //tablenames=tablenames.replaceAll("*",tbns);
                    tablenames = tablenames.substring(0, ind) + tbns + tablenames.substring(ind + 1, tablenames.length());
                }
            }

            joinconditions = br.readLine().trim();
            br.readLine();

            wherecondition = br.readLine().trim();
            br.readLine();

            orderby = br.readLine().trim();
            br.readLine();

            groupby = br.readLine().trim();
            br.readLine();

            aggregate = br.readLine().trim();
            br.readLine();

            String temp = br.readLine();
            if (temp == null) {
                temp = " ";
            }
            if (temp.indexOf(",") >= 0) {
                StringTokenizer str = new StringTokenizer(temp, ",");
                while (str.hasMoreTokens()) {
                    bfld.addElement(str.nextToken());
                }

            } else {
                bfld.addElement(temp.trim());
            }

            temp = br.readLine();  // next line contains the pk field to retrive the exact record
            br.close();   // finished reading from  textfile

            if (temp == null) {
                temp = " ";
            }
            if (temp.indexOf(",") >= 0) {
                StringTokenizer str = new StringTokenizer(temp, ",");
                if (str.hasMoreTokens()) {
                    pkf = str.nextToken();
                }
                if (str.hasMoreTokens()) {
                    pkfnum = str.nextToken();
                }
//			{   String pktmp=str.nextToken(); pkfnum=Integer.parseInt(pktmp.trim());  }
            }

            String qstr = "", q1 = "", q = "", qcount = "";

            qstr = " from " + tablenames;
            String wc = "";
            if (!joinconditions.equals(null) && !joinconditions.equals("")) {
                wc += joinconditions + " and ";
            }

            if (v1[0] != null && datatype[0] != null) {
                wc += WhereCondition();
            }
            if (!wherecondition.equals(null)) {
                int ind = wherecondition.indexOf("?");
                if (ind > 0) {
                    StringTokenizer stk = new StringTokenizer(wherecondition, "?");
                    int cnttok = stk.countTokens();

                    int ii = 0;
                    while (stk.hasMoreTokens()) {
                        String ss = stk.nextToken();
                        // if (!v1[ii].equals(null))
                        if (!(v1[ii] == null) && ii < cnttok - 1) {
                            wc += ss + v1[ii];
                        } else {
                            wc += ss;
                        }
                        ii++;
                    }
                    /*     if(ind>0)
                     {
                     wc+=wherecondition.substring(0,ind)+v1[1]+wherecondition.substring(ind+1,wherecondition.length());
                     }   */
                }
            }
            if (wc.length() != 0) {
                if (wc.substring(wc.length() - 4, wc.length()).equals("and ")) {
                    wc = wc.substring(0, wc.length() - 4);//removing  last and
                }
            }
            if (!wc.trim().equals("")) {
                qstr += " where " + wc;
            }

            if (!orderby.equals("")) {
                qstr += " order by " + orderby;
            }

            if (!groupby.equals("")) {
                qstr += " group by " + groupby;
            }

            //  q="select "+fieldnames + qstr ;
            q = "select ";
            for (int ii = 0; ii < dcount; ii++) {
                q += fld[ii] + ",";
            }
            q = q.substring(0, q.length() - 1);   // remove last comma
            q += qstr;

            qcount = "select count(*) " + qstr;

            if (!aggregate.equals("")) {
                q1 = "select " + aggregate + qstr;
            }

            //st.close();
            //st=conn.createStatement();
// out.println("Query is "+q);
            //rs=st.executeQuery(q);
            try {
                ps = conn.prepareStatement(qcount);
                ResultSet rscount = ps.executeQuery();

                ps = conn.prepareStatement(q);
                rs = ps.executeQuery();

                ResultSetMetaData rsmd = rs.getMetaData();
                field_count = rsmd.getColumnCount();

                while (rscount.next()) {
                    totcount = rscount.getInt(1);
                }
                //totcount++;
                //rs.beforeFirst();
                rcount = 0;

                int cc = rowcount + inccount;

                if (rowcount != 0) {
                    rs.absolute(rowcount);
                }

                while (rs.next()) {
                    if (rowcount >= cc) {
                        break;
                    }

                    for (int i = 0; i < field_count; i++) {
//				   out.println("8888 "+i+" "+rcount);
                        fvalue[rcount][i] = rs.getString(i + 1);
// code added to get name from a master table
                        if (fvalue[rcount][i] == null) {
                            fvalue[rcount][i] = "&nbsp- ";
                        }
                        if (dmast[i] != null) {
                            String s = "select " + dfname[i] + "  from " + dmast[i] + " where " + dlname[i] + " = '" + fvalue[rcount][i] + "'";
//				out.println("fvalue "+fvalue[rcount][i]);
                            if (dsub1[i] != null) {
                                s += " and " + dsub1[i] + " = '" + rs.getString(Integer.parseInt(dsub2[i])) + "'";
                            }
                            if (dsubsub1[i] != null) {
                                s += " and " + dsubsub1[i] + " = '" + rs.getString(Integer.parseInt(dsubsub2[i])) + "'";
                            } //out.println("sub sub theme "+s+"<br>");  }
                            if (dsubsubsub1[i] != null) {
                                s += " and " + dsubsubsub1[i] + " = '" + rs.getString(Integer.parseInt(dsubsubsub2[i])) + "'";
                            } //out.println("sub sub theme "+s+"<br>");  }
                            if (dsubord[i] != null) {
                                s += " order by  " + dsubord[i];
                            }
/////out.println("%%%%%%%%%%get from master table "+s);

                            stat1 = conn.createStatement();
                            rs1 = stat1.executeQuery(s);
                            String ftmp3 = "", ftmp1 = "";
                            while (rs1.next()) {
//			out.println("<br> ******* ");
                                if (ftmp1.equals("")) {
                                    ftmp1 = rs1.getString(1);
                                } else {
                                    ftmp1 = ftmp1 + ", " + rs1.getString(1);
                                }
                                String ftmp2 = rs1.getString(1);
                                //	fvalue[rcount][i] = rs1.getString(1);
                                if (dmast2[i] != null) {

                                    String s1 = "select " + ddisp2[i] + " from " + dmast2[i] + " where " + dlink2[i] + " ='" + ftmp2 + "'";

                                    stat2 = conn.createStatement();
                                    rs2 = stat2.executeQuery(s1);
//			out.println("<br>2222222222 master table "+s1);
                                    while (rs2.next()) {
                                        if (rs2.getString(1) != null) {
                                            if (ftmp3.equals("")) {
                                                ftmp3 = rs2.getString(1);
                                            } else {
                                                ftmp3 = ftmp3 + ", " + rs2.getString(1);
                                            }

                                        }
                                    }
                                }
                            }
                            if (ftmp3.equals("")) {
                                fvalue[rcount][i] = ftmp1;
                            } else {
                                fvalue[rcount][i] = ftmp3;
                            }

                        }
                    }
                    rcount++;
                    rowcount++;
                }//while

                if (!aggregate.equals("")) {
                    st.close();
                    st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    rs1 = st.executeQuery(q1);
                    rsmd = rs1.getMetaData();
                    int field_count_a = rsmd.getColumnCount();

                    rs1.next();

                    for (int i = 0; i < field_count_a; i++) {
                        avalue[i] = rs1.getString(i + 1);
                    }

                }//if(aggregate.....

                sval = Report(htmlfile, tablenames, bfld, wc, pkf, pkfnum, req);
            } catch (Exception e) {
                out.println("Invalid Input Data");
            }

//br.close();
            file_url.close();
        }//try
        catch (FileNotFoundException fe) {
            //fe.printStackTrace();
            out.println("<center><h2> check for file paths </h2></center>");
        } catch (NoSuchElementException ne) {		//	 ne.printStackTrace();
            out.println("<center><h2> check the symbols in textfile </h2></center>");
        } catch (ArrayIndexOutOfBoundsException ae) {
            //	 ae.printStackTrace();
            out.println("<body><Br><br><br><br><h4><center> Too Many Results <br>Please Give More Specific  String to Search <br><a href=\"javascript:history.go(-1)\">BACK</a></h4>");
        } catch (Exception e) {
            //	 e.printStackTrace();
            out.println("query bulider " + e);
        }
        return sval;
    }//end of queryBuilder

    /**
     * *********************************************************************************************
     */
    public String isBlank(String str) {
        str = str == null ? "" : str;
        str = str.replace('\'', ' ');
        str = str.replace('\"', ' ');
        str = str.replace('>', ' ');
        str = str.replace('<', ' ');
        str = str.replace('%', ' ');
        return str;
    }

    /**
     * *********************************************************************************************
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        out = res.getWriter();
        try {

            totcount = 0;
            rowcount = 0;
            inccount = 0;
            screen = true;
            String path = "";
            code = "";
            fl = "";
            String htmlfile = "", textfile = "";
            String q = "";
            String url = "", dbname = "";
            res.setContentType("text/html");
            try {
                ServletContext sc = getServletContext();
                heading = sc.getInitParameter("heading");
                url = sc.getInitParameter("url");
                dbname = sc.getInitParameter("dbname");

                //		 Class.forName(sc.getInitParameter("driver"));
                //		  conn=DriverManager.getConnection(sc.getInitParameter("url"),sc.getInitParameter("user"),sc.getInitParameter("pwd"));
//			  Class.forName("org.gjt.mm.mysql.Driver");
//			  conn = DriverManager.getConnection("jdbc:mysql://localhost/"+dbname,"root","");
                InitialContext ctx = new InitialContext();

                Context envCtx = (Context) ctx.lookup("java:comp/env");
                DataSource ds = (DataSource) envCtx.lookup("jdbc/" + dbname);

                conn = ds.getConnection();

            } catch (Exception e) {
                out.println("Unable to connect to Database..Check Entries in web.xml" + url);
//				   se.printStackTrace();
            }

            //out.println("gggg000"+dbname);
            no_page_number_links = isBlank(req.getParameter("npl"));
            target_frame = isBlank(req.getParameter("tg"));
            bgcolor = isBlank(req.getParameter("bg"));
            onchange = isBlank(req.getParameter("oc"));

            if (no_page_number_links == null || no_page_number_links.equals("")) {
                no_page_number_links = "t";
            }
            code = isBlank(req.getParameter("code"));
            fl = isBlank(req.getParameter("fl"));

            if (fl == null || !MyUtil.containsOnlyNumbers(fl)) {
                fl = "1";
            }
            String slno = isBlank(req.getParameter("sl"));
            String tbns = isBlank(req.getParameter("tbns"));
            try {
                if (slno == null || !MyUtil.containsOnlyNumbers(slno)) {
                    counter = 1;
                } // if(req.getParameter("sl")!=null)   // for <#gslno> for next pages
                //	counter=Integer.parseInt(req.getParameter("sl"));
                else {
                    counter = Integer.parseInt(slno);
                }
            } catch (Exception e) {
                counter = 1;
            }

            q = "select * from genrep where code='" + code + "'";
            String ufldname = "", pfldname = "", logintable = "", loginfilename = "";
            try {
                st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery(q);

                while (rs.next()) {
                    htmlfile = rs.getString("htmlfile");
                    textfile = rs.getString("textfile");
                    try {
                        logintable = rs.getString("logintable");
                        ufldname = rs.getString("ufldname");
                        pfldname = rs.getString("pfldname");

// changes done on 17/05/2012 by cr
                        loginfilename = rs.getString("loginfilename");
                        backfilename = rs.getString("backfilename");

                    } catch (Exception e) {
                        logintable = "";
                        ufldname = "";
                        pfldname = "";
                    }
                }
            } catch (Exception e) {
                out.println("Check for genrep table or file paths");
            }


// get values from session
            HttpSession session = req.getSession(true);

            String sesrole = "", user = "", pwd = "";
            if (session.getAttribute("user") != null) {
                user = session.getAttribute("user").toString();
            }

            if (session.getAttribute("role") != null) {
                sesrole = session.getAttribute("role").toString();
            }
            //out.println("<br>gggyyg"+dbname);

            //checkgenrep(String code, String tab, String ufldname, String pfldname, String sesrole, String user, String pwd, Connection con)  throws SQLException  {
            if (logintable != null && !logintable.equals("")) {
                boolean chklog = MyUtil.checkGenrepSes(code, logintable, ufldname, pfldname, sesrole, user, conn);
                if (!chklog) {
                    res.sendRedirect(loginfilename);
                    /*				  out.println("<center>Access Denied.");
                     return; */
                }
            }

            // out.println("<br>gggx"+dbname);
            try {
                File file = new File(htmlfile);
                if (!file.isAbsolute()) {
                    htmlfile = req.getRealPath(htmlfile);
                }

                file = new File(textfile);
                if (!file.isAbsolute()) {
                    textfile = req.getRealPath(textfile);
                }

                //getQuerydata();
                try {
                    String sscount = isBlank(req.getParameter("scount"));
//			  if(isBlank(req.getParameter("scount"))!=null || !MyUti.containsOnlyNumbers()
                    if (sscount != null && MyUtil.containsOnlyNumbers(sscount)) {
                        scount = Integer.parseInt(sscount);
                    } else {
                        scount = 0;
                    }
                } catch (Exception e) {
                    scount = 0;
                }
                try {
                    String rrcount = isBlank(req.getParameter("rowcount"));
                    if (rrcount == null || !MyUtil.containsOnlyNumbers(rrcount)) {
                        rowcount = 0;
                    } else {
                        rowcount = Integer.parseInt(rrcount);
                    }
                } catch (Exception e) {
                    rowcount = 0;
                }

                try {
                    String iicount = req.getParameter("inccount");
                    if (iicount == null || !MyUtil.containsOnlyNumbers(iicount)) {
                        inccount = 100;
                    } else {
                        inccount = Integer.parseInt(iicount);
                    }
                } catch (Exception e) {
                    inccount = 0;
                }
//out.println("inccount: "+inccount);
            } catch (Exception e) {
                out.println("****Exception 11111111111in Query Screen/ Query Builder***" + e);
                //e.printStackTrace();
            }

            try {

                if (fl.equals("2")) {
                    for (int i = 0; i < scount; i++) {
                        datatype[i] = isBlank(req.getParameter("t" + i));
                        f[i] = isBlank(req.getParameter("f" + i));
                        v1[i] = isBlank(req.getParameter("v1" + i));
                        v2[i] = isBlank(req.getParameter("v2" + i));
                    }//for
                    String s = QueryBuilder(textfile, htmlfile, req);
                    out.println(s);
                } else if (fl.equals("1")) {
                    q = queryScreen(textfile, code, fl, tbns);
                    out.println(q);
                } else {
                    out.println("Invalid Options");
                }

            } catch (Exception e) {
                out.println("****Exception in 1111111111111Query Screen/ Query Builder***");
                //e.printStackTrace();
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (rs2 != null) {
                rs2.close();
                rs2 = null;
            }

            if (rs1 != null) {
                rs1.close();
                rs1 = null;
            }
            if (st != null) {
                st.close();
                st = null;
            }
            if (stat1 != null) {
                stat1.close();
                stat1 = null;
            }
            if (stat2 != null) {
                stat2.close();
                stat2 = null;
            }

            conn.close(); // Return to connection pool
            conn = null;  // Make sure we don't close it twice

        } catch (Exception e) {
            out.println("****Exception in Query Screen/ Query Builder***");
            //e.printStackTrace();
        } finally {
            // Always make sure result sets and statements are closed,
            // and the connection is returned to the pool
            scount = 0;
            field_count = 0;
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    out.println("****SQLException-stat***");
                }
                ps = null;
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    out.println("****SQLException-rs***");
                }
                rs = null;
            }
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException e) {
                    out.println("****SQLException-rs1***");
                }
                rs1 = null;
            }
            if (rs2 != null) {
                try {
                    rs2.close();
                } catch (SQLException e) {
                    out.println("****SQLException-stat***");
                }
                rs2 = null;
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    out.println("****SQLException-st***");
                }
                st = null;
            }
            if (stat1 != null) {
                try {
                    stat1.close();
                } catch (SQLException e) {
                    out.println("****SQLException-stat***");
                }
                stat1 = null;
            }
            if (stat2 != null) {
                try {
                    stat2.close();
                } catch (SQLException e) {
                    out.println("****SQLException-stat***");
                }
                stat2 = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    out.println("****SQLException-conn***");
                }
                conn = null;
            }
        }

    }//post
}//class

