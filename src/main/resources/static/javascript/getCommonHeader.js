let commonHeaderContent = "";

$.get("./commonHeader.html", function(data) {
    commonHeaderContent = data.toString();
}).then(function() {
    let commonHeader = 
    `<nav>
    ${commonHeaderContent}
    </nav>`;

    // console.log(commonHeader);
    document.getElementById("common-header").innerHTML = commonHeader;
});
