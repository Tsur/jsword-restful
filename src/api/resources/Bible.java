package api.resources;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
public class Bible{

	public String initials 			= "";
	public String name 				= "";
	public String category 			= "";
	public Lng language 			= new Lng();
	public String about 			= "";
	public String feature 			= "";
	public String enconding 		= "";
	public String license 			= "";
	
	@XmlRootElement(name = "language")
	//@XmlAccessorType(XmlAccessType.FIELD)
	//@XmlType(name = "language"/*, propOrder = {"street"}*/)
	//@XmlElementDecl(namespace = "http://www.example.org/customer", name = "billing-address")
	public static class Lng{
		
		public String name = "";
		public String code = "";
		public Boolean leftToRight;
	}

}
