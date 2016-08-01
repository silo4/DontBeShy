package dont.be.shy.event;

/**
 * Created by zhonglz on 16/1/20.
 */
public class BaseEvent {
    public int    eventId;
    public String src;
    public String dst;
    public Object obj;
    public long   arg;

    public BaseEvent(int id){
        this.eventId = id;
    }

    public BaseEvent(int id, long arg){
        this.eventId = id;
        this.arg = arg;
    }

    public BaseEvent(int id, int arg, Object obj){
        this.eventId = id;
        this.arg = arg;
        this.obj = obj;
    }

    public BaseEvent(int id, String src, String dst){
        this.eventId = id;
        this.src = src;
        this.dst = dst;
    }

    public BaseEvent(int id, String src, String dst, Object obj){
        this.eventId = id;
        this.src = src;
        this.dst = dst;
        this.obj = obj;
    }
}
