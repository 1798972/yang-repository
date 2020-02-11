/*根据县区查找*/
//城市数组
var changsha4301 = ["FRQ芙蓉区", "TXQ天心区", "YLQ岳麓区", "KFQ开福区", "YHQ雨花区", "WCQ望城区", "CSX长沙县", "NXX宁乡县", "NYS浏阳市"];
var zhuzhou4302 = ["HTQ荷塘区", "LSQ芦淞区", "SFQ石峰区", "TYQ天元区", "YLQ云龙区", "ZZX株洲县", "YX攸县", "CLX茶陵县", "YLX炎陵县", "LLS醴陵市"];
var xiangtan4303 = ["YHQ雨湖区", "YTQ岳塘区", "XTX湘潭县", "XXS湘乡市", "SSS韶山市"];
var zhangjiajie4308 = ["YDQ永定区", "WLYQ武陵源区", "CLX慈利县", "SZX桑植县"];
var yiyang4309 = ["ZYQ资阳区", "HSQ赫山区", "GXQ高新区", "DTHQ大通湖区", "NX南县", "AHX安化县", "TJX桃江县", "YJS沅江市"];
var tujiamiaozuzizhizhou4331 = ["JSS吉首市", "LXX泸溪县", "FHX凤凰县", "HYX花垣县", "BJX保靖县", "GZX古丈县", "YSX永顺县", "LSX龙山县"];
var hengyang4304 = ["ZZQ珠晖区", "YFQ雁峰区", "SGQ石鼓区", "ZXQ蒸湘区", "NYQ南岳区", "HYX衡阳县", "HNX衡南县", "HSX衡山县", "HDX衡东县", "QDX祁东县", "LYS耒阳市", "CNS常宁市"];
var shaoyang4305 = ["SQQ双清区", "DXQ大祥区", "BTQ北塔区", "SDX邵东县", "XSX新邵县", "SYX邵阳县", "LHX隆回县", "DKX洞口县", "SNX绥宁县", "XNX新宁县", "CBMZZZX城步苗族自治县", "WGS武冈市"];
var yongzhou4311 = ["LLQ零陵区", "LSTQ冷水滩区", "QYX祁阳县", "DAX东安县", "SPX双牌县", "DX道县", "JYX江永县", "NYX宁远县", "LSX蓝山县", "XTX新田县", "JHYZZZX江华瑶族自治县"];
var loudi4325 = ["LXQ娄星区", "LSJS冷水江市", "LYS涟源市", "SFX双峰县", "XHX新化县"];
var yueyang4306 = ["YYLQ岳阳楼区", "YXQ云溪区", "JSQ君山区", "YYX岳阳县", "HRX华容县", "XYX湘阴县", "PJX平江县", "MLS汨罗市", "LXS临湘市"];
var changde4307 = ["WLQ武陵区", "DCQ鼎城区", "AXX安乡县", "HSX汉寿县", "LX澧县", "LLX临澧县", "TYX桃源县", "SMX石门县", "JSS津市市"];
var chenzhou4310 = ["BHQ北湖区", "SXQ苏仙区", "GYX桂阳县", "YZX宜章县", "YXX永兴县", "JHX嘉禾县", "LWX临武县", "RCX汝城县", "GDX桂东县", "ARX安仁县", "ZXS资兴市"];
var huaihua4312 = ["HCQ鹤城区", "HJQ洪江区", "ZFX中方县", "YLX沅陵县", "CXX辰溪县", "XPX溆浦县", "HTX会同县", "MYMZZZX麻阳苗族自治县", "XHDZZZX新晃侗族自治县", "ZJDZZZX芷江侗族自治县", "JZMZDZZZX靖州苗族侗族自治县", "TDDZZZX通道侗族自治县", "HJS洪江市"];

//县城名字以及id
var resCityIdAndCountyName = new Map();

//根据前两个字符获取城市的id
function findCityId(str) {
    resCityIdAndCountyName.clear();
    //cityMap存储id以及市包含的县城
    var cityMap = {
        4301: changsha4301,
        4302: zhuzhou4302,
        4303: xiangtan4303,
        4308: zhangjiajie4308,
        4309: yiyang4309,
        4331: tujiamiaozuzizhizhou4331,
        4304: hengyang4304,
        4305: shaoyang4305,
        4311: yongzhou4311,
        4325: loudi4325,
        4306: yueyang4306,
        4307: changde4307,
        4310: chenzhou4310,
        4312: huaihua4312
    };

    for (var k in cityMap) {
        //获取v值  eg:cityMap[4301]       	
        for (j = 0; j < cityMap[k].length; j++) {
            char2 = cityMap[k][j].substr(0, 2);
            if (char2.toLowerCase() == str.toLowerCase()) {
                //截取中文
                var name = cityMap[k][j];
                var reg = /[\u4e00-\u9fa5]/g;
                //县城名字
                var conutyName = name.match(reg).join("");
// 				alert(conutyName+"--"+k);
                //放置市区的id以及县城的名字
                resCityIdAndCountyName.set(k, conutyName);
                //		for (var [key, value] of resCityIdAndCountyName) {
                //		console.log(key+'---'+ value); 
                //		}
            }
        }
    }
    return resCityIdAndCountyName;
}

//根据id查找城市车牌号
function findOneCityName(id) {
    var flag = "error";
    //nameMap存储id以及市名字
    var nameMap = new Map();
    nameMap.set(4301, "长沙市-湘A");
    nameMap.set(4302, "株洲市-湘B");
    nameMap.set(4303, "湘潭市-湘C");
    nameMap.set(4308, "张家界市-湘G");
    nameMap.set(4309, "益阳市-湘H");
    nameMap.set(4331, "湘西土家族苗族自治州-湘U");
    nameMap.set(4304, "衡阳市-湘D");
    nameMap.set(4305, "邵阳市-湘E");
    nameMap.set(4311, "永州市-湘M");
    nameMap.set(4325, "娄底市-湘K");
    nameMap.set(4306, "岳阳市-湘F");
    nameMap.set(4307, "常德市-湘J");
    nameMap.set(4310, "郴州市-湘L");
    nameMap.set(4312, "怀化市-湘N");
    for (var [key, value] of nameMap) {
        if (id == key) {
            flag = nameMap.get(key);
        }
    }
    return flag;
}

//返回结果数组
function findBy2char() {
    //记录查询结果
    var resStringList = new Array();
    //获取的城市简拼
    var city2char = $("#cityName").val();
    //查找得到城市id以及县城名字
    findCityId(city2char);

    var i = 1;
    for (var [key, value] of resCityIdAndCountyName) {
//			alert(key);

        //根据城市id找到城市名字-车牌号
        oneCityName = findOneCityName(key);
//			console.log(oneCityName);

        //将查找结果拼接成字符串
        var resString = resCityIdAndCountyName.get(key) + "—[" + key + "]—" + oneCityName;
        //将结果放进resString中
        resStringList.push(resString);
        i++;
    }
    //for (var i = 0; i < resStringList.length; i++) {
    //	console.log(resStringList[i]);
    //}
    //console.log("____________________________");
    return resStringList;
}