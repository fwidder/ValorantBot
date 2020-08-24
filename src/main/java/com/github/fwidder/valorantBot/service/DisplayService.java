package com.github.fwidder.valorantBot.service;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.github.fwidder.valorantBot.ui.ImagePanel;
import com.github.fwidder.valorantBot.ui.RatioWindowResizeListener;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DisplayService {

	private final boolean enableDisplay;
	private JFrame frame;
	private long lastFrame;
	private final RatioWindowResizeListener ratioWindowResizeListener;
	private final ScreenService screenSercice;
	private final ImagePanel screenshotIcon;
	private final boolean showFPS;

	public DisplayService(ScreenService screenService, @Value("${valorant.bot.display.enabled}") String enableDisplay,
			@Value("${valorant.bot.display.showFPS}") String showFPS, ImagePanel imagePanel,
			RatioWindowResizeListener ratioWindowResizeListener) {
		log.trace("Initializing started.");
		this.screenSercice = screenService;
		this.enableDisplay = Boolean.parseBoolean(enableDisplay);
		this.showFPS = Boolean.parseBoolean(showFPS);
		this.screenshotIcon = imagePanel;
		this.ratioWindowResizeListener = ratioWindowResizeListener;
		log.trace("Initializing finished.");
		log.info("Diplay enabled: {}", enableDisplay);
		if (this.enableDisplay)
			initWindow();
	}

	private double calcHeight() {
		return (600 / screenSercice.getScreenSize().getWidth()) * screenSercice.getScreenSize().getHeight();
	}

	private void clearWindow() {
		// frame.removeAll();
	}

	private String fps() {
		long current = System.currentTimeMillis();
		long dif = current - lastFrame;
		double perSec = 60000 / dif;
		this.lastFrame = System.currentTimeMillis();
		return "Current FPS: " + perSec;
	}

	private void initWindow() {
		log.trace("Initializing Window started.");

		frame = new JFrame("Valorant Bot");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.addComponentListener(ratioWindowResizeListener);
		frame.setBounds(100, 100, 600, (int) calcHeight());
		frame.add(screenshotIcon);
		frame.setVisible(true);
		this.lastFrame = System.currentTimeMillis();
	}

	@Async
	public void refresh() {
		if (!enableDisplay)
			return;
		log.debug("Refreshing Window");
		// do all refresh
		clearWindow();
		refreshScreenshot();
		refreshFPS();
		// repaint Window
		frame.repaint();
	}

	private void refreshFPS() {
		if (showFPS)
			frame.setTitle(fps());
	}

	private void refreshScreenshot() {
		BufferedImage screenshot = screenSercice.getScreenshot();
		screenshotIcon.setImage(screenshot);
	}
}
