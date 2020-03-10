Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};


//查询今日外出
function findTodayAllWc() {
    //存储的 03月6日 所以月要MM
    var toady = new Date().format("MM月d日");
    $.ajax({
        url: "/gljm/findToadyAllWc",
        data: {
            "today": toady
        },
        type: "post",
        datatype: "json",
        success: function (data) {
            if (data !== null) {
                str = "";
                for (i = 0; i < data.length; i++) {
                    str += "<tr>" +
                        "<td align='center'>" + (i + 1) + "</td>" +
                        "<td align='center'>" + data[i].xm + "</td>" +
                        "<td align='center'>" + data[i].xb + "</td>" +
                        "<td align='center'>" + data[i].nl + "</td>" +
                        "<td align='center' style='mso-number-format:\\@'>" + data[i].sfz + "</td>" +
                        "<td align='center'>" + data[i].sb + "</td>" +
                        "<td align='center'>" + data[i].dhh + "</td>" +
                        "<td align='center'>" + data[i].wldzAndFxrqAndFxsj + "</td>" +
                        "<td align='center'>" + data[i].wcyy + "</td>" +
                        "<td align='center'>" + data[i].lwrqAndLwsj + "</td>" +
                        "<td align='center'>" + data[i].wcdzAndWcrqAndWcsj + "</td>" +

                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +

                        "<td align='center'>" + data[i].fxfs + "</td>" +

                        "<td align='center'>" +"无" + "</td>" +

                        "<td align='center'>" + data[i].mfsj + "</td>" +

                        "<td align='center'>" +"范清香" + "</td>" +
                        "<td align='center'>" +"已返回" + "</td>" +

                        "</tr>";
                }
                $("#myTBody1").html(str);
                $("#tableDiv2").hide();
                $("#info_div").hide();
                $("#tableDiv1").show();
            }
            if (data.length === 0) {
                $("#info_div").show();
            }
        },
        error: function () {
        }
    });

}

//查询今日新增
function findTodayAllXz() {
    //存储的 03月6日 所以月要MM
    var toady = new Date().format("MM月d日");
    $.ajax({
        url: "/gljm/findTodayAllXz",
        data: {
            "today": toady
        },
        type: "post",
        datatype: "json",
        success: function (data) {
            if (data !== null) {
                str = "";
                for (i = 0; i < data.length; i++) {
                    str += "<tr>" +
                        "<td align='center'>" + (i + 1) + "</td>" +
                        "<td align='center'>" + data[i].xm + "</td>" +
                        "<td align='center'>" + data[i].xb + "</td>" +
                        "<td align='center'>" + data[i].nl + "</td>" +
                        "<td align='center' style='mso-number-format:\\@'>" + data[i].sfz + "</td>" +
                        "<td align='center'>" + data[i].sb + "</td>" +
                        "<td align='center'>" + data[i].dhh + "</td>" +
                        "<td align='center'>" + data[i].wldzAndFxrqAndFxsj + "</td>" +
                        "<td align='center'>" + data[i].wcyy + "</td>" +
                        "<td align='center'>" + data[i].lwrqAndLwsj + "</td>" +

                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +

                        "<td align='center'>" + data[i].fxfs + "</td>" +

                        "<td align='center'>" +"无" + "</td>" +

                        "<td align='center'>" + data[i].mfsj + "</td>" +
                        "<td align='center'>" + data[i].dcry + "</td>" +
                        "<td align='center'>" + data[i].jtrs + "</td>" +

                        "<td align='center'>" +"待定" + "</td>" +
                        "<td align='center'>" +"新增" + "</td>" +

                        "</tr>";
                }
                $("#myTBody2").html(str);
                $("#tableDiv1").hide();
                $("#info_div").hide();
                $("#tableDiv2").show();
            }
            if (data.length === 0) {
                $("#info_div").show();
            }
        },
        error: function () {
        }
    });
}

//根据日期查询外出
function findWcByRq() {
    //存储的 03月6日 所以月要MM
    var rq = $("#input_cxrq").val();
    $.ajax({
        url: "/gljm/findWcByRq",
        data: {
            "rq": rq
        },
        type: "post",
        datatype: "json",
        success: function (data) {
            if (data !== null) {
                str = "";
                for (i = 0; i < data.length; i++) {
                    str += "<tr>" +
                        "<td align='center'>" + (i + 1) + "</td>" +
                        "<td align='center'>" + data[i].xm + "</td>" +
                        "<td align='center'>" + data[i].xb + "</td>" +
                        "<td align='center'>" + data[i].nl + "</td>" +
                        "<td align='center' style='mso-number-format:\\@'>" + data[i].sfz + "</td>" +
                        "<td align='center'>" + data[i].sb + "</td>" +
                        "<td align='center'>" + data[i].dhh + "</td>" +
                        "<td align='center'>" + data[i].wldzAndFxrqAndFxsj + "</td>" +
                        "<td align='center'>" + data[i].wcyy + "</td>" +
                        "<td align='center'>" + data[i].lwrqAndLwsj + "</td>" +
                        "<td align='center'>" + data[i].wcdzAndWcrqAndWcsj + "</td>" +

                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +

                        "<td align='center'>" + data[i].fxfs + "</td>" +

                        "<td align='center'>" +"无" + "</td>" +

                        "<td align='center'>" + data[i].mfsj + "</td>" +

                        "<td align='center'>" +"范清香" + "</td>" +
                        "<td align='center'>" +"已返回" + "</td>" +

                        "</tr>";
                }
                $("#myTBody1").html(str);
                $("#tableDiv2").hide();
                $("#info_div").hide();
                $("#tableDiv1").show();
            }
            if (data.length === 0) {
                $("#info_div").show();
            }
        },
        error: function () {
        }
    });
}

//根据日期查询新增
function findXzByRq() {
    var rq = $("#input_cxrq").val();
    $.ajax({
        url: "/gljm/findXzByRq",
        data: {
            "rq": rq
        },
        type: "post",
        datatype: "json",
        success: function (data) {
            if (data !== null) {
                str = "";
                for (i = 0; i < data.length; i++) {
                    str += "<tr>" +
                        "<td align='center'>" + (i + 1) + "</td>" +
                        "<td align='center'>" + data[i].xm + "</td>" +
                        "<td align='center'>" + data[i].xb + "</td>" +
                        "<td align='center'>" + data[i].nl + "</td>" +
                        "<td align='center' style='mso-number-format:\\@'>" + data[i].sfz + "</td>" +
                        "<td align='center'>" + data[i].sb + "</td>" +
                        "<td align='center'>" + data[i].dhh + "</td>" +
                        "<td align='center'>" + data[i].wldzAndFxrqAndFxsj + "</td>" +
                        "<td align='center'>" + data[i].wcyy + "</td>" +
                        "<td align='center'>" + data[i].lwrqAndLwsj + "</td>" +

                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +

                        "<td align='center'>" + data[i].fxfs + "</td>" +

                        "<td align='center'>" +"无" + "</td>" +

                        "<td align='center'>" + data[i].mfsj + "</td>" +
                        "<td align='center'>" + data[i].dcry + "</td>" +
                        "<td align='center'>" + data[i].jtrs + "</td>" +

                        "<td align='center'>" +"待定" + "</td>" +
                        "<td align='center'>" +"新增" + "</td>" +

                        "</tr>";
                }
                $("#myTBody2").html(str);
                $("#tableDiv1").hide();
                $("#info_div").hide();
                $("#tableDiv2").show();
            }
            if (data.length === 0) {
                $("#info_div").show();
            }
        },
        error: function () {
        }
    });
}

//根据名字查询外出
function findWcByXm() {
    //存储的 03月6日 所以月要MM
    var xm = $("#input_srxm").val();
    $.ajax({
        url: "/gljm/findWcByXm",
        data: {
            "xm": xm
        },
        type: "post",
        datatype: "json",
        success: function (data) {
            if (data !== null) {
                str = "";
                for (i = 0; i < data.length; i++) {
                    str += "<tr>" +
                        "<td align='center'>" + (i + 1) + "</td>" +
                        "<td align='center'>" + data[i].xm + "</td>" +
                        "<td align='center'>" + data[i].xb + "</td>" +
                        "<td align='center'>" + data[i].nl + "</td>" +
                        "<td align='center' style='mso-number-format:\\@'>" + data[i].sfz + "</td>" +
                        "<td align='center'>" + data[i].sb + "</td>" +
                        "<td align='center'>" + data[i].dhh + "</td>" +
                        "<td align='center'>" + data[i].wldzAndFxrqAndFxsj + "</td>" +
                        "<td align='center'>" + data[i].wcyy + "</td>" +
                        "<td align='center'>" + data[i].lwrqAndLwsj + "</td>" +
                        "<td align='center'>" + data[i].wcdzAndWcrqAndWcsj + "</td>" +

                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +

                        "<td align='center'>" + data[i].fxfs + "</td>" +

                        "<td align='center'>" +"无" + "</td>" +

                        "<td align='center'>" + data[i].mfsj + "</td>" +

                        "<td align='center'>" +"范清香" + "</td>" +
                        "<td align='center'>" +"已返回" + "</td>" +

                        "</tr>";
                }
                $("#myTBody1").html(str);
                $("#tableDiv2").hide();
                $("#info_div").hide();
                $("#tableDiv1").show();
            }
            if (data.length === 0) {
                $("#info_div").show();
            }
        },
        error: function () {
        }
    });

}

//根据名字查询新增
function findXzByXm() {
    var xm = $("#input_srxm").val();
    $.ajax({
        url: "/gljm/findXzByXm",
        data: {
            "xm": xm
        },
        type: "post",
        datatype: "json",
        success: function (data) {
            if (data !== null) {
                str = "";
                for (i = 0; i < data.length; i++) {
                    str += "<tr>" +
                        "<td align='center'>" + (i + 1) + "</td>" +
                        "<td align='center'>" + data[i].xm + "</td>" +
                        "<td align='center'>" + data[i].xb + "</td>" +
                        "<td align='center'>" + data[i].nl + "</td>" +
                        "<td align='center' style='mso-number-format:\\@'>" + data[i].sfz + "</td>" +
                        "<td align='center'>" + data[i].sb + "</td>" +
                        "<td align='center'>" + data[i].dhh + "</td>" +
                        "<td align='center'>" + data[i].wldzAndFxrqAndFxsj + "</td>" +
                        "<td align='center'>" + data[i].wcyy + "</td>" +
                        "<td align='center'>" + data[i].lwrqAndLwsj + "</td>" +

                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +
                        "<td align='center'>" +" " + "</td>" +

                        "<td align='center'>" + data[i].fxfs + "</td>" +

                        "<td align='center'>" +"无" + "</td>" +

                        "<td align='center'>" + data[i].mfsj + "</td>" +
                        "<td align='center'>" + data[i].dcry + "</td>" +
                        "<td align='center'>" + data[i].jtrs + "</td>" +

                        "<td align='center'>" +"待定" + "</td>" +
                        "<td align='center'>" +"新增" + "</td>" +

                        "</tr>";
                }
                $("#myTBody2").html(str);
                $("#tableDiv1").hide();
                $("#info_div").hide();
                $("#tableDiv2").show();
            }
            if (data.length === 0) {
                $("#info_div").show();
            }
        },
        error: function () {
        }
    });
}

//个人查询界面
function fundWcBySelf() {
    //存储的 03月6日 所以月要MM
    var xm = $("#input_srxm").val();
    $.ajax({
        url: "/gljm/findWcByXm",
        data: {
            "xm": xm
        },
        type: "post",
        datatype: "json",
        success: function (data) {
            if (data !== null) {
                str = "";
                for (i = 0; i < data.length; i++) {
                    str += "<tr>" +
                        "<td align='center'>" + (i + 1) + "</td>" +
                        "<td align='center'>" + data[i].xm + "</td>" +
                        "<td align='center'>" + data[i].xb + "</td>" +
                        "<td align='center'>" + data[i].nl + "</td>" +
                        "<td align='center' style='mso-number-format:\\@'>" + data[i].sfz + "</td>" +
                        "<td align='center'>" + data[i].sb + "</td>" +
                        "<td align='center'>" + data[i].dhh + "</td>" +
                        "<td align='center'>" + data[i].wldzAndFxrqAndFxsj + "</td>" +
                        "<td align='center'>" + data[i].wcyy + "</td>" +
                        "<td align='center'>" + data[i].lwrqAndLwsj + "</td>" +
                        "<td align='center'>" + data[i].wcdzAndWcrqAndWcsj + "</td>" +
                        "<td align='center'>" + data[i].fxfs + "</td>" +
                        "<td align='center'>" + data[i].mfsj + "</td>" +
                        "</tr>";
                }
                $("#myTBody1").html(str);
                $("#tableDiv2").hide();
                $("#info_div").hide();
                $("#tableDiv1").show();
            }
            if (data.length === 0) {
                $("#info_div").show();
            }
        },
        error: function () {
        }
    });

}