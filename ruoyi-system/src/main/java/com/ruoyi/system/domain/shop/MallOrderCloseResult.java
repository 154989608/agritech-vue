package com.ruoyi.system.domain.shop;

/** 超时订单批次执行统计。 */
public class MallOrderCloseResult
{
    private final int scannedCount;
    private final int successCount;
    private final int failureCount;

    public MallOrderCloseResult(int scannedCount, int successCount, int failureCount)
    {
        this.scannedCount = scannedCount;
        this.successCount = successCount;
        this.failureCount = failureCount;
    }

    public int getScannedCount() { return scannedCount; }
    public int getSuccessCount() { return successCount; }
    public int getFailureCount() { return failureCount; }
}
