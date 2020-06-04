
function check() {
	if($("#goods_name").val() == ""){
		 layer.msg("请填写商品名！");
		 $("#goods_name").focus();
	     return false;
	}
	if($("#goods_name").val().length >20){
		 layer.msg("商品名过长！");
		 $("#goods_name").focus();
	     return false;
	}
	var myeprice = $("#goods_price").val();  //
    var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
    if (!reg.test(myeprice)) {
        layer.msg("请输入正确的价格");
        $("#goods_price").focus();
        return false;
    }
    if($("#goods_stock").val() == "") {
        layer.msg("请填写发布数量！");
        $("#goods_stock").focus();
        return false;
    }
    
    if($("#goods_stock").val() > 20||$("#goods_stock").val() <=0) {
        layer.msg("数量请小于20或者输入合法数量");
        $("#goods_stock").focus();
        return false;
    }
    if($("#goods_describe").val() == "") {
        layer.msg("请填写商品描述！");
        $("#goods_describe").focus();
        return false;
    }
    if($("#goods_describe").val().length <20) {
        layer.msg("描述过于简单！");
        $("#goods_describe").focus();
        return false;
    }
   
}

function imgClick(obj){
    $("#bigImg").html('<img src="'+$(obj).attr("src")+'" alt="Product">');
    $(obj).parent().parent().siblings().removeClass('active');
    $(obj).parent().parent().addClass("active")
}
function closeImg(obj){
    $(obj).parent().parent().remove();
    if($("#allImgs li:last-child").length > 0){
        $("#allImgs li:last-child").find("img").click();
    }else{
        $("#bigImg").html('');
    }

}
function submitGoods(){
	if(check()==false){
		return false;
	}
    var goodsPictures = new Array();
    $("#allImgs li").each(function(){
        goodsPictures.push($(this).find("img").attr("src"));
    });
    $("#goodsPictures").val(goodsPictures.join(","));
//     $("#publishGoodsForm").submit();
	
    var targetUrl = "usuallyController";    //获取提交路径
	var data = $("#publishGoodsForm").serialize();     //表单数据列表
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
			 var resultMessage = returnJson.resultMessage;
			 
			 if(resultState=="1"){  //如果回调为成功的信息
				 layer.confirm(resultMessage, 
		 		 {
		 			 btn: ['确认'], //可以无限个按钮
		 			 icon: 1, 
		 			 title:'发布成功',
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
		 			 title:'警告！发布已被拦截',
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
             //完成以后隐藏load动画
			 layer.close(index);
         }
	});//end ajax
    
}



	
