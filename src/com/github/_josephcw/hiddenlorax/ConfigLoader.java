package com.github._josephcw.hiddenlorax;

public class ConfigLoader {
	
	HiddenLoraxMain plugin;
	boolean isEnabled;
	
	public ConfigLoader(HiddenLoraxMain hiddenLoraxMain) {
		this.plugin = hiddenLoraxMain;
		loadConfig();
	}

	public void loadConfig() {
		setEnabled(Boolean.valueOf(this.plugin.getConfig().getString("Enabled")));
	}

	public void saveConfig() {
		plugin.saveConfig();
	}

	private void setEnabled(Boolean enable) {
		plugin.getConfig().set("Enabled", Boolean.toString(enable));
		isEnabled = enable;
		saveConfig();
	}
	
	public boolean getEnabled() {
		return isEnabled;
	}
	
}
