<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <div class="widget-body">
        <div class="widget-main">
            <h4 class="header green lighter bigger"><i class="icon-group blue"></i> 修改用户信息</h4>

            <div class="space-6"></div>
            <p>
                Enter your details to begin:
            </p>

            <form id="update-form" action="${request.contextPath}/user/do_update.action" method="post" >
                <input type="hidden" name="id" value="${user.id}">
                <fieldset>
                    <label>
                        <span class="block input-icon input-icon-right">
                            <input type="text" name="loginName" class="span12" value="${user.loginName}"/>
                            <i class="icon-user"></i>
                        </span>
                    </label>
                    <label>
                        <span class="block input-icon input-icon-right">
                            <input type="text" name="email" class="span12" value="${user.email}"/>
                            <i class="icon-envelope"></i>
                        </span>
                    </label>
                    <label>
                        <span class="block input-icon input-icon-right">
                            <input type="text" name="mobile" class="span12" value="${user.mobile}"/>
                            <i class="icon-mobile-phone"></i>
                        </span>
                    </label>

                    <div class="space-24"></div>

                    <div class="row-fluid">
                        <button type="submit" class="span6 btn btn-small btn-success">更新 <i
                                class="icon-arrow-right icon-on-right"></i></button>
                        <button id="update_box_cancel" class="span6 btn btn-small" data-dismiss="modal"><i class="icon-refresh"></i> 取消</button>
                    </div>

                </fieldset>
            </form>
        </div>

    </div>
    <!--/widget-body-->

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
        var validator = $('#update-form').validate({
            errorElement: 'span',
            errorClass: 'help-inline',
            focusInvalid: true,
            debug:true,
            rules: {
                loginName: {
                    required: true,
                    minlength: 4,
                    maxlength: 30,
                    remote: {
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
                email: {
                    is_optional: true,
                    email:true
                },
                mobile: {
                    mobile:true
                }
            },
            messages: {
                loginName: {
                    required: "请输入用户名",
                    remote: "用户名已存在",
                    minlength: jQuery.format("用户名长度不能小于{0}个字符")
                },
                email: {
                    email: "邮箱格式不正确"
                }
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                $('.alert-error', $('.update-form')).show();
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
//                form.submit();
                doUpate();
            },
            invalidHandler: function (form) {
            }
        });

    });

    function doUpate(){
        $.ajax({
            url: "${request.contextPath}/user/do_update_async.action",
            timeout:10000,
            type: "POST",
            async: true,
            data: $("#update-form").serialize(),
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
                    $("#update_box_cancel").trigger("click");
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
</script>