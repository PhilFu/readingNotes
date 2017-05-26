package com.philfu.principle.lod.Teacher;

/**
 * Created by fuweiwei02 on 2017/5/26.
 */
public class Client {
    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        teacher.command(new GroupLeader());
    }
}
