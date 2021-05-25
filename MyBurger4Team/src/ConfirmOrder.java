import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
// 5페이지 - 고객 - 주문 내역 확인 창
public class ConfirmOrder extends JPanel implements ActionListener {
	JPanel ConfirmOrderPnl, pnlNorth, pnlCenter, pnlSouth, subPnl;
	File f, tblF;
	Image img, changeImg;
	ImageIcon icon;
	JLabel iconLbl, lbl, totalLbl, totalLbl2;
	JTable tbl;
	DefaultTableModel model;
	JButton prevBtn, paymentBtn;
	FileReader fr;
	BufferedReader br;
	FileWriter fw;
	PrintWriter pw;

	public ConfirmOrder() throws IOException {
		this.setLayout(new BorderLayout());
		// JFrame은 기본레이아웃이 Border,  JPanel은 기본이 Flow
		// ConfirmOrderPnl => 전체 패널들을 감싸고 있는 제일 큰 패널
		ConfirmOrderPnl = new JPanel(new BorderLayout());
		ConfirmOrderPnl.setBackground(Color.decode("#004516"));

		// North
		pnlNorth = new JPanel(new BorderLayout(0, 1));
		pnlNorth.setBackground(null);// 제일 큰 패널에서 배경색을 줬으므로 내부 패널은 배경색 제거

		// 이미지 파일을 파일 객체에 담아서 이미지 객체로 만들기
		f = new File("bigLogo.png");
		img = ImageIO.read(f);
		// 이미지파일 크기 줄이기
		changeImg = img.getScaledInstance(350, 250, Image.SCALE_SMOOTH);
		icon = new ImageIcon(changeImg);

		iconLbl = new JLabel(icon);
		lbl = new JLabel("주문내역을 확인하세요");
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		lbl.setForeground(Color.WHITE);
		lbl.setHorizontalAlignment(JLabel.CENTER);// 레이블 가운데 정렬
		pnlNorth.add(iconLbl);
		pnlNorth.add(lbl, "South");

		// Center
		pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBackground(null);
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
		
		String[] header = {"메뉴", "가격", "수량"};
		String[][] contents = {};
		// JTable 셀 내용을 수정 못하게 하기
		model = new DefaultTableModel(contents, header) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		tbl = new JTable(model);
		// 테이블 색상 및 글자 설정(헤더랑 컨텐츠 부분 따로 색상 설정해야함)
		tbl.setOpaque(false);// 테이블 투명도 설정 (false는 투명 true는 불투명)
		tbl.getTableHeader().setOpaque(false);
		tbl.getTableHeader().setBackground(Color.WHITE);// 테이블의 헤더부분 배경색 설정
		tbl.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 20));
		tbl.setRowHeight(25);// 셀 높이 설정
		tbl.getColumnModel().getColumn(0).setPreferredWidth(250);// 특정 셀 너비 설정
		tbl.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		JScrollPane sp = new JScrollPane(tbl);
		sp.getViewport().setBackground(Color.WHITE);// 테이블의 컨텐츠부분 배경색 설정
		// 테이블 글자 가운데 정렬(출처 : http://blog.naver.com/PostView.nhn?blogId=rice3320&logNo=140059951103)
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = tbl.getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		
		printTbl();// 테이블 출력
		
		subPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		subPnl.setBackground(null);
		totalLbl = new JLabel("총 결제 금액 ");
		totalLbl.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		totalLbl.setForeground(Color.WHITE);
		totalLbl2 = new JLabel("????");// 금액 숫자 들어가는 부분
		totalLbl2.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		totalLbl2.setForeground(Color.WHITE);
		// 총 결제 금액 출력
		totalLbl2.setText(printTotalLbl2());
				
		subPnl.add(totalLbl);
		subPnl.add(totalLbl2);
		
		pnlCenter.add(sp, "Center");
		pnlCenter.add(subPnl, "South");

		// South
		pnlSouth = new JPanel();
		pnlSouth.setBackground(null);
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
		prevBtn = new RoundedButton("이전");// 둥근 테두리 만들기 내부 클래스 사용함
		prevBtn.addActionListener(this);
		prevBtn.setPreferredSize(new Dimension(150, 50));
		
		prevBtn.setBackground(new Color(255, 199, 44));
		prevBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));

		// 공간 띄우는 용 버튼(실제로 안보임, 패널 새로 안만들고 공간 주려고 만듦)
		JButton emptyBtn = new JButton();
		emptyBtn.setPreferredSize(new Dimension(215, 50));
		emptyBtn.setContentAreaFilled(false);// 버튼 배경색 제거
		emptyBtn.setBorderPainted(false);// 버튼 테두리 제거
		emptyBtn.setEnabled(false);// 버튼 클릭 못함

		paymentBtn = new RoundedButton("주문결제");
		paymentBtn.addActionListener(this);
		paymentBtn.setPreferredSize(new Dimension(150, 50));
		paymentBtn.setBackground(new Color(255,199,44));
		paymentBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		pnlSouth.add(prevBtn);
		pnlSouth.add(emptyBtn);// 공간 띄우려고 중간에 넣었다!
		pnlSouth.add(paymentBtn);

		ConfirmOrderPnl.add(pnlNorth, "North");
		ConfirmOrderPnl.add(pnlCenter, "Center");
		ConfirmOrderPnl.add(pnlSouth, "South");

		this.add(ConfirmOrderPnl);
		this.setVisible(true);
	}
	// 테이블 출력하는 메서드
	void printTbl() {
		tblF = new File("menu_price.txt");
		try {
			fr = new FileReader(tblF);
			br = new BufferedReader(fr);
			String l = null;
			while ((l=br.readLine()) != null) {
				// A이벤트메뉴/8000/1
				String[] str = l.split("/");
				model.addRow(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null){
					br.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	// 총 결제 금액 출력하는 메서드
	String printTotalLbl2() {
		tblF = new File("menu_price.txt");
		int total = 0;// 총 결제 금액
		int price = 0;// 가격
		int cnt = 0;// 수량
		DecimalFormat formatter = new DecimalFormat("###,###");// 금액 모양
		try {
			fr = new FileReader(tblF);
			br = new BufferedReader(fr);
			String l = null;
			while ((l=br.readLine()) != null) {
				// A이벤트메뉴/8000/1
				String[] str = l.split("/");
				price = Integer.parseInt(str[1]);
				cnt = Integer.parseInt(str[2]);				
				total += (price * cnt);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return formatter.format(total);
	}
	
	// 지금 대기중인 주문 파일(waiting.txt)과 오늘 매출로 저장될 파일(sales.txt) 생성
	void makeOrderFile(String fileName) {
		// 주문번호 포함된 파일 생성하기
		int idx = 1;// 주문번호
		// 현재 날짜 구해서 원하는 모양으로 출력하기
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		String date = format1.format(new Date());		
		// 파일명1: 현재 날짜 + waiting.txt
		// 파일명2: 현재 날짜 + sales.txt
		File f = new File(date + fileName);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			String l = null;
			
			while ((l=br.readLine()) != null) {// 파일이 존재하는 경우
				idx = Integer.parseInt(l.split("/")[0]);
			}
			
			idx++;// 주문번호 증가
			
		} catch (FileNotFoundException e2) {// 파일이 없는 경우
			System.out.println(fileName + " 파일을 생성합니다.");
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if(br != null){
					br.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		// menu_price.txt에서 주문번호(idx)를 추가한 내용이 새파일에 저장함
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter(f, true);
			pw = new PrintWriter(fw);
			for (int i = 0; i < tbl.getRowCount(); i++) {// 행 개수 만큼 반복
				pw.print(idx + "/");// idx를 먼저 추가				
				for (int j = 0; j < tbl.getColumnCount(); j++) {// 열 개수 만큼 반복
					pw.print(tbl.getValueAt(i, j));
					if (j < (tbl.getColumnCount() - 1)) {
						pw.print("/");
					}
				}
				pw.println();
			}
			
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==prevBtn) {// 이전 버튼
			this.remove(ConfirmOrderPnl);// 현재 패널 삭제
			this.repaint();
			this.revalidate();
			Burger4Team b4 = new Burger4Team();
			this.add(b4);// 이전 패널 추가
			this.repaint();
			this.revalidate();
			
		} else if (e.getSource()==paymentBtn) {// 주문결제 버튼
			// 대기중인 주문 파일 & 오늘 매출 파일 2개 생성
			// waiting.txt은 판매완료되면 파일내용이 삭제되는 파일
			// sales.txt은 내용이 변경되지 않는 파일
			makeOrderFile("waiting.txt");
			makeOrderFile("sales.txt");
			
			// 다음 페이지로 넘기기
			this.remove(ConfirmOrderPnl);
			this.repaint();
			this.revalidate();
			try {
				InsertCard ic = new InsertCard();
				this.add(ic);
				this.repaint();
				this.revalidate();	
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
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

	public static void main(String[] args) throws IOException {
		new ConfirmOrder();
	}
}
