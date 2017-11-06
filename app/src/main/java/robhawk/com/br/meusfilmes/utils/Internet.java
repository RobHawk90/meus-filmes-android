package robhawk.com.br.meusfilmes.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Robert on 05/11/2017.
 * Acessa as informacoes de conexao do dispositivo para verificar se ele esta conectado a Internet.
 */

public class Internet {

    private static boolean isOnline(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) return false;
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static boolean isOffline(Context context) {
        return !isOnline(context);
    }
}
