package scheduleApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class scheduleAppServer {// ڵ
	private static scheduleAppServer instance = new scheduleAppServer();

	public static scheduleAppServer getInstance() {
		return instance;
	}

	public scheduleAppServer() {

	}

	String jdbc_url = "jdbc:mysql://localhost:3306/we_meet?characterEncoding=euckr&useUnicode=true&mysqlEncoding=euckr&useSSL=false&serverTimezone=Asia/Seoul";
	String dbId = "root"; // MySQL
	String dbPw = "18lsy0322!"; // й ȣ
	Connection conn = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;
	Statement stmt = null;

	ResultSet rs = null;
	String sql = "";
	String sql2 = "";
	String sql3 = ""; // 추가
	String returns = "";
	String returns2 = "";
	String returns3 = "";
	StringBuilder sb = new StringBuilder();

	public String joindb(String id, String pwd, String name) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			if (id.equals("")) {
				return "emptyid";
			} else if (pwd.equals("")) {
				return "emptypw";
			} else if (name.equals("")) {
				return "emptyname";
			} else {
				sql = "select id from user where id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					if (rs.getString("id").equals(id)) { // ̹ ̵ ִ
						returns = "id";
					}
				} else { // Է ̵

					sql2 = "insert into user values(?,?,?,?)";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, id);
					pstmt2.setString(2, pwd);
					pstmt2.setString(3, name);
					pstmt2.setString(4, "지역");

					pstmt2.executeUpdate();

					stmt = conn.createStatement();
					sql3 = sb.append("create table calendar").append(id).append("( id char(15) NOT NULL, ")
							.append("date date NOT NULL, ").append("schedule varchar(80), ").append("memo text, ")
							.append("foreign key(id) references user(id), ").append("primary key(id, date, schedule));")
							.toString();

					stmt.execute(sql3);

					returns = "ok";

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String logindb(String id, String pwd) {
		try {
			System.out.println("DB 접속 전");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			System.out.println("DB 접속 후");
			sql = "select id,pw from user where id=? and pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("id").equals(id) && rs.getString("pw").equals(pwd)) {
					returns2 = "true";// 로그인 가능
				} else {
					returns2 = "false"; // 로그인 실패
				}
			} else {
				returns2 = "noId"; // 아이디 또는 비밀번호 존재 X
			}

		} catch (Exception e) {
			returns2 = "NO!";
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		System.out.println(returns2);
		return returns2;
	}

	public String calendardb(String id, String date, String schedule, String memo) {
		String user_calendar = "calendar" + id;
		String rowdb = "";
		returns3 = "";

		try {
			sb = new StringBuilder();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = sb.append("select date, schedule, memo from ").append(user_calendar).append(" where date = ?")
					.toString();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			System.out.println(rs);
			while (rs.next()) {
				rowdb = rowdb + rs.getString("date") + "," + rs.getString("schedule") + "," + rs.getString("memo")
						+ ","; // date, schedule, memo
			}
			System.out.println(rowdb);
			returns3 = rowdb;

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns3;
	}

	public String addCalendar(String id, String date, String schedule, String memo) {
		String user_calendar = "calendar" + id;

		returns3 = "";

		try {
			sb = new StringBuilder();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = sb.append("select date, schedule from ").append(user_calendar)
					.append(" where date = ? and schedule = ? ").toString();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setString(2, schedule);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			System.out.println(rs);
			if (rs.next()) {
				returns3 = "false";

			} else {
				sb = new StringBuilder();
				sql2 = sb.append("insert into ").append(user_calendar).append(" values(?,?,?,?) ").toString();
				System.out.println(sql2 + "\n");
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, date);
				pstmt2.setString(3, schedule);
				pstmt2.setString(4, memo);
				System.out.println(pstmt2);

				pstmt2.executeUpdate();

				returns3 = "done";
			}

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
		}
		return returns3;
	}

	public String editCalendar(String id, String date, String schedule, String memo, String old) {
		String user_calendar = "calendar" + id;

		returns3 = "";

		try {
			sb = new StringBuilder();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = sb.append("select date, schedule from ").append(user_calendar)
					.append(" where date = ? and schedule = ? ").toString();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setString(2, schedule);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			System.out.println(rs);
			if (rs.next()) {
				returns3 = "false";

			} else {
				sb = new StringBuilder();
				sql2 = sb.append("update ").append(user_calendar)
						.append(" set schedule = ?, memo = ? where schedule =?").toString();
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, schedule);
				pstmt2.setString(2, memo);
				pstmt2.setString(3, old);
				System.out.println(pstmt2);

				pstmt2.executeUpdate();

				returns3 = "done";
			}

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
		}
		return returns3;
	}

	public String loadSchedule() {
		System.out.println("들어가지나");
		returns = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select * from schedule";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				returns += rs.getString("schedule_name") + "\t" + rs.getInt("schedule_id") + "\t";

			}

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		System.out.println(returns);
		return returns;

	}

	/*public String addSchedule(String sche_id, String id, String sche_name) { try
	  { Class.forName("com.mysql.jdbc.Driver"); conn =
	  DriverManager.getConnection(jdbc_url, dbId, dbPw); sql =
	  "select count * from schedule"; Statement stmt = conn.createStatement(); rs =
	  stmt.executeQuery(sql);
	  
	  sql2 = "insert into schedule values(?,?,?)"; pstmt2 =
	  conn.prepareStatement(sql2); pstmt2.setInt(1, rs.getInt(0) + 1);
	  pstmt2.setString(2, id); pstmt2.setString(3, sche_name);
	  
	  pstmt2.executeUpdate();
	  
	  returns = "ok";
	  
	 } catch (ClassNotFoundException e) { // TODO Auto-generated catch block
	  e.printStackTrace(); } catch (SQLException e) { // TODO Auto-generated catch
	  block e.printStackTrace(); }
	  
	 return returns; }*/

}