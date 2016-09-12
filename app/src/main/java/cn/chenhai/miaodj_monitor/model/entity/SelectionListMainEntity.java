package cn.chenhai.miaodj_monitor.model.entity;

/**
 * Created by ChenHai--霜华 on 2016/7/12. 16:43
 * 邮箱：248866527@qq.com
 */
public class SelectionListMainEntity {


    /**
     * keep_msg :
     * material : {"id":"2270","order_code":"CM320501201608180001","customer_code":"CA201608000004","category_code":"11","class_code":"106","brand_code":"102","material_code":"1110610210005","amount":"261.000","cut_amount":"0","unit":"㎡","source":null,"status":"1","createtime":null,"space_id":"3","prepare_time":null,"start_deliver_time":null,"deliver_time":null,"expect_arrive_time":"2016-09-25","cut_prepare_time":null,"cut_reworking_time":null,"cut_reworked_time":null,"cut_start_deliver_time":null,"cut_deliver_time":null,"cut_expect_arrive_time":null,"cut_status":"1","type":"STANDARD","price_amount":"0","memo":"","material_type":"1","min_order_amount":"0.000","hear_time":null,"cut_hear_time":null,"choose_status":"3","title":"大自然实木复合地板","image_count":null,"code":"1110610210005","serial_no":"10005","specs":"910*126*15/0.6","quality":"","model":"海棠木DSJ03Z","color_no":"","color":"","min_order_unit":"㎡","cost_unit":"支","min_delivery_unit":"件","min_purchase_unit":"件","min_cost_count":null,"min_dilivery_count":null,"min_purchase_count":null,"volume_weight":"","unit_weight":"","unit_volume":"","is_wood_floor":"N","safe_stock":null,"area":"","length":"910.00","width":"126.00","height":"15.00","volume":"1719.90","weight":"0.00","is_example":"N","min_stock_unit":"","order_stock_ratio":"","updatetime":null,"is_delete":"N","isset_gift":"N","tail_no":"","min_count":"1","extra_nature":"","synchro_image":"0","is_personal":null,"is_single":"1","rate_order":"0.1150","rate_purchase":"0.1150","category_name":"地板","class_name":"地板","brand_name":"大自然","sign_time":null,"cut_sign_time":null}
     */

    private String keep_msg;
    /**
     * id : 2270
     * order_code : CM320501201608180001
     * customer_code : CA201608000004
     * category_code : 11
     * class_code : 106
     * brand_code : 102
     * material_code : 1110610210005
     * amount : 261.000
     * cut_amount : 0
     * unit : ㎡
     * source : null
     * status : 1
     * createtime : null
     * space_id : 3
     * prepare_time : null
     * start_deliver_time : null
     * deliver_time : null
     * expect_arrive_time : 2016-09-25
     * cut_prepare_time : null
     * cut_reworking_time : null
     * cut_reworked_time : null
     * cut_start_deliver_time : null
     * cut_deliver_time : null
     * cut_expect_arrive_time : null
     * cut_status : 1
     * type : STANDARD
     * price_amount : 0
     * memo :
     * material_type : 1
     * min_order_amount : 0.000
     * hear_time : null
     * cut_hear_time : null
     * choose_status : 3
     * title : 大自然实木复合地板
     * image_count : null
     * code : 1110610210005
     * serial_no : 10005
     * specs : 910*126*15/0.6
     * quality :
     * model : 海棠木DSJ03Z
     * color_no :
     * color :
     * min_order_unit : ㎡
     * cost_unit : 支
     * min_delivery_unit : 件
     * min_purchase_unit : 件
     * min_cost_count : null
     * min_dilivery_count : null
     * min_purchase_count : null
     * volume_weight :
     * unit_weight :
     * unit_volume :
     * is_wood_floor : N
     * safe_stock : null
     * area :
     * length : 910.00
     * width : 126.00
     * height : 15.00
     * volume : 1719.90
     * weight : 0.00
     * is_example : N
     * min_stock_unit :
     * order_stock_ratio :
     * updatetime : null
     * is_delete : N
     * isset_gift : N
     * tail_no :
     * min_count : 1
     * extra_nature :
     * synchro_image : 0
     * is_personal : null
     * is_single : 1
     * rate_order : 0.1150
     * rate_purchase : 0.1150
     * category_name : 地板
     * class_name : 地板
     * brand_name : 大自然
     * sign_time : null
     * cut_sign_time : null
     */

    private MaterialBean material;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public MaterialBean getMaterial() {
        return material;
    }

    public void setMaterial(MaterialBean material) {
        this.material = material;
    }

    public static class MaterialBean {
        private String id;
        private String order_code;
        private String customer_code;
        private String category_code;
        private String class_code;
        private String brand_code;
        private String material_code;
        private String amount;
        private String cut_amount;
        private String unit;
        private String source;
        private String status;
        private String createtime;
        private String space_id;
        private String prepare_time;
        private String start_deliver_time;
        private String deliver_time;
        private String expect_arrive_time;
        private String cut_prepare_time;
        private String cut_reworking_time;
        private String cut_reworked_time;
        private String cut_start_deliver_time;
        private String cut_deliver_time;
        private String cut_expect_arrive_time;
        private String cut_status;
        private String type;
        private String price_amount;
        private String memo;
        private String material_type;
        private String min_order_amount;
        private String hear_time;
        private String cut_hear_time;
        private String choose_status;
        private String title;
        private String image_count;
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
        private String updatetime;
        private String is_delete;
        private String isset_gift;
        private String tail_no;
        private String min_count;
        private String extra_nature;
        private String synchro_image;
        private String is_personal;
        private String is_single;
        private String rate_order;
        private String rate_purchase;
        private String category_name;
        private String class_name;
        private String brand_name;
        private String sign_time;
        private String cut_sign_time;
        private String purchase_amount;

        public String getPurchase_amount() {
            return purchase_amount;
        }

        public void setPurchase_amount(String purchase_amount) {
            this.purchase_amount = purchase_amount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getCustomer_code() {
            return customer_code;
        }

        public void setCustomer_code(String customer_code) {
            this.customer_code = customer_code;
        }

        public String getCategory_code() {
            return category_code;
        }

        public void setCategory_code(String category_code) {
            this.category_code = category_code;
        }

        public String getClass_code() {
            return class_code;
        }

        public void setClass_code(String class_code) {
            this.class_code = class_code;
        }

        public String getBrand_code() {
            return brand_code;
        }

        public void setBrand_code(String brand_code) {
            this.brand_code = brand_code;
        }

        public String getMaterial_code() {
            return material_code;
        }

        public void setMaterial_code(String material_code) {
            this.material_code = material_code;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCut_amount() {
            return cut_amount;
        }

        public void setCut_amount(String cut_amount) {
            this.cut_amount = cut_amount;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getSpace_id() {
            return space_id;
        }

        public void setSpace_id(String space_id) {
            this.space_id = space_id;
        }

        public String getPrepare_time() {
            return prepare_time;
        }

        public void setPrepare_time(String prepare_time) {
            this.prepare_time = prepare_time;
        }

        public String getStart_deliver_time() {
            return start_deliver_time;
        }

        public void setStart_deliver_time(String start_deliver_time) {
            this.start_deliver_time = start_deliver_time;
        }

        public String getDeliver_time() {
            return deliver_time;
        }

        public void setDeliver_time(String deliver_time) {
            this.deliver_time = deliver_time;
        }

        public String getExpect_arrive_time() {
            return expect_arrive_time;
        }

        public void setExpect_arrive_time(String expect_arrive_time) {
            this.expect_arrive_time = expect_arrive_time;
        }

        public String getCut_prepare_time() {
            return cut_prepare_time;
        }

        public void setCut_prepare_time(String cut_prepare_time) {
            this.cut_prepare_time = cut_prepare_time;
        }

        public String getCut_reworking_time() {
            return cut_reworking_time;
        }

        public void setCut_reworking_time(String cut_reworking_time) {
            this.cut_reworking_time = cut_reworking_time;
        }

        public String getCut_reworked_time() {
            return cut_reworked_time;
        }

        public void setCut_reworked_time(String cut_reworked_time) {
            this.cut_reworked_time = cut_reworked_time;
        }

        public String getCut_start_deliver_time() {
            return cut_start_deliver_time;
        }

        public void setCut_start_deliver_time(String cut_start_deliver_time) {
            this.cut_start_deliver_time = cut_start_deliver_time;
        }

        public String getCut_deliver_time() {
            return cut_deliver_time;
        }

        public void setCut_deliver_time(String cut_deliver_time) {
            this.cut_deliver_time = cut_deliver_time;
        }

        public String getCut_expect_arrive_time() {
            return cut_expect_arrive_time;
        }

        public void setCut_expect_arrive_time(String cut_expect_arrive_time) {
            this.cut_expect_arrive_time = cut_expect_arrive_time;
        }

        public String getCut_status() {
            return cut_status;
        }

        public void setCut_status(String cut_status) {
            this.cut_status = cut_status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrice_amount() {
            return price_amount;
        }

        public void setPrice_amount(String price_amount) {
            this.price_amount = price_amount;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getMaterial_type() {
            return material_type;
        }

        public void setMaterial_type(String material_type) {
            this.material_type = material_type;
        }

        public String getMin_order_amount() {
            return min_order_amount;
        }

        public void setMin_order_amount(String min_order_amount) {
            this.min_order_amount = min_order_amount;
        }

        public String getHear_time() {
            return hear_time;
        }

        public void setHear_time(String hear_time) {
            this.hear_time = hear_time;
        }

        public String getCut_hear_time() {
            return cut_hear_time;
        }

        public void setCut_hear_time(String cut_hear_time) {
            this.cut_hear_time = cut_hear_time;
        }

        public String getChoose_status() {
            return choose_status;
        }

        public void setChoose_status(String choose_status) {
            this.choose_status = choose_status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage_count() {
            return image_count;
        }

        public void setImage_count(String image_count) {
            this.image_count = image_count;
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

        public String getExtra_nature() {
            return extra_nature;
        }

        public void setExtra_nature(String extra_nature) {
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

        public String getRate_order() {
            return rate_order;
        }

        public void setRate_order(String rate_order) {
            this.rate_order = rate_order;
        }

        public String getRate_purchase() {
            return rate_purchase;
        }

        public void setRate_purchase(String rate_purchase) {
            this.rate_purchase = rate_purchase;
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

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public String getCut_sign_time() {
            return cut_sign_time;
        }

        public void setCut_sign_time(String cut_sign_time) {
            this.cut_sign_time = cut_sign_time;
        }
    }
}
