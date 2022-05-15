package common;
import java.io.*;
import java.lang.*;
import java.util.*;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;
public class SendMail
 {
   public static boolean SendMail1(String subject, String toAddre, String emessage, String filename, String frmAddr, String toccAddre, String tobccAddre)
   {
                boolean mailsent_status=false;
                
              /*  if (subject == null || subject.equals(""))
                {
                   msg="No Subject";
                   return false;
                }
                if (toAddre == null || toAddre.equals(""))
                {
                   msg="No toAddre";
                   return false;
                }
                if (frmAddr == null || frmAddr.equals(""))
                {
                   msg="No toAddre";
                   return false;
                }
				*/
/* if more than one recipient */
String[] toadds= null;
    toadds = toAddre.split(",");
String[] toccadds= null;
    toccadds = toccAddre.split(",");
String[] tobccadds= null;
    tobccadds = tobccAddre.split(",");
                 try{

                            Properties prop = System.getProperties();
                            prop.put("mail.transport.protocol", "smtp");
                            prop.put("mail.smtp.host","relay.nic.in");
                            prop.put("mail.smtp.port", 25);
                            javax.mail.Session sessn = javax.mail.Session.getInstance(prop,null);
                            MimeMessage message = new MimeMessage(sessn);
                            Address frAddr;
                            frAddr = new InternetAddress(frmAddr);
                            message.setFrom(frAddr);
//Recipient type "To:"

                            for (int ii=0; ii < toadds.length; ii++)  
                            {
                            	Address toAddr;
                            if (toadds[ii] !=null && !toadds[ii].equals("")) 
                            	{	
                               	toAddr = new InternetAddress(toadds[ii]);
                                message.addRecipient(Message.RecipientType.TO, toAddr);
                            	}
                            }
System.out.println("Tolist: "+toAddre);
//Recipient type "Cc:"
                            for (int ii=0; ii < toccadds.length; ii++)  
                            {
                            	Address toAddr;
                            if (toccadds[ii] !=null && !toccadds[ii].equals("")) 
                            	{	
                               	toAddr = new InternetAddress(toccadds[ii]);
                                message.addRecipient(Message.RecipientType.CC, toAddr);
                            	}
                            }
System.out.println("Cclist: "+toccAddre);
//Recipient type "Bcc:"
                            for (int ii=0; ii < tobccadds.length; ii++)  
                            {
                            	Address toAddr;
                            if (tobccadds[ii] !=null && !tobccadds[ii].equals("")) 
                            	{	
                               	toAddr = new InternetAddress(tobccadds[ii]);
                                message.addRecipient(Message.RecipientType.BCC, toAddr);
                            	}
                            }
System.out.println("Bcclist: "+tobccAddre);

                            message.setSubject(subject);
      BodyPart part1 = new MimeBodyPart();
	  try
	  {
		  
      FileDataSource fds = new FileDataSource(filename);
      part1.setDataHandler(new DataHandler(fds));
      part1.setFileName(fds.getName());

	  }
	  catch (Exception e)
	  { System.out.println("Midhanimailerror"+e);	   }

      BodyPart part2 = new MimeBodyPart();
      part2.setText(emessage);
        Multipart multipart = new MimeMultipart();
                              if ((filename !=null))
                              { multipart.addBodyPart(part1); }
                                multipart.addBodyPart(part2);
                                message.setContent(multipart);
                                try{
                                        Transport.send(message);
                                        mailsent_status=true;
                                }catch(javax.mail.SendFailedException sfe)
                                {
                                        mailsent_status=false;
                                        System.out.println("Send Failed");
                                }
                }  catch (AddressException adx1) {
                    mailsent_status=false;
                                        System.out.println("Address Error");
                        }
                catch (MessagingException mex) {
                        mex.printStackTrace();
                    mailsent_status=false;
                                        System.out.println("Messaging Error");
                }
                catch (Exception ex) {
                    mailsent_status=false;
                                        System.out.println("Error");
                }
				return mailsent_status;
        }

}

