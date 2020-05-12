$(document).ready(function(){

	
	
	$("#deleteWishList").on("click",function(){
		console.log("clearGoodsCart");
		
		layer.confirm("你确定要清空所有收藏吗",function(){
			$("#deleteWishList").attr('disabled',"true");  //防止重复提交
			var targetUrl = "usuallyController";
			var data = {"url":"清空收藏"};
			var index = null;
			$.ajax({ 
				 type:'post',  
				 url:targetUrl, 
				 cache: false,
				 data:data,  //重点必须为一个变量如：data
				 dataType:'json', 
				 beforeSend: function (){
	                 //ajax刷新前加载load动画
					 index = layer.load(5, {time: 30*1000,shade: [0.1,'#fff']});
	             },
				 success:function(data){  
					 var returnJson = eval(data);				 
					 if(returnJson.hasOwnProperty("status")){  //如果回调为成功的信息
						 window.location.reload();
						 //layer.msg(returnJson.returnMessage,{icon:6,time:3000,end:function(){ window.location.reload();}});
						 $("#deleteWishList").removeAttr("disabled");
					 }
					 else {parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000}); $("#deleteWishList").removeAttr("disabled");}
					 
				 },
				 error:function(){ 
					 alert("请求失败");
				 },
				 complete:function () {			 
	                 //完成以后隐藏load动画
					 layer.close(index);
	             }
			});//end ajax
		}); //end confirm
		
	});//end function
	
});//end ready


//接收来自 移除此项的请求
function removeItemFromWishList(goods_id,goods_name){
	console.log("clearGoodsCart");
	layer.confirm("确定要移除这件商品吗",function(){
		var targetUrl = "usuallyController";
		var data = {"url":"移除一个收藏","goods_id":goods_id,"goods_name":goods_name};
		var index = null;
		$.ajax({ 
			 type:'post',  
			 url:targetUrl, 
			 cache: false,
			 data:data,  //重点必须为一个变量如：data
			 dataType:'json', 
			 beforeSend: function (){
                 //ajax刷新前加载load动画
				 index = layer.load(5, {time: 30*1000,shade: [0.1,'#fff']});
             },
			 success:function(data){  
				 var returnJson = eval(data);
				 
				 if(returnJson.hasOwnProperty("status")){  //如果回调为成功的信息
					 parent.layer.msg(returnJson.returnMessage,{icon:6,time:500,end:function(){window.location.reload();}});
				 }
				 else parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000}); 
			 },
			 error:function(){ 
				 alert("请求失败");
			 },
			 complete:function () {			 
                 //完成以后隐藏load动画
				 layer.close(index);
             }
		});//end ajax
	}); //end confirm
}//end function

