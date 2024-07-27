package org.cwy.cloud.common;

import lombok.Data;

import java.util.ArrayList;

@Data
public class SegmentBuffer {
    private int segmentIndex = 0;
    private boolean Info = false;
    private volatile boolean TBuffer = false;
    private volatile boolean TBufferLock = false;
    private ArrayList<Segment> segmentList = new ArrayList<>();

    public int addSegment(Segment segment) {
        this.segmentList.add(segment);
        return 1;
    }
    public int setSegment(Integer index, Segment segment) {
        this.segmentList.set(index, segment);
        return 1;
    }
    public Integer GetsegmentBuffer(){
        return this.segmentIndex;
    }
    public Segment getSegment() {
        return this.segmentList.get(segmentIndex);

    }
    public boolean getInfo() {
        return this.Info;
    }
    public boolean getTBuffer() {
        return this.TBuffer;
    }
    public boolean getTBufferLock() {
        return this.TBufferLock;
    }
    public void exchange() {
        if (this.segmentIndex==1)
            this.segmentIndex=0;
        else
            this.segmentIndex=1;
    }
    public Integer getExchangeIndex() {
        if (this.segmentIndex==1)
            return 0;
        else
            return 1;
    }
}
