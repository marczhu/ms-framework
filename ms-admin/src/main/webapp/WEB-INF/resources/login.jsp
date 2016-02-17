<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>登陆页面</title>
    <meta name="description" content="用户登陆页面"/>
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
    <!--[if lt IE 9]>
    <link rel="stylesheet" href="${request.contextPath}/static/css/ace-ie.min.css"/>
    <![endif]-->
</head>
<body class="login-layout">

<div class="container-fluid" id="main-container">
    <div id="main-content">
        <div class="row-fluid">
            <div class="span12">

                <div class="login-container">
                    <div class="row-fluid">
                        <div class="center">
                            <h1><i class="icon-leaf green"></i> <span class="red">Smart</span> <span class="white">Application</span>
                            </h1>
                            <h4 class="blue">&copy; Your Company Name</h4>
                        </div>
                    </div>
                    <div class="space-6"></div>
                    <div class="row-fluid">
                        <div class="position-relative">
                            <div id="login-box" class="visible widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h6 class="header lighter bigger">
                                            <i id="id-msg-icon" class="icon-coffee green"></i>
                                            <span id="id-msg-text" class="text-success"> 请输入用户名和密码 </span>
                                        </h6>

                                        <div class="space-6"></div>
                                        <form id="login-form" action="${request.contextPath}/do_login.action"
                                              method="post">
                                            <fieldset>
                                                <label>
                                                    <span class="block input-icon input-icon-right">
                                                        <input type="text" id="loginName" name="loginName"
                                                               class="span12" placeholder="请输入用户名"/>
                                                        <i class="icon-user"></i>
                                                    </span>
                                                </label>
                                                <label>
                                                    <span class="block input-icon input-icon-right">
                                                        <input type="password" id="password" name="password"
                                                               class="span12" placeholder="请输入密码"/>
                                                        <i class="icon-lock"></i>
                                                    </span>
                                                </label>

                                                <div class="space"></div>
                                                <div class="row-fluid">
                                                    <label class="span8">
                                                        <input type="checkbox" id="id-remeber-me"
                                                               name="rememberMe"><span class="lbl"> 记住密码</span>
                                                    </label>
                                                    <a id="id-login-btn" onclick="login();"
                                                       class="span4 btn btn-small btn-primary"><i
                                                            class="icon-key"></i> 登 陆
                                                    </a>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div>
                                    <!--/widget-main-->
                                </div>
                                <!--/widget-body-->
                            </div>
                            <!--/login-box-->

                        </div>
                        <!--/position-relative-->
                    </div>
                </div>
            </div>
            <!--/span-->
        </div>
        <!--/row-->
    </div>
</div>
<!--/.fluid-container-->
<!-- basic scripts -->
<script src="${request.contextPath}/static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    window.jQuery || document.write("<script src='${request.contextPath}/static/js/jquery-1.9.1.min.js'>\x3C/script>");
</script>
<!-- page specific plugin scripts -->
<script src="${request.contextPath}/static/js/jquery.cookie.js"></script>

<!-- inline scripts related to this page -->

<script type="text/javascript">
    function show_box(id) {
        $('.widget-box.visible').removeClass('visible');
        $('#' + id).addClass('visible');
    }
    $(document).keyup(function (event) {
        if (event.keyCode == 13) {
            $('#id-login-btn').trigger('click');
        }
    });
    function show_info_tips(msg) {
        clear_tips_classes();
        $('#id-msg-icon').addClass('icon-coffee green');
        $('#id-msg-text').addClass('text-success');
        $('#id-msg-text').text(msg);
    }
    function show_warning_tips(msg) {
        clear_tips_classes();
        $('#id-msg-icon').addClass('icon-warning-sign orange');
        $('#id-msg-text').addClass('text-warning');
        $('#id-msg-text').text(msg);
    }
    function show_error_tips(msg) {
        clear_tips_classes();
        $('#id-msg-icon').addClass('icon-warning-sign red');
        $('#id-msg-text').addClass('text-error');
        $('#id-msg-text').text(msg);
    }
    function clear_tips_classes() {
        $('#id-msg-icon').removeClass('icon-coffee green');
        $('#id-msg-text').removeClass('text-success');
        $('#id-msg-icon').removeClass('icon-coffee orange');
        $('#id-msg-text').removeClass('text-warning');
        $('#id-msg-icon').removeClass('icon-warning-sign red');
        $('#id-msg-text').removeClass('text-error');
    }
    function login() {
        if (check()) {
            $.ajax({
                url: "${request.contextPath}/do_login.action",
                timeout:10000,
                type: "POST",
                data: $("#login-form").serialize(),
                success: function (data) {
                    if (data.status != 200) {
                        show_error_tips(data.errors[0].message);
                    } else {
                        if ($('#id-remeber-me').prop('checked') == true) {
                            saveCookies();
                        } else {
                            clearCookies();
                        }
                        show_info_tips("登陆成功");
                        $(location).attr('href','${request.contextPath}/index.action');
                    }
                },
                error: function (data) {
                    alert('系统内部错误，请稍后再试！');
                    if(typeof(console)!='undefined'){
                        console.log(data.responseText);
                    }
                }
            })
        }
    }
    function check() {
        if ($('#loginName').val() == '') {
            show_error_tips('请输入用户名');
            return false;
        } else {
            $('#loginName').val(jQuery.trim($('#loginName').val()));
        }
        if ($('#password').val() == '') {
            show_error_tips('请输入密码');
            return false;
        } else {
            $('#password').val(jQuery.trim($('#password').val()));
        }
        return true;
    }
    function saveCookies() {
        $.cookie('loginName', jQuery.trim($('#loginName').val()),{
            expires: 7
        });
        encryptPassword(jQuery.trim($('#password').val()));
    }
    function savePasswordCookie(data){
        if (data.status != 200) {
            //throw new Error(data.entity);
            if(typeof(console)!='undefined') {
                console.log("加密cookie失败:" + data.entity);
            }
        } else {
            $.cookie('password', data.entity, {
                expires: 7
            });
        }
    }
    function clearCookies() {
        $.cookie('loginName', '', {
            expires: -1
        });
        $.cookie('password', '', {
            expires: -1
        });
    }
    function encryptPassword(passwordText){
        $.ajax({
            url: "${request.contextPath}/encrypt_cookie.action",
            timeout:10000,
            type: "GET",
            async: true,
            data: {password: passwordText},
            success: function(data){
                savePasswordCookie(data);
            },
            error: function (data) {
                alert('系统内部错误，请稍后再试！');
                if(typeof(console)!='undefined'){
                    console.log(data.responseText);
                }
            }
        });
    }

    function decryptPassword(login_name,password){
        $.ajax({
            url: "${request.contextPath}/decrypt_cookie.action",
            timeout:10000,
            type: "GET",
            data: {loginName:login_name,password:password},
            success: function (data) {
                if (data.status != 200) {
                    if(typeof(console)!='undefined') {
                        console.log("解密cookie失败:" + data.entity);
                    }
                } else {
                    $('#password').val(data.entity);
                }
            },
            error: function (data) {
                alert('系统内部错误，请稍后再试！');
                if(typeof(console)!='undefined'){
                    console.log(data.responseText);
                }
            }
        });
    }
</script>
<script type="text/javascript">
    $(function () {
        $('#id-remeber-me').click(function () {
            if ($('#id-remeber-me').prop('checked') == true) {
                show_warning_tips('公共场所不建议自动登录，以防账号丢失');
            } else {
                show_info_tips('请输入用户名和密码');
            }
        });
    });

    $(function () {
        var loginName = $.cookie('loginName');
        var password = $.cookie('password');
        if (typeof(loginName) != 'undefined' && typeof(password) != 'undefined') {
            $('#loginName').val(loginName);
            decryptPassword(loginName,password);
            $('#id-remeber-me').trigger('click').prop('checked', 'true');
        }
    });

</script>

</body>
</html>
