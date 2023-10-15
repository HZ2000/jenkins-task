package com.haykz.inventoryservice.service;

import com.haykz.inventoryservice.dto.InventoryResponse;
import com.haykz.inventoryservice.model.Inventory;
import com.haykz.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inventoryService = new InventoryService(inventoryRepository);
    }

    @Test
    void testIsInStock() {
        // Mock the behavior of the inventoryRepository
        List<String> skuCodes = Arrays.asList("SKU1", "SKU2", "SKU3");
        List<Inventory> mockInventories = Arrays.asList(
                new Inventory(1L, "SKU1", 5),
                new Inventory(2L, "SKU2", 0),
                new Inventory(3L, "SKU3", -2)
        );
        Mockito.when(inventoryRepository.findBySkuCodeIn(skuCodes)).thenReturn(mockInventories);

        // Call the service method
        List<InventoryResponse> response = inventoryService.isInStock(skuCodes);

        // Verify the results
        assertEquals(3, response.size());

        // Check SKU1, which has a quantity > 0
        assertEquals("SKU1", response.get(0).getSkuCode());
        assertTrue(response.get(0).isInStock());

        // Check SKU2, which has a quantity = 0
        assertEquals("SKU2", response.get(1).getSkuCode());
        assertFalse(response.get(1).isInStock());

        // Check SKU3, which has a quantity < 0
        assertEquals("SKU3", response.get(2).getSkuCode());
        assertFalse(response.get(2).isInStock());
    }
}

