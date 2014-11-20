function getTrendAnalysisData() {
    $.ajax({
        url:"/pftstis/getTrendAnalysisData",
        type:"post",
        dataType:"json",
        success: function (data) {
            alert(JSON.stringify(data));
        }
    });
}

getTrendAnalysisData();