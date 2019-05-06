<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside id="sidebarA">
    <nav>
        <ul>
            <li><a class="current" href="<c:url value='/' />">
                    Home</a></li>
            <li><a href="<c:url value='/catalog' />">
                    Browse Catalog</a></li>
            <li><a href="<c:url value='/cart' />">
                    Cart</a></li>
        </ul>
    </nav>
</aside>