import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
//7페이지 - 고객 - 대기 번호(주문 번호) 창
public class WaitingNumber extends JPanel implements MouseListener {
	JPanel subPnl;
	File f, wtF, mpF;
	Image img, changeImg;
	ImageIcon icon;
	JLabel imgLbl, txtLbl1, txtLbl2;
	JTextField tf;
	FileReader fr;
	BufferedReader br;
	FileWriter fw;
	PrintWriter pw;
	
	public WaitingNumber() throws IOException {
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(255,199,44));
		this.addMouseListener(this);// 패널 자체에 마우스 이벤트 달았음
		
		f = new File("receipt.png");
		img = ImageIO.read(f);
		// 이미지 크기 조절
		changeImg = img.getScaledInstance(450, 400, Image.SCALE_SMOOTH);
		icon = new ImageIcon(changeImg);
		imgLbl = new JLabel(icon);
		
		// 주문번호 부분 레이블과 텍스트필드 정렬을 위해 서브 패널 생성
		subPnl = new JPanel();
		subPnl.setBackground(new Color(255,199,44));
		subPnl.setBorder(BorderFactory.createEmptyBorder(30, 0, 80, 0));
		txtLbl1 = new JLabel("대기번호  ");
		txtLbl1.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		
		tf = new JTextField("???");		
		// tf 주문번호 값 넣기
		tf.setText(printTf());	
		tf.setEditable(false);// 텍스트필드 수정 못하게 설정
		tf.setBackground(new Color(255,199,44));
		tf.setBorder(BorderFactory.createEmptyBorder());
		tf.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		txtLbl2 = new JLabel("  번");
		txtLbl2.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		subPnl.add(txtLbl1);
		subPnl.add(tf);
		subPnl.add(txtLbl2);
		
		this.add(imgLbl, "Center");
		this.add(subPnl, "South");		
	}
	// 주문 번호 출력하는 메서드
	String printTf() {
		String str = null;// 주문 번호
		// 조회할 파일명: 현재 날짜 + waiting
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		String date = format1.format(new Date());
		File wtF = new File(date + "waiting.txt");
		try {
			fr = new FileReader(wtF);
			br = new BufferedReader(fr);		
			String l = null;
			
			while ((l=br.readLine()) != null) {
				str = l.split("/")[0];// 제일 마지막줄(최신) 주문번호
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
		
		return str;// 주문번호(String타입) 반환
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// menu_price.txt 파일 초기화
		// 파일 내용이 남아있으면 나중에 주문내역 확인할 때 불러오기됨
		mpF = new File("menu_price.txt");
		try {
			fw = new FileWriter(mpF);
			pw = new PrintWriter(fw);
			pw.print("");// 파일 내용 공백으로 변경
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 주문 터치 화면으로 돌아감
		this.removeAll();
		this.repaint();
		this.revalidate();
		Page2 p2 = new Page2();
		this.add(p2);
		this.repaint();
		this.revalidate();	
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	public static void main(String[] args) throws IOException {
		new WaitingNumber();
	}
}
