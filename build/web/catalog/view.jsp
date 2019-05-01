<%-- 
    Document   : view
    Created on : Apr 30, 2019, 9:38:51 PM
    Author     : curti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,store.data.*,store.business.*" %>  

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%  
String spageid=request.getParameter("page");  
int pageid=Integer.parseInt(spageid);  
int total=5;  
if(pageid==1){}  
else{  
    pageid=pageid-1;  
    pageid=pageid*total+1;  
}  
List<Product> list=EmpDao.getRecords(pageid,total);  
  
out.print("<h1>Page No: "+spageid+"</h1>");  
out.print("<table border='1' cellpadding='4' width='60%'>");  
out.print("<tr><th>Id</th><th>Name</th><th>Salary</th>");  
for(Product p:list){  
    out.print("<tr><td>" + p.getId() + "</td><td>" + p.getCode() + "</td><td>" + p.getDescription() + "</td></tr>" + p.getPrice() + "</td></td>");  
}  
out.print("</table>");  
%>  
<a href="view.jsp?page=1">1</a>  
<a href="view.jsp?page=2">2</a>  
<a href="view.jsp?page=3">3</a>  
    </body>
</html>
