import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;
import org.omg.CORBA.Current;

/**
 * Created by Marie Curie on 01/04/2017.
 */
public class AllTasks extends BasicGameState {

    //Logic Shit
    private static final int ALL_TASKS = 0;
    private static final int TODAY_VIEW = 1;
    private static final int WEEKLY_VIEW = 2;
    private static final int SELECTOR_HEIGHT = 34;
    private static final int LEFTPANEL_WIDTH = 225;

    private boolean isAddingNewTask = false;
    private boolean hasSelectedNewTask = false;
    private boolean hasSelectedDeadline = false;
    private boolean hasSelectedCategory = false;
    public String mouse = "";
    private int currentView = ALL_TASKS;
    private int topYPos;
    private boolean updateTaskVector;
    Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
    //UI Shit
    Font sanFranUITxtRegJava = null;
    Font sanFranUITxtBoldJava = null;
    UnicodeFont sanFranTxReg = null;
    UnicodeFont sanFranTxRegGrey = null;
    UnicodeFont sanFranTxBold = null;
    Image bg = null;
    Image leftpanel = null;
    Image iconalltasks = null;
    Image icontoday = null;
    Image iconthisweek = null;
    Image iconcategory = null;
    Image selectorview = null;
    Image empty = null;
    Image addnewcategory = null;
    Image fab = null;
    Image newTaskWindow = null;
    Image taskBox = null;
    Image boxNotDone = null;
    Image textbox = null;
    Vector<Image> taskBoxVector = new Vector();
    Vector<TaskClass> taskVector = new Vector();
    String[] views = new String[]{"All Tasks", "Today", "This Week"};
    TextField taskName, deadline, category;

    Font font = new Font("Verdana", Font.PLAIN, 26);
    TrueTypeFont ttf = new TrueTypeFont(font, true);

    Sound clickSnd, errorSnd;
    public AllTasks(int alltasks) {
    }

    @Override
    public int getID() {
        return 4;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        topYPos = 0;
        //Loading
        updateTaskVector = true;
        clickSnd = new Sound("res/Sound/click.wav");
        errorSnd = new Sound ("res/Sound/error.wav");
        try {
            bg = new Image("res/Components/04 home/bg-main.png");
            leftpanel = new Image("res/Components/04 home/leftpanel.png");
            iconalltasks = new Image("res/Components/04 home/iconalltasks.png");
            icontoday = new Image("res/Components/04 home/icontoday.png");
            iconthisweek = new Image("res/Components/04 home/iconthisweek.png");
            iconcategory = new Image("res/Components/04 home/iconcategory.png");
            selectorview = new Image("res/Components/04 home/selectorview.png");
            empty = new Image(  "res/Components/04 home/empty.png");
            addnewcategory = new Image("res/Components/04 home/addnewcategory.png");
            fab = new Image("res/Components/04 home/fab.png");
            newTaskWindow = new Image("res/Components/04 home/newtaskwindow.png");
            taskBox = new Image("res/Components/04 home/taskbox.png");
            boxNotDone = new Image("res/Components/04 home/boxnotdone.png");
            sanFranUITxtRegJava = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/Fonts/SFUIText-Regular.ttf"));
            sanFranUITxtBoldJava = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/Fonts/SFUIText-Bold.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        sanFranUITxtRegJava = sanFranUITxtRegJava.deriveFont(Font.PLAIN, 12.f);
        sanFranUITxtBoldJava = sanFranUITxtBoldJava.deriveFont(Font.PLAIN, 12.f);

        //Since fonts are a kind of a bitch, they'll need extra steps before rendering
        sanFranTxReg = new UnicodeFont(sanFranUITxtRegJava);
        sanFranTxReg.addAsciiGlyphs();
        sanFranTxReg.getEffects().add(new ColorEffect(Color.black));
        try {
            sanFranTxReg.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        sanFranTxBold = new UnicodeFont(sanFranUITxtBoldJava);
        sanFranTxBold.addAsciiGlyphs();
        sanFranTxBold.getEffects().add(new ColorEffect(Color.black));
        try {
            sanFranTxBold.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        sanFranTxRegGrey = new UnicodeFont(sanFranUITxtRegJava);
        sanFranTxRegGrey.addAsciiGlyphs();
        sanFranTxRegGrey.getEffects().add(new ColorEffect(Color.gray));
        try {
            sanFranTxRegGrey.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        taskName = new TextField(container, sanFranTxReg, 502, 540-161, 224, 20);
        deadline = new TextField(container, sanFranTxReg, 502, 540-125, 224, 20);
        category = new TextField(container, sanFranTxReg, 502, 540-89, 244, 20);

        initialize(taskName);
        initialize(deadline);
        initialize(category);

        container.setShowFPS(false);
    }
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        if(updateTaskVector) {
            updateTaskVector = false;
            taskVector = TeazyDBMnpln.getTasks(CurrentUser.getUsername());
        }

        renderLeftBar(g, currentView);
        renderAllTasks(g);
        if(isAddingNewTask){
            renderNewTask(container, g);
        } else {
            renderFAB(g);
        }
    }

    private void renderLeftBar(Graphics g, int currentView) {
        //Rendering
        g.drawImage(bg,0,0);
        g.drawImage(leftpanel, 0,0);
        g.drawImage(selectorview,0, 145 + selectorview.getHeight()*currentView);
        g.drawImage(iconalltasks,25, 155);
        g.drawImage(icontoday,25, 145+34+(34-24)/2);
        g.drawImage(iconthisweek,25,145+34*2+(34-24)/2);
        g.drawImage(iconcategory,25,280);
        g.drawImage(addnewcategory, 30,320);


        if(taskVector.isEmpty()){
            g.drawImage(empty, 320, 110);
        }

        for (int i = 0; i < 3; i++) {
            if(i == currentView) {
                sanFranTxBold.drawString(75, 155 + selectorview.getHeight()*i, views[i]);
            } else {
                sanFranTxReg.drawString(75, 155 + selectorview.getHeight()*i, views[i]);
            }
        }
        //g.drawString(mouse, 50, 100);
        ttf.drawString(15, 84,Character.toString(CurrentUser.getFirstname().charAt(0)) +
                Character.toString(CurrentUser.getLastname().charAt(0)));
    }

    private void renderNewTask(GameContainer container, Graphics g) {

        g.drawImage(newTaskWindow, 460, 325);

        if(!hasSelectedNewTask && taskName.getText().equals("")) {
            sanFranTxRegGrey.drawString(502, 540-161, "Type Task Name");
        }
        if(!hasSelectedDeadline && deadline.getText().equals("")) {
            sanFranTxRegGrey.drawString(502, 540-125, "Set due date (MM/DD/YY)");
        }
        if(!hasSelectedCategory && category.getText().equals("")) {
            sanFranTxRegGrey.drawString(502, 540-89, "Set category");
        }

        taskName.render(container, g);
        deadline.render(container, g);
        category.render(container, g);
    }

    private void renderAllTasks(Graphics g) {
        for(int i = 0; i < taskVector.size(); i++) {
            g.drawImage(taskBox, 90*3, topYPos + 10 + (40 + 10) * i);
            sanFranTxReg.drawString(90*3 + 40, topYPos + 10 + 11 + (40 + 10) * i, taskVector.elementAt(i).getDescription());
            g.drawImage(boxNotDone, 90*3 + 12, topYPos + 10 + 11 + (40 + 10) * i) ;
        }
    }

    private void renderFAB(Graphics g) {
        g.drawImage(fab,730,460);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();
        mouse = "x pos = "+xpos+"   y pos = "+ypos;

        Input input = container.getInput();	//keyboard and mouse input

        if(hasSelectedNewTask){
            taskName.setFocus(true);
        }

        if(hasSelectedDeadline){
            deadline.setFocus(true);
        }

        if(hasSelectedCategory){
            category.setFocus(true);
        }

        if(input.isMousePressed(0)) {
            if(isAddingNewTask) {
                if((xpos > 471 && xpos < 782) && (ypos > 29 && ypos < 207) ){
                    //if mouse is in newTaskBox area
                    if(xpos > 496 && xpos < 766) {
                        //is mouse is in xpos range of the textboxes in taskBox
                        if(ypos > 137 && ypos < 162) {
                            //has selected new task
                            hasSelectedNewTask = true;
                            hasSelectedDeadline = false;
                            hasSelectedCategory = false;
                        }else if(ypos > 106 && ypos < 129) {
                            //has selected deadline
                            hasSelectedNewTask = false;
                            hasSelectedDeadline = true;
                            hasSelectedCategory = false;
                        }else if(ypos > 69 && ypos < 93) {
                            //has selected category
                            hasSelectedNewTask = false;
                            hasSelectedDeadline = false;
                            hasSelectedCategory = true;
                        } else if((xpos>730 && xpos<766) && (ypos>46 && ypos<60) ) {
                            //done
                            hasSelectedNewTask = false;
                            hasSelectedDeadline = false;
                            hasSelectedCategory = false;
                            isAddingNewTask = false;
                            //TODO what the hell does thi do
                            String tempTaskName = taskName.getText();
                            String tempUserName = CurrentUser.getUsername();
                            String tempCategory = category.getText();
                            String tempDeadline = deadline.getText();

                            if(!CalendarDemo.isThisDateValid(tempDeadline)){
                                if(!errorSnd.playing()){
                                    errorSnd.play();
                                }
                            }else if(!TeazyDBMnpln.categoryExists(tempCategory)){
                                if(!errorSnd.playing()){
                                    errorSnd.play();
                                }
                            }
                            else if(tempDeadline.length() > 0&& tempTaskName.length() > 0) {
                                if(tempCategory.length() == 0)
                                    tempCategory = "General";
                                taskVector.addElement(new TaskClass(tempTaskName, tempCategory, tempDeadline));
                                TeazyDBMnpln.addTaskDB(tempUserName, tempTaskName, tempCategory, tempDeadline);
                                updateTaskVector = true;
                                initialize(taskName);
                            }else{
                                if(!errorSnd.playing()){
                                    errorSnd.play();
                                }
                            }
                        } else {
                            //didn't click on any interactable object on newTaskBox
                            hasSelectedNewTask = false;
                            hasSelectedDeadline = false;
                            hasSelectedCategory = false;
                        }
                    }
                } else {
                    //if newTaskBox is open but user clicked outside the newTaskBox, the TaskBox closes
                    isAddingNewTask = false;
                    initialize(taskName);
                }
            }
            else {
                if (xpos >= 0 && xpos <= LEFTPANEL_WIDTH) {
                    if (ypos <= (540 - 145) - SELECTOR_HEIGHT && ypos >= (540 - 145) - SELECTOR_HEIGHT) {
                        currentView = ALL_TASKS;
                    }

                    if (ypos <= (540 - 145) - SELECTOR_HEIGHT * TODAY_VIEW && ypos >= (540 - 145) - SELECTOR_HEIGHT * (TODAY_VIEW + 1)) {
                        currentView = TODAY_VIEW;
                    }

                    if (ypos <= (540 - 145) - SELECTOR_HEIGHT * WEEKLY_VIEW && ypos >= (540 - 145) - SELECTOR_HEIGHT * (WEEKLY_VIEW + 1)) {
                        currentView = WEEKLY_VIEW;
                    }
                }

                if((xpos>735 && xpos<781) && (ypos>28 && ypos<77) ) {//forFAB
                    isAddingNewTask = true; //mugawas ang new task window
                }
            }
        }
        //if (bottomYPos > 510)
        if(!isAddingNewTask) {
            topYPos = topYPos + Mouse.getDWheel()/8;
        }
    }

    private static void initialize (TextField taskname){
        taskname.setBorderColor(org.newdawn.slick.Color.transparent);
        taskname.setBackgroundColor(org.newdawn.slick.Color.transparent);
        taskname.setTextColor(org.newdawn.slick.Color.black);
        taskname.setCursorVisible(false);
        taskname.setFocus(false);
        taskname.setConsumeEvents(true);
        taskname.setAcceptingInput(true);
        taskname.setText("");
    }
}
