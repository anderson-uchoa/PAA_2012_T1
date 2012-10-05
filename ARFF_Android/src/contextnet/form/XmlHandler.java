package contextnet.form;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/** 
 * XmlReader Class 
 * @author andremd
 * 
 * Description: This class is used to simulate the loading of a specific data from
 *  			 a xml file and to display it. 
 *  
 *  */
public class XmlHandler {

	private Document dom;
	private boolean xmlIntegrity;

	public void parseXmlFile(InputStream fos) {
		//get the factory
		this.xmlIntegrity = true;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setIgnoringComments(true);
		
		try {
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			dom = db.parse(fos);

		}
		catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		catch(SAXException se) {
			this.xmlIntegrity = false;
			se.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public Document getDom()	{
		return this.dom;
	}
	
	public boolean getXmlIntegrity()	{
		return this.xmlIntegrity;
	}

}