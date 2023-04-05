package tw.com.ispan.eeit48.websocket.config;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import tw.com.ispan.eeit48.springsecurity.filter.JWTUtil;

@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebsockeConfig implements WebSocketMessageBrokerConfigurer {
	
	@Autowired
	JWTUtil jWTUtil;

	/*
	 *  設定連接終點
	 */
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

	/*
	 * 設定 終點底下大代理域
	 * 預設 /PersonalNotify 接收傳送user個人訊息
	 *      /AllNotify 伺服器傳送全局訊息
	 */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
    	registry.enableSimpleBroker("/PersonalNotify","/AllNotify");
    }

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		
		registration.interceptors(
				new ChannelInterceptor() {

					/*
					 *  收到訊息驗證是否合法token 並傳送User參數
					 *  初次連接 設定 SecurityContextHolder 全局對象
					 */
					@Override
					public Message<?> preSend(Message<?> message, MessageChannel channel) {
						StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);	
						if( StompCommand.CONNECT.equals(accessor.getCommand()) &&  accessor.getFirstNativeHeader("jwtToken")!=null  ) {
							String token = accessor.getFirstNativeHeader("jwtToken");
							HashMap<String, String> clientInfo = jWTUtil.getClientInfoFromToken(token);
							UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
			                        new UsernamePasswordAuthenticationToken(clientInfo.get(JWTUtil.USERID_KEY), null, AuthorityUtils.commaSeparatedStringToAuthorityList(clientInfo.get(JWTUtil.AUTHORITY_KEY)));
							SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
							accessor.setUser(usernamePasswordAuthenticationToken);
						}
						
						return message;
					}
					
				}
			);
	}
	
    
}
