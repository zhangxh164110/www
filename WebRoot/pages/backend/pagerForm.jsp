<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
    /***************此分页用有有pager对象的*******************/
	function paginationCommon(field){
		var initHtml="";
		var pageNumberCommon =parseInt(<s:property value="pager.pageNumber"/>);
		if(isNaN(pageNumberCommon)){
			pageNumberCommon=0;
		} 
		var pageCountCommon=parseInt(<s:property value="pager.getLastPageNumber()"/>);
		if(isNaN(pageCountCommon)){
			pageCountCommon=0;
		}
		var pageSizeCommon=parseInt(<s:property value="pager.pageSize"/>);
		
		if(isNaN(pageSizeCommon)){
			pageSizeCommon=15;
		}
		
		var totalCommon=parseInt(<s:property value="pager.totalElements"/>);
		if(isNaN(totalCommon)){
			totalCommon=0;
		}
		
		
		var urlPageCommon=document.getElementById("urlforward").value;
		
		initHtml+='<span style="float:left;margin-top:3px; line-height:20px;height:20px;" >';
		if(pageNumberCommon>1){
			initHtml+='&nbsp;<img style="cursor: pointer" src="images/page-first-disabled01.gif" onclick="javascript:homePage(\''+urlPageCommon+'\','+field+')" />';
			initHtml+='<img style="cursor: pointer" src="images/page-prev-disabled01.gif" onclick="javascript:lastPage(\''+urlPageCommon+'\','+field+')" />';
		}else{
			initHtml+='<img src="images/page-first-disabled.gif" />';
			initHtml+='<img src="images/page-prev-disabled.gif"  />';
		}
		initHtml+='</span>';
		
		initHtml+='<span style="float:left;font-family: SimSun; font-size: 12px;border:0px solid red;text-align:center;line-height:20px;height:20px;" >&nbsp;第&nbsp;';
		initHtml+='<input onkeypress="doPagePressEnter(\''+urlPageCommon+'\', '+field+', event)" style="font-family: SimSun; font-size: 12px; width: 30px;margin:0 0 0 0; " type="text" id="pageNumber" name="pageNumber"  value="'+pageNumberCommon+'"/>';
		initHtml+='&nbsp;/&nbsp;'+pageCountCommon+'&nbsp;页</span>';
		
		initHtml+='<span style="float:left;margin-top:3px; line-height:20px;height:20px;" >';
		if(pageNumberCommon < pageCountCommon){
			initHtml+='<img style="cursor: pointer" src="images/text-prev-disabled01.gif" onclick="javascript:nextPage(\''+urlPageCommon+'\', '+field+')"/>';
			initHtml+='<img style="cursor: pointer" src="images/text-first-disabled01.gif" onclick="javascript:endPage(\''+urlPageCommon+'\','+field+')" />';
		}else{
			initHtml+='<img src="images/text-prev-disabled.gif"/>';
			initHtml+='<img src="images/text-first-disabled.gif"/>';
		}
		initHtml+='</span>';
		
		initHtml+='<span style="float:right;font-family: SimSun; font-size: 12px;">每页';
		initHtml+='<select class="inputfield0_4select" style="font-family: SimSun; font-size: 12px;" name="pageSize" id="pageSize" onchange="changePage(this.form,\''+urlPageCommon+'\')">';
		if(pageSizeCommon ==5 ){initHtml+='<option value="5" selected>5</option>';}else{initHtml+='<option value="5">5</option>';}
		if(pageSizeCommon ==10){initHtml+='<option value="10" selected>10</option>';}else{initHtml+='<option value="10">10</option>';}
		if(pageSizeCommon ==15){initHtml+='<option value="15" selected>15</option>';}else{initHtml+='<option value="15">15</option>';}
		if(pageSizeCommon ==20){initHtml+='<option value="20" selected>20</option>';}else{initHtml+='<option value="20">20</option>';}
		if(pageSizeCommon ==25){initHtml+='<option value="25" selected>25</option>';}else{initHtml+='<option value="25">25</option>';}
		if(pageSizeCommon ==30){initHtml+='<option value="30" selected>30</option>';}else{initHtml+='<option value="30">30</option>';}
		initHtml+='</select>条&nbsp;&nbsp;&nbsp;&nbsp;共'+totalCommon+'条记录&nbsp;&nbsp;';
		initHtml+='</span>';
		document.getElementById("pagination").innerHTML="";		
		document.getElementById("pagination").innerHTML=initHtml;
	}
	
	function changePage(form,url){
         form.action = url;	
	     form.submit();
    } 
     function homePage( url,field){
     	document.getElementById("pageNumber").value = 1;
     	getParam();
		
	}
	
	function endPage( url ,field){
     	document.getElementById("pageNumber").value = parseInt( '<s:property value="#request.pageCount"/>' );
     	getParam();
	}
	      
	function nextPage(url, field){
	      	document.getElementById("pageNumber").value = parseInt(document.getElementById( "pageNumber" ).value) + 1;
	      	getParam();
								 	
	}
	
	function lastPage( url, field ){	
	      	document.getElementById("pageNumber").value = parseInt(document.getElementById( "pageNumber" ).value) - 1;
	      	getParam();
								 	
	    }	
	   function doPagePressEnter( url, field, event){
	    if (document.all) { //IE
			keychar = event.keyCode;
		}
		if (keychar == 13) {
			var pageNumber = $('#pageNumber').val();
			var newPar = /^\d+(\.\d+)?$/; 
					if(!newPar.test(pageNumber)){
						alert('请输入正确的数字');
						return;
					}
	       	document.getElementById("pageNumber").value = parseInt(pageNumber);
	       	getParam();
		}	
	    }
</script>	
