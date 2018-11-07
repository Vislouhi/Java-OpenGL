package com;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

public class Parser {

	public static void main(String[] args) {

		
        
        SceneDataIni scene = new SceneDataIni("ini/config.ini");
        scene.springOscilator.start();
        
        while(true)
        {
        	scene.calculateFormulas(scene.gameItems.get(3));
            scene.gameItems.get(3).bindRotationAngleCalculations();
            scene.gameItems.get(3).startCalculations();
           // System.out.println(scene.gameItems.get(3).getRotationAngle());
        	try {
    			Thread.sleep(500);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}	
        }
        /*
        for(GameItem currentGameItem: scene.gameItems)
        {
        	
        	scene.calculateFormulas(currentGameItem);
        	currentGameItem.bindRotationAngleCalculations();
        	//currentGameItem.start();
        	
        }
        try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //scene.gameItems.get(1);
        System.out.println(scene.gameItems.get(1).fileName);
        scene.gameItems.get(1).updateValueOnLmb();
        scene.calculateFormulas(scene.gameItems.get(1));
        scene.gameItems.get(1).bindRotationAngleCalculations();
        scene.gameItems.get(1).startCalculations();
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        scene.gameItems.get(1).updateValueOnLmb();
        scene.calculateFormulas(scene.gameItems.get(1));
        scene.gameItems.get(1).bindRotationAngleCalculations();
        scene.gameItems.get(1).startCalculations();
        
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        scene.gameItems.get(1).updateValueOnLmb();
        scene.calculateFormulas(scene.gameItems.get(1));
        scene.gameItems.get(1).bindRotationAngleCalculations();
        scene.gameItems.get(1).startCalculations();
	*/
}
}
