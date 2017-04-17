import org.lwjgl.input.Mouse;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Created by Marie Curie on 01/04/2017.
 */
public class AllTasks extends BasicGameState {

    //Logic Shit
    private static final int ALL_TASKS = 0;
    private static final int TODAY_VIEW = 1;
    private static final int WEEKLY_VIEW = 2;
    private static final int CATEGORY_VIEW = 3;
    private static final int SELECTOR_HEIGHT = 34;
    private static final int CATEGORY_S_HEIGHT = 28;
    private static final int LEFTPANEL_WIDTH = 225;

    private boolean isAddingNewTask = false;
    private boolean hasSelectedNewTask = false;
    private boolean hasSelectedDeadline = false;
    private boolean hasSelectedCategory = false;
    private boolean isWrongDateFormat = false;
    private boolean isProfileSettingsSelected = false;
    private boolean isAddingNewCategory = false;
    private boolean hasSelectedCategoryName = false;
    private boolean isCatAlreadyExists = false;
    public String mouse = "";
    private int currentView = ALL_TASKS, currentCategory = 0;
    private int topYPos ,topYPos2;
    private int newCatBoxYPos;


    //UI Shit
    Font sanFranUITxtRegJava = null;
    Font sanFranUITxtBoldJava = null;
    Font sanFranUITxtLtJava = null;
    Font sanFranUITxtLt14ptJava = null;
    Font sanFranUIDispThJava = null;
    Font sanFranUITxBoldBlueJava = null;
    Font sanFranUITxBoldBlue16Java = null;
    UnicodeFont sanFranTxReg = null;
    UnicodeFont sanFranTxRegGrey = null;
    UnicodeFont sanFranTxBold = null;
    UnicodeFont sanFranTxLtBlue = null;
    UnicodeFont SanFranTxLtWt14 = null;
    UnicodeFont SanFranTxLtBk14 = null;
    UnicodeFont sanFranUIDispTh = null;
    UnicodeFont sanFranUITxBoldBlue = null;
    UnicodeFont sanFranTxLtRed = null;
    UnicodeFont sanFranUITxBoldBlue16 = null;
    UnicodeFont SanFranTxLtBlue14 = null;
    Image bg = null;
    Image leftpanel = null;
    Image leftpanel2 = null;
    Image iconalltasks = null;
    Image icontoday = null;
    Image iconthisweek = null;
    Image iconcategory = null;
    Image selectorview = null;
    Image selectorcategory = null;
    Image empty = null;
    Image addnewcategory = null;
    Image newCategoryErr = null;
    Image newCategoryBox = null;
    Image fab = null;
    Image newTaskWindow = null;
    Image newTaskWindowError = null;
    Image taskBox = null;
    Image boxNotDone = null;
    Image boxDone = null;
    Image profileSettingsDots = null;
    Image profileSettingsBox = null;
    int updateTaskVector;
    boolean updateCategoryVector;
    Sound clickSnd, errorSnd;

    Vector<TaskClass> taskVector = new Vector();
    Vector<String> categories = new Vector();
    String[] views = new String[]{"All Tasks", "Today", "This Week"};
    private static String[] monthNames = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    private static String[] weekdayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    TextField taskName, deadline, category, newCategoryName;

    private String name = null;

    public AllTasks(int alltasks) {
    }

    @Override
    public int getID() {
        return 4;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        updateCategoryVector = true;
        updateTaskVector = 1;
        name = "John Doe";
        topYPos = 0;
        topYPos2 = 0;
        clickSnd = new Sound("res/Sound/click.wav");
        errorSnd = new Sound ("res/Sound/error.wav");
        //Loading
        try {
            bg = new Image("res/Components/04 home/bg-main.png");
            leftpanel = new Image("res/Components/04 home/leftpanel.png");
            leftpanel2 = new Image("res/Components/04 home/leftpanel2.png");
            iconalltasks = new Image("res/Components/04 home/iconalltasks.png");
            icontoday = new Image("res/Components/04 home/icontoday.png");
            iconthisweek = new Image("res/Components/04 home/iconthisweek.png");
            iconcategory = new Image("res/Components/04 home/iconcategory.png");
            selectorview = new Image("res/Components/04 home/selectorview.png");
            selectorcategory = new Image("res/Components/04 home/selectorcategory.png");
            empty = new Image("res/Components/04 home/empty.png");
            addnewcategory = new Image("res/Components/04 home/addnewcategory.png");
            newCategoryErr = new Image("res/Components/04 home/newcategorybox2err.png");
            newCategoryBox = new Image("res/Components/04 home/newcategorybox2.png");
            fab = new Image("res/Components/04 home/fab.png");
            newTaskWindow = new Image("res/Components/04 home/newtaskwindow.png");
            newTaskWindowError = new Image("res/Components/04 home/newtaskwindowerrordate.png");
            taskBox = new Image("res/Components/04 home/taskbox.png");
            boxNotDone = new Image("res/Components/04 home/boxnotdone.png");
            boxDone = new Image("res/Components/04 home/boxdone.png");
            profileSettingsDots = new Image("res/Components/04 home/profilesettingsmorenotselected.png");
            profileSettingsBox = new Image("res/Components/04 home/profilesettingsbox.png");
            sanFranUITxtRegJava = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/Fonts/SFUIText-Regular.ttf"));
            sanFranUITxtBoldJava = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/Fonts/SFUIText-Bold.ttf"));
            sanFranUITxtLtJava = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/Fonts/SFUIText-Light.ttf"));
            sanFranUITxtLt14ptJava = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/Fonts/SFUIText-Light.ttf"));
            sanFranUIDispThJava = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/Fonts/SFUIDisplay-Light.ttf"));
            sanFranUITxBoldBlueJava = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/Fonts/SFUIText-Bold.ttf"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        sanFranUITxtRegJava = sanFranUITxtRegJava.deriveFont(Font.PLAIN, 12.f);
        sanFranUITxtBoldJava = sanFranUITxtBoldJava.deriveFont(Font.PLAIN, 12.f);
        sanFranUITxtLtJava = sanFranUITxtLtJava.deriveFont(Font.PLAIN, 10.f);
        sanFranUITxtLt14ptJava = sanFranUITxtLt14ptJava.deriveFont(Font.PLAIN, 16.f);
        sanFranUIDispThJava = sanFranUIDispThJava.deriveFont(Font.PLAIN, 24.f);
        sanFranUITxBoldBlueJava = sanFranUITxtBoldJava.deriveFont(Font.PLAIN, 11.f);
        sanFranUITxBoldBlue16Java = sanFranUITxtBoldJava.deriveFont(Font.PLAIN, 16.f);

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

        sanFranTxLtBlue = new UnicodeFont(sanFranUITxtLtJava);
        sanFranTxLtBlue.addAsciiGlyphs();
        sanFranTxLtBlue.getEffects().add(new ColorEffect(new Color(33, 150, 243)));
        try {
            sanFranTxLtBlue.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        sanFranTxLtRed = new UnicodeFont(sanFranUITxtLtJava);
        sanFranTxLtRed.addAsciiGlyphs();
        sanFranTxLtRed.getEffects().add(new ColorEffect(Color.red));
        try {
            sanFranTxLtRed.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        SanFranTxLtWt14 = new UnicodeFont(sanFranUITxtLt14ptJava);
        SanFranTxLtWt14.addAsciiGlyphs();
        SanFranTxLtWt14.getEffects().add(new ColorEffect(Color.white));
        try {
            SanFranTxLtWt14.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        SanFranTxLtBlue14 = new UnicodeFont(sanFranUITxtLt14ptJava);
        SanFranTxLtBlue14.addAsciiGlyphs();
        SanFranTxLtBlue14.getEffects().add(new ColorEffect(new Color(33, 150, 243)));
        try {
            SanFranTxLtBlue14.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        SanFranTxLtBk14 = new UnicodeFont(sanFranUITxtLt14ptJava);
        SanFranTxLtBk14.addAsciiGlyphs();
        SanFranTxLtBk14.getEffects().add(new ColorEffect(Color.black));
        try {
            SanFranTxLtBk14.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        sanFranUIDispTh = new UnicodeFont(sanFranUIDispThJava);
        sanFranUIDispTh.addAsciiGlyphs();;
        sanFranUIDispTh.getEffects().add(new ColorEffect(Color.white));
        try {
            sanFranUIDispTh.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        sanFranUITxBoldBlue = new UnicodeFont(sanFranUITxBoldBlueJava);
        sanFranUITxBoldBlue.addAsciiGlyphs();;
        sanFranUITxBoldBlue.getEffects().add(new ColorEffect(new Color(33, 150, 243)));
        try {
            sanFranUITxBoldBlue.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        sanFranUITxBoldBlue16 = new UnicodeFont(sanFranUITxBoldBlue16Java);
        sanFranUITxBoldBlue16.addAsciiGlyphs();;
        sanFranUITxBoldBlue16.getEffects().add(new ColorEffect(new Color(33, 150, 243)));
        try {
            sanFranUITxBoldBlue16.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        taskName = new TextField(container, sanFranTxReg, 502, 540-161, 224, 20);
        deadline = new TextField(container, sanFranTxReg, 502, 540-125, 224, 20);
        category = new TextField(container, sanFranTxReg, 502, 540-89, 244, 20);
        newCategoryName = new TextField(container, sanFranTxReg, 502, 540 - 9, 244, 20);

        initialize(taskName);
        initialize(deadline);
        initialize(category);
        initialize(newCategoryName);
        container.setShowFPS(false);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        renderLeftBar(g, currentView);
        if(updateTaskVector!= 0) {
            if(updateTaskVector == 1) {
                taskVector = TeazyDBMnpln.getTasks(CurrentUser.getUsername());
            }else if(updateTaskVector ==2){
                //true if 3 input getTask and date
                taskVector = TeazyDBMnpln.getTasks(CurrentUser.getUsername(),CalendarDemo.currentDate(),true);
            }else if(updateTaskVector == 3){

            }else if(updateTaskVector == 4){

                //false if 3 input getTask and category
                taskVector = TeazyDBMnpln.getTasks(CurrentUser.getUsername(),categories.get(currentCategory),false);
            }
            updateTaskVector = 0;

        }
        if(updateCategoryVector){
            updateCategoryVector = false;
            categories = TeazyDBMnpln.getCategories(CurrentUser.getUsername());
        }
        if(isProfileSettingsSelected){
            renderProfileSettings(g);
        }
        if(isAddingNewCategory) {
            if(isCatAlreadyExists)
                renderCatAlreadyExists(g);
            else
                renderNewCategory(container, g);
        }
        switch (currentView) {
            case ALL_TASKS :
                renderAllTasks(g);
                break;
            case TODAY_VIEW :
                renderTodayView(g);
                break;
            case WEEKLY_VIEW :
                renderWeeklyView(g);
                break;
            case CATEGORY_VIEW :
                renderCategoryView(g, currentCategory);
                break;
            default:
                renderWeeklyView(g);
                break;
        }
        if(isAddingNewTask){
            if(isWrongDateFormat){
                renderWrongDate(g);
            } else {
                renderNewTask(container, g);
            }
        } else {
            renderFAB(g);
        }
    }

    private void renderProfileSettings(Graphics g) {
        g.drawImage(profileSettingsBox, selectorview.getWidth() - profileSettingsBox.getWidth(), 73);
        SanFranTxLtBk14.drawString(75, 90,CurrentUser.getFirstname()+ " "+CurrentUser.getLastname());
    }

    private void renderLeftBar(Graphics g, int currentView) {
        //Rendering
        g.drawImage(bg,0,0);
        g.drawImage(leftpanel, 0,0);
        if(currentView == CATEGORY_VIEW){
            g.drawImage(selectorview, 0, 145 + selectorview.getHeight() * (currentView + 1 + currentCategory) + topYPos2);
        }
        int i = 0;

        for(; i < categories.size(); i++){
            g.drawImage(iconcategory, 25, topYPos2 + 286 + SELECTOR_HEIGHT * i);
            if(currentCategory == i && currentView == CATEGORY_VIEW) {
                sanFranTxBold.drawString(25 + iconcategory.getWidth() + 20, topYPos2 + 290 + SELECTOR_HEIGHT * i, categories.elementAt(i));
            } else {
                sanFranTxReg.drawString(25 + iconcategory.getWidth() + 20, topYPos2 + 290 + SELECTOR_HEIGHT * i, categories.elementAt(i));
            }
        }
        g.drawImage(addnewcategory, 30, topYPos2 + 280 + SELECTOR_HEIGHT*i + (34 - addnewcategory.getHeight())/2);
        g.drawImage(leftpanel2, 0, SELECTOR_HEIGHT/2);
        g.drawImage(leftpanel2, 0, 0);
        sanFranUITxBoldBlue.drawString(30, 255, "Categories");
        if(currentView != CATEGORY_VIEW) {
            g.drawImage(selectorview, 0, 145 + selectorview.getHeight() * currentView);
        }
        g.drawImage(iconalltasks,25, 155);
        g.drawImage(icontoday,25, 145+34+(34-24)/2);
        g.drawImage(iconthisweek,25,145+34*2+(34-24)/2);
        g.drawImage(profileSettingsDots, 207, 510-422+profileSettingsDots.getHeight() );
        g.drawString(mouse, 0, 0);

        //TODO incorporate user's full name
        SanFranTxLtWt14.drawString(65, 90, CurrentUser.getFirstname()+ " "+CurrentUser.getLastname());
        sanFranUIDispTh.drawString(16, 86, Character.toString(CurrentUser.getFirstname().charAt(0)) +
                Character.toString(CurrentUser.getLastname().charAt(0)));

        for (int j = 0; j < 3; j++) {
            if(j == currentView) {
                sanFranTxBold.drawString(75, 155 + selectorview.getHeight()*j, views[j]);
            } else {
                sanFranTxReg.drawString(75, 155 + selectorview.getHeight()*j, views[j]);
            }
        }
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

    private void renderWrongDate (Graphics g) {
        g.drawImage(newTaskWindowError, 460, 325);
    }

    private void renderAllTasks(Graphics g) {
        if(taskVector.isEmpty()){
            g.drawImage(empty, 320, 110);
        } else {
            sanFranUITxBoldBlue16.drawString(90 * 3 + 3, topYPos + 10 + 11 + (40 + 10) * 0, "ALL TASKS");
            for (int i = 0, j = 1; i < taskVector.size(); i++, j++) {
                g.drawImage(taskBox, 90 * 3, topYPos + 10 + (40 + 10) * j);
                sanFranTxReg.drawString(90 * 3 + 40, topYPos + 10 + 11 + (40 + 10) * j, taskVector.elementAt(i).getDescription());
                sanFranTxLtBlue.drawString((90 * 3 + 40) + 418 - sanFranTxLtBlue.getWidth(taskVector.elementAt(i).getDeadline()),
                        topYPos + 10 + 13 + (40 + 10) * j, taskVector.elementAt(i).getDeadline());
            /* TODO late
             * if (taskVector.elementAt(i).isLate()) {
             *      sanFranTxLtRed.drawString((90*3 + 40) + 418 - sanFranTxLtBlue.getWidth(taskVector.elementAt(i).toStringDeadline()), topYPos + 10 + 13 + (40 + 10) * i, taskVector.elementAt(i).toStringDeadline());
             * } else {
             *      sanFranTxLtBlue.drawString((90*3 + 40) + 418 - sanFranTxLtBlue.getWidth(taskVector.elementAt(i).toStringDeadline()), topYPos + 10 + 13 + (40 + 10) * i, taskVector.elementAt(i).toStringDeadline());
             * }
             */
                g.drawImage(boxNotDone, 90 * 3 + 12, topYPos + 10 + 11 + (40 + 10) * j);
            }
        }
    }

    private void renderTodayView(Graphics g) {
        if(taskVector.isEmpty()){
            g.drawImage(empty, 320, 110);
        } else {
            sanFranUITxBoldBlue16.drawString(90 * 3 + 3, topYPos + 10 + 11 + (40 + 10) * 0, "TODAY'S TASKS");
            for (int i = 0, j = 1; i < taskVector.size(); i++, j++) {
                g.drawImage(taskBox, 90 * 3, topYPos + 10 + (40 + 10) * j);
                sanFranTxReg.drawString(90 * 3 + 40, topYPos + 10 + 11 + (40 + 10) * j,
                        taskVector.elementAt(i).getDescription());
                sanFranTxLtBlue.drawString((90 * 3 + 40) + 418 - sanFranTxLtBlue.getWidth(taskVector.elementAt(i).getDeadline()),
                        topYPos + 10 + 13 + (40 + 10) * j, taskVector.elementAt(i).getDeadline());
            /* TODO late
             * if (taskVector.elementAt(i).isLate()) {
             *      sanFranTxLtRed.d
             *      rawString((90*3 + 40) + 418 - sanFranTxLtBlue.getWidth(taskVector.elementAt(i).toStringDeadline()), topYPos + 10 + 13 + (40 + 10) * i, taskVector.elementAt(i).toStringDeadline());
             * } else {
             *      sanFranTxLtBlue.drawString((90*3 + 40) + 418 - sanFranTxLtBlue.getWidth(taskVector.elementAt(i).toStringDeadline()), topYPos + 10 + 13 + (40 + 10) * i, taskVector.elementAt(i).toStringDeadline());
             * }
             */
                    g.drawImage(boxNotDone, 90 * 3 + 12, topYPos + 10 + 11 + (40 + 10) * j);
            }
        }
    }

    private void renderWeeklyView(Graphics g) {
        if(taskVector.isEmpty()){
            g.drawImage(empty, 320, 110);
        } else {
        /*for(int i = 0; i < 7 ; i++) {
            sanFranUITxBoldBlue16.drawString(90*3 + 3, topYPos + 10 + 11 + (40 + 10) * 0, weekdayNames[(currentDay + i) %7]);
            SanFranTxLtBlue14.drawString(90*3 +3 + sanFranUITxBoldBlue16.getWidth("Monday"), topYPos + 10 + 11 + (40 + 10) * 0, "  " + taskday + taskMonth);
        }*/
            sanFranUITxBoldBlue16.drawString(90 * 3 + 3, topYPos + 10 + 11 + (40 + 10) * 0, "Monday");
            SanFranTxLtBlue14.drawString(90 * 3 + 3 + sanFranUITxBoldBlue16.getWidth("Monday"), topYPos + 10 + 11 + (40 + 10) * 0, "  13 Maytember");
            for (int i = 0, j = 1; i < taskVector.size(); i++, j++) {
                g.drawImage(taskBox, 90 * 3, topYPos + 10 + (40 + 10) * j);
                sanFranTxReg.drawString(90 * 3 + 40, topYPos + 10 + 11 + (40 + 10) * j, taskVector.elementAt(i).getDescription());
                sanFranTxLtBlue.drawString((90 * 3 + 40) + 418 - sanFranTxLtBlue.getWidth(taskVector.elementAt(i).getDeadline()), topYPos + 10 + 13 + (40 + 10) * j, taskVector.elementAt(i).getDeadline());
                g.drawImage(boxNotDone, 90 * 3 + 12, topYPos + 10 + 11 + (40 + 10) * j);
            }
        }
    }

    private void renderCategoryView(Graphics g, int index) {
        if(taskVector.isEmpty()){
            g.drawImage(empty, 320, 110);
        } else {
            sanFranUITxBoldBlue16.drawString(90 * 3 + 3, topYPos + 10 + 11 + (40 + 10) * 0, categories.elementAt(index));
            for (int i = 0, j = 1; i < taskVector.size(); i++, j++) {
                g.drawImage(taskBox, 90 * 3, topYPos + 10 + (40 + 10) * j);
                sanFranTxReg.drawString(90 * 3 + 40, topYPos + 10 + 11 + (40 + 10) * j, taskVector.elementAt(i).getDescription());
                sanFranTxLtBlue.drawString((90 * 3 + 40) + 418 - sanFranTxLtBlue.getWidth(taskVector.elementAt(i).getDeadline()), topYPos + 10 + 13 + (40 + 10) * j, taskVector.elementAt(i).getDeadline());
            /* TODO late
             * if (taskVector.elementAt(i).isLate()) {
             *      sanFranTxLtRed.drawString((90*3 + 40) + 418 - sanFranTxLtBlue.getWidth(taskVector.elementAt(i).toStringDeadline()), topYPos + 10 + 13 + (40 + 10) * i, taskVector.elementAt(i).toStringDeadline());
             * } else {
             *      sanFranTxLtBlue.drawString((90*3 + 40) + 418 - sanFranTxLtBlue.getWidth(taskVector.elementAt(i).toStringDeadline()), topYPos + 10 + 13 + (40 + 10) * i, taskVector.elementAt(i).toStringDeadline());
             * }
             */
                    g.drawImage(boxNotDone, 90 * 3 + 12, topYPos + 10 + 11 + (40 + 10) * j);
            }
        }
    }

    private void renderNewCategory(GameContainer container, Graphics g) {
        g.drawImage(newCategoryBox, 21 , newCatBoxYPos);

        if(!hasSelectedCategoryName && newCategoryName.getText().equals("")) {
            sanFranTxRegGrey.drawString(51, newCatBoxYPos + 9, "Enter Category Name");
        }

        newCategoryName.setLocation(21 + 30, newCatBoxYPos + 9);
        newCategoryName.render(container, g);
    }

    private void renderCatAlreadyExists(Graphics g) {
        g.drawImage(newCategoryErr, 21, newCatBoxYPos);
    }

    private void renderFAB(Graphics g) {
        g.drawImage(fab,730,460);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();
        mouse = "x pos = "+xpos+"   y pos = "+ypos;

        Input input = container.getInput(); //keyboard and mouse input

        if(hasSelectedNewTask){
            taskName.setFocus(true);
        }

        if(hasSelectedDeadline){
            deadline.setFocus(true);
        }

        if(hasSelectedCategory){
            category.setFocus(true);
        }

        if(hasSelectedCategoryName) {
            newCategoryName.setFocus(true);
        }

        if(input.isMousePressed(0)) {
            //if mouse was clicked
            if (isAddingNewTask) {
                //if user is adding new task
                if ((xpos > 471 && xpos < 782) && (ypos > 29 && ypos < 207)) {
                    //if mouse is in newTaskBox area
                    if (xpos > 496 && xpos < 766) {
                        if (isWrongDateFormat) {
                            isWrongDateFormat = false;
                        } else {
                            //is mouse is in xpos range of the textboxes in taskBox
                            if (ypos > 137 && ypos < 162) {
                                //has selected new task
                                hasSelectedNewTask = true;
                                hasSelectedDeadline = false;
                                hasSelectedCategory = false;
                            } else if (ypos > 106 && ypos < 129) {
                                //has selected deadline
                                hasSelectedNewTask = false;
                                hasSelectedDeadline = true;
                                hasSelectedCategory = false;
                            } else if (ypos > 69 && ypos < 93) {
                                //has selected category
                                hasSelectedNewTask = false;
                                hasSelectedDeadline = false;
                                hasSelectedCategory = true;
                            } else if ((xpos > 730 && xpos < 766) && (ypos > 46 && ypos < 60)) {
                                //done
                                hasSelectedNewTask = false;
                                hasSelectedDeadline = false;
                                hasSelectedCategory = false;
                                isAddingNewTask = false;

/*                                try {
                                    taskVector.addElement(new TaskClass(taskName.getText(),  category.getText(), deadline.getText()));
                                } catch (Exception e) {
                                    System.out.println("YO");
                                    isWrongDateFormat = true;
                                    isAddingNewTask = true;
                                }*/
                                String tempTaskName = taskName.getText();
                                String tempUserName = CurrentUser.getUsername();
                                String tempCategory = category.getText();
                                String tempDeadline = deadline.getText();
                                if(tempCategory.length() == 0)
                                    tempCategory = "General";

                                if(!CalendarDemo.isThisDateValid(tempDeadline)){
                                    if(!errorSnd.playing()){
                                        errorSnd.play();
                                    }
                                    isWrongDateFormat = true;
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

                                    if(!clickSnd.playing()){
                                        clickSnd.play();
                                    }
                                    initialize(taskName);
                                }else{
                                    if(!errorSnd.playing()){
                                        errorSnd.play();
                                    }
                                }
                                initialize(taskName);
                                initialize(deadline);
                                initialize(category);
                            } else {
                                //didn't click on any interactable object on newTaskBox
                                hasSelectedNewTask = false;
                                hasSelectedDeadline = false;
                                hasSelectedCategory = false;
                            }
                        }
                    }
                } else {
                    //if newTaskBox is open but user clicked outside the newTaskBox, the TaskBox closes
                    isAddingNewTask = false;
                    initialize(taskName);
                    initialize(deadline);
                    initialize(category);
                }
            } else if (isProfileSettingsSelected) {
                if(xpos >= 53 && xpos <= 219 && ypos <= 464 && ypos >= 342){
                    //TODO edit profile/sign out
                } else {
                    isProfileSettingsSelected = false;
                }
            } else if (isAddingNewCategory){
                if(!isCatAlreadyExists){
                    if(!(xpos >= 21 && xpos <= 21+newCategoryBox.getWidth() && ypos <= 540 - newCatBoxYPos && ypos >= 540 - (newCatBoxYPos + newCategoryBox.getHeight()))) {
                        isAddingNewCategory = false;
                        initialize(newCategoryName);
                    } else {
                        if (xpos >= newCategoryName.getX() && xpos <= newCategoryName.getX() + newCategoryName.getWidth() && ypos <= 540-newCategoryName.getY() && ypos >= 540 - newCategoryName.getY() - newCategoryName.getHeight()) {
                            hasSelectedCategoryName = true;
                        } else if (xpos >= 180 && xpos <= 215 && ypos <= 540 - (newCatBoxYPos + 30) && ypos >= 540 -(newCatBoxYPos + 44)){
                            if(categories.contains(newCategoryName.getText())) {
                                isCatAlreadyExists = true;
                            }
                            else {
                                isAddingNewCategory = false;
                                categories.addElement(newCategoryName.getText());
                                TeazyDBMnpln.addCategoryDB(CurrentUser.getUsername(),newCategoryName.getText());
                                if(currentView == ALL_TASKS){
                                    updateTaskVector = 0;
                                }else if(currentView == TODAY_VIEW){
                                    updateTaskVector = 1;
                                }else if(currentView == TODAY_VIEW){
                                    updateTaskVector = 2;
                                }
                            }
                            initialize(newCategoryName);
                        } else {
                            hasSelectedCategoryName = false;
                        }
                    }
                } else {
                    isCatAlreadyExists = false;
                }
            } else {
                if(xpos >= 200 && xpos<= 215 && ypos >= 425 && ypos <= 447){
                    if(!clickSnd.playing()){
                        clickSnd.play();
                    }
                    isProfileSettingsSelected = true;
                }
                if (xpos >= 0 && xpos <= LEFTPANEL_WIDTH) {
                    if (ypos <= (540 - 145) - SELECTOR_HEIGHT * ALL_TASKS && ypos >= (540 - 145) - SELECTOR_HEIGHT * (ALL_TASKS + 1)) {
                        //All
                        updateTaskVector = 1;
                        currentView = ALL_TASKS;
                        topYPos = 0;
                    }

                    if (ypos <= (540 - 145) - SELECTOR_HEIGHT * TODAY_VIEW && ypos >= (540 - 145) - SELECTOR_HEIGHT * (TODAY_VIEW + 1)) {
                        currentView = TODAY_VIEW;
                        updateTaskVector = 2;
                        topYPos = 0;
                    }

                    if (ypos <= (540 - 145) - SELECTOR_HEIGHT * WEEKLY_VIEW && ypos >= (540 - 145) - SELECTOR_HEIGHT * (WEEKLY_VIEW + 1)) {
                        currentView = WEEKLY_VIEW;
                        updateTaskVector = 3;
                        topYPos = 0;
                    }

                    if(ypos <= 540 - 145 - SELECTOR_HEIGHT * 4) {
                        int i = 0;
                        for (; i < categories.size(); i++) {
                            if (ypos < ((540 - 145) - SELECTOR_HEIGHT * 4) - topYPos2 - SELECTOR_HEIGHT * (i) && ypos > ((540 - 145) - SELECTOR_HEIGHT * 4) - topYPos2 - SELECTOR_HEIGHT * (i + 1)) {
                                System.out.println(i);
                                currentCategory = i;
                                updateTaskVector = 4;
                                currentView = CATEGORY_VIEW;
                            }
                        }
                        if (ypos < ((540 - 145) - SELECTOR_HEIGHT * 4) - topYPos2 - SELECTOR_HEIGHT * (i) && ypos > ((540 - 145) - SELECTOR_HEIGHT * 4) - topYPos2 - SELECTOR_HEIGHT * (i + 1)) {
                            System.out.println(i);
                            currentView = CATEGORY_VIEW;
                            isAddingNewCategory = true;
                            newCatBoxYPos = (145 + SELECTOR_HEIGHT * 4) + topYPos2 + SELECTOR_HEIGHT * (i);
                            if(!clickSnd.playing()){
                                clickSnd.play();
                            }
                        }
                    }
                }

                if((xpos>735 && xpos<781) && (ypos>28 && ypos<77) ) {//forFAB
                    isAddingNewTask = true; //mugawas ang new task window
                    if(!clickSnd.playing()){
                        clickSnd.play();
                    }
                    if(!clickSnd.playing()){
                        clickSnd.play();
                    }
                }
                for(int i = 0, j = 1; i < taskVector.size(); i++, j++) {
                    if((xpos >= 90*3+12 && xpos <= 90*3+12+boxNotDone.getWidth() && ypos <= 540 - (topYPos + 10 + 11 + (40 + 10) * j) && ypos >= 540 - (topYPos + 10 + 11 + (40 + 10) * j) - boxNotDone.getHeight())){
                        String tempUserID = taskVector.get(i).getUserID();
                        String tempTaskID = taskVector.get(i).getTaskID();
                        TeazyDBMnpln.deleteTask(tempUserID,tempTaskID);
                        taskVector.remove(i);
                    }
                }
            }
        }

        int temp;
        if(!isAddingNewTask && !isProfileSettingsSelected && !isAddingNewCategory) {
            temp = Mouse.getDWheel()/8;
            if(xpos > leftpanel2.getWidth()) {
                if (temp > 0 && topYPos < 0 || temp < 0 && topYPos > -((40 + 10) * taskVector.size() - 540))
                    topYPos = topYPos + temp;
            } else {
                if (temp > 0 && topYPos2 < 0 || temp < 0 && topYPos2 > -(SELECTOR_HEIGHT * (categories.size()-3)-80))
                    topYPos2 = topYPos2 + temp;
            }
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