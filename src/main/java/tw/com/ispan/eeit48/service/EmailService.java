package tw.com.ispan.eeit48.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.domain.SystemNoticeMessageBean;
import tw.com.ispan.eeit48.repository.SystemNoticeMessageRepository;

@Service
@Transactional
public class EmailService {
	@Autowired
	private SystemNoticeMessageRepository systemNoticeMessageRepository;
	@Autowired
	private JavaMailSender javaMailSender;

	public Boolean saveNewMessage(String messageContent, int receiverId) {
		Boolean result = false;
		SystemNoticeMessageBean bean = systemNoticeMessageRepository.findFirstByOrderByMessageidDesc();
		int newMessageId = bean.getMessageid() + 1;
		// 設值
		SystemNoticeMessageBean newBean = new SystemNoticeMessageBean();
		newBean.setMessageid(newMessageId);
		newBean.setMessagecontent(messageContent);
		newBean.setReceiverid(receiverId);
		newBean.setMessageread("N");
		if (systemNoticeMessageRepository.save(newBean) != null) {
			result = true;
		}
		return result;
	}

	public String sendMail(String receiver, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("eynyseric520@gmail.com");
		message.setTo(receiver);
		message.setSubject(subject);
		message.setText(text);
		try {
			javaMailSender.send(message);
		} catch (Exception e) {
			return e.toString();
		}
		return "OK";
	}
}
