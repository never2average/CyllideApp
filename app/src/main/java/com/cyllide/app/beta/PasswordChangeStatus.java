package com.cyllide.app.beta;

public class PasswordChangeStatus {


   public static boolean rvCardstatus=false;
   public static boolean buttonstatus=false;


    static Boolean passwordChangeStatus=false;
    static void passwordChangeStatusfunc(Boolean status)
    {
        PasswordChangeStatus.passwordChangeStatus=status;


    }

    static void setRvCardstatusfunc(Boolean status)
    {
        PasswordChangeStatus.rvCardstatus=status;
    }
    static void buttonstatusfunc(Boolean status)
    {
        PasswordChangeStatus.buttonstatus=status;
    }
}
