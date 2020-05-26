/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Layt
 */
public class WelcomeSocket implements Runnable {

    private ServerSocket welcomeSocket;
    private ArrayList<SocketWorker> workers;
    private ClientInfoValidator clientValidator;

    public WelcomeSocket() throws IOException {
        welcomeSocket = new ServerSocket(7);
        workers = new ArrayList();
        clientValidator = new ClientInfoValidator();
    }

    @Override
    public void run() {
        Socket client = null;
        Validator validator = null;
        BufferedReader reader = null;
        PrintWriter output = null;

        while (!welcomeSocket.isClosed()) {
            try {

                client = welcomeSocket.accept();

                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                output = new PrintWriter(client.getOutputStream(), true);
                String request = reader.readLine();

                if (request != null || !request.equals("")) {
                    if (request.equals("hello")) {
                        output.println("key");
                        // StringBuilder builder = new StringBuilder();
                        do {
                            request = reader.readLine();
                        } while (request != null);

                        System.out.println(request);

                        validator = new Validator(request, client.getInetAddress(), clientValidator);

                        validator.start();
                        validator.join();

                        switch (validator.result) {
                            case -1:
                                break;
                            case 0:
                                break;
                            case 1:
                                break;
                        }

                        //TODO Logic
                    } else {
                        clientValidator.addClientToBlackList(client.getInetAddress());
                        System.out.println("BlackListed " + client.getInetAddress().toString());
                    }
                }

            } catch (IOException ex) {
                //TODO Catch I/OException
            } catch (InterruptedException ex) {
                //TODO Catch InterruptedException
            }

        }

    }

}
