/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Addiel
 */
@XmlRootElement(name="Model")
public class Model extends Observable implements Serializable{
        List<State> states;
        List<Line> links; 
        State inicial;
    /** variables auxiliares para el desplazamiento del objeto*/ 
    public Model(){
       states = new ArrayList<>();
       links = new ArrayList<>(); 
    }
    @XmlElement(name = "Estados")
    public List<State> getStates(){
        return this.states;
    }
    @XmlElement(name = "Lineas")
    public List<Line> getLinks(){
        return this.links;
    }
    public Model addS(State s){
        states.add(s);
        this.setChanged();
        this.notifyObservers(null); // parametro no usado por ahora                       
        return this;
    }
    public Model addL(String n,int o, int d){
        Line l = new Line(this.states.get(o),this.states.get(d));
        l.setName(n);
        links.add(l);
        this.setChanged();
        this.notifyObservers(null); // parametro no usado por ahora                       
        return this;
     
     }
    /**metodo necesario para recuperar archivo*/
    public Model addL2(Line l){
        links.add(l);
        this.setChanged();
        this.notifyObservers(null); // parametro no usado por ahora                       
        return this;
     
     }
    public void changePos(int i,int x2, int y2){
        this.states.get(i).x+=x2;
        this.states.get(i).y+=y2;
        this.setChanged();
        this.notifyObservers(null);
    }
     @Override
    public void addObserver(java.util.Observer o) {
        super.addObserver(o);
        setChanged();
        notifyObservers(null); // parametro no usado por ahora
    }
    /** metodos para guardar, recuperar y normalizar datos*/
    
    public void guardar(OutputStream xml){
 
        try{
            JAXBContext ctx = JAXBContext.newInstance(this.getClass());
           /** realiza la conversion de obj java a xml*/
            Marshaller ma = ctx.createMarshaller();
            /** prepara formato de archivo xml*/
            ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ma.marshal(this,xml);
            setChanged();
            notifyObservers(null);
            JOptionPane.showMessageDialog(null," Archivo guardado con exito! ","Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }catch(JAXBException e){
            System.out.println("error");
        }
    }
    public void recuperar(InputStream xml){
       try{
           JAXBContext ctx = JAXBContext.newInstance(this.getClass());
           Unmarshaller um = ctx.createUnmarshaller();
           Model mod = (Model) um.unmarshal(xml);
                
           for(int i = 0; i < mod.getStates().size();i++){
               this.addS(mod.getStates().get(i));             
           }
           for(int i = 0; i < mod.getLinks().size();i++){
                this.addL2(mod.getLinks().get(i));
           }
           this.normalizar();
           setChanged();
           notifyObservers(null);
           for(int i = 0; i < mod.getStates().size();i++){
                System.out.println(this.getStates().get(i).getName() );
                System.out.println(this.getLinks().get(i).getName());
           }
           }catch(JAXBException e){}
          
    }
    
    void normalizar(){
        for(int i = 0; i < getLinks().size();i++){
        for(int j = 0; j < getStates().size();j++){
            if(getStates().get(j).getName().equals(this.getLinks().get(i).getOrg().getName())){
               getLinks().get(i).setOrg(this.getStates().get(j));
               
            }else if(getStates().get(j).getName().equals(this.getLinks().get(i).getDest().getName())){
               getLinks().get(i).setDest(this.getStates().get(j));
            }
            else if(this.getLinks().get(i).getOrg().getName().equals(this.getLinks().get(i).getDest().getName())){
                getLinks().get(i).setOrg(this.getStates().get(j));
                getLinks().get(i).setDest(this.getLinks().get(i).getOrg());
            }
        }
        
        }
       
    }
    /**metodos necesarios para validar hileras */
    public String[] getHilera( List<Line> l ){
    String []aux = null;
    for(int i = 0; i < l.size(); i++){
        aux[i] = l.get(i).getName();
    }
    return aux;
    }
    public State getEstadoInicial(){
        for(int i=0;i<this.states.size();i++){
            if(this.states.get(i).getState()==1){
                inicial = this.states.get(i);
                break;
             }
         }
         return inicial;
     }        
        
    
    
   
    
}
