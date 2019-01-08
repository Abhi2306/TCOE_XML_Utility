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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WriteXML {

	static Properties prop;
	static Document doc;
	
	public static void WriteXMLData(String FilePath, String FilePath_Prop, String FileName_XML, String FileName_Prop)
			throws ParserConfigurationException, IOException, SAXException, TransformerException {

		DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder Builder = Factory.newDocumentBuilder();
		
		File file = new File(FilePath + "\\" + FileName_XML);
		doc = Builder.parse(file);

		doc.getDocumentElement().normalize();

		// To read data from properties file
		prop = ReadXML.Read_Data_From_Properties(FilePath_Prop, FileName_Prop);

		WriteXML.AddElement(FilePath, FilePath_Prop, FileName_XML, FileName_Prop);

		WriteXML.UpdateElementValue(FilePath, FilePath_Prop, FileName_XML, FileName_Prop);
		
		WriteXML.UpdateAttributeValue(FilePath, FilePath_Prop, FileName_XML, FileName_Prop);
		
		
		//To append the updates in XML file
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, result);
		
		System.out.println("XML File updated successfully..!!");

	}

	//For adding a new Element in the XML
	
	public static void AddElement(String FilePath, String FilePath_Prop, String FileName_XML,
			String FileName_Prop) throws IOException {

		prop = ReadXML.Read_Data_From_Properties(FilePath_Prop, FileName_Prop);

		NodeList books = doc.getElementsByTagName(prop.getProperty("tag"));

		Element book_1 = null;
		for (int i = 0; i < books.getLength(); i++) {

			book_1 = (Element) books.item(i);
			Element discount = doc.createElement(prop.getProperty("discount_tagname"));
			discount.appendChild(doc.createTextNode(prop.getProperty("discount_value")));
			book_1.appendChild(discount);
		}
	}

	//For updating Element Value in the file
	
	public static void UpdateElementValue(String FilePath, String FilePath_Prop, String FileName_XML,
			String FileName_Prop) throws IOException {

		prop = ReadXML.Read_Data_From_Properties(FilePath_Prop, FileName_Prop);

		NodeList books = doc.getElementsByTagName(prop.getProperty("tag"));

		Element book_1 = null;
		String tag_name = prop.getProperty("tag_name");
		String tag_name_value = prop.getProperty("tag_name_value");
		String tag_value = prop.getProperty("tag_value");
		String Attribute_Value = null;
		
		// Access all the elements which are having "tag" as there value
		for (int i = 0; i < books.getLength(); i++) {

			book_1 = (Element) books.item(i);

			// Access the attribute value of specified "tag"
			Attribute_Value = book_1.getAttribute(prop.getProperty("Attribute_Name"));
			
			Node Element_name = book_1.getElementsByTagName(tag_name).item(0).getFirstChild();
			
			if (Attribute_Value.equalsIgnoreCase(tag_value) && Element_name.getNodeValue().equalsIgnoreCase(tag_name_value)) {
				
				Element_name.setNodeValue("Abhishek");
			}
		}
	}

	// For Updating value of an attribute present in a tag
	
	public static void UpdateAttributeValue(String FilePath, String FilePath_Prop, String FileName_XML,
			String FileName_Prop) throws IOException {

		prop = ReadXML.Read_Data_From_Properties(FilePath_Prop, FileName_Prop);

		NodeList books = doc.getElementsByTagName(prop.getProperty("tag"));
		
		Element book_1 = null;
		String Attribute_Value = null;
		
		for(int i=0; i<books.getLength();i++) {
			
			book_1 = (Element) books.item(i);
			Attribute_Value = book_1.getAttribute(prop.getProperty("Attribute_Name"));
			
			if(Attribute_Value.equalsIgnoreCase(prop.getProperty("tag_value"))) {
				
				book_1.setAttribute(prop.getProperty("Attribute_Name"), prop.getProperty("set_value"));
			}else {
				continue;
			}	
		}	
	}

}
