/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import server.StoreServer;

/**
 *
 * @author Admin
 */
public class AgentUtil {
    public static boolean isAgentOfClient(AMSAgentDescription desc) {
        String agentName = desc.getName().getLocalName();
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(agentName.split("-")));
        return arr.get(arr.size() - 1).equals("client");
    }
    
    public static boolean isAgentOfServer(AMSAgentDescription desc) {
        String agentName = desc.getName().getLocalName();
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(agentName.split("-")));
        return arr.get(arr.size() - 1).equals("server");
    }
    
    public static String getIPAddressFromName(String name) {
        // NOTE: AgentName@IP:Port
        int index1 = name.indexOf('@');
        int index2 = name.indexOf(':');
        return name.substring(index1 + 1, index2);
    }
    
    public static Map<String, String> getDetailOfAgent(AMSAgentDescription desc) {
        Map<String, String> map = new HashMap<>();
        String[] addresses = desc.getName().getAddressesArray();
        
        map.put("id", desc.getName().getName());
        map.put("name", desc.getName().getLocalName());
        map.put("status", desc.getState());
        map.put("position", (addresses != null && addresses.length > 0) ? addresses[0] : "");
        map.put("ip", getIPAddressFromName(map.get("id")));
        return map;
    }
    
    public static String getAgentNameWithoutPostfix(AMSAgentDescription desc) {
        String agentName = desc.getName().getLocalName();
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(agentName.split("-")));
        arr.remove(arr.size() - 1);
        String name = "";
        name = arr.stream().map((str) -> Common.capitalize(str)).reduce(name, String::concat);
        return name;
    }
    
    public static void createAgent(AgentContainer agentContainer, String agentNickName, String agentClassName, Object[] params) throws StaleProxyException {
        agentContainer.createNewAgent(
            agentNickName,
            agentClassName,
            params
        ).start();
    }
}
