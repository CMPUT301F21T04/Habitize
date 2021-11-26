package com.example.habitize.Controllers;

import android.content.Context;
import android.widget.Toast;

// this class is used to display toast messages. Each activity/class implements what messages
// an error code should throw
public class ErrorShower {
    private Context mContext; // The context where the error is displayed, and handling is implemented
    private ErrorHandler handler;

    public ErrorShower(Context context) {
        this.handler = (ErrorHandler) context; // the context will implement error handling
        this.mContext = context; // this is where we will display
    }

    public void throwError(int errorCode) {
        Toast.makeText(this.mContext, handler.getErrorMessage(errorCode), Toast.LENGTH_SHORT).show();
    }

    public interface ErrorHandler {
        public String getErrorMessage(int errorCode);
    }

}
