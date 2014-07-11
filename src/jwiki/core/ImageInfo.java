package jwiki.core;

/**
 * Container object for results returned by imageinfo MediaWiki module.
 * 
 * @author Fastily
 * 
 */
public class ImageInfo
{
	/**
	 * The image size (in bytes)
	 */
	public final int size;
	
	/**
	 * The image width (in pixels)
	 */
	public final int width;
	
	/**
	 * The image height (in pixels)
	 */
	public final int height;
	
	/**
	 * A URL to the full size image.
	 */
	public final String url;
	
	/**
	 * A URL to a thumbnail (if you requested it, otherwise null)
	 */
	public final String thumburl;
	
	/**
	 * Constructor, takes a JSONObject containing image info returned by the server.
	 * 
	 * @param jo The JSONObject to use.
	 */
	protected ImageInfo(ServerReply r)
	{
		size = r.getIntR("size"); 
		width = r.getIntR("width");
		height = r.getIntR("height");
		url = r.getStringR("url");
		thumburl = r.getStringR("thumburl");
	}
	
	/**
	 * Gets a String representation of this object.
	 * 
	 * @return A String representation of this object.
	 */
	public String toString()
	{
		return String.format("----%nSize: %d%nWidth: %d%nHeight %d%nURL: %s%nThumbURL: %s%n----%n", size, width, height, url,
				thumburl);
	}
}