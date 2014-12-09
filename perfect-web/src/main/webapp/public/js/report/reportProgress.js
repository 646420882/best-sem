$(function(){
    jQuery.fn.anim_progressbar = function (aOptions) {
        if ((navigator.userAgent.indexOf('MSIE') >= 0)
            && (navigator.userAgent.indexOf('Opera') < 0)){
            document.execCommand("Stop");
        }else{
            window.stop();
        }
        // def values
        var iCms = 1000;
        var iMms = 60 * iCms;
        var iHms = 3600 * iCms;
        var iDms = 24 * 3600 * iCms;
        var roundr = Math.round(Math.random()*10+70);
        var canshu = 0;var iPerc = 0;
        var dian = 0;
        var aString ="";

        // def options
        var aDefOpts = {
                start: new Date(), // now
        finish: new Date().setTime(new Date().getTime() + 60 * iCms), // now + 60 sec
        interval: 100
    }
    var aOpts = jQuery.extend(aDefOpts, aOptions);
    var vPb = this;
    // each progress bar
    return this.each(
    function() {
        var iDuration = aOpts.finish - aOpts.start;
        // calling original progressbar
        $(vPb).children('.pbar').progressbar();
        // looping process
        var vInterval = setInterval(
        function(){
            if(iPerc < roundr){
                var iLeftMs = aOpts.finish - new Date(); // left time in MS
                var stopdate =new Date();
                var iElapsedMs = new Date() - aOpts.start; // elapsed time in MS
                var iDays = parseInt(iLeftMs / iDms); // elapsed days
                var iHours = parseInt((iLeftMs - (iDays * iDms)) / iHms); // elapsed hours
                var iMin = parseInt((iLeftMs - (iDays * iDms) - (iHours * iHms)) / iMms); // elapsed minutes
                var iSec = parseInt((iLeftMs - (iDays * iDms) - (iMin * iMms) - (iHours * iHms)) / iCms); // elapsed seconds
                iPerc = (iElapsedMs > 0) ? iElapsedMs / iDuration * 100 : 0; // percentages
            }else{
                if(canshu == 1){
                    var iLeftMs = aOpts.finish - (stopdate+(new Date()-stopdate)); // left time in MS
                    var iDays = parseInt(iLeftMs / iDms); // elapsed days
                    var iHours = parseInt((iLeftMs - (iDays * iDms)) / iHms); // elapsed hours
                    var iMin = parseInt((iLeftMs - (iDays * iDms) - (iHours * iHms)) / iMms); // elapsed minutes
                    var iSec = parseInt((iLeftMs - (iDays * iDms) - (iMin * iMms) - (iHours * iHms)) / iCms); // elapsed seconds
                    iPerc = iPerc+1.1;
                }else{
                    iPerc = iPerc+0.005;
                }
            }
            $(document).ajaxComplete(function (event, req, settings) {
                canshu = 1;
            });
            // display current positions and progress
            $(vPb).children('#percentNumber').html('<b>'+iPerc.toFixed(1)+'%</b>');

            if (iPerc <= 100) {
                if (dian <= 5) {
                    dian++;
                    for (var s = 1; s <= dian; s++) {
                        aString = aString + ".";
                    }
                    if (dian >= 5) {
                        dian = 0;
                        aString = "";
                    }
                }
            }
           /* $(vPb).children("#Tips").html("小提示：生成报告时，选择时间范围越广，花费时间越长。")*/
            $(vPb).children('.elapsed').html("报告生成中！请稍后"+aString);
            $(vPb).children('.pbar').children('.ui-progressbar-value').css('width', iPerc+'%');
            $("#jindut").val(iPerc.toFixed(1));
            // in case of Finish
            if (iPerc >= 100) {
                clearInterval(vInterval);
                $(vPb).children('.percent').html('<b>100%</b>');
                $(vPb).children('.elapsed').html('Finished');
            }
        } ,aOpts.interval
    );
    }
    );
}

});
