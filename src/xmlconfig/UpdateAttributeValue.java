package xmlconfig;

import java.io.IOException;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class UpdateAttributeValue {

	static Properties prop = WriteXML.prop;
	static Document doc = WriteXML.doc;
	
	public static int UpdateAttrValue(String FilePath_Prop,String FileName_Prop) throws IOException {

		prop = ReadXML.Read_Data_From_Properties(FilePath_Prop, FileName_Prop);

		NodeList books = doc.getElementsByTagName(prop.getProperty("tag"));

		Element book_1 = null;
		String Attribute_Value = null;
		int count = 0;

		for (int i = 0; i < books.getLength(); i++) {

			book_1 = (Element) books.item(i);
			Attribute_Value = book_1.getAttribute(prop.getProperty("Attribute_Name"));

			if (Attribute_Value.equalsIgnoreCase(prop.getProperty("Attribute_Value"))) {

				book_1.setAttribute(prop.getProperty("Attribute_Name"), prop.getProperty("set_value"));
				count++;
			} else {
				
				continue;
			}
		}
		return count;
	}

}
