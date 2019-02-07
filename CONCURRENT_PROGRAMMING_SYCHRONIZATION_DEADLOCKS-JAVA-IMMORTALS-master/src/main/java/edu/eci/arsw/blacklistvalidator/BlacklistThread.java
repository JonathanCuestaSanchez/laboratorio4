/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jonathan
 */
public class BlacklistThread extends Thread {
    
    LinkedList<Integer> blackListOcurrences=HostBlackListsValidator.getBlackListOcurrences();
    int checkedListsCount=0;
    private int ocurrencesCount=0;
    private static final int BLACK_LIST_ALARM_COUNT=5;
    private int a, b;
    private String ip;
  
    private HostBlacklistsDataSourceFacade skds;

    public BlacklistThread(int a, int b, String ip,HostBlacklistsDataSourceFacade skds) {
        this.a = a;
        this.b = b;
        this.ip = ip;
        this.skds=skds;
       
    }

    public void run() {
        
        for (int i=a;i<b && blackListOcurrences.size()<BLACK_LIST_ALARM_COUNT;i++){
            
            checkedListsCount++;
            
            if (skds.isInBlackListServer(i, ip)){
                
                blackListOcurrences.add(i);
                
                ocurrencesCount++;
            }
        }
    }

    public int getMaliciusServers() {

        return blackListOcurrences.size();

    }

}
