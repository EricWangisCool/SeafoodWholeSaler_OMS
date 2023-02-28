// 換算金額三位一撇 ----------------------------------------------------------------------------------

function costFix(cost){    
    if(typeof cost == "string"){
        cost = parseInt(cost);
    }   
    
    internationalNumberFormat = new Intl.NumberFormat('en-US');
    return internationalNumberFormat.format(cost);
}









// 分頁按鈕---------------------------------------------------------------------------


// HTML內容。---------------------------------
// <div div class="pagination fs-6 fw-normal" >
//    <a href="" onclick="return false">&laquo;</a>
//    <a class="active" href="#">1</a>
//    <a href="#">2</a>
//    <a href="#">3</a>
//    <a href="#">4</a>
//    <a href="#">5</a>
//    <a href="" onclick="return false">&raquo;</a>
// </div >


// 目前分頁按鈕位置，初始化為最左邊1。----------
// let nowPageNum = 1;
// // 最下方分頁鈕點擊時，按鈕狀態改變。
// $(".pagination a").on("click", function () {
//     if ($(this).index() != 0 && $(this).index() != 6) {
//         $(".pagination a").removeClass('active');
//         $(this).addClass('active');
//         nowPageNum = $(this).index();

//         // 變換表格內容。

//     } else if ($(this).index() == 0) {

//         // 如果最左邊的第一個數字是1，就不會更新頁籤。
//         if ($(".pagination a")[1].innerHTML != 1) {

//             // 更新頁籤碼，所有頁籤碼-1
//             for (var i = 1; i <= 5; i++) {
//                 let pageNum = parseInt($(".pagination a")[i].innerHTML);
//                 $(".pagination a")[i].innerHTML = pageNum - 1;
//             }

//             // 更新active位置。
//             // 如果active位置在5，則不移動。
//             // 非5時，向右移動一格。
//             if (nowPageNum != 5) {
//                 $(".pagination a").removeClass('active');
//                 $(".pagination a")[nowPageNum + 1].classList.add('active');
//                 nowPageNum += 1;

//             } else {
//                 $(".pagination a").removeClass('active');
//                 $(".pagination a")[nowPageNum].classList.add('active');
//                 nowPageNum = 5;
//             }

//         }

//     } else {

//         // 更新頁籤碼，所有頁籤碼+1
//         for (var i = 1; i <= 5; i++) {
//             let pageNum = parseInt($(".pagination a")[i].innerHTML);
//             $(".pagination a")[i].innerHTML = pageNum + 1;
//         }

//         // 更新active位置。
//         // 如果active位置在1，則不移動。
//         // 非1時，向左移動一格。
//         if (nowPageNum != 1) {
//             $(".pagination a").removeClass('active');
//             $(".pagination a")[nowPageNum - 1].classList.add('active');
//             nowPageNum -= 1;

//         } else {
//             $(".pagination a").removeClass('active');
//             $(".pagination a")[nowPageNum].classList.add('active');
//             nowPageNum = 1;
//         }

//     }
// })
