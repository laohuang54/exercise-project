package com.fth.utils;

public class UserHolder {
    private static final ThreadLocal<Integer> tl = new ThreadLocal<>();

    public  static Integer getUserId(){
        return tl.get();
    }
    public static void saveUser(Integer userId){
        tl.set(userId);
    }

    public  static void removeUser(){
        tl.remove();
    }
}
