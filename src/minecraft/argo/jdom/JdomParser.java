package argo.jdom;

import argo.saj.InvalidSyntaxException;
import argo.saj.SajParser;
import java.io.*;

public final class JdomParser {
	public JdomParser() {
	}

	public JsonRootNode parse(Reader par1Reader) throws InvalidSyntaxException, IOException {
		JsonListenerToJdomAdapter jsonlistenertojdomadapter = new JsonListenerToJdomAdapter();
		(new SajParser()).parse(par1Reader, jsonlistenertojdomadapter);
		return jsonlistenertojdomadapter.getDocument();
	}

	public JsonRootNode parse(String par1Str) throws InvalidSyntaxException {
		JsonRootNode jsonrootnode;

		try {
			jsonrootnode = parse(new StringReader(par1Str));
		} catch (IOException ioexception) {
			throw new RuntimeException("Coding failure in Argo:  StringWriter gave an IOException", ioexception);
		}

		return jsonrootnode;
	}
}
