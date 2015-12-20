package jwiki.core;

import java.util.ArrayList;
import java.util.stream.Stream;

import jwiki.util.FL;
import jwiki.util.Tuple;

/**
 * High level functions to select and extract values from a list Reply objects. Each RSet is basically just a list of
 * replies from the server. This is intended to be a sane replacement for QueryTools.
 * 
 * @author Fastily
 *
 */
public class RSet
{
	private ArrayList<Reply> rl;

	/**
	 * Constructors disallowed
	 */
	protected RSet(ArrayList<Reply> rl)
	{
		this.rl = rl;
	}

	//TODO: TEMPORARY TODO TRACKER
	protected ArrayList<Reply> getRL()
	{
		return rl;
	}
	
	/**
	 * Selects an &lt;Integer, String&gt; from JSONObjects in a Reply. Example usage: <code>siteinfo</code> →
	 * <code>namespace</code>.
	 * 
	 * @param base The key pointing to the JSONObject that contains JSONObjects in each Reply of the RSet.
	 * @param key1 The key in each selected JSONObject whose value is the Integer
	 * @param key2 The key in each selected JSONObject whose value is the String
	 * @return A list of &lt;Integer, String&gt; found in the list of JSONObjects
	 */
	protected ArrayList<Tuple<Integer, String>> intStringFromJO(String base, String key1, String key2)
	{
		return FL.toAL(rl.stream().map(r -> r.bigJSONObjectGet(base)).flatMap(l -> l.stream())
				.map(jo -> new Tuple<>(jo.getInt(key1), jo.getString(key2))));
	}

	/**
	 * Selects, for a given key, a JSONArray of JSONObjects, and collects the JSONObjects. Example usage: Assist with
	 * <code>Revision</code> and <code>Contrib</code>.
	 * @param base The key pointing to the JSONArray to select.
	 * @return A Stream of collected JSONObjects
	 */
	protected Stream<Reply> getJOofJAStream(String base)
	{
		return rl.stream().flatMap(r -> r.getJAOfJOAsALR(base).stream());
	}

	/**
	 * Selects, for a given key, a String value from each JSONObject in a JSONAray. Example usage:
	 * <code>categorymembers</code>.
	 * 
	 * @param base The key pointing to the JSONArray in each Reply of the RSet.
	 * @param title The title to select in each JSONObject.
	 * @return The list of selected strings.
	 */
	protected ArrayList<String> stringFromJAOfJO(String base, String title)
	{
		return FL.toAL(getJOofJAStream(base).map(jo -> jo.getString(title)));
	}

	/**
	 * Selects, for a given key, a JSONArray of JSONObjects, and collects the JSONObjects. Example usage: Assist with
	 * <code>Revision</code> and <code>Contrib</code>.
	 * 
	 * @param base The key pointing to the JSONArray to select.
	 * @return An ArrayList of collected JSONObjects.
	 */
	protected ArrayList<Reply> getJOofJA(String base)
	{
		return FL.toAL(getJOofJAStream(base));
	}
}