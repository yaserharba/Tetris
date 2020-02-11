import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;
import java.util.Random;
import java.util.concurrent.*;
class musicStuff{
	static public Clip clip;
	static public Clip endClip;
	static public void endGame(){
		clip.setMicrosecondPosition(0l);
		clip.start();
	}
   	static void playMusic(String musicL){
    	try{
      		File musicP = new File(musicL);
      		if(musicP.exists()){
      			AudioInputStream auI = AudioSystem.getAudioInputStream(musicP);
      			clip = AudioSystem.getClip();
        		clip.start();
        		clip.open(auI);
        		clip.loop(Clip.LOOP_CONTINUOUSLY);
      		} else {
        		System.out.println("can't find file");
      		}
    	} catch(Exception ex){
    		ex.printStackTrace();
    	}
  	}
  	static void playEndMusic(){
    	try{
      		File musicP = new File("lose effect.wav");
      		if(musicP.exists()){
      			AudioInputStream auI = AudioSystem.getAudioInputStream(musicP);
      			endClip = AudioSystem.getClip();
        		endClip.start();
        		endClip.open(auI);
        		endClip.loop(0);
      		} else {
        		System.out.println("can't find file");
      		}
    	} catch(Exception ex){
    		ex.printStackTrace();
    	}
  	}
}