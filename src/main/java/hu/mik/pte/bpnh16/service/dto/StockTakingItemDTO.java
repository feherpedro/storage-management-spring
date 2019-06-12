package hu.mik.pte.bpnh16.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link hu.mik.pte.bpnh16.domain.StockTakingItem} entity.
 */
public class StockTakingItemDTO implements Serializable {

    private Long id;

    private Long oldQuantity;

    @NotNull
    private Long newQuantity;

    private Long difference;


    private Long stockTakingId;

    private Long productId;

    private Long statusId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(Long oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    public Long getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(Long newQuantity) {
        this.newQuantity = newQuantity;
    }

    public Long getDifference() {
        return difference;
    }

    public void setDifference(Long difference) {
        this.difference = difference;
    }

    public Long getStockTakingId() {
        return stockTakingId;
    }

    public void setStockTakingId(Long stockTakingId) {
        this.stockTakingId = stockTakingId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockTakingItemDTO stockTakingItemDTO = (StockTakingItemDTO) o;
        if (stockTakingItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockTakingItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockTakingItemDTO{" +
            "id=" + getId() +
            ", oldQuantity=" + getOldQuantity() +
            ", newQuantity=" + getNewQuantity() +
            ", difference=" + getDifference() +
            ", stockTaking=" + getStockTakingId() +
            ", product=" + getProductId() +
            ", status=" + getStatusId() +
            "}";
    }
}
