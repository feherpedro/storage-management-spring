package hu.mik.pte.bpnh16.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link hu.mik.pte.bpnh16.domain.StockTaking} entity.
 */
public class StockTakingDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate stockTakingDate;


    private Long statusId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStockTakingDate() {
        return stockTakingDate;
    }

    public void setStockTakingDate(LocalDate stockTakingDate) {
        this.stockTakingDate = stockTakingDate;
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

        StockTakingDTO stockTakingDTO = (StockTakingDTO) o;
        if (stockTakingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockTakingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockTakingDTO{" +
            "id=" + getId() +
            ", stockTakingDate='" + getStockTakingDate() + "'" +
            ", status=" + getStatusId() +
            "}";
    }
}
