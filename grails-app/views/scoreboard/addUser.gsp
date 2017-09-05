<html>
<head>
    <title>Main Page</title>
    <asset:stylesheet src="customStyles/addUserStyle.css"/>
    <asset:stylesheet src="bootstrap.css"/>
</head>

<body>
<div>

    <div class="navbar navbar-static-top top">
            <div class="collapse navbar-collapse" id="responsive-menu">
                <ul class="nav navbar-nav middle">
                    <li><g:link controller="scoreboard" action="index">Main menu</g:link></li>
                    <li> <g:link controller="scoreboard" action="showGameList">Show all games</g:link></li>
                </ul>
            </div>
        </div>
    </div>

    <g:form action="addUser" method="POST">
        <div class="nav middle">
            <g:actionSubmit value="Add user" class="btn btn-success" action="addUser"/>
            <g:actionSubmit value="Remove user" class="btn btn-danger" action="removeUser"/>
            <g:actionSubmit value="Start game" class="btn btn-primary" action="addNewGame"/>
        </div>
        <div class="main middle">
            <g:each status="i" in="${users.names}" var="name">
                <lable>Player ${i + 1}</lable> <br>
                <g:textField name="users.names[$i]" value="${name}"/><br><br>
            </g:each>
        </div>
        <g:renderErrors bean="${users}"/>
        <g:if test="${flash.errors}">
            <h3>${flash.errors}!</h3>
        </g:if>
    </g:form>

</div>
</body>
</html>