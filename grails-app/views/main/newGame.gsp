<!DOCTYPE html>
<html>
    <head>
        <title>Main Page</title>
        <asset:stylesheet src="myStyles/createGameStyle.css"/>
    </head>
    <body>
        <h2> New Game ${game.date} </h2>
        <div class="main middle">
            <g:form action="newUser"  method="POST">
                <g:hiddenField name="date" value="${game.date}" />
                <table class="table table-bordered">
                    <tr class="head-row">
                        <td rowspan="2">Players Name</td>
                        <%for(i in 1..10) {
                            if(i == 10){%>
                                <td colspan="3">Frame ${i} </td>
                            <%} else {%>
                                <td colspan="2">Frame ${i} </td>
                           <%}
                        }%>
                    </tr>
                    <tr class="head-row">

                        <%for(i in 1..21) {
                            if(i == 21){%>
                        <td>Roll 3</td>
                        <%} else {%>
                        <td>Roll ${(i+1)%2 +1}</td>
                        <%}
                        }%>
                    </tr>
                    <%for(int i = 0; i < game?.users.size()?: 0; i++){
                        out << "<tr>"
                        for(int j in 1..22){
                            if(j == 1){%>
                                <td>
                                    <g:textField class = "name-text-field" name="users[${i}].name" value="${game.users?.get(i)?.name}" />
                                </td>
                            <%} else if(j == 22) {%>
                                <td><g:textField name="users[${i}].frames[9].rollThree"
                                                 value="${game?.users[i]?.frames?.get(9)?.rollThree}" /></td>
                            <%} else {
                                def value
                                int frameNumber = (j/2 - j%2/2) - 1
                                def neededFrame = game?.users?.get(i)?.frames?.get(frameNumber)
                                if(j%2==0){
                                    value = neededFrame?.rollOne
                                } else {
                                    value = neededFrame?.rollTwo
                                }
                                %>
                                <td><g:textField name="users[${i}].frames[${frameNumber}].${j%2==0?"rollOne":"rollTwo"}"
                                                 value="${value}" /></td>
                            <%}
                        }
                        out << "</tr>"
                    }%>
                </table>
                <%if(flash.error != null) {%>
                    <div> <h2 class="error">Error: ${flash.error}</h2></div>
                <%}%>
                <g:submitButton name="create" class="btn btn-primary" value="New User" />
            </g:form>
        </div>
    </body>
</html>