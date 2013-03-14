package api.resources;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.transform.TransformerException;

import org.crosswire.common.util.Language;
import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookException;
import org.crosswire.jsword.passage.NoSuchKeyException;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import example.Main;

@Path("/bibles")
public class BibleResource {

	@Context HttpHeaders headers;
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getDefaultAllBibles(@DefaultValue("") @QueryParam("format") String format)
	{
		MediaType type;
		BibleCollection bibles = null;
		
		if(format.equalsIgnoreCase("xml"))
		{
			type = MediaType.APPLICATION_XML_TYPE;
		}
		else if(format.equalsIgnoreCase("json"))
		{
			type = MediaType.APPLICATION_JSON_TYPE;
		}
		else
		{
			type = headers.getMediaType();
		}
		
		try
		{
			bibles = getTODOAllBibles();
		}
		catch(Exception e)
		{
			
		}
		
		return Response.ok(bibles).type(type).build();
	}
	
	public BibleCollection getTODOAllBibles()
	{
		
		BibleCollection list = new BibleCollection();
		
		for(Book b: Main.getAllBibles())
		{
			Bible bible;
			
			bible = getTODOBible(b);
			
			list.add(bible);
		}
		
		return list;
	}
	
	@Path("/format/xml")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public BibleCollection getXMLAllBibles()
	{
		return getTODOAllBibles();
		
		/* USANDO XSTREAM EN VEZ DE JAXB, REQUIERE DOS JARS
		VER CARPETA DEPEDENCES_XSTEAM
		
		String xml = "";
		
		XStream xstream = new XStream();
	    xstream.alias("bible", Bible.class);
	    xstream.alias("bibles", Bibles.class);
	    xstream.addImplicitCollection(Bibles.class, "list");

	    xml = xstream.toXML(list);
	    
	    return xml;*/
	}
	
	@Path("/format/json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public BibleCollection getJSONAllBibles()
	{
		return getTODOAllBibles();
	}
	
	@Path("/{bibleInitial}")
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getDefaultBible(
			@PathParam("bibleInitial") String bibleInitial,
			@DefaultValue("") @QueryParam("format") String format
			//,@DefaultValue("") @QueryParam("key") String key
			)
	{
		MediaType type;
		Bible bible = null;
		
		if(format.equalsIgnoreCase("xml"))
		{
			type = MediaType.APPLICATION_XML_TYPE;
		}
		else if(format.equalsIgnoreCase("json"))
		{
			type = MediaType.APPLICATION_JSON_TYPE;
		}
		else
		{
			type = headers.getMediaType();
		}
		
		try
		{
			bible = getTODOBible(bibleInitial);
		}
		catch(Exception e)
		{
			
		}
		
		return Response.ok(bible).type(type).build();
	}
	
	public Bible getTODOBible(String id)
	{
		
		Book b = Main.getBible(id);
		
		return getTODOBible(b);
	}
	
	public Bible getTODOBible(Book b)
	{

		Bible bible = new Bible();
		
		//Initials
		bible.initials = b.getInitials();
		
		//Name	
		bible.name = b.getName();
		
		//Category
		bible.category = b.getBookCategory().getName();
		
		//Language
		Language lng = b.getLanguage();
		
		bible.language.name = lng.getName();
		bible.language.code = lng.getCode();
		bible.language.leftToRight = lng.isLeftToRight();
		
		//About
		String about = b.getProperty("About").toString();
		
		bible.about = about.replace("\\par", "").replaceAll("\\s+", " ");
		
		//Feature		
		bible.feature = (String) b.getProperty("Feature");
		
		//Enconding
		bible.enconding = (String) b.getProperty("Encoding");
		
		//License
		bible.license = (String) b.getProperty("DistributionLicense");

		return bible;
	}
	
	@Path("/{bibleInitial}/format/xml")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Bible getXMLBible(@PathParam("bibleInitial") String bibleInitial)
	{
		return getTODOBible(bibleInitial);
	}
	
	@Path("/{bibleInitial}/format/json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Bible getJSONBible(@PathParam("bibleInitial") String bibleInitial)
	{
		return getTODOBible(bibleInitial);
	}
	
	@Path("/{bibleInitial}/key/{keyid}")
	@GET
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	public Response getBibleKey(
			@PathParam("bibleInitial") String bibleInitial
			,@PathParam("keyid") String key
			,@DefaultValue("") @QueryParam("format") String format
			)
	{
		
		try
		{	
			Main ex = new Main();
			MediaType type;
			String result = "";
			
			if(format.equalsIgnoreCase("html"))
			{
				type = MediaType.TEXT_HTML_TYPE;
				result = ex.readStyledHTML(bibleInitial, key, 300);
				
			}
			else
			{
				type = MediaType.TEXT_PLAIN_TYPE;
				result = ex.getPlainText(bibleInitial, key);
			}
			
			return Response.ok(result).type(type).build();
			
		}
		catch(StringIndexOutOfBoundsException e)
		{
			return Response.status(404).build();
			//return "{\"err_msg\":\"\",\"err_code\":"+1+"}";
		}
		catch (NullPointerException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//return "{\"err_msg\":\"\",\"err_code\":"+1+"}";
		} 
		catch (BookException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//e.printStackTrace();	
		}
		catch (NoSuchKeyException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//return "{\"err_msg\":\"\",\"err_code\":"+1+"}";
		} 
		catch (TransformerException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//e.printStackTrace();
		}
		catch (SAXException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//e.printStackTrace();
		} 
	}
	
	@Path("/{bibleInitial}/key/{keyid}/format/html")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getHTMLBibleKey(
			@PathParam("bibleInitial") String bibleInitial
			,@PathParam("keyid") String key
			)
	{
		
		try
		{	
			Main ex = new Main();
			
			return Response.ok(ex.readStyledHTML(bibleInitial, key, 300)).type(MediaType.TEXT_HTML_TYPE).build();
			
		}
		catch(StringIndexOutOfBoundsException e)
		{
			return Response.status(404).build();
			//return "{\"err_msg\":\"\",\"err_code\":"+1+"}";
		}
		catch (NullPointerException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//return "{\"err_msg\":\"\",\"err_code\":"+1+"}";
		} 
		catch (BookException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//e.printStackTrace();	
		}
		catch (NoSuchKeyException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//return "{\"err_msg\":\"\",\"err_code\":"+1+"}";
		} 
		catch (TransformerException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//e.printStackTrace();
		}
		catch (SAXException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//e.printStackTrace();
		} 
	}
	
	@Path("/{bibleInitial}/key/{keyid}/format/txt")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getTXTBibleKey(
			@PathParam("bibleInitial") String bibleInitial
			,@PathParam("keyid") String key
			)
	{
		
		try
		{	
			Main ex = new Main();
			
			return Response.ok(ex.getPlainText(bibleInitial, key)).type(MediaType.TEXT_PLAIN_TYPE).build();
			
		}
		catch(StringIndexOutOfBoundsException e)
		{
			return Response.status(404).build();
			//return "{\"err_msg\":\"\",\"err_code\":"+1+"}";
		}
		catch (NullPointerException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//return "{\"err_msg\":\"\",\"err_code\":"+1+"}";
		} 
		catch (BookException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//e.printStackTrace();	
		}
		catch (NoSuchKeyException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//return "{\"err_msg\":\"\",\"err_code\":"+1+"}";
		} 
	}
	
	@Path("/{bibleInitial}/search/{searchid}")
	@GET
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	public Response getBibleSearch(
			@PathParam("bibleInitial") String bibleInitial
			,@PathParam("searchid") String search
			,@DefaultValue("") @QueryParam("format") String format
			)
	{
		
		try
		{	
			Main ex = new Main();
			MediaType type;
			String result = "";
			
			if(format.equalsIgnoreCase("html"))
			{
				type = MediaType.TEXT_HTML_TYPE;
				
			}
			else
			{
				type = MediaType.TEXT_PLAIN_TYPE;
			}
			
			result = ex.search(bibleInitial,search);
			
			return Response.ok(result).type(type).build();
			
		}
		catch(StringIndexOutOfBoundsException e)
		{
			return Response.status(404).build();
			//return "{\"err_msg\":\"\",\"err_code\":"+1+"}";
		}
		catch (NullPointerException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//return "{\"err_msg\":\"\",\"err_code\":"+1+"}";
		} 
		catch (BookException e)
		{
			// TODO Auto-generated catch block
			return Response.status(404).build();
			//e.printStackTrace();	
		} 
	}

	/*
	//The Java method will process HTTP GET requests
	@GET 
	//The Java method will produce content identified by the MIME Media
	@Produces("application/json")
	public String getResource(
			@DefaultValue("bible") @QueryParam("collection") String collection,
			@DefaultValue("kjv") @QueryParam("resource") String resource)
	{
		
		return collection+" "+resource;
		//Let's create a JSON array
		JSONArray bibles = new JSONArray();
		
		try
		{
			for(Book b: Main.getAllBibles())
			{
				JSONObject obj = new JSONObject();
			
				obj.put("initials", b.getInitials());
				obj.put("name", b.getName());
				
				bibles.put(obj);
			}

			//System.out.print(bibles);
			StringWriter out = new StringWriter();
		
			bibles.write(out);
			String jsonText = out.toString();

			//System.out.print(jsonText);
			return jsonText;
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}*/
	

	/*public String getJSONAllBibles()
	{
		//Let's create a JSON array
		JSONArray bibles = new JSONArray();
		
		try
		{
			for(Book b: Main.getAllBibles())
			{
				JSONObject obj = new JSONObject();
			
				obj.put("initials", b.getInitials());
				obj.put("name", b.getName());
				obj.put("language", b.getLanguage().toString());
				bibles.put(obj);
			}

			//System.out.print(bibles);
			StringWriter out = new StringWriter();
		
			bibles.write(out);
			String jsonText = out.toString();

			//System.out.print(jsonText);
			return jsonText;
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	@Path("/{bibleInitial}/json")
	@GET
	@Produces("application/json")
	public Bible getJSONBible(@PathParam("bibleInitial") String bibleID)
	{
		try
		{
			JSONObject obj = new JSONObject();
			
			Book bible = Main.getBible(work);
			
			//Initials
			//obj.put("initials", bible.getInitials());
			
			//Name
			obj.put("name", bible.getName());
			
			//Category
			obj.put("category", bible.getBookCategory());
			
			//Language
			JSONObject languageJson = new JSONObject();
			Language lng = bible.getLanguage();
			
			languageJson.put("name", lng.getName());
			languageJson.put("code", lng.getCode());
			languageJson.put("leftToRight", lng.isLeftToRight());
			
			obj.put("language", languageJson);
			
			//About
			String about = bible.getProperty("About").toString();
			
			obj.put("about", about.replace("\\par", "").replaceAll("\\s+", " "));
			
			//Feature
			obj.put("feature", bible.getProperty("Feature"));
			
			//Enconding
			obj.put("enconding", bible.getProperty("Encoding"));
			
			//License
			obj.put("license", bible.getProperty("DistributionLicense"));
			
			System.out.print(bible.toString());
			//System.out.print(obj.toString());
			return obj.toString();
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			// TODO Auto-generated catch block
			return "{\"err_msg\":\"\",\"err_code\":"+1+"}";
		}
		
		return null;
	}
	
	*/
}
