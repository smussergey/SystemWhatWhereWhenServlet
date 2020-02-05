<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="messages"/>


<html>
<head>
    <title>
        <fmt:message key="registration.page.title"/>
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

<nav id="nav" class="navbar navbar-expand-sm bg-dark navbar-dark sticky-top ">
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
                <%--                <a th:text="#{navbar.link.home.page}" class="nav-link" th:href="@{/home}"></a>--%>
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
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/loginPage">
                    <fmt:message key="navbar.link.register"/>
                </a>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <h2 class="panel-title">
        <fmt:message key="login.form.header"/>
    </h2>
    <div class="form-input-error">
        <%--        <span th:if="${error}" th:text="#{login.message.wrong.login.or.password}"></span>--%>
    </div>

    <div class="panel-body">
        <form action="${pageContext.request.contextPath}/registration" method="post">
            <div class="form-group">
                <label for="nameua">
                    <fmt:message key="registartion.label.nameUa"/>
                </label>
                <input type="text"
                       class="form-control"
                       id="nameua"
                       name="nameua"
                       placeholder='<fmt:message key="registartion.label.placeholder.nameUa"/>'>
            </div>

            <div class=" form-group">
                <label for="nameen">
                    <fmt:message key="registartion.label.nameEn"/>
                </label>
                <input type="text"
                       class="form-control"
                       id="nameen"
                       name="nameen"
                       placeholder='<fmt:message key="registartion.label.placeholder.nameEn"/>'>
            </div>

            <div class="form-group">
                <label for="username">
                    <fmt:message key="registartion.label.login"/>
                </label>
                <input type="text"
                       class="form-control"
                       id="username"
                       name="username"
                       placeholder='<fmt:message key="registartion.label.placeholder.email"/>'>
            </div>

            <div class=" form-group">
                <label for="username">
                    <fmt:message key="login.label.password"/>
                </label>
                <input type="password"
                       class="form-control"
                       id="password"
                       name="password"
                       placeholder='<fmt:message key="registartion.label.placeholder.password"/>'>
            </div>
            <button type="submit" class="btn btn-primary">
                <fmt:message key="registration.label.button"/>
            </button>
        </form>
    </div>
</div>

<footer>
    <h5>
        <fmt:message key="text.footer"/>
    </h5>
</footer>

</body>
</html>
