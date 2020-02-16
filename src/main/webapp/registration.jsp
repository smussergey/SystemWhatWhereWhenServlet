<%@include file="WEB-INF/fragment/topPageGeneralElements.jsp" %>

<html>
<head>
    <title>
        <fmt:message key="registration.page.title"/>
    </title>

    <%@include file="WEB-INF/fragment/headGeneralElements.jsp" %>

</head>
<body>

<%@include file="WEB-INF/fragment/jumbotron.jsp" %>

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
            <%--            <li class="nav-item">--%>
            <%--            </li>--%>
        </ul>
        <ul class="navbar-nav my-2 my-lg-0">

            <%@include file="WEB-INF/fragment/navBarLangChooserPart.jsp" %>

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/loginPage">
                    <fmt:message key="navbar.link.login"/>
                </a>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <h2 class="panel-title">
        <fmt:message key="registration.form.header"/>
    </h2>
    <div class="form-input-error">
        <%--        <span th:if="${error}" th:text="#{login.message.wrong.login.or.password}"></span>--%>
    </div>

    <div class="panel-body">
        <form action="${pageContext.request.contextPath}/registration" method="post">
            <div class="form-group">
                <label for="nameua">
                    <fmt:message key="registration.field.label.nameUa"/>
                </label>
                <input type="text"
                       class="form-control"
                       id="nameua"
                       name="nameua"
                       placeholder='<fmt:message key="registration.field.placeholder.nameUa"/>'>
            </div>

            <div class=" form-group">
                <label for="nameen">
                    <fmt:message key="registration.field.label.nameEn"/>
                </label>
                <input type="text"
                       class="form-control"
                       id="nameen"
                       name="nameen"
                       placeholder='<fmt:message key="registration.field.placeholder.nameEn"/>'>
            </div>

            <div class="form-group">
                <label for="username">
                    <fmt:message key="registration.field.label.login"/>
                </label>
                <input type="text"
                       class="form-control"
                       id="username"
                       name="username"
                       placeholder='<fmt:message key="registration.field.placeholder.email"/>'>
            </div>

            <div class=" form-group">
                <label for="username">
                    <fmt:message key="registration.field.label.password"/>
                </label>
                <input type="password"
                       class="form-control"
                       id="password"
                       name="password"
                       placeholder='<fmt:message key="registration.field.placeholder.password"/>'>
            </div>
            <button type="submit" class="btn btn-primary">
                <fmt:message key="registration.button.label."/>
            </button>
        </form>
    </div>
</div>

<%@include file="WEB-INF/fragment/footer.jsp" %>

</body>
</html>
