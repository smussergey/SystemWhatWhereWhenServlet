<%@include file="WEB-INF/fragment/topPageGeneralElements.jsp" %>

<html>
<head>
    <title>
        <fmt:message key="index.page.title"/>
    </title>

    <%@include file="WEB-INF/fragment/headGeneralElements.jsp" %>

</head>
<body>

<%@include file="WEB-INF/fragment/jumbotron.jsp" %>

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
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
        </ul>
        <ul class="navbar-nav my-2 my-lg-0">

            <%@include file="WEB-INF/fragment/navBarLangChooserPart.jsp" %>

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/registrationPage">
                    <fmt:message key="navbar.link.register"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/loginPage">
                    <fmt:message key="navbar.link.login"/>
                </a>
            </li>
        </ul>
    </div>
</nav>

<div class="container" style="margin-top:30px">
    <h3>
        <fmt:message key="index.text.please.log.in.to.continue"/>
    </h3>

</div>

<%@include file="WEB-INF/fragment/footer.jsp" %>

</body>

</html>

