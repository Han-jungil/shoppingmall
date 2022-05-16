package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.Employee;
import vo.EmployeeList;

public class EmployeeDao {

//페이징
	//issue :  사진이름대신 사진이 나오게?
	//1.직원 목록
	public List<EmployeeList> selectEmpList(){
		List<EmployeeList> list = new ArrayList<>();
		
		//데이터베이스 연결
		Connection conn = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/shoppingmall","root","java1234"); //DB에 연결한다.
			//쿼리문
			String sql=" SELECT employeeNo, employeeName, employeeImageName"
					+ " FROM employee_list"; 
			stmt = conn.prepareStatement(sql); //쿼리문 실행
			
			System.out.println("직원 목록(EmployeeDao)stmt -> " + stmt); //디버깅
			
			rs = stmt.executeQuery(); //쿼리 실행결과 저장
			System.out.println("직원 목록(EmployeeDao)rs ->" + rs); //디버깅
			
			while(rs.next()) { //다음 행이 있으면 true반환해서 실행
				EmployeeList employeeList = new EmployeeList(); //Employee 객체 생성
				employeeList.setEmployeeNo(rs.getInt("employeeNo")); //직원번호 가져오기
				employeeList.setEmployeeName(rs.getString("employeeName")); //직원이름 가져오기
				employeeList.setEmployeeImageName(rs.getString("employeeImageName")); //직원 사진 가져오기
				list.add(employeeList); //list에 가져온 값 추가
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return list;
	}
	
	//issue : 주소번호 삽입대신 검색해서 삽입하게 
	//2.직원 삽입
	public int insertEmp(Employee employee) {
		int row = 0;
		
		//DB 연결
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/shoppingmall","root","java1234");
			String sql = "INSERT INTO employee(employee_pw, employee_sn, empAddress_id, employee_addressDetail, employee_name, employee_email, employee_phone, employee_gender,employee_imageName, employee_introduce, create_date, update_date)"
					+ " VALUES(PASSWORD(?), ?, ?, ?, ?, ?, ?, ?,?,?, NOW(), NOW())";
			stmt = conn.prepareStatement(sql); //쿼리 실행
			stmt.setString(1, employee.getEmployeePw());
					//System.out.println(employee.getEmployeePw());
			stmt.setString(2, employee.getEmployeeSn());
					//System.out.println(employee.getEmployeeSn());
			stmt.setInt(3, employee.getEmpAddressId());
			stmt.setString(4, employee.getEmployeeAddressDetail());
			stmt.setString(5, employee.getEmployeeName());
			stmt.setString(6, employee.getEmployeeEmail());
			stmt.setString(7, employee.getEmployeePhone());
			stmt.setString(8, employee.getEmployeeGender());
			stmt.setString(9, employee.getEmployeeImageName());
			stmt.setString(10, employee.getEmployeeIntroduce());
			row = stmt.executeUpdate(); //쿼리 실행 결과 저장
			System.out.println("직원 입력 stmt -> " + stmt); //디버깅
			
			if(row == 1) {
				System.out.println("1행 입력 성공");
			} else {
				System.out.println("입력 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	
	//3.직원 수정
	public int updateEmp(Employee employee) {
		int row = 0;
		
		//데이터베이스 연결
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/shoppingmall","root","java1234"); // DB연결
			String sql = "UPDATE employee SET employee_pw = PASSWORD(?), employee_email = ?, employee_phone = ?, employee_introduce = ?, update_date = NOW()"
					+ " WHERE employee_no =? ";
			stmt = conn.prepareStatement(sql); //쿼리문 실행
			// ? 값 대입
			stmt.setString(1, employee.getEmployeePw());
			stmt.setString(2, employee.getEmployeeEmail());
			stmt.setString(3, employee.getEmployeePhone());
			stmt.setString(4, employee.getEmployeeIntroduce());
			stmt.setInt(5, employee.getEmployeeNo());
			System.out.println("직원 수정 stmt -> " + stmt); //디버깅
			
			row = stmt.executeUpdate(); // 몇행입력했는지 리턴
			
			if(row == 1) {
				System.out.println("1행 수정 성공");
			} else {
				System.out.println("수정실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		
		return row;
	}
	
	//4.직원 삭제
	public int deleteEmp(int employeeNo, String employeePw) {
		int row = 0;
		
		//DB연결
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/shoppingmall","root","java1234");
			String sql = "DELETE FROM employee WHERE employee_no = ? AND employee_pw = PASSWORD(?)";
			stmt = conn.prepareStatement(sql); // 쿼리문 실행
			// ? 값 대입
			stmt.setInt(1, employeeNo);
			stmt.setString(2, employeePw);
			System.out.println("직원 삭제 stmt -> " + stmt); //디버깅
			
			row = stmt.executeUpdate();
			
			if(row == 1) {
				System.out.println("1행 삭제 성공");
			} else {
				System.out.println("삭제실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return row;
	}

	//5. 직원상세보기
	public Employee selectEmpOneByAdmin(int employeeNo) {
		Employee e = new Employee();
		//DB 연결
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/shoppingmall","root","java1234");
			//쿼리문
			String sql = "SELECT employeeNo,employeePw,employeeSn, employeeAddress, employeeAddressDetail, employeeName, employeeEmail"
					+ ",employeePhone, employeeGender, employeeImageName, employeeIntroduce, authority, createDate, updateDate"
					+ "FROM  employee_list_one";
			stmt = conn.prepareStatement(sql); //쿼리실행
			stmt.setInt(1, employeeNo);
			rs = stmt.executeQuery(); //쿼리결과저장
			if(rs.next()) {
				e.setEmployeeNo(rs.getInt("employeeNo"));
				e.setEmployeePw(rs.getString("employeePw"));
				e.setEmployeeSn(rs.getString("employeeSn"));
				e.setEmpAddressId(rs.getInt("empAddressId"));
				e.setEmployeeAddressDetail(rs.getString("employeeAddressDetail"));
				e.setEmployeeName(rs.getString("employeeName"));
				e.setEmployeeEmail(rs.getString("employeeEmail"));
				e.setEmployeePhone(rs.getString("employeePhone"));
				e.setEmployeeGender(rs.getString("employeeGender"));
				e.setEmployeeImageName(rs.getString("employeeImageName"));
				e.setAuthority(rs.getInt("authority"));
				e.setEmployeeIntroduce(rs.getString("employeeIntroduce"));
				e.setCreateDate(rs.getString("createDate"));
				e.setUpdateDate(rs.getString("updateDate"));			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return e;
	}
	
}
