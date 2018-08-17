$(document).ready(function () {

    var token = $("#frsc").attr("content");
    var lang = $("#lang").attr("content");

    $(document).on("mouseover", ".has-sub", function (event) {
        event.preventDefault();
        var id = $(this).attr("id");
        if (id != "null") {
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
                                    "<li class='has-sub' id='" + category.id + "'>" +
                                    "<a style='font-size: 16px' href='/category/" + category.id + "'>" + category.name + "</a>"
                                    + "</li>"
                            } else {
                                li =
                                    "<li id='" + category.id + "'>" +
                                    "<a style='font-size: 16px' href='/category/" + category.id + "'>" + category.name + "</a>"
                                    + "</li>"
                            }

                        } else if (lang === "ru") {
                            if (category.child) {
                                li =
                                    "<li class='has-sub' id='" + category.id + "'>" +
                                    "<a style='font-size: 16px' href='/category/" + category.id + "'>" + category.nameRu + "</a>"
                                    + "</li>"
                            } else {
                                li =
                                    "<li id='" + category.id + "'>" +
                                    "<a style='font-size: 16px' href='/category/" + category.id + "'>" + category.nameRu + "</a>"
                                    + "</li>"
                            }

                        } else {
                            if (category.child) {
                                li =
                                    "<li class='has-sub' id='" + category.id + "'>" +
                                    "<a style='font-size: 16px' href='/category/" + category.id + "'>" + category.nameArm + "</a>"
                                    + "</li>"
                            } else {
                                li =
                                    "<li id='" + category.id + "'>" +
                                    "<a style='font-size: 16px' href='/category/" + category.id + "'>" + category.nameArm + "</a>"
                                    + "</li>"
                            }

                        }
                        $("#" + id).children("." + id).append(li);
                    });
                    $("#" + id).attr("id", "null")
                },
                error: function (error) {
                    console.log(error);
                    window.location = "/error-500";
                }
            })
        }

    })
});