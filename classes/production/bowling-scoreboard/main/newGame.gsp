<!DOCTYPE html>
<html>
    <head>
        <title>Main Page</title>
        <asset:stylesheet src="myStyles/createGameStyle.css"/>
    </head>
    <body>
        <h2> New Game ${game.date} </h2>
        <div>
            <g:form action="newUser"  method="POST">
                <g:hiddenField name="usersCount" value="${game?.usersCount}" />
                <g:hiddenField name="date" value="${game.date}" />
                %{--<g:hiddenField name="users[0].frames[0].rollOne" value="1" />--}%
                %{--<g:hiddenField name="users[0].frames[0].rollTwo" value="2" />--}%
                %{--<g:hiddenField name="users[0].frames[0].rollThree" value="3" />--}%
                <table>
                    <tr>
                        <td>Players Name</td>
                        <%for(i in 1..10) {
                            if(i == 10){%>
                                <td colspan="3">Frame ${i} </td>
                            <%} else {%>
                                <td colspan="2">Frame ${i} </td>
                           <%}
                        }%>
                    </tr>
                    <%for(int i = 0; i < game?.usersCount?: 0; i++){
                        out << "<tr>"
                        for(j in 1..22){
                            if(j == 1){%>
                                <td>
                                    <g:textField name="users[${i}].frames[10].rollThree" value="" />
                                </td>
                            <%} else if(j == 22) {%>
                                <td><g:textField name="users[${i}].frames[10].rollThree" value="" /></td>
                            <%} else {%>
                                <td><g:textField name="users[${i}].frames[${i/2}].${i%2==0?"rollOne":"rollTwo"}" value="" /></td>
                            <%}
                        }
                        out << "</tr>"
                    }%>
                </table>
                <%for(i in 1..10) {%>

                <%}%>
                <g:submitButton name="create" class="save" value="New User" />
            </g:form>
        </div>
    </body>
</html>