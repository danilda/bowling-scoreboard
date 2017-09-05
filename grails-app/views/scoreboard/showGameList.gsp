<!DOCTYPE html>
<html>
    <head>
        <asset:stylesheet src="showGameList.css"/>
        <title>Main Page</title>
    </head>
    <body>
        <div class="main middle">
            <div class="items">
            <g:each var="game" in="${gameList}">
                <g:link controller="scoreboard" action="showGame" params="[ id: game.id]">
                    <div class="item">
                        <div class="game-id">Game id: ${game.id}</div>
                        <div class="game-date">Game date: ${game.date}</div>
                    </div>
                </g:link>
            </g:each>
            </div>
            <div class="clearfix"></div>
        </div>
    </body>
</html>