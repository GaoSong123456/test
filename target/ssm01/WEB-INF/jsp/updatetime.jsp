<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>修改上下班时间</title>
</head>
<body>
<form id="tt" enctype="multipart/form-data" action="/updateTimeset.do" method="post">
    <table border="1px" style="text-align: center;">
        <tr>
            <td>上班时间</td>
            <td><input name="cometime" value="${timeset.cometime}"></td>
        </tr>
        <tr>
            <td>下班时间</td>
            <td><input name="leavetime" value="${timeset.leavetime}"></td>

            </td>
        </tr>

        <tr>
            <td colspan="2"><input type="hidden" name="choose" value="1">
                <input type="submit" value="提交">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
