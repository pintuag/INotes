package com.inotes;


public class Globalfunctions {

    public static boolean prescriptionpagelayoutback = false;
    public static boolean referralMandatory = true;
    public static boolean inNavigationActivity = false;
    public static boolean isItemAdded = false;

    public static void setPresViewIndex(int presViewIndex) {
        Globalfunctions.presViewIndex = presViewIndex;
    }

    public static int presViewIndex = 0;


    public static void setSetdeliverylocation(boolean setdeliverylocation) {
        Globalfunctions.setdeliverylocation = setdeliverylocation;
    }

    public static boolean setdeliverylocation = true;

    public static void setPrescriptionpagelayoutback(boolean prescriptionpagelayoutback) {
        Globalfunctions.prescriptionpagelayoutback = prescriptionpagelayoutback;
    }

    public static void setFragment_index(int fragment_index) {
        Globalfunctions.fragment_index = fragment_index;
    }

    public static int fragment_index = 0;
}
