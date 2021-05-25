import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
// 매니저 - 조회된 총 매출액 출력되는 창(서브창, 팝업창)
public class ManagerTotalSales extends JDialog {
	ChoiceSalesDate csd;
	String year, month, day;
	
	JPanel managerTotalSalesPnl, pnlNorth, pnlCenter, pnlSouth;
	ImageIcon icon;
	JLabel[] dateLbl = new JLabel[6];
	JLabel iconLbl, totalLbl1, totalLbl2, totalLbl3;
	JTextArea ta;
	JScrollPane sp;
	File f;
	FileReader fr;
	BufferedReader br;
	
	public ManagerTotalSales(ChoiceSalesDate csd, String title, boolean modal, String year, String month, String day) throws IOException {
		super(csd, title, modal);// 부모창, 팝업창 제목, 모달 여부를 받아옴
		// 전역변수에 값 넣기
		this.csd = csd;	
		this.year = year;
		this.month = month;
		this.day = day;
		
		this.setSize(600, 400);
		// 전체를 감싸는 패널
		managerTotalSalesPnl = new JPanel(new BorderLayout());
		
		// North - 로고 아이콘 & 년월일 매출액 제목 레이블
		pnlNorth = new JPanel();
		pnlNorth.setBackground(new Color(218, 41, 28));
		pnlNorth.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		
		icon = new ImageIcon("miniLogo.png");
		iconLbl  = new JLabel(icon);
		iconLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 160));
	
		pnlNorth.add(iconLbl);
		
		// 년월일 매출액 레이블 생성하기
		makeLbls();
		
		// Center - 매출내역 텍스트 에리어
		pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBackground(new Color(218, 41, 28));
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
		ta = new JTextArea();
		
		printTa();// ta 날짜별 매출내역 불러오기
		
		ta.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		ta.setEditable(false);
		// 세로 스크롤은 항상 있게, 가로 스크롤은 필요할 때만
		sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		pnlCenter.add(sp);	
		
		// South - 총 매출액 레이블
		pnlSouth = new JPanel();
		pnlSouth.setBackground(new Color(218, 41, 28));
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
	
		totalLbl1 = new JLabel("총 매출액 : ");
		totalLbl1.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		totalLbl1.setForeground(new Color(255, 199, 44));
		
		totalLbl2 = new JLabel("????");	
		totalLbl2.setText(printTotalLbl2());// 총 매출액 불러오기
		totalLbl2.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		totalLbl2.setForeground(new Color(255, 199, 44));
		
		totalLbl3 = new JLabel("원");
		totalLbl3.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		totalLbl3.setForeground(new Color(255, 199, 44));
		
		pnlSouth.add(totalLbl1);
		pnlSouth.add(totalLbl2);
		pnlSouth.add(totalLbl3);
		
		managerTotalSalesPnl.add(pnlNorth, "North");
		managerTotalSalesPnl.add(pnlCenter);
		managerTotalSalesPnl.add(pnlSouth, "South");
		
		this.add(managerTotalSalesPnl);
		this.setVisible(true);
	}
	
	// 년월일 매출액 레이블 생성하는 메서드
	void makeLbls() {
		String[] dateStr = {year, "년", month, "월", day, "일 매출액"};
		for (int i = 0; i < dateLbl.length; i++) {
			dateLbl[i] = new JLabel(dateStr[i]);
			dateLbl[i].setFont(new Font("맑은 고딕", Font.BOLD, 25));
			dateLbl[i].setForeground(new Color(255, 199, 44));
			pnlNorth.add(dateLbl[i]);
		}
	}
	
	// 날짜별 매출 내역 불러오는 메서드
	void printTa() {
		// 불러오는 파일명 : 현재 날짜 + sales.txt
		f = new File(year + month + day + "sales.txt");
		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			String l = null;
			while ((l=br.readLine()) != null) {
				ta.append(l + "\n");// 텍스트에리어에 있는 텍스트의 맨 마지막에 문자열을 덧붙이는 기능
			}
		} catch (FileNotFoundException e) {// 매출내역파일이 없을 때
			System.out.println(year + month + day + "sales.txt" + " 파일 없음!");
			JOptionPane.showMessageDialog(this, "매출 내역이 없습니다!");
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
	// 총 매출액 불러오는 메서드
	String printTotalLbl2() {
		// 불러오는 파일명 : 현재 날짜 + sales.txt
		f = new File(year + month + day + "sales.txt");
		int total = 0;// 총 결제 금액
		int price = 0;// 가격
		int cnt = 0;// 수량
		DecimalFormat formatter = new DecimalFormat("###,###");// 금액 모양
		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			String l = null;
			while ((l=br.readLine()) != null) {
				String[] str = l.split("/");
				price = Integer.parseInt(str[2]);
				cnt = Integer.parseInt(str[3]);				
				total += (price * cnt);
			}
		} catch (FileNotFoundException e) {
			System.out.println(year + month + day + "sales.txt" + " 파일 없음!");
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
		
		return formatter.format(total);// String타입의 총매출액 반환
	}
}
