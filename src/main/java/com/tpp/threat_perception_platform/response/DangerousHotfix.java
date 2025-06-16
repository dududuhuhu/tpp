package com.tpp.threat_perception_platform.response;

public class DangerousHotfix {
    private String macAddress;
    private String hotfixId;
    private String cve;
    private String score;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * 页码
     */
    private Integer page;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * 限制
     */
    private Integer limit;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getHotfixId() {
        return hotfixId;
    }

    public void setHotfixId(String hotfixId) {
        this.hotfixId = hotfixId;
    }

    public String getCve() {
        return cve;
    }

    public void setCve(String cve) {
        this.cve = cve;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "DangerousHotfix{" +
                ", macAddress='" + macAddress + '\'' +
                ", hotfixId='" + hotfixId + '\'' +
                ", cve='" + cve + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
