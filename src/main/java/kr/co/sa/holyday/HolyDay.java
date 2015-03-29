package kr.co.sa.holyday;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by oks on 15. 3. 29..
 */
public class HolyDay {
    private String title;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar start = Calendar.getInstance();
            start.setTime(format.parse(date));

            this.date = format.format(start.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Date getConvertedDate() {
        return getCalendarDate().getTime();
    }

    public Calendar getCalendarDate() {
        String[] split = date.split("-");

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.valueOf(split[0]));
        c.set(Calendar.MONTH, Integer.valueOf(split[1]) - 1);
        c.set(Calendar.DATE, Integer.valueOf(split[2]));

        return c;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", title, date);
    }
}
