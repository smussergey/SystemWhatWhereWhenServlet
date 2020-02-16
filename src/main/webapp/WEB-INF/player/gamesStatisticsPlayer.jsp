<%@include file="../fragment/topPageGeneralElements.jsp" %>

<html>
<head>
    <title>
        <fmt:message key="games.statistics.page.title"/>
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
                <a class="nav-link" href="${pageContext.request.contextPath}/player/mainPlayer">
                    <fmt:message key="navbar.link.home.page"/>
                </a>
            </li>

<%--            <li class="nav-item">--%>
<%--                <a class="nav-link" href="${pageContext.request.contextPath}/player/fileAppealPlayer">--%>
<%--                    <fmt:message key="navbar.link.games.appeal"/>--%>
<%--                </a>--%>
<%--            </li>--%>
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
    <div  id="statistics">
        <table class="table table-striped table-hover table-bordered table-sm">
            <h1>
                <fmt:message key="games.statistics.table.caption"/>
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
                <th>
                    <fmt:message key="games.table.header.get.game.details"/>
                </th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${gameDTOs}" var="gameDTO">
                <tr>
                    <td>
                        <c:out value="${gameDTO.date}"/>
                    </td>
                        <%--                    <td><span th:if="${lang.equals('en')}" th:text="${gameDTO.playerNameEn}"></span>--%>
                        <%--                        <span th:if="${lang.equals('ua')}" th:text="${gameDTO.playerNameUa}"></span></td>--%>
                        <%--                    <td><span th:if="${lang.equals('en')}" th:text="${gameDTO.opponentNameEn}"></span>--%>
                        <%--                        <span th:if="${lang.equals('ua')}" th:text="${gameDTO.opponentNameUa}"></span></td>--%>
                    <td>
                        <c:out value="${gameDTO.firstPlayerNameEn}"/>
                    </td>
                    <td>
                        <c:out value="${gameDTO.secondPlayerNameEn}"/>
                    </td>
                    <td>
                        <c:out value="${gameDTO.scores}"/>
                    </td>
                    <td>
                        <c:out value="${gameDTO.appealStage}"/>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/player/gameDetailsPlayer" method="get">
                            <input type="hidden" name="gameid" value="${gameDTO.id}">
                            <button class="button" type="submit">
                                <fmt:message key="games.statistics.button.label.get"/>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>

            <%--                <tr th:each="gameDTO: ${gameDTOs}">--%>
            <%--                    <td th:text="${gameDTO.date}"></td>--%>
            <%--                    <td><span th:if="${lang.equals('en')}" th:text="${gameDTO.playerNameEn}"></span>--%>
            <%--                        <span th:if="${lang.equals('ua')}" th:text="${gameDTO.playerNameUa}"></span></td>--%>
            <%--                    <td><span th:if="${lang.equals('en')}" th:text="${gameDTO.opponentNameEn}"></span>--%>
            <%--                        <span th:if="${lang.equals('ua')}" th:text="${gameDTO.opponentNameUa}"></span></td>--%>
            <%--                    <td th:text="${gameDTO.scores}"></td>--%>
            <%--                    <td th:text="${gameDTO.appealStage}"></td>--%>
            <%--                    <td>--%>
            <%--                        <a th:href="@{|/referee/games/${gameDTO.id}|}"--%>
            <%--                           th:text="#{games.statistics.link.label.get}"></a>--%>
            <%--                    </td>--%>
            <%--                </tr>--%>
            </tbody>
        </table>

        <!--pagination-->

    </div>
</div>

<%@include file="../fragment/footer.jsp" %>

</body>

</html>

