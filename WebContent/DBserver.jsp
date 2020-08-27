<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="scheduleApp.scheduleAppServer"%>
<%@ page import="scheduleApp.Schedule"%>
<%@ page import="java.util.ArrayList"%>


<%
   request.setCharacterEncoding("UTF-8");
   String id = request.getParameter("id");
   String pwd = request.getParameter("pwd");
   String name = request.getParameter("name");
   String type = request.getParameter("type");

   //싱글톤 방식으로 자바 클래스를 불러옵니다.
      scheduleAppServer connectDB = scheduleAppServer.getInstance();
   if(type.equals("login")) {

      String returns = connectDB.logindb(id, pwd);
      out.print(returns);
   }
   
   else if(type.equals("join")) {
      String returns = connectDB.joindb(id, pwd, name);
      out.print(returns);
   }
   
   else if(type.equals("loadSche"))
   {
	  System.out.println("Load");
	  ArrayList<Schedule> list = connectDB.loadSchedule();
	  out.print(list);
	  
   }
  
%>