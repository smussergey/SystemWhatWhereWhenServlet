<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="messages"/>


<html>
<head>
    <title>
        <fmt:message key="game.new.page.title"/>
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
                <a class="nav-link" href="${pageContext.request.contextPath}/mainReferee">
                    <fmt:message key="navbar.link.home.page"/>
                </a>
            </li>
            <li class="nav-item">
                <%--                <a class="nav-link" th:href="@{/referee/games/new}" th:text="#{navbar.link.games.new}"></a>--%>
                <a class="nav-link" href="${pageContext.request.contextPath}/referee/newGameReferee">
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
    <h2 class="panel-title">
        <fmt:message key="game.new.form.header"/>
    </h2>
    <form action="${pageContext.request.contextPath}/referee/generateNewGameReferee" method="post">
        <div class="custom-control mb-3">
            <label for="player">
                <fmt:message key="game.new.field.label.first.player"/>
            </label>
            <select class="custom-select mb-3" id="player" name="firstplayerid">
                <%--                <option th:if="${lang.equals('en')}"--%>
                <%--                        th:each="player : ${players}"--%>
                <%--                        th:value="${player.id}" th:text="${player.nameEn}">--%>
                <%--                </option>--%>
                <%--                <option th:if="${lang.equals('ua')}"--%>
                <%--                        th:each="player : ${players}"--%>
                <%--                        th:value="${player.id}" th:text="${player.nameUa}">--%>
                <%--                </option>--%>
                <c:forEach items="${players}" var="player">
                    <option value="${player.id}">
                            ${player.nameEn}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="custom-control mb-3">
            <label for="opponent">
                <fmt:message key="game.new.field.label.second.player"/>
            </label>
            <select class="custom-select mb-3" id="opponent" name="secondplayerid">
                <%--                <option th:if="${lang.equals('en')}"--%>
                <%--                        th:each="player : ${players}"--%>
                <%--                        th:value="${player.id}" th:text="${player.nameEn}">--%>
                <%--                </option>--%>
                <%--                <option th:if="${lang.equals('ua')}"--%>
                <%--                        th:each="player : ${players}"--%>
                <%--                        th:value="${player.id}" th:text="${player.nameUa}">--%>
                <%--                </option>--%>
                <c:forEach items="${players}" var="player">
                    <option value="${player.id}">
                            ${player.nameEn}
                    </option>
                </c:forEach>

            </select>
        </div>

        <div class="custom-control mb-3">
            <label for="quantity">
                <fmt:message key="game.new.field.label.max.scores"/>
            </label>
            <br>
            <input id="quantity" name="maxscores" required/>
        </div>
        <button type="submit" class="btn btn-primary">
            <fmt:message key="game.new.button.label"/>
        </button>
    </form>
</div>

<footer>
    <h5>
        <fmt:message key="text.footer"/>
    </h5>
</footer>

</body>

</html>

