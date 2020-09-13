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
	String date = request.getParameter("date");
	String schedule = request.getParameter("schedule");
	String memo = request.getParameter("memo");
	String old = request.getParameter("old");
	String sche_name = request.getParameter("sche_name");
	String sche_id = request.getParameter("sche_id");
	String location = request.getParameter("location");
	String sche_date = request.getParameter("sche_date");
	String old_sche_name = request.getParameter("old_sche_name");
	String new_sche_name = request.getParameter("new_sche_name");
	String latitude = request.getParameter("latitude");
	String longitude = request.getParameter("longitude");
	String sign = request.getParameter("sign");
	String total_mem;

	//싱글톤 방식으로 자바 클래스를 불러옵니다.
	scheduleAppServer connectDB = scheduleAppServer.getInstance();
	if (type.equals("login")) {
		String returns = connectDB.logindb(id, pwd);
		out.print(returns);
	} else if (type.equals("join")) {
		String returns = connectDB.joindb(id, pwd, name, latitude, longitude);
		out.print(returns);
	} else if (type.equals("calendar_main")) {
		String returns = connectDB.calendardb(id, date, schedule, memo);
		out.print(returns);
	} else if (type.equals("calendar_add")) {
		String returns = connectDB.addCalendar(id, date, schedule, memo);
		out.print(returns);
	} else if (type.equals("calendar_edit")) {
		String returns = connectDB.editCalendar(id, date, schedule, memo, old);
		out.print(returns);
	} else if (type.equals("loadAllSche")) {
		String returns = connectDB.loadAllSchedule();
		out.print(returns);
	} else if (type.equals("loadSche")) {
		String returns = connectDB.loadSchedule(sche_id);
		out.print(returns);
	} else if (type.equals("setDate")) {
		String returns = connectDB.setDate(sche_id, sche_date);
		out.print(returns);
	} else if (type.equals("setLocation")) {
		String returns = connectDB.setLocation(sche_id, location);
		out.print(returns);
	} else if (type.equals("modiSche")) {
		String returns = connectDB.modiScheduleName(sche_id, new_sche_name);
		out.print(returns);
	} else if (type.equals("delSche")) {
		String returns = connectDB.deleteSchedule(sche_id);
		out.print(returns);
	} else if (type.equals("addSche")) {
		String returns = connectDB.addSchedule(id, sche_name);
		out.print(returns);
	} else if (type.equals("joinAddress")){%>
	   
    <jsp:forward page="address.jsp"/>
<%
	} else if (type.equals("addVote")) {
		total_mem = "4";
		String returns = connectDB.addVote(sche_id, sche_name, total_mem);
		out.print(returns);
	} else if (type.equals("modiVote")) {
		String returns = connectDB.modiVoteName(sche_id, sche_name);
		out.print(returns);
	} else if (type.equals("loadDateVote")) {
		String returns = connectDB.loadDateVote();
		out.print(returns);
	} else if (type.equals("setVoteDate")) {
		String returns = connectDB.setVoteDate(sche_id, date);
		out.print(returns);
	} else if (type.equals("loadLocationVote")) {
		String returns = connectDB.loadLocationVote();
		out.print(returns);
	} else if (type.equals("setVoteLocation")) {
		String returns = connectDB.setVoteLocation(sche_id, location);
		out.print(returns);
	} else if (type.equals("voteDate")) {
		String returns = connectDB.voteDate(sche_id, sign);
		out.print(returns);
	} else if (type.equals("voteLocation")) {
		String returns = connectDB.voteLocation(sche_id, sign);
		out.print(returns);
	} else if (type.equals("initVoteLocation")) {
		String returns = connectDB.initVoteLocation(sche_id);
		out.print(returns);
	} else if (type.equals("initVoteDate")) {
		String returns = connectDB.initVoteDate(sche_id);
		out.print(returns);
	}
%>