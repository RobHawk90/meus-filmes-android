package robhawk.com.br.meusfilmes.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import robhawk.com.br.meusfilmes.R;

/**
 * Created by Robert on 05/11/2017.
 * Utilitario para exibir notificacoes para o usuario.
 */

public class Toaster {

    private static Toast createCustom(Context context, String text, int drawableResId) {
        LayoutInflater inflater = LayoutInflater.from(context);

        @SuppressLint("InflateParams")
        View toastView = inflater.inflate(R.layout.toast_custom, null);
        toastView.setBackgroundResource(drawableResId);
        TextView textView = toastView.findViewById(R.id.toast_custom_text);
        textView.setText(text);

        Toast toast = new Toast(context);
        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setView(toastView);
        return toast;
    }

    public static void warning(Context context, String text) {
        Toast toast = createCustom(context, text, R.drawable.toast_warning);
        toast.show();
    }

    public static void danger(Context context, String text) {
        Toast toast = createCustom(context, text, R.drawable.toast_danger);
        toast.show();
    }

    public static void success(Context context, String text) {
        Toast toast = createCustom(context, text, R.drawable.toast_success);
        toast.show();
    }

}