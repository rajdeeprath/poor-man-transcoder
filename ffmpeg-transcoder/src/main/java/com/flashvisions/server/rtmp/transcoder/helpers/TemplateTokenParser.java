package com.flashvisions.server.rtmp.transcoder.helpers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class TemplateTokenParser {
	
	
	private static final Map<String, String> VARIABLEMAP;
    static
    {
    	VARIABLEMAP = new HashMap<String, String>();
    	VARIABLEMAP.put("${SourceApplication}", "SourceApplication");
    	VARIABLEMAP.put("${SourceStreamName}", "SourceStreamName");
    	VARIABLEMAP.put("${homeDirectory}", "HomeDirectory");
    }
	
	@SuppressWarnings("rawtypes")
	public static void updateDocumentWithVariables(Document document, XPath xpath) throws XPathExpressionException
	{
		Iterator it = VARIABLEMAP.entrySet().iterator();
		
		while (it.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)it.next();
			String xPathExpression = "//*[contains(text(), '" + pair.getKey() + "')]";
			NodeList nodes = (NodeList) xpath.evaluate(xPathExpression, document, XPathConstants.NODESET);

	        for (int idx = 0; idx < nodes.getLength(); idx++) {
	        	String variableName = (String) pair.getKey();
	        	String replacementName = (String) pair.getValue();
	        	String original = nodes.item(idx).getTextContent();
	        	nodes.item(idx).setTextContent(original.replace(variableName , replacementName));
	        } 	
		}
	}
}
