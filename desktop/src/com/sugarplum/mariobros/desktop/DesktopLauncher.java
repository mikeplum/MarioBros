package com.sugarplum.mariobros.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sugarplum.mariobros.MarioBros;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		config.title = "MarioBros";
		config.width = 1200;
		config.height = 624;

		new LwjglApplication(new MarioBros(), config);
	}
}
