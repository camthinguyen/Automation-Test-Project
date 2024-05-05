package saucedemo.com.extension;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class LoggingExtension implements AfterEachCallback {

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		Logger logger = LogManager.getLogger(context.getClass());
		context.getTestClass().ifPresent(x -> logger.info(x));
		logger.info(context.getDisplayName());
		context.getExecutionException().ifPresent(x -> logger.info(x));
	}
}
