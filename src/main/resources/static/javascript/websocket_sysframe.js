/**
 * 
 */
var protocol = window.location.protocol;
    newsCounter = 0;
	if(protocol == "https:"){
		sock = new SockJS("https://"+window.location.host+"/ws");
      }else{
    	sock = new SockJS("http://"+window.location.host+"/ws");
      }

	let client = Stomp.over(sock);
	client.debug = null
	var token = getJwtToken();
	var header = {
			jwtToken:token
	}
	// 溝通訂閱代理位置
	client.connect(header, frame => {
    // /PersonalNotify  WebSocket 訊息來源
    // /user 為規定必加
    client.subscribe("/user/PersonalNotify/Notify", payload => {
    	$(".rounded-pill").show();
    	newsCounter++;
    	$(".rounded-pill")[0].innerHTML = newsCounter; 
        console.log(payload);
        var newNotify = {
                    receiverid: 1,
                    messagecontent: '系統公告請盡快安排訂單#220101574出貨。2022-10-18 20:34:12',
                    messageid: 1,
                    messageread: 'N'
        	}
    	});

	});
	
	
	// 預留方法 未使用
	function sendMessage(){
		testbean = {
				titleType : "測試",
				message : "測試訊息",
				messageTime : new Date()
		}
	    console.log("send!")
	    client.send('/PersonalNotify/Send', {},JSON.stringify(testbean));

	}
	
	//消除消息
	$(".newsli").click(function(){
		$(".rounded-pill").hide();
		newsCounter = 0;
	});