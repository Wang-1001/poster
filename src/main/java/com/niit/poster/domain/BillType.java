package com.niit.poster.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 海报分类表
 */
@ApiModel(description = "海报分类表")
@Entity
@Table(name = "bill_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BillType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    @Column(name = "bill_type_name")
    private String billTypeName;

    /**
     * 分类排序
     */
    @ApiModelProperty(value = "分类排序")
    @Column(name = "bill_type_sort")
    private Integer billTypeSort;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "data_time")
    private ZonedDateTime dataTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillTypeName() {
        return billTypeName;
    }

    public BillType billTypeName(String billTypeName) {
        this.billTypeName = billTypeName;
        return this;
    }

    public void setBillTypeName(String billTypeName) {
        this.billTypeName = billTypeName;
    }

    public Integer getBillTypeSort() {
        return billTypeSort;
    }

    public BillType billTypeSort(Integer billTypeSort) {
        this.billTypeSort = billTypeSort;
        return this;
    }

    public void setBillTypeSort(Integer billTypeSort) {
        this.billTypeSort = billTypeSort;
    }

    public ZonedDateTime getDataTime() {
        return dataTime;
    }

    public BillType dataTime(ZonedDateTime dataTime) {
        this.dataTime = dataTime;
        return this;
    }

    public void setDataTime(ZonedDateTime dataTime) {
        this.dataTime = dataTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillType)) {
            return false;
        }
        return id != null && id.equals(((BillType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillType{" +
            "id=" + getId() +
            ", billTypeName='" + getBillTypeName() + "'" +
            ", billTypeSort=" + getBillTypeSort() +
            ", dataTime='" + getDataTime() + "'" +
            "}";
    }
}
