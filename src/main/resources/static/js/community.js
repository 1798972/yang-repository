function post(){
    //#号取id
    //1.问题的id
    var questionId = $("#questionId").val();
    //2.评论的内容
    var content = $("#comment_content").val();

    if (!content){
        alert("内容不能为空！")
        return;
    }
    //点击按钮后发送的ajax请求！！
    $.ajax({
        contentType:"application/json",
        type: "POST",
        url: "/comment",
        data:JSON.stringify({
            "parentId":questionId,
            "type":1,
            "content":content
        }),
        dataType:"json",
        success:function (response){
            // console.log(response)
            //{code: 200, message: "请求成功！"}
            if (response.code == 200){
                //隐藏回复框
                // $("#comment_section").hide();
                //页面刷新
                window.location.reload();
            }else{
                //用户未登录时 要先登录
                if(response.code == 2003){
                    var accepted = confirm(response.message);
                    //点击确认 跳转登录
                    if(accepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=f0b690e1af8e644032f9&http://localhost:8089/callback&scope=user&state=1");
                        window.localStorage.setItem("close",true);
                    }
                }else {
                alert("错误码:"+response.code+"，"+response.message)
                }
            }
        }
    });
}