package edu.cornell.ncrn.ced2ar.ddigen;

import java.io.File;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.springframework.util.StringUtils;

/**
 *
 * @author NCRN Project Team Apache Cli util class. Validates the options f, s
 *         and l for the DDI generation process
 * 
 * @author Cornell University, Copyright 2012-2015
 * @author Ben Perry
 *
 * @author Cornell Institute for Social and Economic Research
 * @author Cornell Labor Dynamics Institute
 * @author NCRN Project Team
 */
public class Util {

	private Options _options = new Options();
	CommandLineParser _parser = new BasicParser();

	public Util() {
		_options.addOption("f", true,
				"(required) Location of Stata or SPSS file");
		_options.addOption("s", true,
				"(optional) [true|false] Generate summary statistics");
		_options.addOption("l", true, "(optional) Observation limit");
		_options.addOption("format", true, "(optional) DDI format (3.3Fragment or 2.5)");
		_options.addOption("config", true, "(optional) Config file path");
		_options.addOption("exclude", true, "(optional) Exclude variable statistics file path");
	}

	public CommandLineParser getParser() {
		return _parser;
	}

	public Options getOptions() {
		return _options;
	}

	/**
	 * Prints required options
	 */
	public void help() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("Options are as follows...", _options);
		System.exit(1);
	}

	/**
	 * Tests if file exists, exists the program and prints error if it doesn't
	 */
	public static void fileCheck(String path) {
		File od = new File(path);
		if (!od.exists()) {
			System.out.println("The path '" + path + "' does not exist");
			System.exit(1);
		}
	}

	/**
	 * Format is set to 3.3 by default. User can choose 2.5 format
	 *
	 * @param format
	 * @param format
	 */
	public static String formatCheck(String format) {

		if (format == null || format.isEmpty()) {
			return "3.3Fragment";
		}

		if (format.equalsIgnoreCase("2.5") || format.equalsIgnoreCase("3.3Fragment")) {
			return format;
		} else {
			System.out.println("Only following formats are supported: 2.5 and 3.3Fragment");
			System.exit(1);
			return "3.3Fragment";
		}
	}

	/**
	 * valid values f,F or False t,T, or True
	 * 
	 * Summary Statistics are generated by default. Checks to see if user has
	 * entered other options.
	 * 
	 * @param summaryStatistics
	 */
	public static boolean runSumStatsCheck(String summaryStatistics) {
		if (StringUtils.isEmpty(summaryStatistics)) {
			return true;
		} else if (summaryStatistics.startsWith("f")
				|| summaryStatistics.startsWith("F")
				|| summaryStatistics.equalsIgnoreCase("false")) {
			return false;
		} else if (summaryStatistics.startsWith("t")
				|| summaryStatistics.startsWith("T")
				|| summaryStatistics.equalsIgnoreCase("true")) {
			return true;
		} else {
			System.out.println("Argument s must be a boolean value");
			System.exit(1);
			return false;
		}
	}

	/**
	 * All observations are processed by default. User can choose any other
	 * positive value
	 * 
	 * @param observationLimit
	 */
	public static long observationLimitCheck(String observationLimit) {
		
		if (StringUtils.isEmpty(observationLimit)) {
			return -1l;
		}
	
		try {
			Long observations = Long.parseLong(observationLimit);
			if (observations <= 0)
				return -1l;
			else
				return observations;
		} catch (NumberFormatException e) {
			System.out.println("Argument l must be a postive integer");
			System.exit(1);
			return -1l;
		}
	}
}