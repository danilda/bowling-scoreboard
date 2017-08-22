<!DOCTYPE html>
<html>
    <head>
        <title>Main Page</title>
    </head>
    <body>
        <h2> New Game ${game.date} </h2>
        <div>
            <g:link controller="main" action="newUser" params = "[game: game]" >New User ${game.date}</g:link>
        </div>
    </body>
</html>