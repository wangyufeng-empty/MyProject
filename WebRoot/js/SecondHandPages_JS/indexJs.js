$(document).ready(function(){
	
//	/*获得轮播图*/
//	var url = "getAllRotationChart";
//	$.ajax({ 
//		
//		 type:'post',  
//		 url:"usuallyController", 
//		 cache: false,
//		 data:{"url":url},  //重点必须为一个变量如：data
//		 dataType:'json', 
//		 success:function(data){  
//			 var JsonArray = eval(data);//解析json数组
//			 var htmlStr = "";//待嵌入的html代码   双引号：&quot;   单引号：&apos;
//			 htmlStr += 
//					"<div class='hero-slider home-1-hero'>"+
//					 	"<div class='owl-carousel large-controls dots-inside' data-owl-carousel='{&quot;nav&quot;: true, &quot;dots&quot;: true, &quot;loop&quot;: true, &quot;autoplay&quot;: true, &quot;autoplayTimeou&quot;: 7000}'>";
//			 for(var i=0;i<JsonArray.length;i++){				
//				 htmlStr += 		
//						"<div class='item'>"+
//						 "<div class='container padding-top-3x'>"+
//		                 	"<div class='row justify-content-center align-items-center'>"+
//				                 "<div class='col-lg-5 col-md-6 padding-bottom-2x text-md-left text-center hidden-md-down'>"+	                 
//				                     "<div class='from-bottom'>"+
//				                         "<img class='d-inline-block w-150 mb-4' src='assets/images/hero-slider/logo02.png' alt='Puma'>"+
//				                         "<div class='h2 text-body text-normal mb-2 pt-1'>" + JsonArray[i].goods_name + "</div>"+
//				                         "<div class='h2 text-body text-normal mb-4 pb-1'>最低只要<span class='text-bold'>" + JsonArray[i].goods_price + "元</span></div>"+     
//				                     "</div>"+	                     
//				                     "<a class='btn btn-primary scale-up delay-1' href='usuallyController?url=商品详情&goods_id="+JsonArray[i].goods_id+"'>查看详情</a>"+
//				                 "</div>"+
//				                 "<div class='col-md-6 padding-bottom-2x mb-3'>"+
//				                     "<img class='d-block mx-auto' src='" + JsonArray[i].product_image + "' alt='Puma Backpack'>"+
//				                 "</div>"+
//			                 "</div>"+
//			                "</div>"+
//			             "</div>"	
//			 }
//			 htmlStr +=  "</div>"+
//	         		"</div>";
//			 /*把拼接的html串放在相应的位置中*/
//			 console.log(htmlStr);
//			 $("#rotationChart").html(htmlStr);
//		 },
//		 error:function(){ 
//			 alert("请求失败");
//		 }
//	});//end ajax

}); 

//加入收藏
//e 代表每个项目对象
function collectGoods(e){  
	var goods_id = $(e).attr("id");
	console.log("goods_id:"+goods_id);
	var url = "加入收藏";
	var targetUrl = "usuallyController";
	var data = {"url":url,"goods_id":goods_id};
	$.ajax({ 
			 type:'post',  
			 url:targetUrl, 
			 cache: false,
			 data:data,  //重点必须为一个变量如：data
			 dataType:'json', 
			 success:function(data){  
				 var returnJson = eval(data);
				 if(returnJson.hasOwnProperty("status")){  //如果回调为成功的信息
					 parent.layer.msg(returnJson.returnMessage,{icon:6,time:4000});
					 sleep(4000); //休眠4秒
				 }
				 else {parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000});sleep(4000); }//休眠n秒
				 
			 },
			 error:function(){ 
				 alert("请求失败");
			 }
		});//end ajax
	
}

//加入购物车
function addToCart(e){
	var goods_id = $(e).attr("id");
	console.log("goods_id:"+goods_id);
	var url = "加入购物车";
	var targetUrl = "usuallyController";
	var data = {"url":url,"goods_id":goods_id};
	$.ajax({ 
			 type:'post',  
			 url:targetUrl, 
			 cache: false,
			 data:data,  //重点必须为一个变量如：data
			 dataType:'json', 
			 success:function(data){  
				 var returnJson = eval(data);
				 if(returnJson.hasOwnProperty("status")){  //如果回调为成功的信息
					 parent.layer.msg(returnJson.returnMessage,{icon:6,time:4000});
					
				 }
				 else {parent.layer.msg(returnJson.returnMessage,{icon:5,time:4000});  }//休眠4秒
				 //sleep(4000);
			 },
			 error:function(){ 
				 alert("请求失败");
			 }
		});//end ajax
}

var sleep = function(time) {
    var startTime = new Date().getTime() + parseInt(time, 10);
    while(new Date().getTime() < startTime) {}
};

