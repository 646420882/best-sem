jQuery("#KeywordTable").jqGrid({
    datatype: "json",
    url: '/assistantKeyword/list',
    mtype: "POST",
    jsonReader: {
        root: "rows",
        records: "records",
        repeatitems: false
    },
    postData:{
        'aid':null,
        'cid':null,
        'pageNo':0,
        'pageSize':20
    },
    shrinkToFit: true,//此选项用于根据width计算每列宽度的算法,默认值true
    colModel: [
        {label: ' 关键词名称', name: 'keyword', sortable: false, align: 'center', hidden: true},
        {label: ' 关键词状态', name: 'status', sortable: false, width: 200, align: 'center'},
        {label: ' 启动/暂停', name: 'pause', sortable: false, align: 'center', hidden: true},
        {label: ' 出价', name: 'price', sortable: false, align: 'center', hidden: true},
        {label: ' 计算机质量度', name: 'matchType', sortable: false, align: 'center', hidden: true},
        {label: ' 计算机质量度', name: 'phraseType', sortable: false, align: 'center', hidden: true},
        {label: ' 匹配模式', name: 'matchType', sortable: false, align: 'center'},
        {label: ' 访问URL', name: 'pcDestinationUrl', sortable: false, width: 80, align: 'center'},
        {label: ' 移动访问URL', name: 'mobileDestinationUrl', sortable: false, width: 80, align: 'center'}
    ],
    rowNum: 20,// 默认每页显示记录条数
    rownumbers: false,
    loadui: 'disable',
    pgbuttons: false,
    autowidth: true,
    altRows: true,
    altclass: 'list2_box2',
    resizable: true,
    viewrecords : true,
    sortorder : "desc",
    loadonce : true,
    caption : "Load Once Example"
});