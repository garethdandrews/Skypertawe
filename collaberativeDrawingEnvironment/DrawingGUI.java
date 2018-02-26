package collaberativeDrawingEnvironment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import messageManagement.Profile;

/**
 * @file DrawingGUI.java
 * @author Tonye Spiff
 * @date 28 Nov 2016
 *
 * Creates a GUI to allow 2 users to draw on the same canvas.
 */
public class DrawingGUI extends JFrame {
	private JFrame m_drawingFrame;
	private static final long serialVersionUID = 1L;
	private static final String FRAME_TITLE = "Collabrative Drawing Environment";
	public static final int FRAME_WIDTH = 700;
	public static final int FRAME_HEIGHT = 700;
	public static final int CANVAS_WIDTH = 650;
	public static final int CANVAS_HEIGHT = 650;
	public static final Color WHITE = new Color(255, 255, 255);
	private Point m_straightStartPoint = new Point();
	private Point m_straightEndPoint = new Point();
	private ShapeType m_shapeType = ShapeType.LINE;
	private ArrayList<StraightLine> m_lines = new ArrayList<>();
	private ArrayList<ParticleTrace> m_traces = new ArrayList<>();
	private Profile m_sender;
	private Profile m_recipient;

	/**
	 * Initializes the GUI.
	 *
	 * @param sender
	 * @param recipient
	 */
	public DrawingGUI(Profile sender, Profile recipient) {
		this.m_sender = sender;
		this.m_recipient = recipient;
		initializeCDE();
	}

	/**
	 * Creates the JFrame and adds components.
	 */
	public void initializeCDE() {
		m_drawingFrame = new JFrame();
		m_drawingFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		m_drawingFrame.setTitle(FRAME_TITLE);
		m_drawingFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

		JPanel drawingTools = new JPanel();
		m_drawingFrame.add(drawingTools);

		final JPanel canvas = new JPanel();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		canvas.setBackground(WHITE);

		JButton straightLine = new JButton("Straight Line");
		straightLine.setFont(new Font("Arial", Font.BOLD, 17));
		straightLine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				m_shapeType = ShapeType.LINE;
			}
		});

		JButton particleTrace = new JButton("Particle Trace");
		particleTrace.setFont(new Font("Arial", Font.BOLD, 17));
		particleTrace.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				m_shapeType = ShapeType.PARTICLE;
			}
		});

		drawingTools.setLayout(new FlowLayout());

		canvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				@SuppressWarnings("deprecation")
				Cursor crosshairCursor = new Cursor(CROSSHAIR_CURSOR);
				canvas.setCursor(crosshairCursor);
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				m_straightStartPoint = m_straightEndPoint = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (m_shapeType == ShapeType.LINE) {
					m_straightEndPoint = e.getPoint();
					Color color = new Color(0, 0, 0);
					StraightLine newLine = new StraightLine(m_straightStartPoint, m_straightEndPoint, m_sender.getProfileId(), m_recipient.getProfileId());
					canvas.add(newLine);
					Graphics g = canvas.getGraphics();
					newLine.draw(g);
					m_lines.add(newLine);
				}
			}
		});

		canvas.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (m_shapeType == ShapeType.PARTICLE) {
					Point particleStartPoint = new Point(e.getX(), e.getY());
					Point particleEndPoint = new Point(e.getX(), e.getY());
					Color color = new Color(0, 0, 0);
					ParticleTrace newParticleTrace = new ParticleTrace(particleStartPoint, particleEndPoint, m_sender.getProfileId(), m_recipient.getProfileId());
					canvas.add(newParticleTrace);
					Graphics g = canvas.getGraphics();
					newParticleTrace.draw(g);
					m_traces.add(newParticleTrace);
				}

				if (m_shapeType == ShapeType.LINE) {
					m_straightEndPoint = e.getPoint();
				}
			}
		});

		drawingTools.add(canvas);
		drawingTools.add(straightLine);
		drawingTools.add(particleTrace);
		m_drawingFrame.setResizable(false);
		m_drawingFrame.setVisible(true);
	}

	enum ShapeType {
		LINE, PARTICLE;
	}
}