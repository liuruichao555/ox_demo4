<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${detail.user.name} 病历</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <style>
        *{
            margin:0;
            padding:0;
        }
        li{
            list-style: none;
            float: left;
        }
        h1{
            text-align: center;
            margin: 20px;
        }
        .bingli{
            padding: 50px;
            border:1px solid #dddddd;
            margin: 0 auto;
            width: 800px;
        }
        .wrap{
            display: block;
            border-bottom:1px solid #dddddd;
            margin-bottom: 10px;
        }
        .top {
            height:50px;
            line-height: 50px;
        }

        .wrap label{
            margin-left: 10px;
        }
        .wrap span{
            margin-right: 10px;
        }
        .msg p{
            padding:0 10px;
        }
        .clearFix{
            clear: both;
        }
        .photo ul li{
            margin: 15px;
        }
    </style>
</head>
<body>
<h1>病历</h1>
<div class="bingli">
    <div class="wrap top">
        <label>
            姓名：
            <span>${detail.user.name}</span>
        </label>
        <label>
            性别：
            <span>${detail.user.gender}</span>
        </label>
        <%--<label>
            出生日期：
            <span>xxxx-xx-xx</span>
        </label>--%>
        <label>
            创建时间：
            <span><fmt:formatDate value="${detail.createTime}" pattern="yyyy-MM-dd" /></span>
        </label>
        <label>
            电话：
            <span>${detail.user.tel}</span>
        </label>
    </div>
    <div class="wrap top">
        <label>
            身份证号：
            <span>${detail.user.identityId}</span>
        </label>
        <label>
            血型：
            <span>${detail.user.bloodType}</span>
        </label>
    </div>
    <div class="wrap msg">
        <label>
            病史信息：
            <p>${detail.user.medicalHistory}</p>
        </label>
    </div>
    <div class="wrap top">
        <%--<label>
            治疗时间：
            <span>xxxx-xx-xx</span>
        </label>--%>
    </div>
    <div class="wrap msg">
        <label>
            门诊信息：
            <p>${detail.outpatientInfo}</p>
        </label>
    </div>
    <div class="wrap msg">
        <label>
            处方信息：
            <p>${detail.medicineInfo}</p>
        </label>
    </div>
    <div class="wrap msg">
        <label>
            住院信息：
            <p>${detail.medicineInfo}</p>
        </label>
    </div>
    <div class="wrap photo">
        <label>
            拍片
        </label>
        <ul>
            <c:if test="${detail.picInfo != null}">
                <li><img src="/images/${detail.picInfo}" width="220"></li>
            </c:if>
        </ul>
        <div class="clearFix"></div>
    </div>
    <div class="wrap msg">
        <label>
            医生描述：
            <p>${detail.doctorMessage}</p>
        </label>
    </div>
</div>
<script src="/js/jquery-2.2.3.min.js"></script>
<script>
    $(function() {
    });
</script>
</body>
</html>