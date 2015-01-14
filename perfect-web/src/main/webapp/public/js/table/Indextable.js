jQuery("#addcrativeTable").jqGrid({
    datatype: "json",
    url: '',
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
        {label: ' 子链一名称', name: '', sortable: false, align: 'center'},
        {label: ' 子链一URL', name: '', sortable: false, width: 200, align: 'center'},
        {label: ' 子链二名称', name: '', sortable: false, align: 'center'},
        {label: ' 子链二URL', name: '', sortable: false, align: 'center'},
        {label: '子链三名称', name: '', sortable: false, align: 'center'},
        {label: ' 子链三URL', name: '', sortable: false, align: 'center'},
        {label: '子链四名称', name: '', sortable: false, align: 'center'},
        {label: ' 子链四URL', name: '', sortable: false, align: 'center'},
        {label: ' 子链五名称', name: '', sortable: false, align: 'center'},
        {label: ' 子链五URL ', name: '', sortable: false,  align: 'center'}
    ],
    rowNum: 20,// 默认每页显示记录条数
    rownumbers: false,
    loadui: 'disable',
    pgbuttons: false,
    altRows: true,
    altclass: 'list2_box2',
    resizable: true,
    scroll: false,
    autowidth: true,
    shrinkToFit: true,
    forceFit:true

});