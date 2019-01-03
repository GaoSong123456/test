<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>添加员工</title>
</head>
<body>
<form id="tt" enctype="multipart/form-data" action="/addUsertodb.do" method="post">
    <table border="1px" style="text-align: center;">
        <tr>
            <td>员工指纹编号</td>
            <td>
                <input id="code" name="userid" onblur="checkCode(this)">
                <%--<span id="codeMessage"></span>--%>
            </td>

        </tr>
        <tr>
            <td>姓名</td>
            <td><input name="username"></td>
        </tr>
        <tr>
            <td>部门</td>
            <td><input name="department"></td>

            </td>
        </tr>

        <tr>
            <td colspan="2"><input type="hidden" name="choose" value="1">
                <input type="submit" value="提交"> <input type="button"
                                                        value="取消">
            </td>
        </tr>
    </table>
</form>
</body>
<script type="text/javascript" src="/resource/js/jquery_2.1.4_baidu_min.js"></script>
<script type="text/javascript" src="/resource/js/jquery-form.js"></script>
<script type="text/javascript">
//    success:function() {
//        $("#tt").submit;
//    }
</script>
</html>
