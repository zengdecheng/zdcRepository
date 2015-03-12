define(function() {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var styleCode = undefined;
	var biz = {
		init : function() {
//			styleCode = {"picFront":"http://localhost:8080/file/oa/workflow/pic/2014-12-02/pic/1417491217877.jpg","customerName":"1313气场女装原创设计","picBack":"http://localhost:8080/file/oa/workflow/pic/2014-12-02/pic/1417491217878.jpg","craft":"印花,绣花,订珠,打揽,洗水","category":"ZK-针织裤子","styleCode":"ZK000003","mr":"红棉","sales":"范遥","description":"女印花,绣花,订珠,打揽,洗水针织裤子","customerCode":"131132","code":0,"type":"女","msg":"查询成功"};
			if(undefined == styleCode || null == styleCode){
				biz.event.getstyleCode();
			}
			biz.event.showStyleCode();
		},
		bind : function() {

		},
		event : {
			getstyleCode : function() {
				var styleId = $("#styleId").val();
				var url = _url_prefix + "ext/getClothesStyleById?styleId="
						+ styleId;
				$.ajax({
					url : url,
					type : "post",
					async : false,
					success : function(data) {
						if ("0" == data.code) {
							styleCode = data;
						}
					}
				});
			},
			showStyleCode : function() {
				if(undefined == styleCode || null == styleCode){
					biz.event.getstyleCode();
				}
//				resMap.put("category", clothesStyle.getCategory() + "-"
//						+ categoryMap.get(clothesStyle.getCategory())); // 品类
//				resMap.put("type", clothesStyle.getType()); // 男/女款
//				resMap.put("description", clothesStyle.getDescription());// 描述
//				resMap.put("styleCode", clothesStyle.getCategory()
//						+ clothesStyle.getSerialNum());// 款号
//				resMap.put("craft", clothesStyle.getCraft());// 特殊工艺
//				resMap.put("customerCode", clothesStyle.getCustomerCode()); // 客户编号
//				resMap.put("customerName", clothesStyle.getCustomerName()); // 客户名称
//				resMap.put("sales", XbdBuffer.getStaffNameById(clothesStyle
//						.getStaffId()));// 销售
//				resMap.put("mr", clothesStyle.getMrName());// Mr
//				resMap.put("picFront", clothesStyle.getPictureFront());// 图片正面
//				resMap.put("picBack", clothesStyle.getPictureBack());// 图片正面
				$("#category").text(styleCode.category);
				$("#type").text(styleCode.type);
				$("#description").text(styleCode.description);
				$("#styleCode").text(styleCode.styleCode);
				$("#craft").text(styleCode.craft);
				$("#customerCode").text(styleCode.customerCode);
				$("#customerName").text(styleCode.customerName);
				$("#sales").text(styleCode.sales);
				$("#mr").text(styleCode.mr);
				$("#picFront").attr("src", styleCode.picFront);
				$("#picBack").attr("src", styleCode.picBack);
			}
		}
	}
	return fn;
});
