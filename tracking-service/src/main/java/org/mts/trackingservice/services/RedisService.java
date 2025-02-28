package org.mts.trackingservice.services;

import org.mts.trackingservice.documents.TrackingEventDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RedisService implements IRedisService{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * @param adId
     * @return
     */
    @Override
    public List<TrackingEventDocument> getTrackingEvents(String adId) {

        return Objects.requireNonNull(this.redisTemplate.opsForList().range("tracking:"+adId, 0, -1)).stream().map(o -> (TrackingEventDocument)o).toList();
    }

    /**
     * @return
     */
    @Override
    public  Map<String,List<TrackingEventDocument>> getAllTrackingEvents() {

        List<String> keys = this.redisTemplate.keys("tracking:*").stream().toList();
        Map<String,List<TrackingEventDocument>> list = new HashMap<>();
        if (!keys.isEmpty()){
            for (String key: keys){
                List<TrackingEventDocument> tmp = Objects.requireNonNull(this.redisTemplate.opsForList().range(key, 0, -1)).stream().map(o ->(TrackingEventDocument)o ).toList();
                list.put(key.substring(9), tmp);
            }
        }

        if (!keys.isEmpty()){
                this.redisTemplate.delete(keys);
        }

        return list;
    }

    /**
     * @param event
     */
    @Override
    public void enqueueTrackingEvent(TrackingEventDocument event) {
        this.redisTemplate.opsForList().rightPush("tracking:"+event.getAdId(), event);
    }
}
