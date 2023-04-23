let commonHeaderContent = "";

$.get(document.title == "NiceToSeaYou" ? "views/commonHeader.html" : "./commonHeader.html", function(data) {
    commonHeaderContent = data.toString();
}).then(function() {
    let commonHeader = 
    `<nav>
    ${commonHeaderContent}
    </nav>`;

    // console.log(commonHeader);
    document.getElementById("common-header").innerHTML = commonHeader;
});
