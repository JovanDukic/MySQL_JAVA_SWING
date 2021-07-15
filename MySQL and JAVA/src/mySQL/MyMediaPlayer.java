package mySQL;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MyMediaPlayer {

	public LinkedHashMap<Integer, MediaPlayer> songList = new LinkedHashMap<Integer, MediaPlayer>();

	public MyMediaPlayer() {

		Media media = null;
		Media media1 = null;
		
		try {
			media = new Media(getClass().getResource("/music/song1.mp3").toURI().toString());
			media1 = new Media(getClass().getResource("/music/song2.mp3").toURI().toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		

		MediaPlayer mediaPlayer = new MediaPlayer(media);
		MediaPlayer mediaPlayer1 = new MediaPlayer(media1);

		songList.put(0, mediaPlayer);
		songList.put(1, mediaPlayer1);

	}

	public int getSongListSize() {
		return songList.size();
	}

	public LinkedHashMap<Integer, MediaPlayer> getSongList() {
		return songList;
	}

}
