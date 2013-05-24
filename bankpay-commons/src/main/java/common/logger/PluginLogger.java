package common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginLogger {

	private Logger logger;

	protected PluginLogger(Class<?> clazz) {
		logger = LoggerFactory.getLogger(clazz);
	}

	public static PluginLogger getLogger(Class<?> clazz) {
		return new PluginLogger(clazz);
	}

	public void entering(String methodName) {
		if (logger.isInfoEnabled()) {
			logger.info(" Entering  {}  ", methodName);
		}
	}

	public void entering(String methodName, Object parameters) {
		if (logger.isInfoEnabled()) {
			logger.info(" Entering  {}  {}", new Object[] { methodName, parameters });
		}
	}

	public void entering(String methodName, Object[] parameters) {
		if (logger.isInfoEnabled()) {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < parameters.length; i++) {
				buffer.append("  " + parameters[i]);
			}
			logger.info(" Entering  {}  {}", new Object[] { methodName, buffer.toString() });
		}
	}

	public void exiting(String methodName) {
		if (logger.isInfoEnabled()) {
			logger.info(" Exiting  {}  ", methodName);
		}
	}

	public void exiting(String methodName, Object parameters) {
		if (logger.isInfoEnabled()) {
			logger.info(" Exiting  {}  {}", new Object[] { methodName, parameters });
		}
	}

	public void exiting(String methodName, Object[] parameters) {
		if (logger.isInfoEnabled()) {
			StringBuffer buff = new StringBuffer();
			for (int i = 0; i < parameters.length; i++) {
				buff.append("  " + parameters[i]);
			}
			logger.info(" Exiting  {}  {}", new Object[] { methodName, buff.toString() });
		}
	}

	public void debug(String methodName, Object message) {
		if (logger.isDebugEnabled()) {
			logger.debug("{}  {}", new Object[] { methodName, message });
		}
	}

	public void debug(String methodName, Throwable e) {
		if (logger.isDebugEnabled()) {
			logger.debug("{}  ", methodName, e);
		}
	}

	public void info(String methodName, Object message) {
		if (logger.isInfoEnabled()) {
			logger.info("{}  {}", new Object[] { methodName, message });
		}
	}

	public void info(String methodName, Throwable e) {
		if (logger.isInfoEnabled()) {
			logger.info("{}  ", methodName, e);
		}
	}

	public void warn(String methodName, String message) {
		if (logger.isWarnEnabled()) {
			logger.warn("{}  {}", new Object[] { methodName, message });
		}
	}

	public void warn(String methodName, Throwable e) {
		if (logger.isWarnEnabled()) {
			logger.warn("{}  ", methodName, e);
		}
	}

	public void error(String methodName, String message) {
		if (logger.isErrorEnabled()) {
			logger.error("{}  {}", new Object[] { methodName, message });
		}
	}

	public void error(String methodName, Throwable e) {
		if (logger.isErrorEnabled()) {
			logger.error("{}  ", methodName, e);
		}
	}

}
