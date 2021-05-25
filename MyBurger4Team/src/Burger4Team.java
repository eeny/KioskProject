import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

// 4페이지 - 고객 - 메뉴 선택 창
public class Burger4Team extends JPanel implements ActionListener, MouseListener {
	JPanel pnlMenu, pnlMenuNorth, pnlMenuCenter, pnlMenuSouth, pnlLogoNorth, pnlBtnNorth, pnlSouthSub,
			pnlMenuSelectCenter, pnlMenuSelectSouth, pnlBtnSouth;
	
	JPanel pnlEventCenter, pnlNewMenuCenter, pnlBurgerCenter, pnlSideCenter, pnlDrinkCenter;
	JLabel lbl_logoImg, lbl_logoText, lbl_selectMenu, lbl_amount, lbl_sum;
	JButton[] btnSelects = new JButton[5];// 메뉴 종류 고르는 버튼 5개
	JButton btnBack, btnOrder, btnCancel;
	JTextField tf_amount, tf_sum;
	ImageIcon logoIcon;
	
	JLabel[] lbl_lunchMenu, lbl_burgerMenu, lbl_setMenu, lbl_sideMenu, lbl_drinkMenu;
	ImageIcon[] lunchImg, burgerImg, setImg, sideImg, drinkImg;
	
	final int SIZE = 10;// 메뉴 레이블 개수 10개씩

	JTable tbl;
	DefaultTableModel model;
	JScrollPane sp;
	
	int selectedRow = -1; // getSelectedRow 메서드에서 선택한 행이 없으면 -1

	JLabel lbl; // 센터에 있는 메뉴Label
	// 1. 맥런치
	String[] lunchMenu = { "빅맥 베이컨 세트/5700/1", "빅맥 세트/5100/1", "맥스파이시 상하이 버거 세트/5100/1", "1955 버거 세트/6200/1",
			"더블 불고기 버거 세트/4600/1", "더블 필레 오 피쉬 세트/5200/1", "베이컨 토마토 디럭스 세트/6000/1", "맥치킨 모짜렐라 세트/5300/1",
			"쿼터파운더 치즈 세트/5700/1", "슈슈버거 세트/5500/1", };
	String[] lunchMenuImg = { "lunch1.png", "lunch2.png", "lunch3.png", "lunch4.png", "lunch5.png", "lunch6.png",
			"lunch7.png", "lunch8.png", "lunch9.png", "lunch10.png" };

	// 2. 버거
	String[] burgerMenu = { "빅맥 베이컨/5200/1", "빅맥/4600/1", "필레 오 피쉬/3500/1", "더블 필레오피쉬/5000/1", "1955 버거/5700/1",
			"맥스파이시 상하이 버거/4600/1", "맥치킨/3300/1", "맥치킨 모짜렐라/4800/1", "더블 불고기 버거/4400/1", "에그 불고기 버거/2500/1" };
	String[] burgerMenuImg = { "burger1.png", "burger2.png", "burger3.png", "burger4.png", "burger5.png", "burger6.png",
			"burger7.png", "burger8.png", "burger9.png", "burger10.png" };

	// 3. 버거세트
	String[] setMenu = { "빅맥 베이컨 세트/6500/1", "빅맥 세트/5900/1", "필레 오 피쉬 세트/4500/1", "더블 필레오피쉬 세트/6000/1",
			"1955 버거 세트/7200/1", "맥스파이시 상하이 버거 세트/5900/1", "맥치킨 모짜렐라 세트/6200/1", "맥치킨 세트/4500/1", "더블 불고기 버거 세트/5500/1",
			"에그 불고기 버거 세트/4600/1" };
	String[] setMenuImg = { "set1.png", "set2.png", "set3.png", "set4.png", "set5.png", "set6.png", "set7.png",
			"set8.png", "set9.png", "set10.png", };

	// 4. 사이드
	String[] sideMenu = { "웨지 후라이/2900/1", "맥윙 콤보/4300/1", "맥윙/3400/1", "상하이 치킨 스낵랩/2900/1", "골든 모짜렐라 치즈스틱/2900/1",
			"후렌치 후라이/2400/1", "맥너겟/2500/1", "맥스파이시 치킨 텐더/3200/1", "해쉬 브라운/1700/1", "스트링 치즈/2300/1" };
	String[] sideMenuImg = { "side1.png", "side2.png", "side3.png", "side4.png", "side5.png", "side6.png", "side7.png",
			"side8.png", "side9.png", "side10.png" };
	// 5. 드링크
	String[] drinkMenu = { "제주 한라봉 칠러/3400/1", "애플망고 칠러/3400/1", "배 칠러/3400/1", "카페라떼/3400/1", "코카 콜라/2100/1",
			"아이스 카페라떼/3400/1", "스프라이트/2100/1", "아메리카노/2900/1", "디카페인 아메리카노/3000/1", "아이스 아메리카노/2900/1" };
	String[] drinkMenuImg = { "drink1.png", "drink2.png", "drink3.png", "drink4.png", "drink5.png", "drink6.png",
			"drink7.png", "drink8.png", "drink9.png", "drink10.png" };
	
	File f = new File("menu_price.txt");
	FileWriter fw = null;
	PrintWriter pw = null;
	FileReader fr = null;
	BufferedReader br = null;

	public Burger4Team() {
		this.setLayout(new BorderLayout());

		pnlMenu = new JPanel(new BorderLayout());
		pnlMenu.setBackground(new Color(218, 41, 28));

		// North 패널
		pnlMenuNorth = new JPanel(new BorderLayout()); // 북쪽 패널
		pnlMenuNorth.setBackground(new Color(218, 41, 28));

		pnlLogoNorth = new JPanel(new FlowLayout());
		pnlLogoNorth.setBackground(new Color(218, 41, 28));
		logoIcon = new ImageIcon("logo2.png");
		lbl_logoImg = new JLabel(logoIcon);
		lbl_logoImg.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 85));
		lbl_logoText = new JLabel("맥런치"); // 이부분 텍스트는 선택할때마다 바뀌게
		lbl_logoText.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 95));
		lbl_logoText.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		lbl_logoText.setForeground(new Color(255, 199, 44));
		btnBack = new RoundedButton("이전");// 둥근 테두리 버튼(내부 클래스로 만들어놓음)
		btnBack.setPreferredSize(new Dimension(150, 50));
		btnBack.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnBack.setForeground(new Color(218, 41, 28));
		btnBack.setBackground(Color.WHITE);
		btnBack.addActionListener(this);
		pnlLogoNorth.add(lbl_logoImg);
		pnlLogoNorth.add(lbl_logoText);
		pnlLogoNorth.add(btnBack);
		pnlMenuNorth.add(pnlLogoNorth, "North");

		pnlBtnNorth = new JPanel(new GridLayout(0, 5));
		pnlBtnNorth.setBackground(new Color(218, 41, 28));
		
		// 메뉴 종류 버튼 5개 생성 및 설정
		makeBtns();
		
		pnlMenuNorth.add(pnlBtnNorth, "Center");		

		// Center 패널
		pnlMenuCenter = new JPanel(new BorderLayout()); // 센터 패널
		pnlMenuCenter.setBackground(new Color(218, 41, 28));

		pnlEventCenter = new JPanel(new GridLayout(0, 1));

		lunchMenu(); // lbl_lunchMenu 10개 만들거 가져옴
		
		for (int i = 0; i < SIZE; i++) {
			pnlEventCenter.add(lbl_lunchMenu[i]);
		}
		
		JScrollPane sp = new JScrollPane(pnlEventCenter);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		pnlMenuCenter.add(sp);
		pnlMenuCenter.revalidate();
		
		// South 패널
		pnlMenuSouth = new JPanel(new BorderLayout()); // 남쪽 패널
		pnlMenuSouth.setBackground(new Color(218, 41, 28));

		pnlMenuSelectSouth = new JPanel(); // 테이블 들어갈 패널
		pnlMenuSelectSouth.setBackground(new Color(218, 41, 28));
		String[] header = { "메뉴", "가격", "수량" };
		String[][] contents = {};
		// JTable 셀 내용을 수정 못하게 하기
		// (출처 : https://nkdk.tistory.com/entry/자바-jtable-열-조정-안되고-내용-수정-못하게-하기)
		model = new DefaultTableModel(contents, header) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		tbl = new JTable(model);
		
		// 테이블 색상 및 글자 설정
		tbl.setOpaque(false);// setOpaque는 컴포넌트의 투명도를 설정하는 기능 (false는 투명하게)
		tbl.getTableHeader().setOpaque(false);
		tbl.getTableHeader().setBackground(Color.WHITE);// 테이블의 헤더부분 배경색 설정
		tbl.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 15));
		tbl.setRowHeight(20);// 셀 높이 설정
		tbl.getColumnModel().getColumn(0).setPreferredWidth(250);// 특정 셀 너비 설정
		tbl.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		JScrollPane sp1 = new JScrollPane(tbl);
		sp1.setPreferredSize(new Dimension(580, 130));
		sp1.getViewport().setBackground(Color.WHITE);// 테이블의 컨텐츠부분 배경색 설정
		// 테이블 글자 가운데 정렬
		// (출처 : http://blog.naver.com/PostView.nhn?blogId=rice3320&logNo=140059951103)
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = tbl.getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		
		pnlMenuSelectSouth.add(sp1);

		pnlBtnSouth = new JPanel();
		pnlBtnSouth.setBackground(new Color(218, 41, 28));
		// 금액 부분 레이블과 텍스트필드 감싸는 서브 패널
		JPanel subPnl = new JPanel();
		subPnl.setBackground(null);
		subPnl.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 45));
		lbl_sum = new JLabel("금액  ");
		lbl_sum.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		lbl_sum.setForeground(new Color(255, 199, 44));
		tf_sum = new JTextField("");
		tf_sum.setEditable(false);
		tf_sum.setPreferredSize(new Dimension(100, 50));
		tf_sum.setBorder(BorderFactory.createEmptyBorder());
		tf_sum.setBackground(new Color(218, 41, 28));
		tf_sum.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		tf_sum.setForeground(Color.WHITE);
		subPnl.add(lbl_sum);
		subPnl.add(tf_sum);
		// 주문하기 취소하기 버튼
		btnOrder = new RoundedButton("주문하기");
		btnOrder.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnOrder.setBackground(new Color(255, 199, 44));
		btnOrder.setPreferredSize(new Dimension(150, 50));
		btnOrder.addActionListener(this);
		
		btnCancel = new RoundedButton("취소하기");
		btnCancel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnCancel.setBackground(new Color(255, 199, 44));
		btnCancel.setPreferredSize(new Dimension(150, 50));
		btnCancel.addActionListener(this);
		
		pnlBtnSouth.add(subPnl);
		pnlBtnSouth.add(btnCancel);
		pnlBtnSouth.add(btnOrder);

		pnlMenuSouth.add(pnlMenuSelectSouth, "Center");
		pnlMenuSouth.add(pnlBtnSouth, "South");
		
		// 전체 pnlMenu 패널에 각 위치의 패널 붙임
		pnlMenu.add(pnlMenuNorth, "North");
		pnlMenu.add(pnlMenuCenter, "Center");
		pnlMenu.add(pnlMenuSouth, "South");
		
		this.add(pnlMenu);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Burger4Team();
	}
	
	// 메뉴 종류 버튼 5개 생성 및 설정하는 메서드
	public void makeBtns() {
		String[] btnNames = {"맥런치", "버거", "세트", "사이드", "음료"};
		String[] imgFiles = {"btnSelect_N1.png", "btnSelect_N2.png", "btnSelect_N3.png", "btnSelect_N4.png", "btnSelect_N5.png"};
		for (int i = 0; i < 5; i++) {
			btnSelects[i] = new JButton(btnNames[i], new ImageIcon(imgFiles[i]));
			btnSelects[i].setFont(new Font("맑은 고딕", Font.BOLD, 15));
			btnSelects[i].setVerticalTextPosition(JButton.BOTTOM);
			btnSelects[i].setHorizontalTextPosition(JButton.CENTER);
			btnSelects[i].setBackground(new Color(255, 199, 44));
			btnSelects[i].setBorder(BorderFactory.createLineBorder(new Color(218, 41, 28)));
			btnSelects[i].setFocusPainted(false);
			btnSelects[i].addActionListener(this);
			pnlBtnNorth.add(btnSelects[i]);
		}
	}

	// 각 메뉴 레이블 10개씩 생성하는 메서드들
	public void lunchMenu() {
		lbl_lunchMenu = new JLabel[SIZE];
		lunchImg = new ImageIcon[SIZE];

		for (int i = 0; i < SIZE; i++) {
			lbl_lunchMenu[i] = new JLabel(lunchMenu[i]);
			lunchImg[i] = new ImageIcon("lunch/" + lunchMenuImg[i]);
			lbl_lunchMenu[i].setIcon(lunchImg[i]);
			lbl_lunchMenu[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			lbl_lunchMenu[i].addMouseListener(this);
		}
	}

	public void burgerMenu() {
		lbl_burgerMenu = new JLabel[SIZE];
		burgerImg = new ImageIcon[SIZE];

		for (int i = 0; i < SIZE; i++) {
			lbl_burgerMenu[i] = new JLabel(burgerMenu[i]);
			burgerImg[i] = new ImageIcon("burger/" + burgerMenuImg[i]);
			lbl_burgerMenu[i].setIcon(burgerImg[i]);
			lbl_burgerMenu[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			lbl_burgerMenu[i].addMouseListener(this);
		}
	}

	public void setMenu() {
		lbl_setMenu = new JLabel[SIZE];
		setImg = new ImageIcon[SIZE];

		for (int i = 0; i < SIZE; i++) {
			lbl_setMenu[i] = new JLabel(setMenu[i]);
			setImg[i] = new ImageIcon("set/" + setMenuImg[i]);
			lbl_setMenu[i].setIcon(setImg[i]);
			lbl_setMenu[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			lbl_setMenu[i].addMouseListener(this);
		}
	}

	public void sideMenu() {
		lbl_sideMenu = new JLabel[SIZE];
		sideImg = new ImageIcon[SIZE];

		for (int i = 0; i < SIZE; i++) {
			lbl_sideMenu[i] = new JLabel(sideMenu[i]);
			sideImg[i] = new ImageIcon("side/" + sideMenuImg[i]);
			lbl_sideMenu[i].setIcon(sideImg[i]);
			lbl_sideMenu[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			lbl_sideMenu[i].addMouseListener(this);
		}
	}

	public void drinkMenu() {
		lbl_drinkMenu = new JLabel[SIZE];
		drinkImg = new ImageIcon[SIZE];

		for (int i = 0; i < SIZE; i++) {
			lbl_drinkMenu[i] = new JLabel(drinkMenu[i]);
			drinkImg[i] = new ImageIcon("drink/" + drinkMenuImg[i]);
			lbl_drinkMenu[i].setIcon(drinkImg[i]);
			lbl_drinkMenu[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			lbl_drinkMenu[i].addMouseListener(this);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBack) { // 이전화면
			Page3 p3 = new Page3();
			this.removeAll();
			this.repaint();
			this.revalidate();
			this.add(p3);
			this.repaint();
			this.revalidate();

		} else if (e.getSource() == btnSelects[0]) { // 맥런치 버튼
			pnlMenuCenter.removeAll();// 메뉴 버튼 누를 때마다 패널 리셋

			lbl_logoText.setText("맥런치");

			pnlMenuCenter.repaint();// 변경사항이 바로바로 나타나게
			pnlMenuCenter.revalidate();

			pnlEventCenter = new JPanel(new GridLayout(0, 1));
			pnlEventCenter.setBackground(Color.WHITE);

			lunchMenu(); // lbl_lunchMenu 10개 만들거 가져옴

			for (int i = 0; i < SIZE; i++) {
				pnlEventCenter.add(lbl_lunchMenu[i]);
			}
			
			JScrollPane sp = new JScrollPane(pnlEventCenter);
			// 세로 스크롤은 항상 보임, 가로 스크롤은 절대 안보이게
			sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			
			pnlMenuCenter.add(sp);
			pnlMenuCenter.revalidate();

		} else if (e.getSource() == btnSelects[1]) { // 버거 버튼
			pnlMenuCenter.removeAll();

			lbl_logoText.setText("버거   ");

			pnlMenuCenter.repaint();
			pnlMenuCenter.revalidate();

			pnlNewMenuCenter = new JPanel(new GridLayout(0, 1));
			pnlNewMenuCenter.setBackground(Color.WHITE);

			burgerMenu();

			for (int i = 0; i < SIZE; i++) {
				pnlNewMenuCenter.add(lbl_burgerMenu[i]);
			}
			
			JScrollPane sp = new JScrollPane(pnlNewMenuCenter);
			sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			
			pnlMenuCenter.add(sp);
			pnlMenuCenter.revalidate();

		} else if (e.getSource() == btnSelects[2]) { // 세트 버튼
			pnlMenuCenter.removeAll();

			lbl_logoText.setText("세트   ");

			pnlMenuCenter.repaint();
			pnlMenuCenter.revalidate();

			pnlBurgerCenter = new JPanel(new GridLayout(0, 1));
			pnlBurgerCenter.setBackground(Color.WHITE);
			
			setMenu();

			for (int i = 0; i < SIZE; i++) {
				pnlBurgerCenter.add(lbl_setMenu[i]);
			}
		
			JScrollPane sp = new JScrollPane(pnlBurgerCenter);
			sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
			pnlMenuCenter.add(sp);

		} else if (e.getSource() == btnSelects[3]) { // 사이드 버튼
			pnlMenuCenter.removeAll();

			lbl_logoText.setText("사이드");

			pnlMenuCenter.repaint();
			pnlMenuCenter.revalidate();

			pnlSideCenter = new JPanel(new GridLayout(0, 1));
			pnlSideCenter.setBackground(Color.WHITE);
			sideMenu();

			for (int i = 0; i < SIZE; i++) {
				pnlSideCenter.add(lbl_sideMenu[i]);
			}
			JScrollPane sp = new JScrollPane(pnlSideCenter);
			sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			pnlMenuCenter.add(sp);

		} else if (e.getSource() == btnSelects[4]) { // 음료 버튼
			pnlMenuCenter.removeAll();

			lbl_logoText.setText("음료   ");

			pnlMenuCenter.repaint();
			pnlMenuCenter.revalidate();

			pnlDrinkCenter = new JPanel(new GridLayout(0, 1));
			pnlDrinkCenter.setBackground(Color.WHITE);
			drinkMenu();

			for (int i = 0; i < SIZE; i++) {
				pnlDrinkCenter.add(lbl_drinkMenu[i]);
			}
			JScrollPane sp = new JScrollPane(pnlDrinkCenter);
			sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			pnlMenuCenter.add(sp);

		} else if (e.getSource() == btnOrder) { // 주문하기
			try {
				if (tbl.getRowCount()==0) {// 테이블에 행이 없다면
					JOptionPane.showMessageDialog(this, "메뉴를 선택해주세요");
				} else {
					// menu_price.txt 파일 생성
					// FileUtil은 파일을 생성하는 기능이 있는 클래스(선생님이 만들어주심!)
					FileUtil.saveFile("menu_price.txt", tbl, "/");
					
					// 주문내역확인창으로 넘기기
					ConfirmOrder co = new ConfirmOrder();
					this.removeAll();
					this.repaint();
					this.revalidate();
					this.add(co);
					this.repaint();
					this.revalidate();				
				}
				

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} else if (e.getSource() == btnCancel) { // 취소하기
			// 테이블에서 메뉴 삭제할때 사용
			selectedRow = tbl.getSelectedRow(); // 선택한 행 번호 가져오기
			System.out.println("삭제하는 행번호 : " + selectedRow); // 행번호
			
			if (selectedRow == -1 || tbl.getRowCount() == 0) { // 행 선택이 안됬거나 행이 없을때
				JOptionPane.showMessageDialog(this, "삭제할 메뉴를 선택해주세요");
			
			} else {
				// 총 합계 변경
				int total = Integer.parseInt(tf_sum.getText().replaceAll(",", ""));
				// total => tf_sum.getText()로 기존의 총 합계를 갖고오면 ,(콤마)가 있는 String형이다!
				//			replaceAll(",", "")로 콤마를 없애고 int형으로 바꿔야한다!
				int minusPrice = Integer.parseInt(tbl.getValueAt(selectedRow, 1)+"");
				// minusPrice => 선택한 행에서 금액 셀
				int minusCnt = Integer.parseInt(tbl.getValueAt(selectedRow, 2)+"");
				// minusCnt => 선택한 행에서 수량 셀
				int result = total - (minusPrice*minusCnt);
				
				DecimalFormat formatter = new DecimalFormat("###,###");// 금액 모양				
				tf_sum.setText(formatter.format(result));

				model.removeRow(tbl.getSelectedRow()); // 이건 테이블에서만 삭제
			}
		}
	}

	// 총 합계 구하는 메서드
	String printSum() {
		int sum = 0;
		DecimalFormat formatter = new DecimalFormat("###,###");// 금액 모양
		if (tbl.getRowCount() == 0) {// 테이블에 행이 없다면
			sum = 0;
		
		} else {// 행이 있다면
			for (int i = 0; i < tbl.getRowCount(); i++) {
				int price = Integer.parseInt(tbl.getValueAt(i, 1) + "");// 금액 열
				int cnt = Integer.parseInt(tbl.getValueAt(i, 2) + "");// 수량 열
				sum += (price * cnt);
			}
		}

		return formatter.format(sum);// String타입으로 총 합계 반환
	}

	@Override
	public void mouseClicked(MouseEvent e) {// 메뉴 레이블 클릭할 때 발생하는 이벤트
		JLabel lbl = (JLabel) e.getSource();// 내가 클릭한 그 레이블
		System.out.println(lbl.getText());
		
		boolean isExsit = false;// 동일한 메뉴가 테이블에 추가되어있는지 확인
		int rowNum = 0;// 동일한 행이 몇번째인지
		
		for (int i = 0; i < tbl.getRowCount(); i++) {// 테이블 행만큼 반복
			// 내가 메뉴 레이블에서 선택한 메뉴이름과 테이블에 메뉴이름이 같다면(이미 같은메뉴가 테이블에 있을때)
			if (lbl.getText().split("/")[0].equals(tbl.getValueAt(i, 0))) {
				isExsit = true;
				rowNum = i;
			}
		}
		if (isExsit) { // 같은 메뉴가 선택되었다면 그 행의 2열인 수량부분을 1증가 시켜준다
			int cnt = Integer.parseInt(tbl.getValueAt(rowNum, 2) + "");
			tbl.setValueAt(++cnt, rowNum, 2);
		} else { // 같은 메뉴가 선택되지 않았다면 그대로 테이블에 뿌려줌
			model.addRow(lbl.getText().split("/"));
		}

		// 총 합계 변경
		tf_sum.setText(printSum());
	}

	@Override
	public void mouseEntered(MouseEvent e) { // 마우스 커서가 들어올때 빨강 테두리 설정
		JLabel lbl = (JLabel) e.getSource();
		lbl.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
	}

	@Override
	public void mouseExited(MouseEvent e) { // 마우스 커서가 벗어날때 빨강 테두리 제거
		JLabel lbl = (JLabel) e.getSource();
		lbl.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	// 둥근 테두리 버튼 만들기 클래스(출처 : https://the-illusionist.me/42)
	public class RoundedButton extends JButton {
		public RoundedButton() {
			super();
			decorate();
		}

		public RoundedButton(String text) {
			super(text);
			decorate();
		}

		public RoundedButton(Action action) {
			super(action);
			decorate();
		}

		public RoundedButton(Icon icon) {
			super(icon);
			decorate();
		}

		public RoundedButton(String text, Icon icon) {
			super(text, icon);
			decorate();
		}

		protected void decorate() {
			setBorderPainted(false);
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			int width = getWidth();
			int height = getHeight();
			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (getModel().isArmed()) {
				graphics.setColor(getBackground().darker());
			} else if (getModel().isRollover()) {
				graphics.setColor(getBackground().brighter());
			} else {
				graphics.setColor(getBackground());
			}
			graphics.fillRoundRect(0, 0, width, height, 10, 10);
			FontMetrics fontMetrics = graphics.getFontMetrics();
			Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
			int textX = (width - stringBounds.width) / 2;
			int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
			graphics.setColor(getForeground());
			graphics.setFont(getFont());
			graphics.drawString(getText(), textX, textY);
			graphics.dispose();
			super.paintComponent(g);
		}
	}
}
