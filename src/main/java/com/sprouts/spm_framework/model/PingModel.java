package com.sprouts.spm_framework.model;

public class PingModel {

    private int id;
    private String monitorName;
    private String ip;
    private int pack_loss;
    private int pack_receive;
    private int pack_send;
    private float loss_rate;
    private float success_rate;
    private int status;
    private float average_res_time;
    private String timestamp;
    private long timesec;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPack_loss() {
        return pack_loss;
    }

    public void setPack_loss(int pack_loss) {
        this.pack_loss = pack_loss;
    }

    public int getPack_receive() {
        return pack_receive;
    }

    public void setPack_receive(int pack_receive) {
        this.pack_receive = pack_receive;
    }

    public int getPack_send() {
        return pack_send;
    }

    public void setPack_send(int pack_send) {
        this.pack_send = pack_send;
    }

    public float getLoss_rate() {
        return loss_rate;
    }

    public void setLoss_rate(float loss_rate) {
        this.loss_rate = loss_rate;
    }

    public float getSuccess_rate() {
        return success_rate;
    }

    public void setSuccess_rate(float success_rate) {
        this.success_rate = success_rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getAverage_res_time() {
        return average_res_time;
    }

    public void setAverage_res_time(float average_res_time) {
        this.average_res_time = average_res_time;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimesec() {
        return timesec;
    }

    public void setTimesec(long timesec) {
        this.timesec = timesec;
    }


}
