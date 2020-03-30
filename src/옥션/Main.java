package 옥션;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends JFrame implements ActionListener {

	JPanel panelA, panelB, panelC, hoxy;
	JLabel idLb, nameLb, productLb, amountLb, costLb, totalLb, buyLb;
	JTextField idTf, nameTf, productTf, amountTf, costTf, totalTf, buyTf;
	JButton buyBt, totalBt;

	marketDTO dto = new marketDTO();
	marketDAO dao = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		new Main();
	}

	Main() {

		super("장바구니");
		this.setLayout(new BorderLayout());
		panelA();
		PanelB();
		PanelC();

		this.setBackground(Color.BLACK);
		this.setBounds(10, 10, 450, 250);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);

		totalBt.addActionListener(this);
		buyBt.addActionListener(this);
	}

	private void panelA() {
		panelA = new JPanel();

		idLb = new JLabel("I D      ");
		panelA.add(idLb);
		nameLb = new JLabel("이 름      ");
		panelA.add(nameLb);
		productLb = new JLabel("상 품 명      ");
		panelA.add(productLb);
		amountLb = new JLabel("수 량      ");
		panelA.add(amountLb);
		costLb = new JLabel("가 격      ");
		panelA.add(costLb);
		totalBt = new JButton("총   금 액   ");
		panelA.add(totalBt);

		this.add(panelA, "North");

	}

	private void PanelB() {
		panelB = new JPanel();
//		panelB.setLayout(new BorderLayout());

		idTf = new JTextField(5);
		panelB.add(idTf);
		nameTf = new JTextField(5);
		panelB.add(nameTf);
		productTf = new JTextField(5);
		panelB.add(productTf);
		amountTf = new JTextField(5);
		panelB.add(amountTf);
		costTf = new JTextField(5);
		panelB.add(costTf);

//		totalTf = new JTextField(5);
//		panelB.add(totalTf);

		this.add(panelB, "Center");

//		buyBt.addActionListener(this);
	}

	private void PanelC() {
		panelC = new JPanel();
		buyLb = new JLabel("결제 금액");
		panelC.add(buyLb);
		buyTf = new JTextField(8); // ------------------
		panelC.add(buyTf);
		buyBt = new JButton("주문하기");
		panelC.add(buyBt);
		this.add(panelC, "South");

//		buyBt.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String id;
		String name;
		String product;
		String amount;
		String cost;

		dao = new marketDAO();
		Object ob = e.getSource();

		// ----------------------------------------
		if (ob.equals(totalBt)) {
			amount = amountTf.getText();
			cost = costTf.getText();
			int q = Integer.valueOf(amount);
			int w = Integer.valueOf(cost);
			int qw = q * w;
			String qwe = String.valueOf(qw);
			buyTf.setText(qwe);
		}
		// ----------------------------------------

		if (ob.equals(buyBt)) {
			id = idTf.getText();
			name = nameTf.getText();
			product = productTf.getText();
			amount = amountTf.getText();
			cost = costTf.getText();
//			String total = totalTf.getText();

			// ======총합 구하기===========
			int a = Integer.valueOf(amount);
			int b = Integer.valueOf(cost);
			int c = a * b;
			System.out.println(c + " 이거 뭐 나오니");
			String total = String.valueOf(c);
			// ========================

			dto.setId(id);
			dto.setName(name);
			dto.setProduct(product);
			dto.setAmount(amount);
			dto.setCost(cost);
//			dto.setTotal(total);

			dao.insert(dto);

			idTf.setText("");
			nameTf.setText("");
			productTf.setText("");
			amountTf.setText("");
			costTf.setText("");
			buyTf.setText("");

		}

	}

}
