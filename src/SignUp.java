import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Marie Curie on 01/04/2017.
 */
public class SignUp extends BasicGameState {
    private String mouse = "";
    private TextField firstname, lastname, username, password, retypepass, currentschool;
    private boolean isFirstTimeUsername = true, isFirstTimePassword = true, isFirstTimeFirstName = true, isFirstTimeLastName = true, isFirstTimeRetypePass = true, isFirstTimeCurrentSchool = true;
    private Sound clickSnd, errorSnd;
    private boolean usernameexist = false, hasspaces = false, passwordnotlong = false, passwordmismatch1 = false;
    public SignUp(int signup) {
    }

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        clickSnd = new Sound("res/Sound/click.wav");
        errorSnd = new Sound("res/Sound/error.wav");
        container.setShowFPS(false);
        firstname = new TextField(container, container.getDefaultFont(), 291, 212, 126, 20);
        lastname = new TextField(container, container.getDefaultFont(), 425, 212, 90, 20);
        username = new TextField(container, container.getDefaultFont(), 291, 248, 224, 20);
        password = new TextField(container, container.getDefaultFont(), 291, 281, 224, 20);
        retypepass = new TextField(container, container.getDefaultFont(), 291, 315, 224, 20);
        currentschool  = new TextField(container, container.getDefaultFont(), 291, 349, 224, 20);
        initialize(firstname, lastname,username,password,retypepass,currentschool);
        usernameexist =  hasspaces =  passwordnotlong =  passwordmismatch1 = false;
        isFirstTimeUsername = isFirstTimePassword = isFirstTimeFirstName =  isFirstTimeLastName = isFirstTimeRetypePass =  isFirstTimeCurrentSchool = true;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        Image bg = new Image("res/Components/img-bg01-03.png");
        Image signinform = new Image("res/Components/02 sign up/signinform.png");
        Image passwordmismatch = new Image("res/Components/02 sign up/passwordmismatch.png");
        Image usernamealreadyexists = new Image("res/Components/02 sign up/usernamealreadyexists.png");
        Image usernamehasspaces = new Image("res/Components/02 sign up/usernamehasspaces.png");
        Image passwordnotlong1 = new Image("res/Components/02 sign up/usernamenotlong.png");

        g.drawImage(bg,0,0);
        g.drawImage(signinform, 230,100);
        g.drawString(mouse, 0, 0);
        firstname.render(container,g);
        lastname.render(container,g);
        username.render(container,g);
        password.render(container,g);
        retypepass.render(container,g);
        currentschool.render(container,g);
        if(passwordmismatch1) {
            g.drawImage(passwordmismatch, 517, 315);
        }
        if(usernameexist) {
            g.drawImage(usernamealreadyexists, 517, 248);
        }
        if(hasspaces) {
            g.drawImage(usernamehasspaces, 517, 248);
        }
        if(passwordnotlong) {
            g.drawImage(passwordnotlong1, 517, 281);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();
        mouse = "x pos = "+xpos+"   y pos = "+ypos;

        Input input = container.getInput();	//keyboard and mouse input
        if((xpos>296 && xpos<328) && (ypos>121 && ypos<134) ){
            if(input.isMouseButtonDown(0)){
                if(!clickSnd.playing()){
                    clickSnd.play();
                }
                initialize(firstname, lastname,username,password,retypepass,currentschool);
                usernameexist =  hasspaces =  passwordnotlong =  passwordmismatch1 = false;
                isFirstTimeFirstName = isFirstTimeLastName = isFirstTimeUsername = isFirstTimePassword = isFirstTimeRetypePass = isFirstTimeCurrentSchool = true;
                game.enterState(0); //go to landing state
            }
        }

        else if((xpos>487 && xpos<509) && (ypos>122 && ypos<135) ){
            if(input.isMouseButtonDown(0)){
                if(!password.getText().equals(retypepass)){
                    passwordmismatch1 = true;
                    System.out.println("sayop ang password");
                }
                if(TeazyDBMnpln.usernameExists(username.getText())){
                    usernameexist = true;
                    System.out.println("naa nay sulod");
                }
                if (password.getText().length() < 6 || retypepass.getText().length() < 6){
                    passwordnotlong = true;
                    System.out.println("mubo ra siya");
                }
                if (username.getText().contains(" ") && !username.getText().equals("")){
                    hasspaces = true;
                    System.out.println("naay space");
                }
                if(!passwordmismatch1 && !usernameexist && !hasspaces && !passwordnotlong) {
                    if(!clickSnd.playing()){
                        clickSnd.play();
                    }
                    isFirstTimeFirstName = isFirstTimeLastName = isFirstTimeUsername = isFirstTimePassword = isFirstTimeRetypePass = isFirstTimeCurrentSchool = true;
                    //Added this
                    System.out.println("Current user username: " + username.getText());
                    System.out.println("Current user password: " + password.getText());
                    System.out.println("Current user firstname: " + firstname.getText());
                    System.out.println("Current user lastname: " + lastname.getText());
                    System.out.println("Current user school: " + currentschool.getText());
                    System.out.println("Current user taskcount: " + CurrentUser.getTaskcount());
                    TeazyDBMnpln.addStudentDB(username.getText(), password.getText(),
                            firstname.getText(), lastname.getText(), currentschool.getText());
                    //Added this
                    CurrentUser.setUsername(username.getText());
                    CurrentUser.setPassword(password.getText());
                    CurrentUser.setFirstname(TeazyDBMnpln.getFName(username.getText()));
                    CurrentUser.setLastname(TeazyDBMnpln.getLName(username.getText()));
                    CurrentUser.setCurrentschool(TeazyDBMnpln.getSchool(username.getText()));
                    CurrentUser.setTaskcount(TeazyDBMnpln.getTaskCount(username.getText()));

                    System.out.println("Current user username: " + CurrentUser.getUsername());
                    System.out.println("Current user password: " + CurrentUser.getPassword());
                    System.out.println("Current user firstname: " + CurrentUser.getFirstname());
                    System.out.println("Current user lastname: " + CurrentUser.getLastname());
                    System.out.println("Current user school: " + CurrentUser.getCurrentschool());
                    System.out.println("Current user taskcount: " + CurrentUser.getTaskcount());
                    game.enterState(4); //go to main user
                }else{
                    if(!errorSnd.playing()){
                        errorSnd.play();
                    }
                }
            }
        }

        if(isFirstTimeUsername && username.hasFocus()) {
            username.setText("");
            username.setTextColor(Color.black);
            isFirstTimeUsername = false;
        }

        if(isFirstTimePassword && password.hasFocus()) {
            password.setText("");
            password.setTextColor(Color.black);
            isFirstTimePassword = false;
        }
        if(isFirstTimeFirstName && firstname.hasFocus()) {
            firstname.setText("");
            firstname.setTextColor(Color.black);
            isFirstTimeFirstName = false;
        }

        if(isFirstTimeLastName && lastname.hasFocus()) {
            lastname.setText("");
            lastname.setTextColor(Color.black);
            isFirstTimeLastName = false;
        }
        if(isFirstTimeRetypePass && retypepass.hasFocus()) {
            retypepass.setText("");
            retypepass.setTextColor(Color.black);
            isFirstTimeRetypePass = false;
        }

        if(isFirstTimeCurrentSchool && currentschool.hasFocus()) {
            currentschool.setText("");
            currentschool.setTextColor(Color.black);
            isFirstTimeCurrentSchool = false;
        }

        if (!firstname.hasFocus() && firstname.getText().equals("")){
            firstname.setTextColor(Color.gray);
            firstname.setText("First Name");
            isFirstTimeFirstName = true;
        }
        if (!lastname.hasFocus() && lastname.getText().equals("")){
            lastname.setTextColor(Color.gray);
            lastname.setText("Last Name");
            isFirstTimeLastName = true;
        }
        if (!username.hasFocus() && username.getText().equals("")){
            username.setTextColor(Color.gray);
            username.setText("Username");
            isFirstTimeUsername = true;
        }
        if (!password.hasFocus() && password.getText().equals("")){
            password.setTextColor(Color.gray);
            password.setText("Password");
            isFirstTimePassword = true;
        }
        if (!retypepass.hasFocus() && retypepass.getText().equals("")){
            retypepass.setTextColor(Color.gray);
            retypepass.setText("Retype password");
            isFirstTimeRetypePass= true;
        }
        if (!currentschool.hasFocus() && currentschool.getText().equals("")){
            currentschool.setTextColor(Color.gray);
            currentschool.setText("Current school");
            isFirstTimeCurrentSchool = true;
        }
    }

    public static void initialize (TextField firstname, TextField lastname, TextField username, TextField password, TextField retypepass, TextField currentschool){
        firstname.setBorderColor(Color.white);
        firstname.setBackgroundColor(Color.white);
        firstname.setTextColor(Color.gray);
        firstname.setCursorVisible(false);
        firstname.setFocus(false);
        firstname.setConsumeEvents(true);
        firstname.setAcceptingInput(true);
        firstname.setText("First Name");

        lastname.setBorderColor(Color.white);
        lastname.setBackgroundColor(Color.white);
        lastname.setTextColor(Color.gray);
        lastname.setCursorVisible(false);
        lastname.setFocus(false);
        lastname.setConsumeEvents(true);
        lastname.setAcceptingInput(true);
        lastname.setText("Last Name");


        username.setBorderColor(Color.white);
        username.setBackgroundColor(Color.white);
        username.setTextColor(Color.gray);
        username.setCursorVisible(false);
        username.setFocus(false);
        username.setConsumeEvents(true);
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

        retypepass.setBorderColor(Color.white);
        retypepass.setBackgroundColor(Color.white);
        retypepass.setTextColor(Color.gray);
        retypepass.setCursorVisible(false);
        retypepass.setFocus(false);
        retypepass.setConsumeEvents(true);
        retypepass.setAcceptingInput(true);
        retypepass.setText("Retype password");

        currentschool.setBorderColor(Color.white);
        currentschool.setBackgroundColor(Color.white);
        currentschool.setTextColor(Color.gray);
        currentschool.setCursorVisible(false);
        currentschool.setFocus(false);
        currentschool.setConsumeEvents(true);
        currentschool.setAcceptingInput(true);
        currentschool.setText("Current school");
    }
}
