package saucedemo.com.extension;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import saucedemo.com.utils.ScreenShotUtils;

public class CaptureScreenShotExtension implements TestWatcher {
	@Override
	public void testFailed(ExtensionContext context, Throwable cause) {
		ScreenShotUtils.takeScreenshot(ScreenShotUtils.getMethodName(context.getDisplayName()));
	}
}
