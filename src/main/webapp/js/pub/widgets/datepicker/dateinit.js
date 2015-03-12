define([], function(){
	var dateInit ={
		init:function (){
			$(".dataDate").datepicker({showOn:"button",
	    		showOn: "both",
				buttonImage:"/js/pub/widgets/datepicker/calendar.gif",
				buttonImageOnly:true}).attr("readonly", "readonly");
			$(".beginDate,.endDate").datepicker({ 
					beforeShow: dateInit.customRange,
					showOn: "both",
					buttonImage:"/js/pub/widgets/datepicker/calendar.gif",
					buttonImageOnly:true}).attr("readonly", "readonly");
			$(".beginDate1,.endDate1").datepicker({ 
				beforeShow: dateInit.customRange1,
				showOn: "both",
				buttonImage:"/js/pub/widgets/datepicker/calendar.gif",
				buttonImageOnly:true}).attr("readonly", "readonly");
		},
		customRange:function (input) { 
		    return {minDate: ($(input).hasClass("endDate")  ? $(".beginDate").datepicker("getDate") : null), 
		        maxDate: ($(input).hasClass("beginDate") ? $(".endDate").datepicker("getDate") : null)}; 
		},
		customRange1:function (input) { 
		    return {minDate: ($(input).hasClass("endDate1")  ? $(".beginDate1").datepicker("getDate") : null), 
		        maxDate: ($(input).hasClass("beginDate1") ? $(".endDate1").datepicker("getDate") : null)}; 
		}
	}
	return dateInit;
});

