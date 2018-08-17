$(document).ready(function () {

    $("#login").click(function (event) {
        event.preventDefault();
        var username = $("#username").val();
        var password = $("#password").val();
        var rememberMe = $("#brand").val();
        var token = $("#csrf").val();
        $.ajax({
           type: "POST",
           url: "/login",
           data: {username : username,password : password,_csrf : token,"remember-me" : rememberMe},
           success: function (data) {
               console.log(data);
               if(data === "success"){
                   window.location = "/";
               }else{
                   $("#error-login").text(data);
               }
           },
           error: function (error) {
               console.log(error)
           }
        });
    })
    $("#register_").on("click",function () {
       $("#slide-login").hide();
       $("#slide-register").show();
       $("#username").val("");
       $("#password").val("");
    });

    $("#login_").on("click",function () {
        event.preventDefault();
        $("#error-login").text("");
        $("#success").text("");
        $(".fieldError").text("");
        $("#styledSelect1").empty();
        $(".reg-input").val("");
        $(".upload").val("");
        var token = $("#c_s").attr("value");
        $.ajax({
            type:"POST",
            url: "/register/regions",
            data: {_csrf : token},
            success: function (data) {
                console.log(data);
                $("#styledSelect1").append("<option class='region_'>Regions</option>");
                $.each(data,function (i, region) {
                    var opt = "<option value='" + region.id + "'>" + region.name +"</option>";
                    $("#styledSelect1").append(opt);
                });
                $("#styledSelect1").attr("class","ok");
                $("#slide-register").hide();
                $("#slide-login").show();
                $("#styledSelect1").attr("class","active_");
            },
            error: function (error) {
                console.log(error);
                window.location = "/error-500"
            }
        });
    });
});