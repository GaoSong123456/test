<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>考勤</title>

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

        .check {
            width: 80%;
            margin: 50px auto 20px;
            font-size: 18px;
            font-weight: 500;
        }

        .checkStart, .checkEnd, .checkName {
            width: 160px;
            height: 30px;
            border-radius: 5px;
            margin: 0 16px;
            outline: none;
        }

        .checked {
            width: 60px;
            height: 30px;
            background: #A8D5EA;
            border: none;
            cursor: pointer;
            outline: none;
            color: #3498DB;
            border-radius: 5px;
            font-weight: 900;
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
    <h1>员工考勤系统</h1>
    <form action="/selectAll.do" method="post" class="check" id="f">
        开始时间:<input type="date" name="begindate" class="checkStart" value="${begin}">&nbsp;
        结束时间:<input type="date" name="enddate" class="checkEnd" value="${end}">&nbsp;
        姓名：<input type="text" name="username" class="checkName" value="${username}">&nbsp;
        <input type="hidden" name="now" value="1" id="pg">
        <input type="submit" value="查询" class="checked">

    </form>

    <form action="/selectAllUser.do" method="post" class="check" id="s">

        <input type="submit" value="员工管理" class="checked">
    </form>
    <form action="/goTimeset.do" method="post" class="check" id="u">

        <input type="submit" value="设置时间" class="checked">
    </form>



    <form action="/poi/excel/upload" method="post"  class="check" enctype="multipart/form-data">
        <input type="file" name="articleFile" class="required" id="articleFile">&nbsp;
      提示:导入的excel必须为*.xls(excel 97-2003)&nbsp;&nbsp;<button id="btn_import"  class="checked" onclick="return false"> 导入 </button> &nbsp;&nbsp;
    </form>

    <form action="/poi/excel/export"  >
        开始时间:<input type="date" name="begindate1" class="checkStart" value="${begin}">&nbsp;
        结束时间:<input type="date" name="enddate1" class="checkEnd" value="${end}">&nbsp;
        <input type="submit" value="导出" class="checked" />
    </form>

    <form action="" method="post" id="deleteids">
        <table border="1" width="900" align="center" cellspacing="0">
            <thead>
            <tr>
                <th>指纹数字</th>
                <th>姓名</th>
                <th>第一次打卡</th>
                <th>最后一次打卡</th>
                <th>考勤状态</th>
                <th>部门</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${Page_Info.list}" var="ut">
                <tr>
                    <td>${ut.userid}</td>
                    <td>${ut.user.username}</td>
                    <td><fmt:formatDate value="${ut.starttime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${ut.endtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td <c:if test="${ut.state!='正常打卡'}">style="color: red"</c:if>>${ut.state} </td>
                    <td>${ut.user.department}</td>
                </tr>
            </c:forEach>
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
<script>
    function pageNo(no) {
        $("#pg").val(no);
        $("#f").submit();
    }


 /*   function downLoad() {
        window.location.href = "/poi/excel/export";
    }*/

    $("#btn_import").click(function() {
        //避免重复点击,上传多次文件
        $("button").attr("disabled", "disabled");
        var formData = new FormData();
        formData.append("articleFile", $("#articleFile")[0].files[0]);
        $.ajax({
            url: '/poi/excel/upload',
            type: 'post',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data){
                    alert('上传成功');
                }else{
                    alert("上传失败");
                }

            }
        });
    });






</script>
</html>
