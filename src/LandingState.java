import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Marie Curie on 01/04/2017.
 */
public class LandingState extends BasicGameState {
    public String mouse = "";

    public LandingState(int menu) {
    }

    @Override
    public int getID() {
        return 0;
    }



    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        container.setShowFPS(false);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        //draw stuff, adding pictures and all
        Image bg = new Image("res/Components/img-bg01-03.png");
        Image content = new Image("res/Components/01 landing page/content.png");
        Image getstarted = new Image("res/Components/01 landing page/getstarted.png");
        Image login = new Image("res/Components/01 landing page/login.png");
        g.drawImage(bg,0,0);
        g.drawImage(content, 270,80);
        g.drawImage(getstarted, 308,369);
        g.drawImage(login, 309,412);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();
        mouse = "x pos = "+xpos+"   y pos = "+ypos;
        Input input = container.getInput();	//keyboard and mouse input

        if((xpos>316 && xpos<472) && (ypos>130 && ypos<165) ){
            if(input.isMouseButtonDown(0)){
                game.enterState(2); //go to sign up
            }
        }
        else if((xpos>314 && xpos<472) && (ypos>88 && ypos<123) ){
            if(input.isMouseButtonDown(0)){
                game.enterState(1); //go to log in
            }
        }
    }
}
