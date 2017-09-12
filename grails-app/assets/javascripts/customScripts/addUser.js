
if(userCount++ == -1){
    $(".main").append(getUserInput())
}


$("#add-user").click(function(){
    if(userCount >= MAX_NUMBER_OF_USERS - 1){
        alert("Maximum number of players 6");
        return;
    }
    userCount++;
    $(".main").append(getUserInput())
})

$("#remove-user").click(function(){
    if(userCount <= MIN_NUMBER_OF_USERS - 1){
        alert("Mininum number of players 1");
        return;
    }
    $(".user" + userCount--).remove();
})

function getUserInput(){
    return '<div class="user' + userCount + '" ><lable>Player ' + (userCount + 1) + '</lable><br><input type="text" name="users.names[' + userCount + ']" id="users.names[' + userCount + ']"> <div>'
}

$(".close").click(function(){
    $(this).parent().remove();
})