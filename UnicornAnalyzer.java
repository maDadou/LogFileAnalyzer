package Analyzer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import comparator.Pair;
import comparator.RequestComparator;
import comparator.RequestType;
import comparator.Tests;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ButtonGroup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SpinnerDateModel;

import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.awt.Color;
import java.awt.SystemColor;

public class UnicornAnalyzer extends JFrame {
	private Date start, end;

	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UnicornAnalyzer frame = new UnicornAnalyzer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UnicornAnalyzer() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 767, 652);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBackground(new Color(240, 230, 140));
		splitPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(splitPane);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 250, 205));
		splitPane.setRightComponent(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		JScrollPane scrollPane_1 = new JScrollPane(textPane);
		scrollPane_1.setPreferredSize(new Dimension(400, 600));
		splitPane.setLeftComponent(scrollPane_1);

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textPane_1);

		scrollPane.setPreferredSize(new Dimension(200, 500));

		panel.add(scrollPane);

		// Bloc Date
		JPanel panelDate = new JPanel();
		panelDate.setBackground(new Color(189, 183, 107));
		panel.add(panelDate);
		panelDate.setLayout(new GridLayout(2, 2, 0, 1));
		SpinnerDateModel model = new SpinnerDateModel();

		SpinnerDateModel model2 = new SpinnerDateModel();

		JLabel lblStartedTime = new JLabel("Start Time :");
		lblStartedTime.setForeground(new Color(30, 144, 255));
		panelDate.add(lblStartedTime);
		JSpinner spinnerStart = new JSpinner(model);
		panelDate.add(spinnerStart);

		JLabel lblEndTime = new JLabel("End Time :");
		lblEndTime.setForeground(new Color(30, 144, 255));
		panelDate.add(lblEndTime);
		JSpinner spinnerEnd = new JSpinner(model2);
		panelDate.add(spinnerEnd);

		panelDate.setVisible(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(189, 183, 107));
		panel.add(panel_1);
				panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
				JTextPane textPaneWarning = new JTextPane();
				panel_1.add(textPaneWarning);
				textPaneWarning.setForeground(new Color(255, 20, 147));
				textPaneWarning.setBackground(new Color(189, 183, 107));
				textPaneWarning.setVisible(false);
		textPaneWarning.setText(
				"\nWARNING:\nThe choosen parameters will be applied to the next files you open.\nThe Add Report fonction will add report regardless of time parameters");
		textPaneWarning.setEditable(false);
		
		JButton btnOk = new JButton("OK");
		panel_1.add(btnOk);
		btnOk.setForeground(new Color(0, 255, 255));
		btnOk.setBackground(new Color(255, 0, 255));
		btnOk.setVisible(false);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start = (Date) spinnerStart.getValue();
				end = (Date) spinnerEnd.getValue();
				textPane_1.setText(textPane_1.getText()+"The choosen parameters will be applied on next opened files\n");
			}
		});

		JLabel lblSortingBy = new JLabel("Sorting by :");
		lblSortingBy.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblSortingBy);

		JLabel lblNewLabel = new JLabel("Please Choose in menu bar");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblNewLabel);

		JLabel lblNbr = new JLabel("1");
		lblNbr.setVisible(false);
		panel.add(lblNbr);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenu mnOpen = new JMenu("Add");
		mnFile.add(mnOpen);

		JFileChooser fileChooser = new JFileChooser("./");

		JMenuItem mntmOpen = new JMenuItem("Add Log File");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {

						int ret = fileChooser.showOpenDialog(null);

						if (ret == JFileChooser.APPROVE_OPTION) {
							File file = fileChooser.getSelectedFile();

							try {
								Pair p = Tests.readLog(file, Integer.parseInt(lblNbr.getText()), start, end);
								textPane_1.setText(textPane_1.getText() + p.string);
								lblNbr.setText("" + p.nbr);

							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}
				});
				;
			}
		});

		mnOpen.add(mntmOpen);

		JMenuItem mntmOpenReport = new JMenuItem("Add Report");
		mntmOpenReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {

						int ret = fileChooser.showOpenDialog(null);
						Pattern guillement=Pattern.compile("”");
						

						if (ret == JFileChooser.APPROVE_OPTION) {
							File file = fileChooser.getSelectedFile();
							String line = null;
							StringBuilder stringBuilder = new StringBuilder();
							try {
								BufferedReader br = new BufferedReader(new FileReader(file));
								while ((line = br.readLine()) != null) {
									stringBuilder.append(line);
								}
								br.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							String s = stringBuilder.toString();

							JSONObject obj;
							try {
								obj = new JSONObject(s);
								final JSONArray requests = obj.getJSONArray(file.getName());
								final int n = requests.length();
								for (int i = 0; i < n; ++i) {
									JSONObject reqType = requests.getJSONObject(i);
									Matcher m=guillement.matcher(reqType.getString("Type"));
									String newType=m.replaceAll("\"");
									RequestType newReq = new RequestType(newType,
											reqType.getInt("Total Time"), reqType.getDouble("Average Time"),
											reqType.getInt("Number"), reqType.getInt("Maximum Time"));
									if (Tests.requestTypes.containsKey(newType)) {
										RequestType req = Tests.requestTypes.get(newReq.getType());
										req.add(newReq);

									} else {
										Tests.requestTypes.put(newReq.getType(), newReq);
										Tests.requestsSet.add(newReq);
									}
								}
								textPane_1.setText("\n"+
										textPane_1.getText() + file.getName() + " has been imported sucessfully\n\n");

							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								textPane_1.setText("\n\n"+textPane_1.getText() + file.getName()
										+ " couldn't be imported.\nPlease check File Format (must be JSON type)\n\n");
								
							}

							
						}

					}
				});
				;
			}
		});
		mnOpen.add(mntmOpenReport);

		JMenuItem mntmSave = new JMenuItem("Save Report As...");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {

						int returnVal = fileChooser.showSaveDialog(null);
						File file = fileChooser.getSelectedFile();
						BufferedWriter writer = null;
						if (returnVal == JFileChooser.APPROVE_OPTION) {

							String toSave = "{\"" + file.getName() + "\": [\n";
							for (RequestType v : Tests.requestsSet) {
								toSave = toSave + v.toJSONString() + ",\n";
							}
							toSave = toSave.substring(0, toSave.length() - 2);
							toSave = toSave + "\n]}";

							try {
								writer = new BufferedWriter(new FileWriter(file));
								writer.write(toSave);
								writer.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							textPane.setText(textPane.getText()+"\n\n Report has been sucessfully saved\n\n");

						}

					}
				});
				;
			}
		});
		mnFile.add(mntmSave);

		JMenu mnSortBy = new JMenu("Sort by...");
		menuBar.add(mnSortBy);

		JRadioButtonMenuItem rdbtnmntmAverageTime = new JRadioButtonMenuItem("Average Time");
		rdbtnmntmAverageTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						lblNewLabel.setText("Average Time");
					}
				});
				;
			}
		});
		buttonGroup_1.add(rdbtnmntmAverageTime);
		mnSortBy.add(rdbtnmntmAverageTime);

		JRadioButtonMenuItem rdbtnmntmTotalTime = new JRadioButtonMenuItem("Total Time");
		rdbtnmntmTotalTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						lblNewLabel.setText("Total Time");

					}
				});
				;
			}
		});
		buttonGroup_1.add(rdbtnmntmTotalTime);
		mnSortBy.add(rdbtnmntmTotalTime);

		JRadioButtonMenuItem rdbtnmntmMaximumTime = new JRadioButtonMenuItem("Maximum Time");
		rdbtnmntmMaximumTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						lblNewLabel.setText("Maximum Time");
					}
				});
				;
			}
		});
		buttonGroup_1.add(rdbtnmntmMaximumTime);
		mnSortBy.add(rdbtnmntmMaximumTime);

		JMenu mnReportParameters = new JMenu("Report Parameters");
		menuBar.add(mnReportParameters);

		JMenuItem mntmInit = new JMenuItem("Reinitialize");
		mntmInit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						Tests.initialize();
						lblNbr.setText("1");
						textPane_1.setText("");
						textPane.setText("");
					}
				});
				;
			}
		});
		menuBar.add(mntmInit);

		JMenuItem mntmNewMenuItem = new JMenuItem("Time parameters");
		mntmNewMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panelDate.setVisible(true);
				textPaneWarning.setVisible(true);
				btnOk.setVisible(true);

			}

		});
		mnReportParameters.add(mntmNewMenuItem);

		JMenuItem mntmDefault = new JMenuItem("Default");
		mntmDefault.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panelDate.setVisible(false);
				textPaneWarning.setVisible(false);
				btnOk.setVisible(false);
				start = null;
				end = null;
				

				// TODO Auto-generated method stub
			}
		});

		mnReportParameters.add(mntmDefault);

		JButton btnShowReport = new JButton("Show Report");
		btnShowReport.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnShowReport);
		btnShowReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						String typeRanging = lblNewLabel.getText();
						if (typeRanging.equals("Maximum Time")) {

							textPane.setText(RequestComparator.sortByMaximumTime());

						}
						if (typeRanging.equals("Average Time")) {

							textPane.setText(RequestComparator.sortByAverageTime());
							;
						}
						if (typeRanging.equals("Total Time")) {

							textPane.setText(RequestComparator.sortByTotalTime());
						}
						if(typeRanging.equals("Please Choose in menu bar")){
						textPane.setText("You must choose a valid parameter for Sorting in the \"Sort By\" menu");
						}
						
					}
				});
				;
			}
		});

		setVisible(true);
	}
}
