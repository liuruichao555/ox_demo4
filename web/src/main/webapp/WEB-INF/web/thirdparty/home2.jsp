<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${sessionScope.userInfo.realName}--医院</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <style>
        h1{
            text-align: center;
            height: 100px;
            line-height: 100px;
        }
        table{
            margin:0 auto;
            width: 700px;
            padding: 50px 10%;
            border: 1px solid #dddddd;
            border-spacing: 0;
            border-collapse: collapse;
        }

        td {
            padding: 8px 10px;
            white-space: nowrap;
            line-height: 1.42857143;
            text-align: center;
        }
        th {
            padding: 8px 10px;
            white-space: nowrap;
            border-bottom: 2px solid #ddd;
        }
        tr:nth-child(2n) {
            background-color: #f9f9f9;
        }
        button{
            width: 200px;
            height: 35px;
            font-size: 15px;
            color: #252528;
            background: #6ea1ff;
            border-radius: 5px;
            border:#6ea1ff;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div id="hospital">
    <div style="width: 500px; text-align: center;margin: 0 auto;">
        <div style="font-size: 40px; margin: 10px;">${sessionScope.userInfo.realName}</div>
        <div style="margin: 10px;">积分：${requestScope.balance}</div>
        <button type="button" id="linkBtn">查询疾病数据</button>
    </div>


    <h1>已购买数据</h1>
    <table>
        <tr>
            <th>id</th>
            <th>医院名</th>
            <th>时间</th>
            <th>诊断信息</th>
            <th>价格</th>
            <th>操作</th>
        </tr>
        <c:forEach var="medicalRecord" items="${requestScope.list}">
            <tr>
                <td>${medicalRecord.id}</td>
                <td>${medicalRecord.hospitalName}</td>
                <td><fmt:formatDate value="${medicalRecord.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
                <td>${medicalRecord.diagnoseInfo}</td>
                <td>${medicalRecord.price}</td>
                <td>
                    <a target="_blank" href="/hospital/recordDetail?query=${medicalRecord.query}">查看</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<script src="/js/jquery-2.2.3.min.js"></script>
<script>
    $(function() {
        $('#linkBtn').click(function() {
            location.href = '/thirdparty';
        });
    });
</script>
</body>
</html>