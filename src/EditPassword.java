import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Marie Curie on 04/04/2017.
 */
public class EditPassword extends BasicGameState {
    public TextField firstname;
    public TextField lastname;
    public TextField username;
    public TextField password;
    public TextField retypepassword;
    public String mouse = "";
    public boolean isFirstTimePassword = true, isFirstTimeRetypePassword = true;
    public EditPassword(int editpassword) {
    }

    @Override
    public int getID() {
        return 11;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        container.setShowFPS(false);
        isFirstTimePassword = isFirstTimeRetypePassword = true;
        firstname = new TextField(container, container.getDefaultFont(), 233, 188, 198, 23);
        lastname = new TextField(container,container.getDefaultFont(),442,188,127,23);
        username = new TextField(container,container.getDefaultFont(),233,300,335,23);
        password = new TextField(container,container.getDefaultFont(),233,412,335,23);
        retypepassword = new TextField(container,container.getDefaultFont(),233,470,335,23);

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
        username.setTextColor(Color.gray);
        username.setCursorVisible(false);
        username.setFocus(false);
        username.setConsumeEvents(false);
        username.setAcceptingInput(false);
        username.setText("malupethbhouzx");

        password.setBorderColor(Color.transparent);
        password.setBackgroundColor(Color.transparent);
        password.setTextColor(Color.black);
        password.setCursorVisible(false);
        password.setFocus(false);
        password.setConsumeEvents(false);
        password.setAcceptingInput(true);
        password.setText("swaggerzxs");

        retypepassword.setBorderColor(Color.transparent);
        retypepassword.setBackgroundColor(Color.transparent);
        retypepassword.setTextColor(Color.black);
        retypepassword.setCursorVisible(false);
        retypepassword.setFocus(false);
        retypepassword.setConsumeEvents(false);
        retypepassword.setAcceptingInput(true);
        retypepassword.setText("swaggerzxs");

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        Image bg = new Image("res/Components/05 edit account/bg.png");
        Image backbutton = new Image("res/Components/05 edit account/backButton.png");
        Image notEditingName = new Image("res/Components/05 edit account/isNotEditingName.png");
        Image notEditingUsername = new Image("res/Components/05 edit account/isNotEditingUsername.png");
        Image EditingPassword = new Image("res/Components/05 edit account/isEditingPassword.png");
        g.drawImage(bg,0,0);
        g.drawImage(backbutton,20,20);
        g.drawImage(notEditingName,220,150);
        g.drawImage(notEditingUsername,220,265);
        g.drawImage(EditingPassword, 220, 375);
        firstname.render(container,g);
        lastname.render(container,g);
        username.render(container,g);
        password.render(container,g);
        retypepassword.render(container,g);
        g.drawString(mouse, 50, 100);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();
        mouse = "x pos = "+xpos+"   y pos = "+ypos;

        Input input = container.getInput();	//keyboard and mouse input

        if(isFirstTimePassword && password.hasFocus()) {
            password.setText("");
            password.setTextColor(Color.black);
            isFirstTimePassword = false;
        }
        if(isFirstTimeRetypePassword && retypepassword.hasFocus()) {
            retypepassword.setText("");
            retypepassword.setTextColor(Color.black);
            isFirstTimeRetypePassword = false;
        }
        if((xpos>20 && xpos<85) && (ypos>483 && ypos<518) ){
            if(input.isMousePressed(0)){
                game.enterState(8); //go back to nonedit state (back)
            }
        }
        if((xpos>525 && xpos<572) && (ypos>12 && ypos<36) ){
            if(input.isMousePressed(0)){
                if(retypepassword.getText().equals(password.getText())) {
                    game.enterState(8); //go to edit password
                }
            }
        }
    }
}
