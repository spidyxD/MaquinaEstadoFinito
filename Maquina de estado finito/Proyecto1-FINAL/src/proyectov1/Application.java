/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectov1;

import Controler.Controller;
import Model.Model;
import View.View;


/**
 *
 * @author Addiel
 */
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Model model=new Model() ;
        View views = new View();
        Controller control = new Controller(model,views);
        views.setVisible(true);
    
    }
    
}
