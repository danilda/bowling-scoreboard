<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>
<body>

    %{--<div class="navbar navbar-default navbar-static-top" role="navigation">--}%
        %{--<div class="container">--}%
            %{--<div class="navbar-header">--}%
                %{--<span>--}%
                    %{--<g:link controller="main" action="index">Main menu</g:link>--}%
                %{--</span>--}%
                %{--<span>--}%
                    %{--<g:link controller="main" action="addUser">Create new game</g:link>--}%
                %{--</span>--}%
                %{--<span>--}%
                    %{--<g:link controller="main" action="showGameList">Show all games</g:link>--}%
                %{--</span>--}%
            %{--</div>--}%
        %{--</div>--}%
    %{--</div>--}%

    <g:layoutBody/>

    <div class="footer" role="contentinfo"></div>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>

    <asset:javascript src="application.js"/>

</body>
</html>
