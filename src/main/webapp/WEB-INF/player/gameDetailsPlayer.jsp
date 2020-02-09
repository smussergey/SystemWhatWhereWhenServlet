<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="messages"/>


<html>
<head>
    <title>
        <fmt:message key="games.game.details.page.title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <style>
        <%@ include file="/main.css" %>
    </style>
</head>
<body>
<div class="jumbotron text-center" style="margin-bottom:0">
    <h1>
        <fmt:message key="text.header"/>
    </h1>
</div>

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

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/player/gamesStatisticsPlayer">
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


<%--            <li class="nav-item">&ndash;%&gt;--%>
<%--            <c:url value="UDS" var="url">--%>
<%--                <c:param name="StudentID" value="${students.studentID}" />--%>
<%--            </c:url> <a href="${url}">Edit</a></td>--%>
<%--            </li>&ndash;%&gt;--%>

        </ul>
        <ul class="navbar-nav my-2 my-lg-0">
            <li class="nav-item">
                <%--                            th:if="${lang.equals('en')}">--%>
                <a class="nav-link" href="?lang=ua">
                    <fmt:message key="navbar.link.ua"/>
                </a>
            </li>
            <li class="nav-item">
                <%--                            th:if="${lang.equals('ua')}">--%>
                <a class="nav-link" href="?lang=en">
                    <fmt:message key="navbar.link.en"/>
                </a>
            </li>
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
    <div id="statistics">
        <table class="table table-striped table-hover table-bordered table-sm">
            <h1>
                <fmt:message key="games.game.details.table.caption"/>
            </h1>
            <thead class="thead-light">
            <tr>
                <th>
                    <fmt:message key="games.table.header.date"/>
                </th>
                <th>
                    <fmt:message key="games.table.header.team"/>
                </th>
                <th>
                    <fmt:message key="games.table.header.team"/>
                </th>
                <th>
                    <fmt:message key="games.table.header.scores"/>
                </th>
                <th>
                    <fmt:message key="games.table.header.appeal.stage"/>
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
                ${gameDTO.playerNameEn}
            </td>
            <td>
                ${gameDTO.opponentNameEn}
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
            <fmt:message key="games.game.questions.details.table.caption"/>
            </h1>
            <thead class="thead-light">
            <tr>
                <th>
                    <fmt:message key="games.game.table.header.question.id"/>
                </th>
                <th>
                    <fmt:message key="games.game.table.header.name.who.got.point"/>
                </th>
                <th>
                    <fmt:message key="games.table.header.appeal.stage"/>
                </th>
            </tr>
            </thead>

            <tbody>

            <c:forEach items="${gameDTO.questionDTOS}" var="questionDTO">
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

    <c:if test="${gameDTO.appealPossible}">
        <div>
            <form action="${pageContext.request.contextPath}/player/fileAppealFormPlayer" method="post">
                <input type="hidden" name="gameid" value="${gameDTO.id}">
                    <%--            TODO improve--%>
                <button class="button" type="submit">
                    <fmt:message key="navbar.link.games.appeal"/>
                </button>
            </form>
        </div>

    </c:if>

</div>

<footer>
    <h5>
        <fmt:message key="text.footer"/>
    </h5>
</footer>

</body>

</html>

