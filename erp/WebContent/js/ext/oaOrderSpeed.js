$(document).ready(function(){
	var orderId = $("#orderId").val();
	var url = _url_prefix + "ext/getOrderDetail?orderId=" + orderId;
    $.ajax({
        url: url,
        type: "post",
        data: "orderList",
        success: function (data) {
            var list = data.orderDetailList;
            if(data.code == 0){
                $.each(list, function( k , v ){
                    $("#Speed_tab tbody").append("<tr>" +
                        "<td>"+ v.wfStepName+"</td>" +
                        "<td>"+ v.wfRealStart+"</td>" +
                        "<td>"+ v.wfStepDuration+"</td>" +
                        "<td class='abc'>"+ v.wfRealFinish+"</td>" +
                        "<td class='abc'>"+v.totalOffset+"</td>" +
                        "<td>"+ v.exceptFinish+"</td>" +
                        "<td>"+v.city+"</td>" +
                        "<td>"+ v.operator+"</td>" +
                        "</tr>");
                });
                b();
            }
        }
    });

    function b(){
        $(".abc").each(function(i, n) {
            if ($(n).text().indexOf("超时") > -1) {
                $(n).addClass("z_order_bg1");
            }else{
                $(n).addClass("z_order_bg");
            }
        });
    }

});


