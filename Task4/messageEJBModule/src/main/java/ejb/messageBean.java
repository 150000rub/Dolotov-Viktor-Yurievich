package ejb;

import api.messageRemote;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.LinkedHashMap;

import static jdk.nashorn.internal.runtime.JSType.isNumber;

/**
 * Created by Ti_g_programmist(no) on 02.12.2016.
 */
@Remote(messageRemote.class)
@Stateless
public class messageBean implements messageRemote {
    @Override
    public LinkedHashMap<String,Integer> calcSymb(String mess) {
        LinkedHashMap<String,Integer> table = new LinkedHashMap<>();
        for (int i=0;i<mess.length();i++) {
            Object j = table.get(String.valueOf(mess.charAt(i)));
            if (isNumber(j)) {
                table.put(String.valueOf(mess.charAt(i)),(int)j + 1);
            } else {
                table.put(String.valueOf(mess.charAt(i)),1);
            }
        }
        return table;
    }
}
