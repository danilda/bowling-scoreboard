<%@ page import="grails.converters.JSON" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Main Page</title>
    </head>
    <body>
        <h2> New Game ${date} </h2>
        <div>
            <g:form action="newUser"  method="POST">
                <g:hiddenField name="usersCount" value="${model?.usersCount?: usersCount}" />
                <g:hiddenField name="date" value="${date}" />
                <g:hiddenField name="users[0].frames[0].rollOne" value="1" />
                <g:hiddenField name="users[0].frames[0].rollTwo" value="2" />
                <g:hiddenField name="users[0].frames[0].rollThree" value="3" />
                <g:submitButton name="create" class="save" value="New User" />
            </g:form>
        </div>
    </body>
</html>