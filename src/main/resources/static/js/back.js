$(document).ready(function () {

    $(".back_ad").on("click",function () {
        $("#add-new-ad").hide();
        $("#styledSelect1").val("-1");
        $(".addErrors").text("");
        $(".ad-input").val("");
        $("#main-settings").show();
    });

});