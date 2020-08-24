package com.github.fwidder.valorantBot.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchedulerService {

	/**
	 * Time between Frames in preview.<br>
	 * Must be equal or larger than {@link #SCREEN_REFRESH_INTERVAL}.
	 */
	private static final int DISPLAY_REFRESH_INTERVAL = 200;
	/**
	 * Time between Frame Dumps.<br>
	 * Must be equal or larger than {@link #SCREEN_REFRESH_INTERVAL}.
	 */
	private static final int IMAGE_DUMP_INTERVAL = 5000;
	/**
	 * Time between Screenshots.<br>
	 * Must be equal or smaller than {@link #DISPLAY_REFRESH_INTERVAL} and
	 * {@link #IMAGE_DUMP_INTERVAL}.
	 */
	private static final int SCREEN_REFRESH_INTERVAL = 100;
	private final DisplayService displayService;
	private final ScreenService screenService;

	public SchedulerService(DisplayService displayService, ScreenService screenService) {
		log.trace("Initializing started.");
		this.displayService = displayService;
		this.screenService = screenService;
		log.trace("Initializing finished.");
	}

	@Scheduled(fixedRate = IMAGE_DUMP_INTERVAL)
	public void doImageDump() {
		screenService.dumpScreenToTemporaryFile();
	}

	@Scheduled(fixedRate = DISPLAY_REFRESH_INTERVAL)
	public void doRefreshDisplay() {
		displayService.refresh();
	}

	@Scheduled(fixedRate = SCREEN_REFRESH_INTERVAL)
	public void doRefreshScreen() {
		screenService.refresh();
	}
}
