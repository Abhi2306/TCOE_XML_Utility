package xmlconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class ReadXML {

	static Properties prop;
	static Document doc;

	// Generic method to create object of properties file
	public static Properties Read_Data_From_Properties(String FilePath_Prop, String FileName_Prop) throws IOException {

		prop = new Properties();
		File file = new File(System.getProperty("user.dir")+"\\"+FilePath_Prop + "\\" + FileName_Prop);
		FileInputStream inputStream = new FileInputStream(file);

		prop.load(inputStream);

		return prop;
	}

	public static String ReadXMLData(String FilePath, String FilePath_Prop, String FileName_XML, String FileName_Prop) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder Builder = factory.newDocumentBuilder();

		File file = new File(System.getProperty("user.dir")+"\\"+FilePath + "\\" + FileName_XML);

		// To read data from properties file
		prop = ReadXML.Read_Data_From_Properties(FilePath_Prop, FileName_Prop);

		String expression = prop.getProperty("xpath");

		// To parse the XML file
		Document document = Builder.parse(file);

		System.out.println(document.getDocumentElement().getNodeName());

		// Create an instance of XPATH for generating new XPATH.
		XPath xpath = XPathFactory.newInstance().newXPath();

		System.out.println(expression);

		// to evaluate the content present at the provided XPATH
		Node node = (Node) xpath.compile(expression).evaluate(document, XPathConstants.NODE);

		String testInput = node != null ? node.getTextContent() : "Cannot Read the tag from provided xpath";

		return testInput;	
	}
}
