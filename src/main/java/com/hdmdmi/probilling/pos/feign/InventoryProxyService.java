package com.hdmdmi.probilling.pos.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Service
@FeignClient(name = "inventory-service", url = "localhost:8081/v1/probilling")
public interface InventoryProxyService {
    @PutMapping("/inventory/{inventoryId}/decrement-quantity/{count}")
    public ResponseEntity<Void> decrementQuantity(@PathVariable(name = "inventoryId") Long inventoryId,
            @PathVariable(name = "count") Integer count);

}
