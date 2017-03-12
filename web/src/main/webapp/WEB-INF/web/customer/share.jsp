<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${sessionScope.userInfo.cusName}</title>
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
        select{
            margin:0 auto;
            padding: 50px 10%;
            border: 1px solid #dddddd;
            border-spacing: 0;
            margin-bottom: 50px;
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
<h1>${sessionScope.userInfo.cusName}</h1>
<div>
    <h2>共享属性</h2>
    <div style="width: 300px;margin: 0 auto;">
        医院:
        <select id="hospital">
            <option value="bjdxgjyy">北京大学国际医院</option>
            <option value="fddxfshsyy">复旦大学附属华山医院</option>
            <option value="bjdxszyy">深圳市人民医院</option>
        </select>
    </div>
    <table>
        <tr>
            <th></th>
            <th>描述</th>
        </tr>
        <tr>
            <td><input type="checkbox" name="column" checked="checked" value="outpatient_info"></td>
            <td>门诊信息</td>
        </tr>
        <tr>
            <td><input type="checkbox" name="column" checked="checked" value="diagnose_info"></td>
            <td>诊断信息</td>
        </tr>
        <tr>
            <td><input type="checkbox" name="column" checked="checked" value="medicine_info"></td>
            <td>处方信息</td>
        </tr>
        <tr>
            <td><input type="checkbox" name="column" checked="checked" value="hospitalization_info"></td>
            <td>住院信息</td>
        </tr>
        <tr>
            <td><input type="checkbox" name="column" checked="checked" value="pic_info"></td>
            <td>图片</td>
        </tr>
        <tr>
            <td><input type="checkbox" name="column" checked="checked" value="doctor_message"></td>
            <td>医生描述</td>
        </tr>
        <tr>
            <td><input type="checkbox" name="column" checked="checked" value="operation_info"></td>
            <td>治疗信息</td>
        </tr>
        <tr>
            <td><input type="checkbox" name="column" checked="checked" value="treat_duration"></td>
            <td>治疗时间</td>
        </tr>
        <tr>
            <td><input type="checkbox" name="column" checked="checked" value="remark"></td>
            <td>备注</td>
        </tr>
        <tr>
            <td><input type="checkbox" name="column" checked="checked" value="create_time"></td>
            <td>创建时间</td>
        </tr>
    </table>
    <div style="width: 300px;margin: 0 auto;">
        <input type="button" value="确认" id="shareBtn">
    </div>
</div>
<script src="/js/jquery-2.2.3.min.js"></script>
<script>
    $(function() {
        var id = '${requestScope.id}';
        $('#shareBtn').click(function() {
            var dataItems = [];
            $("input[name='column']").each(function () {
                var item = $(this);
                if (item.is(':checked')) {
                    dataItems.push(item.val());
                }
            });
            var consumer = $('#hospital').val();
            $.ajax({
                url: '/customer/shareMedicalRecord',
                type: 'POST',
                data: {'id': id, 'consumer': consumer, 'dataItem': dataItems.join(',')},
                dataType: 'json',
                success: function (data) {
                    if (data.status == 1) {
                        alert('共享成功!' + data.message);
                        location.href = '/customer';
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