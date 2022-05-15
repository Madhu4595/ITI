import javax.xml.parsers.*;
import org.w3c.dom.*;
 
public class ParseXMLDOM 
{ 
    public static void main(String[] args)
    {
        String url = "http://bseapwebdata.org/SSCDATAWEB.svc/rh/SSCData?RollNo=1328617522&Year=2013&Stream=A";   
        try
        {
            DocumentBuilderFactory f = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(url);
 
            doc.getDocumentElement().normalize();
            System.out.println ("Root element: " + 
                        doc.getDocumentElement().getNodeName());
       
            // loop through each item
            NodeList items = doc.getElementsByTagName("NewDataSet");
            for (int i = 0; i < items.getLength(); i++)
            {
                Node n = items.item(i);
                if (n.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                Element e = (Element) n;
 
                // get the "title elem" in this item (only one)
                NodeList titleList = 
                                e.getElementsByTagName("ROLLNO");
                Element titleElem = (Element) titleList.item(0);
 
                // get the "text node" in the title (only one)
                Node titleNode = titleElem.getChildNodes().item(0);
                System.out.println(titleNode.getNodeValue());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}