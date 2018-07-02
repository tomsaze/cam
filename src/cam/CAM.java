/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cam;

import sun.security.krb5.internal.ccache.CCacheInputStream;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author toms
 */
public class CAM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {


        CalculoColisao c = new CalculoColisao();
        c.colisao(4);



//        SUMOOutput s0 = new SUMOOutput();
//        List<SUMOCoordenadas> getListaVehiclo0 = new ArrayList<>();
//        getListaVehiclo0 = s0.testeSUMO("0");
//
//        SUMOOutput s1 = new SUMOOutput();
//        List<SUMOCoordenadas> getListaVehiclo1 = new ArrayList<>();
//        getListaVehiclo1 = s1.testeSUMO("1");
//
//        CalculoColisao cal = new CalculoColisao();
//
//        for(SUMOCoordenadas veiculo0 : getListaVehiclo0 ) {
//           // cal.colisao(veiculo0.latitude, veiculo0.longitude);
//            System.out.println("0: " + veiculo0.latitude + " " + veiculo0.longitude + " " + veiculo0.time);
//        }
//        for(SUMOCoordenadas veiculo1 : getListaVehiclo1 ) {
//            // cal.colisao(veiculo0.latitude, veiculo0.longitude);
//            System.out.println("1: " + veiculo1.latitude + " " + veiculo1.longitude + " " + veiculo1.time);
//        }
    }



         /*       //conn.do_timestep();
         for (int i = 0; i < 3600; i++) {
             conn.do_timestep();
             //System.out.println(conn.do_job_get(Vehicle.getIDList()));
              Object o = conn.do_job_get(Vehicle.getLanePosition("veh1"));
            // Collection<Vehicle> vehicles = conn.getVehicleRepository().getAll().values();
         }
/*
            System.out.println(conn.do_job_get(Simulation.getLoadedNumber()));
            System.out.println(conn.do_job_get(Simulation.getDepartedNumber()));
            System.out.println(conn.do_job_get(Vehicle.getDistance("1")));
            System.out.println(conn.do_job_get(Vehicle.getColor("1")));
            System.out.println(conn.do_job_get(Vehicletype.getIDList()));
             System.out.println(conn.do_job_get(Simulation.getArrivedIDList()));
             //double co2 = (double) conn.do_job_get(Vehicle.getCO2Emission("11"));
               // System.out.println("CO2: " + co2 + " g/s");

               */
}


