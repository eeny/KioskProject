import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
// 2페이지 - 고객 - 터치스크린
public class Page2 extends JPanel implements MouseListener {
	JPanel pnlCenter, pnlSouth;
	ImageIcon icon, miniIcon;
	JLabel lbl, iconLbl;
	
	public Page2() {
		// 원래 JFrame은 기본 레이아웃이 Border인데
		// 지금 클래스가 JPanel을 상속받기 때문에 레이아웃이 기본인 Flow로 돼있어서 수정함
		this.setLayout(new BorderLayout());	
		this.addMouseListener(this);
		
		// Center 패널
		pnlCenter = new JPanel();
		pnlCenter.setBackground(new Color(27, 68, 10));
		icon = new ImageIcon("p2.jpg");
		// Center 패널에 배경으로 이미지 넣기
		pnlCenter = new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		// South 패널(로고 그림이랑 글자 있는 부분)
		pnlSouth = new JPanel();
		pnlSouth.setBackground(new Color(27, 68, 10));
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));// 상, 좌, 하, 우 여백
		miniIcon = new ImageIcon("minigreen.png");
		iconLbl = new JLabel(miniIcon);
		lbl = new JLabel("주문하시려면 터치하세요");
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		lbl.setForeground(new Color(255, 199, 44));
		pnlSouth.add(iconLbl);
		pnlSouth.add(lbl);
		
		this.add(pnlCenter, "Center");
		this.add(pnlSouth, "South");
		
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Page2();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Page3 p3 = new Page3();// 매장/포장 클래스(JPanel)
		this.removeAll();// 현재 패널에 내용 삭제
		this.repaint();
		this.revalidate();	
		this.add(p3);// 새로운 패널 붙이기
		this.repaint();
		this.revalidate();	
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
	
