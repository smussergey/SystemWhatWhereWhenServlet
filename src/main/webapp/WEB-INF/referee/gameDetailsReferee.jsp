<%@include file="../fragment/topPageGeneralElements.jsp" %>

<html>
<head>
    <title>
        <fmt:message key="game.details.page.title"/>
    </title>

    <%@include file="../fragment/headGeneralElements.jsp" %>

</head>
<body>

<%@include file="../fragment/jumbotron.jsp" %>

<nav id="nav" class="navbar navbar-expand-sm bg-dark navbar-dark sticky-top">
    <button
            class="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#collapsibleNavbar"
    >
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <!-- Links -->

        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item">
                <%--                <a class="nav-link" th:href="@{/referee/games/new}" th:text="#{navbar.link.games.new}"></a>--%>
                <a class="nav-link" href="${pageContext.request.contextPath}/referee/mainReferee">
                    <fmt:message key="navbar.link.home.page"/>
                </a>
            </li>
            <li class="nav-item">
                <%--                <a class="nav-link" th:href="@{/referee/games/statistics}"--%>
                <%--                   th:text="#{navbar.link.games.statistics}"></a>--%>
                <a class="nav-link" href="${pageContext.request.contextPath}/referee/gamesStatisticsReferee">
                    <fmt:message key="navbar.link.games.statistics"/>
                </a>
            </li>
        </ul>
        <ul class="navbar-nav my-2 my-lg-0">

            <%@include file="../fragment/navBarLangChooserPart.jsp" %>

        <%--            <li class="nav-item nav-link"--%>
            <%--                th:text="#{navbar.text.you.logined.as}"></li>--%>
            <%--            <li class="nav-item nav-link"--%>
            <%--                th:if="${lang.equals('en')}"--%>
            <%--                th:text="${userNameEn}">--%>
            <%--            </li>--%>
            <%--            <li class="nav-item nav-link"--%>
            <%--                th:if="${lang.equals('ua')}"--%>
            <%--                th:text="${userNameUa}">--%>
            <%--            </li>--%>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                    <fmt:message key="navbar.text.logout"/>
                </a>
            </li>
            <%--            <li class="nav-item">--%>
            <%--                <a class="nav-link" th:href="@{/logout}" th:text="#{navbar.text.logout}"></a>--%>
            <%--            </li>--%>
        </ul>
    </div>
</nav>

<div class="container" style="margin-top:30px">
    <%--        <div th:if=" ${gameDTOs.empty}">--%>
    <%--        <div>--%>
    <%--            <h1>--%>
    <%--                <fmt:message key="requested.information.is.absent"/>--%>
    <%--            </h1>--%>
    <%--        </div>--%>

    <%--        <div th:if=" ${!gameDTOs.empty}" id="statistics">--%>
    <div id="details">
        <table class="table table-striped table-hover table-bordered table-sm">
            <h1>
                <fmt:message key="game.details.table.caption"/>
            </h1>
            <thead class="thead-light">
            <tr>
                <th>
                    <fmt:message key="game.table.header.date"/>
                </th>
                <th>
                    <fmt:message key="game.table.header.player"/>
                </th>
                <th>
                    <fmt:message key="game.table.header.player"/>
                </th>
                <th>
                    <fmt:message key="game.table.header.scores"/>
                </th>
                <th>
                    <fmt:message key="game.table.header.appeal.stage"/>
                </th>
            </tr>
            </thead>

            <tbody>
            <td>
                ${gameDTO.date}
            </td>
            <%--                    <td><span th:if="${lang.equals('en')}" th:text="${gameDTO.playerNameEn}"></span>--%>
            <%--                        <span th:if="${lang.equals('ua')}" th:text="${gameDTO.playerNameUa}"></span></td>--%>
            <%--                    <td><span th:if="${lang.equals('en')}" th:text="${gameDTO.opponentNameEn}"></span>--%>
            <%--                        <span th:if="${lang.equals('ua')}" th:text="${gameDTO.opponentNameUa}"></span></td>--%>
            <td>
                ${gameDTO.firstPlayerNameEn}
            </td>
            <td>
                ${gameDTO.secondPlayerNameEn}
            </td>
            <td>
                ${gameDTO.scores}
            </td>
            <td>
                ${gameDTO.appealStage}
            </td>
            </tr>
            </tbody>
        </table>
    </div>

    <div id="qustions">
        <table class="table table-striped table-hover table-bordered table-sm">
            <h1>
                <fmt:message key="game.questions.table.caption"/>
            </h1>
            <thead class="thead-light">
            <tr>
                <th>
                    <fmt:message key="game.questions.table.header.question.id"/>
                </th>
                <th>
                    <fmt:message key="game.questions.table.header.player.who.got.point"/>
                </th>
                <th>
                    <fmt:message key="game.questions.table.header.appeal.stage"/>
                </th>
            </tr>
            </thead>

            <tbody>

            <c:forEach items="${gameDTO.questionDTOs}" var="questionDTO">
                <tr>
                    <td>
                        <c:out value="${questionDTO.id}"/>
                    </td>
                        <%--                        <td><span th:if="${lang.equals('en')}"--%>
                        <%--                        th:text="${questionDTO.nameWhoGotPointEn}"></span>--%>
                        <%--                        <span th:if="${lang.equals('ua')}"--%>
                        <%--                              th:text="${questionDTO.nameWhoGotPointEn}"></span></td>--%>
                    <td>
                        <c:out value="${questionDTO.nameWhoGotPointEn}"/>
                    </td>
                    <td>
                        <c:out value="${questionDTO.appealStage}"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>


        <c:if test="${gameDTO.appealStage.equals(appealStageFiled)}">
            <div>
                <form action="${pageContext.request.contextPath}/referee/considerationAppealForm" method="get">
                    <input type="hidden" name="gameid" value="${gameDTO.id}">
                        <%--            TODO improve--%>
                    <button class="button" type="submit">
                        <fmt:message key="game.appeal.button.label.consider.appeal"/>
                    </button>
                </form>
            </div>

        </c:if>


</div>

<%@include file="../fragment/footer.jsp" %>

</body>

</html>

