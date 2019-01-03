<%--
  Created by IntelliJ IDEA.
  User: cheney
  Date: 2018/6/15
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>修改员工数据</title>
</head>
<body>
<form id="uu" action="/afterUpdate.do" enctype="multipart/form-data"  method="post">
    <table border="1px" style="text-align: center;">
        <tr>
            <td>指纹编号</td>
            <td><input id="code" readonly="readonly" name="userid" value="${User.userid}" ><span
                    id="codeMessage"></span></td>
        </tr>
        <tr>
            <td>姓名</td>
            <td><input name="username" readonly="readonly" value="${User.username}"></td>
        </tr>
        <tr>
            <td>部门</td>
            <td><input name="department" value="${User.department}"></td>
        </tr>

        </tr>
        <td colspan="2">
            <input type="submit" value="修改">
            <input type="button" value="取消" onclick="jump()">
        </td>
        </tr>
    </table>
</form>
</body>
<script type="text/javascript" src="/resource/js/jquery-form.js"></script>
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    function jump() {
        window.location.href="/selectAllUser.do";
    }

    function imagepp() {

        $("#uu").submit;
    }
</script>
</html>
