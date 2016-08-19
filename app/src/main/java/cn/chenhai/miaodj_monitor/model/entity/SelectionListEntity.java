package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/12. 10:37
 * 邮箱：248866527@qq.com
 */
public class SelectionListEntity {


    /**
     * keep_msg :
     * order_auxiliary : {}
     * auxiliary : [{}]
     * materials : []
     * */

    private String keep_msg;
    /**
     * id : 16
     * code_pre : 32050120160805
     * code : CM320501201608050001
     * serial_no : 1
     * user_code : U32050120160009
     * store_code : S320501
     * customer_code : CA201608000010
     * house_code : HCA201608000010001
     * customer_name : 永醉
     * telephone : 18962101268
     * building_area : 0.00
     * toilet_cnt : 1
     * ceramic_area : 0.00
     * floor_area : 0.00
     * back_area : 0.00
     * package_code : null
     * receiver_name : 收货人
     * receiver_telephone : 13843838438
     * expect_date : 2016-08-25
     * province_code : 460000
     * city_code : 460300
     * area_code : 460322
     * address : 南沙一号
     * createtime : 2016-08-05 18:05:59
     * updatetime : 2016-08-05 20:07:48
     * status : 5
     * disagree_reason : null
     * is_choose_kitchen_toilet : N
     * is_choose_heart : N
     * customer_price : null
     * customer_memo : null
     * assistant_status : 3
     */

    private OrderAuxiliaryBean order_auxiliary;
    /**
     * id : 547
     * title : 落水器
     * brand_code : 112
     * class_name : 主材配件
     * code : F1267
     * specs :
     * specs2 :
     * model : JD-115-2
     * unit : 个
     * status : 3
     * memo :
     * createtime : null
     * updatetime : null
     * is_delete : N
     * brand_name : 吉帝
     * amount : 15
     * thumb_image : /Public/Img/logo_default.png
     * images : []
     */

    private List<AuxiliaryBean> auxiliary;
    /**
     * order_code : CM320501201608050001
     * choose_status : 1
     * id : 2268
     * title : 大自然实木复合地板
     * brand_code : 102
     * category_code : 11
     * image_count : null
     * class_code : 106
     * type : STANDARD
     * code : 1110610210003
     * serial_no : 10003
     * specs : 1210*168*15
     * quality : null
     * model : 橡木套白色DF9016
     * color_no : null
     * color : null
     * min_order_unit : ㎡
     * cost_unit : ㎡
     * min_delivery_unit : ㎡
     * min_purchase_unit : ㎡
     * min_cost_count : null
     * min_dilivery_count : null
     * min_purchase_count : null
     * volume_weight : null
     * unit_weight :
     * unit_volume : null
     * is_wood_floor : N
     * safe_stock : null
     * area : null
     * length : 1210.00
     * width : 168.00
     * height : 15.00
     * volume : 3049.20
     * weight : 0.00
     * is_example : N
     * min_stock_unit : null
     * order_stock_ratio : null
     * status : 1
     * memo :
     * createtime : null
     * updatetime : null
     * is_delete : N
     * isset_gift : N
     * tail_no : null
     * min_count : 1
     * extra_nature : null
     * synchro_image : 0
     * is_personal : null
     * is_single : 1
     * category_name : 地板
     * class_name : 地板
     * brand_name : 大自然
     * space_name : 卧室
     * space_id : 3
     * images : [{"material_code":"1110610210003","path":"/Uploads/materials/201607/source/57899a507ab32.jpg","createtime":"2016-07-16 10:22:15","status":null}]
     * thumb_image : /Uploads/materials/201607/source/thumb_57899a507ab32.jpg
     */

    private List<MaterialsBean> materials;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public OrderAuxiliaryBean getOrder_auxiliary() {
        return order_auxiliary;
    }
    public void setOrder_auxiliary(OrderAuxiliaryBean order_auxiliary) {
        this.order_auxiliary = order_auxiliary;
    }

    public List<AuxiliaryBean> getAuxiliary() {
        return auxiliary;
    }
    public void setAuxiliary(List<AuxiliaryBean> auxiliary) {
        this.auxiliary = auxiliary;
    }

    public List<MaterialsBean> getMaterials() {
        return materials;
    }
    public void setMaterials(List<MaterialsBean> materials) {
        this.materials = materials;
    }

    public static class OrderAuxiliaryBean {
        private String id;
        private String code_pre;
        private String code;
        private String serial_no;
        private String user_code;
        private String store_code;
        private String customer_code;
        private String house_code;
        private String customer_name;
        private String telephone;
        private String building_area;
        private String toilet_cnt;
        private String ceramic_area;
        private String floor_area;
        private String back_area;
        private String package_code;
        private String receiver_name;
        private String receiver_telephone;
        private String expect_date;
        private String province_code;
        private String city_code;
        private String area_code;
        private String address;
        private String createtime;
        private String updatetime;
        private String status;
        private String disagree_reason;
        private String is_choose_kitchen_toilet;
        private String is_choose_heart;
        private String customer_price;
        private String customer_memo;
        private String assistant_status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode_pre() {
            return code_pre;
        }

        public void setCode_pre(String code_pre) {
            this.code_pre = code_pre;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSerial_no() {
            return serial_no;
        }

        public void setSerial_no(String serial_no) {
            this.serial_no = serial_no;
        }

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public String getStore_code() {
            return store_code;
        }

        public void setStore_code(String store_code) {
            this.store_code = store_code;
        }

        public String getCustomer_code() {
            return customer_code;
        }

        public void setCustomer_code(String customer_code) {
            this.customer_code = customer_code;
        }

        public String getHouse_code() {
            return house_code;
        }

        public void setHouse_code(String house_code) {
            this.house_code = house_code;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getBuilding_area() {
            return building_area;
        }

        public void setBuilding_area(String building_area) {
            this.building_area = building_area;
        }

        public String getToilet_cnt() {
            return toilet_cnt;
        }

        public void setToilet_cnt(String toilet_cnt) {
            this.toilet_cnt = toilet_cnt;
        }

        public String getCeramic_area() {
            return ceramic_area;
        }

        public void setCeramic_area(String ceramic_area) {
            this.ceramic_area = ceramic_area;
        }

        public String getFloor_area() {
            return floor_area;
        }

        public void setFloor_area(String floor_area) {
            this.floor_area = floor_area;
        }

        public String getBack_area() {
            return back_area;
        }

        public void setBack_area(String back_area) {
            this.back_area = back_area;
        }

        public String getPackage_code() {
            return package_code;
        }

        public void setPackage_code(String package_code) {
            this.package_code = package_code;
        }

        public String getReceiver_name() {
            return receiver_name;
        }

        public void setReceiver_name(String receiver_name) {
            this.receiver_name = receiver_name;
        }

        public String getReceiver_telephone() {
            return receiver_telephone;
        }

        public void setReceiver_telephone(String receiver_telephone) {
            this.receiver_telephone = receiver_telephone;
        }

        public String getExpect_date() {
            return expect_date;
        }

        public void setExpect_date(String expect_date) {
            this.expect_date = expect_date;
        }

        public String getProvince_code() {
            return province_code;
        }

        public void setProvince_code(String province_code) {
            this.province_code = province_code;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDisagree_reason() {
            return disagree_reason;
        }

        public void setDisagree_reason(String disagree_reason) {
            this.disagree_reason = disagree_reason;
        }

        public String getIs_choose_kitchen_toilet() {
            return is_choose_kitchen_toilet;
        }

        public void setIs_choose_kitchen_toilet(String is_choose_kitchen_toilet) {
            this.is_choose_kitchen_toilet = is_choose_kitchen_toilet;
        }

        public String getIs_choose_heart() {
            return is_choose_heart;
        }

        public void setIs_choose_heart(String is_choose_heart) {
            this.is_choose_heart = is_choose_heart;
        }

        public String getCustomer_price() {
            return customer_price;
        }

        public void setCustomer_price(String customer_price) {
            this.customer_price = customer_price;
        }

        public String getCustomer_memo() {
            return customer_memo;
        }

        public void setCustomer_memo(String customer_memo) {
            this.customer_memo = customer_memo;
        }

        public String getAssistant_status() {
            return assistant_status;
        }

        public void setAssistant_status(String assistant_status) {
            this.assistant_status = assistant_status;
        }
    }

    public static class AuxiliaryBean {
        private String id;
        private String title;
        private String brand_code;
        private String class_name;
        private String code;
        private String specs;
        private String specs2;
        private String model;
        private String unit;
        private String status;
        private String memo;
        private String createtime;
        private String updatetime;
        private String is_delete;
        private String brand_name;
        private String amount;
        private String deliver_code;
        private String thumb_image;
        private List<?> images;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBrand_code() {
            return brand_code;
        }

        public void setBrand_code(String brand_code) {
            this.brand_code = brand_code;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSpecs() {
            return specs;
        }

        public void setSpecs(String specs) {
            this.specs = specs;
        }

        public String getSpecs2() {
            return specs2;
        }

        public void setSpecs2(String specs2) {
            this.specs2 = specs2;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDeliver_code() {
            return deliver_code;
        }
        public void setDeliver_code(String deliver_code) {
            this.deliver_code = deliver_code;
        }

        public String getThumb_image() {
            return thumb_image;
        }

        public void setThumb_image(String thumb_image) {
            this.thumb_image = thumb_image;
        }

        public List<?> getImages() {
            return images;
        }

        public void setImages(List<?> images) {
            this.images = images;
        }
    }

    public static class MaterialsBean {
        private String order_code;
        private String choose_status;
        private String id;
        private String title;
        private String brand_code;
        private String category_code;
        private String image_count;
        private String class_code;
        private String type;
        private String code;
        private String serial_no;
        private String specs;
        private String quality;
        private String model;
        private String color_no;
        private String color;
        private String min_order_unit;
        private String cost_unit;
        private String min_delivery_unit;
        private String min_purchase_unit;
        private String min_cost_count;
        private String min_dilivery_count;
        private String min_purchase_count;
        private String volume_weight;
        private String unit_weight;
        private String unit_volume;
        private String is_wood_floor;
        private String safe_stock;
        private String area;
        private String length;
        private String width;
        private String height;
        private String volume;
        private String weight;
        private String is_example;
        private String min_stock_unit;
        private String order_stock_ratio;
        private String status;
        private String memo;
        private String createtime;
        private String updatetime;
        private String is_delete;
        private String isset_gift;
        private String tail_no;
        private String min_count;
        private Object extra_nature;
        private String synchro_image;
        private String is_personal;
        private String is_single;
        private String category_name;
        private String class_name;
        private String brand_name;
        private String space_name;
        private String space_id;
        private String thumb_image;
        /**
         * material_code : 1110610210003
         * path : /Uploads/materials/201607/source/57899a507ab32.jpg
         * createtime : 2016-07-16 10:22:15
         * status : null
         */

        private List<ImagesBean> images;

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getChoose_status() {
            return choose_status;
        }

        public void setChoose_status(String choose_status) {
            this.choose_status = choose_status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBrand_code() {
            return brand_code;
        }

        public void setBrand_code(String brand_code) {
            this.brand_code = brand_code;
        }

        public String getCategory_code() {
            return category_code;
        }

        public void setCategory_code(String category_code) {
            this.category_code = category_code;
        }

        public String getImage_count() {
            return image_count;
        }

        public void setImage_count(String image_count) {
            this.image_count = image_count;
        }

        public String getClass_code() {
            return class_code;
        }

        public void setClass_code(String class_code) {
            this.class_code = class_code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSerial_no() {
            return serial_no;
        }

        public void setSerial_no(String serial_no) {
            this.serial_no = serial_no;
        }

        public String getSpecs() {
            return specs;
        }

        public void setSpecs(String specs) {
            this.specs = specs;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getColor_no() {
            return color_no;
        }

        public void setColor_no(String color_no) {
            this.color_no = color_no;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getMin_order_unit() {
            return min_order_unit;
        }

        public void setMin_order_unit(String min_order_unit) {
            this.min_order_unit = min_order_unit;
        }

        public String getCost_unit() {
            return cost_unit;
        }

        public void setCost_unit(String cost_unit) {
            this.cost_unit = cost_unit;
        }

        public String getMin_delivery_unit() {
            return min_delivery_unit;
        }

        public void setMin_delivery_unit(String min_delivery_unit) {
            this.min_delivery_unit = min_delivery_unit;
        }

        public String getMin_purchase_unit() {
            return min_purchase_unit;
        }

        public void setMin_purchase_unit(String min_purchase_unit) {
            this.min_purchase_unit = min_purchase_unit;
        }

        public String getMin_cost_count() {
            return min_cost_count;
        }

        public void setMin_cost_count(String min_cost_count) {
            this.min_cost_count = min_cost_count;
        }

        public String getMin_dilivery_count() {
            return min_dilivery_count;
        }

        public void setMin_dilivery_count(String min_dilivery_count) {
            this.min_dilivery_count = min_dilivery_count;
        }

        public String getMin_purchase_count() {
            return min_purchase_count;
        }

        public void setMin_purchase_count(String min_purchase_count) {
            this.min_purchase_count = min_purchase_count;
        }

        public String getVolume_weight() {
            return volume_weight;
        }

        public void setVolume_weight(String volume_weight) {
            this.volume_weight = volume_weight;
        }

        public String getUnit_weight() {
            return unit_weight;
        }

        public void setUnit_weight(String unit_weight) {
            this.unit_weight = unit_weight;
        }

        public String getUnit_volume() {
            return unit_volume;
        }

        public void setUnit_volume(String unit_volume) {
            this.unit_volume = unit_volume;
        }

        public String getIs_wood_floor() {
            return is_wood_floor;
        }

        public void setIs_wood_floor(String is_wood_floor) {
            this.is_wood_floor = is_wood_floor;
        }

        public String getSafe_stock() {
            return safe_stock;
        }

        public void setSafe_stock(String safe_stock) {
            this.safe_stock = safe_stock;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getIs_example() {
            return is_example;
        }

        public void setIs_example(String is_example) {
            this.is_example = is_example;
        }

        public String getMin_stock_unit() {
            return min_stock_unit;
        }

        public void setMin_stock_unit(String min_stock_unit) {
            this.min_stock_unit = min_stock_unit;
        }

        public String getOrder_stock_ratio() {
            return order_stock_ratio;
        }

        public void setOrder_stock_ratio(String order_stock_ratio) {
            this.order_stock_ratio = order_stock_ratio;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getIsset_gift() {
            return isset_gift;
        }

        public void setIsset_gift(String isset_gift) {
            this.isset_gift = isset_gift;
        }

        public String getTail_no() {
            return tail_no;
        }

        public void setTail_no(String tail_no) {
            this.tail_no = tail_no;
        }

        public String getMin_count() {
            return min_count;
        }

        public void setMin_count(String min_count) {
            this.min_count = min_count;
        }

        public Object getExtra_nature() {
            return extra_nature;
        }

        public void setExtra_nature(Object extra_nature) {
            this.extra_nature = extra_nature;
        }

        public String getSynchro_image() {
            return synchro_image;
        }

        public void setSynchro_image(String synchro_image) {
            this.synchro_image = synchro_image;
        }

        public String getIs_personal() {
            return is_personal;
        }

        public void setIs_personal(String is_personal) {
            this.is_personal = is_personal;
        }

        public String getIs_single() {
            return is_single;
        }

        public void setIs_single(String is_single) {
            this.is_single = is_single;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getSpace_name() {
            return space_name;
        }

        public void setSpace_name(String space_name) {
            this.space_name = space_name;
        }

        public String getSpace_id() {
            return space_id;
        }

        public void setSpace_id(String space_id) {
            this.space_id = space_id;
        }

        public String getThumb_image() {
            return thumb_image;
        }

        public void setThumb_image(String thumb_image) {
            this.thumb_image = thumb_image;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            private String material_code;
            private String path;
            private String createtime;
            private String status;

            public String getMaterial_code() {
                return material_code;
            }

            public void setMaterial_code(String material_code) {
                this.material_code = material_code;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
