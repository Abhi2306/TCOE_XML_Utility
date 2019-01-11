package xmlconfig;

import java.io.IOException;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DeleteElement {

	static Properties prop = WriteXML.prop;
	static Document doc = WriteXML.doc;

	public static int DelElement(String FilePath_Prop, String FileName_Prop) throws IOException {

		int count = 0;

		prop = ReadXML.Read_Data_From_Properties(FilePath_Prop, FileName_Prop);

		NodeList books = doc.getElementsByTagName(prop.getProperty("tag"));

		Element book_1 = null;

		String att_value = null;

		Node tag_name = null;

		for (int i = 0; i < books.getLength(); i++) {

			book_1 = (Element) books.item(i);

			att_value = book_1.getAttribute(prop.getProperty("Attribute_Name"));

			tag_name = book_1.getElementsByTagName(prop.getProperty("tag_name")).item(0);

			if (prop.getProperty("Remove_Tag_Across_XML").equalsIgnoreCase("Y")) {

				try {

					book_1.removeChild(tag_name);
					count++;
				} catch (Exception e) {

					System.out.println("Tag must already be deleted or not present at all..!!");
				}
			} else {

				if (att_value.equalsIgnoreCase(prop.getProperty("Attribute_Value"))) {

					try {

						book_1.removeChild(tag_name);
						count++;
					} catch (Exception e) {

						System.out.println("Tag must already be deleted or not present at all..!!");
					}
				}
			}
		}
		return count;

	}

}
