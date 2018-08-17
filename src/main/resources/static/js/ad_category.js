$(document).ready(function () {

    var child = $(".category").attr("id");
    var token = $("#frsc").attr("content");
    var lang = $("#lang").attr("content");

    $.ajax({
        type: "POST",
        url: "/category-by-child",
        data: {_csrf: token, child: child},
        success: function (data) {

            $.each(data, function (i, category) {
                if (lang === "en") {
                    var a = "<a href='/category/" + category.id + "'>" + category.name + " / " + "</a>"
                } else if (lang === "ru") {
                    var a = "<a href='/category/" + category.id + "'>" + category.nameRu + " / " + "</a>"
                } else {
                    var a = "<a href='/category/" + category.id + "'>" + category.nameArm + " / " + "</a>"
                }
                $("#li-categories").prepend(a);
            });
        },
        error: function (error) {
            window.location = "/error-500"
        }
    });
});