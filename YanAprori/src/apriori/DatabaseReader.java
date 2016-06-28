package apriori;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseReader {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/expertsystem";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static final String SQLSTR = "SELECT mainsymptomName,followsymptomName,result FROM record";
	public static List<Diagnose> getRecord() throws ClassNotFoundException, SQLException{
		List<Diagnose> record = new ArrayList<>();
		Connection connection = null;
		Class.forName(DRIVER);
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.out.println("无法连接到数据库");
		}
		PreparedStatement ps = connection.prepareStatement(SQLSTR);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			Diagnose diagnose;
			String mainSymptomName = rs.getString("mainsymptomName");
			String followSymptomName = rs.getString("followsymptomName");
			String result = rs.getString("result");
			//结果集为空
			if(result.equals("")){
				continue;
			}else{
				//插入疾病名称！
				diagnose = new Diagnose();
				ArrayList<String> templist = new ArrayList<>();
				templist.add(result.split("--->")[0]);
				diagnose.setDisease(templist );
			}
			List<String> symptoms = new ArrayList<>();
			if(!mainSymptomName.equals("")){
				symptoms.add(mainSymptomName.split(",|\n")[0]);
			}
			if(!(followSymptomName.equals("")||followSymptomName.equals("无伴随症状"))){
				symptoms.add(followSymptomName);
			}
			diagnose.setSymptoms(symptoms);
			record.add(diagnose);
		}
		return record;
	}
}
