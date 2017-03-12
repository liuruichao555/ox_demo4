<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${sessionScope.userInfo.realName}</title>

    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <style>
        h1{
            text-align: center;
        }
        div h2{
            width:700px;
            display: block;
            margin:0 auto;
            height: 70px;
            line-height: 70px;
        }
        table{
            margin:0 auto;
            width: 700px;
            padding: 50px 10%;
            border: 1px solid #dddddd;
            border-spacing: 0;
            border-collapse: collapse;
            margin-bottom: 50px;
        }
        td button{
            width: 70px;
            height: 30px;
            line-height: 30px;
            font-size: 14px;
            display:inline-block;
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
            background-color: #ceeaff;
        }
    </style>
</head>
<body>
<div style="width: 500px; height: 200px;text-align: center;margin: 0 auto;">
    <span style="font-size: 40px;">${sessionScope.userInfo.realName}</span>
    <span>积分：${requestScope.balance}</span>
</div>
<div>
    <h2><a href="javascript:void(0);" onclick="window.open('https://ox-invoice-web-unblocked-omega.mybluemix.net/invoice/list')" target="_blank"> 报销管理</a></h2>
    <h2>病历列表 </h2>
    <table>
        <tr>
            <th>id</th>
            <th>描述</th>
            <th>日期</th>
            <th>操作</th>
        </tr>
        <c:forEach var="medicalRecord" items="${requestScope.list}">
            <tr>
                <td>${medicalRecord.id}</td>
                <td>${medicalRecord.diagnoseInfo}</td>
                <td><fmt:formatDate value="${medicalRecord.createTime}" pattern="yyyy-MM-dd" /></td>
                <td>
                    <a href="javascript:;" class="shareBtn" data-id="${medicalRecord.id}">共享</a>
                    <c:if test="${medicalRecord.price == null}">
                        <a href="javascript:;" class="fixPriceBtn" data-id="${medicalRecord.id}">定价</a>
                    </c:if>
                    <c:if test="${medicalRecord.price != null}">
                        已定价：${medicalRecord.price}元
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    <h2>通知列表</h2>
    <table>
        <tr>
            <th>通知内容</th>
            <th>操作</th>
        </tr>
        <c:forEach var="message" items="${messages}">
            <tr>
                <td>${message.content}</td>
                <td>
                    <c:if test="${message.status == 1}">
                    已同意
                    </c:if>
                    <c:if test="${message.status == 0}">
                        <a href="javascript:;" data-id="${message.remark}" data-consumer="${message.fromUser.cusName}" data-msgId="${message.id}" class="opAc">同意</a>
                        <a href="javascript:;">拒绝</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    <h2>共享列表</h2>
    <table>
        <tr>
            <th>病例</th>
            <th>医院名称</th>
        </tr>
        <c:forEach items="${shareInfoList}" var="shareInfo">
            <tr>
                <td>${shareInfo.diagnoseInfo}</td>
                <td>${shareInfo.hospitalName}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<script src="/js/jquery-2.2.3.min.js"></script>
<script>
    $(function() {
        $('.shareBtn').click(function() {
            var item = $(this);
            var id = item.attr('data-id');
            if (id) {
                location.href = '/customer/share?id=' + id;
            }
        });

        $('.fixPriceBtn').click(function() {
            var item = $(this);
            var id = item.attr('data-id');
            if (id) {
                location.href = '/customer/setPrice?recordId=' + id;
            }
        });

        $('.opAc').click(function() {
            var item = $(this);
            var id = item.attr('data-id');
            var msgId = item.attr('data-msgId');
            var consumer = item.attr('data-consumer');
            if (id) {
                $.ajax({
                    url: '/customer/updatePermission?recordId=' + id + '&ownerId=${sessionScope.userInfo.cusName}&consumer=' + consumer + '&dataItem=*',
                    dataType: 'json',
                    success: function(data) {
                        if (data.status == 1) {
                            setTimeout(function() {
                                location.href = location.href;
                            }, 50);
                        } else {
                            alert(data.message);
                        }
                    }
                });
                $.ajax({
                    url: '/customer/updateMsgStatus?id=' + msgId,
                    dataType: 'json',
                    success: function(data) {
                    }
                });
            }
        });
    });
</script>
</body>
</html>