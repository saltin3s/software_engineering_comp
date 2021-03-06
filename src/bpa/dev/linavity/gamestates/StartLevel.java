package bpa.dev.linavity.gamestates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bpa.dev.linavity.assets.InputManager;
import bpa.dev.linavity.entities.Camera;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.physics.Gravity;
import bpa.dev.linavity.utils.Utils;
import bpa.dev.linavity.world.Level;

public class StartLevel extends BasicGameState{

	// Images
	private Image bg = null;
	private Image back = null;
	
	// ID of the gamestate
	public static int id = 1;
	
	// Whether or not the pop-up menu is open
	private boolean menuOpen = false;
	
	private int xpos; // Mouse's X position
	private int ypos; // Mouse's Y position

	// util object to access our other objects across the project
	Utils util;
	
	//Variables to set up our level
	public Level level;
	public Camera cam;
	public Rectangle hitbox;
	
	// List of all user inputs
	public InputManager im = new InputManager();
	private boolean[] keyLog = new boolean[7]; // Keyboard
	private int[] mouseLog = new int[3]; // Mouse
	
	//The tileID to this gamestate (will be read in as a file later on)
	private int[][] tileIDs = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
			{1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
			{1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
	};
	
	//Default constructor
	public StartLevel(){ 
		
	}
	
	/**
	 * This runs as soon as we compile the program
	 */
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		util = new Utils();
		cam = new Camera(util.getPlayer().getX(), util.getPlayer().getY());
		back = new Image("data/button_back.png");
		bg = new Image("data/bg.jpg");
		hitbox = new Rectangle(425, 720, 50, 50);
		level = new Level(0, tileIDs);

	}

	/**
	 * Renders content to the game / screen
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		bg.draw(0,0);
		
		renderScreen(gc, sbg, g);
		
		g.draw(hitbox);
		
		g.drawString("X: " + util.getPlayer().getX() + " Y: " + util.getPlayer().getY(), 10,50);
		g.drawString("Cam X: " + cam.getX() + " Cam Y: " + cam.getY(), 10,70);
		
		//Draw player
		util.getPlayer().getMobImage().draw(423, 718); // I have a feeling this line of code is gonna get roasted on by Mr. Santiago
		
		if(menuOpen){
			renderMenu(gc, g);
		}
		
	}

	/**
	 * Render the screen based on where the camera is on our map
	 * @param gc
	 * @param sbg
	 * @param g
	 */
	private void renderScreen(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
		Tile[][] levelTiles = level.getTiles();
		
		
		// Boundaries of our camera.
		int camX1, camX2, camY1, camY2;
		
		// Temp x and y of tile in relation to the camera
		int tileX, tileY;
		
		// Make a buffer variable
		camX1 = cam.getX() - cam.getBuffer();
		camY1 = cam.getY() - cam.getBuffer();
		camX2 = cam.getX() + cam.getWidth() + cam.getBuffer();
		camY2 = cam.getY() + cam.getHeight() + cam.getBuffer();
		
		for(int i = 0; i < levelTiles.length; i++) {
			for(int j = 0; j < levelTiles[i].length; j++) {
				tileX = levelTiles[i][j].getX() - cam.getX();
				tileY = levelTiles[i][j].getY() - cam.getY();
				if(MainMenu.checkBounds(-128, 1024, -128, 1024, tileX, tileY)) {
					levelTiles[i][j].getTexture().draw(tileX, tileY);
				}
			}
		}
		
	}

	/**
	 * Constant Loop, very fast, loops based on a delta (the amount of time that passes between each instance)
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		hitbox = new Rectangle(util.getPlayer().getX(), util.getPlayer().getY(),50, 50);
		
		// If the game is not paused
		if(!menuOpen){
			
			// Make all keyboard-based updates
			input(gc);
			
			gameUpdates(gc, sbg, delta);
			
		}
		
		// Open pop-up menu
		openMenu(gc, delta);
		checkMenu(gc, sbg);
		
	}
	
	/**
	 * Make all info game updates
	 * @param gc
	 * @param sbg
	 * @param delta
	 */
	private void gameUpdates(GameContainer gc, StateBasedGame sbg, int delta) {
		
		// Update Player Attributes
		updatePlayer(delta);
		
		// Update Camera Coordinates
		cam.updateCameraPos(util.getPlayer().getX(), util.getPlayer().getY());
		
		// Open Pop-up menu
		if(keyLog[6])
			menuOpen = !menuOpen;

		// Check Collisions
		collide(gc);
		
	}

	
	/**
	 * Our input method uses the input manager class to update all of out input logs
	 * @param gc
	 */
	public void input(GameContainer gc){
	
		// Update our keyboard log
		keyLog = im.getKeyLog(gc);
		
		// Update our mouse log
		mouseLog = im.getMouseLog(gc);
		
	}
	
	/**
	 * Perform all updates to the player object
	 * @param delta
	 */
	public void updatePlayer(int delta){
		
		// Update the player's position
		util.getPlayer().updatePos(keyLog, delta, util);
		
		// Update the player's attributes
		// player.updateAttributes();
		
	}
	
	/**
	 * Implements world borders
	 * @param gc
	 */
	public void collide(GameContainer gc){

		//Left border
		//if(util.getPlayer().getX() <= 0){
		//	util.getPlayer().setX(0);
		//}
		
		//Right world border
		//if(util.getPlayer().getX()  >= gc.getWidth() - util.getPlayer().getMobImage().getWidth()){
		//	util.getPlayer().setX(gc.getWidth() - util.getPlayer().getMobImage().getWidth());
		//}
		
		//Top world border
		if(util.getPlayer().getY() <= 0){
			util.getPlayer().setY(0);
		}
		
		//Bottom world border
		if(util.getPlayer().getY() >=gc.getHeight() - util.getPlayer().getMobImage().getHeight()){
			util.getPlayer().setY(gc.getWidth() - util.getPlayer().getMobImage().getHeight());
		}
		
	}//end of collide
	

	/**
	 * @method renderMenu
	 * @description draws the images needed for the popup menu
	 * 
	 * @param
	 * GameContainer gc, Graphics g
	 * 
	 * @return
	 * 	void:
	 */
	public void renderMenu(GameContainer gc, Graphics g){
		// Back Button
		g.drawImage(back, (gc.getWidth()/2) - (back.getWidth()/2), 400); // Setting the x value as half of the game container and adjusting for the width of the button
	}
	
	/**
	 * @method checkMenu
	 * @description checks mouse actions for the popup menu
	 * 
	 * @param
	 * GameContainer gc, StateBasedGame sbg, Input input
	 * 
	 * @return
	 * 	void:
	 * @throws SlickException 
	 */
	public void checkMenu(GameContainer gc, StateBasedGame sbg) 
			throws SlickException{
		
		// Create our input object
		Input input = gc.getInput();
		
		back = new Image("data/button_back.png");
		
		// Back Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(MainMenu.checkBounds( (gc.getWidth()/2) - (back.getWidth()/2) , (gc.getWidth()/2) - (back.getWidth()/2) + back.getWidth() , 400 , 400 + back.getHeight(), xpos, ypos)){
			if(input.isMousePressed(0)){
				input.clearKeyPressedRecord();
				sbg.enterState(0);
				menuOpen = false;
			}
			back = new Image("data/button_back_hover.png");
		}
		
	}
	
	/**
	 * Checks for the user input of the escape key to toggle the in-game menu...
	 * @param gc
	 * @param delta
	 */
	public void openMenu(GameContainer gc, int delta){
		Input input = gc.getInput(); // Creating our input object
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			menuOpen = !menuOpen; // ! Makes the escape toggle
		}
	}
	
	public int getID() {
		// TODO Auto-generated method stub
		return StartLevel.id;
	}
	
}//end of class
