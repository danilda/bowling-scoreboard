<!DOCTYPE html>
<html>
    <head>
        <asset:stylesheet src="customStyles/indexStyle.css"/>
        <title>Main Page</title>
    </head>
    <body>
        <div class="middle main">
            <h1 class="head"> Bowling Scoreboard </h1>
            <h3>
                <g:link controller="main" action="addUser">Create new game</g:link>
            </h3>
            <h3>
                <g:link controller="main" action="showGameList">Show all games</g:link>
            </h3>
        </div>
    </body>
</html>