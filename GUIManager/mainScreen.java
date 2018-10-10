package GUIManager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import MovieManager.MovieList;

public class mainScreen {

	public mainScreen(){
		
		
		JFrame frame = new JFrame("script analyzer");
		frame.setPreferredSize(new Dimension(200, 70));
		frame.setLocation(500,  400);
		Container contentPane = frame.getContentPane();
		JTextField textUrl = new JTextField();
		JButton button = new JButton("확인");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//http://www.imsdb.com/scripts/La-La-Land.html
				
				String title = new String();
				String scriptUrl = new String(textUrl.getText());
			
				String[] arr = scriptUrl.split("/");
				title=arr[arr.length-1];
				title=title.replaceAll(".html", "");
				System.out.println(title);
				try {
					MovieList.getInstance().addMovie(title, scriptUrl);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		JLabel label = new JLabel("Input script url");
		contentPane.add(textUrl, BorderLayout.CENTER);
		contentPane.add(button, BorderLayout.EAST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
