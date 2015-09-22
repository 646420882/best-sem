var redisKey = "";
var ie_iframe;

var downloadKeywordQualityCSV = function () {
    var _url = "/keywordQuality/downloadCSV";
    if (!!window.ActiveXObject || "ActiveXObject" in window) {
        document.getElementById("background").style.display = "block";
        document.getElementById("progressBar1").style.display = "block";
        ie_iframe = document.createElement("iframe");
        ie_iframe.id = "downloadhelper_iframe";
        ie_iframe.width = 0;
        ie_iframe.height = 0;
        ie_iframe.src = _url;
        ie_iframe.onload = ie_iframe.onreadystatechange = iframeLoad;
        document.body.appendChild(ie_iframe);
    } else {
        if (window.navigator.userAgent.indexOf("Chrome") != -1) {
            window.open(_url, "");
        } else {
            document.getElementById("background").style.display = "block";
            document.getElementById("progressBar1").style.display = "block";
            var iframe1 = document.createElement("iframe");
            iframe1.id = "downloadhelper_iframe";
            iframe1.width = 0;
            iframe1.height = 0;
            iframe1.src = _url;
            document.body.appendChild(iframe1);
            iframe1.onload = function () {
                document.getElementById("background").style.display = "none";
                document.getElementById("progressBar1").style.display = "none";
            };
        }
    }
};

var iframeLoad = function () {
    if (ie_iframe.readyState == "interactive") {
        document.getElementById('background').style.display = 'none';
        document.getElementById('progressBar1').style.display = 'none';
    }
};

//关键词质量度数据加载
var loadKeywordQualityData = function () {

    var qtrs = $("#keywordQualityTab").find("tr");
    for (var i = 1, l = qtrs.length; i < l; i++) {
        qtrs.eq(i).hide();
    }

    $.ajax({
        url: "/keywordQuality/list",
        dataType: "json",
        async: true,
        data: {
            redisKey: redisKey,
            fieldName: category,
            sort: sort,
            limit: limit
        },
        success: function (data, textStatus, jqXHR) {
            for (var k = 0; k < 11; k++) {
                $("#keywordQuality" + k).empty();
                $("#quality" + k).empty();
            }

            redisKey = data.redisKey;
            var qualityDTO = data.qualityDTO;
            if (qualityDTO != null && qualityDTO.length > 0) {
                $.each(qualityDTO, function (i, item) {
                    var lis = "<li>" + item.keywordQty + "(" + item.keywordQtyRate + "%)" + "</li>" +
                        "<li>" + item.impression + "(" + item.impressionRate + "%)" + "</li>" +
                        "<li>" + item.click + "(" + item.clickRate + "%)" + "</li>" +
                        "<li>" + item.ctr + "%</li>" +
                        "<li>" + item.cost + "(" + item.costRate + "%)" + "</li>" +
                        "<li>" + item.cpc + "</li>" +
                        "<li>" + item.conversion + "(" + item.conversionRate + "%)" + "</li>";
                    $("#quality" + item.grade).append(lis);
                });
            }

            var reports = data.report;
            if (reports != null && reports.length > 0) {
                var map = new Map();

                for (var i = 0; i < 11; i++) {
                    if (reports[i] == undefined)
                        continue;

                    var grade = reports[i]["grade"];
                    $.each(reports[i]["reportList"], function (j, item) {
                        var _class = "";
                        if (j % 2 == 0)
                            _class = "list2_box1";
                        else
                            _class = "list2_box2";

                        var _div = "<div><ul class='" + _class + "'><li class='home_quality'></li><li><span><a href='/assistant/index'>" + item.keywordName + "</a></span></li>" +
                            "<li>" + item.pcImpression + "</li><li>" + item.pcClick + "</li><li>" + item.pcCtr + "%</li><li>" + item.pcCost + "</li>" +
                            "<li>" + item.pcCpc + "</li><li>" + item.pcConversion + "</li></ul></div>";
                        $("#keywordQuality" + grade).append(_div);
                    });
                    map.put(grade * 2, i);
                }

                for (var _k = 1, l = qtrs.length; _k < l; _k++) {
                    if ((_k - 1) % 2 == 0 && map.containsKey(_k - 1)) {
                        qtrs.eq(_k).show();
                        qtrs.eq(_k + 1).show();
                    }
                }

            }
        }
    });
};