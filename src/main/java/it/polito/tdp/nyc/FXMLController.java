/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.nyc.model.Hotspot;
import it.polito.tdp.nyc.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<String> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="txtDistanza"
    private TextField txtDistanza; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtStringa"
    private TextField txtStringa; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtTarget"
    private ComboBox<String> txtTarget; // Value injected by FXMLLoader

    @FXML
    void doAnalisiGrafo(ActionEvent event) {
    	
    	int gradoMax= model.analisiGrafo();
    	//this.txtResult.appendText("\nGrado max: "+gradoMax ) ;

    
    	for(String ss: model.getListGradoMax()) {
        	this.txtResult.appendText("\n"+ss+"  --  Grado: "+gradoMax );
    	}
        
    	}
 
    	
    

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	String target = this.txtTarget.getValue(); 
    	String stringa= this.txtStringa.getText(); 
    	
    	this.txtResult.appendText("\nCAMMINO ACICLICLO TROVATO FINO A : "+target);
    	for(String ss: model.camminoAciclico(target, stringa)) {
    		this.txtResult.appendText("\n"+ss);
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	Double distanza = Double.parseDouble(this.txtDistanza.getText()); 
    	String provider= this.cmbProvider.getValue(); 
    	
    	model.creaGrafo(provider,distanza);
    	this.txtResult.appendText("Numero vertici: "+model.getVertici());
    	this.txtResult.appendText("\nNumero archi : "+model.getNumEdges());

    	//   TRY/CATCH 
    	
    	for(String ss: model.getVerticiCmb()) {
    		this.txtTarget.getItems().add(ss); 
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDistanza != null : "fx:id=\"txtDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStringa != null : "fx:id=\"txtStringa\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    public void setModel(Model model) {
    	this.model = model;
    	this.setCmbox();
    }
    
    public void setCmbox() {
    	
    	for(String s: model.getAllProvider() ) {
   	     this.cmbProvider.getItems().add(s); 
   	 }	
    	
    }
    
    
    
}







