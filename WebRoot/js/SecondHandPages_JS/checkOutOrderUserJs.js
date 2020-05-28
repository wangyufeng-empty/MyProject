

//表单验证
function checkOrderUserInfo(){
	
	if($("#consignee").val()==""||$("#consignee").val()=="null"||$("#consignee").val()=="NULL"){
		layer.msg('请输入收货人');
		$("#consignee").focus();
		return false;
	}
	
	
	var user_tel = $("#userTel").val();
	if (!(/^1[34578]\d{9}$/.test(user_tel))||user_tel.length!=11) 
	{		 //验证电话号码格式
		layer.msg('电话号码格式错误');
		$("#userTel").focus();
		return false;
	}
	
	　var myemail=$("#userEmail").val();  //邮箱格式的正则表达式
	　　var myReg=/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/;  
		if(!myReg.test(myemail)){  //验证邮箱格式		　　　
	　　　　layer.msg('请输入正确的邮箱地址');
			$("#userEmail").focus();
			return false;
		}

	
	if($("#userAddress").val()==""||$("#userAddress").val()=="null"||$("#userAddress").val()=="NULL"){
		layer.msg('请输入收货地址');
		$("#userAddress").focus();
		return false;
	}
}

function SavaUserTradeInfo(){
	/*表单验证*/
	if(checkOrderUserInfo()==false){
		return false;
	}
	
	
	/*如果验证通过*/	
	var targetUrl = "usuallyController";    //获取提交路径
	var data = $("#updateProfile").serialize();     //表单数据列表
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
				 parent.layer.msg(returnJson.returnMessage,{icon:6,time:3000});
				 
			 }
			 else {parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000}); }
			
		 },
		 error:function(){ 
			 layer.alert("请求失败，请重新登录！");
		 },
		 complete:function () {			 
            
			 layer.close(index);
         }
	});//end ajax
	
};//end function

//订单下一步
function OrderNext(){
	/*表单验证*/
	if(checkOrderUserInfo()==false){
		return false;
	}
	
	/*如果验证通过*/	
	var targetUrl = "usuallyController";    //获取提交路径
	var data = {"url":"订单下一步"};     //表单数据列表
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
				 console.log("goUrl："+returnJson.goUrl);
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


