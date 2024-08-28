package fr.doranco.ecom.services.admin.adminOrder;

import fr.doranco.ecom.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {

    List<OrderDto> getAllPlacedOrders();
}
