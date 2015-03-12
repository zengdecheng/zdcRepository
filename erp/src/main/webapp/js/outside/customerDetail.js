define(function() {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var customer = undefined;
	var biz = {
		init : function() {
//			customer = {"customerName":"美杜莎","memo":"电话联系人是崔小姐，不负责店铺的运营","link":"http://shop34215409.taobao.com","code":0,"type":"淘宝商家","msg":"查询成功","stage":"1","clothClass":"女装","level":"0","ww":"jysimon","source":"自己开发","customerCode":"MDS003","addTime":1381542720000};
			if(undefined == customer || null == customer){
				biz.event.getCustomerDetail();
			}
			biz.event.showCustomerDetail();
		},
		bind : function() {

		},
		event : {
			getCustomerDetail : function() {
				var customerId = $("#customerId").val();
				var url = _url_prefix + "ext/customerDetail?customerId="
						+ customerId;
				$.ajax({
					url : url,
					type : "post",
					async : false,
					success : function(data) {
						if ("0" == data.code) {
							customer = data;
						}
					}
				});
			},
			showCustomerDetail : function() {
				if(undefined == customer || null == customer){
					biz.event.getCustomerDetail();
				}
				var stages = {"1":"首次联系","2":"多次跟进","3":"首次面谈","4":"多次面谈","5":"模拟报价","6":"首次打版","7":"多次打版","8":"首单OEM","9":"翻单OEM","10":"大客户"};
				$("#name").text(customer.customerName);
				$("#code").text(customer.customerCode);
				$("#level").text(customer.level);
				$("#source").text(customer.source);
				$("#type").text(customer.type);
				$("#stage").text(stages[customer.stage]);
				$("#ww").text(customer.ww);
				$("#link").text(customer.link);
				$("#clothClass").text(customer.clothClass);
				$("#addTime").text(customer.addTime);
				$("#memo").text(customer.memo);
			}
		}
	}
	return fn;
});
