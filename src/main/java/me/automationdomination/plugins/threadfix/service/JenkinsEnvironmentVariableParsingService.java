package me.automationdomination.plugins.threadfix.service;

import hudson.EnvVars;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JenkinsEnvironmentVariableParsingService {
	
	private final Pattern environmentVariablePattern = Pattern.compile("\\$\\{.+?\\}");
	
	public String parseEnvironentVariables(final EnvVars envVars, final String value) {
		final Matcher matcher = environmentVariablePattern.matcher(value);
		
		String parsedValue = value;
		
		while (matcher.find()) {
			final String matchedValue = matcher.group();
			
			// TODO: can this be done more efficiently?
			final String environmentVariableKey = matchedValue.replaceAll("\\$\\{", "").replaceAll("\\}", "");
			
			final String environmentVariableValue = envVars.get(environmentVariableKey);
			
			// TODO: can this be done more efficiently?
			parsedValue = parsedValue.replaceAll("\\$\\{" + environmentVariableKey + "\\}", environmentVariableValue);
		}
		
		return parsedValue;
	}

}