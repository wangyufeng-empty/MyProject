
function deleteGoods(goods_id){
	layer.confirm('你确定要下架这件商品吗？',function(){
	$("#"+goods_id).attr('disabled',"true");  //防止重复提交
	//window.location.href = "usuallyController?url=<%="下架商品" %>&goods_id="+goods_id;
		var targetUrl = "usuallyController";
		var data = {"url":"下架商品","goods_id":goods_id};
		$.ajax({ 
			 type:'post',  
			 url:targetUrl, 
			 cache: false,
			 data:data,  //重点必须为一个变量如：data
			 dataType:'json', 
			 success:function(data){  
				 var returnJson = eval(data);
				 
				 if(returnJson.hasOwnProperty("status")){  //如果回调为成功的信息
					 parent.layer.msg(returnJson.returnMessage,{icon:6,time:500,end:function(){window.location.reload();}});
				 }
				 else parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000}); 
				 
				 $("#"+goods_id).removeAttr("disabled");
			 },			 
			 error:function(){ 
				 alert("请求失败");
			 }
		});//end ajax
})
}

