package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

public class Balance {

   public static float balance =0;
  public   static void setBalance(float bal)
    {
        Balance.balance=bal;
    }
  public   static void addmoney(float money)
    {
        Balance.balance+=money;
    }
   public static void subtract(float money)
    {
        Balance.balance-=money;
    }
}
