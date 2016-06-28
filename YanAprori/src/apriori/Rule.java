package apriori;

import java.util.List;

public class Rule {
	private List<String> symptoms;
	private String disease;
	private double rate;
	public List<String> getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(List<String> symptoms) {
		this.symptoms = symptoms;
	}
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	@Override
	public String toString() {
		String outStr = "< ";
		for(int i=0;i<symptoms.size();i++){
			outStr+=symptoms.get(i);
			outStr+=" ";
		}
		outStr+="> ----->";
		outStr+=disease;
		outStr+=" ÷√–≈≥Ã∂»£∫";
		outStr+=rate;
		return outStr;
	}
	
	
}
