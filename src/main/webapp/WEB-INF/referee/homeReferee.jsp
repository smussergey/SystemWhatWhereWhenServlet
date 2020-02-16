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
                <a class="nav-link" href="${pageContext.request.contextPath}/referee/newGamePrepareReferee">
                    <fmt:message key="navbar.link.games.new"/>
                </a>
            </li>
            <li class="nav-item">
                <%--                <a class="nav-link" th:href="@{/referee/games/statistics}"--%>
                <%--                   th:text="#{navbar.link.games.statistics}"></a>--%>
                <a class="nav-link" href="${pageContext.request.contextPath}/referee/gamesStatisticsReferee">
                    <fmt:message key="navbar.link.games.statistics"/>
                </a>
            </li>
            <li class="nav-item">
                <%--                <a class="nav-link" th:href="@{/referee/history/consideration}"--%>
                <%--                   th:text="#{navbar.link.history.consideration}"></a>--%>
                <a class="nav-link" href="${pageContext.request.contextPath}/referee/historyGamesStatistics">
                    <fmt:message key="navbar.link.history.statistics"/>
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
    <div>
        <%--        <h3 th:text="#{home.text.please.make.your.choice}"></h3>--%>

        <h3>
            <fmt:message key="home.text.please.make.your.choice"/>
        </h3>

    </div>
</div>

<%@include file="../fragment/footer.jsp" %>

</body>

</html>

