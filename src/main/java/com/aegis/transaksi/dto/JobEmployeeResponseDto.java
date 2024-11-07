package com.aegis.transaksi.dto;

import com.aegis.transaksi.entity.Job;

public class JobEmployeeResponseDto {

    private String jobName;
    private boolean isFinished;

    public JobEmployeeResponseDto(String jobName, boolean isFinished) {
        this.jobName = jobName;
        this.isFinished = isFinished;
    }

    public static JobEmployeeResponseDto convert(Job job){
        return new JobEmployeeResponseDto(job.getJobName(), job.isFinished());
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}

