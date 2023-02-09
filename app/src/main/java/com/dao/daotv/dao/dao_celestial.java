package com.dao.daotv.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class dao_celestial {
    @Id(autoincrement = true)
    private Long id ;
    private String dao;
    private String status;
    @Generated(hash = 1287261863)
    public dao_celestial(Long id, String dao, String status) {
        this.id = id;
        this.dao = dao;
        this.status = status;
    }
    @Generated(hash = 1904723844)
    public dao_celestial() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDao() {
        return this.dao;
    }
    public void setDao(String dao) {
        this.dao = dao;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
