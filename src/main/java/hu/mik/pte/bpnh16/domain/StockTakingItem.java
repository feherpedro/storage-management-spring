package hu.mik.pte.bpnh16.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A StockTakingItem.
 */
@Entity
@Table(name = "stock_taking_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "stocktakingitem")
public class StockTakingItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "old_quantity")
    private Long oldQuantity;

    @NotNull
    @Column(name = "new_quantity", nullable = false)
    private Long newQuantity;

    @Column(name = "difference")
    private Long difference;

    @ManyToOne
    @JsonIgnoreProperties("stockTakingItems")
    private StockTaking stockTaking;

    @ManyToOne
    @JsonIgnoreProperties("stockTakingItems")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("stockTakingItems")
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOldQuantity() {
        return oldQuantity;
    }

    public StockTakingItem oldQuantity(Long oldQuantity) {
        this.oldQuantity = oldQuantity;
        return this;
    }

    public void setOldQuantity(Long oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    public Long getNewQuantity() {
        return newQuantity;
    }

    public StockTakingItem newQuantity(Long newQuantity) {
        this.newQuantity = newQuantity;
        return this;
    }

    public void setNewQuantity(Long newQuantity) {
        this.newQuantity = newQuantity;
    }

    public Long getDifference() {
        return difference;
    }

    public StockTakingItem difference(Long difference) {
        this.difference = difference;
        return this;
    }

    public void setDifference(Long difference) {
        this.difference = difference;
    }

    public StockTaking getStockTaking() {
        return stockTaking;
    }

    public StockTakingItem stockTaking(StockTaking stockTaking) {
        this.stockTaking = stockTaking;
        return this;
    }

    public void setStockTaking(StockTaking stockTaking) {
        this.stockTaking = stockTaking;
    }

    public Product getProduct() {
        return product;
    }

    public StockTakingItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Status getStatus() {
        return status;
    }

    public StockTakingItem status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockTakingItem)) {
            return false;
        }
        return id != null && id.equals(((StockTakingItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StockTakingItem{" +
            "id=" + getId() +
            ", oldQuantity=" + getOldQuantity() +
            ", newQuantity=" + getNewQuantity() +
            ", difference=" + getDifference() +
            "}";
    }
}
