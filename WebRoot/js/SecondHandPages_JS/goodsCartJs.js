$(document).ready(function(){

	
	/*清空购物车*/
	$("#clearGoodsCart").on("click",function(){
		console.log("clearGoodsCart");
		layer.confirm("你确定要清空所有已选商品吗",function(){
			var targetUrl = "usuallyController";
			var data = {"url":"清空购物车"};
			$.ajax({ 
				 type:'post',  
				 url:targetUrl, 
				 cache: false,
				 data:data,  //重点必须为一个变量如：data
				 dataType:'json', 
				 beforeSend: function (){
	                 //ajax刷新前加载load动画
					$("#coverDiv").css("display","block");
					$("#loadgif").css("display","block");
	             },
				 success:function(data){  
					 var returnJson = eval(data);
					 
					 if(returnJson.hasOwnProperty("status")){  //如果回调为成功的信息
						 parent.layer.msg(returnJson.returnMessage,{icon:6,time:1000,end:function(){window.location.reload();}});
					 }
					 else parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000}); 
				 },
				 error:function(){ 
					 alert("请求失败");
				 },
				 complete:function () {			 
	                 //完成以后隐藏load动画
					$("#coverDiv").css("display","none");
					$("#loadgif").css("display","none");
	             }
			});//end ajax
		}); //end confirm
		
	});//end function
	
	
	
	
});//end ready


function submitSelectedQuantity(goods_id,selectedQuantity)
{
	//***************大坑，如果select下拉选择框在循环里，***********/
	//***************一定不能在js中去获得值，**********************/
	//****************一定要在调用函数时就及时传过去，才是动态的值,/
	//******************否则一直都是第一个数据的值***************/
	
	//window.location.href="usuallyController?url=<%="修改数量"%>&goods_id="+goods_id+"&selectedQuantity="+selectedQuantity;   //从这里请求servlet
	if(selectedQuantity != ""){
		var data = {"url":"修改数量","goods_id":goods_id,"selectedQuantity":selectedQuantity};
		$.ajax({ 
			 type:'post',  
			 url:"usuallyController", 
			 cache: false,
			 data:data,  //重点必须为一个变量如：data
			 dataType:'json', 
			 beforeSend: function (){
                 //ajax刷新前加载load动画
				$("#coverDiv").css("display","block");
				$("#loadgif").css("display","block");
             },
			 success:function(data){  
				 var returnJson = eval(data);
				 
				 if(returnJson.hasOwnProperty("status")){  //如果回调为成功的信息
					 parent.layer.msg(returnJson.returnMessage,{icon:6,time:1500,end:function(){window.location.reload();}});
				 }
				 else parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000}); 
			 },
			 error:function(){ 
				 alert("请求失败");
			 },
			 complete:function () {			 
                 //完成以后隐藏load动画
				$("#coverDiv").css("display","none");
				$("#loadgif").css("display","none");
             }
		});//end ajax
	}
	
}//end function

//接收来自 移除此项的请求
function removeItemFromCart(goods_id){
	console.log("clearGoodsCart");
	layer.confirm("确定要移除这件商品吗",function(){
		var targetUrl = "usuallyController";
		var data = {"url":"移除此项","goods_id":goods_id};
		$.ajax({ 
			 type:'post',  
			 url:targetUrl, 
			 cache: false,
			 data:data,  //重点必须为一个变量如：data
			 dataType:'json', 
			 success:function(data){  
				 var returnJson = eval(data);
				 
				 if(returnJson.hasOwnProperty("status")){  //如果回调为成功的信息
					 parent.layer.msg(returnJson.returnMessage,{icon:6,time:2500,end:function(){window.location.reload();}});
				 }
				 else parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000}); 
			 },
			 error:function(){ 
				 alert("请求失败");
			 }
		});//end ajax
	}); //end confirm
}//end function

