package net.heim.usbrelay;

import org.junit.Test;

public class TestUsbRelay {

	@Test
	public void test() {
		Usb_relay_deviceLibrary.INSTANCE.usb_relay_init();
		
		Usb_relay_deviceLibrary.INSTANCE.usb_relay_exit();
	}

}
