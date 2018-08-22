package com.yonyou.cloud.ops.service.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo.ActionType;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.appinfo.LeaseInfo;

public class ErkIstInfo{
    private  String instanceId;
    private  String appName;
    private  String appGroupName;
    private  String ipAddr;
    private  int port;
    private  int securePort;
    private  String homePageUrl;
    private  String statusPageUrl;
    private  String healthCheckUrl;
    private  String secureHealthCheckUrl;
    private  String vipAddress;
    private  String secureVipAddress;
    private String statusPageRelativeUrl;
    private String statusPageExplicitUrl;
    private String healthCheckRelativeUrl;
    private String healthCheckSecureExplicitUrl;
    private String vipAddressUnresolved;
    private String secureVipAddressUnresolved;
    private String healthCheckExplicitUrl;
    private  int countryId;
    private  boolean isSecurePortEnabled = false;
    private  boolean isUnsecurePortEnabled = true;
    private  DataCenterInfo dataCenterInfo;
    private  String hostName;
    private  InstanceStatus status = InstanceStatus.UP;
    private  InstanceStatus overriddenstatus = InstanceStatus.UNKNOWN;
    private  boolean isInstanceInfoDirty = false;
    private  LeaseInfo leaseInfo;
    private  Boolean isCoordinatingDiscoveryServer = Boolean.FALSE;
    private  Map<String, String> metadata = new ConcurrentHashMap<String, String>();
    private  Long lastUpdatedTimestamp = System.currentTimeMillis();
    private  Long lastDirtyTimestamp = System.currentTimeMillis();
    private  ActionType actionType;
    private  String asgName;
    private  String version = "unknown";
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppGroupName() {
		return appGroupName;
	}
	public void setAppGroupName(String appGroupName) {
		this.appGroupName = appGroupName;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getSecurePort() {
		return securePort;
	}
	public void setSecurePort(int securePort) {
		this.securePort = securePort;
	}
	public String getHomePageUrl() {
		return homePageUrl;
	}
	public void setHomePageUrl(String homePageUrl) {
		this.homePageUrl = homePageUrl;
	}
	public String getStatusPageUrl() {
		return statusPageUrl;
	}
	public void setStatusPageUrl(String statusPageUrl) {
		this.statusPageUrl = statusPageUrl;
	}
	public String getHealthCheckUrl() {
		return healthCheckUrl;
	}
	public void setHealthCheckUrl(String healthCheckUrl) {
		this.healthCheckUrl = healthCheckUrl;
	}
	public String getSecureHealthCheckUrl() {
		return secureHealthCheckUrl;
	}
	public void setSecureHealthCheckUrl(String secureHealthCheckUrl) {
		this.secureHealthCheckUrl = secureHealthCheckUrl;
	}
	public String getVipAddress() {
		return vipAddress;
	}
	public void setVipAddress(String vipAddress) {
		this.vipAddress = vipAddress;
	}
	public String getSecureVipAddress() {
		return secureVipAddress;
	}
	public void setSecureVipAddress(String secureVipAddress) {
		this.secureVipAddress = secureVipAddress;
	}
	public String getStatusPageRelativeUrl() {
		return statusPageRelativeUrl;
	}
	public void setStatusPageRelativeUrl(String statusPageRelativeUrl) {
		this.statusPageRelativeUrl = statusPageRelativeUrl;
	}
	public String getStatusPageExplicitUrl() {
		return statusPageExplicitUrl;
	}
	public void setStatusPageExplicitUrl(String statusPageExplicitUrl) {
		this.statusPageExplicitUrl = statusPageExplicitUrl;
	}
	public String getHealthCheckRelativeUrl() {
		return healthCheckRelativeUrl;
	}
	public void setHealthCheckRelativeUrl(String healthCheckRelativeUrl) {
		this.healthCheckRelativeUrl = healthCheckRelativeUrl;
	}
	public String getHealthCheckSecureExplicitUrl() {
		return healthCheckSecureExplicitUrl;
	}
	public void setHealthCheckSecureExplicitUrl(String healthCheckSecureExplicitUrl) {
		this.healthCheckSecureExplicitUrl = healthCheckSecureExplicitUrl;
	}
	public String getVipAddressUnresolved() {
		return vipAddressUnresolved;
	}
	public void setVipAddressUnresolved(String vipAddressUnresolved) {
		this.vipAddressUnresolved = vipAddressUnresolved;
	}
	public String getSecureVipAddressUnresolved() {
		return secureVipAddressUnresolved;
	}
	public void setSecureVipAddressUnresolved(String secureVipAddressUnresolved) {
		this.secureVipAddressUnresolved = secureVipAddressUnresolved;
	}
	public String getHealthCheckExplicitUrl() {
		return healthCheckExplicitUrl;
	}
	public void setHealthCheckExplicitUrl(String healthCheckExplicitUrl) {
		this.healthCheckExplicitUrl = healthCheckExplicitUrl;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public boolean isSecurePortEnabled() {
		return isSecurePortEnabled;
	}
	public void setSecurePortEnabled(boolean isSecurePortEnabled) {
		this.isSecurePortEnabled = isSecurePortEnabled;
	}
	public boolean isUnsecurePortEnabled() {
		return isUnsecurePortEnabled;
	}
	public void setUnsecurePortEnabled(boolean isUnsecurePortEnabled) {
		this.isUnsecurePortEnabled = isUnsecurePortEnabled;
	}
	public DataCenterInfo getDataCenterInfo() {
		return dataCenterInfo;
	}
	public void setDataCenterInfo(DataCenterInfo dataCenterInfo) {
		this.dataCenterInfo = dataCenterInfo;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public InstanceStatus getStatus() {
		return status;
	}
	public void setStatus(InstanceStatus status) {
		this.status = status;
	}
	public InstanceStatus getOverriddenstatus() {
		return overriddenstatus;
	}
	public void setOverriddenstatus(InstanceStatus overriddenstatus) {
		this.overriddenstatus = overriddenstatus;
	}
	public boolean isInstanceInfoDirty() {
		return isInstanceInfoDirty;
	}
	public void setInstanceInfoDirty(boolean isInstanceInfoDirty) {
		this.isInstanceInfoDirty = isInstanceInfoDirty;
	}
	public LeaseInfo getLeaseInfo() {
		return leaseInfo;
	}
	public void setLeaseInfo(LeaseInfo leaseInfo) {
		this.leaseInfo = leaseInfo;
	}
	public Boolean getIsCoordinatingDiscoveryServer() {
		return isCoordinatingDiscoveryServer;
	}
	public void setIsCoordinatingDiscoveryServer(Boolean isCoordinatingDiscoveryServer) {
		this.isCoordinatingDiscoveryServer = isCoordinatingDiscoveryServer;
	}
	public Map<String, String> getMetadata() {
		return metadata;
	}
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
	public Long getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}
	public void setLastUpdatedTimestamp(Long lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}
	public Long getLastDirtyTimestamp() {
		return lastDirtyTimestamp;
	}
	public void setLastDirtyTimestamp(Long lastDirtyTimestamp) {
		this.lastDirtyTimestamp = lastDirtyTimestamp;
	}
	public ActionType getActionType() {
		return actionType;
	}
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	public String getAsgName() {
		return asgName;
	}
	public void setAsgName(String asgName) {
		this.asgName = asgName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
    
}