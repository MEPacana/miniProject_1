import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Marie Curie on 01/04/2017.
 */
public class LogIn extends BasicGameState {
    private String mouse = "";
    private TextField username;
    private TextField password;
    private boolean isFirstTimeUsername = true, isFirstTimePassword = true;
    private Sound clickSnd,errorSnd;
    public LogIn(int home) {
    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        clickSnd = new Sound("res/Sound/click.wav");
        errorSnd = new Sound("res/Sound/error.wav");
        container.setShowFPS(false);
        password = new TextField(container, container.getDefaultFont(), 284, 290, 224, 20);
        username = new TextField(container, container.getDefaultFont(), 284, 255, 224, 20);
        initialize(username,password);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        Image bg = new Image("res/Components/img-bg01-03.png");
        Image loginform = new Image("res/Components/03 log in/loginform.png");
        g.drawImage(bg,0,0);
        g.drawImage(loginform, 255,160);
        username.render(container,g);
        password.render(container,g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();
        mouse = "x pos = "+xpos+"   y pos = "+ypos;
        Input input = container.getInput();	//keyboard and mouse input
        if((xpos>288 && xpos<320) && (ypos>192 && ypos<205) ){
            if(input.isMouseButtonDown(0)){
                if(!clickSnd.playing()) {
                    clickSnd.play();
                }
                initialize(username,password);
                isFirstTimeUsername = isFirstTimePassword = true;
                game.enterState(0); //go to landing state
            }
        }
        else if((xpos>482 && xpos<502) && (ypos>192 && ypos<206) ){
            if(input.isMouseButtonDown(0)){
                isFirstTimeUsername = isFirstTimePassword = true;
                //Added Check
                if(TeazyDBMnpln.usernamePasswordCheck(username.getText(),password.getText())) {
                    if(!clickSnd.playing()) {
                        clickSnd.play();
                    }
                    //ADDED THE CURRENT USER CLASS AND ITS COMPONENTS -CIONG
                    CurrentUser.setUsername(username.getText());
                    System.out.println("Current user username: " + CurrentUser.getUsername());
                    CurrentUser.setPassword(password.getText());
                    System.out.println("Current user password: " + CurrentUser.getPassword());
                    CurrentUser.setFirstname(TeazyDBMnpln.getFName(username.getText()));
                    System.out.println("Current user firstname: " + CurrentUser.getFirstname());
                    CurrentUser.setLastname(TeazyDBMnpln.getLName(username.getText()));
                    System.out.println("Current user lastname: " + CurrentUser.getLastname());
                    CurrentUser.setCurrentschool(TeazyDBMnpln.getSchool(username.getText()));
                    System.out.println("Current user school: " + CurrentUser.getCurrentschool());
                    CurrentUser.setTaskcount(TeazyDBMnpln.getTaskCount(username.getText()));
                    System.out.println("Current user taskcount: " + CurrentUser.getTaskcount());

                    initialize(username,password);
                    game.enterState(4); //go to main user
                }else{
                    if(!errorSnd.playing()){
                        errorSnd.play();
                    }
                }
                //Added Check
            }
        }

        if(isFirstTimeUsername && username.hasFocus()) {
            username.setText("");
            username.setTextColor(Color.black);
            isFirstTimeUsername = false;
            System.out.println("misuload hahahahahha " + username.getText());
        }

        if(isFirstTimePassword && password.hasFocus()) {
            password.setText("");
            password.setTextColor(Color.black);
            isFirstTimePassword = false;
        }
        if (!username.hasFocus() && username.getText().equals("")){
            username.setTextColor(Color.gray);
            username.setText("Username");
            isFirstTimeUsername = true;
        }
        if(!password.hasFocus() && password.getText().equals("")){
            password.setTextColor(Color.gray);
            password.setText("Password");
            isFirstTimePassword = true;
        }
    }
    public static void initialize(TextField username, TextField password){
        username.setBorderColor(Color.white);
        username.setBackgroundColor(Color.white);
        username.setTextColor(Color.gray);
        username.setCursorVisible(false);
        username.setFocus(false);
        username.setConsumeEvents(false);
        username.setAcceptingInput(true);
        username.setText("Username");

        password.setBorderColor(Color.white);
        password.setBackgroundColor(Color.white);
        password.setTextColor(Color.gray);
        password.setCursorVisible(false);
        password.setFocus(false);
        password.setConsumeEvents(true);
        password.setAcceptingInput(true);
        password.setText("Password");
    }

}
