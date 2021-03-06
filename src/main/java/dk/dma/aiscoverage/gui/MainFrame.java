/*
 * Copyright 2012 Danish Maritime Authority. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *   2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY Danish Maritime Safety Administration ``AS IS'' 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of Danish Maritime Authority.
 * 
 */
package dk.dma.aiscoverage.gui;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import com.bbn.openmap.MapHandler;

import dk.dma.aiscoverage.acv.ACV;
import dk.dma.aiscoverage.calculator.CoverageCalculator;
import dk.dma.aiscoverage.calculator.DensityPlotCalculator;
import dk.dma.aiscoverage.data.Ship.ShipClass;
import dk.dma.aiscoverage.project.ProjectHandler;
import dk.dma.aiscoverage.settings.GuiSettings;

/**
 * The main frame containing map and panels 
 */
public class MainFrame extends JFrame implements WindowListener {
	
	
	private static final String TITLE = "AIS Coverage Viewer";
	
	private static final long serialVersionUID = 1L;	
	private static final Logger LOG = Logger.getLogger(MainFrame.class);
	
	protected static final int SENSOR_PANEL_WIDTH = 190;
	
	private final GUIHelper guiHelper = new GUIHelper();
	
	private ChartPanel chartPanel;
	private AnalysisPanel leftPanel;
	//private AnalysisPanel rightPanel;

	private JPanel glassPanel;
	private JMenuBar menuBar;
	
	public MainFrame() {
        super();
        
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        initGUI();
    }
	
	private void initGUI() {
		MapHandler mapHandler = ACV.getMapHandler();
		// Get settings
		GuiSettings guiSettings = ACV.getSettings().getGuiSettings();
		
		setTitle(TITLE);		
		// Set location and size
		if (guiSettings.isMaximized()) {
			setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
		} else {
			setLocation(guiSettings.getAppLocation());
			setSize(guiSettings.getAppDimensions());
		}		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(getAppIcon());
		addWindowListener(this);
		
		// Create panels
		Container pane = getContentPane();
		chartPanel = new ChartPanel();
		leftPanel = new AnalysisPanel();
		leftPanel.setBorder(new EmptyBorder(5,5,5,5));
		//rightPanel = new AnalysisPanel();
		//rightPanel.setBorder(new EmptyBorder(5,5,5,5));
		

		// Add panels
		pane.add(chartPanel);
		
		leftPanel.setPreferredSize(new Dimension(240, 0));
		pane.add(leftPanel, BorderLayout.LINE_END);
		
		
		//rightPanel.setPreferredSize(new Dimension(240, 0));
		//pane.add(rightPanel, BorderLayout.NORTH);
		
		
		
		

		// Set up the chart panel with layers etc
		chartPanel.initChart();
		
		
		// Init glass pane
		initGlassPane();
		
		// Add self to map map handler
		mapHandler.add(chartPanel);
		mapHandler.add(this);
		mapHandler.add(leftPanel);
		mapHandler.add(ProjectHandler.getInstance());

		
		//menubar items
		
		/*
		 * file menu
		 */
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		
		JMenuItem mntmNewAnalysis = new JMenuItem("New analysis");
		menu.add(mntmNewAnalysis);
		mntmNewAnalysis.addActionListener(new ActionListener()  {public void actionPerformed(ActionEvent e)	
		{	NewAnalysis2 newAnalysisframe = new NewAnalysis2(leftPanel, chartPanel);
			newAnalysisframe.setVisible(true);	}	});
		
		JMenuItem mntmSave = new JMenuItem("Save");
		menu.add(mntmSave);
		mntmSave.addActionListener(new ActionListener()  {public void actionPerformed(ActionEvent e)	{	guiHelper.saveFileDialog();	}	});
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		menu.add(mntmLoad);
		mntmLoad.addActionListener(new ActionListener()  {public void actionPerformed(ActionEvent e)	{	guiHelper.openFileDialog();	}	});
		
		
		/*
		 * export menu
		 */
		JMenu mnExport = new JMenu("Export");
		menuBar.add(mnExport);
		
		JMenuItem mntmExportToKml = new JMenuItem("Export to KML");
		mnExport.add(mntmExportToKml);
		mntmExportToKml.addActionListener(new ActionListener()  {public void actionPerformed(ActionEvent e)	{	guiHelper.saveKMLDialog();	}	});
		
		//JMenuItem mntmExportToShape = new JMenuItem("Export to shape");
		//mnExport.add(mntmExportToShape);
		//mntmExportToShape.addActionListener(new ActionListener()  {public void actionPerformed(ActionEvent e)	{	guiHelper.saveShapeDialog();	}	});
		
		JMenuItem mntmExportToPNG = new JMenuItem("Export to PNG");
		mnExport.add(mntmExportToPNG);
		mntmExportToPNG.addActionListener(new ActionListener()  {public void actionPerformed(ActionEvent e)	{	guiHelper.savePNGDialog(chartPanel);	}	});
		
		/*
		 * about menu
		 */
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		//JMenuItem mntmHelp = new JMenuItem("Help");
//		mnAbout.add(mntmHelp);
//		mntmHelp.addActionListener(new ActionListener()  {public void actionPerformed(ActionEvent e) 
//		{	
//			Help helpframe = new Help();
//			helpframe.setVisible(true);	}	});
		
		JMenuItem mntmLicense = new JMenuItem("License");
		mnAbout.add(mntmLicense);
		mntmLicense.addActionListener(new ActionListener()  {public void actionPerformed(ActionEvent e) 
		{	
			License licenseframe = new License();
			licenseframe.setVisible(true);	}	});
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnAbout.add(mntmAbout);
		mntmAbout.addActionListener(new ActionListener()  {public void actionPerformed(ActionEvent e) 
		{	
			About aboutframe = new About();
			aboutframe.setVisible(true);	}	});
		
		
		
	}
	
	private void initGlassPane() {
		glassPanel = (JPanel)getGlassPane();
		glassPanel.setLayout(null);
		glassPanel.setVisible(false);
	}
	
	private static Image getAppIcon() {
		java.net.URL imgURL = ACV.class.getResource("/images/appicon.png");
		if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
		}
		LOG.error("Could not find app icon");
		return null;
	}

	public ChartPanel getChartPanel() {
		return chartPanel;
	}
	
	public void saveSettings() {
		// Save gui settings
		GuiSettings guiSettings = ACV.getSettings().getGuiSettings();
		guiSettings.setMaximized((getExtendedState() & MAXIMIZED_BOTH) > 0);
		guiSettings.setAppLocation(getLocation());
		guiSettings.setAppDimensions(getSize());
		// Save map settings
		chartPanel.saveSettings();
	}
	
	
	@Override
	public void windowActivated(WindowEvent we) {
	}

	@Override
	public void windowClosed(WindowEvent we) {
	}

	@Override
	public void windowClosing(WindowEvent we) {
		// Close routine
		ACV.closeApp();
	}
	


	@Override
	public void windowDeactivated(WindowEvent we) {
	}

	@Override
	public void windowDeiconified(WindowEvent we) {
	}

	@Override
	public void windowIconified(WindowEvent we) {
	}

	@Override
	public void windowOpened(WindowEvent we) {
	}
	
	public void startAnalysisFromCmds(){
		 
			 ProjectHandler projectHandler = ProjectHandler.getInstance();
				String densityCellSize = "-";
//				String coverageCellSize = "-";
				//the used project
				dk.dma.aiscoverage.project.AisCoverageProject project = projectHandler.createProject();
//
				 //adds selected file, or loop thru selected streams and add them to the project
				String input = "filepath";
//				String timer = setAnalysisTimer(project);


				/*
				 * is coverage enabled. if so, add calculator
				 */
//				if (chckbxEnableCoverage.isSelected() == true) {
					// add coverage calculator
					CoverageCalculator coverageCalc = new CoverageCalculator(project, true);
					coverageCalc.setCellSize(ACV.getSettings().getGuiSettings().getCoverageCellSize());
//					coverageCellSize = coverageCellsizeTxt.getText();
					coverageCalc.setHighThreshold(8.0);
					coverageCalc.setLowThreshold(3.0);
//					calc.getAllowedShipClasses().put(ShipClass.CLASS_A, ShipClass.CLASS_A);
//					calc.getAllowedShipClasses().put(ShipClass.CLASS_B, ShipClass.CLASS_B);
//					filterShipClass(coverageCalc);
//					filterCargoType(coverageCalc);
//					coverageCalc.setBufferInSeconds(Integer.parseInt(messageBufferTxt.getText()));
//
//						if (chckbxIncludeTurningShips.isSelected() == true) {
//							coverageCalc.setIgnoreRotation(false);
//							coverageCalc.setDegreesPerMinute(Integer.parseInt(rotationTxt.getText()));
//						}
//					
//					project.addCalculator(coverageCalc);
//				}
//
//				/*
//				 * is density enabled, if so, if so add calculator
//				 */
//				if (chckbxEnableDensity.isSelected() == true) {
//					DensityPlotCalculator densityCalc = new DensityPlotCalculator(project);
//					densityCalc.setCellSize(Integer.parseInt(densityCellSizeTxt.getText()));
//					densityCellSize = densityCellSizeTxt.getText();
//					filterShipClass(densityCalc);
//					filterCargoType(densityCalc);
//					
//					cp.getDensityPlotLayer().setHighMedLow(Integer.parseInt(highTxt.getText()), Integer.parseInt(mediumTxt.getText()), Integer.parseInt(lowTxt.getText()));
//					
//					project.addCalculator(densityCalc);
//					
//					
//					
//				}
//
//				//sets the recorded data in the analysisPanel
//				ap.setAnalysisData(input, coverageCellSize, densityCellSize, timer);
//				//Close the window
//				dispose();
			}
//	
	
}
