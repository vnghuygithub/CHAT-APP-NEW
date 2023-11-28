const url = 'http://localhost:9091';
let stompClient;
let selectedUser;


function connectToChat(selectedGroupChat) {
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe("/topic/group-chat/messages/" + selectedGroupChat, function (response) {
            let data = JSON.parse(response.body);
            render(data.messageBody, data.messageSendId);
        });
    });
}

function sendMsg(from, messageBody, to) {
    selectedUser = $('#selectedGroupChat').val();
    to = selectedUser;
    stompClient.send("/app/group/chat/" + selectedUser, {}, JSON.stringify({
        fromLogin: from,
        messageSendId: from,
        messageReceiverId: to,
        messageBody: messageBody
    }));
}


window.onload = function() {
    scrollToBottom();
    let selectedGroupChat = document.getElementById("selectedGroupChat").value;
    connectToChat(selectedGroupChat);
};