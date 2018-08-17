$(document).ready(function () {
    var  lang = $("#lang").attr("content");

    $("#add-new-ad_").on("click",function () {
        $("#main-settings").hide();
        $("#add-new-ad").show();
    });


    $("#ad-form").submit(function (event) {
        event.preventDefault();
        var category = $("#styledSelect1").val();
        if(category == "-1"){
            if(lang === "ru"){
                $("#categoryError").text("пожалуйста выберите категорию")
            }else if(lang === "arm"){
                $("#categoryError").text("խնդրում ենք ընտրել կատեգորիա")
            }else {
                $("#categoryError").text("please choose a category")
            }
        }else {
            var title = $("#title").val();
            var description = $("#description").val();
            var price = $("#price").val();
            var token = $("#frsc").attr("content");
            $.ajax({
                type: "POST",
                url: "/ad/add/1",
                data: {title: title,description: description,price: price,_csrf: token,"category.id": category},
                success: function (data) {
                    console.log(data);
                    if(data.success){
                        $.each(data.attributes,function (i, attribute) {
                            var input;
                            if(lang === "ru"){
                                input =
                                    "<input type='text' name='values' placeholder='" + attribute.nameRu +"'>"+
                                    "<input type='hidden' name='idList' value='" + attribute.id +"'>";

                            }else if(lang === "arm"){
                                input =
                                    "<input type='text' name='values' placeholder='" + attribute.nameArm +"'>"+
                                    "<input type='hidden' name='idList' value='" + attribute.id +"'>";

                            }else {
                                input =
                                    "<input type='text' name='values' placeholder='" + attribute.name +"'>"+
                                    "<input type='hidden' name='idList' value='" + attribute.id +"'>";
                            }
                            $("#attribute-data").append(input);
                        })

                        $("#add-new-ad").hide();
                        $("#attribute-div_").show();
                    }else {
                        $(".addErrors").text("");
                        $.each(data.errors,function (i, error) {
                            $("#" + error.field + "Error").text(error.defaultMessage);
                        })
                    }
                },
                error: function (error) {
                    console.log(error);
                    window.location = "/error-500";
                }
            })
        }
    });

    $(".back_attribute").on("click",function () {
        deleteTags();
    });

    var deleteTags = function(){
        $("#attribute-data").empty();
        $("#styledSelect1").val("-1");
        $("#attribute-div_").hide();
        $(".addErrors").text("");
        $(".ad-input").val("");
        $("#add-new-ad").show();
    };

    $("#attribute-form").submit(function (event) {
        event.preventDefault();
        $("#imageError").text("");
        $.ajax({
            type: "POST",
            url: "/ad/add/2",
            data: new FormData(this),
            contentType: false,
            processData: false,
            success: function (data) {
                console.log(data);
                if(data.success){
                    deleteTags();
                    window.location = "/ad/" + data.adId;
                }else if(data.imageError != null){
                    $("#imageError").text(data.imageError);
                }else {
                    deleteTags();
                }
            },
            error: function (error) {
                console.log(error);
                window.location = "/error-500"
            }
        })
    })
});