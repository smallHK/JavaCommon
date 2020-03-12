package com.hk.jdk.chars;

public class CharacterTest {

    private static void characterBlock() {
        System.out.println(Character.UnicodeBlock.of('A'));
        System.out.println(Character.UnicodeBlock.of('我'));
    }

    public static void main(String[] args) {
        characterBlock();
    }

}
