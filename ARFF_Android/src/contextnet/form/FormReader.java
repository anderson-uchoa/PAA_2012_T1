package contextnet.form;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author andremd
 * 
 * Reader for a vehicle type Form
 * docMainEle: veiculo (labelParser)
 * label descriptions: name (getItemName)
 * 
 */
public class FormReader extends XmlHandler {
	
	private XmlHandler fh;
	private InputStream fos;
	private List<List<Node>> lNl;
	private int nivelHierarquico;
	public List<String> labels;
	
	/* Constructor */
	public FormReader() {
		fh = new XmlHandler();
		fos = null;
		//fh.parseXmlFile(fos);
		labels = new ArrayList<String>();
		lNl = new ArrayList<List<Node>>();
		nivelHierarquico = 0;
	}
	
	/* Methods */
	public void setFileStream(InputStream fos)
	{
		this.fos = fos;
		fh.parseXmlFile(this.fos);
	}
	
	public void labelParser(int hie) 
	{
		if(fos == null) return;
		
		labels = new ArrayList<String>();
		nivelHierarquico = hie;
		Element docMainEle = fh.getDom().getDocumentElement();
		docMainEle.getElementsByTagName("veiculo");
		
		NodeList aux = docMainEle.getChildNodes();
		lNl.add(nivelHierarquico, new ArrayList<Node>());
		for(int i = 0; i < aux.getLength(); i++)
			lNl.get(nivelHierarquico).add(aux.item(i));
		
		if(lNl.get(nivelHierarquico) != null && lNl.get(nivelHierarquico).size() > 0) 
		{
			for(int i = 0 ; i < lNl.get(nivelHierarquico).size();i++) 
				labels.add(getItemName(i));
		}
	}
	
	public List<String> genericLabelParser(NodeList all)
	{
		List<String> names = new ArrayList<String>();
		lNl.add(nivelHierarquico, new ArrayList<Node>());
		
		for(int i = 0 ; i < all.getLength();i++)
			lNl.get(nivelHierarquico).add(all.item(i));
		
		if(lNl.get(nivelHierarquico) != null && lNl.get(nivelHierarquico).size() > 0) 
		{
			for(int i = 0 ; i < lNl.get(nivelHierarquico).size();i++) 
				names.add(getItemName(i));
		}
		
		return names;
	}
	
	public List<String> genericIdParser(NodeList all)
	{
		List<String> ids = new ArrayList<String>();
		List<Node> aux = new ArrayList<Node>();
		
		for(int i = 0 ; i < all.getLength();i++)
			aux.add(all.item(i));
		
		if(aux != null && aux.size() > 0) 
		{
			for(int i = 0 ; i < aux.size();i++) 
				ids.add(getItemId(i));
		}
		
		return ids;
	}
	
	public void incrementHierarchy()
	{
		nivelHierarquico++;
	}
	
	public void decrementHierarchy()
	{
		nivelHierarquico--;
	}
	
	public Node getNode(int pos)
	{
		return lNl.get(nivelHierarquico).get(pos);
	}
	
	public String getItemId(int pos)
	{
		return ((Element)lNl.get(nivelHierarquico).get(pos)).getAttribute("id");
	}
	
	public String getItemOrientation(int pos)
	{
		return ((Element)lNl.get(nivelHierarquico).get(pos)).getAttribute("orientation");
	}
	
	public String getItemType(int pos)
	{
		return ((Element)lNl.get(nivelHierarquico).get(pos)).getAttribute("type");
	}
	
	public String getItemName(int pos)
	{
		return ((Element)lNl.get(nivelHierarquico).get(pos)).getAttribute("name");
	}
	
	public String getItemLength(int pos)
	{
		return ((Element)lNl.get(nivelHierarquico).get(pos)).getAttribute("length");
	}

	public List<String> genericHashParser(HashMap<String,String> h) {
		
		List<String> subNames = new ArrayList<String>();
		
		for(int i = 0; i < lNl.get(nivelHierarquico).size(); i++)
		{
			boolean flag = false;
			for(int j = 0; j < lNl.get(nivelHierarquico).size(); j++)
			{
				String s1 = getItemId(i);
				String s2 = getItemId(j);
				String tp = getItemType(j);
				if(tp.equals("bool") && s1.equals(s2))
					flag = true;
			}
			if(flag)
			{
				if(h.get(getItemId(i)).equals("true"))
					subNames.add("APROVADO");
				else if(h.get(getItemId(i)).equals("null"))
					subNames.add("NÃO APLICAVEL");
				else if(h.get(getItemId(i)).equals("false"))
					subNames.add("NÃO REALIZADO");
				else
					subNames.add("");
			}
			else
				subNames.add(h.get(getItemId(i)));

		}
		return subNames;
	}
}
