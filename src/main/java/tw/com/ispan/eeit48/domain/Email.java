package tw.com.ispan.eeit48.domain;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//mochi7077
public class Email {
private String userName=""; //寄信者的帳號
private String userPassword="";  //寄信者的密碼
private String customer;  //收信者的email
private String subject;   //寄信的標題
private String txt;  //寄信的內容

public void sendMail(String customer ,String subject, String txt ) throws MessagingException {
	//連線設定
	Properties prop =new Properties();
	 
	 //設定傳輸協定為smtp
    
     //設定mail Server
  
     //SSL authenticatio
    // 建立一個Session物件，並把properties傳進去
	prop.setProperty("mail.transport.protocol", "smtp");
	prop.setProperty("mail.host", "smtp.gmail.com");
	prop.put("mail.smtp.port", "465");
	prop.put("mail.smtp.auth","true");
	//prop.put("mail.smtp.starttls.enable","ture");
	//prop.put("mail.smtp.host","smtp.gmail.com");
	prop.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	prop.put("mail.smtp.socketFactory.port","465");
	
	
//	Session session=Session.getDefaultInstance(prop, new Authenticator() {
//		@Override
//		protected PasswordAuthentication getPasswordAuthentication() {
//			// TODO Auto-generated method stub
//			return new  PasswordAuthentication(userName, userPassword);
//		}
//	});
Auth auth= new Auth(userName, userPassword);
	
//Session session=Session.getDefaultInstance(prop, auth);
Session session=Session.getInstance(prop, auth);	
	MimeMessage message= new MimeMessage(session);
	
	try {
		//message.setSender(new InternetAddress(userName));
	InternetAddress  sender=new InternetAddress(userName);
	message.setSender(sender);

	
	message.setRecipient(RecipientType.TO, new InternetAddress(customer));
//	
	
	message.setSubject(subject);
//	
message.setContent(txt,"text/html;charset=UTF-8");
//	
	Transport transport=session.getTransport();
	transport.send(message);
	transport.close();
//	
	} catch (AddressException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	//} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//	
//}
	
}

}





class Auth extends Authenticator{
  private String userName;
  private String userPassword;
  
  public Auth(String userName,String userPassword) {
	  this.userName=userName;
	  this.userPassword=userPassword;
	  
  }
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		PasswordAuthentication pa= new PasswordAuthentication(userName, userPassword);
		return pa;
	}
	
}





