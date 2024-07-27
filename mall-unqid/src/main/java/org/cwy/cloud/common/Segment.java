package org.cwy.cloud.common;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class Segment {
    Integer tag;
    private long max;
    private int step;
    private int index;

    private boolean info;
    public Segment(){
        this.info = false;
    }

    public boolean getInfo() {
        return this.info;
    }
    public void addIndex() {
        this.index++;
    }
    public Integer getId() {
        return (this.index++ + this.step);
//        this.index++;
    }
}
