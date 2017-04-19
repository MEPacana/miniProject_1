import org.lwjgl.input.Mouse;
import org.lwjgl.Sys;
import java.text.ParseException;
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
    Sound errorSnd, clickSnd;
    public boolean isFirstTimeUsername = true, noteditusername = true;
    public boolean usernameexists = false, hasspace = false;

    public EditUsername(int editusername) {
    }

    @Override
    public int getID() {
        return 10;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        container.setShowFPS(false);
        isFirstTimeUsername = noteditusername = true;
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

        lastname.setBorderColor(Color.transparent);
        lastname.setBackgroundColor(Color.transparent);
        lastname.setTextColor(Color.gray);
        lastname.setCursorVisible(false);
        lastname.setFocus(false);
        lastname.setConsumeEvents(false);
        lastname.setAcceptingInput(false);

        username.setBorderColor(Color.transparent);
        username.setBackgroundColor(Color.transparent);
        username.setTextColor(Color.black);
        username.setCursorVisible(false);
        username.setFocus(false);
        username.setConsumeEvents(false);
        username.setAcceptingInput(true);

        password.setBorderColor(Color.transparent);
        password.setBackgroundColor(Color.transparent);
        password.setTextColor(Color.gray);
        password.setCursorVisible(false);
        password.setFocus(false);
        password.setConsumeEvents(false);
        password.setAcceptingInput(false);

        errorSnd = new Sound("res/Sound/error.wav");
        clickSnd = new Sound ("res/Sound/click.wav");

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        Image bg = new Image("res/Components/05 edit account/bg.png");
        Image backbutton = new Image("res/Components/05 edit account/backButton.png");
        Image notEditingName = new Image("res/Components/05 edit account/isNotEditingName.png");
        Image EditingUsername = new Image("res/Components/05 edit account/isEditingUsername.png");
        Image notEditingPassword = new Image("res/Components/05 edit account/isNotEditingPassword.png");
        Image exists = new Image("res/Components/02 sign up/usernamealreadyexists.png");
        Image spaces = new Image("res/Components/02 sign up/usernamehasspaces.png");
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
        firstname.setText(CurrentUser.getFirstname());
        lastname.setText(CurrentUser.getLastname());
        if (noteditusername) {
            username.setText(CurrentUser.getUsername());
        }
        password.setText(CurrentUser.getPassword());
        if (usernameexists){
            g.drawImage(exists,570,300);
        }
        if(hasspace){
            g.drawImage(spaces,570,280);
        }



    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();
        mouse = "x pos = "+xpos+"   y pos = "+ypos;

        Input input = container.getInput();	//keyboard and mouse input

        if(isFirstTimeUsername && username.hasFocus()) {
            noteditusername = false;
            username.setText("");
            username.setTextColor(Color.black);
            isFirstTimeUsername = false;
        }
        if (!username.hasFocus() && username.getText().equals("")){
            username.setTextColor(Color.black);
            username.setText(CurrentUser.getUsername());
            isFirstTimeUsername = true;
        }
        if((xpos>20 && xpos<85) && (ypos>483 && ypos<518) ){
            if(input.isMousePressed(0)){
                isFirstTimeUsername = noteditusername = true;
                usernameexists = hasspace = false;
                game.enterState(8); //go back to nonedit state (back)
            }
        }
        if((xpos>525 && xpos<572) && (ypos>184 && ypos<207) ){
            if(input.isMousePressed(0)){
                if(TeazyDBMnpln.usernameExists(username.getText())){
                    usernameexists = true;
                }
                else{
                    usernameexists = false;
                }
                if (username.getText().contains(" ")){
                    hasspace = true;
                }
                else{
                    hasspace = false;
                }
                if(!usernameexists && !hasspace && !username.getText().equals("")) {
                        TeazyDBMnpln.updateUsername(CurrentUser.getUsername(),username.getText());
                        CurrentUser.setUsername(username.getText());
                        game.enterState(8); //go to non edit state
                }else{
                    if(!errorSnd.playing()) {
                        errorSnd.play();
                    }
                }
            }
        }
    }
}
