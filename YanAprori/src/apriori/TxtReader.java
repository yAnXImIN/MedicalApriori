package apriori;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * 本模块负责对输入文件进行处理，生成需要的格
 * 
 * */
public class TxtReader {
	//输入文件所在位置
	private static final String INPUT_FILE = "outputSympothy";
	/**
	 * Get symptoms and disease record from file given;
	 * @author yanximin
	 * @return the record of symptoms and disease in the form of List
	 * */
	public static List<Diagnose> getRecord() throws IOException{
		List<Diagnose> record = new ArrayList<Diagnose>();
		File file = new File(INPUT_FILE);
		FileInputStream fileInputStream=null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			System.out.println("文件打开错误");
			e.printStackTrace();
			return null;
		}
		InputStreamReader reader = new InputStreamReader(fileInputStream);
		BufferedReader br = new BufferedReader(reader);
		String tempString = "";
		while((tempString=br.readLine())!=null){
			String[] infomations = tempString.split("\\$");
			String[] sym = infomations[0].split(" ");
			String[] dis = infomations[1].split(" ");
			Diagnose diagnose = new Diagnose();
			List<String> tempSymptoms = new ArrayList<>();
			List<String> tempDisease = new ArrayList<>();
			for(int i =0;i<sym.length;i++){
				tempSymptoms.add(sym[i]);
			}
			diagnose.setSymptoms(tempSymptoms);
			for(int i=0;i<dis.length;i++){
				tempDisease.add(dis[i]);
			}
			diagnose.setDisease(tempDisease);
			record.add(diagnose);
		}
		br.close();
		reader.close();
		fileInputStream.close();
		return record;
	}  
}
