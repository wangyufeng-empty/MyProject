//找回密码，输入密码阶段
$(document).ready(function(){
	//监听用户ID的输入框
	$(document).on('blur', '#user_id', function() {  
		var user_id = $("#user_id").val();
		$.ajax({
			 type:'post',  
			 url:"/JsVerify?method=checkUserId", 
			 cache: false,
			 data:{"user_id":user_id},  //重点必须为一个变量如：data
			 dataType:'json', 
			 success:function(data){    
			 	if(data==1&&$("#user_id").val().length==12)  {
			 		$("#userId_tip").css("color","white");
			 		$("#userId_tip").text("学号正确");				 		
			 	}
			 	else if(user_id.length != 12&&user_id.length != 6){
			   		$("#userId_tip").css("color","yellow");
			   		$("#userId_tip").text("格式错误");				   		
			   	}
			   	else if(data==0&&user_id.length != 6){  //不判断管理员
			   		$("#userId_tip").css("color","yellow");
			   		$("#userId_tip").text("用户不存在");					   		
			   	}	
			   	else{
			   		$("#userId_tip").text("");	
			   	}
			   	
			 },
			 error:function(){ 
			  alert("请求失败");
			 }
		});//end ajax
	}); //end listen user_id
	
	//监听密码输入框
	$("#user_psw").on("blur",function(){
		var length = $("#user_psw").val().length;
		console.log("user_psw:"+length);
		if(length<6){
			$("#password_tip").css("color","red");
			$("#password_tip").text("密码长度必须大于6！");		
		}
		else if(length<10){
			$("#password_tip").css("color","yellow");
			$("#password_tip").text("密码安全系数过低，请注意！");		
		}
		else{
			$("#password_tip").css("color","green");
			$("#password_tip").text("密码安全！");	
		}
	});
	
	//监听确认密码输入框
	$("#password_confirm").on("blur",function(){
		var user_psw = $("#user_psw").val();
		var password_confirm =  $("#password_confirm").val();
		console.log("password_confirm:");
		if(user_psw != password_confirm){
			$("#password_confirm_tip").css("color","red");
			$("#password_confirm_tip").text("两次密码不一致！");		
		}
		else{
			$("#password_confirm_tip").text("");	
		}
	});
});//end ready

function next() 
{
	
	if ($("#user_id").val().length != 12) {
		
		layer.msg('学号格式错误');
		$("#user_id").focus();
		return false;		
	}
	/*如果验证通过*/	
	
	var targetUrl = "retrivePasswordController";    //获取提交路径
	var data = $("#retrievePswForm").serialize();     //表单数据列表
	var index = null;
	$.ajax({ 
		 type:'post',  
		 url:targetUrl, 
		 cache: false,
		 data:data,  //重点必须为一个变量如：data
		 dataType:'json', 
		 beforeSend: function (){
             //ajax刷新前加载load动画
			$("#nextStep").attr('disabled',"true");  //防止重复提交
			index = layer.load(1, {time: 30*1000,shade: [0.2,'#000']});
         },
		 success:function(data){  
			var returnJson = eval(data);
			//判断返回的json中是否含有某个属性
			if(returnJson.hasOwnProperty("goUrl")){   
				console.log("goUrl："+returnJson.goUrl);
				window.location.href = ""+returnJson.goUrl+"?question="+returnJson.question+"&userId="+returnJson.userId;
			}
			else{
		 		parent.layer.msg(returnJson.returnMessage,{icon:5,time:3000});		 		
			}
		 },
		 error:function(){ 
		  alert("请求失败");
		 },
		 complete:function () {			 
             //完成以后隐藏load动画
			$("#nextStep").removeAttr("disabled")
			layer.close(index);
         }
	});  //end ajax

}

//找回密码
function retrievePassword(){
	if($("#user_answer").val()=="")
	 {
		layer.msg('请输入你的答案');
		$("#user_answer").focus();
		return false;	
	}
	if($("#user_psw").val()=="")
	 {
		layer.msg('请输入你的密码');
		$("#user_psw").focus();
		return false;	
	}
	if($("#user_psw").val().length < 6) {
	
		layer.msg('密码长度必须大于6位');
		$("#user_psw").focus();
		return false;	
	}
	if($("#password_confirm").val()=="")
	 {
		layer.msg('请确认你的密码');
		$("#password_confirm").focus();
		return false;	
	}
	if($("#password_confirm").val() != $("#user_psw").val())
	{
		layer.msg('两次密码不一致');
		$("#password_confirm").focus();
		return false;
	}
	
	/*如果验证通过*/	
	
	var targetUrl = "retrivePasswordController";    //获取提交路径
	var data = $("#retrievePswForm").serialize();     //表单数据列表
	var index = null;
	$.ajax({ 
		 type:'post',  
		 url:targetUrl, 
		 cache: false,
		 data:data,  //重点必须为一个变量如：data
		 dataType:'json', 
		 beforeSend: function (){
             //ajax刷新前加载load动画
			$("#nextStep").attr('disabled',"true");  //防止重复提交
			index = layer.load(1, {time: 30*1000,shade: [0.2,'#000']});
         },
		 success:function(data){  
			var returnJson = eval(data);
			//判断返回的json中是否含有某个属性
			if(returnJson.returnMessage=="密码重置成功,快去登录吧！")  {
		 		parent.layer.msg(returnJson.returnMessage,{icon:6,time:3000,end:function(){location.href='login.jsp';}});
		 	}
			else{
		 		parent.layer.msg(returnJson.returnMessage,{icon:5,time:3000});		 		
			}
		 },
		 error:function(){ 
		  alert("请求失败");
		 },
		 complete:function () {			 
             //完成以后隐藏load动画
			$("#nextStep").removeAttr("disabled")
			layer.close(index);
         }
	});  //end ajax
}



