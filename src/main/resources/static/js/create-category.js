$(document).ready(function () {
    var token = $("#frsc").attr("content");
    var lang =  $("#lang").attr("content");

    $(document).on("click",".has-sub",function (event) {
        var id = $(this).attr("id");
        var title = $(this).attr("title");
        console.log(id);
        if(id != 0 || title != "null"){
            $("#categoryId").val(id);
            $(".cssMenu").hide();
            $("#add-category").show();
            $("#c_success").text("");
        }
    });

    $("#category-form").submit(function (event) {
        event.preventDefault();
        var c_en = $("#c_en").val();
        var c_ru = $("#c_ru").val();
        var c_arm = $("#c_arm").val();
        var categoryId = $("#categoryId").val();
        $.ajax({
           type: "POST",
           url: "/admin/category/add",
           data: {
               _csrf: token,
               nameEn: c_en,
               nameRu: c_ru,
               nameArm: c_arm,
               parent: categoryId
           },
           success: function (data) {
               console.log(data);
               $(".c_error").text("");
               if(data.success){
                   $(".c_input").val("");
                   $("#c_success").text(data.successMsg);
                   $("#add-category").hide();
                   $(".cssMenu").show();
               }else {
                   $.each(data.errors,function (i, error) {
                       $("#" + error.field + "Error").text(error.defaultMessage);
                   })
               }
           },
           error: function (error) {
               window.location = "/error-500"
           }
        });
    });

    $(document).on("mouseover", ".has-sub", function (event) {
        event.preventDefault();
        var id = $(this).attr("id");
        var title = $(this).attr("title");
        if (title != "null") {
            $.ajax({
                type: "POST",
                url: "/category",
                data: {parentId: id, _csrf: token},
                success: function (data) {
                    console.log(data);
                    $("#" + id).append("<ul class='" + id + "'></ul>");
                    $.each(data, function (i, category) {
                        var li;
                        if (lang === "en") {
                            if (category.child) {
                                li =
                                    "<li style='cursor: pointer' title='asd' class='has-sub' id='" + category.id + "'>" +
                                    "<a style='font-size: 16px'>" + category.name + "</a>"
                                    + "</li>"
                            } else {
                                li =
                                    "<li style='cursor: pointer' title='asd' id='" + category.id + "'>" +
                                    "<a style='font-size: 16px'>" + category.name + "</a>"
                                    + "</li>"
                            }

                        } else if (lang === "ru") {
                            if (category.child) {
                                li =
                                    "<li title='asd' style='cursor: pointer' class='has-sub' id='" + category.id + "'>" +
                                    "<a style='font-size: 16px'>" + category.nameRu + "</a>"
                                    + "</li>"
                            } else {
                                li =
                                    "<li style='cursor: pointer' title='asd' id='" + category.id + "'>" +
                                    "<a style='font-size: 16px'>" + category.nameRu + "</a>"
                                    + "</li>"
                            }

                        } else {
                            if (category.child) {
                                li =
                                    "<li style='cursor: pointer' title='asd' class='has-sub' id='" + category.id + "'>" +
                                    "<a style='font-size: 16px'>" + category.nameArm + "</a>"
                                    + "</li>"
                            } else {
                                li =
                                    "<li style='cursor: pointer' title='asd' id='" + category.id + "'>" +
                                    "<a style='font-size: 16px'>" + category.nameArm + "</a>"
                                    + "</li>"
                            }

                        }
                        $("#" + id).children("." + id).append(li);
                    });
                    $("#" + id).attr("title", "null")
                },
                error: function (error) {
                    console.log(error);
                    window.location = "/error-500";
                }
            })
        }

    })
});