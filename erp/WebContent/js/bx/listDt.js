requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"n": "jquery.nyroModal.pack",
		"f": "jquery.form",
		"u": "util",
		"p": "paging"
	}
});
define(["n","f","u","p"], function(n,f,u,p) {
	var fn = {
		init : function(){
			biz.bind();
			p.bind(biz.event.list);
		}
	};
	
	var biz = {
		bind : function(){
			//绑定查询的点击事件
			$("select[name='fsp.map.type']").on("change",biz.event.search);
			$("#add_new").off().on("click", function () {
				 biz.initAddDiv();
				 $.nyroModalManual({
				    url: "#add_div"
			    });
			});
			$(".c_dt_edit").off().on("click", function () {
				 biz.initEditDiv(this);
				 $.nyroModalManual({
				    url: "#edit_div"
			    });
			});
			
			$(".c_dt_del").off().on("click",function(){
				 if(confirm("是否将词条记录删除?")){
					 var dt_id = $(this).attr("dt-id");
						$.get("/mr/delDt?mrDt.id="+dt_id,function(msg){
							alert("删除成功");
							window.location.reload();
						});
				}
				
			});
			$("#add_sel").on("change",biz.initAddDiv);
			
			$("#add_submit").on("click",biz.event.addSave);
			$("#edit_submit").on("click",biz.event.editSave);
			
			//配置默认字段显示
			var elsInputs = [ "#a_sz_txt1,#a_sz_txt2,#a_sz_txt3,#e_sz_txt1,#e_sz_txt2,#e_sz_txt3" ];
			u.vInput($(elsInputs.join(",")), '#333', '#BAB6B7');
		},
		initAddDiv : function(){
			if($("#add_sel option:selected").val() == "13"||$("#add_sel option:selected").val() == "18"){
				$("#add_normal").hide();
				$("#a_sz_txt1").val("");
				$("#a_sz_txt2").val("");
				$("#a_sz_txt3").val("");
				$("#add_special").show();
			}else{
				$("#add_dt_value").val("");
				$("#add_normal").show();
				$("#add_special").hide();
				$("#add_unit").text($("#add_sel option:selected").attr("unit"));
			}
		},
		initEditDiv : function(o){
			$("#edit_label").text($(o).attr("dt-memo"));
			$("#edit_type_sel").val($(o).attr("dt-type"));
			$("#edit_id_sel").val($(o).attr("dt-id"));
			$("#edit_dt_inx").val($(o).attr("dt-inx"));
			
			if($(o).attr("dt-type") == "13"||$("#add_sel option:selected").val() == "18"){
				$("#edit_normal").hide();
				$("#edit_special").show();
				$("[name^='edit_special_txt']").val("");
				var tmpValue = $(o).attr("dt-value");
				if(tmpValue.indexOf("x") != -1){
					$("[name='edit_special_rd'][value='2']").attr("checked",'checked');
					var ss  = tmpValue.split("x");
					$("[name='edit_special_txt_2']").each(function(i){
						$(this).val(ss[i]);
					});
				}else{
					$("[name='edit_special_rd'][value='1']").attr("checked",'checked');
					$("[name='edit_special_txt_1']").val($(o).attr("dt-value"));
				}
				
			}else{
				$("#edit_normal").show();
				$("#edit_special").hide();
				
				$("#edit_dt_value").val($(o).attr("dt-value"));
				$("#edit_unit").text($(o).attr("dt-unit"));
			}
		},
		event : {
			addSave : function(){
				var dt_type = $("#add_sel option:selected").val();
				var dt_value = "";
				var dt_inx = $("#add_dt_inx").val();
				var ifValidate = true;
				if(dt_type == "13"||dt_type == "18" ){
					var sel_rd = $("input:radio[name='add_special_rd']:checked").val();
					$("input[name='add_special_txt_"+sel_rd+"']").each(function(){
						if(isNaN($(this).val())){
							alert("输入的纱织数有错误");
							$(this).focus();
							ifValidate = false;
							return;
						};
						if(dt_value != ""){
							dt_value = dt_value +"x" ;
						}
						dt_value = dt_value + $(this).val();
					});
				}else{
					var input_o = $("#add_dt_value");
					var add_unit = $("#add_unit").text();
					dt_value =  $.trim(input_o.val());
					if(dt_value === ""){
						alert("输入不能为空");
						ifValidate = false;
						input_o.focus();
						return;
					}
					if(add_unit != ""){ //如果有单位，认为必须输入数字
						if(isNaN(dt_value)){
							alert("输入的值必须是数字");
							ifValidate = false;
							input_o.focus();
							return;
						};
					};
				}
				if(!ifValidate){
					return;
				}
				var str = "/mr/saveDt?mrDt.type="+dt_type+"&mrDt.value="+encodeURI(dt_value)+"&mrDt.inx="+dt_inx;
				$.post(str,function(msg){
					if(msg == "0"){ 
						alert("提交成功");
						window.location.reload();
					}else if(msg == "2"){
						alert("不能保存呢，同类型下有重复值");
					}else{
						alert("提交失败");
					}
				});
			},
			editSave : function(){
				
				var dt_type = $("#edit_type_sel").val();
				var dt_id = $("#edit_id_sel").val();
				var dt_value = "";
				var dt_inx = $("#edit_dt_inx").val();
				var ifValidate = true;
				if(dt_type == "13"||dt_type == "18"){
					var sel_rd = $("input:radio[name='edit_special_rd']:checked").val();
					$("input[name='edit_special_txt_"+sel_rd+"']").each(function(){
						if(isNaN($(this).val())){
							alert("输入的纱织数有错误");
							$(this).focus();
							ifValidate = false;
							return;
						};
						if(dt_value != ""){
							dt_value = dt_value +"x" ;
						}
						dt_value = dt_value + $(this).val();
					});
				}else{
					var input_o = $("#edit_dt_value");
					var edit_unit = $("#edit_unit").text();
					dt_value =  $.trim(input_o.val());
					if(dt_value === ""){
						alert("输入不能为空");
						ifValidate = false;
						input_o.focus();
						return;
					}
					if(edit_unit != ""){ //如果有单位，认为必须输入数字
						if(isNaN(dt_value)){
							alert("输入的值必须是数字");
							ifValidate = false;
							input_o.focus();
							return;
						};
					};
				}
				if(!ifValidate){
					return;
				}
				var str = "/mr/saveDt?mrDt.type="+dt_type+"&mrDt.id="+dt_id+"&mrDt.inx="+dt_inx+"&mrDt.value="+encodeURI(dt_value);
				$.post(str,function(msg){
					if(msg == "0"){ 
						alert("提交成功");
						window.location.reload();
					}else if(msg == "2"){
						alert("不能提交同类型下相同的值");
					}else {
						alert("提交失败");
					}
				});
			},
			search : function(){
				biz.event.list();
			},
			list : function(){
				var pageNo = arguments[0] || 1;
				
				if($("[name='fsp.map.type'] option:selected").val() == '0'){
					$("[name='fsp.map.type']").addClass("notselect");
				}
				var queryString = $("select[name='fsp.map.type']").not(".notselect").fieldSerialize();
				
				window.location.href = "/mr/listDt?fsp.pageNo=" +pageNo+"&"+queryString;
			},
			add : function(){
				window.location.href = "/mr/addFabric";
			},
			edit : function(){
				var id = $(this).attr("id").split("_").pop();
				window.location.href = "/mr/editFabric?id=" + id;
			}
		}
	}
	return fn;
});