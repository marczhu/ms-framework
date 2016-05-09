<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>新增用户</title>
    <meta name="description" content="新增用户" />
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
            <div class="page-header position-relative">
                <h1>用户管理 <small><i class="icon-double-angle-right"></i> 新用户</small></h1>
            </div>

            <div class="row-fluid">
                <div class="row-fluid">
                    <div class="span12">
                        <div class="widget-box">
                            <div class="widget-header widget-header-blue widget-header-flat wi1dget-header-large">
                                <h4 class="lighter">新用户</h4>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main">
                                    <form id="register-form" class="form-horizontal" action="${request.contextPath}/user/do_create.action" method="post" >
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <label class="control-label" for="login_name">用户名</label>
                                                    <div class="controls">
                                                        <span class="span6">
                                                            <input class="span6" type="text" name="loginName" id="login_name"/>
                                                        </span>
                                                    </div>
                                                </div>

                                                <div class="space-6"></div>

                                                <div class="control-group">
                                                    <label class="control-label" for="password">请设置密码</label>
                                                    <div class="controls">
                                                        <span class="span6">
                                                            <input class="span6" type="password" name="password" id="password"/>
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="space-6"></div>

                                                <div class="control-group">
                                                    <label class="control-label" for="password_confirm">请确认密码</label>
                                                    <div class="controls">
                                                        <span class="span6">
                                                            <input class="span6" type="password" name="password_confirm" id="password_confirm"/>
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="space-6"></div>

                                                <div class="control-group">
                                                    <label class="control-label" for="email">验证邮箱</label>
                                                    <div class="controls">
                                                        <span class="span6">
                                                            <input class="span6" type="email" name="email" id="email"/>
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="space-6"></div>

                                                <div class="control-group">
                                                    <label class="control-label" for="mobile">验证手机号</label>
                                                    <div class="controls">
                                                        <span class="span6">
                                                            <input class="span6" type="text" name="mobile" id="mobile"/>
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="space-6"></div>

                                                <div class="control-group">
                                                    <label class="control-label" for="captcha">验证码</label>
                                                    <div class="controls">
                                                        <span class="span6">
                                                            <input class="span6" type="text" name="captcha" id="captcha"/>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <hr/>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <span class="span6">
                                                            <label><input name="agree" id="agree" type="checkbox" /><span class="lbl"> I accept the policy</span></label>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="form-actions">
                                                    <button class="btn btn-info" type="submit"><i class="icon-ok"></i> Submit</button>
                                                    &nbsp; &nbsp; &nbsp;
                                                    <button class="btn" type="reset"><i class="icon-undo"></i> Reset</button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
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
<script type="text/javascript" src="${request.contextPath}/static/js/jquery.validate.min.js"></script>

<!-- ace scripts -->
<script src="${request.contextPath}/static/js/ace-elements.min.js"></script>
<script src="${request.contextPath}/static/js/ace.min.js"></script>
<!-- inline scripts related to this page -->

<script type="text/javascript">
    $(function(){
        jQuery.validator.addMethod("mobile", function (value, element) {
            if(this.optional(element)){
                return true;
            }else{
                return new RegExp("^0?(13|15|18|14|17)[0-9]{9}$").test(value);
            }
        }, "请输入正确的手机号.");

        jQuery.validator.addMethod("is_optional", function (value, element) {
            return true;
        });
        var validator = $('#register-form').validate({
            errorElement: 'span',
            errorClass: 'help-inline',
            focusInvalid: true,
            debug:true,
            rules: {
                loginName: {
                    required: true,
                    minlength: 4,
                    maxlength: 30,
                    remote: { //TODO 尚不知remote如何处理异常,可以使用自定义验证方法方式代替remote
                        url: "is_valid_login_name.action",//该URL只能返回true或false
                        type: "get",
                        dataType: "json",
                        data: {
                            loginName: function() {
                                return $("#login_name").val();
                            }
                        }
                    }
                },
                password: {
                    required: true,
                    minlength: 5
                },
                password_confirm: {
                    required: true,
                    minlength: 5,
                    equalTo: "#password"
                },
                email: {
                    is_optional: true,
                    email:true
                },
                mobile: {
                    mobile:true
                },
                agree: 'required'
            },
            messages: {
                loginName: {
                    required: "请输入用户名",
                    remote: "用户名已存在",
                    minlength: jQuery.format("用户名长度不能小于{0}个字符")
                },
                email: {
                    email: "邮箱格式不正确"
                },
                password: {
                    required: "请输入密码",
                    minlength: jQuery.format("密码长度不能小于{0}个字符")
                },
                password_confirm: {
                    required: "请输入密码",
                    equalTo: "两次输入的密码不一致"
                },
                agree: "请接受相关协议"
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                $('.alert-error', $('.register-form')).show();
            },
            highlight: function (e) {
                $(e).closest('.control-group').removeClass('info').addClass('error');
            },
            success: function (e) {
                $(e).closest('.control-group').removeClass('error').addClass('info');
                $(e).remove();
            },
            errorPlacement: function (error, element) {
                if(element.is(':checkbox') || element.is(':radio')) {
                    var controls = element.closest('.controls');
                    if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
                    else error.insertAfter(element.nextAll('.lbl').eq(0));
                }
                else if(element.is('.chzn-select')) {
                    error.insertAfter(element.nextAll('[class*="chzn-container"]').eq(0));
                }
                else error.insertAfter(element);
            },
            submitHandler: function (form) {
                form.submit();
            },
            invalidHandler: function (form) {
            }
        });

        //reset
        $('#reset').click(function (){
            validator.resetForm();
        });

        $(document).keyup(function (event) {
            if (event.keyCode == 13) {
                $("button[type=submit]").trigger("click");
            }
        });
    });
</script>
</body>
</html>
