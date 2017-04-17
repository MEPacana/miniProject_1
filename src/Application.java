/**
 * Created by User on 3/31/2017.
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Application extends StateBasedGame{
    public static final String appName = "Teazy";
    public static final int landingstate = 0;
    public static final int login = 1;
    public static final int signup = 2;
    public static final int main = 3;
    public static final int alltasks = 4;
    public static final int todayview = 5;
    public static final int weekview = 6;
    public static final int category = 7;
    public static final int nonedit = 8;
    public static final int editname = 9;
    public static final int editusername = 10;
    public static final int editpassword = 11;

    public Application(String appName){
        super(appName);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new LandingState(landingstate));
        this.addState(new LogIn(login));
        this.addState(new SignUp(signup));
        this.addState(new MainUser(main));
        this.addState(new AllTasks(alltasks));
        this.addState(new TodayView(todayview));
        this.addState(new WeekView(weekview));
        this.addState(new Category(category));
        this.addState(new NonEdit(nonedit));
        this.addState(new EditName(editname));
        this.addState(new EditUsername(editusername));
        this.addState(new EditPassword(editpassword));
    }

    public static void main(String[] args){
        AppGameContainer appgc;
        try{
            appgc = new  AppGameContainer((new Application(appName)));
            appgc.setDisplayMode(810,540,false);
            appgc.setIcon("res/Components/04 home/fab.png");
            appgc.start();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
}