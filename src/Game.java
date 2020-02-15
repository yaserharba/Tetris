 import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;
 import java.io.*;
 import javax.sound.sampled.*;
import java.util.Random;
import java.util.concurrent.*;
class Game extends JFrame{
	Game(){
		super("Tetris Game");
		musicStuff.playMusic("music.wav");
		CardLayout card = new CardLayout();
		Container cont = getContentPane();
		cont.setLayout(card);
		JButton playB = new JButton("Play");
		JButton endB = new JButton("End Game");
		JPanel playP = new JPanel();
		JPanel startP = new JPanel();
		
		endB.setPreferredSize(new Dimension(400, 50));
		endB.setBounds(200,200,200,200);
		
		playP.setLayout(new BorderLayout());
		playP.add(endB, BorderLayout.SOUTH);
		
		startP.setLayout(new BorderLayout());
		startP.add(playB, BorderLayout.CENTER);
		cont.add(startP, "Start wind");
		cont.add(playP, "Play wind");
		
		card.last(cont);
		playB.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					card.last(cont);
				}
			});
		endB.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					card.first(cont);
				}
			});
		setSize(800, 1000);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    	setLocation((int) ((dimension.getWidth() - 800) / 2), (int) ((dimension.getHeight() - 1000) / 2) - 20);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GamePanel gameP = new GamePanel();
		playP.add(gameP, BorderLayout.CENTER);
		gameP.setFocusable(true);
		gameP.requestFocusInWindow();
		gameP.start();
		gameP.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(e.getX() > 150 && e.getX() < 480 && e.getY() > 570 && e.getY()< 670 && gameP.endGame){
					if(!gameP.playAgain){
    					gameP.playAgain = true;
    				}
				}
			}
		});
		gameP.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e) {
				if(e.getX() > 150 && e.getX() < 480 && e.getY() > 570 && e.getY()< 670 && gameP.endGame){
					gameP.playAgainBrightness = 1.5f;
				} else {
					gameP.playAgainBrightness = 1.0f;
				}
			}
		});
		gameP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				 if (e.getKeyCode()==39 && gameP.moveR()){
				 	gameP.xB++;
				 	gameP.repaint();
			     } else if (e.getKeyCode()==37&&gameP.moveL()){
    				gameP.xB--;
				 	gameP.repaint();
    			} else if (e.getKeyChar()==' '){
    				gameP.changeBlock();
    				gameP.repaint();
    			} else if (e.getKeyCode()==KeyEvent.VK_DOWN){
 					gameP.speedVal = 10;
 					gameP.timer.setDelay((int)((380 - (int)(2 * gameP.score))/gameP.speedVal));
    				gameP.pushedBlock = gameP.blockNum;
    			}
			}
		});
		setVisible(true);
	}
}
