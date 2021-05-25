import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JTable;

public class FileUtil {
	static void saveFile(String file, JTable tbl, String regex){
		File f = new File(file);
		PrintWriter pw=null;
		try {
			FileWriter fw = new FileWriter(f);
			pw = new PrintWriter(fw);			
			for(int i=0;i<tbl.getRowCount();i++){
				String str = "";
				for(int j=0;j<tbl.getColumnCount();j++){
					str+=tbl.getValueAt(i, j)+regex;
				}
				pw.println(str.substring(0,str.lastIndexOf(regex)));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			pw.close();
		}
	}
}
