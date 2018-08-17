$(document).ready(function () {

    var lang = $("#lang").attr("content");
    var token = $("#frsc").attr("content");
    var messageSuccessTextEn = "message sent";
    var messageSuccessTextRu = "сообщение отправлено";
    var messageSuccessTextArm = "հաղորդագրությունը ուղարկված է";
    var messageErrorTextEn = "message failed to send";
    var messageErrorTextRu = "сообщение  не удалось отправить";
    var messageErrorTextArm = "հաղորդագրությունը չհաջողվեց ուղարկել";
    var userId = $("#userId").val();

    $("#message-form").submit(function (event) {
        event.preventDefault();
        var message = $("#message").val();
        $.ajax({
            type: "POST",
            url: "/message/send",
            data: {_csrf: token,message: message,"to.id": userId},
            success: function (data) {
                $("#message").val("");
                if(lang === "en"){
                    $("#send-message-text").text(messageSuccessTextEn)
                }else if(lang === "ru"){
                    $("#send-message-text").text(messageSuccessTextRu)
                }else {
                    $("#send-message-text").text(messageSuccessTextArm)
                }
            },
            error: function (error) {
                $("#message").val("");
                if(lang === "en"){
                    $("#send-message-text").text(messageErrorTextEn)
                }else if(lang === "ru"){
                    $("#send-message-text").text(messageErrorTextRu)
                }else {
                    $("#send-message-text").text(messageErrorTextArm)
                }
            }
        })
    })
});