/**
 * Created by XiaoWei on 2014/7/25.
 */
$.fn.extend({
    grid:function(cfg){
        function gridView(_this,config){
            var ths = _this.find("th");
            var currentPage = config.start;
            var maxPage = 0;
            var grid;


            if(_this.find(".grid-view-td").size()==0){
                $("<tr><td class='grid-view-td' colspan='"+ths.length+"'></td></tr>").
                    appendTo(_this);
                $("<tr><td class='grid-pagingbar' colspan='"+ths.length+"'>当前页:<span class='cur-page'/> 跳转至:<input class='go-page'/><a class='go-btn'>Go</a></td></tr>").
                    appendTo(_this);

                _this.find(".grid-pagingbar > .go-btn").click(function(){
                    var p = _this.find(".grid-pagingbar").find(".go-page").val();
                    if(p!=""){
                        grid.load(parseInt(p));
                    }
                });
                var leftBtn = $("<div class='grid-arrow-left'>").appendTo(_this).hover(function(){
                    this.className = "grid-arrow-left-hover";
                },function(){
                    this.className = "grid-arrow-left";
                }).click(function(){
                    grid.load(currentPage-1);
                });
                var rightBtn = $("<div class='grid-arrow-right'>").appendTo(_this).hover(function(){
                    this.className = "grid-arrow-right-hover";
                },function(){
                    this.className = "grid-arrow-right";
                }).click(function(){
                    grid.load(currentPage+1);
                });
                _this.hover(function(){
                    var scrollOffset = _this.parents(".jspPane").css("top");
                    scrollOffset = isNaN(parseInt())?0:Math.abs(parseInt(scrollOffset));
                    leftBtn.css({
                        left:_this.offset().left+15,
                        top:_this.offset().top+scrollOffset+_this.height()/2-29
                    });
                    rightBtn.css({
                        left:_this.offset().left+(_this.width()-25),
                        top:_this.offset().top+scrollOffset+_this.height()/2-29
                    });
                    rightBtn.show();
                    leftBtn.show();
                },function(){
                    rightBtn.hide();
                    leftBtn.hide();
                });
            }

            function createTable(rows,state){
                _this.find(".grid-view").remove();
                var view = $("<table class='grid-view' width='100%'>");
                view.appendTo(_this.find(".grid-view-td"));
                $.each(rows,function(row,entity){
                    var tr =$("<tr class='grid-data'>").appendTo(view).click(function(){
                        if(config.rowclick){
                            config.rowclick(row,entity);
                        }
                    });
                    $.each(ths,function(column,th){
                        var value = entity[$(th).attr("field")];

                        if(value&&$(th).attr("type")=="date"){
                            var date = (value.year+1900)+"/"+(value.month+1)+"/"+value.date;
                            value = date;
                        }
                        else if(value&&$(th).attr("type")=="datetime"){
                            var date = (value.year+1900)+"/"+(value.month+1)+"/"+value.date+" "+value.hours+":"+value.minutes;
                            value = date;
                        }


                        var td = $("<td>").appendTo(tr).css("width",$(th).width()).addClass($(th).attr("tdclass"));
                        var temp_value = config.render?config.render(row,column,value,entity,tr):value;
                        value = temp_value?temp_value:value;
                        if(typeof(value)=="object"){
                            value.appendTo(td);
                        }else{
                            td.html(value+"");
                        }

                        if(column==0){
                            td.css("borderLeft","0px");
                        }
                    });
                });
            }

            function gridClass(){
                this.load = function (start){
                    if(start==-1){
                        start = 1;
                        currentPage = 0;
                        maxPage =0;
                    }

                    var param = config.where?config.where():{};
                    param["pageSize"] =15;// config.pageSize;
                    if(maxPage==0||(start!=currentPage&&start>=1&&start<=maxPage)){
                        param.start = start;
                        $.get(config.url,param,function(json){
                            var pager = eval("("+json+")");
                            maxPage = pager.maxPage;
                            if(config.load){
                                config.load(pager.rows);
                            }
                            createTable(pager.rows);
                            currentPage = start;
                            _this.find(".grid-pagingbar > .cur-page").text(start+"/"+maxPage);
                        });
                    }
                };
            }

            grid = new gridClass();
            grid.load(currentPage);

            return grid;
        }
        return gridView(this,cfg);
    }
})