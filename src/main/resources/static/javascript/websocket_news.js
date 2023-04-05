/**
 * 
 */
//let singleHtmlTr = `
//                <tr data-value="${allNewsList[i].messageid}">
//                    <td data-label="icon">${newsIcon}</td>
//                    <td data-label="內容">
//                        <q>${newsTitle}</q><span class="${allNewsList[i].messageread == 'N' ? 'status-indicator' : ''}"></span>
//                        <span class="d-block py-3">${newsContent}</span>
//                        <span class="d-block">${newsTime}</span>
//                    </td>
//                </tr>`;
//
//$(".newstable").append(singleHtmlTr);

var url_prefix = location.pathname.split( '/' )[1];
var protocol = window.location.protocol;
    newsCounter = 0;
	if(protocol == "https:"){
		sock = new SockJS("https://"+window.location.host  + "/" +url_prefix +"/ws");
      }else{
    	sock = new SockJS("http://"+window.location.host+ "/" +url_prefix  +"/ws");
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
    	
        console.log( JSON.parse(payload.body) );
        var data = JSON.parse(payload.body)
        console.log(data)
        console.log(data.titleType)
        let singleHtmlTr = `
                <tr data-value="">
                    <td data-label="icon"><i class="fas fa-comment-plus fs-5"></i></td>
                    <td data-label="內容">
                        <q>${data.titleType}</q><span class="status-indicator"></span>
                        <span class="d-block py-3">${data.message}</span>
                        <span class="d-block">${data.messageTime}</span>
                    </td>
                </tr>`;

$(".newstable").append(singleHtmlTr);
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