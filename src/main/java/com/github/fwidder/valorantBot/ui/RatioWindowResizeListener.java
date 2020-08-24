package com.github.fwidder.valorantBot.ui;

import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import org.springframework.stereotype.Component;

import com.github.fwidder.valorantBot.service.ScreenService;

@Component
public class RatioWindowResizeListener implements ComponentListener {

	private final ScreenService screenSercice;

	public RatioWindowResizeListener(ScreenService screenSercice) {
		this.screenSercice = screenSercice;
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		int W = (int) screenSercice.getScreenSize().getWidth();
		int H = (int) screenSercice.getScreenSize().getHeight();
		Rectangle b = arg0.getComponent().getBounds();
		arg0.getComponent().setBounds(b.x, b.y, b.width, b.width * H / W);
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
	}
}