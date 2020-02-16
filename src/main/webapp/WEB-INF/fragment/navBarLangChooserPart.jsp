<li class="nav-item">
    <c:if test="${lang != 'ua'}">
        <a class="nav-link" href="?lang=ua">
            <fmt:message key="navbar.link.ua"/>
        </a>
    </c:if>
</li>
<li class="nav-item">
    <c:if test="${lang == 'ua'}">
        <a class="nav-link" href="?lang=en">
            <fmt:message key="navbar.link.en"/>
        </a>
    </c:if>
</li>