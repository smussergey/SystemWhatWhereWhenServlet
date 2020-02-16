<nav aria-label="Navigation for histories">
    <ul class="pagination">
        <c:if test="${gameDTOPage.currentPage != 1}">
            <li class="page-item">
                <a class="page-link" href="?currentPage=${gameDTOPage.currentPage-1}">
                    <fmt:message key="pagination.link.previous"/>
                </a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${gameDTOPage.numberOfPages}" var="i">
            <c:choose>
                <c:when test="${gameDTOPage.currentPage == i}">
                    <li class="page-item active">
                        <a class="page-link">
                                <%--                                    ${i} <span class="sr-only">(current)</span></a>--%>
                                ${i}
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="?currentPage=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${gameDTOPage.currentPage < gameDTOPage.numberOfPages}">
            <li class="page-item">
                <a class="page-link" href="?currentPage=${gameDTOPage.currentPage+1}">
                    <fmt:message key="pagination.link.next"/>
                </a>
            </li>
        </c:if>
    </ul>
</nav>