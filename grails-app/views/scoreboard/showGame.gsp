<html>
    <head>
        <title>Main Page</title>

        <asset:stylesheet src="bootstrap.css"/>
        <asset:stylesheet src="customStyles/navBar.css"/>
        <asset:stylesheet src="customStyles/showGame.css"/>
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
            <h2 class="game">Game id = ${renderMap.game} nextRoll.maxValue = ${nextRoll.maxValue}</h2>
            <g:form action="saveRoll" method="POST">
                <table class="table table-bordered middle">
                    <tr class="head-row info">
                        <td rowspan="2">Players Name</td>
                        <g:each var="i" in="${ (1..10) }">
                             <td colspan="${i == 10 ? 3 : 2}">Frame ${i} </td>
                        </g:each>
                    </tr>
                    <tr class="head-row info">
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
                        <g:if test="${nextRoll?.user?.number == i}">
                            <g:hiddenField name="roll.user.id" value="${nextRoll.user.id}" />
                            <g:if test="${nextRoll.frame.id}">
                                <g:hiddenField name="roll.frame.id" value="${nextRoll.frame.id}" />
                            </g:if>
                            <g:hiddenField name="roll.frame.number" value="${nextRoll.frame.number}" />
                            <g:hiddenField name="roll.rollNumber" value="${nextRoll.rollNumber}" />
                            <g:hiddenField name="roll.maxValue" value="${nextRoll.maxValue}" />
                            <td>
                                <g:select name="roll.value" from="${0..nextRoll.maxValue+1}" />
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
                <g:if test="${nextRoll != null}">
                    <g:actionSubmit value="Save Roll" class="btn btn-primary save-roll" action="saveRoll"/>
                </g:if>

                <g:if test="${error}">
                    <g:hasErrors bean="${roll}">
                        <div class="alert alert-warning alert-dismissable">
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                            <strong>Warning!</strong> <g:message error="${roll?.errors?.getFieldErrors()[0]}" />
                        </div>
                    </g:hasErrors>
                </g:if>

            </g:form>
        </div>
    </body>
</html>