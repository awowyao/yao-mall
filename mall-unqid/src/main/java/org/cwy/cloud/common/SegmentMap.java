package org.cwy.cloud.common;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SegmentMap {
    Integer b = 2;
    public Integer getB() {
        return this.b++;
    }
    ConcurrentHashMap<Integer, SegmentBuffer> SegmentMap = new ConcurrentHashMap<Integer, SegmentBuffer>();
    public int setSegmentBuffer(Integer tag, SegmentBuffer seg){
        SegmentMap.put(tag, seg);
        return 1;
    }
    public SegmentBuffer getSegment(Integer tag){
        return SegmentMap.get(tag);
    }

}
