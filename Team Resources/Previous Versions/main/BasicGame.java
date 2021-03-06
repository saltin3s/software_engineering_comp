package main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class BasicGame extends BasicGameState{

	private Image alien = null;
	private Image bg = null;
	
	public static int id = 1;
	private Rectangle square;
	private Rectangle collide;
	public static int x = 350, y = 350;
	public static boolean collides = false;
	
	
	public BasicGame(){ 
		collide = new Rectangle(350, 350, 50, 50);
		square = new Rectangle(x,y,50,50);
	}
	
	// This runs as soon as we compile the program.
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		alien = new Image("data/alien.png");
		bg = new Image("data/bg.jpg");
	}

	// Renders content to the game / screen
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		bg.draw(0,0);
		g.drawString("X: " + x + " Y: " + y + " collides " + collides, 10,50);
		g.setColor(Color.cyan);
		
		collide = new Rectangle(350, 350, 50, 50);
		square = new Rectangle(x,y,50,50);
		
		//alien.drawWarped(x, y, 100, 100, 200, 200, x+300, y+300);
		
		g.draw(square);
		g.draw(collide);
		alien.draw(x,y);
		
	}

	// Constant Loop, very fast, loops based on a delta (the amount of time that passes between each instance)
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		//Movement
			movePlayer(gc, delta);
			runPlayer(gc, delta);
			changeGrav(gc, delta);
			
			collide(gc);
			
		if(square.intersects(collide)){
				collides = true;
			}
			else {
				collides = false;
			}
		
		Physics.gravity();
		
	}
	
	public void collide(GameContainer gc){
			
		//Left border
		if(x <= 0){
			x = 0;
		}
		
		//Right world border
		if(x >= gc.getWidth() - alien.getWidth()){
			x = gc.getWidth() - alien.getWidth();
		}
		
		//Top world border
		if(y <= 0){
			y = 0;
		}
		
		//Bottom world border
		if(y>=gc.getHeight() - alien.getHeight()){
			y = gc.getWidth() - alien.getHeight();
		}
		
		
	}//end of collide
	
	public void movePlayer(GameContainer gc, int delta){
		Input input = gc.getInput(); // Creating our input object
		if(input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN)){
			y += 200/1000.0f * delta;

		}
		if(input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)){
			x -= 200/1000.0f * delta;

		}
		if(input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)){
			x += 200/1000.0f * delta;

		}
		if(input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_UP)){
			y -= 200/1000.0f * delta;

		}
	}//end of movePlayer
	
	public void runPlayer(GameContainer gc, int delta){
		Input input = gc.getInput(); // Creating our input object

		if((input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)) && input.isKeyDown(Input.KEY_LSHIFT)){
			x -= (200/1000.0f * delta) * 1.5;

		}
		else if((input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)) && input.isKeyDown(Input.KEY_LSHIFT)){
			x += (200/1000.0f * delta) * 1.5;
		}
		
	}//end of runPlayer
	
	public void changeGrav(GameContainer gc, int delta){
		Input input = gc.getInput(); // Creating our input object
		if(input.isKeyPressed(Input.KEY_LCONTROL)){
			Physics.fall = (-Physics.fall);
		}
	}
	
	public int getID() {
		// TODO Auto-generated method stub
		return BasicGame.id;
	}
	
}
