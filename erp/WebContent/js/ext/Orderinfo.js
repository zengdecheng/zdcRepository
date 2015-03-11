$(document).ready(function(){
	var orderId = $("#orderId").val();
	var url = _url_prefix + "ext/getOrderInfo?orderId=" + orderId;
    $.ajax({
        url: url,
        type: "post",
        data:"order",
        success: function (data) {
            var list = data.order;
                if(data.code == 0){
                    $("#divContID ol").append(
                        "<li>订单类型："+list.type+"</li>"+
                        "<li>加急程度："+list.isUrgent+"</li>" +
                        "<li>关联订单："+list.oaOrder+"</li>" +
                        "<li>客户编号："+list.cusCode+"</li>" +
                        "<li>业务担当："+list.operator+"</li>" +
                        "<li>订单编号："+list.orderCode+"</li>" +
                        "<li>订单款号："+list.styleCode+"</li>" +
                        "<li>所属品类："+list.clothClass+"</li>" +
                        "<li>付款方式："+list.payType+"</li>" +
                        "<li>预计数量："+list.wantCnt+"</li>" +
                        "<li>预计交期："+list.endTime+"</li>" +
                        "<li>合同条款特殊说明："+list.memo+"</li>"
                        )
                if(list.isUrgent == 1){
                    $("#divContID ol li:eq(1)").text("加急程度：不加急");
                }else{
                    $("#divContID ol li:eq(1)").text("加急程度：加急");
                }
            }
        }
    });
});


