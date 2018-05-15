/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cam;

/**
 *
 * @author toms
 */
import java.io.IOException;

public class Coordenadas extends Thread {
    private long millis;
    private double longi = -8.401299;
    private double lat = 41.555019;
    private int idcar = 1;
    private static int delay = 1000;
    private double varialat = 0.000100;
    private double varialong = 0.000100;   
       
    
    public Coordenadas() {
       // this.millis = System.currentTimeMillis();
      //  this.lat = 41.558885;
      //  this.longi =  -8.396776;
       // this.idcar = 1;
        }
    
        public Coordenadas(long millis, double longi, double lat, int idcar, double varlong, double varlat) {
        this.millis = millis;
        this.lat = lat;
        this.longi =  longi;
        this.idcar = idcar;
        this.varialat = varlat;
        this.varialong = varlong;
        }
        public Coordenadas(Coordenadas c){
         this.millis = c.getMillis();
         this.lat = c.getLat();
         this.longi = c.getLongi();
         this.idcar = c.getIdCar();
        }
        public long getMillis(){
            return this.millis;
        }
        public double getLat(){
            return this.lat;
        }
        public double getLongi(){
            return this.longi;
        }
        public int getIdCar(){
            return this.idcar;
        }
        public void setMillis(long mill){
            this.millis = mill;
        }
        public void setLat(double lat){
            this.lat = lat;
        }
        public void setLongi(double longi){
            this.longi = longi;
        }
        public void setIdCar(int idcar){
            this.idcar = idcar;
        }
        public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append("Milis: " + this.millis + "\n");
        sb.append("Lat: " + this.lat + "\n");
        sb.append("Longitude: " + this.longi + "\n");
        sb.append("id: " + this.idcar +"\n");
        
        return sb.toString();
                
        }
        public void run() {

            while (true) {

                this.millis = System.currentTimeMillis();
                this.lat = lat + this.varialat;
                this.longi = longi + this.varialong;
                //System.out.println("Latitude: " +lat +  " Longitude: " +longi + " Miliseconds: " +millis+ " IDCAR: " + idcar);

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
