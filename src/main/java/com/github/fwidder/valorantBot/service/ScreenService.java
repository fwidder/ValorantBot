package com.github.fwidder.valorantBot.service;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScreenService {

	public static final String IMAGE_FORMAT = "jpg";
	public static final String IMAGE_PREFIX = "IMAGE_DUMP_";
	public static final String IMAGE_SUFFIX = "." + IMAGE_FORMAT;

	private final AWTUtilService awtUtilService;
	private final boolean enableImageDump;
	private BufferedImage screenshot;

	public ScreenService(AWTUtilService awtUtilService,
			@Value("${valorant.bot.debug.imageDump.enabled}") String enableImageDump) {
		log.trace("Initializing started.");
		this.awtUtilService = awtUtilService;
		this.enableImageDump = Boolean.parseBoolean(enableImageDump);
		refresh();
		log.trace("Initializing finished.");
		log.info("ImageDump enabled: {}", enableImageDump);
	}

	private void dumpScreenToFile(File file) throws IOException {
		log.info("Dumping Screen to File {}", file.getAbsolutePath());
		BufferedImage screenShot = getScreenshot();
		ImageIO.write(screenShot, IMAGE_FORMAT, file);
	}

	public void dumpScreenToTemporaryFile() {
		if (!enableImageDump)
			return;
		log.debug("Creating temporary File for Screenshot");
		try {
			File file = File.createTempFile(IMAGE_PREFIX, IMAGE_SUFFIX);
			file.deleteOnExit();
			dumpScreenToFile(file);
		} catch (IOException e) {
			log.warn("Error while wrting Screenshot to file: {}", e);
		}
	}

	public BufferedImage getScreenshot() {
		return screenshot;
	}

	public Dimension getScreenSize() {
		return awtUtilService.getToolkit().getScreenSize();
	}

	public void refresh() {
		log.debug("Creating Screenshot");
		screenshot = awtUtilService.getRobot().createScreenCapture(new Rectangle(getScreenSize()));

	}
}
