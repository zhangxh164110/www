function checkCookie(){
	var strCookie=document.cookie;
	var arrCookie=strCookie.split("; ");
	var star_role = 2;
	//遍历cookie数组，处理每个cookie对
	for(var i=0;i<arrCookie.length;i++){
		 var arr=arrCookie[i].split("=");
		 //找到名称为userId的cookie，并返回它的值
		 if("star_role"==arr[0]){
			  star_role=parseInt( arr[1] );
			  break;
		 }
	}
	if( star_role ==0 || star_role ==1 ){
		return;
	}
	$('body').html('无权限')
}
$(function(){
	checkCookie();
})