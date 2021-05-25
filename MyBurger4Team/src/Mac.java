import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.EventObject;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

// 2페이지 - 매니저 - 로그인 (id: admin / pw: 1234)
public class Mac extends JPanel implements ActionListener {
	// 아이디랑 비밀번호는 상수로 선언
	final String ID = "admin";
	final String PW = "1234";
	
	JPanel pnlNorth, pnlCenter, subPnl1, subPnl2;
	File f;
	Image img, changeImg;
	ImageIcon loginIcon, logoIcon;
	JLabel loginLbl1, loginLbl2, logoLbl;
	JTextField idTf;
	JPasswordField pwPf;
	JButton loginBtn;

	public Mac() throws IOException {
		this.setLayout(new BorderLayout());// JFrame은 기본레이아웃이 Border, JPanel은 Flow
		
		// North - 매니저 로그인 아이콘 & 제목 레이블
		pnlNorth = new JPanel();
		pnlNorth.setBackground(new Color(255, 199, 44));
		pnlNorth.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		
		loginIcon = new ImageIcon("login.png");
		loginLbl1 = new JLabel(loginIcon);
		loginLbl2 = new JLabel("매니저 로그인");
		loginLbl2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		loginLbl2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 380));
		
		pnlNorth.add(loginLbl1);
		pnlNorth.add(loginLbl2);

		// Center - 로고 이미지 & 아이디, 비밀번호 텍스트필드 & 로그인 버튼
		pnlCenter = new JPanel();
		pnlCenter.setBackground(new Color(218, 41, 28));
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
		
		f = new File("managerLogo.png");
		img = ImageIO.read(f);
		// 로고 이미지 파일 사이즈 조절
		changeImg = img.getScaledInstance(200, 170, Image.SCALE_SMOOTH);
		logoIcon = new ImageIcon(changeImg);
		logoLbl = new JLabel(logoIcon);
		logoLbl.setBorder(BorderFactory.createEmptyBorder(0, 100, 40, 100));
		
		// 아이디 텍스트필드 감싸는 서브 패널1 (텍스트필드끼리 간격을 주기 위해서 만듦)
		subPnl1 = new JPanel();
		subPnl1.setPreferredSize(new Dimension(500, 50));
		subPnl1.setBackground(null);// pnlCenter에서 배경색을 지정했으므로 서브 패널의 배경색은 제거		
		idTf = new JTextField();
		// Enter키를 누르면 로그인 버튼 누르는 액션기능과 같이 실행되게 만들기
		// implements KeyListener 하지 않고 swing에서 제공하는 기능을 이용한다!
		// (출처 : https://luvstudy.tistory.com/37)
		idTf.registerKeyboardAction(this, "login", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
		idTf.setPreferredSize(new Dimension(300, 40));
		idTf.setBorder(BorderFactory.createEmptyBorder());
		idTf.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		subPnl1.add(idTf);
		
		// 비밀번호 텍스트필드 감싸는 서브 패널2 (텍스트필드끼리 간격을 주기 위해서 만듦)
		subPnl2 = new JPanel();
		subPnl2.setPreferredSize(new Dimension(500, 50));
		subPnl2.setBackground(null);
		pwPf = new JPasswordField();
		pwPf.registerKeyboardAction(this, "login", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
		pwPf.setPreferredSize(new Dimension(300, 40));
		pwPf.setBorder(BorderFactory.createEmptyBorder());
		pwPf.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		subPnl2.add(pwPf);
		
		loginBtn = new RoundedButton("로그인");// 둥근 테두리 버튼
		loginBtn.setPreferredSize(new Dimension(300, 50));
		loginBtn.setBackground(new Color(255, 199, 44));
		loginBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		// 로그인 버튼에 부여된 액션명령의 이름 설정(Enter키로 로그인할 때 쓰임)
		loginBtn.setActionCommand("login");
		loginBtn.addActionListener(this);

		pnlCenter.add(logoLbl);
		pnlCenter.add(subPnl1);
		pnlCenter.add(subPnl2);
		pnlCenter.add(loginBtn);

		this.add(pnlNorth, "North");
		this.add(pnlCenter, "Center");
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "login") {// 액션이 발생한 객체가 아닌 ActionCommand를 확인한다!			
			if (idTf.getText().equals("") || pwPf.getText().equals("")) {// 입력 안함
				JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호를 입력하세요.", "알림", JOptionPane.WARNING_MESSAGE);
				
			} else if (!idTf.getText().equals(ID)) {// id 틀림
				JOptionPane.showMessageDialog(this, "아이디를 잘못 입력하셨습니다.", "알림", JOptionPane.WARNING_MESSAGE);
				
			} else if (!pwPf.getText().equals(PW)) {// pw 틀림
				JOptionPane.showMessageDialog(this, "비밀번호를 잘못 입력하셨습니다.", "알림", JOptionPane.WARNING_MESSAGE);
				
			} else if (idTf.getText().equals(ID) && pwPf.getText().equals(PW)) {// 로그인 성공
				Mac2 m2 = new Mac2();
				this.remove(pnlNorth);
				this.remove(pnlCenter);
				this.repaint();
				this.revalidate();	
				this.add(m2);
				this.repaint();
				this.revalidate();	
			}	
		}	
	}

	public static void main(String[] args) throws IOException {
		new Mac();
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