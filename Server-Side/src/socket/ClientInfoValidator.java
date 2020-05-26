/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author Layt
 */
public class ClientInfoValidator {

    private final ArrayList<String> randomNumbers;
    private final ArrayList<InetAddress> blacklist;

    public ClientInfoValidator() {
        randomNumbers = new ArrayList();
        blacklist = new ArrayList();
    }

    public boolean findNumber(String number) {
        for (String n : randomNumbers) {
            if (n.equals(randomNumbers)) {
                return true;
            }
        }
        return false;
    }

    public boolean findIP(InetAddress ip) {
        for (InetAddress i : blacklist) {
            if (i.equals(ip)) {
                return true;
            }
        }
        return false;
    }

    public void addClientToBlackList(InetAddress ip) {
        blacklist.add(ip);
    }

    public void addNumber(String number) {
        randomNumbers.add(number);
    }
}
