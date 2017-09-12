<html>
<head>
    <title>Main Page</title>
    <asset:stylesheet src="bootstrap.css"/>
    <asset:stylesheet src="customStyles/addUserStyle.css"/>
    <asset:stylesheet src="customStyles/navBar.css"/>
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

    <g:form action="addUser" method="POST" id="users">
        <div class="game-buttons middle">
            <button type="button" class="btn btn-success" id="add-user">Add user</button>
            <button type="button" class="btn btn-danger" id="remove-user">Remove user</button>
            <g:actionSubmit value="Start game" class="btn btn-primary" action="addNewGame"/>
        </div>
        <div class="main middle">
            <g:each status="i" in="${users?.names}" var="name">
                <div class="user$i">
                    <lable>Player ${i + 1}</lable> <br>
                    <g:textField name="users.names[$i]" value="${name}"/>
                </div>
            </g:each>
        </div>
        <g:if test="${error}">
            <g:hasErrors bean="${users}">
                <div class="alert alert-warning alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Warning!</strong> <g:message error="${users?.errors?.getFieldErrors('names')[0]}" />
                </div>
            </g:hasErrors>
        </g:if>


    </g:form>

    <script>
        userCount = ${(users?.names?.size()?:0) - 1 };
        MAX_NUMBER_OF_USERS = ${constants.GameConstants.MAX_NUMBER_OF_USERS};
        MIN_NUMBER_OF_USERS = ${constants.GameConstants.MIN_NUMBER_OF_USERS};
    </script>
    <asset:javascript src="jquery-2.2.0.min.js"/>
    <asset:javascript src="customScripts/addUser.js"/>
</div>
</body>
</html>