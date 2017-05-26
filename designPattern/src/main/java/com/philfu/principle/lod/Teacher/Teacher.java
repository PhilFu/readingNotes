package com.philfu.principle.lod.Teacher;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 老师让学生委员清点女生数量。Teacher应该只和GroupLeader交流
 * command方法与Girl类交流,破坏了Teacher的健壮性: 方法是类的一个行为,类竟然不知道自己的行为与其他类产生了依赖关系。
 *
 * Created by fuweiwei02 on 2017/5/26.
 */
public class Teacher {
    public void command(GroupLeader groupLeader) {
        List<Girl> listGirls = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            listGirls.add(new Girl());
        }
        groupLeader.countGirls(listGirls);
    }
}
