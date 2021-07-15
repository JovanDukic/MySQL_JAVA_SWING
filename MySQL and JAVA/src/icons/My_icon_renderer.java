package icons;

import java.net.URL;

import javax.swing.ImageIcon;

public class My_icon_renderer {

	public static ImageIcon createIcon(String path) {

		URL url = System.class.getResource(path);

		if (url == null) {
			System.err.println("Unable to load the image " + path);
		} else {

			ImageIcon icon = new ImageIcon(url);

			return icon;

		}
		return null;
	}

}
