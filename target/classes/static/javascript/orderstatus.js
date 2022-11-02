// 0：叫貨(Buy)，1：接單(Sell)

function checkstatus(player, status) {

    let returnstatus = {
        prev: "",
        now: ""
    }


    if (player == 0) {

        // 叫貨管理(上一步完成的)
        // 理論上不會有6的狀態
        switch (status) {
            case 1:
                returnstatus.prev = "我方已建立叫貨單";
                break;
            case 2:
                returnstatus.prev = "我方已送出叫貨單";
                break;
            case 3:
                returnstatus.prev = "供應商已確認";
                break;
            case 4:
                returnstatus.prev = "供應商已出貨";
                break;
            case 5:
                returnstatus.prev = "供應商已到貨";
                break;
            case 6:
                returnstatus.prev = "我方已收貨";
                break;
            case 7:
                returnstatus.prev = "訂單不成立";
                break;
            default:
                console.log("交易狀態錯誤");
                break;
        }

        // 叫貨管理(現在未完成)
        // 理論上不會有6的狀態
        switch (status) {
            case 1:
                returnstatus.now = "待我方送出叫貨單";
                break;
            case 2:
                returnstatus.now = "待供應商確認";
                break;
            case 3:
                returnstatus.now = "待供應商出貨";
                break;
            case 4:
                returnstatus.now = "供應商出貨中";
                break;
            case 5:
                returnstatus.now = "待我方收貨";
                break;
            case 6:
                returnstatus.now = "訂單完成";
                break;
            case 7:
                returnstatus.now = "訂單不成立";
                break;
            default:
                console.log("交易狀態錯誤");
                break;
        }

    } else if (player == 1) {

        // 接單管理(上一步完成的)
        // 理論上不會有6的狀態
        switch (status) {
            case 1:
                returnstatus.prev = "";
                break;
            case 2:
                returnstatus.prev = "採購商已送出叫貨單";
                break;
            case 3:
                returnstatus.prev = "我方已確認";
                break;
            case 4:
                returnstatus.prev = "我方已出貨";
                break;
            case 5:
                returnstatus.prev = "我方已送到貨";
                break;
            case 6:
                returnstatus.prev = "採購商已收貨";
                break;
            case 7:
                returnstatus.prev = "訂單不成立";
                break;
            default:
                console.log("交易狀態錯誤");
                break;
        }

        // 叫貨管理(現在未完成)
        // 理論上不會有6的狀態
        switch (status) {
            case 1:
                returnstatus.now = "";
                break;
            case 2:
                returnstatus.now = "待我方確認";
                break;
            case 3:
                returnstatus.now = "待我方出貨";
                break;
            case 4:
                returnstatus.now = "我方出貨中";
                break;
            case 5:
                returnstatus.now = "待採購商收貨";
                break;
            case 6:
                returnstatus.now = "訂單完成";
                break;
            case 7:
                returnstatus.now = "訂單不成立";
                break;
            default:
                console.log("交易狀態錯誤");
                break;
        }
    }




    return returnstatus;

}


function checkTimeLine(player, status) {
    let returnTimeLineStatus = {
        step1: "",
        step2: "",
        step3: "",
        step4: "",
        step5: ""
    }

    // 叫貨管理
    if (player == 0) {
        switch (status) {
            case 1: case 7:
                returnTimeLineStatus = {
                    step1: "",
                    step2: "",
                    step3: "",
                    step4: "",
                    step5: ""
                }
                break;
            case 2:
                returnTimeLineStatus = {
                    step1: "我方已送出叫貨單",
                    step2: "待供應商確認",
                    step3: "待供應商出貨",
                    step4: "供應商出貨中",
                    step5: "待我方收貨"
                }
                break;
            case 3:
                returnTimeLineStatus = {
                    step1: "我方已送出叫貨單",
                    step2: "供應商已確認",
                    step3: "待供應商出貨",
                    step4: "供應商出貨中",
                    step5: "待我方收貨"
                }
                break;
            case 4:
                returnTimeLineStatus = {
                    step1: "我方已送出叫貨單",
                    step2: "供應商已確認",
                    step3: "供應商已出貨",
                    step4: "供應商出貨中",
                    step5: "待我方收貨"
                }
                break;
            case 5:
                returnTimeLineStatus = {
                    step1: "我方已送出叫貨單",
                    step2: "供應商已確認",
                    step3: "供應商已出貨",
                    step4: "供應商已到貨",
                    step5: "待我方收貨"
                }
                break;
            case 6:
                returnTimeLineStatus = {
                    step1: "我方已送出叫貨單",
                    step2: "供應商已確認",
                    step3: "供應商已出貨",
                    step4: "供應商已到貨",
                    step5: "我方已收貨"
                }
                break;
            default:
                console.log("orderstatus checkTimeLine error");
                break;
        }

    } else if (player == 1) {

        switch (status) {
            case 1: case 7:
                returnTimeLineStatus = {
                    step1: "",
                    step2: "",
                    step3: "",
                    step4: "",
                    step5: ""
                }
                break;
            case 2:
                returnTimeLineStatus = {
                    step1: "採購商已送出叫貨單",
                    step2: "待我方確認",
                    step3: "待我方出貨",
                    step4: "我方出貨中",
                    step5: "待採購商收貨"
                }
                break;
            case 3:
                returnTimeLineStatus = {
                    step1: "採購商已送出叫貨單",
                    step2: "我方已確認",
                    step3: "待我方出貨",
                    step4: "我方出貨中",
                    step5: "待採購商收貨"
                }
                break;
            case 4:
                returnTimeLineStatus = {
                    step1: "採購商已送出叫貨單",
                    step2: "我方已確認",
                    step3: "我方已出貨",
                    step4: "我方出貨中",
                    step5: "待採購商收貨"
                }
                break;
            case 5:
                returnTimeLineStatus = {
                    step1: "採購商已送出叫貨單",
                    step2: "我方已確認",
                    step3: "我方已出貨",
                    step4: "我方已送到貨",
                    step5: "待採購商收貨"
                }
                break;
            case 6:
                returnTimeLineStatus = {
                    step1: "採購商已送出叫貨單",
                    step2: "我方已確認",
                    step3: "我方已出貨",
                    step4: "我方已送到貨",
                    step5: "採購商已收貨"
                }
                break;
            default:
                console.log("orderstatus checkTimeLine error");
                break;
        }

    }

    return returnTimeLineStatus;
}



function timeCounter(basicTime, status) {
    // 格式：2022-09-22 13:14:17
    let d = new Date(basicTime);

    // 狀態，+3個小時
    d.setHours(d.getHours() + 3);

    let deadlineStr = "";
    if (status == 1 || status == 6 || status == 7 || status == null || status == undefined || status == "") {
        deadlineStr = "";
    } else {
        deadlineStr = "(最晚)"
    }

    let returnTime = `${deadlineStr}${d.getFullYear()}-${
        (parseInt(d.getMonth()) + 1) < 10 ? "0" + (parseInt(d.getMonth()) + 1) : (parseInt(d.getMonth()) + 1)}-${
            d.getDate() < 10 ? "0" + d.getDate() : d.getDate()}<br>${
                d.getHours() < 10 ? "0" + d.getHours() : d.getHours()}:${
                    d.getMinutes() < 10 ? "0" + d.getMinutes() : d.getMinutes()}:${
                        d.getSeconds() < 10 ? "0" + d.getSeconds() : d.getSeconds()}`;

    return returnTime;
}


