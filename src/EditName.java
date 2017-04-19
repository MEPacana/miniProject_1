import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Marie Curie on 04/04/2017.
 */
public class EditName extends BasicGameState {
    public TextField firstname;
    public TextField lastname;
    public TextField username;
    public TextField password;
    public String mouse = "";
    public boolean isFirstTimeFirstName = true, isFirstTimeLastName = true, noteditingfn = true, noteditingln = true;
    Sound errorSnd, clickSnd;
    public EditName(int editname) {
    }

    @Override
    public int getID() {
        return 9;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

        errorSnd = new Sound("res/Sound/error.wav");
        clickSnd = new Sound ("res/Sound/click.wav");
        container.setShowFPS(false);
        isFirstTimeFirstName = isFirstTimeLastName = true;
        noteditingfn =  noteditingln = true;
        firstname = new TextField(container, container.getDefaultFont(), 233, 188, 198, 23);
        lastname = new TextField(container,container.getDefaultFont(),442,188,127,23);
        username = new TextField(container,container.getDefaultFont(),233,300,335,23);
        password = new TextField(container,container.getDefaultFont(),233,412,335,23);

        firstname.setBorderColor(Color.transparent);
        firstname.setBackgroundColor(Color.transparent);
        firstname.setTextColor(Color.black);
        firstname.setCursorVisible(false);
        firstname.setFocus(false);
        firstname.setConsumeEvents(false);
        firstname.setAcceptingInput(true);

        lastname.setBorderColor(Color.transparent);
        lastname.setBackgroundColor(Color.transparent);
        lastname.setTextColor(Color.black);
        lastname.setCursorVisible(false);
        lastname.setFocus(false);
        lastname.setConsumeEvents(false);
        lastname.setAcceptingInput(true);

        username.setBorderColor(Color.transparent);
        username.setBackgroundColor(Color.transparent);
        username.setTextColor(Color.gray);
        username.setCursorVisible(false);
        username.setFocus(false);
        username.setConsumeEvents(false);
        username.setAcceptingInput(false);

        password.setBorderColor(Color.transparent);
        password.setBackgroundColor(Color.transparent);
        password.setTextColor(Color.gray);
        password.setCursorVisible(false);
        password.setFocus(false);
        password.setConsumeEvents(false);
        password.setAcceptingInput(false);

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        Image bg = new Image("res/Components/05 edit account/bg.png");
        Image backbutton = new Image("res/Components/05 edit account/backButton.png");
        Image EditingName = new Image("res/Components/05 edit account/isEditingName.png");
        Image notEditingUsername = new Image("res/Components/05 edit account/isNotEditingUsername.png");
        Image notEditingPassword = new Image("res/Components/05 edit account/isNotEditingPassword.png");

        g.drawImage(bg,0,0);
        g.drawImage(backbutton,20,20);
        g.drawImage(EditingName,220,150);
        g.drawImage(notEditingUsername,220,265);
        g.drawImage(notEditingPassword, 220, 375);
        firstname.render(container,g);
        lastname.render(container,g);
        username.render(container,g);
        password.render(container,g);
        g.drawString(mouse, 50, 100);
        if (noteditingfn) {
            firstname.setText(CurrentUser.getFirstname());
        }
        if (noteditingln){
            lastname.setText(CurrentUser.getLastname());
        }
        username.setText(CurrentUser.getUsername());
        password.setText(CurrentUser.getPassword());

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();
        mouse = "x pos = "+xpos+"   y pos = "+ypos;

        Input input = container.getInput();	//keyboard and mouse input

        if(isFirstTimeFirstName && firstname.hasFocus()) {
            noteditingfn = false;
            firstname.setText("");
            firstname.setTextColor(Color.black);
            isFirstTimeFirstName = false;
        }

        if(isFirstTimeLastName && lastname.hasFocus()) {
            noteditingln = false;
            lastname.setText("");
            lastname.setTextColor(Color.black);
            isFirstTimeLastName = false;
        }

        if (!firstname.hasFocus() && firstname.getText().equals("")){
            firstname.setTextColor(Color.black);
            firstname.setText(CurrentUser.getFirstname());
            isFirstTimeFirstName = true;
        }
        if (!lastname.hasFocus() && lastname.getText().equals("")){
            lastname.setTextColor(Color.black);
            lastname.setText(CurrentUser.getLastname());
            isFirstTimeLastName = true;
        }
        if((xpos>20 && xpos<85) && (ypos>483 && ypos<518) ){
            if(input.isMousePressed(0)){
                isFirstTimeFirstName =isFirstTimeLastName = noteditingfn =  noteditingln = true;
                game.enterState(8); //go to non edit state (back)
            }
        }
        if((xpos>525 && xpos<572) && (ypos>295 && ypos<316) ){
            if(input.isMousePressed(0)){
                if(!firstname.getText().equals("") && !lastname.getText().equals("") && (firstname.getText().matches("[a-zA-Z\\s-]+") && lastname.getText().matches("[a-zA-Z]+"))) {
                    //TODO UPDATE SA DATABASE
                    CurrentUser.setFirstname(firstname.getText());
                    CurrentUser.setLastname(lastname.getText());
                    TeazyDBMnpln.updateName(CurrentUser.getUsername(),firstname.getText(),lastname.getText());
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