
$("#profile-img").click(function () {
    $("#status-options").toggleClass("active");
});

$(".expand-button").click(function () {
    $("#profile").toggleClass("expanded");
    $("#contacts").toggleClass("expanded");
});

$("#status-options ul li").click(function () {
    $("#profile-img").removeClass();
    $("#status-online").removeClass("active");
    $("#status-away").removeClass("active");
    $("#status-busy").removeClass("active");
    $("#status-offline").removeClass("active");
    $(this).addClass("active");

    if ($("#status-online").hasClass("active")) {
        $("#profile-img").addClass("online");
    } else if ($("#status-away").hasClass("active")) {
        $("#profile-img").addClass("away");
    } else if ($("#status-busy").hasClass("active")) {
        $("#profile-img").addClass("busy");
    } else if ($("#status-offline").hasClass("active")) {
        $("#profile-img").addClass("offline");
    } else {
        $("#profile-img").removeClass();
    }
    ;

    $("#status-options").removeClass("active");
});



function addFriendToGroupChat(idFriend, idGroupChat) {

    console.log("idFriend " + idFriend);
    console.log("idGroupChat " + idGroupChat);

    $.ajax({
        type: 'POST',
        contentType : "application/json",
        url : "/addFriend/"+idFriend+"/toGroupChat/"+ idGroupChat,
        success : function() {
            console.log("request SUCCESS");
            document.getElementById('button-add-member-'+idFriend).className = "fa fa-check";
        },
        error : function() {
            console.log("ERROR");
        }
    })
}

function editLogoGroupChat(idGroupChat) {
    var formData = new FormData();
// Attach file
    formData.append('file', $('#logo-group-chat')[0].files[0]);
    console.log($('#logo-group-chat')[0].files[0]);
    console.log(formData)
    console.log(idGroupChat)
    $.ajax({
        url: "/editLogoGroupChat/"+idGroupChat,
        data: formData,
        type: 'POST',
        contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
        processData: false, // NEEDED, DON'T OMIT THIS
        // ... Other options like success and etc
        success : function() {
            console.log("request SUCCESS");
            location.reload();

        },
        error : function() {
            console.log("ERROR");
        }
    });
}

function reloadPageOnClickCloseButton() {
    location.reload();
}

function clearDisableButton() {
    document.getElementById('button-submit-logo').disabled = false;
}