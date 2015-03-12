define([],function () {
	var paging = {
		/*翻页绑定*/
		bind:function(callback,id){
			var paging_id = 'xbd_paging_by_holly';
			if (id && id.length > 0) {
				paging_id = id;
				$("#xbd_paging_by_holly").attr('id',id);
			}
			$("#" + paging_id).delegate("a","click",function(){
				var pageno = $(this).attr("pageno");
				if(pageno){
					callback(pageno);
				}
			});
			var btn_id = 'btn_go',number_id = 'txt_number';
			if (id && id.length > 0) {
				btn_id = id + '_btn_go';
				number_id = id + '_txt_number';
				$('#btn_go').attr('id',btn_id);
				$('#txt_number').attr('id',number_id);
			}
			
			$("#" + btn_id).on("click",function(){
				/\d/.test($.trim($("#" + number_id).val())) && callback($.trim($("#" + number_id).val()));
			});
		}
    };
    return paging;    
});
