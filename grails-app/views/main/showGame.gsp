<html>
    <head>
        <title>Main Page</title>
    </head>
    <body>
        <h2> New User  ${date} </h2>
        <div>
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
                                    <span> ${game.users?.get(i)?.name} </span>
                                </td>
                            <%} else if(j == 22) {%>
                                <td>
                                    <span> ${game?.users[i]?.frames?.get(9)?.rollThree} </span>
                                </td>
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
                                <td>
                                    <span>${value}</span>
                                </td>
                            <%}
                        }
                        out << "</tr>"
                    }%>
                </table>
                <%if(flash.error != null) {%>
                    <div> <h2 class="error">Error: ${flash.error}</h2></div>
                <%}%>
            </g:form>
        </div>
    </body>
</html>