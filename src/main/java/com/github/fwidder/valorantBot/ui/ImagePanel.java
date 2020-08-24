package com.github.fwidder.valorantBot.ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import org.springframework.stereotype.Component;

@Component
public class ImagePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6110869236819166821L;

	private Image img = null;

	@Override
	public void paintComponent(Graphics g) {
		if (img != null)
			g.drawImage(img.getScaledInstance(getWidth(), -1, Image.SCALE_SMOOTH), 0, 0, null);
	}

	public void setImage(Image img) {
		this.img = img;
		repaint();
	}
}