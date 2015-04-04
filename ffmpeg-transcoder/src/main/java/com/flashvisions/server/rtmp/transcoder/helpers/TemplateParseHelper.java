package com.flashvisions.server.rtmp.transcoder.helpers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import com.flashvisions.server.rtmp.transcoder.vo.TranscoderSystem;

public class TemplateParseHelper {
	
	private static final Map<String, String> VARIABLEMAP;
    static
    {
    	VARIABLEMAP = new HashMap<String, String>();
    	VARIABLEMAP.put("${ImageWidth}", "ImageWidth");
    	VARIABLEMAP.put("${ImageHeight}", "ImageHeight");
    	VARIABLEMAP.put("${SourceApplication}", "SourceApplication");
    	VARIABLEMAP.put("${SourceStreamName}", "SourceStreamName");
    	VARIABLEMAP.put("${workingDirectory}", TranscoderSystem.getEnv(TranscoderSystem.Vars.WORKING_DIRECTORY));
    	VARIABLEMAP.put("${templateDirectory}", TranscoderSystem.getEnv(TranscoderSystem.Vars.TEMPLATE_DIRECTORY));
    }
	

	public static int evaluateExpressionForInt(String expression, String variable, double value)
	{
		Expression we = new ExpressionBuilder(expression)
        .variables(variable).build().setVariable(variable, value);
        Double width = we.evaluate();
        
        return width.intValue();
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
