package tw.com.ispan.eeit48.mainfunction.model;

import javax.persistence.*;

@Entity
@Table(name = "t_system_notice_message")
public class SystemNoticeMessageBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer message_id;
	private Integer receiver_id;
	@Column(columnDefinition = "char")
	private String message_read;
	@Column(columnDefinition = "char")
	private String message_content;

	public SystemNoticeMessageBean() {}

	public SystemNoticeMessageBean(Integer message_id, Integer receiver_id, String message_read, String message_content) {
		this.message_id = message_id;
		this.receiver_id = receiver_id;
		this.message_read = message_read;
		this.message_content = message_content;
	}

	public Integer getMessageId() {
		return message_id;
	}

	public void setMessageId(Integer message_id) {
		this.message_id = message_id;
	}

	public Integer getReceiverId() {
		return receiver_id;
	}

	public void setReceiverId(Integer receiver_id) {
		this.receiver_id = receiver_id;
	}

	public String getMessageRead() {
		return message_read;
	}

	public void setMessageRead(String message_read) {
		this.message_read = message_read;
	}

	public String getMessageContent() {
		return message_content;
	}

	public void setMessageContent(String message_content) {
		this.message_content = message_content;
	}
}
