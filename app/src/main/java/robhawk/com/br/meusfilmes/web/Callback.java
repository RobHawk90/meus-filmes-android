package robhawk.com.br.meusfilmes.web;

import android.app.Activity;

/**
 * Created by Robert on 04/11/2017.
 * Implementa o pattern Template Method para enviar dados para a thread principal.
 */

public abstract class Callback<T> {

    private Activity mActivity;

    protected Callback(Activity activity) {
        mActivity = activity;
    }

    /**
     * Este metodo deve ser invocado ao termino de qualquer execucao assincrona.
     *
     * @param result - envia resultado para a UI thread.
     */
    void success(final T result) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onSuccess(result);
            }
        });
    }

    /**
     * Este metodo deve ser implementado para receber o resultado de uma execucao assincrona.
     *
     * @param result - resultado
     */
    public abstract void onSuccess(T result);

    public abstract void onError(String error);

}
