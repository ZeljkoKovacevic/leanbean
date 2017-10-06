package net.heim;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.heim.usbrelay.Usb_relay_deviceLibrary.INSTANCE;
import net.heim.usbrelay.usb_relay_device_info;

public class AtLight {
	private static Logger LOG = LoggerFactory.getLogger(AtLight.class);

	public static void main(String[] args) {

		if (args != null && args.length == 0)
			throw new IllegalArgumentException("Pass path to file as 1'st argument");

		String pathname = args[0];

		File tlStatusFile = new File(pathname);
		try (FileInputStream fis = new FileInputStream(tlStatusFile); DataInputStream dis = new DataInputStream(fis)) {

			byte readByte = dis.readByte();
			int atStatus = (readByte-'0') % 2;
			LOG.info("-> "+atStatus);

			INSTANCE.usb_relay_init();

			usb_relay_device_info deviceInfo = INSTANCE.usb_relay_device_enumerate();
			
			int handle = INSTANCE.usb_relay_device_open(deviceInfo);
			
			INSTANCE.usb_relay_device_close_all_relay_channel(handle);

			INSTANCE.usb_relay_device_open_one_relay_channel(handle, atStatus);

		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			INSTANCE.usb_relay_exit();
		}

	}
}
