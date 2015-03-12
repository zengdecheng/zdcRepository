var i=1;
	var total="";
	var _total="";
	var totalmain="";
	var styleCode=new Array();
	//var styleCode=new Array();
	function add(){
		str="style_code_"+i;
		rts="'"+str+"'"
		$("#add_style_code").after('<br id="'+str+'_br"/><input type="text" id="'+str+'" name="styleCode" class="z_inp z_sty1" onblur="style_code_total('+rts+')" onkeyup="value=value.replace(/[\W]/g,"")" onbeforepaste="clipboardData.setData("text",clipboardData.getData("text").replace(/[^\d]/g,"")) style="margin-left:60px;"/><a id="'+str+'_del" href="javascript:del('+rts+')" class="z_del">X</a>');
		i++;
		//bb='onkeyup="value=value.replace(/[\W]/g,"")" onbeforepaste="clipboardData.setData("text",clipboardData.getData("text").replace(/[^\d]/g,""))"';
		//aa="<br id=\""+str+"_br\"/><input type=\"text\" id=\""+str+"\" class=\"z_inp z_sty1\" onblur=\"style_code_total("+rts+")\"/><a id=\""+str+"_del\" href=\"javascript:del("+rts+")\" class=\"z_del\">X</a>";
	}
	function del(a){
		//alert(styleCode[a]);
		delete styleCode[a];
		total='';
		for(var v in styleCode){
			total+=styleCode[v]+",";
		}
		if (total.length > 0) {
			total = total.substr(0, total.length - 1);	
		}
		$("#total").val(total);
		$("#"+a).remove();
		$("#"+a+"_br").remove();
		$("#"+a+"_del").remove();
	}
	//function style_code_total1(){
	//	styleCode['style_code_0']=$("#style_code_0").val();
	//}
	function style_code_total(b){
		if($("#"+b).val()!=''){
			if(styleCode[b]!=$("#"+b).val()){
				styleCode[b]=$("#"+b).val();
				total='';
				for(var e in styleCode){
					total+=styleCode[e]+"_";
				}
				if (total.length > 0) {
					total = total.substr(0, total.length - 1);	
				}
			}
		}
		$("#total").val(total);
		//alert(total.indexOf("123"));
	}
	function payOther1(){
		$("#pay_other").val($("#payother").val());
	}