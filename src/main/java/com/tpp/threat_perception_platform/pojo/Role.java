package com.tpp.threat_perception_platform.pojo;

/**
 * 
 * @TableName role
 */
public class Role {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String roleName;

    /**
     * 
     */
    private String roleDes;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 
     */
    public String getRoleDes() {
        return roleDes;
    }

    /**
     * 
     */
    public void setRoleDes(String roleDes) {
        this.roleDes = roleDes;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Role other = (Role) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRoleName() == null ? other.getRoleName() == null : this.getRoleName().equals(other.getRoleName()))
            && (this.getRoleDes() == null ? other.getRoleDes() == null : this.getRoleDes().equals(other.getRoleDes()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRoleName() == null) ? 0 : getRoleName().hashCode());
        result = prime * result + ((getRoleDes() == null) ? 0 : getRoleDes().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleName=").append(roleName);
        sb.append(", roleDes=").append(roleDes);
        sb.append("]");
        return sb.toString();
    }
}