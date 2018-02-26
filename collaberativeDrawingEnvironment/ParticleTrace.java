package collaberativeDrawingEnvironment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import messageManagement.Profile;

/**
 * @file ParticleTrace.java
 * @author Tonye Spiff
 * @date 28 Nov 2016
 * @see DrawingElement, DrawingGUI
 *
 * Models a particle trace that can be drawn on a canvas.
 */
public class ParticleTrace extends DrawingElement {
	private static final long serialVersionUID = 1L;
	private final Point START_POINT;
	private final Point END_POINT;
	private final int m_strokeSize = 10;
	private Integer m_sender;
	private Integer m_receiver;
	private final String TYPE = "pt";

	/**
	 * Constructs a new particle trace element.
	 * @param start
	 * @param end
	 * @param sender
	 * @param receiver
     */
	public ParticleTrace(Point start, Point end,  Integer sender, Integer receiver) {
		this.START_POINT = start;
		this.END_POINT = end;
		this.m_sender = sender;
		this.m_receiver = receiver;
	}

	public int getM_strokeSize() {
		return m_strokeSize;
	}

	public void setSender(Integer sender) {
		this.m_sender = sender;
	}
	
	public void setReceiver(Integer receiver) {
		this.m_receiver = receiver;
	}
	
	public Integer getSender() {
		return this.m_sender;
	}
	
	public Integer getReceiver() {
		return this.m_receiver;
	}
	
/*	public void addLine(Point START_POINT, Point END_POINT, Color color) {
	    LINES.add(new StraightLine(START_POINT, END_POINT, color));        
	    DrawingGUI.class.canvas.repaint();
	}*/

	public String getTYPE() {
		return TYPE;
	}

	public boolean isDrawable() {
		return true;		
	}

	public boolean isVisible() {
		return true;
		
	}

	public void draw(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(m_strokeSize));
	    g2.drawLine((int) START_POINT.getX(), (int) START_POINT.getY(), (int) END_POINT.getX(), (int) END_POINT.getY());
	}
	
	public int getStartX() {
		return (int) this.START_POINT.getX();
	}
	
	public int getStartY() {
		return (int) this.START_POINT.getY();
	}

	public int getEndX() {
		return (int) this.END_POINT.getX();
	}

	public int getEndY() {
		return (int) this.END_POINT.getY();
	}
}
