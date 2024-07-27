package org.cwy.cloud.sevise.Iml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.cwy.cloud.DAO.uniqidDAO;
import org.cwy.cloud.common.Segment;
import org.cwy.cloud.common.SegmentBuffer;
import org.cwy.cloud.common.SegmentMap;
import org.cwy.cloud.mapper.uniqidMapper;
import org.cwy.cloud.sevise.segmentSevice;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class segmentSeviceIml implements segmentSevice {
    @Resource
    private SegmentMap SegmentMap;
    @Resource
    private uniqidMapper uniqidmapper;

    ThreadPoolExecutor executor = new ThreadPoolExecutor(2,20,20,TimeUnit.MINUTES, new ArrayBlockingQueue<>(2));
    public int SegmentInfo(Integer tag, Integer BufferIndex) {
        SegmentBuffer segmentBuffer = SegmentMap.getSegment(tag);
        if (segmentBuffer == null){
            updateSegment(tag, 0, 0);

        }
        LambdaQueryWrapper<uniqidDAO> Weapper = new LambdaQueryWrapper<>();
        Weapper.eq(uniqidDAO::getBizTag, tag);
        uniqidDAO uniqid = uniqidmapper.selectOne(Weapper);
        if (uniqid==null){
            return 0;
        }

//        System.out.println(uniqid);

        Segment segment = new Segment();
        segment.setInfo(true);
        segment.setMax(uniqid.getMaxId());
        segment.setStep(uniqid.getStep());
        segment.setTag(tag);
        segment.setIndex(0);
        if (segmentBuffer == null){
            segmentBuffer = new SegmentBuffer();
            segmentBuffer.setInfo(true);
            segmentBuffer.addSegment(segment);
            segmentBuffer.addSegment(new Segment());
            SegmentMap.setSegmentBuffer(tag, segmentBuffer);
        }else {
            segmentBuffer.setInfo(true);
            segmentBuffer.setSegment(BufferIndex, segment);
        }


        return 1;
    }

    @Override
    public synchronized int getId(Integer tag) throws InterruptedException {

        SegmentBuffer segmentBuffer = SegmentMap.getSegment(tag);
//        TimeUnit.SECONDS.sleep(10);
        if (segmentBuffer==null || !segmentBuffer.getInfo()){
            if (SegmentInfo(tag, 0)==0){
                return 0;
            }
            segmentBuffer = SegmentMap.getSegment(tag);
        }
        Segment segment = segmentBuffer.getSegment();

//        synchronized(this){
            int id =  segment.getId();
            if (id< segment.getMax()){
//                segment.addIndex();
                Double proportion = Double.valueOf(segment.getIndex())/(segment.getMax()-segment.getStep());
//            System.out.println(segmentBuffer.getSegmentList());
                if (proportion >= 0.5 && !segmentBuffer.getTBufferLock() && !segmentBuffer.getTBuffer() ){
                    segmentBuffer.setTBufferLock(true);
//                    System.out.println(segmentBuffer.getTBufferLock());

                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            SegmentBuffer finalSegmentBuffer = SegmentMap.getSegment(tag);
                            updateSegment(tag, 1, finalSegmentBuffer.getExchangeIndex());
                            finalSegmentBuffer.setTBufferLock(false);
                            finalSegmentBuffer.setTBuffer(true);
                        }
                    });
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        updateSegment(tag, 1, finalSegmentBuffer.getExchangeIndex());
//                    }
//                });
//                thread.start();

                }
                return id;
            }else {
                segment.setInfo(false);


                while (!SegmentMap.getSegment(tag).getTBuffer()){
                    TimeUnit.MICROSECONDS.sleep(100);
                    System.out.println(123);
                }
//                if (segmentBuffer.getTBuffer()){
//
//                    TimeUnit.MICROSECONDS.sleep(10000);
//                }
                segmentBuffer.exchange();
                segmentBuffer.setTBuffer(false);
//                else
//                updateSegment(tag, 1, 0);
                return getId(tag);
            }
//        }

    }

    public void updateSegment(Integer tag, Integer t, Integer SegmentInfo) {

        LambdaQueryWrapper<uniqidDAO> Weapper = new LambdaQueryWrapper<>();
        Weapper.eq(uniqidDAO::getBizTag, tag);
        uniqidDAO uniqid = uniqidmapper.selectOne(Weapper);
        uniqid.setStep(uniqid.getMaxId());
        uniqid.setMaxId(uniqid.getMaxId()+ 2000 * SegmentMap.getB());
        uniqidmapper.update(uniqid, Weapper);
        if (t == 1){
            SegmentInfo(tag, SegmentInfo);
        }
    }

}
