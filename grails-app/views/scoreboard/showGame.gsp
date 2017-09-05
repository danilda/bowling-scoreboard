<html>
    <head>
        <title>Main Page</title>
        <asset:stylesheet src="customStyles/addUserStyle.css"/>
        <asset:stylesheet src="bootstrap.css"/>
    </head>
    <body>
        <div>
            <div class="navbar navbar-static-top">
                <div class="collapse navbar-collapse" id="responsive-menu">
                    <ul class="nav navbar-nav">
                        <li><g:link controller="scoreboard" action="index">Main menu</g:link></li>
                        <li> <g:link controller="scoreboard" action="showGameList">Show all games</g:link></li>
                    </ul>
                </div>
            </div>
        </div>

            <h1>Hello World</h1>
            <g:if test="${nextRoll}">
                <h2>Game id = ${nextRoll.game} </h2>
            </g:if>
            <g:form action="saveRoll"  method="POST">
                <table class="table table-bordered">
                    <tr class="head-row">
                        <td rowspan="2">Players Name</td>
                        <g:each var="i" in="${ (1..10) }">
                             <td colspan="${i == 10 ? 3 : 2}">Frame ${i} </td>
                        </g:each>
                    </tr>
                    <tr class="head-row">
                        <g:each var="i" in="${ (1..21) }">
                                <td>Roll ${i == 21 ? 3 : (i+1)%2 +1}</td>
                        </g:each>
                    </tr>
                    <g:each status="i" in="${renderMap?.users}" var="user">
                        <tr>
                        <td rowspan="2">
                            <span> ${user.name} </span>
                        </td>

                        <g:each status="j" in="${user.frames}" var="frame">
                            <g:if test="${frame.rollOne != 'null'}">
                                <td>
                                    <span> ${frame.rollOne} </span>
                                </td>
                            </g:if>
                            <g:if test="${frame.rollTwo != 'null'}">
                                <td>
                                    <span> ${frame.rollTwo} </span>
                                </td>
                            </g:if>
                            <g:if test="${j == 9 && frame.rollThree != 'null' && frame.rollThree != null}">
                                <td>
                                    <span> ${frame.rollThree} </span>
                                </td>
                            </g:if>
                        </g:each>
                        <g:if test="${nextRoll?.userNumber == i}">
                            <g:hiddenField name="roll.game" value="${nextRoll.game}" />
                            <g:hiddenField name="roll.userNumber" value="${nextRoll.userNumber}" />
                            <g:hiddenField name="roll.frameNumber" value="${nextRoll.frameNumber}" />
                            <g:hiddenField name="roll.rollNumber" value="${nextRoll.rollNumber}" />
                            <td>
                                <g:select name="roll.value" from="${0..nextRoll.maxValue}" />
                            </td>
                        </g:if>
                        </tr>
                        <tr>
                            <g:each status="j" in="${user.frames}" var="frame">
                                <td colspan="${(j == 9)?3:2}">${frame.score} </td>
                            </g:each>
                        </tr>
                    </g:each>
                </table>
                <g:actionSubmit value="Save Roll" class="btn btn-primary" action="saveRoll"/>
            </g:form>
            <g:if test="${flash.error}">
                 <h3>${flash.error}!</h3>
            </g:if>
        </div>
    </body>
</html>