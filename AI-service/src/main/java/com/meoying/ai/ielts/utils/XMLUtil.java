package com.meoying.ai.ielts.utils;

import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class XMLUtil {

    public static final String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";

    private XMLUtil() {

    }

    public static Map<String, String> getMapFromXML(String xmlString)
            throws ParserConfigurationException, IOException, SAXException {
        if (StringUtils.isEmpty(xmlString)) {
            throw new IllegalStateException("第三方响应结果为空，无法解析");
        }

        // 这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 不允许DTDs (doctypes) ,几乎可以阻止所有的XML实体攻击
        factory.setFeature(FEATURE, true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        String xmlUtf8 = new String(xmlString.getBytes(), "UTF-8");
        InputStream is = new ByteArrayInputStream(xmlUtf8.getBytes());
        Document document = builder.parse(is);

        // 获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, String> map = new HashMap<>();
        int i = 0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if (node instanceof Element) {
                map.put(node.getNodeName(), node.getTextContent());
            }
            i++;
        }
        return map;

    }

    /**
     * @param reqMap
     * @return
     */
    public static String mapToXmlStr(Map<String, String> reqMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (String key : reqMap.keySet()) {
            sb.append("<").append(key).append(">").append("<![CDATA[").append(reqMap.get(key)).append("]]>")
                    .append("</").append(key).append(">");

        }
        sb.append("</xml>");
        return sb.toString();
    }

}
