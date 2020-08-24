package com.github.fwidder.valorantBot.service;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AWTUtilService {

	private final Robot robot;
	private final Toolkit toolkit;

	public AWTUtilService() throws AWTException {
		log.trace("Initializing started.");
		this.robot = new Robot();
		this.toolkit = Toolkit.getDefaultToolkit();
		log.trace("Initializing finished.");
	}

	public Robot getRobot() {
		return robot;
	}

	public Toolkit getToolkit() {
		return toolkit;
	}
}
