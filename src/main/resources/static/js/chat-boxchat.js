const url = 'http://localhost:9091';
let stompClient;
let selectedUser;


function connectToChat(userName) {
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            let data = JSON.parse(response.body);
            render(data.messageBody, data.messageSendId);
        });
    });
}

function sendMsg(from, messageBody, to) {
    selectedUser = $('#selectedUser').val();
    to = selectedUser;
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        fromLogin: from,
        messageSendId: from,
        messageReceiverId: to,
        messageBody: messageBody
    }));
}


window.onload = function() {
    scrollToBottom();
    let userName = document.getElementById("userId").value;
    connectToChat(userName);

};