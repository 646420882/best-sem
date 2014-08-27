var loadExistsCampaign = function () {
    $.ajax({
        url: '/campaign/getAllCampaign',
        dataType: 'json',
        success: function (data, textStatus, jqXHR) {
            $("#existsCampaign ul").empty();
            var results = data.rows;
            if (results.length > 0) {
                $.each(results, function (i, item) {
                    var _li = "";
                    if (i % 2 == 0) {
                        _li = "<li><input id='" + item.campaignId + "' type='checkbox' name='campaign_exists'>" + item.campaignName + "</li>";
                    } else {
                        _li = "<li><input id='" + item.campaignId + "' type='checkbox' class='current' name='campaign_exists'>" + item.campaignName + "</li>";
                    }
                    $("#existsCampaign ul").append(_li);
                });
            }
        }
    });
};

var loadNewCampaignData = function () {
    $.ajax({
        url: '/account/getNewCampaign',
        dataType: 'json',
        success: function (data, textStatus, jqXHR) {
            $("#newCampaign ul").empty();
            var results = data.rows;
            if (results.length > 0) {
                $.each(results, function (i, item) {
                    var _li = "";
                    if (i % 2 == 0) {
                        _li = "<li><input id='" + item.campaignId + "' type='checkbox' name='campaign_new'>" + item.campaignName + "</li>";
                    } else {
                        _li = "<li><input id='" + item.campaignId + "' type='checkbox' class='current' name='campaign_new'>" + item.campaignName + "</li>";
                    }
                    $("#newCampaign ul").append(_li);
                });
            }
        }
    });
};

var updateAllCampaign = function () {
    ;
};

var updateExistsCampaign = function () {
    var campaignIds = [];
    $.each($("input[name='campaign_exists']:checked"), function (i, item) {
        if(item.checked){
            campaignIds.push(item.id);
        }
    });

    $.ajax({
        url: '/account/updateAccountData',
        type: 'POST',
        dataType: 'json',
        data: {
            "campaignIds": JSON.stringify(campaignIds)
        },
        success: function (data, textStatus, jqXHR) {
            alert("**************");
        }
    });
};

var updateNewCampaign = function () {
    var campaignIds = [];
    $.each($("input[name='campaign_new']:checked"), function (i, item) {
        if(item.checked){
            campaignIds.push(item.id);
        }
    });

    $.ajax({
        url: '/account/updateAccountData',
        type: 'POST',
        dataType: 'json',
        data: {
            "campaignIds": JSON.stringify(campaignIds)
        },
        success: function (data, textStatus, jqXHR) {
            alert("**************");
        }
    });
};

var index = "";


$(function () {
    loadExistsCampaign();

    loadNewCampaignData();

    $("input[name=no1]").livequery('click', function () {
        $.each($("input[name=no1]"), function (i, item) {
            if (item.checked) {
                if (i == 0) {
                    index  = i + 1;
                } else if (i == 1) {
                    index  = i + 1;
                } else if (i == 2) {
                    index  = i + 1;
                }
            }
        });
    });

    $("#downloadAccount").livequery('click', function () {
        if(index  = 1){}else if(index  = 2){
            updateExistsCampaign();
        }else if(index  = 3){}
    });

    //update
    /*$.ajax({
     url: '/account/updateAccountData',
     type: 'POST',
     async: false,
     dataType: 'json',
     data: {},
     success: function (data, textStatus, jqXHR) {
     alert(data.status);
     }
     });*/
});