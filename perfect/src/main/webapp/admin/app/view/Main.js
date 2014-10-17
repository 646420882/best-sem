/**
 * Created by XiaoWei on 2014/10/13.
 */
Ext.define("Perfect.view.Main",{
    extends:"Ext.container.Container",
    alias: 'widget.app-mains',
    viewModel:{
        type:"main"
    },
    layout:{
        type:"border"
    },
    items:[
        {
            xtype:"panel",
            bind:{
                title:"{name}"
            },
            region:"west",
            html: '<ul><li>ABCJDDD area is commonly used for navigation, for example, using a "tree" component.</li></ul>',
            width:250,
            split:true,
            tbar:[{
                text:"button"

            }]
        },{
            region: 'center',
            xtype: 'tabpanel',
            items:[{
                title: 'Tab 1',
                html: '<h2>Content appropriate for the current navigation.</h2>'
            },{
                title: 'Tab 2',
                html: '<h1>Content style navigation of width p</h1>'
            }]
        },
        {
            xtype:'panel',
            region:'north',
            height: 50,
            html:'<div style="background-color:gray; font-size:30px; height:50px">DemoX</div>'
        },
        {
            xtype:'toolbar',
            region:'south',
            ui: 'footer',
            margin: '7 0 0 0',
            items: ['就绪', '->', '&copy; 2014 成都松石科技有限公司']
        }
    ]
});