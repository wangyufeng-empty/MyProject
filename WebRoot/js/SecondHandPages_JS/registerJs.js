$(document).ready(function(){
	document.getElementById("resetReg").click();
//注册操作

$("#register").click(function(){
	if (form_register.userName.value == "") {
		
		layer.msg("请输入你的姓名");
		form_register.userName.focus();
		return false;
		
	}
	if (form_register.userId.value == "") {
	
		layer.msg("请输入你的学号");
		form_register.userId.focus();
		return false;
		
	}
	if (form_register.userId.value.length != 12) {
		
		layer.msg('学号格式错误');
		form_register.userId.focus();
		return false;
		
	}
	if(form_register.password.value=="") {
	
		layer.msg('请输入你的密码');
		form_register.password.focus();
		return false;	
	}
	if(form_register.password.value.length < 6) {
	
		layer.msg('密码长度必须大于6位');
		form_register.password.focus();
		return false;	
	}
	if(form_register.password.value != form_register.password_confirm.value) {
	
		layer.msg('两次密码不一致');
		form_register.password_confirm.focus();
		return false;	
	}
	if (form_register.userAnswer.value == "") {
	
		layer.msg("请输入你的密保答案");
		form_register.userAnswer.focus();
		return false;
		
	}
	
	if($("#user_grade").val()==""){
		layer.msg("请选择你的年级");
		form_register.userGrade.focus();
		return false;
	}
	
	if($("#user_hobby:checked").length==0){
		layer.msg('必须选择至少一个爱好');
		return false;
	}
	
	/*如果验证通过*/	
	$("#register").attr('disabled',"true");  //防止重复提交
	var targetUrl = $("#form_register").attr("action");    //获取提交路径
	var data = $("#form_register").serialize();     //表单数据列表
	var index = null;
	$.ajax({ 
	 type:'post',  
	 url:targetUrl, 
	 cache: false,
	 data:data,  //重点必须为一个变量如：data
	 dataType:'json', 
	 beforeSend: function (){
         //ajax刷新前加载load动画
		 index = layer.load(1, {time: 30*1000,shade: [0.2,'#000']});
     },
	 success:function(data){  
		var returnJson = eval(data);
	 	if(data.returnMessage=="注册成功，快去登录吧")  {
	 		// layer.confirm(content, options, yes, cancel)
	 		layer.confirm('注册成功，为了您的账号安全和下次登录方便，建议您立即进行面部信息注册！', 
	 		{
	 			 btn: ['面部信息注册', '以后再说'], //可以无限个按钮
	 			 icon: 3, 
	 			 title:'谨告',
	 			 btn1: function(index){
			 		 //layer.alert("按下注册");
	 				 window.location = "faceLoginJsp/faceRegister.jsp";
			 		 layer.close(index);
		 		 },
		 		 btn2: function(index, layero){
		 			 //layer.alert("按下以后再说");
		 			 window.location = "login.jsp";
			 		 layer.close(index);
		 		 },
		 		 cancel:function(index, layero){
		 			 //layer.alert("按下关闭键");
		 			window.location = "faceLoginJsp/faceRegister.jsp";
		         },
	 		});
	 	}
	   	else parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000});
	   $("#register").removeAttr("disabled");
	 },
	 error:function(){ 
		 layer.alert("请求失败！");
	  $("#register").removeAttr("disabled");
	 },
	 complete:function () {			 
         //完成以后隐藏load动画
		 layer.close(index);
     }
	});//end ajax
});   //end click register


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
		 	if(data==0&&$("#user_id").val().length==12)  {
		 		$("#userId_tip").css("color","green");
		 		$("#userId_tip").text("学号通过");				 		
		 	}
		   	else if(data==1){
		   		$("#userId_tip").css("color","red");
		   		$("#userId_tip").text("用户已存在");					   		
		   	}	
		   	else{
		   		$("#userId_tip").css("color","red");
		   		$("#userId_tip").text("格式错误");				   		
		   	}
		 },
		 error:function(){ 
			 layer.alert("请求失败！");
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
})

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
	
})

//监听重置按钮
$(document).on('click', '#resetReg', function() {  
	$("#userId_tip").text("");	
	$("#password_tip").text("");	
	$("#password_confirm_tip").text("");
});


});//end ready


