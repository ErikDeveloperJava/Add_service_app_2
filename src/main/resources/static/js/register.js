$(document).ready(function () {

    $("#styledSelect1").change(function (event) {
        event.preventDefault();
        $(".fieldError").text("");
        var clazz = $("#styledSelect1").attr("class");
        if(clazz === "active_"){
            $("#regionError").text("")
            $("#styledSelect1").attr("class","not")
            var id = $(this).val();
            var token = $("#c_s").attr("value");
            $.ajax({
                type: "POST",
                url: "/register/cities",
                data: {regionId: id,_csrf : token},
                success: function (data) {
                    console.log(data);
                    $("#styledSelect1").empty();
                    $.each(data,function (i, city) {
                        var option = "<option value='" + city.id + "'>" + city.name + "</option>";
                        $("#styledSelect1").append(option);
                    });
                },
                error: function (error) {
                    console.log(error);
                    window.location = "/error"
                }
            })
        }
    });

    $("#register-form").submit(function (event) {
        event.preventDefault();
        var clazz = $("#styledSelect1").attr("class");
        if(clazz === "active_"){
            var lang = $("#lang").attr("content");
            switch (lang){
                case "en":
                    $("#regionError").text("Please choose a region");
                    break;
                case "ru":
                    $("#regionError").text("Пожалуйста выберите регион");
                    break;
                default:
                    $("#regionError").text("Խնդրում ենք ընտրել մարզը");
            }

        }else {
            $.ajax({
                type: "POST",
                url: "/register",
                data: new FormData(this),
                contentType: false,
                processData: false,
                success: function (data) {
                    $(".fieldError").text("");
                    console.log(data);
                    if(data.success){
                        $("#success").text(data.message);
                        $(".fieldError").text("");
                        $(".reg-input").val("");
                        $.ajax({
                            type: "POST",
                            url: "/register/regions",
                            data: {_csrf: token},
                            success: function (data) {
                                $("#styledSelect1").append("<option class='region_'>Regions</option>");
                                $.each(data, function (i, region) {
                                    var opt = "<option value='" + region.id + "'>" + region.name + "</option>";
                                    $("#styledSelect1").append(opt);
                                });
                                $("#styledSelect1").attr("class", "ok");
                                $("#slide-register").hide();
                                $("#slide-login").show();
                                $("#styledSelect1").attr("class", "active_");
                            },
                            error: function (error) {
                                window.location = "/error-500"
                            }
                        });
                    }else {
                        $.each(data.errors,function (i, error) {
                            if(i === 0){
                                $("#" + error.field + "Error").text(error.defaultMessage);
                            }
                        })
                    }
                },
                error: function (data) {
                    console.log(data);
                    window.location = "/error-500";
                }
            })
        }
    });

});