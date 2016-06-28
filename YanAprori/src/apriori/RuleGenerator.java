package apriori;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * 程序入口，生成规则集！
 * */
public class RuleGenerator {
	//输入的记录
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
//		generateFromDatabase();
		gennerateFromText();
		
	}
	private static void gennerateFromText() throws IOException{
		HighFrequencySymptomGenerator.record = TxtReader.getRecord();
		long startime = System.currentTimeMillis();
		HighFrequencySymptomGenerator.MIN_SUPPORT = 0.01;
		HighFreaquencyRuleGenerator.MIN_SUPPORT = 0.01;
		HighFreaquencyRuleGenerator.MIN_CONFIDENCE = 0.5;
		HighFrequencySymptomGenerator.getHighFreSymptom();
		HighFreaquencyRuleGenerator.highFreSymptom = HighFrequencySymptomGenerator.highFreSymptom;
		HighFreaquencyRuleGenerator.record = HighFrequencySymptomGenerator.record;
		HighFreaquencyRuleGenerator.generateRules();
		List<Rule> rules = HighFreaquencyRuleGenerator.rules;
		long endstime = System.currentTimeMillis();
		System.out.println("输入测试集大小："+HighFrequencySymptomGenerator.record.size());
		System.out.println("共找到"+HighFrequencySymptomGenerator.highFreSymptom.size()+"个频繁症状集");
		System.out.println("共找到"+HighFreaquencyRuleGenerator.highFreDisease.size()+"个频繁疾病");
		System.out.println("共找到"+rules.size()+"个可以置信的规则，如下所示");
		for(Rule rule:rules){
			System.out.println(rule);
		}
		System.out.println("程序所用时间："+(endstime-startime)+"毫秒");
		Scanner s = new Scanner(System.in);
		while(true){
			String str = s.nextLine();
			if(str==null||"".equals(str)){
				break;
			}
			String[] tempStrs = str.split(" ");
			List<String> findStr = new ArrayList<>();
			for(String tempStr:tempStrs){
				findStr.add(tempStr);
			}
			Rule rule = findRule(findStr, rules);
			System.out.println(rule);
		}
	}
	private static void generateFromDatabase() throws ClassNotFoundException, SQLException{
		HighFrequencySymptomGenerator.record = DatabaseReader.getRecord();;
		long startime = System.currentTimeMillis();
		HighFrequencySymptomGenerator.MIN_SUPPORT = 0.05;
		HighFreaquencyRuleGenerator.MIN_SUPPORT = 0.05;
		HighFrequencySymptomGenerator.getHighFreSymptom();
		HighFreaquencyRuleGenerator.highFreSymptom = HighFrequencySymptomGenerator.highFreSymptom;
		HighFreaquencyRuleGenerator.record = HighFrequencySymptomGenerator.record;
		HighFreaquencyRuleGenerator.generateRules();
		List<Rule> rules = HighFreaquencyRuleGenerator.rules;
		long endstime = System.currentTimeMillis();
		System.out.println("输入测试集大小："+HighFrequencySymptomGenerator.record.size());
		System.out.println("共找到"+HighFrequencySymptomGenerator.highFreSymptom.size()+"个频繁症状集");
		System.out.println("共找到"+HighFreaquencyRuleGenerator.highFreDisease.size()+"个频繁疾病");
		System.out.println("共找到"+rules.size()+"个可以置信的规则，如下所示");
		for(Rule rule:rules){
			System.out.println(rule);
		}
		System.out.println("程序所用时间："+(endstime-startime)+"毫秒\n");
	}
	public static Rule findRule(List<String> findStr,List<Rule> rules){
		Rule rule = new Rule();
		rule.setDisease("未发现疾病");
		rule.setSymptoms(new ArrayList<String>());
		for(Rule tempRule : rules){
			if(isSameSet(findStr, tempRule.getSymptoms())){
				return tempRule;
			}
		}
		
		return rule;
	}
	/**
	 * 检测两个集合是否相等。
	 * */
	private static boolean isSameSet(List<String> child,List<String> parent){
		if(toHash(child)==toHash(parent)){
			return true;
		}
		else{
			return false;
		}
	}
	private static int toHash(List<String> list){
		int hashcode = 0;
		for(String temp:list){
			hashcode+=temp.hashCode();
		}
		return hashcode;
	}
}
