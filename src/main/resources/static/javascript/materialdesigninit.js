// MDC Button註冊動畫(待研究，沒有這邊會無法運作動畫，但理論上應該是不需要---------------)
document.querySelectorAll('.mdc-button').forEach(
    function (ele) {
        mdc.ripple.MDCRipple.attachTo(ele);
    });


// MDC Text註冊  ------------------------------------------------------------------
// window.mdc.autoInit();


// MDC Select註冊 -----------------------------------------------------------------
var selectBoxMap = {};
function initializeSelect() {
    var mdcSelectList = document.querySelectorAll(' .mdc-select');
    if (mdcSelectList) {
        [].forEach.call(mdcSelectList, function (el) {
            var select = new mdc.select.MDCSelect(el);
            el.setAttribute('ripple-attached', true);
            var dropDownId = $(el).find('ul.mdc-list').attr('id');
            selectBoxMap[dropDownId] = select;
        });
    }
}
initializeSelect();


// MDC Switch設定
document.querySelectorAll('.mdc-switch').forEach(
    function (ele) {
        mdc.switchControl.MDCSwitch.attachTo(ele);
    });


// MDC radio設定
// document.querySelectorAll('.mdc-radio').forEach(
//     function (ele) {
//         mdc.radioControl.MDCRadio.attachTo(ele);
//     });

// const radio = new MDCRadio(document.querySelector('.mdc-radio'));
// const formField = new MDCFormField(document.querySelector('.mdc-form-field'));
// formField.input = radio;