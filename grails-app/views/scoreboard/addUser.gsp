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

    <g:form action="addUser" method="POST">
        <div class="game-buttons middle">
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
        <g:if test="${error == true}">
            <g:hasErrors bean="${users}">
                <div class="alert alert-warning alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Warning!</strong> <g:message error="${users.errors.getFieldErrors('names')[0]}" />
                </div>
            </g:hasErrors>
        </g:if>
        <g:if test="${flash.error != null}">
            <div class="alert alert-warning alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <strong>Warning!</strong> <g:message error="${flash.error}" />
            </div>
        </g:if>


    </g:form>

</div>
</body>
</html>