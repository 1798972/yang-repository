/*回复与评论*/
function comment2Target(targetId,type,content) {
    if (!content) {
        alert("内容不能为空！")
        return;
    }
    //点击按钮后发送的ajax请求！！
    $.ajax({
        contentType: "application/json",
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": targetId,
            "type": type,
            "content": content
        }),
        dataType: "json",
        success: function (response) {
            if (response.code == 200) {
                //隐藏回复框
                // $("#comment_section").hide();
                //页面刷新
                window.location.reload();
            } else {
                //用户未登录时 要先登录
                if (response.code == 2003) {
                    var accepted = confirm(response.message);
                    //点击确认 跳转登录
                    if (accepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=f0b690e1af8e644032f9&http://localhost:8089/callback&scope=user&state=1");
                        window.localStorage.setItem("close", true);
                    }
                } else {
                    alert("错误码:" + response.code + "，" + response.message)
                }
            }
        }
    });
}

/*对问题的评论*/
function post() {
    //#号取id
    //1.问题的id
    var questionId = $("#questionId").val();
    //2.评论的内容
    var content = $("#comment_content").val();
    comment2Target(questionId,1,content);
}

/*对评论的评论*/
function comment(e) {
    var commentId =  e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2Target(commentId,2,content);
}

/*删除问题*/
function deletequestion() {
    var message = confirm("您确定删除该问题吗？")
    if (message == true) {
        return true;
    } else {
        return false;
    }
}

/*点击展开二级评论*/
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);

    //展开/关闭 判断是否包含in这个属性
    if (comments.hasClass("in") == false){
        //若点击了展开评论
        //先判断当前页面是否有其评论 有的话就不用重新请求得到数据
        if (comments.children().length != 1 ){
            //页面上有数据 直接展开即可
            comments.addClass("in");
            e.classList.add("active");
        }else{
            //访问接口得到json数据
            $.getJSON("/comment/"+id,function (data) {
                /*debugger;*/
                var subComment = $("#comment-" + id);

                //遍历得到的数据 设置每个元素的样式
                $.each(data.data,function (index,comment){
                    //拼接头像、内容、时间
                    var mediaLeftElement = $("<div/>",{
                        "class": "media-left"
                    }).append($("<img/>",{
                        "class": "media-object img-rounded avatar-icon",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>",{
                        "class":'media-body'
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class" :'menu'
                    }).append($("<span/>", {
                        "class" :"pull-right",
                        "html" : moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                var mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLeftElement)
                     .append(mediaBodyElement);

                var commentElement = $("<div/>", {
                    "class": "col-1g-12 col-md-12 col-sm-12 col-xs-12 comments"
                }). append(mediaElement);
                    subComment.prepend(commentElement);
                });

            });
            comments.addClass("in");
            e.classList.add("active");
        }
        //若点击关闭
    }else {
        //折叠
        comments.removeClass("in");
        e.classList.remove("active")
    }
}