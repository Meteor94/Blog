<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 顶部开始--%>
<div class="row">
    <div class="col-md-4">
        <%-- border:1px solid red; --%>
        <img alt="Java 博客系统" style="height: 60px" src="${pageContext.request.contextPath}/static/images/logo3.jpg">
    </div>
    <div class="col-md-8 pull-right">
        <div>
            <%--<p class="text-right">向右对齐文本</p>--%>
            <p class="text-right">
                <a href="${pageContext.request.contextPath}/admin/main.html" class="btn btn-default" role="button">
                    进入后台
                </a>
                <br>
                <span>
                    博主出生 ${passedDayNumBirth} 天,博主学习 Java ${passedDayNumJava} 天。
                </span>
            </p>
        </div>
    </div>
</div>
