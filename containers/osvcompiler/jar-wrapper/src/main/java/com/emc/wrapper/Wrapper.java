package com.emc.wrapper;

import io.osv.Main;

public class Wrapper {
    public static void main(String[] args) {
        Bootstrap.bootstrap();
        Main.main(args);
    }
}
