function post(){
    //#号取id
    //1.问题的id
    var questionId = $("#questionId").val();
    //2.评论的内容
    var content = $("#comment_content").val();

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
                $("#comment_section").hide();
            }else{
                alert("错误码:"+response.code+"，"+response.message)
            }
        }
    });
}