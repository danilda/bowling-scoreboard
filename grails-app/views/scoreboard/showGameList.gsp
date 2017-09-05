<!DOCTYPE html>
<html>
    <head>
        <asset:stylesheet src="bootstrap.css"/>
        <asset:stylesheet src="customStyles/navBar.css"/>
        <asset:stylesheet src="customStyles/showGameList.css"/>
        <%@ page import="java.text.SimpleDateFormat" %>
        <title>Main Page</title>
    </head>
    <body>
        <div>
            <div class="navbar navbar-static-top top">
                <div class="collapse navbar-collapse" id="responsive-menu">
                    <ul class="nav navbar-nav">
                        <li><g:link controller="scoreboard" action="index">Main menu</g:link></li>
                        <li> <g:link controller="scoreboard" action="showGameList">Show all games</g:link></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="main middle">
            <div class="items">
            <g:each var="game" in="${gameList}">
                <g:link controller="scoreboard" action="showGame" params="[ id: game.id]">
                    <div class="item">
                        <div class="game-id">Game id: ${game.id}</div>
                        <%SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss")%>
                        <div class="game-date">Game date: ${dateFormat.format(game.date)}</div>
                    </div>
                </g:link>
            </g:each>
            </div>
            <div class="clearfix"></div>
        </div>
    </body>
</html>