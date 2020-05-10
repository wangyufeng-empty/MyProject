$(document).ready(function(){
	
});//end ready

function check() {
	if($("#goods_name").val() == ""){
		 layer.msg("请填写商品名！");
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
    
    if($("#goods_stock").val() > 20||$("#goods_stock").val() <0) {
        layer.msg("发布数量请少于20件！");
        $("#goods_stock").focus();
        return false;
    }
    if($("#goods_describe").val() == "") {
        layer.msg("请填写商品描述！");
        $("#goods_describe").focus();
        return false;
    }
   
}
