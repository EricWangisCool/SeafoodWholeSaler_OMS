// 登入
async function login(data) {
    let connurl = "/login";
    return await ajaxmethod(connurl, data);
}

async function logout(data) {
    let connurl = "/logout";
    return await ajaxmethod(connurl, data);
}


// 註冊
async function logininsert(data) {
    let connurl = "/login/insert";
    return await ajaxmethod(connurl, data);
}


// 後臺首頁
async function viewspage(data) {
    let connurl = "/views/page";
    return await ajaxmethod(connurl, data);
}


// 個人資訊相關
async function viewsaccount(data) {
    let connurl = "/views/account";
    return await ajaxmethod(connurl, data);
}

async function viewsaccountupdate(data) {
    let connurl = "/views/account/update";
    return await ajaxmethod(connurl, data);
}


// 通知與概況
async function viewsnews(data) {
    let connurl = "/views/news";
    return await ajaxmethod(connurl, data);
}


// 庫存概況
async function viewsorderstock(data) {
    let connurl = "/views/orderstock";
    return await ajaxmethod(connurl, data);
}

// 新增庫存相關
async function viewsaddstockinsert(data) {
    let connurl = "/views/addstock/insert";
    return await ajaxmethod(connurl, data);
}

async function viewsaddstockupdate(data) {
    let connurl = "/views/addstock/update";
    return await ajaxmethod(connurl, data);
}

async function viewsaddstockdelete(data) {
    let connurl = "/views/addstock/delete";
    return await ajaxmethod(connurl, data);
}


// 建立叫貨單相關
async function viewsgoods(data) {
    let connurl = "/views/goods";
    return await ajaxmethod(connurl, data);
}

async function viewsgoodsinsert(data) {
    let connurl = "/views/goods/insert";
    return await ajaxmethod(connurl, data);
}


// 叫貨管理相關
async function viewsorderbuy(data) {
    let connurl = "/views/orderbuy";
    return await ajaxmethod(connurl, data);
}

async function viewsorderbuychildTable(data) {
    let connurl = "/views/orderbuy/childTable";
    return await ajaxmethod(connurl, data);
}

async function viewsorderbuyupdate(data) {
    let connurl = "/views/orderbuy/update";
    return await ajaxmethod(connurl, data);
}


// 接單管理相關
async function viewsordersell(data) {
    let connurl = "/views/ordersell";
    return await ajaxmethod(connurl, data);
}

async function viewsordersellupdate(data) {
    let connurl = "/views/ordersell/update";
    return await ajaxmethod(connurl, data);
}


// 廠商管理相關
async function viewscooperate(data) {
    let connurl = "/viwes/cooperate";
    return await ajaxmethod(connurl, data);
}

async function viewscooperateinsert(data) {
    let connurl = "/viwes/cooperate/insert";
    return await ajaxmethod(connurl, data);
}

// 對帳單
async function viewstatement(data) {
    let connurl = "/views/statement";
    return await ajaxmethod(connurl, data);
}

// 第三方金流
async function ecpay() {
    let connurl = "/ecpay";
    data = "";
    return await ajaxmethod(connurl, data);
}

// 第三方金流年費
async function ecpay2() {
    let connurl = "/ecpay2";
    data = "";
    return await ajaxmethod(connurl, data);
}

// 數據中心
async function viewsanalyze(data) {
    let connurl = "/views/analyze";
    return await ajaxmethod(connurl, data);
}


// 最新消息
async function viewsnews(data) {
    let connurl = "/views/news";
    return await ajaxmethod(connurl, data);
}

async function viewsnewsupdate(data) {
    let connurl = "/views/news/update";
    return await ajaxmethod(connurl, data);
}












async function ajaxmethod(connurl, data) {

    let returnObj = {
        responseObj: "",
        responseText: "",
        responseStatus: ""
    };


    // console.log("to server:");
    // console.log(data);

    try {
        await $.ajax({
            type: 'post',
            url: connurl,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            dataType: 'json',
            crossDomain: true,
            xhrFields: {
                withCredentials: true
            },
            // success: function (getRequest) {
            //     returnObj.requestObj = getRequest.responseJSON;
            //     returnObj.requestStatus = getRequest.status;
            // },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // console.log("ajaxconnect error:  " + "(XMLHttpRequest.status)" + XMLHttpRequest.status + 
                // "   (XMLHttpRequest.readyState)" + XMLHttpRequest.readyState + "   (textStatus)" + textStatus);
            },
            complete: function (XMLHttpRequest, textStatus) {
                console.log(XMLHttpRequest);
                returnObj.responseText = XMLHttpRequest.responseText;
                returnObj.responseObj = XMLHttpRequest.responseJSON;
                returnObj.responseStatus = XMLHttpRequest.status;
            }

        });
    } catch (err) {
        // console.log(err);
    }

    return returnObj;
}
