
var redisKey = "";

//关键词质量度数据加载
var loadKeywordQualityData = function () {

    $.ajax({
        url: "/keywordQuality/list",
        dataType: "json",
        data: {
            redisKey: redisKey,
            fieldName: category,
            sort: sort,
            limit: limit
        },
        success: function (data, textStatus, jqXHR) {
            for (var k = 0; k <= 10; k++) {
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
                for (var i = 0; i <= 10; i++) {
                    var grade = reports[i]["grade"];
                    $.each(reports[i]["reportList"], function (j, item) {
                        var _class = "";
                        if (j % 2 == 0) {
                            _class = "list2_box1";
                        } else {
                            _class = "list2_box2";
                        }

                        var _div = "<div><ul class='" + _class + "'><li></li><li><span>" + item.keywordName + "</span></li>" +
                            "<li>" + item.pcImpression + "</li><li>" + item.pcClick + "</li><li>" + item.pcCtr + "%</li><li>" + item.pcCost + "</li>" +
                            "<li>" + item.pcCpc + "</li><li>" + item.pcConversion + "</li></ul></div>";
                        $("#keywordQuality" + grade).append(_div);
                    });
                }
            }
        }
    });
};