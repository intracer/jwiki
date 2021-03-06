package fastily.jwiki.core;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import fastily.jwiki.util.FString;

/**
 * Builds a URL with the specified domain. Retains state and is easily modifiable, so it can be used to make multiple,
 * similar queries.
 * 
 * @author Fastily
 * 
 */
public final class URLBuilder
{
	/**
	 * The base of the URL, constructed with the domain.
	 */
	private String base;

	/**
	 * The parameter list to append to the URL.
	 */
	private HashMap<String, String> pl = new HashMap<>();

	/**
	 * Constructor, takes the domain name we'll be working with.
	 * @param domain The domain name to use, in shorthand (e.g. 'commons.wikimedia.org').
	 * @param action Sets the action param to use in the final URL. (e.g. query, edit, delete)
	 * @param pl The parameter list to apply to this URLBuilder.  These should NOT be URL-encoded.
	 */
	protected URLBuilder(String domain, String action, HashMap<String, String> pl)
	{
		base = Settings.comPro + domain + "/w/api.php?format=json&action=" + action;

		if (pl != null)
			setParams(pl);
	}

	/**
	 * Sets a parameter pair in this URLBuilder. CAVEAT: This method automatically URL-encodes Strings, so don't pass in
	 * URLEncoded Strings!
	 * 
	 * @param p1 The key
	 * @param p2 The value
	 */
	protected void setParam(String p1, String p2)
	{
		pl.put(p1, FString.enc(p2));
	}

	/**
	 * Set the parameter list of this object with the key-value pairs in the specified HashMap. Note that subsequent
	 * calls of this method will not overwrite keys-value pairs that are not named.
	 * 
	 * @param params The key-value pairs to set the object's parameter list with.
	 */
	protected void setParams(HashMap<String, String> params)
	{
		for (Map.Entry<String, String> e : params.entrySet())
			setParam(e.getKey(), e.getValue());
	}

	/**
	 * Makes a URL using the current state of the object.
	 * 
	 * @return A URL based off the state of the object, or null if something went wrong.
	 */
	protected URL makeURL()
	{
		try
		{
			return new URL(base + FString.makeURLParamString(pl));
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			return null;
		}
	}
}