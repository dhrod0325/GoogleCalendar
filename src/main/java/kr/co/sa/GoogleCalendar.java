package kr.co.sa;

import kr.co.sa.holyday.HolyDay;
import kr.co.sa.holyday.HolyDays;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by oks on 15. 3. 28..
 */
public class GoogleCalendar {
    public HolyDays getHolyDays(String url) throws Exception {
        URL u = new URL(url);
        URLConnection conn = u.openConnection();

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dom = db.parse(conn.getInputStream());

        Element element = dom.getDocumentElement();

        NodeList nodeList = element.getElementsByTagName("entry");

        HolyDays holyDays = new HolyDays();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node entry = nodeList.item(i);
            NodeList items = entry.getChildNodes();

            HolyDay holyDay = new HolyDay();

            for (int j = 0; j < items.getLength(); j++) {
                Node node = items.item(j);

                if (node == null) {
                    continue;
                }

                String name = node.getNodeName();
                String value = node.getTextContent();

                if ("title".equals(name)) {
                    value = value.replaceAll("\\(.*", "");
                    value = value.replaceAll("\\s", "");
                    holyDay.setTitle(value);
                } else if ("content".equals(name)) {
                    String date = value.replaceAll("[^\\d.]", " ");
                    String[] split_date = date.split("\\.");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    switch (split_date.length) {
                        case 1:
                            String[] days = split_date[0].trim().split(" ");

                            int y = Integer.valueOf(days[0].trim());
                            String m = days[1].trim();
                            String d = days[2].trim();

                            holyDay.setDate(String.format("%d-%s-%s", y, m, d));

                            for (int k = y - 2; k < y + 3; k++) {
                                HolyDay holyDay2 = new HolyDay();
                                holyDay2.setTitle(holyDay.getTitle());
                                holyDay2.setDate(String.format("%d-%s-%s", k, m, d));

                                holyDays.addHolyDay(holyDay2);
                            }

                            break;
                        case 4:
                            holyDay.setDate(String.format("%s-%s-%s", split_date[0].trim(), split_date[1].trim(), split_date[2].trim()));
                            break;
                        case 7:
                            String startDate = String.format("%s-%s-%s", split_date[0].trim(), split_date[1].trim(), split_date[2].trim());
                            String endDate = String.format("%s-%s-%s", split_date[3].trim(), split_date[4].trim(), split_date[5].trim());

                            Calendar start = Calendar.getInstance();
                            start.setTime(format.parse(startDate));

                            holyDay.setDate(format.format(start.getTime()));

                            Calendar end = Calendar.getInstance();
                            end.setTime(format.parse(endDate));

                            int diffInDays = (int) ((end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60 * 24));

                            for (int k = 1; k < diffInDays + 1; k++) {
                                Calendar term = Calendar.getInstance();
                                term.setTime(start.getTime());

                                term.add(Calendar.DATE, k);

                                HolyDay _holyDay = new HolyDay();
                                _holyDay.setTitle(holyDay.getTitle());
                                _holyDay.setDate(format.format(term.getTime()));

                                holyDays.addHolyDay(_holyDay);
                            }

                            break;
                    }
                }
            }

            if (!holyDays.contains(holyDay.getDate())) {
                holyDays.addHolyDay(holyDay);
            }
        }

        return holyDays;
    }

}