

function SaveUserInfo(){
	/*表单验证*/
	var user_tel = $("#userTel").val();
	if (!(/^1[34578]\d{9}$/.test(user_tel))||user_tel.length!=11) 
	{		 //验证电话号码格式
		layer.msg('电话号码格式错误');
		updateProfile.userTel.focus();
		return false;
	}
	
	
　　var myemail=$("#userEmail").val();  //邮箱格式的正则表达式
　　var myReg=/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/;  
	if(!myReg.test(myemail)){  //验证邮箱格式		　　　
　　　　layer.msg('请输入正确的邮箱地址');
		updateProfile.userEmail.focus();
		return false;
	}
	
	if($("#userAddress").val()==""){
		layer.msg('请输入收货地址');
		$("#userAddress").focus();
		return false;
	}
	if($("#userGrade").val()==""){
		layer.msg('请选择年级');
		$("#userGrade").focus();
		return false;
	}
	
	/*如果验证通过*/	
	$("#saveUserInfo").attr('disabled',"true");  //防止重复提交
	console.log("进来了ajax");
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
			 var resultState = returnJson.resultState;
			 var resultMessage = returnJson.returnMessage;
			 
			 if(resultState=="1"){  //如果回调为成功的信息
				 layer.confirm(resultMessage, 
		 		 {
		 			 btn: ['确认'], //可以无限个按钮
		 			 icon: 1, 
		 			 title:'修改成功',
		 			 btn1: function(index){
				 		 layer.close(index);
			 		 },		
		 		 });
				
			 }
			 else {
				 layer.confirm(resultMessage, 
		 		 {
		 			 btn: ['确认'], //可以无限个按钮
		 			 icon: 2, 
		 			 title:'警告！修改的信息已被拦截',
		 			 btn1: function(index){
				 		 layer.close(index);
			 		 },		
		 		 });
			 }
			
		 },
		 error:function(){ 
			 layer.alert("请求失败，请重新登录！");
		 },
		 complete:function () {		
			 $("#saveUserInfo").removeAttr('disabled');  //移除按钮禁止
             //完成以后隐藏load动画
			 layer.close(index);
         }
	});//end ajax
	
};//end function

//制造彩蛋

function trick(e){
    if(typeof window.k=="undefined"){
        window.k=""
    }
    var e=e||event;
    //alert(e.keyCode);
    if(e.keyCode==116) window.k=""; 
    window.k+=e.keyCode+",";
    console.log(window.k);
    if(window.k=="38,38,40,40,37,39,37,39,66,65,66,65,") {
    	window.k=""; //执行play 同时清空
    	window.open("easter_egg.html", "_blank");     
    }        
}
document.onkeydown=trick; 

