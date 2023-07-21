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
        let unreadMessagesQty = 0;
        allNewsList = []
        //console.log(messages)
        if (document.title == "sysframe"|| document.title == "news") {
            messages.forEach(message => {
                if (message.messageRead == "N") {
                    unreadMessagesQty++;
                };
            });

			// 顯示紅色新訊息通知
            if (unreadMessagesQty > 0) {
                $(".rounded-pill").show();
                if($(".rounded-pill")[0]!=undefined){
                	$(".rounded-pill")[0].innerHTML = unreadMessagesQty;
                }else{
					$(".rounded-pill")["prevObject"][0].innerHTML = unreadMessagesQty;
				}
            } else {
                $(".rounded-pill").hide();
                if($(".rounded-pill")[0]!=undefined){
                	$(".rounded-pill")[0].innerHTML = "";
                }else{
					$(".rounded-pill")["prevObject"][0].innerHTML = "";
				}
            }
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

function reNewData() {
			let selectTitle = ['所有通知', '系統公告', '庫存管理通知', '叫貨管理通知', '接單管理通知'];
			if($(".newstable")["prevObject"]!=undefined){
            $(".newstable")["prevObject"][0].innerHTML = `
            <table class="newstable fs-5 fw-normal w-100">
                <thead>
                    <tr>
                        <th width="50"></th>
                        <th width="100%">通知內容</th>
                    </tr>
                </thead>
            </table>`;
			}else{
				$(".newstable")[0].innerHTML = `
            <table class="newstable fs-5 fw-normal w-100">
                <thead>
                    <tr>
                        <th width="50"></th>
                        <th width="100%">通知內容</th>
                    </tr>
                </thead>
            </table>`;
			}

            for (let i = 0; i < allNewsList.length; i++) {

                let newsTitle = "";
                let newsIcon = "";
                let newsTime = "";
                let newsContent = "";

                for (let k = 0; k < selectTitle.length; k++) {
                    let selectTitleLen = selectTitle[k].length;
                    if ((allNewsList[i].messageContent).substr(0, selectTitleLen) == selectTitle[k]) {
                        // 標題
                        newsTitle = selectTitle[k];

                        // 時間(-19是時間的長度)
                        newsTime = (allNewsList[i].messageContent).slice(allNewsList[i].messageContent.length - 19);
                        // 判斷時間格式是否正常
                        if (!isNaN(newsTime) || isNaN(Date.parse(newsTime))) {
                            newsTime = "2022-12-31 00:00:00";
                        }

                        // 內容
                        if (newsTime == "2022-12-31 00:00:00") {
                            // 標題內容裡面沒有時間or時間錯誤
                            newsContent = (allNewsList[i].messageContent).slice(newsTitle.length + 1);
                        } else {
                            // 標題內容有時間，要把時間部分排除
                            newsContent = (allNewsList[i].messageContent).slice(newsTitle.length + 1, (allNewsList[i].messageContent.length - 19));
                        }

                        // 檢查icon
                        switch (newsTitle) {
                            case selectTitle[0]:
                                newsIcon = `<i class="fas fa-comment-plus fs-5"></i>`;
                                break;
                            case selectTitle[1]:
                                newsIcon = `<i class="fas fa-comment-plus fs-5"></i>`;
                                break;
                            case selectTitle[2]:
                                newsIcon = `<i class="fas fa-cubes fs-5"></i>`;
                                break;
                            case selectTitle[3]:
                                newsIcon = `<i class="fas fa-shopping-cart fs-5"></i>`;
                                break;
                            case selectTitle[4]:
                                newsIcon = `<i class="fas fa-money-check-alt fs-5"></i>`;
                                break;
                            default:
                                newsIcon = "";
                                console.log("newsTitle error");
                                break;
                        }

                    }
                }

                // 沒有標題
                if (newsTitle == "") {
                    // 預設標題為selectTitle[1]
                    newsTitle = selectTitle[1];
                    newsIcon = `<i class="fas fa-comment-plus fs-5"></i>`;

                    // 時間(-19是時間的長度)
                    newsTime = (allNewsList[i].messageContent).slice(allNewsList[i].messageContent.length - 19);
                    // 判斷時間格式是否正常
                    if (!isNaN(newsTime) || isNaN(Date.parse(newsTime))) {
                        newsTime = "2022-12-31 00:00:00";
                    }

                    // 內容
                    if (newsTime == "2022-12-31 00:00:00") {
                        // 標題內容裡面沒有時間or時間錯誤 並且 沒有標題
                        newsContent = allNewsList[i].messageContent;
                    } else {
                        // 標題內容有時間，要把時間部分排除
                        newsContent = (allNewsList[i].messageContent).slice(0, (allNewsList[i].messageContent.length - 19));
                    }
                }


                let singleHtmlTr = `
                <tr data-value="${allNewsList[i].messageid}">
                    <td data-label="icon">${newsIcon}</td>
                    <td data-label="內容">
                        <q>${newsTitle}</q><span class="${allNewsList[i].messageread == 'N' ? 'status-indicator' : ''}"></span>
                        <span class="d-block py-3">${newsContent}</span>
                        <span class="d-block">${newsTime}</span>
                    </td>
                </tr>`;

                $(".newstable").append(singleHtmlTr);

            }

        }