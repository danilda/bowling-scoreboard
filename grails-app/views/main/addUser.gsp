<html>
    <head>
        <title>Main Page</title>
    </head>
    <body>
        <div>
            <g:form action="addUser"  method="POST">
                <g:hiddenField name="date" value="123" />
                <g:each status="i" in="${users.names}" var="name">
                    <g:textField name="users.names[$i]" value="${name}" /><br>
                </g:each>
                <g:actionSubmit value="Add user" class="btn btn-primary" action="addUser"/>
                <g:actionSubmit value="Start game" class="btn btn-primary" action="addNewGame"/>
                <g:if test="${flash.error}">
                     <h3>${flash.error}!</h3>
                </g:if>
            </g:form>
        </div>
    </body>
</html>