package test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.ansj.*;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.recognition.NatureRecognition;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.MyStaticValue;
import org.apache.commons.io.FileUtils;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import org.nlpcn.commons.lang.util.IOUtil;
import org.nlpcn.commons.lang.util.StringUtil;


public class AnaTest {

	public static final String DEFAULT_NATURE = "userDefine";

	public static final Integer DEFAULT_FREQ = 1000;

	public static final String DEFAULT_FREQ_STR = "1000";
	private static List<String> userDic;
	private static File f1=null; // 问题
	private static File f2=null; // 结果
	private static Integer count=0;
	public static void main(String agrv[] ) throws IOException{
		//DBreader dbreader = new DBreader();
		//dbreader.readDBsympotm();
		f1=new File( "C:\\Users\\mac\\Desktop\\hello2" );
		f2=new File( "C:\\Users\\mac\\Desktop\\hello" );
		FileWriter fileWriter =  new FileWriter("C:\\Users\\mac\\Desktop\\resultnew.txt");
		
			File[] files1 = f1.listFiles();
			File[] files2 = f2.listFiles();
			for(int x=0; x<files1.length; x++)
		//	for(int x=0; x<1; x++)
			{
				//判断文件名是否以fileType结尾
					if( files1[x].getName().endsWith( "txt" ))
					{
						//File fr = new File(files1[x].getName());
						String ques = FileUtils.readFileToString(files1[x]);
						
						//File fr2 = new File(files2[x].getName());
						String ans = FileUtils.readFileToString(files2[x]);
					     
						analysisText(ques,ans,fileWriter);
					}
			}
			fileWriter.flush();
			fileWriter.close();
			System.out.println("The End!");
		}
	

	private static void analysisText(String ques,String ans,FileWriter fileWriter) throws IOException{
		loadFile(null, new File("C:\\Users\\mac\\Workspaces\\MyEclipse 8.5\\Test\\library\\userLibrary\\userLibrary.dic"));
		//		List<Term> parse = ToAnalysis.parse(ques);
		List<Term> parse = ToAnalysis.parse("我最近胸疼，发热，咽喉有异物感。是什么原因？");
		
		new NatureRecognition(parse).recognition() ;
		String buff = "";
		HashMap<String, Boolean> mp = new HashMap<String,Boolean>();
		int flag=0;
		for(int i=0;i<parse.size();i++)
		{
			if(userDic.contains(parse.get(i).getName()))
			{
				if(!mp.containsKey(parse.get(i).getName()))
				{
					mp.put(parse.get(i).getName(), true);
					if(flag==0)
					{
						buff = buff + parse.get(i).getName();
						flag=1;
					}
					else
					{
						buff = buff + " " + parse.get(i).getName();
					}
				}
			}
		}
	//	System.out.println(buff);
		mp.clear();
		userDic.clear();
		loadFile(null, new File("C:\\Users\\mac\\Workspaces\\MyEclipse 8.5\\Test\\library\\userLibrary\\resultLibrary.dic"));
		
	//	List<Term> parse2 = ToAnalysis.parse("你好,考虑咽喉炎引起的,治疗时应以寻找病因,预防为主.应戒除烟酒,消除外界各种不良因素.用台漱剂漱口,局部涂碘甘油.有滤泡形成者可行激光手术.也可采用中药治疗.如胖大海泡茶饮用等. 预防：锻炼身体,增强体质.预防上呼吸道感染,防止慢性咽炎急性发作.注意口腔和鼻腔卫生,防治口,鼻疾病.注意饮食卫生,保证身体营养平衡,少吃过热,过冷及辛辣刺激食物,保持大便通畅.因为慢性咽炎疗程长,疗效慢,不容易完全治愈.需要认识.不需要担心.保持心情舒畅很重要.意见建议：你好，根据情况建议到医院检查并对症治疗比较好的。");
	   
		List<Term> parse2 = ToAnalysis.parse(ans);
		new NatureRecognition(parse2).recognition() ;
		buff = buff + "$";
		HashMap<String, Boolean> mp2 = new HashMap<String,Boolean>();
		int flag2 = 0;
		for(int i=0;i<parse2.size();i++)
		{
			if(userDic.contains(parse2.get(i).getName())&&flag==1)
			{
				if(!mp2.containsKey(parse2.get(i).getName()))
				{
					mp2.put(parse2.get(i).getName(), true);
					//System.out.println(buff+parse2.get(i).getName());
					//fileWriter.write(count+"\t");
					if(flag2==0)
					{
						buff = buff + parse2.get(i).getName();
						flag2=1;
					}
					else
					{
						buff = buff + " " + parse2.get(i).getName();
					}
					//count++;
					//fileWriter.write(buff+parse2.get(i).getName());
					//fileWriter.write("\r\n");
				}
			}
		}
		if(flag == 1 && flag2 ==1)
		{
			System.out.println(buff);
			fileWriter.write(buff);
			fileWriter.write("\r\n");
		}
	
		mp2.clear();
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

						// 如何核心辞典存在那么就放弃

						if (strs.length != 3) {
							value = new Value(strs[0], DEFAULT_NATURE, DEFAULT_FREQ_STR);
						} else {
							value = new Value(strs[0], strs[1], strs[2]);
						}
						//System.out.println(strs[0]);
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
