<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>用户列表</title>
    <meta name="description" content="用户列表" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- basic styles -->
    <link rel="stylesheet" href="${request.contextPath}/static/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${request.contextPath}/static/css/bootstrap-responsive.min.css"/>
    <link rel="stylesheet" href="${request.contextPath}/static/css/font-awesome.min.css"/>
    <!--[if IE 7]>
    <link rel="stylesheet" href="${request.contextPath}/static/css/font-awesome-ie7.min.css"/>
    <![endif]-->
    <!-- page specific plugin styles -->

    <!-- ace styles -->
    <link rel="stylesheet" href="${request.contextPath}/static/css/ace.min.css"/>
    <link rel="stylesheet" href="${request.contextPath}/static/css/ace-responsive.min.css"/>
    <link rel="stylesheet" href="${request.contextPath}/static/css/ace-skins.min.css" />
    <!--[if lt IE 9]>
    <link rel="stylesheet" href="${request.contextPath}/static/css/ace-ie.min.css"/>
    <![endif]-->
</head>
<body>
<div class="navbar navbar-inverse">
    <c:catch var="exception">
        <c:import url="${request.contextPath}/top_nav.action" />
    </c:catch>
    <c:if test="${not empty exception}">
        Sorry, the remote content is not currently available.
    </c:if>
</div>
<div class="container-fluid" id="main-container">
    <a href="#" id="menu-toggler"><span></span></a><!-- menu toggler -->
    <div id="sidebar">
        <c:catch var="exception">
            <c:import url="${request.contextPath}/left_menu.action" />
        </c:catch>
        <c:if test="${not empty exception}">
            Sorry, the remote content is not currently available.
        </c:if>
    </div>

    <!--/#sidebar-->

    <div id="main-content" class="clearfix">

        <div id="breadcrumbs">
            <ul class="breadcrumb">
                <li><i class="icon-home"></i> <a href="${request.contextPath}/index.action">首页</a><span class="divider"><i class="icon-angle-right"></i></span></li>
                <li class="active">用户管理</li>
            </ul>
            <div id="nav-search">
                <%@include file="../search_bar.jsp"%>
            </div>
        </div>

        <div id="page-content" class="clearfix">
            <div class="row-fluid">
                <c:if test="${users != null}">
                    <c:forEach var="user" items="${users}">
                        ${user.id} -  ${user.loginName} - ${user.password} - ${user.salt}<br/>
                    </c:forEach>
                </c:if>

                <c:if test="${page != null}">
                    <c:if test="${page.content != null}">
                        <c:forEach var="user" items="${page.content}">
                            ${user.id} -  ${user.loginName} - ${user.password} - ${user.salt}<br/>
                        </c:forEach>
                    </c:if>
                    TotalPages: ${page.totalPages}
                    TotalElements:${page.totalElements}
                    Number:${page.number}
                    Size:${page.size}
                    NumberOfElements:${page.numberOfElements}

                    isFirst:${page.isFirst}
                    isLast:${page.isLast}
                    hasNext:${page.hasNext}
                    hasPrevious:${page.hasPrevious}
                </c:if>

            </div>
        </div>

        <div id="ace-settings-container">
            <%@include file="../page_setting_box.jsp"%>
        </div>
    </div>
</div>

<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
    <i class="icon-double-angle-up icon-only"></i>
</a>
<!-- basic scripts -->
<script src="${request.contextPath}/static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    window.jQuery || document.write("<script src='${request.contextPath}/static/js/jquery-1.9.1.min.js'>\x3C/script>");
</script>
<script src="${request.contextPath}/static/js/bootstrap.min.js"></script>
<!-- page specific plugin scripts -->

<!-- ace scripts -->
<script src="${request.contextPath}/static/js/ace-elements.min.js"></script>
<script src="${request.contextPath}/static/js/ace.min.js"></script>
<!-- inline scripts related to this page -->

<script type="text/javascript">

</script>
</body>
</html>
