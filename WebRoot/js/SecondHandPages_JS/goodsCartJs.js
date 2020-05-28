

function submitSelectedQuantity(goods_id,selectedQuantity)
{
	//***************大坑，如果select下拉选择框在循环里，***********/
	//***************一定不能在js中去获得值，**********************/
	//****************一定要在调用函数时就及时传过去，才是动态的值,/
	//******************否则一直都是第一个数据的值***************/
	if(selectedQuantity != ""){
		var data = {"url":"修改数量","goods_id":goods_id,"selectedQuantity":selectedQuantity};
		var index = null;
		$.ajax({ 
			 type:'post',  
			 url:"usuallyController", 
			 cache: false,
			 data:data,  //重点必须为一个变量如：data
			 dataType:'json', 
			 beforeSend: function (){
                 //ajax刷新前加载load动画
//			
				 index = layer.load(5, {time: 30*1000,shade: [0.1,'#fff']});
             },
			 success:function(data){  
				 var returnJson = eval(data);
				 
				 if(returnJson.hasOwnProperty("status")){  //如果回调为成功的信息
					 parent.layer.msg(returnJson.returnMessage,{icon:6,time:1500,end:function(){window.location.reload();}});
				 }
				 else parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000}); 
			 },
			 error:function(){ 
				 layer.alert("请求失败，请重新登录！");
			 },
			 complete:function () {			 
                 //完成以后隐藏load动画
//				
				 layer.close(index);
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
		var index = null;
		$.ajax({ 
			 type:'post',  
			 url:targetUrl, 
			 cache: false,
			 data:data,  //重点必须为一个变量如：data
			 dataType:'json', 
			 beforeSend: function (){
                 //ajax刷新前加载load动画
//				$("#coverDiv").css("display","block");
//				$("#loadgif").css("display","block");
				 index = layer.load(5, {time: 30*1000,shade: [0.1,'#fff']});
             },
			 success:function(data){  
				 var returnJson = eval(data);
				 
				 if(returnJson.hasOwnProperty("status")){  //如果回调为成功的信息
					 parent.layer.msg(returnJson.returnMessage,{icon:6,time:2500,end:function(){window.location.reload();}});
				 }
				 else parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000}); 
			 },
			 error:function(){ 
				 layer.alert("请求失败，请重新登录！");
			 },
			 complete:function () {			 
                 //完成以后隐藏load动画
//				$("#coverDiv").css("display","none");
//				$("#loadgif").css("display","none");
				 layer.close(index);
             }
		});//end ajax
	}); //end confirm
}//end function

