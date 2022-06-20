<html>
<body>
<h2>Hello World!</h2>
</body>
</html>
String createTime = DateTimeUtil.getSysTime();
User user = (User)request.getSession().getAttribute("user");
String createBy = user.getName();


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">