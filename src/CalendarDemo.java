import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;


/*
*
 * Created by Marie Curie on 10/04/2017.
 */

public class CalendarDemo {

    public static String[] weeklyCalendar(){
        String[] week = new String[7];
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        week[0] = sdf.format(cal.getTime());
        System.out.println(sdf.format(cal.getTime()));
        for (int i = 1; i<7; i++){
            cal.add(Calendar.DATE,1);
            week[i] = sdf.format(cal.getTime());
            System.out.println(sdf.format(cal.getTime()));
        }
        return week;
    }
    public static String currentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy");
        String currentdate = sdf.format(new Date());
        System.out.println(currentdate);
        return currentdate;
    }
}
