$(document).ready(function () {
    var token = $("#frsc").attr("content");

    $("#comment-form").submit(function (event) {
        event.preventDefault();
        var adId = $("#adId").val();
        var comment = $("#comment").val();
        var userImage = $("#user-image").attr("content");
        $.ajax({
            type: "POST",
            url: "/comment/add",
            data: {_csrf: token,comment: comment,adId: adId},
            success: function (data) {
                var date = new Date(data.sendDate);
                var day = date.getDay();
                var month = date.getMonth() + 1;
                var year = date.getFullYear();
                var hours = date.getHours();
                var minute = date.getMinutes();
                var com_div = "<div class='comments-grid'>" +
                    "<div class='comments-grid-left'>" +
                    "<img height='50px' width='60px' src='/resources/" + userImage +"' alt='' class='img-responsive'>" +
                    "</div>" +
                    "<div class='comments-grid-right'>" +
                    "<h5><a>" + data.user.name + " " + data.user.surname + "</a></h5>" +
                    "<h5>" + year + "-"  + month + "-" + day + " " + hours + ":" + minute +"</h5>" +
                    "<p>" + data.comment +"</p>" +
                    "</div>" +
                    "<div class='clearfix'></div>" +
                    "</div>";
                $("#commet-div").append(com_div);
                $("#comment").val("");
            },
            error: function (error) {
                window.location = "/error-500";
            }
        });
    })
});