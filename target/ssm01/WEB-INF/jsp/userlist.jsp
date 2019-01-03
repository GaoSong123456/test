<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/10/9
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>员工管理</title>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }

        body {
            background: linear-gradient(#A8D5EA, #fff);
            background-repeat: no-repeat !important;
            text-align: center;
        }

        div {
            width: 1100px;
            height: 540px;
            margin: 50px auto;
            text-align: center;
        }

        h1 {
            width: 100%;
            background: #ffffff;
            color: #3498DB;
            text-align: center;
            line-height: 60px;
            letter-spacing: 6px;
            border-radius: 10px;
            box-shadow: 3px 3px 3px;
        }


        .nub span {
            margin: 0 10px;
            line-height: 45px;
        }
        table{
            margin: 0 auto;
        }

        table th {
            background: #AFE3F5;
        }

        table tr {
            height: 50px;
            line-height: 40px;
        }

        #bottom-text td a {
            margin-right: 35px;
            cursor: pointer;
        }

        td {
            text-align: center;
        }
    </style>
</head>
<body>



<div>
    <h1>员工管理</h1>
    <a href="/addUser.do">添加员工</a>
    <form action="/selectAllUser.do" method="post"  id="query">
        <table border="1" width="900" align="center" cellspacing="0">
            <thead>
            <tr>
                <th>指纹数字</th>
                <th>姓名</th>
                <th>部门</th>
                <th>删除</th>
                <%--<th>修改</th>--%>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${Page_Info.list}" var="ut">
                <tr>
                    <td>${ut.userid}</td>
                    <td>${ut.username}</td>
                    <td>${ut.department}</td>
                    <td><a href="/del.do?id=${ut.userid}">删除</a></td>
                    <%--<td><a href="/beforeupdate.do?id=${ut.userid}">修改</a></td>--%>
                </tr>
            </c:forEach>
            <input type="hidden" name="now" value="1" id="pg">
            <tr id="bottom-text">
                <td colspan="10"><a onclick="pageNo(1)">首页</a><a onclick="pageNo(${Page_Info.pageNum-1})">上一页</a><a
                        onclick="pageNo(${Page_Info.pageNum+1})">下一页</a><a onclick="pageNo(${Page_Info.pages})">尾页</a>&nbsp;&nbsp;<span>${Page_Info.pageNum}/${Page_Info.pages}页</span>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
<script type="text/javascript" src="/resource/js/jquery_2.1.4_baidu_min.js"></script>
<script type="text/javascript">

    function pageNo(no) {
//        alert(1)
        $("#pg").val(no);
        $("#query").submit();
    }
</script>

</html>
