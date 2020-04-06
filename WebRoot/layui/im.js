var basePath = $("#basePath").val();
var baseWsPath = $("#baseWsPath").val();
var mineName = $("#username").val();
var mineId = $("#userId").val();
var socket = null;  // 判断当前浏览器是否支持WebSocket
var mine =  {
    "username": mineName //我的昵称
    ,"id": mineId //我的ID
    ,"status": "online" //在线状态 online：在线、hide：隐身
    ,"remark": "在深邃的编码世界，做一枚轻盈的纸飞机" //我的签名
    ,"avatar": "https://q.qlogo.cn/qqapp/101235792/B704597964F9BD0DB648292D1B09F7E8/100" //我的头像
    ,"basePath": basePath
}
if ('WebSocket' in window) {
	socket = new WebSocket("ws://"+baseWsPath+"/LLWS/"+mineId);
} else {
	alert('该浏览器不支持本系统即时通讯功能，推荐使用谷歌或火狐浏览器！');
}
// 连接发生错误的回调方法
socket.onerror = function() {
    console.log("llws连接失败!");
};
// 连接成功建立的回调方法
socket.onopen = function(event) {
    console.log("llws连接成功!");
}
// 连接关闭的回调方法
socket.onclose = function() {
    console.log("llws关闭连接!");
}

// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
$(window).on('beforeunload',function(){
    socket.close();
});
layui.use('layim', function(){
    var layim = layui.layim;
    //先来个客服模式压压精
    layim.config({
        brief: true //是否简约模式（如果true则不显示主面板）
        ,init: {
            //配置客户信息
            mine:mine
        }
        ,uploadImage: {
            url: basePath+'/uploadFile?url=layim'
            ,type: '' //默认post
        }
        ,uploadFile: {
            url: basePath+'/uploadFile?url=layim'
            ,type: '' //默认post
        }
        ,chatLog: layui.cache.dir + 'css/modules/layim/html/chatlog.html' //聊天记录页面地址，若不开启，剔除该项即可
    });

    /**
     * layim建立就绪
     * 简约模式（即brief: true时）不会触发该事件
     * init直接赋值mine、friend的情况下（只有设置了url才会执行 ready 事件）
     */
    layim.on('ready',function(){
    });

	// 接收到消息的回调方法
    socket.onmessage = function(res) {
        console.log("llws收到消息啦:" +res.data);
        res = eval("("+res.data+")");
        if(res.type == 'friend' || res.type == 'group'){
            layim.getMessage(res);
        }else if(res.type == 'system'){
            layim.msgbox(res.num);
        }else if(res.type == 'addFriendFanKui'){
            parent.layui.layim.addList({
                type: 'friend'
                ,avatar: res.avatar //好友头像
                ,username: res.username //好友昵称
                ,groupid: res.group //所在的分组id
                ,id: res.id //好友ID
                ,sign: res.sign //好友签名
            });
            res.type = 'friend';
            layim.getMessage(res);
        }else{
            layim.setFriendStatus(res.id,res.content);
        }

    }

    // 监听发送消息
    layim.on('sendMessage', function(data){
	   var obj={
			    "mine":{
				   "avatar":data.mine.avatar,
				   "content":data.mine.content,
				   "id":data.mine.id,
				   "mine":true,
				   "username":data.mine.username
				 },
				 "to":{
					   "avatar":data.to.avatar,
					   "id":data.to.id,
					   "name":data.to.groupname,
					   "sign":data.to.sign,
					   "type":data.to.type,
					   "username":data.to.username
				 }
			   }
	    console.log(JSON.stringify(obj));
		socket.send(JSON.stringify(obj));  	//发送消息倒Socket服务
   });

    setTimeout(function(mid){
        //获取离线消息
        $.post(basePath+"/message?url=OfflineMsg&userId="+mid,function(res){
            if(res.status == 'success'){
                var data = res.data;
                $.each(data,function(k,v){
                    layim.getMessage(v);
                });
            }else{
                console.log("拉取离线消息失败！");
            }
        });
    },1000,mineId);

  //监听在线状态的切换事件
  layim.on('online', function(data){
    console.log(data);
  });

  //监听查看群员
  layim.on('members', function(data){
    console.log(data);
  });

  //监听聊天窗口的切换
  layim.on('chatChange', function(data){
    console.log(data);
  });


$('.contactPublisher').on('click', function(){
    var goodsPublisher = $(this).attr("publisher");
    $.ajax({
        async:false,
        type: "GET",
        url: basePath+'/usuallyController?url=userInfo',
        data:{"username":goodsPublisher},
        dataType: "json",
        success: function(rsp){
            var data = rsp.data;
            //自定义会话
            layim.chat(data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("请求异常");
        }
    });
});

});




