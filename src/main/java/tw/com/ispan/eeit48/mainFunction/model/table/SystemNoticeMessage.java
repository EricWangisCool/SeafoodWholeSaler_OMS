package tw.com.ispan.eeit48.mainFunction.model.table;

import jakarta.persistence.*;

@Entity
@Table(name = "t_system_notice_message")
public class SystemNoticeMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer messageId;
	private Integer receiverId;
	@Column(columnDefinition = "char")
	private String messageRead;
	@Column(columnDefinition = "char")
	private String messageContent;

	public SystemNoticeMessage() {}

	public SystemNoticeMessage(Integer messageId, Integer receiverId, String messageRead, String messageContent) {
		this.messageId = messageId;
		this.receiverId = receiverId;
		this.messageRead = messageRead;
		this.messageContent = messageContent;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}

	public String getMessageRead() {
		return messageRead;
	}

	public void setMessageRead(String messageRead) {
		this.messageRead = messageRead;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}


}
