import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//매니저 - 총매출액 조회할 날짜 선택하는 창
public class ChoiceSalesDate extends JFrame implements ActionListener {
	JPanel pnlNorth, pnlCenter, pnlSouth, subPnl;
	ImageIcon icon;
	JLabel iconLbl, northLbl, centerLbl;
	JLabel[] ymdLbls = new JLabel[3];// 콤보박스 뒤에 붙는 년, 월, 일
	String[] year = {"2021", "2022", "2023", "2024", "2025"};
	String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	String[] day = new String[31];// 1~31	
	JComboBox yearCb;
	JComboBox monthCb;
	JComboBox dayCb;
	JButton btn;
		
	public ChoiceSalesDate() {
		// 이 창은 JFrame이라서 새창으로 뜬다
		//this.setDefaultCloseOperation(3);
		// 이 창이 꺼지더라도 프로그램은 계속 실행중이 돼야하므로 close 설정 안함!
		this.setSize(600, 400);
		this.setResizable(false);// 창 크기 고정
		this.setTitle("매출조회");
		
		// 윈도우창 아이콘 변경(새창으로 뜨므로 새로 추가)
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image toolImg = toolkit.getImage("windowLogo.png");
		this.setIconImage(toolImg);
		
		// North - 로고 & 날짜별 매출 조회 제목	
		pnlNorth = new JPanel();
		pnlNorth.setBackground(new Color(218, 41, 28));
		pnlNorth.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		
		icon = new ImageIcon("miniLogo.png");
		iconLbl  = new JLabel(icon);
		iconLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 120));
		northLbl = new JLabel("날짜별 매출 조회");
		northLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 130));
		northLbl.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		northLbl.setForeground(new Color(255, 199, 44));
		
		pnlNorth.add(iconLbl);		
		pnlNorth.add(northLbl);	
		
		// Center - 선택하세요 레이블 & 년월일 콤보박스
		pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBackground(new Color(218, 41, 28));
		
		centerLbl = new JLabel("매출을 조회할 날짜를 선택하세요");
		centerLbl.setHorizontalAlignment(JLabel.CENTER);
		centerLbl.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		centerLbl.setForeground(new Color(255, 199, 44));
		
		// 년월일 콤보박스 감싸는 서브 패널
		subPnl = new JPanel();
		subPnl.setBackground(null);
		subPnl.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
		
		// day에 값 넣기 1~31
		for (int i = 0; i < 31; i++) {
			day[i] = i+1 + "";
		}
		// 콤보박스 사이즈 및 글자 설정
		yearCb = new JComboBox(year);
		yearCb.setPreferredSize(new Dimension(100, 35));
		yearCb.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		monthCb = new JComboBox(month);
		monthCb.setPreferredSize(new Dimension(70, 35));
		monthCb.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		dayCb = new JComboBox(day);
		dayCb.setPreferredSize(new Dimension(70, 35));
		dayCb.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		// 콤보박스 뒤에 년 월 일 레이블 생성
		makeLbls();
		
		subPnl.add(yearCb);
		subPnl.add(ymdLbls[0]);
		subPnl.add(monthCb);
		subPnl.add(ymdLbls[1]);
		subPnl.add(dayCb);
		subPnl.add(ymdLbls[2]);
		
		pnlCenter.add(centerLbl, "Center");	
		pnlCenter.add(subPnl, "South");	
		
		// South - 조회 버튼
		pnlSouth = new JPanel();
		pnlSouth.setBackground(new Color(218, 41, 28));
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));// 상 좌 하 우 여백
		
		btn = new RoundedButton("조회");
		btn.setPreferredSize(new Dimension(300, 50));
		btn.setBackground(new Color(255, 199, 44));
		btn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btn.addActionListener(this);
		
		pnlSouth.add(btn);
		
		this.add(pnlNorth, "North");
		this.add(pnlCenter, "Center");
		this.add(pnlSouth, "South");
		this.setVisible(true);
	}

	// 콤보박스 뒤에 년 월 일 레이블 3개 생성하기
	void makeLbls() {
		String[] lblNames = {" 년 ", " 월 ", " 일"};
		for (int i = 0; i < lblNames.length; i++) {
			ymdLbls[i] = new JLabel(lblNames[i]);
			ymdLbls[i].setFont(new Font("맑은 고딕", Font.BOLD, 30));
			ymdLbls[i].setForeground(new Color(255, 199, 44));			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// 서브창(JDialog) 열기
		// ManagerTotalSales(ChoiceSalesDate csd, String title, boolean modal, String year, String month, String day)
		try {
			// 매출 파일이 있으면 서브창 열고 아니면 알림만 뜨게!
			String date = yearCb.getSelectedItem().toString() + monthCb.getSelectedItem().toString() + dayCb.getSelectedItem().toString();
			File f = new File(date + "sales.txt");
			
			if (f.exists()) {// 매출 파일이 존재하는 경우
				new ManagerTotalSales(this, "날짜별매출", true, yearCb.getSelectedItem().toString(), monthCb.getSelectedItem().toString(), dayCb.getSelectedItem().toString());
			} else {
				JOptionPane.showMessageDialog(this, "매출 내역이 없습니다.");
			}
					
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ChoiceSalesDate();
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
