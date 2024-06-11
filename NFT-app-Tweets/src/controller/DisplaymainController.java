/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class DisplaymainController  {

    private Stage stage;
    private Scene scene;
    private Parent root;
  
  
    @FXML
    private RadioButton radiobtn1;

    @FXML
    private RadioButton radiobtn2;

    @FXML
    private RadioButton radiobtn3;

     @FXML
    void switchToNftDis(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/giaodienNft.fxml"));
       stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("NFT Analyse");
        stage.show();
     
       
    }
    
     @FXML
    void switchToTweetDis(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/giaodienTweet.fxml"));
       stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tweets Analyse");
        stage.show();
     
       
    }
    
    @FXML
    void switchToHome(ActionEvent event) throws IOException {
          Parent root = FXMLLoader.load(getClass().getResource("/view/giaodienchinh2.fxml"));
       stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.show();
    }
}
