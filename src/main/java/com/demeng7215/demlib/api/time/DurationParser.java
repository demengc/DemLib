package com.demeng7215.demlib.api.time;

import com.demeng7215.demlib.utils.TimeScanner;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

public class DurationParser {

	@Getter
	private String ogTime;
	@Getter
	private long millis;

	public DurationParser(String time) {
		this.ogTime = time;
		reparse(time);
	}

	private void reparse(String time) {
		millis = 0;

		TimeScanner scanner = new TimeScanner(time
				.replace(" ", "")
				.replace("and", "")
				.replace(",", "")
				.toLowerCase());

		long next;
		while (scanner.hasNext()) {
			next = scanner.nextLong();
			switch (scanner.nextString()) {
				case "ms":
				case "millis":
				case "milliseconds":
					millis += next;
					break;
				case "s":
				case "sec":
				case "secs":
				case "second":
				case "seconds":
					millis += TimeUnit.SECONDS.toMillis(next);
					break;
				case "m":
				case "min":
				case "mins":
				case "minute":
				case "minutes":
					millis += TimeUnit.MINUTES.toMillis(next);
					break;
				case "h":
				case "hr":
				case "hrs":
				case "hour":
				case "hours":
					millis += TimeUnit.HOURS.toMillis(next);
					break;
				case "d":
				case "dy":
				case "dys":
				case "day":
				case "days":
					millis += TimeUnit.DAYS.toMillis(next);
					break;
				case "w":
				case "week":
				case "weeks":
					millis += TimeUnit.DAYS.toMillis(next * 7);
					break;
				case "mo":
				case "mon":
				case "mnth":
				case "month":
				case "months":
					millis += TimeUnit.DAYS.toMillis(next * 30);
					break;
				case "y":
				case "yr":
				case "yrs":
				case "year":
				case "years":
					millis += TimeUnit.DAYS.toMillis(next * 365);
					break;
				default:
					throw new IllegalArgumentException();
			}
		}
	}

	public long getSeconds() {
		return TimeUnit.MILLISECONDS.toSeconds(millis);
	}

	public long getMinutes() {
		return TimeUnit.MILLISECONDS.toMinutes(millis);
	}

	public long getHours() {
		return TimeUnit.MILLISECONDS.toHours(millis);
	}

	public long getDays() {
		return TimeUnit.MILLISECONDS.toDays(millis);
	}

	public long getWeeks() {
		return getDays() / 7;
	}

	public long getMonths() {
		return getDays() / 30;
	}

	public long getYears() {
		return getDays() / 365;
	}
}