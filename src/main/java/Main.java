import kr.co.sa.GoogleCalendar;
import kr.co.sa.holyday.HolyDay;
import kr.co.sa.holyday.HolyDays;

import java.util.List;

/**
 * Created by oks on 15. 3. 28..
 */
public class Main {
    public static void main(String[] args) throws Exception {
        GoogleCalendar gc = new GoogleCalendar();
        HolyDays holyDays = gc.getHolyDays("https://www.google.com/calendar/feeds/blffot637do35g8hc1hf9a046s%40group.calendar.google.com/public/basic");

        List<HolyDay> holyDayList = holyDays.getHolyDaysByYear(2015);

        for (HolyDay holyDay : holyDayList) {
            System.out.println(holyDay);
        }
    }
}
