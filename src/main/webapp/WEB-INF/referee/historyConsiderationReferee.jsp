<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="messages"/>


<html>
<head>
    <title>
        <fmt:message key="game.history.page.title"/>
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
            <li class="nav-item">
                <%--                <a class="nav-link" th:href="@{/referee/history/consideration}"--%>
                <%--                   th:text="#{navbar.link.history.consideration}"></a>--%>
                <a class="nav-link" href="${pageContext.request.contextPath}/referee/historyGamesStatistics">
                    <fmt:message key="navbar.link.history.statistics"/>
                </a>
            </li>
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
    <div>
        <table class="table table-striped table-hover table-bordered table-sm">
            <h1>
                <fmt:message key="game.history.table.caption.move.to.history"/>
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
                    <fmt:message key="game.history.table.header.move"/>
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
                        <form action="${pageContext.request.contextPath}/referee/moveToHistory" method="post">
                            <input type="hidden" name="gameid" value="${gameDTO.id}">
                            <button class="button" type="submit">
                                <fmt:message key="game.history.button.label.move"/>
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

<footer>
    <h5>
        <fmt:message key="text.footer"/>
    </h5>
</footer>

</body>

</html>

