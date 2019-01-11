package xmlconfig;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class WriteXML {

	static Properties prop;
	static Document doc;

	/*
	 * public static void main(String[] args) throws XPathExpressionException,
	 * ParserConfigurationException, IOException, SAXException, TransformerException
	 * {
	 * 
	 * String FileName_XML = "SampleXML.xml"; String FileName_Prop =
	 * "Configuration.properties"; String FilePath = "src\\main\\java\\Resources";
	 * String FilePath_Prop = "src\\main\\java\\Configuration";
	 * 
	 * WriteXML.WriteXMLData(FilePath, FilePath_Prop, FileName_XML, FileName_Prop);
	 * 
	 * }
	 */

	public static int[] WriteXMLData(String FilePath, String FilePath_Prop, String FileName_XML, String FileName_Prop)
			throws ParserConfigurationException, IOException, SAXException, TransformerException,
			XPathExpressionException {

		DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder Builder = Factory.newDocumentBuilder();

		// File file = new
		// File("C:\\Users\\abhishek.khatod\\Documents\\eclipse_workspace\\yash.TCOEFrameWork\\src\\main\\java\\Resources"+"\\"
		// + FileName_XML);

		File file = new File(System.getProperty("user.dir") + "\\" + FilePath + "\\" + FileName_XML);
		doc = Builder.parse(file);

		doc.getDocumentElement().normalize();

		int count_add = 0, count_update_ele = 0, count_update_att = 0, count_delete_element = 0;

		// To read data from properties file
		prop = ReadXML.Read_Data_From_Properties(FilePath_Prop, FileName_Prop);

		if (prop.getProperty("add_element_xml").equalsIgnoreCase("Y")) 
			count_add = AddElement.AddElmnt(FilePath, FilePath_Prop, FileName_XML, FileName_Prop);

		if (prop.getProperty("update_element_xml").equalsIgnoreCase("Y"))
			count_update_ele = UpdateElementValue.UpdateElmntValue(FilePath_Prop, FileName_Prop);

		if (prop.getProperty("update_attribute_xml").equalsIgnoreCase("Y"))
			count_update_att = UpdateAttributeValue.UpdateAttrValue(FilePath_Prop, FileName_Prop);

		if (prop.getProperty("delete_element").equalsIgnoreCase("Y"))
			count_delete_element = DeleteElement.DelElement(FilePath_Prop, FileName_Prop);

		// To append the updates in XML file

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, result);

		int[] count_array = new int[] { count_add, count_delete_element, count_update_att, count_update_ele };

		/*
		 * if (count_add > 0 && count_update_ele > 0 && count_update_att > 0) {
		 * 
		 * System.out.println("XML File updated successfully..!!"); } else {
		 * 
		 * System.out.
		 * println("Issue with one of the operations, please check as below :\n Add Operation :"
		 * + count_add + "\n Update Element :" + count_update_ele +
		 * "\n Update Attribute :" + count_update_att + "\n Delete Element :" +
		 * count_delete_element);
		 * 
		 * }
		 */

		return count_array;
	}
}