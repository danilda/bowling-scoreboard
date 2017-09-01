<html>
    <head>
        <title>Main Page</title>
    </head>
    <body>
        <div>
            <h1>Hello World</h1>
            <h2>gameNumber = ${nextRoll.gameId} ,  userNumber = ${nextRoll.userNumber}, frameNumber = ${nextRoll.frameNumber}, rollNumber = ${nextRoll.rollNumber}</h2>
            <g:form action="addUser"  method="POST">
                <g:hiddenField name="roll.gameId" value="${nextRoll.gameId}" />
                <g:hiddenField name="roll.userNumber" value="${nextRoll.userNumber}" />
                <g:hiddenField name="roll.frameNumber" value="${nextRoll.frameNumber}" />
                <g:hiddenField name="roll.rollNumber" value="${nextRoll.rollNumber}" />
                <table class="table table-bordered">
                    <tr class="head-row">
                        <td rowspan="2">Players Name</td>
                        <g:each var="i" in="${ (1..10) }">
                            <g:if test="${i == 10}">
                                 <td colspan="3">Frame ${i} </td>
                            </g:if>
                            <g:else>
                                 <td colspan="2">Frame ${i} </td>
                            </g:else>
                        </g:each>
                    </tr>
                    <tr class="head-row">
                        <g:each var="i" in="${ (1..21) }">
                            <g:if test="${i == 21}">
                                <td>Roll 3</td>
                            </g:if>
                            <g:else>
                                 <td>Roll ${(i+1)%2 +1}</td>
                            </g:else>
                        </g:each>
                    </tr>
                    <g:each status="i" in="${renderMap?.users}" var="user">
                        <tr>
                            <td>
                                <span> ${user.name} </span>
                            </td>

                        <g:each status="j" in="${user.frames}" var="frame">
                            <g:if test="${frame.rollOne != null}">
                                <td>
                                    <span> ${frame.rollOne} </span>
                                </td>
                            </g:if>
                            <g:if test="${frame.rollTwo != 'null'}">
                                <td>
                                    <span> ${frame.rollTwo} </span>
                                </td>
                            </g:if>
                            <g:if test="${j == 9 && frame.rollThree != null}">
                                <td>
                                    <span> ${frame.rollThree} </span>
                                </td>
                            </g:if>
                        </g:each>

                        <g:if test="${nextRoll.userNumber == i}">
                            <td>
                                <g:select name="roll.value" from="${0..10}" />
                            </td>
                        </g:if>
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