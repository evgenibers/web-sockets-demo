var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#messages-list").html("");
}

function connect() {
    var socket = null;
    if ($("#connect-as-admin").is(":checked")) {
        socket = new SockJS('/api/secured/admin/stomp')
    } else {
        socket = new SockJS('/api/secured/stomp')
    }
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/topic/secured/echo', function (message) {
            showMessage(JSON.parse(message.body).text);
        });
        stompClient.subscribe('/user/topic/secured/session/echo', function (message) {
            showMessage(JSON.parse(message.body).text);
        });
        stompClient.subscribe('/topic/secured/broadcast/echo', function (message) {
            showMessage(JSON.parse(message.body).text);
        });
        stompClient.subscribe('/user/topic/secured/admin/echo', function (message) {
            showMessage(JSON.parse(message.body).text);
        });
        stompClient.subscribe('/user/topic/secured/error', function (message) {
            alert("Error message: " + message.body);
        });
    },
    function (error) {
        console.log("Error: " + error);
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendToUser() {
    stompClient.send("/api/secured/echo", {}, JSON.stringify({'text': $("#input-user").val()}));
}

function sendToSession() {
    stompClient.send("/api/secured/session/echo", {}, JSON.stringify({'text': $("#input-session").val()}));
}

function sendToAll() {
    stompClient.send("/api/secured/broadcast/echo", {}, JSON.stringify({'text': $("#input-all").val()}));
}

function sendToAdmin() {
    stompClient.send("/api/secured/admin/echo", {}, JSON.stringify({'text': $("#input-admin").val()}));
}

function showMessage(message) {
    $("#messages-list").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function() { connect(); });
    $("#disconnect").click(function() { disconnect(); });
    $("#send-user").click(function() { sendToUser(); });
    $("#send-session").click(function() { sendToSession(); });
    $("#send-all").click(function() { sendToAll(); });
    $("#send-admin").click(function() { sendToAdmin(); });
});