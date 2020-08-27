package com.peixinchen.model;

import java.util.Objects;

public class Section {
    public int sid;
    public String name;
    // 如果关联声音，uuid 就是保存声音的 uuid
    // 否则，uuid == null
    public String uuid;

    public Section() {
    }

    public Section(int sid, String uuid, String name) {
        this.sid = sid;
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Section{" +
                "uuid='" + uuid + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}
