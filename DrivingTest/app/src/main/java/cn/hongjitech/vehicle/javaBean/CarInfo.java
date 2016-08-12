package cn.hongjitech.vehicle.javaBean;

/**
 * 车辆信息
 */
public class CarInfo {
    private int id;
    private String car_num;
    private int licence_type_id;
    private int vehicle_type_id;
    private int oil_gas_type_id;
    private int is_bus;
    private String insurance_valid;
    private int school_id;
    private int status;
    private String created_at;
    private String updated_at;
    private String drivice_id;

    public CarInfo() {
    }

    public CarInfo(int id, String car_num, int licence_type_id, int vehicle_type_id, int oil_gas_type_id, int is_bus, String insurance_valid, int school_id, int status, String created_at, String updated_at, String drivice_id) {
        this.id = id;
        this.car_num = car_num;
        this.licence_type_id = licence_type_id;
        this.vehicle_type_id = vehicle_type_id;
        this.oil_gas_type_id = oil_gas_type_id;
        this.is_bus = is_bus;
        this.insurance_valid = insurance_valid;
        this.school_id = school_id;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.drivice_id = drivice_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCar_num() {
        return car_num;
    }

    public void setCar_num(String car_num) {
        this.car_num = car_num;
    }

    public int getLicence_type_id() {
        return licence_type_id;
    }

    public void setLicence_type_id(int licence_type_id) {
        this.licence_type_id = licence_type_id;
    }

    public int getVehicle_type_id() {
        return vehicle_type_id;
    }

    public void setVehicle_type_id(int vehicle_type_id) {
        this.vehicle_type_id = vehicle_type_id;
    }

    public int getOil_gas_type_id() {
        return oil_gas_type_id;
    }

    public void setOil_gas_type_id(int oil_gas_type_id) {
        this.oil_gas_type_id = oil_gas_type_id;
    }

    public int getIs_bus() {
        return is_bus;
    }

    public void setIs_bus(int is_bus) {
        this.is_bus = is_bus;
    }

    public String getInsurance_valid() {
        return insurance_valid;
    }

    public void setInsurance_valid(String insurance_valid) {
        this.insurance_valid = insurance_valid;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDrivice_id() {
        return drivice_id;
    }

    public void setDrivice_id(String drivice_id) {
        this.drivice_id = drivice_id;
    }
}
