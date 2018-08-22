package com.yonyou.cloud.ops.alert.ops.alert.domain.dto;

import java.util.List;

/**
 * @author joy
 */
public class GroupUsers {
    private Integer id;
    private String code;
	private String name;
	List<UserBo> members ;
    List<UserBo> leaders;
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public GroupUsers() {
    }

    public GroupUsers(List<UserBo> members, List<UserBo> leaders,int id,String code,String name) {
        this.members = members;
        this.leaders = leaders;
        this.id=id;
        this.name=name;
        this.code=code;
    }

    public List<UserBo> getMembers() {
        return members;
    }

    public void setMembers(List<UserBo> members) {
        this.members = members;
    }

    public List<UserBo> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<UserBo> leaders) {
        this.leaders = leaders;
    }
}
