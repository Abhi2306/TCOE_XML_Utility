package xmlconfig;

import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AddElement {
	
	static Properties prop = WriteXML.prop;
	static Document doc = WriteXML.doc;

	public static int AddElmnt(String FilePath, String FilePath_Prop, String FileName_XML, String FileName_Prop)
			throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {

		prop = ReadXML.Read_Data_From_Properties(FilePath_Prop, FileName_Prop);

		NodeList books = doc.getElementsByTagName(prop.getProperty("tag"));

		Element book_1 = null, tagName_Added = null;

		int count = 0;
		
		String Att_Value=null;
		
		String Search_Tag = ReadXML.ReadXMLData(FilePath, FilePath_Prop, FileName_XML, FileName_Prop);
		
		//Opt between adding tags across XML or adding in a specific node
		
		if(prop.getProperty("Add_Tag_Across_XML").equalsIgnoreCase("Y")) {
			
			for (int i = 0; i < books.getLength(); i++) {

				int count_add = 0;
				book_1 = (Element) books.item(i);
				NodeList tagname_list = book_1.getElementsByTagName(prop.getProperty("tagname_added"));
				
				if(tagname_list.getLength()==0) {
					
					count_add++;
				}
				
				//System.out.println("tagname : "+tagname.getNodeValue()+":"+tagname.toString().equalsIgnoreCase(null));
				
				//String att_value = book_1.getAttribute(prop.getProperty("Attribute_Name"));
				
				if(count_add>0) {
					
					tagName_Added = doc.createElement(prop.getProperty("tagname_added"));
					tagName_Added.appendChild(doc.createTextNode(prop.getProperty("tagname_value")));
					book_1.appendChild(tagName_Added);
					count++;
				}
				else {
					
					continue;
				}
			}
		}else {
			
			if(Search_Tag.contains("Cannot Read the tag from provided xpath")) {
				
				for(int i=0;i<books.getLength();i++) {
					
					book_1 = (Element) books.item(i);
					Att_Value = book_1.getAttribute(prop.getProperty("Attribute_Name"));
					
					if(Att_Value.equalsIgnoreCase(prop.getProperty("Attribute_Value"))) {
						
						tagName_Added = doc.createElement(prop.getProperty("tagname_added"));
						tagName_Added.appendChild(doc.createTextNode(prop.getProperty("tagname_value")));
						book_1.appendChild(tagName_Added);
						count++;
					}
				}
			}else {
				
				System.out.println("Tag must already be there..!!");
			}
		}
		return count;
	}

}
