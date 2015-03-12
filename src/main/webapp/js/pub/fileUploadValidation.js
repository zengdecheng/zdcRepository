/**
 * by fangwei 2014-12-25
 */
(function($){
	var opts;
	$.fn.fileValidation = function(opt){
		opts = $.extend({
			fileTypes:[''],
			fileSize:0
		},opt||{});
		return checkFile(this);
	};
	function checkFile($obj){
		// 检查是否IE浏览器
		var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
		var target = $obj[0];
		var fileSize = 0;
		var fileTypes = opts.fileTypes;
		var filepath = target.value;
		var filemaxsize = opts.fileSize;
		if (filepath) {
			var isValid = false;
			var suffix = filepath.substring(filepath.indexOf("."));
			if (fileTypes && fileTypes.length > 0) {
				for (var i = 0; i < fileTypes.length; i++) {
					if (fileTypes[i] == suffix.toLowerCase()) {
						isValid = true;
						break;
					}
				}
			}
			if (!isValid) {
				alert("只接受类型为"+fileTypes+"的文件！");
				target.value = "";
				return false;
			}
		} else {
			return false;
		}
		if (isIE && !target.files) {
			var filePath = target.value;
			var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
			if (!fileSystem.FileExists(filePath)) {
				alert("附件不存在，请重新选择类型为"+fileTypes+"的文件！");
				return false;
			}
			var file = fileSystem.GetFile(filePath);
			fileSize = file.Size;
		} else {
			fileSize = target.files[0].size;
		}

		var size = fileSize / 1024;
		if (size > filemaxsize) {
			alert("附件大小不能大于" + filemaxsize / 1024 + "M！");
			target.value = "";
			return false;
		}
		if (size <= 0) {
			alert("附件大小不能为0M！");
			target.value = "";
			return false;
		}
		return true;
	}
})(jQuery);