import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;


public class Main extends Canvas implements Runnable, KeyListener,MouseListener, MouseMotionListener {

	Frame frame = new Frame();
	Graphics g;
	
	public Main() {
			frame.setVisible(true);
			frame.setResizable(false);
			frame.setMinimumSize(new Dimension(1024, 768));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.addMouseListener(this);
			frame.addMouseMotionListener(this);
			frame.addKeyListener(this);
			frame.setVisible(true);
			frame.createBufferStrategy(bufNum);
			frame.setIconImage(frameIcon);
			try {
				startMusic();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	public void start() {
		
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

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
