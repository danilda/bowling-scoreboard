<%@ page import="grails.converters.JSON" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Main Page</title>
    </head>
    <body>
        <h2> New Game ${game.date} </h2>
        <div>
            <g:link controller="main" action="newUser" params = "[game: game]" >New User ${game.date}</g:link>
            <g:form action="newUser" params="[game: game as JSON]" >
                <g:submitButton name="create" class="save" value="New User" />
            </g:form>
        </div>
    </body>
</html>