package entity;

import net.sf.json.JSONObject;

/**
 * Created by Melody on 2017/7/29.
 */
public class ServiceResult {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private  int code;
    private String messsage;
    private Object data;

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
