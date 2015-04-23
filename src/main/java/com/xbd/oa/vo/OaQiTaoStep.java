package com.xbd.oa.vo;
import java.util.Date;

/**
 * Created by cengdecheng on 15-4-22.
 */
public class OaQiTaoStep {

    private static final long serialVersionUID = 1L;

    private Integer oaOrderId;			//订单ID
    private Date qitaoReceiveTime;		//齐套接收时间
    private Date qitaoSendTime;			//齐套外发时间
    private Date qitaoTiHuoTime;        //齐套可提货时间
    private Date qitaoIsBackTime;       //齐套退回时间


    public Integer getOaOrderId() {
        return oaOrderId;
    }

    public void setOaOrderId(Integer oaOrderId) {
        this.oaOrderId = oaOrderId;
    }

    public Date getQitaoReceiveTime() {
        return qitaoReceiveTime;
    }

    public void setQitaoReceiveTime(Date qitaoReceiveTime) {
        this.qitaoReceiveTime = qitaoReceiveTime;
    }

    public Date getQitaoSendTime() {
        return qitaoSendTime;
    }

    public void setQitaoSendTime(Date qitaoSendTime) {
        this.qitaoSendTime = qitaoSendTime;
    }

    public Date getQitaoTiHuoTime() {
        return qitaoTiHuoTime;
    }

    public void setQitaoTiHuoTime(Date qitaoTiHuoTime) {
        this.qitaoTiHuoTime = qitaoTiHuoTime;
    }

    public Date getQitaoIsBackTime() {
        return qitaoIsBackTime;
    }

    public void setQitaoIsBackTime(Date qitaoIsBackTime) {
        this.qitaoIsBackTime = qitaoIsBackTime;
    }


}
