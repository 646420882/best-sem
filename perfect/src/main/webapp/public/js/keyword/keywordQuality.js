//关键词质量度数据加载
var loadKeywordQualityData = function () {

    $.ajax({
        url: "/keywordQuality/list",
        dataType: "json",
        data: {
            fieldName: category,
            sort: sort,
            limit: limit
        },
        success: function (data, textStatus, jqXHR) {
            for (var k = 0; k <= 10; k++) {
                $("#keywordQuality" + k).empty();
                $("#quality" + k).empty();
            }

            var qualityDTO = data.qualityDTO0;
            var lis = "";
            if (qualityDTO != null) {
                lis = "<li>" + qualityDTO["keywordQty"] + "(" + qualityDTO["keywordQtyRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["impression"] + "(" + qualityDTO["impressionRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["click"] + "(" + qualityDTO["clickRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["ctr"] + "%</li>" +
                    "<li>" + qualityDTO["cost"] + "(" + qualityDTO["costRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["cpc"] + "</li>" +
                    "<li>" + qualityDTO["conversion"] + "(" + qualityDTO["conversionRate"] + "%)" + "</li>";
                $("#quality0").append(lis);
            }

            qualityDTO = data.qualityDTO1;
            if (qualityDTO != null) {
                lis = "<li>" + qualityDTO["keywordQty"] + "(" + qualityDTO["keywordQtyRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["impression"] + "(" + qualityDTO["impressionRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["click"] + "(" + qualityDTO["clickRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["ctr"] + "%</li>" +
                    "<li>" + qualityDTO["cost"] + "(" + qualityDTO["costRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["cpc"] + "</li>" +
                    "<li>" + qualityDTO["conversion"] + "(" + qualityDTO["conversionRate"] + "%)" + "</li>";
                $("#quality1").append(lis);
            }
            qualityDTO = data.qualityDTO2;
            if (qualityDTO != null) {
                lis = "<li>" + qualityDTO["keywordQty"] + "(" + qualityDTO["keywordQtyRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["impression"] + "(" + qualityDTO["impressionRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["click"] + "(" + qualityDTO["clickRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["ctr"] + "%</li>" +
                    "<li>" + qualityDTO["cost"] + "(" + qualityDTO["costRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["cpc"] + "</li>" +
                    "<li>" + qualityDTO["conversion"] + "(" + qualityDTO["conversionRate"] + "%)" + "</li>";
                $("#quality2").append(lis);
            }
            qualityDTO = data.qualityDTO3;
            if (qualityDTO != null) {
                lis = "<li>" + qualityDTO["keywordQty"] + "(" + qualityDTO["keywordQtyRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["impression"] + "(" + qualityDTO["impressionRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["click"] + "(" + qualityDTO["clickRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["ctr"] + "%</li>" +
                    "<li>" + qualityDTO["cost"] + "(" + qualityDTO["costRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["cpc"] + "</li>" +
                    "<li>" + qualityDTO["conversion"] + "(" + qualityDTO["conversionRate"] + "%)" + "</li>";
                $("#quality3").append(lis);
            }
            qualityDTO = data.qualityDTO4;
            if (qualityDTO != null) {
                lis = "<li>" + qualityDTO["keywordQty"] + "(" + qualityDTO["keywordQtyRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["impression"] + "(" + qualityDTO["impressionRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["click"] + "(" + qualityDTO["clickRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["ctr"] + "%</li>" +
                    "<li>" + qualityDTO["cost"] + "(" + qualityDTO["costRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["cpc"] + "</li>" +
                    "<li>" + qualityDTO["conversion"] + "(" + qualityDTO["conversionRate"] + "%)" + "</li>";
                $("#quality4").append(lis);
            }
            qualityDTO = data.qualityDTO5;
            if (qualityDTO != null) {
                lis = "<li>" + qualityDTO["keywordQty"] + "(" + qualityDTO["keywordQtyRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["impression"] + "(" + qualityDTO["impressionRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["click"] + "(" + qualityDTO["clickRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["ctr"] + "%</li>" +
                    "<li>" + qualityDTO["cost"] + "(" + qualityDTO["costRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["cpc"] + "</li>" +
                    "<li>" + qualityDTO["conversion"] + "(" + qualityDTO["conversionRate"] + "%)" + "</li>";
                $("#quality5").append(lis);
            }
            qualityDTO = data.qualityDTO6;
            if (qualityDTO != null) {
                lis = "<li>" + qualityDTO["keywordQty"] + "(" + qualityDTO["keywordQtyRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["impression"] + "(" + qualityDTO["impressionRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["click"] + "(" + qualityDTO["clickRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["ctr"] + "%</li>" +
                    "<li>" + qualityDTO["cost"] + "(" + qualityDTO["costRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["cpc"] + "</li>" +
                    "<li>" + qualityDTO["conversion"] + "(" + qualityDTO["conversionRate"] + "%)" + "</li>";
                $("#quality6").append(lis);
            }
            qualityDTO = data.qualityDTO7;
            if (qualityDTO != null) {
                lis = "<li>" + qualityDTO["keywordQty"] + "(" + qualityDTO["keywordQtyRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["impression"] + "(" + qualityDTO["impressionRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["click"] + "(" + qualityDTO["clickRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["ctr"] + "%</li>" +
                    "<li>" + qualityDTO["cost"] + "(" + qualityDTO["costRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["cpc"] + "</li>" +
                    "<li>" + qualityDTO["conversion"] + "(" + qualityDTO["conversionRate"] + "%)" + "</li>";
                $("#quality7").append(lis);
            }
            qualityDTO = data.qualityDTO8;
            if (qualityDTO != null) {
                lis = "<li>" + qualityDTO["keywordQty"] + "(" + qualityDTO["keywordQtyRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["impression"] + "(" + qualityDTO["impressionRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["click"] + "(" + qualityDTO["clickRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["ctr"] + "%</li>" +
                    "<li>" + qualityDTO["cost"] + "(" + qualityDTO["costRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["cpc"] + "</li>" +
                    "<li>" + qualityDTO["conversion"] + "(" + qualityDTO["conversionRate"] + "%)" + "</li>";
                $("#quality8").append(lis);
            }
            qualityDTO = data.qualityDTO9;
            if (qualityDTO != null) {
                lis = "<li>" + qualityDTO["keywordQty"] + "(" + qualityDTO["keywordQtyRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["impression"] + "(" + qualityDTO["impressionRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["click"] + "(" + qualityDTO["clickRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["ctr"] + "%</li>" +
                    "<li>" + qualityDTO["cost"] + "(" + qualityDTO["costRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["cpc"] + "</li>" +
                    "<li>" + qualityDTO["conversion"] + "(" + qualityDTO["conversionRate"] + "%)" + "</li>";
                $("#quality9").append(lis);
            }
            qualityDTO = data.qualityDTO10;
            if (qualityDTO != null) {
                lis = "<li>" + qualityDTO["keywordQty"] + "(" + qualityDTO["keywordQtyRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["impression"] + "(" + qualityDTO["impressionRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["click"] + "(" + qualityDTO["clickRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["ctr"] + "%</li>" +
                    "<li>" + qualityDTO["cost"] + "(" + qualityDTO["costRate"] + "%)" + "</li>" +
                    "<li>" + qualityDTO["cpc"] + "</li>" +
                    "<li>" + qualityDTO["conversion"] + "(" + qualityDTO["conversionRate"] + "%)" + "</li>";
                $("#quality10").append(lis);
            }


            var results = data.quality0;
            if (results != null && results.length > 0) {
                $.each(results, function (i, item) {
                    var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                        "<li>" + item.pcImpression + "</li><li>" + item.pcClick + "</li><li>" + item.pcCtr + "%</li><li>" + item.pcCost + "</li>" +
                        "<li>" + item.pcCpc + "</li><li>" + item.pcConversion + "</li></ul></div>";
                    $("#keywordQuality0").append(_div);
                })
            }
            results = data.quality1;
            if (results != null && results.length > 0) {
                $.each(results, function (i, item) {
                    var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                        "<li>" + item.pcImpression + "</li><li>" + item.pcClick + "</li><li>" + item.pcCtr + "%</li><li>" + item.pcCost + "</li>" +
                        "<li>" + item.pcCpc + "</li><li>" + item.pcConversion + "</li></ul></div>";
                    $("#keywordQuality1").append(_div);
                })
            }
            results = data.quality2;
            if (results != null && results.length > 0) {
                $.each(results, function (i, item) {
                    var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                        "<li>" + item.pcImpression + "</li><li>" + item.pcClick + "</li><li>" + item.pcCtr + "%</li><li>" + item.pcCost + "</li>" +
                        "<li>" + item.pcCpc + "</li><li>" + item.pcConversion + "</li></ul></div>";
                    $("#keywordQuality2").append(_div);
                })
            }
            results = data.quality3;
            if (results != null && results.length > 0) {
                $.each(results, function (i, item) {
                    var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                        "<li>" + item.pcImpression + "</li><li>" + item.pcClick + "</li><li>" + item.pcCtr + "%</li><li>" + item.pcCost + "</li>" +
                        "<li>" + item.pcCpc + "</li><li>" + item.pcConversion + "</li></ul></div>";
                    $("#keywordQuality3").append(_div);
                })
            }
            results = data.quality4;
            if (results != null && results.length > 0) {
                $.each(results, function (i, item) {
                    var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                        "<li>" + item.pcImpression + "</li><li>" + item.pcClick + "</li><li>" + item.pcCtr + "%</li><li>" + item.pcCost + "</li>" +
                        "<li>" + item.pcCpc + "</li><li>" + item.pcConversion + "</li></ul></div>";
                    $("#keywordQuality4").append(_div);
                })
            }
            results = data.quality5;
            if (results != null && results.length > 0) {
                $.each(results, function (i, item) {
                    var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                        "<li>" + item.pcImpression + "</li><li>" + item.pcClick + "</li><li>" + item.pcCtr + "%</li><li>" + item.pcCost + "</li>" +
                        "<li>" + item.pcCpc + "</li><li>" + item.pcConversion + "</li></ul></div>";
                    $("#keywordQuality5").append(_div);
                })
            }
            results = data.quality6;
            if (results != null && results.length > 0) {
                $.each(results, function (i, item) {
                    var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                        "<li>" + item.pcImpression + "</li><li>" + item.pcClick + "</li><li>" + item.pcCtr + "%</li><li>" + item.pcCost + "</li>" +
                        "<li>" + item.pcCpc + "</li><li>" + item.pcConversion + "</li></ul></div>";
                    $("#keywordQuality6").append(_div);
                })
            }
            results = data.quality7;
            if (results != null && results.length > 0) {
                $.each(results, function (i, item) {
                    var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                        "<li>" + item.pcImpression + "</li><li>" + item.pcClick + "</li><li>" + item.pcCtr + "%</li><li>" + item.pcCost + "</li>" +
                        "<li>" + item.pcCpc + "</li><li>" + item.pcConversion + "</li></ul></div>";
                    $("#keywordQuality7").append(_div);
                })
            }
            results = data.quality8;
            if (results != null && results.length > 0) {
                $.each(results, function (i, item) {
                    var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                        "<li>" + item.pcImpression + "</li><li>" + item.pcClick + "</li><li>" + item.pcCtr + "%</li><li>" + item.pcCost + "</li>" +
                        "<li>" + item.pcCpc + "</li><li>" + item.pcConversion + "</li></ul></div>";
                    $("#keywordQuality8").append(_div);
                })
            }
            results = data.quality9;
            if (results != null && results.length > 0) {
                $.each(results, function (i, item) {
                    var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                        "<li>" + item.pcImpression + "</li><li>" + item.pcClick + "</li><li>" + item.pcCtr + "%</li><li>" + item.pcCost + "</li>" +
                        "<li>" + item.pcCpc + "</li><li>" + item.pcConversion + "</li></ul></div>";
                    $("#keywordQuality9").append(_div);
                })
            }
            results = data.quality10;
            if (results != null && results.length > 0) {
                $.each(results, function (i, item) {
                    var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                        "<li>" + item.pcImpression + "</li><li>" + item.pcClick + "</li><li>" + item.pcCtr + "%</li><li>" + item.pcCost + "</li>" +
                        "<li>" + item.pcCpc + "</li><li>" + item.pcConversion + "</li></ul></div>";
                    $("#keywordQuality10").append(_div);
                })
            }
        }
    });
};