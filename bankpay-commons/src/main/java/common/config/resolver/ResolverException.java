package common.config.resolver;

import common.exception.PluginException;

public class ResolverException extends PluginException {

	private static final long serialVersionUID = -7795948487713087037L;

	public ResolverException() {
		super();
	}

	public ResolverException(String s) {
		super(s);
	}

	public ResolverException(Throwable s) {
		super(s);
	}

}
