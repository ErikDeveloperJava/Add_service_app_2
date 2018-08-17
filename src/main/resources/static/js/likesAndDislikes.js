$(document).ready(function () {
    var like = "/resources/icons/like.png";
    var likeActive = "/resources/icons/like_active.png";
    var dislike = "/resources/icons/dislike.png";
    var dislikeActive = "/resources/icons/dislike_active.png";
    var token = $("#frsc").attr("content");

    $(".like").on("click", function (event) {
        event.preventDefault();
        var id = $(this).attr("id");
        var src = $("#" + id).children().attr("src");
        var adId = $("#" + id).attr("title");
        $.ajax({
            type: "POST",
            url: "/like",
            data: {_csrf: token,adId: adId},
            success: function (data) {
                if (data === "like-active dislike-not"){
                    var dislikeCount = $("#" + adId + "countDislikes").text();
                    var likeCount = $("#" + adId  + "countLikes").text();
                    $("#" + id).children().attr("src",likeActive);
                    $("#" + adId + "d").children().attr("src",dislike);
                    $("#" + adId + "countDislikes").text(parseInt(dislikeCount) - 1)
                    $("#" + adId + "countLikes").text(parseInt(likeCount) + 1)
                }else if(data === "like-not"){
                    var likeCount = $("#" + adId  + "countLikes").text();
                    $("#" + id).children().attr("src",like);
                    $("#" + adId + "countLikes").text(parseInt(likeCount) -1)
                }else {
                    var likeCount = $("#" + adId  + "countLikes").text();
                    $("#" + id).children().attr("src",likeActive);
                    $("#" + adId + "countLikes").text(parseInt(likeCount) +1)
                }
            },
            console: function (error) {
                window.location = "/error-500"
            }
        })
    });

    $(".dislike").on("click", function (event) {
        event.preventDefault();
        var id = $(this).attr("id");
        var src = $("#" + id).children().attr("src");
        var adId = $("#" + id).attr("title");
        $.ajax({
            type: "POST",
            url: "/dislike",
            data: {_csrf: token,adId: adId},
            success: function (data) {
                if (data === "dislike-active like-not"){
                    var dislikeCount = $("#" + adId + "countDislikes").text();
                    var likeCount = $("#" + adId  + "countLikes").text();
                    $("#" + id).children().attr("src",dislikeActive);
                    $("#" + adId + "l").children().attr("src",like);
                    $("#" + adId + "countDislikes").text(parseInt(dislikeCount) + 1);
                    $("#" + adId + "countLikes").text(parseInt(likeCount) -1)
                }else if(data === "dislike-not"){
                    var dislikeCount = $("#" + adId + "countDislikes").text();
                    $("#" + id).children().attr("src",dislike);
                    $("#" + adId + "countDislikes").text(parseInt(dislikeCount) - 1)
                }else {
                    var dislikeCount = $("#" + adId + "countDislikes").text();
                    $("#" + id).children().attr("src",dislikeActive);
                    $("#" + adId + "countDislikes").text(parseInt(dislikeCount) +1);
                }
            },
            console: function (error) {
                window.location = "/error-500"
            }
        })
    })
});
