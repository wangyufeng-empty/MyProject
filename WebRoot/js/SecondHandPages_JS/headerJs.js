$(document).ready(function(){
	/*为下拉框选择默认值*/
	var user_grade = $("#user_grade").val();
	$("#"+user_grade).attr("selected","true");
	
	/*分类搜索*/
	$('.CategorySearch').on('click', function(){
	    var category = $(this).attr("category");
	    var targetUrl = "usuallyController";
	    var data = {"category":category,"url":"CategorySearch"}
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
				 console.log("goUrl："+returnJson.goUrl);
				 window.location.href = returnJson.goUrl;
				
			 },
			 error:function(){ 
				 layer.alert("请求失败，请重新登录！");
			 },
			 complete:function () {			 
                //完成以后隐藏load动画
//			
				 layer.close(index);
            }
		});//end ajax
	});// end click
	
	/*全站模糊搜索*/
	$("#site-search").on('submit', function(){
	  
	    var targetUrl = "usuallyController";
	    var data = $("#site-search").serialize();     //表单数据列表
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
				 if(returnJson.hasOwnProperty("goUrl")){   
					 window.location.href = returnJson.goUrl;
				 }
				else{
				 		parent.layer.msg(returnJson.returnMessage,{icon:5,time:3000});				 		
					}
				
			 },
			 error:function(){ 
				 layer.alert("请求失败，请重新登录！");
			 },
			 complete:function () {			 
                //完成以后隐藏load动画
//			
				 layer.close(index);
            }
		});//end ajax
	});// end click
	
	
	
	/*清空购物车*/
	$("#clearGoodsCart").on("click",function(){
		console.log("clearGoodsCart");
		layer.confirm("你确定要清空所有已选商品吗",function(){
			var targetUrl = "usuallyController";
			var data = {"url":"清空购物车"};
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
					 index = layer.load(5, {time: 30*1000,shade: [0.1,'#fff']});
	             },
				 success:function(data){  
					 var returnJson = eval(data);
					 
					 if(returnJson.hasOwnProperty("status")){  //如果回调为成功的信息
						 parent.layer.msg(returnJson.returnMessage,{icon:6,time:1000,end:function(){window.location.reload();}});
					 }
					 else parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000}); 
				 },
				 error:function(){ 
					 layer.alert("请求失败，请重新登录！");
				 },
				 complete:function () {			 
	                 //完成以后隐藏load动画
//				
					 layer.close(index);
	             }
			});//end ajax
		}); //end confirm
		
	});//end function
	
	/*处理发布商品页面图片上传*/
	 var allImgs = $("#allImgs");
     layui.use(['upload', 'layer'], function () {
         var upload = layui.upload,
             layer = layui.layer;
         //多文件上传
         var multipleUploadInst = upload.render({
             elem: '#uploadImages'
             , url: basePath + '/uploadFile?url=multipleUpload'
             , multiple: true
             , acceptMime: 'image/*'
             , field: 'files'
             , choose: function (obj) {
                 layer.load(); //上传loading
             }
             , done: function (res) {
                 if (res.code == '0') {//成功
                     $.each(res.data, function (k, o) {
                         var imgHtml = '<li><a href="#' + o.identifier + '" class="img-wrap"><span onclick="closeImg(this);" class="close">&times;</span><img onclick="imgClick(this);" src="' + o.src + '" alt="Product"></a></li>';
                         allImgs.append(imgHtml);
                     });
                 } else {
                     layer.msg(res.msg, {
                         icon: 2
                     });
                 }
             }
             , allDone: function (obj) { //当文件全部被提交后，才触发
                 $("#allImgs li:last-child").find("img").click();
                 layer.closeAll('loading');
                 layer.msg("文件上传完成！", {
                     icon: 1
                 });
             }
             , error: function () {
                 layer.closeAll('loading');
                 layer.msg("请求异常，请重新登录", {
                     icon: 2
                 });
             }
         });
     });
	
	
});//end ready

/*请求导航栏跳转*/
function NavigationJump(method){
	var targetUrl = "usuallyController";
    var data = {"url":method};     //表单数据列表
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
			
			 window.location.href = returnJson.goUrl;
			
		 },
		 error:function(){ 
			 layer.alert("请求失败，请重新登录！");
		 },
		 complete:function () {			 
            //完成以后隐藏load动画
//		
			 layer.close(index);
        }
	});//end ajax
}


/*清单详情页面确认收货*/
function ConfirmReceipt(order_id){
	layer.confirm("确认收货吗？",function(){
		var targetUrl = "usuallyController";
		var data = {"url":"确认收货","order_id":order_id};
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
					 parent.layer.msg(returnJson.returnMessage,{icon:6,time:2000,end:function(){window.location.reload();}});
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
	}); //end confirm
}



