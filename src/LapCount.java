
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;



/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class LapCount extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* Auto-generated main method to display this JFrame
	*/
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LapCount inst = null;
				inst = new LapCount(); 
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	private LapCount() {
		super();
		initGUI();
		
		running = true;
		uz = new UhrzeitThread();
		db = new DerbyDB();

		refresh = true;
		runOnce = false;
		uz.start();
		db.start();
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setTitle("View Race Result");
			this.setPreferredSize(new java.awt.Dimension(450, 550));
			{
				jPanel2 = new JPanel();
				GridBagLayout jPanel2Layout = new GridBagLayout();
				jPanel2Layout.rowWeights = new double[] {0.1, 4, 0.1};
				jPanel2Layout.rowHeights = new int[] {7, 7, 7};
				jPanel2Layout.columnWeights = new double[] {0.1};
				jPanel2Layout.columnWidths = new int[] {7};
				jPanel2.setLayout(jPanel2Layout);
				getContentPane().add(jPanel2, new AnchorConstraint(14, 1001, 1000, 14, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				jPanel2.setPreferredSize(new java.awt.Dimension(428, 505));
				{
					uhrzeitAusgabe = new JLabel();
					jPanel2.add(uhrzeitAusgabe, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					uhrzeitAusgabe.setText("00:00:00");
					uhrzeitAusgabe.setFont(new Font(Font.DIALOG, Font.BOLD, uhrFontSize));
				}
				{
					jScrollPanelReadTable = new JScrollPane();
					jPanel2.add(jScrollPanelReadTable, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jScrollPanelReadTable.setPreferredSize(new java.awt.Dimension(400, 410));
					{

				        String[] columnNames = {"Place", "Stnr", "Lap", "Time"};

						dataTableModel = new DefaultTableModel(null, columnNames);
						dataTable = new JTable();
						dataTable.setModel(dataTableModel);
												
						jScrollPanelReadTable.setViewportView(dataTable);
						dataTable.setFillsViewportHeight(true);
						//dataTable.setPreferredSize(new java.awt.Dimension(400, 398));
					}
				}
				{
					jPanelButtons = new JPanel();
					GridBagLayout jPanelButtonsLayout = new GridBagLayout();
					jPanel2.add(jPanelButtons, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jPanelButtons.setPreferredSize(new java.awt.Dimension(400, 40));
					jPanelButtonsLayout.rowWeights = new double[] {0.1};
					jPanelButtonsLayout.rowHeights = new int[] {7};
					jPanelButtonsLayout.columnWeights = new double[] {0.1, 0.1, 0.1};
					jPanelButtonsLayout.columnWidths = new int[] {7, 7, 7};
					jPanelButtons.setLayout(jPanelButtonsLayout);
					{
						jLabelRefreshing = new JLabel();
						jPanelButtons.add(jLabelRefreshing, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabelRefreshing.setText("");
						jLabelRefreshing.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
						jLabelRefreshing.setHorizontalAlignment(SwingConstants.CENTER);				
						jLabelRefreshing.setPreferredSize(new java.awt.Dimension(77, 15));
					}
					{
						jToggleRefresh = new JToggleButton();
						jPanelButtons.add(jToggleRefresh, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jToggleRefresh.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
						jToggleRefresh.setText("automatic refresh");
						jToggleRefresh.setSelected(true);
						jToggleRefresh.setPreferredSize(new java.awt.Dimension(170, 22));
					}
					{
						jButtonSort = new JButton();
						jPanelButtons.add(jButtonSort, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jButtonSort.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
						jButtonSort.setText("sorting by race Result");
						jButtonSort.setPreferredSize(new java.awt.Dimension(170, 22));
					}
				}
			}
			
			jToggleRefresh.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	if(jToggleRefresh.isSelected()) {
	            		refresh = true;
	            		db = new DerbyDB();
	            		db.start();
	            	} else {
	            		refresh = false;
	            		db.interrupt();
	            	}
	            }
	        });
			
			
			jButtonSort.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	if(evt.getActionCommand().equals("sorting by race Result")) {
						jButtonSort.setText("sorting by last event");
	            	} else {
						jButtonSort.setText("sorting by race Result");
	            	}
	            	refreshOnce();
	            }
	        });
			
			
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {					
					thisWindowClosing(evt);
				}
			});

			this.addComponentListener(new ComponentAdapter() {
			    public void componentResized(ComponentEvent e) {
			    	height = e.getComponent().getHeight() - 150;
			    	jScrollPanelReadTable.setPreferredSize(new java.awt.Dimension(400, height));
			    }
			});

			
			pack();
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

	
	public void refreshOnce() {
		if(!db.isAlive()) {
			refresh = true;
			runOnce = true;
			db = new DerbyDB();
			db.start();
		}
	}
	
	private void thisWindowClosing(WindowEvent evt) {   
		running = false;
	}

	private JPanel jPanel2;
	private JLabel uhrzeitAusgabe;
    private JScrollPane jScrollPanelReadTable;
    private JLabel jLabelRefreshing;
	private JPanel jPanelButtons;
    private int uhrFontSize = 40;
    private FileWriter csvFile;
	private UhrzeitThread uz;
	private DerbyDB db;
	private boolean running;
	private boolean refresh;
	private boolean runOnce;
	private String startTime;
	private String laufZeit;
	private JTable dataTable;
    private DefaultTableModel dataTableModel;
    private int height;
    
	String outStringCSV = "";
	private JButton jButtonSort;
	private JToggleButton jToggleRefresh;
	String orderBy = "";

	class UhrzeitThread extends Thread{
		Calendar calender = Calendar.getInstance();
		CalculateTime calc = new CalculateTime();
		
		public void run(){
			startTime = ReadConfig.getConfig().getString("STARTTIME");
			while(running == true){
				try{Thread.sleep(1000);}catch(Exception e){ e.printStackTrace(); }
				String now = new SimpleDateFormat("HH:mm:ss").format(new Date());				
				laufZeit = calc.calcTime(startTime, now);
		        uhrzeitAusgabe.setText(laufZeit);
		    }
		}
	}

	public class DerbyDB extends Thread {
		private Connection conn = null;
		private ResultSet resultSet = null;
		private String[] splitString = null;
		private ArrayList<String> csvContent;
		CalculateTime calc = new CalculateTime();
		
		public void run() {
		
			while (running && refresh) {
				try {
					if(runOnce) { refresh = false; runOnce = false; }
					jLabelRefreshing.setText("refresh....");
					csvContent = getCsvContent();
						
					connectDB();
					
					Statement stmt = conn.createStatement();
					stmt.executeUpdate("delete from ZEIT");
					
					int i = 0;
					while (i < csvContent.size()) {
						splitString = csvContent.get(i).split(";");
						stmt.executeUpdate("insert into ZEIT (ZEIT, STARTNUMMER) values ('" + splitString[3] + "', " + splitString[2] + ")");
						i++;
					}
					
					if (jButtonSort.getText() == "sorting by race Result") {
						orderBy = "order by COUNT desc, ZEIT asc";
					} else {
						orderBy = "order by ZEIT desc";					
					}
					
					resultSet = stmt.executeQuery("SELECT count(STARTNUMMER) as COUNT, STARTNUMMER, max(ZEIT) as ZEIT " +
														"from ZEIT WHERE ZEIT > '" + startTime + "' group by STARTNUMMER " + orderBy);
					
					i = 1;
					outStringCSV = "";	
					outStringCSV = outStringCSV + "PL;NR;LAP;TIME\n";

			        dataTable.setVisible(false);
			        dataTableModel.setRowCount(0);

					while (resultSet.next()) {
						String st = resultSet.getString("STARTNUMMER");
						String rd = resultSet.getString("COUNT");
						String z  = resultSet.getString("ZEIT");
						z = calc.calcTime(startTime, z);
						
						if (jButtonSort.getText() == "sorting by last event") { i = 0; }
						String[] rowData = {Integer.toString(i), st, rd, z};
				        dataTableModel.addRow(rowData);
						
						outStringCSV = outStringCSV + i + ";" + st + ";" + rd + ";" + z + "\n";
						i++;
					}

					
			        dataTable.setVisible(true);

					try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }

					jLabelRefreshing.setText("");
								        
					openCsv();
					csvFile.write(outStringCSV);
					closeCsv();
					
					stmt.close();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				closeDB();
				int sleep = 5000;
			    if(running && refresh) {
					try{Thread.sleep(sleep);}catch(Exception e){ }			    	
			    }
			}
		}	

		private ArrayList<String> getCsvContent() throws IOException {
			FileReader fr = null;
			BufferedReader br;
			String csvFile = ReadConfig.getConfig().getString("FILENAME");
			
			if( csvFile.startsWith("http://")) {
			    URL url = new URL(csvFile);
			    System.out.println("Connecting to " + url.toString() + " ... ");
			    br = new BufferedReader(new InputStreamReader(url.openStream()));
			} else {
				fr = new FileReader(csvFile);
				br = new BufferedReader(fr);
			}
					
			String s;
			ArrayList<String> lines = new ArrayList<String>();

			while ((s = br.readLine()) != null) {
				if (s.length() > 1 ) {
					lines.add(s);
				}
			}

			if( !csvFile.startsWith("http://")) {
				fr.close();
			}
			return lines;
		}

		private void connectDB() {
			try {
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
				conn = DriverManager.getConnection("jdbc:derby:memory:Result-DB;create=true");
				
				DatabaseMetaData dmd = conn.getMetaData();
				ResultSet rs = dmd.getTables(null,"APP", "ZEIT",null);
				if (!rs.next()) {
					Statement stmt = conn.createStatement();
				    stmt.executeUpdate("CREATE TABLE \"APP\".\"ZEIT\"(ZEIT time, STARTNUMMER int NOT NULL)");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		}

		private void closeDB() {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {

			}
		}

		
		public void openCsv() {
		  	try {
		  		csvFile = new FileWriter(ReadConfig.getConfig().getString("LAPCOUNT_RESULT_FILENAME"), false);
				//csvFile.append("time;antenna;serial;\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	        }
		}
		
		public void closeCsv() {
	        try {
	        	csvFile.flush();
				csvFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		
	}
		
}
