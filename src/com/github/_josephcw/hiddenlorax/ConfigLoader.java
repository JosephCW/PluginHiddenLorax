package com.github._josephcw.hiddenlorax;

public class ConfigLoader {
	
	HiddenLoraxMain plugin;
	boolean isEnabled;
	long delayTimer;
	
	public ConfigLoader(HiddenLoraxMain hiddenLoraxMain) {
		this.plugin = hiddenLoraxMain;
		loadConfig();
	}

	public void loadConfig() {
		setEnabled(Boolean.valueOf(this.plugin.getConfig().getString("Enabled")));
		setDelayTimer(20L * Long.valueOf(this.plugin.getConfig().getString("Loop Delay")));
	}

	private void setDelayTimer(long l) {
		plugin.getConfig().set("Loop Delay", Long.valueOf(l) / 20);
		delayTimer = l;
		saveConfig();
	}
	
	public long getDelayTimer() {
		return delayTimer;
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
