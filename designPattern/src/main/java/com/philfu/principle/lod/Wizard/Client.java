package com.philfu.principle.lod.Wizard;

/**
 * Created by fuweiwei02 on 2017/5/26.
 */
public class Client {
    public static void main(String[] args) {
        InstallSoftware invoker = new InstallSoftware();
        invoker.installWizard(new Wizard());
    }
}
