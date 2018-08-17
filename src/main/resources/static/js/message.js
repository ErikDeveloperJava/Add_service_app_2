$(document).ready(function () {
    var role = $("#role").attr("content");
    var lang = $("#lang").attr("content");
    var token = $("#frsc").attr("content");
    var newMessageEn = "new Messages";
    var newMessageRu = "Новые сообщения";
    var newMessageArm = "Նոր հաղորդագրություններ";
    var user_Id = $("#user-id").attr("content");
    var messageSize;
    var main = function(){
        $.ajax({
            type: "POST",
            url: "/message/counts",
            data: {_csrf: token},
            success: function (data) {
                messageSize = data;
                if(lang === "en"){
                    $("#message-input").val("Messages(" + data + ")");
                }else if(lang === "ru"){
                    $("#message-input").val("Сообщения(" + data + ")");
                }else {
                    $("#message-input").val("Հաղորդագրություններ(" + data + ")");
                }
            },
            error: function (error) {
                window.location = "/error-500"
            }
        });
    };
    if (role === "USER") {
      main();
    }

    $("#message-input").on("click",function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: "/message/users",
            data: {_csrf: token},
            success: function (data) {
                if(data.length > 0){
                    $.each(data,function (i, messageForm) {
                        var msg =
                            "<div class='blog-grids'>" +
                            "<div class='blog-grid-left' style='width:70px;height: 70px'>" +
                            "<img  src='/resources/" + messageForm.user.image +"' class='img-responsive' alt=''>" +
                            "</div>";
                        if(lang === "en"){
                            msg = msg + "<span class='" + messageForm.count +  "' id='" + messageForm.user.id + "sp" + "'>" + newMessageEn + "(" + messageForm.count + ")" +"</span>";
                        }else if(lang === "ru"){
                            msg = msg + "<span class='"+ messageForm.count +  "' id='"+ messageForm.user.id + "sp" +  "'>" + newMessageRu + "(" + messageForm.count + ")" +"</span>";
                        }else {
                            msg = msg + "<span class='"+ messageForm.count +  "' id='"+ messageForm.user.id + "sp" + "'>" + newMessageRu + "(" + messageForm.count + ")" +"</span>";
                        }
                        msg = msg +
                            "<div class='blog-grid-right'>" +
                            "<h5 class='all-messages' style='cursor: pointer'><a id='" + messageForm.user.id + "'>"+  messageForm.user.name +" "+ messageForm.user.surname +"</a></h5>" +
                            "</div>" +
                            "<div class='clearfix'></div>" +
                            "</div>";
                        $("#message-div").append(msg)
                    });
                }else {
                    if(lang === "en"){
                        $("#message-header").text("You does not messages");
                    }else if(lang === "ru"){
                        $("#message-header").text("У вас нет сообщений");
                    }else {
                        $("#message-header").text("Դուք չունեք հաղորդագրություններ");
                    }
                }
                $("#message-main").hide();
                $("#message-user").show();
            },
            error: function (error) {
                window.location = "/error-500"
            }
        })
    });

    $("#back-message").on("click",function () {
        $("#message-div").empty();
        $("#message-user").hide();
        main();
        $("#message-main").show();
    });

    $(document).on("click",".all-messages",function () {
        var id = $(this).children().attr("id");
        $.ajax({
            type: "POST",
            url: "/message/all",
            data: {_csrf: token,userId: id},
            success: function (data) {
                if(data.length > 0){
                    console.log(data);
                    $.each(data,function (i, message) {
                        var msg;
                        if(message.to.id === user_Id && message.status === "NEW" ){
                            msg =
                                "<li style='color: red'>" +
                                "<a >" + message.from.name + " " + message.from.surname + "</a>" +
                                "<p>" + message.message + "</p>" +
                                "</li>";
                        }else {
                            msg =
                                "<li>" +
                                "<a >"+ message.from.name + " " + message.from.surname + "</a>" +
                                "<p>"+ message.message + "</p>" +
                                "</li>";
                        }
                        $("#message-ul").append(msg);
                    });
                    $("#userId").val(id);
                    $.ajax({
                        type: "POST",
                        url: "/message/update/status",
                        data: {_csrf: token,userId: id},
                        success: function (data) {
                            var size = $("#" + id + "sp").attr("class");
                            if(lang === "en"){
                                $("#" + id + "sp").text(newMessageEn + "(" + 0 +")")
                            }else if(lang === "ru"){
                                $("#" + id + "sp").text(newMessageRu + "(" + 0 +")")
                            }else {
                                $("#" + id + "sp").text(newMessageArm + "(" + 0 +")")
                            }
                            if(lang === "en"){
                                $("#message-input").val("Messages(" + (parseInt(messageSize) - parseInt(size)) + ")");
                            }else if(lang === "ru"){
                                $("#message-input").val("Сообщения(" + (parseInt(messageSize) - parseInt(size)) + ")");
                            }else {
                                $("#message-input").val("Հաղորդագրություններ(" + (parseInt(messageSize) - parseInt(size)) + ")");
                            }
                        },
                        error :function (error) {

                        }
                    });
                    $("#show-all-messages").show();
                    $("#message-user").hide();

                }
            },
            error: function (error) {
                window.location = "/error-500"
            }
        })
    });

    $("#back-message-user").on("click",function () {
        $("#message-ul").empty();
        $("#show-all-messages").hide();
        $("#message-user").show();
    });

    $("#message-form").submit(function (event) {
        event.preventDefault();
        var id = $("#userId").val();
        var message = $("#message").val();
        $.ajax({
            type: "POST",
            url: "/message/send2",
            data: {_csrf: token,userId: id,message: message},
            success: function (data) {
                var msg =
                    "<li>" +
                    "<a >"+ data.from.name + " " + data.from.surname + "</a>" +
                    "<p>"+ data.message + "</p>" +
                    "</li>";
                $("#message-ul").append(msg);
            },
            error: function (error) {
            }
        });
        $("#message").val("")
    })
});