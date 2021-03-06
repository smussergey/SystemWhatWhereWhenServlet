<%@include file="../fragment/topPageGeneralElements.jsp" %>

<html>
<head>
    <title>
        <fmt:message key="game.consideration.appeal.page.title"/>
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
                <a class="nav-link" href="${pageContext.request.contextPath}/referee/mainPlayer">
                    <fmt:message key="navbar.link.home.page"/>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/referee/gamesStatisticsReferee">
                    <fmt:message key="navbar.link.games.statistics"/>
                </a>
            </li>
            <%--            <li class="nav-item">--%>
            <%--                <c:if test="${gameDTO.appealPossible}">--%>
            <%--                    <a class="nav-link"--%>
            <%--                    <a class="nav-link" href="${pageContext.request.contextPath}/player/fileAppealPlayer/${gameDTO.id}">--%>
            <%--                        <fmt:message key="navbar.link.games.appeal"/>--%>
            <%--                    </a>--%>
            <%--                </c:if>--%>
            <%--            </li>--%>


            <%--            <li class="nav-item">--%>
            <%--                &lt;%&ndash;                <a class="nav-link" th:href="@{/referee/history/consideration}"&ndash;%&gt;--%>
            <%--                &lt;%&ndash;                   th:text="#{navbar.link.history.consideration}"></a>&ndash;%&gt;--%>
            <%--                <a class="nav-link" href="${pageContext.request.contextPath}/referee/historyConsiderationReferee">--%>
            <%--                    <fmt:message key="navbar.link.history.consideration"/>--%>
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

    <div id="appeal" class="panel-body">
        <h1>
            <fmt:message key="game.appeal.form.header.questions.for.appeal"/>
        </h1>
        <form action="${pageContext.request.contextPath}/referee/considerAppealAgainstAnsweredQuestions" method="get">
            <div class="form-group">
                <c:forEach items="${gameDTO.questionDTOs}" var="questionDTO">
                    <span>
                      <c:if test="${questionDTO.getAppealStage().equals(appealStageFiled)}">
                        <input type="checkbox" name="ids"
                               value="${questionDTO.id}"/> ${questionDTO.id}
                      </c:if>
                    </span>
                </c:forEach>
            </div>
            <button type="submit" class="btn btn-primary">
                <fmt:message key="game.button.label.approve.appeal"/>
            </button>
        </form>
    </div>

</div>

<%@include file="../fragment/footer.jsp" %>

</body>

</html>

