$(document).ready(function(){
	
//监听用户ID的输入框
$(document).on('blur', '#user_id', function() {  
	var user_id = $("#user_id").val();
	$.ajax({
		 type:'post',  
		 url:"JsVerify?method=checkUserId", 
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
			 layer.alert("请求失败！");
		 }
	});//end ajax
}); //end listen user_id


});

//登录操作
function loginIndex(){
	
	if (form_login.username.value == "") {
	
		layer.msg("请输入你的账号/学号");
		form_login.username.focus();
		return false;
		
	}
	if (form_login.username.value.length != 12 && form_login.username.value.length != 6) {
		
		layer.msg('账号格式错误');
		form_login.username.focus();
		return false;
		
	}
	if(form_login.password.value=="") {
	
		layer.msg('请输入你的密码');
		form_login.password.focus();
		return false;	
	}
	
	/*如果验证通过*/	
	$("#login").attr('disabled',"true");  //防止重复提交
	var targetUrl = "userLoginController";    //获取提交路径
	var data = $("#form_login").serialize();     //表单数据列表
	var index = null;
	$.ajax({ 
		 type:'post',  
		 url:targetUrl, 
		 cache: false,
		 data:data,  //重点必须为一个变量如：data
		 dataType:'json', 
		 beforeSend: function (){
           //ajax刷新前加载load动画
//		
			 index = layer.load(1, {time: 30*1000,shade: [0.2,'#000']});
       },
		 success:function(data){  
			var returnJson = eval(data);
			//判断返回的json中是否含有某个属性
			if(returnJson.hasOwnProperty("goUrl")){   
				console.log("goUrl："+returnJson.goUrl);
				window.location.href = returnJson.goUrl;
			}
			else{
				if(returnJson.ERRORPWDCOUNT>=3){
					$("#userPWD_tip").css("color","yellow");
					$("#userPWD_tip").text("密码错误三次或以上，建议您使用面部识别登录！");
					layer.confirm("密码错误三次或以上，建议您使用面部识别登录！", function() {
               		 	window.location="faceLoginJsp/GetUserId.jsp"                  	
					});
				}
				else{
					parent.layer.msg(returnJson.returnMessage,{icon:5,time:3000});
			 		$("#login").removeAttr("disabled");
				}
		 		
			}
		 },
		 error:function(){ 
			 layer.alert("请求失败！");
		  $("#login").removeAttr("disabled");
		 },
		 complete:function () {			 
           //完成以后隐藏load动画
//		
			 layer.close(index);
       }
	});  //end ajax
}   //end click login


//在非form里面ID 和函数名是可以重名的，在form标签里不行，不然会发生函数找不到的错误
function faceLogin(){
	window.location.href = "faceLoginJsp/GetUserId.jsp";
}



