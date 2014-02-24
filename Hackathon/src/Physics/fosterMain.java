package Physics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.naming.LinkLoopException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.util.Random;

public class fosterMain extends Canvas implements Runnable, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1382444253480141332L;

	public boolean running = false;
	Random generator=new Random();
	JFrame frame;
	
	public boolean rightPressed = false;
	public boolean leftPressed = false;
	private boolean lowCountChange;
	public float x = 100, y = 100;
	public float velocityX, velocityY;
	public float gravity = 1f;
	
	public boolean isIdle = false;
	public boolean isFalling = false;
	public boolean isJumping = false;
	public boolean isMoving = false;
	
	public boolean facingRight = true;
	
	public boolean lockMovement = false;
	
	public int animID = 0;
	public int animFrames = 0;
	public int phase = 0;
	public int jumpheight=0;
	private int backgroundX = 0;
	private int backgroundX2 = 170;
	private int backgroundX3 = 340;
	private int backgroundX4 = 510;
	private int backgroundX5 = 680;
	private int backgroundX6 = 850;
	private int backgroundX7 = 1020;
	private int currentLow = backgroundX;
	private int currentJumpHeight = 0;
	public boolean finished = false;
	public boolean doJump = false;
	private int oneJump=-76;
	private int movePixels;
	boolean dead, escape, escapePressed, isGrounded;
	int lowestX;
	//jumps
	private int jumpheight1, jumpheight2, jumpheight3, jumpheight4, jumpheight5, jumpheight6, jumpheight7;
	//music
	AudioInputStream backgroundMusic;
	Clip music;
	
	Graphics g;
	
	ImageIcon background = new ImageIcon(getClass().getResource("/resources/background.png"));
	Image picture = background.getImage();
	
	ImageIcon failSafeIcon= new ImageIcon(getClass().getResource("/resources/failsafe.png"));
	Image failSafe = failSafeIcon.getImage();
	
	ImageIcon frameIcon = new ImageIcon(getClass().getResource("/resources/background.png"));
	Image icon = frameIcon.getImage();
	
	ImageIcon idle = new ImageIcon(getClass().getResource("/resources/idle.png"));
	Image idlePhase = idle.getImage();
	
	ImageIcon walk1 = new ImageIcon(getClass().getResource("/resources/walk1.png"));
	Image walkPhase1 = walk1.getImage();
	
	ImageIcon walk2 = new ImageIcon(getClass().getResource("/resources/walk2.png"));
	Image walkPhase2 = walk2.getImage();
	
	ImageIcon walk3 = new ImageIcon(getClass().getResource("/resources/walk3.png"));
	Image walkPhase3 = walk3.getImage();
	
	ImageIcon walk4 = new ImageIcon(getClass().getResource("/resources/walk4.png"));
	Image walkPhase4 = walk4.getImage();
	
	ImageIcon walk5 = new ImageIcon(getClass().getResource("/resources/walk5.png"));
	Image walkPhase5 = walk5.getImage();
	
	ImageIcon walk6 = new ImageIcon(getClass().getResource("/resources/walk6.png"));
	Image walkPhase6 = walk6.getImage();
	
	ImageIcon walk7 = new ImageIcon(getClass().getResource("/resources/walk7.png"));
	Image walkPhase7 = walk7.getImage();
	
	ImageIcon walk8 = new ImageIcon(getClass().getResource("/resources/walk8.png"));
	Image walkPhase8 = walk8.getImage();

	ImageIcon jump1 = new ImageIcon(getClass().getResource("/resources/jump1.png"));
	Image jumpPhase1 = jump1.getImage();
	
	ImageIcon jump2 = new ImageIcon(getClass().getResource("/resources/jump2.png"));
	Image jumpPhase2 = jump2.getImage();
	
	ImageIcon jump3 = new ImageIcon(getClass().getResource("/resources/jump3.png"));
	Image jumpPhase3 = jump3.getImage();
	
	ImageIcon fall1 = new ImageIcon(getClass().getResource("/resources/fall1.png"));
	Image fallPhase1 = fall1.getImage();
	
	ImageIcon fall2 = new ImageIcon(getClass().getResource("/resources/fall2.png"));
	Image fallPhase2 = fall2.getImage();
	
	ImageIcon land = new ImageIcon(getClass().getResource("/resources/land.png"));
	Image landPhase = land.getImage();
	
	Image player = idlePhase;
	
	public fosterMain() {
		frame = new JFrame("Jump Game");
		frame.setMinimumSize(new Dimension(1020, 760));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setIconImage(icon);
	}
	
	public void run() {
		
		long time1 = System.currentTimeMillis();
		long time2 = System.currentTimeMillis();
		long time3 = System.currentTimeMillis();
		try {
			startMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(running) {
			//old was 50
			if (System.currentTimeMillis() - time1 >= 70) {
				update();
				time1 = System.currentTimeMillis();
			}
//			
//			if (System.currentTimeMillis() - time2 >= 16) {
//				render();
//				time2 = System.currentTimeMillis();
//			}
			if (System.currentTimeMillis() - time3 >= 100) {
				animation();
				time3 = System.currentTimeMillis();
			}
		}
	}
	
	public void animation() {
		
		if (isIdle) {
			player = idlePhase;
		}
		
		if (doJump && isMoving) {
			phase = 0;
			isMoving = false;
		}
		
		if (doJump) {
			if (finished || isMoving) {
				phase = 0;
				finished = false;
				isMoving = false;
			}
			if (phase <= 2) {
				lockMovement = true;
				player = jumpPhase1;
				phase++;
			} else if (phase == 3) {
				playSound("grunt.wav");
				lockMovement = false;
				velocityY = -20;
				doJump = false;
				finished = true;
			}
		}
		
		if (isJumping) {
			if (finished) {
				phase = 0;
				finished = false;
			}
			if (phase == 0) {
				player = jumpPhase2;
			}
			if (isFalling) {
				finished = true;
				isJumping = false;
			}
		}
		
		if (isFalling) {
			if (finished) {
				phase = 0;
				finished = false;
			}
			if (phase == 0 && y < 560) {
				player = fallPhase1;
				phase++;
			} else if (phase == 1 && y < 560) {
				player = fallPhase2;
			} else {
				player = landPhase;
				finished = true;
			}
		}
		
		if (isMoving && isGrounded && !doJump) {
			if (finished) {
				phase = 0;
				finished = false;
			}
			if (phase == 0) {
				player = walkPhase1;
				phase++;
			} else if (phase == 1) {
				player = walkPhase2;
				phase++;
			} else if (phase == 2) {
				player = walkPhase3;
				phase++;
			} else if (phase == 3) {
				player = walkPhase4;
				phase++;
			} else if (phase == 4) {
				player = walkPhase5;
				phase++;
			} else if (phase == 5) {
				player = walkPhase6;
				phase++;
			} else if (phase == 6) {
				player = walkPhase7;
				phase++;
			} else if (phase == 7) {
				player = walkPhase8;
				finished = true;
			}
		}
	}
	
	public void update() {
		
		backgroundX-=10;
		backgroundX2-=10;
		backgroundX3-=10;
		backgroundX4-=10;
		backgroundX5-=10;
		backgroundX6-=10;
		backgroundX7-=10;
		if (!lowCountChange){
			lowestX=backgroundX;
		}
		if(lowestX <=-170) {
			jumpheight=generator.nextInt(10);
			if ((jumpheight - currentJumpHeight > 2) || currentJumpHeight - jumpheight <2) {
				int decision = generator.nextInt(2);
				if ((decision ==1) && (currentJumpHeight>0)) {
					jumpheight = currentJumpHeight -2;
				}else{
					jumpheight = currentJumpHeight +2;
				}
			}
			System.out.println("new jump height " + jumpheight);
			movePixels = jumpheight * oneJump;
			currentJumpHeight=jumpheight;
			System.out.println(movePixels);
			if (currentLow==1) {
				jumpheight7 = movePixels;
				System.out.println("attempting change on jumpheight7");
			}
			else if (currentLow==2) {
				jumpheight1 = movePixels;
				System.out.println("attempting change on jumpheight1");
			}
			else if(currentLow==3){
				jumpheight2 = movePixels;
				System.out.println("attempting change on jumpheight2");
			}
			else if(currentLow==4) {
				jumpheight3 = movePixels;
				System.out.println("attempting change on jumpheight3");
			}
			else if(currentLow==5){
				jumpheight4 = movePixels;
				System.out.println("attempting change on jumpheight4");
			}
			else if(currentLow==6){
				jumpheight5 = movePixels;
				System.out.println("attempting change on jumpheight5");
			}
			else if(currentLow==7){
				jumpheight6 = movePixels;
				System.out.println("attempting change on jumpheight6");
			}
			System.out.println("jumpheight " + jumpheight1);
			System.out.println("jumpheight2 " + jumpheight2);
			System.out.println("jumpheight3 " + jumpheight3);
			System.out.println("jumpheight4 " + jumpheight4);
			System.out.println("jumpheight5 " + jumpheight5);
			System.out.println("jumpheight6 " + jumpheight6);
			System.out.println("jumpheight7 " + jumpheight7);
			lowestX=0;
		}
		if (!rightPressed && !leftPressed) {
			isIdle = true;
			isMoving = false;
		} else {
			if (!lockMovement) {
				isMoving = true;
			}
		}
		
		if (velocityY < 0) {
			isJumping = true;
		} else if (velocityY > 0) {
			isFalling = true;
		}
		
		if (isFalling || isJumping) {
			isIdle = false;
		}
		
		if (isGrounded) {
			isFalling = false;
			isJumping = false;
		}
		
		x += velocityX;
		velocityY += gravity;
		y += velocityY;
		if (!lockMovement) {
			if (rightPressed && velocityX < 20 && x < 900) {
				if (velocityX < 0) {
					velocityX += 2;
				} else {
					velocityX++;
				}
			} else if (rightPressed && velocityX >= 10) {
				velocityX = 10;
			}
			if (leftPressed && velocityX > -20 && x > 0) {
				if (velocityX > 0) {
					velocityX -= 2;
				} else {
					velocityX--;
				}
			} else if (leftPressed && velocityX <= -10) {
				velocityX = -10;
			}
			if (rightPressed && x + velocityX > 900) {
				velocityX = 0;
			}
			if (leftPressed && x - velocityX < 0) {
				velocityX = 0;
			}
		}
		if (!leftPressed && !rightPressed && velocityX > 0 && isGrounded) {
			velocityX = 0;
		} else if (!leftPressed && !rightPressed && velocityX < 0 && isGrounded) {
			velocityX = 0;
		}
		if (y >= 600) {
			y = 600;
			velocityY = 0;
			isGrounded = true;
		} else {
			isGrounded = false;
		}
		render();
	}
	
	public void render() {
		//g = frame.getGraphics();
		//g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
		//g.drawImage(sprite, Math.round(x), Math.round(y), 200, 200, frame);
		//g.dispose();
		BufferStrategy bs = frame.getBufferStrategy();
		if (bs==null) {
			frame.createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
		//g.drawImage(failSafe, 0,0,this);
		
		g.drawImage(picture, backgroundX, jumpheight1,this);
		g.drawImage(picture, backgroundX2, jumpheight2,this);
		g.drawImage(picture, backgroundX3, jumpheight3,this);
		g.drawImage(picture, backgroundX4, jumpheight4,this);
		g.drawImage(picture, backgroundX5,jumpheight5, this);
		g.drawImage(picture, backgroundX6, jumpheight6, this);
		g.drawImage(picture, backgroundX7, jumpheight7,this);
		if (backgroundX <= -170){
			System.out.println("resetting background");
			lowCountChange = true;
			currentLow = 2;
			backgroundX = 1020;
			lowestX = -170;
		}else if(backgroundX2<=-170) {
			lowCountChange = true;
			currentLow=3;
			backgroundX2 = 1020;
			lowestX = -170;
		}else if(backgroundX3<=-170) {
			lowCountChange = true;
			currentLow=4;
			backgroundX3 = 1020;
			lowestX = -170;
		}else if(backgroundX4<=-170) {
			lowCountChange = true;
			currentLow=5;
			backgroundX4 = 1020;
			lowestX = -170;
		}else if(backgroundX5<=-170) {
			lowCountChange = true;
			currentLow=6;
			backgroundX5 = 1020;
			lowestX = -170;
		}else if(backgroundX6<=-170) {
			lowCountChange = true;
			currentLow=7;
			backgroundX6 = 1020;
			lowestX = -170;
		}else if(backgroundX7<=-170) {
			lowCountChange = true;
			currentLow=1;
			backgroundX7 = 1020;
			lowestX = -170;
		}
		if (facingRight) {
			g.drawImage(player, Math.round(x), Math.round(y), player.getWidth(frame), player.getHeight(frame), frame);
		} else {
			g.drawImage(player, Math.round(x + 125), Math.round(y), -player.getWidth(frame), player.getHeight(frame), frame);
		}

		g.dispose();
		bs.show();
	}
	
	public void start() throws Exception {
		new Thread(this).start();
		running = true;
	}
	
	public void stop() {
		running = false;
	}
	
	public static void main(String[] args) {
		try {
			new fosterMain().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method handle the movement for the character and all other key binds
	 */
	public void keyPressed(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_RIGHT) { rightPressed = true; facingRight = true;}
		if (key.getKeyCode() == KeyEvent.VK_LEFT) {leftPressed = true; facingRight = false;}
		if (key.getKeyCode() == KeyEvent.VK_UP && isGrounded) {doJump = true;}
		// escape key
		if ((key.getKeyCode() == 27) && (!escape) && (!escapePressed) && (!dead)) {
			escape = true;
			escapePressed = true;
			render();
		} else if ((key.getKeyCode() == 27) && (escape) && (!escapePressed) && (!dead)) {
			escape = false;
			escapePressed = true;
		}
			
}

	/**
	 * This method handles the stopping of movement for the character and
	 * stoping all other key binds
	 */
	public void keyReleased(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_RIGHT){ rightPressed = false;}
		if (key.getKeyCode() == KeyEvent.VK_LEFT) {leftPressed = false;}
		if ((key.getKeyCode() == 27)  && (escapePressed)) {
		escapePressed = false;
		}
	}
	
	public synchronized void playSound(final String sound) {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/resources/" + sound));
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		    	  e.printStackTrace();
		      }
		    }
	public void startMusic()throws Exception{
		backgroundMusic = AudioSystem.getAudioInputStream(getClass().getResource(
			"/resources/music.wav"));
		music = AudioSystem.getClip();
		try {
			music.open(backgroundMusic);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		music.loop(178);
	}


	public void keyTyped(KeyEvent key) {
	
	}
}
