/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import jade.core.Location;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.wrapper.AgentContainer;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class StoreServer {
    public static AgentContainer mainContainer;
    public static String pathImages;
    public static String serverIP;
    public static int port;
    public static AMSAgentDescription[] listAgents;
    public static ArrayList<Location> listLocations = new ArrayList<>();
    public static String workstationName;
    public static String workstationIP;
    public static String workstationOS;
    public static String workstationArchitecture;
    public static String workstationVersion;
}
