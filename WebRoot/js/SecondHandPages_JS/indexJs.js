

//加入收藏
//e 代表每个项目对象
function collectGoods(e){  
	var goods_id = $(e).attr("id");
	console.log("goods_id:"+goods_id);
	var url = "加入收藏";
	var targetUrl = "usuallyController";
	var data = {"url":url,"goods_id":goods_id};
	$.ajax({ 
			 type:'post',  
			 url:targetUrl, 
			 cache: false,
			 data:data,  //重点必须为一个变量如：data
			 dataType:'json', 
			 success:function(data){  
				 var returnJson = eval(data);
				 if(returnJson.hasOwnProperty("status")){  //如果回调为成功的信息
					 parent.layer.msg(returnJson.returnMessage,{icon:6,time:4000});
					 sleep(4000); //休眠4秒
				 }
				 else {parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000});sleep(4000); }//休眠n秒
				 
			 },
			 error:function(){ 
				 alert("请求失败");
			 }
		});//end ajax
	
}

//加入购物车
function addToCart(e){
	var goods_id = $(e).attr("id");
	console.log("goods_id:"+goods_id);
	var url = "加入购物车";
	var targetUrl = "usuallyController";
	var data = {"url":url,"goods_id":goods_id};
	$.ajax({ 
			 type:'post',  
			 url:targetUrl, 
			 cache: false,
			 data:data,  //重点必须为一个变量如：data
			 dataType:'json', 
			 success:function(data){  
				 var returnJson = eval(data);
				 if(returnJson.hasOwnProperty("status")){  //如果回调为成功的信息
					 parent.layer.msg(returnJson.returnMessage,{icon:6,time:4000});
				 }
				 else parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000});
			 },
			 error:function(){ 
				 alert("请求失败");
			 }
		});//end ajax
}