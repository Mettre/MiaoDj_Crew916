package cn.chenhai.miaodj_monitor.model.info;

import cn.chenhai.miaodj_monitor.commonlib.utils.StringUtils;

/**
 * Created by ChenHai--霜华 on 2016/6/27. 11:48
 * 邮箱：248866527@qq.com
 */
public class Material_auxiliary_Info {

    private String img_portraitPath;
    private String auxiliaryNameDes;
    private String auxiliarySpecs;
    private String auxiliaryBrand;
    private String auxiliarySpecs2;
    private String auxiliaryCount;
    private String auxiliaryCountUnit;
//    private String CbAuxiliaryStatus;
//    private String TvAuxiliaryStatus;
    private String status;
    private String materialCode;
    private String deliverCode;
    private boolean ifChecked =false;


    public String getDeliverCode() {
        return deliverCode;
    }
    public void setDeliverCode(String deliverCode) {
        this.deliverCode = deliverCode;
    }

    public boolean isIfChecked() {
        return ifChecked;
    }
    public void setIfChecked(boolean ifChecked) {
        this.ifChecked = ifChecked;
    }

    public String getImg_portraitPath() {
        return img_portraitPath;
    }
    public void setImg_portraitPath(String img_portraitPath) {
        this.img_portraitPath = img_portraitPath;
    }

    public String getAuxiliaryNameDes() {
        return auxiliaryNameDes;
    }
    public void setAuxiliaryNameDes(String auxiliaryNameDes) {
        this.auxiliaryNameDes = auxiliaryNameDes;
    }

    public String getAuxiliarySpecs() {
        return auxiliarySpecs;
    }
    public void setAuxiliarySpecs(String auxiliarySpecs) {
        this.auxiliarySpecs = auxiliarySpecs;
    }

    public String getAuxiliaryBrand() {
        return auxiliaryBrand;
    }
    public void setAuxiliaryBrand(String auxiliaryBrand) {
        this.auxiliaryBrand = auxiliaryBrand;
    }

    public String getAuxiliarySpecs2() {
        return auxiliarySpecs2;
    }
    public void setAuxiliarySpecs2(String auxiliarySpecs2) {
        this.auxiliarySpecs2 = auxiliarySpecs2;
    }

    public String getAuxiliaryCount() {
        return auxiliaryCount;
    }
    public void setAuxiliaryCount(String auxiliaryCount) {
        this.auxiliaryCount = auxiliaryCount;
    }

    public String getAuxiliaryCountUnit() {
        return auxiliaryCountUnit;
    }
    public void setAuxiliaryCountUnit(String auxiliaryCountUnit) {
        this.auxiliaryCountUnit = auxiliaryCountUnit;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getMaterialCode() {
        return materialCode;
    }
    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }
}
