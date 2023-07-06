package tw.com.ispan.eeit48.mainFunction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(String receiver, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("eynyseric520@gmail.com");
		message.setTo(receiver);
		message.setSubject(subject);
		message.setText(text);
		// 因為yml輸入smtp資訊會遭人盜取，目前暫時關閉寄信功能
//		javaMailSender.send(message);
	}
}
