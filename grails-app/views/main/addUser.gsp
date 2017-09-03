<html>
<head>
    <title>Main Page</title>
    <asset:stylesheet src="customStyles/addUserStyle.css"/>
</head>

<body>
<div>

    <g:form action="addUser" method="POST">
        <div class="nav middle">
            <g:actionSubmit value="Add user" class="btn btn-success" action="addUser"/>
            <g:actionSubmit value="Remove user" class="btn btn-danger" action="removeUser"/>
            <g:actionSubmit value="Start game" class="btn btn-primary" action="addNewGame"/>
        </div>
        <div class="main middle">
            <g:hiddenField name="date" value="123"/>
            <g:each status="i" in="${users.names}" var="name">
                <lable>Player ${i + 1}</lable> <br>
                <g:textField name="users.names[$i]" value="${name}"/><br><br>
            </g:each>
        </div>
        <g:if test="${flash.error}">
            <h3>${flash.error}!</h3>
        </g:if>
    </g:form>

</div>
</body>
</html>