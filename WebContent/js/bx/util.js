define([], function(){
	var util = {
    page: {
        clientWidth: function() {
            return document.compatMode == "BackCompat" ? document.body.clientWidth : document.documentElement.clientWidth
        },
        clientHeight: function() {
            return document.compatMode == "BackCompat" ? document.body.clientHeight : document.documentElement.clientHeight
        },
        getWidth: function() {
            return Math.max(document.body.scrollWidth, document.documentElement.scrollWidth, this.clientWidth())
        },
        getHeight: function() {
            return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight, this.clientHeight())
        }
    },
    format: function(source, opts) {
        source = String(source);
        var data = Array.prototype.slice.call(arguments, 1), toString = Object.prototype.toString;
        if (data.length) {
            data = data.length == 1 ?
    	    (opts !== null && (/\[object Array\]|\[object Object\]/.test(toString.call(opts))) ? opts : data) : data;
            return source.replace(/#\{(.+?)\}/g, function(match, key) {
                var replacer = data[key];
                if ('[object Function]' == toString.call(replacer)) {
                    replacer = replacer(key);
                }
                return ('undefined' == typeof replacer ? '' : replacer);
            });
        }
        return source;
    },
    setToCenter: function(_objjQuery, maxHeight) {
        var yScroll = 0,
            height = $(window).height();
        if (util.isIE6) yScroll = $(window).scrollTop();
        _objjQuery.css({ "left": parseInt(($(window).width() - _objjQuery.width()) / 2) + 'px'
                , "top": (height - _objjQuery.height()) / 2 + yScroll + 'px'
        });
    },
    showOverlayDiv: function(_objjQueryId) {
        var objjQuery = $("#" + _objjQueryId),
            objClosejQuery = $("#" + _objjQueryId + "_CLOSE");
        this.mask.create();
        util.setToCenter(objjQuery);
        objjQuery.show();
        if (!objClosejQuery) return;
        objClosejQuery.one("click", function() {
            util.mask.close();
            objjQuery.hide();
        });
    },
    mask: {
        opacity: .5,
        bgColor: "#000",
        winNum: 0,
        layers: [],
        create: function() {
            var layer = $("<div></div>");
            $("body").append(layer);
            layer.css({
                left: "0px",
                width: util.page.getWidth() + "px",
                height: util.page.getHeight() + "px",
                position: "absolute",
                top: "0px",
                "z-index": "100",
                opacity: +util.mask.opacity,
                filter: "alpha(opacity =" + util.mask.opacity * 100 + ")",
                background: util.mask.bgColor
            });
            this.layers.push(layer);
        },
        close: function() {
            if (this.layers && this.layers.length > 0) {
                this.layers[this.layers.length - 1].remove();
                this.layers.pop();
            };
        }
    },
    d: {
        show: function(_objjQueryId, callback) {
            util.showOverlayDiv(_objjQueryId);
            callback && callback();
        },
       
        close: function(_objjQueryId) {
            var _objjQuery = $("#" + _objjQueryId);
            _objjQuery.hide();
            util.mask.close();
        }
    },
    vInput: function() {
        var objArg = arguments[0],
            color1 = arguments[1],
            color2 = arguments[2];
        objArg.each(function() {
        	if(this.value == ''){
        		if(this.title != null){
        			this.value = this.title;
        			 $(this).css("color", color2);
        		}
        	}
            this.msg = $(this).val();
            if (!$(this).attr("disabled")) {
            	//加入带回默认属性设置，启用title,不再使用value
            	
                $(this).focus(function() {
                    if (this.value == this.msg) {
                        this.value = "";
                        $(this).css("color", color1);
                    }
                }).blur(function() {
                    if (this.value == '') {
                        this.value = this.msg;
                        $(this).css("color", color2);
                    }
                });
            }
        });
    },
    _addMsg: function(d, txt) {
        d.show();
        d.text(txt);
        setTimeout(function() {
            d.fadeOut();
        }, 3000);

	}, /**模拟下拉框*/
    downListInit: function(contentId,callback) {
        $("#"+contentId).each(function() {
            var me = this,
                layer = $(this).children("ul"),
                list = layer.children(),
                input = $(this).find("input"),
                span = $(this).find("span");
            span.css(
                "width", $(me).width() - 20 + 'px'
            );
            $(this).mouseout(function() {
                layer.hide();
            });
            layer.css(
                "width", $(me).width() + 'px'
            );
            $(this).mouseover(function() {
                layer.show();
            });
            list.each(function() {
                $(this).click(function() {
                    layer.hide();
                    $(me).attr("v", $(this).attr("v"));
                    input.val($(this).attr("v"));
                    span.text($(this).text());
                    callback&&callback($(this).attr("v"));
                });
                if ($(this).attr("selected")) {
                    $(me).attr("v", $(this).attr("v"));
                    input.val($(this).attr("v"));
                    span.text($(this).text());
                }
                if (util.isIE6) {
                    $(this).hover(function() {
                        $(this).addClass("over");
                    }, function() {
                        $(this).removeClass("over");
                    });
                }
            });
        });
    },
    downListSetValue: function(contentId,value) {
        $("#"+contentId).find("li").each(function() {
        	 if ($(this).attr("v") == value){
        		 $(this).click();
        	 } 
        });
    },
    downListGetValue: function(contentId) {
    	return $("#"+contentId).attr("v");
    }
	}
	
	return util;
});