

function deleteGoods(goods_id){
	layer.confirm('你确定要下架这件商品吗？',function(){
	$("#"+goods_id).attr('disabled',"true");  //防止重复提交
	//window.location.href = "usuallyController?url=<%="下架商品" %>&goods_id="+goods_id;
		var targetUrl = "usuallyController";
		var data = {"url":"下架商品","goods_id":goods_id};
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
				 
				 $("#"+goods_id).removeAttr("disabled");
			 },			 
			 error:function(){ 
				 layer.alert("请求失败，请重新登录！");
			 },
			 complete:function () {			 
                 //完成以后隐藏load动画
				 layer.close(index);
             }
		});//end ajax
})//end confirm
}

//修改库存
function updateStock(goods_id){
	
	//prompt层
	layer.prompt({title: '请输入库存', formType: 3}, function(goods_stock, index){
		var regexp_stock = new RegExp("^[0-9]*[1-9][0-9]*$");
		//表单验证
		if(goods_stock>20||goods_stock<=0||!regexp_stock.test(goods_stock)){
			layer.msg("您输入的库存有误！");
			$(".layui-layer-input").focus();
		}
		else{
			var data = {"url":"updateStock","goods_id":goods_id,"goods_stock":goods_stock};
			var targetUrl = "usuallyController";
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
						 
						 layer.msg(returnJson.returnMessage,{icon:6,time:3000,end:function(){window.location.reload();}});
					 }
					 else parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000}); 
					
				 },			 
				 error:function(){ 
					 layer.alert("请求失败，请重新登录！");
				 },
				 complete:function () {			 
	                 //完成以后隐藏load动画
					 layer.close(index);
	             }
			});//end ajax
		}// end else
		
 
	});//end promt
}


