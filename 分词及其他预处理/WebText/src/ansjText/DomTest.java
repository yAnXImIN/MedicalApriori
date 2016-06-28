package ansjText;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomTest {

	private  static int tr;
	private  static int td;
	private  static int colspan;
	private  static int rowspan;
	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.parse(new File("train_file/CSC_train.xml"));

		// 获得根元素结点
		DomTest DT = new DomTest();
		NodeList table = doc.getElementsByTagName("MISTAKE");
		FileWriter fileWriter=new FileWriter("wrong_dic/wrong_dic.txt");
		for(int i = 0; i < table.getLength(); i++)
		{
			DT.parseElement((Element) table.item(i),null,fileWriter);
		}
		fileWriter.flush();
		   fileWriter.close();
		pr();
	}

	public void parseElement(Element element,String fartherTagName,FileWriter fileWriter) throws DOMException, IOException{
		
		String tagName = element.getNodeName();
		NodeList children = element.getChildNodes();
		// element元素的所有属性所构成的NamedNodeMap对象，需要对其进行判断

		if(tagName == "WRONG")
		{
			//System.out.println("11111");
			tr=0;
			td=0;
		}
		else if (tagName == "tr")
		{	tr++;
			td=0;
		}
		else if (tagName == "td")
		{
			td++;colspan=0;rowspan=0;
		}
		
		// 如果该元素存在属性
			NamedNodeMap map = element.getAttributes();
//			if (null != map)
//			{
//	
//				for (int i = 0; i < map.getLength(); i++)
//				{
//					// 获得该元素的每一个属性
//					Attr attr = (Attr) map.item(i);
//					String attrName = attr.getName();
//					if(attrName=="colspan"&&tagName=="td")
//					{
//						colspan= Integer.parseInt(attr.getValue());
//						//System.out.println("colspan="+colspan);
//					}
//					if(attrName=="rowspan"&&tagName=="td")
//					{
//						rowspan= Integer.parseInt(attr.getValue());
//						//System.out.println("colspan="+colspan);
//					}
//				}
//			}
//			
			for (int i = 0; i < children.getLength(); i++){
	
					Node node = children.item(i);
	
					// 获得结点的类型
	
					short nodeType = node.getNodeType();
	
					if (nodeType == Node.ELEMENT_NODE)
					{
						// 是元素，继续递归
						parseElement((Element) node,tagName,fileWriter);
					}
	
					else if (nodeType == Node.TEXT_NODE)
					{
						if(tagName == "WRONG")
								fileWriter.write(node.getNodeValue());
								//fileWriter.write("	wrong\r\n");
						else if(tagName == "CORRECTION")
						{
							fileWriter.write("	");
							fileWriter.write(node.getNodeValue());

							fileWriter.write("	1000\r\n");
						}
							   
					}
				}
	}

	public static void pr() {
		System.out.println("END");
	}
}