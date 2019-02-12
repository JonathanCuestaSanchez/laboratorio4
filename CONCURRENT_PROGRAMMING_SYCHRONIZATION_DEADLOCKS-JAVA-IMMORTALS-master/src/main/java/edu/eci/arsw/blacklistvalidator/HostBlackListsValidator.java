/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {
 private static final LinkedList<Integer> blackListOcurrences = new LinkedList<>();
    private static final int BLACK_LIST_ALARM_COUNT = 5;
    public final int ocurrencesCount = 0;
    /**
     * Check the given host's IP address in all the available black lists, and
     * report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case. The
     * search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as NOT
     * Trustworthy, and the list of the five blacklists returned.
     *
     * @param ipaddress suspicious host's IP address.
     * @return Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int a) {

        
        ArrayList<BlacklistThread> thread = new ArrayList<BlacklistThread>();
        

        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();

        if (a % 2 == 0) {
            int flag = 0;
            int x = skds.getRegisteredServersCount() / a;
            for (int i = 0; i < a; i++) {
                flag += x;
                BlacklistThread hilo = new BlacklistThread(flag - x, flag, ipaddress, skds);

                thread.add(hilo);

                hilo.start();
            }
        } else {
            int flag = 0;
            int x = (int) skds.getRegisteredServersCount() / a;
            for (int i = 0; i < a; i++) {

                flag += x;
                if (flag > skds.getRegisteredServersCount()) {
                    flag = skds.getRegisteredServersCount();
                }
                BlacklistThread hilo = new BlacklistThread(flag - x, flag, ipaddress, skds);

                thread.add(hilo);

                hilo.start();
                
            }
        }
        boolean flag=true;
        
        while(flag){
            System.out.println(blackListOcurrences.size() );
            if (blackListOcurrences.size() == BLACK_LIST_ALARM_COUNT){
                flag=false;
                
            }
         
        }
        for (BlacklistThread i: thread){
            i.stop();
        }
        
        if (ocurrencesCount >= BLACK_LIST_ALARM_COUNT) {
            skds.reportAsNotTrustworthy(ipaddress);
        } else {
            skds.reportAsTrustworthy(ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{ocurrencesCount, skds.getRegisteredServersCount()});

        return blackListOcurrences;
    }

    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());

    public static LinkedList<Integer> getBlackListOcurrences() {
        return blackListOcurrences;
    }

}
