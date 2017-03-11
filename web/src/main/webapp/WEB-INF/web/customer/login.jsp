<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>登录</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <style>
        *{
            margin:0;
            padding: 0;
        }
        ul li{
            list-style: none;
        }
        #login{
            width:100%;
            height:100%;
        }
        #login .login-box{
            width: 400px;
            height: 350px;
            background:#dcefff;
            position: fixed;
            left:50%;
            top:50%;
            transform: translate(-50%,-50%);
        }

        #login .login-box h2{
            text-align: center;
            height: 100px;
            line-height: 100px;
            font-weight: 300;
            font-size:30px;
            background: #6ea1ff;
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            color:#171b28;
        }
        #login .login-box ul li label, #login .login-box ul li input{
            display: block;
            text-align: left;
            margin:0 auto;
            width: 200px;
            font-weight: 300;
        }
        #login .login-box ul li input{
            height: 35px;
            border:none;
            background-color: #dcefff;
            border-bottom: 1px solid #6ea1ff;
        }
        #login .login-box ul li button{
            width: 100px;
            height: 35px;
            font-size: 18px;
            border-radius: 5px;
            background: #6ea1ff;
            border:#6ea1ff;
            font-weight: 400;
        }
        #login .login-box ul li{
            /*width:100%;*/
            height: 70px;
            margin-top: 10px;
            text-align: center;
        }
    </style>
</head>
<body>
<div id="login">
    <div class="login-box">
        <h2>登 录</h2>
        <ul>
            <li>
                <label>用户名</label>
                <input type="text" id="username">
            </li>
            <li>
                <label>密码</label>
                <input type="password" id="password">
            </li>
            <li>
                <button id="loginBtn">登录</button>
            </li>
        </ul>
    </div>
</div>
<script src="/js/jquery-2.2.3.min.js"></script>
<script>
    $(function() {
        $('#loginBtn').click(function() {
            var username = $("#username").val();
            var password = $("#password").val();
            if (username == '') {
                alert('请输入用户名！');
                return;
            }
            if (password == '') {
                alert('请输入密码！');
                return;
            }
            $.ajax({
                type: 'POST',
                url: '/customer/login',
                data: {'username': username, 'password': password},
                dataType: 'json',
                success: function(data) {
                    if (data.status == 1) {
                        if (data.data.cusType == 'HOSPITAL') {
                            location.href = '/hospital';
                        } else if ('PERSONAL' == data.data.cusType) {
                            location.href = '/customer';
                        } else {
                            location.href = '/thirdparty';
                        }
                    } else {
                        alert(data.message);
                    }
                }
            });
        });
    });
</script>
</body>
</html>