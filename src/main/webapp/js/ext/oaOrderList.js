$(document).ready(function(){
	var cusCode = $("#cusCode").val();
	var url = _url_prefix + "ext/getOrderByCusCode?cusCode=" + cusCode;
    $.ajax({
        url: url,
        type: "post",
        data:"orderList",
//        data = {"code":0,"orderList":[{"id":2798,"isUrgent":"1","wfStepName":"确定定金","styleCode":"T4023","totalRest":"超时15小时8分","orderPlanOffset":"超时1天13小时","nodePlanOffset":"超时1天13小时","type":"样衣打版","operator":"阮星竹","city":"广州"},{"id":2788,"isUrgent":"1","wfStepName":"采购面料","styleCode":"T4011","totalRest":"超时1天18小时","orderPlanOffset":"超时1天8小时","nodePlanOffset":"超时1天3小时","type":"样衣打版","operator":"李小龙","city":"广州"},{"id":2776,"isUrgent":"1","wfStepName":"验布、裁剪","styleCode":"TZ3489B","totalRest":"超时1天0小时","orderPlanOffset":"超时16小时56分","nodePlanOffset":"超时9小时22分","type":"大货生产","operator":"陆小凤","city":null},{"id":2758,"isUrgent":"1","wfStepName":"订单完成","styleCode":"TZ3987","totalRest":"剩余1小时3分","orderPlanOffset":"剩余22小时34分","nodePlanOffset":"超时2小时42分","type":"样衣打版","operator":"温黛黛","city":null},{"id":2729,"isUrgent":"1","wfStepName":"尾查","styleCode":"T3490","totalRest":"超时3天6小时","orderPlanOffset":"超时1天1小时","nodePlanOffset":"超时1天8小时","type":"大货生产","operator":"萧峰","city":null},{"id":2724,"isUrgent":"1","wfStepName":"订单完成","styleCode":"TZ3489","totalRest":"超时18小时0分","orderPlanOffset":"剩余7小时44分","nodePlanOffset":"超时8小时19分","type":"样衣打版","operator":"温黛黛","city":null},{"id":2714,"isUrgent":"1","wfStepName":"订单完成","styleCode":"T3509","totalRest":"超时1天15小时","orderPlanOffset":"剩余4小时37分","nodePlanOffset":"剩余1小时29分","type":"大货生产","operator":"萧峰","city":null},{"id":2709,"isUrgent":"1","wfStepName":"订单完成","styleCode":"T3676","totalRest":"超时1天12小时","orderPlanOffset":"超时12小时11分","nodePlanOffset":"剩余52分35秒","type":"大货生产","operator":"萧峰","city":null},{"id":2690,"isUrgent":"1","wfStepName":"订单完成","styleCode":"YR3927","totalRest":"超时2天1小时","orderPlanOffset":"超时1天14小时","nodePlanOffset":"超时1小时47分","type":"样衣打版","operator":"温黛黛","city":null},{"id":2662,"isUrgent":"1","wfStepName":"订单完成","styleCode":"T3676","totalRest":"超时18小时29分","orderPlanOffset":"剩余2小时31分","nodePlanOffset":"超时11小时54分","type":"样衣打版","operator":"温黛黛","city":null},{"id":2661,"isUrgent":"1","wfStepName":"订单完成","styleCode":"T3509","totalRest":"超时1天5小时","orderPlanOffset":"超时8小时31分","nodePlanOffset":"超时1小时0分","type":"样衣打版","operator":"温黛黛","city":null},{"id":2645,"isUrgent":"1","wfStepName":"订单完成","styleCode":"T3490","totalRest":"剩余1天3小时","orderPlanOffset":"剩余1天13小时","nodePlanOffset":"超时2小时13分","type":"样衣打版","operator":"温黛黛","city":null},{"id":2623,"isUrgent":"1","wfStepName":"打板","styleCode":"T3607","totalRest":"超时8天12小时","orderPlanOffset":"超时8天3小时","nodePlanOffset":"超时4天5小时","type":"样衣打版","operator":"段誉","city":null},{"id":2619,"isUrgent":"1","wfStepName":"订单完成","styleCode":"TZ3489","totalRest":"超时16小时1分","orderPlanOffset":"超时9小时55分","nodePlanOffset":"剩余17分0秒","type":"大货生产","operator":"萧峰","city":null},{"id":2527,"isUrgent":"1","wfStepName":"订单完成","styleCode":"T3397","totalRest":"超时3天0小时","orderPlanOffset":"超时1天13小时","nodePlanOffset":"剩余1小时53分","type":"大货生产","operator":"萧峰","city":null},{"id":2515,"isUrgent":"1","wfStepName":"订单完成","styleCode":"TZ3616","totalRest":"超时3天9小时","orderPlanOffset":"超时1天0小时","nodePlanOffset":"剩余1小时53分","type":"大货生产","operator":"萧峰","city":null},{"id":2456,"isUrgent":"1","wfStepName":"订单完成","styleCode":"T3490","totalRest":"超时9小时35分","orderPlanOffset":"超时3小时28分","nodePlanOffset":"超时6小时18分","type":"样衣打版","operator":"温黛黛","city":null},{"id":2455,"isUrgent":"1","wfStepName":"订单完成","styleCode":"TZ3616","totalRest":"剩余0分0秒","orderPlanOffset":"剩余21小时25分","nodePlanOffset":"剩余0分0秒","type":"样衣打版","operator":"温黛黛","city":null},{"id":2454,"isUrgent":"1","wfStepName":"订单完成","styleCode":"T3676","totalRest":"超时3小时59分","orderPlanOffset":"剩余6小时3分","nodePlanOffset":"超时7小时36分","type":"样衣打版","operator":"温黛黛","city":null},{"id":2430,"isUrgent":"1","wfStepName":"订单完成","styleCode":"D3553","totalRest":"超时1天10小时","orderPlanOffset":"剩余1小时36分","nodePlanOffset":"剩余1小时23分","type":"大货生产","operator":"萧峰","city":null},{"id":2405,"isUrgent":"1","wfStepName":"订单完成","styleCode":"P3397","totalRest":"超时20小时5分","orderPlanOffset":"剩余1小时49分","nodePlanOffset":"超时11小时5分","type":"样衣打版","operator":"温黛黛","city":null},{"id":2395,"isUrgent":"1","wfStepName":"订单完成","styleCode":"T3602","totalRest":"超时9小时10分","orderPlanOffset":"超时2小时57分","nodePlanOffset":"超时9小时10分","type":"样衣打版","operator":"温黛黛","city":null},{"id":2372,"isUrgent":"1","wfStepName":"订单完成","styleCode":"TZ3489","totalRest":"超时4天17小时","orderPlanOffset":"超时2天11小时","nodePlanOffset":"剩余1小时57分","type":"大货生产","operator":"萧峰","city":null},{"id":2370,"isUrgent":"1","wfStepName":"订单完成","styleCode":"D3553","totalRest":"超时4小时38分","orderPlanOffset":"剩余1小时19分","nodePlanOffset":"超时1小时58分","type":"样衣打版","operator":"温黛黛","city":null},{"id":2309,"isUrgent":"1","wfStepName":"订单完成","styleCode":"TZ3489","totalRest":"超时1小时40分","orderPlanOffset":"剩余10小时11分","nodePlanOffset":"超时3小时32分","type":"样衣打版","operator":"温黛黛","city":null},{"id":1963,"isUrgent":"1","wfStepName":"订单完成","styleCode":"D3044","totalRest":"超时14小时15分","orderPlanOffset":"剩余1小时4分","nodePlanOffset":"超时22分17秒","type":"样衣打版","operator":"景天","city":null}],"msg":"查询成功"}
        success: function (data) {
            var list = data.orderList;
            console.log(list);
            if(data.code == 0){
                $.each(list, function( k , v ){
                	var tr = "<tr>" + "<td><a href='Orderinfo.jsp?orderId="+v.id+"'>" + cusCode + "</a></td>" + "<td>";
                    if("0" == v.isUrgent) {
                    	tr += "<div class='jiaji'>[加急]</div>";
                    }
                    tr += v.styleCode+"</td>" +
                    "<td>"+ v.wfStepName+"</td>" +
                    "<td class='abc'>"+ v.nodePlanOffset+"</td>" +
                    "<td class='abc'>"+v.orderPlanOffset+"</td>" +
                    "<td class='abc'>"+ v.totalRest+"</td>" +
                    "<td>"+ v.type+"</td>" +
                    "<td>"+ v.city+"</td>" +
                    "<td>"+v.operator+"</td>" +
                    "<td><a href='oaOrderSpeed.jsp?orderId="+v.id+"'>详情</a></td>" +
                    "</tr>";
                    $("#divHeaderID tbody").append(tr);
                });
               b();
            }
        }
    });

    function b(){
        $(".abc").each(function(i, n) {
            if ($(n).text().indexOf("超时") > -1) {
                $(n).addClass("red");
            }else{
                $(n).addClass("green");
            }
        });
    }
});


