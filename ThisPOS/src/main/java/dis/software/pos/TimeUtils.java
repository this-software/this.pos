/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos;

import java.util.Arrays;
import java.util.List;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 * @author Milton Cavazos
 */
public class TimeUtils
{
    
    public static final List<Long> millis = Arrays.asList
    (
        DAYS.toMillis(365),
        DAYS.toMillis(30),
        DAYS.toMillis(7),
        DAYS.toMillis(1),
        HOURS.toMillis(1),
        MINUTES.toMillis(1),
        SECONDS.toMillis(1)
    );
    
    public static final List<String> times = Arrays.asList(
        "año", "mes", "semana", "día", "hora", "minuto", "segundo"
    );
    
    /**
     * Método para obtener el tiempo relativo transcurrido de un fecha determinada
     *
     * NOTE:
     *  if (duration > WEEK_IN_MILLIS) getRelativeTime muestra la fecha.
     *
     * ALT:
     *  return getRelativeTime(date, now, SECOND_IN_MILLIS, FORMAT_ABBREV_RELATIVE);
     *
     * @param date String.valueOf(TimeUtils.getRelativeTime(1000L * Date/Time in Millis)
     * @return Tiempo relativo
     */
    public static CharSequence getRelativeTime(final long date)
    {
        return toDuration(Math.abs(System.currentTimeMillis() - date));
    }
    
    private static String toDuration(long duration)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < times.size(); i++)
        {
            Long current = millis.get(i);
            long time = duration / current;
            if (time > 0)
            {
                sb.append("hace ")
                  .append(time)
                  .append(" ")
                  .append(times.get(i))
                  .append(time > 1 ? (i == 1 ? "es" : "s") : "");
                break;
            }
        }
        return sb.toString().isEmpty() ? "hace unos momentos" : sb.toString();
    }
    
}
