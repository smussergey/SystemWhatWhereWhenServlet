
<%@include file="WEB-INF/fragment/topPageGeneralElements.jsp" %>

<html>
<head>
    <title>
        <fmt:message key="login.page.title"/>
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
        </ul>
        <ul class="navbar-nav my-2 my-lg-0">

            <%@include file="WEB-INF/fragment/navBarLangChooserPart.jsp" %>

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/registrationPage">
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
        <c:if test="${errorIncorrectCredentials==true}">
            <label class="error"> <fmt:message key="login.error.message.wrong.login.or.password"/></label>
        </c:if>
    </div>

    <div class="panel-body">
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="username">
                    <fmt:message key="login.field.label.login"/>
                </label>
                <input type="text"
                       class="form-control"
                       id="username"
                       value="ad"
                       name="username"
                       value='<fmt:message key="login.field.placeholder.login"/>'>
            </div>

            <div class=" form-group">
                <label for="username">
                    <fmt:message key="login.field.label.password"/>
                </label>
                <input type="password"
                       class="form-control"
                       id="password"
                       value="adpas"
                       name="password"
                       value='<fmt:message key="login.field.placeholder.password"/>'>
            </div>
            <button type="submit" class="btn btn-primary">
                <fmt:message key="login.button.label"/>
            </button>
        </form>
    </div>
</div>

<%@include file="WEB-INF/fragment/footer.jsp" %>

</body>
</html>
