import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
// 1페이지 - 매니저/고객 선택
public class Page1 extends JFrame implements ActionListener {
	JButton btn1, btn2;
	JPanel page1Pnl, subPnl1, subPnl2;
	ImageIcon icon;

	public Page1() throws IOException {
		this.setSize(600, 800);
		this.setDefaultCloseOperation(3);
		this.setResizable(false);// 창 크기 고정
		this.setTitle("햄버거 키오스크");
		// 윈도우창 아이콘 변경하기
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image toolImg = toolkit.getImage("windowLogo.png");
		this.setIconImage(toolImg);
		
		// 모든 패널이 붙어있는 제일 큰 패널
		page1Pnl = new JPanel(new GridLayout(2, 0));
		// 버튼 붙어있는 서브 패널 1
		subPnl1 = new JPanel(new GridLayout(0, 2));

		btn1 = new JButton("매니저");
		btn2 = new JButton("고객");
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn1.setBackground(new Color(218, 41, 28));
		btn1.setBorder(BorderFactory.createLineBorder(new Color(255, 199, 44)));
		btn1.setFont(new Font("맑은 고딕", Font.BOLD, 50));
		btn1.setForeground(new Color(255, 199, 44));
		btn1.setFocusPainted(false);// 버튼을 클릭했을 때 생기는 테두리 안보이게
		btn2.setBackground(new Color(218, 41, 28));
		btn2.setBorder(BorderFactory.createLineBorder(new Color(255, 199, 44)));
		btn2.setFont(new Font("맑은 고딕", Font.BOLD, 50));
		btn2.setForeground(new Color(255, 199, 44));
		btn2.setFocusPainted(false);
		
		subPnl1.add(btn1);
		subPnl1.add(btn2);		
		
		icon = new ImageIcon("first.png");// 상단 패널에 들어갈 이미지
		// 서브 패널 2 에 배경을 이미지로 설정
		subPnl2 = new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		page1Pnl.add(subPnl2);
		page1Pnl.add(subPnl1);

		this.add(page1Pnl);

		this.setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		new Page1();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn1) {// 매니저					
			try {
				Mac m = new Mac();// 매니저창 클래스(JPanel로 돼있음)
				this.remove(page1Pnl);// 지금 패널을 지우고
				this.repaint();
				this.revalidate();	
				this.setTitle("매장주문관리");
				this.add(m);// 띄울 패널을 붙인다!
				this.repaint();
				this.revalidate();		
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		} else if (e.getSource() == btn2) {// 고객
			Page2 p2 = new Page2();// 고객창 클래스
			this.remove(page1Pnl);
			this.repaint();
			this.revalidate();	
			this.add(p2);
			this.repaint();
			this.revalidate();	
		}
	}
}