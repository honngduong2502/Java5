<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="col-md-9">
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <c:forEach items="${products}" var="product">
            <div class="col">
                <div class="card h-100">
                    <img src="/productImages/${product.imageName}" class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">${product.name}</h5>
                        <h6 class="card-title">${product.price}</h6>
                        <p class="card-text">${product.description}}</p>
                    </div>
                    <div class = "d-flex justify-content-center">
                        <a class="btn btn-primary mb-3" style="width: 80%" href="/shop/viewproduct/${product.id}" role="button">View Product</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

</div>