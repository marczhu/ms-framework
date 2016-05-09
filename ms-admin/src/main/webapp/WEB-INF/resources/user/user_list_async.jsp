<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>用户列表</title>
    <meta name="description" content="用户列表"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
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
    <link rel="stylesheet" href="${request.contextPath}/static/css/ace-skins.min.css"/>
    <!--[if lt IE 9]>
    <link rel="stylesheet" href="${request.contextPath}/static/css/ace-ie.min.css"/>
    <![endif]-->
    <style type="text/css">
        .search-table tr td{position:relative;line-height:5;padding:10px 0.4cm 0px 0px;}
        .error-msg{color:red;}
    </style>
</head>
<body>
<div class="navbar navbar-inverse">
    <c:catch var="exception">
        <c:import url="${request.contextPath}/top_nav.action"/>
    </c:catch>
    <c:if test="${not empty exception}">
        Sorry, the remote content is not currently available.
    </c:if>
</div>
<div class="container-fluid" id="main-container">
    <a href="#" id="menu-toggler"><span></span></a><!-- menu toggler -->
    <div id="sidebar">
        <c:catch var="exception">
            <c:import url="${request.contextPath}/left_menu.action"/>
        </c:catch>
        <c:if test="${not empty exception}">
            Sorry, the remote content is not currently available.
        </c:if>
    </div>

    <!--/#sidebar-->

    <div id="main-content" class="clearfix">

        <div id="breadcrumbs">
            <ul class="breadcrumb">
                <li><i class="icon-home"></i> <a href="${request.contextPath}/index.action">首页</a><span class="divider"><i
                        class="icon-angle-right"></i></span></li>
                <li class="active">用户管理</li>
            </ul>
            <div id="nav-search">
                <%@include file="../search_bar.jsp" %>
            </div>
        </div>

        <div id="page-content" class="clearfix">
            <div class="page-header position-relative">
                <h1>用户管理 <small><i class="icon-double-angle-right"></i> 用户列表</small></h1>
            </div>

            <div class="row-fluid">
                <div class="row-fluid">
                    <form id="search-form" class="form-search" method="post">
                        <input id="pageno" type="hidden" name="pageno" value="${page.number}"/>
                        <input id="size" type="hidden" name="size" value="${page.size}"/>
                        <input type="hidden" name="direction" value="${page.sortOrder == null ? "":page.sortOrder.direction}"/>
                        <input type="hidden" name="sortProperty" value="${page.sortOrder == null ? "":page.sortOrder.property}"/>
                        <div>
                            <table class="search-table">
                                <tr>
                                    <td>
                                        <label for="user_id">用户编号</label>
                                    </td>
                                    <td>
                                        <input type="text" id="user_id" name="id" class="input-medium search-query" value="${user.id == 0 ? null:user.id}"/>
                                    </td>
                                    <td>
                                        <label for="login_name">登录名</label>
                                    </td>
                                    <td>
                                        <input type="text" id="login_name" name="loginName" class="input-medium search-query" value="${user.loginName}"/>
                                    </td>
                                    <td>
                                        <label for="begin_time">创建开始时间</label>
                                    </td>
                                    <td>
                                        <input type="text" id="begin_time" name="beginTime" class="input-medium search-query" value="<fmt:formatDate value="${beginTime}" pattern="yyyy-MM-dd"/>"/>
                                    </td>
                                    <td><label for="end_time">创建结束时间</label></td>
                                    <td>
                                        <input type="text" id="end_time" name="endTime" class="input-medium search-query" value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label for="email">Email</label>
                                    </td>
                                    <td>
                                        <input type="text" id="email" name="email" class="input-medium search-query" value="${user.email}"/>
                                    </td>
                                    <td>
                                        <label for="mobile">手机号</label>
                                    </td>
                                    <td>
                                        <input type="text" id="mobile" name="mobile" class="input-medium search-query" value="${user.mobile}"/>
                                    </td>
                                    <td>
                                        <label for="status">状态</label>
                                    </td>
                                    <td>
                                        <input type="text" id="status" name="status" class="input-medium search-query" value="${user.status == 0 ? null:user.status}"/>
                                    </td>
                                    <td colspan="2">
                                        <button type="submit" id="search-form-submit" class="btn btn-purple btn-small"><i class="icon-search icon-on-right"></i>查询</button>
                                        &nbsp; &nbsp; &nbsp;
                                        <button type="reset" class="btn btn-info btn-small"><i class="icon-undo"></i>重置</button>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </form>
                    <hr/>
                </div>

                <div class="row-fluid">
                    <div id="table_report_wrapper" class="dataTables_wrapper" role="grid">

                    </div>
                </div>

                <div class="row-fluid">
                    <div class="login-container">
                        <div class="position-relative">
                            <div id="update-user-box" class="invisible widget-box no-border">

                            </div>

                            <div id="update-user-modal-box" class="modal fade"  tabindex="-1" role="dialog"
                                 aria-labelledby="modal_title_text" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close"
                                                    data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h4 class="modal-title" id="modal_title_text">
                                                更新用户
                                            </h4>
                                        </div>
                                        <div class="modal-body">

                                        </div>
                                        <div class="modal-footer"></div>
                                    </div>
                                </div>
                            </div>

                        </div>
                     </div>
                </div>
            </div>
        </div>

        <div id="ace-settings-container">
            <%@include file="../page_setting_box.jsp" %>
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
<script type="text/javascript" src="${request.contextPath}/static/js/jquery.validate.min.js"></script>

<script type="text/javascript" src="${request.contextPath}/static/js/bootbox.min.js"></script>

<script type="text/javascript" src="${request.contextPath}/static/js/layer/layer.js"></script>

<!-- ace scripts -->
<script src="${request.contextPath}/static/js/ace-elements.min.js"></script>
<script src="${request.contextPath}/static/js/ace.min.js"></script>
<!-- inline scripts related to this page -->

<script type="text/javascript">
    $(function(){
        var validator = $('#search-form').validate({
            rules: {
                id: {
                    digits:true,
                    min: 1
                }
            },
            messages: {
                id: {
                    digits: "(必须是正整数)",
                    min: "(必须大于1)"
                }
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                $('.alert-error', $('.search-form')).show();
            },
            highlight: function (e) {
                $(e).closest('.control-group').removeClass('info').addClass('error');
            },
            success: function (e) {
                $(e).closest('.control-group').removeClass('error').addClass('info');
                $(e).remove();
            },
            errorPlacement: function (error, element) {
                // Append error within linked label
                $( element )
                        .closest( "form" )
                        .find( "label[for='" + element.attr( "id" ) + "']" )
                        .append( error );
                //以下是将错误信息插入验证元素后面
               // error.insertAfter(element);
            },
            errorElement: "span",
            errorClass:"error-msg",
            submitHandler: function (form) {
                loadData();
            },
            invalidHandler: function (form) {
            }
        });

        //reset
        $('#reset').click(function (){
            validator.resetForm();
        });
    });

    $(function (){
        //初始化加载数据
        loadData();
    });

    function loadData(){
        $.ajax({
            url: "${request.contextPath}/user/list_async_data.action",
            timeout:10000,
            type: "POST",
            data: $("#search-form").serialize(),
            success: function(data){
                $("#table_report_wrapper").html(data);
                $("#page_size").val($("#size").val());
            },
            error: function (data) {
                alert('系统内部错误，请稍后再试！');
                if(typeof(console)!='undefined'){
                    console.log(data);
                    console.log(data.responseText);
                }
            }
        });
    }
    function goto(pageno){
        $("#pageno").val(pageno);
        $("#search-form-submit").trigger("click");
    }

    function changePagesize(pageSize){
        $("#pageno").val(0);
        $("#size").val(pageSize);
        $("#search-form-submit").trigger("click");
    }

    function doUnLock(userId){
        changeStatus(userId,2);
    }
    function doLock(userId){
        changeStatus(userId,20);
    }
    function changeStatus(userId,status){
        $.ajax({
            url: "${request.contextPath}/user/do_update_async.action",
            timeout:10000,
            type: "POST",
            async: true,
            data: {id: userId,status:status},
            success: function(data){
                if (data.status != 200) {
                    bootbox.dialog("操作失败!" + data.errors[0].message,
                            [
                                {
                                    "label" : "确定!",
                                    "class" : "btn-small btn-success",
                                    "callback": function() {}
                                }]
                    );
                } else {
                    layer.msg('操作成功',{time: 1000});
                    loadData();
                }
            },
            error: function (data) {
                alert('系统内部错误，请稍后再试！');
                if(typeof(console)!='undefined'){
                    console.log(data);
                    console.log(data.responseText);
                }
            }
        });
    }
    function doDelete(button,userId){
        bootbox.confirm("确定删除?", function(result) {
            if(result) {
                $.ajax({
                    url: "${request.contextPath}/user/delete.action",
                    timeout:10000,
                    type: "GET",
                    async: true,
                    data: {userId: userId},
                    success: function(data){
                        if (data.status != 200) {
                            bootbox.dialog("删除用户失败!" + data.errors[0].message,
                                    [
                                        {
                                            "label" : "确定!",
                                            "class" : "btn-small btn-success",
                                            "callback": function() {}
                                        }]
                            );
                        } else {
                            layer.msg('删除成功',{time: 1000});
                            //TODO 如果当前页记录数(<tr>的个数)大于1，删除当前数据所在<tr>，否则重新加载上一页的数据
                            var recordCount = $(button).parent().parent().parent().siblings().size();
                            if(recordCount>1) {
                                $(button).parent().parent().parent().remove();
                            }else{
                                var currentPageNumber = $("#pageno").val();
                                if(currentPageNumber >= 1) {
                                    $("#pageno").val(currentPageNumber-1);
                                    loadData();
                                }else{
                                    $(button).parent().parent().parent().remove();
                                }
                            }

                        }
                    },
                    error: function (data) {
                        alert('系统内部错误，请稍后再试！');
                        if(typeof(console)!='undefined'){
                            console.log(data);
                            console.log(data.responseText);
                        }
                    }
                });
            }
        });
    }

</script>
</body>
</html>
