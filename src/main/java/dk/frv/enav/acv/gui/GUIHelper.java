package dk.frv.enav.acv.gui;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import dk.dma.aiscoverage.KMLGenerator;
import dk.dma.aiscoverage.data.BaseStation;
import dk.dma.aiscoverage.data.BaseStationHandler;
import dk.dma.aiscoverage.project.ProjectHandler;


public class GUIHelper {
	
	
	JFileChooser fileChooser = new JFileChooser(){
		@Override
		public void approveSelection(){
		    File f = getSelectedFile();
		    if(f.exists() && getDialogType() == SAVE_DIALOG){
		        int result = JOptionPane.showConfirmDialog(this,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
		        switch(result){
		            case JOptionPane.YES_OPTION:
		                super.approveSelection();
		                return;
		            case JOptionPane.NO_OPTION:
		                return;
		            case JOptionPane.CLOSED_OPTION:
		                return;
		            case JOptionPane.CANCEL_OPTION:
		                cancelSelection();
		                return;
		        }
		    }
		    super.approveSelection();
		}
	};
	

	//JFileChooser fileChooser = null;
	FileFilter internalFilter = null;
	FileFilter kmlFilter = null;
	FileFilter shapeFilter = null;
	ProjectHandler projectHandler = ProjectHandler.getInstance();
	
	
	public GUIHelper()
	{
		internalFilter = new FileNameExtensionFilter("aiscoverage", "aiscoverage"); 
		kmlFilter = new FileNameExtensionFilter("kml", "kml"); 
		shapeFilter = new FileNameExtensionFilter("shape", "shape"); 	
		fileChooser.setBounds(0, 0, 600, 400);
	}
	
	
	public String openShapeFileDialog()
	{
		String fileUrl = null;
		//fileChooser = new JFileChooser();
		//fileChooser.setBounds(0, 0, 600, 400);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.removeChoosableFileFilter(internalFilter);
		fileChooser.removeChoosableFileFilter(shapeFilter);
		fileChooser.removeChoosableFileFilter(kmlFilter);
		fileChooser.setFileFilter(shapeFilter);

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			fileUrl = fileChooser.getSelectedFile().getPath();
			System.out.println(fileUrl);
		}		
		return fileUrl;
	}
	
	public String openAISFileDialog()
	{
		String fileUrl = null;
		//fileChooser = new JFileChooser();
		//fileChooser.setBounds(0, 0, 600, 400);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.removeChoosableFileFilter(internalFilter);
		fileChooser.removeChoosableFileFilter(shapeFilter);
		fileChooser.removeChoosableFileFilter(kmlFilter);
		//fileChooser.setFileFilter(internalFilter);

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			fileUrl = fileChooser.getSelectedFile().getPath();
			System.out.println(fileUrl);
		}		
		return fileUrl;
	}
	
	public void openFileDialog()
	{
		String fileUrl = null;
		//fileChooser = new JFileChooser();
		//fileChooser.setBounds(0, 0, 600, 400);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.removeChoosableFileFilter(internalFilter);
		fileChooser.removeChoosableFileFilter(shapeFilter);
		fileChooser.removeChoosableFileFilter(kmlFilter);
		fileChooser.setFileFilter(internalFilter);

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			fileUrl = fileChooser.getSelectedFile().getPath();
			System.out.println(fileUrl);
			projectHandler.loadProject(fileUrl);
		}		
	}
	
	public void saveFileDialog()
	{
		//fileChooser = new JFileChooser();
		//fileChooser.setBounds(0, 0, 600, 400);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.removeChoosableFileFilter(internalFilter);
		fileChooser.removeChoosableFileFilter(shapeFilter);
		fileChooser.removeChoosableFileFilter(kmlFilter);
		fileChooser.setFileFilter(internalFilter);

		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			String fileUrl = fileChooser.getSelectedFile().getPath();
			System.out.println(""+fileUrl);
			
			if(!fileUrl.endsWith(".aiscoverage"))
			{
			    fileUrl = (fileUrl + ".aiscoverage");
			}
			
			
			projectHandler.saveProject(projectHandler.getProject(), fileUrl+"");
			//.aiscoverage
		}
	}
	
	/*
	 * not working properly
	 */
	public void saveKMLDialog()
	{
		//fileChooser = new JFileChooser();
		//fileChooser.setBounds(0, 0, 600, 400);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.removeChoosableFileFilter(internalFilter);
		fileChooser.removeChoosableFileFilter(shapeFilter);
		fileChooser.removeChoosableFileFilter(kmlFilter);
		fileChooser.setFileFilter(kmlFilter);

		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			String fileUrl = fileChooser.getSelectedFile().getPath();
			System.out.println(""+fileUrl);
			
			if(!fileUrl.endsWith(".kml"))
			{
			    fileUrl = (fileUrl + ".kml");
			}
			
			ArrayList<BaseStation> list = new ArrayList<BaseStation>(projectHandler.getProject().getBaseStationHandler().grids.values());
			KMLGenerator.generateKML(list , fileUrl+".kml");
			
			System.out.println(fileUrl + " printet");
		}
	}
	
	
	public void saveShapeDialog()
	{
		//fileChooser = new JFileChooser();
		//fileChooser.setBounds(0, 0, 600, 400);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.removeChoosableFileFilter(internalFilter);
		fileChooser.removeChoosableFileFilter(shapeFilter);
		fileChooser.removeChoosableFileFilter(kmlFilter);
		fileChooser.setFileFilter(shapeFilter);

		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			String fileUrl = fileChooser.getSelectedFile().getPath();
			System.out.println(""+fileUrl);
			
			if(!fileUrl.endsWith(".shape"))
			{
			    fileUrl = (fileUrl + ".shape");
			}
			
			
			File fileName = new File( fileChooser.getSelectedFile( ) + ".shape" );
			try {
				BufferedWriter outFile = new BufferedWriter( new FileWriter( fileName ) );
				/*
				 * write polygon layer data to file
				 */
				//outFile.write( getTxtArea_log().getText( ) ); //put in textfile
				outFile.flush( ); // redundant, done by close()
				outFile.close( );
				
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	

}
	

