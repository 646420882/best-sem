/**
 * Created by XiaoWei on 2014/10/31.
 */
Ext.define('Perfect.view.tbar.HykToolbar', {
    extend:'Ext.toolbar.Toolbar',
    alias:'widget.hyktbar',
    bodyPadding: 10,
    items: [
        {
            fieldLabel: '选择行业',
            xtype: 'combobox',
            query: 'remote',
            id: 'tradeComboBox',
            displayField: 'trade',
            valueField: 'trade',
            emptyText: '行业..',
            allowBlank: false,
            afterLabelTextTpl: required,
            msgTarget: 'side',
            editable: false,
            store: storeTr,
            listeners: {
                change: function () {
                    var newUrl = '../person/findPager?trade='+this.getValue()+'';
                    hykStore.proxy.setUrl(newUrl);
                    var combox = Ext.getCmp("cateCombobox");
                    combox.setStore(Ext.StoreManager.lookup("cateStore").load({
                        params: {
                            trade: this.getValue()
                        }
                    }));
                    combox.setDisabled(false);
                }
            }
        },{
            xtype: 'combobox',
            query: 'remote',
            fieldLabel: '类别',
            id: "cateCombobox",
            emptyText: '类别..',
            allowBlank: false,
            afterLabelTextTpl: required,
            msgTarget: 'side',
            displayField: 'category',
            valueField: 'category',
            disabled:true,
            editable: false,
            store:null,
            listeners:{
                change:function(){
                    var _trade=Ext.getCmp("tradeComboBox").getValue();
                    var newUrl = '../person/findPager?trade='+_trade+'&&category='+this.getValue()+'';
                    hykStore.proxy.setUrl(newUrl);
                }
            }
        },{
            text:'查询',
            handler:function(){
                var _trade=Ext.getCmp("tradeComboBox");
                var _category=Ext.getCmp("cateCombobox");
                if(_trade.isValid()){
                    hykStore.load();
                }

            }
        }
    ]
});