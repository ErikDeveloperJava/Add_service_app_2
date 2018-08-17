$(document).ready(function () {
    var token = $("#frsc").attr("content");

    $(".delete-user").on("click", function (event) {
        event.preventDefault();
        var id = $(this).attr("id");
        $.ajax({
            type: "POST",
            url: "/user/delete",
            data: {_csrf: token,id: id},
            success: function (data) {
                $("#" + id).parent().remove();
            },
            error: function (error) {

            }
        })
    })
});