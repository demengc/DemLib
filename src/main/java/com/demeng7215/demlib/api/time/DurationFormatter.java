package com.demeng7215.demlib.api.time;

import lombok.Getter;

public class DurationFormatter {

	@Getter
	private final long totalSeconds;

	@Getter
	private final long years;
	@Getter
	private final long months;
	@Getter
	private final long weeks;
	@Getter
	private final long days;
	@Getter
	private final long hours;
	@Getter
	private final long minutes;
	@Getter
	private final long seconds;

	public DurationFormatter(long totalSeconds) {

		this.totalSeconds = totalSeconds;

		this.years = this.totalSeconds / 31536000;
		this.months = (this.totalSeconds % 31536000) / 2592000;
		this.weeks = ((this.totalSeconds % 31536000) % 2592000) / 604800;
		this.days = (((this.totalSeconds % 31536000) % 2592000) % 604800) / 86400;
		this.hours = ((((this.totalSeconds % 31536000) % 2592000) % 604800) % 86400) / 3600;
		this.minutes = (((((this.totalSeconds % 31536000) % 2592000) % 604800) % 86400) % 3600) / 60;
		this.seconds = (((((this.totalSeconds % 31536000) % 2592000) % 604800) % 86400) % 3600) % 60;
	}

	public String format() {

		StringBuilder builder = new StringBuilder();

		if (years != 0) {
			builder.append(years);
			if (years > 1) builder.append(" years ");
			else builder.append(" year ");
		}

		if (months != 0) {
			builder.append(months);
			if (months > 1) builder.append(" months ");
			else builder.append(" month ");
		}

		if (weeks != 0) {
			builder.append(weeks);
			if (weeks > 1) builder.append(" weeks ");
			else builder.append(" week ");
		}

		if (days != 0) {
			builder.append(days);
			if (days > 1) builder.append(" days ");
			else builder.append(" day ");
		}

		if (hours != 0) {
			builder.append(hours);
			if (hours > 1) builder.append(" hours ");
			else builder.append(" hour ");
		}

		if (minutes != 0) {
			builder.append(minutes);
			if (minutes > 1) builder.append(" minutes ");
			else builder.append(" minute ");
		}

		if (seconds != 0) {
			builder.append(seconds);
			if (seconds > 1) builder.append(" seconds ");
			else builder.append(" second ");
		}

		final String s = builder.toString();

		return s.substring(0, s.length() - 1);
	}
}
