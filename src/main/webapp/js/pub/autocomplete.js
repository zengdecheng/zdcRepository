define([], function() {
	
	var txt_id;
	var source;
	var callback;
	var isNew = false;
	var currentItemIndex = -1;
	var timer;
	var timeParam;
	var fn = {
		init : function(){
			txt_id = arguments[0] || null;
			source = arguments[1] || null;
			callback = arguments[2] || null;
			isNew = arguments[3] || false;
			
			if(txt_id){
				biz.bind();
			}
			
			if(!!$("#div_auto").length){
				$("#div_auto").remove();
			}
			
			$("body").delegate("div","click",function(){
				var $id = $(this).attr("id");
				if($id !== "div_auto"){
					$("#div_auto").hide();
				}
			});
		}
	};

	var biz = {
		bind : function(){
			if($("#" + txt_id).length === 1){
				$("#" + txt_id).on("input propertychange", biz.event.autoComplate);
				$("#" + txt_id).on("keydown", biz.event.txtKeydown);
			}
		},
		event : {
			autoComplate : function(){
				clearTimeout(timer);
				//timer = setTimeout(biz.event.timeOutAjaxGetSource($(this)),1000);
				//timer = setTimeout(biz.event.ajaxGetSource,1000,$(this));
				//setTimeout("setword("+word+")",wordspeed);应该有语法错误
				//换成setTimeout("'setword("+word+")'",wordspeed); 
				timeParam = $(this);
				timer = setTimeout(biz.event.ajaxGetSource,1000,$(this));
				//timer = setTimeout("biz.event.ajaxGetSource("+o+")",1000); 
				
				
			},
			timeOutAjaxGetSource : function ($this){
				return biz.event.ajaxGetSource ($this);
			},
			ajaxGetSource : function($this){
				if(source){
					var txt = timeParam.val();
					if(txt){
						
						if($("#div_auto").length === 0){
							var div = '<div id="div_auto" class="container"></div>';
							$("body").append(div);
							$("#div_auto").css("top", $("#" + txt_id).offset().top + $("#" + txt_id).height() + 5 + "px");
							$("#div_auto").css("left", $("#" + txt_id).offset().left + "px");
							$("#div_auto").css("min-width", $("#" + txt_id).width() + "px");
							
							$("#div_auto").delegate("div[id^='div_item_']", "click", biz.event.itemClick);
							$("#div_auto").delegate("div[id^='div_item_']", "mouseover", biz.event.itemMouseOver);
							$("#div_auto").delegate("div[id^='div_item_']", "mouseout", biz.event.itemMouseOut);
						}
						
						$.get(source + "?txt=" + encodeURI(txt),function(data){
							
							$("#div_auto").empty();
							currentItemIndex = -1;
							
							if(!!data && data.length > 0){
								for(var i = 0,j = 0, l = data.length; i < l; i++){
									var newStr = "<span class='match'>" + txt + "</span>";
									$("#div_auto").append('<div id="div_item_' + j + '" itemid="' + data[i].id + '" itemname="' + data[i].name + '" class="item">' + data[i].name.replace(txt, newStr) + "</div>");
									j++;
								}
								$("#div_auto").show();
							}else{
								if($("div[id^='div_item_']").length === 0 && isNew){
									$("#div_auto").append('<div id="btn_add" class="add">&nbsp;暂无匹配，<span style="color:blue;">+新增供应商</span></div>');
									$("#div_auto").show();
									$("#btn_add").on("click", function(){
										callback && callback(null, $("#" + txt_id).val());
									});
								}
							}
						});
						
					}else{
						if($("#div_auto").length !== 0){
							$("#div_auto").empty().hide();
						}
					}
				}
			},
			txtKeydown : function(e){
				e = window.event || e;
				var l = $("#div_auto").find("div").length;
				switch (e.keyCode) {
					case 38:
						if (currentItemIndex > 0) {
							currentItemIndex--;
						} else {
							currentItemIndex = l - 1;
						}
						biz.event.itemUpDown();
					break;
					case 40:
						if (currentItemIndex < l - 1) {
							currentItemIndex++;
						} else {
							currentItemIndex = 0;
						}
						biz.event.itemUpDown();
					break;
					case 13:
						if(callback && currentItemIndex > -1){
							
							var itemiId = $("#div_item_" + currentItemIndex).attr("itemid");
							var itemName = $("#div_item_" + currentItemIndex).attr("itemname");
							
							callback(itemiId, itemName);
						}
						$("#div_auto").empty().hide();
						currentItemIndex = -1;
					break;
				}
				
			},
			itemUpDown : function(){
				if(currentItemIndex > -1){
					$("#" + txt_id).val($("#div_item_" + currentItemIndex).attr("itemname"));
					$("div[id^='div_item_']").removeClass("select").addClass("normal");
					$("#div_item_" + currentItemIndex).addClass("select");
				}
			},
			itemMouseOver : function(){
				currentItemIndex = $(this).attr("id").split("_").pop();
				$("div[id^='div_item_']").removeClass("select").addClass("normal");
				$(this).removeClass("normal").addClass("select");
			},
			itemMouseOut : function(){
				$(this).removeClass("select").addClass("normal");
			},
			itemClick : function(){
				var itemiId = $(this).attr("itemid");
				var itemName = $(this).attr("itemname");
				$("#" + txt_id).val(itemName);
				$("#div_auto").empty().hide();
				currentItemIndex = -1;
				
				if(callback){
					callback(itemiId, itemName);
				}
			}
		}
	}
	
	return fn;
});