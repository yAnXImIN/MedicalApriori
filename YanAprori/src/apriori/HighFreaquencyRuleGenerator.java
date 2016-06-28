package apriori;

import java.util.ArrayList;
import java.util.List;

public class HighFreaquencyRuleGenerator {
	
	//生成规则集
	public static List<Rule> rules = new ArrayList<>();
	public static List<List<String>> highFreSymptom;
	public static List<String> highFreDisease = new ArrayList<>();
	public static List<Diagnose> record ;
	public static double MIN_SUPPORT = 0.25;
	public static double MIN_CONFIDENCE = 0.75;
	public static void generateRules(){
		List<String> dic = new ArrayList<>();
		//获得疾病词典
		for(int i=0;i<record.size();i++){
			for(int j=0;j<record.get(i).getDisease().size();j++){
				if(!dic.contains(record.get(i).getDisease().get(j))){
					dic.add(record.get(i).getDisease().get(j));
				}
			}
		}
		//选出高频词汇
		for(int i=0;i<dic.size();i++){
			int tempCount = 0;
			for(int j=0;j<record.size();j++){
				if(record.get(j).getDisease().contains(dic.get(i))){
					tempCount++;
				}
			}
			if(tempCount>MIN_SUPPORT*record.size()){
				highFreDisease.add(dic.get(i));
			}
		}
		//选择出高频规则
		for(int i=0;i<highFreSymptom.size();i++){
			for(int j=0;j<highFreDisease.size();j++){
				int tempCount = 0;
				int tempSym = 0;
				for(int k=0;k<record.size();k++){
					if(HighFrequencySymptomGenerator.isChildSet(highFreSymptom.get(i), record.get(k).getSymptoms())){
						tempSym++;
						if(record.get(k).getDisease().contains(highFreDisease.get(j))){
							tempCount++;
						}
					}
				}
				if(tempCount>MIN_SUPPORT*record.size()){
					//利用贝叶斯公式算出置信度
					if(tempCount>MIN_CONFIDENCE*tempSym&&tempSym!=0){
						Rule rule = new Rule();
						rule.setDisease(highFreDisease.get(j));
						rule.setSymptoms(highFreSymptom.get(i));
						rule.setRate((double)tempCount/(double)tempSym);
						rules.add(rule);
					}
				}
			}
		}
	}
}
