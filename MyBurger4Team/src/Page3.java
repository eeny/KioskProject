import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.AncestorListener;

// 3페이지 - 고객 - 매장/포장 선택
public class Page3 extends JPanel implements ActionListener {
	JPanel pnl1, pnl2, pnl3, small_pnl1, small_pnl2, small_pnl3;
	JButton btn1, btn2, btn3, btn4;
	JLabel lbl1, lbl2, lbl3;
	Image img1, img2;
	Page3 p3;

	public Page3() {
		// 전체(메인) 패널
		this.setLayout(new GridLayout(0, 1));
		this.setBackground(Color.decode("#004516"));

		// 첫번째 패널
		pnl1 = new JPanel(new BorderLayout());
		pnl1.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));// 상, 좌, 하, 우 여백
		pnl1.setBackground(Color.decode("#004516"));

		ImageIcon icon1 = new ImageIcon("green.png");		
		Image img1 = icon1.getImage();
		// 이미지 사이즈 조절
		Image chageImg1 = img1.getScaledInstance(150, 140, Image.SCALE_SMOOTH);
		ImageIcon changeIcon1 = new ImageIcon(chageImg1);
		lbl1 = new JLabel(changeIcon1);
		// 레이블 가운데 정렬
		lbl1.setHorizontalAlignment(JLabel.CENTER);

		lbl2 = new JLabel("식사하실 장소를 선택해 주세요");
		lbl2.setHorizontalAlignment(JLabel.CENTER);
		lbl2.setForeground(Color.white);
		lbl2.setFont(new Font("맑은 고딕", Font.BOLD, 30));

		pnl1.add(lbl1, "Center");
		pnl1.add(lbl2, "South");

		// 두번째패널
		pnl2 = new JPanel(new BorderLayout());
		pnl2.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		pnl2.setBackground(Color.decode("#004516"));
		
		small_pnl1 = new JPanel();
		small_pnl1.setBackground(Color.decode("#004516"));

		ImageIcon i1 = new ImageIcon("1.png");
		Image im1 = i1.getImage();
		Image chage1 = im1.getScaledInstance(150, 250, Image.SCALE_SMOOTH);
		ImageIcon changeI1 = new ImageIcon(chage1);
		btn1 = new JButton(changeI1);
		btn1.setContentAreaFilled(false);
		btn1.setBorderPainted(false);
		btn1.addActionListener(this);

		ImageIcon i2 = new ImageIcon("2.png");
		Image im2 = i2.getImage();
		Image chage2 = im2.getScaledInstance(150, 250, Image.SCALE_SMOOTH);
		ImageIcon changeI2 = new ImageIcon(chage2);
		btn2 = new JButton(changeI2);
		btn2.setContentAreaFilled(false);
		btn2.setBorderPainted(false);
		btn2.addActionListener(this);
		
		small_pnl1.add(btn1);
		small_pnl1.add(btn2);
		
		pnl2.add(small_pnl1, "Center");

		// 세번째 패널
		pnl3 = new JPanel(new BorderLayout());
		
		small_pnl2 = new JPanel(new FlowLayout());
		small_pnl2.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		small_pnl2.setBackground(Color.decode("#004516"));
		lbl3 = new JLabel("Please select your language");
		lbl3.setHorizontalAlignment(JLabel.CENTER);
		lbl3.setForeground(Color.white);
		lbl3.setFont(new Font("Rockwell", Font.BOLD, 25));
		small_pnl2.add(lbl3);
		
		small_pnl3 = new JPanel(new FlowLayout());
		small_pnl3.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
		small_pnl3.setBackground(Color.decode("#004516"));
		btn3 = new RoundedButton("한국어");
		btn4 = new RoundedButton("English");
		btn3.setPreferredSize(new Dimension(150, 50));// 버튼 사이즈 조절
		btn4.setPreferredSize(new Dimension(150, 50));
		btn3.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btn4.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btn3.setForeground(new Color(54, 140, 94));
		btn4.setForeground(new Color(54, 140, 94));
		btn3.setBackground(Color.WHITE);
		btn4.setBackground(Color.WHITE);
		btn3.setFocusPainted(false);// 버튼에 포커스가 생겨도 테두리 안생기게
		btn4.setFocusPainted(false);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		small_pnl3.add(btn3);
		small_pnl3.add(btn4);
		
		pnl3.add(small_pnl2, "North");
		pnl3.add(small_pnl3, "Center");

		this.add(pnl1);
		this.add(pnl2);
		this.add(pnl3);

		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Page3();
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		// 한국어를 누르든 영어를 누르든 넘어가는 패널은 똑같음
		Burger4Team b4 = new Burger4Team();
		
		if (e.getSource()==btn3) {// 한국어			
			this.removeAll();
			this.repaint();
			this.revalidate();	
			this.add(b4);
			this.repaint();
			this.revalidate();
			
		} else if (e.getSource()==btn4) {// 영어
			this.removeAll();
			this.repaint();
			this.revalidate();	
			this.add(b4);
			this.repaint();
			this.revalidate();
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
}
