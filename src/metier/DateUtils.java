package metier;

import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateUtils {

	public static Date LocalDateToDate(LocalDate local) {
		return Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	public static LocalDate DateToLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}	
	
}
