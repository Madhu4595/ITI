/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package callleter;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author GMPRSC
 */
public class CallOne extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    @SuppressWarnings("CallToThreadDumpStack")
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        CallOneForm cof = (CallOneForm) form;
        String from = cof.getFrom();
        String to = cof.getTo();
        Calendar calendar = Calendar.getInstance();
        String dt = calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
        String dt1 = calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(calendar.YEAR);

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter docWriter = PdfWriter.getInstance(document, baos);
        HttpSession ses = request.getSession();
        document.open();
        String dist_code = "" + ses.getAttribute("ins_code");
        String dist_name = beans.MyUtil.getOptionValue("dist_mst", "dist_code", "dist_name", "" + ses.getAttribute("ins_code"), "statecode", "28", DBC.DBConnection.getConnection(request));

        String qual = cof.getQual();
        String qualif = "";
        if (qual.equals("bssc")) {
            qualif = " ssc_passed=false and ";
        } else if (qual.equals("ssc")) {
            qualif = " ssc_passed=true and ";
        } else if (qual.equals("assc")) {
            qualif = " ssc_passed=true and inter_passed=true and ";
        }

        String sql = "select * from merit_report where " + qualif + " dist_code='" + dist_code + "' rank>=" + from + " and rank<=" + to;
        Connection con = DBC.DBConnection.getConnection(request);
        Font heading = new Font("Arial", Font.BOLD, 16);
        try {
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql);
            Statement st = con.createStatement();
            String fieldname = "dist_code";
            while (rs2.next()) {
                document.newPage();
                String rank = rs2.getString("rank");
                String regid = rs2.getString("regid");
                //       String query = "select name,fname from application where regid='" + regid + "'";
                String fname = "", name = "";
                name = rs2.getString("name");
                fname = rs2.getString("fname");
                String query = "select to_char(cal_date,'dd-mm-yyyy'),to_char(cal_time,'HH12:MI AM') from admission_timings where merit_from<= 1 and merit_to >= 1 and " + fieldname + "='" + dist_code + "'and min_qul='" + qual + ";";//' and '";//caste='"+caste+"';
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                String cal_date = "", cal_time = "";
                while (rs.next()) {
                    cal_date = rs.getString(1);
                    cal_time = rs.getString(2);
                }
                Paragraph pg = new Paragraph("                                 GOVERNMENT OF ANDHRA PRADESH", FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 14));
                document.add(pg);
                pg = new Paragraph("                DIRECTORATE OF EMPLOYMENT AND TRAINING, A.P., HYDERABAD ", FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 12));
                document.add(pg);
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Office of the Convenor", FontFactory.getFont(FontFactory.TIMES, 12)));
                pg = new Paragraph("" + dist_name, FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
                document.add(pg); //get Dist r ITI name
                pg = new Paragraph("                                                                                                                                            Date:" + dt, FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
                document.add(pg);//System Date

                pg = new Paragraph("                               INTERVIEW FOR GOVT. I.T.Is/PRIVATE I.T.Is.", FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 12));
                document.add(pg);
                Paragraph parent = new Paragraph();
                parent.add(new Chunk("Merit Order No: ", FontFactory.getFont(FontFactory.TIMES, 12)));
                parent.add(new Chunk(" " + rank + " ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                parent.add(new Chunk(" in ", FontFactory.getFont(FontFactory.TIMES, 12)));
                parent.add(new Chunk(" " + dist_name + " ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                parent.add(new Chunk(" with Register Number:  ", FontFactory.getFont(FontFactory.TIMES, 12)));
                parent.add(new Chunk(" " + regid + " ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                document.add(parent);

                parent = new Paragraph();
                parent.add(new Chunk("Sri/Kum ", FontFactory.getFont(FontFactory.TIMES, 12)));
                parent.add(new Chunk(" " + name + " ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                parent.add(new Chunk(" S/o.D/o ", FontFactory.getFont(FontFactory.TIMES, 12)));
                parent.add(new Chunk(" " + fname + " ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                parent.add(new Chunk("   is advised to report at this Industrial Training Institute on  at  sharp for interview and selection. If he/she fails to attend the interview on the scheduled date ", FontFactory.getFont(FontFactory.TIMES, 12)));
                parent.add(new Chunk(" " + cal_date + " ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                parent.add(new Chunk(" and time: ", FontFactory.getFont(FontFactory.TIMES, 12)));
                parent.add(new Chunk(" " + cal_time + " ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                parent.add(new Chunk(" his/her admission into ITI will not be considered. Such of these candidates who do not report for interview at the scheduled time they will be interviewed before the closure of the session of the day. But they will be eligible only into such trades and such institutions where seats are vacant at that point of time. Selection will be made on Merit-cum-Reservation as per admission rules inforce for Trade/Place (i.e. institute) choice of the applicant will be as per availability of seats at the time of interview in the trade/Institute. ", FontFactory.getFont(FontFactory.TIMES, 12)));

                document.add(parent);
                document.add(new Paragraph(""));
                // document.add(new Paragraph("Merit Order No: " + rank + " in " + dist_name + " with Register Number: " + regid + ""));//Rank,Dist/ITI,Reg Number
                //document.add(new Paragraph("Sri/Kum " + name + " S/o.D/o " + fname + "  is advised to report at this Industrial Training Institute on  at  sharp for interview and selection. If he/she fails to attend the interview on the scheduled date " + cal_date + " and time:  " + cal_time + " his/her admission into ITI will not be considered. Such of these candidates who do not report for interview at the scheduled time they will be interviewed before the closure of the session of the day. But they will be eligible only into such trades and such institutions where seats are vacant at that point of time. Selection will be made on Merit-cum-Reservation as per admission rules inforce for Trade/Place (i.e. institute) choice of the applicant will be as per availability of seats at the time of interview in the trade/Institute. ")); //Name,Fname,Cal_date,Cal_time
                document.add(new Paragraph("This call letter is only for an interview and does not give any right or guarantee or admission in ITI.", FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(new Paragraph("He/she is allowed to bring parent/guardian at the time of interview for his/her guidance in selection of Trade/place (Institute).", FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(new Paragraph("Selection is made according to availability of seats in I.T.I.", FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(new Paragraph("No T.A & D.A is admissible for attending the above interview.", FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(new Paragraph("1.(a) Selections are made to such of these Trades/Units affiliated to NCVT in the I.T.Is.", FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(new Paragraph("   (b) Selections are made under State Council for Vocational Training for I.T.Is./Units which are permitted by the State Director of Employment & Training Department which are not covered by (NCVT) National Council for Vocational Training.", FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(new Paragraph("2. He/she should attend the interview with all certificates in original T.C. The T.C. in original to be produced after selection within a week. The original certificates of selected candidates will be retained in the institute till the completion of the training. Hence the candidate is advised to keep sufficient number of true copies before submitting the original certificates.", FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(new Paragraph("3. Admission are subject to medical fines by the medical Officer of the Institute.", FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(new Paragraph("4. In case of S.C., S.T. and B.C Caste Certificates issued by the M.R.O should be submitted.", FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(new Paragraph("5. CAUTION MONEY: Candidates selected for admission into Govt. and Private I.T.I should pay Rs. 60/- and Rs. 100/- respectively towards the Caution Money Deposit immediately after selection. SC/ST candidates are exempted from payment of Caution Money Deposit.", FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(new Paragraph("6. In case of selection in private I.T.Is the candidates should pay Rs. 3500/- P.A towards tuition fee(Rs. 1100/- at the time of selection and Rs. 200/- per month).", FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(new Paragraph("7. The children of Ex-Servicemen should submit the certificates issued by the Secretary, Dist. Sainik Board in original.", FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(new Paragraph(""));

                document.close();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=myPdf.pdf");
                response.setHeader("Contentdisposition", "inline; filename=myPDF.pdf");
                response.setContentLength(baos.size());
                ServletOutputStream sos = response.getOutputStream();
                baos.writeTo(sos);
                sos.flush();
                sos.close();
                baos.close();
                if (rs != null) {
                    rs.close();
                }
            }
            if (st != null) {
                st.close();
            }
            if (rs2 != null) {
                rs2.close();
            }
            if (st2 != null) {
                st2.close();
            }
        } catch (Exception ex) {
        } finally {
            con.close();
        }
        return mapping.findForward(SUCCESS);
    }
}
