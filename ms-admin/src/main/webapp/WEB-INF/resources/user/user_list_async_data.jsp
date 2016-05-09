<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center">
            <label><input type="checkbox"/><span class="lbl"></span></label>
        </th>
        <th>登录名</th>
        <th>Email</th>
        <th class="hidden-480">手机号</th>
        <th class="hidden-phone"><i class="icon-time hidden-phone"></i> 创建时间</th>
        <th class="hidden-480">状态</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${page != null && page.content != null}">
        <c:forEach var="user" items="${page.content}">
            <tr>
                <td class='center'>
                    <label><input type='checkbox'/><span class="lbl"></span></label>
                </td>
                <td><a href='#'>${user.loginName}</a></td>
                <td>${user.email}</td>
                <td class='hidden-480'>${user.mobile}</td>
                <td class='hidden-phone'><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd"/></td>
                <td class='hidden-480'>
                    <c:choose>
                        <c:when test="${user.status==1}">
                            <span class='label label-warning'>未初始化</span>
                        </c:when>
                        <c:when test="${user.status==2}">
                            <span class='label label-success'>正常</span>
                        </c:when>
                        <c:when test="${user.status==20}">
                            <span class='label label-inverse arrowed-in'>已锁定</span>
                        </c:when>
                        <c:otherwise>
                            <span class='label label-info arrowed arrowed-right'>其他</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <div class='hidden-phone visible-desktop btn-group'>
                        <button class='btn btn-mini btn-success' onclick="doUnLock(${user.id})"><i class='icon-ok'></i></button>
                        <button class='btn btn-mini btn-info' data-toggle="modal" data-target="#update-user-modal-box" href="${request.contextPath}/user/update.action?id=${user.id}"><i class='icon-edit'></i></button>
                        <button class='btn btn-mini btn-warning' onclick="doLock(${user.id})"><i class='icon-lock'></i></button>
                        <button class='btn btn-mini btn-danger' id="bootbox-confirm" onclick="doDelete(this,${user.id})"><i class='icon-trash'></i></button>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>

<%@include file="../pager.jsp"%>
