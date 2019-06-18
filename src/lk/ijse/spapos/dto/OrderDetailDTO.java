package lk.ijse.spapos.dto;

public class OrderDetailDTO {
    private String odID;
    private String itemCode;
    private int qty;
    private double unitPricr;

    public OrderDetailDTO(String odID, String itemCode, int qty, double unitPricr) {
        this.odID = odID;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPricr = unitPricr;
    }

    public String getOdID() {

        return odID;
    }

    public void setOdID(String odID) {
        this.odID = odID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPricr() {
        return unitPricr;
    }

    public void setUnitPricr(double unitPricr) {
        this.unitPricr = unitPricr;
    }
}
