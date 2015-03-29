package kr.co.sa.holyday;

import java.util.*;

/**
 * Created by oks on 15. 3. 29..
 */
public class HolyDays {
    private List<HolyDay> holyDays = new ArrayList<HolyDay>();

    public void addHolyDay(HolyDay holyDay) {
        holyDays.add(holyDay);
    }

    public List<HolyDay> getHolyDays() {
        return holyDays;
    }

    public void setHolyDays(List<HolyDay> holyDays) {
        this.holyDays = holyDays;
    }

    public void sort() {
        Collections.sort(holyDays, new Comparator<HolyDay>() {
            @Override
            public int compare(HolyDay holyDay, HolyDay t1) {
                return holyDay.getCalendarDate().getTimeInMillis() > t1.getCalendarDate().getTimeInMillis() ? 1 : 0;
            }
        });
    }

    public List<HolyDay> getHolyDaysByYear(int year) {
        sort();

        List<HolyDay> results = new ArrayList<HolyDay>();

        for (HolyDay holyDay : holyDays) {
            int y = holyDay.getCalendarDate().get(Calendar.YEAR);

            if (y == year) {
                results.add(holyDay);
            }
        }

        return results;
    }

    public boolean contains(String date) {
        for (HolyDay holyDay : holyDays) {
            if (holyDay.getDate() != null && date.equals(holyDay.getDate())) {
                return true;
            }
        }

        return false;
    }
}
