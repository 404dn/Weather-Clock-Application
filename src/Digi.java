import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Calendar;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Digi {

	 JFrame frame;
     JLabel  timeLbl;
	 JTextField tf;
	 JButton btnStarttimer;
	 Timer timer;
	 JLabel timerLbl;
	
	 
	 static JLabel DescLbl = new JLabel("0");
	 private static JLabel DescLbl_1;
	 static JLabel minLbl = new JLabel("0");
	 static JLabel tempLbl = new JLabel("0");
	 static JLabel maxLbl = new JLabel("0");
	 static JLabel feelslikeLbl = new JLabel("0"); 
	 
	 static int min = 0;
	 static int max = 0;
	 static int temp = 0;
	 static int fl = 0;
	 static String descriptionString = " ";
	 
	 private static HttpURLConnection connection;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Digi window = new Digi();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Digi() {
		initialize();
		javax.swing.Timer t = new javax.swing.Timer(1000, new ClockListener());
		t.start(); 
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://api.openweathermap.org/data/2.5/weather?id=292223&appid=708f719ab2d2aff1904d472a39260951&units=metric")).build();
		
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenApply(Digi::parse).join();
	}
	public class ulr implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://api.openweathermap.org/data/2.5/weather?id=292223&appid=708f719ab2d2aff1904d472a39260951&units=metric")).build();
			
			client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenApply(Digi::parse).join();
			
		}
		
	}
	
	public static String parse(String responseBody) {
		
		JSONObject first = new JSONObject(responseBody);
		JSONObject second = first.getJSONObject("main");
		JSONArray weather = first.getJSONArray("weather");
		JSONObject weatherobj = weather.getJSONObject(0);
		
		temp = second.getInt("temp");
		min = second.getInt("temp_min");
		max = second.getInt("temp_max");
		fl = second.getInt("feels_like");
		descriptionString = weatherobj.getString("description");
		
		tempLbl.setText(""+temp+"c");
		minLbl.setText(""+min+"c");
		maxLbl.setText(""+max+"c");
		feelslikeLbl.setText(""+fl+"c");
		DescLbl_1.setText(descriptionString);
		
		return null;
	}
	
	
	class ClockListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Calendar now = Calendar.getInstance();
			int h = now.get(Calendar.HOUR_OF_DAY);
			int m = now.get(Calendar.MINUTE);
			int s = now.get(Calendar.SECOND);
			timeLbl.setText("" + h + ":" + m + ":" + s);
			
		}
		
	}
	
	
	
	
    public class countDown implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			int count = (int)(Double.parseDouble(tf.getText()));
			timerLbl.setText("Timer Left: " + count);
			TimeClass tc = new TimeClass(count);
			timer = new Timer(1000,tc);
			timer.start();
			
		}
	}
    
    
    
    public class TimeClass implements ActionListener {
   	 int counter;
   	 
   	 public TimeClass (int counter) {
   		 this.counter = counter;
   		 
   	 }
   	 public void actionPerformed(ActionEvent tc) {
   		 counter--;
   		 
   		 if(counter>=1) {
   			 timerLbl.setText("Time left: " + counter);
   			 
   			 
   		 }
   		 else {
   			 timer.stop();
   			 timerLbl.setText("Done!");
   			 Toolkit.getDefaultToolkit().beep();
   		 }
   	 }
    }
    
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(255, 255, 255));
		tabbedPane.setBounds(6, 6, 438, 266);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(Color.GRAY);
		tabbedPane.addTab("Clock", null, panel, null);
		tabbedPane.setEnabledAt(0, true);
		tabbedPane.setBackgroundAt(0, Color.GRAY);
		panel.setLayout(null);
		
		timeLbl = new JLabel("");
		timeLbl.setBounds(6, 6, 405, 208);
		panel.add(timeLbl);
		timeLbl.setEnabled(false);
		timeLbl.setForeground(Color.BLACK);
		timeLbl.setFont(new Font("Phosphate", Font.PLAIN, 90));
		timeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		tabbedPane.addTab("Timer", null, panel_1, null);
		tabbedPane.setBackgroundAt(1, Color.GRAY);
		panel_1.setLayout(null);
		
		tf = new JTextField();
		tf.setBounds(200, 111, 211, 35);
		panel_1.add(tf);
		tf.setColumns(10);
		btnStarttimer = new JButton("Sart Timer");
		btnStarttimer.setBounds(6, 158, 405, 56);
		panel_1.add(btnStarttimer);
		btnStarttimer.addActionListener(new countDown());
		
		timerLbl = new JLabel("Waiting....");
		timerLbl.setHorizontalAlignment(SwingConstants.CENTER);
		timerLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 46));
		timerLbl.setBounds(6, 6, 405, 93);
		panel_1.add(timerLbl);
		
		JLabel lblNewLabel = new JLabel("Enter Seconds");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel.setBounds(6, 111, 177, 35);
		panel_1.add(lblNewLabel);
		
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Weather", null, panel_2, null);
		panel_2.setBackground(Color.GRAY);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Temperature:");
		lblNewLabel_1.setBounds(6, 6, 223, 39);
		lblNewLabel_1.setFont(new Font("Phosphate", Font.PLAIN, 30));
		panel_2.add(lblNewLabel_1);
		
	    
		tempLbl.setHorizontalAlignment(SwingConstants.CENTER);
		tempLbl.setForeground(Color.WHITE);
		tempLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		tempLbl.setBounds(283, -2, 98, 47);
		panel_2.add(tempLbl);
		
		DescLbl = new JLabel("Description:");
		DescLbl.setFont(new Font("Phosphate", Font.PLAIN, 15));
		DescLbl.setForeground(new Color(0, 0, 0));
		DescLbl.setHorizontalAlignment(SwingConstants.LEFT);
		DescLbl.setBounds(6, 100, 98, 35);
		panel_2.add(DescLbl);
		
		minLbl = new JLabel("0");
		minLbl.setForeground(Color.WHITE);
		minLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		minLbl.setBounds(51, 157, 61, 35);
		panel_2.add(minLbl);
		
		maxLbl = new JLabel("0");
		maxLbl.setForeground(Color.WHITE);
		maxLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		maxLbl.setBounds(306, 157, 75, 35);
		panel_2.add(maxLbl);
		
		JLabel lblNewLabel_2 = new JLabel("Min:");
		lblNewLabel_2.setFont(new Font("Phosphate", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(257, 168, 52, 16);
		panel_2.add(lblNewLabel_2);
		
		JLabel label = new JLabel("Min:");
		label.setFont(new Font("Phosphate", Font.PLAIN, 20));
		label.setBounds(6, 168, 52, 16);
		panel_2.add(label);
		
		JLabel lblNewLabel_3 = new JLabel("Feels like:");
		lblNewLabel_3.setFont(new Font("Phosphate", Font.PLAIN, 21));
		lblNewLabel_3.setBounds(6, 57, 98, 47);
		panel_2.add(lblNewLabel_3);
		
		feelslikeLbl = new JLabel(".");
		feelslikeLbl.setForeground(Color.WHITE);
		feelslikeLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		feelslikeLbl.setBounds(105, 49, 67, 51);
		panel_2.add(feelslikeLbl);
		
		DescLbl_1 = new JLabel(".");
		DescLbl_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		DescLbl_1.setForeground(Color.WHITE);
		DescLbl_1.setBounds(105, 95, 150, 39);
		panel_2.add(DescLbl_1);
		
		JButton refreshbtn = new JButton("Refresh");
		refreshbtn.setBounds(328, 185, 83, 29);
		panel_2.add(refreshbtn);
		refreshbtn.addActionListener(new ulr() );
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
