package common.exception;

public class PluginException extends RuntimeException {

	private static final long serialVersionUID = -3140884249848588366L;

	public PluginException() {
		super();
	}

	public PluginException(String s) {
		super(s);
	}

	public PluginException(Throwable s) {
		super(s);
	}

	public PluginException(String message, Throwable cause) {
		super(message, cause);
	}

}
