package 옥션;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class marketDAO {

	private Connection conn;
	private Statement stmt; // 텍스트 SQL 호출
	private ResultSet rs; // 튜플을 순차적으로 가리킨다.
	private PreparedStatement ppsm;

	private static marketDAO DAOobject;

	marketDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 클래스 불러오라.
			System.out.println("클래스 로드 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("클래스 로드 실패");
		}

	}

	
	public static marketDAO getInstance() { // 싱글톤
		if (DAOobject == null) {
			DAOobject = new marketDAO();
		}
		return DAOobject;
	}

	
	public boolean connect() { // oracle DB에 접속하기 위한 자원
		boolean cFlag = false;
		try {
			conn = DriverManager.getConnection("" + "jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			cFlag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cFlag;
	}

	
	public void insert(marketDTO dto) {
		if (connect()) {
			try {
				System.out.println("뭘 쳐다보고 있는 거야. 어서 DB로 돌아가서 select문이나 쳐.");
				String sql = "insert into market2 values(market2_no.nextval, ?, ?, ?, ?, ?)";	//여섯 번째 물음표
				ppsm = conn.prepareStatement(sql);
				ppsm.setString(1, dto.getId());
				ppsm.setString(2, dto.getName());
				ppsm.setString(3, dto.getProduct());
				ppsm.setString(4, dto.getAmount());
				ppsm.setString(5, dto.getCost());
				
				ppsm.executeUpdate();
				ppsm.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("insert 실패");
			System.exit(0); // << 종료??
		}
	}

	
	public void modify(marketDTO dto) {
		if(connect()) {
			System.out.println("수정 되는가?");
			String sql = "update market2 set id = ?, name = ?, product = ?, amount = ?, cost = ? where no = ?";
			try {
				ppsm = conn.prepareStatement(sql);
				ppsm.setString(1, dto.getId());
				ppsm.setString(2, dto.getName());
				ppsm.setString(3, dto.getProduct());
				ppsm.setString(4, dto.getAmount());
				ppsm.setString(5, dto.getCost());
				ppsm.setString(6, dto.getNo());
				
				ppsm.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
	public int delete(String a) {
		if (connect()) {
			try {
				System.out.println("삭제 되는가?");
				String sql = "delete from market2 where id = ?";
				ppsm = conn.prepareStatement(sql);
				ppsm.setString(1, a);
				int k = ppsm.executeUpdate();
				return k;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}


	public ArrayList<String[]> getList() {
		ArrayList<String[]> list = new ArrayList<>();
		String sql = "select * from market2";
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt != null) {
					rs = stmt.executeQuery(sql); // executeQuary는 객체 값 가져오는 것
					while(rs.next()) {
						marketDTO dto = new marketDTO();
						dto.setNo(String.valueOf(rs.getInt("no")));
						dto.setId(rs.getString("id"));
						dto.setName(rs.getString("name"));
						dto.setProduct(rs.getString("product"));
						dto.setAmount(rs.getString("amount"));
						dto.setCost(rs.getString("cost"));

//						System.out.println("이거 뭐라 나오는데?" + rs.getString("sno"));
						
						list.add(dto.saveDB());
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			System.out.println("getList 실패");
			System.exit(0);
		}
		return list;
	}
	

}
