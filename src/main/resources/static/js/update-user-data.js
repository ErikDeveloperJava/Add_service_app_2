$(document).ready(function () {

    var name_ = $("#_name").val();
    var surname_ = $("#_surname").val();
    var username_ = $("#_username").val();
    var telephone_ = $("#_telephone").val();

    $("#update-user-data").on("click",function () {
        $("#main-settings").hide();
        $("#update-my-data_").show();
    })

    $("#update-form").submit(function (event) {
        event.preventDefault();
        var token = $("#frsc").attr("content");
        var name = $("#_name").val();
        var surname = $("#_surname").val();
        var username = $("#_username").val();
        var newPassword = $("#_newPassword").val();
        var oldPassword = $("#_oldPassword").val();
        var telephone = $("#_telephone").val();
        $.ajax({
            type: "POST",
            url: "/user/update",
            data: {_csrf: token,name: name,surname: surname,username: username,
            newPassword: newPassword,oldPassword: oldPassword,telephone: telephone},
            success: function (data) {
                if(data.success){
                    $("._fieldError").text("");
                    $(".update-input").val("");
                    $("#success_").text(data.successMsg);
                    name_ = name;
                    surname_ = surname;
                    username_ = username;
                    telephone_ = telephone;
                }else{
                    $("._fieldError").text("");
                    $.each(data.errors,function (i, error) {
                        $("#" + error.field + "Error_").text(error.defaultMessage);
                    })
                }
            },
            error: function (error) {
                window.location = "/error-500"
            }
        })
    });


    $(".back_update").on("click",function () {
        $("._fieldError").text("");
        $(".update-input").val("");
        $("#_name").val(name_);
        $("#_surname").val(surname_);
        $("#_username").val(username_);
        $("#_telephone").val(telephone_);
        $("#update-my-data_").hide();
        $("#main-settings").show();
    });
});