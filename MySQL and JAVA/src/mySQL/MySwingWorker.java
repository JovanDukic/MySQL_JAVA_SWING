package mySQL;

import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class MySwingWorker extends SwingWorker<Void, Integer> {

	private int songNumber;
	private MyMediaPlayer mediaPlayer;
	private JProgressBar progressBar;

	private int nextSongNumber;

	public MySwingWorker(int songNumber, MyMediaPlayer mediaPlayer, JProgressBar progressBar) {
		this.songNumber = songNumber;
		this.mediaPlayer = mediaPlayer;
		this.progressBar = progressBar;
	}

	@Override
	protected Void doInBackground() throws Exception {

		int i = 0;

		progressBar.setMinimum(0);
		progressBar
				.setMaximum((int) Math.round(mediaPlayer.getSongList().get(songNumber).getTotalDuration().toSeconds()));

		System.out.println(mediaPlayer.getSongList().get(songNumber).getTotalDuration().toMinutes());

		mediaPlayer.getSongList().get(songNumber).play();

		while (i < (int) Math.round(mediaPlayer.songList.get(songNumber).getTotalDuration().toSeconds())
				&& !this.isCancelled()) {

			// System.out.println(i);

			publish(i);

			i = (int) Math.round(mediaPlayer.songList.get(songNumber).getCurrentTime().toSeconds());

		}

		publish(0);

		songNumber = nextSongNumber;

		return null;

	}

	@Override
	protected void process(List<Integer> seconds) {

		int time = seconds.get(seconds.size() - 1);

		progressBar.setValue(time);

	}

	@Override
	protected void done() {

		System.out.println("END");

	}

	public void setNextSongNumber(int nextSongNumber) {
		this.nextSongNumber = nextSongNumber;
	}

	public boolean pauseMediaPlayer() {
		mediaPlayer.getSongList().get(songNumber).pause();
		return true;
	}

	public boolean resumeMediaPlayer() {
		mediaPlayer.getSongList().get(songNumber).play();
		return false;
	}

	public boolean stopTrack() {
		mediaPlayer.getSongList().get(songNumber).stop();
		this.cancel(true);
		return false;
	}

	public void shuffle(int nextSongNumber) {
		this.nextSongNumber = nextSongNumber;
	}

}
