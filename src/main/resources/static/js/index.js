var stompClient = null;
var username_ = "";
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
        username: $("#usernameTxt").val()
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
    //私聊频道
    stompClient.subscribe('/user/topic/private', onMessageReceived);
    //通知用户上线
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: $("#usernameTxt").val(), type: 'JOIN', to: 'ALL'})
    )

}

//关闭双通道
function disconnect() {
    if (stompClient != null) {
        stompClient.send("/app/chat.deleteUser",
            {},
            JSON.stringify({sender: $("#usernameTxt").val(), type: 'LEAVE', to: 'ALL'})
        )
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

//公共订阅频道回调
function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    console.log("公共频道", message)
    var formGroup = "";
    var privateChatBox = "";
    //在线用户
    var onlineUsers = message.onlineUsers;
    var userBox = "";
    if (message.type == "JOIN" || message.type == "LEAVE") {
        if(message.type == "JOIN"){
            formGroup = "<li class='list-group-item list-group-item-success'>" + "“" + message.sender + "”" + "上线了！" + "</li>"
        }else{
            formGroup = "<li class='list-group-item list-group-item-warning'>" + "“" + message.sender + "”" + "下线了！" + "</li>"
        }
        for (var key in onlineUsers) {
            userBox += "<li class='list-group-item list-group-item-success'>" + onlineUsers[key];
            if (onlineUsers[key] != $("#usernameTxt").val()) {
                userBox += "<button  style='margin-left: 100px;' onclick='privateChat(" + "`" + onlineUsers[key] + "`" + ")' type='button' class='btn btn-info btn-xs'>私聊</button>";
            }
            userBox += "</li>";
        }
        $("#userBox").empty();
        $("#userBox").append(userBox);
        $("#messageBox").append(formGroup);
    } else if (message.type == "CHAT") {
        formGroup = "<li class='list-group-item list-group-item-info'>" + message.sender + ":    " + message.content + "</li>"
        $("#messageBox").append(formGroup);
    } else if (message.type == "PRIVATE_CHAT") {
        privateChatBox = "<li class='list-group-item list-group-item-info'>" + message.sender + ":    " + message.content + "</li>"
        $("#privateChatBox").append(privateChatBox);
    }

}

function onError(error) {
    console.log('Could not connect to WebSocket server. Please refresh this page to try again!');
}

//发送消息
$("#sendMessage").click(function () {
    if (stompClient) {
        var chatMessage = {
            sender: $("#usernameTxt").val(),
            content: $("#content").val(),
            type: 'CHAT',
            to: 'ALL'

        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
    }
})

//私聊
function privateChat(username) {
    username_ = username;
    $("#myModalLabel").text(username)
    $("#myModal").modal('show')
}

function sendToUser() {
    //私聊
    var chatMessage = {
        sender: $("#usernameTxt").val(),
        content: $("#messageTxt").val(),
        type: 'PRIVATE_CHAT',
        to: username_

    };
    stompClient.send("/app/chat.sendMessageToUser",
        {},
        JSON.stringify(chatMessage))
    var privateChatBox = "<li class='list-group-item list-group-item-info'>" + $("#usernameTxt").val() + ":    " + $("#messageTxt").val() + "</li>"
    $("#privateChatBox").append(privateChatBox);
}

