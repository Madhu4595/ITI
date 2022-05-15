package DBC;

import java.io.*;

import java.sql.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import javax.sql.DataSource;

import beans.MyUtil;

public class GenricReports {

    public static String isBlank(String str) {
        str = str == null ? "" : str;
        str = str.replace('\'', ' ');
        str = str.replace('\"', ' ');
        str = str.replace('>', ' ');
        str = str.replace('<', ' ');
        str = str.replace('%', ' ');
        return str;
    }

    static String ChangeDateFormat(String date1) {
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
    static String ChangeDateFormattotimestamp(String date1) {
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

    public static String WhereCondition(String v1[], String v2[], String f[]) {

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

    public static String getwhereCondition(String pk, HttpServletRequest req) {
        String v1[] = new String[300];
        String v2[] = new String[300];
        String f[] = new String[300];

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
                //q = queryScreen(textfile, code, fl, tbns);
                //out.println(q);
            } else {
            }

        } catch (Exception e) {
        }
        String x = "";
        if (v1[0] != null && datatype[0] != null) {
            x += WhereCondition(v1, v2, f);
        }
        return x;

    }
}//class

