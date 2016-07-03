package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class TestXml {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.getProperty("user.dir")  获取项目的绝对路径
		String filepath=System.getProperty("user.dir");//+"\\xmlfiles";
		System.out.println(filepath+"\\student.xml");
		//createXml(filepath+"\\students0.xml");
	//	delXml(filepath+"\\students0.xml");
		//addXml(filepath+"\\students0.xml");
		//updateXml(filepath+"\\students0.xml");
		HashMap<String , String > hum = new HashMap<String , String>();
		
		readerXML(filepath+"\\student.xml", hum);
		for (String string : hum.keySet()) {
			System.out.println(string+"\t----"+hum.get(string));
		}
		for(int i = 0;i<hum.size();i+=6){
			int j=i/6;
			System.out.print(hum.get("name"+j)+"\t");
			System.out.print(hum.get("student-age"+j)+"\t");
			System.out.print(hum.get("college"+j)+"\t");
			System.out.print(hum.get("college-leader"+j)+"\t");
			System.out.print(hum.get("telephone"+j)+"\t");
			System.out.println(hum.get("notes"+j)+"\t");
			
		}
	}
	public static void readerXML(String filename ,HashMap<String , String > hum){
		SAXReader reader = new SAXReader();
		int num =-1;
		try {
			Document document = reader.read(new File(filename));
			Element root = document.getRootElement();
			for (Iterator ite = root.elementIterator();ite.hasNext();) {
				Element student =(Element) ite.next();
				num++;
				//age 
				Attribute attr = student.attribute("age");
				if (attr!=null) {
					String age = attr.getValue();
					if (age!=null&&!age.equals("")) {
						hum.put(student.getName()+"-"+attr.getName()+num, age);
					}else{
						hum.put(student.getName()+"-age"+num, "20");
					}
				}else{
					hum.put(student.getName()+"-age"+num, "20");
				}
				//遍历学生节点
				for (Iterator iter= student.elementIterator();iter.hasNext();) {
					Element element = (Element) iter.next();
					if (element.getName().equals("college")) {
						Attribute attribute = element.attribute("leader");
						if (attribute!=null) {
							String leader = attribute.getValue();
							if (leader!=null&&!leader.equals("")) {
								hum.put(element.getName()+"-"+attribute.getName()+num, leader);
							}else{
								hum.put(element.getName()+"-leader"+num, "leader");
							}
						}else{
							hum.put(element.getName()+"-leader"+num, "leader");
						}
					}
					hum.put(element.getName()+num, element.getText());
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * @Description: TODO 创建新的XML文件
	 * @param @param filename   XML路径
	 * @return void  
	 * @throws
	 * @author sky_mxc
	 * @date 2016年4月25日
	 */
	private static void createXml(String filename){
		Document document = DocumentHelper.createDocument();//创建XML文档对象
		Element root = document.addElement("students");//创建根节点
		Element student=root.addElement("student").addAttribute("age", "26");//根节点下创建元素 并且增加属性 age  值 26
		student.addElement("name").addText("刘导");
		student.addElement("college").addText("导演系");
		student.addElement("telephone").addText("1232343");
		student.addElement("notes").addText("刘导，专业动作片导演");
		//创建一个有缩进的格式对象
		OutputFormat formt = OutputFormat.createPrettyPrint();
		XMLWriter writer = null;//XML文件输出对象
		try {
			writer= new XMLWriter(new FileWriter(filename),formt);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("create succeed;");
	}
	/**
	 * 
	 * @Description: TODO 修改XML文件
	 * @param    filename   XML路径
	 * @return void  
	 * @throws
	 * @author sky_mxc
	 * @date 2016年4月25日
	 */
	private static void updateXml(String filename){
		SAXReader reader = new SAXReader();//创建saxReader 对象 用来读取文档
		try {
			//根据文件路径读取xml文档到内存中
			Document document = reader.read(new File(filename));
			Element root = document.getRootElement();
			//遍历根节点下所有子元素
			for (Iterator iterator = root.elementIterator();iterator.hasNext();) {
				Element element = (Element) iterator.next();
				if(element.element("name").getText().equals("刘导")){
					element.element("telephone").setText("8788888888888888888");
					System.out.println("update succeed;");
				}
			}
			//创建一个有缩进的格式对象
			OutputFormat formt = OutputFormat.createPrettyPrint();
			formt.setEncoding("utf-8");//设置字节码
			XMLWriter writer = null;//XML文件输出对象
			try {
				writer= new XMLWriter(new FileWriter(filename),formt);
				writer.write(document);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ok");
	}
	/**
	 * 
	 * @Description: TODO 追加内容
	 * @param @param filename   
	 * @return void  
	 * @throws
	 * @author sky_mxc
	 * @date 2016年4月25日
	 */
	private static void addXml(String filename){
		SAXReader reader = new SAXReader();//创建saxReader 对象 用来读取文档
		try {
			//根据文件路径读取xml文档到内存中
			Document document = reader.read(new File(filename));
			Element root = document.getRootElement();//获取根节点
			Element student=root.addElement("student").addAttribute("age", "21");//根节点下创建元素 并且增加属性 age  值 26
			student.addElement("name").addText("刘导");
			student.addElement("college").addText("导演系");
			student.addElement("telephone").addText("1232343434");
			student.addElement("notes").addText("刘导，专业动作片导演");
			//创建一个有缩进的格式对象
			OutputFormat formt = OutputFormat.createPrettyPrint();
			XMLWriter writer = null;//XML文件输出对象
			try {
				writer= new XMLWriter(new FileWriter(filename),formt);
				writer.write(document);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ok");
	}
	/**
	 * 
	 * @Description: TODO 删除XML文件
	 * @param    filename   XML路径
	 * @return void  
	 * @throws
	 * @author sky_mxc
	 * @date 2016年4月25日
	 */
	private static void delXml(String filename){
		SAXReader reader = new SAXReader();//创建saxReader 对象 用来读取文档
		try {
			//根据文件路径读取xml文档到内存中
			Document document = reader.read(new File(filename));
			Element root = document.getRootElement();
			//遍历根节点下所有子元素
			for (Iterator iterator = root.elementIterator();iterator.hasNext();) {
				Element element = (Element) iterator.next();
				if(element.element("name").getText().equals("刘导")){
					root.remove(element);
					System.out.println("remove succeed;");
				}
			}
			//创建一个有缩进的格式对象
			OutputFormat formt = OutputFormat.createPrettyPrint();
			formt.setEncoding("utf-8");//设置字节码
			XMLWriter writer = null;//XML文件输出对象
			try {
				writer= new XMLWriter(new FileWriter(filename),formt);
				writer.write(document);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ok");
	}
}
