package tw.com.ispan.eeit48.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.domain.SystemNoticeMessageBean;
import tw.com.ispan.eeit48.repository.ProductRepository;
import tw.com.ispan.eeit48.repository.SupplierProductForOwnerProductRepository;
import tw.com.ispan.eeit48.repository.SystemNoticeMessageRepository;
import tw.com.ispan.eeit48.repository.View_product_order_orderdetailsRepository;

@Service
@Transactional
public class SystemNoticeMessageService {
	@Autowired
	private SystemNoticeMessageRepository systemNoticeMessageRepository;

	@Transactional
	public Boolean saveNewMessage(String messageContent, int receiverId) {
		Boolean result = false;
		SystemNoticeMessageBean bean = systemNoticeMessageRepository.findFirstByOrderByMessageidDesc();
		int newMessageId = bean.getMessageid()+1 ;
		// 設值
		SystemNoticeMessageBean bb= new SystemNoticeMessageBean();
		bb.setMessageid(newMessageId);
		bb.setMessagecontent(messageContent);
		bb.setReceiverid(receiverId);
		bb.setMessageread("N");
		if (systemNoticeMessageRepository.save(bb) != null) {
			result = true;
		}
		return result;
	}
	
	public SystemNoticeMessageService(SystemNoticeMessageRepository systemNoticeMessageRepository){
		this.systemNoticeMessageRepository =systemNoticeMessageRepository;

	}

	
	
	
}
