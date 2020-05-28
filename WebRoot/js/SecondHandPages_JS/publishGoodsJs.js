
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
