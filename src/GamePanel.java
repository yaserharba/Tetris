import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;
import java.util.Random;
import java.util.concurrent.*;
class GamePanel extends JPanel{
 	public Timer timer;
 	public int[][] mat = new int[14][10];
 	public Color[] blockColors = {Color.YELLOW , new Color(255, 128, 0), new Color(255, 128, 0), new Color(255, 128, 0), new Color(255, 128, 0), Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, new Color(0, 191, 255), new Color(0, 191, 255), Color.GREEN, Color.GREEN, new Color(128, 0, 255), new Color(128, 0, 255), new Color(128, 0, 255), new Color(128, 0, 255), Color.RED,  Color.RED};
	public int[][][] Blocks = {
 		{
 		{1, 1, 0, 0},
 		{1, 1, 0, 0},
 		{0, 0, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{2, 0, 0, 0},
	 	{2, 0, 0, 0},
 		{2, 2, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{0, 0, 3, 0},
 		{3, 3, 3, 0},
 		{0, 0, 0, 0},
 		{0, 0, 0, 0}
  		},
    	{
 		{4, 4, 0, 0},
 		{0, 4, 0, 0},
 		{0, 4, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{5, 5, 5, 0},
 		{5, 0, 0, 0},
 		{0, 0, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{6, 0, 0, 0},
 		{6, 6, 6, 0},
 		{0, 0, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{0, 7, 0, 0},
 		{0, 7, 0, 0},
 		{7, 7, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{8, 8, 8, 0},
 		{0, 0, 8, 0},
 		{0, 0, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{9, 9, 0, 0},
 		{9, 0, 0, 0,},
 		{9, 0, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{10, 10, 10, 10},
 		{0, 0, 0, 0},
 		{0, 0, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{11, 0, 0, 0},
 		{11, 0, 0, 0},
 		{11, 0, 0, 0},
 		{11, 0, 0, 0}
  		},
  		{
 		{0, 12, 12, 0},
 		{12, 12, 0, 0},
 		{0, 0, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{13, 0, 0, 0},
 		{13, 13, 0, 0},
 		{0, 13, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{0, 14, 0, 0},
 		{14, 14, 14, 0},
 		{0, 0, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{0, 15, 0, 0},
 		{15, 15, 0, 0},
 		{0, 15, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{16, 16, 16, 0},
 		{0, 16, 0, 0},
 		{0, 0, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{17, 0, 0, 0},
 		{17, 17, 0, 0},
 		{17, 0, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{18, 18, 0, 0},
 		{0, 18, 18, 0},
 		{0, 0, 0, 0},
 		{0, 0, 0, 0}
  		},
  		{
 		{0, 19, 0, 0},
 		{19, 19, 0, 0},
 		{19, 0, 0, 0},
 		{0, 0, 0, 0}
  		}
	};
	int bestScore = 0;
	int t = 0;
	int line = 0;
	int block = 0;
	public int xB = 4;
	boolean stop = false;
	static boolean endGame = false;
	static boolean playAgain = false;
	static boolean endSoundDone = false;
	Random rand = new Random();
	public int score = 0;
	int firstBlock = pickBlock();
 	int secondBlock = pickBlock();
 	int blockNum = 0;
 	int pushedBlock = 0;
 	int speedVal = 1;
 	public float playAgainBrightness = 1.0f;
	int [] changeToBlock = {0, 2, 3, 4, 1, 6, 7, 8, 5, 10, 9, 12, 11, 14, 15, 16, 13, 18, 17};
 	public GamePanel(){
 		System.out.println("Start!!");
 	}
 	public void printMat(){
 		for(int j = 0; j < 14; j ++){
 			for(int i = 0; i < 10; i++){
 				System.out.print("" + mat[j][i]);
 			}
 			System.out.println("");
 		}
 	}
 	public void drawBlock(Graphics g, int x, int y, int block){
 		for(int i = 0; i < 4; i++){
 			for(int j = 0; j < 4; j++){
 				if(Blocks[block][i][j] != 0){
 					g.setColor(blockColors[block]);
 					g.fillRect(11+60*(x+j), 11+60*(y+i),59, 59);
 					g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.6f));
 					g.fillPolygon(new int [] {11+60*(x+j), 70+60*(x+j), 65+60*(x+j), 16+60*(x+j)},new int [] {11+60*(y+i), 11+60*(y+i), 16+60*(y+i), 16+60*(y+i)},4);
 					g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.4f));
 					g.fillPolygon(new int [] {11+60*(x+j), 70+60*(x+j), 65+60*(x+j), 16+60*(x+j)},new int [] {70+60*(y+i), 70+60*(y+i), 65+60*(y+i), 65+60*(y+i)},4);
 					g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.2f));
 					g.fillPolygon(new int [] {11+60*(x+j), 11+60*(x+j), 16+60*(x+j), 16+60*(x+j)},new int [] {11+60*(y+i), 70+60*(y+i), 65+60*(y+i), 16+60*(y+i)},4);
 					g.fillPolygon(new int [] {70+60*(x+j), 70+60*(x+j), 65+60*(x+j), 65+60*(x+j)},new int [] {11+60*(y+i), 70+60*(y+i), 65+60*(y+i), 16+60*(y+i)},4);
 				}
 			}
 		}
 	}
 	public void paintComponent(Graphics g){
 		super.paintComponent(g);
 		g.setColor(new Color(70, 70, 70));
 		g.fillRect(10, 10, 760, 840);
 		if(!endGame){
 			g.setColor(Color.WHITE);
 			g.setFont(new Font("Monospaced", Font.BOLD, 40));
 			g.drawString("Best:", 620, 120);
 			g.drawString("score:", 620, 170);
 			g.drawString(""+bestScore, 640, 220);
 			g.drawString("Score:", 620, 350);
 			g.drawString(""+score, 640, 400);
 			g.drawString("Next:", 620, 550);
 			if(blockNum%2==0){
	 			for(int i = 0; i < 4; i++){
 					for(int j = 0; j < 4; j++){
	 					if(Blocks[secondBlock][i][j] != 0){
 							g.setColor(blockColors[secondBlock]);
 							g.fillRect(630+30*j, 600+30*i,29, 29);
 							g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.6f));
 							g.fillPolygon(new int [] {630+30*j, 659+30*j, 656+30*j, 633+30*j}, new int [] {600+30*i, 600+30*i, 603+30*i, 603+30*i},4);
 							g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.4f));
 							g.fillPolygon(new int [] {630+30*j, 659+30*j, 656+30*j, 633+30*j}, new int [] {630+30*i, 630+30*i, 627+30*i, 627+30*i},4);
 							g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.2f));
 							g.fillPolygon(new int [] {630+30*j, 630+30*j, 633+30*j, 633+30*j}, new int [] {600+30*i, 630+30*i, 627+30*i, 603+30*i},4);
 							g.fillPolygon(new int [] {659+30*j, 659+30*j, 656+30*j, 656+30*j},new int [] {600+30*i, 630+30*i, 627+30*i, 603+30*i},4);
 						}
 					}
 				}
 			} else {
 				for(int i = 0; i < 4; i++){
 					for(int j = 0; j < 4; j++){
 						if(Blocks[firstBlock][i][j] != 0){
 							g.setColor(blockColors[firstBlock]);
 							g.fillRect(630+30*j, 600+30*i,29, 29);
 							g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.6f));
 							g.fillPolygon(new int [] {630+30*j, 659+30*j, 656+30*j, 633+30*j}, new int [] {600+30*i, 600+30*i, 603+30*i, 603+30*i},4);
 							g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.4f));
 							g.fillPolygon(new int [] {630+30*j, 659+30*j, 656+30*j, 633+30*j}, new int [] {630+30*i, 630+30*i, 627+30*i, 627+30*i},4);
 							g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.2f));
 							g.fillPolygon(new int [] {630+30*j, 630+30*j, 633+30*j, 633+30*j}, new int [] {600+30*i, 630+30*i, 627+30*i, 603+30*i},4);
 							g.fillPolygon(new int [] {659+30*j, 659+30*j, 656+30*j, 656+30*j},new int [] {600+30*i, 630+30*i, 627+30*i, 603+30*i},4);
 						}
 					}
 				}
 			}
 		}
 		g.setColor(new Color(50, 50, 50));
 		g.fillRect(10, 10, 600, 840);
 		for(int j = 0; j < 14; j ++){
 			for(int i = 0; i < 10; i++){
 				if(mat[j][i] != 0){
 					g.setColor(blockColors[mat[j][i] - 1]);
 					g.fillRect(11+60*i, 11+60*j,59, 59);
 					g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.6f));
 					g.fillPolygon(new int [] {11+60*i, 70+60*i, 65+60*i, 16+60*i},new int [] {11+60*j, 11+60*j, 16+60*j, 16+60*j},4);
 					g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.4f));
 					g.fillPolygon(new int [] {11+60*i, 70+60*i, 65+60*i, 16+60*i},new int [] {70+60*j, 70+60*j, 65+60*j, 65+60*j},4);
 					g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.2f));
 					g.fillPolygon(new int [] {11+60*i, 11+60*i, 16+60*i, 16+60*i},new int [] {11+60*j, 70+60*j, 65+60*j, 16+60*j},4);
 					g.fillPolygon(new int [] {70+60*i, 70+60*i, 65+60*i, 65+60*i},new int [] {11+60*j, 70+60*j, 65+60*j, 16+60*j},4);
 				}
 			}
 		}
 		g.setColor(new Color(90, 90, 90));
 		for(int i = 1; i <= 9; i++){
 			g.drawLine(10 + i * 60, 10, 10 + i * 60, 850);
 		}
 		for(int i = 1; i <= 13; i++){
 			g.drawLine(10, 10 + i * 60, 610, 10 + i * 60);
 		}
 		if(t==0||stop){
 			if(!endGame){
 				block = nextBlock();
 				blockNum++;
 			}
 			line = 0;
 			stop = false;
 			xB = 4;
 			loseCheck();
 		}
 		for(int i = 0; i < 4; i++){
 			for(int j = 0; j < 4; j++){
 		    	if(Blocks[block][i][j] != 0){
 					if(line + i + 1 >= 14){
 						stop = true;
 					}else{
 						if(mat[line+i+1][xB+j] != 0){
 							stop = true;
 						}
 					}
 		    	}
 			}
 		}
 		if(stop){
 			for(int i = 0; i < 4; i++){
 				for(int j= 0; j < 4; j++){
 					if(Blocks[block][i][j] != 0){
 						mat[line+i][xB+j] = Blocks[block][i][j];
 					}
 				}
 			}
 		}
 		drawBlock(g, xB, line, block);
 		line++;
 		class TimerClass implements  ActionListener{
 			public void actionPerformed(ActionEvent e){
 				if(e.getSource() == timer){
 					erase();
 					repaint();
 					if(!endGame){
 						t++;
 					}
 				}
 			}
 		}
 		if(pushedBlock==blockNum){
 			speedVal = 10;
 		} else {
 			speedVal = 1;
 		}
 		TimerClass TimerOb = new TimerClass();
 		timer = new Timer((int)((380 - (int)(0.4 * t))/speedVal), TimerOb);
 		timer.start();
 		if(endGame) {
 			g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.6f));
 			g.fillRoundRect(30, 160, 560, 550, 40, 40);//border of all end game things
 			 g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.9f));
 			g.fillRoundRect(80, 240, 450, 100, 40, 40); //border of End Game
 			g.fillRoundRect(90, 430, 200, 60, 40, 40); //border of score
 			g.fillRoundRect(320, 430, 200, 60, 40, 40); //border of best
 			g.setColor(new Color(1.0f, 1.0f, 1.0f, (playAgainBrightness*0.6f)));
 			g.fillRoundRect(150, 570, 330, 100, 40, 40); //border od Play Again
 			g.setColor(Color.BLACK);
 			g.setFont(new Font("TimesRoman", Font.BOLD, 85)); 
 			g.drawString("Game Over", 95, 315);
 			g.setFont(new Font("Monospaced", Font.BOLD, 50)); 
 			g.drawString("Play Again", 165, 630);
 			g.setFont(new Font("Monospaced", Font.BOLD, 30)); 
 			g.drawString("Score:", 100, 468);
 			g.drawString(""+score, 208, 468);
 			g.drawString("Best:", 330, 468);
 			g.drawString(""+bestScore, 420, 468);
 			musicStuff.clip.stop();
 			if(!endSoundDone){
 				musicStuff.playEndMusic();
 				endSoundDone = true;
 			}
 			if(playAgain){
 				for(int j = 0; j < 14; j ++){
 					for(int i = 0; i < 10; i++){
 						mat[j][i] = 0;
 					}
 				}
 				secondBlock = pickBlock();
 				t = 0;
 				musicStuff.endGame();
 				endGame = false;
 				endSoundDone = false;
				line = 0;
				xB = 4;
				stop = false;
		    	score = 0;
 				blockNum = 0;
 				playAgain = false;
 			}
 		}
 	} // End paint method
 	public boolean moveR(){
 		for(int i = 0; i < 4; i++){
 			for(int j = 0; j < 4; j++){
 		    	if(Blocks[block][i][j] != 0){
 					if(xB + j + 1 >= 10){
 						return false;
 					}else{
 						if(mat[line+i][xB+j+1] != 0){
 							return false;
 						}
 					}
 		    	}
 			}
 		}
 		return true;
 	}
 	public boolean moveL(){
 		for(int i = 0; i < 4; i++){
 			for(int j = 0; j < 4; j++){
 		    	if(Blocks[block][i][j] != 0){
 					if(xB == 0 || line+i >= 14){
 						return false;
 					}else{
 						if(mat[line+i][xB+j-1] != 0){
 							return false;
 						}
 					}
 		    	}
 			}
 		}
 		return true;
 	}
 	public int pickBlock(){
		int randShape = rand.nextInt(7);
		int [] pickArray = {0, 1 + rand.nextInt(4), 5 + rand.nextInt(4), 9 + rand.nextInt(2), 11 + rand.nextInt(2), 13 + rand.nextInt(4), 17 + rand.nextInt(2)};
		return pickArray[randShape];
 	}
 	public void erase(){
 		boolean cleen = true;
 		int j = 13;
 		for(j = 13; j >= 0; j --){
 			cleen = true;
 			for(int i = 0; i < 10; i++){
 				if(mat[j][i]==0){
 					cleen = false;
 				}
 			}
 			if(cleen){
 				score+=10;
 				if(bestScore < score){
 					bestScore = score;
 				}
 				for(;j > 0; j--){
 					for(int i = 0; i < 10; i++){
 						mat[j][i] = mat[j-1][i];
 					}
 				}
 				j = 13;
 			}
 		}
 	}
 	public boolean changeBlockP1(){
 		boolean changeAble = true;
 		for(int i = 0; i < 4; i++){
 			for(int j = 0; j < 4; j++){
 		    	if(Blocks[changeToBlock[block]][i][j] != 0){
 					if(line + i >= 14 || xB + j + 1 >= 10){
 						changeAble = false;
 						break;
 					}else if(mat[line+i][xB+j+1] != 0){
 						changeAble = false;
 					}
 		    	}
 			}
 		}
 		return changeAble;
 	}
 	public boolean changeBlockM1(){
 		boolean changeAble = true;
 		for(int i = 0; i < 4; i++){
 			for(int j = 0; j < 4; j++){
 		    	if(Blocks[changeToBlock[block]][i][j] != 0){
 					if(line + i >= 14 || xB + j - 1 >= 10 || xB + j - 1 < 0){
 						changeAble = false;
 						break;
 					}else if(mat[line+i][xB+j-1] != 0){
 						changeAble = false;
 					}
 		    	}
 			}
 		}
 		return changeAble;
 	}
 	public boolean changeBlockM2(){
 		boolean changeAble = true;
 		for(int i = 0; i < 4; i++){
 			for(int j = 0; j < 4; j++){
 		    	if(Blocks[changeToBlock[block]][i][j] != 0){
 					if(line + i >= 14 || xB + j - 2 >= 10 || xB + j - 2 < 0){
 						changeAble = false;
 						break;
 					}else if(mat[line+i][xB+j-2] != 0){
 						changeAble = false;
 					}
 		    	}
 			}
 		}
 		return changeAble;
 	}
 	public boolean changeBlockM3(){
 		boolean changeAble = true;
 		for(int i = 0; i < 4; i++){
 			for(int j = 0; j < 4; j++){
 		    	if(Blocks[changeToBlock[block]][i][j] != 0){
 					if(line + i >= 14 || xB + j - 3 >= 10 || xB + j - 3 < 0){
 						changeAble = false;
 						break;
 					}else if(mat[line+i][xB+j-3] != 0){
 						changeAble = false;
 					}
 		    	}
 			}
 		}
 		return changeAble;
 	}
 	public boolean changeBlock0(){
 		boolean changeAble = true;
 		for(int i = 0; i < 4; i++){
 			for(int j = 0; j < 4; j++){
 		    	if(Blocks[changeToBlock[block]][i][j] != 0){
 					if(line + i >= 14 || xB + j >= 10){
 						changeAble = false;
 						break;
 					}else if(mat[line+i][xB+j] != 0){
 						changeAble = false;
 					}
 		    	}
 			}
 		}
 		return changeAble;
 	}
 	public void changeBlock(){
 		if(block == 1 || block == 12 || block == 16){
 			if(changeBlockM1()){
 				xB--;
 				block = changeToBlock[block];
 			} else if(changeBlock0()){
 				block = changeToBlock[block];
 			} else if(changeBlockP1()){
 				xB++;
 				block = changeToBlock[block];
 			}
 		} else if(block == 7 || block == 9 || block == 11 || block == 15){
 			if(changeBlockP1()){
 				xB++;
 				block = changeToBlock[block];
 			} else if(changeBlockM1()){
 				xB--;
 				block = changeToBlock[block];
 			} else if(changeBlock0()){
 				block = changeToBlock[block];
 			}
 		} else if((block >=2 && block <= 6) || block == 8 || block == 13 || block == 14 || block == 17 || block == 18){
 			if(changeBlock0()){
 				block = changeToBlock[block];
 			} else if(changeBlockM1()){
 				xB--;
 				block = changeToBlock[block];
 			} else if(changeBlockP1()){
 				xB++;
 				block = changeToBlock[block];
 			}
 		}else if(block == 10){
 			if(changeBlockM1()){
 				xB--;
 				block = changeToBlock[block];
 			} else if(changeBlock0()){
 				block = changeToBlock[block];
 			} else if(changeBlockM2()){
 				xB-=2;
 				block = changeToBlock[block];
 			} else if(changeBlockM3()){
 				xB-=3;
 				block = changeToBlock[block];
 			} else if(changeBlockP1()){
 				xB++;
 				block = changeToBlock[block];
 			}
 		}
 	}
 	public int nextBlock(){
 		if(blockNum%2==0){
 			firstBlock = pickBlock();
 			return secondBlock;
 		} else {
 			secondBlock = pickBlock();
 			return firstBlock;
 		}
 	}
 	public void loseCheck(){
 		for(int i = 0; i < 4; i++){
 			for(int j = 0; j < 4; j++){
 		    	if(Blocks[block][i][j] != 0){
 					if(mat[line+i][xB+j] != 0){
 						endGame = true;
 						break;
 					}
 		    	}
 			}
 			if(endGame){
 				break;
 			}
 		}
 	}
 } // end Class
