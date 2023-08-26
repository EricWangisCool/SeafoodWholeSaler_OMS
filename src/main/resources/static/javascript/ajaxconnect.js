// --------------jwtToken method----------------------------------- 
function getJwtToken() {
    return localStorage.getItem("jwtToken");
 }


function createAuthorizationTokenHeader() {
    var token = getJwtToken();
    if (token) {
       return {"Authorization": "Bearer " + token};
    } else {
       return {};
    }
 }

// -------------- AJAX method ----------------------------------- 
// Login API ----------------------------------- 
async function login(data) {
    let connurl = "login";
    let httpMethod = "post";
    return await ajaxmethod(connurl, data, httpMethod);
}


async function getUserAccount(data) {
    let connurl = "../login";
    let httpMethod = "get";
    return await ajaxmethod(connurl, null, httpMethod);
}


// 後臺首頁 Page API ----------------------------------- 
async function viewspage(data) {
    let connurl = "../page";
    let httpMethod = "get";
    return await ajaxmethod(connurl, null, httpMethod);
}


// Stock API ------------------------------------------
async function callGetStock(data) {
    let connurl = "../stock";
    let httpMethod = "get";
    return await ajaxmethod(connurl, null, httpMethod);
}


async function callInsertStock(data) {
    let connurl = "../stock";
    let httpMethod = "post";
    return await ajaxmethod(connurl, data, httpMethod);
}

async function callUpdateStock(data) {
    let connurl = "../stock";
    let httpMethod = "put";
    return await ajaxmethod(connurl, data, httpMethod);
}

async function callDeleteStock(data) {
    let connurl = "../stock/" + data;
    let httpMethod = "delete";
    return await ajaxmethod(connurl, null, httpMethod);
}


// 建立叫貨單相關 Goods API  ------------------------------------------
async function viewsgoods(data) {
    let connurl = "../goods" + "?supplierAccountId=" + data;
    let httpMethod = "get";
    return await ajaxmethod(connurl, null, httpMethod);
}

async function viewsgoodsinsert(data) {
    let connurl = "../goods";
    let httpMethod = "post";
    return await ajaxmethod(connurl, data, httpMethod);
}


// 叫貨管理相關 OrderBuy API  ------------------------------------------
async function viewsorderbuy(data) {
    let connurl = "../orderBuy";
    let httpMethod = "get";
    return await ajaxmethod(connurl, null, httpMethod);
}

async function viewsorderbuychildTable(data) {
    let connurl = "../orderBuy/orderDetail" + "?orderId=" + data.orderId;
    let httpMethod = "get";
    return await ajaxmethod(connurl, null, httpMethod);
}

async function viewsorderbuyupdate(data) {
    let connurl = "../orderBuy";
    let httpMethod = "put";
    return await ajaxmethod(connurl, data, httpMethod);
}


// 接單管理相關 OrderSell API  ------------------------------------------
async function viewsordersell(data) {
    let connurl = "../orderSell";
    let httpMethod = "get";
    return await ajaxmethod(connurl, null, httpMethod);
}

async function viewsordersellupdate(data) {
    let connurl = "../orderSell";
    let httpMethod = "put";
    return await ajaxmethod(connurl, data, httpMethod);
}


// 廠商管理相關 Cooperate API  ------------------------------------------
async function viewscooperate(data) {
    let connurl = "../cooperate";
    let httpMethod = "get";
    return await ajaxmethod(connurl, null, httpMethod);
}

async function viewscooperateinsert(data) {
    let connurl = "../cooperate";
    let httpMethod = "post";
    return await ajaxmethod(connurl, data, httpMethod);
}

// 對帳單 Statement API  ------------------------------------------
async function viewstatement(data) {
    let connurl = "../statement" + "?orderTime=" + data.orderTime + "&completeOrderTime=" + data.completeOrderTime + "&buyerId=" + data.buyerId;
    let httpMethod = "get";
    return await ajaxmethod(connurl, null, httpMethod);
}

// 第三方金流 EcPay API  ------------------------------------------
async function ecpay() {
    let connurl = "../ecPay";
    let httpMethod = "get";
    return await ajaxmethod(connurl, null, httpMethod);
}


// 數據中心 Data analyze API  ------------------------------------------
async function viewsanalyze(data) {
    let connurl = "../analyze" + "?orderTime=" + data.orderTime + "&completeOrderTime=" + data.completeOrderTime;
    let httpMethod = "get";
    return await ajaxmethod(connurl, null, httpMethod);
}


// News API  ------------------------------------------
async function viewsnewsupdate(data) {
    let connurl = "../news";
    let httpMethod = "put";
    return await ajaxmethod(connurl, data, httpMethod);
}


// Common API  ------------------------------------------
function getContextPath() {
    let connurl = "../common/contextPath";
    let httpMethod = "get";
    return sync_ajaxmethod(connurl, null, httpMethod);
}



// --------------AJAX Connection Method----------------------------------- 
var returnObj;


function initReturnObject() {
    returnObj = {
        responseObj: "",
        responseText: "",
        responseStatus: "",
        responseToken: ""
    };
}


function getAjaxObject(isAsync, connurl, data, httpMethod) {
    initReturnObject();
    return {
        type: httpMethod,
        url: connurl,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        dataType: 'json',
        crossDomain: true,
        async: isAsync,
        headers: createAuthorizationTokenHeader(),
        xhrFields: {
            withCredentials: true
        },
        complete: function (XMLHttpRequest, textStatus) {
            returnObj.responseText = XMLHttpRequest.responseText;
            returnObj.responseObj = XMLHttpRequest.responseJSON == null ? {status: null} : XMLHttpRequest.responseJSON;
            returnObj.responseStatus = XMLHttpRequest.status;
            returnObj.responseToken = XMLHttpRequest.getResponseHeader("authorization");
            // console.log(returnObj.responseText + "\n" + returnObj.responseObj.status + "\n" +  returnObj.responseStatus + "\n" +  returnObj.responseToken);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            // console.log("AjaxConnect error:  " + "\nXMLHttpRequest.status= " + XMLHttpRequest.status + 
            // "\nXMLHttpRequest.readyState= " + XMLHttpRequest.readyState + "\ntextStatus= " + textStatus);
        }
    };
};


async function ajaxmethod(connurl, data, httpMethod) {
    initReturnObject();
    try {
        await $.ajax(getAjaxObject(true, connurl, data, httpMethod));
    } catch (err) {
        console.log(err);
    }
    return returnObj;
};


function sync_ajaxmethod(connurl, data, httpMethod) {
    initReturnObject();
    try {
        $.ajax(getAjaxObject(false, connurl, data, httpMethod));
    } catch (err) {
        console.log(err);
    }
    return returnObj;
};


function isReturnObjectCorrect(returnObj) {
    var body = returnObj.responseObj;
    if (body.status == "0" && returnObj.responseStatus == 200) {
        if (body.data == "Request has been processed successfully") {
            body.data = [];
        };
        return true;
    } else if (returnObj.responseStatus == 401 || // 登入失敗或jwt過期不用alert
                body.status == 'B100-1' // 廠商加入失敗不用alert
            ) { 
        return false;        
    } else {
        console.error(body.status, body.statusInfo);
        // 统一由服务端返回给用户的提示信息
        alert(body.statusInfo.message + ":\n\n[" + body.status + "]" + body.statusInfo.detail.exception);
        return false;
    }
};
