import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Marie Curie on 04/04/2017.
 */
public class EditUsername extends BasicGameState {
    public TextField firstname;
    public TextField lastname;
    public TextField username;
    public TextField password;
    public String mouse = "";
    public boolean isFirstTimeUsername = true;

    public EditUsername(int editusername) {
    }

    @Override
    public int getID() {
        return 10;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        container.setShowFPS(false);
        isFirstTimeUsername = true;
        firstname = new TextField(container, container.getDefaultFont(), 233, 188, 198, 23);
        lastname = new TextField(container,container.getDefaultFont(),442,188,127,23);
        username = new TextField(container,container.getDefaultFont(),233,300,335,23);
        password = new TextField(container,container.getDefaultFont(),233,412,335,23);

        firstname.setBorderColor(Color.transparent);
        firstname.setBackgroundColor(Color.transparent);
        firstname.setTextColor(Color.gray);
        firstname.setCursorVisible(false);
        firstname.setFocus(false);
        firstname.setConsumeEvents(false);
        firstname.setAcceptingInput(false);
        firstname.setText("Noah Dominic");

        lastname.setBorderColor(Color.transparent);
        lastname.setBackgroundColor(Color.transparent);
        lastname.setTextColor(Color.gray);
        lastname.setCursorVisible(false);
        lastname.setFocus(false);
        lastname.setConsumeEvents(false);
        lastname.setAcceptingInput(false);
        lastname.setText("Silvio");

        username.setBorderColor(Color.transparent);
        username.setBackgroundColor(Color.transparent);
        username.setTextColor(Color.black);
        username.setCursorVisible(false);
        username.setFocus(false);
        username.setConsumeEvents(false);
        username.setAcceptingInput(true);
        username.setText("malupethbhouzx");

        password.setBorderColor(Color.transparent);
        password.setBackgroundColor(Color.transparent);
        password.setTextColor(Color.gray);
        password.setCursorVisible(false);
        password.setFocus(false);
        password.setConsumeEvents(false);
        password.setAcceptingInput(false);
        password.setText("swaggerzxs");

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        Image bg = new Image("res/Components/05 edit account/bg.png");
        Image backbutton = new Image("res/Components/05 edit account/backButton.png");
        Image notEditingName = new Image("res/Components/05 edit account/isNotEditingName.png");
        Image EditingUsername = new Image("res/Components/05 edit account/isEditingUsername.png");
        Image notEditingPassword = new Image("res/Components/05 edit account/isNotEditingPassword.png");
        g.drawImage(bg,0,0);
        g.drawImage(backbutton,20,20);
        g.drawImage(notEditingName,220,150);
        g.drawImage(EditingUsername,220,265);
        g.drawImage(notEditingPassword, 220, 375);
        firstname.render(container,g);
        lastname.render(container,g);
        username.render(container,g);
        password.render(container,g);
        g.drawString(mouse, 50, 100);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();
        mouse = "x pos = "+xpos+"   y pos = "+ypos;

        Input input = container.getInput();	//keyboard and mouse input

        if(isFirstTimeUsername && username.hasFocus()) {
            username.setText("");
            username.setTextColor(Color.black);
            isFirstTimeUsername = false;
        }
        if((xpos>20 && xpos<85) && (ypos>483 && ypos<518) ){
            if(input.isMousePressed(0)){
                game.enterState(8); //go back to nonedit state (back)
            }
        }
        if((xpos>525 && xpos<572) && (ypos>184 && ypos<207) ){
            if(input.isMousePressed(0)){
                game.enterState(8); //go to non edit state
            }
        }

    }
}
