/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Addiel
 */
public class Line {
    String name;
    State or;
    State dest;
public Line(String n,State or, State dest){
    this.name = n;
    this.or = or;
    this.dest = dest;
}
public Line(State or, State dest){
    this.name = "";
    this.or = or;
    this.dest = dest;
}
public Line(){
    this.name = " ";
    this.dest = null;
    this.or = null;
}
@XmlElement
 public String getName(){
        return this.name;
    }  
public void setName(String n){
    this.name = n;
    }
@XmlElement
public State getOrg(){
    return this.or;
    }
@XmlElement
public State getDest(){
    return this.dest;
    }
public void setOrg(State or){
    this.or = or;
    }
public void setDest(State dest){
    this.dest = dest;
    }
}
