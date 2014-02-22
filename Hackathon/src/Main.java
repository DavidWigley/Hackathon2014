import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main extends Canvas implements Runnable, KeyListener,MouseListener, MouseMotionListener {

	Frame frame = new Frame();
	Graphics g;
	//images
	ImageIcon background = new ImageIcon(getClass().getResource("/resources/background.png"));
	Image picture = background.getImage();
	ImageIcon frameIcon = new ImageIcon(getClass().getResource("/resources/background.png"));
	Image icon = frameIcon.getImage();
	//music
	AudioInputStream backgroundMusic;
	Clip music;
	//misc
	boolean isRunning;
	int bufNum = 2;
	boolean dead, escape, escapePressed, isGrounded;
	int velocityY = 0;
	
	public Main() {
			frame.setVisible(true);
			frame.setResizable(false);
			frame.setMinimumSize(new Dimension(1024, 768));
			((JFrame) frame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.addMouseListener(this);
			frame.addMouseMotionListener(this);
			frame.addKeyListener(this);
			frame.setVisible(true);
			frame.createBufferStrategy(bufNum);
			frame.setIconImage(icon);
			try {
				startMusic();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	public void start() {
	run();
	}
	
	public void paint() {
		BufferStrategy bf = frame.getBufferStrategy();
		g = bf.getDrawGraphics();
		g.clearRect(0, 0, 1024, 768);
		// g = null;

		try {
			g.drawRect(5, 5, 5, 5);
		}
			//drawing
		finally{
			g.dispose();
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method handle the movement for the character and all other key binds
	 */
	public void keyPressed(KeyEvent e) {
		// up arrow
		if (e.getKeyCode() == 38 || e.getKeyCode() == 87) {
			//up = true;
			//upPressed = true;
			//down = false;
			if (isGrounded) {
				velocityY = -8;
			}
			
		}
		// escape key
		if ((e.getKeyCode() == 27) && (!escape) && (!escapePressed) && (!dead)) {
			escape = true;
			escapePressed = true;
			paint();
		} else if ((e.getKeyCode() == 27) && (escape) && (!escapePressed) && (!dead)) {
			escape = false;
			escapePressed = true;
		}
		
	}

	/**
	 * This method handles the stopping of movement for the character and
	 * stoping all other key binds
	 */
	public void keyReleased(KeyEvent e) {
		// up arrow
		if (e.getKeyCode() == 38 || e.getKeyCode() == 87) {
			//up = false;
			//upPressed = false;
		}
		if ((e.getKeyCode() == 27)  && (escapePressed)) {
			escapePressed = false;
		}
	}


	
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		//do stuff
		paint();
	}
	public void startMusic()throws Exception, IOException {
		backgroundMusic = AudioSystem.getAudioInputStream(getClass().getResource(
			"/resources/music.wav"));
		music = AudioSystem.getClip();
		try {
			music.open(backgroundMusic);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		music.loop(178);
	}
}
