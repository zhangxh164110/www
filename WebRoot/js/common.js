Date.prototype.format = function(format){ 
	var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
	}; 
	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 
	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	return format; 
}
String.prototype.yyMMdd=function(){
	if(this.indexOf("-"))
	  return new Date(this.replace(/-/g,"/"));
	else
	  return new Date(this);
}
// 清空全部空格
String.prototype.TrimAll=function(){
return this.replace(/\s/g,"");
} 
// 清空左右空格
String.prototype.trim=function(){
return this.replace(/(^\s*)|(\s*$)/g,"");
} 
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}
//计算字节长度
String.prototype.lenReg = function(){
   var byteLen=0,len=this.length; 
   for(var i=0; i<len; i++){ 
     if(this.charCodeAt(i)>255){ 
          byteLen += 2;
     }else{
        byteLen++; 
     } 
   } 
   return byteLen; 
}
//截取字符串，超出字节长度用...省略
String.prototype.ellipsis = function(leng){
	if(isNaN(leng)){
		return this;
	}
	if(this.lenReg() > leng){
		leng = leng - 3;
		var byteLen=0,len=this.length; 
		for(var i=0; i<len; i++){ 
			if(this.charCodeAt(i)>255){ 
				byteLen += 2; 
			}else{ 
				byteLen++; 
			} 
			if(byteLen > leng){
				return this.substring(0,i) + "...";
			}
		} 
	}
	return this;
}
//给非IE浏览器的元素添加模拟单击事件
if(!document.all){
	Element.prototype.click = function(){
		var evt = document.createEvent("MouseEvents");  
		evt.initEvent("click", true, true);  
		this.dispatchEvent(evt);  
	}
}


function setcookie(name,value,Days,path,domain,secure){  
    if(!Days || isNaN(Days)) Days = 0;  
    var exp  = new Date();  
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    path = path ? path : '';
    domain = domain ? domain : '';
    secure = secure ? secure : '';
    var c = name + "="+ escape (value);
    if(Days > 0)
    	c += ";expires=" + exp.toGMTString();
    if(path)
    	c += ";path="+path
    if(domain)
    	c += ";domain="+domain
    if(secure)
    	c += ";secure="+secure
    document.cookie = c;  
}  
  
function getcookie(name){  
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));   
    if(arr != null){  
        return unescape(arr[2]);  
    }else{  
        return "";  
    }  
}  
  
function delcookie(name){  
    var exp = new Date();   
    exp.setTime(exp.getTime() - 1);  
    var cval=getcookie(name);  
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();  
} 
/** 请输入
 * text:默认内容;style:默认内容样式;first:是否点击一次清空
 */
$.fn.pleaseInput = function (text,style,first){
	if(this.length == 0){ return this };
	var pleace = typeof text=="string"?text:"请输入";
	var obj = this[0];
	if(obj.oStyle){
		for(var key in obj.oStyle){
			$(this).css(key,obj.oStyle[key]);
		}
	}
	obj.oStyle = {};
	if(typeof style == 'boolean'){
		first = style;
	}
	if(typeof style == "string"){
		var color = style;
		if(color.indexOf("#")<0){
			color = "#"+color;
		}
		style = {};
		style["color"] = color;
	}
	if(typeof style == "object"){
		for(var key in style){
			obj.oStyle[key] = this.css(key);
		}
	}
	if(this.val() == "" || this.val() == pleace){
		this.val(pleace);
		for(var key in style){
			this.css(key,style[key]);
		}
	}
	var focus = function (){
		if(obj.value == pleace){
			obj.value = "";
		}
		for(var key in style){
			$(obj).css(key,obj.oStyle[key]);
		}
	}
	var blur = function (){
		if(obj.value == ""){
			obj.value = pleace;
			for(var key in style){
				$(obj).css(key,style[key]);
			}
		}
	}
	this[0].change = function(){
		if(obj.value == pleace){
			obj.value = "";
		}
		for(var key in style){
			$(obj).css(key,obj.oStyle[key]);
		}
	}
	this.unbind(".pleaceInput");
	this.bind("focus.pleaceInput",focus);
	this.bind("keypress.pleaceInput",this[0].change);
	if(first != true){
		this.bind("blur.pleaceInput",blur);
	}
	/*
	 * 是否输入值,isClear=true：如果没有输入则清空内容
	 */
	this[0].isInput = function(isClear){
		if(this.value != pleace && this.value != ''){
			return true;
		}else{
			if(isClear == true){
				this.value = '';
			}else{
				this.value = pleace;
			}
		}
	}
	return this;
}


//判断滚动条是否滚动底了
function reachBottom(top){
	if(!top){
		top = 400;
	}
    var scrollTop = 0;
    var clientHeight = 0; 
    var scrollHeight = 0;
 
    if(document.documentElement && document.documentElement.scrollTop) {  
        scrollTop = document.documentElement.scrollTop;  
    } else if (document.body) {  
		scrollTop = document.body.scrollTop;  
    }  
	clientHeight = document.documentElement.clientHeight;
    scrollHeight = Math.max(document.body.scrollHeight,document.documentElement.scrollHeight);
    if(scrollTop + clientHeight > scrollHeight - top) {  
        return true;  
    } else {
        return false; 
    }
}

//点击div之外关闭div
function clickOutsideClose(obj,remove,fn){
	if(typeof obj == 'string'){
		obj = $('#'+obj)
	}
	if(typeof remove == 'function'){
		fn = remove;
	}
	var flag = $(obj).attr('id');
	if(flag && flag != ''){
		$(obj).unbind('click.' + flag);
		$(obj).bind('click.' + flag, function(event){
			stopBubble(event);
		})
		setTimeout(function(){
			$(document).bind('click.' + flag, function(){
				if(typeof fn == 'function'){
					if(fn(obj)) return;
				}else if(remove == true){
					$(obj).remove();
				}else{
					$(obj).hide();
				}
				$(document).unbind('click.' + flag);
			});
		},50);
		function stopBubble(e){   
	        if(e && e.stopPropagation){
	            e.stopPropagation();    //w3c
	        }else{
	            window.event.cancelBubble=true; //IE
	        }
    	}
	}
}

//js文件国际化的方法
function getTextByKeys(keys, fn){
	//lang["textKeys"] = keys;
	$.post('getTextByKeys.action','keys='+keys,function(text){
		if(typeof fn == "function") fn(text);
	},"json");
}


// 文本框内空为空，不是文本框，禁用退格键
$(document).keydown(function(e){
	try{
		var isie = (document.all) ? true:false;
		var key;
		var ev;
		if(isie){//IE浏览器
			key = window.event.keyCode;
			ev = window.event;
		}else{//火狐浏览器
			key = e.which;
			ev = e;
		} 
		
		var tagName = isie? ev.srcElement.tagName : ev.target.tagName;
		var tagValue = isie ? ev.srcElement.value : ev.target.value;
		
		if(key==8 && ((tagName.toUpperCase() !="INPUT" && tagName.toUpperCase() != "TEXTAREA" && tagName.toUpperCase() != "TEXT") || tagValue.length <=0)){
			if(isie){
				ev.keyCode=0;
				ev.returnValue=false;
			}else{//火狐浏览器
				ev.which=0;
				ev.preventDefault();
			}
		}
	}catch(e){
		alert("error:" + e);
	}
});

//按回车键触发
function handleEnterToObject(fnName,event) {
	if (event.keyCode == 13) { //回车
		fnName();
	}
}
//将滚动条滚到指位置或元素
function scrollToObj(obj,fn){
    //判断是否有DOCTYPE声明，有与没有，scrollTop不一样
    var scrollPos;
    if(typeof document.compatMode != 'undefined' && document.compatMode != 'BackCompat') {
        scrollPos = document.documentElement;
    }
    else if (typeof document.body != 'undefined') {
        scrollPos = document.body;
    }
    var next_top = obj;
    if(isNaN(obj)){
		if(typeof obj == 'string'){
			obj = $('#'+obj);
		}else{
			obj = $(obj);
		}
	    if(obj.length != 0 && obj.offset() != null){
		    next_top = obj.offset().top;
	    }
	}
	if(typeof fn != 'function') fn = function(){}
	if(Math.abs(scrollPos.scrollTop - next_top) <= 20){
		fn();
	}else{
    	$(scrollPos).animate({ scrollTop : next_top }, 300, fn);
    }
}
//将html内容显示到title
function initTitle(obj){
	obj.title=obj.innerHTML.TrimAll().replaceAll('&nb','').replaceAll('sp;',' ').replace(/<.+?>/gim,''); 
	obj.onmouseover=null;
}
//计算天数差
function daysOfTwo(s1, s2){
	s1 = s1.replace(/-/g, "/");
	s2 = s2.replace(/-/g, "/");
	d1 = new Date(s1);
	d2 = new Date(s2);
	
	var time= d2.getTime() - d1.getTime();
	var days = parseInt(time / (1000 * 60 * 60 * 24));
	return days;
}
//计算小时差，超过1分钟也算一个小时
function hoursOfTwo(s1, s2){
	s1 = s1.replace(/-/g, "/");
	s2 = s2.replace(/-/g, "/");
	d1 = new Date(s1);
	d2 = new Date(s2);
	
	var time= d2.getTime() - d1.getTime();
	var hours = parseInt(time / (1000 * 60 * 60));
	if(time % (1000 * 60 * 60) > 0)
		hours++;
	return hours;
}
//验证用户是否登录然后进行一步操作
function isLogin(param){
	var flag = false;
	$.ajax({
		url:'isLogin',
		async:false,
		success:function(result){
			if(result == 1){ 
				try{
					var fn = eval(param);
					if(typeof fn == 'function') fn();
				}catch(e){
					if(param){
		 				window.location.href = param;
		 			}
				}
				flag = true;
			}else{
				if(typeof loginx == 'function'){
					loginx(param);
				}else{
					alert('您还没有登录，请登录后再操作');
				}
				flag = false;
			}
		}
	})
	return flag;
}
 //验证邮箱
 function isEmail(str) {
 	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(!myreg.test(str)){
		return true;
	} else {
		return false;
    }
  }
  
function delObj( url, idVal ){
	$.post(url,function(data){
		if(data == 1){
			alert("已经存在关联数据，不能删除");
		}else if(data == 2){
			alert("系统繁忙，请稍后再试");
		}else{
			window.location.reload();
		}
	})
}  
 //验证邮箱
 function isEmail(str) {
 	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(!myreg.test(str)){
		return true;
	} else {
		return false;
    }
  }