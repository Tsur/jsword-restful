package api.resources;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="bibles")
public class BibleCollection{
	
	//@XmlElementWrapper(name="bibles")
    public List<Bible> bible = new ArrayList<Bible>();
	
	public BibleCollection(){}
	
    public void add(Bible b)
    {
        bible.add(b);
    }
}