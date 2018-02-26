/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Model.Line;
import Model.Model;
import Model.State;
import View.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
/**
 *
 * @author Addiel
 */
public class Controller extends JLabel implements ActionListener, MouseListener, MouseMotionListener{
    Model model;
    View view;
    int c;
    State s;
    Line l;
    String aux = "";
    int selecc = -1;
    int org = 0;
    int dest = 0;
    Point begin=null;
    Point end=null;
    Boolean flag = true;
  
public Controller(Model model, View view) {
    this.model = model;
    this.view = view;
    this.s = null;
    this.l = null;
    view.setModel(model);
    view.setController(this);
        
    }
    
     @Override
    public void mouseClicked(MouseEvent ev) {  
        if (selecc!=-1){
            if(flag){ 
                org = selecc;
                flag=false;
            }
            else{
                dest = selecc;
                agregaSimbolo();
            }
        }
    }
    public void agregaSimbolo(){ // Agrega un simbolo a la transicion
        Boolean aux = false;
        String n = JOptionPane.showInputDialog(null,"Simbolos","Simbolo transicion",JOptionPane.QUESTION_MESSAGE);
        if(n!=null){
            for(int i = 0; i < model.getLinks().size();i++){
                if(model.getLinks().get(i).getOrg()==model.getStates().get(org)){
                    aux = n.contentEquals(model.getLinks().get(i).getName());
                    if(aux){
                        break;
                    }
                }
            }
            if(!n.isEmpty() && !aux){
                model.addL(n,org,dest);
                flag = true;
            }
            else{
                JOptionPane.showMessageDialog(null,"Ingrese un simbolo valido", "Aviso",JOptionPane.WARNING_MESSAGE);
                flag = true;
            }
        }
        else{
            flag = true;
            selecc = -1;
            org = 0;
            dest = 0;
        }
    }

    @Override
    public void mousePressed(MouseEvent ev) {
        try{
            begin = ev.getPoint();
            selecc = isInside(ev.getPoint());
        }catch(Exception e){}
    }

    @Override
    public void mouseReleased(MouseEvent ev) {  

    }

    @Override
    public void mouseEntered(MouseEvent e) {  
     
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }

    @Override
    public void mouseDragged(MouseEvent ev) {
      try{
         end = ev.getPoint();
         if(selecc!=-1){
            model.changePos(selecc,end.x-begin.x, end.y-begin.y);
            begin=end;
        }
      }catch(Exception e){}
    }
    @Override
    public void mouseMoved(MouseEvent me) {
    }
     private int isInside(Point p){
        try{
        for(int i=0; i<model.getStates().size();i++){
                s = model.getStates().get(i);
            Rectangle r = new Rectangle(s.getX(),s.getY(),64,64);
            if (r.contains(p)){
                return i;
            }
        }
        }catch(Exception e){}
            return -1;
        
    }
    
      @Override
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand()){ // Switch principal del la barra de menu
            case "Guardar":{
                try{
                    view.getGuardarArchivo();
                    
                }catch(Exception e){}
            }break;
            case "Recuperar":{
                try{
                    view.getAbrirArchivo();
                }catch(Exception e){}
            }break; 
            case "Limpiar":{
                try{
                    limpiar();
                    break;
                }catch(Exception e){}
            }
            case "Inicial": {
               try{
                   if (c == 0){ //Validacion para que solo exista un unico estado inicial
                       agregaInicial();
                       break;
                   }
                   else{
                       JOptionPane.showMessageDialog(null,"Solo puede existir un estado incial", "Aviso",JOptionPane.WARNING_MESSAGE); 
                       break;
                   }
               }catch(Exception e){}
           }   
           case "Intermedio":{
               try{
                   agregaIntermedio();
                   break;
               }catch(Exception e){}
           }
           case "Final": {
               try{
                   agregaFinal();
                   break;
               }catch(Exception e){}
           }
           case "Hilera": {
               try{
                   hilera();
                   break;
               }catch(Exception e){}
            }
        } 
    }
    public void hilera(){
        String h =  JOptionPane.showInputDialog(null,"Ingrese la hilera a verificar","Hilera",JOptionPane.QUESTION_MESSAGE);
        if(h!=null){
            if(!h.isEmpty()){
                if(esAceptada(h)){
                    JOptionPane.showMessageDialog(null,h+" : ACEPTADA","Mensaje",JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null,h+" : NO ACEPTADA","Mensaje",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Digite una hilera", "Aviso",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    public Boolean esAceptada(String h){
        Boolean es=true;
        State actual = model.getEstadoInicial();
        int estado;
        int cont=0;
        while(cont<h.length()){
            for(int i=0;i<model.getLinks().size();i++){
                l = model.getLinks().get(i);
                if(l.getOrg()==actual && (comparaTexto(l.getName(),h.charAt(cont))==true)){
                    actual = l.getDest();
                    es = true;
                    break;
                }
                else{
                    es = false;
                }
            }
            cont++;
        }
        estado = actual.getState();
        return (es==true && estado == 3);
    }
    public Boolean comparaTexto(String n, char h){
        for(int i=0; i<n.length();i++){
            if(h == n.charAt(i)){
                return true;
            }
        }
        return false;
    }
    public void agregaInicial(){
        Boolean aux=false;
        State s = new State();
        String n = JOptionPane.showInputDialog(null,"Escribe el id del Estado","Estado inicial",JOptionPane.QUESTION_MESSAGE);
        if(n!=null){
            for(int i = 0; i < model.getStates().size();i++){
                aux = n.contentEquals(model.getStates().get(i).getName());
                if(aux){
                    break;
                }
            }
            if(!n.isEmpty() && !aux){
                s.setName(n);
                s.setState(1);
                this.model.addS(s);
                c=1;
            }
            else{
                JOptionPane.showMessageDialog(null,"Digite un id valido", "Aviso",JOptionPane.WARNING_MESSAGE);
                c = 0;
            }
        }
    }
    public void agregaIntermedio(){
        Boolean aux=false;
        State s = new State();
        String n = JOptionPane.showInputDialog(null,"Escribe el id del Estado","Estado intermedio",JOptionPane.QUESTION_MESSAGE);
        if(n!=null){
            for(int i = 0; i < model.getStates().size();i++){
                aux = n.contentEquals(model.getStates().get(i).getName());
                if(aux){
                    break;
                }
            }
            if(!n.isEmpty() && !aux){
                s.setName(n);
                s.setState(2);
                this.model.addS(s);
            }
            else{
                JOptionPane.showMessageDialog(null,"Digite un id valido", "Aviso",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    public void agregaFinal(){
        Boolean aux =false;
        State s = new State();
        String n = JOptionPane.showInputDialog(null,"Escribe el id del Estado","Estado final",JOptionPane.QUESTION_MESSAGE);
        if(n!=null){
            for(int i = 0; i < model.getStates().size();i++){
                aux = n.contentEquals(model.getStates().get(i).getName());
                if(aux){
                    break;
                }
            }
            if(!n.isEmpty() && !aux){
                s.setName(n);
                s.setState(3);
                this.model.addS(s);
            }
            else{
                JOptionPane.showMessageDialog(null,"Digite un id valido", "Aviso",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    public void limpiar(){
        int op;
        op = JOptionPane.showConfirmDialog(null, "Estas seguro de limpiar la pantalla?", "Aviso",JOptionPane.YES_OPTION, JOptionPane.ERROR_MESSAGE);
        if(op == 1){
            JOptionPane.showMessageDialog(null,"OK", "MENSAJE",JOptionPane.INFORMATION_MESSAGE);
            c = 1;
        }
        else if(op == 0){
            model.getStates().clear();
            model.getLinks().clear();
            view.clean();
            c = 0;
        }
    }
}

  


