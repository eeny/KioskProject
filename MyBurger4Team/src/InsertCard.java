import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//6페이지 - 고객 - 카드 삽입하는 창
public class InsertCard extends JPanel implements MouseListener {
	File f;
	Image img, changeImg;
	ImageIcon icon;
	JLabel imgLbl, txtLbl;
	
	public InsertCard() throws IOException {
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(255,199,44));
		this.addMouseListener(this);// 패널 자체에 마우스 이벤트
		
		f = new File("insertCard.png");
		img = ImageIO.read(f);
		// 이미지 사이즈 조절
		changeImg = img.getScaledInstance(500, 400, Image.SCALE_SMOOTH);
		icon = new ImageIcon(changeImg);
		imgLbl = new JLabel(icon);
		
		txtLbl = new JLabel("카드를 투입구에 넣어주세요");
		txtLbl.setBorder(BorderFactory.createEmptyBorder(30, 0, 90, 0));
		txtLbl.setHorizontalAlignment(JLabel.CENTER);
		txtLbl.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		
		this.add(imgLbl, "Center");
		this.add(txtLbl, "South");
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {// 패널을 클릭하면 다음 패널로 넘어간다
		this.removeAll();
		this.repaint();
		this.revalidate();
		try {
			WaitingNumber wn = new WaitingNumber();
			this.add(wn);
			this.repaint();
			this.revalidate();	
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	
	public static void main(String[] args) throws IOException {
		new InsertCard();
	}
}
