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
            width: 100px;
            height: 35px;
            font-size: 15px;
            color: #252528;
            background: #6ea1ff;
            border-radius: 5px;
            border:#6ea1ff;
            margin-left:80%;
        }
    </style>
</head>
<body>
<div id="hospital">
    <h1>${sessionScope.userInfo.realName}</h1>
    <!--<button type="button">共享病例</button>-->
    <div style="width: 300px;margin: 0 auto;">
        <input type="text" id="q"><input type="button" id="qBtn" value="搜索">
    </div>

    <h1>历史记录</h1>
    <table>
        <thead>
            <tr>
                <th>时间</th>
                <th>医院</th>
                <th>门诊信息</th>
                <th>治疗信息</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody id="queryData"></tbody>
    </table>

    <h1>本地数据</h1>
    <table>
        <tr>
            <th>id</th>
            <th>病人姓名</th>
            <th>诊断信息</th>
            <th>时间</th>
            <th>来源</th>
            <th>操作</th>
        </tr>
        <c:forEach var="medicalRecord" items="${requestScope.list}">
            <tr>
                <td>${medicalRecord.id}</td>
                <td>${medicalRecord.user.name}</td>
                <td>${medicalRecord.diagnoseInfo}</td>
                <td><fmt:formatDate value="${medicalRecord.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
                <td>${medicalRecord.source}</td>
                <td>
                    <a href="/hospital/recordDetail?recordId=${medicalRecord.id}">查看</a>
                    <!--<a href="javascript:;" class="shareBtn" data-id="${medicalRecord.id}">共享</a>-->
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<script src="/js/jquery-2.2.3.min.js"></script>
<script>
    $(function() {
        var loginUserId = '${sessionScope.userInfo.id}';
        var loginName = '${sessionScope.userInfo.cusName}';
        $('#qBtn').click(function() {
            var q = $('#q').val();
            if (q == '') {
                alert('请输入查询id');
                return;
            }
            $.ajax({
                url: '/getSummary?name=' + q,
                data: null,
                dataType: 'json',
                success: function(data) {
                    if (data.status == 1) {
                        var html = "";
                        var list  = data.data;
                        var map = {};
                        var medicalRecordMap = {};
                        var queryMap = {};
                        for (var i = 0; i < list.length; i++) {
                            var obj = list[i];
                            medicalRecordMap[obj.recordId] = obj.medicalRecord;
                            if (!map[obj.recordId]) {
                                map[obj.recordId] = obj.havePermission;
                                queryMap[obj.recordId] = obj.query;
                            } else {
                                if (loginName == obj.userId) {
                                    map[obj.recordId] = obj.havePermission;
                                    queryMap[obj.recordId] = obj.query;
                                }
                            }
                        }
                        for (var key in map) {
                            var medicalRecordObj = medicalRecordMap[key];
                            var diagnoseInfo = medicalRecordObj.diagnoseInfo;
                            if (diagnoseInfo.length > 10) {
                                diagnoseInfo = diagnoseInfo.substring(0, 7) + '...';
                            }
                            if (map[key] == 'True') {
                                html = html + '<tr><td>' + new Date(medicalRecordObj.createTime).format('yyyy-MM-dd')
                                    + '</td><td>' + medicalRecordObj.hospitalName
                                    + '</td><td>' + diagnoseInfo + '</td>'
                                    + '<td>' + medicalRecordObj.medicineInfo + '</td><td><a target="_blank" href="/hospital/recordDetail?query=' + queryMap[key] + '">查看</a></td></tr>';
                            } else {
                                html = html + '<tr><td>' + new Date(medicalRecordObj.createTime).format('yyyy-MM-dd')
                                    + '</td><td>' + medicalRecordObj.hospitalName
                                    + '</td><td>' + diagnoseInfo + '</td>'
                                    + '<td>' + medicalRecordObj.medicineInfo + '</td><td><a target="_blank" href="javascript:;" class="consent" data-id="' + key + '">申请</a></td></tr>';
                            }

                        }
                        $('#queryData').html(html);
                    } else {
                        $('#queryData').html(data.message);
                    }
                }
            });
        });

        $('body').delegate('.consent', 'click', function() {
            var item = $(this);
            var recordId = item.attr('data-id');
            var ownerName = $('#q').val();
            $.ajax({
                type: 'POST',
                url: '/hospital/consent',
                data: {'recordId': recordId, 'ownerName': ownerName},
                dataType: 'json',
                success: function(data) {
                    alert('申请请求已发送')
                }
            });
        });

        Date.prototype.format =function(format)
        {
            var o = {
                "M+" : this.getMonth()+1, //month
                "d+" : this.getDate(), //day
                "h+" : this.getHours(), //hour
                "m+" : this.getMinutes(), //minute
                "s+" : this.getSeconds(), //second
                "q+" : Math.floor((this.getMonth()+3)/3), //quarter
                "S" : this.getMilliseconds() //millisecond
            }
            if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
                (this.getFullYear()+"").substr(4- RegExp.$1.length));
            for(var k in o)if(new RegExp("("+ k +")").test(format))
                format = format.replace(RegExp.$1,
                    RegExp.$1.length==1? o[k] :
                        ("00"+ o[k]).substr((""+ o[k]).length));
            return format;
        }
    });
</script>
</body>
</html>