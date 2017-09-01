<!DOCTYPE html>
<html>
    <head>
        <title>Main Page</title>
    </head>
    <body>
        <h2> Hello World ${user.name}</h2>
        <div>
            <g:link controller="main" action="addUser">Create new game</g:link>
        </div>
        <div>
            <g:link controller="main" action="showAllGames">Show all games</g:link>
        </div>
    </body>
</html>