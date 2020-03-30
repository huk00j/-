package 옥션;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Main2 extends JFrame {
	
	String header[] = { "주문번호", "id", "이름", "상품명", "수량", "가격" };
	String contents[][] = { { "gildong", "길동", "사탕", "5", "400" } };
	
	JTabbedPane tabPane = new JTabbedPane();
	DefaultTableModel tableModel = new DefaultTableModel(null, header);	// gui 자체에서 수정

	JTable table = new JTable(tableModel);
	JScrollPane tableScroll = new JScrollPane(table);
	
	JPanel tabPanel = new JPanel();
	JPanel inputPanel = new JPanel();
	
	JTextField[] underField = new JTextField[5];

	JPopupMenu popup;	//오른쪽 마우스
	JMenuItem popSelect;
	
	marketDAO sqlDAO = marketDAO.getInstance();	 // 싱글톤// 참조할 때 클레스명도 가능?
	
	int minus = -1;
	
	JPanel bottom;
	JButton totalPrice;
	JTextField priceField;
	
	JPanel total = new JPanel();
	
//	ArrayList<String[]> startList = new ArrayList();	//굳이?
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main2();
	}

	
	Main2(){
		windows();
		Dimension size = new Dimension(600,400); // UI 크기
		createTab();
		createInput();
		
//		price();
		
		rightMenu();
		tableSetting();
		
		this.setLocation(300, 20); // 위치
		this.setSize(size);	// 사이즈 조정 x
		this.add(tabPane); // 탭 등록.
		this.setVisible(true); // 창 보이게 함.
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE); //종료하면 창 꺼짐.
		
	}

	
	public void windows() {
		ArrayList<String[]> startList = new ArrayList();
		startList = sqlDAO.getList();
		for(int i = 0 ; i < startList.size() ; i++) {
			tableModel.addRow(startList.get(i));
		}
	}
		
	
	private void createInput() {
		
		bottom = new JPanel();
		
		totalPrice = new JButton("결제 금액");
		priceField = new JTextField(7);
		bottom.add(totalPrice);
		bottom.add(priceField);
		this.add(bottom, "South");
		
//		priceField.setText("아아아아아아아아");
	//----------------------------------------
		
		
		totalPrice.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int q = underField.length;
				String in[] = new String[2];
				
				in[0] = underField[q-2].getText();
				in[1] = underField[q-1].getText();
				
				int a = Integer.valueOf(in[0]);
				int b = Integer.valueOf(in[1]);
				int c = a * b;
				String d = String.valueOf(c);
				
				priceField.setText(d);
				
			}
		});
		
//=================================================================		
		
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
		
		// BoxLayout : 창을 벗어나도 배치.
		
		for(int i = 0 ; i < underField.length ; i++) {		// 아래 TextField 4개 만듦
			inputPanel.add(underField[i] = new JTextField(7));
		}
		
		//-------------------------------------------------------------
		JButton buyBt = new JButton("구매");	// 전역 변수 일단 안 씀.
		inputPanel.add(buyBt);

		buyBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String in[] = new String[6];
				for(int i= 0 ; i < underField.length; i++) {
					in[i+1] = underField[i].getText();
					underField[i].setText("");
				}
				
				tableModel.addRow(in);
				saveDB(in);
				
				priceField.setText("");
			}
		});
		
		
		//--------------------------------------------------------------
		JButton modBt = new JButton("수정");
		inputPanel.add(modBt);
		modBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if( minus != -1) {
					String in[] = new String[5];
					for(int i = 0; i < underField.length ; i++) {
						in[i] = underField[i].getText();
						underField[i].setText("");
						
					}
					delTable(minus);	// 얘 없으면 for문 따라서 Jtable에 ++됨
					tableModel.insertRow(minus, in);
					
					for(int j = 0; j<in.length ; j++) {
						System.out.println(in[j] + " 뭐라고?"+ j);
					}
					modDB(in);
					minus = -1;
					
					priceField.setText("");
				}
				
			}
		});
		
		
		//--------------------------------------------------------------
		JButton delBt = new JButton("삭제");
		inputPanel.add(delBt);
		delBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				marketDTO dto = new marketDTO();
				if(table.getSelectedRow() != -1) {
//					for(int i = 0 ; i < table.getRowCount() ; i++) {
//						System.out.println("뭐지 "+ table.getValueAt(i, 0));
					System.out.println(table.getSelectedRow() + " 뭐가 나옵니까");
						String a = (String) table.getValueAt(table.getSelectedRow(), 0);
						int k = sqlDAO.delete(a);
						
						for(int i = 0 ; i < underField.length ; i++) {
							underField[i].setText("");
						}
						
						if(k == 1) {
							System.out.println("delete 성공");
						}else if(k == -1) {
							System.out.println("delete 실패");
						}
						
						priceField.setText("");
//					}
					delTable(table.getSelectedRow());	// 테이블에 있는 속성 값을 선택해라
				}else {
					return;
				}
				
			}
		});
		
	}
	
	

	public void modDB(String[] in) {
		marketDTO dto = new marketDTO();
		dto.setNo(in[0]);
		dto.setId(in[1]);
		dto.setName(in[2]);
		dto.setProduct(in[3]);
		dto.setAmount(in[4]);
		dto.setCost(in[5]);
		
		sqlDAO.modify(dto);
	}
	
	public void saveDB(String[] in) {	// 값을 한 번에 객체로 보내주려고 만듦. 	// DB에 넣어줌
		marketDTO dto = new marketDTO();
//		dto.setNo(in[0]);
		dto.setId(in[0]);
		dto.setName(in[1]);
		dto.setProduct(in[2]);
		dto.setAmount(in[3]);
		dto.setCost(in[4]);
		
		sqlDAO.insert(dto);	// id, name, product 등 배열에 들어있는 값의 속성들을 분별하기 위함.
		
	}
	
	
	public void delTable(int row) {
		tableModel.removeRow(row);
		
	}
	
	
	private void createTab() {	//맨 위에  탭 키 만드는 메소드.
		tabPanel.setLayout(new BorderLayout());	// 분할
		tabPanel.add(tableScroll, "Center");	// 스크롤 
		tabPanel.add(inputPanel, "South");		// 밑에 TextField.
		tabPane.add("구매자 리스트", tabPanel);	// 탭 이름
		
		inputPanel.setLayout(new BorderLayout());
		
		

		
	}
	
	
	private void rightMenu() {	// 오른쪽 마우스 기능.
		popup = new JPopupMenu();
		popSelect = new JMenuItem("선택");
		popSelect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				minus = table.getSelectedRow();		// 설명 필요
				for(int i = 0 ; i < underField.length ; i++) {
					underField[i].setText((String) table.getValueAt(minus, i));
				}
				
			}
		});
		popup.add(popSelect);
	}
	
	
	private void tableSetting() {
		table.setRowMargin(0);	// 셀 사이의 여백
		table.getColumnModel().setColumnMargin(0);	// 각 열의 크기(길이) 조절.
		table.getTableHeader().setReorderingAllowed(false);	// 테이블 열의 이동 방지.
		table.getTableHeader().setResizingAllowed(false);	// 테이블 열의 크기 고정.
		
		table.add(popup);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) { // 3 : 오른쪽 마우스
					int column = table.columnAtPoint(e.getPoint());
					int row = table.rowAtPoint(e.getPoint());
					table.changeSelection(row, column, false, false);
					popup.show(table, e.getX(), e.getY());
				}
				
			}
		
		});
		
	}
	
	
	
}









