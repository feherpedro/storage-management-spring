package hu.mik.pte.bpnh16.service.dto;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link hu.mik.pte.bpnh16.domain.OrderEntity} entity.
 */
public class OrderEntityDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate createDate;

    private LocalDate paymentDate;

    private LocalDate dueDate;

    private Long statusId;

    private String statusName;

    private List<OrderItemDTO> orderItemList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<OrderItemDTO> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemDTO> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderEntityDTO orderEntityDTO = (OrderEntityDTO) o;
        if (orderEntityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderEntityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderEntityDTO{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", status=" + getStatusId() +
            "}";
    }
}
