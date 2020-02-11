/*根据市区查找*/
/*
 预期输入:cs
 预期输出:
 长沙市--车牌号--id  默认复制'长沙市'/或者按0
 地名
 1.
 2.
 3.
 ...
 * */
//市区名字数组
var cityNames = ["CS4301长沙市", "ZZ4302株洲市", "XT4303湘潭市", "ZJ4303张家界市", "YY4309益阳市", "XX4331湘西土家苗族自治州", "HY4304衡阳市", "SY4305邵阳市", "YZ4311永州市", "LD4325娄底市", "YY4306岳阳市", "CD4307常德市", "CZ4310郴州市", "HH4312怀化市"]

//市区id以及对应的名字
var cityIdAndName = new Map();
//市区id以及市区的城市
var cityIdAndHasCountrys = new Map();
//市区id以及对应的车牌号
var cityIdAndCarNumber = new Map();

//得到城市的名字以及id
function finCityIdAndName(str) {
    //遍历市区名字数组
    for (j = 0; j < cityNames.length; j++) {
        char2 = cityNames[j].substr(0, 2);
        //若相等
        if (char2.toLowerCase() == str.toLowerCase()) {
// 	 		 	console.log(char2)
            //得到市区id
            var cityId = cityNames[j].substr(2, 4);
            var name = cityNames[j];
            var reg = /[\u4e00-\u9fa5]/g;
            //得到市区名字
            var cityName = name.match(reg).join("");
            cityIdAndName.set(cityId, cityName);
        }
    }
    return cityIdAndName;
}

//根据id找到对应的城市以及城市列表
function findCityHasCountrys(id) {
    //cityMap存储id以及市包含的县城
    var cityMap = new Map();
    cityMap.set(4301, ["芙蓉区", "天心区", "岳麓区", "开福区", "雨花区", "望城区", "长沙县", "宁乡县", "浏阳市"]);
    cityMap.set(4302, ["荷塘区", "芦淞区", "石峰区", "天元区", "云龙区", "株洲县", "攸县", "茶陵县", "炎陵县", "醴陵市"]);
    cityMap.set(4303, ["雨湖区", "岳塘区", "湘潭县", "湘乡市", "韶山市"]);
    cityMap.set(4308, ["永定区", "武陵源区", "慈利县", "桑植县"]);
    cityMap.set(4309, ["资阳区", "赫山区", "高新区", "大通湖区", "南县", "安化县", "桃江县", "沅江市"]);
    cityMap.set(4331, ["吉首市", "泸溪县", "凤凰县", "花垣县", "保靖县", "古丈县", "永顺县", "龙山县"]);
    cityMap.set(4304, ["珠晖区", "雁峰区", "石鼓区", "蒸湘区", "南岳区", "衡阳县", "衡南县", "衡山县", "衡东县", "祁东县", "耒阳市", "常宁市"]);
    cityMap.set(4305, ["双清区", "大祥区", "北塔区", "邵东县", "新邵县", "邵阳县", "隆回县", "洞口县", "绥宁县", "新宁县", "城步苗族自治县", "武冈市"]);
    cityMap.set(4311, ["零陵区", "冷水滩区", "祁阳县", "东安县", "双牌县", "道县", "江永县", "宁远县", "蓝山县", "新田县", "江华瑶族自治县"]);
    cityMap.set(4325, ["娄星区", "冷水江市", "涟源市", "双峰县", "新化县"]);
    cityMap.set(4306, ["岳阳楼区", "云溪区", "君山区", "岳阳县", "华容县", "湘阴县", "平江县", "汨罗市", "临湘市"]);
    cityMap.set(4307, ["武陵区", "鼎城区", "安乡县", "汉寿县", "澧县", "临澧县", "桃源县", "石门县", "津市市"]);
    cityMap.set(4310, ["北湖区", "苏仙区", "桂阳县", "宜章县", "永兴县", "嘉禾县", "临武县", "汝城县", "桂东县", "安仁县", "资兴市"]);
    cityMap.set(4312, ["鹤城区", "洪江区", "中方县", "沅陵县", "辰溪县", "溆浦县", "会同县", "麻阳苗族自治县", "新晃侗族自治县", "芷江侗族自治县", "靖州苗族侗族自治县", "通道侗族自治县", "洪江市"]);
    for (var [key, value] of cityMap) {
        if (id == key) {
//       		console.log(id);
//       		console.log(cityMap.get(key))
            cityIdAndHasCountrys.set(id, cityMap.get(key));
        }
    }
    return cityIdAndHasCountrys;
}

//根据id查找到城市对应的车牌号
function findCityAndCarNumber(id) {
    //nameMap存储id以及市名字
    var carNumberMap = new Map();
    carNumberMap.set(4301, "湘A");
    carNumberMap.set(4302, "湘B");
    carNumberMap.set(4303, "湘C");
    carNumberMap.set(4308, "湘G");
    carNumberMap.set(4309, "湘H");
    carNumberMap.set(4331, "湘U");
    carNumberMap.set(4304, "湘D");
    carNumberMap.set(4305, "湘E");
    carNumberMap.set(4311, "湘M");
    carNumberMap.set(4325, "湘K");
    carNumberMap.set(4306, "湘F");
    carNumberMap.set(4307, "湘J");
    carNumberMap.set(4310, "湘L");
    carNumberMap.set(4312, "湘N");
    for (var [key, value] of carNumberMap) {
        if (id == key) {
            cityIdAndCarNumber.set(id, carNumberMap.get(key));
        }
    }
}


//返回结果数组
function findCityBy2char_Name() {
    //每次执行前先清空上次的记录
    cityIdAndName.clear();
    //获取的市区简拼
    var city2char = $("#cityName2").val();
    //结果1
    var cityNameInfo = new Array();
    //cs
    //结果1显示
    //长沙市--湘A--4301
    temp1 = finCityIdAndName(city2char);
    for (var [key, value] of cityIdAndName) {
        findCityAndCarNumber(key);
    }
    var i = 1;
    //拼接结果
    for (var [key, value] of cityIdAndName) {
        var nameTemp = cityIdAndName.get(key) + "—" + cityIdAndCarNumber.get(key) + "—" + key;
        cityNameInfo.push(nameTemp);
        i++;
    }
    return cityNameInfo;
}

//返回包含的县城
function findCityBy2char_Countrys() {
    //每次执行前先清空上次的记录
    cityIdAndHasCountrys.clear();
    //获取的市区简拼
    var city2char = $("#cityName2").val();
    //结果2
    var cityCountrys = new Array();

//		 	temp2 = finCityIdAndName(city2char);
    for (var [key, value] of cityIdAndName) {
        findCityHasCountrys(key);
    }
    //拼接结果
    for (var [key, value] of cityIdAndName) {
        var nameTemp = cityIdAndHasCountrys.get(key);
        cityCountrys.push(nameTemp);
    }
    return cityCountrys;
}
