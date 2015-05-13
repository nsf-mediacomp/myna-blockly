package config;

import edu.cmu.sphinx.util.props.ConfigurationManager;

public class Config {
	private static ConfigurationManager cm = null;
	
	public static ConfigurationManager getManager() {
		if (cm == null) {
			cm = new ConfigurationManager(Config.class.getResource("/config/myna.config.xml"));
		}
		return cm;
	}

}
