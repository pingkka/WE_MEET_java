package scheduleApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class scheduleAppServer {//ڵ
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
	ResultSet rs = null;
	String sql = "";
	String sql2 = "";
	String returns = "";
	String returns2 = "";

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
		}
		return returns;
	}

	public String logindb(String id, String pwd) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
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
		return returns2;
	}

	public ArrayList<Schedule> loadSchedule() {
		System.out.println("들어가지나");
		ArrayList<Schedule> schedule_list = new ArrayList<Schedule>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select * from schedule";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Schedule schedule = new Schedule();

				System.out.println(schedule.getId());
				schedule.setSche_id(rs.getInt("schedule_id"));
				schedule.setSche_name(rs.getString("schedule_name"));
				schedule.setId(rs.getString("id"));
				schedule.setDate(rs.getDate("date"));
				schedule.setLocation(rs.getString("location"));
				schedule.setParticipants(rs.getString("participants"));

				schedule_list.add(schedule);

				System.out.println(schedule.toString());

			}

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {}
		}
		
		return schedule_list;

	}

	/*public String addSchedule(String sche_id, String id, String sche_name) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select count * from schedule";
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			sql2 = "insert into schedule values(?,?,?)";
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setInt(1, rs.getInt(0) + 1);
			pstmt2.setString(2, id);
			pstmt2.setString(3, sche_name);

			pstmt2.executeUpdate();

			returns = "ok";

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returns;
	}*/
}