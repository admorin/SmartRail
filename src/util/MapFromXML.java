package util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/***
 * Example of how the XML code looks.
 *
 *  <railroad> <--the root element
 *       <pos id="0"> <--pos is child element, id is attribute
 *           <component>station:A</component> <--child element without attribute
 *       </pos>
 *   </railroad>
 */
public class MapFromXML
{
    //TODO find a max size or somehow allocate appropriate space
    private String[] railroad = new String[8];

    public MapFromXML()
    {
        try
        {
            File xmlFile = new File("resources\\map.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            //System.out.println("Root element : " + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("pos");

            //  System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) nNode;
                    int rrIndex = Integer.parseInt(eElement.getAttribute("id"));
                    railroad[rrIndex] = eElement.getElementsByTagName("component").item(0).getTextContent();
                    //System.out.println("Pos: " + eElement.getAttribute("id") + " | Component : " + eElement.getElementsByTagName("component").item(0).getTextContent());
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String[] getMap()
    {
        return railroad;
    }

}