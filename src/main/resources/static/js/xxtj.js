/*信息统计表js*/

//基本信息部分

//0.名字校验
function checkName(){
    var infoTag = $("#info_xm");
    infoTag.hide();
    var xm = $("#input_xm").val();
    eval("var reg = /^[\u4E00-\u9FA5]{2,4}$/");
    var resFlag = RegExp(reg).test(xm);
    if (!resFlag) {
        infoTag.html("<b style='color:red'>姓名格式错误!</b>");
        infoTag.show();
    }
}
//1.身份证校验

// 前17位每项的系数
let coefficient = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
// 除11取余的结果对应的校验位（最后一位）的值
let checkDigitMap = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'];
//计算身份证最后一位
function calculate(){
    var idCard = document.querySelector("#input_sfz");
    var result;
    var sum = 0;
    for (var i = 0; i < 17; i++) {
        sum += idCard.value[i] * coefficient[i];
    }
    result = checkDigitMap[sum % 11];
    return result;
}


function checkSFX() {
    var infoTag = $("#info_sfz");
    var sfzhm = $("#input_sfz").val();

    var xb = $("#input_xb");
    var nl = $("#input_nl");

    infoTag.hide();
    if (sfzhm === "" || sfzhm === undefined || sfzhm === null) {
        infoTag.html("<b style='color:red'>身份证号不能为空!</b>");
        infoTag.show();
        xb.val('');
        nl.val('');
        return false;
    }
    eval("var reg = /^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$/");
    var resFlag = RegExp(reg).test(sfzhm);
    if (!resFlag) {
        infoTag.html("<b style='color:red'>身份证号码格式错误!</b>");
        infoTag.show();
        xb.val('');
        nl.val('');
        return false;
    }
    //将最后一位x替换成大写
    var reg = new RegExp("x","g");//g,表示全部替换。
    $("#input_sfz").val(sfzhm.replace(reg,"X"));

    //格式正确 计算最后一位
    var lastNum = calculate();
    var inputLast = sfzhm.substr(17,1);
    if (inputLast !== lastNum){
        infoTag.html("<b style='color:red'>身份证号码不正确!</b>");
        infoTag.show();
        xb.val('');
        nl.val('');
        return false;
    }
    //计算性别
    var sex = "";
    if (parseInt(sfzhm.substr(16, 1)) % 2 === 1) {
        sex = "男";
    } else {
        sex = "女";
    }
    xb.val(sex);

    //计算年龄
    //获取年龄 
    var myDate = new Date();
    var month = myDate.getMonth() + 1;
    var day = myDate.getDate();
    var age = myDate.getFullYear() - sfzhm.substring(6, 10) - 1;
    if (sfzhm.substring(10, 12) < month || sfzhm.substring(10, 12) === month && sfzhm.substring(12, 14) <= day) {
        age++;
    }
    nl.val(age);

    //成功后返回true
    return true;
}

//2.社别选择
$(document).ready(function () {
    var element = $("#input_sb");
    $("#ul_sb li").click(function () {
        var result = $(this).text();
        //若标签框中为空
        element.val(result);
    })
});

//人数选择
$(document).ready(function () {
    var element = $("#input_jtrs");
    $("#ul_jtrs li").click(function () {
        var result = $(this).text();

        //若标签框中为空
        element.val(result.replace('人',''));
    })
});

//3.电话号校验
function checkDHH() {
    var infoTag = $("#info_dhh");
    var telNumber = $("#input_dhh").val();
    infoTag.hide();

    if (telNumber === "" || telNumber === undefined || telNumber === null) {
        infoTag.html("<b style='color:red'>电话号不能为空!</b>");
        infoTag.show();
        return false;
    }
    eval("var reg = /^1[34578]\\d{9}$/");
    var resFlag = RegExp(reg).test(telNumber);
    if (!resFlag) {
        infoTag.html("<b style='color:red'>电话号格式错误!</b>");
        infoTag.show();
        return false;
    }
    return true;
}

//返乡信息部分
//1.外出原因
$(document).ready(function () {
    var element = $("#input_wcyy");
    $("#ul_yy li").click(function () {
        var result = $(this).text();
        //若标签框中为空
        element.val(result);
    })
});

//2.返乡方式
$(document).ready(function () {
    var element = $("#input_fxfs");
    $("#ul_fx li").click(function () {
        var result = $(this).text();
        //若标签框中为空
        element.val(result);
    })
});

//检查基本信息部分
function checkJBXX() {
    var infoTag = $("#info_part1");
    infoTag.hide();
    var xm = $("#input_xm").val();
    var sfz = $("#input_sfz").val();
    var nl = $("#input_nl").val();
    var xb = $("#input_xb").val();
    var sb = $("#input_sb").val();
    var dhh = $("#input_dhh").val();
    var jtrs  = $("#input_jtrs").val();

    if (xm === "" || sfz === "" || nl === "" || xb === "" || sb === "" || dhh === "" || jtrs ===""||
        xm === null || sfz === null || nl === null || xb === null || sb === null || dhh === null|| jtrs === null
    ) {
        infoTag.html("<b><h4 style='color:red'>部分1存在未填写项目!</h4></b>");
        infoTag.show();
        return false;
    }
    return true;
}

//检查返乡信息部分
function checkFXXX() {
    var infoTag = $("#info_part2");
    infoTag.hide();
    var wldz = $("#input_wldz").val();
    var fxrq = $("#input_fxrq").val();
    var fxsj = $("#input_fxsj").val();
    var wcyy = $("#input_wcyy").val();
    var fxfs = $("#input_fxfs").val();
    var lwrq = $("#input_lwrq").val();
    var lwsj = $("#input_lwsj").val();
    if (wldz === "" || fxrq === "" || fxsj === "" || wcyy === "" || fxfs === "" || lwrq === "" || lwsj === "" ||
        wldz === null || fxrq === null || fxsj === null || wcyy === null || fxfs === null || lwrq === null || lwsj === null
    ) {
        infoTag.html("<b><h4 style='color:red'>部分2存在未填写项目!</h4></b>");
        infoTag.show();
        return false;
    }
    return true;
}
//检查外出信息部分
function checkWCXX() {
    var infoTag = $("#info_part3");
    infoTag.hide();
    var wcdz = $("#input_wcdz").val();
    var wcrq = $("#input_wcrq").val();
    var wcsj = $("#input_wcsj").val();
    if (wcdz === "" || wcrq === "" || wcsj === "" ||
        wcdz === null || wcrq === null || wcsj === null
    ) {
        infoTag.html("<b><h4 style='color:red'>部分3存在未填写项目!</h4></b>");
        infoTag.show();
        return false;
    }
    return true;
}


var x=5; //利用了全局变量来执行
function go(){
    x--;
    if(x>0){
        $("#successtime").html(x+'S');  //每次设置的x的值都不一样了。
    }else{
        location.href='/xxtj';
    }
}

//提交时检查所有
function checkAll() {
    var flag = checkJBXX() && checkFXXX() && checkWCXX();
    // var flag = true;
    if (flag) {
        var xm = $("#input_xm").val();
        var sfz = $("#input_sfz").val();
        var nl = $("#input_nl").val();
        var xb = $("#input_xb").val();
        var sb = $("#input_sb").val();
        var dhh = $("#input_dhh").val();
        var jtrs = $("#input_jtrs").val();

        var wldz = $("#input_wldz").val();
        var fxrq = $("#input_fxrq").val();
        var fxsj = $("#input_fxsj").val();
        var wcyy = $("#input_wcyy").val();
        var fxfs = $("#input_fxfs").val();
        var lwrq = $("#input_lwrq").val();
        var lwsj = $("#input_lwsj").val();

        var wcdz = $("#input_wcdz").val();
        var wcrq = $("#input_wcrq").val();
        var wcsj = $("#input_wcsj").val();
        //设置面访时间为外出时间-1
        var aPos = wcrq.indexOf('月');
        var bPos = wcrq.indexOf('日');
        var dt = wcrq.substr(aPos + 1, bPos - aPos - 1);
        var d = dt -1 ;
        var a2Pos = wcrq.indexOf('年');
        var b2Pos = wcrq.indexOf('月');
        var m = wcrq.substr(a2Pos + 1, b2Pos - a2Pos - 1);
        var mfsj = m + '月'+ d +'日';
        //提交操作
        $.ajax({
            url: "/xxtj/wcjl",
            data: {
                "xm": xm,
                "sfz": sfz,
                "nl": nl,
                "xb": xb,
                "sb": sb,
                "dhh": dhh,
                "jtrs" : jtrs,
                "wldz": wldz,
                "fxrq": fxrq,
                "fxsj": fxsj,
                "wcyy": wcyy,
                "fxfs": fxfs,
                "lwrq": lwrq,
                "lwsj": lwsj,
                "wcdz": wcdz,
                "wcrq": wcrq,
                "wcsj": wcsj,
                "mfsj": mfsj
            },
            type: "post",
            datatype: "text",
            success: function (data) {
               if (data==="success"){
                   $("#input_div").hide();
                   $("#inputSuccess").show();
                   //跳转
                   setInterval(go, 1000);
               }else {
                   alert("系统出错了,暂时无法提交，请采用其他方式...")
               }
            },
            error: function () {
                alert("系统出错！");
            }
        });

    } else {
        alert("信息未填写完整")
    }
}
