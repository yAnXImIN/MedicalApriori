package apriori;

import java.util.List;
/**
 * 输入的每条诊断的POJO，包含症状和诊断结果
 * */
public class Diagnose {
	private List<String> symptoms;
	private List<String> disease;
	public List<String> getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(List<String> symptoms) {
		this.symptoms = symptoms;
	}
	public List<String> getDisease() {
		return disease;
	}
	public void setDisease(List<String> disease) {
		this.disease = disease;
	}

	
}
