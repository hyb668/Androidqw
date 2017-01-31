package view;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.common.R;

/**
 *   界面的进度条
 * Created by liuyuzhe on 2017/1/30.
 */

public class LoadingDialog {

    private static Dialog dialog;

    /**
     *  指定进度条的内容
     * @param activity
     * @param msg
     * @param cancelable
     */
    public static Dialog showDialogForLoading(Activity activity , String msg , boolean cancelable) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_loading, null);
        TextView  textView = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
        textView.setText(msg);

        dialog = new Dialog(activity, R.style.CustomProgressDialog);



        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT
        );
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(cancelable);
        dialog.addContentView(view,params);
        dialog.show();

        return dialog;

    }

    public static Dialog showDialogForLoading(Activity activity){
       return   showDialogForLoading(activity,"加载中....",true);
    }

    /**
     *  关闭dialog
     */
    public static  void closeDialogForloading(){
        if (dialog != null) {
            dialog.cancel();
        }
    }
}
