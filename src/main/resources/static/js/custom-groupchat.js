let $chatHistory;
let $button;
let $textarea;
let $chatHistoryList;

function init() {
    cacheDOM();
    bindEvents();

}

function bindEvents() {
    $button.on('click', addMessage.bind(this));
    $textarea.on('keyup', addMessageEnter.bind(this));
}

function cacheDOM() {
    $chatHistory = $('.messages');
    $button = $('#sendBtn');
    $textarea = $('#message-to-send');
    $chatHistoryList = $chatHistory.find('ul');
}

function render(messageBody, userName) {
    let userId = $('#userId').val();
    if (userName != userId) {
        scrollToBottom();
        // ben trai
        var templateResponse = Handlebars.compile($("#message-response-template").html());
        var contextResponse = {
            response: messageBody,
            time: getCurrentTime(),
            fromUserName: userName,
            image: '/image/display/' + userName
        };

        setTimeout(function () {
            $chatHistoryList.append(templateResponse(contextResponse));
            scrollToBottom();
        }.bind(this), 500);
    }
}

function sendMessage(messageBody) {
    let userId = $('#userId').val();
    sendMsg(userId, messageBody, selectedUser);
    scrollToBottom();
    if (messageBody.trim() !== '') {
        //ben phai
        var template = Handlebars.compile($("#message-template").html());
        var context = {
            messageOutput: messageBody,
            time: getCurrentTime(),
            toUserName: selectedUser,
            image: '/image/display/' + userId

        };

        $chatHistoryList.append(template(context));
        scrollToBottom();
        $textarea.val('');
    }
}


function scrollToBottom() {
    $chatHistory.scrollTop($chatHistory[0].scrollHeight);
}

function getCurrentTime() {
    /*    return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");*/
    return new Date().toLocaleTimeString();
}

function addMessage() {
    sendMessage($textarea.val());
}

function addMessageEnter(event) {
    // enter was pressed
    if (event.keyCode === 13) {
        addMessage();
    }
}


init();

