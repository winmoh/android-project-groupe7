package com.example.projetandroid;

import java.util.Random;

class CodeGenerator {

    private String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    Random random = new Random();

    public String getCode(){
        StringBuilder code = new StringBuilder();
        for(int j=0; j<6 ; j++){
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }
}
