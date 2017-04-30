package menezes.paulo.bomdebola.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Paulo Menezes on 23/07/2015.
 */
public class DatesUtil {
    public static int elapsed(Calendar before, Calendar after, int field) {
        Calendar clone = (Calendar) before.clone(); // Otherwise changes are been reflected.
        int elapsed = -1;
        while (!clone.after(after)) {
            clone.add(field, 1);
            elapsed++;
        }
        return elapsed;
    }

    public static String getPeriod(Date date1, Date date2) {
        Calendar start = Calendar.getInstance();
        start.setTime(date1);
        Calendar end = Calendar.getInstance();
        end.setTime(date2);

        Integer[] elapsed = new Integer[6];
        Calendar clone = (Calendar) start.clone(); // Otherwise changes are been reflected.
        elapsed[0] = elapsed(clone, end, Calendar.YEAR);
        clone.add(Calendar.YEAR, elapsed[0]);
        elapsed[1] = elapsed(clone, end, Calendar.MONTH);
        clone.add(Calendar.MONTH, elapsed[1]);
        elapsed[2] = elapsed(clone, end, Calendar.DATE);
        clone.add(Calendar.DATE, elapsed[2]);
        elapsed[3] = (int) (end.getTimeInMillis() - clone.getTimeInMillis()) / 3600000;
        clone.add(Calendar.HOUR, elapsed[3]);
        elapsed[4] = (int) (end.getTimeInMillis() - clone.getTimeInMillis()) / 60000;
        clone.add(Calendar.MINUTE, elapsed[4]);
        elapsed[5] = (int) (end.getTimeInMillis() - clone.getTimeInMillis()) / 1000;

        String[] labels = { "anos", "meses", "dias", "horas", "minutos", "segundos" };
        String[] label = { "ano", "mês", "dia", "hora", "minuto", "segundo" };

        for (int i = 0; i < elapsed.length; i++) {
            if (elapsed[i] > 0) {
                if (elapsed[i] == 1) {
                    return "Há " + elapsed[i] + " " + label[i];
                } else {
                    return "Há " + elapsed[i] + " " + labels[i];
                }
            }
        }

        return "Agora";
    }
}
