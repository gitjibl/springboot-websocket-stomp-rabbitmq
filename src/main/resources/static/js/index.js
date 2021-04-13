var stompClient = null;
$("#message").text("gggg");
//加载完浏览器后  调用connect（），打开双通道
$(function () {
    $("#sendMessageDiv").hide();
    $("#messageBox").hide();
})

function connect() {
    //连接SockJS的endpoint名称为"ws"
    var socket = new SockJS('/ws');
    //使用STMOP子协议的WebSocket客户端
    stompClient = Stomp.over(socket);
    //连接WebSocket服务端
    stompClient.connect({
        userid: $("#usernameTxt").val()
    }, onConnected, onError);
}

//强制关闭浏览器  调用websocket.close（）,进行正常关闭
window.onunload = function () {
    disconnect()
}
//链接websocket
$("#enter").click(function enter() {
    $("#enterDiv").hide();
    var usernameTxt = $("#usernameTxt").val();
    $("#username").text("当前用户：" + usernameTxt)
    $("#messageBox").show();
    $("#sendMessageDiv").show();
    connect();
});

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

}

//关闭双通道
function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    console.log(message)
    var formGroup = "<li class='list-group-item list-group-item-info'>"+message.sender +":    "+message.content+"</li>"
    $("#messageBox").append(formGroup);
}

function onError(error) {
    console.log('Could not connect to WebSocket server. Please refresh this page to try again!');
}

$("#sendMessage").click(function () {
    if (stompClient) {
        var chatMessage = {
            sender: $("#usernameTxt").val(),
            content: $("#content").val(),
            type: 'CHAT',
            to: 'all'

        };
        console.log("消息发送-----------");
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
    }
})
