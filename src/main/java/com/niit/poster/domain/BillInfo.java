package com.niit.poster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 海报信息表
 */
@ApiModel(description = "海报信息表")
@Entity
@Table(name = "bill_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BillInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户登录名
     */
    @ApiModelProperty(value = "用户登录名")
    @Column(name = "bill_user_name")
    private String billUserName;

    /**
     * 海报图片
     */
    @ApiModelProperty(value = "海报图片")
    @Column(name = "bill_pic")
    private String billPic;

    /**
     * 海报文字
     */
    @ApiModelProperty(value = "海报文字")
    @Column(name = "bill_word")
    private String billWord;

    /**
     * 海报签名
     */
    @ApiModelProperty(value = "海报签名")
    @Column(name = "bill_author")
    private String billAuthor;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    @Column(name = "bill_time")
    private ZonedDateTime billTime;

    /**
     * 布局方式
     */
    @ApiModelProperty(value = "布局方式")
    @Column(name = "bill_layout_mode")
    private String billLayoutMode;

    /**
     * 海报表--海报分类表
     */
    @ApiModelProperty(value = "海报表--海报分类表")
    @ManyToOne
    @JsonIgnoreProperties(value = "billInfos", allowSetters = true)
    private BillType billType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillUserName() {
        return billUserName;
    }

    public BillInfo billUserName(String billUserName) {
        this.billUserName = billUserName;
        return this;
    }

    public void setBillUserName(String billUserName) {
        this.billUserName = billUserName;
    }

    public String getBillPic() {
        return billPic;
    }

    public BillInfo billPic(String billPic) {
        this.billPic = billPic;
        return this;
    }

    public void setBillPic(String billPic) {
        this.billPic = billPic;
    }

    public String getBillWord() {
        return billWord;
    }

    public BillInfo billWord(String billWord) {
        this.billWord = billWord;
        return this;
    }

    public void setBillWord(String billWord) {
        this.billWord = billWord;
    }

    public String getBillAuthor() {
        return billAuthor;
    }

    public BillInfo billAuthor(String billAuthor) {
        this.billAuthor = billAuthor;
        return this;
    }

    public void setBillAuthor(String billAuthor) {
        this.billAuthor = billAuthor;
    }

    public ZonedDateTime getBillTime() {
        return billTime;
    }

    public BillInfo billTime(ZonedDateTime billTime) {
        this.billTime = billTime;
        return this;
    }

    public void setBillTime(ZonedDateTime billTime) {
        this.billTime = billTime;
    }

    public String getBillLayoutMode() {
        return billLayoutMode;
    }

    public BillInfo billLayoutMode(String billLayoutMode) {
        this.billLayoutMode = billLayoutMode;
        return this;
    }

    public void setBillLayoutMode(String billLayoutMode) {
        this.billLayoutMode = billLayoutMode;
    }

    public BillType getBillType() {
        return billType;
    }

    public BillInfo billType(BillType billType) {
        this.billType = billType;
        return this;
    }

    public void setBillType(BillType billType) {
        this.billType = billType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillInfo)) {
            return false;
        }
        return id != null && id.equals(((BillInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillInfo{" +
            "id=" + getId() +
            ", billUserName='" + getBillUserName() + "'" +
            ", billPic='" + getBillPic() + "'" +
            ", billWord='" + getBillWord() + "'" +
            ", billAuthor='" + getBillAuthor() + "'" +
            ", billTime='" + getBillTime() + "'" +
            ", billLayoutMode='" + getBillLayoutMode() + "'" +
            "}";
    }
}
