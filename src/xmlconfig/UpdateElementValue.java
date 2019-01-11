package xmlconfig;

import java.io.IOException;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UpdateElementValue {

	static Properties prop = WriteXML.prop;
	static Document doc = WriteXML.doc;
	
	public static int UpdateElmntValue(String FilePath_Prop,String FileName_Prop) throws IOException {

		prop = ReadXML.Read_Data_From_Properties(FilePath_Prop, FileName_Prop);

		NodeList books = doc.getElementsByTagName(prop.getProperty("tag"));

		Element book_1 = null;
		String tag_name = prop.getProperty("tag_name");
		String tag_name_value = prop.getProperty("tag_name_value");
		String Attribute_value = prop.getProperty("Attribute_Value");
		String Attr_Value = null;
		int count = 0;
		// Access all the elements which are having "tag" as there value
		for (int i = 0; i < books.getLength(); i++) {

			book_1 = (Element) books.item(i);

			// Access the attribute value of specified "tag"
			Attr_Value = book_1.getAttribute(prop.getProperty("Attribute_Name"));

			Node Element_name = book_1.getElementsByTagName(tag_name).item(0).getFirstChild();

			if (Attr_Value.equalsIgnoreCase(Attribute_value)
					&& Element_name.getNodeValue().equalsIgnoreCase(tag_name_value)) {

				Element_name.setNodeValue("TCOE_Framework");
				count++;
			}
		}
		return count;
	}

}
