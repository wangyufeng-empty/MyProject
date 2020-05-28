
/*提交订单请求*/
function submitOrder(){
	
	/*如果验证通过*/	
	var targetUrl = "usuallyController";    //获取提交路径
	var data = {"url":"提交订单"};     //表单数据列表
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
			 
			 if(returnJson.hasOwnProperty("goUrl")){  //如果回调为成功的信息
				 window.location.href = returnJson.goUrl;
				 
			 }
			 else {parent.layer.msg("跳转失败，请联系管理员！",{icon:5,time:4000}); }
			
		 },
		 error:function(){ 
			 layer.alert("请求失败，请重新登录！");
		 },
		 complete:function () {			           
			 layer.close(index);
         }
	});//end ajax
}


