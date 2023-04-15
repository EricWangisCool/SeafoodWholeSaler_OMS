var contextPath = getContextPath().responseText;
let client = Stomp.over(new SockJS(window.location.protocol + "//" + window.location.host + contextPath + "/ws"));
client.debug = null;


var header = {
    jwtToken : getJwtToken()
}
var messages = "";


// 溝通訂閱代理位置
client.connect(header, frame => {
    // /PersonalNotify  WebSocket 訊息來源
    // /user 為規定必加
    client.subscribe("/user/PersonalNotify/Notify", payload => {
        messages = JSON.parse(payload.body);
        var unreadMessagesQty = 0;

        if (document.title == "sysframe") {
            messages.forEach(message => {
                if (message.messageRead == "N") {
                    unreadMessagesQty++;
                };
            });

            if (unreadMessagesQty > 0) {
                $(".rounded-pill").show();
                $(".rounded-pill")[0].innerHTML = unreadMessagesQty;     
            } else {
                $(".rounded-pill").hide();
                $(".rounded-pill")[0].innerHTML = "";
            }
        } else {
            allNewsList = messages;
            reNewData();
        }

    });

    // 訂閱後立即跟後端索取訊息
    client.send('/PersonalNotify/GetMessages', {}, {});
});


// 將未讀訊息已讀
$(".newsli").click(async function() {
    let readMessageIds = [];

    messages.forEach(message => {
        if (message.messageRead == "N") {
            readMessageIds.push(message.messageId);
        }
    });

    if (document.title == "sysframe") {
        var returnObj = await viewsnewsupdate(readMessageIds);
        
        if (returnObj.responseStatus != 200) {
            // 沒接到資料
            console.log("updateData getServerData error:" + "\nreturnObj.responseStatus: " + returnObj.responseStatus + "\nreturnObj.responseObj: " + returnObj.responseObj);
        } else {  
            console.log("news read");
        }
    }
});
