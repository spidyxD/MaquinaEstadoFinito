/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.xml.bind.annotation.XmlType;
/**
 *
 * @author Addiel
 */
//@XmlRootElement(name="State")
@XmlType( propOrder = {"name", "state", "x", "y"})
public class State {
    String name;
    int state;
    int x,y;
    /** variable que sirve para calcular el movimiento del objeto */
   
    /**
     * variables auxiliares para el desplazamiento del objeto
     * @param n
     * @param x
     * @param y
     * @param s
     */
    public State(String n, int x, int y, int s){
        this.name = n;
        this.state = s;
        this.x = x;
        this.y = y;
    }                                               /**constructores de la clase*/
     public State( ){
        this.name = " ";
        this.state = 0;
        this.x = 100;
        this.y = 100;
       
    }
    public String getName(){
        return this.name;
    }
    public int getState(){
        return this.state;
    }
    public void setName(String n){
        this.name = n;
    }
    public void setState(int s){
        this.state = s;
    }
    public int getX(){
        return this.x;
    }
    public void setX(int x){
        this.x = x;
    }
     public int getY(){
        return this.y;
    }
    public void setY(int y){
        this.y = y;
    }
}
