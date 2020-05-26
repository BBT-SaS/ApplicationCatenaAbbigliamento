/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.net.InetAddress;

/**
 *
 * @author Layt
 */
public class Validator extends Thread {

    private ClientInfoValidator validator;
    private String client;
    private InetAddress ip;
    public int result = -1;

    public Validator(String s, InetAddress ip, ClientInfoValidator civ) {
        super("Validator Socket ");
        validator = civ;
        client = s;
        this.ip = ip;
    }

    @Override
    public void run() {
        String[] sections = client.split("\\$SEP\\$");

//        DEBUG
//        System.out.println(sections.length);
//        
//        for(int i = 0; i < sections.length; i++){
//            System.out.println(sections[i]+"\n");
//        }
        if (validator.findIP(ip)) {
            return;
        }

        if (validator.findNumber(sections[0])) {
            validator.addClientToBlackList(ip);
            return;
        }
        

        if (sections.length != 0) {
            if (sections.length == 3) {
                // System.out.println("Richiesta di ricerca");
                
                result = 0;
            } else {
                // System.out.println("Richiesta di prenotazione/inserimento/elimina");
                result = 0;
            }
        } else {
            result = 1;
        }
    }

}
