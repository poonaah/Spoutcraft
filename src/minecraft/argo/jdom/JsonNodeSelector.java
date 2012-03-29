package argo.jdom;

public final class JsonNodeSelector {
	final Functor valueGetter;

	JsonNodeSelector(Functor par1Functor) {
		valueGetter = par1Functor;
	}

	public boolean matches(Object par1Obj) {
		return valueGetter.matchesNode(par1Obj);
	}

	public Object getValue(Object par1Obj) {
		return valueGetter.applyTo(par1Obj);
	}

	public JsonNodeSelector with(JsonNodeSelector par1JsonNodeSelector) {
		return new JsonNodeSelector(new ChainedFunctor(this, par1JsonNodeSelector));
	}

	String shortForm() {
		return valueGetter.shortForm();
	}

	public String toString() {
		return valueGetter.toString();
	}
}
