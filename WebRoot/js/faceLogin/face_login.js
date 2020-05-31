

$(document).ready(function(){
	var user_id = $("#reqUser_Id").val();
	console.log("reqUser_Id: "+user_id);
	if(user_id!="null"){
		$("#user_id").attr("value",user_id);
	}
	
})

function faceLoginNext(){
if ($("#user_id").val().length != 12) {
		
		layer.msg('学号格式错误');
		$("#user_id").focus();
		return false;		
	}
	/*如果验证通过*/	
	
	var targetUrl = "/SecondHandShopping/faceLoginController";    //获取提交路径
	var data = $("#faceLoginForm").serialize();     //表单数据列表
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
			//判断返回的json中是否含有某个属性
			if(returnJson.hasOwnProperty("goUrl")){   
				console.log("goUrl："+returnJson.goUrl);
				window.location.href = ""+returnJson.goUrl;
			}
			else{
		 		parent.layer.msg(returnJson.resultMessage,{icon:5,time:3000});		 		
			}
		 },
		 error:function(){ 
			 layer.alert("请求失败！");
		 },
		 complete:function () {			 
             //完成以后隐藏load动画
			
			layer.close(index);
         }
	});  //end ajax
}