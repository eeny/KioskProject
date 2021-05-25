import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

// 3페이지 - 매니저 - 주문내역확인, 판매완료 처리, 매출확인, 시스템종료
public class Mac2 extends JPanel implements ActionListener, MouseListener, Runnable {
	JPanel pnlNorth, pnlCenter, pnlSouth, subPnl, subPnl2, subPnl3, subPnl4;
	ImageIcon icon, refIcon;
	JLabel dateLbl, timeLbl, iconLbl;
	JTable tbl;
	DefaultTableModel model;
	int selectedRow = -1;// getSelectedRow() 해서 선택한 행이 없으면 -1
	JTextArea ta;
	JScrollPane tblSp, taSp;
	JButton[] btns = new JButton[3];// 판매완료, 총매출, 시스템종료
	JButton refreshBtn;// 새로고침 버튼
	Thread thread;// 현재 시간 구할 때 쓰임

	public Mac2() {
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(218, 41, 28));// 전체 패널 배경색 설정
		
		// North
		pnlNorth = new JPanel();
		pnlNorth.setBackground(new Color(218, 41, 28));
		
		subPnl = new JPanel(new GridLayout(2, 0));
		subPnl.setBackground(null);
		
		// 현재 날짜 dateLabel
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy년 MM월 dd일");// 날짜 형식 설정
		String date = format1.format(new Date());
		dateLbl = new JLabel(date);
		dateLbl.setFont(new Font("맑은 고딕", Font.BOLD, 35));
		dateLbl.setForeground(new Color(255, 199, 44));
		dateLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		
		// 시간 레이블이랑 새로고침 버튼 같이 붙이는 서브 패널4
		subPnl4 = new JPanel();
		subPnl4.setBackground(null);
		subPnl4.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		
		// 현재 시간 timeLabel
		timeLbl = new JLabel();
		timeLbl.setBackground(Color.cyan);
		timeLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 10));
		timeLbl.setFont(new Font("맑은 고딕", Font.BOLD, 35));
		timeLbl.setForeground(new Color(255, 199, 44));
		// implements Runnable을 해뒀기 때문에
		// 생성자가 실행될 때 자동으로 밑에 만들어둔 run() 메서드가 실행됨
		// 1초 마다 현재 시간이 갱신
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		// 새로고침 버튼
		refIcon = new ImageIcon("refresh.png");
		refreshBtn = new JButton(refIcon);
		refreshBtn.setPreferredSize(new Dimension(35, 35));
		refreshBtn.setBackground(null);
		refreshBtn.setBorderPainted(false);// 버튼 테두리 제거
		refreshBtn.setFocusPainted(false);// 버튼 클릭했을 때 생기는 포커스 테두리 제거
		refreshBtn.addActionListener(this);
		
		subPnl4.add(timeLbl);
		subPnl4.add(refreshBtn);
		
		subPnl.add(dateLbl);// 날짜
		subPnl.add(subPnl4);// 시간, 새로고침
		
		// 로고 아이콘
		icon = new ImageIcon("mediumLogo.png");
		iconLbl = new JLabel(icon);
		iconLbl.setBorder(BorderFactory.createEmptyBorder(10, 75, 10, 0));
		
		pnlNorth.add(subPnl);// 날짜, 시간, 새로고침
		pnlNorth.add(iconLbl);// 로고아이콘
		
		// Center
		pnlCenter = new JPanel();
		pnlCenter.setBackground(new Color(218, 41, 28));// 패널에 배경색 설정
		
		// 대기중인 주문 테이블 부분
		String[] header = { "주문번호", "메뉴", "가격", "수량" };
		String[][] contents = {};
		// JTable 셀 내용을 수정 못하게 하기
		model = new DefaultTableModel(contents, header) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		tbl = new JTable(model);
		tbl.addMouseListener(this);// 마우스 이벤트 달기
		
		// 테이블 색상 및 글자 설정(헤더랑 컨텐츠 부분 따로 색상 설정해야함)
		tbl.setOpaque(false);// 테이블 투명도 설정 (false는 투명 true는 불투명)
		tbl.getTableHeader().setOpaque(false);
		tbl.getTableHeader().setBackground(Color.WHITE);// 테이블의 헤더부분 배경색 설정
		tbl.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 20));
		tbl.setRowHeight(25);// 셀 높이 설정
		tbl.getColumnModel().getColumn(1).setPreferredWidth(250);// 특정 셀 너비 설정
		tbl.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		// 테이블 글자 가운데 정렬(출처 : http://blog.naver.com/PostView.nhn?blogId=rice3320&logNo=140059951103)
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = tbl.getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		
		tblSp = new JScrollPane(tbl);
		tblSp.setPreferredSize(new Dimension(520, 300));
		tblSp.getViewport().setBackground(Color.WHITE);// 테이블의 컨텐츠부분 배경색 설정	
		
		pnlCenter.add(tblSp);

		// South
		pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.setBackground(new Color(218, 41, 28));
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		// 전달사항메모 텍스트에리어가 있는 서브 패널2
		subPnl2 = new JPanel();
		subPnl2.setBackground(null);
	
		ta = new JTextArea("<전달 사항 메모>\n");
		ta.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		taSp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		taSp.setPreferredSize(new Dimension(520, 170));
	
		subPnl2.add(taSp);
		// 판매완료, 매출조회, 시스템종료 버튼이 있는 서브 패널3
		subPnl3 = new JPanel();
		subPnl3.setBackground(null);
		subPnl3.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		
		// 버튼 3개 만들기
		makeBtns();

		pnlSouth.add(subPnl2, "Center");
		pnlSouth.add(subPnl3, "South");

		this.add(pnlNorth, "North");
		this.add(pnlCenter, "Center");
		this.add(pnlSouth, "South");
		this.setVisible(true);
	}
	
	// 테이블 출력하는 메서드
	void printTbl() {
		model.setNumRows(0);// 테이블 초기화 (여러번 클릭해도 한번만 추가되게)
		
		// 오늘 판매대기 주문들만 뜨도록
		// 파일명: 현재 날짜 + waiting.txt
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		String date = format1.format(new Date());
		File f = new File(date + "waiting.txt");
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			String l = null;
			int i = 0;
			while ((l=br.readLine()) != null) {
				String[] str = l.split("/");
				model.addRow(str);
			}
		} catch (FileNotFoundException e) {// 파일이 없는 경우
			JOptionPane.showMessageDialog(this, "대기중인 주문이 없습니다.");
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
	
	// 판매완료, 매출조회, 시스템종료 버튼 3개 만들기
	void makeBtns() {
		String[] btnNames = {"판매완료", "매출조회", "시스템종료"};
		
		for (int i = 0; i < 3; i++) {
			btns[i] = new RoundedButton(btnNames[i]);
			btns[i].setPreferredSize(new Dimension(150, 50));
			btns[i].setBackground(new Color(255, 199, 44));
			btns[i].setFont(new Font("맑은 고딕", Font.BOLD, 20));
			btns[i].addActionListener(this);
			
			subPnl3.add(btns[i]);	
		}	
	}
	
	// 대기중인 모든 주문이 판매완료가 되는 경우에는 현재 날짜+waiting.txt 파일 삭제하기
	// (파일이 남아있으니까 자꾸 처음 인덱스가 2가 돼버려서 이렇게 처리함)
	void chkAndremoveFile() {
		// 삭제할 파일명 : 현재 날짜 + waiting.txt
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		String date = format1.format(new Date());
		File deleteFile = new File(date + "waiting.txt");
		// 파일에 내용이 있는지 확인하기
		FileReader fr = null;
		BufferedReader br = null;
		String l = null;
		try {
			fr = new FileReader(deleteFile);
			br = new BufferedReader(fr);
			l = br.readLine();// l에 한 줄 넣기(내용 없으면 그대로 null이다)
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null){
					br.close();
					// br에서 deleteFile을 사용중이라서 close() 한 후에 삭제가능하다!
					if (l == null) {
						// 파일이 존재하면 삭제하기
						if (deleteFile.exists()) {
							deleteFile.delete();// 파일 삭제 기능
							System.out.println(date + "waiting.txt" + "파일을 삭제했습니다.");
						} else {
							System.out.println(l);
						}
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btns[0]) {// 판매완료 (테이블 행 삭제 및 waiting 파일에서도 삭제)
			// 행 단위로 삭제하기
			if (selectedRow==-1 || tbl.getRowCount()==0) {// 행이 없는 경우
				JOptionPane.showMessageDialog(this, "선택된 행이 없습니다.");
			} else {
				model.removeRow(selectedRow);
				// 삭제 후 waiting 파일에도 똑같이 저장하기
				// 파일명: 현재 날짜 + waiting.txt
				SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
				String date = format1.format(new Date());
				// 파일 저장하는 FileUtil클래스 사용
				FileUtil.saveFile(date + "waiting.txt", tbl, "/");
				
				// 만약에 모든 주문이 판매완료가 되면 대기번호 파일 삭제하기
				// (파일이 남아있으면 자꾸 대기번호가 2가 돼서 이렇게 처리함)
				chkAndremoveFile();
			}
			
		} else if (e.getSource()==btns[1]) {// 매출조회
			new ChoiceSalesDate();// 새창으로 열기
			
		} else if (e.getSource()==btns[2]) {// 시스템 종료
			int result = JOptionPane.showConfirmDialog(this, "시스템을 종료하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (result == 0) {// JOptionPane.YES_OPTION 확인 누르면
				System.exit(0);// 시스템 종료
			}
		} else if (e.getSource()==refreshBtn) {// 새로고침 버튼 (테이블 불러오기)
			printTbl();// 테이블 출력
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		selectedRow = tbl.getSelectedRow();// 내가 클릭한(선택한) 줄 번호 가져오기
		System.out.println("selectedRow: " + selectedRow);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	// 현재 시간 구하기(출처 : https://blog.naver.com/choilee6/70165276447)
	public void run() {
		while (true) {
			Calendar cal = Calendar.getInstance();

			StringBuffer now = new StringBuffer();
			now.append(cal.get(Calendar.HOUR_OF_DAY));
			now.append(" : ");
			now.append(cal.get(Calendar.MINUTE));
			now.append(" : ");
			now.append(cal.get(Calendar.SECOND));
			// now.append(":");

			timeLbl.setText(now.toString());// 시간 레이블에 값 넣기

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
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

	public static void main(String[] args) {
		new Mac2();
	}
}
