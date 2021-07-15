package mySQL;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class MusicPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5519358592058774349L;

	private JButton start;
	private JButton stop;
	private JButton pause;
	private JButton next;
	private JButton previous;
	private JButton seek;
	private JCheckBox shuffle;

	private boolean isPlaying = false;

	private boolean isPaused = false;

	private MySwingWorker swingWorker;

	private MyMediaPlayer mediaPlayer;

	private JProgressBar progressBar;

	private Integer songNumber = 0;
	private int numberOfSongs = 0;

	public MusicPanel() {

		start = new JButton("Start");
		stop = new JButton("Stop");
		pause = new JButton("Pause");
		next = new JButton("Next song");
		previous = new JButton("previous song");
		seek = new JButton("Seek");
		shuffle = new JCheckBox("Shuffule");
		
		shuffle.setFont(new Font(Font.DIALOG, Font.BOLD, 15));

		progressBar = new JProgressBar();

		progressBar.setStringPainted(true);
		progressBar.setBorder(BorderFactory.createEtchedBorder());

		progressBar.setPreferredSize(new Dimension(300, 25));

		mediaPlayer = new MyMediaPlayer();

		numberOfSongs = mediaPlayer.getSongListSize();

		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!isPaused) {

					if (!isPlaying) {
						
						System.out.println(songNumber);

						swingWorker = new MySwingWorker(songNumber, mediaPlayer, progressBar);

						swingWorker.execute();

						isPlaying = true;

					}

				} else {

					isPaused = swingWorker.resumeMediaPlayer();

				}
			}
		});

		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					isPlaying = swingWorker.stopTrack();

				} catch (NullPointerException error) {
					System.out.println("Null!");
				}

			}
		});

		pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					isPaused = swingWorker.pauseMediaPlayer();

				} catch (NullPointerException error) {
					System.out.println("NULL");
				}

			}
		});

		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("Number of songs:" + numberOfSongs);

				if ((songNumber + 1) <= numberOfSongs) {

					mediaPlayer.getSongList().get(songNumber).stop();

					try {

						swingWorker.cancel(true);

					} catch (NullPointerException error) {
						System.out.println("Null pointer!");
					}

					songNumber++;

					swingWorker = new MySwingWorker(songNumber, mediaPlayer, progressBar);

					swingWorker.execute();

				}
			}
		});

		previous.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("Number of songs:" + numberOfSongs);

				if (!((songNumber - 1) == 0)) {

					mediaPlayer.getSongList().get(songNumber).stop();

					try {

						swingWorker.cancel(true);

					} catch (NullPointerException error) {
						System.out.println("Null pointer!");
					}

					songNumber--;

					swingWorker = new MySwingWorker(songNumber, mediaPlayer, progressBar);

					swingWorker.execute();

				}
			}
		});

		// seek.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// System.out.println("Sekunde:" +
		// mediaPlayer.getSongList().get(songNumber).getCurrentTime().toSeconds());
		//
		// mediaPlayer.getSongList().get(songNumber).seek(new Duration(15000)); // MOTA
		// DO new Duration(BROJ
		// // SEKUNDI) ali ne preskace po
		// // BROJ SEKUNDI !!!
		//
		// System.out.println("Sekunde:" +
		// mediaPlayer.getSongList().get(songNumber).getCurrentTime().toSeconds());
		// }
		// });

		shuffle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				boolean n = shuffle.isSelected();

				if (n) {

					Random random = new Random();

					int a = random.nextInt(mediaPlayer.getSongListSize());

					System.out.println(a);

					songNumber = a;

				}

			}
		});

		setLayout();

	}

	private void setLayout() {

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.ipady = 40;
		gc.insets = new Insets(10, 10, 10, 10);
		
		gc.gridwidth = 3;
		
		gc.gridy = 0;
		gc.gridx = 0;
		add(progressBar, gc);

		//////////////// +++++++++
		
		gc.gridwidth = 1;
		gc.weightx = 0.5;
		
		gc.gridy++;

		gc.gridx = 0;
		add(start, gc);

		gc.gridx = 1;
		add(pause, gc);

		gc.gridx = 2;
		add(stop, gc);

		//////////////// +++++++++

		gc.gridy++;

		gc.gridx = 0;
		add(next, gc);

		gc.gridx = 1;
		add(previous, gc);

		gc.gridx = 2;
		add(seek, gc);

		//////////////// +++++++++

		gc.gridy++;
		
		gc.fill = GridBagConstraints.NONE;
		gc.ipady = 0;
		gc.gridwidth = 3;
		gc.weightx = 0.0;
		gc.weighty = 0.5;

		gc.gridx = 2;
		gc.anchor = GridBagConstraints.LAST_LINE_END;
		add(shuffle, gc);

	}
}
