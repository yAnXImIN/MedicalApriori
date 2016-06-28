package ansjText;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.recognition.NatureRecognition;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.util.IOUtil;
import org.nlpcn.commons.lang.util.StringUtil;


public class AnsjTest {
	

		public static final String DEFAULT_NATURE = "WRONG";

		public static final Integer DEFAULT_FREQ = 1000;

		public static final String DEFAULT_FREQ_STR = "1000";
		private static List<String> userDic;
		private static File f1=null; // 问题
		private static File f2=null; // 结果
		private static Integer count=0;
		public static void main(String agrv[] ) throws IOException{
			//定义输出结果文件
			FileWriter resultfileWriter=new FileWriter("result.txt");
			//读入文件
			FileInputStream fis = new FileInputStream("sample_SC_test.txt"); 
		    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		    BufferedReader br=new BufferedReader(isr);
		    StringBuffer stringBuffer=new StringBuffer(4096);
		    String temp = null;
		    int indexStr=0;
		    while((temp=br.readLine())!=null){
		    	//stringBuffer.append(temp).append("\n");
		    	//获取每一行，然后采用错误词典分词
		    	//System.out.println(temp);
		    	String str = temp.toString();
		    	String idStr="";
		    	int flag=0;
		    	for(int i=0;i<str.length();i++){
		    		char  item =  str.charAt(i);
		    		if(item=='='){
		    			flag=1;
		    			continue;
		    		}
		    		else if(item==')'){
		    			for(int j=i+1;j<str.length();j++){
		    				char  item2 =  str.charAt(j);
		    				if(item2!=' '){
		    					indexStr=j;
		    					break;
		    				}
		    			}
		    			break;
		    		}
		    		else if(flag==1){
		    			idStr +=item;
		    		}
		    	}
		    	temp = temp.substring(indexStr,temp.length()-1);
		    	//System.out.println(temp.length());
		    	analysisText(temp.toString(),"",resultfileWriter,idStr);
		    }
		    resultfileWriter.flush();
			resultfileWriter.close();
		    br.close();
		}
		private static void analysisText(String ques,String ans,FileWriter resultfileWriter,String idStr) throws IOException{
			loadFile(null, new File("library/userLibrary.dic"));
			List<Term> parse = ToAnalysis.parse(ques);
			
			new NatureRecognition(parse).recognition() ;
			String wrongWords[] = new String[100];
			String correctWords[] = new String[100];
			int k=0;
			HashMap<String, Boolean> mp = new HashMap<String,Boolean>();
			int flag=0;
			for(int i=0;i<parse.size();i++)
			{
				if(userDic.contains(parse.get(i).getName()))
				{
					if(!mp.containsKey(parse.get(i).getName()))
					{
						mp.put(parse.get(i).getName(), true);
						if(!parse.get(i).getNatureStr().matches("^[a-zA-Z]*")){//如果不是是字母表示上面得到的错词是错误的
							wrongWords[k] = parse.get(i).getName();
							correctWords[k++] = parse.get(i).getNatureStr();
						}
					}
				}
			}
			if(k==0){
				System.out.println(idStr+", 0");
				resultfileWriter.write(idStr+", 0");
				resultfileWriter.write("\r\n");
			}
			else{
				//找对应的第几个位置
				String check = "";
				for(int kk=0;kk<k;kk++){
					int index = ques.indexOf(wrongWords[kk]);
					//System.out.println("index:"+index);
					for(int kindex=0;kindex<correctWords[kk].length();kindex++){
						char item1 = correctWords[kk].charAt(kindex);
						char item2 = wrongWords[kk].charAt(kindex);
						if(item1!=item2){
							check+=(index+kindex);
							check+=",";
							check+=correctWords[kk].charAt(kindex);
							check+=",";
						}
					}
				}
				check = check.substring(0, check.length()-1);
				System.out.println(idStr+","+check);
				resultfileWriter.write(idStr+","+check);
				resultfileWriter.write("\r\n");
			}
			/*
			for(int kk=0;kk<k;kk++){
				System.out.println(wrongWords[kk]+","+correctWords[kk]);
			}
			*/
			mp.clear();
			userDic.clear();
			
		}
		public static void loadFile(Forest forest, File file) {
				userDic = new ArrayList<String>();
				if (!file.canRead()) {
					return;
				}
				String temp = null;
				BufferedReader br = null;
				String[] strs = null;
				Value value = null;
				try {
					br = IOUtil.getReader(new FileInputStream(file), "UTF-8");
					while ((temp = br.readLine()) != null) {
						if (StringUtil.isBlank(temp)) {
							continue;
						} else {
							strs = temp.split("\t");

							strs[0] = strs[0].toLowerCase();

							// 如果默认辞典存在就放弃
							if (strs.length != 3) {
								value = new Value(strs[0], DEFAULT_NATURE, DEFAULT_FREQ_STR);
							} else {
								value = new Value(strs[0], strs[1], strs[2]);
							}
							userDic.add(strs[0]);
							UserDefineLibrary.insertWord(strs[0], strs[1], Integer.parseInt(strs[2]) );
							//UserDefineLibrary.insertWord("csdn创新院","userDefine",1000);
						}
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					IOUtil.close(br);
					br = null;
				}
			}
}
